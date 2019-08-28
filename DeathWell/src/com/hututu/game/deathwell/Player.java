package com.hututu.game.deathwell;

public class Player {
	int mPath=0;
	int mCount=0;
	int ang=0;
	
	float mul =2;
	
	void set(int Path,int count){
		mPath = Path;
		mCount = count;
		ang =0;
		mul =2;
	}

	void Update() {
		mCount += M.SPD;
		if (mCount >= M.ARR) {
			mCount = 0;
			GameRenderer.mStart.mGR.Cir++;
			GameRenderer.mStart.mGR.mTotal++;
			if(GameRenderer.mStart.mGR.Cir > GameRenderer.mStart.mGR.BestCir){
				GameRenderer.mStart.mGR.BestCir = GameRenderer.mStart.mGR.Cir;
			}
		}
		if (mPath == 0) {
			mul=1.9f;
		}else{
			mul=2.42f;
		}
		angle();
	}
	void angle()
	{
		int inc =M.SPD;
		if ((mCount >= 22 && mCount <= 102)||(mCount >= 0 && mCount <= 21) || mCount >= 642)
		{
			if(ang <165){
				ang+=inc;
				if(ang>=165)
					ang = 165;
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
