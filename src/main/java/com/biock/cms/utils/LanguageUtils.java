package com.biock.cms.utils;

import com.biock.cms.CmsApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public final class LanguageUtils {

    private LanguageUtils() {

        // Empty
    }

    public static String getLanguage() {

        return StringUtils.defaultIfBlank(LocaleContextHolder.getLocale().getLanguage(), CmsApi.DEFAULT_LANGUAGE);
    }
}
