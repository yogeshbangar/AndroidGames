package com.hututu.games.highwayracing;

public class Coin {
	int counter =0;
	float x,y;
	
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		counter =0;
	}
	void set(float _x,float _y,int _counter)
	{
		x = _x;
		y = _y;
		counter =_counter;
	}
}
