package ru.oa2.edu.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JobResult {

    public JobResult() {}

    @JsonProperty("job_id")
    private long jobId;
    @JsonProperty("task_id")
    private long taskId;
    @JsonProperty("status")
    private boolean status;

    @JsonProperty("log")
    private List<String> log;

    @JsonProperty("result")
    private String result;
}
