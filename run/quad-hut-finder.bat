:: this is a dumb script because I don't know batch stuff as well as I know bash
:: -- this script has to be run from the project root (ie with project root as your pwd)
gradlew.bat clean build && java -jar quad-hut-finder/build/libs/quad-hut-finder-1.0.0.jar
