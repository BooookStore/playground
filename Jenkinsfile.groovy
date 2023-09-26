pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'Hello world!'
            }
        }
        stage('Stage 2') {
            steps {
                echo 'Hello world!'
            }
        }
        stage('SCM') {
            node {
                checkout scm
            }

            steps {
                echo 'checkout scm'
            }
        }
    }
}