package com.civicconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String priority;
    private Double latitude;
    private Double longitude;
    private String location;
    private UserDTO reportedBy;
    private UserDTO assignedTo;
    private Integer upvotes;
    private Integer downvotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
}
