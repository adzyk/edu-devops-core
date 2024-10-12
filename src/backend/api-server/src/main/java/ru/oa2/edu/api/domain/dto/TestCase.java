package ru.oa2.edu.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestCase {

    public TestCase() {}

    @JsonProperty("case_id")
    private long caseId;
    @JsonProperty("case_type")
    private String caseType;
    @JsonProperty("context")
    private Object Context;
}
