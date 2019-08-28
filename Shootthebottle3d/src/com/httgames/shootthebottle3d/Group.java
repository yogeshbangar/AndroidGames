package com.httgames.shootthebottle3d;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.0f;
	int counter = 0;
	int sel =0;
	public void setting() {float ud = .01f;switch (_keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;case 5:sz -= ud;break;case 6:sz += ud;break;}System.out.println(counter+"~~~~~~~  " + sx + "f,  " + sy + "f,  "+ sz + "f");}
	public boolean Handle(MotionEvent event){
		int val = 170;
		if(event.getX() <val && event.getY() <val){_keyCode = 1;}
		if(event.getX() >M.ScreenWidth - val && event.getY() <val){_keyCode = 2;}
		if(event.getX() < val && event.getY() >M.ScreenHieght-val){_keyCode = 3;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >M.ScreenHieght-val){_keyCode = 4;}
		if(event.getX() < val && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 5;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 6;}
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	void Draw(){
		counter++;
		
		if(counter < 5 && mGR.o2D_Start == null){
			if(counter > 3 && mGR.o2D_Start == null){
				mGR.init();
				HTTRenderer.mStart.resume();
				mGR.setVisible(false);
			}
			return;
		}
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.oLogo.setVisible(true);
			if(counter > 60){
				M.GameScreen =M.GAMEMENU;
				mGR.setVisible(false);
			}
			break;
		case M.GAMEMENU:
			Splash();
			break;
		case M.GAMEARCARD:
			ArcadeMenu();
			break;
		case M.GAMETIME:
			TimeMenu();
			break;
		case M.GAMEPLAY:
			Draw_Gameplay();
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Over_Pause();
			break;
		}
		
		
//		setting();
	}
	public boolean onTouch(View v, MotionEvent event) {
		if(counter < 5 && mGR.o2D_Start == null){
			return false;
		}
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Splash(event);
			break;
		case M.GAMEARCARD:
			Handle_Arcade(event);
			break;
		case M.GAMETIME:
			Handle_Time(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Handle_OverPause(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	
	void Splash(){
		mGR.o2D_Splash.setPosition(0, 0, 0);
		
		
		mGR.o2D_Splash.setVisible(true);
		mGR.o2D_TimeB.setVisible(true);
		mGR.o2D_Arcade.setVisible(true);
		
		mGR.o2D_Arcade.setPosition(0, -.5f, 0);
		mGR.o2D_TimeB.setPosition(0,-1.14f, 0);
		mGR.o2D_Arcade.setScale(sel == 1?1:.78f);
		mGR.o2D_TimeB.setScale(sel == 2?1:.78f);
		if (mGR.getCurrentCamera().getRotX() != 0) {
			mGR.getCurrentCamera().setRotX(0);
		}
	}
	boolean Handle_Splash(MotionEvent event) {
		sel = 0;
		if (CircRectsOverlap(0, -.22f, .3f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 1;
		}
		if (CircRectsOverlap(0,-.51f, .3f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 2;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1://Arcade
				M.GameScreen = M.GAMEARCARD;
				mGR.setVisible(false);
				break;
			case 2://TIme
				M.GameScreen = M.GAMETIME;
				mGR.setVisible(false);
				break;
			}
			M.sound11(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	
	void ArcadeMenu(){
		mGR.o2D_BG.setVisible(true);
		mGR.o2D_50Bottle.setVisible(true);
		mGR.o2D_100Bottle.setVisible(true);
		mGR.o2D_20Bottle.setVisible(true);		
		mGR.o2D_Arcade.setVisible(true);
		
		mGR.o2D_Arcade.setPosition(0, 1.24, 0);
		mGR.o2D_20Bottle.setScale(sel == 1?1:.78f);
		mGR.o2D_50Bottle.setScale(sel == 2?1:.78f);
		mGR.o2D_100Bottle.setScale(sel == 3?1:.78f);
		
		if (mGR.getCurrentCamera().getRotX() != 0) {
			mGR.getCurrentCamera().setRotX(0);
		}
	}
	boolean Handle_Arcade(MotionEvent event) {
		sel = 0;
		for (int i = 0; i < 3; i++) {
			if (CircRectsOverlap(0, .14f - i * .28f, .3f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i + 1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1://50 Bottle
				mGR.setVisible(false);
				mGR.gameReset(true,0);
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2://100 Bottle
				mGR.setVisible(false);
				mGR.gameReset(true,1);
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3://250 Bottle
				mGR.setVisible(false);
				mGR.gameReset(true,2);
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			M.sound11(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	void TimeMenu(){
		mGR.o2D_20Sec.setPosition(0, .42f-0*.72f, 0);
		mGR.o2D_40Sec.setPosition(0,.42f-1*.72f, 0);
		mGR.o2D_60Sec.setPosition(0,.42f-2*.72f, 0);		
		mGR.o2D_TimeB.setPosition(0, 1.24, 0);
		
		mGR.o2D_BG.setVisible(true);
		mGR.o2D_20Sec.setVisible(true);
		mGR.o2D_40Sec.setVisible(true);
		mGR.o2D_60Sec.setVisible(true);
		mGR.o2D_TimeB.setVisible(true);
		
		
		mGR.o2D_20Sec.setScale(sel == 1?1:.78f);
		mGR.o2D_40Sec.setScale(sel == 2?1:.78f);
		mGR.o2D_60Sec.setScale(sel == 3?1:.78f);
		if (mGR.getCurrentCamera().getRotX() != 0) {
			mGR.getCurrentCamera().setRotX(0);
		}
	}
	
	boolean Handle_Time(MotionEvent event) {
		sel = 0;
		for (int i = 0; i < 3; i++) {
			if (CircRectsOverlap(0, .14f - i * .28f, .3f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i + 1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel >0) {
			switch (sel) {
			case 1://20 Sec
				mGR.setVisible(false);
				mGR.gameReset(false,0);
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2://40 Sec
				mGR.setVisible(false);
				mGR.gameReset(false,1);
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3://60 Sec
				mGR.setVisible(false);
				mGR.gameReset(false,2);
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			M.sound11(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	
	void Over_Pause() {
		if (M.GameScreen == M.GAMEOVER) {
			mGR.o2D_Gameover.setVisible(true);
		} else {
			mGR.o2D_Gamepaused.setVisible(true);
		}
		mGR.o2D_BG.setVisible(true);
		mGR.o2D_Score.setVisible(true);
		mGR.o2D_BScore.setVisible(true);

		mGR.o2D_home.setVisible(true);
		mGR.o2D_Achiev.setVisible(true);
		mGR.o2D_Leader.setVisible(true);
		mGR.o2D_More.setVisible(true);
		mGR.o2D_Play.setVisible(true);

		
		mGR.o2D_home.setScale(sel == 1?1:.78f);
		mGR.o2D_Achiev.setScale(sel == 2?1:.78f);
		mGR.o2D_Leader.setScale(sel == 3?1:.78f);
		mGR.o2D_More.setScale(sel == 4?1:.78f);
		mGR.o2D_Play.setScale(sel == 5?1:.78f);
		
		
		if (mGR.getCurrentCamera().getRotX() != 0) {
			mGR.getCurrentCamera().setRotX(0);
		}
		
		
		mGR.oScore.setVisible(true);
		mGR.oScore.setRotation(180, 0, 180);
		mGR.oScore.setPosition(0,-.20f, 0.1f);
		mGR.oScore.setScale(1);
		if(counter%10 ==2){
			
		}
		
		
		mGR.oTime.setVisible(true);
		mGR.oTime.setRotation(180, 0, 180);
		mGR.oTime.setPosition(0, -.82f, 0);
		mGR.oTime.setScale(1);
		
		if(counter%10 ==2){
			mGR.updateScoreBitmap();
			mGR.updateTimeBitmap();
		}
		
		
		if (mGR.getCurrentCamera().getRotX() != 0) {
			mGR.getCurrentCamera().setRotX(0);
		}
	}
	
	
	boolean Handle_OverPause(MotionEvent event) {
		if(counter < 30)
			return true;
		System.out.println(screen2worldX(event.getX())+"       "+screen2worldY(event.getY()));
		sel = 0;
		for (int i = 0; i < 4; i++) {
			if (CircRectsOverlap(-.30f + i * .20f,0.27f, .1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i + 1;
			}
		}
		
		if (CircRectsOverlap(0,0.52f, .1f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 5;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1://Home
				mGR.setVisible(false);
				M.GameScreen = M.GAMEMENU;
				break;
			case 2://Achievement
				HTTRenderer.mStart.onShowAchievementsRequested();
				break;
			case 3:////LeaderBoard
				HTTRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 4://more
				MoreGame();
				break;
			case 5://Play
				mGR.setVisible(false);
				if(M.GameScreen == M.GAMEOVER)
					mGR.gameReset(mGR.mIsArcad, mGR.mMode);
				else{
					if(mGR.mIsArcad)
						mGR.mTimeScore = System.currentTimeMillis()-mGR.mTimeScore;
					else
						mGR.mTimeScore+=System.currentTimeMillis();
				}
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			M.sound11(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	
	void Draw_Gameplay() {
		for (int i = 0; i < mGR.m3D_Bottle.length; i++) {
			mGR.m3D_Bottle[i].setVisible(false);
			mGR.m3D_Bottom[i].setVisible(false);
			mGR.m3D_Top[i].setVisible(false);
		}
		
		mGR.o2D_Start.setVisible(!mGR.mIsGameStart);
		
		mGR.m3D_Floor.setPosition(0, 0, 0);
		mGR.m3D_Floor.setScale(.033);
		mGR.m3D_Floor.setVisible(true);
		mGR.m3D_BG.setPosition(0, 0, 0);
		mGR.m3D_BG.setScale(.033);
		mGR.m3D_BG.setVisible(true);
		mGR.m3D_Table.setPosition(0, 0, 0);
		mGR.m3D_Table.setScale(.033);
		mGR.m3D_Table.setVisible(true);
		
		
		for(int i =0;i<mGR.m3D_Chair.length;i++){
			mGR.m3D_Chair[i].setVisible(true);
		}
		
		if(mGR.crack == 0)
			mGR.crack = 1;
		for (int i = 0; i < mGR.mBottle.length; i++) {
			if (mGR.mBottle[i].ison) {
				mGR.m3D_Bottle[mGR.mBottle[i].img].setPosition(((i * .014) - (.035)) * 33, 0, .83f);
				mGR.m3D_Bottle[mGR.mBottle[i].img].setVisible(true);
				mGR.m3D_Bottle[mGR.mBottle[i].img].setRotZ(counter);
				mGR.crack = 0;
			} else {
				mGR.m3D_Bottom[mGR.mBottle[i].img].setPosition(((i * .014) - (.035)) * 33, 0, .83f);
				mGR.m3D_Bottom[mGR.mBottle[i].img].setVisible(true);
				if (mGR.m3D_Top[mGR.mBottle[i].img].getZ() > 0.3) {
					mGR.m3D_Top[mGR.mBottle[i].img].setPosition(((i * .014) - (.035)) * 33, 0,mGR.m3D_Top[mGR.mBottle[i].img].getZ()+ mGR.mBottle[i].vz);
					mGR.m3D_Top[mGR.mBottle[i].img].setRotation(0, 0, counter);
					mGR.mBottle[i].vz -= .01f;
				}

				for (int j = 0; j < mGR.mParticle[mGR.mBottle[i].img].length; j++) {
					mGR.mParticle[mGR.mBottle[i].img][j].update();
				}
				mGR.m3D_Top[mGR.mBottle[i].img].setVisible(true);

			}
			for (int j = 0; j < mGR.mBall.length; j++) {
				if (!mGR.mBall[j].isCracked && mGR.mBottle[i].ison
						&& CirCir(mGR.m3D_Bottle[mGR.mBottle[i].img].getX(),mGR.m3D_Bottle[mGR.mBottle[i].img].getY(), .1f,
								mGR.mBall[j].obj3d.getX(),mGR.mBall[j].obj3d.getY(), .1)) {
					mGR.mBottle[i].ison = false;
					mGR.mBall[j].isCracked = true;
					M.sound1(R.drawable.g1);
					if(mGR.mIsArcad){
						mGR.NoBottle --;
					}else{
						mGR.NoBottle ++;
					}
					for (int k = 0; k < mGR.mParticle[mGR.mBottle[i].img].length; k++) {
						mGR.mParticle[mGR.mBottle[i].img][k].set((float) mGR.m3D_Bottle[mGR.mBottle[i].img].getX());
					}

				}
			}
		}
		if (mGR.crack > 0) {
			mGR.crack++;
			if(mGR.crack > 20){
				mGR.bottleset();
				if(!mGR.mIsArcad){
					mGR.mTimeScore+=2000;
				}
			}
		}
		for (int i = 0; i < mGR.mBall.length; i++) {
			mGR.mBall[i].update();
		}
		mGR.oScore.setVisible(true);
		mGR.oScore.setRotation(90, 0, 180);
		mGR.oScore.setPosition(0.33,  -4.13,  0.12);
		mGR.oScore.setScale(.41f);
		if(counter%10 ==2){
			mGR.updateScoreBitmap();
		}
		
		
		if(mGR.mIsArcad){
			if(mGR.NoBottle <= 0){
				counter = 6;
				M.GameScreen = M.GAMEOVER;
				mGR.setVisible(false);
				mGR.mTimeScore = System.currentTimeMillis() - mGR.mTimeScore;
				if(mGR.mTimeScore < mGR.mTScore[mGR.mMode] || mGR.mTScore[mGR.mMode] == 0){
					mGR.mTScore[mGR.mMode] = mGR.mTimeScore; 
				}
				HTTRenderer.mStart.Achivment();
				HTTRenderer.mStart.ShowInterstitial();
			}
		}else{
			if(mGR.mTimeScore < System.currentTimeMillis()){
				counter = 6;
				mGR.setVisible(false);
				M.GameScreen = M.GAMEOVER;
				if(mGR.NoBottle > mGR.mBScore[mGR.mMode]){
					mGR.mBScore[mGR.mMode] = mGR.NoBottle; 
				}
				HTTRenderer.mStart.Achivment();
				HTTRenderer.mStart.ShowInterstitial();
			}
		}
		mGR.m3D_Ball.setVisible(true);
		mGR.m3D_Ball.setPosition(0, -4f, .05f);
		mGR.m3D_Ball.setRotation(counter, counter, counter);
		
		if (mGR.getCurrentCamera().getRotX() != -78) {
			mGR.getCurrentCamera().setRotX(-78);
		}
	}
	float go =0;
	public boolean Handle_Gameplay(MotionEvent event) {
		
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			go = screen2worldY(event.getY());
		}
		if(MotionEvent.ACTION_UP == event.getAction() && go < -.1f)
		{
			go = (screen2worldY(event.getY()) - go);
			if (go > .1) {
				float dx = M.ScreenWidth / 2;
				float dy = M.ScreenHieght * .9f;
				double ang = (GetAngle(-(dy - event.getY()),-(dx - event.getX())));
				if (ang > 2.6f && ang < 3.75f) {
					for (int i = 0; i < mGR.mBall.length; i++) {
						if (mGR.mBall[i].obj3d.getZ() < 0 || mGR.mBall[i].obj3d.getY() > .1f) {
							mGR.mBall[i].set(ang);
							M.sound6(R.drawable.shoot);
							break;
						}
					}

				}
				if(!mGR.mIsGameStart){
					mGR.mIsGameStart = true;
					if(mGR.mIsArcad){
						mGR.mTimeScore = System.currentTimeMillis();
					}else{
						mGR.mTimeScore = System.currentTimeMillis() +(20000*(mGR.mMode+1));
					}
				}
			}
		}
		return true;
	}
	
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }

	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}
	boolean Rect2RectIntersection(double ax,double ay,double adx,double ady,double bx,double by,double bdx,double bdy){
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
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		Start.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		Start.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		Start.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		Start.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		Start.mContext.startActivity(mIntent);
	}
}
