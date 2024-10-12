package ru.oa2.edu.api.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oa2.edu.api.domain.dto.EngineTypes;
import ru.oa2.edu.api.domain.dto.Job;
import ru.oa2.edu.api.application.services.RunnerService;

@RestController
@RequestMapping("/api")
public class RunController {

    @Autowired
    RunnerService runnerService;

    @PutMapping()
    @RequestMapping("/run")
    public ResponseEntity run(@RequestBody Job job) {
        EngineTypes.DOCKER.toString();
        switch (EngineTypes.fromString(job.getEngineType())) {
            case EngineTypes.DOCKER, EngineTypes.DOCKER_BUILDX: {
                var runId = runnerService.Job("edu_task_docker", job);
                return ResponseEntity.ok().body(runId);
            }
            default: {
                var res = ResponseEntity.badRequest();
                res.body("not support engine");
                return res.build();
            }
        }
    }

    @GetMapping()
    @RequestMapping("/run/status/{job_id}")
    public ResponseEntity getResult(@PathVariable("job_id") long jobId) {
        var result = runnerService.Status(jobId);
        return ResponseEntity.ok().body(result);
    }
}
