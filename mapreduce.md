# MapReduce vs. Spark

### What is MapReduce
 - 2004 [Google’s MapReduce paper](https://static.googleusercontent.com/media/research.google.com/en//archive/mapreduce-osdi04.pdf)
 - It was inspired by the map and reduce functions in functional programming: "Our abstraction is inspired by the map and reduce primitives present in Lisp and many other functional languages"
 - Phases: **map(key, value)** + shuffle phase(partitioning + sorting + grouping) + **reduce(key, list<value>)** [slides 11-21](https://www.slideshare.net/JoaquinVanschoren/hadoop-tutorial-12877034)
 - Data are always serialized and stored on the disk after the map phase.
 - Note: after the map phase, **combiner** can be defined. it can help to reduce the amount of data written to disk, and transmitted over the network by aggregating partial results. Input and output of the combiner have to be identical. 

### Characteristics of the MapReduce
In this section, it is considered **MapReduce** in a meaning of a default processing engine of **Hadoop**.

  Charachteristics:
   * work with permanent storage -> handle enormous datasets
   * less expensive hardware -> since it doesn't need that much memory
   * incredible scalability potential
   * Hadoop has an extensive ecosystem
   * fault tolerance 

It is best suited for handling very large data that sit on permament storage.

But there are some disatvatages:
  * constrained by two stages (map and reduce)
  * uncofortable work with API -> skilled developer
  * heavily leverages permanent storage, reading and writing multiple times per task, it tends to be fairly slow

It has to be said that many concepts are good even today and Hadoop is frequently used as a building block for other software in many frameworks.

### What about Apache Spark?
All what it is mentioned plus ...
  * confortable API
  * not only two stages
  * it is possible create data flow
  * work with operation memory -> better performace
  * batch and streaming (kind of)
  * doesn't have to rely on Hadoop (no HDFS, no YARN), warriaty of inputs/outpus

But there are may other frameworks that can be used. We focus on Apache Spark because it has [huge community](https://www.openhub.net/p/apache-spark) 

### Other frameworks:
  * batch only: [Apache Hadoop](http://hadoop.apache.org/)
  * stream only: [Apache Storm](http://storm.apache.org/), [Apache Samza](http://samza.apache.org/)
  * hybrid (batch, stream): [Apache Flink](https://flink.apache.org/), [Apache Spark](https://spark.apache.org/), [Apache Apex](https://apex.apache.org/)
