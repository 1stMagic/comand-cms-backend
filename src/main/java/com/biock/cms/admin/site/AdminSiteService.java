package com.biock.cms.admin.site;

import com.biock.cms.site.Site;
import com.biock.cms.site.SiteRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class AdminSiteService {

    private final SiteRepository siteRepository;

    public AdminSiteService(final SiteRepository siteRepository) {

        this.siteRepository = siteRepository;
    }

    public Optional<Site> getSite(@NotNull final String name) {

        return this.siteRepository.getSite(name, false);
    }
}
