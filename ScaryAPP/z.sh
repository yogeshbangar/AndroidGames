clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.app.scaryapp
adb install ./bin/ScaryAPP.apk
adb logcat -c 
adb shell am start -n com.hututu.app.scaryapp/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
