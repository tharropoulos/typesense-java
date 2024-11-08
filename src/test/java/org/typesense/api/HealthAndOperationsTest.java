package org.typesense.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.HealthStatus;

class HealthAndOperationsTest {

    private Client client;
    private Helper helper;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        helper.teardown();
        client = helper.getClient();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testRetrieve() throws Exception {
        HealthStatus result = this.client.health.retrieve();

        assertEquals(result.isOk(), true);
    }

    @Test
    void testPerformSnapshot() throws Exception {
        HashMap<String, String> query = new HashMap<>();
        query.put("snapshot_path", "/tmp/typesense-data-snapshot");
        Map<String, String> result = client.operations.perform("snapshot", query);

        assertEquals(result.get("success"), true);
    }

    @Test
    void testPerformVote() throws Exception {
        Map<String, String> result = client.operations.perform("vote");

        assertNotNull(result.get("success"));
    }

    @Test
    void testMetrics() throws Exception {
        assertNotNull(client.metrics.retrieve());
    }

    @Test
    void testDebug() throws Exception {
        assertNotNull(client.debug.retrieve());
    }
}