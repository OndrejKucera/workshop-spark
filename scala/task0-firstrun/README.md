## Task 0: The First Run of Spark

You will have a brief look at Spark API and run the first job. 
___

#### 1. Briefly look at the Spark directory
  Go to the main directory of Apache Spark
  ```
  cd /usr/local/spark/
  ```
  Inspect the files in the bin directory. You have aleady used the ```spark-shell``` that starts the REPL of Spark. Also note ```spark-submit```, which is used to submit standalone Spark programs to a cluster.

  Inspect the scripts in the sbin directory. These scripts help with setting up a standalone Spark cluster, deploying Spark to EC2 virtual machines, and a bunch of additional tasks.

  Finally, take a look at the examples directory. You can find a number of standalone demo programs here, covering a variety of Spark APIs.
___

#### 2. Spark REPL
  ```
  spark-shell --master spark://spark:7077
  ```
  Spark’s shell provides a simple way to learn the API, as well as a powerful tool to analyze data interactively.
  During starting a REPL you can see lines as:
  * [Spark context](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/spark-SparkContext.html) is available as 'sc' - It is entry point to Spark Core and the heart of a Spark application. It is owned by Spark session.
  * [Spark session](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/spark-sql-SparkSession.html#implicits) is available as 'spark' - It is the very first object you have to create while developing Spark SQL applications.
___

#### 3. Run your first job
  ```
  cat /usr/local/spark/examples/src/main/scala/org/apache/spark/examples/SparkPi.scala
  ```
  The source code of SparkPi.scala from the Spark examples
  ```
  package org.apache.spark.examples

  import scala.math.random
  import org.apache.spark.sql.SparkSession

  /** Computes an approximation to pi */
  object SparkPi {
    def main(args: Array[String]) {
      val spark = SparkSession
        .builder
        .appName("Spark Pi")
        .getOrCreate()
        
      val slices = if (args.length > 0) args(0).toInt else 2
      val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
      
      val count = spark.sparkContext
        .parallelize(1 until n, slices)
        .map { i =>
          val x = random * 2 - 1
          val y = random * 2 - 1
          if (x*x + y*y <= 1) 1 else 0
        }.reduce(_ + _)
        
      println("Pi is roughly " + 4.0 * count / (n - 1))
      spark.stop()
    }
  }
  ```

Execute Spark job for calculating `Pi` Value
  ```
  spark-submit \
  --class org.apache.spark.examples.SparkPi \
  --master spark://spark:7077 \
  /usr/local/spark/examples/jars/spark-examples_2.11-2.2.0.jar \
  100
  Pi is roughly 3.140495114049511
  ```
OR even simpler
  ```
  $SPARK_HOME/bin/run-example SparkPi 100
  Pi is roughly 3.1413855141385514
  ```

Please note the first command above expects Spark Master and Slave to be running. We can even check the Spark Web UI after executing this command. You can write ```stop-master.sh``` and you will see that the first command doesn't work.

