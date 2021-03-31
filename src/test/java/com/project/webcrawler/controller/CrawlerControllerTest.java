package com.project.webcrawler.controller;

import com.project.webcrawler.WebCrawlerApplication;
import com.project.webcrawler.exception.BadRequestException;
import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.service.CrawlerService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.webcrawler.factory.CrawlerFactory.generateResponseDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WebCrawlerApplication.class
)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class CrawlerControllerTest {

    @MockBean
    private CrawlerService crawlerService;

    @Autowired
    private MockMvc mockMvc;

    private static final String PATH = "/v1/crawler";
    private static final String JSON_CHARSET = "application/json;charset=UTF-8";

    @Test
    public void shouldInitCrawler() throws Exception {

        CrawlerResponseDTO result = generateResponseDTO();

        when(crawlerService.initCrawler()).thenReturn(result);

        mockMvc.perform(
                get(PATH)
                        .accept(JSON_CHARSET))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CHARSET))
                .andExpect(jsonPath("executed_at_time").value(result.getExecutedAtTime().toString()))
                .andExpect(jsonPath("success").value(result.isSuccess()))
                .andExpect(jsonPath("visited_web_site_name").value(result.getVisitedWebSiteName()));
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenInitCrawlerFails() throws Exception {

        when(crawlerService.initCrawler()).thenThrow(BadRequestException.class);

        mockMvc.perform(
                get(PATH)
                        .accept(JSON_CHARSET))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(JSON_CHARSET))
                .andExpect(jsonPath("success").value(false));
    }

}
