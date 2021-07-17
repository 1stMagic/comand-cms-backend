package com.biock.cms.page;

import com.biock.cms.shared.Label;
import com.biock.cms.shared.page.PageConfig;

public class DefaultPageConfig extends PageConfig {

    public DefaultPageConfig() {

        super(
                true,
                false,
                false,
                false,
                "",
                "",
                "",
                Label.empty(),
                Label.empty(),
                Label.empty());
    }
}
