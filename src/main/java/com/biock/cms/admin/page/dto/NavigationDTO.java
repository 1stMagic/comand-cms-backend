package com.biock.cms.admin.page.dto;

import com.biock.cms.admin.page.AdminPage;
import com.biock.cms.shared.Descriptor;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class NavigationDTO {

    private String id;
    private String parentId;
    private String path;
    private Descriptor descriptor;
    private String title;
    private boolean external;
    private boolean active;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<NavigationDTO> children;

    public static NavigationDTO of(
            @NotNull final AdminPage page,
            @NotNull final Function<AdminPage, String> titleSupplier) {

        final var dto = new NavigationDTO();
        dto.id = page.getId();
        dto.parentId = page.getParentId();
        dto.path = page.getPath();
        dto.descriptor = page.getDescriptor();
        dto.title = titleSupplier.apply(page);
        dto.external = page.getConfig().isExternal();
        dto.active = page.isActive();
        dto.children = page.getChildren()
                .stream()
                .map(childPage -> NavigationDTO.of(childPage, titleSupplier))
                .collect(toList());
        return dto;
    }

    public String getId() {

        return this.id;
    }

    public String getParentId() {

        return this.parentId;
    }

    public String getPath() {

        return this.path;
    }

    public Descriptor getDescriptor() {

        return this.descriptor;
    }

    public String getTitle() {

        return this.title;
    }

    public boolean isExternal() {

        return this.external;
    }

    public boolean isActive() {

        return this.active;
    }

    public List<NavigationDTO> getChildren() {

        return this.children;
    }
}
