pipeline {
    agent any

    tools {
        maven 'maven'   
    }

    environment {
        DOCKERHUB_REPO = "yassinahmed10/spring-boot-app"
        IMAGE_TAG      = "${BUILD_NUMBER}"
        NEXUS_REPO_URL = "http://192.168.192.130:8081/repository/nexus-releases1/"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Yassin-Abuelsheikh/Jenkins-Full-Project.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                      mvn clean verify \
                      org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar
                    '''
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
                withCredentials([usernamePassword(
                    credentialsId: 'nexus-creds',
                    usernameVariable: 'NEXUS_USER',
                    passwordVariable: 'NEXUS_PASS'
                )]) {
                    sh '''
                      mvn deploy \
                      -Dnexus.username=$NEXUS_USER \
                      -Dnexus.password=$NEXUS_PASS
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                  docker build -t $DOCKERHUB_REPO:$IMAGE_TAG .
                '''
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
                subject: " Jenkins Pipeline Failed: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
                Pipeline Failed 

                Job: ${JOB_NAME}
                Build Number: ${BUILD_NUMBER}
                Build URL: ${BUILD_URL}
                """,
                to: "yassinabuelsheikh@gmail.com"
            )
        }

        success {
            echo "Pipeline completed successfully"
        }
    }
}

