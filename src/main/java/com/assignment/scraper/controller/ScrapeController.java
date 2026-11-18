
package com.assignment.scraper.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import com.assignment.scraper.dto.ScrapeRequest;
import com.assignment.scraper.dto.ScrapeResponse;
import com.assignment.scraper.entity.ScrapeJob;
import com.assignment.scraper.repository.ScrapeJobRepository;
import com.assignment.scraper.service.ScrapingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/scrape")
public class ScrapeController {

    private final ScrapeJobRepository scrapeJobRepository;
    private final ScrapingService scrapingService;
    private final ObjectMapper objectMapper;

    public ScrapeController(
            ScrapeJobRepository scrapeJobRepository,
            ScrapingService scrapingService,
            ObjectMapper objectMapper) {

        this.scrapeJobRepository = scrapeJobRepository;
        this.scrapingService = scrapingService;
        this.objectMapper = objectMapper;
    }

    @Operation(
        summary = "Create a scraping job",
        description = "Scrapes the given URLs and extracts specified keywords. "
                + "Supports immediate scraping, one-time scheduling, "
                + "and recurring scheduled scraping."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "URLs, keywords, optional schedule and repeat interval",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = {
                @ExampleObject(
                    name = "Immediate Scraping",
                    value = """
                    {
                        "urls": ["https://example.com"],
                        "keywords": ["example", "domain"]
                    }
                    """
                ),
                @ExampleObject(
                    name = "Recurring Scraping",
                    value = """
                    {
                        "urls": ["https://example.com"],
                        "keywords": ["example", "domain"],
                        "schedule": "2026-11-18T15:00:00",
                        "repeatIntervalMinutes": 5
                    }
                    """
                )
            }
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Scraping completed successfully"
        ),
        @ApiResponse(
            responseCode = "202",
            description = "Scraping job scheduled successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Scraping failed"
        )
    })
    @PostMapping
    public ResponseEntity<ScrapeResponse> scrape(
            @Valid @RequestBody ScrapeRequest request)
            throws JsonProcessingException {

        ScrapeJob job = new ScrapeJob();

        job.setCreatedAt(LocalDateTime.now());
        job.setScheduledAt(request.getSchedule());
        job.setRepeatIntervalMinutes(request.getRepeatIntervalMinutes());

        job.setUrlsJson(
                objectMapper.writeValueAsString(request.getUrls()));

        job.setKeywordsJson(
                objectMapper.writeValueAsString(request.getKeywords()));

        if (request.getSchedule() != null
                && request.getSchedule().isAfter(LocalDateTime.now())) {

            job.setStatus("SCHEDULED");

            ScrapeJob savedJob = scrapeJobRepository.save(job);

            ScrapeResponse response = new ScrapeResponse(
                    "success",
                    "Scraping scheduled successfully.",
                    savedJob.getId(),
                    savedJob.getScheduledAt()
            );

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }

        job.setStatus("RUNNING");

        ScrapeJob savedJob = scrapeJobRepository.save(job);

        try {
            for (String url : request.getUrls()) {

                scrapingService.scrapeAndSave(
                        savedJob.getId(),
                        url,
                        request.getKeywords()
                );
            }

            savedJob.setStatus("COMPLETED");
            savedJob.setCompletedAt(LocalDateTime.now());

            scrapeJobRepository.save(savedJob);

            ScrapeResponse response = new ScrapeResponse(
                    "success",
                    "Scraping completed successfully.",
                    savedJob.getId(),
                    null
            );

            return ResponseEntity.ok(response);

        } catch (IOException | RuntimeException exception) {

            savedJob.setStatus("FAILED");
            savedJob.setCompletedAt(LocalDateTime.now());

            scrapeJobRepository.save(savedJob);

            ScrapeResponse response = new ScrapeResponse(
                    "failed",
                    "Scraping failed: " + exception.getMessage(),
                    savedJob.getId(),
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
