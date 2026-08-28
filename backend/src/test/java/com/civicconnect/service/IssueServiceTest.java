package com.civicconnect.service;

import com.civicconnect.dto.IssueDTO;
import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.Issue;
import com.civicconnect.model.User;
import com.civicconnect.repository.IssueRepository;
import com.civicconnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IssueService issueService;

    private IssueDTO issueDTO;
    private Issue testIssue;
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@test.com");

        issueDTO = new IssueDTO();
        issueDTO.setTitle("Pothole on Main Street");
        issueDTO.setDescription("Large pothole causing traffic issues");
        issueDTO.setCategory("Road");
        issueDTO.setPriority("HIGH");
        issueDTO.setLocation("Main Street");

        testIssue = new Issue();
        testIssue.setId(1L);
        testIssue.setTitle("Pothole on Main Street");
        testIssue.setStatus("OPEN");
        testIssue.setReportedBy(testUser);
    }

    @Test
    public void testCreateIssueSuccess() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));
        when(issueRepository.save(any(Issue.class))).thenReturn(testIssue);

        IssueDTO result = issueService.createIssue(issueDTO, "test@test.com");

        assertNotNull(result);
        assertEquals("Pothole on Main Street", result.getTitle());
        verify(issueRepository, times(1)).save(any(Issue.class));
    }

    @Test
    public void testCreateIssueWithInvalidUser() {
        when(userRepository.findByEmail("invalid@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> issueService.createIssue(issueDTO, "invalid@test.com"));
    }

    @Test
    public void testGetIssueById() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));

        IssueDTO result = issueService.getIssueById(1L);

        assertNotNull(result);
        assertEquals("Pothole on Main Street", result.getTitle());
    }

    @Test
    public void testGetIssueByIdNotFound() {
        when(issueRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> issueService.getIssueById(999L));
    }

    @Test
    public void testUpdateIssue() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(testIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(testIssue);

        IssueDTO updatedDTO = new IssueDTO();
        updatedDTO.setStatus("RESOLVED");

        IssueDTO result = issueService.updateIssue(1L, updatedDTO);

        assertNotNull(result);
        verify(issueRepository, times(1)).save(any(Issue.class));
    }
}
