package com.hututu.game.cricket.premier.league;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	int Sel =0;
	int Counter =0;
	int Count2 =0;
	int Count3 =0;
	float sx,sy;
	float spx = 0,spy=.49f;//.29-.67f
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"f,  "+sy+"f");}
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
//		M.GameScreen =M.SCOREBOARD;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter >100){
				M.GameScreen = M.SPLASH;//M.TEAMDETAIL;//
				Counter  =0;
			}
			break;
		case M.SPLASH:
			DrawTexture(gl, mGR.mTex_Splash, 0, 0);
			if(Counter >100)
				M.GameScreen = M.GAMESERIES;//M.TEAMDETAIL;//
			break;
		case M.GAMESERIES:
			Draw_Series(gl);
			break;
		case M.GAMETEAMSEL:
			Draw_TeamSel(gl);
			break;
		case M.TEAMDETAIL:
			Draw_TeamDetail(gl);
			break;
		case M.GAMECAPTAIN:
			Draw_Captain(gl);
			break;
		case M.TEAMINFO:
			Draw_TeamInfo(gl);
			break;
		case M.SCOREBOARD:
			Draw_ScoreBoard(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.WINLOOSE:
			Draw_WinLoose(gl);
			break;
		case M.BUYCOIN:
			Draw_Buy(gl);
			break;
		case M.ABOUT:
			Draw_About(gl);
			break;
		case M.SINGLEMATCH:
			Draw_SINGLE(gl);
			break;
		case M.PAUSE:
			Draw_Pause(gl);
			break;
		case M.FIXING:
			Draw_Fixing(gl);
			break;
		case M.CALLING:
			Draw_Calling(gl);
			break;
		case M.FIXED:
			Draw_Fixed(gl);
			break;
		case M.WARNING:
			Draw_Warning(gl);
			break;
		case M.LOADING:
			Draw_LOADING(gl);
			break;
		case M.DISCLAIMER:
			Draw_Disclaimer(gl);
			break;
		}
		Counter++;
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMESERIES:
			Handle_Series(event);
			break;
		case M.GAMETEAMSEL:
			Handle_TeamSel(event);
			break;
		case M.TEAMDETAIL:
			Handle_TeamDetail(event);
			break;
		case M.GAMECAPTAIN:
			Handle_Captain(event);
			break;
		case M.TEAMINFO:
			Handle_TeamInfo(event);
			break;
		case M.SCOREBOARD:
			Handle_ScoreBoard(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.WINLOOSE:
			Handle_WinLoose(event);
			break;
		case M.BUYCOIN:
			Handle_Buy(event);
			break;
		case M.ABOUT:
			Handle_About(event);
			break;
		case M.SINGLEMATCH:
			Handle_SINGLE(event);
			break;
		case M.PAUSE:
			Handle_Pause(event);
			break;
		case M.FIXING:
			Handle_Fixing(event);
			break;
		case M.CALLING:
			Handle_Calling(event);
			break;
		case M.FIXED:
			Handle_Fixed(event);
			break;
		case M.WARNING:
			Handle_Warning(event);
			break;
		case M.LOADING:
			Handle_LOADING(event);
			break;
		case M.DISCLAIMER:
			Handle_Disclaimer(event);
			break;
		}
//		Handle(event);
		return true;
	}
	/*****************************LOADING Start***************************************/
	void Draw_LOADING(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		
		DrawTexture(gl, mGR.mTex_Sfont, 0,0);
		
		if(Counter%3==0)
			Count3++;
		String str = "Loading";
		for(int i=0;i<(Count3%10);i++)
			str+=".";
		mGR.mFont.Draw(gl,str,.34f,-.89f, 1, 0,0);
		if(Counter>150){
			M.GameScreen =M.GAMEPLAY;
			M.play(R.drawable.game_play);
		}
		
	}
	boolean Handle_LOADING(MotionEvent event) {
		Sel =0;
		/*
		if(CircRectsOverlap(-.04f,-.52f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(.47f, -.52f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				winLoose();
				break;
			case 11:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.game_play);
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}*/
		return true;
	}
	/*****************************LOADING End***************************************/
	/*****************************Warning Start***************************************/
	void Draw_Warning(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup	, 0, 0);
		if (mGR.type == 0) {
			if (mGR.Serise == 0)
				DrawTexture(gl, mGR.mTex_Clip[4], -.45f, -.1f);
			else
				DrawTexture(gl, mGR.mTex_Clip[3],-.41f, -.1f);
		} else
			DrawTexture(gl, mGR.mTex_Bat_S, -0.45f,  -0.28f);
		mGR.mFont.Draw(gl, "Warning",-.52f,.56f, 1, 1,2);
		float gap = .15f;
		
		 
		
		mGR.mFont.Draw(gl, "Are you sure you want"	,-.1f,0.3f-gap*0, 1, 0,0);
		mGR.mFont.Draw(gl, "to Quit this match?"	,-.1f,0.3f-gap*1, 1, 0,0);
		mGR.mFont.Draw(gl, "Match cannot be undone."	,-.1f,0.3f-gap*2, 1, 0,0);
		
//		mGR.mTex_Red256.drawScal(gl , .47f, -.52f, .6f, Sel == 11 ? 1.3f : 1);
		DrawTransScal(gl, mGR.mTex_Blu128,.47f, -.52f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl,"Continue", .37f, -.52f, 1, 0,0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.04f,-.52f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "Quit Match",-.04f,-.52f, .80f,1);
	}
	boolean Handle_Warning(MotionEvent event) {
		Sel =0;
		
		if(CircRectsOverlap(-.04f,-.52f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(.47f, -.52f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				winLoose();
				break;
			case 11:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.game_play);
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************Warning End***************************************/
	/*****************************Fixed Start***************************************/
	void Draw_Fixed(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup	, 0, 0);
		DrawTexture(gl, mGR.mTex_Clip[1], 0, -.1f);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no]	,-.24f,-.03f, 0, 0,1);
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
//		mGR.mTex_Red256.drawScal(gl	, .51f, -.52f, .6f, Sel == 11 ? 1.5f : 1);
//		mGR.mFont.Draw(gl,"Continue", .51f, -.52f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
	}
	boolean Handle_Fixed(MotionEvent event) {
		Sel =0;
		
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
//			case 10:
//				M.GameScreen = M.GAMESERIES;
//				break;
			case 11:
//				M.GameScreen = M.GAMEPLAY;
				mGR.mBalTeam[mGR.Serise].Run = 50+M.mRand.nextInt(50);
//				M.play(R.drawable.game_play);
				Counter =0;
				M.GameScreen = M.LOADING;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************Fixed End***************************************/
	
	/*****************************Calling Start***************************************/
	void Draw_Calling(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup	, 0, 0);
		DrawTexture(gl, mGR.mTex_Clip[0], -.54f, -.1f);
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
		float gap = .13f;

		mGR.mFont.Draw(gl, "It is a Good deal.",-.2f,0.3f-gap*1, 1, 0,0);
		mGR.mFont.Draw(gl, "Soon you will get good News.",-.2f,0.3f-gap*2, 1, 0,0);
		
		if(Counter%3==0)
			Count3++;
//		mGR.mTex_Red256.drawScal(gl, .16f, -.52f, .6f, Sel == 11 ? 1.5f : 1);
		String str = "Calling";
		for(int i=0;i<(Count3%6);i++)
			str+=".";
		mGR.mFont.Draw(gl,str,.05f,-.52f, 1, 0,0);
		
		if(Counter >100){
			M.GameScreen = M.TEAMINFO;
			mGR.isFIxed = true;
		}
	}
	boolean Handle_Calling(MotionEvent event) {
		Sel =0;
		/*
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(.16f,-.52f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				M.GameScreen = M.GAMESERIES;
				break;
			case 11:
				M.GameScreen = M.TEAMINFO;
				mGR.isFIxed = true;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}*/
		return true;
	}
	/*****************************Calling End***************************************/
	
	/*****************************Fixing Start***************************************/
	void Draw_Fixing(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		DrawTexture(gl, mGR.mTex_Clip[2], -.47f, -.1f);
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
		float gap = .12f;
		
		mGR.mFont.Draw(gl, "Hello I am Bob the News Editor"	,-.2f,0.33f-gap*0, 1, 0,0);
		mGR.mFont.Draw(gl, "Reason you are standing here?"	,-.2f,0.33f-gap*1, 1, 0,0);
		mGR.mFont.Draw(gl, "If you want to fix something"	,-.2f,0.33f-gap*2, 1, 0,0);
		mGR.mFont.Draw(gl, "you are interested in,"			,-.2f,0.33f-gap*3, 1, 0,0);
		mGR.mFont.Draw(gl, "I am a right point of contact"	,-.2f,0.33f-gap*4, 1, 0,0);
		mGR.mFont.Draw(gl, "I will guaranteed Reduced the"	,-.2f,0.33f-gap*5, 1, 0,0);
		mGR.mFont.Draw(gl, "next Target."					,-.2f,0.33f-gap*6, 1, 0,0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "No Thanks",-.8f,-.9f, .85f,1);
		
		DrawTransScal(gl, mGR.mTex_Red256			,.16f,-.55f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Pay '"+M.FIxFee+"' Gold",.16f,-.55f, 1, 1,0);
	}
	boolean Handle_Fixing(MotionEvent event) {
		Sel =0;
		
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(.16f,-.55f, mGR.mTex_Red256.width()*.4f, mGR.mTex_Red256.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				M.GameScreen = M.TEAMINFO;
				break;
			case 11:
				if (mGR.mGold >= M.FIxFee) {
					M.GameScreen = M.CALLING;
					mGR.mGold -= M.FIxFee;
					Counter =0;
				}else{
					M.GameScreen = M.BUYCOIN;
				}
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************Fixing End***************************************/
	/*****************************Pause Start***************************************/
	
	//yogesh
	void Draw_Pause(GL10 gl){
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		

		mGR.mFont.Draw(gl, "Game Paused",-.25f,.56f, 2, 0,0);
		
		
		DrawTransScal(gl, mGR.mTex_Icon[4],-.54f,.26f, Sel == 10 ? 1.08f : 1, Sel == 10 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Blu128  ,-.31f,.26f, Sel == 10 ? 1.08f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "Continue"	,-.39f,.26f, .83f,0);
		
		DrawTransScal(gl, mGR.mTex_Icon[3],0.19f,.26f, Sel == 11 ? 1.08f : 1, Sel == 11 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Blu128  ,0.42f,.26f, Sel == 11 ? 1.08f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "Quit Match"	,.31f,.26f, .83f,0);
		
		DrawTransScal(gl, mGR.mTex_Icon[2],-.54f,-.05f, Sel == 12 ? 1.08f : 1, Sel == 12 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Blu128  ,-.31f,-.05f, Sel == 12 ? 1.08f : 1, Sel == 12 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "Score"	,-.35f,-.05f, .83f,0);
		
		DrawTransScal(gl, mGR.mTex_Icon[M.setValue?0:1],0.19f,-.05f, Sel == 13 ? 1.08f : 1, Sel == 13 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Blu128  ,0.42f,-.05f, Sel == 13 ? 1.08f : 1, Sel == 13 ? 0.5f : 1);
		mGR.mFont.DrawS(gl, "Sound "+(M.setValue?"On":"Off")	,.32f,-.05f, .83f,0);
		
		DrawTexture(gl, mGR.mTex_Yelo256,-.37f,-.31f);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no]	,-.37f,-.31f, 1, 1,0);
		
//		mGR.mTex_Whit160.drawScal(gl,-.447f,-.445f,1.18f,1);
		mGR.mTex_Whit256.drawScal(gl,-.447f,-.445f, .73f, 1);
		DrawTexture(gl, mGR.mTex_Whit64 ,-.150f,-.445f);
		mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p1],-.58f,-.445f,1,0,1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p1].run, -.150f,-.445f, 1, 1,1);
		
//		mGR.mTex_Whit160.drawScal(gl	,-.447f,-.58f,1.18f,1);
		mGR.mTex_Whit256.drawScal(gl,-.447f,-.58f, .73f, 1);
		DrawTexture(gl, mGR.mTex_Whit64 ,-.150f,-.58f);
		mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p2],-.58f,-.58f,1,0,1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p2].run, -.150f,-.58f, 1, 1,1);
		
		DrawTexture(gl, mGR.mTex_Yelo256,0.37f,-.31f);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no]	,0.37f,-.31f, 1, 1,0);
		
//		mGR.mTex_Whit160.drawScal(gl	,0.293f,-.445f,1.18f,1);
		mGR.mTex_Whit256.drawScal(gl,0.293f,-.445f, .73f, 1);
		DrawTexture(gl, mGR.mTex_Whit64 ,0.590f,-.445f);
		mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p1],0.16f,-.445f,1,0,1);
		mGR.mFont.Draw(gl, (mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p1].Ball/6)+"."+
					(mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p1].Ball%6), 0.590f,-.445f, 1, 1,1);
		
