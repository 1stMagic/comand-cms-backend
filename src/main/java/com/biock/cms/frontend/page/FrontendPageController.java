package com.biock.cms.frontend.page;

import com.biock.cms.CmsApi;
import com.biock.cms.frontend.page.dto.PageDTO;
import com.biock.cms.frontend.site.FrontendSiteService;
import com.biock.cms.i18n.Messages;
import com.biock.cms.utils.LanguageUtils;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = CmsApi.FRONTEND_PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class FrontendPageController {

    private final FrontendPageService frontendPageService;
    private final FrontendSiteService frontendSiteService;
    private final ResponseBuilder responseBuilder;
    private final Messages messages;

    public FrontendPageController(
            final FrontendPageService frontendPageService,
            final FrontendSiteService frontendSiteService,
            final ResponseBuilder responseBuilder,
            final Messages messages) {

        this.frontendPageService = frontendPageService;
        this.frontendSiteService = frontendSiteService;
        this.responseBuilder = responseBuilder;
        this.messages = messages;
    }

    @GetMapping("/of-site/{siteId}")
    public ResponseEntity<ResponseDTO<PageDTO>> getSitePage(
            @PathVariable final String siteId,
            @RequestParam final String relativePagePath) {

        return this.responseBuilder.buildOptional(
                () -> this.frontendPageService.getPageOfSite(siteId, relativePagePath),
                page -> PageDTO.of(page, LanguageUtils.getLanguage(), this.frontendSiteService.getDefaultLanguageOfSite(siteId)),
                this.messages.supplyMessage("frontend.page.not_found"));
    }
}
