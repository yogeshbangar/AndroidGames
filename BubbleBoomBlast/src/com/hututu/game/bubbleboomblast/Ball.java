package com.hututu.game.bubbleboomblast;

public class Ball {
	float x,y,vx,vy;
	float ang =0;
	int gayab;
	int SolidCount;
	
	void set(float _x,float _y,float _vx,float _vy)
	{
		x = _x;
		y = _y;
		vx = _vx;
		vy = _vy;
		gayab = 0;
		SolidCount = 0;
	}
	void update()
	{
		if(gayab>0)
		{
			if(gayab==2)
				x =-100;
			gayab--;
		}
		else if (vy != 0 && vx != 0) {
			x += vx;
			y += vy;
			if(vy>-.2f)
				vy -= .005f;
			if(M.mulCount == 10)
				vx -= .005f;
		}
		
		
	}
}
