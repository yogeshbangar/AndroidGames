package com.httgames.app.stylishframestudio;

public class Skeleton {

	public float sx = 0;
	public float lsx = 1;
	public float sy = 0;
	public float lsy = 0;
	public float rgb[] = { .35f, .35f, .35f };
	public int rectX, rectY;
	public int rectDx, rectDy;
	public float ang = 0;
	public float langle = 0;
	public float x = 0;
	public float y = 0;

	public APLAN mTexture;

	public void Reset() {

		rgb[0] = .4f;
		rgb[1] = .4f;
		rgb[2] = .4f;
		lsx = 1;
		sx = 1;
		lsy = 1;
		sy = 1;
		langle = ang = 0;

	}

	public Skeleton() {

	}

	public Skeleton(int _rectx, int _recty, int _rectdx, int _rectdy, float _x, float _y) {

		langle = ang = 0;
		x = _x;
		y = _y;
		rgb[0] = .4f;
		rgb[1] = .4f;
		rgb[2] = .4f;
		
		rectX = _rectx;
		rectY = _recty;
		lsx = sx = lsy = sy = 1;
		rectDx = _rectdx;
		rectDy = _rectdy;
		mTexture = null;
	}

}
