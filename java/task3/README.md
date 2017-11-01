## Optional: Run all spark jobs in the cluster
  You can submit and run all app in cluster deploy mode on standalone cluster.
___

#### 1. Set environment  
  Those who has installed 'docker for windows' or 'docker for mac' should already have a docker-compose. You can check it.
  ```
  docker-compose -version
  ```
  Those who doesn't have docker-compose can find the link for installation here.
  * https://docs.docker.com/compose/install/#install-compose
  
  If you run the container from previous tasks then stop and remove it. We want to avoid any collisions of ports.
  ```
  docker stop spark
  ```
  
  Run a instances of one master and two workers
  ```
  docker-compose up -d
  ```
  Go into spar-master's container
  ```
  docker-compose exec spark-master /bin/bash
  ```
  When you wil want to stop and remove all containers and volume then write
  ```
  docker-compose down -v
  ```
___

#### 2. Submit the word-count job
  * '--deploy-mode cluster' the driver program is launched on one of the worker machines inside the cluster
  * '--supervise' restarts the driver on failure
  ```
  spark-submit \
  --class org.workshop.WordCount \
  --master spark://spark-master:7077 \
  --deploy-mode cluster \
  --supervise \
  --executor-memory 1G \
  --total-executor-cores 4 \
  /root/workshop-spark/java/task1-wordcount/target/word-count-1.0.jar \
  "/root/workshop-spark/data/task1/*.txt"
  ```
  After the job will be completed you can explore it in History server [http://localhost:18080/](http://localhost:18080/).
___

#### 3. Submit the flights job
  ```
  spark-submit \
  --class org.workshop.Flights \
  --master spark://spark-master:7077 \
  --deploy-mode cluster \
  --supervise \
  --executor-memory 1G \
  --total-executor-cores 4 \
  /root/workshop-spark/java/task1-wordcount/target/target/flights-1.0.jar \
  "/root/workshop-spark/data/task2/airline-delays.csv"
  "/root/workshop-spark/data/task2/output"
  ```
  After the job will be complete you can explore it in History server [http://localhost:18080/](http://localhost:18080/).
