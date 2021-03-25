package services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import services.NaiveClassifierService;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service()
public class NaiveClassifierServiceImpl implements NaiveClassifierService {

    @Override
    public String removeStopWords(String textFile) throws Exception {
        CharArraySet stopWords = PortugueseAnalyzer.getDefaultStopSet();

        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_48, new StringReader(textFile.trim()));
        tokenStream = new StopFilter(Version.LUCENE_48, tokenStream, stopWords);
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        Set<String> tokens=new HashSet<String>();

        while (tokenStream.incrementToken()) {
            String term = charTermAttribute.toString();
            tokens.add(term.toLowerCase());
        }
        for(String term:tokens){
            if(term.length()>5 && term.length()<15) sb.append(term + " ");
        }

        return sb.toString();
    }

}
