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
                    script{
                                        def properties = readProperties file: 'src/main/java/Utility/config.properties'

                                                                                // Access the properties using their keys
                                                                                def testId = properties['performance_Test_Id']
                                                                                def api_key = properties['blazemeter_api_key']
                                                                                def api_secret = properties['blazemeter_api_secret']}

                    echo 'performance'
//                         bat "curl 'https://a.blazemeter.com/api/v4/tests/12584787/start?delayedStart=false'     -X POST     -H 'Content-Type: application/json'     --user '2c0efbe3c64b6c60d864aea9:828ae67a0b1c2f5f9d86dc952ef59d75b3d8f062467973372d4560fd0d4bc6b273b731db' \""
                           bat 'curl "https://a.blazemeter.com/api/v4/tests/${testId}/start?delayedStart=false"     -X POST     -H "Content-Type: application/json"     --user "${api_key}:${api_secret}"\''

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