//		mGR.mTex_Whit160.drawScal(gl	,0.293f,-.58f,1.18f,1);
		mGR.mTex_Whit256.drawScal(gl,0.293f,-.58f, .73f, 1);
		DrawTexture(gl, mGR.mTex_Whit64 ,0.590f,-.58f);
		mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p2],0.16f,-.58f,1,0,1);
		mGR.mFont.Draw(gl, (mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p2].Ball/6)+"."+
				(mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p2].Ball%6), 0.590f,-.58f, 1, 1,1);
		
	}
	boolean Handle_Pause(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.43f,.26f, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;//continue
		}
		if(CircRectsOverlap(0.35f,.26f, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;//Quit Match
		}
		if(CircRectsOverlap(-.43f,-.05f, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =12;//Score
		}
		if(CircRectsOverlap(0.35f,-.05f, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =13;//Sound
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.game_play);
				break;
			case 11:
				M.GameScreen =M.WARNING;//winLoose();
				break;
			case 12:
				GameRenderer.mStart.onShowLeaderboardsRequested();//SCore
				break;
			case 13:
				M.setValue=!M.setValue;
				break;
				}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************Pause End***************************************/
	/*****************************SINGLE Start***************************************/
	void Draw_SINGLE(GL10 gl){
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		

		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
//		
		if(mGR.type == 1)
			mGR.mFont.Draw(gl, "T10 Match",-.64f,.56f, 2, 0,0);
		else
			mGR.mFont.Draw(gl, "T20 Match",-.64f,.56f, 2, 0,0);
		
		DrawTexture(gl, mGR.mTex_Trophy,-.35f,0);
		
		
		DrawTexture(gl, mGR.mTex_RedPop256, .21f,-.06f);
		mGR.mFont.Draw(gl, "All The Best"	,.22f,.10f, 0, 1,0);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no]	,.22f,-.03f, 1, 1,0);
		mGR.mFont.Draw(gl, "v/s"	,.22f,-.14f, 0, 1,0);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no]	,.22f,-.25f, 1, 1,0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back",-.8f,-.9f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
	}
	boolean Handle_SINGLE(MotionEvent event){
		Sel =0;
		
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				M.GameScreen = M.GAMESERIES;
				break;
			case 11:
//				M.GameScreen = M.GAMEPLAY;
//				M.play(R.drawable.game_play);
				Counter =0;
				M.GameScreen = M.LOADING;
				Counter =0;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************SINGLE End***************************************/
	/*****************Pop Up Start**********************/
	void popup(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Pup512x256, 0,0);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no]
				+ " vs " + M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no],-.01f,.4f, 1, 1, 0);
		mGR.mTex_Red256.drawScal(gl, 0,.24f,1.92f,1);
		mGR.mFont.Draw(gl,  (M.tBall / 6) + " over to chase "
				+ M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no] + " "
				+ mGR.mBalTeam[mGR.Serise].Run + " total.", -.06f, .25f,
				0, 1, 0);
		
		if(mGR.popUp == 3){
			popup3(gl);
		}
		else if(mGR.popUp == 1){
		mGR.mTex_BolClip[0].drawSRGBT(gl, -.38f,-.20f, 1,
				(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0]) / 255f,
				(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1]) / 255f,
				(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2]) / 255f, 1);
		DrawTexture(gl, mGR.mTex_BolClip[1], -.38f,-.20f);
		if (!mGR.mBalTeam[mGR.Serise].isTwo) {
			mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p1],
							.21f, .03f, 1, 0, 1);
			if(mGR.mBalTeam[mGR.Serise].p1 > 8){
				mGR.mFont.Draw(gl,"Spinner bowler",.28f,-.1f, 0, 1, 1);
			}else if(mGR.mBalTeam[mGR.Serise].p1 == 8){
				mGR.mFont.Draw(gl,"Fast medium bowler",.28f,-.1f, 0, 1, 1);
			}else{
				mGR.mFont.Draw(gl,"Fast bowler",.28f,-.1f, 0, 1, 1);
			}
		} else {
			mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p2],
							.21f, .03f, 0, 0, 1);
			if(mGR.mBalTeam[mGR.Serise].p2 > 8){
				mGR.mFont.Draw(gl,"Spinner bowler",.28f,-.1f, 0, 1, 1);
			}else if(mGR.mBalTeam[mGR.Serise].p2 == 8){
				mGR.mFont.Draw(gl,"Fast medium bowler",.28f,-.1f, 0, 1, 1);
			}else
				mGR.mFont.Draw(gl,"Fast bowler",.28f,-.1f, 0, 1, 1);
		}
		}else{
			mGR.mTex_BatClip[1].drawSRGBT(gl, -.35f,-.24f, 1,
					(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0]) / 255f,
					(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1]) / 255f,
					(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2]) / 255f, 1);
			DrawTexture(gl, mGR.mTex_BatClip[0], -.35f,-.24f);
			if (!mGR.mBatTeam[mGR.Serise].isTwo) {
				mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p1],
								.21f, .03f, 1, 0, 1);
			} else {
				mGR.mFont.Draw(gl,M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p2],
								.21f, .03f, 0, 0, 1);
			}
			mGR.mFont.Draw(gl,"Right Arm Batsman",.28f,-.1f, 0, 1, 1);
		}
		mGR.mFont.Draw(gl,"Tap to continue",0,-.38f, 1, 0, 0);
	}
	/*****************Pop Up End**********************/
	/*****************************About Start***************************************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		
		
		mGR.mFont.Draw(gl, "ABOUT", -.65f, .56f, 1, 0, 0);
		
		
		mGR.mFont.Draw(gl, "Cricket Premier League", 0, .33f, 1, 1, 0);
		mGR.mFont.Draw(gl, "(CPL)", 0, .21f, 1, 1, 0);
		mGR.mFont.Draw(gl, "Developd & Published by", 0, .05f, 1, 1, 0);
		mGR.mFont.Draw(gl, "hututu games software pvt. ltd.", 0, -.07f, 1, 1, 0);
		mGR.mFont.Draw(gl, "Indore (India)", 0, -.19f, 1, 1, 0);
		
		mGR.mFont.Draw(gl, "Version "+ mGR.ver, 0, -.39f, 0, 1, 0);
		mGR.mFont.Draw(gl, "info@hututugames.com", 0, -.49f, 0, 1, 0);
		mGR.mFont.Draw(gl, "www.hututugames.com", 0, -.59f, 0, 1, 0);
		
		DrawTransScal(gl, mGR.mTex_Red256,.61f,-.9f, Sel == 12 ? 1.1f : 1, Sel == 12 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Play More games",.60f,-.89f, 1, 1, 0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back", -.8f,-.9f, 1, 1,0);
	}
	boolean Handle_About(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(0.6f,-.9f, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =12;
		}
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.5f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 12:
				MoreGame();
				break;
			case 11:
				M.GameScreen = M.GAMESERIES;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel = 0;
			
		}
		return true;
	}
	/*****************************About End***************************************/
	/*****************************Buy Start***************************************/
	void Draw_Buy(GL10 gl){
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
		
		mGR.mFont.DrawS(gl, "Purchase Gold here !", -.65f, .56f, 1.3f, 0);
		DrawTexture(gl, mGR.mTex_Purchase, .43f,-.1f);
		
		for(int i =0;i<4;i++){
			DrawTexture(gl, mGR.mTex_Tran64, -.48f,.30f-.285f*i);
			mGR.mFont.Draw(gl, i+".99$",-.2f, .30f-.285f*i, 1, 0, 0);
			DrawTexture(gl, mGR.mTex_Red64, 0.08f,.30f-.285f*i);
			mGR.mFont.Draw(gl, "Buy",0.06f, .30f-.285f*i, 1, 0, 0);
		}
		mGR.mFont.Draw(gl,  (5000)+" Gold",-.48f, .30f-.285f*0, 1, 1, 2);
		mGR.mFont.Draw(gl, (10000)+" Gold",-.48f, .30f-.285f*1, 1, 1, 2);
		mGR.mFont.Draw(gl, (40000)+" Gold",-.48f, .30f-.285f*2, 1, 1, 2);
		mGR.mFont.Draw(gl, (90000)+" Gold",-.48f, .30f-.285f*3, 1, 1, 2);
		
		mGR.mTex_Red256.drawScal(gl, .51f,-.9f, 1.58f, 1);
		mGR.mFont.Draw(gl, "Any purchase removes Advertisement",.50f,-.89f, 0, 1, 0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back", -.8f,-.9f, 1, 1,0);
	}
	boolean Handle_Buy(MotionEvent event){
		Sel = 0;
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(0.08f,.30f-.285f*i, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel =i+1;
			}
		}
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				mGR.mInApp.onBuyGold10000(null);
				break;
			case 2:
				mGR.mInApp.onBuyGold20000(null);
				break;
			case 3:
				mGR.mInApp.onBuyGold30000(null);
				break;
			case 4:
				mGR.mInApp.onBuyGold40000(null);
				break;
			case 11:
				M.GameScreen = M.GAMESERIES;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel = 0;
			
		}
		return true;
	}
	/*****************************Buy End***************************************/
	
	
	/*****************************WinLoose Start***************************************/
	void Draw_WinLoose(GL10 gl){
		DrawTransScal(gl, mGR.mTex_AllBg[0], 0, 0, 1, 1f);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		
		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no], -.64f,.56f, 2, 0,0);
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
		DrawTexture(gl, mGR.mTex_Yelo256,.38f,.25f-0*.138f);
		mGR.mFont.Draw(gl, "Earnning", .15f, .25f-0*.138f, 0, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Whit256,.38f,.25f-1*.138f);
		mGR.mFont.Draw(gl, "Appearance fee:", .15f, .25f-1*.138f, 0, 0, 1);
		mGR.mFont.Draw(gl, ""+M.gameFee, .57f, .25f-1*.138f, 0, 0, 1);
		
		DrawTexture(gl, mGR.mTex_Whit256,.38f,.25f-2*.138f);
		mGR.mFont.Draw(gl, "Six Bonus:", .15f, .25f-2*.138f, 0, 0, 1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].s6*M.six, .57f, .25f-2*.138f, 0, 0, 1);
		
		DrawTexture(gl, mGR.mTex_Red256,.38f,.25f-3*.138f);
		mGR.mFont.Draw(gl, "Total Earning:", .15f, .25f-3*.138f, 0, 0, 0);
		mGR.mFont.Draw(gl, ""+((M.gameFee)+mGR.mBatTeam[mGR.Serise].s6*M.six), .57f, .25f-3*.138f, 0, 0, 0);
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);

		if(mGR.type == 0){
			int pos=0;
			for(int i=0;i<6;i++){
				if(mGR.mTeamInfo[mGR.Serise][i].point>mGR.mTeamInfo[mGR.Serise][mGR.mBatTeam[mGR.Serise].no].point
						&& mGR.mBatTeam[mGR.Serise].no !=i){
					pos++;
				}
			}
			DrawTexture(gl, mGR.mTex_Rank, -.18f,.05f);
			mGR.mFont.Draw(gl, (pos+1)+"", -.14f,.15f, 2, 0, 0);
	//		mGR.mTex_Red256.drawScal(gl,.5f,-.57f,.65f,1);
	//		mGR.mFont.Draw(gl, "Game saved", .39f,-.56f, 0, 0, 0);
			if(mGR.Year[mGR.Serise]<6)
				mGR.Pos[mGR.Serise][mGR.Year[mGR.Serise]] = (byte)(pos+1);
			
			if(mGR.round[mGR.Serise] <(mGR.mTeamInfo[ mGR.Serise].length-1)){
				if (mGR.mBatTeam[mGR.Serise].Run < mGR.mBalTeam[mGR.Serise].Run){
					mGR.mTex_Loose[1].drawSRGBT(gl,-.67f,-.32f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
					DrawTexture(gl, mGR.mTex_Loose[0],-.67f,-.32f);
					mGR.mFont.Draw(gl, "You Loose By", -.03f,-.35f, 2, 1, 0);
					mGR.mFont.Draw(gl, (-mGR.mBatTeam[mGR.Serise].Run + mGR.mBalTeam[mGR.Serise].Run)+" Run", -.03f,-.55f, 2, 1, 3);
				}else{
					mGR.mTex_Won[1].drawSRGBT(gl,-0.64f,  -0.28f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
					DrawTexture(gl, mGR.mTex_Won[0],-0.64f,  -0.28f);
					mGR.mFont.Draw(gl, "You Won", -.03f,-.35f, 2, 1, 0);
					mGR.mFont.Draw(gl, "Congratulation", -.03f,-.55f, 2, 1, 3);
				}
			}else{
					if (pos > 0){
						mGR.mTex_Loose[1].drawSRGBT(gl,-.67f,-.32f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
						DrawTexture(gl, mGR.mTex_Loose[0],-.67f,-.32f);
						mGR.mFont.Draw(gl, "You Lost Series", -.03f,-.35f, 2, 1, 0);
						mGR.mFont.Draw(gl, "Year-"+(M.StartYear+mGR.Year[mGR.Serise]), -.03f,-.55f, 2, 1, 3);
					}else{
						mGR.mTex_Won[1].drawSRGBT(gl,-0.64f,  -0.28f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
						DrawTexture(gl, mGR.mTex_Won[0],-0.64f,  -0.28f);
						mGR.mFont.Draw(gl, "You Won Series", -.03f,-.35f, 2, 1, 0);
						mGR.mFont.Draw(gl, "Year-"+(M.StartYear+mGR.Year[mGR.Serise]), -.03f,-.55f, 2, 1, 3);
					}
			}
		}else{
			if (mGR.mBatTeam[mGR.Serise].Run < mGR.mBalTeam[mGR.Serise].Run){
				mGR.mTex_Loose[1].drawSRGBT(gl,-.67f,-.32f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
				DrawTexture(gl, mGR.mTex_Loose[0],-.67f,-.32f);
				mGR.mFont.Draw(gl, "You Loose By", -.03f,-.35f, 2, 1, 0);
				mGR.mFont.Draw(gl, (-mGR.mBatTeam[mGR.Serise].Run + mGR.mBalTeam[mGR.Serise].Run)+" Run", -.03f,-.55f, 2, 1, 3);
				DrawTexture(gl, mGR.mTex_Rank, -.18f,.05f);
				mGR.mFont.Draw(gl, "2", -.14f,.15f, 2, 0, 0);
			}else{
				mGR.mTex_Won[1].drawSRGBT(gl,-0.64f,  -0.28f,1,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,1);
				DrawTexture(gl, mGR.mTex_Won[0],-0.64f,  -0.28f);
				mGR.mFont.Draw(gl, "You Won", -.03f,-.35f, 2, 1, 0);
				mGR.mFont.Draw(gl, "Congratulation", -.03f,-.55f, 2, 1, 3);
				DrawTexture(gl, mGR.mTex_Trophy, -.18f,.05f);
			}
		}
	}
	boolean Handle_WinLoose(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			if (mGR.type == 0) {
				if (mGR.round[mGR.Serise] == (mGR.mTeamInfo[mGR.Serise].length - 1)) {
					M.GameScreen = M.GAMESERIES;
					if (mGR.Year[mGR.Serise] < 4)
						mGR.Year[mGR.Serise]++;
					mGR.reset();
					mGR.round[mGR.Serise] = 0;
					getNextMatch();
				} else {
					getNextMatch();
					M.GameScreen = M.TEAMINFO;
					mGR.isFIxed = false;
					boss= -1.30f;
				}
			}else{
				M.GameScreen = M.GAMESERIES;
			}
			Sel = 0;
			Count2 = 67;
			M.sound1(R.drawable.ball_hit0);
		}
		return true;
	}
	/*****************************WinLoose End***************************************/
	/*****************************GamePlay Start***************************************/
	void winLoose(){
		M.stop();
		M.GameScreen =M.WINLOOSE;
		if(mGR.type == 0)
			TeamUpdate();
		else
			mGR.mGold+=((M.gameFee)+mGR.mBatTeam[mGR.Serise].s6*M.six);
		GameRenderer.mStart.ShowInterstitial();
		GameRenderer.mStart.Achivment();
	}
	void ScoreUpdate(int run) {
		if (mGR.mBalTeam[mGR.Serise].ball % 6 == 0) {
			for (int i = 0; i < mGR.Over.length; i++)
				mGR.Over[i] = -1;
		}
		mGR.Over[mGR.mBalTeam[mGR.Serise].ball % 6] = (byte) run;

		if (run < 7) {
			M.TOTAL += run;
			mGR.mBatTeam[mGR.Serise].update(run, true);
			mGR.mBalTeam[mGR.Serise].update(run, false);
			if (run % 2 != 0) {
				mGR.mBatTeam[mGR.Serise].isTwo = !mGR.mBatTeam[mGR.Serise].isTwo;
			}
			if (mGR.type == 0) {
				if (6 == run) {
					mGR.tS6[mGR.Serise]++;
					M.T_SIX++;
				}
				if (4 == run) {
					mGR.tS4[mGR.Serise]++;
					M.T_FOUR++;
				}
			}
		}else{
			int no = 0;
			if(mGR.mBall[mGR.Serise].Run == 7)
				no = 0;
			if(mGR.mBall[mGR.Serise].Run == 12)
				no = 1;
			if(mGR.mBall[mGR.Serise].Run > 7 && mGR.mBall[mGR.Serise].Run < 12)
				no = 2;
			
			if((mGR.mBatTeam[mGR.Serise].isTwo && mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p2].run == 0)||
					(!mGR.mBatTeam[mGR.Serise].isTwo && mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p1].run == 0)){
					mGR.Duck = 1;
			}
			
			mGR.mBatTeam[mGR.Serise].out(true, mGR.mBalTeam[mGR.Serise].isTwo?mGR.mBalTeam[mGR.Serise].p2:mGR.mBalTeam[mGR.Serise].p1, no);
			mGR.mBalTeam[mGR.Serise].out(false, mGR.mBatTeam[mGR.Serise].p1, 0);
		}

	}
	void gamelogic(){
		if(Counter %3==0){
			Count2++;
			mGR.mBatsman.hit++;
			if(Count2>70){
				Count2 =0;
			}
		}
		if(Counter%20==0)
			Count3++;
		if(Count2 == 19 && !mGR.mBall[mGR.Serise].isin()){
			mGR.mBall[mGR.Serise].set(0.05f,  0.34f,0);
			mGR.mBatsman.isClick = false;
		}
		if(mGR.mBatsman.hit == 3 && mGR.mBall[mGR.Serise].isin() && mGR.mBall[mGR.Serise].hit ==0){
			if(mGR.mBall[mGR.Serise].Run != 7){
				if ((mGR.mBatsman.img < 3 && (mGR.mBall[mGR.Serise].tye < 3 || mGR.mBall[mGR.Serise].tye == 5))||
						(mGR.mBatsman.img == 3 && (mGR.mBall[mGR.Serise].tye >= 3 && mGR.mBall[mGR.Serise].tye <= 5))||
						(mGR.mBall[mGR.Serise].tye == 4 || mGR.mBall[mGR.Serise].tye == 6))
						mGR.mBall[mGR.Serise].setHit(mGR.mBall[mGR.Serise].x,mGR.mBall[mGR.Serise].y, mGR.mBatsman.img);
			}
//			else 
//				mGR.mBall[mGR.Serise].Run = 7;
			System.out.print(mGR.mBall[mGR.Serise].Run+"          "+mGR.mBall[mGR.Serise].tye);
		}
		if (Count2 == 35 && Counter % 3 == 0) {
			ScoreUpdate(mGR.mBall[mGR.Serise].Run == 5 ? 6 : mGR.mBall[mGR.Serise].Run);
		}
		if (Count2 == 32 && Counter % 3 == 0 && mGR.mBall[mGR.Serise].Run == 7) {
			mGR.mGuli[0].set(-.01f, -.59f);
			mGR.mGuli[1].set(0.01f, -.59f);
			M.sound6(R.drawable.stumping);
		}
		if (Count2 == 38 && Counter % 3 == 0 && (mGR.mBall[mGR.Serise].Run >= 8 || mGR.mBall[mGR.Serise].Run == 9)) {
			mGR.mBall[mGR.Serise].z =0;
		}
		if (Count2 == 40 && Counter % 3 == 0 && mGR.mBall[mGR.Serise].Run > 6){
			/*if(mGR.Duck == 1){
				M.sound3(R.drawable.duckling);
				mGR.Duck = 20;
			}else*/{
				if(M.mRand.nextBoolean()){
					M.sound7(R.drawable.out0);
				}else{
					M.sound8(R.drawable.out1);
				}
			}
		}
		if (Count2 == 43 && Counter % 3 == 0 && (mGR.mBall[mGR.Serise].Run == 4||mGR.mBall[mGR.Serise].Run == 5)){
			M.sound5(R.drawable.noise);
		}
		if(mGR.mBalTeam[mGR.Serise].ball%6 == 0 && Count2 == 65){
			M.GameScreen =M.SCOREBOARD;
			if (mGR.mBalTeam[mGR.Serise].ball % 12 == 0)
				GameRenderer.mStart.ShowInterstitial();
			mGR.mBatTeam[mGR.Serise].isTwo = !mGR.mBatTeam[mGR.Serise].isTwo;
			
			int a =0;
			for(a =0;a<mGR.Over.length;a++){
				if (mGR.Over[a] > 0 && mGR.Over[a] < 7)
					break;
			}

			if(a == 6){
				if(mGR.mBalTeam[mGR.Serise].isTwo)
					mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p2].HowOut++;
				else
					mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p1].HowOut++;
			}
			mGR.mBalTeam[mGR.Serise].isTwo = !mGR.mBalTeam[mGR.Serise].isTwo;
		}
		if(mGR.mBall[mGR.Serise].Run >= 7 && Count2 == 65){
			M.GameScreen =M.SCOREBOARD;
			mGR.mBall[mGR.Serise].Run =0;
			if(mGR.mBatTeam[mGR.Serise].falWicket >9)
				winLoose();
				
		}
		if ((mGR.mBalTeam[mGR.Serise].ball >= M.tBall ||
				mGR.mBatTeam[mGR.Serise].Run > mGR.mBalTeam[mGR.Serise].Run) && Count2 == 64) {
			winLoose();
		}
		mGR.mBall[mGR.Serise].update();
	}
	int cou=0;
	void Draw_Fileder(GL10 gl,float x,float y,float z){
		
		if(Counter%4 ==0 && mGR.mBall[mGR.Serise].pos<2){
			if(mGR.mBall[mGR.Serise].pos==0)
				cou =0;
			mGR.mBall[mGR.Serise].pos++;
		}
		if(Counter%8 ==0 && cou <4)
			cou ++;
//		if(Counter% 50 ==0)
//			mGR.mBall[mGR.Serise].pos =0;
		DrawTextureS(gl, mGR.mTex_BSedo	,x+.01f,y-z*.25f,z);
		mGR.mTex_FAnim[1][mGR.mBall[mGR.Serise].pos].drawSRGBT(gl		,x,y+((cou%4)*z*.05f),z,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
		DrawTextureS(gl, mGR.mTex_FAnim[0][mGR.mBall[mGR.Serise].pos]	,x,y+((cou%4)*z*.05f),z);
		
	}
	void Draw_GamePlay(GL10 gl){
		DrawTexture(gl, mGR.mTex_Ground, 0, 0);
		if(Count2 > 40 && Count2 < 50){
			if(mGR.mBall[mGR.Serise].Run>6)
				DrawTexture(gl, mGR.mTex_Ampaire[6],0,.22f);
			else if(mGR.mBall[mGR.Serise].Run==4)
				DrawTexture(gl, mGR.mTex_Ampaire[1+(Count2%3)],0,.22f);
			else if(mGR.mBall[mGR.Serise].Run==5)
				DrawTexture(gl, mGR.mTex_Ampaire[5],0,.22f);
			else
				DrawTexture(gl, mGR.mTex_Ampaire[0],0,.22f);
		}
		else{
			DrawTexture(gl, mGR.mTex_Ampaire[0],0,.22f);
		}
		mGR.mTex_Runner[1].drawSRGBT(gl,-.07f,.165f,1,
						(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0]) / 255f,
						(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1]) / 255f,
						(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2]) / 255f,
						.7f);
		DrawTextureS(gl, mGR.mTex_Runner[0],-.07f,.155f,1);
		
		
		float blr = 1;
		DrawTextureS(gl, mGR.mTex_Stump[0], .045f, .13f,.22f);
		if(Count2 > 50){
			mGR.mTex_Baler[1][0].drawSRGBT(gl,.06f, .10f,blr,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Baler[0][0],.06f, .10f,blr);
		}
		else if(Count2 >= 32 && Count2 <= 50){
			if(mGR.mBall[mGR.Serise].Run<7 || Count2 < 39){
				mGR.mTex_Baler[1][31].drawSRGBT(gl,.06f, .10f,blr,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
				DrawTextureS(gl, mGR.mTex_Baler[0][31],.06f, .10f,blr);
			}else{
				if(mGR.mBall[mGR.Serise].Run%2==0){
					mGR.mTex_BAnim[1][Count2%4].drawSRGBT(gl,.06f, .10f,blr,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
					DrawTextureS(gl, mGR.mTex_BAnim[0][Count2%4],.06f, .10f,blr);
				}else{
					mGR.mTex_BAnim[1][(Count2%2)+4].drawSRGBT(gl,.06f, .10f,blr,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
					DrawTextureS(gl, mGR.mTex_BAnim[0][(Count2%2)+4],.06f, .10f,blr);
				}
			}
		}
		else{
			mGR.mTex_Baler[1][Count2%mGR.mTex_Baler[1].length].drawSRGBT(gl,.06f, .10f,blr,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Baler[0][Count2%mGR.mTex_Baler[1].length],.06f, .10f,blr);
		}
		
		if(mGR.mBall[mGR.Serise].isin() && mGR.mBall[mGR.Serise].hit < 2){
			
			if(mGR.mBall[mGR.Serise].hit == 1 && mGR.mBall[mGR.Serise].Run <4 && mGR.mBall[mGR.Serise].Run >0){
//				DrawTextureS(gl, mGR.mTex_BSedo, mGR.mBall[mGR.Serise].x,  mGR.mBall[mGR.Serise].y-.01f,mGR.mBall[mGR.Serise].z);
			}
			else
				DrawTextureS(gl, mGR.mTex_BSedo, mGR.mBall[mGR.Serise].x,  mGR.mBall[mGR.Serise].sedo,mGR.mBall[mGR.Serise].z);
			DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mBall[mGR.Serise].x,  mGR.mBall[mGR.Serise].y,mGR.mBall[mGR.Serise].z);
		}
		
		float fld = .6f;
		if(mGR.mBall[mGR.Serise].Run > 6 && Count2 >35){
			if(mGR.mBall[mGR.Serise].Run == 8 && Count2 >35){
				mGR.mTex_Filder[1][4].drawSRGBT(gl		,0.80f,.18f,fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
				DrawTextureS(gl, mGR.mTex_Filder[0][4]	,0.80f,.18f,fld);
			}else{
				Draw_Fileder(gl, 0.80f,.18f,fld);
			}
		}else{
			mGR.mTex_Filder[1][0].drawSRGBT(gl,.80f	,0.18f,fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Filder[0][0]	,0.80f,.18f,fld);
		}
		
		if(mGR.mBall[mGR.Serise].Run > 6 && Count2 >35){
			if(mGR.mBall[mGR.Serise].Run == 9 && Count2 >35){
			mGR.mTex_Filder[1][1].drawSRGBT(gl		,-.80f,.18f,fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Filder[0][1]	,-.80f,.18f,fld);
			}else{
				Draw_Fileder(gl, -.80f,.18f,fld);
			}
		}else{
			mGR.mTex_Filder[1][0].drawSRGBT(gl		,-.80f,.18f,fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Filder[0][0]	,-.80f,.18f,fld);
		}
		fld = .8f;
		
		if(mGR.mBall[mGR.Serise].Run > 6 && Count2 >35){
			if(mGR.mBall[mGR.Serise].Run == 10 && Count2 >35){
				mGR.mTex_Filder[1][2].drawSRGBT(gl,-.26f,.25f,.51f*fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
				DrawTextureS(gl, mGR.mTex_Filder[0][2],-.26f,.25f,.51f*fld);
			}else{
				Draw_Fileder(gl, -.26f,.25f,.51f*fld);
			}
		}else{
			mGR.mTex_Filder[1][0].drawSRGBT(gl,-.26f,.25f,.51f*fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Filder[0][0],-.26f,.25f,.51f*fld);
		}
		if(mGR.mBall[mGR.Serise].Run > 6 && Count2 >35){
			if(mGR.mBall[mGR.Serise].Run == 11 && Count2 >35){
				mGR.mTex_Filder[1][3].drawSRGBT(gl,0.26f,.25f,.51f*fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
				DrawTextureS(gl, mGR.mTex_Filder[0][3],0.26f,.25f,.51f*fld);
		}else{
			Draw_Fileder(gl, 0.26f,.25f,.51f*fld);
		}
		}else{
			mGR.mTex_Filder[1][0].drawSRGBT(gl,0.26f,.25f,.51f*fld,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][2])/255f,1);
			DrawTextureS(gl, mGR.mTex_Filder[0][0],0.26f,.25f,.51f*fld);
		}
		float bats =1.12f;
		if(mGR.mBatsman.hit < mGR.mTex_Batman[mGR.mBatsman.img][1].length){
			mGR.mTex_Batman[mGR.mBatsman.img][1][mGR.mBatsman.hit].drawSRGBT(gl,0.04f,  -0.28f,bats,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,.7f);
			DrawTransScal(gl, mGR.mTex_Batman[mGR.mBatsman.img][0][mGR.mBatsman.hit],0.04f,  -0.28f,bats,.7f);
		}else{
			if(mGR.mBall[mGR.Serise].Run >= 7 && Count2 >30){
				mGR.mTex_ComnBat[1][1].drawSRGBT(gl,0.04f,  -0.28f,bats,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,.7f);
				DrawTransScal(gl, mGR.mTex_ComnBat[0][1],0.04f,  -0.28f,bats,.7f);
			}else{
				mGR.mTex_ComnBat[1][Count3%2].drawSRGBT(gl,0.04f,  -0.28f,bats,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][1])/255f,(M.RGB[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][2])/255f,.7f);
				DrawTransScal(gl, mGR.mTex_ComnBat[0][Count3%2],0.04f,  -0.28f,bats,.7f);
			}
			
		}
		if(mGR.mBall[mGR.Serise].Run == 7 && Count2 >32){
			DrawTexture(gl, mGR.mTex_Stump[1], 0, -.76f);
			DrawTexture(gl, mGR.mTex_Bail, mGR.mGuli[0].update(),mGR.mGuli[0].y);
			DrawTexture(gl, mGR.mTex_Bail, mGR.mGuli[1].update(),mGR.mGuli[1].y);
		}
		else{
			DrawTexture(gl, mGR.mTex_Stump[0], 0, -.76f);
			
		}
		if(mGR.mBall[mGR.Serise].isin() && mGR.mBall[mGR.Serise].hit == 2){
			DrawTextureS(gl, mGR.mTex_BSedo, mGR.mBall[mGR.Serise].x,  mGR.mBall[mGR.Serise].sedo,mGR.mBall[mGR.Serise].z);
			DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mBall[mGR.Serise].x,  mGR.mBall[mGR.Serise].y,mGR.mBall[mGR.Serise].z);
		}
		if(Counter%80 <10)
			DrawTexture(gl, mGR.mTex_Spark, spx, spy);
		if(Counter%80 ==0){
			spx =(M.mRand.nextInt(200)-100f)*.01f;
			spy =(M.mRand.nextInt(40)+28f)*.01f;
		}
		
		DrawTransScal(gl, mGR.mTex_ShotBtn[0], -.7f, -.76f, Sel == 1 ? 1.1f : 1, Sel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_ShotBtn[1], 0.7f, -.76f, Sel == 2 ? 1.1f : 1, Sel == 2 ? 0.5f : 1);
