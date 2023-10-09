variable "flavor" { default = "m1.large" }
variable "image" { default = "Rocky Linux 9 20220830" }

variable "name" { default = "gitlabserver" }

variable "network" { default = "d1017923_network" }

variable "keypair" { default = "d1017923_keypair" }
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./server.sh" }
variable "security_description" { default = "Gitlab security group" }
variable "security_name" { default = "gitlab_security" }
