package com.biock.cms.page;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class PageService {

    private final PageRepository pageRepository;

    public PageService(final PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public Optional<Page> getPage(@NotNull final String language, @NotNull final String siteName, @NotNull final String pagePath, final boolean onlyActive) {

        final Optional<Page> page = this.pageRepository.getPage(siteName, pagePath, onlyActive);
        page.ifPresent(p -> p.buildBreadcrumbs(language, this.pageRepository));
        return page;
    }
}