//		DrawTexture(gl, mGR.mTex_ShotBtn[0], -.7f, -.76f);
//		DrawTexture(gl, mGR.mTex_ShotBtn[1], 0.7f, -.76f);
		float m1 = -.10f;
		float m2 = 0.25f;
		
		DrawTexture(gl, mGR.mTex_Yelo256,m1-.600f,.940f);//Run
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no].toUpperCase()
				.substring(0, 3)
				+ " "
				+ mGR.mBatTeam[mGR.Serise].Run
				+ "/"
				+ mGR.mBatTeam[mGR.Serise].falWicket
				+ "  ["
				+ M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no].toUpperCase().substring(0, 3)
				+ " "
				+ mGR.mBalTeam[mGR.Serise].Run + "]", m1-.620f, .943f, 0, 1, 0);
		DrawTexture(gl, mGR.mTex_Yelo256,m1-.600f,.807f);//Current RR
		float rr = 0;
		if((Counter %500)>250)
		{
			if(mGR.mBatTeam[mGR.Serise].Run>0 && mGR.mBatTeam[mGR.Serise].ball>0)
				rr = (mGR.mBatTeam[mGR.Serise].Run/((mGR.mBatTeam[mGR.Serise].ball/6)+((mGR.mBatTeam[mGR.Serise].ball%6)/6f)));
			mGR.mFont.Draw(gl, "Current RR " + mGR.df.format(rr), m1-.620f , .805f, 0, 1, 0);
		}
		else{
			if((mGR.mBalTeam[mGR.Serise].Run-mGR.mBatTeam[mGR.Serise].Run)>0 && M.tBall >mGR.mBatTeam[mGR.Serise].ball)
				rr = ((mGR.mBalTeam[mGR.Serise].Run-mGR.mBatTeam[mGR.Serise].Run)/(((M.tBall-mGR.mBatTeam[mGR.Serise].ball)/6)+(((M.tBall-mGR.mBatTeam[mGR.Serise].ball)%6)/6f)));
			mGR.mFont.Draw(gl, "Required RR " + mGR.df.format(rr), m1-.620f, .805f, 0, 1, 0);
		}
		
		if(!mGR.mBatTeam[mGR.Serise].isTwo){
			DrawTexture(gl, mGR.mTex_Whit32	  ,m1-.864f,.671f);
			DrawTexture(gl, mGR.mTex_Bat32	  ,m1-.864f,.671f);
		}
		else{
			DrawTexture(gl, mGR.mTex_Whit32	  ,m1-.864f,.533f);
			DrawTexture(gl, mGR.mTex_Bat32	  ,m1-.864f,.533f);
		}
		
