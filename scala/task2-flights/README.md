## Task 2: Analyzing Flight Delays

You will get a taste of SparkSQL API during an analyzing a real-world dataset that represents information about US flight delays in January 2016. We will use RDD and DataFrame which wraps RDD with schema information. You can [download](https://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=236&DB_Short_Name=On-Time) bigger additional datasets.

> **NOTE** Solutions for all questions are already in Flights.scala file, please don't look at it :-). Try to come up with your solution first. We can check the file later.
___

#### 1. Look at a data
  There is archive file ```airline-delays.csv.zip``` in ```/root/workshop-spark/data/task2``` directory. Unzip archive to same directory as it is. There will be ```airline-delays.csv``` file that represents a comma-separated collection of flight records.

  First, let's count the number of the record.
  ```
  wc -l /root/workshop-spark/data/task2/airline-delays.csv
  ```
  and display first 5 lines of file. The first line represents a header.
  ```
  head -n 5 data/task2/airline-delays.csv
  "Year","Quarter","Month","DayofMonth","DayOfWeek","FlightDate","UniqueCarrier","AirlineID","Carrier","TailNum","FlightNum","OriginAirportID","OriginAirportSeqID","OriginCityMarketID","Origin","OriginCityName","OriginState","OriginStateFips","OriginStateName","OriginWac","DestAirportID","DestAirportSeqID","DestCityMarketID","Dest","DestCityName","DestState","DestStateFips","DestStateName","DestWac","CRSDepTime","DepTime","DepDelay","DepDelayMinutes","DepDel15","DepartureDelayGroups","DepTimeBlk","TaxiOut","WheelsOff","WheelsOn","TaxiIn","CRSArrTime","ArrTime","ArrDelay","ArrDelayMinutes","ArrDel15","ArrivalDelayGroups","ArrTimeBlk","Cancelled","CancellationCode","Diverted","CRSElapsedTime","ActualElapsedTime","AirTime","Flights","Distance","DistanceGroup","CarrierDelay","WeatherDelay","NASDelay","SecurityDelay","LateAircraftDelay","FirstDepTime","TotalAddGTime","LongestAddGTime","DivAirportLandings","DivReachedDest","DivActualElapsedTime","DivArrDelay","DivDistance","Div1Airport","Div1AirportID","Div1AirportSeqID","Div1WheelsOn","Div1TotalGTime","Div1LongestGTime","Div1WheelsOff","Div1TailNum","Div2Airport","Div2AirportID","Div2AirportSeqID","Div2WheelsOn","Div2TotalGTime","Div2LongestGTime","Div2WheelsOff","Div2TailNum","Div3Airport","Div3AirportID","Div3AirportSeqID","Div3WheelsOn","Div3TotalGTime","Div3LongestGTime","Div3WheelsOff","Div3TailNum","Div4Airport","Div4AirportID","Div4AirportSeqID","Div4WheelsOn","Div4TotalGTime","Div4LongestGTime","Div4WheelsOff","Div4TailNum","Div5Airport","Div5AirportID","Div5AirportSeqID","Div5WheelsOn","Div5TotalGTime","Div5LongestGTime","Div5WheelsOff","Div5TailNum",
  2016,1,1,6,3,2016-01-06,"AA",19805,"AA","N4YBAA","43",11298,1129804,30194,"DFW","Dallas/Fort Worth, TX","TX","48","Texas",74,11433,1143302,31295,"DTW","Detroit, MI","MI","26","Michigan",43,"1100","1057",-3.00,0.00,0.00,-1,"1100-1159",15.00,"1112","1424",8.00,"1438","1432",-6.00,0.00,0.00,-1,"1400-1459",0.00,"",0.00,158.00,155.00,132.00,1.00,986.00,4,,,,,,"",,,0,,,,,"",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","",
  2016,1,1,7,4,2016-01-07,"AA",19805,"AA","N434AA","43",11298,1129804,30194,"DFW","Dallas/Fort Worth, TX","TX","48","Texas",74,11433,1143302,31295,"DTW","Detroit, MI","MI","26","Michigan",43,"1100","1056",-4.00,0.00,0.00,-1,"1100-1159",14.00,"1110","1416",10.00,"1438","1426",-12.00,0.00,0.00,-1,"1400-1459",0.00,"",0.00,158.00,150.00,126.00,1.00,986.00,4,,,,,,"",,,0,,,,,"",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","",
  2016,1,1,8,5,2016-01-08,"AA",19805,"AA","N541AA","43",11298,1129804,30194,"DFW","Dallas/Fort Worth, TX","TX","48","Texas",74,11433,1143302,31295,"DTW","Detroit, MI","MI","26","Michigan",43,"1100","1055",-5.00,0.00,0.00,-1,"1100-1159",21.00,"1116","1431",14.00,"1438","1445",7.00,7.00,0.00,0,"1400-1459",0.00,"",0.00,158.00,170.00,135.00,1.00,986.00,4,,,,,,"",,,0,,,,,"",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","",
  2016,1,1,9,6,2016-01-09,"AA",19805,"AA","N489AA","43",11298,1129804,30194,"DFW","Dallas/Fort Worth, TX","TX","48","Texas",74,11433,1143302,31295,"DTW","Detroit, MI","MI","26","Michigan",43,"1100","1102",2.00,2.00,0.00,0,"1100-1159",13.00,"1115","1424",9.00,"1438","1433",-5.00,0.00,0.00,-1,"1400-1459",0.00,"",0.00,158.00,151.00,129.00,1.00,986.00,4,,,,,,"",,,0,,,,,"",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","","",,,"",,,"","",
  ```
___
 
#### 2. Parsing the CSV
  Next, create an DataFrame based on the ```airline-delays.csv``` file.
  ```
  val flightsDF = spark.read
    .format("csv")
    .option("header", "true")
    .option("mode", "DROPMALFORMED")
    .option("inferSchema", true)
    .load("file:///root/workshop-spark/data/task2/airline-delays.csv")
  ```
  The options that are used during a loading:
  * header - Load the first row as a header .
  * mode - Ignores the whole corrupted records.
  * inferSchema - Spark will figure out data types of columns by itself.

  You can check the schema by printing it.
  ```
  flightsDF.printSchema
  ```
___
 
#### 3. Querying with RDD
  Create the [RDD](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.RDD) with [Row](https://spark.apache.org/docs/2.2.0/api/scala/index.html#org.apache.spark.sql.Row)s.
  ```
  val rdd = flightsDF.rdd
  ```
  
  Question 3.1: Suppose you're in 'Boston, MA'. Which airline has the most flights departing from Boston? 
  > **HINT**: The solution is quite similar as the previous word-count. Try to focus on filter, map, reduceByKey and sortBy methods.
  ```
  val onlyFromBoston = ...
  val airlinesOnlyFromBoston = ...
  val airlineWithMostFlights = ...
  val airlineWithMostFlight = ...
  ```
  
  Question 3.2: Overall, which airline has the worst average delay? How bad was that delay?
  > **HINT**: The Column 'ArrDelay' can be negative. The negative number means that an airplane came earlier. So you should filter all negative numbers. The tricky part could be to count an average :-).
___
 
#### 4. Querying with DataSet
  Now, We are going to work with [DataSet](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.sql.Dataset).

  Question 4.1: Suppose you're in 'New York, NY' and you want to take direct flight to 'San Francisco, CA'. In terms of arrival delay, which airline has the best record on that route?
___
 
#### 5. Querying with ordinary SQL
  Question 5.1: Living in 'Chicago, IL', what are the furthest 10 destinations that you could fly to? (Note that our dataset contains only US domestic flights.)
  Create temporary table ```flights```.
  ```
  flightsDF.withColumn("Distance", new Column("Distance").cast(org.apache.spark.sql.types.DoubleType))
      .createOrReplaceTempView("flights")
  ```
  ```
  spark.sql("""SELECT ... ... ... """)
  ```
___
 
#### 6. Build/submit/run (optional)
  When your solutions return right answers try them write them out to output directory ```/root/workshop-spark/data/task2/output```. You can try to gather all your code into Flights.scala, build and submit it with the command bellow.
  ```
  spark-submit \
  --class org.workshop.Flights \
  --master spark://spark:7077 \
  --executor-memory 1G \
  --total-executor-cores 4 \
  target/scala-2.11/flights_2.11-1.0.jar \
  /root/workshop-spark/data/task2/airline-delays.csv \
  /root/workshop-spark/data/task2/output
  ```
If you run spark-submit command more than once you can get ```Output directory file:/root/workshop-spark/data/task2/output/airline-with-most-flight already exists```. You have to delete the ouput first.
