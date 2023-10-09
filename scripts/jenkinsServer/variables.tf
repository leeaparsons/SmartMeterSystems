variable "flavor" { default = "m1.large" }
variable "image" { default = "Rocky Linux 9 20220830" }

variable "name" { default = "jenkinsserver" }

variable "network" { default = "default" }   # you need to change this

variable "keypair" { default = "d1017923_keypair" } # you need to change this
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./server.sh" }
variable "security_description" { default = "Terraform security group" }
variable "security_name" { default = "tf_security" }