//		DrawTexture(gl, mGR.mTex_Whit160  ,m1-.638f,.671f);
		mGR.mTex_Whit256.drawScal(gl,m1-.638f,.671f, .63f, 1);//yogesh
		DrawTexture(gl, mGR.mTex_Whit64	  ,m1-.375f,.671f);//Player 1
		mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p1], m1-.69f,.671f, 0, 1, 1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p1].run, m1-.38f,.671f, 0, 0, 1);
		
		
//		DrawTexture(gl, mGR.mTex_Whit160  ,m1-.638f,.533f);
		mGR.mTex_Whit256.drawScal(gl,m1-.638f,.533f, .63f, 1);//yogesh
		DrawTexture(gl, mGR.mTex_Whit64	  ,m1-.375f,.533f);//Player 2
		mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].p2], m1-.69f,.536f, 0, 1, 1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[mGR.mBatTeam[mGR.Serise].p2].run	 , m1-.38f,.536f, 0, 0, 1);
		
		DrawTexture(gl, mGR.mTex_Yelo64,m1-.218f,.940f);//6's
		DrawTexture(gl, mGR.mTex_Yelo64,m1-.06f,.940f);//4's
		DrawTexture(gl, mGR.mTex_Whit64,m1-.218f,.807f);//6's
		DrawTexture(gl, mGR.mTex_Whit64,m1-.06f,.807f);//4's
		mGR.mFont.Draw(gl, "6's", m1-.218f,.940f, 1, 1, 0);
		mGR.mFont.Draw(gl, "4's", m1-.06f,.940f, 1, 1, 0);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].s6	 , m1-.218f,.807f, 1, 0, 1);
		mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].s4	 , m1-.06f,.807f, 1, 0, 1);
		
		
		DrawTexture(gl, mGR.mTex_Yelo256,m2+.46f,.940f);//Over
		mGR.mFont.Draw(gl, "Over " + (mGR.mBalTeam[mGR.Serise].ball / 6) + "."
				+ (mGR.mBalTeam[mGR.Serise].ball % 6) + "   [" + (M.tBall / 6)
				+ "]", m2 + .46f, .943f, 0, 1, 0);
		
		DrawTexture(gl, mGR.mTex_Whit32	  ,m2+.340f,.807f);
		DrawTexture(gl, mGR.mTex_Bal32	  ,m2+.340f,.807f);
