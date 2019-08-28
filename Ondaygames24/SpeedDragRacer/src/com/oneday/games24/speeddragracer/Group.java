package com.oneday.games24.speeddragracer;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	float sx,sy;
	int Counter =0;
	
	public Group(GameRenderer _GameRenderer){mGR = _GameRenderer;} 
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	@Override
	public void draw(GL10 gl) 
	{
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 50)
			{
				M.GameScreen = M.GAMEMENU;
				M.loopstop();
				//M.play(GameRenderer.mContext, R.drawable.background);
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Draw_Manu(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEHELP:case M.GAMEABUT:
			Draw_HELP_ABOUT(gl);
			break;
		case M.GAMEWIN:case M.GAMEOVER:
			DrawWinOver(gl);
			break;
//		case M.GAMEHIGH:
//			DrawHigh(gl);
//			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				M.loopstop();
				//M.play(GameRenderer.mContext, R.drawable.background);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Handle_Manu(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEHELP:case M.GAMEABUT:
			Handle_HELP_ABOUT(event);
			break;
		case M.GAMEWIN:case M.GAMEOVER:
			HandleWinOver(event);
			break;
		default:
		case M.GAMEPLAY:
			HandleGamePlay(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	
//	void DrawHigh(GL10 gl)
//	{
//		mGR.mTex_D[0].drawRS(gl, 1.41f, 1, 18+sx*100,0.03f, .16f);
//		DrawTexture(gl, mGR.mTex_D[2], 0, 0);
//		mGR.mTex_D[1].drawRS(gl,1-sx, 1, 		 sx*100	,-.03f, .08f);
//	}
//	
	void DrawWinOver(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_SetBG, 0, 0);
		if(M.GameScreen == M.GAMEWIN)
		{
			mGR.mFont.draw(gl, M.SHARE, -.91f,-.05f, 2, 0);
			mGR.mFont.draw(gl, M.NEXT  , -0.829f,-.85f, 1, 1);
			mGR.mFont.draw(gl, M.WIN, -.0f, .7f, 2, 1);
		}
		else
		{
			mGR.mFont.draw(gl, M.HINT, -.91f,-.05f, 2, 0);
			mGR.mFont.draw(gl, M.RETRY  , -0.829f,-.85f, 1, 1);
			mGR.mFont.draw(gl, M.LOST,-.0f, .7f, 2, 1);
		}
		
		DrawTexture(gl, mGR.mTex_Pattern, -.05f, 0.359f);
		
		DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[1].mNo][0], -.65f,  0.489f,.6f);
		DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[0].mNo][0], -.65f,  0.220f,.6f);
	
		
		mGR.mFont.draw(gl, mGR.playertime,-0.479f, 0.489f, 2, 0);
		mGR.mFont.draw(gl, mGR.opptime	,-0.479f, 0.220f, 2, 0);
		
		
		
		mGR.mFont.draw(gl, M.MAXSPEED	,0.46f,0.529f-(0*0.12f), 1, 2);
		mGR.mFont.draw(gl, M.PERFECT	,0.46f,0.529f-(1*0.12f), 1, 2);
		mGR.mFont.draw(gl, M.MPH060		,0.46f,0.529f-(2*0.12f), 1, 2);
		mGR.mFont.draw(gl, M.MPH0100	,0.46f,0.529f-(3*0.12f), 1, 2);
		
		mGR.mFont.draw(gl, mGR.mCar[1].mSpeed+" MPH"	,0.48f,0.529f-(0*0.12f), 2, 0);
		mGR.mFont.draw(gl, mGR.mCar[1].pShift+"]"+mGR.mCar[1].mGear,0.48f,0.529f-(1*0.12f), 2, 0);
		
		String s="";
		int n = (int)(mGR.mCar[1].m60%1000);
		n/=10;
		if(mGR.mCar[1].m60==0)
			s = "NA"; 
		else if(n<10)
			s = (mGR.mCar[1].m60/1000)+"\\0"+n+" SEC";
		else
			s = (mGR.mCar[1].m60/1000)+"\\"+n+" SEC";
		mGR.mFont.draw(gl, s	,0.48f,0.529f-(2*0.12f), 2, 0);
		
		n = (int)(mGR.mCar[1].m100%1000);
		n/=10;
		if(mGR.mCar[1].m100==0)
			s = "NA"; 
		else if(n<10)
			s = (mGR.mCar[1].m100/1000)+"\\0"+n+" SEC";
		else
			s = (mGR.mCar[1].m100/1000)+"\\"+n+" SEC";
		mGR.mFont.draw(gl, s	,0.48f,0.529f-(3*0.12f), 2, 0);
		
		
		
		mGR.mFont.draw(gl, M.BONUS , -.1f,-.14f, 0, 0);
		
		mGR.mFont.draw(gl, M.MORE, +.68f,-.05f, 2, 0);
		
		mGR.mFont.draw(gl, M.BACK  ,  0.839f,-.85f, 1, 1);
		switch (mGR.mSel) {
		case 12:
			DrawTexture (gl, mGR.mTex_LevelBut[0],-.775f,-0.052f);
			break;
		case 13:
			DrawTexture (gl, mGR.mTex_LevelBut[0],+.775f,-0.052f);
			break;
		case 14:
			DrawTexture (gl, mGR.mTex_LevelBut[1],-.829f,-0.839f);
			break;
		case 15:
			DrawTexture (gl, mGR.mTex_LevelBut[1],0.829f,-0.839f);
			break;
		}
		
		mGR.mFont.draw(gl, M.RACEBONUS	,0,-.25f-(0*.13f), 1, 2);
		mGR.mFont.draw(gl, M.LAUNCEBONUS,0,-.25f-(1*.13f), 1, 2);
		mGR.mFont.draw(gl, M.PERFECT	,0,-.25f-(2*.13f), 1, 2);
		mGR.mFont.draw(gl, M.GOODBONUS	,0,-.25f-(3*.13f), 1, 2);
		mGR.mFont.draw(gl, M.RACEPRICE	,0,-.25f-(4*.13f), 1, 2);
		mGR.mFont.draw(gl, M.TOTAL		,0,-.25f-(5*.13f), 1, 2);
		
		mGR.mFont.draw(gl,""+mGR.mCar[0].racebonus	,0.1f,-.25f-(0*.13f), 2, 0);
		mGR.mFont.draw(gl,""+mGR.mCar[0].launcebonus,0.1f,-.25f-(1*.13f), 2, 0);
		mGR.mFont.draw(gl,""+mGR.mCar[0].perfect	,0.1f,-.25f-(2*.13f), 2, 0);
		mGR.mFont.draw(gl,""+mGR.mCar[0].goodbonus	,0.1f,-.25f-(3*.13f), 2, 0);
		mGR.mFont.draw(gl,""+mGR.mCar[0].raceprice	,0.1f,-.25f-(4*.13f), 2, 0);
		mGR.mFont.draw(gl,""+mGR.mCar[0].total		,0.1f,-.25f-(5*.13f), 2, 0);
		
		if(mGR.mCar[0].racebonus<mGR.mCar[1].racebonus)
			mGR.mCar[0].racebonus++;
		if(mGR.mCar[0].launcebonus<mGR.mCar[1].launcebonus)
			mGR.mCar[0].launcebonus++;
		if(mGR.mCar[0].perfect<mGR.mCar[1].perfect)
			mGR.mCar[0].perfect++;
		if(mGR.mCar[0].goodbonus<mGR.mCar[1].goodbonus)
			mGR.mCar[0].goodbonus++;
		if(mGR.mCar[0].raceprice<mGR.mCar[1].raceprice)
		{
			mGR.mCar[0].raceprice+=20;
			if(mGR.mCar[0].raceprice>mGR.mCar[1].raceprice)
				mGR.mCar[0].raceprice=mGR.mCar[1].raceprice;
		}
		if(mGR.mCar[0].total<mGR.mCar[1].total)
		{
			mGR.mCar[0].total+=20;
			if(mGR.mCar[0].total>mGR.mCar[1].total)
				mGR.mCar[0].total=mGR.mCar[1].total;
		}
		
	}
	boolean HandleWinOver(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 12;//Hint
		}
		if(CircRectsOverlap(0.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 13;//MORE
		}
		if(CircRectsOverlap(-.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 14;//NEXT
		}
		if(CircRectsOverlap(0.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 15;//SHARE
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			
			case 12:
				if(M.GameScreen == M.GAMEWIN)
					share2friend();
				else
					GameRenderer.mStart.Massage("Upgrade your car from shop.");
				break;
			case 13:
				MoreGame();
				break;
			case 14:
				{
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
				}
				break;
			case 15:
				M.GameScreen = M.GAMEMENU;
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	void Draw_Level(GL10 gl)
	{
		float y = .35f;
		DrawTexture(gl, mGR.mTex_SetBG, 0, 0);
		mGR.mFont.draw(gl, M.PHASE+(mGR.breakPoint+1), -.16f, .7f, 2, 0);
		for(int i=0;i<mGR.mLevel.length;i++)
		{
			DrawTransScal(gl, mGR.mTex_CarPer,mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
			DrawTransScal(gl, mGR.mTex_Car[i][0],mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
			if(mGR.unlockLevel <= i)
				DrawTransScal(gl, mGR.mTex_Lock,mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
		}
		DrawTexture(gl, mGR.mTex_Mask,0.0f,.39f);
		if(mGR.mSel == 11)
			DrawTextureR(gl, mGR.mTex_Arrow,-.90f,0.35f,180);
		else
			DrawFlip(gl, mGR.mTex_Arrow,-.90f,0.35f,0);
		
		if(mGR.mSel == 10)
			DrawFlip(gl, mGR.mTex_Arrow,0.90f,0.35f,180);
		else
			DrawTexture (gl, mGR.mTex_Arrow,0.90f,0.35f);
		
		mGR.mFont.draw(gl, M.RATEUS, -.91f,-.05f, 2, 0);
		mGR.mFont.draw(gl, M.MORE  , +.68f,-.05f, 2, 0);
		if(mGR.mSel == 12)
			DrawTexture (gl, mGR.mTex_LevelBut[0],-.775f,-0.052f);
		if(mGR.mSel == 13)
			DrawTexture (gl, mGR.mTex_LevelBut[0],+.775f,-0.052f);
		
		mGR.mFont.draw(gl, M.LEVEL , -.1f,-.14f, 0, 0);
		
		for( int i=0;i<4;i++)
		{
			DrawTexture(gl, mGR.mTex_Level[0],-.4f+(i*.26f),-.40f);
			mGR.mFont.draw(gl, ""+(i+1)  ,-.4f+(i*.26f),-.40f, 1, 1);
			if(mGR.mLevel[mGR.breakPoint].mSLevel == i)
				DrawTexture(gl, mGR.mTex_Level[1],-.4f+(i*.26f),-.40f);
			if(mGR.mLevel[mGR.breakPoint].mULevel<i || mGR.unlockLevel <= mGR.breakPoint)
				DrawTexture(gl, mGR.mTex_Lock,-.4f+(i*.26f),-.40f);
		}
		
		mGR.mFont.draw(gl, M.CALT  , +.0f,-.65f, 1, 1);
		mGR.mFont.draw(gl, M.UNF   , -.0f,-.80f, 1, 1);
		
		mGR.mFont.draw(gl, M.RACE  , -0.829f,-.85f, 1, 1);
		mGR.mFont.draw(gl, M.BACK  ,  0.839f,-.85f, 1, 1);
		
		if(mGR.mSel == 14)
			DrawTexture (gl, mGR.mTex_LevelBut[1],-.829f,-0.839f);
		if(mGR.mSel == 15)
			DrawTexture (gl, mGR.mTex_LevelBut[1],0.829f,-0.839f);
		
		if(mGR.mMove == 1)
		{
			for(int i=0;i<mGR.mLevel.length;i++)
			{
				mGR.mLevel[i].x +=.01f*5;
				mGR.mLevel[i].z +=mGR.mLevel[i].vz*5;
			}
			if(mGR.mLevel[mGR.breakPoint].x>0)
				mGR.mMove = 0;
		}
		if(mGR.mMove == 2)
		{
			for(int i=0;i<mGR.mLevel.length;i++)
			{
				mGR.mLevel[i].x -=.01f*5;
				mGR.mLevel[i].z +=mGR.mLevel[i].vz*5;
			}
			if(mGR.mLevel[mGR.breakPoint].x<0)
				mGR.mMove = 0;
		}
	}
	public boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel = 0;
		
		for( int i=0;i<4;i++)
		{
			if(CircRectsOverlap(-.4f+(i*.26f),-.40f,mGR.mTex_Level[0].width()*.4f,mGR.mTex_Level[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			{
				mGR.mSel = i+1;//Level
			}
		}
		if(mGR.mMove ==0 && CircRectsOverlap(0.90f,0.35f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 10;//Right Arrow
		}
		if(mGR.mMove ==0 && CircRectsOverlap(-.90f,0.35f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 11;//Left Arrow
		}
		if(CircRectsOverlap(-.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 12;//Rate Us
		}
		if(CircRectsOverlap(0.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 13;//More
		}
		if(CircRectsOverlap(-.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 14;//Race
		}
		if(CircRectsOverlap(0.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 15;//Back
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:
				if(mGR.mLevel[mGR.breakPoint].mULevel>=mGR.mSel-1)
					mGR.mLevel[mGR.breakPoint].mSLevel = mGR.mSel-1;
				else{
//					System.out.println(M.LEVCROSS);
					GameRenderer.mStart.Massage(M.LEVCROSS);
				}
				break;
			case 10:
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				moveAction(1);
				break;
			case 11:
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				moveAction(2);
				break;
			case 12:
				RateUs();
				break;
			case 13:
				MoreGame();
				break;
			case 14:
				if(mGR.unlockLevel > mGR.breakPoint)
				{
					
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
					mGR.sond = 50;
				}else{
//					System.out.println(M.PHSCROSS);
					GameRenderer.mStart.Massage(M.PHSCROSS);
				}
				break;
			case 15:
				M.GameScreen = M.GAMEMENU;
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/******************************************************************************
	 * @param Shop START
	 *******************************************************************************/
	void Draw_Shop(GL10 gl)
	{
		float y = .35f;
		DrawTexture(gl, mGR.mTex_SetBG, 0, 0);
		mGR.mFont.draw(gl, M.COIN+mGR.mCoint, -.00f, .7f, 2, 1);
		for(int i=0;i<mGR.mLevel.length;i++)
		{
			DrawTransScal(gl, mGR.mTex_CarPer,mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
			DrawTransScal(gl, mGR.mTex_Car[i][0],mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
			if(mGR.unlockLevel <= i)
				DrawTransScal(gl, mGR.mTex_Lock,mGR.mLevel[i].x,y,mGR.mLevel[i].z,mGR.mLevel[i].z);
		}
		DrawTexture(gl, mGR.mTex_Mask,0.0f,.39f);
		if(mGR.mSel == 11)
			DrawTextureR(gl, mGR.mTex_Arrow,-.90f,0.35f,180);
		else
			DrawFlip(gl, mGR.mTex_Arrow,-.90f,0.35f,0);
		
		if(mGR.mSel == 10)
			DrawFlip(gl, mGR.mTex_Arrow,0.90f,0.35f,180);
		else
			DrawTexture (gl, mGR.mTex_Arrow,0.90f,0.35f);
		
		if(mGR.mLevel[mGR.breakPoint].mUPower<5){
			mGR.mFont.draw(gl, M.UPRICE, -.78f,-.00f, 0, 1);
			mGR.mFont.draw(gl, ""+(int)(mGR.mLevel[mGR.breakPoint].strength[3]*mGR.mLevel[mGR.breakPoint].mUPower*M.CON), -.78f,-.10f, 1, 1);
			mGR.mFont.draw(gl, M.UPGRADE,+.62f,-.05f, 2, 0);
		}
		else{
			mGR.mFont.draw(gl, M.FULLY	  ,-.77f,-.00f, 0, 1);
			mGR.mFont.draw(gl, M.UPGRADED ,-.77f,-.10f, 0, 1);
			mGR.mFont.draw(gl, M.FULLY	  ,+.77f,-.00f, 0, 1);
			mGR.mFont.draw(gl, M.UPGRADED ,+.77f,-.10f, 0, 1);
		}
		if(mGR.mSel == 13)
			DrawTexture (gl, mGR.mTex_LevelBut[0],+.775f,-0.052f);
		
		mGR.mFont.draw(gl, M.TITLE , -.1f,-.14f, 0, 0);
		
		for( int i=0;i<6;i++)
		{
			mGR.mFont.draw(gl, M.FEATURES[i]  ,-.06f,-.28f-(i*.125f), 1, 2);
			DrawTexture(gl, mGR.mTex_Power,.30f,-.28f-(i*.125f));
			mGR.mTex_Red.drawSS(gl,.035f,-.278f-(i*.125f), mGR.mLevel[mGR.breakPoint].strength[i]*mGR.mLevel[mGR.breakPoint].mUPower*1.5f, .9f);
		}
		
		mGR.mFont.draw(gl, M.NEXT  , -0.829f,-.85f, 1, 1);
		mGR.mFont.draw(gl, M.BACK  ,  0.839f,-.85f, 1, 1);
		
		if(mGR.mSel == 14)
			DrawTexture (gl, mGR.mTex_LevelBut[1],-.829f,-0.839f);
		if(mGR.mSel == 15)
			DrawTexture (gl, mGR.mTex_LevelBut[1],0.829f,-0.839f);
		
		if(mGR.mMove == 1)
		{
			for(int i=0;i<mGR.mLevel.length;i++)
			{
				mGR.mLevel[i].x +=.01f*5;
				mGR.mLevel[i].z +=mGR.mLevel[i].vz*5;
			}
			if(mGR.mLevel[mGR.breakPoint].x>0)
				mGR.mMove = 0;
		}
		if(mGR.mMove == 2)
		{
			for(int i=0;i<mGR.mLevel.length;i++)
			{
				mGR.mLevel[i].x -=.01f*5;
				mGR.mLevel[i].z +=mGR.mLevel[i].vz*5;
			}
			if(mGR.mLevel[mGR.breakPoint].x<0)
				mGR.mMove = 0;
		}
	}
	public boolean Handle_Shop(MotionEvent event)
	{
		mGR.mSel = 0;
		if(mGR.mMove ==0 && CircRectsOverlap(0.90f,0.35f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 10;//Right Arrow
		}
		if(mGR.mMove ==0 && CircRectsOverlap(-.90f,0.35f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 11;//Left Arrow
		}
		if(CircRectsOverlap(0.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 13;//Upgrade
		}
		if(CircRectsOverlap(-.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 14;//Next
		}
		if(CircRectsOverlap(0.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 15;//Back
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
//			System.out.println("-----------------------------------------------------------------");
			switch (mGR.mSel) {
			case 10:
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				moveAction(1);
				break;
			case 11:
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				moveAction(2);
				break;
			case 13:
				Upgrade();
				break;
			case 14:
				M.GameScreen = M.GAMELEVEL;
				break;
			case 15:
				M.GameScreen = M.GAMEMENU;
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	void Upgrade()
	{
		int val = (int)(mGR.mLevel[mGR.breakPoint].strength[3]*mGR.mLevel[mGR.breakPoint].mUPower*M.CON);
		if(mGR.mLevel[mGR.breakPoint].mUPower<5 && val<=mGR.mCoint)
		{
			mGR.mLevel[mGR.breakPoint].mUPower++;
			mGR.mCoint-=val;
		}
		else if(mGR.mLevel[mGR.breakPoint].mUPower>=5)
		{
			GameRenderer.mStart.Massage("Full Upgraded.");
		}
		else
		{
			GameRenderer.mStart.Massage(M.DONTCOIN);
		}
	}
	/******************************************************************************
	 * @param Shop END
	 *******************************************************************************/
	/******************************************************************************
	 * @param HELP ABOUT START
	 *******************************************************************************/
	void Draw_HELP_ABOUT(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_SetBG, 0, 0);
		
		if(M.GameScreen == M.GAMEHELP){
			mGR.mFont.draw(gl, "HELP", -0.0f, .7f, 2, 1);
			DrawTexture (gl, mGR.mTex_Help[1],0.000f,0.548f);
			DrawTexture (gl, mGR.mTex_Help[0],-.010f,-.566f);
		}
		else{
			mGR.mFont.draw(gl, "ABOUT US", -.18f, .7f, 2, 0);
			DrawTexture (gl, mGR.mTex_About[1],0,0.46f);
			DrawTexture (gl, mGR.mTex_About[0],0,-.56f);
		}
		mGR.mFont.draw(gl, M.RATEUS, -.91f,-.05f, 2, 0);
		mGR.mFont.draw(gl, M.MORE  , +.68f,-.05f, 2, 0);
		if(mGR.mSel == 12)
			DrawTexture (gl, mGR.mTex_LevelBut[0],-.775f,-0.052f);
		if(mGR.mSel == 13)
			DrawTexture (gl, mGR.mTex_LevelBut[0],+.775f,-0.052f);
		
		mGR.mFont.draw(gl, M.SHARE  , -0.829f,-.85f, 1, 1);
		mGR.mFont.draw(gl, M.BACK  ,  0.839f,-.85f, 1, 1);
		if(mGR.mSel == 14)
			DrawTexture (gl, mGR.mTex_LevelBut[1],-.829f,-0.839f);
		if(mGR.mSel == 15)
			DrawTexture (gl, mGR.mTex_LevelBut[1],0.829f,-0.839f);
		
	}
	public boolean Handle_HELP_ABOUT(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0,0.52f,.7f,.2f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 11;//Download
		}
		if(CircRectsOverlap(-.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 12;//Rate Us
		}
		if(CircRectsOverlap(0.775f,-0.052f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 13;//More
		}
		if(CircRectsOverlap(-.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 14;//Share
		}
		if(CircRectsOverlap(0.829f,-0.839f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 15;//Back
		}
		
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
//			System.out.println("-----------------------------------------------------------------");
			switch (mGR.mSel) {
			case 11:
				Intent mIntent = new Intent(Intent.ACTION_VIEW);
				if(M.GameScreen == M.GAMEABUT)
					mIntent.setData(Uri.parse(M.STUNT));
				else
					mIntent.setData(Uri.parse(M.COPTER));
			    GameRenderer.mContext.startActivity(mIntent);
				break;
			case 12:
				RateUs();
				break;
			case 13:
				MoreGame();
				break;
			case 14:
				share2friend();
				break;
			case 15:
				M.GameScreen = M.GAMEMENU;
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/******************************************************************************
	 * @param HELP ABOUT END
	 *******************************************************************************/
	void moveAction(int select)
	{
		int i =0;
		if(select == 1)
		{
			mGR.mMove = 1;
			mGR.breakPoint = mGR.mLevel.length;
			for(i=0;i<mGR.mLevel.length;i++)
			{
				if(mGR.mLevel[i].x >.5f && mGR.mLevel[i].x <.7f)
					break;
			}
			for(int j=0;j<mGR.mLevel.length;j++)
			{
				if(i<0)
					i=mGR.mLevel.length-1;
				if(j<2)
					mGR.mLevel[i].set(0.6f-(j*.6f),(.6f + (j*.4f)),-.0066f);
				else
				{
					if(mGR.breakPoint==mGR.mLevel.length)
						mGR.breakPoint = i;
					mGR.mLevel[i].set(0.6f-(j*.6f),(1.0f - ((j-1)*.4f)),0.0066f);
				}
				i--;
			}
			mGR.breakPoint %= mGR.mLevel.length;
		}
		if(select == 2)
		{
		
			mGR.mMove = 2;
			mGR.breakPoint = mGR.mLevel.length;
			for(i=0;i<mGR.mLevel.length;i++)
			{
				if(mGR.mLevel[i].x <-.5f && mGR.mLevel[i].x >-.7f)
					break;
			}
			for(int j=0;j<mGR.mLevel.length;j++)
			{
				if(i==mGR.mLevel.length)
					i=0;
				if(j<2)
					mGR.mLevel[i].set(-.6f+(j*.6f),(.6f + (j*.4f)),-.0066f);
				else
				{
					if(mGR.breakPoint==mGR.mLevel.length)
						mGR.breakPoint = i;
					mGR.mLevel[i].set(-.6f+(j*.6f),(1.0f - ((j-1)*.4f)),0.0066f);
				}
				i++;
			}
			mGR.breakPoint %= mGR.mLevel.length;
		}
	}
	float[] y ={-1.2f,-1.2f,-1.2f,-1.2f,-1.2f,-1.2f};
	int mCounter;
	void Draw_Manu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Menubar, 0, y[0]-.26f);
		for(int i=0;i<mGR.mTex_MenuIcn.length;i++)
		{
			DrawTexture(gl, mGR.mTex_MenuBut[0], -0.78f+(i*0.39f),y[i+1]-.16f);
			if(mGR.mSel == i+1)
				DrawTexture(gl, mGR.mTex_MenuBut[1], -0.78f+(i*0.39f),y[i+1]-.16f);
			DrawTexture(gl, mGR.mTex_MenuIcn[i], -0.92f+(i*0.39f),y[i+1]);//-.67f
			if(!M.setValue && i==4)
				DrawTexture(gl, mGR.mTex_SOff, -0.92f+(i*0.39f),y[i+1]);
				
			mGR.mFont.draw(gl, M.MENU[i], -0.89f+(i*0.39f),y[i+1]-.16f, 1, 0);
		}
		if(y[mCounter%6]>-.67f && mCounter<6)
			mCounter++;
		if(mCounter<6)
			y[mCounter]+=.05f;
			
	}
	public boolean Handle_Manu(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<mGR.mTex_MenuIcn.length;i++)
		{
			if(CircRectsOverlap(-0.78f+(i*0.39f),-.83f,mGR.mTex_MenuBut[0].width()*.4f,mGR.mTex_MenuBut[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMELEVEL;
				break;
			case 2:
				M.GameScreen = M.GAMESHOP;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				M.GameScreen = M.GAMEABUT;
				break;
			case 5:
				M.loopstop();
				M.setValue = !M.setValue;
				if(M.setValue)
					M.play(GameRenderer.mContext, R.drawable.background);
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	void gameWinLoose()
	{
//		M.loopstop();
		int n = (int)(mGR.mCar[1].mTime%1000);
		n/=10;
		if(n<10)
			mGR.playertime = (mGR.mCar[1].mTime/1000)+"\\0"+n+" SEC";
		else
			mGR.playertime = (mGR.mCar[1].mTime/1000)+"\\"+n+" SEC";
		
		n = (int)(mGR.mCar[0].mTime%1000);
		n/=10;
		if(n<10)
			mGR.opptime = (mGR.mCar[0].mTime/1000)+"\\0"+n+" SEC";
		else
			mGR.opptime = (mGR.mCar[0].mTime/1000)+"\\"+n+" SEC";
		
		
		mGR.mCar[1].racebonus = M.mRand.nextInt(100)+10;
		mGR.mCar[1].launcebonus = M.mRand.nextInt(100)+10;
		mGR.mCar[1].goodbonus = M.mRand.nextInt(100)+10;
		mGR.mCar[1].perfect = M.mRand.nextInt(100)+10;
		
		
		if(mGR.mCar[1].mTime <= mGR.mCar[0].mTime)
		{
			mGR.mCar[1].raceprice = (mGR.breakPoint*200+500)*(mGR.mLevel[mGR.breakPoint].mSLevel+1);
			if(mGR.mLevel[mGR.breakPoint].mSLevel<=mGR.mLevel[mGR.breakPoint].mULevel);
			{
//				System.out.println("0~~~~~[SLeve = "+mGR.mLevel[mGR.breakPoint].mSLevel+"] [ULeve = "+mGR.mLevel[mGR.breakPoint].mULevel+"] [BreakPoint = "+mGR.breakPoint+"] [unlockLevel = "+mGR.unlockLevel+"]");
				if(mGR.mLevel[mGR.breakPoint].mSLevel<3)
				{
					if(mGR.mLevel[mGR.breakPoint].mSLevel == mGR.mLevel[mGR.breakPoint].mULevel && mGR.mLevel[mGR.breakPoint].mULevel <3)
						mGR.mLevel[mGR.breakPoint].mULevel++;
					mGR.mLevel[mGR.breakPoint].mSLevel++;
				}
				else if(mGR.breakPoint == (mGR.unlockLevel-1) && mGR.unlockLevel < 5)
				{
					mGR.unlockLevel++;
					mGR.breakPoint ++;
					mGR.mLevel[mGR.breakPoint].mSLevel =0;
				}
				else if(mGR.breakPoint < 5)
				{
					mGR.breakPoint ++;
					mGR.mLevel[mGR.breakPoint].mSLevel =0;
				}
			}
			M.GameScreen = M.GAMEWIN;
			GameRenderer.mStart.ShowInterstitial();//Smart();//Interstitial();
//			GameRenderer.mStart.load();
//			try {handler.sendEmptyMessage(0);}catch(Exception e){}
		}
		else
		{
			M.GameScreen = M.GAMEOVER;
			mGR.mCar[1].raceprice = 0;
			GameRenderer.mStart.ShowInterstitial();
//			try {handler.sendEmptyMessage(0);}catch(Exception e){}
//			GameRenderer.mStart.load();
		}
		mGR.mCar[1].total = mGR.mCar[1].racebonus+mGR.mCar[1].launcebonus+mGR.mCar[1].goodbonus+mGR.mCar[1].perfect+mGR.mCar[1].raceprice;
		mGR.mCoint+=mGR.mCar[1].total;
		mGR.mHScore+=mGR.mCar[1].total;
//		M.play(GameRenderer.mContext, R.drawable.background);
	}
//	private Handler handler = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.load();}};//AdHouse
	void bgUpadate()
	{
		if(!mGR.mCar[1].Complete)
		{
//			System.out.println(mGR.mCar[1].totalDis+"        "+mGR.mCar[1].mCarSpeed);
			mGR.mCar[1].totalDis+=mGR.mCar[1].mCarSpeed;
			
			mGR.mSGame+=mGR.mCar[1].mCarSpeed;
			
			mGR.mBG +=(mGR.mCar[1].mCarSpeed)*.5f;
			mGR.mBG2+=(mGR.mCar[1].mCarSpeed)*.5f;
			if(mGR.mBG<-2)
				mGR.mBG = mGR.mBG2+2;
			if(mGR.mBG2<-2)
				mGR.mBG2 = mGR.mBG +2;
			
			mGR.mBGB +=(mGR.mCar[1].mCarSpeed)*.01f;
			mGR.mBGB2+=(mGR.mCar[1].mCarSpeed)*.01;
			if(mGR.mBGB<-2)
				mGR.mBGB = mGR.mBGB2+2;
			if(mGR.mBGB2<-2)
				mGR.mBGB2 = mGR.mBGB +2;
			
			mGR.mBGBD +=(mGR.mCar[1].mCarSpeed*.13f);
			mGR.mBGBD2+=(mGR.mCar[1].mCarSpeed*.13);
			if(mGR.mBGBD<-2)
				mGR.mBGBD = mGR.mBGBD2+2;
			if(mGR.mBGBD2<-2)
				mGR.mBGBD2 = mGR.mBGBD+2;
		}
		if(mGR.mCar[1].dx > .91f && mGR.mCar[0].dx > .91f && mGR.EndCounter == 0)
		{
			M.loopstop();
			mGR.EndCounter =30;
			M.play(GameRenderer.mContext, R.drawable.background);
		}
		if(mGR.EndCounter>0)
		{
			mGR.EndCounter--;
			if(mGR.EndCounter<2)
				gameWinLoose();
		}
	}
	void gameLogic()
	{
		bgUpadate();
		mGR.mCar[0].x += mGR.mCar[1].mCarSpeed-mGR.mCar[0].mCarSpeed;
		
		for(int i  =0;i<mGR.mCar.length;i++)
		{
			mGR.mCar[i].update();
		}
		mGR.mCar[0].Opponet(mGR.mCar[1].mCarSpeed);
		if(mGR.mCar[1].mGear == 1)
		{
			if(mGR.mCar[1].mRPM>100)
				mGR.mCar[1].mRPM -=mGR.mCar[1].Rcount;
			else
				mGR.mCar[1].Rcount = 0;
		}
		
	}
	void Draw_GamePlay(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Road	 , mGR.mBG		,0.00f);
		DrawTexture(gl, mGR.mTex_Road	 , mGR.mBG2		,0.00f);
		DrawTexture(gl, mGR.mTex_Building, mGR.mBGB		,0.96f);
		DrawTexture(gl, mGR.mTex_Building, mGR.mBGB2	,0.96f);
		
		DrawTexture(gl, mGR.mTex_Boundry[0] , mGR.mBGBD ,0.69f);
		DrawTexture(gl, mGR.mTex_Boundry[0] , mGR.mBGBD2,0.69f);
		DrawTexture(gl, mGR.mTex_Boundry[1] , mGR.mBG 	,-.60f);
		DrawTexture(gl, mGR.mTex_Boundry[1] , mGR.mBG2	,-.60f);
		
		DrawTexture(gl, mGR.mTex_RaodMeter	,0	  			,0.870f-.2f);
		DrawTexture(gl, mGR.mTex_CarBar[0]	,mGR.mCar[0].dx	,0.870f-.2f);
		DrawTexture(gl, mGR.mTex_CarBar[1]	,mGR.mCar[1].dx	,0.775f-.2f);
		DrawTexture(gl, mGR.mTex_winning	,mGR.mCar[1].totalDis,0.00f);
		
		if(mGR.mLevel[mGR.breakPoint].mUPower > 4)
		{
			DrawTransScal(gl,mGR.mTex_NO2[0]	,-.9f,-.5f,1,mGR.mCar[1].NO2==M.NO3?1:.5f);
			if(mGR.mSel == 15)
				DrawTexture(gl, mGR.mTex_NO2[1]	,-.9f,-.5f);
		}
//		System.out.println(mGR.mSignal+ "				~~~~ "+mGR.mCar[1].mCPower+"  ~~  "+mGR.mLevel[mGR.breakPoint].mUPower+" ~~~~ "+mGR.mCar[1].mRPM+"  ~~~~~~~~~       "+mGR.mSGame);
		
		if(Counter%25==0 && mGR.mSignal<3 && mGR.mSignal!=-2)
		{
			mGR.mSignal++;
			if(mGR.mSignal>=0)
				M.sound4(GameRenderer.mContext, R.drawable.beep);
//			System.out.println(mGR.mSignal+"        "+mGR.mCar[1].mCarSpeed);
			if(mGR.mSignal == 3)
			{
				M.loopstop();
				M.play(GameRenderer.mContext, R.drawable.gameplay0);
				//mGR.mCar[1].mRPM = 60;
			}
		}
		
		//DrawTexture(gl, mGR.mTex_youlost, 0,0);
		//DrawTexture(gl, mGR.mTex_youwin	, 0,0);
		//
		
		//Blue  135 to 160
		//Green 160 to 165
		//Red   166 to 180
		if(mGR.mSignal == 3 && !mGR.mCar[1].Complete && mGR.mCar[1].Rcount == 0){
			if(mGR.mCar[1].mRPM>135 && mGR.mCar[1].mRPM<156)
			{
				DrawTransScal(gl, mGR.mTex_Light[0][0], 0, .2f,1,Counter%2==0?1:.8f);
			}
			
			if(mGR.mCar[1].mRPM>156 && mGR.mCar[1].mRPM<166)
			{
				DrawTransScal(gl, mGR.mTex_Light[1][0], 0, .2f,1,Counter%2==0?1:.8f);
			}
			
			if(mGR.mCar[1].mRPM>166 && mGR.mCar[1].mRPM<180)
			{
				DrawTransScal(gl, mGR.mTex_Light[2][0], 0, .2f,1,Counter%2==0?1:.8f);
			}
		}
		if(mGR.mAnimation.y < 2)
		{
			DrawTexture(gl, mGR.mTex_Light[mGR.mAnimation.No][1], .00f	,mGR.mAnimation.y);
			mGR.mAnimation.update();
		}
		DrawTexture(gl, mGR.mTex_Dasboard, .00f	,-.51f);
		if(mGR.mSel == 2)
			DrawTexture(gl, mGR.mTex_Gear[0], -.69f	,-.74f);
		else
			DrawTexture(gl, mGR.mTex_Gear[0], -.71f	,-.72f);
		if(mGR.mSel == 3)
			DrawTexture(gl, mGR.mTex_Gear[1], 0.69f	,-.74f);
		else
			DrawTexture(gl, mGR.mTex_Gear[1], 0.71f	,-.72f);
		if(mGR.mSignal < 3)
			DrawTexture(gl, mGR.mTex_MRPM[0], 0.0f	,-.63f);
		else{
			DrawTexture(gl, mGR.mTex_MRPM[1], 0.19f	,-.76f);
			DrawTexture(gl, mGR.mTex_MRPM[2], 0.22f	,-.84f);
		}
		DrawTexture(gl, mGR.mTex_Meter, 0	  	,-.79f);
		//DrawTextureR(gl,mGR.mTex_Clock	 , 0	,-.97f,-((mGR.mCar[1].mRPM%180)-90));
		if(mGR.mSignal<-2 || mGR.mCar[1].Complete)
			mGR.mTex_Clock.drawRotet(gl, 0, 0, -((0)-90), 0	,-.97f);
		else
			mGR.mTex_Clock.drawRotet(gl, 0, 0, -((mGR.mCar[1].mRPM%180)-90), 0	,-.97f);
		if(mGR.mCar[1].Complete)
			drawNumber(gl, "000",-0.42f,-0.85f);
		else if(mGR.mCar[1].mSpeed/100>0)
			drawNumber(gl, ""+mGR.mCar[1].mSpeed,-0.42f,-0.85f);
		else if(mGR.mCar[1].mSpeed/10>0)
			drawNumber(gl, "0"+mGR.mCar[1].mSpeed,-0.42f,-0.85f);
		else
			drawNumber(gl, "00"+mGR.mCar[1].mSpeed,-0.42f,-0.85f);
			
		drawNumber(gl, mGR.mCar[1].mGear+""	,0.40f,-0.85f);
		DrawTexture(gl, mGR.mTex_StartL	, mGR.mSGame-.42f,0.00f);
		DrawCar(gl);
		if(mGR.mSGame>-3)
		{
			mGR.sond--;
			if(mGR.sond==0)
			{
				M.loopstop();
				M.play(GameRenderer.mContext, R.drawable.standing);
			}
			
			DrawTexture(gl, mGR.mTex_Signal	, mGR.mSGame,0.44f);
			
			if(mGR.mSignal>=-2)
			{
				DrawTexture(gl, mGR.mTex_Start	, mGR.mSGame, 0.749f);
				if(mGR.mSignal==-2)
				{
					if(!mGR.mCar[1].start){
						mGR.mCar[1].mRPM +=5;
						if(mGR.mCar[1].mRPM > 150)
						{
							mGR.mCar[1].start = true;
						}
					}
					else
					{
						mGR.mCar[1].mRPM -=4;
						if(mGR.mCar[1].mRPM < 30)
						{
							mGR.mCar[1].mRPM = 35;
						}
					}
				}
				
			}
			if(mGR.mSignal>-1){
				if(mGR.mSignal<3)
				{
					DrawTexture(gl, mGR.mTex_Ready	, mGR.mSGame+0.01f,0.655f-mGR.mSignal*.08f);
					if(mGR.mSel==1)
					{
						mGR.mCar[1].mRPM +=4;
						if(mGR.mCar[1].mRPM > 165)
							mGR.mCar[1].mRPM = 161;
					}
					else{
						mGR.mCar[1].mRPM -=2;
						if(mGR.mCar[1].mRPM<49)
							mGR.mCar[1].mRPM =52;
					}
				}
				else
					DrawTexture(gl, mGR.mTex_Go		, mGR.mSGame+0.01f, 0.38f);
			}
		}
		
		if(mGR.mSignal>=3)
			gameLogic();
		else
		{
			DrawTexture(gl, mGR.mTex_Acce[0],0.8f,0.0f);
			if(mGR.mSel != 1)
				DrawTexture(gl, mGR.mTex_Acce[1],0.8f,0.0f);
		}
		
		if(mGR.EndCounter>0)
		{
			if(mGR.mCar[1].mTime <= mGR.mCar[0].mTime)
			{
				DrawTexture(gl, mGR.mTex_youwin,0.0f,0.0f);
				if(mGR.mLevel[mGR.breakPoint].mSLevel == 3)
				{
					mGR.mFont.draw(gl, M.PHASEM+(mGR.breakPoint+1), 0,-.33f, 2, 1);
				}
			}
			else
				DrawTexture(gl, mGR.mTex_youlost,0.0f,0.0f);
		}
		
//		System.out.println((mGR.mCar[1].mRPM/30000f)+"RPM = "+  mGR.mCar[1].mRPM);
	}
	void DrawCar(GL10 gl)
	{
		if(!mGR.mCar[1].Complete)
		{
			if(mGR.mCar[1].NO2>0&& mGR.mCar[1].NO2<M.NO3)
			{
				if(mGR.mCar[1].mNo == 4 || mGR.mCar[1].mNo == 1)
					DrawTexture(gl,mGR.mTex_Fire[Counter%2]	,mGR.mCar[1].x -0.27f+sx, mGR.mCar[1].y-0.03f+sy);
				else
					DrawTexture(gl,mGR.mTex_Fire[Counter%2]	,mGR.mCar[1].x -0.3f+sx, mGR.mCar[1].y-0.03f+sy);
			}
//				DrawTransScal(gl,mGR.mTex_Fire[Counter%2]	,mGR.mCar[1].x -0.3f+sx, mGR.mCar[1].y-0.03f+sy,1,mGR.mCar[1].NO2==100?1:.5f);
		}
//		mGR.mCar[0].mNo = mGR.mCar[1].mNo = 1;
		DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[0].mNo][0], mGR.mCar[0].x, mGR.mCar[0].y,.9f);
		DrawTexture(gl, mGR.mTex_Car[mGR.mCar[1].mNo][0], mGR.mCar[1].x, mGR.mCar[1].y);
		if(Counter%2==0 && mGR.mSignal >=3)
		{
			DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[0].mNo][1], mGR.mCar[0].x, mGR.mCar[0].y,.9f);
			if(!mGR.mCar[1].Complete)
				DrawTexture(gl, mGR.mTex_Car[mGR.mCar[1].mNo][1], mGR.mCar[1].x, mGR.mCar[1].y);
		}
		
		
	}
	public boolean HandleGamePlay(MotionEvent event) 
	{
		if(mGR.mSignal<3 && CircRectsOverlap(0.8f,0.0f, mGR.mTex_Acce[0].width()*.4f, mGR.mTex_Acce[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
			if(mGR.mSignal<=-2)
			{
				M.loopstop();
				mGR.mSignal =-1;
				M.play(GameRenderer.mContext, R.drawable.accelerator);
			}
		}
		if(mGR.mSignal >= 3 && CircRectsOverlap(-.66f,-.67f, mGR.mTex_Acce[0].width()*.3f, mGR.mTex_Acce[0].Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		
		if(mGR.mSignal >= 3 && CircRectsOverlap(+.66f,-.67f, mGR.mTex_Acce[0].width()*.3f, mGR.mTex_Acce[0].Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(mGR.mSignal >= 3 && mGR.mCar[1].NO2==M.NO3 &&
				CircRectsOverlap(-.9f,-.5f, mGR.mTex_NO2[0].width()*.4f, mGR.mTex_NO2[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 15;
		}
		
		if(event.getAction()== MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				
				break;
			case 2:
				if(mGR.mCar[1].mGear > 1)
				{
					mGR.mCar[1].mGear--;
					mGR.mCar[1].mRPM +=50;
					M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				}
				break;
			case 3 :
				if(mGR.mCar[1].mGear < 6)
				{
					
					if(mGR.mCar[1].mRPM>135 && mGR.mCar[1].mRPM<156)
					{
						mGR.mAnimation.set(.5f,0);
					}
					
					if(mGR.mCar[1].mRPM>156 && mGR.mCar[1].mRPM<166)
					{
						mGR.mCar[1].pShift++;
						mGR.mAnimation.set(.5f,1);
					}
					
					if(mGR.mCar[1].mRPM>166 && mGR.mCar[1].mRPM<180)
					{
						mGR.mAnimation.set(.5f,2);
					}
					mGR.mCar[1].mGear++;
					if(mGR.mCar[1].mRPM>50)
						mGR.mCar[1].mRPM -=50;
					M.sound1(GameRenderer.mContext, R.drawable.gearsift);
				}
				break;
			case 15:
				if(mGR.mLevel[mGR.breakPoint].mUPower > 4)
				{
					mGR.mCar[1].NO2 = M.NO3-20;
				}
				break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawR(gl, angle, x, y);
	}
	
//	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
//	{
//		Tex.drawRotet(gl, 0,0,angle, x, y);
//	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y,float r)
	{
		Tex.drawFilp(gl, x, y,r);
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
		if( ax+adx > bx  && ay+ady > by && bx+bdx > ax && by+bdy> ay)
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
	void drawNumber(GL10 gl,String strs,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width();
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
			{
				mGR.mTex_font.drawPos(gl,x+i*dx,y);
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
			}
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.MARKET);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
}
