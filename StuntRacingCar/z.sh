clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.stuntracingcar
adb install ./bin/StuntRacingCar.apk
adb logcat -c 
adb shell am start -n com.hututu.game.stuntracingcar/.Start
adb logcat
#adb logcat System.out:I *:S
echo "=========Complete============="
