clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.santafreerunner
#adb install ./bin/SantaFreeRunner2.apk
adb install /home/yogesh/Desktop/SantaFreeRunner2.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.santafreerunner/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000


#Help
#adout Remove