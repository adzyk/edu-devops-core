package ru.oa2.edu.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Run {
    private long id;
    private long userId;
    private long taskId;
    private JobStatus status;
    private Object taskRequest;
    private String taskResponse;
    private boolean result;
    private Timestamp lastDate;
}
