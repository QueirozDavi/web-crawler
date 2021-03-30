package com.project.webcrawler.config;

import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.model.entity.Crawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(getMapHtmlParseDataToCrawlerResponseDTO(), HtmlParseData.class, Crawler.class);
        modelMapper.addConverter(getMapCrawlerToCrawlerResponseDTO(), Crawler.class, CrawlerResponseDTO.class);
        return modelMapper;
    }


    private Converter<HtmlParseData, Crawler> getMapHtmlParseDataToCrawlerResponseDTO() {

        return context -> Crawler.builder()
                .visitedWebSiteName(context.getSource().getTitle())
                .executedAtTime(LocalDateTime.now())
                .goingOutUrls(CollectionUtils.isEmpty(context.getSource().getOutgoingUrls())
                        ? null : context.getSource().getOutgoingUrls())
                .success(true)
                .build();
    }

    private Converter<Crawler, CrawlerResponseDTO> getMapCrawlerToCrawlerResponseDTO() {

        return context -> CrawlerResponseDTO.builder()
                .visitedWebSiteName(context.getSource().getVisitedWebSiteName())
                .executedAtTime(context.getSource().getExecutedAtTime())
                .success(context.getSource().isSuccess())
                .build();
    }
}
