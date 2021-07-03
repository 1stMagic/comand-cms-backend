package com.biock.cms.system.repository;

import com.biock.cms.CmsApi;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = CmsApi.REPOSITORY, produces = MediaType.APPLICATION_JSON_VALUE)
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(final RepositoryService repositoryService) {

        this.repositoryService = repositoryService;
    }

    @GetMapping("/{*path}")
    public List<RepositoryNode> rootNodes(@PathVariable final String path) {

        return this.repositoryService.getNodes(path);
    }
}
