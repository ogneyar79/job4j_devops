import com.github.spotbugs.snom.SpotBugsTask

plugins {
    checkstyle
    java
    jacoco
    alias(libs.plugins.com.github.spotbugs)
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.io.spring.dependency.management)
}

group = "ru.job4j.devops"
version = "1.0.0"

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

repositories {
	mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.spring.boot.starter.web)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.register<Zip>("zipJavaDoc") {
    group = "documentation" // Группа, в которой будет отображаться задача
    description = "Packs the generated Javadoc into a zip archive"
    dependsOn("javadoc") // Указываем, что задача зависит от выполнения javadoc
    from("build/docs/javadoc") // Исходная папка для упаковки
    archiveFileName.set("javadoc.zip") // Имя создаваемого архива
    destinationDirectory.set(layout.buildDirectory.dir("archives")) // Директория, куда будет сохранен архив
}

tasks.named<SpotBugsTask>("spotbugsMain") {
    reports.create("html") {
        required.set(true)
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/spotbugs.html"))
    }
}

tasks.register("checkJarSize") {
    group = "verification"
    description = "Checks the size of the generated JAR file."
    dependsOn("jar") // Задача зависит от сборки JAR
    doLast {
        val jarFile = file("build/libs/${project.name}-${project.version}.jar") // Путь к JAR-файлу
        if (jarFile.exists()) {
            val sizeInMB = jarFile.length() / (1024 * 1024) // Размер в мегабайтах
            if (sizeInMB > 5) {
                println("WARNING: JAR file exceeds the size limit of 5 MB. Current size: ${sizeInMB} MB")
            } else {
                println("JAR file is within the acceptable size limit. Current size: ${sizeInMB} MB")
            }
        } else {
            println("JAR file not found. Please make sure the build process completed successfully.")
        }
    }
}

tasks.test {
    finalizedBy("spotbugsMain") // Ленивый доступ к задаче, без необходимости явной конфигурации
}


