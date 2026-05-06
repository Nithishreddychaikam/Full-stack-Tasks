package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        List<Job> jobs = jobService.findJobsByEmployer(user);
        model.addAttribute("user", user);
        model.addAttribute("jobs", jobs);
        return "recruiter/dashboard";
    }

    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "recruiter/post_job";
    }

    @PostMapping("/post-job")
    public String postJob(@ModelAttribute("job") Job job, Principal principal) {
        User employer = userService.findByEmail(principal.getName());
        jobService.saveJob(job, employer);
        return "redirect:/recruiter/dashboard?success";
    }

    @GetMapping("/job/{id}/applicants")
    public String viewApplicants(@PathVariable String id, Model model) {
        Job job = jobService.findJobById(id);
        List<Application> applications = applicationService.getApplicationsByJob(job);
        model.addAttribute("job", job);
        model.addAttribute("applications", applications);
        return "recruiter/view_applicants";
    }

    @PostMapping("/application/{id}/status")
    public String updateApplicationStatus(@PathVariable String id, 
                                          @RequestParam("status") String status, 
                                          @RequestParam("jobId") String jobId) {
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/recruiter/job/" + jobId + "/applicants?updated";
    }
}
