FROM openjdk:11
ADD build/libs/rate-exchange-0.0.1-SNAPSHOT.jar rate-exchange-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rate-exchange-0.0.1-SNAPSHOT.jar"]
