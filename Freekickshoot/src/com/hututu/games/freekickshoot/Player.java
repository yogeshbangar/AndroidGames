package com.hututu.games.freekickshoot;

public class Player {
	
	boolean move = false;
	boolean pmove = false;
	
	int pno;
	int anim =0;
	int pani =0;
	int rset = 0;
	
	
	float bx, by,bz;
	float bvx, bvy;
	float bgx;//.79f //-.79f
	float sx,sy;
	float ang;
	float px[] = new float[3];
	float py[] = new float[3];
	
	
	
	GameRenderer mGR;
	public Player(GameRenderer GR) {
		mGR = GR;
	}

	void reset(float _x, float _y) {
		px[0] = _x;
		py[0] = _y;
		px[1] = _x - .3f;
		py[1] = _y;
		px[2] = _x + .3f;
		py[2] = _y;
		sx = bx = _x;
		sy = by = _y + .2f;
		bz =1;
		pmove = move = false;
		rset =0;
	}

	void set(float _ang) {
		ang=_ang;
		move = false;
		if (ang > 3.35)
			pno = 2;
		else if (ang < 2.95)
			pno = 1;
		else
			pno = 0;
		pmove = true;
		pani = 0;

	}

	void set() {
		bvx = (float) (Math.sin(ang) * .042);
		bvy =-(float) (Math.cos(ang) * .045);
		move = true;
		pmove =false;
		M.sound4(GameRenderer.mContext, R.drawable.kick);
	}

	void update() {
		if (move) {
			if (by > .3f && bvy > 0) {
				if (CircRectsOverlap(bgx, .36f, .26f, .06f, bx, by, .03)) {
					bvy = -Math.abs(bvy);
					System.out.println(mGR.Level>25?25:mGR.Level);
					if (!CircRectsOverlap(mGR.mKeeper.x, mGR.mKeeper.y, 
							mGR.mTex_Opp[0].width() *.5f*.05f*(mGR.Level>25?25:mGR.Level),
							mGR.mTex_Opp[0].Height()*.5f*.05f*(mGR.Level>25?25:mGR.Level),
							bx, by, .03)) 
					{
						mGR.root.SetAnim();
						anim = 10;
						mGR.Goal = true;
						M.sound3Play(GameRenderer.mContext, R.drawable.goal);
					}else{	
						M.sound5(GameRenderer.mContext, R.drawable.miss);
					}
				} else if (by > .4f) {
					bvy = -Math.abs(bvy);
					M.sound5(GameRenderer.mContext, R.drawable.miss);
				}
			}
			if (bvy > 0) {
				bx += bvx;
				by += bvy;
				bz -= .012f;
				if (by < 0) {
					sx += bvx * .8f;
					sy += bvy * .8f;
				} else {
					sx += bvx * 1.3f;
					sy += bvy * 1.3f;
				}
			} else {
				bz += .012f;
				bx += bvx;
				if (!mGR.Goal) {
					by += bvy;
					if (by < 0.1) {
						sx = bx;
						sy = by;
						move = false;
					} else {
						sx += bvx * 1.2f;
						sy += bvy * 1.2f;
					}
				}else{
					by += bvy/2;
					if (by < 0.2) {
						sx = bx;
						sy = by;
						move = false;
					} else {
						sx += bvx * 1.4f;
						sy += bvy * 1.4f;
					}
				
				}
			}
			rset = 1;
		}else if(rset>0){
			rset++;
			if(rset>20 || bx >1.3f || bx <-1.3f)
				mGR.reset();
		}
		if (pmove && pani < 7) {
			py[pno] += .013f;
			switch (pno) {
			case 1:
				px[pno] += .013f;
				break;
			case 2:
				px[pno] -= .013f;
				break;
			default:
				break;
			}

		}
		if (anim > 0)
			anim--;
	}
	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY, double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}
}
