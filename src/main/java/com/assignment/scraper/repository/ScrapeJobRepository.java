package com.assignment.scraper.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.scraper.entity.ScrapeJob;

public interface ScrapeJobRepository
        extends JpaRepository<ScrapeJob, Long> {

    List<ScrapeJob> findByStatusAndScheduledAtLessThanEqual(
            String status, LocalDateTime scheduledAt);

}