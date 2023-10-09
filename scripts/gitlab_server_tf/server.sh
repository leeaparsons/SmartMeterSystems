#!/usr/bin/bash
cd /home/rocky

echo "Installing packages..."
sudo dnf install wget -y
sudo dnf install unzip -y
sudo dnf install git -y

echo "Installing Java 17..."
sudo dnf install java-17-openjdk-devel -y
java --version

echo "installing gradle"
sudo dnf update -y
wget https://services.gradle.org/distributions/gradle-7.4.2-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-7.4.2-bin.zip
export PATH=$PATH:/opt/gradle/gradle-7.4.2/bin

echo "installing docker"
sudo dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo dnf -y install docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl --now enable docker
# remove need for sudo

# gitlab runner needs to be set up
echo "install gitlab runner"
curl -L "https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.rpm.sh" | sudo bash
sudo yum install gitlab-runner -y

sudo gitlab-runner register -n \
  --url https://git.cardiff.ac.uk \
  --registration-token GR1348941DuUbxyFcjQgj_meYGYv7 \
  --executor docker \
  --description "project15dissertation" \
  --docker-image "docker:20.10.16" \
  --docker-privileged \
  --docker-volumes "/certs/client"

sudo usermod -aG docker $USER && newgrp docker

sudo docker login registry.git.cf.ac.uk -u d1017923 -p ZCGGdKFoJVStnwG98Fad
sudo docker pull registry.git.cf.ac.uk/d1310691/project15dissertation/meter-data-service
sudo docker pull registry.git.cf.ac.uk/d1310691/project15dissertation/interactive-map-service
sudo docker pull registry.git.cf.ac.uk/d1310691/project15dissertation/database-upload-service

#version: '3'
#services:
#  meter-data-service:
#    image: registry.git.cf.ac.uk/d1310691/project15dissertation/meter-data-service
#    networks:
#      appnetwork:
#        ipv4_address: 172.20.0.2
#    ports:
#      - "8081:8081"
#  interactive-map-service:
#    image: registry.git.cf.ac.uk/d1310691/project15dissertation/interactive-map-service-test
#    networks:
#      appnetwork:
#        ipv4_address: 172.20.0.1
#    ports:
#     - "8080:8080"
#  database-upload-service:
#    image: registry.git.cf.ac.uk/d1310691/project15dissertation/database-upload-service
#    networks:
#      appnetwork:
#        ipv4_address: 172.20.0.2
#    ports:
#      - 8082:8082
#networks:
#  appnetwork:
#    ipam:
#      config:
#        - subnet: 172.20.0.0/16

echo "installing kubernetes"
sudo swapoff -a
sudo sed -i '/ swap / s/^/#/' /etc/fstab
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-latest.x86_64.rpm
sudo rpm -Uvh minikube-latest.x86_64.rpm
minikube start --driver=docker
minikube kubectl -- get po -A
alias kubectl="minikube kubectl --"
