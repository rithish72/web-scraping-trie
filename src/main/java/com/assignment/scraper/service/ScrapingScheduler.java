
package com.assignment.scraper.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.assignment.scraper.entity.ScrapeJob;
import com.assignment.scraper.repository.ScrapeJobRepository;

@Component
public class ScrapingScheduler {

    private final ScrapeJobRepository scrapeJobRepository;
    private final ScrapingService scrapingService;
    private final ObjectMapper objectMapper;

    public ScrapingScheduler(
            ScrapeJobRepository scrapeJobRepository,
            ScrapingService scrapingService,
            ObjectMapper objectMapper) {

        this.scrapeJobRepository = scrapeJobRepository;
        this.scrapingService = scrapingService;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 30000)
    public void executeScheduledJobs() {

        List<ScrapeJob> jobs =
                scrapeJobRepository.findByStatusAndScheduledAtLessThanEqual(
                        "SCHEDULED",
                        LocalDateTime.now()
                );

        for (ScrapeJob job : jobs) {

            try {
                // Mark job as running
                job.setStatus("RUNNING");
                scrapeJobRepository.save(job);

                // Read URLs and keywords from database
                List<String> urls = objectMapper.readValue(
                        job.getUrlsJson(),
                        new TypeReference<List<String>>() {}
                );

                List<String> keywords = objectMapper.readValue(
                        job.getKeywordsJson(),
                        new TypeReference<List<String>>() {}
                );

                // Execute scraping
                for (String url : urls) {
                    scrapingService.scrapeAndSave(
                            job.getId(),
                            url,
                            keywords
                    );
                }

                job.setCompletedAt(LocalDateTime.now());

                // Check whether this is a recurring job
                if (job.getRepeatIntervalMinutes() != null) {

                    // Schedule the next execution
                    job.setScheduledAt(
                            LocalDateTime.now().plusMinutes(
                                    job.getRepeatIntervalMinutes()
                            )
                    );

                    job.setStatus("SCHEDULED");

                    System.out.println(
                            "Recurring job scheduled again: "
                                    + job.getId()
                    );

                } else {

                    // Complete one-time job
                    job.setStatus("COMPLETED");

                    System.out.println(
                            "Scheduled job completed: "
                                    + job.getId()
                    );
                }

                scrapeJobRepository.save(job);

            } catch (Exception exception) {

                job.setStatus("FAILED");
                job.setCompletedAt(LocalDateTime.now());

                scrapeJobRepository.save(job);

                System.err.println(
                        "Scheduled job failed: " + job.getId()
                );

                exception.printStackTrace();
            }
        }
    }
}
