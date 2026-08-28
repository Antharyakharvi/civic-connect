package com.civicconnect.service;

import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.User;
import com.civicconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.from:noreply@civicconnect.com}")
    private String fromEmail;

    public void sendIssueCreatedNotification(String email, String issueTitle, Long issueId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Issue Created - " + issueTitle);
        message.setText("Your issue has been created successfully.\n" +
                "Issue ID: " + issueId + "\n" +
                "Title: " + issueTitle + "\n" +
                "You can track the status of your issue in the application.");
        mailSender.send(message);
    }

    public void sendIssueStatusUpdateNotification(Long issueId, String issueTitle, String status, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Issue Status Updated - " + issueTitle);
        message.setText("Your issue status has been updated.\n" +
                "Issue ID: " + issueId + "\n" +
                "Title: " + issueTitle + "\n" +
                "New Status: " + status);
        mailSender.send(message);
    }

    public void sendIssueAssignedNotification(String email, String issueTitle, Long issueId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Issue Assigned - " + issueTitle);
        message.setText("An issue has been assigned to you.\n" +
                "Issue ID: " + issueId + "\n" +
                "Title: " + issueTitle + "\n" +
                "Please take necessary action.");
        mailSender.send(message);
    }

    public void sendCommentNotification(String email, String issueTitle, Long issueId, String commenterName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("New Comment - " + issueTitle);
        message.setText("A new comment has been added to your issue.\n" +
                "Issue ID: " + issueId + "\n" +
                "Title: " + issueTitle + "\n" +
                "Commented by: " + commenterName);
        mailSender.send(message);
    }
}
