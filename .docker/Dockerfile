FROM eclipse-temurin:17-jammy as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jammy
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
LABEL component=application
ENV SPRING_MAIN_BANNER-MODE=off
ENV SPRING_PROFILES_ACTIVE=container
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
