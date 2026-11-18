
package com.assignment.scraper.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WebScraperService {

    // Scrape webpage content using Jsoup
    public ScrapedContent scrape(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        String title = document.title();

        String content = document.body() != null
                ? document.body().text()
                : "";

        return new ScrapedContent(url, title, content);
    }

    // Simple result object containing scraped data
    public record ScrapedContent(
            String url,
            String title,
            String content
    ) {
    }
}
