package com.hututu.game.bubblecandyrescue;


public class Ball {
	
	boolean visited;
	boolean checkFall;
	byte candy;
	int Color;
	int anim = 0;
	int i = 0,j=0;
	void set(int _color,int _i ,int _j)
	{
		Color = _color;
		checkFall = visited = false; 
		i = _i;
		j =_j;
		anim = 0;
		candy = (byte)M.mRand.nextInt(25);
	}
	void reset()
	{
		Color = -1;
		checkFall = visited = false; 
	}
	void copy(Ball ball) {
		Color = ball.Color;
		visited = ball.visited;
		checkFall = ball.checkFall;
		i = ball.i;
		j = ball.j;
	}
	void anim()
	{
		anim = 10;
	}
}
