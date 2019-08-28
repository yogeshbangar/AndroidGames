clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.crisisofrail
#adb install ./bin/CrisisOfRail.apk
adb install /home/yogesh/Desktop/CrisisOfRail.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.crisisofrail/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
