package services.impl;

import com.project.webcrawler.exception.BadRequestException;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import services.IndexingCrawlerService;

@Slf4j
@Service
public class IndexingCrawlerServiceImpl implements IndexingCrawlerService {

//    private static CrawlerIndexer indexer = new CrawlerIndexer();
//    private static NaiveClassifier classifier = new NaiveClassifier();

    @Override
    public void visiting(Page page) {

        log.info("Visiting page {}", page.getWebURL());

        if (page.getParseData() instanceof HtmlParseData) {
            String url = page.getWebURL().getURL();
            String domain = page.getWebURL().getDomain();
            HtmlParseData htmlParsedData = (HtmlParseData) page.getParseData();
            String text = htmlParsedData.getText();
            String html = htmlParsedData.getHtml();

//            String classification = classifier.getClass(text);


        } else
            throw new BadRequestException("It's not a HTML Page");


    }
}
