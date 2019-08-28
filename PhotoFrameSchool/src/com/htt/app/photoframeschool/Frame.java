package com.htt.app.photoframeschool;

public class Frame {
	int rectX,rectY;
	int rectDx,rectDy;
	
	
	float x,y;
	float sx = 0;
	float lsx =1;
	float sy = 0;
	float lsy = 0;
	float rgb[]= {.35f,.35f,.35f};
	float ang;
	float langle =0;
	SimplePlane mTexture;
	
	public Frame(){
		
	}
	public Frame(int _rectx,int _recty,int _rectdx,int _rectdy,float _x,float _y){
		rectX = _rectx;
		rectY = _recty;
		rectDx = _rectdx;
		rectDy = _rectdy;
		
		x = _x;
		y = _y;
		
		rgb[0] = rgb[1] = rgb[2] = .4f;
		lsx = sx = lsy = sy = 1;
		langle = ang = 0;
		
		mTexture = null;
	}
	
	
	void Reset(){
		
		rgb[0] = rgb[1] = rgb[2] = .4f;
		lsx = sx = lsy = sy = 1;
		langle = ang = 0;
		
	}
}
