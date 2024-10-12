package ru.oa2.edu.api.domain.job;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.oa2.edu.api.domain.dto.JobStatus;
import ru.oa2.edu.api.domain.dto.RequestData;
import ru.oa2.edu.api.domain.task.Task;
import ru.oa2.edu.api.domain.user.User;

import java.sql.Timestamp;

import static ru.oa2.edu.api.application.utils.MappingTypes.parseRequestData;


public class Job {


    private long id;
    private User user;
    private Task task;
    private JobStatus status;
    private String taskRequest;

    private String taskResponse;
    private boolean result;
    private Timestamp lastDate;

    public Job() {

    }

    public Job(User user, Task task, JobStatus status, String taskRequest) {
        this.user = user;
        this.task = task;
        this.status = status;
        this.taskRequest = taskRequest;
        this.lastDate = new Timestamp(System.currentTimeMillis());
    }

    public void updateResult(boolean result, String taskResponse) {
        this.result = result;
        this.status = JobStatus.FINISHED;
        this.taskResponse = taskResponse;
        this.lastDate = new Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getTaskRequest() {
        return taskRequest;
    }

    public void setTaskRequest(String taskRequest) {
        this.taskRequest = taskRequest;
    }

    public String getTaskResponse() {
        return taskResponse;
    }

    public void setTaskResponse(String taskResponse) {
        this.taskResponse = taskResponse;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Timestamp getLastDate() {
        return lastDate;
    }

    public void setLastDate(Timestamp lastDate) {
        this.lastDate = lastDate;
    }
}
