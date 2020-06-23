#!/bin/sh

mkdir "${ANDROID_HOME}/licenses" || true
echo "d56f5187479451eabf01fb78af6dfcb131a6481e" > "${ANDROID_HOME}/licenses/android-sdk-license"
yes | sdkmanager "platforms;android-28"
yes | sudo $ANDROID_HOME/tools/bin/sdkmanager --licenses