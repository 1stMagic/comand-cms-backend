package com.biock.cms.admin.page;

import com.biock.cms.CmsApi;
import com.biock.cms.admin.page.dto.NavigationDTO;
import com.biock.cms.utils.LanguageUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = CmsApi.ADMIN_PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminPageController {

    private final AdminPageService adminPageService;

    public AdminPageController(final AdminPageService adminPageService) {

        this.adminPageService = adminPageService;
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
}
