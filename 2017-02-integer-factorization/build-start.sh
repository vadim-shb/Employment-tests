#!/bin/bash

## Install nodejs 7
#curl -sL https://deb.nodesource.com/setup_7.x | sudo -E bash -
#sudo apt-get install -y nodejs

## Install last angular-cli
#sudo npm uninstall -g angular-cli
#sudo npm cache clean
#sudo npm install -g angular-cli@latest
ng build --prod
