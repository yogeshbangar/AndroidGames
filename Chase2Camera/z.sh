clear
clear
clear
echo "=============Start=============="
adb uninstall com.htt.games.chase2camera
adb install ./bin/Chase2Camera.apk
#adb install /home/yogesh/Desktop/MilatryFighter.apk
adb logcat -c 
adb shell am start -n com.htt.games.chase2camera/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
