clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.TrainCrash
adb install ./bin/TrainCrash.apk
#adb install /home/nikhil/Desktop/TrainCrash.apk
adb logcat -c 
adb shell am start -n com.hututu.game.TrainCrash/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
