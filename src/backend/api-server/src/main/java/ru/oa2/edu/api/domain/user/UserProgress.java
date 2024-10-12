package ru.oa2.edu.api.domain.user;

import ru.oa2.edu.api.domain.task.Task;
import ru.oa2.edu.api.domain.theme.Theme;


public class UserProgress {


    private Long id;
    private User user;
    private Theme theme;
    private Task task;
    private Long count;
    private boolean done;

    public UserProgress() {}

    public UserProgress(User user, Theme theme, Task task, Long count, boolean done) {
        this.user = user;
        this.theme = theme;
        this.task = task;
        this.count = count;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
