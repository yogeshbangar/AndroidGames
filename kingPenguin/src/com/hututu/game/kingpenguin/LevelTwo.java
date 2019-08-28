package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import android.view.MotionEvent;

public class LevelTwo {
	boolean fall;
	
	byte st,mSel,Space,jumpCount,c1,wCount,Counter = 0;
	
	float Bx1, Bx2;
	float move, dis;
	float px, py, pvy;
	
	Step[] mStep;

	SimplePlane mTex_bg, mTex_BgTile, mTex_Bubbul, mTex_Bord[], mTex_BOne;
	SimplePlane mTex_Frog[], mTex_BTwo,mTex_JumpAnim[];
	GameRenderer mGR;
	public LevelTwo(GameRenderer _GameRenderer) {
		mGR =_GameRenderer;
		mTex_bg = GameRenderer.add("l2/bg.png");
		mTex_BgTile = GameRenderer.add("l2/water.png");
		mTex_Bubbul = GameRenderer.add("l2/bubble.png");
		mTex_Bord	= new SimplePlane[3];
		for(int i=0;i<mTex_Bord.length;i++)
			mTex_Bord[i] = GameRenderer.add("l2/"+i+".png");
		mTex_BOne = GameRenderer.add("l2/one_jump.png");
		mTex_BTwo = GameRenderer.add("l2/two_jump.png");
		Bitmap b = GameRenderer.LoadImgfromAsset("l2/jump.png");
		mTex_Frog = new SimplePlane[8];
		for (int i = 0; i < mTex_Frog.length; i++) {
			mTex_Frog[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
		}
		b.recycle();
		mTex_JumpAnim	= new SimplePlane[12];
		b = GameRenderer.LoadImgfromAsset("l2/water0.png");
		for(int i=0;i<8;i++){
			mTex_JumpAnim[i]= GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
		}
		b.recycle();
		b = GameRenderer.LoadImgfromAsset("l2/water1.png");
		for(int i=8;i<12;i++){
			mTex_JumpAnim[i]= GameRenderer.addBitmap(Bitmap.createBitmap(b,(i-8)*b.getWidth()/4, 0, b.getWidth()/4,b.getHeight(), null, true));
		}
		
		mStep = new Step[10];
		for (int i = 0; i < mStep.length; i++) {
			mStep[i] = new Step(0, true);
		}
		
//		GameResart();
	}

	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		set();
		M.stop();
	}

	void set() {
		jumpCount = (byte)mTex_JumpAnim.length;
		px = -.30f;
		py = -.24f;
		pvy = 0;
		Bx1 = 0;
		Bx2 = mTex_bg.width() * .96f;
		move = 0.01f;
		Space = 5;
		dis = st = 0;
		fall = false;
		for (int i = 0; i < mStep.length; i++) {
			mStep[i].set(-.3f + i * .24f, true,0);
			if(wCount%6==0)
				mStep[i].Watch = true;
			if (Space <= 0) {
				Space = (byte)(M.mRand.nextInt(5) + 2);
				mStep[i].isOn = false;
				mStep[i].Watch = false;
			}
			wCount++;
			Space--;
		}
		mStep[0].Watch = false;
		c1 = 10;
	}

