
package com.assignment.scraper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.scraper.dto.SearchRequest;
import com.assignment.scraper.dto.SearchResponse;
import com.assignment.scraper.service.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(
        summary = "Search keywords using Trie",
        description = "Searches for keywords matching the given prefix "
                + "using the Trie data structure and returns matching "
                + "pages with their URL, title, timestamp and snippet."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Prefix and maximum number of results",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Search Example",
                value = """
                {
                    "prefix": "ex",
                    "limit": 5
                }
                """
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Search results returned successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid search request"
        )
    })
    @PostMapping
    public ResponseEntity<List<SearchResponse>> search(
            @Valid @RequestBody SearchRequest request) {

        List<SearchResponse> results = searchService.search(
                request.getPrefix(),
                request.getLimit()
        );

        return ResponseEntity.ok(results);
    }
}