//		DrawTexture(gl, mGR.mTex_Whit160  ,m2+.567f,.807f);
		mGR.mTex_Whit256.drawScal(gl,m2+.567f,.807f, .63f, 1);//yogesh
		if(!mGR.mBalTeam[mGR.Serise].isTwo){
			mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p1],m2+0.44f,.805f, 0, 0, 1);
		}
		else{
			mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBalTeam[mGR.Serise].p2],m2+0.44f,.805f, 0, 0, 1);
		}
		
		for(int i =0;i<6;i++){
			DrawTexture(gl, mGR.mTex_Whit32  ,m2+.340f+i*.077f,.671f);
			if (mGR.Over[i] >= 7)
				mGR.mFont.Draw(gl, "W" ,m2+ .355f + i * .077f, .671f,0, 0, 1);
			else if (mGR.Over[i] >= 0)
				mGR.mFont.Draw(gl, "" + mGR.Over[i], m2+.365f + i * .077f, .671f,0, 0, 1);
			
		}
		if (mGR.mBall[mGR.Serise].Run > 0 &&  mGR.mBall[mGR.Serise].RunTran < 50 && Count2 > 47) {
			byte num = 50;
			mGR.mTex_Whit64x64.drawSRGBT(gl,0,.7f,2,0,0,0,.4f);
			if(mGR.mBall[mGR.Serise].Run > 6){
				if(mGR.mBall[mGR.Serise].RunTran<10)
					mGR.mTex_Run[5].drawSRGBT(gl,0,.7f,1,(M.RGB[0][0][0])/255f,(M.RGB[0][0][1])/255f,(M.RGB[0][0][2])/255f,(mGR.mBall[mGR.Serise].RunTran*.1f));
				else if(mGR.mBall[mGR.Serise].RunTran>=10 && mGR.mBall[mGR.Serise].RunTran < num-10)
					mGR.mTex_Run[5].drawSRGBT(gl,0,.7f,1,(M.RGB[0][0][0])/255f,(M.RGB[0][0][1])/255f,(M.RGB[0][0][2])/255f,1);
				else //if(mGR.mBall[mGR.Serise].RunTran>=num-10)
					mGR.mTex_Run[5].drawSRGBT(gl,0,.7f,1,(M.RGB[0][0][0])/255f,(M.RGB[0][0][1])/255f,(M.RGB[0][0][2])/255f,((num-mGR.mBall[mGR.Serise].RunTran)*.1f));
			}else if(mGR.mBall[mGR.Serise].Run < 6){
				if(mGR.mBall[mGR.Serise].RunTran<10)
					mGR.mTex_Run[mGR.mBall[mGR.Serise].Run-1].drawSRGBT(gl,0,.7f,1,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][0])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][1])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][2])/255f,(mGR.mBall[mGR.Serise].RunTran*.1f));
				else if(mGR.mBall[mGR.Serise].RunTran>=10 && mGR.mBall[mGR.Serise].RunTran < num-10)
					mGR.mTex_Run[mGR.mBall[mGR.Serise].Run-1].drawSRGBT(gl,0,.7f,1,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][0])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][1])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][2])/255f,1);
				else //if(mGR.mBall[mGR.Serise].RunTran>=num-10)
					mGR.mTex_Run[mGR.mBall[mGR.Serise].Run-1].drawSRGBT(gl,0,.7f,1,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][0])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][1])/255f,(M.RGB[mGR.Serise][mGR.mBall[mGR.Serise].Run][2])/255f,((num-mGR.mBall[mGR.Serise].RunTran)*.1f));
			}
			mGR.mBall[mGR.Serise].RunTran++;
		}
		if(mGR.Duck>9 && mGR.Duck<120)
		{
			DrawTexture(gl, mGR.mTex_Duck,mGR.Duck*.01f,-.807f);
			mGR.Duck+=2;
		}
		if(mGR.popUp == 0)
			gamelogic();
		else{
			popup(gl);
		}
		if(Counter%600 ==0){
			if(M.mRand.nextBoolean()){
				M.sound9(R.drawable.whistle0);
			}else{
				M.sound10(R.drawable.whistle1);
			}
		}
	}
	boolean Handle_GamePlay(MotionEvent event){
		Sel = 0;
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.popUp == 0 && Count2 > 21 && !mGR.mBatsman.isClick){
			if(CirCir(-.7f, -.76f, mGR.mTex_ShotBtn[0].Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mBatsman.set(M.mRand.nextInt(5)==0?4:3,0);
				Sel =1;
			}
			if(CirCir(0.7f, -.76f, mGR.mTex_ShotBtn[0].Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mBatsman.set(M.mRand.nextInt(10)==0?4:M.mRand.nextInt(3),0);
				Sel =2;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			if( mGR.popUp > 0)
				mGR.popUp = 0;
			if(Sel > 0)
				Sel =0;
			
		}
		return true;
	}
	/*****************************GamePlay  End***************************************/
	
	/*****************************ScoreBoard Start***************************************/
	void Draw_ScoreBoard(GL10 gl) {
		final float alx = -.02f;
		DrawTransScal(gl, mGR.mTex_Ground, 0, 0, 1, 1f);
		mGR.mTex_Ground.drawSRGBT(gl,0,0,1,0,0,0,.4f);
		DrawTexture(gl, mGR.mTex_White200x50,  -0.64f,0.92f);
		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no].toUpperCase(), -0.64f,0.92f, 1, 1,1);
		
		
		mGR.mFont.Draw(gl, "Bowled off", alx-.206f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "Runs",  alx+.061f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "Balls", alx+.213f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "S/R",  alx+.365f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "4's",  alx+.517f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "6's",  alx+.669f,.86f, 0, 1,0);
		mGR.mFont.Draw(gl, "FOW",  alx+.822f,.86f, 0, 1,0);
		
		for(int i=0;i<11;i++){
			DrawTexture(gl, mGR.mTex_Yelo40x22,  alx-.810f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo160x22, alx-.583f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo160x22, alx-.206f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.057f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.208f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.359f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.510f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.662f,.76f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.813f,.76f-.092f*i);
			
			mGR.mFont.Draw(gl, ""+(i+1),  alx-.810f,.76f-.092f*i, 0, 1,0);
			mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i], alx-.12f-.583f,.76f-.092f*i, 0, 0,0);
			
			
			if(mGR.mBatTeam[mGR.Serise].mPly[i].Ball>-1){
				if(mGR.mBatTeam[mGR.Serise].mPly[i].OutBy==-1){
					DrawTexture(gl, mGR.mTex_Whit40x22,  alx-.810f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit160x22, alx-.583f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit160x22, alx-.206f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.057f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.208f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.359f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.510f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.662f,.76f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.813f,.76f-.092f*i);
					
					mGR.mFont.Draw(gl, "not out", alx-.206f,.76f-.092f*i, 0, 1,1);
					
					
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].run,  alx+.061f,.76f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].Ball,  alx+.213f,.76f-.092f*i, 0, 1,1);
					if(mGR.mBatTeam[mGR.Serise].mPly[i].Ball>0)
						mGR.mFont.Draw(gl, ""+((mGR.mBatTeam[mGR.Serise].mPly[i].run*100)/mGR.mBatTeam[mGR.Serise].mPly[i].Ball),  alx+.365f,.76f-.092f*i, 0, 1,1);
					else
						mGR.mFont.Draw(gl, "0",  alx+.365f,.76f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].s4,  alx+.517f,.76f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].s6,  alx+.669f,.76f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+(i+1),  alx-.810f,.76f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i], alx-.12f-.583f,.76f-.092f*i, 0, 0,1);
				}
				else{
					
					String str = M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][mGR.mBatTeam[mGR.Serise].mPly[i].OutBy];
					
					if(mGR.mBatTeam[mGR.Serise].mPly[i].HowOut ==0)// bold //1 LBW //caught)
						mGR.mFont.Draw(gl, "(B)"+str, alx-.206f,.76f-.092f*i, 0, 1,0);
					else if(mGR.mBatTeam[mGR.Serise].mPly[i].HowOut ==1)// bold //1 LBW //caught)
						mGR.mFont.Draw(gl, "(LBW)"+str, alx-.206f,.76f-.092f*i, 0, 1,0);
					else //if(mGR.mBatTeam[mGR.Serise].mPly[i].HowOut ==0)// bold //1 LBW //caught)
						mGR.mFont.Draw(gl, "(C)"+str, alx-.206f,.76f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].fallOff,  alx+.822f,.76f-.092f*i, 0, 1,0);
					
					
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].run,  alx+.061f,.76f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].Ball,  alx+.213f,.76f-.092f*i, 0, 1,0);
					if(mGR.mBatTeam[mGR.Serise].mPly[i].Ball>0)
						mGR.mFont.Draw(gl, ""+((mGR.mBatTeam[mGR.Serise].mPly[i].run*100)/mGR.mBatTeam[mGR.Serise].mPly[i].Ball),  alx+.365f,.76f-.092f*i, 0, 1,0);
					else
						mGR.mFont.Draw(gl, "0",  alx+.365f,.76f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].s4,  alx+.517f,.76f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+mGR.mBatTeam[mGR.Serise].mPly[i].s6,  alx+.669f,.76f-.092f*i, 0, 1,0);
				}
				
					
			}
