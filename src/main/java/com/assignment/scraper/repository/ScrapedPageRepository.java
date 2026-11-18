
package com.assignment.scraper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.scraper.entity.ScrapedPage;

public interface ScrapedPageRepository
        extends JpaRepository<ScrapedPage, Long> {

    List<ScrapedPage> findByScrapeJob_Id(Long jobId);

}
