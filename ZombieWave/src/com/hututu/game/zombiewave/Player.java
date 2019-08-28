package com.hututu.game.zombiewave;

public class Player {
	
	boolean BoostActive = false;
	boolean Run = false;
	boolean GameOn = false;

	int img = 0;
	int pahiye = 0;
	int kitNo = 0;
	int petrol = 0;
	int Boostfill = 0;
	int bullatefire = 0;
	int Engine = 0;
	int Gear = 0;
	int overCounter = 0;
	int Gunfill = 0;
	int citi = 1;

	float mDis = 0;
	float Speed = 0;
	float boostSpd = 0;
	float x = 0, y = 0, vx = 0;
	float p = 0;// Vibration
	float vp = 0;// Vibration vx
	float mBegin = -.50f;

	void set( float _x, float _y,float _Spd,int carSelect) {
		img = carSelect;
		Speed = 0;
		boostSpd = _Spd*5f-img*.002f;
		x = _x;
		y = _y;
		mDis = 0;
		vp = .01f;
		overCounter =0;
		GameOn = false;
		mBegin =-.5f;
		vx = .05f;
	}

	void update() {
		float max = -.02f  - (Engine * .001f) - (Gear * .001f) - (pahiye * .001f) - (img * .004f);
		mDis += Speed;
		if(GameOn){
			petrol--;
			if(petrol < 100)
				M.sound10(GameRenderer.mContext, R.drawable.low_fule);
		}
		if (bullatefire > 0)
			bullatefire-=2;
		if(mBegin>.51f){
			if (Speed < 0) {
				Speed += .008f;
				if (Speed > 0){
					Speed = 0;
				}
			}
		}
		else if (BoostActive && Boostfill > 1) {
			M.sound7(GameRenderer.mContext, R.drawable.boost);
			GameOn = true;
			Boostfill--;
			if (Speed > boostSpd) {
				Speed -= .01f;
				if (Speed > max)
					Speed = max;
			}
		} else if (Run || BoostActive) {	
			if(petrol > 50){
			if(img%3 == 0)
				M.sound9(GameRenderer.mContext, R.drawable.car0);
			if(img%3 == 1)
				M.sound9(GameRenderer.mContext, R.drawable.car1);
			if(img%3 == 2)
				M.sound9(GameRenderer.mContext, R.drawable.car2);
			}
			GameOn = true;
			if (Speed < max) {
				Speed += .001f;
			}else
				Speed -= .0005f;
		} else {
			if (Speed < 0) {
				Speed += .001f;
			}
			if (Speed > 0)
				Speed = 0;
		}
		if (Speed < -.001f) {
			p += vp;
			if (p > .006f)
				vp = -.002f;
			if (p < -.00f)
				vp = 0.002f;
		}
	}
	void Decrease(int Dec){
		Speed += 0.001 * Dec;
		int l = (int)(-(Engine * .35f) - (Gear * .35f) - (pahiye * .35f));
		int k =(int)((Dec * 5)-(img*.6f)+l);
		petrol -= k;
		
	}
	
	void Print(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		float max = -.02f  - (Engine * .002f) - (Gear * .002f) - (pahiye * .002f) - (img * .02f);
		System.out.println("[mDis = "+mDis+"][Speed = "+Speed+"] [boostSpd = "+boostSpd+ "] [x = "+x+"] [ y = "+y+"][ p ="+p+"][vp="+vp+"]");
		System.out.println("[img = "+img+"][pahiy = "+pahiye+"] [kNo = "+kitNo+ "] [ptrol = "+petrol+"] [ BoostF = "+Boostfill+"][ bullfire ="+bullatefire+"][citi="+citi+"][Engine = "+Engine+"][Gear = "+Gear+"] [oCont = "+overCounter+ "]");
		System.out.println("[BoostActive = "+BoostActive+"][Run = "+Run+"] [GameOn = "+GameOn+ "] [Gunfill = "+Gunfill+"][max = "+max+"]");
		
		
	}
	
	
}
