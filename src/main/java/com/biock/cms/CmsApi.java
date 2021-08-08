package com.biock.cms;

public final class CmsApi {

    public static final String V1 = "/api/v1";

    public static final String SITES = V1 + "/sites";
    public static final String PAGES = V1 + "/pages";

    public static final String REPOSITORY = V1 + "/repository";

    public static final String ADMIN_SITES = V1 + "/admin/sites";
    public static final String ADMIN_PAGES = V1 + "/admin/pages";

    public static final String DEFAULT_LANGUAGE = "de";

    private CmsApi() {

        // Empty
    }
}
