clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.dont.tap.white.tile.piano.donttap
adb install ./bin/DontTap.apk
#adb install /home/yogesh/Desktop/DontTap.apk
adb logcat -c 
adb shell am start -n com.hututu.dont.tap.white.tile.piano.donttap/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
