pipeline {
    agent { label 'agent1' }

    tools {
        git 'Default'
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
            def buildInfo = """\
<b>Build number:</b> ${currentBuild.number}
<b>Status:</b> ${currentBuild.currentResult}
<b>Started at:</b> ${new Date(currentBuild.startTimeInMillis ?: System.currentTimeMillis())}
<b>Duration:</b> ${currentBuild.durationString}

<a href="${env.BUILD_URL}">Открыть билд в Jenkins</a>
"""
            telegramSend(chatId: '422946316', message: buildInfo, parseMode: 'HTML')
        }
    }
}
}
