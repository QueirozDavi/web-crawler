package com.project.webcrawler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerResponseDTO {

    private boolean success;
    private LocalDateTime executedAtTime;
    private String visitedWebSiteName;

}
