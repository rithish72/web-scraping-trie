
package com.assignment.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.scraper.dto.SearchResponse;
import com.assignment.scraper.entity.PageKeyword;
import com.assignment.scraper.entity.ScrapedPage;
import com.assignment.scraper.repository.PageKeywordRepository;

@Service
public class SearchService {

    private final TrieService trieService;
    private final PageKeywordRepository pageKeywordRepository;

    public SearchService(TrieService trieService,
            PageKeywordRepository pageKeywordRepository) {

        this.trieService = trieService;
        this.pageKeywordRepository = pageKeywordRepository;
    }

    public List<SearchResponse> search(String prefix, int limit) {

        List<SearchResponse> results = new ArrayList<>();

        // Step 1: Find matching keywords using Trie
        List<String> matchingKeywords =
                trieService.autocomplete(prefix);

        // Step 2: Retrieve pages for each matching keyword
        for (String keyword : matchingKeywords) {

            List<PageKeyword> keywordRecords =
                    pageKeywordRepository.findByKeywordIgnoreCase(keyword);

            for (PageKeyword record : keywordRecords) {

                if (results.size() >= limit) {
                    return results;
                }

                ScrapedPage page = record.getScrapedPage();

                String snippet = createSnippet(
                        page.getContent(), keyword);

                // Step 3: Build search response
                SearchResponse response = new SearchResponse(
                        keyword,
                        page.getUrl(),
                        page.getTitle(),
                        page.getScrapedAt(),
                        snippet
                );

                results.add(response);
            }
        }

        return results;
    }

    private String createSnippet(String content, String keyword) {

        if (content == null || content.isBlank()) {
            return "";
        }

        int index = content.toLowerCase()
                .indexOf(keyword.toLowerCase());

        if (index == -1) {
            return content.substring(0, Math.min(150, content.length()));
        }

        int start = Math.max(0, index - 50);
        int end = Math.min(content.length(), index + keyword.length() + 100);

        return content.substring(start, end);
    }
}
