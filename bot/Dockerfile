FROM maven:3.8.3-amazoncorretto-17 as builder

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn clean package -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true

FROM amazoncorretto:17

COPY --from=builder target/bot-0.0.1-SNAPSHOT.jar bot-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:8082,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","/bot-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
