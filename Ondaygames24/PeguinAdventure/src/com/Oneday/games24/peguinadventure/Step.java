package com.Oneday.games24.peguinadventure;

public class Step {
	boolean isOn;
	boolean Watch;
	int no;
	float x;
	
	public Step(float _x,boolean On)
	{
		x = _x;
		isOn=On;
		Watch = false;
		no = 0;
	}
	void set(float _x,boolean On,int _no)
	{
		x = _x;
		isOn=On;
		Watch = false;
		no=_no;
	}
}
