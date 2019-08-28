package com.hututu.games.monkeyjump;

public class Tile {
	int hardle = 0;
	float x,y;
	void set(float _x,float _y,int hrdle){
		x = _x;
		y = _y;
		hardle = hrdle;
	}
	void setNew(float _y,int hard){
		y =_y;
		hardle = hard;
	}
}
