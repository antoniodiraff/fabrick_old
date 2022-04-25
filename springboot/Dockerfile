#FROM amazoncorretto:11.0.12-alpine3.14
#VOLUME /tmp
#EXPOSE 8080
#ADD esercizio-0.0.1-SNAPSHOT-docker.jar esercizio.jar
#ENTRYPOINT ["java","-jar","esercizio.jar"]

FROM maven:3.8.2-jdk-11
WORKDIR /esercizio-app
COPY . .
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run