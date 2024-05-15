FROM amazoncorretto:21-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY target/realworld-0.0.1-SNAPSHOT.jar /app/realworld.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "realworld.jar"]