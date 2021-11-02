FROM maven:3-openjdk-11 as builder
WORKDIR /tmp
ADD pom.xml ./
ADD src /tmp/src
RUN mvn -f pom.xml clean package

FROM openjdk:12-alpine
COPY --from=builder /tmp/target/course-servicemesh-forntend-0.0.1.jar app.jar
EXPOSE 8080
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
