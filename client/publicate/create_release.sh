#!/bin/bash

echo "Clearing old release files..."
rm -fr release
mkdir -p release/logs

echo "Copying new resources files..."
cp -r ../res/ release/
cp ../test.tmx release/

echo "Maven make..."
cd ..
mvn package
cd -

echo "Copying jar..."
cp ../target/epicbossarenasclient-0.0.1-SNAPSHOT.jar release/Game.jar

echo "Copying natives..."
cp -r ../target/natives/ release/

echo "Copying exec files..."
cp LaunchGame.sh release/
cp LaunchGame.bat release/

echo "Zipping the release..."
zip -r release.zip release

echo "Publicating to the webserver..."
# ADD YOURS HERE

echo "Finished."
