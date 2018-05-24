#!/bin/bash
./gradlew clean build -Prelease uploadArchives closeAndPromoteRepository
