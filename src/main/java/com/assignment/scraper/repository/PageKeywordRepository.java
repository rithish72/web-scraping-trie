
package com.assignment.scraper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.scraper.entity.PageKeyword;

public interface PageKeywordRepository
        extends JpaRepository<PageKeyword, Long> {

    // Find all keyword records belonging to a particular page
    List<PageKeyword> findByScrapedPage_Id(Long pageId);

    // Find pages containing a specific keyword
    List<PageKeyword> findByKeywordIgnoreCase(String keyword);

    // Find keywords beginning with a given prefix
    List<PageKeyword> findByKeywordStartingWithIgnoreCase(String prefix);
}
