FROM eclipse-temurin:11-jdk-alpine
LABEL COMPANY="B&G Digital"
LABEL MAINTAINER="maodo.diagne@bgdigit-all.com"
LABEL APPLICATION="Anpej Gateway"

ENV EUREKA_SERVER=http://localhost:8761/eureka \
    REDIS_HOST=localhost \


WORKDIR /app/

#Copy war files to tomvat
ADD  ./target/gateway-0.0.1-SNAPSHOT.jar /app/

VOLUME /var/opt/project

CMD ["java", "-Xmx200m", "-jar", "/app/gateway-0.0.1-SNAPSHOT.jar"]

EXPOSE 9090