clear
clear
clear
echo "=============Start=============="
adb uninstall com.onedaygames24.stickpowerheroes
#adb install ./bin/StickPowerHeroes.apk
adb install /home/yogesh/Desktop/StickPowerHeroes.apk
adb logcat -c 
adb shell am start -n com.onedaygames24.stickpowerheroes/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
