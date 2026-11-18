
package com.assignment.scraper.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.assignment.scraper.entity.PageKeyword;
import com.assignment.scraper.entity.ScrapeJob;
import com.assignment.scraper.entity.ScrapedPage;
import com.assignment.scraper.repository.PageKeywordRepository;
import com.assignment.scraper.repository.ScrapeJobRepository;
import com.assignment.scraper.repository.ScrapedPageRepository;

@Service
public class ScrapingService {

    private final WebScraperService webScraperService;
    private final KeywordExtractionService keywordExtractionService;

    private final ScrapeJobRepository scrapeJobRepository;
    private final ScrapedPageRepository scrapedPageRepository;
    private final PageKeywordRepository pageKeywordRepository;

    private final TrieService trieService;

    public ScrapingService(
            WebScraperService webScraperService,
            KeywordExtractionService keywordExtractionService,
            ScrapeJobRepository scrapeJobRepository,
            ScrapedPageRepository scrapedPageRepository,
            PageKeywordRepository pageKeywordRepository,
            TrieService trieService) {

        this.webScraperService = webScraperService;
        this.keywordExtractionService = keywordExtractionService;
        this.scrapeJobRepository = scrapeJobRepository;
        this.scrapedPageRepository = scrapedPageRepository;
        this.pageKeywordRepository = pageKeywordRepository;
        this.trieService = trieService;
    }

    // Scrape a URL, extract keywords, and save everything
    public List<String> scrapeAndSave(
            Long jobId,
            String url,
            List<String> requestedKeywords) throws IOException {

        // Step 1: Scrape webpage
        WebScraperService.ScrapedContent scrapedContent =
                webScraperService.scrape(url);

        // Step 2: Extract matching keywords
        List<String> foundKeywords =
                keywordExtractionService.extractKeywords(
                        scrapedContent.content(),
                        requestedKeywords);

        // Remove duplicate keywords
        Set<String> uniqueKeywords = new LinkedHashSet<>(foundKeywords);

        // Step 3: Find the corresponding job
        ScrapeJob job = scrapeJobRepository.findById(jobId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Scrape job not found"));

        // Step 4: Save scraped webpage
        ScrapedPage page = new ScrapedPage();

        page.setUrl(scrapedContent.url());
        page.setTitle(scrapedContent.title());
        page.setContent(scrapedContent.content());
        page.setScrapedAt(LocalDateTime.now());
        page.setScrapeJob(job);

        ScrapedPage savedPage = scrapedPageRepository.save(page);

        // Step 5: Save extracted keywords
        List<PageKeyword> keywordRecords = new ArrayList<>();

        for (String keyword : uniqueKeywords) {

            PageKeyword pageKeyword = new PageKeyword();

            pageKeyword.setKeyword(keyword);
            pageKeyword.setScrapedPage(savedPage);

            keywordRecords.add(pageKeyword);

            // Step 6: Add keyword to Trie
            trieService.insertKeyword(keyword);
        }

        pageKeywordRepository.saveAll(keywordRecords);

        return new ArrayList<>(uniqueKeywords);
    }
}
