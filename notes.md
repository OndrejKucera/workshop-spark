##### Batch processing:
xxx

##### Cluster:
Cluster is a set of loosely or tightly connected computers that work together so that, in many respects, they can be viewed as a single system. (source: wikipedia](https://en.wikipedia.org/wiki/Computer_cluster)

##### Data locality:
Ability to move the computation (code) close to where the actual data resides on the node, instead of moving large data to computation. This minimizes network i/o operations (slow HDD) and increases the overall throughput of the system.

##### Hadoop:
I would call it ecosystem for distributed computation: it has to have at least DFS, YARN (resource manager), which runs an application with different frameworks (MapReduce, Spark, ...). We can find it in different distributions [CDH](https://www.cloudera.com/products/open-source/apache-hadoop/key-cdh-components.html), [Hortonwork](https://hortonworks.com/), [MapR](https://mapr.com/products/)

##### Partitioning:
xxxx rozdělení dat na části. Děje se především v mezi stupni, kde se předávají mezi výsledky
Důležité => výpočet je možné paralelizovat jen tak jak je možné rozdělit vstupní data. A to i v reduce fázi. Pokud reduce fáze musí zpracovat 1TB dat, tak to trvá neskutečně dlouho a ani to možná nedoběhne, protože to bude mít problémy s řazením.

##### Scalability:
Scalability is the capability of a system, network, or process to handle a growing amount of work, or its potential to be enlarged to accommodate that growth. 
 - horizontally (add more machines)
 - verticaly (better machine)
 
##### Sharding:
xxxx nasekání dat podle klíče. Například věcí, které se zpracovávají dohromady aby byly společně
 
##### Stream processing:
xxxx
