## Task 1: Word Count

You will see a spark code and run your first Spark job.
> **NOTE** Solution is already in WordCount.scala file, please don't look at it :-). Try to come up with your solution first. We can check it later.
___

#### 1. Check data
  You will process texts of books that are freely avaliable at [Gutenberg project](http://www.gutenberg.org/). Take a look at the text files that are in the resources directory of ```task1-wordcount```.
  ```
  head -n 50 /root/workshop-spark/data/task1/*.txt | less
  ```
  This shows the first 50 lines of each file.
___

#### 2. How to load data
   You can use the [SparkContext.textFile](https://spark.apache.org/docs/2.2.0/api/scala/index.html#org.apache.spark.SparkContext) method to load all data. The textFile method can work with a directory path or a wildcard filter such as /*.txt.
  Your first task is to print out the number of lines in all the text files, combined. The solution should be quite short so you could write in only in the spark-shell.
  ```
  spark.read.textFile("file:///root/workshop-spark/data/task1/*.txt")
  ```
___

#### 3. Implementing the Word Count
  Your task is to implement the actual word-count program. Print the top 10 most frequent words in the provided books. Create solution to the spark-shell. When it will work you can try to copy-paste it to ```scala/task1-worldcount/src/main/scala/org/workshop/WordCount.scala``` and then build the jar by the command.
  ```
  sbt package
  ```
  SBT wil create the jar in ```target/scala-2.11/word-count_2.11-1.0.jar```. When you have your jar then you can [submit](https://spark.apache.org/docs/latest/submitting-applications.html#launching-applications-with-spark-submit) the job to spark cluster.
  ```
  spark-submit \
  --class org.workshop.WordCount \
  --master spark://spark:7077 \
  --executor-memory 1G \
  --total-executor-cores 2 \
  target/scala-2.11/word-count_2.11-1.0.jar \
  "/root/workshop-spark/data/task1/*.txt"
  ```
___

#### 4. Discussion

You could use ```reduceByKey``` but also the method ```countByValue```. Read [its documentation](https://spark.apache.org/docs/2.0.1/api/scala/index.html#org.apache.spark.rdd.RDD), and try to understand how it works. Which method is better to use and why?
