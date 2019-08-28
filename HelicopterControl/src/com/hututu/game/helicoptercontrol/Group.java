package com.hututu.game.helicoptercontrol;

import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {

	GameRenderer mGR = null;
	int Counter = 0;
	int Score = 0;
	final int Check = 100;

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	// public void setting()
	// {
	// switch (GameRenderer.mStart._keyCode) {
	// case KeyEvent.KEYCODE_DPAD_DOWN:
	// sy-=.01f;
	// break;
	// case KeyEvent.KEYCODE_DPAD_UP:
	// sy+=.01f;
	// break;
	// case KeyEvent.KEYCODE_DPAD_LEFT:
	// sx-=.01f;
	// break;
	// case KeyEvent.KEYCODE_DPAD_RIGHT:
	// sx+=.01f;
	// break;
	// }
	// Log.d("~~~~~~~~~~~~~~~      "+sx,"~~~~~~~~~~~~       "+sy);
	// }
	@Override
	public void draw(GL10 gl) {
		if (mGR.resumeCounter == 0)
			DrawALL(gl);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			Counter++;
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 80) {
				Counter = 0;
				M.GameScreen = M.GAMESPLASH;
				M.GameScreen = M.GAMEADD;//AdHouse
				M.GameScreen = M.GAMESPLASH;
				Counter=0;//AdHouse
			}
			break;
		// case Check:
		// Counter++;
		// for(int i=0;i<mGR.mParticle.length;i++)
		// {
		// DrawTextureRS(gl,mGR.mTex_Particle[i],mGR.mParticle[i].x,mGR.mParticle[i].y,mGR.mParticle[i].scal,mGR.mParticle[i].scal,Counter*10/360);
		// mGR.mParticle[i].Update();
		// }
		// break;
			/*AdHouse*/
		case M.GAMEADD:
			Counter++;
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMESPLASH:
			Counter++;
			DrawTexture(gl, mGR.mTex_Splash, 0, 0);
			if (Counter > 80) {
				Counter = 0;
				M.GameScreen = M.GAMEMENU;
			}
			break;
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
		case M.GAMEGOINGPLAY:
		case M.GAMEGOINGOVER:
			DrawGamePlay(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			DrawPauseOrGover(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEINFO:
		case M.GAMEHIGH:
			DrawHelpInfo(gl);
			break;
		}
	}

	public boolean TouchEvent(MotionEvent event) {
		Log.d("  " + M.GameScreen, "   " + M.GameScreen);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMESPLASH;
				Counter =0;
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEGOINGPLAY:
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (M.setValue)
					M.BGplay(mGR.mContext, R.drawable.background);
				else
					M.stop();
				mGR.mPlayer.vy = -player.VY;
				M.GameScreen = M.GAMEPLAY;

			}
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			HandlePauseOrGameover(event);
			break;
		case M.GAMEHELP:
		case M.GAMEINFO:
		case M.GAMEHIGH:
			HandleHelpInfo(event);
			break;

		}
		return true;

	}

	void DrawBackBtn(GL10 gl) {
		DrawTexture(gl, mGR.mTex_backBtn, .85f, -.85f);
		if (mGR.mSel == 100)
			DrawTexture(gl, mGR.mTex_SmallSel, .85f, -.85f);
	}

	void DrawMenu(GL10 gl) {
		float x = .7f;
		float x1 = -.82f;
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_NewGameBtn, x, 0.3f);
		DrawTexture(gl, mGR.mTex_HelpBtn, x, 0.05f);
		DrawTexture(gl, mGR.mTex_AbtUsBtn, x, -0.2f);
		DrawTexture(gl, mGR.mTex_HighScoreBtn, x, -0.45f);
