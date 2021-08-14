package com.biock.cms.backend.site;

import com.biock.cms.site.SiteRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackendSiteService {

    private final SiteRepository siteRepository;

    public BackendSiteService(final SiteRepository siteRepository) {

        this.siteRepository = siteRepository;
    }

    public Optional<List<Navigation>> getTopNavigation(final String siteId) {

        return this.siteRepository.getTopNavigation(siteId);
    }

    public Optional<List<Navigation>> getMainNavigation(final String siteId) {

        return this.siteRepository.getMainNavigation(siteId);
    }

    public Optional<List<Navigation>> getFooterNavigation(final String siteId) {

        return this.siteRepository.getFooterNavigation(siteId);
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        return this.siteRepository.getDefaultLanguageOfSite(siteId);
    }

    public Resource exportSite(final String siteId) {

        return this.siteRepository.exportSite(siteId);
    }
}
