#!/bin/bash
./gradlew clean -Ptest findbugsMain findbugsTest checkStyleMain checkStyleTest test
