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
                dir('shield') {
                    // Compile the Java code using Maven
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Package') {
            steps {
                dir('shield') {
                    // Package the application
                    sh 'mvn package'
                }
            }
        }

        stage('Archive') {
            steps {
                // Archive the build artifacts
                archiveArtifacts artifacts: 'shield/target/*.jar', allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the application (placeholder for actual deployment steps)
                echo "Deploying shield on local ..."
                sh 'process_pid= ps | grep "java -Dserver.port=9000" | awk "{print $2}"'
                sh 'if [[ -n "$process_pid" ]]; then kill -9 "$process_pid"; fi'
                sh 'env SERVER.PORT=9000 nohup java -jar shield/target/*.jar &> ~/Desktop/shield.log &'
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
