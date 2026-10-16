pipeline {
    agent any

    parameters {
        choice(
            name: 'RUN_MODE',
            choices: ['ALL', 'CLASS', 'METHOD'],
            description: 'ALL - all tests; CLASS - one class; METHOD - one method'
        )

        string(
            name: 'TEST_CLASS',
            defaultValue: '',
            description: 'Test class, e.g. AddDocumentTest'
        )

        string(
            name: 'TEST_METHOD',
            defaultValue: '',
            description: 'Test method name'
        )
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                script {
                    def testFilter = ''

                    if (params.RUN_MODE == 'CLASS') {
                        if (!params.TEST_CLASS?.trim()) {
                            error 'Enter TEST_CLASS'
                        }

                        testFilter = params.TEST_CLASS.trim()

                    } else if (params.RUN_MODE == 'METHOD') {
                        if (!params.TEST_CLASS?.trim()) {
                            error 'Enter TEST_CLASS'
                        }

                        if (!params.TEST_METHOD?.trim()) {
                            error 'Enter TEST_METHOD'
                        }

                        testFilter = params.TEST_CLASS.trim() +
                            '#' + params.TEST_METHOD.trim()
                    }

                    def command = 'mvn -B clean test ' +
                        '-Dsurefire.rerunFailingTestsCount=1'

                    if (testFilter) {
                        command += ' "-Dtest=' + testFilter + '"'
                    }

                    if (isUnix()) {
                        sh command
                    } else {
                        bat command
                    }
                }
            }
        }
    }

    post {
        always {
            junit(
                testResults: 'target/surefire-reports/*.xml',
                allowEmptyResults: true,
                skipPublishingChecks: true
            )

            allure([
                    includeProperties: false,
                    results: [[path: 'target/allure-results']]
                ])

            archiveArtifacts(
                artifacts: 'target/surefire-reports/**',
                allowEmptyArchive: true
            )
        }
    }

        success {
            echo 'Tests completed successfully.'
        }

        failure {
            echo 'Tests failed. Check Console Output and Surefire reports.'
        }
    }
