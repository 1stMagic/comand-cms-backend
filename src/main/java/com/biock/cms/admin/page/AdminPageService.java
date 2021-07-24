package com.biock.cms.admin.page;

import com.biock.cms.CmsProperty;
import com.biock.cms.admin.page.dto.AdminPageDTO;
import com.biock.cms.shared.Descriptor;
import com.biock.cms.shared.Label;
import com.biock.cms.shared.Modification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.biock.cms.jcr.PropertyUtils.getBooleanProperty;

@Service
public class AdminPageService {

    private final AdminPageRepository adminPageRepository;

    public AdminPageService(final AdminPageRepository adminPageRepository) {

        this.adminPageRepository = adminPageRepository;
    }

    public List<AdminPage> getTopNavigationPages(@NotNull final String siteName) {

        return this.adminPageRepository.getPagesAsFlatList(
                siteName,
                node -> getBooleanProperty(node, CmsProperty.SHOW_IN_TOP_NAVIGATION, false));
    }

    public List<AdminPage> getMainNavigationPages(@NotNull final String siteName) {

       return this.adminPageRepository.getPages(
               siteName,
               node -> getBooleanProperty(node, CmsProperty.SHOW_IN_MAIN_NAVIGATION, false));
    }

    public List<AdminPage> getFooterNavigationPages(@NotNull final String siteName) {

       return this.adminPageRepository.getPagesAsFlatList(
               siteName,
               node -> getBooleanProperty(node, CmsProperty.SHOW_IN_FOOTER_NAVIGATION, false));
    }

    public Optional<String> create(@NotNull final String siteName, @NotNull final AdminPageDTO page, final String beforePageId) {

        final var savedAdminPage = this.adminPageRepository.save(siteName, AdminPage.of(page));
        if (savedAdminPage.isPresent()) {
            if (StringUtils.isNotBlank(beforePageId)) {
                throw new UnsupportedOperationException("Reordering of pages is not yet implemented");
            }
            return Optional.of(savedAdminPage.get().getId());
        }
        return Optional.empty();
    }
}
