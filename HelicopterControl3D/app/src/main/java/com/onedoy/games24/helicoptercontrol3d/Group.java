package com.onedoy.games24.helicoptercontrol3d;
import org.rajawali3d.bounds.IBoundingVolume;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group {
	HTTRenderer mGR;
	int sel = 0;
	byte isSet;

	public Group(HTTRenderer GR) {
		mGR = GR;
	}

	void Draw() {
		Counter++;
//		setting();
		if (mGR.mFont == null) {
			mGR.m2DLogo.setVisible(true);
			if (Counter > 3) {
				mGR.init();
				resume();
			}
			return;
		}
		mGR.m2DLogo.setVisible(false);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.m2DLogo.setVisible(true);
			if (Counter > 60){
				changeScreen(M.GAMEMENU);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu();
			break;
		case M.GAMESETTING:
			Draw_Setting();
			break;
		case M.GAMESHOP:
			Draw_Shop();
			break;
		case M.GAMEOVER:
			Draw_GameOver();
			break;
		case M.GAMEEXIT:
//			Draw_Exit();
			break;
		default:
			Draw_GamePlay();
			break;
		}
		if(reset > 0){
			mGR.setVisible(false);
			reset --;
		}
	}

	public boolean onTouch(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMESETTING:
			Handle_Setting(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEEXIT:
			Handle_Exit(event);
			break;
		default:
			Handle_Game(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	void Draw_Common(){
		for (int i = 0; i < mGR.m3D_BG.length; i++) {
			mGR.m3D_BG[i].setVisible(true);
		}
		
	}
	float fz =1,vfz = .005f;
	void DrawFree(float x,float y){
//		mGR.m2D_Exit[4].setVisible(true);
//		mGR.m2D_Exit[4].setPosition(x, -30, y);
//		mGR.m2D_Exit[4].setScale(sel == 100?1.1f:fz);
////		mGR.mTex_Ads[3].drawTransprentScal(gl, x,y,Sel == 100?1.1f:fz,Sel == 100?0.5f:1);
//		fz+=vfz;
//		if(fz > 1.04)
//			vfz =-.005f;
//		if(fz < 0.9)
//			vfz =0.005f;
//		
//	}
//	void Draw_Exit(){
//		Draw_Common();
//		mGR.m2D_Exit[3].setVisible(true);
//		mGR.m2D_Exit[3].setPosition(21.5, -30, 36.5);
//		mGR.m2D_Exit[3].setScale(sel == 4 ? 1.1 : 1);
//		for(int i =0;i<3;i++){
//			mGR.m2D_Exit[i].setVisible(true);
//			mGR.m2D_Exit[i].setPosition(12.5+9*i, -30, 22.5 +9*.4*i);
//			mGR.m2D_Exit[i].setScale(sel == i+1 ? 1.1 : 1);
//		}
//		DrawFree(3.5f, 32);
	}
	void Handle_Exit(MotionEvent event){
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+" , "+screen2worldY(event.getY()));
		if(CircRectsOverlap( -.68 , -0.5, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;
		}
		if(CircRectsOverlap(-.00 , -0.5, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;		
		}
		if(CircRectsOverlap(0.68 , -0.5, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;	
		}
		if(CircRectsOverlap(0.0 , 0.33, 1.1, .45, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 4;	
		}
		
		if(CircRectsOverlap(-1.30, 0.56, 0.2, .2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 100;	
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				changeScreen(M.GAMEMENU);
				break;
			case 2:
				Counter = 0;
				HTTRenderer.mStart.finish();
				M.GameScreen = M.GAMELOGO;
				break;
			case 3:
				RateUs();
				break;
			case 4:
				Banner();
				break;
			case 100:
				if(M.DOWNLOAD.equalsIgnoreCase(getClass().getPackage().getName()))
					MoreGame();
				else
					Download(M.DOWNLOAD);
				break;
			}
			M.sound2(R.raw.button);
			sel = 0;
		}
	}
	void Download(String str) {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + str));
		HTTRenderer.mStart.startActivity(mIntent);
	}
	
	void Draw_Menu(){
		Draw_Common();
		if (Counter % 100 == 0)
			mGR.mFont.update1Bitmap(0, (int)(sx*100), (int)(sy*100));
		
		mGR.mFont.mPlan[0].setVisible(true);
		mGR.mFont.mPlan[0].setPosition(34, -30, 42);
		
		mGR.m2D_Text.setVisible(true);
		mGR.m2D_Text.setPosition(21, -30, 33);
		
		mGR.m2D_Setting.setVisible(true);
		mGR.m2D_Setting.setPosition(3.8, -30, 34.5);
		mGR.m2D_Setting.setScale(sel == 1 ? 1.1 : 1);
		
		mGR.m2D_Play.setVisible(true);
		mGR.m2D_Play.setPosition(6, -30, 20);
		mGR.m2D_Play.setScale(sel == 2?1.1:1);
		
		mGR.m2D_Player.setVisible(true);
		mGR.m2D_Player.setPosition(14, -30, 23);
		mGR.m2D_Player.setScale(sel == 3?1.1:1);
		
		DrawFree(39, 34);
	}
	void Handle_Menu(MotionEvent event){
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+" , "+screen2worldY(event.getY()));
		if(CircRectsOverlap(-1.34, 0.86, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;
		}
		if(CircRectsOverlap(-1.2 , -0.42, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;		
		}
		if(CircRectsOverlap(-0.55 , -0.45, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;	
		}
		if(CircRectsOverlap(1.45, -0.34, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)){
			sel = 100;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				changeScreen(M.GAMESETTING);
				break;
			case 2:
				mGR.gamereset(true);
				changeScreen(M.GAMEPLAY);
				M.play(R.raw.background);
				break;
			case 3:
				changeScreen(M.GAMESHOP);
				break;
			case 100:
				if(M.DOWNLOAD.equalsIgnoreCase(getClass().getPackage().getName()))
					MoreGame();
				else
					Download(M.DOWNLOAD);
				break;
			}
			M.sound2(R.raw.button);
			sel = 0;
		}
	}
	
	void Draw_Shop(){
		Draw_Common();
		if (Counter % 100 == 0){
			for(int i=0;i<3;i++)
				mGR.mFont.update1Bitmap(i, (int)(sx*100), (int)(sy*100));
		}
		
		for(int i=0;i<3;i++)
			mGR.mFont.mPlan[i].setVisible(true);
		mGR.mFont.mPlan[0].setPosition(34, -30, 42);
		
		mGR.mFont.mPlan[1].setPosition(20, -30, 25.3);
		mGR.mFont.mPlan[2].setPosition(20, -30, 19);
		mGR.mFont.mPlan[2].setScale(sel == 3?5.5:5);
		
		double ang = Math.toRadians(Counter);
		double mx = Math.cos(ang)*1.22;
		double my = Math.sin(ang)*1.22;
		
		mGR.m3D_Player[mGR.plNo].setVisible(true);
		mGR.m3D_Player[mGR.plNo].setPosition(20, -30, 35);
		mGR.m3D_Player[mGR.plNo].setRotX(Counter);
		mGR.mPly.m3D_Blade.setVisible(true);
		mGR.mPly.m3D_Blade.setPosition(20+my, -30+mx, 37.7);
		mGR.mPly.m3D_Blade.setRotation(Counter*30,0,0);
		mGR.mPly.m3D_Blade.setScale(.32);
		
		
		
		mGR.m2D_Arow[0].setVisible(mGR.plNo > 0);
		mGR.m2D_Arow[0].setPosition(6, -30, 25.7);
		mGR.m2D_Arow[0].setScale(sel == 1?1.1:1);
		mGR.m2D_Arow[1].setVisible(mGR.plNo < mGR.m3D_Player.length - 1);
		mGR.m2D_Arow[1].setPosition(35.7, -30, 37.3);
		mGR.m2D_Arow[1].setScale(sel == 2?1.1:1);
		
		
		mGR.m2D_Back.setVisible(true);
		mGR.m2D_Back.setPosition(5.5, -30, 34.5);
		mGR.m2D_Back.setScale(sel == 5?1.1:1);
		
	}
	void Handle_Shop(MotionEvent event){
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+" , "+screen2worldY(event.getY()));
		if(CircRectsOverlap(-1.15, 0.02, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;
		}
		if(CircRectsOverlap(1.2 , -0.04, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;		
		}
		if(CircRectsOverlap( -0.06 , -0.61, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;	
		}
		if(CircRectsOverlap( -1.2, 0.7, .25, .20, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 5;	
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				if(mGR.plNo>0){
					mGR.plNo--;
					for(int i =0;i<mGR.m3D_Player.length;i++)
						mGR.m3D_Player[i].setVisible(false);
					for(int i =0;i<mGR.mFont.mPlan.length;i++){
						mGR.mFont.update1Bitmap(i, 0, 0);
					}
				}
				break;
			case 2:
				if(mGR.plNo<mGR.m3D_Player.length-1){
					mGR.plNo++;
					for(int i =0;i<mGR.m3D_Player.length;i++)
						mGR.m3D_Player[i].setVisible(false);
					for(int i =0;i<mGR.mFont.mPlan.length;i++){
						mGR.mFont.update1Bitmap(i, 0, 0);
					}
				}
				break;
			case 3:
				if (mGR.mHScore >= M.POINT[mGR.plNo])
					{
					mGR.mPly.imgNo = mGR.plNo;
					mGR.gamereset(true);
					changeScreen(M.GAMEPLAY);
					M.play(R.raw.background);
					}
				else{
					HTTRenderer.mStart.DoNot();
					//mPaint.setColor(Color.GRAY);
				}
				break;
			case 5:
				changeScreen(M.GAMEMENU);
				break;
			}
			M.sound2(R.raw.button);
			sel = 0;
		}
	}
	
	
	
	void Draw_Setting(){
		Draw_Common();
		if (Counter % 100 == 0)
			mGR.mFont.update1Bitmap(0, (int)(sx*100), (int)(sy*100));
		
		mGR.mFont.mPlan[0].setVisible(true);
		mGR.mFont.mPlan[0].setPosition(20.7, -30, 31.9);
		
		mGR.m2D_Comman.setVisible(true);
		mGR.m2D_Comman.setPosition(20, -30, 32.5);
		mGR.m2D_Comman.setScale(.9);
		
		mGR.m2D_Setex.setVisible(true);
		mGR.m2D_Setex.setPosition(20.2, -30, 41.45);
		
		mGR.m2D_Btn.setVisible(true);
		mGR.m2D_Btn.setPosition(20, -30, 36.8);
		
		mGR.m2D_STxt.setVisible(true);
		mGR.m2D_STxt.setPosition(20.5, -30, 33.55);
		
		mGR.m2D_Back.setVisible(true);
		mGR.m2D_Back.setPosition(9, -30, 36.8);
		mGR.m2D_Back.setScale(sel == 1?1.1:1);
		
		mGR.m2D_Sound[0].setVisible(M.setValue);
		mGR.m2D_Sound[1].setVisible(!M.setValue);
		mGR.m2D_Sound[0].setPosition(20, -30, 30);
		mGR.m2D_Sound[1].setPosition(20, -30, 30);
		mGR.m2D_Sound[0].setScale(sel == 2 ? 1.1 : 1);
		mGR.m2D_Sound[1].setScale(sel == 2 ? 1.1 : 1);
		
//		mGR.m2D_Leader.setVisible(true);
//		mGR.m2D_Leader.setPosition(13.3, -30, 22.7);
//		mGR.m2D_Leader.setScale(sel == 3?1.1:1);
//		
//		mGR.m2D_Share.setVisible(true);
//		mGR.m2D_Share.setPosition(20, -30, 25.5);
//		mGR.m2D_Share.setScale(sel == 4?1.1:1);
//
//		mGR.m2D_Achiev.setVisible(true);
//		mGR.m2D_Achiev.setPosition(27, -30, 28.5);
//		mGR.m2D_Achiev.setScale(sel == 5?1.1:1);
	}
	void Handle_Setting(MotionEvent event){
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+" , "+screen2worldY(event.getY()));
		if(CircRectsOverlap(-0.92 , 0.78, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;//Back
		}
		if(CircRectsOverlap(-0.05, -0.09, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;//Sound
		}
//		if(CircRectsOverlap(-0.60, -0.480, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
//			sel = 3;//Leader
//		}
//		if(CircRectsOverlap(-.05, -0.48, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
//			sel = 4;//Share
//		}
//		if(CircRectsOverlap(0.55, -0.480, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
//			sel = 5;//Achievement
//		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				changeScreen(M.GAMEMENU);
				break;
			case 2:
				M.setValue=!M.setValue;
				break;
			case 3:
//				HTTRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 4:
//				share2friend();
				break;
			case 5:
//				HTTRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			}
			M.sound2(R.raw.button);
			sel = 0;
		}
	}
	
	void SetOver(){
		M.loopstop();
		M.sound1(R.raw.blast);
		mGR.mPly.setOvet();
		int clr = M.mRand.nextInt(10);
		for(int i = 0;i<mGR.mColi.length;i++){
			mGR.mColi[i].set(mGR.mPly.obj.get(0).getPosition(),clr);
		}
	}
	void Draw_GamePlay() {
		mGR.mFont.mPlan[0].setVisible(true);
		mGR.mFont.mPlan[0].setPosition(34, -30, 42);
		if (mGR.mPly.OverCount == 0) {
			for (int i = 0; i < mGR.m3D_BG.length; i++) {
				if(!mGR.iSGamePause)
					mGR.m3D_BG[i].setX(mGR.m3D_BG[i].getX() + M.SPD);
				if(!mGR.m3D_BG[i].isVisible())
					mGR.m3D_BG[i].setVisible(true);
			}

			for (int i = 0; i < mGR.m3D_BG.length; i++) {
				if (mGR.m3D_BG[i].getX() < -50) {
					mGR.m3D_BG[i].setX(mGR.m3D_BG[(i == 0 ? mGR.m3D_BG.length : i) - 1].getX() + M.A * M.B);
				}
			}
			if (mGR.mPly.obj.size() > 0) {
				mGR.mPly.update(Counter);
				IBoundingVolume bbox2 = mGR.mPly.obj.get(0).getGeometry().getBoundingBox();
				bbox2.transform(mGR.mPly.obj.get(0).getModelMatrix());
				bbox2.setBoundingColor(Color.BLUE);
				for (int i = 0; i < mGR.mOpp.length; i++) {
					mGR.mOpp[i].update(mGR);
					if (mGR.mOpp[i].m3D_Obj.getX() < 5) {
						IBoundingVolume bbox = mGR.mOpp[i].m3D_Obj.getGeometry().getBoundingBox();
						bbox.transform(mGR.mOpp[i].m3D_Obj.getModelMatrix());
						if (bbox.intersectsWith(bbox2)){
							mGR.mOpp[i].m3D_Obj.setColor(Color.RED);
							SetOver();
						}
					}
					if (mGR.mOpp[i].m3D_Obj.getX() < -40) {
						mGR.mOpp[i].set(mGR.mOpp[(i == 0 ? mGR.mOpp.length : i) - 1].m3D_Obj.getX());
					}
				}
			}
			
//			mGR.m2D_GameOver.setVisible(false);
		} else if (mGR.mPly.OverCount > 0) {
			mGR.mPly.OverCount++;
			for (int i = 0; i < mGR.mColi.length; i++) {
				mGR.mColi[i].update();
			}
			if (mGR.mPly.OverCount == 60){
				changeScreen(M.GAMEOVER);
				HTTRenderer.mStart.ShowInterstitial();
			}
		}
		mGR.mFont.mPlan[1].setVisible(mGR.iSGamePause);
		mGR.mFont.mPlan[1].setPosition(22, -30, 22);
	}
	void Handle_Game(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			mGR.mPly.isTap = true;
			if(mGR.iSGamePause)
				mGR.iSGamePause = false;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			mGR.mPly.isTap = false;
		}
	}
	
	
	
	void Draw_GameOver(){
		Draw_Common();
		
		mGR.mFont.mPlan[0].setVisible(true);
		mGR.mFont.mPlan[0].setPosition(20, -30, 33);
		
		mGR.m2D_Comman.setVisible(true);
		mGR.m2D_Comman.setPosition(20, -30, 32.5);
		mGR.m2D_Comman.setScale(.9);
		
		mGR.m2D_GameOver.setVisible(true);
		mGR.m2D_GameOver.setPosition(20.2, -30, 41.45);
		
		
		
		mGR.m2D_Play.setVisible(true);
		mGR.m2D_Play.setPosition(20, -30, 30);
		mGR.m2D_Play.setScale(sel == 6?.8:.7);
		
		
		
//		mGR.m2D_Leader.setVisible(true);
//		mGR.m2D_Leader.setPosition(11.4+5.85*0, -30, 21.4+.4*5.85*0);
//		mGR.m2D_Leader.setScale(sel == 1?1.1:1);
		
		mGR.m2D_Sound[0].setVisible(M.setValue);
		mGR.m2D_Sound[1].setVisible(!M.setValue);
		mGR.m2D_Sound[0].setPosition(11.4+5.85*1, -30, 21.4+.4*5.85*1);
		mGR.m2D_Sound[1].setPosition(11.4+5.85*1, -30, 21.4+.4*5.85*1);
		mGR.m2D_Sound[0].setScale(sel == 2 ? 1.1 : 1);
		mGR.m2D_Sound[1].setScale(sel == 2 ? 1.1 : 1);
		
		mGR.m2D_Player.setVisible(true);
		mGR.m2D_Player.setPosition(11.4+5.85*2, -30, 21.4+.4*5.85*2);
		mGR.m2D_Player.setScale(sel == 3?1.1:1);
		
//		mGR.m2D_Achiev.setVisible(true);
//		mGR.m2D_Achiev.setScale(sel == 4?1.1:1);
//		mGR.m2D_Achiev.setPosition(11.4+5.85*3, -30, 21.4+.4*5.85*3);
		
		mGR.m2D_Back.setVisible(true);
		mGR.m2D_Back.setPosition(9, -30, 36.8);
		mGR.m2D_Back.setScale(sel == 5?1.1:1);
		DrawFree(39, 34);
	}
	void Handle_GameOver(MotionEvent event){
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+" , "+screen2worldY(event.getY()));
		if(CircRectsOverlap(-0.72, -.55, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
//			sel = 1;//Leader
		}
		if(CircRectsOverlap(-0.25 , -0.55, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;//Sound
		}
		if(CircRectsOverlap(0.20 , -0.55, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;//Shop
		}
		if(CircRectsOverlap(0.70 , -0.55, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
//			sel = 4;// Achieve
		}
		if(CircRectsOverlap(-0.92 , 0.78, .15, .15, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 5;//Back
		}
		if(CircRectsOverlap(-0.05 , -0.1, .20, .20, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 6;//Play
		}
		if(CircRectsOverlap(1.45, -0.34, .25, .25, screen2worldX(event.getX()), screen2worldY(event.getY()), .02)){
//			sel = 100;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
//				HTTRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 2:
				M.setValue = !M.setValue;
				break;
			case 3:
				changeScreen(M.GAMESHOP);
				break;
			case 4:
//				HTTRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 5:
				changeScreen(M.GAMEMENU);
				break;
			case 6:
				if(M.GameScreen == M.GAMEOVER){
					mGR.gamereset(true);
					mGR.bgNo++;
					mGR.mBGTexture.setBitmap(mGR.mBitBG[mGR.bgNo%mGR.mBitBG.length]);
					mGR.getTextureManager().replaceTexture(mGR.mBGTexture);
				}
				changeScreen(M.GAMEPLAY);
				M.play(R.raw.background);
				break;
			case 100:
				if(M.DOWNLOAD.equalsIgnoreCase(getClass().getPackage().getName()))
					MoreGame();
				else
					Download(M.DOWNLOAD);
				break;
			}
			M.sound2(R.raw.button);
			sel = 0;
		}
	}
	
	
	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}

	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}

	boolean Rect2RectIntersection(double ax, double ay, double adx, double ady,
			double bx, double by, double bdx, double bdy) {
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

	double GetAngle(double d, double e) {
		if (d == 0)
			return e >= 0 ? Math.PI / 2 : -Math.PI / 2;
		else if (d > 0)
			return Math.atan(e / d);
		else
			return Math.atan(e / d) + Math.PI;
	}

	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		Start.mContext.startActivity(mIntent);
	}

	void Banner() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.BANNER));
		Start.mContext.startActivity(mIntent);
	}
	
	void MoreGame() {
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(M.MARKET));
//		Start.mContext.startActivity(mIntent);
	}

	void Twitter() {
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
//		Start.mContext.startActivity(mIntent);
	}

	void google() {
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
//		Start.mContext.startActivity(mIntent);
	}

	void facebook() {
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
//		Start.mContext.startActivity(mIntent);
	}

	int _keyCode = 0;
	float sx, sy, sz = .20f;
	int Counter = 0;
	int mSel = 0;

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
		case 5:
			sz -= ud;
			break;
		case 6:
			sz += ud;
			break;
		}
		System.out.println("~~~~~~~  " + sx + "f,  " + sy + "f,  " + sz + "f");
	}

	public boolean Handle(MotionEvent event) {
		int val = 170;
		if (event.getX() < val && event.getY() < val) {
			_keyCode = 1;
		}
		if (event.getX() > M.ScreenWidth - val && event.getY() < val) {
			_keyCode = 2;
		}
		if (event.getX() < val && event.getY() > M.ScreenHieght - val) {
			_keyCode = 3;
		}
		if (event.getX() > M.ScreenWidth - val
				&& event.getY() > M.ScreenHieght - val) {
			_keyCode = 4;
		}
		if (event.getX() < val && event.getY() > (M.ScreenHieght - val * 2) / 2
				&& event.getY() < (M.ScreenHieght + val) / 2) {
			_keyCode = 5;
		}
		if (event.getX() > M.ScreenWidth - val
				&& event.getY() > (M.ScreenHieght - val * 2) / 2
				&& event.getY() < (M.ScreenHieght + val) / 2) {
			_keyCode = 6;
		}
		if (event.getAction() == MotionEvent.ACTION_UP)
			_keyCode = 0;
		return true;
	}

	void changeScreen(int scr) {
		M.GameScreen = scr;
		mGR.setVisible(false);
		for(int i =0;i<mGR.mFont.mPlan.length;i++){
			mGR.mFont.update1Bitmap(i, 0, 0);
		}
		reset = 5;
	}
	int reset = 0; 
	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Roking new Game '"
				+ Start.mContext.getResources().getString(R.string.app_name)
				+ "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK);
		try {
			Start.mContext.startActivity(Intent
					.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(HTTRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
	void resume() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPUASE;
		M.stop();
		SharedPreferences prefs = HTTRenderer.mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
		M.GameScreen = prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		
		
		for(int i =0;i<mGR.mOpp.length;i++){
			mGR.mOpp[i].isAdd = prefs.getBoolean("c", mGR.mOpp[i].isAdd);
			mGR.mOpp[i].m3D_Obj.setX(prefs.getFloat("d", (float)mGR.mOpp[i].m3D_Obj.getX()));
		}
		{
			mGR.mPly.OverCount = prefs.getInt("e", mGR.mPly.OverCount);
			mGR.mPly.imgNo = prefs.getInt("f", mGR.mPly.imgNo);
			mGR.mPly.vz = prefs.getFloat("g", mGR.mPly.vz);
			if(mGR.mPly.obj.size()>0)
				mGR.mPly.obj.get(0).setX(prefs.getFloat("h", (float)mGR.mPly.obj.get(0).getZ()));
		}
		
		mGR.bgNo = prefs.getInt("i", mGR.bgNo);
		mGR.plNo = prefs.getInt("j", mGR.plNo);
		mGR.mScore = prefs.getInt("k", mGR.mScore);
		mGR.mHScore = prefs.getInt("l", mGR.mHScore);
		mGR.mTotal = prefs.getInt("m", mGR.mTotal);
		mGR.iSGamePause = true;
	}
}
