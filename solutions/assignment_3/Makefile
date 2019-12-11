# Modified by: Michael Valdron
# Date: Dec 2019

scalac = scalac -d target -sourcepath src
scala = scala -classpath target

compile:
	mkdir -p ./target
	$(scalac) src/fileio/CsvIO.scala
	$(scalac) src/part1.scala
	$(scalac) src/part2.scala
	$(scalac) src/part3.scala

run: part1 part2 part3

part1:
	$(scala) part1 schedule.csv

part2:
	$(scala) part2 schedule.csv csci3055u csci4100u

part3:
	$(scala) part3 schedule.csv M 10:00 12:00

clean:
	rm -rf ./target

