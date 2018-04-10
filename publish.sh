#!/bin/bash
./gradlew publishToMavenLocal uploadArchives -PnexusUsername="${SONATYPE_USERNAME}" -PnexusPassword="${SONATYPE_PASSWORD}" -x findbugsMain -x findbugsTest -x checkStyleMain -x checkStyleTest -x test
