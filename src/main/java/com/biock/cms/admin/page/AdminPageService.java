package com.biock.cms.admin.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.admin.page.dto.CreatePageDTO;
import com.biock.cms.admin.page.dto.UpdatePageDTO;
import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;

@Service
public class AdminPageService {

    private final PageRepository pageRepository;

    public AdminPageService(final PageRepository pageRepository) {

        this.pageRepository = pageRepository;
    }

    public List<Page> getTopNavigationPages(@NotNull final String siteName) {

        return this.pageRepository.getPagesAsFlatList(
                siteName,
                node -> getBooleanProperty(node, CmsProperty.SHOW_IN_TOP_NAVIGATION, false));
    }

    public List<Page> getMainNavigationPages(@NotNull final String siteName) {

       return this.pageRepository.getPages(
               siteName,
               node -> getBooleanProperty(node, CmsProperty.SHOW_IN_MAIN_NAVIGATION, false));
    }

    public List<Page> getFooterNavigationPages(@NotNull final String siteName) {

       return this.pageRepository.getPagesAsFlatList(
               siteName,
               node -> getBooleanProperty(node, CmsProperty.SHOW_IN_FOOTER_NAVIGATION, false));
    }

    public Optional<String> create(@NotNull final String siteName, @NotNull final CreatePageDTO page, final String beforePageId) {

        final var savedPage = this.pageRepository.save(siteName, Page.of(page));
        if (savedPage.isPresent()) {
            if (StringUtils.isNotBlank(beforePageId)) {
                throw new UnsupportedOperationException("Reordering of pages is not yet implemented");
            }
            return Optional.of(savedPage.get().getId());
        }
        return Optional.empty();
    }

    public Optional<String> update(final String siteName, final String pageId, final UpdatePageDTO update) {

        final Optional<Page> page = this.pageRepository.getPage(siteName, pageId);
        if (page.isPresent()) {
            final var savedPage = this.pageRepository.save(siteName, page.get().apply(update));
            if (savedPage.isPresent()) {
                return Optional.of(savedPage.get().getId());
            }
        }
        return Optional.empty();
    }

    public Optional<Page> clonePage(final String siteName, final String pageId) {

        return this.pageRepository.clone(siteName, pageId);
    }

    public Optional<String> deletePage(final String siteName, final String pageId) {

        return this.pageRepository.delete(siteName, pageId);
    }

}
