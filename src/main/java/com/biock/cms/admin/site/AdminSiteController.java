package com.biock.cms.admin.site;

import com.biock.cms.CmsApi;
import com.biock.cms.site.Site;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = CmsApi.ADMIN_SITES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminSiteController {

    private final AdminSiteService adminSiteService;

    public AdminSiteController(final AdminSiteService adminSiteService) {

        this.adminSiteService = adminSiteService;
    }

    @GetMapping("/{name}")
    public Site getSite(@PathVariable final String name) {

        return this.adminSiteService
                    .getSite(name)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            MessageFormat.format("Site not found ''{0}''", name)));
    }
}
