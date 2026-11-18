
package com.assignment.scraper.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details and execution status of a scraping job")
public class JobStatusResponse {

    @Schema(
        description = "Unique ID of the scraping job",
        example = "7"
    )
    private Long jobId;

    @Schema(
        description = "Current status of the scraping job",
        example = "SCHEDULED",
        allowableValues = {
            "SCHEDULED", "RUNNING", "COMPLETED", "FAILED"
        }
    )
    private String status;

    @Schema(
        description = "Date and time when the job was created",
        example = "2026-11-18T13:56:16"
    )
    private LocalDateTime createdAt;

    @Schema(
        description = "Next scheduled execution time",
        example = "2026-11-18T14:00:00"
    )
    private LocalDateTime scheduledAt;

    @Schema(
        description = "Date and time when the last execution completed",
        example = "2026-11-18T13:59:32"
    )
    private LocalDateTime completedAt;

    @Schema(
        description = "List of URLs scraped by this job",
        example = "[\"https://example.com\"]"
    )
    private List<String> scrapedUrls;

    @Schema(
        description = "Distinct keywords found during scraping",
        example = "[\"example\", \"domain\"]"
    )
    private List<String> keywordsFound;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public List<String> getScrapedUrls() {
        return scrapedUrls;
    }

    public void setScrapedUrls(List<String> scrapedUrls) {
        this.scrapedUrls = scrapedUrls;
    }

    public List<String> getKeywordsFound() {
        return keywordsFound;
    }

    public void setKeywordsFound(List<String> keywordsFound) {
        this.keywordsFound = keywordsFound;
    }
}
