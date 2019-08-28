clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.papershot
adb install ./bin/PaperShot.apk
adb logcat -c 
adb shell am start -n com.hututu.game.papershot/.Start
adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
