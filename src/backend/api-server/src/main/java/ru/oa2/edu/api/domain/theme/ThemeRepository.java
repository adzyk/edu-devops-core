package ru.oa2.edu.api.domain.theme;

import java.util.List;

public interface ThemeRepository {

    Theme findById(long id);
    List<Theme> findAll();
}
