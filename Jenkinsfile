pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = "yassinahmed10/spring-boot-app"
        IMAGE_TAG      = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                dir('spring-boot-app') {
                    sh 'mvn clean verify'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('spring-boot-app') {
                    withSonarQubeEnv('sonarqube') {
                        sh '''
                          mvn clean verify \
                          org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Publish Artifact to Nexus') {
            steps {
                dir('spring-boot-app') {
                    withCredentials([usernamePassword(
                        credentialsId: 'nexus-creds',
                        usernameVariable: 'NEXUS_USER',
                        passwordVariable: 'NEXUS_PASS'
                    )]) {
                        sh '''
                          mvn deploy
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('spring-boot-app') {
                    sh '''
                      docker build -t $DOCKERHUB_REPO:$IMAGE_TAG .
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(
                    credentialsId: 'dockerhub-creds',
                    variable: 'DOCKER_TOKEN'
                )]) {
                    sh '''
                      echo $DOCKER_TOKEN | docker login -u yassinahmed10 --password-stdin
                      docker push $DOCKERHUB_REPO:$IMAGE_TAG
                    '''
                }
            }
        }
    }

    post {
        failure {
            emailext(
                subject: "❌ Jenkins Pipeline Failed: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Pipeline Failed ❌

Job: ${JOB_NAME}
Build Number: ${BUILD_NUMBER}
Build URL: ${BUILD_URL}
""",
                to: "yassinabuelsheikh@gmail.com"
            )
        }

        success {
            echo "✅ Pipeline completed successfully"
        }
    }
}

