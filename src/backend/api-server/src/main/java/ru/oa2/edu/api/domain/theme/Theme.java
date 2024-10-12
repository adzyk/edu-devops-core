package ru.oa2.edu.api.domain.theme;


import ru.oa2.edu.api.domain.task.Task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;



public class Theme {

    private Long id;
    private String name;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Set<Task> tasks = new HashSet<>();

    public Theme() {}

    public Theme(String name, boolean active) {
        this.name = name;
        this.active = active;
        this.created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
