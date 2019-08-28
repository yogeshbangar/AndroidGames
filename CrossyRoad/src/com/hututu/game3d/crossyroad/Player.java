package com.hututu.game3d.crossyroad;

public class Player {
	boolean water = false;
	boolean isPress = false;
	boolean isEgle = false;
	boolean waterSound = false;
	boolean random = true;
	int no = 0;
	int Crash = 0;
	int died =0;
	int img;
	int buyChar;
	
	float x,y;
	float z;
	float vz;
	float vx;
	float pres = .3f;
	
	long time =0;
	
	void set(float _x,float _y,int _no,int _img,int _buyChar){
		x = _x;
		y = _y;
		no =_no;
		water = false;
		Crash = 0;
		if(random)
			img = _img;
		died = 0;
		buyChar = _buyChar;
		z =0;
		pres = .3f;
		isPress = false;
		isEgle = false;
		time = System.currentTimeMillis();
	}
}
