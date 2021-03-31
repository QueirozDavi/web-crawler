package com.project.webcrawler.service;

import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.repository.CrawlerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.project.webcrawler.factory.CrawlerFactory.generateValidCrawler;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerServiceTest {

    private CrawlerRepository crawlerRepository;
    private HtmlCrawlerService htmlCrawlerService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CrawlerService service;

    @Before
    public void setUp() {
        crawlerRepository = Mockito.mock(CrawlerRepository.class);
        htmlCrawlerService = Mockito.mock(HtmlCrawlerService.class);
    }

    @Test
    public void shouldInitCrawler() throws Exception {
        when(crawlerRepository.findFirstByOrderByCreatedAtDesc()).thenReturn(generateValidCrawler());
        when(htmlCrawlerService.shouldVisit(any(), any())).thenReturn(true);
        doNothing().when(htmlCrawlerService).visit(any());
        CrawlerResponseDTO response = service.initCrawler();

        assertNotNull(response);
    }
}
