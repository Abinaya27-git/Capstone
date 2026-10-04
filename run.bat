@echo off
REM AbinayaMart run script (Windows)
REM Put mysql-connector-j jar inside lib\ before running
if not exist lib mkdir lib
if not exist out mkdir out
echo Compiling AbinayaMart...
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -cp "lib\*" -d out @sources.txt
if errorlevel 1 (
  echo COMPILE FAILED. Check errors above.
  exit /b 1
)
del sources.txt
echo Starting AbinayaMart...
java -cp "out;lib\*" com.abinayamart.Main
