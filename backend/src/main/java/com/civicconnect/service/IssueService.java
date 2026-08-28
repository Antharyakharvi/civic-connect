package com.civicconnect.service;

import com.civicconnect.dto.IssueDTO;
import com.civicconnect.dto.UserDTO;
import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.Issue;
import com.civicconnect.model.User;
import com.civicconnect.repository.IssueRepository;
import com.civicconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueDTO createIssue(IssueDTO issueDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Issue issue = new Issue();
        issue.setTitle(issueDTO.getTitle());
        issue.setDescription(issueDTO.getDescription());
        issue.setCategory(issueDTO.getCategory());
        issue.setStatus("OPEN");
        issue.setPriority(issueDTO.getPriority() != null ? issueDTO.getPriority() : "LOW");
        issue.setLatitude(issueDTO.getLatitude());
        issue.setLongitude(issueDTO.getLongitude());
        issue.setLocation(issueDTO.getLocation());
        issue.setReportedBy(user);
        issue.setUpvotes(0);
        issue.setDownvotes(0);

        Issue savedIssue = issueRepository.save(issue);
        return convertToDTO(savedIssue);
    }

    public IssueDTO getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
        return convertToDTO(issue);
    }

    public Page<IssueDTO> getAllIssues(Pageable pageable) {
        return issueRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<IssueDTO> getIssuesByStatus(String status, Pageable pageable) {
        return issueRepository.findByStatus(status, pageable).map(this::convertToDTO);
    }

    public Page<IssueDTO> getIssuesByCategory(String category, Pageable pageable) {
        return issueRepository.findByCategory(category, pageable).map(this::convertToDTO);
    }

    public Page<IssueDTO> getIssuesByPriority(String priority, Pageable pageable) {
        return issueRepository.findByPriority(priority, pageable).map(this::convertToDTO);
    }

    public IssueDTO updateIssue(Long id, IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        if (issueDTO.getTitle() != null) issue.setTitle(issueDTO.getTitle());
        if (issueDTO.getDescription() != null) issue.setDescription(issueDTO.getDescription());
        if (issueDTO.getStatus() != null) issue.setStatus(issueDTO.getStatus());
        if (issueDTO.getPriority() != null) issue.setPriority(issueDTO.getPriority());
        if (issueDTO.getLocation() != null) issue.setLocation(issueDTO.getLocation());

        Issue updatedIssue = issueRepository.save(issue);
        return convertToDTO(updatedIssue);
    }

    public void deleteIssue(Long id) {
        Issue issue = issueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
        issueRepository.delete(issue);
    }

    public IssueDTO assignIssue(Long issueId, String assigneeEmail) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        User assignee = userRepository.findByEmail(assigneeEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        issue.setAssignedTo(assignee);
        Issue updatedIssue = issueRepository.save(issue);
        return convertToDTO(updatedIssue);
    }

    public IssueDTO resolveIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        issue.setStatus("RESOLVED");
        issue.setResolvedAt(LocalDateTime.now());
        Issue updatedIssue = issueRepository.save(issue);
        return convertToDTO(updatedIssue);
    }

    public IssueDTO upvoteIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
        issue.setUpvotes(issue.getUpvotes() + 1);
        Issue updatedIssue = issueRepository.save(issue);
        return convertToDTO(updatedIssue);
    }

    public IssueDTO downvoteIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
        issue.setDownvotes(issue.getDownvotes() + 1);
        Issue updatedIssue = issueRepository.save(issue);
        return convertToDTO(updatedIssue);
    }

    private IssueDTO convertToDTO(Issue issue) {
        return new IssueDTO(
            issue.getId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getCategory(),
            issue.getStatus(),
            issue.getPriority(),
            issue.getLatitude(),
            issue.getLongitude(),
            issue.getLocation(),
            convertUserToDTO(issue.getReportedBy()),
            convertUserToDTO(issue.getAssignedTo()),
            issue.getUpvotes(),
            issue.getDownvotes(),
            issue.getCreatedAt(),
            issue.getUpdatedAt(),
            issue.getResolvedAt()
        );
    }

    private UserDTO convertUserToDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getRole(),
            user.getCity(),
            user.getState()
        );
    }
}
