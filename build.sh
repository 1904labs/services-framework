#!/bin/bash
./gradlew clean build installDist assembleDistBin assembleDistLibs assembleDistConfig publishToMavenLocal -x findbugsMain -x findbugsTest -x checkStyleMain -x checkStyleTest -x test -x signArchives -x uploadArchives
