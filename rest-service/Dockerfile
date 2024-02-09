FROM openjdk:21
ARG JAR_FILE=target/libraryOnlineApiService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar", "-Dspring.profiles.active=docker"]