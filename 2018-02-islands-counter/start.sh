#!/bin/bash
cd "$(dirname "$0")/code"

mvn clean
mvn compile
mvn jar:jar
java -jar target/islands-counter-1.0.jar src/test/resources/map.txt
