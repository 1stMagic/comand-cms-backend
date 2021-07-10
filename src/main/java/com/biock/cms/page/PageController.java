package com.biock.cms.page;

import com.biock.cms.CmsApi;
import com.biock.cms.page.dto.PageDTO;
import com.biock.cms.utils.LanguageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = CmsApi.PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class PageController {

    private final PageService pageService;

    public PageController(final PageService pageService) {

        this.pageService = pageService;
    }

    @GetMapping("/of-site/{siteName}")
    public PageDTO getSitePage(@PathVariable final String siteName, @RequestParam final String relativePagePath) {

        return PageDTO.of(
                LanguageUtils.getLanguage(),
                this.pageService
                    .getPage(LanguageUtils.getLanguage(), siteName, relativePagePath, true)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            MessageFormat.format("Page not found ''{0}'' for site ''{1}''", relativePagePath, siteName))));
    }
}
