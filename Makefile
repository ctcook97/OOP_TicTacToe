.DEFAULT_GOAL := all

all: compile instructions permissions

compile:
	javac -d bin src/course/oop/*/*.java

instructions:
	echo 'java -cp ./bin course.oop.main.$$1' > run

permissions:
	chmod 777 run