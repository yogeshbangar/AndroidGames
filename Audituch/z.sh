clear
clear
clear
echo "=============Start=============="
adb uninstall com.tech.game.Audituch
adb install ./bin/Audituch.apk
adb logcat -c 
adb shell am start -n com.tech.game.Audituch/.Audituch
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
