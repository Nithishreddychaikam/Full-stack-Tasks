package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import com.jobportal.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final JobService jobService;
    private final ApplicationService applicationService;
    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("applications", applicationService.getApplicationsByCandidate(user));
        model.addAttribute("notifications", notificationService.getNotificationsForUser(user));
        return "candidate/dashboard";
    }

    @GetMapping("/jobs")
    public String browseJobs(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Job> jobs;
        if (keyword != null && !keyword.isEmpty()) {
            jobs = jobService.searchJobs(keyword);
        } else {
            jobs = jobService.findAllJobs();
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "candidate/jobs";
    }

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable String jobId, Principal principal, Model model) {
        try {
            User user = userService.findByEmail(principal.getName());
            Job job = jobService.findJobById(jobId);
            applicationService.applyForJob(user, job);
            return "redirect:/candidate/dashboard?applied";
        } catch (Exception e) {
            return "redirect:/candidate/jobs?error=" + e.getMessage();
        }
    }

    @PostMapping("/upload-resume")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadResume(@RequestParam("file") MultipartFile file, Principal principal) {
        Map<String, String> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Please select a file to upload.");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            User user = userService.findByEmail(principal.getName());
            userService.saveResume(file, user);
            notificationService.createNotification(user, "Your resume was uploaded successfully.");
            response.put("status", "success");
            response.put("message", "Resume uploaded successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to upload resume: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/resume")
    public ResponseEntity<byte[]> viewResume(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        if (user == null || user.getResumeData() == null || user.getResumeData().length == 0) {
            return ResponseEntity.notFound().build();
        }

        String contentType = user.getResumeContentType() != null ? user.getResumeContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        String resumeFileName = user.getResumeFileName() != null ? user.getResumeFileName() : "resume";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resumeFileName + "\"")
                .body(user.getResumeData());
    }
}
