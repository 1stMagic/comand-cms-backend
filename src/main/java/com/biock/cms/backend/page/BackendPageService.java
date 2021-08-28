package com.biock.cms.backend.page;

import com.biock.cms.CmsMetaData;
import com.biock.cms.backend.page.dto.ClonePageDTO;
import com.biock.cms.backend.page.dto.CreatePageDTO;
import com.biock.cms.backend.page.dto.UpdatePageDTO;
import com.biock.cms.component.simple.SimpleComponent;
import com.biock.cms.component.simple.SimpleComponentProperty;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.i18n.Translation;
import com.biock.cms.page.MetaData;
import com.biock.cms.page.Page;
import com.biock.cms.page.PageRepository;
import com.biock.cms.page.builder.MetaDataBuilder;
import com.biock.cms.page.builder.PageBuilder;
import com.biock.cms.shared.Modification;
import com.biock.cms.site.SiteRepository;
import com.biock.cms.utils.LanguageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
                .mainNavigationTitle(title)
                .showInTopNavigation(dto.isShowInTopNavigation())
                .showInMainNavigation(dto.isShowInMainNavigation())
                .showInFooterNavigation(dto.isShowInFooterNavigation())
                .navigationEntry(dto.isNavigationEntry())
                .media(dto.isMedia())
                .external(dto.isExternal())
                .href(dto.getHref())
                .metaData(MetaData.builder().metaDate(CmsMetaData.TITLE, title).build());
//                .component(SimpleComponent.builder()
//                        .componentName("CmdWidthLimitationWrapper")
//                        .active(true)
//                        .property(new SimpleComponentProperty("innerWrapper", false))
//                        .component(SimpleComponent.builder()
//                                .componentName("h1")
//                                .active(true)
//                                .property(new SimpleComponentProperty("innerHTML", title))
//                                .build())
//                        .build());

        return this.pageRepository.createPage(siteId, pageBuilder, dto.getParentId(), dto.getAfterPageId());
    }

    public String updatePage(final String siteId, final String pageId, final UpdatePageDTO dto) {

        final Optional<Page> page = this.pageRepository.findPageOfSiteById(siteId, pageId);
        if (page.isEmpty()) {
            throw new NodeNotFoundException("Page " + pageId);
        }
        if (!dto.isEmpty()) {
            final PageBuilder pageBuilder = Page.builder().apply(page.get());
            final BiConsumer<Boolean, Consumer<Boolean>> setIf = (value, setter) -> {
                if (value != null) {
                    setter.accept(value);
                }
            };
            if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
                pageBuilder.mainNavigationTitle(Translation.builder().translations(dto.getTitle()).build());
            }
            if (dto.getMetaData() != null && !dto.getMetaData().isEmpty()) {
                final MetaDataBuilder metaDataBuilder = MetaData.builder();
                dto.getMetaData().forEach((name, translations) -> metaDataBuilder.metaDate(
                        name,
                        Translation.builder().translations(translations).build()));
                pageBuilder.metaData(metaDataBuilder.build());
            }
            if (dto.getHref() != null) {
                pageBuilder.href(dto.getHref());
            }
            setIf.accept(dto.isShowInTopNavigation(), pageBuilder::showInTopNavigation);
            setIf.accept(dto.isShowInMainNavigation(), pageBuilder::showInMainNavigation);
            setIf.accept(dto.isShowInFooterNavigation(), pageBuilder::showInFooterNavigation);
            setIf.accept(dto.isNavigationEntry(), pageBuilder::navigationEntry);
            setIf.accept(dto.isMedia(), pageBuilder::media);
            setIf.accept(dto.isExternal(), pageBuilder::external);
            setIf.accept(dto.isActive(), pageBuilder::active);
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
