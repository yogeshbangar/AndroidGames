cls
cls
cls
echo "=============Start=============="
adb uninstall com.hututu.game.poker
adb install ./bin/classes/Poker.apk
adb logcat -c 
adb shell am start -n com.hututu.game.poker/.Start
adb logcat
echo "=========Complete============="
