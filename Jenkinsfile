pipeline {
    agent any

    parameters {
        choice(
            name: 'RUN_MODE',
            choices: ['ALL', 'CLASS', 'METHOD'],
            description: 'ALL — все тесты; CLASS — класс; METHOD — метод'
        )

        string(
            name: 'TEST_CLASS',
            defaultValue: '',
            description: 'Имя класса, например AddDocumentTest'
        )

        string(
            name: 'TEST_METHOD',
            defaultValue: '',
            description: 'Имя метода теста, например uploadDocument'
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
                        if (!(params.TEST_CLASS ==~
                                /[A-Za-z_$][A-Za-z0-9_$]*(\.[A-Za-z_$][A-Za-z0-9_$]*)*/)) {
                            error 'Укажи корректное имя класса в TEST_CLASS'
                        }

                        testFilter = params.TEST_CLASS

                    } else if (params.RUN_MODE == 'METHOD') {
                        if (!(params.TEST_CLASS ==~
                                /[A-Za-z_$][A-Za-z0-9_$]*(\.[A-Za-z_$][A-Za-z0-9_$]*)*/)) {
                            error 'Укажи корректное имя класса в TEST_CLASS'
                        }

                        if (!(params.TEST_METHOD ==~
                                /[A-Za-z_$][A-Za-z0-9_$]*/)) {
                            error 'Укажи корректное имя метода в TEST_METHOD'
                        }

                        testFilter =
                            "${params.TEST_CLASS}#${params.TEST_METHOD}"
                    }

                    if (isUnix()) {
                        def command = 'mvn -B clean test ' +
                            '-Dsurefire.rerunFailingTestsCount=1'

                        if (testFilter) {
                            command += " '-Dtest=${testFilter}'"
                        }

                        sh command
                    } else {
                        def command = 'mvn -B clean test ' +
                            '-Dsurefire.rerunFailingTestsCount=1'

                        if (testFilter) {
                            command += " \"-Dtest=${testFilter}\""
                        }

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

            archiveArtifacts(
                artifacts: 'target/surefire-reports/**',
                allowEmptyArchive: true
            )
        }

        success {
            echo 'Тестирование завершено успешно.'
        }

        failure {
            echo 'Сборка завершилась с ошибкой. Проверь Console Output и отчёт тестов.'
        }
    }
}