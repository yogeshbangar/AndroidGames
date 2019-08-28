package com.hututu.game.trafficracing;

public class Player {
	float x,y;
	float fule;
	float boost;
	float Dis;
	float disBar;
	boolean isBoost;
	int d;
	int start=0;
	int Gun;
	int damege;
	int carNo =0;
	int buy[] 	= {0,1000,2000,5000};
	int power[][]	= { {1,1,1,2},{1,1,1,3},{1,1,1,4},{1,1,1,5}};
	private int bc = 0;
	int fualSound = 0;
	void set(float _x, float _y)
	{
		if(buy[carNo] != 0)
		{
			for(int i=buy.length-1;i>=0;i--)
				if(buy[i] == 0){
					carNo = i;
					break;
				}
				
		}
		x =_x;
		y = _y;
		fule  = power[carNo][0];
		boost = power[carNo][1];
		Gun	  = power[carNo][2]*10;
		damege= power[carNo][3];
		isBoost = false;
		Dis = 0;
		d = 0;
		fualSound = 0;
	}
	void update()
	{
		if(d == 1)
			y-=.03f;
		if(d == 2)
			y+=.03f;
		if(fule>0)
			fule-=(.001f)/(carNo+1);
		if(M.uLevel==1)
			Dis-=(M.SPEED*.01f)*(1.2f);
		else
			Dis-=(M.SPEED*.01f)/(M.uLevel);
		if(isBoost&&boost <=0 && M.SPEED != 0){
			isBoost = false;
			M.SPEED = M.CSPEED;
		}
		if(isBoost){
			M.SPEED = M.CSPEED*3;
			boost-=(.01f)/(carNo+1);
			if(bc%100==0)
				M.sound1(GameRenderer.mContext, R.drawable.boost);
			bc++;
		}
		else
		{
			bc=0;
			M.sound1P();
		}
	}
	void blast()
	{
		fule-=(.01f)/(carNo+1);
		if(fule<0)
			fule =0;
		y = 0;
		start =20;
		damege--;
	}
	void reset()
	{
		x		=-.5f;
		y		= 0;
		fule	= 0;
		boost	= 0;
		Dis		= 0;
		isBoost	= false;
		d		= 0;
		start	= 0;
		Gun 	= 0;
		carNo 	= 0;
		int bu[]= {0,1000,2000,5000};
		for(int i=0;i<buy.length;i++)
			buy[i] = bu[i];
		int wer[][]	= { {1,1,1,2},{1,1,1,3},{1,1,1,4},{1,1,1,5}};
		for(int i=0;i<power.length;i++)	{
			for(int j=0;j<power[i].length;j++){
				power[i][j] = wer[i][j]; 
			}
		}
	}
}
