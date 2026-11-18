package com.assignment.scraper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.scraper.entity.ScrapeJob;
import com.assignment.scraper.repository.ScrapeJobRepository;

@SpringBootTest
class ScrapeJobRepositoryTest {

    @Autowired
    private ScrapeJobRepository repository;

    @Test
    void testSaveScrapeJob() {

        ScrapeJob job = new ScrapeJob();

        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());

        ScrapeJob savedJob = repository.save(job);

        assertNotNull(savedJob.getId());

        System.out.println("Saved Job ID: " + savedJob.getId());
    }
}
