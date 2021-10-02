package com.biock.cms.backend.site;

import com.biock.cms.backend.site.dto.CreateUserDTO;
import com.biock.cms.backend.site.dto.CreateUserGroupDTO;
import com.biock.cms.backend.site.dto.UpdateUserDTO;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.i18n.Translation;
import com.biock.cms.shared.ContactData;
import com.biock.cms.shared.builder.ContactDataBuilder;
import com.biock.cms.site.SiteRepository;
import com.biock.cms.user.User;
import com.biock.cms.user.UserGroup;
import com.biock.cms.user.UserRepository;
import com.biock.cms.user.builder.UserBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

    public Optional<User> createUser(final String siteId, final CreateUserDTO dto) {

        return this.userRepository.createUser(
                siteId,
                User.builder()
                        .email(dto.getEmail())
                        .salutation(dto.getSalutation())
                        .title(dto.getTitle())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .contactData(ContactData.builder()
                                .company(dto.getCompany())
                                .street(dto.getStreet())
                                .postalCode(dto.getPostalCode())
                                .city(dto.getCity())
                                .phone(dto.getPhone())
                                .mobile(dto.getMobile())
                                .fax(dto.getFax())
                                .country(dto.getCountry())
                                .build())
                        .groups(dto.getGroups())
                        .active(true)
                        .build());
    }

    public String updateUser(final String siteId, final String userId, final UpdateUserDTO dto) {

        final Optional<User> user = this.userRepository.getUser(siteId, userId);
        if (user.isEmpty()) {
            throw new NodeNotFoundException("User " + userId);
        }
        if (!dto.isEmpty()) {
            final UserBuilder userBuilder = User.builder().apply(user.get());
            final BiConsumer<String, Consumer<String>> setIf = (value, setter) -> {
                if (value != null) {
                    setter.accept(value);
                }
            };
            setIf.accept(dto.getEmail(), userBuilder::email);
            setIf.accept(dto.getSalutation(), userBuilder::salutation);
            setIf.accept(dto.getTitle(), userBuilder::title);
            setIf.accept(dto.getFirstName(), userBuilder::firstName);
            setIf.accept(dto.getLastName(), userBuilder::lastName);
            if (dto.isActive() != null) {
                userBuilder.active(dto.isActive());
            }
            if (dto.getGroups() != null) {
                userBuilder.groups(dto.getGroups());
            }
            if (dto.hasContactData()) {
                final ContactDataBuilder contactDataBuilder = ContactData.builder().apply(user.get().getContactData());
                setIf.accept(dto.getCompany(), contactDataBuilder::company);
                setIf.accept(dto.getStreet(), contactDataBuilder::street);
                setIf.accept(dto.getPostalCode(), contactDataBuilder::postalCode);
                setIf.accept(dto.getCity(), contactDataBuilder::city);
                setIf.accept(dto.getPhone(), contactDataBuilder::phone);
                setIf.accept(dto.getMobile(), contactDataBuilder::mobile);
                setIf.accept(dto.getFax(), contactDataBuilder::fax);
                setIf.accept(dto.getCountry(), contactDataBuilder::country);
                userBuilder.contactData(contactDataBuilder.build());
            }
            return this.userRepository.updateUser(siteId, userBuilder.build());
        }
        return user.get().getId();
    }

    public String deleteUser(final String siteId, final String userId) {

        return this.userRepository.deleteUser(siteId, userId);
    }

    public Resource exportSite(final String siteId) {

        return this.siteRepository.exportSite(siteId);
    }
}
