
package com.assignment.scraper.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after creating a scraping job")
public class ScrapeResponse {

    @Schema(
        description = "Operation result",
        example = "success"
    )
    private String status;

    @Schema(
        description = "Message describing the scraping operation",
        example = "Scraping scheduled successfully."
    )
    private String message;

    @Schema(
        description = "Unique ID assigned to the scraping job",
        example = "7"
    )
    private Long jobId;

    @Schema(
        description = "Scheduled execution date and time",
        example = "2026-11-18T15:00:00"
    )
    private LocalDateTime scheduledAt;

    public ScrapeResponse() {
    }

    public ScrapeResponse(
            String status,
            String message,
            Long jobId,
            LocalDateTime scheduledAt) {

        this.status = status;
        this.message = message;
        this.jobId = jobId;
        this.scheduledAt = scheduledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
