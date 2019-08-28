clear
clear
clear
echo "=============Start=============="
adb uninstall com.game2d.timberboy
#adb install ./bin/TimberBoy.apk
adb install /home/yogesh/Desktop/TimberBoy.apk
adb logcat -c 
adb shell am start -n com.game2d.timberboy/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
