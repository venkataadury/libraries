#!/bin/bash

locn=`pwd`
for fol in `$locn/sd $locn`
do
	cd $fol
	javac -cp $locn *.java
	for subfol in `$locn/sd .`
	do
		cd $subfol
		javac -cp $locn *.java 1> compile_java.log 2> errors_java.log
		cd ..
	done
	cd ..
done
