﻿clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.game24.frongjumpingjump
#adb install ./bin/FrongJumpingJump.apk
adb install /home/yogesh/Desktop/FrongJumpingJump.apk
adb logcat -c 
adb shell am start -n com.oneday.game24.frongjumpingjump/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="