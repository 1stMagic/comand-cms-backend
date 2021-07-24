package com.biock.cms.admin.page;

import com.biock.cms.CmsApi;
import com.biock.cms.admin.page.dto.AdminPageDTO;
import com.biock.cms.admin.page.dto.CreatePageResultDTO;
import com.biock.cms.admin.page.dto.NavigationDTO;
import com.biock.cms.utils.LanguageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = CmsApi.ADMIN_PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminPageController {

    private final AdminPageService adminPageService;
    private final MessageSourceAccessor messageSourceAccessor;

    public AdminPageController(final AdminPageService adminPageService, final MessageSource messageSource) {

        this.adminPageService = adminPageService;
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @GetMapping("/{site}/top-navigation")
    public List<NavigationDTO> getTopNavigation(@PathVariable final String site) {

        final var language = LanguageUtils.getLanguage();
        return this.adminPageService.getTopNavigationPages(site)
                .stream()
                .map(page -> NavigationDTO.of(page, p -> p.getTopNavigationTitle(language)))
                .collect(toList());
    }

    @GetMapping("/{site}/top-navigation/details")
    public List<AdminPage> getTopNavigationDetails(@PathVariable final String site) {

        return this.adminPageService.getTopNavigationPages(site);
    }

    @GetMapping("/{site}/main-navigation")
    public List<NavigationDTO> getMainNavigation(@PathVariable final String site) {

        final var language = LanguageUtils.getLanguage();
        return this.adminPageService.getMainNavigationPages(site)
                .stream()
                .map(page -> NavigationDTO.of(page, p -> p.getMainNavigationTitle(language)))
                .collect(toList());
    }

    @GetMapping("/{site}/main-navigation/details")
    public List<AdminPage> getMainNavigationDetails(@PathVariable final String site) {

        return this.adminPageService.getMainNavigationPages(site);
    }

    @GetMapping("/{site}/footer-navigation")
    public List<NavigationDTO> getFooterNavigation(@PathVariable final String site) {

        final var language = LanguageUtils.getLanguage();
        return this.adminPageService.getFooterNavigationPages(site)
                .stream()
                .map(page -> NavigationDTO.of(page, p -> p.getFooterNavigationTitle(language)))
                .collect(toList());
    }

    @GetMapping("/{site}/footer-navigation/details")
    public List<AdminPage> getFooterNavigationDetails(@PathVariable final String site) {

        return this.adminPageService.getFooterNavigationPages(site);
    }

    @PostMapping(path = "/{site}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePageResultDTO createPage(
            @PathVariable final String site,
            @Valid @RequestBody final AdminPageDTO page,
            final BindingResult bindingResult) {

        final CreatePageResultDTO result = new CreatePageResultDTO();

        if (bindingResult.hasErrors()) {
            return result.setMessages(
                    bindingResult.getAllErrors()
                            .stream()
                            .map(this.messageSourceAccessor::getMessage)
                            .collect(toList()));
        }

        final var pageId = this.adminPageService.create(site, page, page.getBeforePageId());

        if (pageId.isPresent()) {
            return result.setId(pageId.get()).setSuccess(true);
        }

        return result.addMessage(this.messageSourceAccessor.getMessage("admin.page.create_error"));
    }
}
