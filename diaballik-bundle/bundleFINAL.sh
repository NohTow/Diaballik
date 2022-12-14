#!/bin/sh

name1="Antoine-CHAFFIN"
name2="Adrien-BURIDANT"
release="FINAL"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
# compiling and testing the back-end
cd ../diaballik-model
mvn clean package
# building the frontend
cd ../diaballik-frontend
ng build --prod --build-optimizer
# creating release files
cd ../diaballik-bundle
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-$name1-$name2-sources.zip diaballik-doc diaballik-model diaballik-frontend/ -x diaballik-model/target/\* lib-algo/target/ \*.idea/\* \*.iml
cd diaballik-bundle/