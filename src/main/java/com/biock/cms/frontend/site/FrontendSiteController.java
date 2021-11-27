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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        return this.responseBuilder.buildOptional(
                () -> this.frontendSiteService.getSite(id),
                this::buildSiteDTO,
                this.messages.supplyMessage("frontend.site.not_found"));
    }

    @PostMapping("/{id}/login")
    public ResponseEntity<ResponseDTO<String>> login(@PathVariable final String id, @RequestParam final String username, @RequestParam final String password) {

        final Optional<String> token = this.frontendSiteService.login(id, username, password);
        return token.map(payload -> ResponseEntity.ok(new ResponseDTO<String>().setSuccess(true).setPayload(payload)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>()));
    }

    @GetMapping("/encode")
    public String encode(final String password) {

        return this.frontendSiteService.encode(password);
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
