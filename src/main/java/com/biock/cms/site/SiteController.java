package com.biock.cms.site;

import com.biock.cms.CmsApi;
import com.biock.cms.page.PageService;
import com.biock.cms.site.dto.CreateSiteResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = CmsApi.SITES, produces = MediaType.APPLICATION_JSON_VALUE)
public class SiteController {

    private final SiteService siteService;

    public SiteController(final SiteService siteService, final PageService pageService) {

        this.siteService = siteService;
    }

    @GetMapping("/{name}")
    public Site getSite(@PathVariable final String name) {

        return this.siteService
                .getSite(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MessageFormat.format("Site not found ''{0}''", name)));
    }

    @GetMapping
    public List<Site> getAllSites(@RequestParam(required = false) final boolean onlyActive) {

        return this.siteService.getAllSites(onlyActive);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateSiteResultDTO createSite(@Valid @RequestBody final Site site) {

        return this.siteService.createSite(site);
    }
}
