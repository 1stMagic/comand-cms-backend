package com.biock.cms.site;

import com.biock.cms.shared.Label;

import static java.util.Collections.singletonList;

public class DefaultSiteConfig extends SiteConfig {

    public DefaultSiteConfig() {

        super(
                "Default",
                "default",
                "de",
                "index.html",
                singletonList(
                        SupportedLanguage.builder()
                                .language("de")
                                .title(Label.builder().text("de", "Deutsch").build())
                                .tooltip(Label.empty())
                                .build()
                )
        );
    }
}
