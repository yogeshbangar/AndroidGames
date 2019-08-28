clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.BoomBaseball
adb install ./bin/BoomBaseball.apk
adb logcat -c 
adb shell am start -n com.hututu.game.BoomBaseball/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
