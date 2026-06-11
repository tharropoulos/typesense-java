package org.typesense.api;

import org.typesense.api.utils.URLEncoding;
import org.typesense.model.CurationSetCreateSchema;
import org.typesense.model.CurationSetSchema;
import org.typesense.model.CurationSetDeleteSchema;

public class CurationSet {

    private String curationSetName;
    private ApiCall apiCall;

    public CurationSet(String curationSetName, ApiCall apiCall) {
        this.curationSetName = curationSetName;
        this.apiCall = apiCall;
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
    public CurationSetCreateSchema retrieve() throws Exception {
        return this.apiCall.get(this.getEndpoint(), null, CurationSetCreateSchema.class);
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
    public CurationSetSchema upsert(CurationSetCreateSchema curationSetCreateSchema) throws Exception {
        return this.apiCall.put(this.getEndpoint(), curationSetCreateSchema, null, CurationSetSchema.class);
    }

    /**
     * Delete a curation set.
     *
     * <p>
     * Delete a specific curation set by its name
     *
     * <p>
     * HTTP: DELETE /curation_sets/{curationSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/curation.html">Typesense docs</a>
     */
    public CurationSetDeleteSchema delete() throws Exception {
        return this.apiCall.delete(this.getEndpoint(), null, CurationSetDeleteSchema.class);
    }

    public String getEndpoint() {
        return "/curation_sets/" + URLEncoding.encodeURIComponent(this.curationSetName);
    }
}
