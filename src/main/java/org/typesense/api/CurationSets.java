package org.typesense.api;

import org.typesense.model.CurationSetCreateSchema;
import org.typesense.model.CurationSetSchema;

public class CurationSets {

    private ApiCall apiCall;
    public final static String RESOURCEPATH = "/curation_sets";

    public CurationSets(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Create or update a curation set.
     *
     * <p>
     * Create or update a curation set with the given name
     *
     * <p>
     * HTTP: PUT /curation_sets/{curationSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/curation.html">Typesense docs</a>
     */
    public CurationSetSchema upsert(String curationSetName, CurationSetCreateSchema curationSetCreateSchema) throws Exception {
        return this.apiCall.put(getEndpoint(curationSetName), curationSetCreateSchema, null, CurationSetSchema.class);
    }

    /**
     * Retrieve a curation set.
     *
     * <p>
     * Retrieve a specific curation set by its name
     *
     * <p>
     * HTTP: GET /curation_sets/{curationSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/curation.html">Typesense docs</a>
     */
    public CurationSetSchema[] retrieve() throws Exception {
        return this.apiCall.get(this.getEndpoint(null), null, CurationSetSchema[].class);
    }

    public String getEndpoint(String operation) {
        return RESOURCEPATH + "/" + (operation == null ? "" : operation);
    }
}
