FROM eclipse-temurin:19-jdk-alpine
VOLUME /tmp
COPY "target/sbc-1.0.0.jar" app.jar
ENTRYPOINT ["java","-jar","/app.jar"]