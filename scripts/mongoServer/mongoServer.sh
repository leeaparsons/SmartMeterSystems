#!/usr/bin/bash

sudo sysctl -w vm.max_map_count=262144
sysctl -p

#Installing Docker
sudo dnf check-update
sudo dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo dnf install docker-ce docker-ce-cli containerd.io -y
sudo systemctl start docker
sudo systemctl status docker
sudo systemctl enable docker


#Installing mongoDB
sudo docker pull mongodb/mongodb-community-server
sudo docker container ls


#To change the bindIp (cannot be done whilst connected to the DB)
# sudo vi /etc/mongod.conf
#net:
#  port: 27017
#  bindIp: 0.0.0.0

#To connect to the DB
#sudo docker exec -it mongo mongosh

#To insert dummy data (remember to change the collection name from collection, if you want)
#db.collection.insertMany([[{"mpxn": "1023536563550","status": 200,"devices": [{"deviceId": "00-0B-6B-01-00-1D-B8-46","deviceType": "CHF","deviceStatus": "COMMISSIONED","deviceManufacturer": "1072","deviceModel": "57310102","smetsChtsVersion": "V1.3","deviceFirmwareVersion": "04000007","deviceFirmwareVersionStatus": "ACTIVE","cplStatus": "ACTIVE","dateCommissioned": "2021-06-01Z","uprn": "10002600297","propertyFilter": {"postCode": "CB6 2WX","addressIdentifier": "64,,STOUR GREEN,,,ELY,CAMBRIDG"},"cspRegion": "CENTRAL","deviceGbcsVersion": "3.2","hanVariant": "Single Band (2.4GHz only)"},{"deviceId": "30-EB-5A-FF-FF-08-DD-11","deviceType": "ESME","deviceStatus": "COMMISSIONED","deviceManufacturer": "1063","deviceModel": "00050203","smetsChtsVersion": "V4.2","deviceFirmwareVersion": "38040404","deviceFirmwareVersionStatus": "ACTIVE","cplStatus": "ACTIVE","dateCommissioned": "2021-06-01Z","importMpxn": "1023536563550","esmeVariant": "ADE","uprn": "10002600297","propertyFilter": {"postCode": "CB6 2WX","addressIdentifier": "64,,STOUR GREEN,,,ELY,CAMBRIDG"},"cspRegion": "CENTRAL","deviceGbcsVersion": "3.2","lastCommunicationsDateTime": {"status": 200,"statusMessage": "SUCCESS","value": "2023-06-26T13:58:30.000Z","lastUpdate": "2023-06-26T13:59:08.948Z"},"checkFirmware": {"status": 200,"statusMessage": "SUCCESS","firmwareVersionValue": "38040404","lastUpdate": "2023-06-26T13:59:10.716Z"}},{"deviceId": "00-0B-6B-AA-00-1D-B8-46","deviceType": "GPF","deviceStatus": "COMMISSIONED","deviceManufacturer": "1072","deviceModel": "57310102","smetsChtsVersion": "V1.3","deviceFirmwareVersion": "04000007","deviceFirmwareVersionStatus": "ACTIVE","cplStatus": "ACTIVE","dateCommissioned": "2021-06-01Z","uprn": "10002600297","propertyFilter": {"postCode": "CB6 2WX","addressIdentifier": "64,,,Stour Green,,ELY"},"cspRegion": "CENTRAL","deviceGbcsVersion": "3.2"},{"deviceId": "30-EB-5A-FF-FF-51-1E-A2","deviceType": "GSME","deviceStatus": "COMMISSIONED","deviceManufacturer": "1063","deviceModel": "47720101","smetsChtsVersion": "V4.2","deviceFirmwareVersion": "03033254","deviceFirmwareVersionStatus": "ACTIVE","cplStatus": "ACTIVE","dateCommissioned": "2021-06-01Z","importMpxn": "7434197709","propertyFilter": {"postCode": "CB6 2WX","addressIdentifier": "64,,,Stour Green,,ELY"},"cspRegion": "CENTRAL","deviceGbcsVersion": "3.2","lastCommunicationsDateTime": {"status": 200,"statusMessage": "SUCCESS","value": "2023-06-26T13:30:47.000Z","lastUpdate": "2023-06-26T13:59:09.135Z"}},{"deviceId": "0C-A2-F4-00-00-5E-20-3B","deviceType": "PPMID","deviceStatus": "COMMISSIONED","deviceManufacturer": "10E0","deviceModel": "00090100","smetsChtsVersion": "V4.2","deviceFirmwareVersion": "00020002","deviceFirmwareVersionStatus": "ACTIVE","cplStatus": "ACTIVE","dateCommissioned": "2021-06-01Z","uprn": "10002600297","propertyFilter": {"postCode": "CB6 2WX","addressIdentifier": "64,,STOUR GREEN,,,ELY,CAMBRIDG"},"cspRegion": "CENTRAL","deviceGbcsVersion": "3.2","lastCommunicationsDateTime": {"status": 200,"statusMessage": "SUCCESS","value": "2023-06-26T13:59:02.000Z","lastUpdate": "2023-06-26T13:59:09.161Z"}}],"wanMatrix": {"status": 404},"lastUpdate": "2023-06-26T13:58:59.307Z"}]])
