package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public void applyForJob(User candidate, Job job) {
        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new RuntimeException("You have already applied for this job.");
        }

        Application application = Application.builder()
                .candidate(candidate)
                .job(job)
                .status("APPLIED")
                .appliedAt(new Date())
                .build();

        Application savedApplication = applicationRepository.save(application);

        if (job.getApplications() == null) {
            job.setApplications(new ArrayList<>());
        }
        job.getApplications().add(savedApplication);
        jobRepository.save(job);
    }

    public List<Application> getApplicationsByCandidate(User candidate) {
        return applicationRepository.findByCandidate(candidate);
    }

    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public void updateApplicationStatus(String applicationId, String status) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID: " + applicationId));
        app.setStatus(status);
        applicationRepository.save(app);
    }
}
