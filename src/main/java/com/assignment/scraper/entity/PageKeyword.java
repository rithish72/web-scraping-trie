
package com.assignment.scraper.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "page_keywords",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"page_id", "keyword"})
    }
)
public class PageKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String keyword;

    // Many keywords can belong to one scraped page
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private ScrapedPage scrapedPage;

    public PageKeyword() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ScrapedPage getScrapedPage() {
        return scrapedPage;
    }

    public void setScrapedPage(ScrapedPage scrapedPage) {
        this.scrapedPage = scrapedPage;
    }
}
