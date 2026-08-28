package com.civicconnect.controller;

import com.civicconnect.model.IssueImage;
import com.civicconnect.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<IssueImage> uploadImage(
            @RequestParam Long issueId,
            @RequestParam("file") MultipartFile file) throws IOException {
        IssueImage uploadedImage = imageUploadService.uploadIssueImage(issueId, file);
        return new ResponseEntity<>(uploadedImage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) throws IOException {
        imageUploadService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
