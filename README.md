# ulala CI/CD Demo

This repository demonstrates a complete CI/CD pipeline using Jenkins, Docker, and Docker Compose for a tiny Flask web application with a `/health` endpoint.

## Repository Layout
- `app/` – Flask demo app and unit tests
- `Dockerfile` – Builds the application container image
- `docker-compose.yml` – Deploys the app locally via Docker Compose
- `Jenkinsfile` – Declarative Jenkins pipeline (build → test → package → deploy → health check)
- `healthcheck.sh` – Script used by Jenkins to verify the running container
- `.dockerignore` – Keeps the Docker build context slim

## Jenkins Pipeline
The `Jenkinsfile` defines the following stages:
1. **Build** – Installs Python dependencies and byte-compiles the app inside a Python container.
2. **Test** – Runs `pytest` against the app.
3. **Package** – Builds the Docker image `ulala-demo-app:latest`.
4. **Deploy** – Uses Docker Compose to stand up the container.
5. **Health Check** – Executes `healthcheck.sh` to assert the `/health` endpoint returns HTTP 200.

> Tip: ensure `healthcheck.sh` is executable if you run the steps manually: `chmod +x healthcheck.sh`.

## Local Workflow
```bash
# Build and run the app locally
docker compose up -d --build

# Verify health
./healthcheck.sh

# Tear down
docker compose down
```

## Running Jenkins in Docker (Bonus)
Launch Jenkins with Docker access (Docker-in-Docker via socket mount):

```bash
docker compose -f docker-compose.jenkins.yml up -d
```

This starts a Jenkins controller capable of executing the provided pipeline. Configure a pipeline job that points to this repository and run it; Jenkins will execute Docker commands on the host through the mounted socket.

## Sample Pipeline Execution
1. Create a new Jenkins **Pipeline** job using the `Jenkinsfile` in this repo.
2. Ensure the agent has `docker`, `docker-compose` (or the Docker Compose plugin), and `curl` available.
3. Run the job – Jenkins will build, test, package, deploy, and verify the app automatically.

Happy shipping!