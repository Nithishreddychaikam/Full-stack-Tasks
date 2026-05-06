package com.jobportal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    private String id;

    private String message;

    private LocalDateTime createdAt;

    @DBRef
    private User user;

    private boolean isRead = false;
}
