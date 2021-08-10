package com.biock.cms.frontend.page;

import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(final PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public Optional<Page> getPageOfSite(final String siteId, final String relativePagePath) {

        return this.pageRepository.findPageBySIteIdAndRelativePagePath(siteId, relativePagePath);
    }
}
