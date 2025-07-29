FROM openjdk:22

COPY target/app.jar app/app.jar

ENTRYPOINT ["sh", "-c", "sleep 18 && java -jar app/app.jar"]
