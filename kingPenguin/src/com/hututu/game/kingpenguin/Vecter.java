package com.hututu.game.kingpenguin;

public class Vecter {
	boolean off;
	byte no;
	float x,y;
	float vy;
	public Vecter()
	{
	}
	
	public Vecter(float _x,float _y,int _no)
	{
		x = _x;
		y = _y;
		off = true;
		no = (byte)_no;
	}
	void set(float _x,float _y,int _no)
	{
		x = _x;
		y = _y;
		off = true;
		no = (byte)_no;
	}
}
