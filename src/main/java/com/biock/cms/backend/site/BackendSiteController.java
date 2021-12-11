package com.biock.cms.backend.site;

import com.biock.cms.CmsApi;
import com.biock.cms.backend.site.dto.*;
import com.biock.cms.exception.NodeNotFoundException;
import com.biock.cms.i18n.Messages;
import com.biock.cms.i18n.Translation;
import com.biock.cms.utils.LanguageUtils;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@RestController
@RequestMapping(path = CmsApi.BACKEND_SITES, produces = MediaType.APPLICATION_JSON_VALUE)
public class BackendSiteController {

    private final BackendSiteService backendSiteService;
    private final ResponseBuilder responseBuilder;
    private final Messages messages;

    public BackendSiteController(
            final BackendSiteService backendSiteService,
            final ResponseBuilder responseBuilder,
            final Messages messages) {

        this.backendSiteService = backendSiteService;
        this.responseBuilder = responseBuilder;
        this.messages = messages;
    }

    @GetMapping("/{id}/navigation")
    public ResponseEntity<ResponseDTO<List<NavigationDTO>>> getNavigation(@PathVariable final String id) {

        return this.responseBuilder.buildOptional(
                () -> this.backendSiteService.getNavigation(id),
                navEntries -> navEntries.stream()
                        .map(navEntry -> NavigationDTO.of(
                                navEntry,
                                navigation -> translator(id).apply(
                                        navigation.getMainNavigationTitle(),
                                        navigation.getMetaDataTitle())))
                        .toList(),
                this.messages.supplyMessage("backend.site.not_found"));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getUsers(@PathVariable final String id) {

        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.getUsers(id)),
                users -> users.stream().map(UserDTO::of).toList());
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(
            @PathVariable final String id,
            @Valid @RequestBody final CreateUserDTO user,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.buildOptional(bindingResult);
        }

        return this.responseBuilder.buildOptional(
                () -> this.backendSiteService.createUser(id, user),
                UserDTO::of);
    }

    @PutMapping("/{id}/users/{userId}")
    public ResponseEntity<ResponseDTO<UserModificationResultDTO>> updateUser(
            @PathVariable final String id,
            @PathVariable final String userId,
            @Valid @RequestBody final UpdateUserDTO user,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.buildOptional(bindingResult);
        }

        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.updateUser(id, userId, user)),
                updatedUserId -> new UserModificationResultDTO().setId(updatedUserId));
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<ResponseDTO<UserModificationResultDTO>> deleteUser(
            @PathVariable final String id,
            @PathVariable final String userId) {

        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.deleteUser(id, userId)),
                deletedUserId -> new UserModificationResultDTO().setId(deletedUserId));
    }

    @GetMapping("/{id}/user-groups")
    public ResponseEntity<ResponseDTO<List<UserGroupDTO>>> getUserGroups(@PathVariable final String id) {

        final String language = LanguageUtils.getLanguage();
        final String fallbackLanguage = this.backendSiteService.getDefaultLanguageOfSite(id);
        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.getUserGroups(id)),
                userGroups -> userGroups.stream()
                        .map(userGroup -> UserGroupDTO.of(userGroup, language, fallbackLanguage))
                        .toList());
    }

    @PostMapping("/{id}/user-groups")
    public ResponseEntity<ResponseDTO<UserGroupDTO>> createUserGroup(
            @PathVariable final String id,
            @Valid @RequestBody final CreateUserGroupDTO userGroup,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.buildOptional(bindingResult);
        }

        final String language = LanguageUtils.getLanguage();
        final String fallbackLanguage = this.backendSiteService.getDefaultLanguageOfSite(id);
        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.createUserGroup(id, userGroup)),
                group -> UserGroupDTO.of(group, language, fallbackLanguage));
    }

    @PutMapping("/{id}/user-groups/{userGroupId}")
    public ResponseEntity<ResponseDTO<UserGroupModificationResultDTO>> updateUserGroup(
            @PathVariable final String id,
            @PathVariable final String userGroupId,
            @Valid @RequestBody final UpdateUserGroupDTO userGroup,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.buildOptional(bindingResult);
        }

        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.updateUserGroup(id, userGroupId, userGroup)),
                updatedUserGroupId -> new UserGroupModificationResultDTO().setId(updatedUserGroupId));
    }

    @DeleteMapping("/{id}/user-groups/{userGroupId}")
    public ResponseEntity<ResponseDTO<UserGroupModificationResultDTO>> deleteUserGroup(
            @PathVariable final String id,
            @PathVariable final String userGroupId) {

        return this.responseBuilder.buildOptional(
                () -> Optional.of(this.backendSiteService.deleteUserGroup(id, userGroupId)),
                deletedUserGroupId -> new UserGroupModificationResultDTO().setId(deletedUserGroupId));
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<Resource> exportSite(@PathVariable final String id) {

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"cms_site_" + id + ".xml\"")
                    .body(this.backendSiteService.exportSite(id));
        } catch (final NodeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private BiFunction<Translation, Translation, String> translator(final String siteId) {

        final String language = LanguageUtils.getLanguage();
        final String fallbackLanguage = this.backendSiteService.getDefaultLanguageOfSite(siteId);
        return (t1, t2) -> StringUtils.defaultIfBlank(
                t1.getTranslation(language, fallbackLanguage),
                t2.getTranslation(language, fallbackLanguage));
    }
}
