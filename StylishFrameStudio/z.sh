clear
clear
clear
echo "=============Start=============="
adb uninstall com.httgames.app.stylishframestudio
adb install ./bin/StylishFrameStudio.apk
#adb install /home/yogesh/Desktop/StylishFrameStudio.apk
adb logcat -c 
adb shell am start -n com.httgames.app.stylishframestudio/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
