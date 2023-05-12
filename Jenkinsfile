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
//                        sh '''
//                                           curl -X POST \
//                                           -H "Content-Type: application/json" \
//                                           -H "Authorization: Bearer 2c0efbe3c64b6c60d864aea9:0c24c98761b5d1597d5cc05ae74c57b97cabd76890ba6973bd205e3f0adea94689e1da9e" \
//                                           -d '{"testId":"12584787"}' \
//                                           "https://a.blazemeter.com/api/latest/tests/YOUR_TEST_ID/start"
//                                       '''

                    }
                }
        stage('UI-Automation-Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}