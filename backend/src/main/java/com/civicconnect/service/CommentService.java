package com.civicconnect.service;

import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.Comment;
import com.civicconnect.model.Issue;
import com.civicconnect.model.User;
import com.civicconnect.repository.CommentRepository;
import com.civicconnect.repository.IssueRepository;
import com.civicconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public Comment addComment(Long issueId, String content, String userEmail) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setIssue(issue);
        comment.setAuthor(user);

        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsByIssue(Long issueId, Pageable pageable) {
        return commentRepository.findByIssueId(issueId, pageable);
    }

    public Comment updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }
}
