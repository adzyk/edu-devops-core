package ru.oa2.edu.api.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oa2.edu.api.domain.user.UserProgress;
import ru.oa2.edu.api.domain.user.UserProgressRepository;

public interface UserProgressJpaRepository extends JpaRepository<UserProgress, Long>, UserProgressRepository {
}
