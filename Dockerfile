FROM adoptopenjdk/openjdk11:alpine-jre

#ANTES DE COMPIAR DEBE SER EJECUTADO EN EL RUNNER.
ARG JAR_FILE=target/org-trading-webhook-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app

RUN apk add tzdata && cp /usr/share/zoneinfo/America/Santiago /etc/localtime \
                       && echo "America/Santiago" > /etc/timezone \
                       && apk del tzdata

COPY ${JAR_FILE} app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","app.jar"]