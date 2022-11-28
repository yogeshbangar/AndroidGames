package com.oneday.game24.frongjumpingjump;

public class Step {
	boolean isOn;
	boolean Watch;
	int no;
	float x;
	int moveCnt;
	public Step(float _x,boolean On)
	{
		x = _x;
		isOn=On;
		Watch = false;
		no = 0;
		moveCnt =M.randomRangeInt(0,360);
	}
	void set(float _x,boolean On,int _no)
	{
		x = _x;
		isOn=On;
		Watch = false;
		no=_no;
		moveCnt=M.randomRangeInt(0,360);
	}
}
