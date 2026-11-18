
package com.assignment.scraper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.scraper.dto.JobStatusResponse;
import com.assignment.scraper.service.JobStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/status")
public class JobStatusController {

    private final JobStatusService jobStatusService;

    public JobStatusController(JobStatusService jobStatusService) {
        this.jobStatusService = jobStatusService;
    }

    @Operation(
        summary = "Get scraping job status",
        description = "Retrieves the current status of a scraping job, "
                + "including its scheduled time, completed time, "
                + "scraped URLs and extracted keywords."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Job status retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Scraping job not found"
        )
    })
    @GetMapping("/{jobId}")
    public ResponseEntity<JobStatusResponse> getJobStatus(

            @Parameter(
                description = "Unique ID of the scraping job",
                example = "7",
                required = true
            )
            @PathVariable Long jobId) {

        JobStatusResponse response = jobStatusService.getJobStatus(jobId);

        return ResponseEntity.ok(response);
    }
}
