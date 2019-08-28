package com.hututu.game.cricket.premier.league;

public class Ball {
	boolean goSedo;
	byte tye;
	byte pos =0;
	int hit = 0;
	int Run = 0;
	int RunTran =0;
	
	float x, y, z;
	float vx, vy, vz;
	float sedo;
	
	
	void setHit(float _x, float _y, int _side) {
		if (hit == 2)
			return;
		hit = 1;
		x = _x;
		y = _y;

		if (_side != 3) {
			vx = 0.005f + M.mRand.nextInt(10) * .003f;
		} else {
			vx = -(0.005f + M.mRand.nextInt(10) * .003f);
		}
		
		if(GameRenderer.mStart.mGR.mBatsman.img == 2)
			vx = 0.025f;
		
		vy = 0.01f + M.mRand.nextInt(15) * .005f;
		vz = -.008f;
		Run = 0;
		RunTran = 1000;
		if (vy >= .009f && vy < .019f) {
			Run = 1;
			RunTran = 0;
			if (_side != 3) {
				vx = 0.020f + M.mRand.nextInt(4) * .003f;
			} else {
				vx = -(0.020f + M.mRand.nextInt(4) * .003f);
			}
		}
		if (vy >= .019f && vy < .029f) {
			Run = 2;
			RunTran = 0;
			if (_side != 3) {
				vx = 0.020f + M.mRand.nextInt(4) * .003f;
			} else {
				vx = -(0.020f + M.mRand.nextInt(4) * .003f);
			}
		}
		if (vy >= .029f && vy < .050f) {
			Run = 3;
			RunTran = 0;
		}
		if (vy >= .050f && vy < .065f) {
			Run = 4;
			RunTran = 0;
		}
		if (vy >= .065f) {
			Run = 5;
			RunTran = 0;
		}
		if (tye == 1 && M.mRand.nextInt(5) == 0)
		{
			Run = 8;
			vy = .054f;
			vx = .013f;
		}
		if (tye == 4 && M.mRand.nextInt(5) == 0) {
			Run = 9;
			vy = .053f;
			vx = -.015f;
		}
		if (tye == 0 && M.mRand.nextInt(5) == 0) {
			Run = 11;
			vy = .054f;
			vx = .005f;
		}
		if (tye == 3 && M.mRand.nextInt(5) == 0) {
			Run = 10;
			vy = .054f;
			vx = -.005f;
		}
		if (tye == 6 && M.mRand.nextInt(3)==0){
			Run = 12;
			vy = -.062f;
			vx = 0.005f;
		}else{
			if(M.mRand.nextBoolean())
				M.sound1(R.drawable.ball_hit0);
			else
				M.sound2(R.drawable.ball_hit1);
		}
		
	}
	
	void set(float _x, float _y, int _hit) {
		hit = _hit;
		pos =0;
		if (hit == 0) {
			Run = 0;
			x = _x;
			y = _y;
			sedo = _y - .4f;
			z = .33f;
			vx = .005f;
			vy = -.04f;
			vz = .015f;
			goSedo = false;
			tye = (byte) M.mRand.nextInt(7);
//			tye =3;
			if (tye == 6 && M.mRand.nextInt(4)==0){
				Run = 7;
			}
		}
	}
	boolean isin() {
		if (x > -1 && x < 1 && y > -1 && y < 3){
			return true;
		}
		return false;
	}

	boolean update() {
		if(hit == 0)
			return hit0();
		if(hit == 1)
			return hit1();
		if(hit == 2)
			return hit2();
		return false;
	}

	boolean hit0() {
		if (x > -1 && x < 1 && y > -1 && y < 3) {
			z += vz;
			if (!goSedo) {
				switch (tye) {
				case 0:// ON
					y += vy;
					sedo += vy * .6f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					x += vx * .1f;
					break;
				case 1:// ON Site Bouncer (in-swing)
					y += vy;
					sedo += vy * .3f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					x += vx;
					break;
				case 2:// ON
					y += vy;
					sedo += vy * .6f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					x += vx;
					break;
				case 3:// OFF Site Ball
					y += vy;
					sedo += vy * .6f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					x -= vx;
					break;
				case 4:// OFF Site Bouncer (in-swing)
					y += vy;
					sedo += vy * .3f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					x -= vx;
					break;
				case 5:// State Bouncer
					y += vy;
					sedo += vy * .3f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					break;
				case 6:// Bold
				case 7:// Bold
					y += vy;
					sedo += vy * .7f;
					if (sedo > y - .04f) {
						goSedo = true;
					}
					// x += vx*.1f;
					break;
				}

			} else {
				switch (tye) {
				case 0:
					sedo += vy * 3f;
					y -= vy * .5f;
					x += vx;
					if (y > -.3f)
						reset(1);
					break;
				case 1:
					sedo += vy * 3f;
					y -= vy * .2f;
					x -= vx;
					if (y > 0.0f)
						reset(1);
					break;
				case 2:
					sedo += vy * 3f;
					y -= vy * .5f;
					x += vx;
					if (y > -.3f)
						reset(1);
					break;
				case 3:
					sedo += vy * 3f;
					y -= vy * .5f;
					x += vx;
					if (y > -.3f)
						reset(1);
					break;
				case 4:
					sedo += vy * 3f;
					y -= vy * .2f;
					x += vx;
					if (y > 0.0f)
						reset(1);
					break;
				case 5:
					sedo += vy * 3f;
					y -= vy * .4f;
					if (y > 0.1f)
						reset(1);
					break;
				case 6:
				case 7:
					sedo += vy * 3f;
					y -= vy * .5f;
					x -= vx * .1f;
					z -= vz;
					if (y > -.6f)
						reset(1);
					break;
				}
			}
			return true;
		}
		return false;
	}
	boolean hit1() {
		if (x > -1 && x < 1 && y > -1 && y < 3){
			if(tye ==6 && Run == 12){
				if(x <.15){
					x+=vx;
					y+=vy;
					z+=vz;
					if(y>sedo)
						sedo += Math.abs(vy * .9f);
					else
						y=sedo;
					vy -=.001f;
					if((vy<=0 && y<.0f))
						vy =.005f;
					if (z < 0 || (Run == 5 && y < 0.1f && vy < 0))
						reset(0);
				}
				else{
					sedo =y;
					if(RunTran>20)
						reset(0);
				}
			}else{
				x+=vx*2;
				y+=vy*2;
				z+=vz*2;
				if(y>sedo)
					sedo += Math.abs(vy * 1.6f);
				else
					y=sedo;
				vy -=.0018f*2;
				if((vy<=0 && y<.0f))
					vy =.005f;
				if((tye == 1 || tye == 4) && Run > 7 && vy <0 && y < 0.1f)
					reset(0);
				if((tye == 0|| tye == 3) && Run > 7 && vy <-.01)
					reset(0);
				if (z < 0 || (Run == 5 && y < 0.1f && vy < 0))
					reset(0);
			}
			return true;
		}
		return false;
	}
	boolean hit2() {
		if (x > -1 && x < 1 && y > -1 && y < 3) {
			x += vx;
			z += .04f;
			if (z > 2.0f)
				reset(0);
			return true;
		}
		return false;
	}
	void reset(int type) {
		if(type !=1)
			x = y = 100;
		else
			hit =2;
	}
}
