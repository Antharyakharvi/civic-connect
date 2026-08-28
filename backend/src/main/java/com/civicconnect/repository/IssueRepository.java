package com.civicconnect.repository;

import com.civicconnect.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Page<Issue> findByStatus(String status, Pageable pageable);
    Page<Issue> findByCategory(String category, Pageable pageable);
    Page<Issue> findByPriority(String priority, Pageable pageable);
    long countByStatus(String status);
}
