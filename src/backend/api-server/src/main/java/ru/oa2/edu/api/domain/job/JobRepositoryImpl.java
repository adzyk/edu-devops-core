package ru.oa2.edu.api.domain.job;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobRepositoryImpl implements JobRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Job> findUserStatistic(long userId) {
        var query = entityManager.createQuery("from Job where user.Id = :userId", Job.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
