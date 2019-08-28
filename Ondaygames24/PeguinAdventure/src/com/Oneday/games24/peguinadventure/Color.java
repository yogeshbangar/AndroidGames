package com.Oneday.games24.peguinadventure;

public class Color {
	public boolean isOne = false;
	public boolean ex;
	int color = 0;
	public int no;
	
	public Color(boolean _isOne,int _no)
	{
		isOne =_isOne;
		no =_no;
		color = 0;
	}
	public void Set(boolean _isOne,int _no)
	{
		ex  = true;
		isOne =_isOne;
		no = _no;
		color = 0;
	}
}
