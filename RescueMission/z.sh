clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.rescuemission
#adb install ./bin/RescueMission.apk
adb install /home/yogesh/Desktop/RescueMission.apk
adb logcat -c 
adb shell am start -n com.hututu.game.rescuemission/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000 z	
