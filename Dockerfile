FROM openjdk:22

COPY target/app.jar app/app.jar

ENTRYPOINT ["sh", "-c", "sleep 15 && java -jar app/app.jar"]
