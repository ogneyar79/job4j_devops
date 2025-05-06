FFROM gradle:8.11.1-jdk21

 ARG GRADLE_REMOTE_CACHE_URL
 ARG GRADLE_REMOTE_CACHE_PUSH
 ARG GRADLE_REMOTE_CACHE_USERNAME
 ARG GRADLE_REMOTE_CACHE_PASSWORD

 RUN mkdir job4j_devops
 WORKDIR /job4j_devops
 COPY . .

 # Используйте переменные в процессе сборки
 RUN gradle clean build -x test

 EXPOSE 8080
 ENTRYPOINT ["java", "-jar", "build/libs/DevOps-1.0.0.jar"]
