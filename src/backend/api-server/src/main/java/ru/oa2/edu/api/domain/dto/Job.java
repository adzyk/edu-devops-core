package ru.oa2.edu.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Job {

    @JsonProperty("job_id")
    private long jobId;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("task_id")
    private long taskId;

    @JsonProperty("engine_type")
    private String engineType;

    @JsonProperty("request_data")
    private RequestData requestData;

    @JsonProperty("test_cases")
    private List<TestCase> testCases;
}
