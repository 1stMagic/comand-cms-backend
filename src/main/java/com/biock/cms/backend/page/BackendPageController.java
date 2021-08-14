package com.biock.cms.backend.page;

import com.biock.cms.CmsApi;
import com.biock.cms.backend.page.dto.ClonePageDTO;
import com.biock.cms.backend.page.dto.CreatePageDTO;
import com.biock.cms.backend.page.dto.PageModificationResultDTO;
import com.biock.cms.backend.page.dto.UpdatePageDTO;
import com.biock.cms.web.api.ResponseBuilder;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = CmsApi.BACKEND_PAGES, produces = MediaType.APPLICATION_JSON_VALUE)
public class BackendPageController {

    private final BackendPageService backendPageService;
    private final ResponseBuilder responseBuilder;

    public BackendPageController(
            final BackendPageService backendPageService,
            final ResponseBuilder responseBuilder) {

        this.backendPageService = backendPageService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping(path = "/{site}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<PageModificationResultDTO>> createPage(
            @PathVariable final String site,
            @Valid @RequestBody final CreatePageDTO page,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.build(bindingResult);
        }

        return this.responseBuilder.build(
                () -> Optional.of(this.backendPageService.createPage(site, page)),
                createdPageId -> new PageModificationResultDTO().setId(createdPageId));
    }

    @PutMapping(path = "/{site}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<PageModificationResultDTO>> updatePage(
            @PathVariable final String site,
            @PathVariable final String id,
            @Valid @RequestBody final UpdatePageDTO page,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.build(bindingResult);
        }

        return this.responseBuilder.build(
                () -> Optional.of(this.backendPageService.updatePage(site, id, page)),
                updatedPageId -> new PageModificationResultDTO().setId(updatedPageId));
    }

    @PostMapping(path = "/{site}/clone/{id}")
    public ResponseEntity<ResponseDTO<PageModificationResultDTO>> clonePage(
            @PathVariable final String site,
            @PathVariable final String id,
            @Valid @RequestBody final ClonePageDTO page,
            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.responseBuilder.build(bindingResult);
        }

        return this.responseBuilder.build(
                () -> Optional.of(this.backendPageService.clonePage(site, id, page)),
                clonedPageId -> new PageModificationResultDTO().setId(clonedPageId));
    }

    @DeleteMapping("/{site}/{id}")
    public ResponseEntity<ResponseDTO<PageModificationResultDTO>> deletePage(
            @PathVariable final String site,
            @PathVariable final String id) {

        // TODO: nur das entsprechende "showIn...Navigation" Flag aktualisieren - nur wenn alle Flags auf false dann tatsächlich löschen

        return this.responseBuilder.build(
                () -> Optional.of(this.backendPageService.deletePage(site, id)),
                deletedPageId -> new PageModificationResultDTO().setId(deletedPageId));
    }
}
