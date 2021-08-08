package com.biock.cms.frontend.site;

import com.biock.cms.CmsApi;
import com.biock.cms.frontend.site.dto.SiteDTO;
import com.biock.cms.i18n.Messages;
import com.biock.cms.utils.LanguageUtils;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CmsApi.SITES, produces = MediaType.APPLICATION_JSON_VALUE)
public class SiteController {

    private final SiteService siteService;
    private final ResponseBuilder responseBuilder;
    private final Messages messages;

    public SiteController(
            final SiteService siteService,
            final ResponseBuilder responseBuilder,
            final Messages messages) {

        this.siteService = siteService;
        this.responseBuilder = responseBuilder;
        this.messages = messages;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SiteDTO>> getSite(@PathVariable final String id) {

        return this.responseBuilder.build(
                () -> this.siteService.getSite(id),
                site -> SiteDTO.of(site, LanguageUtils.getLanguage(), this.siteService.getDefaultLanguageOfSite(id)),
                this.messages.supplyMessage("frontend.site.not_found"));
    }
}
