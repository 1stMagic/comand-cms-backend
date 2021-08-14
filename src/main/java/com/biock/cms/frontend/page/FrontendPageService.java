package com.biock.cms.frontend.page;

import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FrontendPageService {

    private final PageRepository pageRepository;

    public FrontendPageService(final PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public Optional<Page> getPageOfSite(final String siteId, final String relativePagePath) {

        return this.pageRepository.findPageBySiteIdAndRelativePagePath(siteId, relativePagePath);
    }
}
