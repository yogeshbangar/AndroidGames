package com.htt.game.monkeyeatbananas;

import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {
	int sel = 0;
	GameRenderer mGR = null;
	int _keyCode = 0;
	int Counter = 0;
	int Count2 = 0;
	float sx, sy;

	public void setting() {
		float ud = .01f;
		switch (_keyCode) {
		case 1:
			sy -= ud;
			break;
		case 2:
			sy += ud;
			break;
		case 3:
			sx -= ud;
			break;
		case 4:
			sx += ud;
			break;
		}
		System.out.println(M.GameScreen + "~~~~~~~~~~~~~~~      " + sx
				+ "~~~~~~~~~~~~       " + sy);
	}

	public boolean Handle(MotionEvent event) {
		if (CircRectsOverlap(-.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			_keyCode = 3;
		if (CircRectsOverlap(0.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			_keyCode = 4;
		if (CircRectsOverlap(-.0f, -.8f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			_keyCode = 1;
		if (CircRectsOverlap(0.0f, 0.8f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			_keyCode = 2;
		if (event.getAction() == MotionEvent.ACTION_UP)
			_keyCode = 0;
		return true;
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60) {
				M.GameScreen = M.GAMESARNDI;
			}
			break;
		case M.GAMESARNDI:
			Draw_Sarndi(gl);
			break;
		case M.GAMEROKO:
			Draw_Roko(gl);
			break;
		case M.GAMEOVER:
			Draw_KhelKhatam(gl);
			break;
		case M.GAMEPLAY:
			Draw_MonkeyGame(gl);
			break;
		}

		Counter++;
//		 setting();
	}

	public boolean TouchEvent(MotionEvent event) {

		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMESARNDI:
			Handle_Sarndi(event);
			break;
		case M.GAMEROKO:
			Handle_Roko(event);
			break;
		case M.GAMEOVER:
			Handle_KhelKhatam(event);
			break;
		case M.GAMEPLAY:
			Handle_MonkeyGame(event);
			break;
		}
		Handle(event);
		return true;
	}

	void Draw_Sarndi(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[0], 0, 0);
		DrawDark(gl, mGR.mTex_BG[0], 0, 0);

		DrawTexture(gl, mGR.mTex_text, 0, 0.6f);

		DrawTransScal(gl, mGR.mTex_Playbig, 0, 0.1f, sel == 1 ? 1.1f : 1f,
				sel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_fb, -.60f, -.3f, sel == 2 ? 1.1f : 1f,
				sel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_rate, -.20f, -.3f, sel == 3 ? 1.1f : 1f,
				sel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_music[M.setMusic ? 1 : 0], 0.20f, -.3f,
				sel == 4 ? 1.1f : 1f, sel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_sound[M.setValue ? 1 : 0], 0.60f, -.3f,
				sel == 5 ? 1.1f : 1f, sel == 5 ? 0.5f : 1);
//		DrawTransScal(gl, mGR.mTex_fb, 0, -.57f, sel == 6 ? 1.1f : 1f,
//				sel == 6 ? 0.5f : 1);
		
		
		DrawTransScal(gl, mGR.mTex_leader, -.30f, -.570f, sel == 6 ? 1.1f : 1f, sel == 6 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Achiv, 0.30f, -.570f, sel == 7 ? 1.1f : 1f, sel == 7 ? 0.5f : 1);
	}

	boolean Handle_Sarndi(MotionEvent event) {
		sel = 0;
		if (CircRectsOverlap(0, 0.1, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 1;
		}
		if (CircRectsOverlap(-.60f, -.3f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 2;
		}
		if (CircRectsOverlap(-.20f, -.3f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 3;
		}
		if (CircRectsOverlap(0.20f, -.3f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 4;
		}
		if (CircRectsOverlap(0.60f, -.3f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 5;
		}
		if (CircRectsOverlap(-.3f, -.57f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 6;
		}
		if (CircRectsOverlap(0.3f, -.57f, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 7;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.playtheme);
				break;
			case 2:
				facebook();
				break;
			case 3:
				RateUs();
				break;
			case 4:
				M.setMusic = !M.setMusic;
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();// Leader-Board
				break;
			case 7:
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			}
			M.sound5(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	void Draw_Roko(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[0], 0, 0);
		DrawDark(gl, mGR.mTex_BG[0], 0, 0);

		DrawTransScal(gl, mGR.mTex_leader, -.40f, 0.00f, sel == 1 ? 1.1f : 1f,
				sel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_home, 0.00f, 0.00f, sel == 2 ? 1.1f : 1f,
				sel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Play, 0.40f, 0.00f, sel == 3 ? 1.1f : 1f,
				sel == 3 ? 0.5f : 1);

	}

	boolean Handle_Roko(MotionEvent event) {
		sel = 0;
		if (CircRectsOverlap(-.4, 0, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 1;
		}
		if (CircRectsOverlap(0.0, 0, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 2;
		}
		if (CircRectsOverlap(0.4, 0, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 3;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 2:
				M.GameScreen = M.GAMESARNDI;
				break;
			case 3:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.playtheme);
				break;
			}
			M.sound5(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	void Draw_KhelKhatam(GL10 gl) {
		
		DrawTexture(gl, mGR.mTex_BG[0], 0, 0);
		DrawDark(gl, mGR.mTex_BG[0], 0, 0);

		DrawTexture(gl, mGR.mTex_score, 0, -.26f);

		DrawTexture(gl, mGR.mTex_Lvl, -.22f, -.14f);
		drawNumber(gl, ":" + mGR.mLvl, 0.10f,-.14f);

		DrawTexture(gl, mGR.mTex_BnnTxt, -.22f, -.26f);
		drawNumber(gl, ":" + mGR.mScore, 0.10f, -.26f);

		DrawTexture(gl, mGR.mTex_Best, -.22f, -.38f);
		drawNumber(gl,":"+mGR.mHScore, 0.10f, -.38f);

		DrawTransScal(gl, mGR.mTex_retry , 0.00f, -.58f, sel == 1 ? 1.1f : 1f, sel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_leader, -.50f, -.58f, sel == 6 ? 1.1f : 1f, sel == 6 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Achiv , 0.50f, -.58f, sel == 7 ? 1.1f : 1f, sel == 7 ? 0.5f : 1);
		
		DrawTransScal(gl, mGR.mTex_share ,-.6f, -.85f, sel == 2 ? 1.1f : 1f, sel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_rate	 ,-.2f, -.85f, sel == 3 ? 1.1f : 1f, sel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_home	 ,0.2f, -.85f, sel == 4 ? 1.1f : 1f, sel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_ads	 ,0.6f, -.85f, sel == 5 ? 1.1f : 1f, sel == 5 ? 0.5f : 1);

	}

	boolean Handle_KhelKhatam(MotionEvent event) {
		sel = 0;
		if (CircRectsOverlap(0, -.58, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 1;
		}
		for (int i = 0; i < 4; i++) {
			if (CircRectsOverlap(-.6f + .4f * i, -.85f,
					mGR.mTex_ads.width() * .4f, mGR.mTex_ads.Height() * .4f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					0.02f)) {
				sel = i+2;
			}
		}
		if (CircRectsOverlap(-.5, -.58, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 6;
		}
		if (CircRectsOverlap(0.5, -.58, mGR.mTex_ads.width() * .4f,
				mGR.mTex_ads.Height() * .4f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), 0.02f)) {
			sel = 7;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.playtheme);
				break;
			case 2:
				share2friend();
				break;
			case 3:
				RateUs();
				break;
			case 4:
				M.GameScreen = M.GAMESARNDI;
				break;
			case 5:
				mGR.mInApp.onBuyGold10000(null);// Ads Free
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 7:
				GameRenderer.mStart.onShowAchievementsRequested();// Achive
				break;
			}
			M.sound5(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	void gameEnd() {
		if (mGR.spd < .4f)
			mGR.spd += .01f;
		mGR.mBGY += mGR.spd * .3f;

		if (mGR.spd > .1f && mGR.spd < .12f) {
			for (int i = 0; i < mGR.mPrati.length; i++) {
				if (i < mGR.noObject)
					mGR.mPrati[i].set((M.mRand.nextInt(180) - 90) * .01f,
							mGR.mBGY - .85f, mGR.mBGY);
				else
					mGR.mPrati[i].set((M.mRand.nextInt(180) - 90) * .01f,
							mGR.mBGY + 1.85f, mGR.mBGY);
			}
		}
		for (int i = 0; i < mGR.mDanda.length; i++) {
			mGR.mDanda[i].y += mGR.spd;
		}
		for (int i = 0; i < mGR.mDanda.length; i++) {
			if (mGR.mDanda[i].y > 1.5)
				mGR.mDanda[i]
						.set(mGR.mDanda[(i == 0 ? mGR.mDanda.length : i) - 1].y
								- mGR.mTex_Danda.Height());
		}
		for (int i = 0; i < mGR.mPrati.length; i++) {
			mGR.mPrati[i].y += mGR.spd * .3f;
			// if(mGR.mPrati[i].y < -1.5)
			// mGR.mPrati[i].set(M.mRand.nextBoolean()?.2f:-.2f, mGR.mPrati[(i
			// == 0?mGR.mPrati.length:i)-1].y+1f);
		}
		for (int i = 0; i < mGR.mBanana.length; i++) {
			mGR.mBanana[i].y += mGR.spd;
			if (CirCir(mGR.mBanana[i].img % 2 == 0 ? -.14f : .14f,
					mGR.mBanana[i].y, mGR.mTex_Kela[0].width() * .3f,
					mGR.mBandar.x, mGR.mBandar.y,
					mGR.mTex_Bandar[0][0].width() * .1f)) {
				mGR.mBanana[i].show = false;
				M.sound2(R.drawable.eat);
			}
			// if(mGR.mBanana[i].y < -1.5)
			// mGR.mBanana[i].set(mGR.mBanana[(i ==
			// 0?mGR.mBanana.length:i)-1].y+.5f);
		}
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			mGR.mLeaf[i].y += mGR.spd;
			if (mGR.mLeaf[i].isShow
					&& CircRectsOverlap(mGR.mLeaf[i].x, mGR.mLeaf[i].y,
							mGR.mTex_Kela[0].width() * .3f,
							mGR.mTex_Kela[0].Height(), mGR.mBandar.x,
							mGR.mBandar.y, mGR.mTex_Bandar[0][0].width() * .1f)) {
				mGR.spd = mGR.lspd;
				// mGR.spd = -.01f;
				mGR.gameEnd = 0;
				M.PlayStop();
				M.faliing();
				mGR.mLeaf[i].isShow = false;
				for (int j = 0; j < mGR.mDanda.length; j++) {
					mGR.mDanda[j].set(-1 + j * mGR.mTex_Danda.Height());
				}
				for (int j = 0; j < mGR.mPrati.length; j++) {
					mGR.mPrati[j].set(M.mRand.nextBoolean() ? .2f : -.2f,
							1 + j * 1, mGR.mBGY);
				}
				for (int j = 0; j < mGR.mBanana.length; j++) {
					mGR.mBanana[j].set(j * .5f);
				}
			}
		}
		if (mGR.mBGY > 0) {
			mGR.mBGY = 0;
			Counter = 0;
			M.faliing();
		}

	}

	void MonkeyLogic() {
		if (mGR.spd > -.02f)
			mGR.spd -= .0001f;
		mGR.mBGY += mGR.spd * .1f;
		mGR.lspd = mGR.spd;

		if (mGR.mLvl < (int) (-mGR.mBGY)) {
			mGR.mLvl++;
		}

		if (mGR.obspd < 3)
			mGR.obspd += .001f;
		for (int i = 0; i < mGR.mDanda.length; i++) {
			mGR.mDanda[i].y += mGR.spd;
		}

		for (int i = 0; i < mGR.mDanda.length; i++) {
			if (mGR.mDanda[i].y < -1.5)
				mGR.mDanda[i]
						.set(mGR.mDanda[(i == 0 ? mGR.mDanda.length : i) - 1].y
								+ mGR.mTex_Danda.Height());
		}
		for (int i = 0; i < mGR.mPrati.length; i++) {
			mGR.mPrati[i].y += mGR.spd * mGR.obspd;
			if (mGR.mPrati[i].y < -1.5) {
				mGR.mPrati[i]
						.set(M.mRand.nextBoolean() ? .2f : -.2f,
								mGR.mPrati[(i == 0 ? mGR.mPrati.length : i) - 1].y + 1f,
								mGR.mBGY);
				mGR.noObject++;
			}

			if (CirCir(mGR.mPrati[i].x, mGR.mPrati[i].y,
					mGR.mTex_Kela[0].width() * .3f, mGR.mBandar.x,
					mGR.mBandar.y, mGR.mTex_Bandar[0][0].width() * .1f)) {
				// mGR.mPrati[i].show =false;
				for (int j = 0; j < mGR.mTakkar.length; j++) {
					mGR.mTakkar[j].set(mGR.mPrati[i].x, mGR.mPrati[i].y);
				}
				mGR.gameEnd = 1;
				M.Pause();
				M.sound1(R.drawable.crash);
				M.sound4(R.drawable.falling);
				for (int j = 0; j < mGR.mDanda.length; j++) {
					mGR.mDanda[j].set(1 - j * mGR.mTex_Danda.Height());
				}

			}
		}
		for (int i = 0; i < mGR.mBanana.length; i++) {
			mGR.mBanana[i].y += mGR.spd;
			if (mGR.mBanana[i].show
					&& CirCir(mGR.mBanana[i].img % 2 == 0 ? -.14f : .14f,
							mGR.mBanana[i].y, mGR.mTex_Kela[0].width() * .3f,
							mGR.mBandar.x, mGR.mBandar.y,
							mGR.mTex_Bandar[0][0].width() * .1f)) {
				mGR.mBanana[i].show = false;
				M.sound2(R.drawable.eat);
				mGR.mBcCollect.set(mGR.mBanana[i].img % 2 == 0 ? -.14f : .14f,
						mGR.mBanana[i].y);
				mGR.mScore++;
				mGR.mTotal++;
				if (mGR.mHScore < mGR.mScore)
					mGR.mHScore = mGR.mScore;
				if (mGR.mScore % 25 == 0) {

					for (int j = 0; j < mGR.mPartical.length; j++) {
						mGR.mPartical[j].set();
					}

					if (!mGR.mLeaf[0].isShow)
						mGR.mLeaf[0].set(mGR.mBanana[i].img % 2 == 0 ? -.25f
								: .25f, mGR.mBanana[i].y + .9f, true);
					else if (!mGR.mLeaf[1].isShow)
						mGR.mLeaf[1].set(mGR.mBanana[i].img % 2 == 0 ? -.25f
								: .25f, mGR.mBanana[i].y + .9f, true);
					else if (mGR.mLeaf[0].y > mGR.mLeaf[1].y)
						mGR.mLeaf[1].set(mGR.mBanana[i].img % 2 == 0 ? -.25f
								: .25f, mGR.mBanana[i].y + .9f, true);
					else
						mGR.mLeaf[0].set(mGR.mBanana[i].img % 2 == 0 ? -.25f
								: .25f, mGR.mBanana[i].y + .9f, true);
				}
			}
			if (mGR.mBanana[i].y < -1.5)
				mGR.mBanana[i]
						.set(mGR.mBanana[(i == 0 ? mGR.mBanana.length : i) - 1].y + .5f);
		}
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			mGR.mLeaf[i].y += mGR.spd;
			if (!mGR.mLeaf[i].isShow && mGR.mLeaf[i].y < -1.1
					&& mGR.mLeaf[i].x < 1.0) {
				mGR.mLeaf[i].x = 100;
			}
		}
	}

	void Draw_MonkeyGame(GL10 gl) {
		for (int i = 0; i < mGR.mTex_BG.length; i++) {
			if ((i * 2 + mGR.mBGY) > -2.1 && (i * 2 + mGR.mBGY) < 2.1) {
				if (i < 2)
					DrawTexture(gl, mGR.mTex_BG[i], 0, i * 2 + mGR.mBGY);
				else {
					DrawTextureS(gl, mGR.mTex_BG[i], 0, i * 2 + mGR.mBGY, 15, 1);
					DrawTexture(gl, mGR.mTex_Claud, 0, i * 2 + mGR.mBGY);
				}
			}
		}

		for (int i = 0; i < mGR.mBanana.length; i++) {
			if (mGR.mBanana[i].show)
				DrawTexture(gl, mGR.mTex_Kela[mGR.mBanana[i].img],
						mGR.mBanana[i].img % 2 == 0 ? -.14f : .14f,
						mGR.mBanana[i].y);
		}

		for (int i = 0; i < mGR.mDanda.length; i++) {
			DrawTexture(gl, mGR.mTex_Danda, 0, mGR.mDanda[i].y);
		}
		for (int i = 0; i < mGR.mPrati.length; i++) {
			DrawTexture(gl, mGR.mTex_Opp[mGR.mPrati[i].Obj], mGR.mPrati[i].x,
					mGR.mPrati[i].y);
		}
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			// if(mGR.mLeaf[i].isShow)
			DrawTexture(gl, mGR.mTex_Leaf[mGR.mLeaf[i].x > 0 ? 1 : 0],
					mGR.mLeaf[i].x, mGR.mLeaf[i].y);

		}

		if (mGR.gameEnd == 0)
			DrawTexture(gl, mGR.mTex_Bandar[mGR.mBandar.x > 0 ? 0 : 1][Count2
					% mGR.mTex_Bandar[0].length], mGR.mBandar.x, mGR.mBandar.y);
		else {
			if (mGR.mBGY < 0) {
				DrawTexture(gl, mGR.mTex_BanderFall[Count2 % 2], mGR.mBandar.x,
						mGR.mBandar.y);
			} else {
				DrawTexture(gl, mGR.mTex_Dead, mGR.mBandar.x,
						mGR.mBandar.y - .08f);
				DrawTexture(gl, mGR.mTex_Star[Count2 % mGR.mTex_Star.length],
						mGR.mBandar.x + .1f, mGR.mBandar.y);

			}
		}

		if (mGR.mBcCollect.vz < 1.1) {
			DrawTextureS(gl, mGR.mTex_Anim, mGR.mBcCollect.x, mGR.mBcCollect.y,
					mGR.mBcCollect.vz, mGR.mBcCollect.vz);
			mGR.mBcCollect.y += mGR.spd;
			mGR.mBcCollect.vz += .1f;
		}

		if (Counter % 2 == 0)
			Count2++;
		drawNumber(gl, mGR.mScore + "", -.9f, 0.9f);

		if (mGR.mPartical[0].vx != 0) {
			for (int i = 0; i < mGR.mPartical.length; i++) {
				mGR.mPartical[i].update();
				DrawTexture(gl, mGR.mTex_SBnn, mGR.mPartical[i].x,
						mGR.mPartical[i].y);
			}
		}

		if (mGR.gameEnd == 0) {
			MonkeyLogic();
		} else {
			for (int i = 0; i < mGR.mTakkar.length; i++) {
				if (mGR.mTakkar[i].update()) {
					DrawTexture(gl, mGR.mTex_SBnn, mGR.mTakkar[i].x,
							mGR.mTakkar[i].y);
				}
			}
			if (mGR.mBGY < 0) {
				gameEnd();
			}
			if (mGR.mBGY >= 0 && Counter > 50) {
				M.GameScreen = M.GAMEOVER;
				M.stop();
				GameRenderer.mStart.Achivment();
				if (mGR.AdcCount % 3 == 0) {
					if (mGR.AdcCount % 6 == 3)
						GameRenderer.mStart.ShowSmart();
					else
						GameRenderer.mStart.ShowInterstitial();
				}
				mGR.AdcCount++;
			}
		}
	}

	boolean Handle_MonkeyGame(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			mGR.mBandar.x = -mGR.mBandar.x;
			M.sound5(R.drawable.move);
		}
		return true;
	}

	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float zx,
			float zy) {
		Tex.drawScal(gl, x, y, zx, zy);
	}

	void DrawDark(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawDark(gl, x, y);
	}

	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}

	float screen2worldX(float a) {
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}

	boolean Rect2RectIntersection(float ax, float ay, float adx, float ady,
			float bx, float by, float bdx, float bdy) {
		ax -= adx / 2;
		ay += ady / 2;
		bx -= bdx / 2;
		by += bdy / 2;
		if (ax + adx > bx && ay - ady < by && bx + bdx > ax && by - bdy < ay) {
			return true;
		}
		return false;
	}

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2,
			double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2))
				+ ((cy1 - cy2) * (cy1 - cy2)));
		if (bVectMag < (r1 + r2))
			return true;
		return false;

	}

	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Uri uri = Uri.parse("android.resource://"+getClass().getPackage().getName()+"/drawable/share");
		Intent shareIntent = new Intent();
		shareIntent.setType("image/png");
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+ " " + mGR.mScore+ " "
								+ " and Best Score is "+mGR.mHScore+ " "
								+ "in  "+ GameRenderer.mContext.getResources().getString(R.string.app_name)
								+ ". Can you beat me..."
								+ "Let the battle commence!!! Download it now and let’s ROCK!!!!  "
								+ "https://play.google.com/store/apps/details?id=" + getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(
					shareIntent, "Share from"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	void drawNumber(GL10 gl, String strs, float x, float y/* ,int align */) {
		float dx = mGR.mTex_Font[0].width() * .5f;
		// if(align ==1)
		// x-=strs.length()*dx*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
}
