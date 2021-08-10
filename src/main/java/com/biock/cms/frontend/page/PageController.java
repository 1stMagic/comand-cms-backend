package com.biock.cms.frontend.page;

import com.biock.cms.CmsApi;
import com.biock.cms.frontend.page.dto.PageDTO;
import com.biock.cms.frontend.site.SiteService;
import com.biock.cms.i18n.Messages;
import com.biock.cms.utils.LanguageUtils;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = CmsApi.PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class PageController {

    private final PageService pageService;
    private final SiteService siteService;
    private final ResponseBuilder responseBuilder;
    private final Messages messages;

    public PageController(
            final PageService pageService,
            final SiteService siteService,
            final ResponseBuilder responseBuilder,
            final Messages messages) {

        this.pageService = pageService;
        this.siteService = siteService;
        this.responseBuilder = responseBuilder;
        this.messages = messages;
    }

    @GetMapping("/of-site/{siteId}")
    public ResponseEntity<ResponseDTO<PageDTO>> getSitePage(
            @PathVariable final String siteId,
            @RequestParam final String relativePagePath) {

        return this.responseBuilder.build(
                () -> this.pageService.getPageOfSite(siteId, relativePagePath),
                page -> PageDTO.of(page, LanguageUtils.getLanguage(), this.siteService.getDefaultLanguageOfSite(siteId)),
                this.messages.supplyMessage("frontend.page.not_found"));
    }
}
