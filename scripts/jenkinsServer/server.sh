#!/usr/bin/bash
cd /home/rocky
echo in directory $PWD

echo "Installing packages..."
sudo dnf install wget -y
sudo dnf install unzip -y
sudo dnf install git -y

echo "Installing Java 17..."
sudo dnf install java-17-openjdk-devel -y
java --version

echo "install Jenkins"
sudo wget -O /etc/yum.repos.d/jenkins.repo \
    https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo dnf upgrade -y
sudo dnf install java-11-openjdk -y
sudo dnf install jenkins -y
sudo systemctl daemon-reload

echo "installing gitlab server key... has to be added to the jenkins user home (~) dir "
mkdir /var/lib/jenkins/.ssh
sudo touch /var/lib/jenkins/.ssh/known_hosts
sudo ssh-keyscan git.cardiff.ac.uk >> /var/lib/jenkins/.ssh/known_hosts
sudo chmod 644 /var/lib/jenkins/.ssh/known_hosts

sudo systemctl start jenkins
systemctl status jenkins
sudo systemctl enable jenkins

echo "installing gradle"
sudo dnf update -y
wget https://services.gradle.org/distributions/gradle-7.4.2-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-7.4.2-bin.zip
export PATH=$PATH:/opt/gradle/gradle-7.4.2/bin
gradle -v
