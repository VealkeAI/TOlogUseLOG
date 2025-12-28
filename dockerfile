FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /opt/app

COPY gradlew gradlew.bat build.gradle settings.gradle /opt/app/
COPY gradle /opt/app/gradle
COPY src /opt/app/src

RUN chmod +x gradlew
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /opt/app

COPY --from=builder /opt/app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
