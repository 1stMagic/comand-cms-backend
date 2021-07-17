package com.biock.cms.admin.page;

import com.biock.cms.CmsProperty;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

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

}
