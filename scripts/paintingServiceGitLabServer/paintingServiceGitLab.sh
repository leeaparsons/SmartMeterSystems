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
  --description "paintingServiceRunner" \
  --docker-image "docker:20.10.16" \
  --docker-privileged \
  --docker-volumes "/certs/client"

sudo usermod -aG docker $USER && newgrp docker


#TO INSTALL MONGO
#sudo vi /etc/yum.repos.d/mongodb-org-6.0.repo
#INSERT THE FOLLOWING
#[mongodb-org-6.0]
#name=MongoDB Repository
#baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/6.0/x86_64/
#gpgcheck=1
#enabled=1
#gpgkey=https://www.mongodb.org/static/pgp/server-6.0.asc

#sudo yum install -y mongodb-org
#sudo systemctl start mongod