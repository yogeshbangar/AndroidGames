package com.hututu.game.facechanger2;

public class Edit {
	float sx = 0;
	float sy = 0;
	float lsx = 0;
	float lsy = 0;
	float x, y;
	float vx, vy;
	float angle;
	float langle;
	float rgb[] = { 10, 10, 10 };
	SimplePlane mTexture;

	public Edit(SimplePlane Texture) {
		set();
		mTexture = Texture;
	}

	public void set() {
		rgb[0] = rgb[1] = rgb[2] = 10;
		lsx = sx = lsy = sy = 1;
		langle = angle = x = y = 0;

	}
}
