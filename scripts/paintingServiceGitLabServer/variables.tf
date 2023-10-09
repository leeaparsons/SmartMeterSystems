variable "flavor" { default = "m1.xlarge" }
variable "image" { default = "Rocky Linux 9 20220830" }

variable "name" { default = "paintingServiceGitLab" }

variable "network" { default = "paintingGitLabNetwork" }

variable "keypair" { default = "1711278key" }
variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./paintingServiceGitLab.sh" }
variable "security_description" { default = "paintingService" }
variable "security_name" { default = "paintingService" }
