clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.MotoTrafficControl
adb install ./bin/MotoTrafficControl.apk
#adb install /home/nikhil/Desktop/MotoTrafficControl.apk
adb logcat -c 
adb shell am start -n com.hututu.game.MotoTrafficControl/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
