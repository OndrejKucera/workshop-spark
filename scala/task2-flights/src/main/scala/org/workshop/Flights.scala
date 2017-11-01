package org.workshop

import org.apache.spark.sql.{Column, Dataset, SparkSession}
import org.apache.spark.sql.types.DoubleType

object Flights {
  def main(arg: Array[String]) {
    val spark = SparkSession.builder
      .appName("Flights")
      .getOrCreate()

    import spark.implicits._

    val flightsDF = spark.read
      .format("csv")
      .option("header", "true")
      .option("mode", "DROPMALFORMED")
      .option("inferSchema", true)
      .load(arg(0))

    val rdd = flightsDF.rdd
    rdd.cache()


    // 3.1) RDD: airline with the most flights from Boston
    val airlineWithMostFlights = rdd
      .filter(f => f.getAs("OriginCityName") == "Boston, MA")
      .map(f => (f.getAs[String]("Carrier"), 1))
      .reduceByKey(_ + _)
    val sortedFlights = airlineWithMostFlights.sortBy(_._2, false).coalesce(1)
    sortedFlights.saveAsTextFile(arg(1) + "/airline-with-most-flight/")


    // 3.2) RDD: airline with the worst average delay
    val airlineWithWorstDelay = rdd
      .filter(r => !r.isNullAt(r.fieldIndex("ArrDelay"))
        && r.getAs[Double]("ArrDelay") > 0)
      .map(row => (row.getAs[String]("Carrier"), row.getAs[Double]("ArrDelay").toDouble))
      // count of average
      .mapValues(value => (value, 1))
      .reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2))
      .mapValues(v => v._1/v._2)
    val sortedDelays = airlineWithWorstDelay.sortBy(_._2, false).coalesce(1)
    sortedDelays.saveAsTextFile(arg(1) + "/airline-with-worst-delay/")


    // 4.1) Dataset: airline with the least delay
    val newYToSanF: Dataset[MyRow] = flightsDF
      .filter((flightsDF.col("OriginCityName") === "New York, NY")
        && (flightsDF.col("DestCityName") === "San Francisco, CA")
        && (flightsDF.col("ArrDelay") > 0))
      .map(f => MyRow(f.getAs[String]("Carrier"), f.getAs[Double]("ArrDelay").toDouble))
    val airlineWithLeastDelay = newYToSanF
      .groupByKey(_.carrier)
      .reduceGroups((a, b) => MyRow(a.carrier, a.arrDelay + b.arrDelay))
      .map(_._2)
    val sortedLeastDelay = airlineWithLeastDelay.orderBy("arrDelay").coalesce(1)
    sortedLeastDelay.write.format("csv").save(arg(1) + "/airline-with-least-delay/")


    // 5.1) SQL: the furthest 10 destination from Chicago
    flightsDF.withColumn("Distance", new Column("Distance").cast(DoubleType))
      .createOrReplaceTempView("flights")
    val furthestDestination = spark.sql("""SELECT DISTINCT OriginCityName, DestCityName, Distance
      FROM flights
      WHERE OriginCityName == 'Chicago, IL'
      ORDER BY Distance
      DESC LIMIT 10""")
    furthestDestination.write.format("csv").save(arg(1) + "/furthest-destination/")


    spark.stop()
  }

  case class MyRow(carrier: String, arrDelay: Double)
}