//			3010510636
		}
		
		DrawTexture(gl, mGR.mTex_White200x50,  -0.64f,-.43f);
		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no].toUpperCase(), -0.64f,-.43f, 1, 1,1);
		
		mGR.mTex_Whit64x64.drawScal(gl,  -0.30f,  -0.39f,1.24f,1);
		mGR.mFont.Draw(gl, "Target", -0.295f,  -0.325f, 0, 1,1);
		mGR.mFont.Draw(gl, mGR.mBalTeam[mGR.Serise].Run+"", -0.295f,  -0.45f, 1, 1,0);
		
		
		mGR.mTex_Red256.drawScal(gl, 0.17f, -0.32f, 1.2f, 1);
		mGR.mFont.Draw(gl, "Overs " + (mGR.mBalTeam[mGR.Serise].ball / 6) + "."
				+ (mGR.mBalTeam[mGR.Serise].ball % 6) + "[" + M.tBall / 6
				+ "] " + mGR.mBatTeam[mGR.Serise].Run + "/"
				+ mGR.mBalTeam[mGR.Serise].falWicket, 0.15f, -0.32f, 1, 1, 2);

		
		DrawTransScal(gl, mGR.mTex_Blu128,.73f,-.32f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .73f,-.32f, 1, 1,0);
		
		mGR.mFont.Draw(gl, "Overs",  alx-.091f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "Maid.",  alx+.061f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "Runs",  alx+.213f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "Wicket",  alx+.365f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "4's",  alx+.517f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "6's",  alx+.669f,-.46f, 0, 1,0);
		mGR.mFont.Draw(gl, "Econ.",  alx+.822f,-.46f, 0, 1,0);
		
		for(int i=0;i<5;i++){
			DrawTexture(gl, mGR.mTex_Yelo160x22, alx-.660f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx-.091f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.061f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.213f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.365f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.517f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.669f,-.58f-.092f*i);
			DrawTexture(gl, mGR.mTex_Yelo64x22,  alx+.822f,-.58f-.092f*i);
			
			if(mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball>-1){
				if(mGR.mBalTeam[mGR.Serise].p1 == 6+i||mGR.mBalTeam[mGR.Serise].p2 == 6+i){
					DrawTexture(gl, mGR.mTex_Whit160x22, alx-.660f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx-.091f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.061f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.213f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.365f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.517f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.669f,-.58f-.092f*i);
					DrawTexture(gl, mGR.mTex_Whit64x22,  alx+.822f,-.58f-.092f*i);
					mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][6+i], alx-.660f-.12f,-.58f-.092f*i, 0, 0,1);
					mGR.mFont.Draw(gl, (mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball/6)+"."+(mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball%6),  alx-.091f,-.58f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+(mGR.mBalTeam[mGR.Serise].mPly[6+i].HowOut+1),  alx+.061f,-.58f-.092f*i, 0, 1,1);//midin
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].run,  alx+.213f,-.58f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+(mGR.mBalTeam[mGR.Serise].mPly[6+i].OutBy+1),  alx+.365f,-.58f-.092f*i, 0, 1,1);//wicket
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].s4,  alx+.517f,-.58f-.092f*i, 0, 1,1);
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].s6,  alx+.669f,-.58f-.092f*i, 0, 1,1);
					if(mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball>0){
						float rr = (mGR.mBalTeam[mGR.Serise].mPly[6+i].run/((mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball/6)+((mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball%6)/6f)));
						mGR.mFont.Draw(gl, ""+mGR.df.format(rr),  alx+.822f,-.58f-.092f*i, 0, 1,1);
					}
				}else{
					mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBalTeam[mGR.Serise].no][6+i], alx-.660f-.12f,-.58f-.092f*i, 0, 0,0);
					mGR.mFont.Draw(gl, (mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball/6)+"."+(mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball%6),  alx-.091f,-.58f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+(mGR.mBalTeam[mGR.Serise].mPly[6+i].HowOut+1),  alx+.061f,-.58f-.092f*i, 0, 1,0);//midin
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].run,  alx+.213f,-.58f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+(mGR.mBalTeam[mGR.Serise].mPly[6+i].OutBy+1),  alx+.365f,-.58f-.092f*i, 0, 1,0);//wicket
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].s4,  alx+.517f,-.58f-.092f*i, 0, 1,0);
					mGR.mFont.Draw(gl, ""+mGR.mBalTeam[mGR.Serise].mPly[6+i].s6,  alx+.669f,-.58f-.092f*i, 0, 1,0);
					if(mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball>0){
						float rr = (mGR.mBalTeam[mGR.Serise].mPly[6+i].run/((mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball/6)+((mGR.mBalTeam[mGR.Serise].mPly[6+i].Ball%6)/6f)));
						mGR.mFont.Draw(gl, ""+mGR.df.format(rr),  alx+.822f,-.58f-.092f*i, 0, 1,0);
					}
				}
			}
		}
		
	}
	boolean Handle_ScoreBoard(MotionEvent event) {
		Sel =0;
		
		if(CircRectsOverlap(.73f,-.32f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 11:
				
				if(M.tBall<=mGR.mBalTeam[mGR.Serise].ball){
//					TeamUpdate();
					M.GameScreen = M.TEAMINFO;
					mGR.isFIxed = false;
					boss= -1.30f;
				}
				else{
				
					mGR.popUp = 2;
					M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.game_play);
					Count2 = 67;
					if(mGR.mBalTeam[mGR.Serise].ball%6 == 0){
						if(mGR.mBalTeam[mGR.Serise].ball%12 == 0)
							GameRenderer.mStart.load();
						mGR.popUp = 1;
						for(int i =0;i<mGR.Over.length;i++)
							mGR.Over[i] = -1;
					}
					if((mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p1].Ball) >= (M.tBall/5)){
						for(int i=8;i<11;i++){
							if(mGR.mBalTeam[mGR.Serise].mPly[i].Ball<0){
								mGR.mBalTeam[mGR.Serise].p1 = i;
								mGR.mBalTeam[mGR.Serise].mPly[i].Ball = 0;
								break;
							}
						}
					}
					if((mGR.mBalTeam[mGR.Serise].mPly[mGR.mBalTeam[mGR.Serise].p2].Ball) >= (M.tBall/5)){
						for(int i=8;i<11;i++){
							if(mGR.mBalTeam[mGR.Serise].mPly[i].Ball<0){
								mGR.mBalTeam[mGR.Serise].p2 = i;
								mGR.mBalTeam[mGR.Serise].mPly[i].Ball = 0;
								break;
							}
						}
					}
				}
				break;
			
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	int getNextMatch(){
		int p = mGR.mBatTeam[mGR.Serise].no;
		for (int k = mGR.round[mGR.Serise]; k < mGR.mTeamInfo[mGR.Serise].length - 1; k++) 
		{
			for (int i = k, m = 0; i <  mGR.mTeamInfo[ mGR.Serise].length; i++, m++) {
				for (int j = i + 1; j <  mGR.mTeamInfo[ mGR.Serise].length&& j < i + 2; j++) {
					if (m == 0 && j != p) {
//						System.out.print("[" + p + "," + j + "]");
						mGR.gameReset(10, p, j);
						return k;
					}
					if (p == m && j != p && j != 0){
//						System.out.print("[" + 0 + "," + j + "]");
						continue;
					}
					if (p == j && j != m && m != 0){
//						System.out.print("[" + m + "," + 0 + "]");
						continue;
					}
					if (j == p && m != p){
						mGR.gameReset(10, p, m);
						return k;
					}
					
				}
			}
		}
		return -1;
	}
	void TeamUpdate(){
		mGR.mGold+=((M.gameFee)+mGR.mBatTeam[mGR.Serise].s6*M.six);
		int p = mGR.mBatTeam[mGR.Serise].no;
		mGR.round[mGR.Serise]++;
		for (int i = mGR.round[mGR.Serise]-1, m = 0; i <  mGR.mTeamInfo[ mGR.Serise].length; i++, m++) {
			for (int j = i + 1; j <  mGR.mTeamInfo[ mGR.Serise].length&& j < i + 2; j++) {
				if (m == 0 && j != p) {
					if(mGR.mBatTeam[mGR.Serise].Run > mGR.mBalTeam[mGR.Serise].Run){
						mGR.mTeamInfo[mGR.Serise][p].result(2);
						mGR.mTeamInfo[mGR.Serise][j].result(0);
					}else if(mGR.mBatTeam[mGR.Serise].Run == mGR.mBalTeam[mGR.Serise].Run){
						mGR.mTeamInfo[mGR.Serise][p].result(1);
						mGR.mTeamInfo[mGR.Serise][j].result(1);
					}
					else {
						mGR.mTeamInfo[mGR.Serise][p].result(0);
						mGR.mTeamInfo[mGR.Serise][j].result(2);
					}
//					System.out.print("[" + p + "," + j + "]");
					continue;
				}
				if (p == m && j != p && j != 0){
					if(M.mRand.nextBoolean()){
						mGR.mTeamInfo[mGR.Serise][0].result(0);
						mGR.mTeamInfo[mGR.Serise][j].result(2);
					}else{
						mGR.mTeamInfo[mGR.Serise][0].result(2);
						mGR.mTeamInfo[mGR.Serise][j].result(1);
					}
//					System.out.print("[" + 0 + "," + j + "]");
					continue;
				}
				if (p == j && j != m && m != 0){
					if(M.mRand.nextBoolean()){
						mGR.mTeamInfo[mGR.Serise][m].result(0);
						mGR.mTeamInfo[mGR.Serise][0].result(2);
					}else{
						mGR.mTeamInfo[mGR.Serise][m].result(2);
						mGR.mTeamInfo[mGR.Serise][0].result(1);
					}
//					System.out.print("[" + m + "," + 0 + "]");
					continue;
				}
				if (j == p && m != p){

					if(mGR.mBatTeam[mGR.Serise].Run > mGR.mBalTeam[mGR.Serise].Run){
						mGR.mTeamInfo[mGR.Serise][p].result(2);
						mGR.mTeamInfo[mGR.Serise][m].result(0);
					}else if(mGR.mBatTeam[mGR.Serise].Run == mGR.mBalTeam[mGR.Serise].Run){
						mGR.mTeamInfo[mGR.Serise][p].result(1);
						mGR.mTeamInfo[mGR.Serise][m].result(1);
					}
					else {
						mGR.mTeamInfo[mGR.Serise][p].result(0);
						mGR.mTeamInfo[mGR.Serise][m].result(2);
					}
//					System.out.print("[" + p + "," + j + "]");
					continue;
				
//					mGR.gameReset(mGR.Serise, m, j);
//					continue;
				}
				if(M.mRand.nextBoolean()){
					mGR.mTeamInfo[mGR.Serise][m].result(0);
					mGR.mTeamInfo[mGR.Serise][j].result(2);
				}else{
					mGR.mTeamInfo[mGR.Serise][m].result(2);
					mGR.mTeamInfo[mGR.Serise][j].result(1);
				}
			}
		}
	}
	/*****************************ScoreBoard End***************************************/
	
	/*****************************TeamInfo Start***************************************/
	void Draw_TeamInfo(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);
		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no], -.64f,.56f, 2, 0,0);
		
		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				DrawTexture(gl, mGR.mTex_Whit128, -.55f, .32f - .135f * i);
				DrawTexture(gl, mGR.mTex_Whit64, -.323f, .32f - .135f * i);
				DrawTexture(gl, mGR.mTex_Whit64, -.172f, .32f - .135f * i);
				
				mGR.mFont.Draw(gl, "League", -.55f, .32f - .135f * i, 1, 1, 1);
				mGR.mFont.Draw(gl, "Plyd", -.323f, .32f - .135f * i, 1, 1, 1);
				mGR.mFont.Draw(gl, "Pts", -.172f, .32f - .135f * i, 1, 1, 1);
				
			} else {
				
				DrawTexture(gl, mGR.mTex_Yelo128, -.55f, .32f - .135f * i);
				DrawTexture(gl, mGR.mTex_Yelo64, -.323f, .32f - .135f * i);
				DrawTexture(gl, mGR.mTex_Yelo64, -.172f, .32f - .135f * i);
				
				mGR.mFont.Draw(gl, M.Team[mGR.Serise][i-1], -.55f, .32f - .135f * i, 1, 1, 0);
				mGR.mFont.Draw(gl, ""+mGR.mTeamInfo[mGR.Serise][i-1].pld, -.323f, .32f - .135f * i, 1, 1, 0);
				mGR.mFont.Draw(gl, ""+mGR.mTeamInfo[mGR.Serise][i-1].point, -.172f, .32f - .135f * i, 1, 1, 0);
			}
		}
		
		DrawTexture(gl, mGR.mTex_Yelo64, -.021f, .32f);
		DrawTexture(gl, mGR.mTex_Yelo64, 0.130f, .32f);
		DrawTexture(gl, mGR.mTex_Whit64x64, -.021f, .116f);
		DrawTexture(gl, mGR.mTex_Whit64x64, 0.130f, .116f);
		
		
		mGR.mFont.Draw(gl, "6's", -.021f, .320f, 1, 1, 1);
		mGR.mFont.Draw(gl, "4's", 0.130f, .320f, 1, 1, 1);
		mGR.mFont.Draw(gl, ""+mGR.tS6[mGR.Serise], -.020f, .116f, 1, 1, 1);
		mGR.mFont.Draw(gl, ""+mGR.tS4[mGR.Serise], 0.131f, .116f, 1, 1, 1);
		
		
		DrawTexture(gl, mGR.mTex_Whit32, .31f, .32f);
		DrawTexture(gl, mGR.mTex_Bat32 , .31f, .32f);
