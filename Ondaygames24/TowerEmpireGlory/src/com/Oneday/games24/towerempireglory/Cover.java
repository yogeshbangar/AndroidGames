package com.Oneday.games24.towerempireglory;


public class Cover {
	float x,y;
	int power;
	void set(float _x,float _y,int _power){
		x = _x;
		y = _y;
		power = _power;
	}
}

/*
 
 
 
		float x = screen2worldX(event.getX()) - mGR.mBow.x;
		float y = screen2worldY(event.getY()) - mGR.mBow.y;
		mGR.mBow.radian = M.GetAngle(x, y);
		mGR.mBow.radian *= .78f;

		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.mSparkal.set(screen2worldX(event.getX()),
					screen2worldY(event.getY()), -1);
		}
		
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			for (int i = 0; i < mGR.mTex_Power.length; i++) {
				if (CirCir(.50f + .2f * i, -.88f,mGR.mTex_button[4].Height() * .3f,
						screen2worldX(event.getX()),screen2worldY(event.getY()), .01f)) {
					mGR.mCover.set(screen2worldX(event.getX()),screen2worldY(event.getY()), i+1);
				}
			}
		}
		if(mGR.mCover.power>0)
			mGR.mCover.set(screen2worldX(event.getX()),screen2worldY(event.getY()), mGR.mCover.power);
		
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mCover.power >0) {
			switch (mGR.mCover.power) {
			case 1:
				setFirePower(screen2worldX(event.getX())-2,2+screen2worldY(event.getY()));
				break;
			case 2:
				mGR.mFreez.set(screen2worldX(event.getX()),screen2worldY(event.getY()),-1);
				break;
			default:
				break;
			}
		 
		 
		}
		return true;
	
 
 
*/