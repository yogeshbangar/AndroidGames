﻿clear
clear
clear
echo "=============Start=============="
adb uninstall com.hututu.game.flapycherrybird
adb install ./bin/FlapyBirdGoogle.apk
adb logcat -c 
adb shell am start -n com.hututu.game.flapycherrybird/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
