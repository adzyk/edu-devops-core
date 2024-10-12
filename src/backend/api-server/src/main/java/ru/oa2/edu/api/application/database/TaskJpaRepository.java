package ru.oa2.edu.api.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oa2.edu.api.domain.task.Task;
import ru.oa2.edu.api.domain.task.TaskRepository;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Long>, TaskRepository {
}
