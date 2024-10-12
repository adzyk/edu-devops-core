package ru.oa2.edu.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestData {

    public RequestData() {}

    @JsonProperty("main")
    private String main;
    @JsonProperty("context_path")
    private String contextPath;
}
