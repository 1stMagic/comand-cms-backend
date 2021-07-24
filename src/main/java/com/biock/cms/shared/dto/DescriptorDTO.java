package com.biock.cms.shared.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class DescriptorDTO {

    @NotBlank
    @Pattern(regexp = "[\\w]+")
    private String name;
    private String title;
    private String description;

    public String getName() {

        return this.name;
    }

    public DescriptorDTO setName(final String name) {

        this.name = name;
        return this;
    }

    public String getTitle() {

        return this.title;
    }

    public DescriptorDTO setTitle(final String title) {

        this.title = title;
        return this;
    }

    public String getDescription() {

        return this.description;
    }

    public DescriptorDTO setDescription(final String description) {

        this.description = description;
        return this;
    }

    @Override
    public String toString() {

        return "DescriptorDTO{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
