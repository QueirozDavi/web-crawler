package com.project.webcrawler.service;

import com.project.webcrawler.model.CrawlerStatistics;
import com.project.webcrawler.model.entity.Crawler;
import com.project.webcrawler.repository.CrawlerRepository;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Pattern;

import static com.project.webcrawler.util.PatternUtils.excludePattern;

@Slf4j
@Service
public class HtmlCrawlerService extends WebCrawler {

    @Value("${website.url}")
    private String URL;
    private final static Pattern EXCLUSIONS = excludePattern();
    private int count = 1;

    @Value("${crawlers.number}")
    private int NUM_CRAWLERS;

    private final CrawlerStatistics stats = new CrawlerStatistics();
    private final ModelMapper mapper;
    private final CrawlerRepository repository;

    @Autowired
    public HtmlCrawlerService(ModelMapper mapper, CrawlerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches() && urlString.startsWith(URL);
    }

    @Override
    public void visit(Page page) {
        stats.incrementProcessedPageCount();
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            stats.incrementTotalLinksCount(links.size());

            if (count == NUM_CRAWLERS)
                repository.save(mapper.map(htmlParseData, Crawler.class));

            count++;
        }
    }

}