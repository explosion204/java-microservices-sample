#!/bin/sh

echo "JVM Options: ${JVM_OPTIONS}"
echo "JAR file: ${JAR_FILE}"

java ${JVM_OPTIONS} -jar ${JAR_FILE} ${OPTIONALS}