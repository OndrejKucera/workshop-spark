# MapReduce vs. Spark

### What is MapReduce
 Conscept/approche?
 when? what is it? how it was created?
 
 Algorimus: Map + shuffle fáze(partitioning + sort + group by) + Reduce
 follows the map, shuffle, reduce algorithm using key-value pairs
 
 Průběh:
při přenosu dat z mapu do reduce jsou data serializována a ukládána na disk, takže hodnoty musí být Writable
a také klíč musí být WritableComaparable, protože v shuffle fází potřebujeme řadit
Combiner: mohou se provést dílčí agregace, předpočítání a až následně se výsledek pošle do reduceru. Musí mít stejný vstup a výstup. Může a nemusí se spustit. Snižuje se zátěž reduce nodu a vytížení sítě.

 
 the image and explanation?
 https://www.slideshare.net/esaliya/mapreduce-in-simple-terms/3-Sam_thought_of_drinking_the
 
 https://www.slideshare.net/AbeArredondo/mapreduce-64145644
 
 https://www.slideshare.net/JoaquinVanschoren/hadoop-tutorial-12877034

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
