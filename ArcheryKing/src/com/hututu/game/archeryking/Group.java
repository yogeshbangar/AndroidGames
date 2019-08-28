package com.hututu.game.archeryking;
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
		Counter ++;
//		M.GameScreen=M.GAMEACHIEV;
		if(M.GameScreen!=M.GAMELOGO)
			mGR.mBGround.DrawBG(gl);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>60)
			{
				M.play(GameRenderer.mContext,R.drawable.mainbg);
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
		case M.GAMEHIGH:
			Draw_AHelp(gl);
			break;
		case M.GAMEPUASE:
		case M.GAMEOVER:
			Draw_Pause(gl);
			break;
		case M.GAMEWIN:
			DrawTransScal(gl, mGR.mTex_Back	, -.92f,-.89f,mGR.mSel==2?2.2f:1,mGR.mSel==1?.5f:1);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		
		}
		if(M.GameScreen != M.GAMEPLAY && M.GameScreen!=M.GAMELOGO)
			mGR.mBGround.RunDraw_Char(gl);
		if(M.GameScreen == M.GAMEACHIEV)
			Draw_Achiev(gl);
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
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
		case M.GAMEHIGH:
		case M.GAMEACHIEV:
			Handle_AHelp(event);
			break;
		case M.GAMEPUASE:
		case M.GAMEOVER:
			Handle_Pause(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEWIN:
			if(MotionEvent.ACTION_UP == event.getAction() && CircRectsOverlap(-.92f,-.89f, mGR.mTex_Skip.width()*.5f, mGR.mTex_Skip.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEOVER;
				M.sound5(GameRenderer.mContext,R.drawable.button_click);
				M.play(GameRenderer.mContext,R.drawable.mainbg);
			}
			break;
		}
		Handle(event);
		return true;
	}
	/************************************Achievement Start******************************************************/
	void Draw_Achiev(GL10 gl){
		DrawTexture(gl, mGR.mTex_Ach[4], 0, 0);
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(mGR.mCom.Achiev[j][i])
					DrawTexture(gl, mGR.mTex_Ach[j], -.3f+j*.3f, .5f-i*.33f);
			}
		}
		DrawTransScal(gl, mGR.mTex_Back		 , -.92f,-.89f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	}
	/************************************Achievement Start******************************************************/
	/************************************About Help Start******************************************************/
	void Draw_AHelp(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Angle1	, -.42f, 0.06f);
		DrawTexture(gl, mGR.mTex_Angle2	, 0.49f, 0.61f);
		DrawTexture(gl, mGR.mTex_Angle0	, -.75f, -.35f);
		DrawTexture(gl, mGR.mTex_Node0	, -.75f, 0.77f);
		DrawTexture(gl, mGR.mTex_Node1	, 0.95f, 0.61f);
		DrawTexture(gl, mGR.mTex_Flag_bg, 0.24f, 0.06f);
		DrawTexture(gl, mGR.mTex_Target	, -.83f, 0.28f);
		DrawFlip	(gl,mGR.mTex_Target	, -.66f, -.66f);
		
		
		if(M.GameScreen == M.GAMEABOUT)
		{
			DrawTexture(gl, mGR.mTex_Aboutus, 0.00f, 0.82f);
			DrawTexture(gl, mGR.mTex_About	, 0.24f, 0.16f);
		}
		if(M.GameScreen == M.GAMEHELP)
		{
			DrawTexture(gl, mGR.mTex_HelpText, 0.00f, 0.82f);
			DrawTexture(gl, mGR.mTex_Help	, 0.24f, 0.16f);
		}
		if(M.GameScreen == M.GAMEHIGH)
		{
			DrawTexture(gl, mGR.mTex_HighscoreT, 0.00f, 0.82f);
			DrawTexture(gl, mGR.mTex_Score, .23f, .19f);
			drawNumber(gl, mGR.mHighScore, .18f, .19f);
			
		}
		DrawTransScal(gl, mGR.mTex_Back		 , -.92f,-.89f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	}
	boolean Handle_AHelp(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.92f,-.89f, mGR.mTex_Skip.width()*.5f, mGR.mTex_Skip.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Exit
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				if(M.GAMEHELP == M.GameScreen)
				{
					M.stop(GameRenderer.mContext);
					M.GameScreen = M.GAMEPLAY;
				}
				else
					M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel !=0)
				M.sound5(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************************About Help End******************************************************/
	
	/************************************Pause Over Start******************************************************/
	void Draw_Pause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_PausedArrow, 0, .58f);
		DrawTexture(gl, mGR.mTex_Archery	, 0, .70f);
		
		DrawTransScal(gl, mGR.mTex_Share	 , 0.9f, .84f-(0*.26f),mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
//		DrawTransScal(gl, mGR.mTex_Rateus	 , 0.9f, .84f-(1*.26f),mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Home		, 0.9f, .84f-(2*.26f),mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
		if(M.GameScreen == M.GAMEPUASE){
			DrawTexture(gl, mGR.mTex_Paused		, 0, .42f);
			DrawTransScal(gl, mGR.mTex_Resume	,-.60f,-.25f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		}
		else{
			DrawTexture(gl, mGR.mTex_Failed		, 0, .42f);
			DrawTransScal(gl, mGR.mTex_Playagain,-.60f,-.25f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Highscore,-.60f,-.60f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
	}
	boolean Handle_Pause(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.9f, .84f-(0*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Share
		}
		if(CircRectsOverlap(0.9f, .84f-(1*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
//			mGR.mSel = 2;//RateUs
		}
		if(CircRectsOverlap(0.9f, .84f-(2*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Menu
		}
		if(CircRectsOverlap(-.6f,-.25f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//GamePlay
		}
		if(CircRectsOverlap(-.6f,-.60f, mGR.mTex_Achievment.width()*.4f, mGR.mTex_Achievment.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//HighScore
		}
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				share2friend();
				break;
			case 2:
				RateUs();
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				if(M.GameScreen == M.GAMEOVER)
					mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.stop(GameRenderer.mContext);
				break;
			case 5:
				M.GameScreen = M.GAMEHIGH;
				break;
			}
			if(mGR.mSel !=0)
				M.sound5(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************************Pause Over End******************************************************/
	
	/************************************Menu Start******************************************************/
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash_font, 0.0f, .5f);
		
		DrawTransScal(gl, mGR.mTex_Facebook	 , 0.9f, .84f-(0*.26f),mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_AboutI	 , 0.9f, .84f-(1*.26f),mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Googleplay, 0.9f, .84f-(2*.26f),mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Play		 ,-.60f,-.25f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		DrawTransScal(gl, mGR.mTex_Achievment,-.60f,-.60f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
		DrawTransScal(gl, mGR.mTex_Skip		 ,-.92f,-.89f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
		
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.9f, .84f-(0*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//FaceBook
		}
		if(CircRectsOverlap(0.9f, .84f-(1*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//About
		}
		if(CircRectsOverlap(0.9f, .84f-(2*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Google Play
		}
		if(CircRectsOverlap(-.6f,-.25f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//GamePlay
		}
		if(CircRectsOverlap(-.6f,-.60f, mGR.mTex_Achievment.width()*.4f, mGR.mTex_Achievment.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Achievement
		}
		if(CircRectsOverlap(-.92f,-.89f, mGR.mTex_Skip.width()*.5f, mGR.mTex_Skip.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;//Exit
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				FaceBook();
				break;
			case 2:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				break;
			case 5:
				M.GameScreen = M.GAMEACHIEV;
				break;
			case 6:
				GameRenderer.mStart.Exit();
				break;
			}
			if(mGR.mSel !=0)
				M.sound5(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************************Menu End******************************************************/
	
	
	/************************************GamePlay Start******************************************************/
	void GameOver()
	{
		M.GameScreen = M.GAMEWIN;
		M.sound8(GameRenderer.mContext, R.drawable.gameover);
	}
	
	void gameplay()
	{
		if(mGR.mPower.y>-1.2f)
			mGR.mPower.update();
		if(mGR.mArrow[0].x<-1.5 ||mGR.mArrow[0].x>1.5 || mGR.mArrow[0].y > 1.5 || mGR.mArrow[0].y<-1.5)
		{
			mGR.mArrow[0].x 	= mGR.mBow.x;
			mGR.mArrow[0].y 	= mGR.mBow.y;
			mGR.mBow.d 		= 0;
			mGR.mArrow[0].vx 	= mGR.mArrow[0].vy = 0;
			mGR.setTarget();
			mGR.mTarget[0].setColor(0.5f);
			mGR.mCom.colorUpdate(mGR.mTarget[0].Color);
			GameRenderer.mArrowNO--;
			if(GameRenderer.mArrowNO<=0)
				GameOver();
		}
		
		
		
		if(CircRectsOverlap(mGR.mPower.x, mGR.mPower.y, mGR.mTex_Power[mGR.mPower.rPower].width()*.2f, mGR.mTex_Power[mGR.mPower.rPower].Height()*.2f,
				mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f, .08f))
		{
			mGR.score(mGR.mArrow[0].x,mGR.mArrow[0].y,123+M.mRand.nextInt(100));
			mGR.mBow.angle = 0;
			mGR.mArrow[0].radian = 0;
			M.sound6(GameRenderer.mContext, R.drawable.powercollect);
			mGR.mCom.SetAnimation(mGR.mPower.x, mGR.mPower.y);
			mGR.mCom.ColorAch	= M.mRand.nextInt(4);
			mGR.mPower.Power 	= mGR.mPower.rPower+1;
			mGR.mPower.time 	= 4;
			mGR.setArrow(0,0);
			mGR.mBow.d 			= 0;
			mGR.mPower.y 		=-100;
			mGR.mPower.counter	= 0;
			if(mGR.mPower.Power == 3)
			{
				mGR.mTarget[0].set(.2f, 0);
				mGR.mTarget[1].set(.6f,mGR.mTarget[0].y-mGR.mTex_Target.Height()*1.2f);
				mGR.mTarget[2].set(.6f,mGR.mTarget[0].y+mGR.mTex_Target.Height()*1.2f);
			}
			if(mGR.mPower.Power == 1)
			{
				mGR.mTarget[0].set(mGR.mTarget[0].x, 0);
			}
		}
		
		if(mGR.mPower.Power == 1){
			if(CircRectsOverlap(mGR.mTarget[0].x, mGR.mTarget[0].y, mGR.mTex_Target.width()*.3f, mGR.mTex_Target.Height()*.8f,
					mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f, .08f) && !mGR.mArrow[0].colide)
			{
				mGR.mArrow[0].colide = true;
				mGR.mArrow[0].x = mGR.mTarget[0].x-(float)Math.cos(mGR.mArrow[0].radian)*.35f;
				Counter = 0;
				M.sound2(GameRenderer.mContext, R.drawable.target_hit);
				mGR.mTarget[0].setColor((mGR.mTarget[0].y-(mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f))/2);
				
				mGR.mCom.colorUpdate(mGR.mTarget[0].Color);
				if(mGR.mTarget[0].Color!=0 && mGR.mTarget[0].Color!=1)
				{
					GameRenderer.mArrowNO--;
					if(GameRenderer.mArrowNO<=0)
						GameOver();
				}
				mGR.score(mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f,(100-(int)Math.abs(mGR.mTarget[0].hit*100)));
			}
		}
		else if(mGR.mPower.Power == 2){
			for(int i=0;i<mGR.mArrow.length;i++)
			{
				if(CircRectsOverlap(mGR.mTarget[0].x, mGR.mTarget[0].y, mGR.mTex_Target.width()*.3f, mGR.mTex_Target.Height()*.35f,
						mGR.mArrow[i].x+(float)Math.cos(mGR.mArrow[i].radian)*.33f,mGR.mArrow[i].y+ (float)Math.sin(mGR.mArrow[i].radian)*.605f, .08f) && !mGR.mArrow[i].colide)
				{
					mGR.mArrow[i].colide = true;
					mGR.mArrow[i].x = mGR.mTarget[0].x-(float)Math.cos(mGR.mArrow[0].radian)*.35f;
					Counter = 0;
					M.sound2(GameRenderer.mContext, R.drawable.target_hit);
					mGR.mTarget[0].setColor((mGR.mTarget[0].y-(mGR.mArrow[i].y+ (float)Math.sin(mGR.mArrow[i].radian)*.605f))/2);
					mGR.score(mGR.mArrow[i].x+(float)Math.cos(mGR.mArrow[i].radian)*.33f,mGR.mArrow[i].y+ (float)Math.sin(mGR.mArrow[i].radian)*.605f,(100-(int)Math.abs(mGR.mTarget[0].hit*100)));
					
				}
			}
		}
		else if(mGR.mPower.Power == 3){
			for(int i=0;i<mGR.mTarget.length;i++)
			{
				if(CircRectsOverlap(mGR.mTarget[i].x, mGR.mTarget[i].y, mGR.mTex_Target.width()*.3f, mGR.mTex_Target.Height()*.35f,
						mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f, .08f) && !mGR.mArrow[0].colide)
				{
					mGR.mArrow[0].colide = true;
					mGR.mArrow[0].x = mGR.mTarget[i].x-(float)Math.cos(mGR.mArrow[0].radian)*.35f;
					Counter = 0;
					M.sound2(GameRenderer.mContext, R.drawable.target_hit);
					mGR.mTarget[i].setColor(mGR.mTarget[i].y-(mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f));
					mGR.score(mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f,(100-(int)Math.abs(mGR.mTarget[i].hit*100)));
					mGR.mCom.colorUpdate(mGR.mTarget[i].Color);
					if(mGR.mTarget[i].Color!=0 && mGR.mTarget[i].Color!=1)
					{
						GameRenderer.mArrowNO--;
						if(GameRenderer.mArrowNO<=0)
							GameOver();
					}
				}
			}
		}
		else
			if(CircRectsOverlap(mGR.mTarget[0].x, mGR.mTarget[0].y, mGR.mTex_Target.width()*.2f, mGR.mTex_Target.Height()*.35f,
					mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f, .08f) && !mGR.mArrow[0].colide)
			{
				mGR.mArrow[0].colide = true;
				mGR.mArrow[0].x = mGR.mTarget[0].x-(float)Math.cos(mGR.mArrow[0].radian)*.35f;
				Counter = 0;
				M.sound2(GameRenderer.mContext, R.drawable.target_hit);
				mGR.mTarget[0].setColor(mGR.mTarget[0].y-(mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f));
				mGR.score(mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f,(100-(int)Math.abs(mGR.mTarget[0].hit*100)));
				mGR.mCom.colorUpdate(mGR.mTarget[0].Color);
//				mGR.mCom.colorUpdate(0);
				if(mGR.mTarget[0].Color!=0 && mGR.mTarget[0].Color!=1)
				{
					GameRenderer.mArrowNO--;
					if(GameRenderer.mArrowNO<=0)
						GameOver();
				}
						
			}
		if(mGR.mArrow[0].colide && Counter >20)
		{
			mGR.mBow.set();
			mGR.setArrow(0,0);
			mGR.setTarget();
		}

		mGR.mTarget[0].update();
		if(mGR.mPower.Power == 2){
			for(int i=0;i<mGR.mArrow.length;i++)
				mGR.mArrow[i].update();
		}
		else
			mGR.mArrow[0].update();
		
	}
	void Draw_GamePlay(GL10 gl)
	{
		gameplay();
		DrawTexture(gl,mGR.mTex_Score		,-.6f, .8f);
		drawNumber(gl ,mGR.mScore,-.64f, .8f);
		DrawTexture(gl,mGR.mTex_ArrowsBar	,-.6f, .6f);
		drawNumber(gl ,GameRenderer.mArrowNO,-.63f, .6f);
		
		
		DrawTransScal(gl, mGR.mTex_HelpI	, 0.9f, .84f-(0*.26f),mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Pause	, 0.9f, .84f-(1*.26f),mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Reset	, 0.9f, .84f-(2*.26f),mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
		
		if(mGR.mPower.Power == 1)
			DrawTextureS(gl, mGR.mTex_Target, mGR.mTarget[0].x, mGR.mTarget[0].y,2,0);
		else if(mGR.mPower.Power == 3)
		{
			DrawTexture(gl, mGR.mTex_Target, mGR.mTarget[0].x, mGR.mTarget[0].y);
			DrawTexture(gl, mGR.mTex_Target, mGR.mTarget[1].x, mGR.mTarget[1].y);
			DrawTexture(gl, mGR.mTex_Target, mGR.mTarget[2].x, mGR.mTarget[2].y);
		}
		else
			DrawTextureS(gl, mGR.mTex_Target, mGR.mTarget[0].x, mGR.mTarget[0].y,1,0);
		
		Draw_Char(gl);
		DrawTexture(gl, mGR.mTex_Text[mGR.mTarget[0].Power], 0, mGR.mTarget[0].ty);
		if(mGR.mCom.Counter<100){
			for(int i=0;i<mGR.mCom.mAni.length;i++)
			{
				if(mGR.mCom.mAni[i].x>-1 && mGR.mCom.mAni[i].y >-1 && mGR.mCom.mAni[i].x<1 &&mGR.mCom.mAni[i].y<1 && mGR.mCom.mAni[i].z>0&& mGR.mCom.mAni[i].t>0)
					DrawTransScal(gl, mGR.mTex_Dot[mGR.mCom.ColorAch], mGR.mCom.mAni[i].x, mGR.mCom.mAni[i].y,mGR.mCom.mAni[i].z,mGR.mCom.mAni[i].t);
			}
			DrawTextureS(gl, mGR.mTex_Color	, 0.00f, 0,mGR.mCom.Blast,mGR.mCom.ColorAch);
			if(mGR.mCom.ColorAch ==0){
				DrawTextureS(gl, mGR.mTex_Combo[mGR.mCom.ColorAch]	, -.00f,0.06f,mGR.mCom.Blast,0);
				DrawTextureS(gl, mGR.mTex_ComboText	, -.04f,-.06f,mGR.mCom.Blast,0);	
			}
			else{
				DrawTextureS(gl, mGR.mTex_Combo[mGR.mCom.ColorAch]	, -.12f, 0,mGR.mCom.Blast,0);
				DrawTextureS(gl, mGR.mTex_ComboText	, 0.07f, 0,mGR.mCom.Blast,0);
			}
			if(mGR.mCom.NewAchiv)
				DrawTextureS(gl, mGR.mTex_Combo[4]	, -.12f, -.2f,mGR.mCom.Blast,0);
			mGR.mCom.update();
		}
		if(mGR.mPower.counter<100){
			mGR.mPower.counter++;
			for(int i=0;i<mGR.mCom.mAni.length;i++)
			{
				if(mGR.mCom.mAni[i].x>-1 && mGR.mCom.mAni[i].y >-1 && mGR.mCom.mAni[i].x<1 &&mGR.mCom.mAni[i].y<1 && mGR.mCom.mAni[i].z>0&& mGR.mCom.mAni[i].t>0)
					DrawTransScal(gl, mGR.mTex_Dot[mGR.mCom.ColorAch], mGR.mCom.mAni[i].x, mGR.mCom.mAni[i].y,mGR.mCom.mAni[i].z,mGR.mCom.mAni[i].t);
			}
			mGR.mCom.animation();
		}
		if(mGR.mPower.y>-1.2f)
			DrawTexture(gl, mGR.mTex_Power[mGR.mPower.rPower], mGR.mPower.x, mGR.mPower.y);
		
		for(int i=0;i<mGR.mPoint.length;i++)
		{
			if(mGR.mPoint[i].y<1)
			{
				DPoint(gl ,mGR.mPoint[i].ID,mGR.mPoint[i].x, mGR.mPoint[i].y, mGR.mPoint[i].t);
				mGR.mPoint[i].update();
			}
		}
	}
	void Draw_Char(GL10 gl)
	{
		float x=mGR.mBow.x+.04f,y=mGR.mBow.y-.07f;
		
		float m=1.11f;
		float xx = 0.1f;
		if(mGR.mArrow[0].vx !=0||mGR.mArrow[0].vy!=0){
			mGR.mTex_Character[6].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,1.4f*m);
			mGR.mTex_Character[7].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,2.1f*m);
			mGR.mTex_Character[8].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,1.5f*m);
		}
		else
		{
			mGR.mTex_Character[6].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,1.4f*m);
			mGR.mTex_Character[7].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,2.1f*m);
			mGR.mTex_Character[8].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,1.5f*m);
		}
		DrawTexture(gl, mGR.mTex_Character[4]	, x+.05f,y-.15f);
		DrawTexture(gl, mGR.mTex_Character[5]	, x+.00f,y-.00f);
		DrawTexture(gl, mGR.mTex_Character[3]	, x-.00f,y+.06f);
		DrawTextureR(gl,mGR.mTex_Character[1]	, x-.01f,y+.27f,mGR.mBow.angle/4,1);//c
		
		if(mGR.mArrow[0].vx !=0||mGR.mArrow[0].vy!=0)
		{
//			DrawTexture(gl, mGR.mTex_Character[3]	, x+.06f,y+.04f);
			mGR.mTex_Character[2].drawRotet3(gl, 0, 0,0, x, y+.05f, 1);
			if(mGR.mPower.Power == 2){
				for(int i=0;i<mGR.mArrow.length;i++){
					mGR.mTex_Bow[0].drawRotet1(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1);
					if(mGR.mArrow[i].colide)
						mGR.mTex_ArrowCut.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[i].radian), mGR.mArrow[i].x,mGR.mArrow[i].y,mGR.mArrow[i].t,-1);
					else
						mGR.mTex_Arrow.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[i].radian), mGR.mArrow[i].x,mGR.mArrow[i].y,1,-1);
				}
			}
			else
			{
				mGR.mTex_Bow[0].drawRotet1(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1);
				if(mGR.mArrow[0].colide)
					mGR.mTex_ArrowCut.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[0].radian), mGR.mArrow[0].x,mGR.mArrow[0].y,mGR.mArrow[0].t,1);
				else
					mGR.mTex_Arrow.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[0].radian), mGR.mArrow[0].x,mGR.mArrow[0].y,1,1);
			
			}
			
		}
		else
		{
			int no = (int)(mGR.mBow.d*30)+1;
			no = no>=mGR.mTex_Bow.length?11:no;
			xx = no*.005f;
			if(mGR.mBow.angle>0)
				DrawTextureS(gl, mGR.mTex_Character[3]	, x+.03f-xx,y+.06f+(float)Math.sin(Math.toRadians(mGR.mBow.angle))*.03f,.8f,0);
			else
				DrawTextureS(gl, mGR.mTex_Character[3]	, x+.03f-xx,y+.06f-(float)Math.sin(Math.toRadians(mGR.mBow.angle))*.01f,.8f,0);
			mGR.mTex_Bow[no].drawRotet1(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1);
		}
	}
	/*void Draw_Char(GL10 gl)
	{
		float x=mGR.mBow.x+.02f,y=mGR.mBow.y-.07f;
		
		
		float xx = 0.1f;
		if(mGR.mArrow[0].vx !=0||mGR.mArrow[0].vy!=0){
			mGR.mTex_Character[6].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,1.2f);
			mGR.mTex_Character[7].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,2.0f);
			mGR.mTex_Character[8].drawRotet2(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1,1.5f);
		}
		else
		{
			mGR.mTex_Character[6].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,1.2f);
			mGR.mTex_Character[7].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,2.0f);
			mGR.mTex_Character[8].drawRotet2(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1,1.5f);
		}
		DrawTexture(gl, mGR.mTex_Character[4]	, x+.05f,y-.15f);
		DrawTexture(gl, mGR.mTex_Character[5]	, x+.00f,y-.00f);
		DrawTexture(gl, mGR.mTex_Character[3]	, x-.00f,y+.04f);
		DrawTextureR(gl,mGR.mTex_Character[1]	, x-.01f,y+.27f,mGR.mBow.angle/4,1);//c
		
		if(mGR.mArrow[0].vx !=0||mGR.mArrow[0].vy!=0)
		{
//			DrawTexture(gl, mGR.mTex_Character[3]	, x+.06f,y+.04f);
			mGR.mTex_Character[2].drawRotet3(gl, 0, 0,0, x, y+.1f, 1);
			if(mGR.mPower.Power == 2){
				for(int i=0;i<mGR.mArrow.length;i++){
					mGR.mTex_Bow[0].drawRotet1(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1);
					if(mGR.mArrow[i].colide)
						mGR.mTex_ArrowCut.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[i].radian), mGR.mArrow[i].x,mGR.mArrow[i].y,mGR.mArrow[i].t,1);
					else
						mGR.mTex_Arrow.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[i].radian), mGR.mArrow[i].x,mGR.mArrow[i].y,1,1);
				}
			}
			else
			{
				mGR.mTex_Bow[0].drawRotet1(gl	, 0, 0, 0, mGR.mBow.x,mGR.mBow.y,1);
				if(mGR.mArrow[0].colide)
					mGR.mTex_ArrowCut.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[0].radian), mGR.mArrow[0].x,mGR.mArrow[0].y,mGR.mArrow[0].t,1);
				else
					mGR.mTex_Arrow.drawRotet2(gl	, 0, 0, (float)Math.toDegrees(mGR.mArrow[0].radian), mGR.mArrow[0].x,mGR.mArrow[0].y,1,1);
			
			}
			
		}
		else
		{
			int no = (int)(mGR.mBow.d*30)+1;
			no = no>=mGR.mTex_Bow.length?11:no;
			xx = no*.007f;
			if(mGR.mBow.angle>0)
				DrawTexture(gl, mGR.mTex_Character[3]	, x+.06f-xx,y+.04f+(float)Math.sin(Math.toRadians(mGR.mBow.angle))*.05f);
			else
				DrawTexture(gl, mGR.mTex_Character[3]	, x+.06f-xx,y+.04f-(float)Math.sin(Math.toRadians(mGR.mBow.angle))*.01f);
			mGR.mTex_Bow[no].drawRotet1(gl	, 0, 0, mGR.mBow.angle, mGR.mBow.x,mGR.mBow.y,1);
		}
	}*/
	
	public boolean Handle_GamePlay(MotionEvent event) 
	{
		mGR.mSel =0;
		if(CircRectsOverlap(0.9f, .84f-(0*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//FaceBook
		}
		if(CircRectsOverlap(0.9f, .84f-(1*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//About
		}
		if(CircRectsOverlap(0.9f, .84f-(2*.26f), mGR.mTex_HelpI.width()*.4f, mGR.mTex_HelpI.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Google Play
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.play(GameRenderer.mContext,R.drawable.mainbg);
				M.GameScreen = M.GAMEHELP;
				mGR.mSel = 0;
				return true;
			case 2:
				M.play(GameRenderer.mContext,R.drawable.mainbg);
				M.GameScreen = M.GAMEPUASE;
				mGR.mSel = 0;
				return true;
			case 3:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				mGR.mSel = 0;
				return true;
			}
			if(mGR.mSel !=0)
				M.sound5(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel =0;
		}
		if((mGR.mArrow[0].vx == 0 && mGR.mArrow[0].vy ==0)){
			float x = mGR.mBow.x - screen2worldX(event.getX());
			float y = mGR.mBow.y - screen2worldY(event.getY());
			mGR.mBow.d = (float)Math.sqrt(x*x+y*y);
			x = (int)Math.toDegrees(M.GetAngle(x,y));
			if(x>-40 && x<60)
				mGR.mBow.angle = (int)x;
			mGR.mArrow[0].radian = (float)M.GetAngle((float)Math.cos(Math.toRadians(mGR.mBow.angle))*mGR.mBow.d*.4f,(float)Math.sin(Math.toRadians(mGR.mBow.angle))*mGR.mBow.d*.4f);
			if(MotionEvent.ACTION_UP == event.getAction())
			{
				mGR.mArrow[0].vx = (float)Math.cos(Math.toRadians(mGR.mBow.angle))*mGR.mBow.d*.4f;
				mGR.mArrow[0].vy = (float)Math.sin(Math.toRadians(mGR.mBow.angle))*mGR.mBow.d*.4f;
				mGR.setArrow(mGR.mArrow[0].vx,mGR.mArrow[0].vy);
				M.sound1(GameRenderer.mContext, R.drawable.arrow_shoot);
			}
		}
		return true;
	}
	/************************************GamePlay End******************************************************/
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float t)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y,t);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal,int color)
	{
		switch(color){
		case 1:
			Tex.drawScal(gl, x, y,scal,scal,1,1,0);
			break;
		case 2:
			Tex.drawScal(gl, x, y,scal,scal,0,0,1);
			break;
		case 3:
			Tex.drawScal(gl, x, y,scal,scal,0,0,0);
			break;
		default:
			Tex.drawScal(gl, x, y,scal,scal,1,1,1);
		break;
		
		}
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
	
	void drawNumber(GL10 gl, int no, float x, float y) {
		float dx = mGR.mTex_Font[0].width();
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
	
	void DPoint(GL10 gl,int no,float x, float y, float t)
	{
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
			{
				mGR.mTex_Font[k].drawTransprentScal(gl,x+i*dx,y,1,t);
			}
		}
	}
	
	
	void FaceBook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.FACEBOOK));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
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
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
