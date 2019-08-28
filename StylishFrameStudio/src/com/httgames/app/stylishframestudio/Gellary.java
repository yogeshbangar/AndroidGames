package com.httgames.app.stylishframestudio;

public class Gellary {
	
	float Eangle;
	float Elangle;
	float Ergb[] = { 10, 10, 10 };
	
	float movx, movy;
	float movvx, movvy;
	
	float ex = 0;
	float ey = 0;
	float lex = 0;
	float ley = 0;
	
	APLAN mTexture;

	public void set() {
		lex = ex = ley = ey = 1;
		Elangle = Eangle = movx = movy = 0;
		Ergb[0] = Ergb[1] = Ergb[2] = .4f;
	}
	public Gellary(APLAN Texture) {
		set();
		mTexture = Texture;
	}
}
