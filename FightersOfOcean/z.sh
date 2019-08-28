clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.games.fightersofocean
adb install ./bin/FightersOfOcean.apk
adb logcat -c 
adb shell am start -n com.hututu.games.fightersofocean/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


