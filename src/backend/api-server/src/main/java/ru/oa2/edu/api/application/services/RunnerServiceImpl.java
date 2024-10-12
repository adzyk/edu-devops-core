package ru.oa2.edu.api.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.oa2.edu.api.application.database.JobJpaRepository;
import ru.oa2.edu.api.application.database.TaskJpaRepository;
import ru.oa2.edu.api.application.database.ThemeJpaRepository;
import ru.oa2.edu.api.application.database.UserJpaRepository;
import ru.oa2.edu.api.domain.dto.DataCase;
import ru.oa2.edu.api.domain.dto.JobResult;
import ru.oa2.edu.api.domain.dto.JobStatus;
import ru.oa2.edu.api.domain.job.Job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static ru.oa2.edu.api.application.utils.MappingTypes.marshalRequestData;

@Log4j2
@Service
@Transactional
public class RunnerServiceImpl implements RunnerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserJpaRepository userRepository;
    private final TaskJpaRepository taskRepository;
    private final JobJpaRepository jobRepository;
    private final ThemeJpaRepository themeRepository;

    public RunnerServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                             UserJpaRepository userRepository,
                             TaskJpaRepository taskRepository,
                             JobJpaRepository jobRepository,
                             ThemeJpaRepository themeRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.jobRepository = jobRepository;
        this.themeRepository = themeRepository;
    }

    @Override
    public long Job(String topicName, ru.oa2.edu.api.domain.dto.Job jobDTO) {
        var mapperObject = new ObjectMapper();
        var user = userRepository.findById(jobDTO.getUserId());
        var task = taskRepository.findById(jobDTO.getTaskId());

        String requestDataString = null;
        try {
            requestDataString = marshalRequestData(jobDTO.getRequestData());
        } catch (Exception e) {
            log.error(e);
        }
        var job = new Job(user, task, JobStatus.RUNNING, requestDataString);
        jobRepository.save(job);

        var uuid = UUID.randomUUID();
        var requestData = jobDTO.getRequestData();
        var data = task.getData();
        DataCase dataCase = null;
        try {
            dataCase = mapperObject.readValue(data, DataCase.class);
        } catch (JsonProcessingException e) {
           //TODO
        }
        requestData.setContextPath(dataCase.getRequestData().getContextPath());
        var jobResult = new ru.oa2.edu.api.domain.dto.Job(
                job.getId(),
                job.getUser().getId(),
                job.getTask().getId(),
                jobDTO.getEngineType(),
                requestData,
                dataCase.getTestCases()
        );
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(jobResult);
        } catch (JsonProcessingException e) {
            // TODO
        }
        kafkaTemplate.send(topicName, uuid.toString(), json);
        // TODO journal message

        return job.getId();
    }

    @Override
    public JobResult Status(long jobId) {
        var res = jobRepository.findById(jobId);
        if (res.isPresent()) {
            var job = res.get();
            if (job.getStatus().equals(JobStatus.RUNNING)) {
                return null;
            }
            return new JobResult(
                    jobId,
                    job.getTask().getId(),
                    job.isResult(),
                    job.getTaskResponse() != null ? Arrays.stream(job.getTaskResponse().split("\n")).toList() : new ArrayList<>(),
                    null);
        }
        return null;
    }

    @KafkaListener(topics = "edu_results", groupId = "edu")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
        ObjectMapper mapper = new ObjectMapper();
        JobResult jobResult = null;
        try {
            jobResult = mapper.readValue(message, JobResult.class);
        } catch (JsonProcessingException e) {
            log.error(e);
            return;
        }
        var res = jobRepository.findById(jobResult.getJobId());
        // TODO
        var job = res.get();
        job.updateResult(jobResult.isStatus(), String.join("\n", jobResult.getLog()));
        jobRepository.save(job);
        // TODO как аккумулировать статистику (в процессе запроса или при обработке)
        // TODO journal message
    }
}
