package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JobService jobService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("jobs", jobService.findAllJobs());
        return "admin/dashboard";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/admin/dashboard?userDeleted";
    }

    @PostMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return "redirect:/admin/dashboard?jobDeleted";
    }
}
