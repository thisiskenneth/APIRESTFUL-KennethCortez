FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/test-0.0.1-SNAPSHOT.jar ./test-0.0.1-SNAPSHOT.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","test-0.0.1-SNAPSHOT.jar"]