package com.hututu.game.bubblecandyunlimited;

public class Animation {
	float x, y;
	byte anim;
	byte color;
	void set(float x, float y, int anim,int color) {
		this.x = x;
		this.y = y;
		this.anim = (byte)anim;
		this.color = (byte)color;
	}
}
