package com.assignment.scraper.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scrape_jobs")
public class ScrapeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "LONGTEXT")
    private String urlsJson;

    @Column(columnDefinition = "LONGTEXT")
    private String keywordsJson;
    
    private LocalDateTime createdAt;

    private LocalDateTime scheduledAt;

    private LocalDateTime completedAt;
    
    private Integer repeatIntervalMinutes;

    public ScrapeJob() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public String getUrlsJson() {
        return urlsJson;
    }

    public void setUrlsJson(String urlsJson) {
        this.urlsJson = urlsJson;
    }

    public String getKeywordsJson() {
        return keywordsJson;
    }

    public void setKeywordsJson(String keywordsJson) {
        this.keywordsJson = keywordsJson;
    }
    
    public Integer getRepeatIntervalMinutes() {
        return repeatIntervalMinutes;
    }

    public void setRepeatIntervalMinutes(Integer repeatIntervalMinutes) {
        this.repeatIntervalMinutes = repeatIntervalMinutes;
    }
}
