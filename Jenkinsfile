pipeline {
    agent { label 'agent1' }

    tools {
        git 'Default'
    }

    environment {
        BOT_TOKEN = credentials('8156463082:AAEdc3TNbRQQnEQDw42rCX2H1Fzltso0dm0')
        CHAT_ID = '422946316'
    }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                }
            }
        }
        stage('Checkstyle Main') {
            steps {
                script {
                    sh './gradlew checkstyleMain'
                }
            }
        }
        stage('Checkstyle Test') {
            steps {
                script {
                    sh './gradlew checkstyleTest'
                }
            }
        }
        stage('Compile') {
            steps {
                script {
                    sh './gradlew compileJava'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh './gradlew test'
                }
            }
        }
        stage('JaCoCo Report') {
            steps {
                script {
                    sh './gradlew jacocoTestReport'
                }
            }
        }
        stage('JaCoCo Verification') {
            steps {
                script {
                    sh './gradlew jacocoTestCoverageVerification'
                }
            }
        }
    }
    post {
        always {
            script {
                def buildStatus = currentBuild.currentResult
                def buildInfo = """
                    📌 Сборка: ${env.JOB_NAME} #${env.BUILD_NUMBER}
                    🚀 Статус: ${buildStatus}
                    ⏱ Длительность: ${currentBuild.durationString.replace(' and counting', '')}
                """
                try {
                    telegramSend(
                        chatId: env.CHAT_ID,
                        message: buildInfo
                    )
                    echo "Сообщение отправлено через Telegram plugin"
                } catch (Exception e) {
                    echo "Не удалось отправить через плагин, пробуем curl: ${e.getMessage()}"
                    sh """
                        curl -s -X POST "https://api.telegram.org/bot${env.BOT_TOKEN}/sendMessage" \
                            -d chat_id="${env.CHAT_ID}" \
                            -d text="${buildInfo}"
                    """
                }
            }
        }
    }
}