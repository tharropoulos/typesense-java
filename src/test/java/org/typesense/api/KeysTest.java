package org.typesense.api;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.ApiKey;
import org.typesense.model.ApiKeySchema;
import org.typesense.model.ApiKeysResponse;
import org.typesense.model.ApiKeyDeleteResponse;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.ApiKey;
import org.typesense.model.ApiKeySchema;
import org.typesense.model.ApiKeysResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

class KeysTest {

    private Client client;
    private Helper helper;
    private String testKey;
    private Long id;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        helper.teardown();
        client = helper.getClient();
        ApiKey key = helper.createTestKey();
        testKey = key.getValue();
        id = key.getId();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testCreate() throws Exception {
        ApiKeySchema apiKeySchema = new ApiKeySchema();
        List<String> actionValues = new ArrayList<>();
        List<String> collectionValues = new ArrayList<>();

        actionValues.add("*");
        collectionValues.add("*");

        apiKeySchema.description("Admin Key").actions(actionValues).collections(collectionValues);

        ApiKey result = client.keys().create(apiKeySchema);

        assertNotNull(result);
        assertEquals("Admin Key", result.getDescription());
        assertEquals(actionValues, result.getActions());
        assertEquals(collectionValues, result.getCollections());
    }

    @Test
    void testCreateSearchOnly() throws Exception {
        ApiKeySchema apiKeySchema = new ApiKeySchema();
        List<String> actionValues = new ArrayList<>();
        List<String> collectionValues = new ArrayList<>();

        actionValues.add("documents:search");
        collectionValues.add("books");

        apiKeySchema.description("Search only Key").actions(actionValues).collections(collectionValues);

        ApiKey result = client.keys().create(apiKeySchema);

        assertNotNull(result);
        assertEquals("Search only Key", result.getDescription());
        assertEquals(actionValues, result.getActions());
        assertEquals(collectionValues, result.getCollections());
    }

    @Test
    void testRetrieve() throws Exception {
        ApiKey result = this.client.keys(id).retrieve();

        List<String> actionValues = new ArrayList<>();
        List<String> collectionValues = new ArrayList<>();
        actionValues.add("*");
        collectionValues.add("*");

        assertNotNull(result);
        assertEquals("Admin Key", result.getDescription());
        assertEquals(actionValues, result.getActions());
        assertEquals(collectionValues, result.getCollections());
    }

    @Test
    void testRetrieveAll() throws Exception {
        ApiKeysResponse result = client.keys().retrieve();

        List<String> actionValues = new ArrayList<>();
        List<String> collectionValues = new ArrayList<>();
        actionValues.add("*");
        collectionValues.add("*");

        assertNotNull(result);
        assertEquals(1, result.getKeys().size());
        assertEquals("Admin Key", result.getKeys().get(0).getDescription());
        assertEquals(actionValues, result.getKeys().get(0).getActions());
        assertEquals(collectionValues, result.getKeys().get(0).getCollections());
    }

    @Test
    void testDelete() throws Exception {
        ApiKeyDeleteResponse result = this.client.keys(id).delete();

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testScopedKey() throws Exception {
        String keyDef = "keyDef1234567890";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter_by", "company_id:124");

        String scopedKey = this.client.keys().generateScopedSearchKey(keyDef, parameters);

        assertNotNull(scopedKey);

        byte[] decodedBytes = Base64.getDecoder().decode(scopedKey);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        String digest = decodedString.substring(0, 44); // Base64 encoded HMAC-SHA256 is 44 characters
        String keyPrefix = decodedString.substring(44, 48);
        String params = decodedString.substring(48);

        assertEquals(keyDef.substring(0, 4), keyPrefix);

        ObjectMapper mapper = new ObjectMapper();
        String expectedParams = mapper.writeValueAsString(parameters);
        assertEquals(expectedParams, params);

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec sks = new SecretKeySpec(keyDef.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(sks);
        byte[] expectedHmac = mac.doFinal(params.getBytes(StandardCharsets.UTF_8));
        String expectedDigest = Base64.getEncoder().encodeToString(expectedHmac);
        assertEquals(expectedDigest, digest);
    }
}
