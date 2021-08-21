package com.biock.cms.backend.page;

import com.biock.cms.CmsMetaData;
import com.biock.cms.backend.page.dto.ClonePageDTO;
import com.biock.cms.backend.page.dto.CreatePageDTO;
import com.biock.cms.backend.page.dto.UpdatePageDTO;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.i18n.Translation;
import com.biock.cms.page.MetaData;
import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.Modification;
import com.biock.cms.site.SiteRepository;
import com.biock.cms.utils.LanguageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

@Service
public class BackendPageService {

    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;

    public BackendPageService(final PageRepository pageRepository, final SiteRepository siteRepository) {

        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
    }

    public Optional<Page> getPage(final String siteId, final String pageId) {

        return this.pageRepository.findPageOfSiteById(siteId, pageId);
    }

    public String createPage(final String siteId, final CreatePageDTO dto) {

        final Translation title = new Translation(dto.getTitle());
        final PageBuilder pageBuilder = Page.builder()
                .name(buildPageName(title.getTranslation(LanguageUtils.getLanguage())))
                .modification(Modification.now("api"))
                .topNavigationTitle(title)
                .mainNavigationTitle(title)
                .footerNavigationTitle(title)
                .showInTopNavigation(dto.isShowInTopNavigation())
                .showInMainNavigation(dto.isShowInMainNavigation())
                .showInFooterNavigation(dto.isShowInFooterNavigation())
                .metaData(MetaData.builder().metaDate(CmsMetaData.TITLE, title).build());

        return this.pageRepository.createPage(siteId, pageBuilder, dto.getParentId(), dto.getAfterPageId());
    }

    public String updatePage(final String siteId, final String pageId, final UpdatePageDTO dto) {

        final Optional<Page> page = this.pageRepository.findPageOfSiteById(siteId, pageId);
        if (page.isEmpty()) {
            throw new NodeNotFoundException("Page " + pageId);
        }
        if (!dto.isEmpty()) {
            final PageBuilder pageBuilder = Page.builder().apply(page.get());
            if (dto.getTopNavigationTitle() != null && !dto.getTopNavigationTitle().isEmpty()) {
                pageBuilder.topNavigationTitle(Translation.builder().translations(dto.getTopNavigationTitle()).build());
            }
            if (dto.getMainNavigationTitle() != null && !dto.getMainNavigationTitle().isEmpty()) {
                pageBuilder.mainNavigationTitle(Translation.builder().translations(dto.getMainNavigationTitle()).build());
            }
            if (dto.getFooterNavigationTitle() != null && !dto.getFooterNavigationTitle().isEmpty()) {
                pageBuilder.footerNavigationTitle(Translation.builder().translations(dto.getFooterNavigationTitle()).build());
            }
            if (dto.isShowInTopNavigation() != null) {
                pageBuilder.showInTopNavigation(dto.isShowInTopNavigation());
            }
            if (dto.isShowInMainNavigation() != null) {
                pageBuilder.showInMainNavigation(dto.isShowInMainNavigation());
            }
            if (dto.isShowInFooterNavigation() != null) {
                pageBuilder.showInFooterNavigation(dto.isShowInFooterNavigation());
            }
            if (dto.isActive() != null) {
                pageBuilder.active(dto.isActive());
            }
            pageBuilder.modification(Modification.builder()
                    .apply(page.get().getModification())
                    .lastModified(OffsetDateTime.now())
                    .lastModifiedBy("api")
                    .build());
            return this.pageRepository.updatePage(siteId, pageBuilder);
        }
        return page.get().getId();
    }

    public String clonePage(final String siteId, final String pageId, final ClonePageDTO dto) {

        final Page clonedPage = this.pageRepository.clonePage(siteId, pageId, dto.getAfterPageId());
        final PageBuilder pageBuilder = Page.builder()
                .apply(clonedPage)
                .showInTopNavigation(dto.isShowInTopNavigation())
                .showInMainNavigation(dto.isShowInMainNavigation())
                .showInFooterNavigation(dto.isShowInFooterNavigation())
                .modification(Modification.now("api"));
        final UnaryOperator<String> titleModifier = title -> String.format("Copy of %s", title);

        if (clonedPage.hasTopNavigationTitle()) {
            pageBuilder.topNavigationTitle(clonedPage.getTopNavigationTitle().modify(titleModifier));
        }
        if (clonedPage.hasMainNavigationTitle()) {
            pageBuilder.mainNavigationTitle(clonedPage.getMainNavigationTitle().modify(titleModifier));
        }
        if (clonedPage.hasFooterNavigationTitle()) {
            pageBuilder.footerNavigationTitle(clonedPage.getFooterNavigationTitle().modify(titleModifier));
        }
        if (clonedPage.hasMetaData()) {
            pageBuilder.metaData(clonedPage.getMetaData().mofify(CmsMetaData.TITLE, titleModifier));
        }
        return this.pageRepository.updatePage(siteId, pageBuilder);
    }

    public String deletePage(final String siteId, final String pageId) {

        return this.pageRepository.deletePage(siteId, pageId);
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        return this.siteRepository.getDefaultLanguageOfSite(siteId);
    }

    private String buildPageName(final String title) {

        final String name = StringUtils.stripAccents(StringUtils.defaultString(title))
                .replace(' ', '_')
                .replace("ß", "ss")
                .replaceAll("[^\\w.-]", "");
        return StringUtils.defaultIfBlank(name, UUID.randomUUID().toString());
    }
}
