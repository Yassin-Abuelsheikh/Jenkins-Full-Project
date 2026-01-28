pipeline {
    agent any

    environment {
        DOCKERHUB_REPO = "yassinahmed10/spring-boot-app"
        IMAGE_TAG      = "${BUILD_NUMBER}"

        AWS_REGION     = "us-east-1"
        AWS_ACCOUNT_ID = "328911924204"
        ECR_REPO       = "spring-boot-app"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Tests') {
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
                          mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                          -Dsonar.ws.timeout=120
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        /* ================= OWASP Dependency Check ================= */

        stage('OWASP Dependency Check') {
            steps {
                dir('spring-boot-app') {
                    withCredentials([string(credentialsId: 'nvd-api-key', variable: 'NVD_API_KEY')]) {
                        dependencyCheck additionalArguments: """
                          --scan .
                          --nvdApiKey ${NVD_API_KEY}
                          --format XML
                          --out target
                          --failOnCVSS 9
                        """, odcInstallation: 'OWASP-Dependency-Check'
                    }
                }
            }
        }

        stage('Publish Artifact to Nexus') {
            steps {
                dir('spring-boot-app') {
                    sh 'mvn deploy -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('spring-boot-app') {
                    sh 'docker build -t $DOCKERHUB_REPO:$IMAGE_TAG .'
                }
            }
        }

        /* ================= Trivy Image Scan ================= */

        stage('Trivy Image Scan (CRITICAL only)') {
            steps {
                sh '''
                  docker run --rm \
                  -v /var/run/docker.sock:/var/run/docker.sock \
                  aquasec/trivy:latest image \
                  --severity CRITICAL \
                  --timeout 10m \
                  --scanners vuln \
                  --no-progress \
                  --exit-code 1 \
                  $DOCKERHUB_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Push Docker Image to Docker Hub') {
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

        stage('Push Docker Image to AWS ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-ecr-creds'
                ]]) {
                    sh '''
                      aws ecr get-login-password --region $AWS_REGION \
                      | docker login --username AWS --password-stdin \
                      $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com

                      docker tag $DOCKERHUB_REPO:$IMAGE_TAG \
                      $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG

                      docker push \
                      $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                    '''
                }
            }
        }
    }

    post {
        failure {
            emailext(
                subject: "Jenkins Pipeline Failed: ${JOB_NAME} #${BUILD_NUMBER}",
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
            echo "Pipeline completed successfully ✅"
        }
    }
}

