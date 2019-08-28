package com.hututu.game.bubblecandyunlimited;


public class CBubble {
	
	boolean visited;
	boolean checkFall;
	byte candy;
	byte CBubble;
	byte anim = 0;
	byte star = 100;
	byte i = 0,j=0;
	
	void set(int _color,int _i ,int _j)
	{
		CBubble = (byte)_color;
		checkFall = visited = false; 
		i = (byte)_i;
		j =(byte)_j;
		anim = 0;
		star = 100;
		candy = (byte)M.mRand.nextInt(25);
	}
	void reset()
	{
		CBubble = -1;
		checkFall = visited = false; 
	}
	void copy(CBubble ball) {
		CBubble = ball.CBubble;
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
