clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.extreme.drag.racing
#adb install ./bin/ExtremeDragRacing.apk
adb install /home/yogesh/Desktop/ExtremeDragRacing.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.extreme.drag.racing/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
