clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.games.crazydragracing
adb install ./bin/CrazyDragRacing.apk
#adb install /home/yogesh/Desktop/CrazyDragRacing.apk
adb logcat -c 
adb shell am start -n com.hututu.games.crazydragracing/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="

#
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
