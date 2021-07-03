package com.biock.cms.page;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(final PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public Optional<Page> getPage(final String siteName, final String pagePath) {

        return this.pageRepository.getPage(siteName, pagePath, true);
    }
}
