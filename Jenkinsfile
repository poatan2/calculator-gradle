pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub-access-token'
        DOCKER_IMAGE = 'goguma1/calculator-gradle'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/poatan2/calculator-gradle.git'
            }
        }

        stage('Permission') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

         stage('Compile') {
            steps {
                sh './gradlew compileJava'
            }
        }

        stage('Unit Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Code Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
                sh './gradlew jacocoTestReport'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh './gradlew sonar'
                }
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build -x test'
                sh 'cp $(ls build/libs/*.jar | grep -v plain) app.jar'
            }
        }


        stage('Image Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:latest ."
            }
        }

        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                        docker push ${DOCKER_IMAGE}:latest
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['ec2-ssh-key']) {
                    sh '''
                        scp -o StrictHostKeyChecking=no start_server.sh ubuntu@43.200.176.52:~/deploy/start-server.sh
                        ssh -o StrictHostKeyChecking=no ubuntu@43.200.176.52 'chmod +x ~/deploy/start-server.sh && ~/deploy/start-server.sh'
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Build & Deploy Success!'
        }
        failure {
            echo 'Build or Deploy Failed'
        }
    }
}