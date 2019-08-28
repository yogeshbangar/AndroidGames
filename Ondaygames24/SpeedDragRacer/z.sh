clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.speeddragracer
adb install ./bin/SpeedDragRacer.apk
#adb install /home/yogesh/Desktop/SpeedDragRacer.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.speeddragracer/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
