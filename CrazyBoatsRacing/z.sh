clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.games.crazyboatsracing
adb install ./bin/CrazyBoatsRacing.apk
#adb install /home/yogesh/Desktop/CrazyBoatsRacing.apk
adb logcat -c 
adb shell am start -n com.hututu.games.crazyboatsracing/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000