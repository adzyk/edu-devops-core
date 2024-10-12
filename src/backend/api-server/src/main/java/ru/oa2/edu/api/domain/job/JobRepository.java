package ru.oa2.edu.api.domain.job;

import java.util.List;

public interface JobRepository {

    List<Job> findUserStatistic(long userId);
}
