package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByEmployer(User employer);
    List<Job> findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCase(String title, String skills);
}
