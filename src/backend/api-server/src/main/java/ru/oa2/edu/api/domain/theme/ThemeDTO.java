package ru.oa2.edu.api.domain.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.oa2.edu.api.domain.task.TaskDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class ThemeDTO {

    public ThemeDTO() {}

    private Long id;
    private String name;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Collection<TaskDTO> tasks;
}
