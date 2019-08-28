clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.speeddragracing
adb install ./bin/SpeedDragRacing.apk
adb logcat -c 
adb shell am start -n com.hututu.game.speeddragracing/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
