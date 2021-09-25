package com.biock.cms.backend.site;

import com.biock.cms.backend.site.dto.CreateUserGroupDTO;
import com.biock.cms.i18n.Translation;
import com.biock.cms.site.SiteRepository;
import com.biock.cms.user.User;
import com.biock.cms.user.UserGroup;
import com.biock.cms.user.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackendSiteService {

    private final SiteRepository siteRepository;
    private final UserRepository userRepository;

    public BackendSiteService(final SiteRepository siteRepository, final UserRepository userRepository) {

        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<Navigation>> getNavigation(final String siteId) {

        return this.siteRepository.getNavigation(siteId);
    }

    public Optional<List<Navigation>> getTopNavigation(final String siteId) {

        return this.siteRepository.getTopNavigation(siteId);
    }

    public Optional<List<Navigation>> getMainNavigation(final String siteId) {

        return this.siteRepository.getMainNavigation(siteId);
    }

    public Optional<List<Navigation>> getFooterNavigation(final String siteId) {

        return this.siteRepository.getFooterNavigation(siteId);
    }

    public String getDefaultLanguageOfSite(final String siteId) {

        return this.siteRepository.getDefaultLanguageOfSite(siteId);
    }

    public List<User> getUsers(final String siteId) {

        return this.userRepository.getUsers(siteId);
    }

    public List<UserGroup> getUserGroups(final String siteId) {

        return this.userRepository.getUserGroups(siteId);
    }

    public UserGroup createUserGroup(final String siteId, final CreateUserGroupDTO dto) {

        return this.userRepository.createUserGroup(
                siteId,
                UserGroup.builder()
                        .name(new Translation(dto.getName()))
                        .active(true));
    }

    public Resource exportSite(final String siteId) {

        return this.siteRepository.exportSite(siteId);
    }
}
