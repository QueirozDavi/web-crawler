package com.project.webcrawler.factory;

import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.model.entity.Crawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrawlerFactory {

    public static CrawlerResponseDTO generateResponseDTO() {
        return CrawlerResponseDTO.builder()
                .visitedWebSiteName("Xpto | Web Site")
                .executedAtTime(LocalDateTime.now())
                .success(true)
                .build();
    }

    public static Crawler generateValidCrawler() {
        return Crawler.builder()
                .visitedWebSiteName("Xpto | Web Site")
                .executedAtTime(LocalDateTime.now())
                .success(true)
                .id("6062a4f43347da2014a79b02")
                .goingOutUrls(getWebURLs())
                .build();
    }


    private static Set<WebURL> getWebURLs() {
        return new HashSet<>(getWebURLList());
    }

    private static List<WebURL> getWebURLList() {
        WebURL webURL = new WebURL();
        webURL.setURL("https://img-cdn.hltv.org/teamlogo/C1Nyy0ZcMxR_iUlJFLtUW7.svg");
        webURL.setDocid(-1);
        webURL.setParentDocid(1);

        WebURL webURL2 = new WebURL();
        webURL2.setURL("https://img-cdn.hltv.org/teamlogo/C1Nyy0ZcMxR_iUlJFLtUW7.svg");
        webURL2.setDocid(-1);
        webURL2.setParentDocid(1);

        WebURL webURL3 = new WebURL();
        webURL3.setURL("https://img-cdn.hltv.org/teamlogo/C1Nyy0ZcMxR_iUlJFLtUW7.svg");
        webURL3.setDocid(-1);
        webURL3.setParentDocid(1);
        return Arrays.asList(webURL, webURL2, webURL3);
    }
}
