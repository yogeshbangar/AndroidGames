package com.hututu.game.bubblecandyunlimited;

//0 : 10,126,221 //.04f,.50f,.87f
//1 : 238,204,11 //.94f,.80f,.44f
//2 : 188,60,21	 //.74f,.24f,.80f
//3 : 70,250,70  //.27f,.98f,.27f
//4 : 170,125,100//.67f,.49f,.39f
//5 : 240,50,240 //.94f,.20f,.94f
//6 : 180,180,180//.70f,.70f,.70f
//7 : 255,255,255//1.0f,1.0f,1.0f

public class RCandy {
	float x, y;
	float ny;
	float vx,vy;
	int color;
	int count = 0;
	int candy =0;
	int water =0;
	int score = 100;
	void reset() {
		if(count<=0)
			x = y = -100;
	}

	void resetAll() {
		
			x = y = -100;
	}
	
	public RCandy()
	{
		ny = -.83f-M.mRand.nextInt(100)*.001f;
	}
	void set(float _x, float _y, int Color, int cunt, int _candy,int _score) {
		x = _x;
		y = _y;
		color = Color;
		count = cunt;
		vy = M.mRand.nextInt(4) * .01f + .01f;
		vx = (M.mRand.nextBoolean()?1:-1)*M.mRand.nextInt(4) * .01f;
		candy=_candy;
		if (count > 2999)
			count =10000;
		score = _score;
	}

	void update() {
		if (count > 2999) {
			if (count < 10000) {
				y += vy;
				x += vx;
				if (y < -1)
					count = -1;
				vy -= .005f;
				if(x<-.9f||x>0.9f)
					vx = -vx;
			}
		}
		count--;
		if (y < -.8)
			water = 12;
	}
}
