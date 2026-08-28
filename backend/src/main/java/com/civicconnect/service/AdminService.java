package com.civicconnect.service;

import com.civicconnect.dto.UserDTO;
import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.User;
import com.civicconnect.repository.UserRepository;
import com.civicconnect.repository.IssueRepository;
import com.civicconnect.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    public UserDTO updateUserRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalIssues", issueRepository.count());
        stats.put("totalComments", commentRepository.count());
        stats.put("openIssues", issueRepository.countByStatus("OPEN"));
        stats.put("resolvedIssues", issueRepository.countByStatus("RESOLVED"));
        return stats;
    }

    private UserDTO convertToDTO(User user) {
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
