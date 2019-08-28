clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.motocitytraffic
#adb install ./bin/MotoCityTraffic.apk
adb install /home/yogesh/Desktop/MotoCityTraffic.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.motocitytraffic/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
