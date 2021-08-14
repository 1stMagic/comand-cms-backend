package com.biock.cms.frontend.site;

import com.biock.cms.page.PageRepository;
import com.biock.cms.site.Site;
import com.biock.cms.site.SiteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FrontendSiteService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    public FrontendSiteService(final SiteRepository siteRepository, final PageRepository pageRepository) {

        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        return this.siteRepository.getDefaultLanguageOfSite(siteId);
    }

    public Optional<Site> getSite(final String siteId) {

        final Optional<Site> site = this.siteRepository.findSiteById(siteId);
        site.ifPresent(s -> s.buildNavigation(this.pageRepository));
        return site;
    }
}
