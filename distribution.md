# Distributed computing
Why and when should you consider any data distributed computing model?
___

For reasonable small datasets, we can use R/Python/Matlab and execute the parallel computation on a single machine, where tasks share memory and CPU. Let's look at this approach closer.

### Parallel (shared memory) computing
  - Split data
  - Workers/threads independently and parallelly work with data
  - When the work is done, combine the results

In this case, we have only one machine which runs paralelly more threads. Data are partitioned in memory. But what if for example our data doesn't fit into a memory? When we need more memory we can [scale](notes.md#Scalability) by adding more machines.

### Distributed computing
  - Split data over all nodes
  - Nodes independently and parallelly do the work with data 
  - When nodes are finished let's combined the results

The solution in distributed system, where the **nodes are independent and don't physically share memory or processor**, seems to be quite similar to the "just" parallel computing. Data are partitioned among nodes. We solved the memory issue by adding more nodes but we've got new characteristic to deal with - the network and its latency.

### When do we need data distributed computing model?
When we hit the limit of one of the resources:
  - CPU (i.e. we need count logs faster than they are generated)
  - Memory
  - Disk (i.e. when the storage is not big enough for data)
  - Network

### The frameworks for distributed data computing
They try to solve many problems which can accure in a distributed environment:
  - partial failure, a subset of nodes can crash in a cluster  
  - the communication between the nodes
  - sending data over the network, [data latency](http://blog.morizyun.com/computer-science/basic-latency-comparison-numbers.html#Latency-Comparison-Numbers) (1 MB from disk 100x expensive than read it from memory) (it is related with [data locality]())
  - checking resources (CPU, memory) of the cluster
  - persist data over the cluster
