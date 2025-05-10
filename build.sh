#!/bin/bash

# Загрузить переменные из .env
export $(grep -v '^#' .env | xargs)

# Создать временный секретный файл
cat > gradle-cache-credentials.properties <<EOF
systemProp.gradle.cache.remote.username=${GRADLE_REMOTE_CACHE_USERNAME}
systemProp.gradle.cache.remote.password=${GRADLE_REMOTE_CACHE_PASSWORD}
EOF

# Собрать образ с секретом
DOCKER_BUILDKIT=1 docker build \
  --secret id=gradle_credentials,src=gradle-cache-credentials.properties \
  --build-arg GRADLE_REMOTE_CACHE_URL=$GRADLE_REMOTE_CACHE_URL \
  --build-arg GRADLE_REMOTE_CACHE_PUSH=$GRADLE_REMOTE_CACHE_PUSH \
  -t job4j_devops .

# Удалить временный файл
rm gradle-cache-credentials.properties
