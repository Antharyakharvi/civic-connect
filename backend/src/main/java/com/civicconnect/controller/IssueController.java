package com.civicconnect.controller;

import com.civicconnect.dto.IssueDTO;
import com.civicconnect.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO, Authentication authentication) {
        String userEmail = authentication.getName();
        IssueDTO createdIssue = issueService.createIssue(issueDTO, userEmail);
        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable Long id) {
        IssueDTO issue = issueService.getIssueById(id);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<IssueDTO>> getAllIssues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssueDTO> issues = issueService.getAllIssues(pageable);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<IssueDTO>> getIssuesByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssueDTO> issues = issueService.getIssuesByStatus(status, pageable);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<IssueDTO>> getIssuesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssueDTO> issues = issueService.getIssuesByCategory(category, pageable);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Page<IssueDTO>> getIssuesByPriority(
            @PathVariable String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssueDTO> issues = issueService.getIssuesByPriority(priority, pageable);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable Long id, @RequestBody IssueDTO issueDTO) {
        IssueDTO updatedIssue = issueService.updateIssue(id, issueDTO);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<IssueDTO> assignIssue(@PathVariable Long id, @RequestParam String email) {
        IssueDTO assignedIssue = issueService.assignIssue(id, email);
        return new ResponseEntity<>(assignedIssue, HttpStatus.OK);
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<IssueDTO> resolveIssue(@PathVariable Long id) {
        IssueDTO resolvedIssue = issueService.resolveIssue(id);
        return new ResponseEntity<>(resolvedIssue, HttpStatus.OK);
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<IssueDTO> upvoteIssue(@PathVariable Long id) {
        IssueDTO upvotedIssue = issueService.upvoteIssue(id);
        return new ResponseEntity<>(upvotedIssue, HttpStatus.OK);
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<IssueDTO> downvoteIssue(@PathVariable Long id) {
        IssueDTO downvotedIssue = issueService.downvoteIssue(id);
        return new ResponseEntity<>(downvotedIssue, HttpStatus.OK);
    }
}
