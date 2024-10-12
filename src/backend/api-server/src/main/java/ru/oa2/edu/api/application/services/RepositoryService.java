package ru.oa2.edu.api.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oa2.edu.api.application.database.*;
import ru.oa2.edu.api.application.utils.TaskComparator;
import ru.oa2.edu.api.application.utils.ThemeComparator;
import ru.oa2.edu.api.domain.task.TaskDTO;
import ru.oa2.edu.api.domain.theme.Theme;
import ru.oa2.edu.api.domain.theme.ThemeDTO;
import ru.oa2.edu.api.domain.user.StatJob;
import ru.oa2.edu.api.domain.user.UserDataStatistic;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class RepositoryService {

    private final ThemeJpaRepository themeRepository;
    private final TaskJpaRepository taskRepository;
    private final UserJpaRepository userRepository;
    private final UserProgressJpaRepository userProgressRepository;
    private final JobJpaRepository jobJpaRepository;

    public RepositoryService(ThemeJpaRepository themeRepository,
                             TaskJpaRepository taskRepository,
                             UserJpaRepository userRepository,
                             UserProgressJpaRepository userProgressRepository, JobJpaRepository jobJpaRepository) {
        this.themeRepository = themeRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userProgressRepository = userProgressRepository;
        this.jobJpaRepository = jobJpaRepository;
    }

    public Collection<ThemeDTO> listThemes() {
        var themes = themeRepository.findAll();
        var list = new ArrayList<ThemeDTO>();
        for (var theme : themes) {
            list.add(
                    ThemeDTO.builder()
                            .id(theme.getId())
                            .name(theme.getName())
                            .active(theme.isActive())
                            .created(theme.getCreated())
                            .updated(theme.getUpdated())
                            .tasks(null)
                            .build()
            );
        }
        list.sort(new ThemeComparator());
        return list;
    }

    public ThemeDTO getTheme(Long id) {
        ThemeDTO themeDTO = null;
        var result = themeRepository.findById(id);
        if (result.isPresent()) {
            var theme = result.get();
            Collection<TaskDTO> tasks = new ArrayList<>();
            for (var task : theme.getTasks()) {
                tasks.add(
                       TaskDTO.builder()
                               .id(task.getId())
                               .name(task.getName())
                               .active(task.isActive())
                               .data(task.getData())
                               .created(task.getCreated())
                               .updated(task.getUpdated())
                               .build()
                );
            }
            tasks = tasks.stream().sorted(new TaskComparator()).toList();
            themeDTO = ThemeDTO.builder()
                    .id(theme.getId())
                    .name(theme.getName())
                    .active(theme.isActive())
                    .created(theme.getCreated())
                    .updated(theme.getUpdated())
                    .tasks(tasks)
                    .build();
                }
        return themeDTO;
    }

    public TaskDTO getTask(long id) {
        var task = taskRepository.findById(id);
        return TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .data(task.getData())
                .active(task.isActive())
                .created(task.getCreated())
                .updated(task.getUpdated())
                .build();
    }

    public UserDataStatistic getUserStatistic(long id) {
        var stats = new HashMap<String, Integer>();
        var stats2 = new HashMap<String, Integer>();
        var userData = new UserDataStatistic();
        var result = jobJpaRepository.findUserStatistic(id);

        for (var job : result) {
            var date = job.getLastDate().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            var isDone = job.isResult() ? 1 : 0;
            if (stats.containsKey(date)) {
                stats.put(date, stats.get(date) + 1);
                stats2.put(date, stats2.get(date) + isDone);
            } else {
                stats.put(date, 1);
                stats2.put(date, isDone);
            }
        }
        for (var stat : stats.entrySet()) {
            userData.addLabel(stat.getKey());
            userData.addTotal(stat.getValue());
        }
        for (var stat : stats2.entrySet()) {
            userData.addFinishedCount(stat.getValue());
        }
        return userData;
    }

    public void createTheme(ThemeDTO themeDTO) {
        var theme = new Theme(themeDTO.getName(), themeDTO.isActive());
        themeRepository.save(theme);
    }

    public ThemeDTO updateTheme(Long id, ThemeDTO themeDTO) {
        // TODO
        return null;
    }
}
