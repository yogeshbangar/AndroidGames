clear
clear
clear
echo "=============Start=============="
adb uninstall com.onedaygames24.shoot2bottle
adb install ./bin/Shoot2Bottle.apk
#adb install /home/yogesh/Desktop/Shoot2Bottle.apk
adb logcat -c 
adb shell am start -n com.onedaygames24.shoot2bottle/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="


#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000
#keytool -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 900000


xmlns:ads="http://schemas.android.com/apk/res-auto"
    xmlns:ap="http://schemas.android.com/apk/res-auto"
    
