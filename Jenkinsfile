pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
         stage('Performance Test') {
                    steps {
                        echo 'Performace...'
                    }
                }
        stage('UI-Automation-Test') {
            steps {
                //sh 'mvn clean test'
                bat 'mvn clean test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}