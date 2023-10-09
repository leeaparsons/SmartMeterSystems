variable "flavor" { default = "m1.xlarge" }
variable "image" { default = "Debian 10.13.0 20220910" }

variable "name" { default = "paintingServiceDeployment" }

variable "network" { default = "paintingNetwork" }

variable "keypair" { default = "debianMain" }
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./paintingService.sh" }
variable "security_description" { default = "paintingService" }
variable "security_name" { default = "paintingService" }
