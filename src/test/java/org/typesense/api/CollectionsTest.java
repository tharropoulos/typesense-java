package org.typesense.api;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.typesense.model.CollectionResponse;
import org.typesense.model.CollectionSchema;
import org.typesense.model.Field;
import org.typesense.model.FieldEmbed;
import org.typesense.model.FieldEmbedModelConfig;

class CollectionsTest {

    Client client;
    private Helper helper;

    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        helper.teardown();
        this.client = helper.getClient();
    }

    @AfterEach
    void tearDown() throws Exception {
        helper.teardown();
    }

    @Test
    void testRetrieveAllCollections() throws Exception {
        helper.createTestCollection();
        CollectionResponse[] result = client.collections().retrieve();

        assertEquals(result.length, 1);

        CollectionResponse collection = result[0];
        assertEquals(collection.getName(), "books");
        assertEquals(collection.getNumDocuments(), 0);
        assertEquals(collection.getFields().size(), 1);

        Field field = collection.getFields().get(0);

        assertEquals(field.getName(), ".*");
        assertEquals(field.getType(), "auto");
        assertEquals(field.isOptional(), true);
        assertEquals(field.isFacet(), false);
        assertEquals(field.isSort(), false);
        assertEquals(field.isIndex(), true);
        assertEquals(field.isInfix(), false);
        assertEquals(field.getReference(), null);
        assertEquals(field.getNumDim(), null);
        assertEquals(field.isStore(), true);
        assertEquals(field.isStem(), false);
        assertEquals(field.getEmbed(), null);
    }

    @Test
    void testRetrieveSingleCollection() throws Exception {
        helper.createTestCollection();
        CollectionResponse collection = client.collections("books").retrieve();

        assertEquals(collection.getName(), "books");
        assertEquals(collection.getNumDocuments(), 0);
        assertEquals(collection.getFields().size(), 1);

        Field field = collection.getFields().get(0);

        assertEquals(field.getName(), ".*");
        assertEquals(field.getType(), "auto");
        assertEquals(field.isOptional(), true);
        assertEquals(field.isFacet(), false);
        assertEquals(field.isSort(), false);
        assertEquals(field.isIndex(), true);
        assertEquals(field.isInfix(), false);
        assertEquals(field.getReference(), null);
        assertEquals(field.getNumDim(), null);
        assertEquals(field.isStore(), true);
        assertEquals(field.isStem(), false);
        assertEquals(field.getEmbed(), null);
    }

    @Test
    void testDeleteCollection() throws Exception {
        helper.createTestCollection();
        CollectionResponse collection = client.collections("books").delete();

        assertEquals(collection.getName(), "books");
        assertEquals(collection.getNumDocuments(), 0);
        assertEquals(collection.getFields().size(), 1);

        Field field = collection.getFields().get(0);

        assertEquals(field.getName(), ".*");
        assertEquals(field.getType(), "auto");
        assertEquals(field.isOptional(), true);
        assertEquals(field.isFacet(), false);
        assertEquals(field.isSort(), false);
        assertEquals(field.isIndex(), true);
        assertEquals(field.isInfix(), false);
        assertEquals(field.getReference(), null);
        assertEquals(field.getNumDim(), null);
        assertEquals(field.isStore(), true);
        assertEquals(field.isStem(), false);
        assertEquals(field.getEmbed(), null);
    }

    @Test
    void testCreateCollection() throws Exception {

        ArrayList<Field> fields = new ArrayList<>();
        fields.add(new Field().name("countryName").type(FieldTypes.STRING));
        fields.add(new Field().name("capital").type(FieldTypes.STRING));
        fields.add(new Field().name("gdp").type(FieldTypes.INT32).facet(true));

        CollectionSchema collectionSchema = new CollectionSchema();
        collectionSchema.name("Countries").fields(fields);

        CollectionResponse collection = client.collections().create(collectionSchema);

        assertEquals(collection.getName(), "Countries");
        assertEquals(collection.getNumDocuments(), 0);
        assertEquals(collection.getFields().size(), 3);
        
        Field field = collection.getFields().get(0);
        assertEquals(field.getName(), "countryName");
        assertEquals(field.getType(), "string");
        assertEquals(field.isOptional(), false);
        assertEquals(field.isFacet(), false);
        assertEquals(field.isSort(), false);
        assertEquals(field.isIndex(), true);
        assertEquals(field.isInfix(), false);
        assertEquals(field.getReference(), null);
        assertEquals(field.getNumDim(), null);
        assertEquals(field.isStore(), true);
        assertEquals(field.isStem(), false);
        assertEquals(field.getEmbed(), null);
    }

    @Test
    void testCreateCollectionWithModel() throws Exception {
        ArrayList<Field> fields = new ArrayList<>();
        fields.add(new Field().name("title").type(FieldTypes.STRING));

        ArrayList<String> embedFrom = new ArrayList<>();
        embedFrom.add("title");

        fields.add(new Field().name("embedding").type(FieldTypes.FLOAT_ARRAY).embed(
            new FieldEmbed().from(embedFrom).modelConfig(new FieldEmbedModelConfig().modelName("ts/e5-small"))
        ));

        CollectionSchema collectionSchema = new CollectionSchema();
        collectionSchema.name("titles").fields(fields);

        CollectionResponse collection = client.collections().create(collectionSchema);

        assertEquals(collection.getName(), "titles");
        assertEquals(collection.getNumDocuments(), 0);

        Field field = collection.getFields().get(1);
        assertEquals(field.getName(), "embedding");
        assertEquals(field.getType(), "float[]");
        assertEquals(field.isOptional(), false);
        assertEquals(field.isFacet(), false);
        assertEquals(field.isSort(), false);
        assertEquals(field.isIndex(), true);
        assertEquals(field.isInfix(), false);
        assertEquals(field.getReference(), null);
        assertEquals(field.getNumDim(), 384);
        assertEquals(field.getVecDist(), "cosine");
        assertEquals(field.isStore(), true);
        assertEquals(field.isStem(), false);
        assertEquals(field.getEmbed().getFrom().get(0), "title");
        assertEquals(field.getEmbed().getModelConfig().getModelName(), "ts/e5-small");
    }
}