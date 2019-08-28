clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.galaxyhunt
adb install ./bin/GalaxyHunt.apk
adb logcat -c 
adb shell am start -n com.hututu.game.galaxyhunt/.Start
adb logcat System.out:I *:S
#adb logcat
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
