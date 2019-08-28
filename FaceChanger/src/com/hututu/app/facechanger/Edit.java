package com.hututu.app.facechanger;

public class Edit {
	float sx = 0;
	float sy = 0;
	float lsx = 0;
	float lsy = 0;
	
	float x,y;
//	float vx,vy;
	float angle;
	float langle;
	SimplePlane mTexture;
	public Edit(SimplePlane Texture)
	{
		set();
		mTexture = Texture;
	}
	public void set()
	{
		lsx=sx =lsy=sy = 1;
		langle = angle = x = y = 0;
		
	}
}
