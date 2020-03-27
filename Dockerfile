FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG jarFile=target/WebServiceTest-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app
RUN mkdir -p /app/log
RUN mkdir -p /app/error
RUN mkdir -p /app/info
COPY ${jarFile} app
EXPOSE 6060
ENTRYPOINT ["java","-jar","/app/WebServiceTest-0.0.1-SNAPSHOT.jar"]
