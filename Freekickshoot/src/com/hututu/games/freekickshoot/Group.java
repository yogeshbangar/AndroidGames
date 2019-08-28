


package com.hututu.games.freekickshoot;

import javax.microedition.khronos.opengles.GL10;



import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {
	GameRenderer mGR = null;
	int Counter = 0;
	float sx, sy;

	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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

	@Override
	public void draw(GL10 gl) {
		Counter++;
//		M.GameScreen = M.GAMELEVEL;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_logo, 0, 0);
			if(Counter> 60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMECONT:
			Draw_Control(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;	
		case M.GAMEJOIN:
			Draw_Join(gl);
			break;
		case M.GAMEOVER:
			Draw_Gameover(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		}
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMECONT:
			Handle_Control(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEJOIN:
			Handle_Join(event);
			break;
		case M.GAMEOVER:
			Handle_Gameover(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		}
		
//		Handle(event);
		return true;
	}
	
	
	/*************************Level Start**********************************************/
	int page = 0;
	float sd,sv,svx;
	boolean isDown = false;
	float lAnim = .1f;
	void Draw_Level(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBG, 0, 0);
		
	/*	for (int i = 0; i < 15; i++) {
				DrawTransScal(gl, mGR.mTex_Icon, -.45f + .25f * (i % 5) , .36f - .4f * (i / 5),1, 1);
			
		}
		*/
		
		if (lAnim < 1) {
			for (int i = 0; i < 15; i++) {
				DrawTransScal(gl, mGR.mTex_Icon  , -.45f + .25f * (i % 5) + sv, .36f - .4f * (i / 5),lAnim, 1);
				if((page * 15 + i)<mGR.mULvl){
					drawNumberScal(gl,""+ (page*15+i+1), -.44f + .25f * (i % 5) + sv, .38f - .4f * (i / 5), 1,lAnim, 1);
				} else {
					DrawTransScal(gl, mGR.mTex_levelOff, -.45f + .25f * (i % 5) + sv, .36f - .4f * (i / 5),lAnim, 1);
				}
			}lAnim*=1.3f;
		}else{
			for (int i = 0; i < 15; i++) {
				DrawTransScal(gl, mGR.mTex_Icon, -.45f + .25f * (i % 5) + sv, .36f - .4f * (i / 5),mGR.mSel==i+1?1.1f:1, mGR.mSel==i+1?0.5f:1);
				if((page * 15 + i)<mGR.mULvl){
					if((page * 15 + i)==mGR.Level){
//						mGR.mTex_LAnim[1].drawRotet2(gl,(Counter+20)%360, -.45f + .25f * (i % 5) + sv, .79f - .28f * (i / 3),1,0);
//						DrawTransScal(gl, mGR.mTex_LAnim[0], -.45f + .25f * (i % 5)+ sv, .79f - .28f * (i / 3),.7f+(Counter%20)*.01f, (20-(Counter%20))*.05f);
					}
					drawNumberScal(gl,""+(page * 15 + i + 1), -.44f + .25f * (i % 5) + sv, .38f - .4f * (i / 5), 1,1,1);
				}
				else
				{
					DrawTransScal(gl, mGR.mTex_levelOff, -.45f + .25f * (i % 5) + sv, .36f - .4f * (i / 5),mGR.mSel==i+1?1.1f:1, mGR.mSel==i+1?0.5f:1);
				}
			}
		}
	for (int i = 0; i < 7; i++) {
			if (page == i)
				DrawTexture(gl, mGR.mTex_Dot[1], -.35f + .13f * i, -.75f);
			else
				DrawTexture(gl, mGR.mTex_Dot[0], -.35f + .13f * i, -.75f);
		}
		
		
		if (sv != 0 && !isDown) {
			sv += svx;
			if (Math.abs(sv) > 1.8f) {
				if (sv > 0) {
					if (page > 0)
						page--;
				} else {
					if (page < 6)
						page++;
				}
				svx = sv = 0;lAnim =.2f;
			}
			if ((sv > 0 && page == 0) || (sv < 0 && page == 6)) {
				svx = sv = 0;
			}
		}
//		DrawTransScal(gl, mGR.mTex_Back	,.84f,-.60f,mGR.mSel==50?1.1f:1, mGR.mSel==50?0.5f:1);
		
	}
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel = 0;
		for (int i = 0; i < 15; i++){
			if(CircRectsOverlap(-.45f + .25f * (i % 5) + sv, .36f - .4f * (i / 5), mGR.mTex_Icon.width()*.5f, mGR.mTex_Icon.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.84f,-.60f, mGR.mTex_Icon.width()*.5f, mGR.mTex_Icon.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 50;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			sd = screen2worldX(event.getX());
			isDown = true;
		}
		sv = screen2worldX(event.getX())-sd;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			isDown = false;
			if(Math.abs(sv)<.1){
				sv = 0;
				switch (mGR.mSel) {
				
				default:
					if ((page * 15 + (mGR.mSel-1)) < mGR.mULvl) {
						System.out.println((page * 15 + (mGR.mSel-1)));
						mGR.gameReset(1,(page * 15 + (mGR.mSel-1)));
						M.GameScreen = M.GAMEPLAY;
						if(!GameRenderer.mStart.isSignedIn())
							M.GameScreen = M.GAMEJOIN;
						else
							M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
					}
					break;
				}
				if(mGR.mSel != 0)
					M.sound10(GameRenderer.mContext, R.drawable.click);
			}
			else
				svx = (sv > 0) ? 0.2f : -0.2f;
				
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Level End************************************************/
	
	
	/*********************Gameover Start*************************************/
	void Draw_Gameover(GL10 gl){
	
		DrawTexture(gl, mGR.mTex_AllBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bord, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Button	,-.65f, .88f);
		DrawTexture(gl, mGR.mTex_Icon	,-.87f, .88f);
		DrawTexture(gl, mGR.mTex_Over	,-.65f, .88f);

		DrawTexture(gl, mGR.mTex_ScorBox	,-0, .44f);
		DrawTexture(gl, mGR.mTex_Level	,	-0, .51f);
		drawNumberScal(gl, ""+mGR.Level, 0.01f, .37f, 1, 1, 1);

		
		DrawTransScal(gl, mGR.mTex_Button	,-.20f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_ReIcn	,-.38f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Rtry		,-.20f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		
		DrawTransScal(gl, mGR.mTex_Button	,0.33f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_MIcn		,0.12f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Menu		,0.33f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		
		
		DrawTransScal(gl, mGR.mTex_Reward	,-.10f,-.46f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Score	,0.25f,-.46f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		

	
	}
	boolean Handle_Gameover(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.20f,0.04f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.33f,0.04f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.10f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(0.25f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen =M.GAMEPLAY;
				mGR.gameReset(mGR.mType,0);
				//Sing-in
				break;
			case 2:
				M.GameScreen =M.GAMEMENU;
				break;
			case 3:
				M.setValue = !M.setValue;
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();
				//Achievement
				//Sing-in
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//Leader-Board
				//Sing-in
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true; 
	}
	/*********************Gameover End*************************************/
	
	/*********************Join Start*************************************/
	void Draw_Join(GL10 gl){
	
		DrawTexture(gl, mGR.mTex_AllBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bord, 0, 0);

		DrawTexture(gl, mGR.mTex_JTex	,-0, .38f);
		
		DrawTransScal(gl, mGR.mTex_Button	,-.20f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Icon		,-.38f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Join		,-.20f,0.04f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		
		DrawTransScal(gl, mGR.mTex_Button	,0.33f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Icon		,0.12f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Later	,0.28f,0.04f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		
		
		DrawTransScal(gl, mGR.mTex_Reward	,-.10f,-.46f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Score	,0.25f,-.46f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		

	
	}
	boolean Handle_Join(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.20f,0.04f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.33f,0.04f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.10f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(0.25f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen =M.GAMEPLAY;
				GameRenderer.mStart.beginUserInitiatedSignIn();//Sing-in
				break;
			case 2:
				M.GameScreen =M.GAMEPLAY;
				M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				M.GameScreen =M.GAMEPLAY;//Sing-in
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				M.GameScreen =M.GAMEPLAY;//Sing-in
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true; 
	}
	/*********************Join End*************************************/
	
	
	/*********************Pause Start*************************************/
	void Draw_Pause(GL10 gl){
	
		DrawTexture(gl, mGR.mTex_AllBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bord, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Button	,-.65f, .88f);
		DrawTexture(gl, mGR.mTex_PIcn	,-.85f, .88f);
		DrawTexture(gl, mGR.mTex_PText	,-.65f, .88f);
		
		DrawTransScal(gl, mGR.mTex_Button	,-.20f,0.44f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_RIcn		,-.38f,0.44f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_RTex		,-.20f,0.44f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		
		DrawTransScal(gl, mGR.mTex_Button	,0.33f,0.44f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_MIcn		,0.12f,0.44f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Menu		,0.28f,0.44f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		
		
		DrawTransScal(gl, mGR.mTex_Button	,-.26f +.33f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_SIcon	,-.26f +.12f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_SText	,-.26f +.28f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		if(M.setValue)
			DrawTransScal(gl, mGR.mTex_On	,-.26f +.41f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		else{
			DrawTransScal(gl, mGR.mTex_Off	,-.26f +.41f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Slash,-.26f +.12f,.08f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		}
		
		DrawTransScal(gl, mGR.mTex_Reward	,-.10f,-.46f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Score	,0.25f,-.46f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		

	
	}
	boolean Handle_Pause(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.20f,0.44f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.33f,0.44f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.26f +.33f,.08f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.10f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(0.25f,-.44f, mGR.mTex_Reward.width()*.4f, mGR.mTex_Reward.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen =M.GAMEPLAY;
				if (mGR.ads % 1 == 0) {
					try {
						mGR.adsHandler.sendEmptyMessage(0);
					} catch (Exception e) {
					}
				}
				break;
			case 2:
				M.GameScreen =M.GAMEMENU;
				break;
			case 3:
				M.setValue = !M.setValue;
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true; 
	}
	/*********************Pause End*************************************/
	
	/*********************Control Start***********************************/
	void Draw_Control(GL10 gl)	{
		DrawTexture(gl, mGR.mTex_AllBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bord, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Button	,-.65f, .88f);
		DrawTexture(gl, mGR.mTex_CIcn	,-.85f, .88f);
		DrawTexture(gl, mGR.mTex_CText	,-.65f, .88f);

		DrawTexture(gl, mGR.mTex_AText	,0,.26f);
		
		DrawTransScal(gl, mGR.mTex_Button	,-.26f +.33f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_SIcon	,-.26f +.12f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_SText	,-.26f +.28f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		if(M.setValue)
			DrawTransScal(gl, mGR.mTex_On	,-.26f +.41f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		else{
			DrawTransScal(gl, mGR.mTex_Off	,-.26f +.41f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Slash,-.26f +.12f,-.46f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		}
		
		DrawTransScal(gl, mGR.mTex_Back	,-.29f,-.74f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		

	}
	boolean Handle_Control(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.26f +.33f,-.46f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.29f,-.74f, mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 3;
		}
		if(MotionEvent.ACTION_UP == event.getAction())		{
			switch (mGR.mSel) {
			case 2:
				M.setValue = !M.setValue;
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true; 
	}
	/*********************Control End*************************************/
	
	/*********************Menu Start***********************************/
	void Draw_Menu(GL10 gl)	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_Control	, -.875f,.33f- 0*.37f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Score	, -.875f,.33f- 1*.37f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Reward	, -.875f,.33f- 2*.37f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Quick	, 0.650f,.13f- 0*.37f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Arcade	, 0.550f,.13f- 1*.37f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Turnament, 0.450f,.13f- 2*.37f, mGR.mSel == 6 ? 1.1f : 1, mGR.mSel == 6 ? 0.5f : 1);
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.875f,.33f- 0*.37f, mGR.mTex_Control.width()*.4f, mGR.mTex_Control.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.875f,.33f- 1*.37f, mGR.mTex_Control.width()*.4f, mGR.mTex_Control.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.875f,.33f- 2*.37f, mGR.mTex_Control.width()*.4f, mGR.mTex_Control.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.650f,.13f- 0*.37f, mGR.mTex_Quick.width()*.4f, mGR.mTex_Quick.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(0.550f,.13f- 1*.37f, mGR.mTex_Quick.width()*.4f, mGR.mTex_Quick.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(0.450f,.13f- 2*.37f, mGR.mTex_Quick.width()*.4f, mGR.mTex_Quick.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 6;
		}
		if(MotionEvent.ACTION_UP == event.getAction())		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMECONT;
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 4:
				mGR.gameReset(0,0);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else
					M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
				break;
			case 5:
				M.GameScreen = M.GAMELEVEL;
				page = (mGR.mULvl/15)%7;
				break;
			case 6:
				mGR.gameReset(2,0);
				M.GameScreen = M.GAMEPLAY;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else
					M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
				break;
				
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true; 
	}
	/*********************Menu End*************************************/
	 
	/*********************GamePlay Start***********************************/
	
	void gameOver(){
		M.GameScreen = M.GAMEOVER;
		GameRenderer.mStart.show();
	}
	
	void SetAnim(){
		for(int i=0;i<mGR.mAni.length;i++)
				mGR.mAni[i].set(0, .2f);
	}
	void Draw_Animation(GL10 gl){
		boolean draw = false;
		for(int i=0;i<mGR.mAni.length;i++)
			if(mGR.mAni[i].no>0){
				DrawTextureRS(gl, mGR.mTex_Part[i%11], mGR.mAni[i].x,mGR.mAni[i].y,Counter*10,mGR.mAni[i].no*.03f);
				mGR.mAni[i].update();
				draw = true;
			}
		if(draw)
		{
			DrawTextureS(gl, mGR.mTex_Goal,0,0,(40-mGR.mAni[0].no)*.03f);
		}
	}
	float vx =0.01f;
	void gamelogic(){
		mGR.mPlayer.update();
		mGR.mKeeper.update();
	}
	void Draw_Gameplay(GL10 gl)	{
		DrawTexture(gl, mGR.mTex_BG[0], mGR.mPlayer.bgx-mGR.mTex_BG[0].width()*.5f-mGR.mTex_BG[1].width()*.5f, 0);
		DrawTexture(gl, mGR.mTex_BG[2], mGR.mPlayer.bgx+mGR.mTex_BG[2].width()*.5f+mGR.mTex_BG[1].width()*.5f, 0);
		DrawTexture(gl, mGR.mTex_BG[1], mGR.mPlayer.bgx, 0);
		
		DrawTexture(gl, mGR.mTex_Net[mGR.mPlayer.anim%3], mGR.mPlayer.bgx, .36f);
		
		DrawTexture(gl, mGR.mTex_Shadow, mGR.mKeeper.x, .28f);
		DrawTexture(gl, mGR.mTex_Opp[mGR.mKeeper.img], mGR.mKeeper.x, mGR.mKeeper.y);
				
		for (int i = 0; i < mGR.Deewar; i++) {
			DrawTexture(gl, mGR.mTex_Dewar, mGR.DeewarX + .05f * i, -.1f);
		}
		
		mGR.mTex_Ball[0].drawTS(gl, mGR.mPlayer.sx,mGR.mPlayer.sy-.01f, mGR.mPlayer.bz,.25f);
		DrawTextureS(gl, mGR.mTex_Ball[mGR.mPlayer.move?(Counter%mGR.mTex_Ball.length):0], mGR.mPlayer.bx,mGR.mPlayer.by,mGR.mPlayer.bz);
		if(!mGR.mPlayer.pmove){
			for(int i=0;i<3;i++)
				DrawTexture(gl, mGR.mTex_Player[i][0], mGR.mPlayer.px[i], mGR.mPlayer.py[i]);
		}else{
			
				DrawTexture(gl, mGR.mTex_Player[mGR.mPlayer.pno][mGR.mPlayer.pani%mGR.mTex_Player[mGR.mPlayer.pno].length], mGR.mPlayer.px[mGR.mPlayer.pno], mGR.mPlayer.py[mGR.mPlayer.pno]);
				if(mGR.mPlayer.pani<mGR.mTex_Player[mGR.mPlayer.pno].length){
					if(Counter%3==0){
						mGR.mPlayer.pani++;
						if(mGR.mPlayer.pani>=mGR.mTex_Player[mGR.mPlayer.pno].length){
							mGR.mPlayer.set();
							mGR.mKeeper.set();
						}
					}
				}
			for(int i=0;i<3;i++)
				if(mGR.mPlayer.pno !=i)
					DrawTexture(gl, mGR.mTex_Player[i][0], mGR.mPlayer.px[i], mGR.mPlayer.py[i]);
				
		}
//		DrawTexture(gl, mGR.mTex_Level,-.9f, -.9f);
//		drawNumberScal(gl, ""+mGR.Level, -.8f, -.9f, 0, 1, 1);
		Draw_Animation(gl);
		
		if (mGR.mType != 0) {
			DrawTexture(gl, mGR.mTex_Button, -.83f, .88f);
			DrawTexture(gl, mGR.mTex_Icon, -.83f - .09f, .88f);
			DrawTexture(gl, mGR.mTex_BallText, -.83f + .05f, .88f);
			drawNumberScal(gl, "" + mGR.mBall, -.90f, .90f, 1, 1, 1);
		}
		DrawTextureRS(gl, mGR.mTex_Button, .83f, .880f, 180, 1);
		DrawTexture(gl, mGR.mTex_Icon, .83f + .1f, .88f);
		DrawTexture(gl, mGR.mTex_Level, .83f - .05f, .88f);
		drawNumberScal(gl, "" + mGR.Level, .94f, .90f, 1, 1, 1);
		
		
		gamelogic();
	}
	float mx,my;
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			mx	= screen2worldX(event.getX());
			my	= screen2worldY(event.getY());
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			float dx = world2screenX(mGR.mPlayer.bx);
			float dy = M.ScreenHieght * .8f;
			mx -= screen2worldX(event.getX());
			my -= screen2worldY(event.getY());
			float dis = (float)Math.sqrt((mx*mx)+(my*my));
			double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
//			System.out.println(dis+"  ---------------   "+ang);
			if (dis > .2f && ang > 2 && ang < 4 && !mGR.mPlayer.pmove && !mGR.mPlayer.move
					&& mGR.mPlayer.py[0] == mGR.mPlayer.py[1]
					&& mGR.mPlayer.py[0] == mGR.mPlayer.py[2]) {
				mGR.mPlayer.set((float) ang);
				
			}
		}
		return true;
	}
	
	/*********************GamePlay End*************************************/
	
	
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureRS(GL10 gl, SimplePlane Tex, float x, float y, float angle,float scal) {
		Tex.drawRotet(gl, x, y,angle,scal);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}

	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z, float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY, double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
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
	
	
	float world2screenX(float a) {
		float c = ((a / 2) + 0.5f) * M.ScreenWidth;
		return c;
	}

	float world2screenY(float a) {
		float c = (0.5f-(a / 2)) * (M.ScreenHieght);
		return c;
	}
	

	boolean Rect2RectIntersection(float ax, float ay, float adx, float ady, float bx, float by, float bdx, float bdy) {
		ax -= adx / 2;
		ay += ady / 2;
		bx -= bdx / 2;
		by += bdy / 2;
		if (ax + adx > bx && ay - ady < by && bx + bdx > ax && by - bdy < ay) {
			return true;
		}
		return false;
	}

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2, double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2)) + ((cy1 - cy2) * (cy1 - cy2)));
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

	void drawNumberScal(GL10 gl,String strs,float x, float y,int align,float scal,float tranc)
	{
		float dx = mGR.mTex_Font[0].width()*scal;
		 if(align ==1)
				x-=strs.length()*dx*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawTransprentScal(gl,x+i*dx,y,scal,tranc);
		}
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

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Roking new Game '" + GameRenderer.mContext.getResources().getString(R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT, "Let the battle commence!!! Download it now and let’s ROCK!!!!  " + M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT) .show();
		}
	}
}
