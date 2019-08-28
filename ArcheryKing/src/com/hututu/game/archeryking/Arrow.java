package com.hututu.game.archeryking;

public class Arrow {
	boolean colide;
	float x,y,vx,vy,radian,t;
	void set(float _x,float _y,float _vx,float _vy)
	{
		x	=_x;
		y	=_y;
		vx	=_vx;
		vy	=_vy;
		radian = (float)M.GetAngle(vx,vy);
		colide = false;
		t =1;
	}
	void update()
	{
		if(!colide){
			x +=vx;
			y +=vy;
		if(vy !=0 || vx!=0)
			vy-=.003f;
		}
		else{
			if(t>0)
				t-=.05f;
		}
		radian = (float)M.GetAngle(vx,vy);
	}
}
