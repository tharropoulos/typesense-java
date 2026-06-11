package org.typesense.api;

import org.typesense.api.utils.URLEncoding;

import org.typesense.model.SynonymSetCreateSchema;
import org.typesense.model.SynonymSetSchema;
import org.typesense.model.SynonymSetDeleteSchema;

public class SynonymSet {

    private String synonymSetName;
    private ApiCall apiCall;

    public SynonymSet(String synonymSetName, ApiCall apiCall) {
        this.synonymSetName = synonymSetName;
        this.apiCall = apiCall;
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
    public SynonymSetCreateSchema retrieve() throws Exception {
        return this.apiCall.get(this.getEndpoint(), null, SynonymSetCreateSchema.class);
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
    public SynonymSetSchema upsert(SynonymSetCreateSchema synonymSetCreateSchema) throws Exception {
        return this.apiCall.put(this.getEndpoint(), synonymSetCreateSchema, null, SynonymSetSchema.class);
    }

    /**
     * Delete a synonym set.
     *
     * <p>
     * Delete a specific synonym set by its name
     *
     * <p>
     * HTTP: DELETE /synonym_sets/{synonymSetName}
     *
     * @see <a href="https://typesense.org/docs/latest/api/synonyms.html">Typesense docs</a>
     */
    public SynonymSetDeleteSchema delete() throws Exception {
        return this.apiCall.delete(this.getEndpoint(), null, SynonymSetDeleteSchema.class);
    }

    public String getEndpoint() {
        return "/synonym_sets/" + URLEncoding.encodeURIComponent(this.synonymSetName);
    }
} 