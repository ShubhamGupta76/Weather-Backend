pipeline {
    agent any

    // Environment variables for the pipeline
    environment {
        // Application name and version
        APP_NAME = 'weather-App'
        DOCKER_IMAGE_NAME = 'weather-app'
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
        
        // Java and Maven configuration
        JAVA_HOME = tool name: 'JDK-17', type: 'jdk'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        
        // Maven settings
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=256m'
    }

    // Pipeline options
    options {
        // Discard old builds to save disk space
        buildDiscarder(logRotator(numToKeepStr: '10'))
        
        // Add timestamps to console output
        timestamps()
        
        // Retry build on failure (optional)
        retry(2)
        
        // Timeout for the entire pipeline (30 minutes)
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        // Stage 1: Checkout source code from GitHub
        stage('Checkout') {
            steps {
                script {
                    echo '========================================'
                    echo 'Stage: Checkout Source Code'
                    echo '========================================'
                }
                
                // Checkout code from GitHub
                checkout scm
                
                script {
                    // Display repository information
                    bat """
                        echo Repository: ${env.GIT_URL ?: 'N/A'}
                        echo Branch: ${env.GIT_BRANCH ?: 'N/A'}
                        echo Commit: ${env.GIT_COMMIT ?: 'N/A'}
                    """
                }
            }
        }

        // Stage 2: Validate Java version and Maven setup
        stage('Environment Setup') {
            steps {
                script {
                    echo '========================================'
                    echo 'Stage: Environment Setup'
                    echo '========================================'
                }
                
                script {
                    // Verify Java version
                    bat """
                        echo Java Version:
                        java -version
                        echo.
                        echo Java Home: ${JAVA_HOME}
                        echo.
                        echo Maven Version:
                        mvn -version
                    """
                }
            }
        }

        // Stage 3: Maven Build (compile and package)
        stage('Maven Build') {
            steps {
                script {
                    echo '========================================'
                    echo 'Stage: Maven Build'
                    echo '========================================'
                }
                
                script {
                    // Clean, compile, and package the application
                    // Tests are skipped as per requirements
                    bat """
                        echo Starting Maven build...
                        mvn clean package -DskipTests
                        echo.
                        echo Build completed successfully!
                        echo JAR file location: target\\${APP_NAME}-*.jar
                    """
                }
            }
            
            post {
                success {
                    script {
                        // Archive the built JAR file
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        echo "✓ Maven build artifacts archived"
                    }
                }
            }
        }

        // Stage 4: Validate Dockerfile by building Docker image
        stage('Docker Build') {
            steps {
                script {
                    echo '========================================'
                    echo 'Stage: Docker Build Validation'
                    echo '========================================'
                }
                
                script {
                    // Build Docker image to validate Dockerfile
                    // This ensures the Dockerfile is correct and the image can be built
                    bat """
                        echo Building Docker image to validate Dockerfile...
                        docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .
                        docker build -t ${DOCKER_IMAGE_NAME}:latest .
                        echo.
                        echo Docker image built successfully!
                        echo Image: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
                        echo Image: ${DOCKER_IMAGE_NAME}:latest
                    """
                }
            }
            
            post {
                success {
                    script {
                        // Display Docker image information
                        bat """
                            echo Docker image information:
                            docker images ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
                            echo.
                            echo Docker image size:
                            docker images ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} --format "{{.Size}}"
                        """
                        echo "✓ Docker image validation completed"
                    }
                }
                always {
                    echo "Docker build stage completed"
                }
            }
        }
    }

    // Post-build actions
    post {
        // Actions to perform when pipeline succeeds
        success {
            script {
                echo '========================================'
                echo 'Pipeline Status: SUCCESS ✓'
                echo '========================================'
                echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Build URL: ${env.BUILD_URL}"
                echo ""
                echo "Next Steps:"
                echo "- Render will auto-deploy on git push"
                echo "- No manual deployment required"
            }
        }

        // Actions to perform when pipeline fails
        failure {
            script {
                echo '========================================'
                echo 'Pipeline Status: FAILED ✗'
                echo '========================================'
                echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Please check the logs above for errors"
            }
        }

        // Actions to perform regardless of pipeline status
        always {
            script {
                echo '========================================'
                echo 'Pipeline Execution Completed'
                echo '========================================'
                echo "Duration: ${currentBuild.durationString}"
            }
        }
    }
}

