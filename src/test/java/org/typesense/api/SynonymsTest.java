package org.typesense.api;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.SearchSynonym;
import org.typesense.model.SearchSynonymDeleteResponse;
import org.typesense.model.SearchSynonymSchema;
import org.typesense.model.SearchSynonymsResponse;

class SynonymsTest {

    private Client client;
    private Helper helper;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        client = helper.getClient();
        helper.teardown();
        helper.createTestCollection();
        helper.createTestSynonym();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testUpsert() throws Exception {
        SearchSynonymSchema synonym = new SearchSynonymSchema();

        // One-way
        synonym.addSynonymsItem("dictionary").addSynonymsItem("guide").addSynonymsItem("encyclopedia");
        synonym.root("books");

        SearchSynonym result = this.client.collections("books").synonyms().upsert("books-synonyms", synonym);

        assertNotNull(result);

        assertEquals(3, result.getSynonyms().size());
        assertEquals(synonym.getSynonyms(), result.getSynonyms());
    }

    @Test
    void testRetrieve() throws Exception {
        SearchSynonymSchema synonym = new SearchSynonymSchema();
        synonym.addSynonymsItem("blazer").addSynonymsItem("coat").addSynonymsItem("jacket");

        SearchSynonym result = this.client.collections("books").synonyms("coat-synonyms").retrieve();

        assertNotNull(result);

        assertEquals(3, synonym.getSynonyms().size());
        
        assertEquals("blazer", synonym.getSynonyms().get(0));
        assertEquals("coat", synonym.getSynonyms().get(1));
        assertEquals("jacket", synonym.getSynonyms().get(2));
    }

    @Test
    void testRetrieveAll() throws Exception {
        SearchSynonymsResponse result = this.client.collections("books").synonyms().retrieve();

        assertNotNull(result);

        assertEquals(1, result.getSynonyms().size());

        SearchSynonym synonym = result.getSynonyms().get(0);

        assertEquals("coat-synonyms", synonym.getId());
        assertEquals(3, synonym.getSynonyms().size());
        
        assertEquals("blazer", synonym.getSynonyms().get(0));
        assertEquals("coat", synonym.getSynonyms().get(1));
        assertEquals("jacket", synonym.getSynonyms().get(2));
    }

    @Test
    void testDelete() throws Exception {
        SearchSynonymDeleteResponse result = this.client.collections("books").synonyms("coat-synonyms").delete();

        assertEquals("coat-synonyms", result.getId());
    }
}