clear
clear
clear
echo "=============Start=============="
adb uninstall com.oneday.games24.extrememotoracer
adb install ./bin/ExtremeMotoRacer.apk
#adb install /home/yogesh/Desktop/ExtremeMotoRacer.apk
adb logcat -c 
adb shell am start -n com.oneday.games24.extrememotoracer/.Start
#adb logcat
adb logcat System.out:I *:S
echo "=========Complete============="
# keytool -exportcert -alias androiddebugkey -keystore /home/yogesh/.android/debug.keystore -list -v
# MD5:  34:79:5D:9F:D3:90:ED:AF:04:25:D9:55:27:F8:CB:B9
# SHA1: 87:A0:F8:E0:2A:AA:8B:3A:0C:A9:A4:DF:8F:5A:79:77:2B:43:53:94
# Signature algorithm name: SHA1withRSA
# Version: 3

