package com.robotics.crosstrafficcity;

public class Train {
	float x,y;
	float vx =.2f;
	int no =-1;
	/*void set1(float _x,float _y){
		x =20;
		y =_y;
		vx =.2f;
	}*/
	
	void set(float _y,int _no){
		if(M.mRand.nextBoolean()){
			x =-5;
			vx =.1f;
		}else{
			x =5;
			vx =-.1f;
		}
		y =_y;
		no = _no;
	}
}
