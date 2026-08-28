package com.civicconnect.service;

import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.IssueImage;
import com.civicconnect.model.Issue;
import com.civicconnect.repository.IssueImageRepository;
import com.civicconnect.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageUploadService {

    private final IssueImageRepository issueImageRepository;
    private final IssueRepository issueRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public IssueImage uploadIssueImage(Long issueId, MultipartFile file) throws IOException {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found"));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.write(Paths.get(uploadDir, fileName), file.getBytes());

        IssueImage issueImage = new IssueImage();
        issueImage.setIssue(issue);
        issueImage.setFileName(fileName);
        issueImage.setOriginalFileName(file.getOriginalFilename());
        issueImage.setFilePath("/uploads/" + fileName);
        issueImage.setFileSize(file.getSize());
        issueImage.setContentType(file.getContentType());

        return issueImageRepository.save(issueImage);
    }

    public void deleteImage(Long imageId) throws IOException {
        IssueImage image = issueImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        Path filePath = Paths.get(uploadDir, image.getFileName());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        issueImageRepository.delete(image);
    }
}
