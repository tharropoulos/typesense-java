package org.typesense.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.SearchOverride;
import org.typesense.model.SearchOverrideDeleteResponse;
import org.typesense.model.SearchOverrideExclude;
import org.typesense.model.SearchOverrideInclude;
import org.typesense.model.SearchOverrideRule;
import org.typesense.model.SearchOverrideSchema;
import org.typesense.model.SearchOverridesResponse;

class OverridesTest {

    private Client client;
    private Helper helper;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        helper.teardown();
        client = helper.getClient();
        helper.createTestCollection();
        helper.createTestOverrirde();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testUpsert() throws Exception {
        SearchOverrideSchema searchOverrideSchema = new SearchOverrideSchema();

        List<SearchOverrideInclude> searchOverrideIncludes = new ArrayList<>();
        searchOverrideIncludes.add(new SearchOverrideInclude().id("422").position(1));
        searchOverrideIncludes.add(new SearchOverrideInclude().id("54").position(2));

        List<SearchOverrideExclude> searchOverrideExcludes = new ArrayList<>();
        searchOverrideExcludes.add(new SearchOverrideExclude().id("287"));

        searchOverrideSchema.rule(new SearchOverrideRule().query("apple").match(SearchOverrideRule.MatchEnum.EXACT))
                .includes(searchOverrideIncludes)
                .excludes(searchOverrideExcludes);

        SearchOverride result = client.collections("books").overrides().upsert("apple", searchOverrideSchema);

        assertEquals("apple", result.getRule().getQuery());
        assertEquals(SearchOverrideRule.MatchEnum.EXACT, result.getRule().getMatch());
        assertEquals(searchOverrideExcludes, result.getExcludes());
        assertEquals(searchOverrideIncludes, result.getIncludes());
    }

    @Test
    void testRetrieveAll() throws Exception {
        SearchOverridesResponse result = this.client.collections("books").overrides().retrieve();

        SearchOverrideSchema searchOverrideSchema = new SearchOverrideSchema();
        List<SearchOverrideInclude> searchOverrideIncludes = new ArrayList<>();
        searchOverrideIncludes.add(new SearchOverrideInclude().id("422").position(1));
        searchOverrideSchema.rule(new SearchOverrideRule().query("apple").match(SearchOverrideRule.MatchEnum.EXACT))
                .includes(searchOverrideIncludes);

        assertEquals(1, result.getOverrides().size());

        SearchOverride searchOverride = result.getOverrides().get(0);

        assertEquals("apple", searchOverride.getRule().getQuery());
        assertEquals(SearchOverrideRule.MatchEnum.EXACT, searchOverride.getRule().getMatch());
        assertEquals(searchOverrideIncludes, searchOverride.getIncludes());
    }

    @Test
    void testRetrieve() throws Exception {
        SearchOverride searchOverride = this.client.collections("books").overrides("customize-apple").retrieve();

        SearchOverrideSchema searchOverrideSchema = new SearchOverrideSchema();
        List<SearchOverrideInclude> searchOverrideIncludes = new ArrayList<>();
        searchOverrideIncludes.add(new SearchOverrideInclude().id("422").position(1));
        searchOverrideSchema.rule(new SearchOverrideRule().query("apple").match(SearchOverrideRule.MatchEnum.EXACT))
                .includes(searchOverrideIncludes);

        assertEquals("apple", searchOverride.getRule().getQuery());
        assertEquals(SearchOverrideRule.MatchEnum.EXACT, searchOverride.getRule().getMatch());
        assertEquals(searchOverrideIncludes, searchOverride.getIncludes());
    }

    @Test
    void testDelete() throws Exception {
        SearchOverrideDeleteResponse searchOverride = this.client.collections("books").overrides("customize-apple")
                .delete();

        assertEquals("customize-apple", searchOverride.getId());
    }
}