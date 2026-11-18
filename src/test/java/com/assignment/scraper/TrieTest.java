
package com.assignment.scraper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.assignment.scraper.trie.Trie;

class TrieTest {

    @Test
    void testInsertAndSearch() {

        Trie trie = new Trie();

        trie.insert("technology");
        trie.insert("technical");
        trie.insert("technician");

        assertTrue(trie.search("technology"));
        assertTrue(trie.search("technical"));
        assertFalse(trie.search("tech"));
        assertFalse(trie.search("unknown"));
    }

    @Test
    void testAutocomplete() {

        Trie trie = new Trie();

        trie.insert("technology");
        trie.insert("technical");
        trie.insert("technician");
        trie.insert("telephone");

        List<String> results = trie.autocomplete("tech");

        assertEquals(3, results.size());
        assertTrue(results.contains("technology"));
        assertTrue(results.contains("technical"));
        assertTrue(results.contains("technician"));
    }

    @Test
    void testAutocompleteWithNoMatch() {

        Trie trie = new Trie();

        trie.insert("technology");

        List<String> results = trie.autocomplete("java");

        assertTrue(results.isEmpty());
    }
    
    @Test
    void testCaseInsensitiveSearch() {

        Trie trie = new Trie();

        trie.insert("Technology");

        assertTrue(trie.search("technology"));
        assertTrue(trie.search("TECHNOLOGY"));

        List<String> results = trie.autocomplete("TECH");

        assertEquals(1, results.size());
        assertEquals("tech", results.get(0).substring(0, 4));
    }
}
