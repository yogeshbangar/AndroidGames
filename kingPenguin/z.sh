clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.kingpenguin
adb install ./bin/kingPenguin.apk
#adb install /home/yogesh/Desktop/kingPenguin.apk
adb logcat -c 
adb shell am start -n com.hututu.game.kingpenguin/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
