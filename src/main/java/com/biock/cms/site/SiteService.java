package com.biock.cms.site;

import com.biock.cms.page.PageRepository;
import com.biock.cms.site.dto.CreateSiteResultDTO;
import com.biock.cms.site.exception.SiteException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    public SiteService(final SiteRepository siteRepository, final PageRepository pageRepository) {

        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
    }

    public Optional<Site> getSite(@NotNull final String language, @NotNull final String name, final boolean onlyActive) {

        final Optional<Site> site = this.siteRepository.getSite(name, onlyActive);
        site.ifPresent(s -> s.buildNavigation(language, this.pageRepository));
        return site;
    }

    public List<Site> getAllSites(final boolean onlyActive) {

        return this.siteRepository.getAllSites(onlyActive);
    }

    public CreateSiteResultDTO createSite(@NotNull final Site site) {

        if (this.siteRepository.hasSite(site.getDescriptor().getName())) {
            throw new SiteException(MessageFormat.format("Site with name ''{0}'' already exists", site.getDescriptor().getName()));
        }
        final Site savedSite = this.siteRepository.save(site);
        return new CreateSiteResultDTO()
                .setSuccess(true)
                .setSiteId(savedSite.getDescriptor().getName());
    }
}
