package com.oneday.games24.motocitytraffic;

public class Animation {
    float x, y, z, t;
    float vx, vy, vz;
    float dt = .1f;
    float ang;
    GameRenderer mGR;

    public Animation(GameRenderer _mGR) {
        mGR = _mGR;
        y = x = -100;
    }

    void SetBoost(float _x, float _y, float _vx, float _vy, float _ang) {
        x = _x;
        y = _y;
        vx = _vx;
        vy = _vy;
        z = .75f;
        t = 1f;
        dt = .0625f;
        ang = _ang;
    }

    void UpdateBoost() {
        if (M.GameScreen == M.GAMEPLAY) {
            z *= 1.08f;
            if (t >= 0f) {
                t -= dt;
                dt += .01f;
            } else
                x = y = -100;
        } else {
            z *= 1.035f;
            if (t >= 0f) {
                t -= dt;
            } else {
                x = y = -100;
            }
            dt += .003f;
        }
    }

    void setAchieve(float _x, float _y, float Cnt) {
        x = _x;
        y = _y;
        z = 1f;
        vz = 1.2f;
        ang = Cnt / 2;
    }

    void updateAchieve() {
        if (y >= .01f)
            y *= .85f;
        ang--;
        if (ang <= 0) {
            z *= vz;
            if (z > 2)
                vz = .8f;
            if (z <= .01f)
                setAchieve(100, 100, 0);
        }
    }

    void SetTap(float _x, float _y) {
        x = _x;
        y = _y;
        z = 1f;
        vz = .9f;
    }

    void UpdateTap() {
        z *= vz;
        if (z < 0)
            SetTap(100, 100);
    }
}
