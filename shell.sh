#!/bin/sh

mvn package
java -cp target/winter-1.0-SNAPSHOT.jar org.winterframework.MainController