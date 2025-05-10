# syntax=docker/dockerfile:1.4
FROM gradle:8.11.1-jdk21 AS builder

RUN mkdir job4j_devops
WORKDIR /job4j_devops

COPY . .

RUN --mount=type=secret,id=gradle_credentials \
    cp /run/secrets/gradle_credentials gradle-cache-credentials.properties && \
    gradle --no-daemon \
      -Dorg.gradle.jvmargs="-Djava.util.logging.config.file=gradle-cache-credentials.properties" \
      -Dgradle.cache.remote.url=$GRADLE_REMOTE_CACHE_URL \
      -Dgradle.cache.remote.push=$GRADLE_REMOTE_CACHE_PUSH \
      build && \
    rm gradle-cache-credentials.properties

RUN jar xf build/libs/DevOps-1.0.0.jar

RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    build/libs/DevOps-1.0.0.jar > deps.info

RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /slim-jre

FROM debian:bookworm-slim
ENV JAVA_HOME=/user/java/jdk21
ENV PATH=$JAVA_HOME/bin:$PATH

COPY --from=builder /slim-jre $JAVA_HOME
COPY --from=builder /job4j_devops/build/libs/DevOps-1.0.0.jar .
ENTRYPOINT ["java", "-jar", "DevOps-1.0.0.jar"]
