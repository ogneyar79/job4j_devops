import java.util.*

rootProject.name = "DevOps"

// Загружаем .env (только локально)
val envFile = file(".env")
if (envFile.exists()) {
    val props = Properties()
    props.load(envFile.inputStream())
    props.forEach { key, value ->
        System.setProperty(key.toString(), value.toString())
    }
}

// Универсальная функция: сначала System properties, потом переменные среды
fun envOrProp(key: String): String =
    System.getProperty(key) ?: System.getenv(key) ?: error("Missing environment variable: $key")

buildCache {
    remote<HttpBuildCache> {
        url = uri(envOrProp("GRADLE_REMOTE_CACHE_URL"))
        isAllowInsecureProtocol = true
        isAllowUntrustedServer = true
        isPush = envOrProp("GRADLE_REMOTE_CACHE_PUSH").toBoolean()
        credentials {
            username = envOrProp("GRADLE_REMOTE_CACHE_USERNAME")
            password = envOrProp("GRADLE_REMOTE_CACHE_PASSWORD")
        }
    }
}
