pipeline {
    agent any

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

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['ec2-ssh-key']) {
                    sh '''
                        scp -o StrictHostKeyChecking=no build/libs/*.jar ubuntu@43.200.176.52:~/deploy/
                        ssh -o StrictHostKeyChecking=no ubuntu@43.200.176.52 'cd ~/deploy && ./start_server.sh'
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