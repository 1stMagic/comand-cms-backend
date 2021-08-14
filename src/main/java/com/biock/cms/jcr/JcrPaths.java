package com.biock.cms.jcr;

import com.biock.cms.CmsNode;

public final class JcrPaths {

    private JcrPaths() {

        // Empty
    }

    public static String cms(final String... additionalPathElements) {

        return absolute(buildPathElements(CmsNode.CMS, additionalPathElements));
    }

    public static String config() {

        return absolute(CmsNode.CMS, CmsNode.CONFIG);
    }

    public static String sites(final String... additionalPathElements) {

        return absolute(buildPathElements(relative(CmsNode.CMS, CmsNode.SITES), additionalPathElements));
    }

    public static String absolute(final String... pathElements) {

        final String path = relative(pathElements);
        if (path.length() == 0 || path.charAt(0) != '/') {
            return "/" + path;
        }
        return path;
    }

    public static String relative(final String... pathElements) {

        return String.join("/", pathElements);
    }

    private static String[] buildPathElements(final String pathElement, final String... additionalPathElements) {

        final String[] pathElements = new String[additionalPathElements.length + 1];
        pathElements[0] = pathElement;
        if (additionalPathElements.length > 0) {
            System.arraycopy(additionalPathElements, 0, pathElements, 1, additionalPathElements.length);
        }
        return pathElements;
    }
}
