package org.workshop

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(arg: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("World Count")
      .getOrCreate()

    val lines = spark.read.textFile(arg(0))
    val words = lines.flatMap(line => line.split(" ").filter(w => w != null && !w.isEmpty))
    val pairs = words.map(word => (word, 1))
    val freqs = pairs.reduceByKey((a, b) => a + b)
    val top = freqs.sortBy(_._2, false)

    val top10 = top.take(10)

    top10.foreach(println)

    spark.stop()
  }
}

