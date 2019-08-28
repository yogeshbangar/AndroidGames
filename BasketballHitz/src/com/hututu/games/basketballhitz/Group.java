package com.hututu.games.basketballhitz;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen=M.GAMEPAUSE;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>60){
				Counter =0;
				M.GameScreen = M.GAMEADD;
				if(mGR.addFree){
					M.GameScreen = M.GAMESPLSH;
				}
			}
			break;
		case M.GAMEADD:
			if (Counter > 100)
				DrawTransScal(gl, mGR.mTex_Cross, .80f, 0.85f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			else {
				mGR.mTex_Ling.drawSS(gl, -.24f, .87f, .7f, 1.0f);// 10
				mGR.mTex_LingBar.drawSS(gl, -.20f, .835f, Counter * .198f, 1.3f);// 10
			}
			break;
		case M.GAMESPLSH:
			Draw_Splash(gl);
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Draw_OverPause(gl);
			break;
		case M.GAMEJOIN:
			Draw_Join(gl);
			break;
		}
		
//		setting();
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEADD:
			mGR.mSel = 0;
			if(CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)) {
				mGR.mSel = 1;
			}
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)) {
				M.GameScreen = M.GAMESPLSH;
				if(mGR.mSel !=0)
					M.sound1(GameRenderer.mContext, R.drawable.click);
				mGR.mSel = 0;
				
			}
			break;
		case M.GAMESPLSH:
			Handle_Splash(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Handle_OverPause(event);
			break;
		case M.GAMEJOIN:
			Handle_Join(event);
			break;
		}
		
		Handle(event);
		return true;
	}
	
	
	/***************************Join Start**********************************/
	
	void Draw_Join(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTexture(gl, mGR.mTex_Join, 0, .30f);
		DrawTexture(gl, mGR.mTex_joinboard, 0 , 0.05f);
		DrawTransScal(gl,mGR.mTex_Now	, -.4f, -.23f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl,mGR.mTex_Later	, 0.4f, -.23f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
	}
	boolean Handle_Join(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.4f, -.23f, mGR.mTex_Now.width()*.4f, mGR.mTex_Now.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.4f, -.23f, mGR.mTex_Later.width()*.4f, mGR.mTex_Later.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel != 0)	{
			if(mGR.mSel == 1)
				GameRenderer.mStart.beginUserInitiatedSignIn();
			M.GameScreen = M.GAMEPLAY;
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/***************************Join End************************************/
	
	
	/***************************OverPause Start**********************************/
	
	void Draw_OverPause(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);

		DrawTextureS(gl, mGR.mTex_ITxt[0], -.52f,0.10f,1.0f);
		DrawTextureS(gl, mGR.mTex_BestScore	,  -.40f,-.04f,1.0f);
		DrawTextureS(gl, mGR.mTex_SBack	,  0.40f, .10f,1.8f);
		drawNumberScal(gl, mGR.mScore+""	,  0.25f, .10f, 0, 1, 1);
		DrawTextureS(gl, mGR.mTex_SBack	,  0.40f,-.04f,1.8f);
		drawNumberScal(gl, mGR.mBest[mGR.mBall.type]+"", 0.25f, -.04f, 0, 1, 1);
		
		if(M.GameScreen == M.GAMEOVER){
			DrawTexture(gl, mGR.mTEx_GameOver, 0, .30f);
			if(!mGR.addFree)
				DrawTransScal(gl, mGR.mTex_Ads	, -.61f+.41f*0,-.26f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			else
				DrawTransScal(gl,mGR.mTex_Icon[0], -.61f+.41f*0,-.26f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Icon[1]	, -.61f+.41f*1,-.26f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Menu		, -.61f+.41f*2,-.26f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Retry	, -.61f+.41f*3,-.26f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		}else{
			DrawTexture(gl, mGR.mTex_Pause, 0, .30f);
			DrawTransScal(gl, mGR.mTex_Menu	, -.61f+.41f*0,-.26f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Icon[M.setValue ? 5 : 6], -.61f+.41f*1,-.26f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_PlayIcn	, -.61f+.41f*2,-.26f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Retry	, -.61f+.41f*3,-.26f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		}
		
	}
	boolean Handle_OverPause(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<4;i++)
		if(CircRectsOverlap(-.61f+.41f*i,-.26f, mGR.mTex_PlayIcn.width()*.4f, mGR.mTex_PlayIcn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = i+1;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel != 0)	{
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMEOVER ){
					if(!mGR.addFree)
						mGR.mMainActivity.onBuyGold50000(null);
					else
						GameRenderer.mStart.onShowLeaderboardsRequested();
				}else{
					M.GameScreen = M.GAMEMENU;
				}
				break;
			case 2:
				if(M.GameScreen == M.GAMEOVER ){
					GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				}else{
					M.setValue = !M.setValue;
				}
				break;
			case 3:
				if(M.GameScreen == M.GAMEOVER ){
					M.GameScreen = M.GAMEMENU;
				}else{
					M.GameScreen = M.GAMEPLAY;
				}
				break;
			case 4:
				mGR.gameReset(mGR.mBall.type);
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/***************************OverPause End************************************/
	
	
	/***************************About Start**********************************/
	
	void Draw_About(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTexture(gl, mGR.mTex_Abuot, 0, 0);
		DrawTransScal(gl, mGR.mTex_Back,0.8f,-.33f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
	}
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.8f,-.33f, mGR.mTex_PlayIcn.width()*.5f, mGR.mTex_PlayIcn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel != 0)	{
			M.GameScreen = M.GAMEMENU;
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/***************************About End************************************/
	
	/***************************Menu Start**********************************/
	float menux = -.48f;
	boolean move = true;

	void Draw_Menu(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_menubox, menux + .00f, 0.10f);
		DrawTexture(gl, mGR.mTex_Menuhoop[0], menux + .67f, 0.10f);
		DrawTexture(gl, mGR.mTex_Circle[mGR.mSel == 2 ? 0 : 1], menux + .67f, 0.07f);
		DrawTextureR(gl, mGR.mTex_Arow, menux + .67f, 0.07f, move ? 0 : 180);

		for (int i = 0; i < 6; i++) {
			DrawTextureS(gl, mGR.mTex_SmallBox, menux - .17f, .555f - .18f * i,mGR.mSel==i+20?1.2f:1);
			DrawTexture(gl, mGR.mTex_ITxt[i], menux - .17f, .555f - .18f * i);
			if (i == 5)
				DrawTextureS(gl, mGR.mTex_Icon[M.setValue ? 5 : 6], menux + .32f, .555f - .18f * i,mGR.mSel==i+20?1.2f:1);
			else
				DrawTextureS(gl, mGR.mTex_Icon[i], menux + .32f, .555f - .18f * i,mGR.mSel==i+20?1.2f:1);
		}

		DrawTexture(gl, mGR.mTex_menubox, 1.9f + menux + .00f, -.20f);
		DrawTexture(gl, mGR.mTex_Menuhoop[1], 1.9f + menux - .67f, -.20f);
		DrawTexture(gl, mGR.mTex_Circle[mGR.mSel == 1 ? 0 : 1], 1.9f + menux - .67f, -.23f);
		DrawTextureR(gl, mGR.mTex_Arow, 1.9f + menux - .67f, -.23f, move ? 0 : 180);

		for (int i = 0; i < 5; i++) {
			DrawTextureS(gl, mGR.mTex_SmallBox, 1.9f + menux, 0.19f - 0.19f * i,  mGR.mSel==i+10?1.8f:1.7f, mGR.mSel==i+10?1.1f:1);
			DrawTexture(gl, mGR.mTex_MPlay[i], 1.9f + menux, 0.19f - 0.19f * i);

		}

		if (move && menux > -1.35) {
			menux -= .09f;
			if (menux < -1.35)
				menux = -1.42f;
		}
		if (!move && menux < -0.48) {
			menux += .09f;
			if (menux > -0.48)
				menux = -0.48f;
		}
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(1.9f+menux-.67f,-.23f, mGR.mTex_PlayIcn.width()*.5f, mGR.mTex_PlayIcn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(menux+.67f,0.07f, mGR.mTex_PlayIcn.width()*.5f, mGR.mTex_PlayIcn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		for(int i=0;i<5;i++)
		{
			if(CircRectsOverlap(1.9f + menux, 0.19f - 0.19f * i, mGR.mTex_SmallBox.width()*.8f, mGR.mTex_SmallBox.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+10;
			}
		}
		for (int i = 0; i < 6; i++)
		{
			if(CircRectsOverlap(menux - .17f, .555f - .18f * i, mGR.mTex_SmallBox.width()*1.1f, mGR.mTex_SmallBox.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+20;
			}
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
			case 2:
				move = !move;
				M.sound10(GameRenderer.mContext, R.drawable.menu_swing);
				mGR.mSel = 0;
				break;
			case 10://Time 60 sec
				mGR.gameReset(0);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else{
				}
				break;
			case 11 ://arcade 12 Ball
				mGR.gameReset(1);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else{
				}
				break;
			case 12://Tournament 30 Sec
				mGR.gameReset(2);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else{
				}
				break;
			case 13://1 Ball
				mGR.gameReset(3);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else{
				}
				break;
			case 14://Target Shoot
				mGR.gameReset(4);
				M.GameScreen = M.GAMEPLAY;
				break;
			case 20:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//Leaderboard
				break;
			case 21:
				GameRenderer.mStart.onShowAchievementsRequested();
				//Achievement
				break;
			case 22:
				facebook();
				break;
			case 23:
				MoreGame();
				break;
			case 24:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 25:
				M.setValue = !M.setValue;
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/***************************Menu End************************************/
	
	
	
	/***************************Splash Start**********************************/
	float anis=1,anivs=.001f;
	void Draw_Splash(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Playtxt, -.38f, .47f);
		DrawTextureS(gl, mGR.mTex_Title, 0, -.33f,anis);
		if(anis>1.03f)
			anivs=-.0005f;
		if(anis<0.97f)
			anivs=.0005f;
		anis+=anivs;
//		DrawTransScal(gl, mGR.mTex_PlayIcn, 0,-.77f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
	}
	boolean Handle_Splash(MotionEvent event)
	{
		mGR.mSel = 0;
//		if(CircRectsOverlap(0,0, mGR.mTex_PlayIcn.width()*.5f, mGR.mTex_PlayIcn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			mGR.mSel = 1;
		
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel != 0)
		{
			M.GameScreen = M.GAMEMENU;
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/***************************Splash End************************************/
	
	/***************************GamePlay Start**********************************/
	void setAnimation(float x, float y) {
		for (int i = 0; i < mGR.mAni.length; i++) {
			if (mGR.mAni[i].no <= 0) {
				mGR.mAni[i].set(x, y);
				break;
			}
		}

	}
	void score()
	{
		mGR.mScore++;
		mGR.mTScore++;
		if(mGR.mBest[mGR.mBall.type]<mGR.mScore)
		{
			mGR.mBest[mGR.mBall.type]=mGR.mScore;
		}
	}
	void gamelogic() {
		mGR.mBall.update();
		setAnimation(mGR.mBall.x, mGR.mBall.y);
		ChackAchiv();
	}
	
	void Draw_GamePlay(GL10 gl) {
		DrawTexture(gl,mGR.mTex_Ground	, 0, -.28f);
		DrawTexture(gl,mGR.mTex_Building, 0, 0.85f);
		DrawTexture(gl,mGR.mTex_Wall	, 0, 0.56f);
		DrawTexture(gl,mGR.mTex_Bboard	, 0, 0.58f);
		if(mGR.mBall.type != 4)
			DrawTexture(gl,mGR.mTex_Hoop[0][mGR.mBall.anim%mGR.mTex_Hoop[0].length], 0, .63f);
		
		if(mGR.mBall.type != 4)
			DrawTexture(gl,mGR.mTex_Hoop[1][0], 0, .56f);
		else
			DrawTexture(gl, mGR.mTex_Target[mGR.mBall.tno], mGR.mBall.tx,mGR.mBall.ty);
		
		
		if(mGR.mBall.type == 0 && mGR.mBall.xtime<3){
			DrawTexture(gl, mGR.mTex_Tup[mGR.mBall.xtime], 0,.45f);
		}
		
		if (mGR.mBall.collide < 1) 
		{
			if (mGR.mBall.y < .8f && mGR.mBall.vy == 0){
				DrawTransScal(gl, mGR.mTex_Shadow[0], mGR.mBall.sx,mGR.mBall.sy-.08f, mGR.mBall.z,.5f);
				DrawTransScal(gl, mGR.mTex_Shadow[1], mGR.mBall.sx,mGR.mBall.sy-.08f, mGR.mBall.z,.5f);
			}else
				DrawTransScal(gl, mGR.mTex_Shadow[0], mGR.mBall.sx, mGR.mBall.sy,mGR.mBall.z*1.5f-Math.abs(mGR.mBall.y-mGR.mBall.sy)*.5f,.5f);
		} else {
			mGR.mTex_Ball[0].drawTransDark(gl,  mGR.mBall.x, mGR.mBall.y-.02f,mGR.mBall.z,.3f);
			mGR.mBall.collide--;
		}
		for (int i = 0; i < mGR.mAni.length; i++) {
			if (mGR.mAni[i].no > 0) {
				DrawTransScal(gl,mGR.mTex_Tel	, mGR.mAni[i].x, mGR.mAni[i].y,mGR.mAni[i].no*.1f,mGR.mAni[i].no*.1f);
				mGR.mAni[i].no--;
			}
		}
		
		DrawTextureS(gl,mGR.mTex_Ball[(mGR.mBall.vy==0)?0:Counter%12], mGR.mBall.x, mGR.mBall.y, mGR.mBall.z-.01f);
		
		if(mGR.mBall.getRev()==2)
		{
			DrawTexture(gl,mGR.mTex_Ground	, 0, -.28f);
			DrawTexture(gl,mGR.mTex_Wall	, 0, 0.56f);
			DrawTexture(gl,mGR.mTex_Bboard	, 0, 0.58f);
			if(mGR.mBall.type != 4){
				DrawTexture(gl,mGR.mTex_Hoop[0][mGR.mBall.anim%mGR.mTex_Hoop[0].length], 0, .63f);
				DrawTexture(gl,mGR.mTex_Hoop[1][mGR.mBall.anim%mGR.mTex_Hoop[0].length], 0, .56f);
			}else{
				DrawTexture(gl, mGR.mTex_Target[mGR.mBall.tno], mGR.mBall.tx,mGR.mBall.ty);
			}
			for (int i = 0; i < mGR.mAni.length; i++) {
				if (mGR.mAni[i].no > 0) {
					mGR.mAni[i].no=-1;
				}
			}
			if(mGR.mBall.type == 0 && mGR.mBall.xtime<3){
				DrawTexture(gl, mGR.mTex_Tup[mGR.mBall.xtime], 0,.45f);
			}
		}
		
		if(mGR.mBall.vy<0  && mGR.mBall.getRev()<3){
			if(mGR.mBall.type != 4)
				DrawTexture(gl,mGR.mTex_Hoop[1][mGR.mBall.anim%mGR.mTex_Hoop[0].length], 0, .56f);
			if(mGR.mBall.type == 0 && mGR.mBall.xtime<3){
				DrawTexture(gl, mGR.mTex_Tup[mGR.mBall.xtime], 0,.45f);
			}
		}
		
		
		for(int i=0;i<4;i++){
			DrawTexture(gl, mGR.mTex_SBack	, -.77f, .96f-.08f*i);
			if(i<2)
				DrawTexture(gl, mGR.mTex_SBack	, 0.77f, .96f-.08f*i);
		}
		
		DrawTexture(gl, mGR.mTex_GScore, -.77f, .96f);
		drawNumberScal(gl, mGR.mScore+"", -.78f, .88f, 1, .9f, 1);
		DrawTexture(gl, mGR.mTex_GBest, -.77f, .80f);
		drawNumberScal(gl, mGR.mBest[mGR.mBall.type]+"", -.78f, .72f, 1, .9f, 1);
		switch(mGR.mBall.type){
		case 0://TIme
		case 2://Turnament
			DrawTexture(gl, mGR.mTex_Time, 0.77f, .96f);
			//mGR.gametime = System.currentTimeMillis() - mGR.gametime;
			int time = 31000;
			if(mGR.mBall.type == 0)
				time = 61000;
			int sec = (int)(mGR.gametime+time-System.currentTimeMillis())/1000;
			if(M.GameScreen == M.GAMEOVER)
				sec = 0;
			if(M.GameScreen == M.GAMEPAUSE)
				sec = (int)(time-mGR.gametime)/1000;
			drawNumberScal(gl, sec+"", 0.74f, .88f, 1, .9f, 1);
			if (sec < 10 && Counter % 30 == 0)
			{
				M.sound13(GameRenderer.mContext, R.drawable.time_count);
			}
			if(sec<=0)
				gameOver();
			break;
		case 1:case 3:case 4:
			DrawTexture(gl, mGR.mTex_Balls, 0.77f, .96f);
			drawNumberScal(gl, mGR.mBall.mBalls+"", 0.74f, .88f, 1, .9f, 1);
			if (mGR.mBall.bani > 0) {
				DrawTextureS(gl, mGR.mTex_Blast[0], 0.74f, .88f, mGR.mBall.bani * .1f);
				DrawTextureS(gl, mGR.mTex_Blast[1], 0.74f, .88f, .4f + (10 - mGR.mBall.bani) * .1f);
				DrawTextureS(gl, mGR.mTex_Blast[2], 0.74f, .88f, .4f + (10 - mGR.mBall.bani) * .1f);
				mGR.mBall.bani--;
			}
			break;
		}
		if (mGR.mBall.type == 0 && mGR.mTimeAni.y < 1)
		{
			drawNumberScal(gl, mGR.mTimeAni.no+";", 0,mGR.mTimeAni.y, 0, 1.5f, 1);
			mGR.mTimeAni.y+=mGR.mTimeAni.x;
			mGR.mTimeAni.x+=.001f;
		}
		if (mGR.mComboAni.y < 1 && mGR.mComboAni.x >.1f)
		{
			DrawTransScal(gl, mGR.mTex_blast		,0.0f, mGR.mComboAni.y	,1.1f+mGR.mComboAni.x*.1f,mGR.mComboAni.x);
			DrawTransScal(gl, mGR.mTex_combo		,.08f, mGR.mComboAni.y	,1.0f+mGR.mComboAni.x*.1f,mGR.mComboAni.x);
			drawNumberScal(gl, mGR.mComboAni.no+"" ,-.26f, mGR.mComboAni.y,0,1.0f+mGR.mComboAni.x*.1f,mGR.mComboAni.x);
			mGR.mComboAni.y+=.001f;
			mGR.mComboAni.x-=.02f;
		}
//		DrawTexture(gl, mGR.mTex_Target[0],0,sy);
		gamelogic();
	}
	void ChackAchiv()
	{
		
		int time = (int)(System.currentTimeMillis()-mGR.gametime)/1000;
		
		if(time<60 && mGR.mScore >9 && !mGR.Achi[0])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[0]);
			mGR.Achi[0] = true;
		}
		if(mGR.mBall.Drap==0 && mGR.mScore >4 && !mGR.Achi[1])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[1]);
			mGR.Achi[1] = true;
		}
		if(time<30 && mGR.mScore >4 && !mGR.Achi[2])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[2]);
			mGR.Achi[2] = true;
		}
		if(mGR.mBall.Drap==0 && mGR.mScore >14 && !mGR.Achi[3])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[3]);
			mGR.Achi[3] = true;
		}
		if(mGR.mTScore >999 && !mGR.Achi[4])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[4]);
			mGR.Achi[4] = true;
		}
	}
	void gameOver()
	{
		GameRenderer.mStart.Submitscore(M.scoreID[mGR.mBall.type], mGR.mBest[mGR.mBall.type]);
		M.sound6(GameRenderer.mContext, R.drawable.game_over);
		M.GameScreen = M.GAMEOVER;
		if (mGR.ads % 1 == 0 ) {
			try {
				GameRenderer.mStart.show();
			} catch (Exception e) {
			}
		}
		mGR.ads++;
	}
	
	float dx,dy;
	boolean Handle_GamePlay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			dx	= screen2worldX(event.getX());
			dy	= screen2worldY(event.getY());
		}
		if (MotionEvent.ACTION_UP == event.getAction() && !mGR.mBall.move) {
			dx	= (dx - screen2worldX(event.getX()));
			dy	= (dy - screen2worldY(event.getY()));
			
			double dis = Math.sqrt((dx * dx) + (dy * dy))-.1;
			
			float dx = M.ScreenWidth * .5f;
			float dy = M.ScreenHieght * .9f;
			double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
			if (ang > 2.5 && ang < 3.6 && dis >0.05){
				mGR.mBall.set(ang, dis);
				M.sound4(GameRenderer.mContext, R.drawable.ball_shoot);
			}
			
			
		}
		return true;
	}

	/***************************GamePlay End**********************************/
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, x, y,angle);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy)
	{
		Tex.drawScal(gl, x, y,sx,sy);
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
	void drawNumberScal(GL10 gl,String strs,float x, float y,int align,float scal,float tranc)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
//		 String strs = ""+no;
		 if(align ==1)
				x-=strs.length()*dx*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawTransprentScal(gl,x+i*dx,y,scal,tranc);
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
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
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
