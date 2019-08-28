clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.bubblecandyrescue
adb install ./bin/BubbleCandyRescue.apk
#adb install /home/yogesh/Desktop/BubbleCandyRescue.apk
adb logcat -c 
adb shell am start -n com.hututu.game.bubblecandyrescue/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
