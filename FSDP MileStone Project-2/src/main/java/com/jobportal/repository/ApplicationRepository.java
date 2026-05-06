package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByCandidate(User candidate);
    List<Application> findByJob(Job job);
    void deleteByJob(Job job);
    boolean existsByCandidateAndJob(User candidate, Job job);
}
