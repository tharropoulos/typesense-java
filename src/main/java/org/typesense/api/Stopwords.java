package org.typesense.api;

import org.typesense.api.utils.URLEncoding;
import org.typesense.model.StopwordsSetSchema;
import org.typesense.model.StopwordsSetUpsertSchema;
import org.typesense.model.StopwordsSetsRetrieveAllSchema;

public class Stopwords {
    public final static String RESOURCEPATH = "/stopwords";

    private final ApiCall apiCall;

    public Stopwords(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    /**
     * Upserts a stopwords set.
     *
     * <p>
     * When an analytics rule is created, we give it a name and describe the type, the source collections and the destination collection.
     *
     * <p>
     * HTTP: PUT /stopwords/{setId}
     *
     * @see <a href="https://typesense.org/docs/latest/api/stopwords.html">Typesense docs</a>
     */
    public StopwordsSetSchema upsert(String stopwordSetId, StopwordsSetUpsertSchema stopwordSet) throws Exception {
        return this.apiCall.put(getEndpoint(stopwordSetId), stopwordSet, null, StopwordsSetSchema.class);
    }

    /**
     * Retrieves all stopwords sets.
     *
     * <p>
     * Retrieve the details of all stopwords sets
     *
     * <p>
     * HTTP: GET /stopwords
     *
     * @see <a href="https://typesense.org/docs/latest/api/stopwords.html">Typesense docs</a>
     */
    public StopwordsSetsRetrieveAllSchema retrieve() throws Exception {
        return this.apiCall.get(Stopwords.RESOURCEPATH, null, StopwordsSetsRetrieveAllSchema.class);
    }

    private String getEndpoint(String stopwordSetId) {
        return RESOURCEPATH + "/" + URLEncoding.encodeURIComponent(stopwordSetId);
    }

}
