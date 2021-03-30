package com.project.webcrawler.config;

import com.project.webcrawler.model.CrawlerStatistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerStatisticsConfig {

    @Bean
    public void initializeCrawlerStatistics() {
        CrawlerStatistics crawlerStatistics = new CrawlerStatistics();
    }
}
