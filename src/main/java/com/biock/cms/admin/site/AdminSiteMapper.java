package com.biock.cms.admin.site;

import com.biock.cms.CmsProperty;
import com.biock.cms.jcr.exception.RuntimeRepositoryException;
import com.biock.cms.shared.DescriptorMapper;
import com.biock.cms.shared.Mapper;
import com.biock.cms.shared.ModificationMapper;
import com.biock.cms.shared.site.SiteConfigMapper;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;

public class AdminSiteMapper implements Mapper<AdminSite> {

    private final ZoneOffset timeZoneOffset;

    public AdminSiteMapper(@NotNull final ZoneOffset timeZoneOffset) {

        this.timeZoneOffset = timeZoneOffset;
    }

    @Override
    public AdminSite.Builder toEntityBuilder(@NotNull final Node node) {

        return AdminSite.builder()
                .descriptor(new DescriptorMapper().toEntity(node))
                .modification(new ModificationMapper(this.timeZoneOffset).toEntity(node))
                .active(getBooleanProperty(node, CmsProperty.ACTIVE))
                .config(new SiteConfigMapper().toEntity(node));
    }

    @Override
    public void toNode(@NotNull final AdminSite site, @NotNull final Node node) {

        try {
            new DescriptorMapper().toNode(site.getDescriptor(), node);
            new ModificationMapper(this.timeZoneOffset).toNode(site.getModification(), node);
            node.setProperty(CmsProperty.ACTIVE, site.isActive());
            new SiteConfigMapper().toNode(site.getConfig(), node);
        } catch (final RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}
