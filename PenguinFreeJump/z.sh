clear
clear
clear
echo "=============Start=============="
adb uninstall com.fun2sh.penguinfreejump
#adb install ./bin/PenguinFreeJump.apk
adb install /home/yogesh/Desktop/PenguinFreeJump.apk
adb logcat -c 
adb shell am start -n com.fun2sh.penguinfreejump/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000
