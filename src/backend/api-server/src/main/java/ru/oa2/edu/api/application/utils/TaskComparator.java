package ru.oa2.edu.api.application.utils;

import ru.oa2.edu.api.domain.task.TaskDTO;

import java.util.Comparator;

public class TaskComparator implements Comparator<TaskDTO> {
    @Override
    public int compare(TaskDTO t1, TaskDTO t2) {
        return Long.compare(t1.getId(), t2.getId());
    }
}
