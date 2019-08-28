clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.scarygames
adb install ./bin/ScaryGames.apk
adb logcat -c 
adb shell am start -n com.hututu.game.scarygames/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
