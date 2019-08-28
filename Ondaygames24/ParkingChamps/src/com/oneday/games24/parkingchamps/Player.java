package com.oneday.games24.parkingchamps;

public class Player {
	boolean gameWin;
	boolean glow;
	int angleno;
	int speedno;
	int mThukai = 0;
	int mBlast;
	
	float x,y,a;
	float x1,y1;
	float x2,y2;
	float pa;
	float ox,oy,oa;
	float vx,vy;
	float t,vt=.1f;
	void set(float _x,float _y,float _angle)
	{
		x = _x;
		y = _y;
		a = _angle;
		pa = 0;
		vx = (float)(Math.cos(Math.toRadians(a))*.006);
		vy = (float)(Math.sin(Math.toRadians(a))*.01);
		mBlast=100;
		gameWin = false;
		glow	= true;
		mThukai =speedno =angleno = 0;
		mBlast = 10;
	}
	void update()
	{
		if(gameWin)
			return;
		ox=x;oy=y;oa=a;
		switch (angleno) {
		case 3:
			if(pa<25)
				pa++;
			break;
		case 4:
			if(pa>-25)
				pa--;
			break;
		case 0:
			if(pa>0)
				pa--;
			if(pa<0)
				pa++;
			break;
		}
		switch (speedno) {
		
		case 1:
			a+=pa*.1f;
			vx = (float)(Math.cos(Math.toRadians(a+pa))*.006);
			vy = (float)(Math.sin(Math.toRadians(a+pa))*.01);
			x+=vx;
			y+=vy;
			break;
		case 2:
			a-=pa*.1f;
			vx = (float)(Math.cos(Math.toRadians(a+pa))*.006);
			vy = (float)(Math.sin(Math.toRadians(a+pa))*.01);
			x-=vx;
			y-=vy;
			break;
		
		}
		
	}
}
