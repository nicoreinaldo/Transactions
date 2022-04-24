FROM openjdk:17-jdk-slim-buster

COPY ./target/transaction-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch transaction-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","transaction-0.0.1-SNAPSHOT.jar"]