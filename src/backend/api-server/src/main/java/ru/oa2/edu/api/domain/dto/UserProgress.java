package ru.oa2.edu.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserProgress {

    public UserProgress() {}

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("theme_id")
    private Long themeId;
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("count")
    private Long count;
    @JsonProperty("done")
    private boolean done;
}
