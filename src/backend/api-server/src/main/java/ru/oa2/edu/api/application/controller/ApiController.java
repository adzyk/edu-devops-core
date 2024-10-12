package ru.oa2.edu.api.application.controller;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.oa2.edu.api.domain.task.TaskDTO;
import ru.oa2.edu.api.domain.theme.ThemeDTO;
import ru.oa2.edu.api.application.services.RepositoryService;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final RepositoryService repositoryService;

    public ApiController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping()
    @RequestMapping("/themes")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CURATOR')")
    public Collection<ThemeDTO> getThemes() {
        return repositoryService.listThemes();
    }



    @GetMapping()
    @RequestMapping("/task/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CURATOR')")
    public TaskDTO getTask(@PathVariable("id") long id) {
        return repositoryService.getTask(id);
    }

    @GetMapping("/version")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CURATOR')")
    public String getVersion() {
        return "1.0.0";
    }
}
