package com.jobportal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    private String id;

    private String title;

    private String description;

    private String skillsRequired;

    private String salary;

    private Date postedDate;

    @DBRef
    private User employer;

    @DBRef
    @Builder.Default
    private List<Application> applications = new ArrayList<>();
}
