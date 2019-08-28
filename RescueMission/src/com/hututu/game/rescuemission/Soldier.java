package com.hututu.game.rescuemission;

public class Soldier {
	float x,y;
	float vy,vx;
	boolean isDead;
	boolean isRailing;
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		vy =0;
		vx =0;
		isDead = false;
		isRailing = false;
	}
	
	void update()
	{
		if(vx==0 && !isRailing && !isDead){
			if(y>-.75f)
				y-=vy;
			else{
				isDead = true;
				y-=M.mRand.nextInt(10)*.01f;
				M.sound4(GameRenderer.mContext, R.drawable.puppet_crash);
			}
			vy+=.005f;
		}
		else{
			if(y>-.55f){
				y-=.01f;
				if(y>=-.54f)
					y-=M.mRand.nextInt(10)*.01f;
			}
		}
		x+=vx;
	}
}
