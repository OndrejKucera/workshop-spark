package org.workshop;

import scala.Tuple2;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import java.util.Arrays;
import java.util.List;

public final class WordCount {

  public static void main(String[] args) throws Exception {

    SparkSession spark = SparkSession
      .builder()
      .appName("Word Count")
      .getOrCreate();

    JavaRDD<String> lines = spark.read().textFile(args[0]).javaRDD();
    JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
    JavaPairRDD<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
    JavaPairRDD<String, Integer> freqs = pairs.reduceByKey((i1, i2) -> i1 + i2);

    // sortByValue, bit more comlicated than in scala
    JavaPairRDD<Integer, String> swaped = freqs.mapToPair(pair -> pair.swap());
    JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);
    JavaPairRDD<String, Integer> top = sorted.mapToPair(pair -> pair.swap());

    // print first 10
    List<Tuple2<String, Integer>> output = top.take(10);
    for (Tuple2<?,?> tuple : output) {
      System.out.println(tuple._1() + ": " + tuple._2());
    }

    spark.stop();
  }

}
