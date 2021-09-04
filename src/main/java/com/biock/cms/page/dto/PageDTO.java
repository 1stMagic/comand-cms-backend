package com.biock.cms.page.dto;

import com.biock.cms.i18n.dto.TranslationDTO;
import com.biock.cms.page.Page;
import com.biock.cms.shared.dto.ModificationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PageDTO {

    private String id;
    private String name;
    private String description;
    private ModificationDTO modification;
    private boolean active;
    @JsonIgnore
    private TranslationDTO topNavigationTitle;
    @JsonProperty("navigationTitle")
    private TranslationDTO mainNavigationTitle;
    @JsonIgnore
    private TranslationDTO footerNavigationTitle;
    private boolean showInTopNavigation;
    private boolean showInMainNavigation;
    private boolean showInFooterNavigation;
    private String iconClass;
    private boolean navigationEntry;
    private boolean media;
    private boolean external;
    private String href;
    private String target;
    @JsonUnwrapped
    private MetaDataDTO metaData;

    public static PageDTO of(final Page entity) {

        if (entity == null) {
            return new PageDTO();
        }
        return new PageDTO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setModification(ModificationDTO.of(entity.getModification()))
                .setActive(entity.isActive())
                .setTopNavigationTitle(TranslationDTO.of(entity.getTopNavigationTitle()))
                .setMainNavigationTitle(TranslationDTO.of(entity.getMainNavigationTitle()))
                .setFooterNavigationTitle(TranslationDTO.of(entity.getFooterNavigationTitle()))
                .setShowInTopNavigation(entity.isShowInTopNavigation())
                .setShowInMainNavigation(entity.isShowInMainNavigation())
                .setShowInFooterNavigation(entity.isShowInFooterNavigation())
                .setIconClass(entity.getIconClass())
                .setNavigationEntry(entity.isNavigationEntry())
                .setMedia(entity.isMedia())
                .setExternal(entity.isExternal())
                .setHref(entity.getHref())
                .setTarget(entity.getTarget())
                .setMetaData(MetaDataDTO.of(entity.getMetaData()));
    }

    public String getId() {

        return this.id;
    }

    public PageDTO setId(final String id) {

        this.id = id;
        return this;
    }

    public String getName() {

        return this.name;
    }

    public PageDTO setName(final String name) {

        this.name = name;
        return this;
    }

    public String getDescription() {

        return this.description;
    }

    public PageDTO setDescription(final String description) {

        this.description = description;
        return this;
    }

    public ModificationDTO getModification() {

        return this.modification;
    }

    public PageDTO setModification(final ModificationDTO modification) {

        this.modification = modification;
        return this;
    }

    public boolean isActive() {

        return this.active;
    }

    public PageDTO setActive(final boolean active) {

        this.active = active;
        return this;
    }

    public TranslationDTO getTopNavigationTitle() {

        return this.topNavigationTitle;
    }

    public PageDTO setTopNavigationTitle(final TranslationDTO topNavigationTitle) {

        this.topNavigationTitle = topNavigationTitle;
        return this;
    }

    public TranslationDTO getMainNavigationTitle() {

        return this.mainNavigationTitle;
    }

    public PageDTO setMainNavigationTitle(final TranslationDTO mainNavigationTitle) {

        this.mainNavigationTitle = mainNavigationTitle;
        return this;
    }

    public TranslationDTO getFooterNavigationTitle() {

        return this.footerNavigationTitle;
    }

    public PageDTO setFooterNavigationTitle(final TranslationDTO footerNavigationTitle) {

        this.footerNavigationTitle = footerNavigationTitle;
        return this;
    }

    public boolean isShowInTopNavigation() {

        return this.showInTopNavigation;
    }

    public PageDTO setShowInTopNavigation(final boolean showInTopNavigation) {

        this.showInTopNavigation = showInTopNavigation;
        return this;
    }

    public boolean isShowInMainNavigation() {

        return this.showInMainNavigation;
    }

    public PageDTO setShowInMainNavigation(final boolean showInMainNavigation) {

        this.showInMainNavigation = showInMainNavigation;
        return this;
    }

    public boolean isShowInFooterNavigation() {

        return this.showInFooterNavigation;
    }

    public PageDTO setShowInFooterNavigation(final boolean showInFooterNavigation) {

        this.showInFooterNavigation = showInFooterNavigation;
        return this;
    }

    public String getIconClass() {

        return this.iconClass;
    }

    public PageDTO setIconClass(final String iconClass) {

        this.iconClass = iconClass;
        return this;
    }

    public boolean isNavigationEntry() {

        return this.navigationEntry;
    }

    public PageDTO setNavigationEntry(final boolean navigationEntry) {

        this.navigationEntry = navigationEntry;
        return this;
    }

    public boolean isMedia() {

        return this.media;
    }

    public PageDTO setMedia(final boolean media) {

        this.media = media;
        return this;
    }

    public boolean isExternal() {

        return this.external;
    }

    public PageDTO setExternal(final boolean external) {

        this.external = external;
        return this;
    }

    public String getHref() {

        return this.href;
    }

    public PageDTO setHref(final String href) {

        this.href = href;
        return this;
    }

    public String getTarget() {

        return this.target;
    }

    public PageDTO setTarget(final String target) {

        this.target = target;
        return this;
    }

    public MetaDataDTO getMetaData() {

        return this.metaData;
    }

    public PageDTO setMetaData(final MetaDataDTO metaData) {

        this.metaData = metaData;
        return this;
    }
}
