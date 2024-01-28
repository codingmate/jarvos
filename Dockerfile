FROM openjdk:17-alpine

# Set the timezone to KST
RUN apk add --no-cache tzdata
ENV TZ=Asia/Seoul

WORKDIR /app

COPY ./build/libs/jarvos-0.0.1-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
