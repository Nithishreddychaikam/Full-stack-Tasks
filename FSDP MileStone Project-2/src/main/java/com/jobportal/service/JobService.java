package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public void saveJob(Job job, User employer) {
        job.setEmployer(employer);
        job.setPostedDate(new Date());
        jobRepository.save(job);
    }

    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> findJobsByEmployer(User employer) {
        return jobRepository.findByEmployer(employer);
    }

    public Job findJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
    }
    
    public void deleteJob(String id) {
        Job job = findJobById(id);
        applicationRepository.deleteByJob(job);
        jobRepository.deleteById(id);
    }
    
    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCase(keyword, keyword);
    }
}
