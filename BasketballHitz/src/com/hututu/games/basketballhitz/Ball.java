package com.hututu.games.basketballhitz;

public class Ball {
	
	boolean move = false;

	byte rev = 0;

	int Bounce = 0;
	int collide = 0;
	int anim = 0;
	int mBalls;
	int type;
	int Drap;
	int combo;
	int xtime;
	int tno;
	int bani;

	float x, y, z;
	float sx, sy;
	float vx, vy, vz;
	float ang;
	float tx, ty;
	
	void update() {
		if (move) {	
			x += vx;
			y += vy;
			sx += vx;
			if (rev < 3) {
				if (z > .35f && rev != 1)
					z += vz;
				if (vy > 0) {
					sy += vy * (.75f/*+speed*/);
					if(sy>y)
						sy=y-.05f;
				}
				if (sy > y && vy < 0) {
					vy = -vy*.5f;
					Bounce++;
					M.sound7(GameRenderer.mContext, R.drawable.ground_bounce);
				}
				vy -= .002f;
				if (y < -1) {
					move = false;
					sx = x = vx = vy = vz = 0;
					z = 1;
					sy = y = -.9f;
				}
				if (z < .51 && rev == 0) {
					if (CircRectsOverlap(0, .63f, .135, .0185, x, y, .1f)) 	{
						if(((ang > 3.198 && ang < 3.202)||(ang > 3.048 && ang < 3.058))&& type !=4)
						{
							M.sound9(GameRenderer.mContext, R.drawable.hoop_hit);
							rev = 3;
							vx = vx*8f;
							vz = .001f;
							vy = Math.abs(vy)*2;
						}else{
							rev = 1;
							anim =8;
							combo++;
							if(type!=4)
								GameRenderer.mStart.mGR.root.score();
						}
					} else if(y>.85f){
						rev = 2;
					}else {
						rev = 3;
						vz = .002f;
						vy = Math.abs(vy) / 2;
						collide = 5;
						sy = .5f;
						if (!CircRectsOverlap(0, .7f, .335, .15, x, y, .1f)) {
							z-=.1f;
						}
					}
					if((type == 1 || type > 2) && rev != 1){
						Drap++;
						if(mBalls>0){
							mBalls--;
							bani = 10;
							M.sound2(GameRenderer.mContext, R.drawable.ball_less);
						}
					}
					if(type == 4 && CircRectsOverlap(tx, ty, .10f, .08, x, y, .05f)){
						GameRenderer.mStart.mGR.root.score();
						tx =-100;
						M.sound11(GameRenderer.mContext, R.drawable.target_shoot);
					}
					if (type != 4) {
						if (rev == 3 && CircRectsOverlap(0, .7f, .4, .3, x, y, .05f)) {
							M.sound5(GameRenderer.mContext, R.drawable.board_hit);
						}else
							M.sound14(GameRenderer.mContext, R.drawable.wall_hit);
					}
					if (rev != 1) {
						combo = 0;
					}
				}
			}else {
				if (z < 1)
					z += vz;
				if (/*y > sy && */vy >0) 
				{
					sy -= vy*.2f;
				}else{
					sy += vy*.15f;
				}
				if (vy < 0) {
					if(y < 0.26 && Bounce == 0 ||
							y < 0.16 && Bounce == 1 ||
							y < -.06 && Bounce == 2){
					vy = -vy*.8f;
//					sy =y;
					Bounce++;
					M.sound8(GameRenderer.mContext, R.drawable.ground_bounce2);
					}
				}
				vy -= .002f;
				if (y < -1) {
					move = false;
					sx = x = vx = vy = vz = 0;
					z = 1;
					sy = y = -.9f;
				}
			}
			if ((Bounce > 1 || x<-1.1 || x>1.1) || (rev==2 && y<.4f)) {
				reset();
				setTarget();
				xtime = M.mRand.nextInt(3);
				if(type == 1 || type > 2){
					if(mBalls<=0)
						GameRenderer.mStart.mGR.root.gameOver();
				}
			}
			if(sy>y)
				sy=y-.05f;
			if(sy>.4f)
				sy=.4f;
		}
		
		if (anim > 0 && y <.56f){
			anim--;
			if (anim == 1) {
				M.sound3(GameRenderer.mContext, R.drawable.goal);
				if (type == 0) {
					GameRenderer.mStart.mGR.gametime += 1000 * (xtime + 3);
					GameRenderer.mStart.mGR.mTimeAni.set(0, .4f, xtime < 2 ? (xtime + 2) : (xtime + 3));
					xtime = 3;
					M.sound12(GameRenderer.mContext, R.drawable.time_collect);
				}
				if (combo > 2)
				{
					GameRenderer.mStart.mGR.mComboAni.set(1, .4f, combo);
				}
			}
		}
	}

	void set(double _ang, double dis) {
//		System.out.println(_ang);
		if(_ang<3.21 && _ang>3.08){
			_ang = 3.14;
		}
		
		if(_ang>=3.21 && _ang<3.31){
				_ang = 3.20;
		}
		if(_ang< 3.08 && _ang>2.98){
			_ang = 3.05;
		}
//		_ang = 3.05;
		ang = (float)_ang;
//		System.out.println(ang);
		reset();
		vx = (float) (Math.sin(_ang) * .03 * 5 * (.3f+.1*dis));
		vy =-(float) (Math.cos(_ang) * .05 * 5 * (.3f+.1*dis));
		vz = -.014f;
		move = true;
	}
	
	
	void reset() {
		move = false;
		collide = 0;
		sx = x = vx = vy = vz = 0;
		z = 1;
		sy = y = -.85f;
		rev = 0;
		Bounce = 0;
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;
    }
	byte getRev()
	{
		return rev;
	}
	void setTarget()
	{
		tx = -.32f + M.mRand.nextInt(64) * .01f;
		ty = 0.62f + M.mRand.nextInt(20) * .01f;
		tno = M.mRand.nextInt(2);
	}
}
