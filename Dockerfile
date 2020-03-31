FROM maven:3.3.9-jdk-8

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY ./target/SharedPhotosMediaService-0.0.1-SNAPSHOT.jar /opt/app

EXPOSE 8080

CMD java -jar SharedPhotosMediaService-0.0.1-SNAPSHOT.jar