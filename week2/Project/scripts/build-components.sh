#!/bin/sh

mvn -f ../frontend/pom.xml clean package
mvn -f ../books/pom.xml clean package
mvn -f ../authors/pom.xml clean package
