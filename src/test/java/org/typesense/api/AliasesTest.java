package org.typesense.api;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.CollectionAlias;
import org.typesense.model.CollectionAliasSchema;
import org.typesense.model.CollectionAliasesResponse;

class AliasesTest {

    private Client client;
    private Helper helper;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        helper.teardown();
        client = helper.getClient();
        helper.createTestAlias();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testUpsert() throws Exception {
        CollectionAliasSchema collectionAliasSchema = new CollectionAliasSchema();
        collectionAliasSchema.collectionName("books_june11");
        CollectionAlias result = client.aliases().upsert("books1", collectionAliasSchema);

        assertEquals(result.getCollectionName(), "books_june11");
        assertEquals(result.getName(), "books1");
    }

    @Test
    void testUpsertWithURLEncodedName() throws Exception {
        CollectionAliasSchema collectionAliasSchema = new CollectionAliasSchema();
        collectionAliasSchema.collectionName("books_june11");

        CollectionAlias result = client.aliases().upsert("books1 ~!@#$%^&*()_++-=/'", collectionAliasSchema);
        assertEquals(result.getName(), "books1 ~!@#$%^&*()_++-=/'");
        assertEquals(result.getCollectionName(), "books_june11");
    }

    @Test
    void testRetrieveAll() throws Exception {
        CollectionAliasesResponse result = client.aliases().retrieve();

        assertEquals(result.getAliases().size(), 1);
        assertEquals(result.getAliases().get(0).getName(), "books");
        assertEquals(result.getAliases().get(0).getCollectionName(),"books_june11");
    }

    @Test
    void testRetrieveSingleAlias() throws Exception {
        CollectionAlias collectionAlias = client.aliases("books").retrieve();

        assertEquals(collectionAlias.getName(), "books");
        assertEquals(collectionAlias.getCollectionName(), "books_june11");
    }

    @Test
    void testDelete() throws Exception {
        CollectionAlias collectionAlias = client.aliases("books").delete();

        assertEquals(collectionAlias.getName(), "books");
        assertEquals(collectionAlias.getCollectionName(), "books_june11");
    }
}