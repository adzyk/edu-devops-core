package ru.oa2.edu.api.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.oa2.edu.api.application.services.RepositoryService;
import ru.oa2.edu.api.domain.theme.ThemeDTO;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    private final RepositoryService repositoryService;

    public ThemeController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CURATOR')")
    public ThemeDTO getTheme(@PathVariable("id") Long id) {
        return repositoryService.getTheme(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CURATOR', 'ADMIN')")
    public ResponseEntity createTheme(@RequestBody ThemeDTO themeDTO) {
        repositoryService.createTheme(themeDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('CURATOR', 'ADMIN')")
    public ThemeDTO updateTheme(@PathVariable("id") Long id, @RequestBody ThemeDTO themeDTO) {
        return repositoryService.updateTheme(id, themeDTO);
    }
}
