package com.hututu.game.trafficracing;

public class CoinAnim {
	float x,y;
	float vx,vy;
	public CoinAnim()
	{
		x =-100;
		y=-100;
		vx =0;
		vy=0;
	}
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		vx = world2screenX(x)-720f;
		vy = world2screenY(y)-76f;
		double d = M.GetAngle(-vx, -vy);//vy, -vx
		vx = (float)Math.cos(d)*.06f;
		vy =-(float)Math.sin(d)*.10f;
	}
	void update()
	{
		x+=vx;
		y+=vy;
	}
	float world2screenX(float a)
	{
		float c = ((a+1)*.5f)*M.ScreenWidth;
		return c;
	}
	float world2screenY(float a)
	{
		float c = ((1-a)*.5f)*M.ScreenHieght;
		return c;
	}
	
}
