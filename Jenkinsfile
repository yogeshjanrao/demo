pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git url: 'https://github.com/yogeshjanrao/demo.git'
            }
        }

        stage('Build') {
            steps {
                // Compile the Java code using Maven
                sh 'mvn -f shield/pom.xml clean compile'
            }
        }

        stage('Package') {
            steps {
                // Package the application
                sh 'mvn -f shield/pom.xml package'
            }
        }

        stage('Archive') {
            steps {
                // Archive the build artifacts
                archiveArtifacts artifacts: '**/shield/target/*.jar', allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the application (placeholder for actual deployment steps)
                echo 'Deploying application...'
            }
        }
    }

    post {
        always {
            // Clean up workspace after build
            cleanWs()
        }
    }
}
