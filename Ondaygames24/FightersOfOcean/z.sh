clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.fightersofocean
adb install ./bin/FightersOfOcean.apk
#adb install /home/yogesh/Desktop/FightersOfOcean.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.fightersofocean/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="

