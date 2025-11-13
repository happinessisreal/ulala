pipeline {
  agent any

  environment {
    APP_IMAGE = "ulala-demo-app:latest"
    COMPOSE_FILE = "docker-compose.yml"
  }

  options {
    timestamps()
  }

  stages {
    stage('Build') {
      steps {
        sh '''
        docker run --rm \
          -v "$PWD/app":/app \
          -w /app \
          python:3.11-slim \
          /bin/bash -c "pip install --no-cache-dir -r requirements.txt && python -m compileall ."
        '''
      }
    }

    stage('Test') {
      steps {
        sh '''
        docker run --rm \
          -v "$PWD/app":/app \
          -w /app \
          python:3.11-slim \
          /bin/bash -c "pip install --no-cache-dir -r requirements.txt && pytest"
        '''
      }
    }

    stage('Package') {
      steps {
        sh "docker build -t ${env.APP_IMAGE} ."
      }
    }

    stage('Deploy') {
      steps {
        script {
          def composeCmd = sh(returnStdout: true, script: '''
            if command -v docker-compose >/dev/null 2>&1; then
              echo docker-compose
            else
              echo "docker compose"
            fi
          ''').trim()

          sh "${composeCmd} -f ${env.COMPOSE_FILE} down || true"
          sh "${composeCmd} -f ${env.COMPOSE_FILE} up -d --build"
        }
      }
    }

    stage('Health Check') {
      steps {
        sh 'chmod +x healthcheck.sh'
        sh './healthcheck.sh'
      }
    }
  }

  post {
    always {
      script {
        sh '''
          set -e
          if command -v docker-compose >/dev/null 2>&1; then
            COMPOSE_CMD=docker-compose
          else
            COMPOSE_CMD="docker compose"
          fi

          $COMPOSE_CMD -f "$COMPOSE_FILE" down || true
        '''
      }
    }
  }
}
