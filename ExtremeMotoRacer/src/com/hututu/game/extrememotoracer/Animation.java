package com.hututu.game.extrememotoracer;

public class Animation {
	
	float x,scal;
	static int counter = 0;
	static boolean Move;
	int no;
	boolean lock = true;
	
	int Price = 0;
	int Acc = 0;
	int Speed = 0;
	int handling = 0;
	int strenth =0;
	String Name = "";
	
	public Animation(float _x,float _scal ,int _no,boolean _lock,
			int _Price,int _Acc,int _Speed,int _handling,int _strenth,String _Name)
	{
		x = _x;
		scal = _scal ;
		no = _no;
		lock = _lock;
		
		Price = _Price;
		Acc = _Acc;
		Speed = _Speed;
		handling = _handling;
		strenth =_strenth;
		Name = _Name;
	}
	void update()
	{
		if(counter==0)
			return;
		counter++;
		x+=Move?.1f:-.1f;
		if(Move)
			scal+=x > 0?(scal>.2f?-.027f:0):0.027f;
		else
			scal+=x > 0?0.027f:(scal>.2f?-.027f:0);
	}
	void set(float _x)
	{
		x = _x;
		scal = .1f;
	}
	
}
