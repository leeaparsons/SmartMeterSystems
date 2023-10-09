variable "flavor" { default = "m1.xlarge" }
variable "image" { default = "Rocky Linux 9 20220830" }

variable "name" { default = "mongoServer" }

variable "network" { default = "mongoNetwork" }

variable "keypair" { default = "1711278key" }
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./mongoServer.sh" }
variable "security_description" { default = "mongoSecurity" }
variable "security_name" { default = "mongoSecurity" }
