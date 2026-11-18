
package com.assignment.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class KeywordExtractionService {

    // Find user-provided keywords in scraped content
    public List<String> extractKeywords(
            String content,
            List<String> requestedKeywords) {

        List<String> foundKeywords = new ArrayList<>();

        if (content == null || requestedKeywords == null) {
            return foundKeywords;
        }

        String searchableContent = content.toLowerCase(Locale.ROOT);

        for (String keyword : requestedKeywords) {

            if (keyword == null || keyword.isBlank()) {
                continue;
            }

            String normalizedKeyword = keyword.trim();

            if (searchableContent.contains(
                    normalizedKeyword.toLowerCase(Locale.ROOT))) {

                foundKeywords.add(normalizedKeyword);
            }
        }

        return foundKeywords;
    }
}
