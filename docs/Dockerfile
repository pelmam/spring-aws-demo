FROM openjdk:8-jdk-alpine
expose 8080
ARG MY_FILES=*.jar
COPY ${MY_FILES} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]