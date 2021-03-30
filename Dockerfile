FROM maven:3.6.3-jdk-11 AS Build
COPY src /home/build/src
COPY pom.xml /home/build
RUN mvn -f /home/build/pom.xml dependency:go-offline
RUN mvn -f /home/build/pom.xml clean install

FROM openjdk:11-jre-slim
COPY --from=Build /home/build/target/web-crawler-0.0.1.jar /usr/local/web-crawler/api/

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /usr/local/web-crawler/api/

ENTRYPOINT [ "java" , "-jar", "web-crawler-0.0.1.jar" ]