# Setting the enviroment

## Download and install git:
  - mac/windows/ubuntu: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
  
#### clone this project
  ```
  git clone https://github.com/OndrejKucera/workshop-spark.git
  ```

## Download and install the docker:
  - mac: https://docs.docker.com/docker-for-mac/install/
  - windows: https://docs.docker.com/docker-for-windows/install/
  - ubuntu: https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/

## Prepare the docker:
#### build the docker image
  ```
  docker build -t spark-docker .
  ```
#### run docker container
  {your-dir} stands for your absolute path to workshop-spark directory that you cloned.
  ```
  docker run -it -p 4040:4040 -p 8080:8080 -p 8081:8081 -v {your-dir}:/root/workshop-spark -h spark --rm --name=spark spark-docker
  ```

## Check the enviroment:
#### Host name:
  ```
  root@spark:~# hostname
  spark
  ```
#### Java:
  ```
  root@spark:~# java -version
  openjdk version "1.8.0_141"
  OpenJDK Runtime Environment (build 1.8.0_141-8u141-b15-1~deb9u1-b15)
  OpenJDK 64-Bit Server VM (build 25.141-b15, mixed mode)
  ```

#### Scala:
  ```
  root@spark:~# scala -version
  Scala code runner version 2.12.2 -- Copyright 2002-2017, LAMP/EPFL and Lightbend, Inc.
  ```

#### Maven:
  ```
  root@spark:~# mvn -version
  Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T07:58:13Z)
  ```
  
#### SBT:
  ```
  Running `sbt about` will download and setup SBT on the image.
  ```

#### Spark:
  * master = local[*]	- Run Spark locally with as many worker threads as logical cores on your machine.
  ```
  root@spark:~# spark-shell
  park context Web UI available at http://172.17.0.2:4040
  Spark context available as 'sc' (master = local[*], app id = local-1509561912585).
  Spark session available as 'spark'.
  Welcome to
        ____              __
       / __/__  ___ _____/ /__
      _\ \/ _ \/ _ `/ __/  '_/
     /___/ .__/\_,_/_/ /_/\_\   version 2.2.0
        /_/

  Using Scala version 2.11.8 (OpenJDK 64-Bit Server VM, Java 1.8.0_141)
  Type in expressions to have them evaluated.
  Type :help for more information.
  scala>
  ```

#### Spark commands:
All the required binaries have been added to the `PATH`.
  - Start Spark Master: ```start-master.sh```
  - Start Spark Slave: ```start-slave.sh spark://spark:7077```
  - Start Spark Shell: ```spark-shell --master spark://spark:7077``` to connect shell to the Spark standalone cluster master

### View Spark Master WebUI console:
  [`http://localhost:8080/`](http://localhost:8080/)

### View Spark Worker WebUI console:
  [`http://localhost:8081/`](http://localhost:8081/)

### View Spark WebUI console
  [`http://localhost:4040/`](http://localhost:4040/) (Only available during a running of Spark application.)
