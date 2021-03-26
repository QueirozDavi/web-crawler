package services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import services.NaiveClassifierService;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static com.project.webcrawler.config.SparkConfig.getSparkConf;

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
        Set<String> tokens = new HashSet<String>();

        while (tokenStream.incrementToken()) {
            String term = charTermAttribute.toString();
            tokens.add(term.toLowerCase());
        }
        for (String term : tokens) {
            if (term.length() > 5 && term.length() < 15) sb.append(term + " ");
        }

        return sb.toString();
    }

    public void train(List<LabeledPoint> trainList, List<LabeledPoint> testList) {
        SparkConf conf = getSparkConf();
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<LabeledPoint> training = sc.parallelize(trainList, 2).cache();
        JavaRDD<LabeledPoint> test = sc.parallelize(testList, 2).cache();

        final NaiveBayesModel model = NaiveBayes.train(training.rdd(), 1.0);

        JavaPairRDD<Double, Double> predictionAndLabel = getPredictionAndLabel(test, model);
        double accuracy = getAccuracy(test, predictionAndLabel);

        log.info("Model accuracy " + accuracy);

        model.save(sc.sc(), "modelPath");
        sc.close();
    }

    private double getAccuracy(JavaRDD<LabeledPoint> test, JavaPairRDD<Double, Double> predictionAndLabel) {
        return predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {

            private static final long serialVersionUID = 8604799362581634343L;

            @Override
            public Boolean call(Tuple2<Double, Double> pl) {
                return pl._1().equals(pl._2());
            }
        }).count() / (double) test.count();
    }

    private JavaPairRDD<Double, Double> getPredictionAndLabel(JavaRDD<LabeledPoint> test, NaiveBayesModel model) {
        return test.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {

            private static final long serialVersionUID = -4498879256866700408L;

            @Override
            public Tuple2<Double, Double> call(LabeledPoint p) {
                return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
            }
        });
    }

    @Override
    public List<LabeledPoint> getPoints(String [] pages, double label) throws Exception {
        HashingTF tf = new HashingTF();

        List<LabeledPoint> labeledPoints = new ArrayList<LabeledPoint>();

        for(String page:pages){
            String text = removeStopWords(page);
            Vector vector = tf.transform(Arrays.asList(text.split(" ")));
            labeledPoints.add(new LabeledPoint(label, vector));
        }

        return labeledPoints;
    }
}
