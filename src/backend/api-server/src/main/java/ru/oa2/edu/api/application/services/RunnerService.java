package ru.oa2.edu.api.application.services;

import ru.oa2.edu.api.domain.dto.Job;
import ru.oa2.edu.api.domain.dto.JobResult;

public interface RunnerService {

    long Job(String topicName, Job job);
    JobResult Status(long jobId);
}
