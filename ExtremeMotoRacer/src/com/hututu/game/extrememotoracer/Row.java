package com.hututu.game.extrememotoracer;


public class Row {
	float y;
	int Id,obj,bg;
	void update()
	{
		y-=(GameRenderer.mGR.mPlayer.MinSpeed+GameRenderer.mGR.mPlayer.vy);
	}
	void set(float _y,int _Id,int _obj,int _bg)
	{
		y = _y;
		Id =_Id;
		obj =_obj;
		bg = _bg;
	}
	
	

}
