package com.hututu.games.crazydragracing;

public class Car {
	
	boolean Complete;
	boolean start;
	
	int mSpeed = 0;
	int mGear;
	int mNo;
	int pShift;
	int m60;
	int m100;
	int Rcount = 0;
	int racebonus;
	int launcebonus;
	int goodbonus;
	int perfect;
	int raceprice;
	int total;
	int NO2;
	
	long mTime;
	
	float x,y;
	float dx;
	float mCarSpeed =-.02f;
	float mRPM =0;
	float mCPower;
	float totalDis =0;
	
	
	void set(float _x, float _y,int No,float CPower,float RPM)
	{
		racebonus=launcebonus=goodbonus=perfect=raceprice=total=0;
		m100 = m60 = 0;
		pShift =0;
		mSpeed = 0;
		mGear = 1;
		x=_x;
		y=_y;
		dx =-.9f;
		mCarSpeed =-.0f;
		mRPM =RPM;
		mNo = No;
		mCPower = CPower;
		mTime = System.currentTimeMillis();
		start = Complete = false;
		Rcount =10;
		NO2 = M.NO3;
	}
	void update()
	{
		//Blue  135 to 160
		//Green 160 to 165
		//Red   166 to 180
		if(dx>.92f)
		{
			Complete = true;
			return;
		}
		if(mTime > 999999 && dx >=.90f)
		{
			mTime = System.currentTimeMillis() - mTime;
		}
		if(mSpeed>=60 && m60 ==0)
			m60 = (int)(System.currentTimeMillis() - mTime);
		
		if(mSpeed>=100 && m100 ==0)
		{
			m100 = (int)(System.currentTimeMillis() - mTime);
		}
		
		float val = (mRPM/100000f);
		if(mRPM<165)
			mCarSpeed-=(val*mCPower);
		if(NO2>0 && NO2<M.NO3)
		{
			mCarSpeed-=.0005f;
			NO2--;
		}
		
		dx -=(mCarSpeed*GameRenderer.mStart.mGR.mLvlLength);
		mSpeed = (int)Math.abs(mCarSpeed*200);
		
		switch (mGear) {
		case 1:
			mRPM +=1.0;
			break;
		case 2:
			mRPM +=0.4;
			break;
		case 3:
			mRPM +=0.3;
			break;
		case 4:
			mRPM +=0.2;
			break;
		case 5:
			mRPM +=0.1;
			break;
		case 6:default:
			mRPM +=0.08;
			break;

		}
		if(mRPM>170)
			mRPM = 170;
	}
	void Opponet(float spd)
	{
		x += spd-mCarSpeed;
		if(mRPM>165)
		{
			mRPM=110;
			mGear++; 
		}
			
	}
}
