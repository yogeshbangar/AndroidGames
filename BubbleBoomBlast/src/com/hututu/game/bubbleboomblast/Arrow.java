package com.hututu.game.bubbleboomblast;
public class Arrow {
	float x,y;
	byte dir;
	void set(float _x,float _y, byte _dir)
	{
		x =_x;
		y =_y;
		dir=_dir;
	}
	void update()
	{
		switch (dir) {
		case 0:
			x +=.04f;
			break;
		case 1:
			y +=.04f;
			break;
		case 2:
			x -=.04f;
			break;
		case 3:
			y -=.04f;
			break;
			}
	}
}
