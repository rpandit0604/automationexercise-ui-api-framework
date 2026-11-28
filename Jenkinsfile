// Declarative Jenkins Pipeline for Automation Exercise Hybrid UI & API Framework

pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    // Build parameters selectable from the Jenkins UI ("Build with Parameters")
    parameters {
        choice(
            name: 'SUITE_XML',
            choices: ['smoke.xml', 'api-suite.xml', 'regression.xml'],
            description: 'Select the TestNG XML suite to execute'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run browser tests in headless mode'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Automation Suite') {
            steps {
                script {
                    def cmd = "mvn clean test -DsuiteXmlFile=${params.SUITE_XML} -Dheadless=${params.HEADLESS}"
                    if (isUnix()) {
                        sh cmd
                    } else {
                        bat cmd
                    }
                }
            }
        }
    }

    // Publish JUnit XML test results and archive Surefire & ExtentReports artifacts
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**, reports/**', allowEmptyArchive: true
        }
        success {
            echo "Test suite ${params.SUITE_XML} passed successfully."
        }
        failure {
            echo "Test suite ${params.SUITE_XML} failed. Check archived ExtentReports and Surefire logs."
        }
    }
}