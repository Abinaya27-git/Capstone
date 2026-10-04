#!/bin/bash
# AbinayaMart run script (Linux/Mac)
# Put mysql-connector-j jar inside lib/ before running
mkdir -p lib out
echo "Compiling AbinayaMart..."
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -cp "lib/*" -d out @sources.txt
if [ $? -ne 0 ]; then
  echo "COMPILE FAILED."
  exit 1
fi
rm sources.txt
echo "Starting AbinayaMart..."
java -cp "out:lib/*" com.abinayamart.Main