//		DrawTexture(gl, mGR.mTex_Whit160, 0.54f, .32f);
//		DrawTexture(gl, mGR.mTex_Whit160, 0.54f, .185f);
		mGR.mTex_Whit256.drawScal(gl,0.54f, .32f, .63f, 1);//yogesh
		mGR.mTex_Whit256.drawScal(gl,0.54f, .185f, .63f, 1);//yogesh
		mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0],0.54f, 0.32f, 0, 1,1);
		mGR.mFont.Draw(gl, "Year-"+(M.StartYear+mGR.Year[mGR.Serise])	, 0.54f, 0.185f, 0, 1,1);
		
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
//		
		DrawTexture(gl, mGR.mTex_RedPop256, .35f,-.32f);
		mGR.mFont.Draw(gl, "Next League Match"	,.34f,-.16f, 0, 1,0);
		mGR.mFont.Draw(gl, M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no]	,.37f,-.29f, 1, 1,0);
		mGR.mFont.Draw(gl, "v/s"	,.37f,-.40f, 0, 1,0);
		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBalTeam[mGR.Serise].no]	,.37f,-.51f, 1, 1,0);
		
		DrawTextureS(gl, mGR.mTex_Red256,0,0.9f,1.2f);
		mGR.mFont.Draw(gl, "Match fee "+M.MatchFee+" Gold", -.02f,0.9f, 1, 1,0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back",-.8f,-.9f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
		
		DrawTransScal(gl, mGR.mTex_Clip[6], 0,boss, Sel == 12 ? 1.1f : 1, Sel == 12 ? 0.5f : 1);
		if(boss > -.75f){
			boss = -.75f;
		}
		if(boss < -.75f)
			boss += .02f;
	}
	float boss= -1.30f;
	boolean Handle_TeamInfo(MotionEvent event) {
		Sel =0;
		
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if(CircRectsOverlap(0,boss, mGR.mTex_Clip[6].width()*.5f, mGR.mTex_Clip[6].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =12;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 10:
				M.GameScreen = M.GAMESERIES;
				break;
			case 11:
				if (mGR.isFIxed) {
					mGR.isFIxed = false;
					M.GameScreen =M.FIXED;
				} else {
					if (mGR.mGold >= M.MatchFee) {
						// M.GameScreen = M.GAMEPLAY;
						Counter = 0;
						M.GameScreen = M.LOADING;
						mGR.mGold -= M.MatchFee;
						// M.play(R.drawable.game_play);
					} else {
						M.GameScreen = M.BUYCOIN;
					}
				}
				break;
			case 12:
				M.GameScreen = M.FIXING;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************TeamInfo End***************************************/
	/*****************************TeamDetail Start***************************************/
	void Draw_TeamDetail(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);

		mGR.mFont.Draw(gl, ""+M.Team[mGR.Serise][mGR.mBatTeam[mGR.Serise].no], -.64f,.56f, 2, 0,0);
		for (int i = 0; i < 6; i++) {

			if (i % 2 == 0) {
//				DrawTexture(gl, mGR.mTex_Yelo160, -.45f, .32f - .136f * i);
				mGR.mTex_Yelo256.drawScal(gl, -.45f, .32f - .136f * i, .63f, 1);
				DrawTexture(gl, mGR.mTex_Yel32, -.68f, .32f - .136f * i);
				mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i]+(i==0?"*":""), -.59f, .32f - .136f * i, 1, 0,0);
			} else {
//				DrawTexture(gl, mGR.mTex_Whit160, -.45f, .32f - .136f * i);
				mGR.mTex_Whit256.drawScal(gl, -.45f, .32f - .136f * i, .63f, 1);
				DrawTexture(gl, mGR.mTex_Whit32, -.68f, .32f - .136f * i);
				mGR.mFont.Draw(gl, M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i], -.59f, .32f - .136f * i, 1, 0,1);
			}
			DrawTexture(gl, mGR.mTex_Bat32, -.68f, .32f - .136f * i);
			if (i < 5) {
				if (i % 2 == 0) {
//					DrawTexture(gl, mGR.mTex_Yelo160, 0.10f, .32f - .136f * i);
					mGR.mTex_Yelo256.drawScal(gl, 0.10f, .32f - .136f * i, .63f, 1);
					DrawTexture(gl, mGR.mTex_Yel32, -.13f, .32f - .136f * i);
					mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i+6], -.04f, .32f - .136f * i, 1, 0,0);
				} else {
//					DrawTexture(gl, mGR.mTex_Whit160, 0.10f, .32f - .136f * i);
					mGR.mTex_Whit256.drawScal(gl, 0.10f, .32f - .136f * i, .63f, 1);
					DrawTexture(gl, mGR.mTex_Whit32, -.13f, .32f - .136f * i);
					mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][i+6], -.04f, .32f - .136f * i, 1, 0,1);
				}
				DrawTexture(gl, mGR.mTex_Bal32, -.13f, .32f - .136f * i);
			}
		}
		DrawTexture(gl, mGR.mTex_Tran64, .52f,.56f);
		mGR.mFont.Draw(gl, mGR.mGold+" Gold",.52f,.56f, 1, 1,2);
		
		
		DrawTexture(gl, mGR.mTex_Tran128, .54f,-.02f);
		
		mGR.mTex_Face[1].drawSRGBT(gl,.54f,-.02f, 1,(M.CAP[1][mGR.Scol[mGR.Serise]][0])/255f,
				(M.CAP[1][mGR.Scol[mGR.Serise]][1])/255f,(M.CAP[1][mGR.Scol[mGR.Serise]][2])/255f,1);
		mGR.mTex_Face[0].drawSRGBT(gl,.54f,-.02f,1,(M.CAP[0][mGR.Hcol[mGR.Serise]][0])/255f,
				(M.CAP[0][mGR.Hcol[mGR.Serise]][1])/255f,(M.CAP[0][mGR.Hcol[mGR.Serise]][2])/255f,1);
		
		if(mGR.Serise == 0)
			DrawTexture(gl, mGR.mTex_Clip[4], .14f,-.64f);
		else
			DrawTexture(gl, mGR.mTex_Clip[3], .14f,-.64f);
		DrawTexture(gl, mGR.mTex_Clip[5], -.35f,-.71f);
		mGR.mFont.Draw(gl, "I am the boss"		,-.39f,-.68f + .10f, 0, 1,0);
		mGR.mFont.Draw(gl, "U have 2 win Trophy",-.39f,-.68f - .00f, 0, 1,0);
		mGR.mFont.Draw(gl, "All the best"		,-.39f,-.68f - .10f, 0, 1,0);
		
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
		
		
		
		
	}
	boolean Handle_TeamDetail(MotionEvent event) {
		Sel =0;
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.4f, mGR.mTex_Yelo128.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 11:
				M.GameScreen = M.TEAMINFO;
				mGR.isFIxed = false;
				boss= -1.30f;
				break;
			
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************TeamDetail End***************************************/
	
	
	/*****************************TeamSel Start***************************************/
	void Draw_TeamSel(GL10 gl) {
		float chY =.15f;
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);

		DrawTexture(gl, mGR.mTex_Trophy, 0, chY -.31f);
		mGR.mFont.Draw(gl, "Choose your team", -.64f,.56f, 2, 0,0);
		for(int i=0;i<3;i++){
			if(mGR.mBatTeam[mGR.Serise].no == i){
				DrawTransScal(gl, mGR.mTex_Whit128		,-.35f,chY-.3f*i, Sel == i+1 ? 1.1f : 1, Sel == i+1 ? 0.5f : 1);
				DrawTransScal(gl, mGR.mTex_Icon[6]		,-.60f,chY-.3f*i, Sel == i+1 ? 1.1f : 1, Sel == i+1 ? 0.5f : 1);
				mGR.mFont.Draw(gl, M.Team[mGR.Serise][i],-.35f,chY-.3f*i, 1, 1,1);
			}else
			{
				DrawTransScal(gl, mGR.mTex_Yelo128		,-.35f,chY-.3f*i, Sel == i+1 ? 1.1f : 1, Sel == i+1 ? 0.5f : 1);
				DrawTransScal(gl, mGR.mTex_Icon[6]		,-.60f,chY-.3f*i, Sel == i+1 ? 1.1f : 1, Sel == i+1 ? 0.5f : 1);
				mGR.mFont.Draw(gl, M.Team[mGR.Serise][i],-.35f,chY-.3f*i, 1, 1,0);
			}
			
			
//			DrawTexture(gl, mGR.mTex_Icon[6]	,-.60f,chY-.3f*i);
			if(mGR.mBatTeam[mGR.Serise].no == i+3){
				DrawTransScal(gl, mGR.mTex_Whit128		, .35f,chY-.3f*i, Sel == i+4 ? 1.1f : 1, Sel == i+4 ? 0.5f : 1);
				mGR.mFont.Draw(gl,M.Team[mGR.Serise][i+3],.35f,chY-.3f*i, 1, 1,1);
				DrawTransScal(gl, mGR.mTex_Icon[7]		, .60f,chY-.3f*i, Sel == i+4 ? 1.1f : 1, Sel == i+4 ? 0.5f : 1);
			}else{
				DrawTransScal(gl, mGR.mTex_Yelo128		, .35f,chY-.3f*i, Sel == i+4 ? 1.1f : 1, Sel == i+4 ? 0.5f : 1);
				mGR.mFont.Draw(gl,M.Team[mGR.Serise][i+3],.35f,chY-.3f*i, 1, 1,0);
				DrawTransScal(gl, mGR.mTex_Icon[7]		, .60f,chY-.3f*i, Sel == i+4 ? 1.1f : 1, Sel == i+4 ? 0.5f : 1);
				}
//			DrawTexture(gl, mGR.mTex_Icon[7]	, .60f, .3f-.3f*i);
			
		}
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back",-.8f,-.9f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
	}
	boolean Handle_TeamSel(MotionEvent event) {
		Sel =0;
		float chY =.13f;
		for(int i=0;i<3;i++){
			if(CircRectsOverlap( .45f,chY-.3f*i, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel =i+4;
			}
			if(CircRectsOverlap(-.45f,chY-.3f*i, mGR.mTex_Yelo128.width()*.8f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel =i+1;
			}
		}
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.6f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.6f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			default:
				mGR.mBatTeam[mGR.Serise].no = Sel -1;
				break;
			case 10:
				M.GameScreen = M.GAMESERIES;
				break;
			case 11:
				mGR.TeamSelect[mGR.Serise] = true;
				mGR.round[mGR.Serise] = 0;
				getNextMatch();
				M.GameScreen = M.GAMECAPTAIN;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************TeamSel End***************************************/
	/*****************************Captain Start***************************************/
	void Draw_Captain(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_BPopup, 0, 0);

//		mGR.mTex_Red256.drawScal(gl, 0, .51f,1,1);
//		mGR.mFont.Draw(gl, ""+M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0], sx, .5f, 2, 1,0);
		mGR.mFont.DrawS(gl,M.PlayerName[mGR.Serise][mGR.mBatTeam[mGR.Serise].no][0],0,.56f, 1.5f,1);
		mGR.mTex_Yelo256.drawScal(gl, 0, .3f,1.38f,1);
		mGR.mFont.Draw(gl, "Customize Your Captain", -.02f, .31f, 1, 1,0);
		
		DrawTexture(gl, mGR.mTex_Tran128, -.35f, -.16f);
		mGR.mTex_Face[1].drawSRGBT(gl,-.35f, -.16f, 1,
				(M.CAP[1][mGR.Scol[mGR.Serise]][0])/255f,
				(M.CAP[1][mGR.Scol[mGR.Serise]][1])/255f,
				(M.CAP[1][mGR.Scol[mGR.Serise]][2])/255f,1);
		mGR.mTex_Face[0].drawSRGBT(gl,-.35f, -.16f,1,
				(M.CAP[0][mGR.Hcol[mGR.Serise]][0])/255f,
				(M.CAP[0][mGR.Hcol[mGR.Serise]][1])/255f,
				(M.CAP[0][mGR.Hcol[mGR.Serise]][2])/255f
				,1);
		
		DrawTransScal(gl, mGR.mTex_Icon[7], -.07f, -.00f, Sel == 1 ? 1.1f : 1, Sel == 1 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Skin Tone", .25f, -.00f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Icon[6], .58f, -.00f, Sel == 2 ? 1.1f : 1, Sel == 2 ? 0.5f : 1);
		
		DrawTransScal(gl, mGR.mTex_Icon[7], -.07f, -.37f, Sel == 3 ? 1.1f : 1, Sel == 3 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Hair Color", 0.25f, -.37f, 1, 1,0);
		DrawTransScal(gl, mGR.mTex_Icon[6], 0.58f, -.37f, Sel == 4 ? 1.1f : 1, Sel == 4 ? 0.5f : 1);

		DrawTransScal(gl, mGR.mTex_Blu128,.8f,-.9f, Sel == 11 ? 1.1f : 1, Sel == 11 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Continue", .8f,-.9f, 1, 1,0);
	}
	boolean Handle_Captain(MotionEvent event) {
		Sel =0;
		if(CircRectsOverlap(-.07f, -.00f, mGR.mTex_Icon[7].width()*.4f, mGR.mTex_Icon[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =1;//skin -
		}
		if(CircRectsOverlap(.58f,-.00f, mGR.mTex_Icon[7].width()*.4f, mGR.mTex_Icon[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =2;//skin +
		}
		if(CircRectsOverlap(-.07f, -.37f, mGR.mTex_Icon[7].width()*.4f, mGR.mTex_Icon[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =3;//Hire -
		}
		if(CircRectsOverlap(.58f,-.37f, mGR.mTex_Icon[7].width()*.4f, mGR.mTex_Icon[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =4;//Hire+
		}
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Yelo128.width()*.6f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =11;//Continue
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 1:
				
				if(mGR.Scol[mGR.Serise]>0)
					mGR.Scol[mGR.Serise]--;
				else
					mGR.Scol[mGR.Serise] = M.CAP[1][mGR.Scol[mGR.Serise]].length-1;
				break;
			case 2:
				mGR.Scol[mGR.Serise]++;
				mGR.Scol[mGR.Serise]%=M.CAP[1][mGR.Scol[mGR.Serise]].length;
				break;
			case 3:
				if(mGR.Hcol[mGR.Serise]>0)
					mGR.Hcol[mGR.Serise]--;
				else
					mGR.Hcol[mGR.Serise] = M.CAP[1][mGR.Hcol[mGR.Serise]].length-1;
				break;
			case 4:
				mGR.Hcol[mGR.Serise]++;
				mGR.Hcol[mGR.Serise]%=M.CAP[1][mGR.Hcol[mGR.Serise]].length;
				break;
			case 11:
				M.GameScreen = M.TEAMDETAIL;
				break;
			}
			M.sound1(R.drawable.ball_hit0);
			Sel =0;
		}
		return true;
	}
	/*****************************Captain End***************************************/
	
	/*****************************Series Start***************************************/
	void Draw_Series(GL10 gl) {
		DrawTexture(gl, mGR.mTex_AllBg[0], 0, 0);
		for (int i = 0; i < 7; i++) {
			if ((move + mGR.Formove + i) > -1.5f && (move + mGR.Formove + i) < 1.5f) {
				DrawTexture(gl, mGR.mTex_Popup, move + mGR.Formove + i, 0.00f);
				DrawTexture(gl, mGR.mTex_Shadow, move + mGR.Formove + i, -.80f);
				mGR.mFont.Draw(gl, M.SeriesName[i], move + mGR.Formove + i,0.42f, 1, 1,0);
				switch (i) {
				case 0:
				case 1:
					DrawTexture(gl, mGR.mTex_Clip[i == 0 ? 4 : 3], move + mGR.Formove + i -.29f, -.1f);
					mGR.mFont.Draw(gl, ""+M.Team[i][mGR.mBatTeam[i].no], move + mGR.Formove + i +.07f,.08f, 1, 1,2);
					mGR.mFont.Draw(gl, ""+M.PlayerName[i][mGR.mBatTeam[i].no][0], move + mGR.Formove + i +.07f,-.08f, 1, 1,0);
					DrawTransScal(gl, mGR.mTex_Blu128, move + mGR.Formove + i +.07f,-.36f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
					mGR.mFont.Draw(gl, "Select", move + mGR.Formove + i +.07f,-.36f, 1, 1,0);
					for (int j = 0; j < 5; j++) {
						DrawTexture(gl, mGR.mTex_Yelo64, move + mGR.Formove + i + .35f, 0.26f - .17f * j);
						if(mGR.Year[i] <= j){
							if(mGR.Year[i] == j){
								DrawTexture(gl, mGR.mTex_Bat32, move + mGR.Formove + i + .35f, 0.26f - .17f * j);
							}
							else{
								mGR.mFont.Draw(gl, "" + (M.StartYear + j), move + mGR.Formove + i + .355f, 0.26f - .17f * j, 1, 1,0);
							}
						}else{
							DrawTexture(gl, mGR.mTex_Cup, move + mGR.Formove + i + .35f-.03f, 0.26f - .17f * j);
							mGR.mFont.Draw(gl, mGR.Pos[i][j]+"", move + mGR.Formove + i + .35f+.04f, 0.26f - .17f * j, 1, 1,1);
						}
					}
					break;
				case 2:
				case 3:
					
					DrawTexture(gl, mGR.mTex_Bat_S, move + mGR.Formove + i -.15f, -.1f);
					mGR.mFont.Draw(gl, M.Team[i%2][mGR.mBatTeam[i%2].no]		, move + mGR.Formove + i +.18f,0.05f, 1, 1,0);
					mGR.mFont.Draw(gl,M.PlayerName[i%2][mGR.mBatTeam[i%2].no][0], move + mGR.Formove + i +.18f,-.10f, 1, 1,2);
					DrawTransScal(gl, mGR.mTex_Blu128, move + mGR.Formove + i +.18f,-.36f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
					mGR.mFont.Draw(gl, "Select", move + mGR.Formove + i +.18f,-.36f, 1, 1,0);
					break;
				case 4:
					for (int j = 0; j < 4; j++) {
						DrawTransScal(gl, mGR.mTex_Blu128, move + mGR.Formove + i + .00f, 0.26f - .22f * j,Sel==j+10?1.3f:1.2f,Sel==j+10?0.5f:1);
					}
					mGR.mFont.Draw(gl, "Disclaimer", move + mGR.Formove + i + .01f, 0.26f - .22f * 0, 1, 1,0);
					mGR.mFont.Draw(gl, "About", move + mGR.Formove + i + .01f, 0.26f - .22f * 1, 1, 1,0);
					mGR.mFont.Draw(gl, "Sound "+(M.setValue?"On":"Off"), move + mGR.Formove + i + .01f, 0.26f - .22f * 2, 1, 1,0);
					mGR.mFont.Draw(gl, "Like", move + mGR.Formove + i + .01f, 0.26f - .22f * 3, 1, 1,0);
					break;
				case 5:
					DrawTransScal(gl, mGR.mTex_4Like[4], move + mGR.Formove + i,0,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
//					DrawTransScal(gl, mGR.mTex_4Like[5], move + mGR.Formove + i - .1f, -.18f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
					break;
				case 6:
					
					for (int j = 0; j < 4; j++) {
						DrawTransScal(gl, mGR.mTex_4Like[j], move + mGR.Formove + i -.15f+.3f*(j%2),0.14f-.4f *(j/2),Sel==j+20?1.1f:1,Sel==j+20?0.5f:1);
					}
					
//					DrawTransScal(gl, mGR.mTex_4Like[0], move + mGR.Formove + i + 0.15f, -.25f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
//					DrawTransScal(gl, mGR.mTex_4Like[1], move + mGR.Formove + i + 0.15f, 0.14f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
//					DrawTransScal(gl, mGR.mTex_4Like[2], move + mGR.Formove + i - 0.15f, -.25f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
//					DrawTransScal(gl, mGR.mTex_4Like[3], move + mGR.Formove + i - 0.15f, 0.14f,Sel==i+1?1.1f:1,Sel==i+1?0.5f:1);
					break;
				}
			}
			if(mGR.Formove == -i)
				DrawTexture(gl, mGR.mTex_Dot[0], -.27f+.09f*i, -.70f);
			else
				DrawTexture(gl, mGR.mTex_Dot[1], -.27f+.09f*i, -.70f);
			
		}
		if (move != 0 && chng == 0) {
			if (move > 0) {
				if (mGR.Formove < 0) {
					move += .08f;
					if (move > 0.9) {
						move = 0;
						mGR.Formove++;
					}
				} else {
					move -= .08f;
					if (move < 0.1) {
						move = 0;
						mGR.Formove = 0;
					}
				}
			} else {
				if (mGR.Formove > -6) {
					move -= .08f;
					if (move < -.9) {
						move = 0;
						mGR.Formove--;
					}
				} else {
					move += .08f;
					if (move > -.1) {
						move = 0;
						mGR.Formove = -6;
					}
				}
			}
		}
		
		if(GameRenderer.mStart.isSignedIn()){
			DrawTransScal(gl, mGR.mTex_Sing[0],-.88f, -.86f,Sel==31?1.1f:1,Sel==31?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Sing[1],-.70f, -.86f,Sel==32?1.1f:1,Sel==32?0.5f:1);
		}else{
			DrawTransScal(gl, mGR.mTex_Sing[2],-.82f, -.86f,Sel==30?1.1f:1,Sel==30?0.5f:1);
		}
		
	}
	float chng = 0;
	float move = 0;
	boolean Handle_Series(MotionEvent event) {
		Sel =0;
		for (int i = 0; i < 7; i++) {
			if ((move + mGR.Formove + i) > -1.5f && (move + mGR.Formove + i) < 1.5f) {
				switch (i) {
				case 0:
				case 1:
					if(CircRectsOverlap(move + mGR.Formove + i +.07f,-.36f, mGR.mTex_Blu128.width()*.5f, mGR.mTex_Blu128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
						Sel =i+1;
					}
					break;
				case 2:
				case 3:
					if(CircRectsOverlap(move + mGR.Formove + i +.18f,-.36f, mGR.mTex_Blu128.width()*.5f, mGR.mTex_Blu128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
						Sel =i+1;
					}
					break;
				case 4:
					for (int j = 0; j < 4; j++) {
						if(CircRectsOverlap(move + mGR.Formove + i - .00f, 0.26f - .22f * j, mGR.mTex_Blu128.width()*.5f, mGR.mTex_Blu128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							Sel =j+10;
						}
					}
					break;
				case 5:
					if(CircRectsOverlap(move + mGR.Formove + i - .00f, -.0f, mGR.mTex_Blu128.width()*.9f, mGR.mTex_Blu128.Height()*2.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
						Sel =i+1;
					}
					break;
				case 6:
					for (int j = 0; j < 4; j++) {
						if(CircRectsOverlap(move + mGR.Formove + i -.15f+.3f*(j%2),0.14f-.4f *(j/2), mGR.mTex_Blu128.width()*.5f, mGR.mTex_Blu128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							Sel =j+20;
						}
					}
					break;
				}
			}
		}
		
		if(GameRenderer.mStart.isSignedIn()){
			if(CircRectsOverlap(-.88f, -.86f,  mGR.mTex_Sing[0].width()*.4f,  mGR.mTex_Sing[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel=31;//LeaderBoard
			}
			if(CircRectsOverlap(-.70f, -.86f,  mGR.mTex_Sing[0].width()*.4f,  mGR.mTex_Sing[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel=32;//Achievement
			}
		}else{
			if(CircRectsOverlap(-.82f, -.86f,  mGR.mTex_Sing[2].width()*.5f,  mGR.mTex_Sing[2].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel=30;	//Sing-in
			}
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			chng = screen2worldX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()) {
			move = screen2worldX(event.getX())-chng ;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(Math.abs(move)<.1f){
				move =0;
				int no = M.mRand.nextInt(6);
				no = (no == mGR.mBatTeam[mGR.Serise].no ? (mGR.mBatTeam[mGR.Serise].no + 1) % 6 : no);
				switch (Sel) {
				case 1://India
					mGR.Serise = 0;
					if (mGR.Year[mGR.Serise] < 5) {
						if (!mGR.TeamSelect[mGR.Serise]) {
							M.GameScreen = M.GAMETEAMSEL;// M.GameScreen =M.TEAMINFO;
							mGR.TeamSelect[mGR.Serise] = true;
						} else {
							M.GameScreen = M.TEAMINFO;
							boss= -1.30f;
							mGR.isFIxed = false;
						}
					}else{
						
					}
					break;
				case 2://England
					mGR.Serise = 1;
					if (mGR.Year[mGR.Serise] < 5) {
						if (!mGR.TeamSelect[mGR.Serise]) {
							M.GameScreen = M.GAMETEAMSEL;
							mGR.TeamSelect[mGR.Serise] = true;
						} else {
							M.GameScreen = M.TEAMINFO;
							mGR.isFIxed = false;
							boss= -1.30f;
						}
					}else{
						
					}
					break;
				case 3://T10
					mGR.Serise =0;
					mGR.gameReset(10,mGR.mBatTeam[mGR.Serise].no,no);
					mGR.type = 1;
					M.GameScreen = M.SINGLEMATCH;
					break;
				case 4://T20
					mGR.Serise =1;
					mGR.gameReset(20,mGR.mBatTeam[mGR.Serise].no,no);
					mGR.mBalTeam[mGR.Serise].Run = 120+M.mRand.nextInt(130);
					mGR.type = 2;
					M.GameScreen = M.SINGLEMATCH;
					break;
				case 6://More Games
					MoreGame();
					break;
				case 10://Disclaimer
					M.GameScreen =M.DISCLAIMER;
					break;
				case 11://About
					M.GameScreen = M.ABOUT;
					break;
				case 12://Sound
					M.setValue=!M.setValue;
					break;
				case 13://Like
					RateUs();
					break;
				case 20:
					google();
					break;
				case 21:
					share2friend();
					break;
				case 22:
					facebook();
					break;
				case 23:
					Twitter();
					break;
				case 30:
					GameRenderer.mStart.beginUserInitiatedSignIn();//Sing in
					break;
				case 31:
					GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
				case 32:
					GameRenderer.mStart.onShowAchievementsRequested();//Achievement
					break;
				}
				if(Sel > 0)
					M.sound1(R.drawable.ball_hit0);
			}else{
				M.sound4(R.drawable.move_left_right);
			}
			chng =0;
			Sel =0;
		}
		return true;
	}
	/*****************************Series End***************************************/
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
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
	void drawNumber(GL10 gl,int no,float x, float y)
	{
//		float dx = mGR.mTex_Font[0].width();
//		 String strs = ""+no;
//		for(int i =0;i<strs.length();i++)
//		{
//			int k = ((int)strs.charAt(i))-48;
//			if(k>=0 && k<mGR.mTex_Font.length)
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
//		}
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
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				"Roking new Game '"
						+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK+getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,
					"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
	int Hint[] ={R.string.D_One,
			R.string.D_Two,
			R.string.D_Three,
			R.string.D_Four,
			R.string.D_Five,
			R.string.D_Six,
			R.string.D_Seven,
			R.string.D_Eight,
			};
	
	void popup3(GL10 gl)
	{
		String str = GameRenderer.mStart.getString(Hint[mGR.Hint%8]);
		DrawTexture(gl, mGR.mTex_Pup512x256, 0,0);
		mGR.mFont.Draw(gl, "Hint", -.44f, .41f, 1, 0, 0);
		int end = 0,start =0;
		int i =0;
		int j =0;
		for(;i <str.length();){
			start = end;
			int nStr = 0;
			while(i <str.length() && nStr < 40){
				if(str.charAt(i)==' '){
					end =i;
				}
				nStr++;
				i++;
			}
			if(i >=str.length())
				end = str.length();
			if(str.charAt(start)==' '){
				mGR.mFont.Draw(gl, str.substring(start, end), -.5f, .20f-j*.1f, 0, 0, 1);
			}else{
				mGR.mFont.Draw(gl, " "+str.substring(start, end), -.5f, .20f-j*.1f, 0, 0, 1);
			}
			j++;
			
		}
		
	}
	void Draw_Disclaimer(GL10 gl)
	{
		String str = GameRenderer.mStart.getString(R.string.DISCLAIMER);
		DrawTexture(gl, mGR.mTex_AllBg[1], 0,0);
		DrawTexture(gl, mGR.mTex_BPopup, 0,0);
		mGR.mFont.Draw(gl, "Disclaimer", -.64f,.56f, 1, 0, 0);
		int end = 0,start =0;
		int i =0;
		int j =0;
		for(;i <str.length();){
			start = end;
			int nStr = 0;
			while(i <str.length() && nStr < 56){
				if(str.charAt(i)==' '){
					end =i;
				}
				nStr++;
				i++;
			}
			if(i >=str.length())
				end = str.length();
			if(str.charAt(start)==' '){
				mGR.mFont.Draw(gl, str.substring(start, end), -0.69f,  0.31f-j*.09f, 0, 0, 0);
			}else{
				mGR.mFont.Draw(gl, " "+str.substring(start, end), -0.69f,  0.31f-j*.09f, 0, 0, 0);
			}
			j++;
			
		}
		DrawTransScal(gl, mGR.mTex_Blu128,-.8f,-.9f, Sel == 10 ? 1.1f : 1, Sel == 10 ? 0.5f : 1);
		mGR.mFont.Draw(gl, "Back",-.8f,-.9f, 1, 1,0);
	}
	boolean Handle_Disclaimer(MotionEvent event){
		if(CircRectsOverlap(-.8f,-.9f, mGR.mTex_Yelo128.width()*.6f, mGR.mTex_Yelo128.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =10;
		}
		if(MotionEvent.ACTION_UP ==event.getAction() && Sel>0){
			M.GameScreen = M.GAMESERIES;
			Sel =0;
		}
		return true;
	}
	
}
