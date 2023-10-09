#!/usr/bin/bash

#Update registry for docker
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg -y
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg
echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

#Install docker
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

#Login to docker
sudo docker login registry.git.cf.ac.uk -u d1711278 -p 3uwy_zxDrxhVXjA7AeLQ

#Pull and run images
sudo docker run -d -p 8070:8070 registry.git.cf.ac.uk/d1310691/project15dissertation/jackson-pollock
sudo docker run -d -p 8069:8069 registry.git.cf.ac.uk/d1310691/project15dissertation/bob-ross

#Install nginx
sudo apt install nginx

#Install Java
sudo apt update
sudo apt install default-jdk -y

#Install Jenkins
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins -y
sudo systemctl enable jenkins
sudo systemctl start jenkins