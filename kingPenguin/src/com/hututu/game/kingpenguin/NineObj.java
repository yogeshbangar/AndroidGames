package com.hututu.game.kingpenguin;

public class NineObj {
	
	int no[];
	int tile;
	float x,y;
	public NineObj(int n)
	{
		if(n ==3)
			x = -.4f*1.0f;
		else
			x = -.4f*1.5f;
		no = new int[n];
		tile = 0;
	}
	void set(float _y,byte _no[])
	{
		y = _y;
		for(int i=0;i<no.length;i++){
			no[i] = _no[i];
		}
		
	}
}
