
package com.assignment.scraper.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    // Insert a keyword into the Trie
    public void insert(String word) {

        TrieNode current = root;

        for (char ch : word.toLowerCase().toCharArray()) {

            current.getChildren().putIfAbsent(ch, new TrieNode());

            current = current.getChildren().get(ch);
        }

        current.setEndOfWord(true);
    }

    // Check whether an exact word exists
    public boolean search(String word) {

        TrieNode node = findNode(word);

        return node != null && node.isEndOfWord();
    }

    // Find all words matching a given prefix
    public List<String> autocomplete(String prefix) {

        List<String> results = new ArrayList<>();

        TrieNode node = findNode(prefix);

        if (node == null) {
            return results;
        }

        collectWords(node, new StringBuilder(prefix.toLowerCase()), results);

        return results;
    }

    // Find the Trie node corresponding to a prefix
    private TrieNode findNode(String word) {

        TrieNode current = root;

        for (char ch : word.toLowerCase().toCharArray()) {

            current = current.getChildren().get(ch);

            if (current == null) {
                return null;
            }
        }

        return current;
    }

    // Collect all complete words from a given node
    private void collectWords(
            TrieNode node,
            StringBuilder currentWord,
            List<String> results) {

        if (node.isEndOfWord()) {
            results.add(currentWord.toString());
        }

        for (Map.Entry<Character, TrieNode> entry
                : node.getChildren().entrySet()) {

            currentWord.append(entry.getKey());

            collectWords(entry.getValue(), currentWord, results);

            currentWord.deleteCharAt(currentWord.length() - 1);
        }
    }
    
 // Clear all existing Trie data
    public void clear() {
        root.getChildren().clear();
        root.setEndOfWord(false);
    }
}
