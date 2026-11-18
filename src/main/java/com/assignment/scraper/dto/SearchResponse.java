
package com.assignment.scraper.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search result containing matching keyword and webpage details")
public class SearchResponse {

    @Schema(
        description = "Matched keyword",
        example = "example"
    )
    private String keyword;

    @Schema(
        description = "URL of the webpage where the keyword was found",
        example = "https://example.com"
    )
    private String url;

    @Schema(
        description = "Title of the scraped webpage",
        example = "Example Domain"
    )
    private String title;

    @Schema(
        description = "Date and time when the webpage was scraped",
        example = "2026-11-18T13:58:03"
    )
    private LocalDateTime scrapedAt;

    @Schema(
        description = "Text snippet containing the matched keyword",
        example = "This domain is for use in illustrative examples."
    )
    private String snippet;

    public SearchResponse() {
    }

    public SearchResponse(
            String keyword,
            String url,
            String title,
            LocalDateTime scrapedAt,
            String snippet) {

        this.keyword = keyword;
        this.url = url;
        this.title = title;
        this.scrapedAt = scrapedAt;
        this.snippet = snippet;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getScrapedAt() {
        return scrapedAt;
    }

    public void setScrapedAt(LocalDateTime scrapedAt) {
        this.scrapedAt = scrapedAt;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
