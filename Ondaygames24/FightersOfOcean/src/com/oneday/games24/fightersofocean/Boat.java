package com.oneday.games24.fightersofocean;

public class Boat {
	
	float x,y,vx;			//vx Opp Boat Speed
	int mLife;				//Life of Opponent
	int mNo;				//Boat Number
	void set(float _x,float _y,int NO)
	{
		x = _x;
		y = _y;
		vx = .003f;
		mNo = NO;
		mLife = 6+(mNo*M.LS);
		M.mOppSet++;
	}
	void update(boolean life)
	{
		if(x<0)
			x += vx;
		if(x>-.1 && life)
			M.PlayerLife-=(mNo/2f+1);
	}
	void replace(Boat boat)
	{
		x = boat.x;
		y = boat.y;
		vx = boat.vx;
		mLife = boat.mLife;
		mNo = boat.mNo;
	}
}
