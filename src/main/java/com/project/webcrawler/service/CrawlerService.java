package com.project.webcrawler.service;

import com.project.webcrawler.exception.BadRequestException;
import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.repository.CrawlerRepository;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class CrawlerService {

    private final HtmlCrawlerService htmlCrawlerService;
    private final CrawlerRepository repository;
    private final ModelMapper mapper;

    @Value("${website.url}")
    private String URL;
    @Value("${path.name}")
    private String PATH_NAME;
    @Value("${crawlers.number}")
    private int NUM_CRAWLERS;
    @Value("${crawlers.politenessDelay}")
    private int POLITENESS_DELAY;
    @Value("${crawlers.maxDepthOfCrawling}")
    private int MAX_DEPTH_CRAWLING;
    @Value("${crawlers.maxPagesToFetch}")
    private int MAX_PAGE_TO_FETCH;
    @Value("${crawlers.includeBinaryContent}")
    private boolean INCLUDE_BINARY_CONTENT;


    @Autowired
    public CrawlerService(HtmlCrawlerService htmlCrawlerService, CrawlerRepository repository, ModelMapper mapper) {
        this.htmlCrawlerService = htmlCrawlerService;
        this.repository = repository;
        this.mapper = mapper;
    }


    protected CrawlController getCrawlController() throws Exception {
        File crawlStorage = new File(PATH_NAME);
        CrawlConfig config = new CrawlConfig();
        getCrawlConfig(crawlStorage, config);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        return new CrawlController(config, pageFetcher, robotstxtServer);
    }

    private void getCrawlConfig(File crawlStorage, CrawlConfig config) {
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        config.setPolitenessDelay(POLITENESS_DELAY);
        config.setMaxDepthOfCrawling(MAX_DEPTH_CRAWLING);
        config.setMaxPagesToFetch(MAX_PAGE_TO_FETCH);
        config.setIncludeBinaryContentInCrawling(INCLUDE_BINARY_CONTENT);
    }

    public CrawlerResponseDTO initCrawler() throws Exception {
        CrawlController controller = getCrawlController();
        controller.addSeed(URL);
        CrawlController.WebCrawlerFactory<HtmlCrawlerService> factory = () -> htmlCrawlerService;

        try {
            log.info("Initing Web Crawling");
            controller.start(factory, NUM_CRAWLERS);
        } catch (Exception e) {
            log.error("Faield to Crawl: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        log.info("Success at Crawling requisition.");

        return mapper.map(repository.findFirstByOrderByCreatedAtDesc(), CrawlerResponseDTO.class);
    }
}
