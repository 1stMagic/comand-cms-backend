package com.biock.cms.admin.site;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class AdminSiteService {

    private final AdminSiteRepository adminSiteRepository;

    public AdminSiteService(final AdminSiteRepository adminSiteRepository) {

        this.adminSiteRepository = adminSiteRepository;
    }

    public Optional<AdminSite> getSite(@NotNull final String name) {

        return this.adminSiteRepository.getSite(name);
    }
}