//		DrawTexture(gl, mGR.mTex_RateUsBtn, x1, -0.35f);
		DrawTexture(gl, mGR.mTex_ShareBtn, x1, -0.6f);
		if (M.setValue)
			DrawTexture(gl, mGR.mTex_SoundBtn[0], x1, -0.85f);
		else
			DrawTexture(gl, mGR.mTex_SoundBtn[1], x1, -0.85f);
		DrawTexture(gl, mGR.mTex_Exit, .85f, -.85f);

		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Selector, x, 0.3f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Selector, x, 0.05f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Selector, x, -0.2f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Selector, x, -0.45f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_SmallSel, x1, -0.35f);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_SmallSel, x1, -0.6f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_SmallSel, x1, -0.85f);
			break;
		case 8:
			DrawTexture(gl, mGR.mTex_SmallSel, .85f, -0.85f);
			break;

		}

	}

	boolean HandleMenu(MotionEvent event) {
		mGR.mSel = 0;
		float x = .7f;
		float x1 = -.82f;
		float dx = mGR.mTex_NewGameBtn.width() / 2;
		float dy = mGR.mTex_NewGameBtn.Height() / 2;

		if (CircRectsOverlap(x, .3f, dx, dy, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 1; // New Game
		if (CircRectsOverlap(x, .05f, dx, dy, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 2; // Help
		if (CircRectsOverlap(x, -.2f, dx, dy, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 3; // AboutUs
		if (CircRectsOverlap(x, -.45f, dx, dy, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 4; // HighScore
//		if (CircRectsOverlap(x1, -.35f, dx / 2, dy,
//				screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//			mGR.mSel = 5; // RateUs
		if (CircRectsOverlap(x1, -.6f, dx / 2, dy, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 6; // Share
		if (CircRectsOverlap(x1, -.85f, dx / 2, dy,
				screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 7; // Sound
		if (CircRectsOverlap(.85f, -.85f, dx / 2, dy,
				screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 8; // Exit

		if (event.getAction() == MotionEvent.ACTION_UP) {

			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEGOINGPLAY;
				mGR.gameReset();
				break;
			case 2:
				M.GameScreen = M.GAMEHELP;
				break;
			case 3:
				M.GameScreen = M.GAMEINFO;
				break;
			case 4:
//				M.GameScreen = M.GAMEHIGH;
				 GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 5:
				RateUs();
				break;
			case 6:
				share2friend();
				break;
			case 7:
				M.setValue = !M.setValue;
				break;
			case 8:
				GameRenderer.mStart.Exit();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}

	void gameLogic() {
		Counter++;
		if (Counter % 2 == 0)
			mGR.GameCount++;
		if (Counter % 33 == 0)
			mGR.timeCnt++;
		mGR.mPlayer.update();
		if (mGR.mScore > mGR.HighScore)
			mGR.HighScore = mGR.mScore;
		if (mGR.GameCount > 300) {
			if (mGR.ScalVal <= 1.25f)
				mGR.ScalVal += .20;
			mGR.GameCount = 0;
		}
		UpdateBg();
		for (int i = 0; i < mGR.mObstacle.length; i++) {
			mGR.mObstacle[i].x += mGR.mObstacle[i].vx;
			if (mGR.mObstacle[i].x < -1.2f) {
				if (mGR.ScalVal <= .35f)
					mGR.mObstacle[i].setObject(1.2f,
							mGR.randomRange(-.7f, .7f), M.BGSPEED);
				if (mGR.ScalVal > .35f && mGR.ScalVal <= .6f)
					mGR.mObstacle[i].setObject(1.2f,
							mGR.randomRange(-.6f, .6f), M.BGSPEED);
				if (mGR.ScalVal > .6f && mGR.ScalVal <= .85f)
					mGR.mObstacle[i].setObject(1.2f,
							mGR.randomRange(-.5f, .5f), M.BGSPEED);
				if (mGR.ScalVal > .85f && mGR.ScalVal <= 1.1f)
					mGR.mObstacle[i].setObject(1.2f,
							mGR.randomRange(-.5f, .5f), M.BGSPEED);
				if (mGR.ScalVal > 1.1f)
					mGR.mObstacle[i].setObject(1.2f,
							mGR.randomRange(-.4f, .4f), M.BGSPEED);

			}

			if (CirCir(mGR.mObstacle[i].x, mGR.mObstacle[i].y,
					mGR.mTex_obstacle.width() / 2, mGR.mPlayer.x
							- mGR.mTex_Copter[0].width() * .225f, mGR.mPlayer.y
							- mGR.mTex_Copter[0].Height() * .05f,
					mGR.mTex_Copter[0].width() / 4)) {
				M.sound5(mGR.mContext, R.drawable.explosion);
				M.GameScreen = M.GAMEGOINGOVER;
				player.mPlyerang = 0;
				SetParticle(mGR.mPlayer.x, mGR.mPlayer.y);
				mGR.blastX = mGR.mObstacle[i].x;
				mGR.blastY = mGR.mObstacle[i].y;
				mGR.BlastCnt = 0;
				mGR.mObstacle[i].Reset();
			}

		}

		for (int i = 0; i < mGR.mCoin.length; i++) {
			if (mGR.mCoin[mGR.mCoin.length - 1].x < -1.2f)
				SetCoin();
			if (CirCir(mGR.mCoin[i].x, mGR.mCoin[i].y,
					mGR.mTex_Coin[0].width() / 2, mGR.mPlayer.x
							- mGR.mTex_Copter[0].width() * .45f, mGR.mPlayer.y
							- mGR.mTex_Copter[0].Height() * .05f,
					mGR.mTex_Copter[0].width() / 4)) {
				M.sound1(mGR.mContext, R.drawable.coin);
				Score++;
				mGR.mScore = mGR.timeCnt * Score * 3;
				mGR.mCoin[i].Reset();
			}
			mGR.mCoin[i].x += mGR.mCoin[i].vx;
		}

		float dy = (mGR.mTex_Cloud.Height()) * mGR.ScalVal;
		if (mGR.mPlayer.y >= 1 - dy)// Top
		{
			M.sound5(mGR.mContext, R.drawable.explosion);
			mGR.blastX = mGR.mPlayer.x;
			mGR.blastY = mGR.mPlayer.y;
			mGR.BlastCnt = 0;
			SetParticle(mGR.mPlayer.x, mGR.mPlayer.y);
			M.GameScreen = M.GAMEGOINGOVER;
			player.mPlyerang = 0;
		}
		float dy1 = (mGR.mTex_Grass.Height()) * mGR.ScalVal;
		if (mGR.mPlayer.y <= -1 + dy1) // Bottom
		{
			M.sound5(mGR.mContext, R.drawable.explosion);
			mGR.blastX = mGR.mPlayer.x;
			mGR.blastY = mGR.mPlayer.y;
			mGR.BlastCnt = 0;
			SetParticle(mGR.mPlayer.x, mGR.mPlayer.y);
			M.GameScreen = M.GAMEGOINGOVER;
			player.mPlyerang = 0;
		}

	}

	void UpdateBg() {
		for (int i = 0; i < mGR.mBg1.length; i++) {
			mGR.mBg1[i].x += mGR.mBg1[i].vx;
			mGR.mBg2[i].x += mGR.mBg2[i].vx;
			mGR.mBg3[i].x += mGR.mBg3[i].vx;
			mGR.mBg4[i].x += mGR.mBg4[i].vx;
			mGR.mBg5[i].x += mGR.mBg5[i].vx;
			mGR.mBgtop[i].x += mGR.mBgtop[i].vx;
		}
		for (int i = 0; i < mGR.mBg1.length; i++) {
			if (mGR.mBg1[i].x < -2) {
				if (i > 0)
					mGR.mBg1[i].x = mGR.mBg1[i - 1].x + mGR.mTex_Bg[0].width();
				else
					mGR.mBg1[i].x = mGR.mBg1[mGR.mBg1.length - 1].x
							+ mGR.mTex_Bg[0].width();
			}
			if (mGR.mBg2[i].x < -2) {
				if (i > 0)
					mGR.mBg2[i].x = mGR.mBg2[i - 1].x + mGR.mTex_Bg[1].width();
				else
					mGR.mBg2[i].x = mGR.mBg2[mGR.mBg1.length - 1].x
							+ mGR.mTex_Bg[1].width();
			}
			if (mGR.mBg3[i].x < -2) {
				if (i > 0)
					mGR.mBg3[i].x = mGR.mBg3[i - 1].x + mGR.mTex_Bg[2].width();
				else
					mGR.mBg3[i].x = mGR.mBg3[mGR.mBg1.length - 1].x
							+ mGR.mTex_Bg[2].width();
			}
			if (mGR.mBg4[i].x < -2) {
				if (i > 0)
					mGR.mBg4[i].x = mGR.mBg4[i - 1].x + mGR.mTex_Bg[3].width();
				else
					mGR.mBg4[i].x = mGR.mBg4[mGR.mBg1.length - 1].x
							+ mGR.mTex_Bg[3].width();
			}
			if (mGR.mBg5[i].x < -2) {
				if (i > 0)
					mGR.mBg5[i].x = mGR.mBg5[i - 1].x + mGR.mTex_Bg[4].width();
				else
					mGR.mBg5[i].x = mGR.mBg5[mGR.mBg1.length - 1].x
							+ mGR.mTex_Bg[4].width();
			}
			if (mGR.mBgtop[i].x < -2) {
				if (i > 0)
					mGR.mBgtop[i].x = mGR.mBgtop[i - 1].x
							+ mGR.mTex_Grass.width();
				else
					mGR.mBgtop[i].x = mGR.mBgtop[mGR.mBgtop.length - 1].x
							+ mGR.mTex_Grass.width();
			}
		}

		for (int i = 0; i < mGR.mCloud.length; i++)
			mGR.mCloud[i].x += mGR.mCloud[i].vx;

		for (int i = 0; i < mGR.mCloud.length; i++) {
			if (mGR.mCloud[i].x < -1.3f) {
				if (i > 0)
					mGR.mCloud[i].x = mGR.mCloud[i - 1].x
							+ mGR.mTex_Cloud.width() / 2;
				else
					mGR.mCloud[i].x = mGR.mCloud[mGR.mCloud.length - 1].x
							+ mGR.mTex_Cloud.width() / 2;
			}
		}

	}

	public void SetCoin() {
		float x = 1.4f;
		switch (mGR.mRand.nextInt() % 6) {
		case 0:
			mGR.coinPattern1(x, 10);
			break;
		case 1:
			mGR.coinPattern2(x, 10);
			break;
		case 2:
			mGR.coinPattern3(x, 10);
			break;
		case 3:
			mGR.coinPattern4(x, 10);
			break;
		case 4:
			mGR.coinPattern5(x, 10);
			break;
		case 5:
			mGR.coinPattern6(x, 10);
			break;
		}
	}

	// void SmokeLogic()
	// {
	// mGR.StopCnt++;
	// if (mGR.StopCnt>2)
	// {
	// if(mGR.NoSmoke < mGR.mSmoke.length - 1)
	// mGR.NoSmoke++;
	// mGR.StopCnt = 0;
	// }
	// mGR.mSmoke[mGR.NoSmoke].SetSmoke(mGR.mPlayer.x-mGR.mTex_Copter[0].width()/2,
	// mGR.mPlayer.y,.35f,1 ,0);
	// for (int i = mGR.NoSmoke; i > 0; i--){
	// mGR.mSmoke[i].Time++;
	// if(mGR.mSmoke[i].scal<=1)
	// mGR.mSmoke[i].scal+=.03f;
	// if(mGR.mSmoke[i].fadeVal>=0)
	// mGR.mSmoke[i].fadeVal-=.03;
	// if (mGR.mSmoke[i].Time > M.LAST){
	// mGR.mSmoke[i].SetSmoke(mGR.mPlayer.x-mGR.mTex_Copter[0].width()/2,
	// mGR.mPlayer.y,.35f,1,0);
	// mGR.NoSmoke--;
	// mGR.StopCnt = 10;
	// }
	// mGR.mSmoke[i].x+=mGR.mBg[0].vx;
	// mGR.mSmoke[i].y+=mGR.mPlayer.vy;

	// }

	// }
	// void DrawSmoke(GL10 gl)
	// {
	// for(int i=mGR.NoSmoke;i>0;i--)
	// DrawTextureF(gl,mGR.mTex_Smoke,mGR.mSmoke[i].x,
	// mGR.mSmoke[i].y,mGR.mSmoke[i].scal,mGR.mSmoke[i].fadeVal);
	// }
	void Gameover() {
		mGR.BlastCnt++;
		player.mPlyerang += 10;
		for (int i = 0; i < mGR.mParticle.length; i++) {
			mGR.mParticle[i].Update();
			if (mGR.mParticle[i].y < -1.2f) {
				M.stop();
				M.GameScreen = M.GAMEOVER;
			}
		}
	}

	void DrawGamePlay(GL10 gl) {

		if (M.GameScreen == M.GAMEGOINGPLAY)
			Counter++;
		for (int i = 0; i < M.ScreenWidth; i++) {
			float dx = mGR.mTex_Strip.width();
			DrawTexture(gl, mGR.mTex_Strip, -1 + i * dx, 0);
		}
		for (int i = 0; i < mGR.mBg1.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[0], mGR.mBg1[i].x, 0);
		for (int i = 0; i < mGR.mBg2.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[1], mGR.mBg2[i].x, 0);
		for (int i = 0; i < mGR.mBg2.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[2], mGR.mBg3[i].x, 0);
		for (int i = 0; i < mGR.mBg2.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[3], mGR.mBg4[i].x, 0);
		for (int i = 0; i < mGR.mBg2.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[4], mGR.mBg5[i].x, 0);

		for (int i = 0; i < mGR.mBgtop.length; i++) {
			float y = (mGR.mTex_Grass.Height() / 2) * mGR.ScalVal;
			DrawTextureS(gl, mGR.mTex_Grass, mGR.mBgtop[i].x, -1 + y, 1,
					mGR.ScalVal);
		}
		for (int i = 0; i < mGR.mCloud.length; i++) {
			float y = ((mGR.mTex_Cloud.Height() * mGR.ScalVal) / 2);
			DrawTextureS(gl, mGR.mTex_Cloud, mGR.mCloud[i].x, 1 - y, 1,
					mGR.ScalVal);
		}
		for (int i = 0; i < mGR.mObstacle.length; i++) {
			if (mGR.mObstacle[i].x > -1.2f && mGR.mObstacle[i].x < 1.2f)
				DrawTexture(gl, mGR.mTex_obstacle, mGR.mObstacle[i].x,
						mGR.mObstacle[i].y);
		}
		for (int i = 0; i < mGR.mCoin.length; i++) {
			if (mGR.mCoin[i].x > -1.2f && mGR.mCoin[i].x < 1.2f)
				DrawTexture(gl, mGR.mTex_Coin[(Counter / 4)
						% mGR.mTex_Coin.length], mGR.mCoin[i].x, mGR.mCoin[i].y);
		}
		if (mGR.mPlayer.x > -1.2f && mGR.mPlayer.x < 1.2f) {
			if (M.GameScreen != M.GAMEGOINGOVER)
				DrawTextureRP(gl, mGR.mTex_Copter[Counter
						% mGR.mTex_Copter.length], mGR.mPlayer.x,
						mGR.mPlayer.y, player.mPlyerang,
						-mGR.mTex_Copter[0].width() * .45f,
						mGR.mTex_Copter[0].Height() * .05f);
			else {
				for (int i = 0; i < mGR.mParticle.length; i++) {
					if (mGR.mParticle[i].x > -1.2f && mGR.mParticle[i].x < 1.2f)
						DrawTextureRS(gl,
								mGR.mTex_Particle[mGR.mParticle[i].ImgNo],
								mGR.mParticle[i].x, mGR.mParticle[i].y,
								mGR.mParticle[i].scal, mGR.mParticle[i].scal,
								(player.mPlyerang * 2) % 360);
				}
			}
		}
		if (mGR.BlastCnt < mGR.mTex_Blast.length) {
			if (mGR.blastX > -1.2f && mGR.blastY < 1.2f)
				DrawTexture(gl, mGR.mTex_Blast[(mGR.BlastCnt / 2)
						% mGR.mTex_Blast.length], mGR.blastX, mGR.blastY);
		}
		DrawTexture(gl, mGR.mTexPauseBtn, .85f, .85f);
		DrawTexture(gl, mGR.mTex_GameScore, -.88f, .85f);
		DrawTexture(gl, mGR.mTex_ScoreBox, -.5f, .85f);
		String str = mGR.mScore + "";
		float dx = (str.length() * mGR.mTex_Font[0].width() / 2) / 2;
		drawNumber(gl, mGR.mScore, -.5f - dx, .86f);
		if (mGR.mSel == 1)
			DrawTexture(gl, mGR.mTex_SmallSel, .85f, .85f);

		if (M.GameScreen == M.GAMEPLAY)
			gameLogic();
		if (M.GameScreen == M.GAMEGOINGOVER)
			Gameover();
	}

	boolean HandleGame(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mGR.mSel = 0;
			if (CircRectsOverlap(.85f, .85f, mGR.mTexPauseBtn.width() / 2,
					mGR.mTexPauseBtn.Height() / 2, screen2worldX(event.getX()),
					screen2worldY(event.getY()), .05f))
				mGR.mSel = 1;
			else
				mGR.mPlayer.vy = player.VY;
			break;
		case MotionEvent.ACTION_UP:
			if (mGR.mSel == 1) {
				M.stop();
				M.GameScreen = M.GAMEPAUSE;

			} else
				mGR.mPlayer.vy = -player.VY;
			mGR.mSel = 0;
			break;

		}

		return true;
	}

	void DrawHelpInfo(GL10 gl) {
		DrawTexture(gl, mGR.mTex_UIbg, 0, 0);
		DrawTexture(gl, mGR.mTex_PopUp, 0, -0.25f);
		if (M.GameScreen == M.GAMEHELP) {
			DrawTexture(gl, mGR.mTex_HelpTitle, 0, 0.55f);
			DrawTexture(gl, mGR.mTex_HelpTxt, 0, 0);
		}
		if (M.GameScreen == M.GAMEINFO) {
			DrawTexture(gl, mGR.mTex_AbtTitle, 0, 0.55f);
			DrawTexture(gl, mGR.mTex_AbtUsTxt, 0, 0f);
		}
		if (M.GameScreen == M.GAMEHIGH) {
			DrawTexture(gl, mGR.mTex_HighScoreTitle, 0, 0.55f);
			drawNumber(gl, mGR.HighScore, -.1f, 0);
		}
		DrawTexture(gl, mGR.mTex_ShareBtn, -0.2f, -0.6f);
//		DrawTexture(gl, mGR.mTex_RateUsBtn, 0.2f, -0.6f);
		DrawBackBtn(gl);
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_SmallSel, -0.2f, -0.6f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_SmallSel, 0.2f, -0.6f);
			break;
		}
	}

	boolean HandleHelpInfo(MotionEvent event) {
		mGR.mSel = 0;
		if (CircRectsOverlap(-.2f, -.6f, mGR.mTex_ShareBtn.width() / 2,
				mGR.mTex_ShareBtn.Height() / 2, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 1;// Share
//		if (CircRectsOverlap(.2f, -.6f, mGR.mTex_RateUsBtn.width() / 2,
//				mGR.mTex_RateUsBtn.Height() / 2, screen2worldX(event.getX()),
//				screen2worldY(event.getY()), .05f))
//			mGR.mSel = 2;// RateUs
		if (CircRectsOverlap(.85f, -.85f, mGR.mTex_backBtn.width() / 2,
				mGR.mTex_backBtn.Height() / 2, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05f))
			mGR.mSel = 100;

		if (event.getAction() == MotionEvent.ACTION_UP) {

			switch (mGR.mSel) {
			case 1:
				share2friend();
				break;
			case 2:
				RateUs();
				break;
			case 100:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}

	void DrawPauseOrGover(GL10 gl) {
		DrawTexture(gl, mGR.mTex_UIbg, 0, 0);
		DrawTexture(gl, mGR.mTex_PopUp, 0, -0.25f);
		if (M.GameScreen == M.GAMEPAUSE)
			DrawTexture(gl, mGR.mTex_GamePauseTitle, 0, 0.55f);
		else
			DrawTexture(gl, mGR.mTex_GameOverTitle, 0, 0.55f);

		DrawTexture(gl, mGR.mTex_NewScore, -0.3f, .32f);
		DrawTexture(gl, mGR.mTex_ScoreBox, 0.25f, .30f);
		String str = mGR.mScore + "";
		float dx = (str.length() / 2 * mGR.mTex_Font[0].width() / 2);
		drawNumber(gl, mGR.mScore, 0.24f - dx, .31f);

		 DrawTexture(gl,mGR.mTex_Submitt[0],0.65f,.12f);

		DrawTexture(gl, mGR.mTex_BestScore, -0.3f, .12f);
		DrawTexture(gl, mGR.mTex_ScoreBox, 0.25f, .1f);
		String str1 = mGR.HighScore + "";
		float dx1 = (str1.length() / 2 * mGR.mTex_Font[0].width() / 2);
		drawNumber(gl, mGR.HighScore, 0.24f - dx1, .11f);

		DrawTexture(gl, mGR.mTex_HighScoreTitle, -0.25f, -0.1f);
		DrawTexture(gl, mGR.mTex_HututTitle, 0.25f, -0.1f);

		if (M.setValue)
			DrawTexture(gl, mGR.mTex_SoundBtn[0], -0.35f, -0.35f);
		else
			DrawTexture(gl, mGR.mTex_SoundBtn[1], -0.35f, -0.35f);

		DrawTexture(gl, mGR.mTex_MenuBtn, 0, -0.35f);
		if (M.GameScreen == M.GAMEPAUSE)
			DrawTexture(gl, mGR.mTex_Continue, 0.35f, -0.35f);
		else
			DrawTexture(gl, mGR.mTex_Replay, 0.35f, -0.35f);

		DrawTexture(gl, mGR.mTex_ShareBtn, -0.2f, -0.62f);
		DrawTexture(gl, mGR.mTex_RateUsBtn, 0.2f, -0.62f);
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Selector, -0.25f, -0.1f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Selector, 0.25f, -0.1f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_SmallSel, -0.35f, -0.35f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_SmallSel, 0, -0.35f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_SmallSel, 0.35f, -0.35f);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_SmallSel, -0.2f, -0.62f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_SmallSel, 0.2f, -0.62f);
			break;
		case 8:
			DrawTexture(gl, mGR.mTex_Submitt[1], 0.65f, .12f);
			break;
		}

	}

	boolean HandlePauseOrGameover(MotionEvent event) {
		mGR.mSel = 0;
		float dx = mGR.mTex_HighScoreTitle.width() / 2;
		float dy = mGR.mTex_HighScoreTitle.Height() / 2;
		if (CircRectsOverlap(-0.25f, -0.1f, dx, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 1; // HighScore
		if (CircRectsOverlap(0.25f, -0.1f, dx, dy, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel = 2; // MoreGame
		if (CircRectsOverlap(-0.35f, -0.35f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 3; // Sound
		if (CircRectsOverlap(0f, -0.35f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 4; // Menu
		if (CircRectsOverlap(0.35f, -0.35f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 5; // Continue //Replay
		if (CircRectsOverlap(-0.2f, -0.62f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 6; // Share
		if (CircRectsOverlap(0.2f, -0.62f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 7; // RateUs
		if (CircRectsOverlap(0.65f, 0.12f, dx / 2, dy,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 8; // Submitt

		if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (mGR.mSel) {
			case 1:
//				M.GameScreen = M.GAMEHIGH;
				// GameRenderer.mStart.CallLeaderBord();
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 2:
				MoreGame();
				break;
			case 3:
				M.setValue = !M.setValue;
				break;
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			case 5:
				if (M.GameScreen == M.GAMEPAUSE) {
					if (M.setValue)
						M.BGplay(mGR.mContext, R.drawable.background);
					else
						M.stop();
					M.GameScreen = M.GAMEPLAY;
				} else {
					mGR.gameReset();
					M.GameScreen = M.GAMEGOINGPLAY;
				}
				break;
			case 6:
				share2friend();
				break;
			case 7:
				RateUs();
				break;
			case 8:
				 GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
				break;

			}
			mGR.mSel = 0;
		}

		return true;
	}

	void DrawALL(GL10 gl) {
		float x = -100;
		DrawTexture(gl, mGR.mTex_Strip, x, x);

		for (int i = 0; i < mGR.mTex_Bg.length; i++)
			DrawTexture(gl, mGR.mTex_Bg[i], x, x);

		for (int i = 0; i < mGR.mTex_Copter.length; i++)
			DrawTexture(gl, mGR.mTex_Copter[i], x, x);

		for (int i = 0; i < mGR.mTex_Coin.length; i++)
			DrawTexture(gl, mGR.mTex_Coin[i], x, x);

		for (int i = 0; i < mGR.mTex_Particle.length; i++)
			DrawTexture(gl, mGR.mTex_Particle[i], x, x);

		DrawTexture(gl, mGR.mTex_Cloud, x, x);
		DrawTexture(gl, mGR.mTex_Grass, x, x);

	}

	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawTransprent(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, angle, x, y);
	}

	void DrawTextureRP(GL10 gl, SimplePlane Tex, float x, float y, float angle,
			float rx, float ry) {
		Tex.drawRotetAtPoint(gl, angle, rx, ry, x, y);
	}

	void DrawTextureRS(GL10 gl, SimplePlane Tex, float x, float y, float scalx,
			float scaly, float angle) {
		Tex.drawRotScal(gl, x, y, scalx, scaly, angle);
	}

	void DrawTextureF(GL10 gl, SimplePlane Tex, float x, float y, float Scal,
			float trans) {
		Tex.drawScalFad(gl, x, y, Scal, trans);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scalx,
			float scaly) {
		Tex.drawScal(gl, x, y, scalx, scaly);
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
		if (ax + adx > bx && ay + ady > by && bx + bdx > ax && by + bdy > ay) {
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

	double GetAngle(double d, double e) {
		if (d == 0)
			return e >= 0 ? Math.PI / 2 : -Math.PI / 2;
		else if (d > 0)
			return Math.atan(e / d);
		else
			return Math.atan(e / d) + Math.PI;
	}

	void drawNumber(GL10 gl, int no, float x, float y) {
		float dx = mGR.mTex_Font[0].width();
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}

	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		mGR.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		mGR.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Helicopter Control");
		i.putExtra(Intent.EXTRA_TEXT,
				"It is a new brand and distinctive Helicopter game! Install the best free game"
						+ M.LINK + getClass().getPackage().getName());
		try {
			mGR.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	void SetParticle(float x, float y) {
		for (int i = 0; i < mGR.mParticle.length; i++) {
			float vx = M.mRand.nextBoolean() ? M.mRand.nextInt(40) / 1000.0f
					: -M.mRand.nextInt(40) / 1000.0f;
			float vy = M.mRand.nextBoolean() ? -M.mRand.nextInt(15) / 10000.0f
					: M.mRand.nextInt(15) / 1000.0f;
			mGR.mParticle[i].set(x, y, vx, vy, i % mGR.mTex_Particle.length);
		}
	}
}

// void SetPattern(float x)
// {
// switch(mGR.mRand.nextInt(4)){
// case 0:
// mGR.pattern1(x);
// break;
// case 1:
// mGR.pattern2(x);
// break;
// case 2:
// mGR.pattern3(x);
// break;
// case 3:
// mGR.pattern4(x);
// break;
// }
// }
// void DrawGameOver(GL10 gl)
// {
// DrawTexture(gl,mGR.mTex_Splash,0,0);
// DrawTexture(gl,mGR.mTex_PopUp,0,-0.25f);
// DrawTexture(gl,mGR.mTex_GamePauseTitle,0,0.55f);
// DrawTexture(gl,mGR.mTex_NewScore,-0.3f,.32f);
// DrawTexture(gl,mGR.mTex_ScoreBox,0.25f,.30f);
//
// DrawTexture(gl,mGR.mTex_BestScore,-0.3f,.12f);
// DrawTexture(gl,mGR.mTex_ScoreBox,0.25f,.1f);
//
// DrawTexture(gl,mGR.mTex_HighScoreTitle,-0.25f,-0.1f);
// DrawTexture(gl,mGR.mTex_HututTitle,0.25f,-0.1f);
//
// if(M.setValue)
// DrawTexture(gl,mGR.mTex_SoundBtn[0],-0.35f,-0.35f);
// else
// DrawTexture(gl,mGR.mTex_SoundBtn[1],-0.35f,-0.35f);
//
// DrawTexture(gl,mGR.mTex_MenuBtn,0,-0.35f);
// DrawTexture(gl,mGR.mTex_Replay,0.35f,-0.35f);
// DrawTexture(gl,mGR.mTex_ShareBtn,-0.2f,-0.62f);
// DrawTexture(gl,mGR.mTex_RateUsBtn,0.2f,-0.62f);
// switch(mGR.mSel)
// {
// case 1:
// DrawTexture(gl,mGR.mTex_Selector,-0.25f,-0.1f);
// break;
// case 2:
// DrawTexture(gl,mGR.mTex_Selector,0.25f,-0.1f);
// break;
// case 3:
// DrawTexture(gl,mGR.mTex_SmallSel,-0.35f,-0.35f);
// break;
// case 4:
// DrawTexture(gl,mGR.mTex_SmallSel,0,-0.35f);
// break;
// case 5:
// DrawTexture(gl,mGR.mTex_SmallSel,0.35f,-0.35f);
// break;
// case 6:
// DrawTexture(gl,mGR.mTex_SmallSel,-0.2f,-0.62f);
// break;
// case 7:
// DrawTexture(gl,mGR.mTex_SmallSel,0.2f,-0.62f);
// break;
// }
//
// }
// boolean HandleGameOver(MotionEvent event)
// {
// mGR.mSel=0;
// float dx =mGR.mTex_HighScoreTitle.width()/2;
// float dy =mGR.mTex_HighScoreTitle.Height()/2;
// if(CircRectsOverlap(-0.25f,-0.1f,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=1; //HighScore
// if(CircRectsOverlap(0.25f,-0.1f,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=2; //MoreGame
// if(CircRectsOverlap(-0.35f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=3; //Sound
// if(CircRectsOverlap(0f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=4; //Menu
// if(CircRectsOverlap(0.35f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=5; //Replay
// if(CircRectsOverlap(-0.2f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=6; //Share
// if(CircRectsOverlap(0.2f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),
// .05f))
// mGR.mSel=7; //RateUS
//
// if(event.getAction() == MotionEvent.ACTION_UP)
// {
// switch(mGR.mSel)
// {
// case 1:
// GameRenderer.mStart.CallLeaderBord();
// break;
// case 2:
// MoreGame();
// break;
// case 3:
// M.setValue =!M.setValue;
// break;
// case 4:
// M.GameScreen = M.GAMEMENU;
// break;
// case 5:
// mGR.gameReset();
// M.GameScreen = M.GAMEGOINGPLAY;
// break;
// case 6:
// share2friend();
// break;
// case 7:
// RateUs();
// break;
//
// }
// mGR.mSel=0;
// }
//
//
// return true;
// }