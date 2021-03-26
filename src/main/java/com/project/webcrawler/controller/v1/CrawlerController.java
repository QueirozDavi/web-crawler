package com.project.webcrawler.controller.v1;

import com.project.webcrawler.service.HtmlCrawlerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Crawler")
@RestController
@RequestMapping(path = "/v1/crawler")
public class CrawlerController {

    private final HtmlCrawlerService service;

    @Autowired
    public CrawlerController(HtmlCrawlerService service) {
        this.service = service;
    }

    @GetMapping
    public String callCrawler() throws Exception {

        service.crawlerSetup();
        return "success";
    }
}
