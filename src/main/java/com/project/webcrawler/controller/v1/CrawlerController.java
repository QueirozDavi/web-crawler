package com.project.webcrawler.controller.v1;

import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.service.CrawlerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Crawler")
@RestController
@RequestMapping(path = "/v1/crawler")
public class CrawlerController {

    private final CrawlerService service;

    @Autowired
    public CrawlerController(CrawlerService service) {
        this.service = service;
    }

    @GetMapping
    public CrawlerResponseDTO callCrawler() throws Exception {

        return service.initCrawler();
    }
}
