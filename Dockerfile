FROM openjdk:17-alpine

# Set the timezone to KST
RUN apk add --no-cache tzdata
ENV TZ=Asia/Seoul

WORKDIR /app

COPY ./jar/jarvos-0.0.1-SNAPSHOT.jar /jar/application.jar

ENTRYPOINT ["java", "-jar", "/jar/application.jar"]