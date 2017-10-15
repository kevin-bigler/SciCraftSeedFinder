#!/usr/bin/env bash
set -e

cd $(dirname "$0")
cd ..

./gradlew clean build && java -jar quad-hut-finder/build/libs/quad-hut-finder-1.0.0.jar
