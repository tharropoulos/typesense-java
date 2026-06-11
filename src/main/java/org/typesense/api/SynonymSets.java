package org.typesense.api;

import java.util.List;
import org.typesense.model.SynonymSetCreateSchema;
import org.typesense.model.SynonymSetSchema;

public class SynonymSets {

    private ApiCall apiCall;
    public final static String RESOURCEPATH = "/synonym_sets";

    public SynonymSets(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Create or update a synonym set.
     *
     * <p>
     * Create or update a synonym set with the given name
     *
     * <p>
     * HTTP: PUT /synonym_sets/{synonymSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/synonyms.html">Typesense docs</a>
     */
    public SynonymSetSchema upsert(String synonymSetName, SynonymSetCreateSchema synonymSetCreateSchema) throws Exception {
        return this.apiCall.put(getEndpoint(synonymSetName), synonymSetCreateSchema, null, SynonymSetSchema.class);
    }

    /**
     * Retrieve a synonym set.
     *
     * <p>
     * Retrieve a specific synonym set by its name
     *
     * <p>
     * HTTP: GET /synonym_sets/{synonymSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/synonyms.html">Typesense docs</a>
     */
    public SynonymSetSchema[] retrieve() throws Exception {
        return this.apiCall.get(this.getEndpoint(null), null, SynonymSetSchema[].class);
    }

    public String getEndpoint(String operation) {
        return RESOURCEPATH + "/" + (operation == null ? "" : operation);
    }
} 