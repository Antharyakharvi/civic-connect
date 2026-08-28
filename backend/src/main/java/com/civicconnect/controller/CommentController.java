package com.civicconnect.controller;

import com.civicconnect.model.Comment;
import com.civicconnect.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(
            @RequestParam Long issueId,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String userEmail = authentication.getName();
        String content = request.get("content");
        Comment comment = commentService.addComment(issueId, content, userEmail);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<Page<Comment>> getCommentsByIssue(
            @PathVariable Long issueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentService.getCommentsByIssue(issueId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String content = request.get("content");
        Comment updatedComment = commentService.updateComment(id, content);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
