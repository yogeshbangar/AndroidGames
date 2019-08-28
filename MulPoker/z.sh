clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.mulpoker
#adb install ./bin/MulPoker.apk
adb install /home/yogesh/Desktop/MulPoker.apk
adb logcat -c 
adb shell am start -n com.hututu.game.mulpoker/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="

#
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
