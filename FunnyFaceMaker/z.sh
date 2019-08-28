clear
clear
clear
echo "=============Start=============="
adb uninstall com.robotics.app.funnyfacemaker
adb install ./bin/FunnyFaceMaker.apk
#adb install /home/yogesh/Desktop/FunnyFaceMaker.apk
adb logcat -c 
adb shell am start -n com.robotics.app.funnyfacemaker/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
