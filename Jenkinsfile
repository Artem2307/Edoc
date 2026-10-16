pipeline {
    agent any

    parameters {
        choice(
            name: 'RUN_MODE',
            choices: ['ALL', 'CLASS', 'METHOD', 'FAILED'],
            description: 'ALL - all tests; CLASS - one class; METHOD - one method; FAILED - retry failures from previous build'
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

        stage('Prepare failed tests') {
            when {
                expression { params.RUN_MODE == 'FAILED' }
            }
            steps {
                script {
                    def previousNumber = currentBuild.number - 1

                    if (previousNumber < 1) {
                        error 'No previous build exists.'
                    }

                    copyArtifacts(
                        projectName: env.JOB_NAME,
                        selector: specific("${previousNumber}"),
                        filter: 'target/surefire-reports/*.xml',
                        target: 'previous-reports',
                        optional: true
                    )

                    powershell '''
                        $files = Get-ChildItem `
                            "previous-reports/target/surefire-reports/*.xml" `
                            -ErrorAction SilentlyContinue

                        $failed = @()

                        foreach ($file in $files) {
                            [xml]$report = Get-Content $file.FullName

                            foreach ($test in $report.SelectNodes("//testcase")) {
                                if ($test.SelectSingleNode("failure") -or
                                    $test.SelectSingleNode("error")) {

                                    $className = $test.GetAttribute("classname")
                                    $testName = $test.GetAttribute("name")
                                    $methodName = ($testName -split '[\\(\\[]')[0]

                                    if ($className -and $methodName) {
                                        $failed += "$className#$methodName"
                                    }
                                }
                            }
                        }

                        $failed = @($failed | Sort-Object -Unique)

                        if ($failed.Count -eq 0) {
                            Write-Error "No failed tests found in the previous build."
                            exit 1
                        }

                        $failed | Set-Content "failed-tests.txt"
                        Write-Host "Tests to rerun:"
                        $failed | ForEach-Object { Write-Host $_ }
                    '''

                    def failedTests = readFile('failed-tests.txt')
                    .readLines()
                    .collect { it.trim() }
                    .findAll { it }

                    if (failedTests.isEmpty()) {
                        error 'Failed test list is empty.'
                    }

                    env.FAILED_TEST_FILTER = failedTests.join(',')
                }
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

                    } else if (params.RUN_MODE == 'FAILED') {
                        testFilter = env.FAILED_TEST_FILTER
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

        success {
            echo 'Tests completed successfully.'
        }

        failure {
            echo 'Tests failed. Check Console Output and Surefire reports.'
        }
    }
}
