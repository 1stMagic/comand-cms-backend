package com.biock.cms.page.dto;

import com.biock.cms.component.dto.ComponentDTO;
import com.biock.cms.page.BreadcrumbItem;
import com.biock.cms.page.Page;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PageDTO {

    private String id;
    private String title;
    private Map<String, String> metaData;
    private List<ComponentDTO> components;
    private List<BreadcrumbItem> breadcrumbs;

    public static PageDTO of(@NotNull final String language, @NotNull final Page page) {

        final var dto = new PageDTO();
        dto.id = page.getId();
        dto.title = page.getTitle().getText(language);
        dto.metaData = page.getMetaData().getMetaData();
        dto.components = page.getComponents().stream().map(c -> ComponentDTO.of(language, c)).collect(toList());
        dto.breadcrumbs = page.getBreadcrumbs();
        return dto;
    }

    public String getId() {

        return this.id;
    }

    public String getTitle() {

        return this.title;
    }

    public Map<String, String> getMetaData() {

        return this.metaData;
    }

    public List<ComponentDTO> getComponents() {

        return this.components;
    }

    public List<BreadcrumbItem> getBreadcrumbs() {

        return this.breadcrumbs;
    }
}
