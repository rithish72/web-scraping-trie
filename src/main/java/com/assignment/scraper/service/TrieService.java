
package com.assignment.scraper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.scraper.entity.PageKeyword;
import com.assignment.scraper.repository.PageKeywordRepository;
import com.assignment.scraper.trie.Trie;

@Service
public class TrieService {

    private final Trie trie;
    private final PageKeywordRepository pageKeywordRepository;

    public TrieService(PageKeywordRepository pageKeywordRepository) {

        this.pageKeywordRepository = pageKeywordRepository;
        this.trie = new Trie();

        loadKeywords();
    }

    // Load existing keywords from MySQL into Trie
    public void loadKeywords() {

        trie.clear();

        List<PageKeyword> keywords = pageKeywordRepository.findAll();

        for (PageKeyword pageKeyword : keywords) {

            trie.insert(pageKeyword.getKeyword());
        }
    }

    // Insert a new keyword
    public void insertKeyword(String keyword) {

        if (keyword != null && !keyword.isBlank()) {
            trie.insert(keyword.trim());
        }
    }

    // Search for autocomplete suggestions
    public List<String> autocomplete(String prefix) {

        if (prefix == null || prefix.isBlank()) {
            return List.of();
        }

        return trie.autocomplete(prefix.trim());
    }

    // Check whether a keyword exists
    public boolean search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return false;
        }

        return trie.search(keyword.trim());
    }
}
