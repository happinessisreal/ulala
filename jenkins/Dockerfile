FROM jenkins/jenkins:lts-jdk17

USER root

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        docker.io \
        docker-compose \
        git \
    && rm -rf /var/lib/apt/lists/*

COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/

USER jenkins
