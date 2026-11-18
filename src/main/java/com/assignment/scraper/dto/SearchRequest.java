
package com.assignment.scraper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to search keywords using Trie prefix matching")
public class SearchRequest {

    @NotBlank(message = "Search prefix is required")
    @Schema(
        description = "Prefix used to find matching keywords",
        example = "ex",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String prefix;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 100, message = "Limit cannot exceed 100")
    @Schema(
        description = "Maximum number of search results to return",
        example = "5",
        minimum = "1",
        maximum = "100"
    )
    private int limit = 5;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
