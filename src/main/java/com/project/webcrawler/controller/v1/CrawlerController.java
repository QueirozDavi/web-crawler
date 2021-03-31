package com.project.webcrawler.controller.v1;

import com.project.webcrawler.exception.BadRequestException;
import com.project.webcrawler.model.dto.CrawlerResponseDTO;
import com.project.webcrawler.service.CrawlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Crawler")
@RestController
@Slf4j
@RequestMapping(path = "/v1/crawler")
public class CrawlerController {

    private final CrawlerService service;

    @Autowired
    public CrawlerController(CrawlerService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation("Init Web Crawler")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK."),
            @ApiResponse(code = 500, message = "Internal Server Error.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request.")
    })
    public CrawlerResponseDTO initCrawler() throws Exception {

        return service.initCrawler();
    }

    @ExceptionHandler
    ResponseEntity<CrawlerResponseDTO> handleException(BadRequestException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(CrawlerResponseDTO.builder().success(false).build(),
                HttpStatus.BAD_REQUEST);
    }
}
