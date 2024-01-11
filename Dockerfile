FROM openjdk:21

EXPOSE 8000

ADD target/platform.jar platform-docker.jar

#WORKDIR /app

#COPY ./target/platform-0.0.1-SNAPSHOT.jar platform.jar

#COPY ./src/main/resources/application.properties /app/config/application.properties

ENTRYPOINT ["java", "-jar", "platform-docker.jar"]