
package com.assignment.scraper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Web Scraping and Trie-Based Search API")
                        .description(
                                "REST APIs for web scraping, keyword extraction, "
                                + "Trie-based prefix search, and scraping job status."
                        )
                        .version("1.0.0"));
    }
}
