package ru.oa2.edu.api.domain.task;

import java.util.List;

public interface TaskRepository {

    Task save(Task task);
    Task findById(long id);
    List<Task> findAll();
}
