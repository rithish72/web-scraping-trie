
package com.assignment.scraper.trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    // Stores child nodes for each character
    private Map<Character, TrieNode> children;

    // Indicates whether a complete word ends here
    private boolean isEndOfWord;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
