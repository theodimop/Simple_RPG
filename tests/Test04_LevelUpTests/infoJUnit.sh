#!/bin/bash
JUNITPATH="$TESTDIR"
TESTPACKAGE="enhancements"
TESTRUNNER="PlayerTestSuiteRunner"

if [ -d "../p2-oop-enhancement-tests/$TESTPACKAGE" ]; then
	echo "ERROR: directoy ../p2-oop-enhancement-tests/$TESTPACKAGE already exists. Please remove or rename and re-run stacscheck"
	exit 1
else 
	mkdir -p ../p2-oop-enhancement-tests/$TESTPACKAGE
	cp "$JUNITPATH"/$TESTPACKAGE/*.java ../p2-oop-enhancement-tests/$TESTPACKAGE/

	javac -cp "$JUNITPATH/junit.jar":"$JUNITPATH/hamcrest.jar":. ../p2-oop-enhancement-tests/$TESTPACKAGE/*.java

	java -cp "$JUNITPATH/junit.jar":"$JUNITPATH/hamcrest.jar":../p2-oop-enhancement-tests:.  $TESTPACKAGE.$TESTRUNNER
fi

