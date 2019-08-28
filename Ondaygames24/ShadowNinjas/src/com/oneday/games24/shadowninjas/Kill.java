package com.oneday.games24.shadowninjas;

public class Kill {
	float x,y;
	float hide =0;
	int img =0;
	void set(float _x,float _y,int _img){
		x =_x;
		y =_y;
		img =_img;
		hide =1;
	}
	void update(){
		hide-=.2f;
	}
}
