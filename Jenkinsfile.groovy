pipeline {
    agent any

    stages {
        stage('Stage 1') {
            steps {
                echo 'Hello world!'
                sh 'ls -l'
            }
        }
        stage('Stage 2') {
            steps {
                dir('ktor-item-order-service') {
                    sh 'ls -l'
                }
            }
        }
        stage('docker') {
            agent {
                docker {
                    image 'node:18.18.0-alpine3.18'
                }
            }

            steps {
                sh 'node --version'
            }
        }
    }
}