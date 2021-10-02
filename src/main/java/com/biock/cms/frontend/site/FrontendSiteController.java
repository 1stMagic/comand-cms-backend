package com.biock.cms.frontend.site;

import com.biock.cms.CmsApi;
import com.biock.cms.config.CmsConfig;
import com.biock.cms.frontend.site.dto.SiteDTO;
import com.biock.cms.i18n.Messages;
import com.biock.cms.site.Site;
import com.biock.cms.utils.LanguageUtils;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CmsApi.FRONTEND_SITES, produces = MediaType.APPLICATION_JSON_VALUE)
public class FrontendSiteController {

    private final CmsConfig config;
    private final FrontendSiteService frontendSiteService;
    private final ResponseBuilder responseBuilder;
    private final Messages messages;

    public FrontendSiteController(
            final CmsConfig config,
            final FrontendSiteService frontendSiteService,
            final ResponseBuilder responseBuilder,
            final Messages messages) {

        this.config = config;
        this.frontendSiteService = frontendSiteService;
        this.responseBuilder = responseBuilder;
        this.messages = messages;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SiteDTO>> getSite(@PathVariable final String id) {

        return this.responseBuilder.build(
                () -> this.frontendSiteService.getSite(id),
                this::buildSiteDTO,
                this.messages.supplyMessage("frontend.site.not_found"));
    }

    private SiteDTO buildSiteDTO(final Site site) {

        final SiteDTO dto = SiteDTO.of(site, LanguageUtils.getLanguage(), this.frontendSiteService.getDefaultLanguageOfSite(site.getId()));
        if (StringUtils.isBlank(dto.getTimeZone())) {
            dto.setTimeZone(this.config.getTimeZone());
        }
        if (StringUtils.isBlank(dto.getDateFormat())) {
            dto.setDateFormat(this.config.getDateFormat());
        }
        if (StringUtils.isBlank(dto.getTimeFormat())) {
            dto.setTimeFormat(this.config.getTimeFormat());
        }
        return dto;
    }
}
