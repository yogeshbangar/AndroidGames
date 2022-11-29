package com.oneday.games24.motocitytraffic;

public class GameAcc {
    int mUnlockVal, mUpgradeVal, noBuy;
    int mActivetime;
    float x, y, ang;
    int no = 0;
    boolean isUnlock, isActive = false;

    public GameAcc(int _unlockVal, int _upgVal, int _mBuy) {
        mUnlockVal = _unlockVal;
        mUpgradeVal = _upgVal;
        noBuy = _mBuy;
        isUnlock = false;
        isActive = false;
        x = y = 100;
    }

    void Set(int buy) {
        noBuy = buy;
        mUpgradeVal = (1 + buy) * 20;
        mActivetime = noBuy * 25;
    }

    void SetPos(float _x, float _y, float _ang) {
        x = _x;
        y = _y;
        ang = _ang;
    }

    void resetPower() {
        if (isActive) {
            x = y = 10;
            mActivetime = 0;
            isActive = false;
        }
    }

}
