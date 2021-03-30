package com.project.webcrawler.model.entity;

import com.project.webcrawler.model.DocumentBase;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Crawler extends DocumentBase {

    private String id;
    private boolean success;
    private LocalDateTime executedAtTime;
    private Set<WebURL> goingOutUrls;
    private String visitedWebSiteName;

}
