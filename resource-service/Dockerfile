FROM openjdk:16-alpine

ENV JAR_FILE /opt/service.jar

ADD build/libs/resource-service.jar ${JAR_FILE}
ADD docker/entrypoint.sh /opt/entrypoint.sh

ENTRYPOINT ["/opt/entrypoint.sh"]