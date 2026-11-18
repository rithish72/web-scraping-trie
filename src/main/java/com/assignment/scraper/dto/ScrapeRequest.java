
package com.assignment.scraper.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.FutureOrPresent;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create an immediate or scheduled scraping job")
public class ScrapeRequest {

    @NotEmpty(message = "URLs are required")
    @Schema(
        description = "List of website URLs to scrape",
        example = "[\"https://example.com\"]",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> urls;

    @NotEmpty(message = "Keywords are required")
    @Schema(
        description = "Keywords to search for in the scraped website content",
        example = "[\"example\", \"domain\"]",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> keywords;

    @FutureOrPresent(message = "Schedule must be in the present or future")
    @Schema(
        description = "Optional date and time for scheduled scraping. "
                + "Leave null or omit for immediate scraping.",
        example = "2026-11-18T15:00:00"
    )
    private LocalDateTime schedule;

    @Min(value = 1, message = "Repeat interval must be at least 1 minute")
    @Schema(
        description = "Optional recurring interval in minutes. "
                + "Requires a future schedule.",
        example = "5",
        minimum = "1"
    )
    private Integer repeatIntervalMinutes;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public Integer getRepeatIntervalMinutes() {
        return repeatIntervalMinutes;
    }

    public void setRepeatIntervalMinutes(Integer repeatIntervalMinutes) {
        this.repeatIntervalMinutes = repeatIntervalMinutes;
    }
}
