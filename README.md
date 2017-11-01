# Workshop: An Intorduction to Apache Spark - 101    
![](http://spark.apache.org/docs/latest/img/spark-logo-hd.png)
  
   When you will go thought the workshop you will get to know what is distributed computing, differences between approaches of MapReduce and Spark, basic Spark architecture. You will be able to start [Spark](https://spark.apache.org/) job in the standalone cluster and work with basic [Spark API](https://spark.apache.org/docs/latest/api/scala/index.html) - RDD and Datasets/DataFrames. The workshop focus only on Spark SQL module.
> **NOTE** This workshop was initially created for the [DevFest 2017](https://2017.devfest.cz/speakers/42) in Prague.
___

## Set the environment
  As the first step you have to set your [Spark environment](environment.md) to get everything work. It includes docker installation and description how to run docker container where Apache Spark will be ready to use.
___

## Distributed computing
  [Let's find out](distribution.md) why to choose distributed computing approach and what it actually means. 
___
## Differences between MapReduce and Spark
  Why isn't the MapReduce's approach good enough and what are differences from Spark? You can read [here](mapreduce.md).
___

## Spark’s Basic Architecture
  In order to understand how to use Spark, it is good to understand the basics of [Spark architecture](architecture.md).
___

## Tasks

#### Task 0: The First Run of Spark
  Get to know the Spark, Spark REPL and run your first job.
  * scala: [link](scala/task0-firstrun/README.md)
  * java: [link](java/task0-firstrun/README.md)
___

#### Task 1: Word-count
  You will write your first Spark application. The word-count is the "hello world" in the distribution computation.
  * scala: [link](scala/task1-wordcount/README.md)
  * java: [link](java/task1-wordcount/README.md)
___

#### Task 2: Analyzing Flight Delays
  You will analyze real data with help RDD and Dataset.
  * scala: [link](scala/task2-flights/README.md)
  * java: [link](java/task2-flights/README.md)
___

#### Task 3: Run both spark jobs in the cluster (optional)
  You can submit and run all spark jobs on Spark standalone cluster in cluster deploy mode.
  * scala: [link](scala/task3/README.md)
  * java: [link](java/task3/README.md)
___

Recommendation for further reading: [Spark: The Definitive Guide](http://shop.oreilly.com/product/0636920034957.do)
