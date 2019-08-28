clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.SwampFrogJumping
adb install ./bin/SwampFrogJumping.apk
#adb install /home/nikhil/Desktop/MotoTrafficControl.apk
adb logcat -c 
adb shell am start -n com.hututu.game.SwampFrogJumping/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
