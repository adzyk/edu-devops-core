package ru.oa2.edu.api.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oa2.edu.api.domain.theme.Theme;
import ru.oa2.edu.api.domain.theme.ThemeRepository;

@Repository
public interface ThemeJpaRepository extends JpaRepository<Theme, Long>, ThemeRepository {
}
