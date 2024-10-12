package ru.oa2.edu.api.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.oa2.edu.api.domain.job.Job;
import ru.oa2.edu.api.domain.job.JobRepository;

@Repository
public interface JobJpaRepository extends JpaRepository<Job, Long>, JobRepository {
}
