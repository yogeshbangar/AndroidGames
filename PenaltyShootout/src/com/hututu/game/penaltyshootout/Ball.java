package com.hututu.game.penaltyshootout;

public class Ball {
	boolean sound = false;
	boolean tappa;
	
	int in;
	int ani;
	int dir;
	int reset;
	
	float x,y,z,vx,vy,vz;
	float ang;
	float s,sy;

	
	void set(float _x,float _y,float _z,float _vx,float _vy,float _vz)
	{
		x = _x;
		y = _y;
		z = _z;
		vx=_vx;
		vy=_vy;
		vz=_vz;
		tappa = false;
		in =0;
		ani = 0;
		if(ang >=3.0 && ang <=3.2)
			dir = M.mRand.nextInt(2);
		else if(ang >=2.9 && ang <=3.0)
			dir = 2;
		else if(ang >=3.2 && ang <=3.3)
			dir = 3;
		else if(ang <2.9)
			dir = 4;
		else if(ang >3.3)
			dir = 5;
		s= y;
		sy=vy/2;
		reset =0;
		sound = false;
//		System.out.println("[ang = "+ang+"] ~~~~~~~~~~~~~~~~~~~~~  [dir = "+dir+"]");
	}
	void update()
	{
		if(z>M.SC && in ==0){
			vy -=.003f;
			x+=vx;
			y+=vy;
			if(z>.2f)
				z+=vz;
		}
		else{
			if(in==1||in==5||in==6){//if ball in the net
				if(y >.52f)
					y-=.01f;
			}
			if(in == 2){ // if Ball goes to out of net
				if(!tappa){
					vy = -(float)Math.cos(ang)*.03f; 
					sy =vy/2;
				}
				vy -=.003f;
				x+=vx;
				y+=vy;
				if(z>.2f)
					z+=vz;
			}
			if(in == 3){ // if ball collide on the road
				if(y >.4f){
					if(!tappa){
						vy = (float)Math.cos(ang)*.03f;
						sy =vy/2;
					}
					vy -=.003f;
					x+=vx;
					y+=vy;
					if(z>.2f)
						z-=vz/2;
				}
			}
		}
		if(s < y)
			s+=sy;
		else
			s =y;
	}
}
