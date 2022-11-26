package com.oneday.games24.extrememotoracer;

public class Coin {
	float x,y;
	void setCoin(float _x,float _y)
	{
		x=_x;
		y=_y;
//		vy=_vy=-.01f;
	}
	void Update()
	{
		y-=(.03f+GameRenderer.mGR.mPlayer.vy);
	}
}
