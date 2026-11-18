
package com.assignment.scraper.service;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.assignment.scraper.dto.JobStatusResponse;
import com.assignment.scraper.entity.PageKeyword;
import com.assignment.scraper.entity.ScrapeJob;
import com.assignment.scraper.entity.ScrapedPage;
import com.assignment.scraper.repository.PageKeywordRepository;
import com.assignment.scraper.repository.ScrapeJobRepository;
import com.assignment.scraper.repository.ScrapedPageRepository;

@Service
public class JobStatusService {

    private final ScrapeJobRepository scrapeJobRepository;
    private final ScrapedPageRepository scrapedPageRepository;
    private final PageKeywordRepository pageKeywordRepository;

    public JobStatusService(
            ScrapeJobRepository scrapeJobRepository,
            ScrapedPageRepository scrapedPageRepository,
            PageKeywordRepository pageKeywordRepository) {

        this.scrapeJobRepository = scrapeJobRepository;
        this.scrapedPageRepository = scrapedPageRepository;
        this.pageKeywordRepository = pageKeywordRepository;
    }

    public JobStatusResponse getJobStatus(Long jobId) {

        // 1. Find the job
        ScrapeJob job = scrapeJobRepository.findById(jobId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Job not found: " + jobId));

        // 2. Find all pages belonging to this job
        List<ScrapedPage> pages =
                scrapedPageRepository.findByScrapeJob_Id(jobId);

        List<String> urls = pages.stream()
                .map(ScrapedPage::getUrl)
                .toList();

        // 3. Collect keywords from all pages
        Set<String> keywords = new LinkedHashSet<>();

        for (ScrapedPage page : pages) {

            List<PageKeyword> records =
                    pageKeywordRepository.findByScrapedPage_Id(page.getId());

            for (PageKeyword record : records) {
                keywords.add(record.getKeyword());
            }
        }

        // 4. Prepare response
        JobStatusResponse response = new JobStatusResponse();

        response.setJobId(job.getId());
        response.setStatus(job.getStatus());
        response.setCreatedAt(job.getCreatedAt());
        response.setScheduledAt(job.getScheduledAt());
        response.setCompletedAt(job.getCompletedAt());
        response.setScrapedUrls(urls);
        response.setKeywordsFound(List.copyOf(keywords));

        return response;
    }
}
