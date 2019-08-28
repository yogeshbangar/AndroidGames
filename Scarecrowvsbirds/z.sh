clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.ScarecrowvsBirds
adb install ./bin/Scarecrowvsbirds.apk
adb logcat -c 
adb shell am start -n com.hututu.game.ScarecrowvsBirds/.Start
adb logcat
echo "=========Complete============="


#http://180.179.207.245/cutthebox.html
