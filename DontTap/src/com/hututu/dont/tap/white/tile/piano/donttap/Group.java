package com.hututu.dont.tap.white.tile.piano.donttap;
import java.math.BigDecimal;

import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {
	GameRenderer mGR = null;
	int Counter = 0;
	float sx, sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~ "+mGR.mSelTile+"~~~~~~~~~~~~~      "+sx+"f, "+sy+"f");}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}
	float z=.01f,vz=1.1f;
	@Override
	public void draw(GL10 gl) {
		// M.GameScreen = M.GAMEWIN;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, 1, .4f, .0f, 2.1f, 3.6f);// Orange
																			// 255,100,0
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, .24f, .78f, 1f, 2.1f, 3.6f);// Blue
																				// 60,200,255
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, 0, 0, 0, 2.1f, 3.6f);// Black
																		// 0,0,0
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60) {
				M.GameScreen = M.GAMEMENU;
				mGR.mSelTile = -1;
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GINSIDE:
			Draw_Inside(gl);
			break;
		case M.GAMEMORE:
			Draw_More(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
			Draw_WinLose(gl);
			break;

		}
		Counter++;
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) {	
		switch (M.GameScreen) {
		case M.GAMELOGO:
			z=.01f;vz=1.2f;
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GINSIDE:
			Handle_Inside(event);
			break;
		case M.GAMEMORE:
			Handle_More(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
			Handle_WinLose(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~Win Loose Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void gamewinLoose() {
		if (mGR.mSelTile == 0) {
			if (mGR.Score > mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile]) {
				mGR.NewBest = false;
			} else {
				if (M.GameScreen == M.GAMEWIN) {
					GameRenderer.mStart.Submitscore(M.Score[mGR.mSelTile][mGR.mSelSemiTile],
							(System.currentTimeMillis() - mGR.STime));
					mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile] = mGR.Score;
					mGR.NewBest = true;
				}
			}
		} else if (mGR.mSelTile == 4) {
			if (mGR.type > 0) {
				System.out.println(mGR.mSelTile + "  ###################  " + mGR.type);
				if (mGR.MoveTile > mGR.BestScore[mGR.mSelTile][mGR.type - 1]) {
					mGR.NewBest = true;
					mGR.BestScore[mGR.mSelTile][mGR.type - 1] = mGR.MoveTile;
					GameRenderer.mStart.Submitscore(M.Score[mGR.mSelTile][mGR.type - 1], (System.currentTimeMillis() - mGR.STime));
				} else {
					mGR.NewBest = false;

				}
			}
		} else {
			if (mGR.MoveTile > mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile]) {
				mGR.NewBest = true;
				mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile] = mGR.MoveTile;
				if(mGR.mSelTile!=3)
					GameRenderer.mStart.Submitscore(M.Score[mGR.mSelTile][mGR.mSelSemiTile], (System.currentTimeMillis() - mGR.STime));
			} else {
				mGR.NewBest = false;

			}
		}
		if(mGR.TotalTile>99 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.TotalTile>999 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.TotalTile>4999 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if((System.currentTimeMillis() - mGR.STime)>39999 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if((System.currentTimeMillis() - mGR.STime)>59999 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.ShowInterstitial();
	}
	void Draw_WinLose(GL10 gl) {
		if(M.GameScreen == M.GAMEWIN)
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, 1, .4f, .0f, 2.1f, 3.6f);// Orange 255,100,0
		else
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, .24f, .78f, 1f, 2.1f, 3.6f);// Blue 60,200,255
		if (mGR.mSelTile >= 0) {
			DrawTextureS(gl, mGR.mTex_MemuText[mGR.mSelTile], 0, .5f, 2);
		}
		if (mGR.mSelTile == 0) {
			drawNumber(gl, mGR.Score + "", 1, -.33f, .10f);
			if (M.GameScreen == M.GAMEWIN) {
				if (mGR.NewBest) {
					DrawTexture(gl, mGR.mTex_New, -.13f, -.13f);
					DrawTexture(gl, mGR.mTex_Best, .13f, -.13f);
				} else {
					DrawTexture(gl, mGR.mTex_Best, -.2f, -.13f);
					drawNumber(gl,mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile] + "", 0, .0f, -.13f);
				}
			} else {
				DrawTexture(gl, mGR.mTex_Failed, 0, -.13f);
			}
		}
		if (mGR.mSelTile == 1 || mGR.mSelTile == 2) {
			drawNumber(gl, mGR.MoveTile + "", 1, -.10f, .10f);
			if (mGR.NewBest) {
				DrawTexture(gl, mGR.mTex_New, -.13f, -.13f);
				DrawTexture(gl, mGR.mTex_Best, .13f, -.13f);
			} else {
				DrawTexture(gl, mGR.mTex_Best, -.2f, -.13f);
				drawNumber(gl,(int)mGR.BestScore[mGR.mSelTile][mGR.mSelSemiTile] + "", 0, .0f, -.13f);
			}
		}
		if(mGR.mSelTile == 3){
			if(mGR.time < mGR.Score){
				DrawTexture(gl, mGR.mTex_Well, 0, -.13f);
			}else{
				DrawTexture(gl, mGR.mTex_Failed, 0, -.13f);
			}
		}
		if(mGR.mSelTile == 4){
			drawNumber(gl, mGR.MoveTile + "", 1, -.10f, .10f);
			if (mGR.NewBest) {
				DrawTexture(gl, mGR.mTex_New, -.13f, -.13f);
				DrawTexture(gl, mGR.mTex_Best, .13f, -.13f);
			} else {
				DrawTexture(gl, mGR.mTex_Best, -.2f, -.13f);
				if(mGR.type > 0)
					drawNumber(gl,(int)mGR.BestScore[mGR.mSelTile][mGR.type-1] + "", 0, .0f, -.13f);
			}
		
		}
		
		DrawTexture(gl, mGR.mTex_Back, -.6f, -.60f);
		DrawTexture(gl, mGR.mTex_Retry, 0.6f, -.60f);
		DrawTexture(gl, mGR.mTex_Leader, 0.0f, -.60f);
		if(mGR.mPName.length()>0)
			mGR.mFont.Draw_String(gl, mGR.mPName, 0, -.33f, 4, 1);
	}

	boolean Handle_WinLose(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (CircRectsOverlap(-.0f, -.60f, mGR.mTex_Leader.width() * .45f,
					mGR.mTex_Leader.Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),.01f)) {
				//LeaderBord
				M.playAll(GameRenderer.mContext);
				GameRenderer.mStart.onShowLeaderboardsRequested();
			}
			if (CircRectsOverlap(-.6f, -.60f, mGR.mTex_STile[0].width() * .45f,
					mGR.mTex_STile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),.01f)) {
				M.GameScreen = M.GAMEMENU;
				mGR.mSelTile = -1;
				M.playAll(GameRenderer.mContext);
			}
			if (CircRectsOverlap(0.6f, -.60f, mGR.mTex_STile[0].width() * .45f,
					mGR.mTex_STile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					.01f)) {
				
				M.playAll(GameRenderer.mContext);
				switch (mGR.mSelTile) {
				case 0:{
					switch (mGR.mSelSemiTile) {
					case 0:
						mGR.gameReset(25, -1, 0, 0);
						break;
					case 1:
						mGR.gameReset(50, -1, 0, 0);
						break;
					case 2:
						mGR.gameReset(100, -1, 0, 0);
						break;
					}
				}
				break;
				case 1:{
					switch (mGR.mSelSemiTile) {
					case 0:
						mGR.gameReset(-1, -1, -1, 0);
						break;
					case 1:
						mGR.gameReset(-1, -1, -2, 0);
						break;
					case 2:
						mGR.gameReset(-1, -1, 1, 0);
						break;
					}
				}
				break;
				case 2:{
					switch (mGR.mSelSemiTile) {
					case 0:
						mGR.gameReset(-1, 15, 0, 0);
						break;
					case 1:
						mGR.gameReset(-1, 30, 0, 0);
						break;
					case 2:
						mGR.gameReset(-1, 60, 0, 0);
						break;
					}
				}
				break;
				case 3:{
					switch (mGR.mSelSemiTile) {
					case 0:
						mGR.gameReset(-1, 10, -1, 0);
						break;
					case 1:
						mGR.gameReset(-1, 20, -2, 0);
						break;
					case 2:
						mGR.gameReset(-1, 30, 1, 0);
						break;
					}
				}
					break;
				case 4:
					switch (mGR.type) {
					case 1:
						mGR.gameReset(-1, -1, -1, 1);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 2:
						mGR.gameReset(-1, -1, -1, 2);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 3:
						mGR.gameReset(-1, -1, -1, 3);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 4:
						mGR.gameReset(-1, -1, -1, 4);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 5:
						mGR.gameReset(-1, -1, -1, 5);
						M.GameScreen = M.GAMEPLAY;
						break;
					}
				
				
					break;
				}
			}
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~About End~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	
	
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~About Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_About(GL10 gl) {
		DrawTexture(gl, mGR.mTex_About, 0, 0);
		DrawTextureS(gl, mGR.mTex_STile[0], 0, -.92f, .56f);
		DrawTexture(gl, mGR.mTex_Back, 0, -.92f);
		if(mGR.mPName.length()>0)
			mGR.mFont.Draw_String(gl, mGR.mPName, 0, -.81f, 2, 1);
	}

	boolean Handle_About(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (CircRectsOverlap(0, -.92f, mGR.mTex_BTile[0].width() * .45f, mGR.mTex_BTile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)) {
				M.GameScreen = M.GAMEMORE;
				M.playAll(GameRenderer.mContext);
			}
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~About End~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~More Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_More(GL10 gl) {
		DrawColor(gl, mGR.mTex_BTile[1], 0, -.83f, .9f, .9f, .9f, 2.1f, .64f);
		for (int i = 0; i < 6; i++) {
			DrawTexture(gl, mGR.mTex_BTile[(i == 1 || i == 2 || i == 5) ? 1 : 0], -.5f + i % 2, .72f - th * (i / 2));
			if (i == 4) {
				DrawTexture(gl, mGR.mTex_STile[1], -.5f + i % 2, .72f - th * (i / 2));
				for (int j = 0; j < 3; j++)
					DrawTexture(gl, mGR.mTex_More[i][j], -.5f + i % 2, .72f
							- th * (i / 2) + (mGR.mTex_STile[0].Height() - j * mGR.mTex_STile[0].Height()));
			} else if (i == 1) {
				DrawTexture(gl, mGR.mTex_More[1][0], 0.5f - .13f, .72f - th * (1 / 2));
				DrawTexture(gl, mGR.mTex_More[1][M.setValue ? 1 : 2], 0.5f + .19f, .72f - th * (1 / 2));
			} else {
				DrawTexture(gl, mGR.mTex_More[i][0], -.5f + i % 2, .72f - th * (i / 2));
			}
		}
		
		DrawTexture(gl, mGR.mTex_More[1][mGR.mul_Color ? 1 : 2], -.5f + 5 % 2, .62f - th * (5 / 2));
		
		DrawTextureS(gl, mGR.mTex_STile[0], 0, -.92f, .56f);
		DrawTexture(gl, mGR.mTex_Back, 0, -.92f);
		if(mGR.mPName.length()>0)
			mGR.mFont.Draw_String(gl, mGR.mPName, 0, -.81f, 2, 1);
	}

	boolean Handle_More(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			for (int i = 0; i < 6; i++) {
				if (CircRectsOverlap(-.5f + i % 2, .72f - th * (i / 2),
						mGR.mTex_BTile[0].width() * .45f,
						mGR.mTex_BTile[0].Height() * .45f,
						screen2worldX(event.getX()),
						screen2worldY(event.getY()), .01f)) {
					M.playAll(GameRenderer.mContext);
					switch (i) {
					case 0:
						GameRenderer.mStart.onShowLeaderboardsRequested();// Score
						return true;
					case 1:
						M.setValue = !M.setValue;
						return true;
					case 2:
						MoreGame();
						return true;
					case 3:
						M.GameScreen = M.GAMEABOUT;
						return true;
					case 4: {
						for (int j = 0; j < 3; j++) {
							if (CircRectsOverlap(-.5f + i % 2, .72f
									- th
									* (i / 2)
									+ (mGR.mTex_STile[0].Height() - j
											* mGR.mTex_STile[0].Height()),
									mGR.mTex_STile[0].width() * .45f,
									mGR.mTex_STile[0].Height() * .45f,
									screen2worldX(event.getX()),
									screen2worldY(event.getY()), .01f)) {
								switch (j) {
								case 0:
									FaceBook();
									return true;
								case 1:
									Google();
									return true;
								case 2:
									share2friend();
									return true;
								}
							}
						}
					}
						break;
					case 5:
						mGR.mul_Color = !mGR.mul_Color;
						return true;
					}
				}
			}
			if (CircRectsOverlap(0, -.92f, mGR.mTex_BTile[0].width() * .45f,
					mGR.mTex_BTile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					.01f)) {
				M.GameScreen = M.GAMEMENU;
				mGR.mSelTile =-1;
				M.playAll(GameRenderer.mContext);
			}
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~More End~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~Inside Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Inside(GL10 gl) {
		DrawColor(gl, mGR.mTex_BTile[1], 0, -.83f, .9f,.9f,.9f, 2.1f, .64f);
		for (int i = 0; i < 6; i++) {
			DrawTexture(gl, mGR.mTex_BTile[(i == 1 || i == 2 || i == 5) ? 1 : 0], -.5f + i % 2, .72f - th * (i / 2));
			DrawTexture(gl, mGR.mTex_InText[i], -.5f + i % 2, .72f - th * (i / 2));
		}
		DrawTextureS(gl, mGR.mTex_STile[0], 0,-.92f,.56f);
		DrawTexture(gl, mGR.mTex_Back, 0, -.92f);
		if(mGR.mPName.length()>0)
			mGR.mFont.Draw_String(gl, mGR.mPName, 0, -.81f, 2, 1);
	}

	boolean Handle_Inside(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			for (int i = 0; i < 6; i++) {
				if (CircRectsOverlap(-.5f + i % 2, .72f - th * (i / 2),
						mGR.mTex_BTile[0].width() * .45f,
						mGR.mTex_BTile[0].Height() * .45f,
						screen2worldX(event.getX()),
						screen2worldY(event.getY()), .01f)) {
					M.playAll(GameRenderer.mContext);
					switch (i) {
					case 0:
						mGR.gameReset(-1, -1, -1, 1);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 1:
						mGR.gameReset(-1, -1, -1, 2);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 2:
						mGR.gameReset(-1, -1, -1, 3);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 3:
						mGR.gameReset(-1, -1, -1, 4);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 4:
						mGR.gameReset(-1, -1, -1, 5);
						M.GameScreen = M.GAMEPLAY;
						break;
					case 5:
						mGR.mInApp.onBuyGold10000(null);//Buy
						break;
					}
				
				}
			}
			if (CircRectsOverlap(0, -.92f, mGR.mTex_BTile[0].width() * .45f,
					mGR.mTex_BTile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					.01f)) {
				M.GameScreen = M.GAMEMENU;
				mGR.mSelTile =-1;
				M.playAll(GameRenderer.mContext);
			}
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~Inside End~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	float th; 
	float anim[] = {0,0,0};
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~Menu Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Menu(GL10 gl){
		DrawColor(gl, mGR.mTex_BTile[1], 0, -.83f, .9f,.9f,.9f, 2.1f, .64f);
		for(int i=0;i<6;i++){
			DrawTexture(gl, mGR.mTex_BTile[(i == 1 || i == 2 || i == 5)?1:0], -.5f+i%2, .72f-th*(i/2));
			if (mGR.mSelTile != i || i > 3)
				DrawTexture(gl, mGR.mTex_MemuText[i], -.5f+i%2, .72f-th*(i/2));
			else{
				for(int j=0;j<3;j++){
					DrawTexture(gl,mGR.mTex_STile[(((i == 1 || i == 2) ? 1 : 0) + j) % 2], anim[j]-.5f + i % 2, .72f - th * (i / 2)
									+ mGR.mTex_STile[j % 2].Height() - (mGR.mTex_STile[j % 2].Height() * j));
					DrawTexture(gl,mGR.mTex_SMText[i][j], anim[j]-.5f + i % 2, .72f - th * (i / 2)
							+ mGR.mTex_STile[j % 2].Height() - (mGR.mTex_STile[j % 2].Height() * j));
					if(anim[j]<0 && i % 2 == 0){
						anim[j]+=.15f;
						if(anim[j]>0)
							anim[j] =0;
					}
					if (anim[j] > 0 && i % 2 == 1) {
						anim[j] -= .15f;
						if (anim[j] < 0)
							anim[j] = 0;
					}
				}
			}
		}
		
		if(GameRenderer.mStart.isSignedIn()){
			DrawTextureS(gl, mGR.mTex_STile[0], 0,-.92f,.56f);
			DrawTexture(gl, mGR.mTex_Exit, 0, -.92f);
			mGR.mFont.Draw_String(gl, mGR.mPName, 0, -.81f, 2, 1);
		}
		else{
			DrawTextureS(gl, mGR.mTex_STile[0]	, -.68f,-.92f,.56f);
			DrawTexture(gl, mGR.mTex_Exit		, -.68f, -.92f);
			DrawTextureS(gl, mGR.mTex_STile[0]	, .68f,-.92f,.56f);
			DrawTexture(gl, mGR.mTex_Signin		, .68f, -.92f);
		}
	}

	boolean Handle_Menu(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			for (int i = 0; i < 6; i++) {
				if (CircRectsOverlap(-.5f + i % 2, .72f - th * (i / 2),
						mGR.mTex_BTile[0].width() * .45f,
						mGR.mTex_BTile[0].Height() * .45f,
						screen2worldX(event.getX()),
						screen2worldY(event.getY()), .01f)) {
					M.playAll(GameRenderer.mContext);
					if (mGR.mSelTile != i) {
						mGR.mSelTile =i;
						switch (i) {
						case 0:case 1:case 2:case 3:
							mGR.mSelTile = i;
							anim[0] = 1.25f * (mGR.mSelTile % 2 == 0 ? -1 : 1);
							anim[1] = 1.75f * (mGR.mSelTile % 2 == 0 ? -1 : 1);
							anim[2] = 2.25f * (mGR.mSelTile % 2 == 0 ? -1 : 1);
							return true;
						case 4:
							M.GameScreen = M.GINSIDE;
							return true;
						case 5:
							M.GameScreen = M.GAMEMORE;
							return true;
						}
					} else {
						for (int j = 0; j < 3; j++) {
							if (CircRectsOverlap(anim[j]-.5f + i % 2, .72f - th * (i / 2)
									+ mGR.mTex_STile[j % 2].Height() - (mGR.mTex_STile[j % 2].Height() * j),
									mGR.mTex_STile[0].width() * .45f,
									mGR.mTex_STile[0].Height() * .45f,
									screen2worldX(event.getX()),
									screen2worldY(event.getY()), .01f)) {
								M.GameScreen = M.GAMEPLAY;
								mGR.mSelSemiTile = j;
								switch (mGR.mSelSemiTile){
								case 0:
									switch (mGR.mSelTile) {
									case 0:
										mGR.gameReset(25,-1,0,0);
										break;
									case 1:
										mGR.gameReset(-1,-1,-1,0);
										break;
									case 2:
										mGR.gameReset(-1, 15, 0, 0);
										break;
									case 3:
										mGR.gameReset(-1, 10, -1, 0);
										break;
									}
									return true;
								case 1:
									switch (mGR.mSelTile) {
									case 0:
										mGR.gameReset(50,-1,0,0);
										break;
									case 1:
										mGR.gameReset(-1,-1,-2,0);
										break;
									case 2:
										mGR.gameReset(-1, 30, 0, 0);
										break;
									case 3:
										mGR.gameReset(-1, 20, -2, 0);
										break;
									}
									return true;
								case 2:
									switch (mGR.mSelTile) {
									case 0:
										mGR.gameReset(100,-1,0,0);
										break;
									case 1:
										mGR.gameReset(-1,-1,1,0);
										break;
									case 2:
										mGR.gameReset(-1, 60, 0, 0);
										break;
									case 3:
										mGR.gameReset(-1, 30, 1, 0);
										break;
									}
									return true;
								}
							}

						}
					}
				}
			}
			if(GameRenderer.mStart.isSignedIn()){
			if (CircRectsOverlap(0, -.92f, mGR.mTex_BTile[0].width() * .45f,
					mGR.mTex_BTile[0].Height() * .45f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					.01f)) {
				GameRenderer.mStart.Exit();
			}
			}else{
				if (CircRectsOverlap(-.68f, -.92f, mGR.mTex_BTile[0].width() * .45f,
						mGR.mTex_BTile[0].Height() * .45f,
						screen2worldX(event.getX()), screen2worldY(event.getY()),
						.01f)) {
					GameRenderer.mStart.Exit();
				}
				if (CircRectsOverlap(0.68f, -.92f, mGR.mTex_BTile[0].width() * .45f,
						mGR.mTex_BTile[0].Height() * .45f,
						screen2worldX(event.getX()), screen2worldY(event.getY()),
						.01f)) {
					GameRenderer.mStart.beginUserInitiatedSignIn();
				}
				}
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~Menu End~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~GamePlay Start~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	int findDoubleROW(){
		int d =0;
		for (int j = 0; j < mGR.mTile[mGR.RowNo].length; j++) {
			if (mGR.mTile[mGR.RowNo][j].clr == M.DOUBL_ROW) {
				d++;
			}
		}
		return d;
	}
	boolean findBomb(){

		for (int j = 0; j < mGR.mTile[mGR.RowNo].length; j++) {
			if (mGR.mTile[mGR.RowNo][j].clr == M.BOMB) {
				return true;
			}
		}
		return false;
	}
	float findbig(){
		float high = 0;
		for (int i = 0; i < mGR.mTile.length; i++) {
			if (mGR.mTile[i][0].y > high) {
				high = mGR.mTile[i][0].y;
			}
		}
		return high;
	}
	void checkColide(float _x, float _y) {
//		
		if (mGR.type == 1 && findBomb()) {
			
//			for (int i = mGR.RowNo; i <= mGR.RowNo+1; i++){
//				System.out.println("["+(i%mGR.mTile.length)+" = "+i+"]   "+mGR.mTile[i%mGR.mTile.length][0].clr
//						+"   "+mGR.mTile[i%mGR.mTile.length][1].clr
//						+"   "+mGR.mTile[i%mGR.mTile.length][2].clr
//						+"   "+mGR.mTile[i%mGR.mTile.length][3].clr);
//			}
			
			for (int i = mGR.RowNo; i <= mGR.RowNo+1; i++) {
				for (int j = 0; j < mGR.mTile[i%mGR.mTile.length].length; j++) {
					if (mGR.mTile[i%mGR.mTile.length][j].clr != M.CORECT) {
						if (CircRectsOverlap(-0.75f + j * .5f,
								mGR.mTile[i%mGR.mTile.length][0].y, .25f, .25f, _x, _y,
								.05f)) {
							if (mGR.mTile[i%mGR.mTile.length][j].clr == M.BLACK) {
								mGR.mTile[i%mGR.mTile.length][j].clr = M.GRAY;
								mGR.go += .5f;
								mGR.RowNo=i+1;
								mGR.RowNo %= mGR.mTile.length;
								mGR.MoveTile++;
								mGR.TotalTile++;
								M.playAll(GameRenderer.mContext);
								return;
							} else {
								M.Over(GameRenderer.mContext,R.drawable.gameover);
								mGR.OverCounter = 1;
								mGR.mTile[i%mGR.mTile.length][j].clr = M.BOMB_RED;
								z = .1f;
								vz = 1.3f;
								return;
							}
						}
					}
				}
			}

		}else
		{
			for (int j = 0; j < mGR.mTile[mGR.RowNo].length; j++) {
				if (mGR.mTile[mGR.RowNo][j].clr != M.CORECT) {
					if (CircRectsOverlap(-0.75f + j * .5f,
							mGR.mTile[mGR.RowNo][0].y, .25f, .25f, _x, _y, .05f)) {
						if (mGR.mTile[mGR.RowNo][j].clr == M.DOUBL_TAP) {
							mGR.mTile[mGR.RowNo][j].clr = M.BLACK;
							return;
						}
						else if (mGR.mTile[mGR.RowNo][j].clr == M.DOUBL_ROW) {
							if (findDoubleROW() > 1) {
								mGR.mTile[mGR.RowNo][j].clr = M.WHITE;
								M.playAll(GameRenderer.mContext);
								return;
							} else {
								mGR.mTile[mGR.RowNo][j].clr = M.GRAY;
								mGR.go += .5f;
								mGR.RowNo++;
								mGR.RowNo %= mGR.mTile.length;
								mGR.MoveTile++;
								mGR.TotalTile++;
								M.playAll(GameRenderer.mContext);
								return;
							}
						}
						else if (mGR.mTile[mGR.RowNo][j].clr != M.WHITE) {
							mGR.mTile[mGR.RowNo][j].clr = M.GRAY;
							mGR.go += .5f;
							mGR.RowNo++;
							mGR.RowNo %= mGR.mTile.length;
							mGR.MoveTile++;
							mGR.TotalTile++;
							M.playAll(GameRenderer.mContext);
							return;
						} else {
							M.Over(GameRenderer.mContext, R.drawable.gameover);
							mGR.OverCounter = 1;
							mGR.mTile[mGR.RowNo][j].clr = M.RED;
							z=.1f;vz=1.3f;
							return;
						}
					}
				}
			}
		}
	}
	int del;

	
	void gameLogic(){
		for (int i = 0; i < mGR.mTile.length; i++) {
			for (int j = 0; j < mGR.mTile[i].length; j++){
				if ((mGR.go > 0 || mGR.spd != 0) && mGR.MoveTile > 0 && mGR.OverCounter < 1)
					mGR.mTile[i][j].update(mGR.Speed);
				if(mGR.OverCounter > 1 && mGR.OverCounter < 10 && mGR.OverRev)
					mGR.mTile[i][j].update(-mGR.Speed*2);
			}
		}
		if (mGR.spd == 0) {
			if (mGR.go > 0) {
				mGR.go += mGR.Speed;
				if(mGR.lastWindow <100){
					mGR.lastWindow += mGR.Speed;
				}
			}
		}
		if (mGR.MoveTile > 0) {
			if (mGR.spd < 0 && mGR.Speed > -.07f) {
				mGR.Speed -= .00001f;
			}
			if (mGR.spd > 0 && mGR.Speed < 0.07f) {
				mGR.Speed += .00001f;
			}
		}
		for (int i = 0; i < mGR.mTile.length; i++) {
			if (mGR.MoveTile + mGR.mTile.length - 4 < mGR.tile || mGR.tile == -1) {
				if ((mGR.mTile[i][0].y < -1.8f && mGR.spd < 1)||(mGR.mTile[i][0].y > 1.8f && mGR.spd > 0)) {
					int rand = M.mRand.nextInt(4);
					for (int j = 0; j < mGR.mTile[i].length; j++) {
						mGR.mTile[i][j].set(rand == j ? M.BLACK : M.WHITE,
						mGR.mTile[i == 0 ? mGR.mTile.length - 1 : i - 1][0].y +
						(mGR.spd < 1 ?.5f:-.5f));
						if (mGR.type > 0) {
							if(mGR.next <= 0 && mGR.mTile[i][j].clr == M.BLACK){
							switch (mGR.type) {
							case 1:
								 mGR.mTile[i][j].clr = M.BOMB;//Bomb 
								break;
							case 2:
								mGR.Bllinck = 30;
								break;
							case 3:
								mGR.mTile[i][j].clr = M.DOUBL_ROW;//Bomb
								mGR.mTile[i][(j+2)%4].clr = M.DOUBL_ROW;//Bomb
								break;
							case 4:
								mGR.mTile[i][j].clr = M.DOUBL_TAP;
								break;
							case 5:
								mGR.mTile[i][j].clr = M.DOUBL_HIDE;
								break;
							}
							mGR.next = M.mRand.nextInt(4)+4;
							}
						}
					}
					mGR.next--;
				}
			}else if(mGR.lastWindow >100){
				mGR.lastWindow = findbig()+1.33f;
			}
		}
		int nw = (int)(System.currentTimeMillis() - mGR.STime);
		mGR.Score = ((nw/1000)+((nw%1000f)/1000));
		mGR.Score = new BigDecimal(mGR.Score).setScale(3,BigDecimal.ROUND_HALF_UP).floatValue();
		
		if (mGR.MoveTile >= mGR.tile && mGR.tile != -1){
			if (mGR.lastWindow < 0) {
				M.GameScreen = M.GAMEWIN;
				gamewinLoose();
			}else{
				mGR.go+=.5f;
			}
		}
		if (mGR.OverCounter > 0) {
			mGR.OverCounter++;
			if(mGR.OverCounter>30){
				M.GameScreen = M.GAMEOVER;
				gamewinLoose();
			}
		}
		if (((mGR.mTile[mGR.RowNo][0].y < -1.4 && mGR.spd < 0)||
				(mGR.mTile[mGR.RowNo][0].y > 1.4 && mGR.spd >0)) 
				&& mGR.OverCounter < 1) {
			mGR.OverCounter = 1;
			z=.1f;vz=1.3f;
			mGR.OverRev = true;
			for (int j = 0; j < mGR.mTile[mGR.RowNo].length; j++){
				if(mGR.mTile[mGR.RowNo][j].clr!=M.WHITE){
					mGR.mTile[mGR.RowNo][j].clr = M.LBLACK;
				}
			}
		}
		if(mGR.time >0 && mGR.MoveTile>0){
			if(mGR.time < mGR.Score && mGR.OverCounter <1)
			{
				mGR.OverCounter = 1;
				z=.1f;vz=1.3f;
//				gamewinLoose();
			}
		}
	}
	void Draw_Gameplay(GL10 gl) {
		
		if(mGR.lastWindow <100){
			DrawColor(gl, mGR.mTex_BTile[1], 0, mGR.lastWindow, 1,.4f,.0f, 2.1f, 3.6f);//Orange 255,100,0
		}
		if(mGR.MoveTile <3 && mGR.OverCounter <1){
			if(mGR.spd > 0)
				DrawColor(gl, mGR.mTex_BTile[1], 0, mGR.mTile[0][0].y+1.33f, 1,.4f,.4f, 2.1f, 3.6f);//Orange 255,100,0
			else
				DrawColor(gl, mGR.mTex_BTile[1], 0, mGR.mTile[0][0].y-1.33f, .2f,.9f,.2f, 2.1f, 3.6f);//Orange 255,100,0
		}
		for (int i = 0; i < mGR.mTile.length; i++) {
			for (int j = 0; j < mGR.mTile[i].length; j++){
				if(mGR.mTex_Tile.length > mGR.mTile[i][j].clr){
					if(mGR.mTile[i][j].clr == M.BLACK){
						if(!mGR.mul_Color)
							DrawTexture(gl, mGR.mTex_Tile[mGR.mTile[i][j].clr], -0.75f + j * .5f, mGR.mTile[i][0].y);//
						else{
							switch (mGR.mTile[i][j].mul) {
							case 1:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,0.0f,1.0f,.59f,1,1);//
								break;
							case 2:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,.59f,.40f,.20f,1,1);//
								break;
							case 3:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,1.0f,.59f,0.0f,1,1);//
								break;
							case 4:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,0.0f,1.0f,0.0f,1,1);//
								break;
							case 5:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,0,1,1,1,1);//
								break;
							case 6:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,0,0,1,1,1);//
								break;
							case 7:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,1,0,1,1,1);//
								break;
							default:
								DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,1,1,0,1,1);//
								break;}
						}
					}
				}
				if (mGR.mTile[i][j].clr == M.GRAY)
					DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,.92f,.92f,.92f,1,1);//
				if (Counter % 2 == 0 && mGR.mTile[i][j].clr == M.RED)
					DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,1,0,0,1,1);//
				if (Counter % 2 == 0 && mGR.mTile[i][j].clr == M.LBLACK)
					DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,.9f,0.9f,0.9f,1,1);//
				if(mGR.mTile[i][j].clr == M.BOMB){
					DrawTexture(gl, mGR.mTex_Tile[1], -0.75f + j * .5f, mGR.mTile[i][0].y);//
					DrawTexture(gl, mGR.mTex_Bomb, -0.75f + j * .5f, mGR.mTile[i][0].y);//
				}
				if (mGR.mTile[i][j].clr == M.BOMB_RED){
					DrawColor(gl, mGR.mTex_Tile[2], -0.75f + j * .5f, mGR.mTile[i][0].y,1,0,0,1,1);//
					if(Counter % 2 == 0)
						DrawTexture(gl, mGR.mTex_Bomb, -0.75f + j * .5f, mGR.mTile[i][0].y);//
				}
				if(mGR.mTile[i][j].clr == M.DOUBL_ROW){
					DrawTexture(gl, mGR.mTex_Tile[1], -0.75f + j * .5f, mGR.mTile[i][0].y);//
				}
				if(mGR.mTile[i][j].clr == M.DOUBL_TAP){
					DrawTexture(gl, mGR.mTex_Tile[1], -0.75f + j * .5f, mGR.mTile[i][0].y);//
					DrawTexture(gl, mGR.mTex_DTap, -0.75f + j * .5f, mGR.mTile[i][0].y);//
				}
				if(mGR.mTile[i][j].clr == M.DOUBL_HIDE){
					if(mGR.RowNo !=i){
						for (int k = 0; k < mGR.mTile[i].length; k++){
							DrawTexture(gl, mGR.mTex_Tile[3], -0.75f + k * .5f, mGR.mTile[i][0].y);//
						}
					}else{
						DrawTexture(gl, mGR.mTex_Tile[1], -0.75f + j *.5f, mGR.mTile[i][0].y);//
					}
				}
				if(mGR.MoveTile<=0 && mGR.mTile[i][j].clr != M.WHITE && i==0)
					DrawTexture(gl, mGR.mTex_Start, -0.75f + j * .5f, mGR.mTile[i][0].y);
			}
		}
		
		for (int i = 0; i < mGR.mTile.length; i++) {
			for (int j = 0; j < mGR.mTile[i].length; j++) {
				DrawTexture(gl, mGR.mTex_Tile[0], -0.75f + j * .5f, mGR.mTile[i][0].y);
			}
		}
		
		if (mGR.MoveTile > 0) {
			switch (mGR.mSelTile) {
			case 0:
				NumberColor(gl, mGR.Score + "", 0, 0, 0.95f);
				break;
			case 1:case 4:
				NumberColor(gl, mGR.MoveTile + "", 0, 0, 0.95f);
				break;
			case 2:case 3:
				float time = mGR.time*1000 - (System.currentTimeMillis() - mGR.STime);
				time = ((time / 1000) + ((time % 1000f) / 1000));
				time = new BigDecimal(time).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
				NumberColor(gl, time + "", 0, 0, 0.95f);
				break;
			}

		}
		else{
			if(mGR.mSelTile == 3 || mGR.mSelTile == 2)
				NumberColor(gl, mGR.time+".000", 0, 0, 0.95f);
			else
				NumberColor(gl, "0000", 0, 0, 0.95f);
		}
		
		if(mGR.OverCounter>10){
			DrawColor(gl, mGR.mTex_BTile[1], 0, 0, .24f, .78f, 1f, z*2.1f, z*3.6f);// Blue 60,200,255
			z*=vz;
		}
		if(mGR.Bllinck>0){
			if(mGR.Bllinck>10)
				DrawTransScal(gl, mGR.mTex_BTile[0], 0, 0, 3.6f,(30-mGR.Bllinck)*.05f);
			else if(mGR.Bllinck>5)
				DrawTransScal(gl, mGR.mTex_BTile[0], 0, 0, 3.6f,1);
			else if (Counter%2 == 0)
				DrawTransScal(gl, mGR.mTex_BTile[0], 0, 0, 3.6f,1);
			mGR.Bllinck --;
		}
		gameLogic();
	}

	boolean Handle_GamePlay(MotionEvent event) {
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			if(mGR.OverCounter == -1)
				checkColide(screen2worldX(event.getX()), screen2worldY(event.getY()));
			if(mGR.MoveTile==1)
				mGR.STime = System.currentTimeMillis();
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~GamePlay End~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}
	
	void DrawColor(GL10 gl, SimplePlane Tex, float _x, float _y, float r,
			float g, float b, float sx, float sy) {
		Tex.drawColor(gl, _x, _y, r, g, b, sx, sy);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl,String strs,int type,float x, float y)
	{
		float dx = mGR.mTex_Font[type][0].width()*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-46;
			if(k>=0 && k<mGR.mTex_Font[0].length)
				mGR.mTex_Font[type][k].drawPos(gl,x+i*dx,y);
		}
	}
	void NumberColor(GL10 gl,String strs,int type,float x, float y)
	{
		float dx = mGR.mTex_Font[type][0].width()*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-46;
			if(k>=0 && k<mGR.mTex_Font[0].length)
				mGR.mTex_Font[type][k].drawColor(gl,x+i*dx,y,1,.4f,0,1,1);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void FaceBook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
