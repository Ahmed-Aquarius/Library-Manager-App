FROM openjdk:latest

COPY target/app.jar /app/app.jar

ENTRYPOINT ["java", "-jar"]

CMD ["app.jar"]
