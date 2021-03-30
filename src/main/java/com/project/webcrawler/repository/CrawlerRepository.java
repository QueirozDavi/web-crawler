package com.project.webcrawler.repository;

import com.project.webcrawler.model.entity.Crawler;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrawlerRepository extends MongoRepository<Crawler, String> {

    Crawler findFirstByOrderByCreatedAtDesc();

}
