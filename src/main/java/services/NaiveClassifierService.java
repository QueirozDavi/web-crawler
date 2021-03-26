package services;

import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.IOException;
import java.util.List;

public interface NaiveClassifierService {

    String removeStopWords(String textFile) throws Exception;

    List<LabeledPoint> getPoints(String [] pages, double label) throws Exception;
}
