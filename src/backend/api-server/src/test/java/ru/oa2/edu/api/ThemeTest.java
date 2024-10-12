package ru.oa2.edu.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.oa2.edu.api.application.services.RepositoryService;
import ru.oa2.edu.api.domain.theme.ThemeDTO;

import java.util.Collection;

@SpringBootTest
public class ThemeTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void getListTest() {
        Collection<ThemeDTO> listThemes = repositoryService.listThemes();
        // TODO данные для теста
        System.out.println(listThemes);
    }
}