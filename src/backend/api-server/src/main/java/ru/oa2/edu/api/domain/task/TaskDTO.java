package ru.oa2.edu.api.domain.task;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String name;
    private String data;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime updated;
}
