package com.project.webcrawler.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrawlerStatistics {

    private int processedPageCount = 0;
    private int totalLinksCount = 0;

    public void incrementProcessedPageCount() {
        processedPageCount++;
    }

    public void incrementTotalLinksCount(int linksCount) {
        totalLinksCount += linksCount;
    }

}
