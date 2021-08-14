package com.biock.cms;

public final class CmsApi {

    public static final String DEFAULT_LANGUAGE = "de";

    public static final String V1 = "/api/v1";

    public static final String FRONTEND_SITES = V1 + "/sites";
    public static final String FRONTEND_PAGES = V1 + "/pages";

    public static final String BACKEND_SITES = V1 + "/backend/sites";
    public static final String BACKEND_PAGES = V1 + "/backend/pages";

    public static final String REPOSITORY = V1 + "/repository";

    private CmsApi() {

        // Empty
    }
}
