### Introduction
This program is to solve the water jug riddle (AKA The Die Hard Riddle).  You have a 3-gallon and a 5-gallon jug that you can fill from a fountain of water. The problem is to fill one of the jugs with exactly 4 gallons of water.

This program will print out most proficient operations to get the the target volume of water.  Note that
the program can solve the target up to total volume of both jugs. 

### How to run the program
#### Prerequisites:

1. jdk 11
2. Apache Maven 3.6.0

#### Run with Maven
Go to folder `water-jug-riddle`, then run the following command:

    mvn compile exec:java -Dexec.mainClass="com.zhentao.riddle.WaterJugRiddle"

Then follow the instructions to enter required info. 

#### Run without Maven
Go to folder `water-jug-riddle/src/main/java`, compile the source code:

    javac com/zhentao/riddle/WaterJugRiddle.java
    java com.zhentao.riddle.WaterJugRiddle

Then follow the instructions to enter required info.

