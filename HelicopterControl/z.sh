clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.helicoptercontrol
adb install ./bin/HelicopterControl.apk
#adb install /home/yogesh/Desktop/HelicopterControl.apk
adb logcat -c 
adb shell am start -n com.hututu.game.helicoptercontrol/.Start
#adb logcat
echo "=========Complete============="
