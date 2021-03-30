package com.project.webcrawler.service;

import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.repository.CrawlerRepository;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.webcrawler.factory.CrawlerResponseDTOFactory.generateResponse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerServiceTest {

    private CrawlerRepository crawlerRepository;
    private HtmlCrawlerService htmlCrawlerService;
    private CrawlController crawlController;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CrawlerService service;

    @Before
    public void setUp() {
        crawlerRepository = Mockito.mock(CrawlerRepository.class);
        htmlCrawlerService = Mockito.mock(HtmlCrawlerService.class);
        crawlController = Mockito.mock(CrawlController.class);
    }

    @Test
    public void shouldInitCrawler() throws Exception {
        when(crawlerRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(generateResponse());
        when(htmlCrawlerService.shouldVisit(any(), any())).thenReturn(true);
        doNothing().when(htmlCrawlerService).visit(any());
//        doNothing().when(crawlController).start(eq(CrawlController.WebCrawlerFactory.class), any());
        CrawlerResponseDTO response = service.initCrawler();

        assertNotNull(response);
    }
}
