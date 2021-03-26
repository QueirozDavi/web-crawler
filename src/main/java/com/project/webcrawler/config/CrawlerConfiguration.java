package com.project.webcrawler.config;

import com.project.webcrawler.service.HtmlCrawlerService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

public class CrawlerConfiguration {

//    private final int NUM_CRAWLERS = 12;
//
//    @Value("${website.url}")
//    private String URL;
//
//    public void crawlerConfigurationSetup() throws Exception {
//        File crawlStorage = new File("src/test/resources/crawler4j");
//        CrawlConfig config = new CrawlConfig();
//        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
//        PageFetcher pageFetcher = new PageFetcher(config);
//        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
//        RobotstxtServer robotstxtServer= new RobotstxtServer(robotstxtConfig, pageFetcher);
//        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
//
//        controller.addSeed(URL);
//
//        CrawlController.WebCrawlerFactory<HtmlCrawlerService> factory = HtmlCrawlerService::new;
//
//        controller.start(factory, NUM_CRAWLERS);
//    }

}