	void gamelogic() {
		if (py > -.24f) {
			py += pvy;
			pvy -= .03f;
		} else {
			pvy = 0;
			py = -.24f;
		}

		if (c1 < 6) {
			for (int i = 0; i < mStep.length; i++) {
				mStep[i].x -= (move);
			}
			if (c1 < 6)
				dis -= (move);

			Bx1 -= (move / 2);
			Bx2 -= (move / 2);
			if (Bx1 < -2.5f)
				Bx1 = Bx2 + mTex_bg.width() * .96f;
			if (Bx2 < -2.5f)
				Bx2 = Bx1 + mTex_bg.width() * .96f;

			for (int i = 0; i < mStep.length; i++) {
				if (mStep[i].x < -1.2f) {
					mStep[i].set(mStep[i == 0 ? mStep.length - 1 : i - 1].x + .24f,true,(mGR.mScore/50)%3);
					if(wCount%(mGR.mScore/25+6)==0)
						mStep[i].Watch = true;
					if (Space <= 0) {
						Space = (byte)(M.mRand.nextInt(4) + 2);
						mStep[i].isOn = false;
						mStep[i].Watch = false;
					}
					wCount ++;
					Space--;
				}

			}
			c1++;
			if (c1 == 6) {
				if(!mStep[st].isOn){
					move = -.04f;
					c1 = 0;
					st--;
					if (st < 0)
						st = (byte)(mStep.length - 1);
					fall = true;
					mGR.mScore -= 1;
					jumpCount = 0;
					M.sound10(GameRenderer.mContext, R.drawable.l_2_penguin_in_water);
				}
				if(mStep[st].Watch){
					mGR.mLSelect.IncTime(2000);
					mStep[st].Watch = false;
				}
			}
		} else
			move = 0;

	}

	
	void Draw_GamePlay(GL10 gl) {
		Counter++;
		mTex_BgTile.drawScal(gl, 0, -.07f, 200, 1);
		Group.DrawTexture(gl, mTex_bg, Bx1, .35f);
		Group.DrawTexture(gl, mTex_bg, Bx2, .35f);
		for (int i = 0; i < mStep.length; i++) {
			if(mStep[i].x>-1.2f && mStep[i].x<1.2f){
				if (mStep[i].isOn){
					if(mStep[i].no==1)
						Group.DrawTexture(gl, mTex_Bord[mStep[i].no], mStep[i].x, -.40f);
					else
						Group.DrawTexture(gl, mTex_Bord[mStep[i].no], mStep[i].x, -.45f);
					if(i%3==0)
						Group.DrawTexture(gl, mTex_Bubbul, mStep[i].x, -.7f);
				}else if(jumpCount<mTex_JumpAnim.length && mStep[i].x > px-.05f && mStep[i].x<px+.25f){
					if(jumpCount<2)
						Group.DrawTexture(gl, mTex_Frog[jumpCount+6], mStep[i].x,-.35f);
					Group.DrawTexture(gl, mTex_JumpAnim[jumpCount%mTex_JumpAnim.length], mStep[i].x,-.35f);
					if(Counter%2==0)
						jumpCount++;
				}
				if (mStep[i].Watch)
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mStep[i].x, -.3f);
			}
		}
		if(jumpCount>=mTex_JumpAnim.length){
			if(c1>=0 && c1<6)
				Group.DrawTexture(gl, mTex_Frog[c1], px, py);
			else
				mGR.mLOne.Draw_AniChar(gl, px, py, (mGR.mLOne.ani/mGR.mLOne.FrogAnim.length)%3==0);
		}
		Group.DrawTransScal(gl, mTex_BOne ,-.80f, -.8f,mSel==1?1.15f:1,mSel==1?0.5f:1);
		Group.DrawTransScal(gl, mTex_BTwo ,0.80f, -.8f,mSel==2?1.15f:1,mSel==2?0.5f:1);
		mGR.mLSelect.GTime(gl);
		gamelogic();
	}

	boolean Handle_GamePlay(MotionEvent event) {
		mGR.mLSelect.ClockScreen(event);
		mSel = 0;
		if (move == 0 && Group.CircRectsOverlap(-.80f, -.8f, mTex_BTwo.width() * .4f,mTex_BTwo.Height() * .4f,Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()), .05f)) {
			mSel = 1;
		}
		if (move == 0 && Group.CircRectsOverlap(0.80f, -.8f, mTex_BTwo.width() * .4f,mTex_BTwo.Height() * .4f,Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()), .05f)) {
			mSel = 2;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
				performAction();
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else if(mGR.timesUp < 5 && mGR.ClockCount < 5 && (jumpCount>=mTex_JumpAnim.length)){
				performAction();
			}
			st %= mStep.length;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			
			mSel = 0;
		}
		return true;
	}
	void performAction()
	{
		switch (mSel) {
		case 1:
			py = -.23f;
			pvy = .1f;
			st += 1;
			dis = 0;

			move = 0.04f;
			c1 = 0;
			mGR.mScore += 1;
			M.sound1(GameRenderer.mContext, R.drawable.l_2_jump);
			break;
		case 2:
			py = -.23f;
			pvy = .1f;

			st += 2;
			dis = 0;

			move = 0.08f;
			c1 = 0;
			mGR.mScore += 2;
			mGR.Total[mGR.mLSelect.mGameSel]++;
			M.sound2(GameRenderer.mContext, R.drawable.l_2_second_jump);
			break;
		}
		mGR.Total[mGR.mLSelect.mGameSel]++;
		mGR.mLSelect.Achievement(fall,0);
		
		
	}
}
