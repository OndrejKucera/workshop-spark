package org.workshop;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.ReduceFunction;
import org.apache.spark.sql.*;
import scala.Serializable;
import scala.Tuple2;

import static org.apache.spark.sql.types.DataTypes.DoubleType;

public final class Flights {

  public static void main(String[] args) throws Exception {

    SparkSession spark = SparkSession
      .builder()
        .appName("Word Count")
        .getOrCreate();

    Dataset<Row> flightsDF = spark.read()
      .format("csv")
        .option("header", "true")
        .option("mode", "DROPMALFORMED")
      .option("inferSchema", true)
        .load(args[0]);

    JavaRDD<Row> rdd = flightsDF.javaRDD();
    rdd.cache();


    // 3.1) RDD: airline with the most flights from Boston
    JavaPairRDD<String, Integer> airlineWithMostFlights = rdd
      .filter((Row row) -> row.getAs("OriginCityName").equals("Boston, MA"))
      .mapToPair((Row row) -> new Tuple2<>((String)row.getAs("Carrier"), 1))
      .reduceByKey((i1, i2) -> i1 + i2);
    JavaPairRDD<String, Integer> sortedFlights = sortByValue(airlineWithMostFlights).coalesce(1);
    sortedFlights.saveAsTextFile(args[1] + "/airline-with-most-flight/");


    // 3.2) RDD: airline with the worst average delay
    JavaPairRDD<String, Double> airlineWithWorstDelay = rdd
      .filter((Row row) -> !row.isNullAt(row.fieldIndex("ArrDelay"))
          && (Double)row.getAs("ArrDelay") > 0)
      .mapToPair(row -> new Tuple2<>((String)row.getAs("Carrier"),
          (Double)row.getAs("ArrDelay")))
      // count of average
      .mapValues(value -> new Tuple2<>(value, 1))
      .reduceByKey((a, b) -> new Tuple2<>(a._1() + b._1(), a._2() + b._2()))
      .mapValues(value -> value._1()/value._2());
    JavaPairRDD<String, Double> sortedDelays = sortByValue(airlineWithWorstDelay).coalesce(1);
    sortedDelays.saveAsTextFile(args[1] + "/airline-with-worst-delay/");


    // 4.1) Dataset: airline with the least delay
    Dataset<MyRow> newYorkToSanFran = flightsDF
      .filter((flightsDF.col("OriginCityName").equalTo("New York, NY"))
        .and(flightsDF.col("DestCityName").equalTo("San Francisco, CA"))
        .and(flightsDF.col("ArrDelay").gt(0)))
      .map((MapFunction<Row, MyRow>) row ->
        new MyRow(row.getAs("Carrier"), row.getAs("ArrDelay")), Encoders.bean(MyRow.class));
    KeyValueGroupedDataset<String, MyRow> carrierGrouped = newYorkToSanFran.groupByKey(
        (MapFunction<MyRow, String>) MyRow::getCarrier, Encoders.STRING());
    Dataset<Tuple2<String, MyRow>> sumDelayCarriers = carrierGrouped.reduceGroups(
        (ReduceFunction<MyRow>) (r1, r2) -> new MyRow(r1.getCarrier(), r1.getArrDelay() + r2.getArrDelay()));
    Dataset<MyRow> airlineWithLeastDelay = sumDelayCarriers.map(
        (MapFunction<Tuple2<String, MyRow>, MyRow>) Tuple2::_2, Encoders.bean(MyRow.class));
    Dataset<MyRow> sortedLeastDelay = airlineWithLeastDelay.orderBy("ArrDelay").coalesce(1);
    sortedLeastDelay.write().csv(args[1] + "/airline-with-least-delay/");


    // 5.1) SQL: the farthest 10 destination from Chicago
    flightsDF.withColumn("Distance", new Column("Distance").cast(DoubleType))
       .createOrReplaceTempView("flights");
    Dataset<Row> furthestDestination = spark.sql("SELECT DISTINCT OriginCityName, DestCityName, Distance " +
          "FROM flights " +
          "WHERE OriginCityName == 'Chicago, IL' " +
          "ORDER BY Distance " +
          "DESC LIMIT 10");
    furthestDestination.write().format("csv").save(args[1] + "/farthest-destination/");


    spark.stop();
  }

  // helper method sortByValue (it doesn't exist in Java API)
  private static <T extends Number> JavaPairRDD<String, T> sortByValue(JavaPairRDD<String, T> rdd) {
    return rdd
      .mapToPair(Tuple2::swap)
      .sortByKey(false)
      .mapToPair(Tuple2::swap);
  }

  // the class has to be Java bean
  public static class MyRow implements Serializable {
    private String carrier;
    private Double arrDelay;

    public MyRow() {}

    public MyRow(String carrier, Double delay) {
      this.carrier = carrier;
      this.arrDelay = delay;
    }

    public String getCarrier() {
      return carrier;
    }

    public void setCarrier(String c) {
      this.carrier = c;
    }

    public Double getArrDelay() {
      return arrDelay;
    }

    public void setArrDelay(Double delay) {
          this.arrDelay = delay;
    }
  }

}
