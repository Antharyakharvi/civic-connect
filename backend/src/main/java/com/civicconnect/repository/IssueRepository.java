package com.civicconnect.repository;

import com.civicconnect.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Page<Issue> findByStatus(String status, Pageable pageable);
    Page<Issue> findByCategory(String category, Pageable pageable);
    Page<Issue> findByPriority(String priority, Pageable pageable);
    Page<Issue> findByReportedByEmail(String email, Pageable pageable);
    Page<Issue> findByAssignedToEmail(String email, Pageable pageable);

    @Query("SELECT i FROM Issue i WHERE i.createdAt BETWEEN ?1 AND ?2")
    List<Issue> findIssuesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(i) FROM Issue i WHERE i.status = ?1")
    Long countByStatus(String status);

    @Query("SELECT COUNT(i) FROM Issue i WHERE i.category = ?1")
    Long countByCategory(String category);
}
