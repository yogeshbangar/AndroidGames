package com.oneday.games24.parkingchamps;

public class Opponent {
    int type;
    float x, y;
    float x1, y1;
    float vx, vy;

    void set(float _x, float _y, float _vx, float _vy, int t) {
        x = _x;
        y = _y;
        vx = _vx;
        vy = _vy;
        type = t;
    }

    void update() {
        x += vx;
        y += vy;
        x1 = x + vx * 50;
        y1 = y + vy * 50;
        switch (type) {
            case 1:
                if (y > 1.2)
                    y = -1.2f;
                break;
            case 2:
                if (x > 1.2)
                    x = -1.2f;
                break;
            case 3:
                if (x < -1.2)
                    x = 1.2f;
                break;
            case 4:
                if (y < -1.2)
                    y = 1.2f;
                break;
            case 5:
                if (y < -1.0)
                    vy = .005f;
                if (y > -.4)
                    vy = -.005f;
                break;
            case 6:
                if (x < -0.4)
                    vx = .005f;
                if (x > -.05)
                    vx = -.005f;
                break;
            case 7:
                if (x < -0.85)
                    vx = .005f;
                if (x > -.55)
                    vx = -.005f;
                break;
            case 8:
                if (x < -0.45) {
                    vx = .005f;
                    vy = -.005f;
                }
                if (x > -.15) {
                    vx = -.005f;
                    vy = 0.005f;
                }
                break;
            case 9:
                if (x < 0.20) {
                    vx = .005f;
                    vy = -.005f;
                }
                if (x > .50) {
                    vx = -.005f;
                    vy = 0.005f;
                }
                break;
            case 10:
                if (x < 0.25) {
                    vx = .005f;
                    vy = -.008f;
                }
                if (x > 0.50) {
                    vx = -.005f;
                    vy = 0.008f;
                }
                break;
            case 11:
                if (x < 0.10) {
                    vx = .005f;
                    vy = .005f;
                }
                if (x > .50) {
                    vx = -.005f;
                    vy = -.005f;
                }
                break;
            case 12:
                if (x < -0.85) {
                    vx = .005f;
                }
                if (x > -.55) {
                    vx = -.005f;
                }
                break;
            case 13:
                if (y < -.35)
                    vy = .005f;
                if (y > 0.25)
                    vy = -.005f;
                break;
            case 14:
                if (x < -0.60)
                    vx = .005f;
                if (x > -.33)
                    vx = -.005f;
                break;
            case 15:
                if (x < 0.35)
                    vx = .005f;
                if (x > 0.60)
                    vx = -.005f;
                break;
            case 16:
                if (y < -.05)
                    vy = .005f;
                if (y > 0.35)
                    vy = -.005f;
                break;
            case 17:
                if (x < -.25) {
                    vx = .005f;
                    vy = -.008f;
                }
                if (x > 0.00) {
                    vx = -.005f;
                    vy = 0.008f;
                }
                break;
            case 18:
                if (x < 0.30) {
                    vx = .005f;
                    vy = .005f;
                }
                if (x > .70) {
                    vx = -.005f;
                    vy = -.005f;
                }
                break;
        }
    }


    float i_x, i_y;

    boolean get_line_intersection(float p0_x, float p0_y, float p1_x, float p1_y) {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = x1 - x;
        s2_y = y1 - y;
        float s, t;
        s = (-s1_y * (p0_x - x) + s1_x * (p0_y - y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = (s2_x * (p0_y - y) - s2_y * (p0_x - x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            i_x = p0_x + (t * s1_x);
            i_y = p0_y + (t * s1_y);
            return true;
        }
        return false; // No collision
    }
}
