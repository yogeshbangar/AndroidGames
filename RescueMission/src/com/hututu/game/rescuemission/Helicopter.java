package com.hututu.game.rescuemission;

public class Helicopter {
	float x,y;
	int life;
	int save;
	int dir;
	void set(int _life)
	{
		x = .0f;
		y = .7f;
		life = _life;
		save =0;
	}
	void update()
	{
		if(dir==1&&x <1.0)
			x+=.05f;
		if(dir==2 && x >-.9)
			x-=.05f;
	}
}
