#!/bin/bash
./build.sh
./gradlew publishToMavenLocal uploadArchives -x findbugsMain -x findbugsTest -x checkStyleMain -x checkStyleTest -x test
