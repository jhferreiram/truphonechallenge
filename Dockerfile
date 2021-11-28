FROM adoptopenjdk/openjdk11 AS build

ENV APP_HOME /app
WORKDIR $APP_HOME

COPY . $APP_HOME/

RUN $APP_HOME/mvnw clean package -Dmaven.test.skip=true