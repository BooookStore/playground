FROM openjdk:17

WORKDIR /app

COPY ./ktor-hello-world .

RUN ./gradlew buildFatJar

ENTRYPOINT [ "java", "-jar", "build/libs/com.example.ktor-hello-world-all.jar" ]