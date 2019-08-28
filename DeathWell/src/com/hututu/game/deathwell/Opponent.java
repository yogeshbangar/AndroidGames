package com.hututu.game.deathwell;

public class Opponent {
	boolean change = false;
	boolean place = false;
	
	int mPath;
	int mCount;
	int ang;
	
	float mul=2;
	
	
	void set(int Path,int count){
		mPath = Path;
		mCount = count;
		mul=2;
		ang =0;
		change = false;
		place = false;
	}
	void Update(int i){
		mCount-=M.SPD;
		if(mCount<0){
			mCount =M.ARR-1;
			if(GameRenderer.mStart.mGR.Cir >M.DIS){
				if(i ==0)
				{
					change = GameRenderer.mStart.mGR.mOpp[1].change;
				}else if(M.mRand.nextBoolean())
					change = false;
				
			}else{
				if (i == 2){
					change = false;//M.mRand.nextBoolean();
					place = M.mRand.nextBoolean();
				}
				change = GameRenderer.mStart.mGR.mOpp[2].change;
				place = GameRenderer.mStart.mGR.mOpp[2].place;
			}
		}
		
		
		if (mPath == 0) {
			if(mul>1.9){
				mul -= 0.04f;
				if(mul < 1.9)
					mul =1.9f;
			}
		}else{
//			mul=2.42f;
			if(mul<2.42){
				mul += 0.04f;
				if(mul > 2.42)
					mul =2.42f;
			}
		}
		
		if (!change && ((place && mCount<400)||(!place && mCount<100))) {
			change = true;
			if (mPath == 0) {
				mPath = 1;
			} else
				mPath = 0;
		}
		angle();
	}
	
	void angle()
	{
		int inc =M.SPD;
		if ((mCount >= 22 && mCount <= 102)||(mCount >= 0 && mCount <= 21) || mCount >= 642)
		{
			if(ang <350)
			{
				ang+=inc;
				if(ang>=350)
					ang = 350;
			}
		}
		if (mCount >= 103 && mCount <= 281)
		{
			if(ang <180){
				ang+=inc;
				if(ang>=180)
					ang = 180;
			}
		}
		if ((mCount >= 282 && mCount <= 368)||(mCount >= 369 && mCount <= 376))
		{
			if(ang <270){
				ang+=inc;
				if(ang>=270)
					ang = 270;
			}
		}
		if (mCount >= 377 && mCount <= 462)
		{
			if(ang <335){
				ang+=inc;
				if(ang>=335)
					ang = 335;
			}
		}
		if (mCount >= 463 && mCount <= 641){
			if(ang <360 && ang!=0){
				ang+=inc;
				if(ang>=360)
					ang = 0;
			}
		}
		
	}
}
