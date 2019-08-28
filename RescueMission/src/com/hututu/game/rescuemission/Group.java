package com.hututu.game.rescuemission;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	int Counter =0;
	int c2=0;
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
//		M.GameScreen = M.GAMECONG;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl,mGR.mTex_hututugames, 0,0);
			if(Counter>60){
				M.GameScreen = M.GAMEADD;
				Counter =0;
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMEA:
			Draw_About(gl);
			break;
		default:
			Draw_GamePlay(gl);
			if(M.GameScreen == M.GAMEWIN||M.GameScreen ==M.GAMEPAUSE||M.GameScreen ==M.GAMEOVER||M.GameScreen == M.GAMECONG)
				Draw_WPO(gl);
			break;
		
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{

		
		switch (M.GameScreen) {
		case M.GAMELOGO:
//			M.GameScreen = M.GAMEPLAY;
//			Handle_SignIn(event);
			break;
		case M.GAMEADD:
			if((Counter>100)&&CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				M.loopStop();
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEA:
			Handle_About(event);
			break;
		case M.GAMEWIN:
		case M.GAMEPAUSE:
		case M.GAMEOVER:
		case M.GAMECONG:
			Handle_WPO(event);
		break;
		
		default:
			Handle_GamePlay(event);
			break;
		}
		Handle(event);
		return true;
	}
	/****************************WPO End*********************************/
	void Draw_WPO(GL10 gl){
		if(M.GameScreen == M.GAMEPAUSE)
		{
			DrawTexture(gl, mGR.mTex_GPaused, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score	, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_Retry	, -.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Cont	, 0.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu	, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Rate	, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else if(M.GameScreen == M.GAMEWIN)
		{
			DrawTexture(gl, mGR.mTex_Lvl		, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_Retry		, -.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Next		, 0.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu		, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Ggl		, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else if(M.GameScreen == M.GAMECONG)
		{
			DrawTexture(gl, mGR.mTex_Cong		, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_More		, 0.00f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu		, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Ggl		, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_Gameover,0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_bScore	, 0.00f,-.7f);
			DrawTexture(gl, mGR.mTex_Retry	, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Menu	, 0.00f,-.9f);
			DrawTexture(gl, mGR.mTex_Like	, 0.55f,-.9f);
			drawNumber(gl, mGR.mHScore, -.05f, -.7f, 0);
		}
		drawNumber(gl, mGR.mScore, -.29f, -.51f, 0);
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Plays	, -.35f,-.7f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Plays	, 0.35f,-.7f);
			break;
		case 3:
			if(M.GameScreen == M.GAMEOVER)
				DrawTexture(gl, mGR.mTex_Plays	, -.55f,-.9f);
			else 
				DrawTexture(gl, mGR.mTex_LSel	, -.55f,-.9f);
			break;
		case 4:
			if(M.GameScreen == M.GAMECONG || M.GameScreen == M.GAMEWIN)
				DrawTexture(gl, mGR.mTex_CSel, .00f,-.9f);
			else
				DrawTexture(gl, mGR.mTex_LSel	, -.00f,-.9f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_LSel	, 0.55f,-.9f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_Plays	,-.6f,-.51f);
			break;
		case 8:
			DrawTexture(gl, mGR.mTex_Plays	,0.6f,-.51f);
			break;
			
			}
	
	}
	boolean Handle_WPO(MotionEvent event)
	{
		if(CircRectsOverlap(-.35f,-.7f, mGR.mTex_Cont.width()*.4f, mGR.mTex_Cont.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen != M.GAMEOVER)
				mGR.mSel =1;
		}
		if(CircRectsOverlap( 0.35f,-.7f, mGR.mTex_Cont.width()*.4f, mGR.mTex_Cont.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen != M.GAMEOVER)
				mGR.mSel =2;
		}
		
		if(CircRectsOverlap( -.55f,-.9f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =3;
		}
		if(CircRectsOverlap(  -.00f,-.9f, mGR.mTex_Menu.width()*.6f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =4;
		}
		if(CircRectsOverlap( 0.55f,-.9f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =5;
		}
		if(CircRectsOverlap( 0.0f,-.7f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen == M.GAMECONG)
				mGR.mSel =6;
		}
		if(CircRectsOverlap(-.6f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =7;
		}
		if(CircRectsOverlap(0.6f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =8;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMECONG)
				{
					MoreGame();
				}
				else
				{
					M.loopStop();
					M.play1(GameRenderer.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
					
				}
				
				break;
			case 2:
				if(M.GameScreen == M.GAMECONG)
				{
					MoreGame();
				}
				else  if(M.GameScreen == M.GAMEWIN){
					M.loopStop();
					M.play1(GameRenderer.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					if(mGR.mLevel>49)
						mGR.mLevel =0;
					mGR.mLevel++;
					mGR.gameReset();
				}
				else{
					M.GameScreen = M.GAMEPLAY;
					M.loopStop();
					M.play1(GameRenderer.mContext,R.drawable.gameplay);
				}
				break;
			case 3:
				if(M.GameScreen == M.GAMEOVER)
				{
					M.loopStop();
					M.play1(GameRenderer.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.mScore =0;
					mGR.gameReset();
				}
				else 
					M.GameScreen = M.GAMELEVEL;
				break;
			case 4:
				if(M.GameScreen == M.GAMEOVER)
				{
					M.GameScreen = M.GAMELEVEL;
				}
				else if(M.GameScreen == M.GAMEWIN||M.GameScreen == M.GAMECONG)
					Google();
				else
					RateUs();
				break;
			case 5:
				if(M.GameScreen == M.GAMEOVER)
					facebook();
				else{
					M.setValue =!M.setValue;
				}
				break;
			case 6:
				MoreGame();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 8:
				GameRenderer.mStart.Submitscore(R.string.leaderboard_score,mGR.mScore);
				break;
				
			}
			if(mGR.mSel !=0)
				M.sound2(GameRenderer.mContext,R.drawable.click);
			mGR.mSel=0;
		}
		return true;
	}
	/****************************WPO Start*******************************/
	/****************************About Start*****************************/
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_scrAbt, 0, -.4f);
	}
	boolean Handle_About(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			M.loopStop();
			M.play(GameRenderer.mContext, R.drawable.splash_theme);
			M.GameScreen= M.GAMEMENU;
			if(mGR.mSel !=0)
				M.sound2(GameRenderer.mContext,R.drawable.click);
		}
		return true;
	}
	/****************************About End*******************************/
	/****************************Level Start*****************************/
	float dy = .4f;
	void Draw_Level(GL10 gl)
	{
		System.out.println(mGR.mUnLevel+"         !!");
		float ay = .3f;
		DrawTexture(gl, mGR.mTex_Cloud		, mGR.clud_x, .36f);
		DrawTexture(gl, mGR.mTex_Mountain	, mGR.mount_x, .17f);
		DrawTexture(gl, mGR.mTex_Ground		, mGR.ground_x, -.35f);
		DrawTexture(gl, mGR.mTex_Road		, mGR.road_x, -.70f);
		setTruck();
		for(int i=0;i<mGR.mTruck.length;i++){
			if(mGR.mTruck[i].x > -1.5){
				DrawTexture(gl, mGR.mTex_Truck[mGR.mTruck[i].type+3],mGR.mTruck[i].x-.22f, mGR.mTruck[i].y+.06f);
				DrawRGB(gl, mGR.mTex_Truck[0], mGR.mTruck[i].x, mGR.mTruck[i].y,mGR.mTruck[i].r, mGR.mTruck[i].g,mGR.mTruck[i].b);
				DrawTexture(gl, mGR.mTex_Truck[1],mGR.mTruck[i].x, mGR.mTruck[i].y);
				if(Counter%2==0)
					DrawTexture(gl, mGR.mTex_Truck[2],mGR.mTruck[i].x, mGR.mTruck[i].y);
			}
			mGR.mTruck[i].update();
		}
		for(int i=0;i<11 && mGR.move!=0;i++)
		{
			if(i!=8){
				DrawTexture(gl, mGR.mTex_LBox, -.76f+((i%4)*.5f)-mGR.y2,ay+.3f-((i/4)*dy));
//				if(mGR.move == 0)
				{
					if(mGR.mUnLevel<(i>7?-1:0)+mGR.mPage*10+i+1)
						DrawTexture(gl, mGR.mTex_Lock, -.76f+((i%4)*.5f)-mGR.y2,ay+.3f-((i/4)*dy));
					else
						drawNumber(gl, (i>7?-1:0)+mGR.mPage*10+i+1, -.76f+((i%4)*.5f)-mGR.y2,ay+.3f-((i/4)*dy),1);
				}
			}
		}
		for(int i=0;i<11;i++)
		{
			
			if(i!=8)
			{
				DrawTexture(gl, mGR.mTex_LBox, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy));
				if(mGR.move == 0)
				{
					if(mGR.mUnLevel<(i>7?-1:0)+mGR.mPage*10+i+1)
						DrawTexture(gl, mGR.mTex_Lock, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy));
					else 
						drawNumber(gl, (i>7?-1:0)+mGR.mPage*10+i+1, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy),1);
				}
				else
				{
					if(mGR.move > 0){
						if(mGR.mUnLevel<(i>7?-1:0)+mGR.mPage*10+i+1-10)
							DrawTexture(gl, mGR.mTex_Lock, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy));
						else 
							drawNumber(gl, (i>7?-1:0)+mGR.mPage*10+i+1-10, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy),1);
					}
					else {
						if(mGR.mUnLevel<(i>7?-1:0)+mGR.mPage*10+i+1+10)
							DrawTexture(gl, mGR.mTex_Lock, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy));
						else 
							drawNumber(gl, (i>7?-1:0)+mGR.mPage*10+i+1+10, -.76f+((i%4)*.5f)-mGR.y1,ay+.3f-((i/4)*dy),1);
					}
				}
				if(mGR.mSel==i+1)
					DrawTexture(gl, mGR.mTex_LSel, -.76f+((i%4)*.5f),ay+.3f-((i/4)*dy));
			}
		}
		
		{
			if(mGR.mPage <4)
			{
				DrawRotate(gl, mGR.mTex_UPA, -.7f,-.6f,90);
				if(mGR.mSel==100)
					DrawRotate(gl, mGR.mTex_uparrowS, -.7f,-.6f,90);
					
			}
		}
		{
			if(mGR.mPage >0)
			{
				DrawRotate(gl, mGR.mTex_UPA, +.7f,-.6f,-90);
				if(mGR.mSel==101)
					DrawRotate(gl, mGR.mTex_uparrowS, +.7f,-.6f,-90);
			}
		}
		
		
		if(mGR.move!=0)
		{
			mGR.y1+=mGR.move;
			mGR.y2+=mGR.move;

			if(mGR.road_x > 1.1)
				mGR.road_x = 1.1f;
			if(mGR.road_x < -1.1)
				mGR.road_x = -1.1f;
			
			if(mGR.mount_x > 1.1)
				mGR.mount_x = 1.1f;
			if(mGR.mount_x < -1.1)
				mGR.mount_x = -1.1f;
			
			if(mGR.ground_x > 1.1)
				mGR.ground_x = 1.1f;
			if(mGR.ground_x < -1.1)
				mGR.ground_x = -1.1f;
			
			if(mGR.clud_x > 1.1)
				mGR.clud_x = 1.1f;
			if(mGR.clud_x < -1.1)
				mGR.clud_x = -1.1f;
			
			mGR.clud_x	-=mGR.move*.05f;
			mGR.mount_x	-=mGR.move*.10f;
			mGR.ground_x-=mGR.move*.15f;
			mGR.road_x	-=mGR.move*.25f;
			
			if(mGR.move>0)
			{
				if(mGR.y2>=0)
				{
					mGR.move =0;
					mGR.y2 = 0;
					mGR.y1 = 0;
				}
			}
			if(mGR.move<0)
			{
				if(mGR.y2<=0)
				{
					mGR.move =0;
					mGR.y2 = 0;
					mGR.y1 = 0;
				}
			}
		}
	}
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel=0;
		float ay = .3f;
		
		for(int i=0;i<11;i++)
		{
			if(CirCir(-.76f+((i%4)*.5f),ay+.3f-((i/4)*dy), .20f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CirCir(-.7f,-.6f, .20f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if( mGR.move==0 && mGR.mPage <4)
				mGR.mSel = 100;
		}
		if(CirCir(0.7f,-.6f, .20f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if( mGR.move==0 && mGR.mPage >0)
				mGR.mSel = 101;
		}
//		System.out.println("  mGR.mSel  = "+mGR.mSel);
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:
			case 6:case 7:case 8:case 10:case 11:
				if(mGR.mUnLevel>=(mGR.mSel>7?-1:0)+mGR.mPage*10+mGR.mSel)
				{
					M.loopStop();
					M.play1(GameRenderer.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.mLevel=(mGR.mSel>7?-1:-0)+mGR.mPage*10+mGR.mSel;
					mGR.mScore =0; 
					mGR.gameReset();
				}
				break;
			case 100:
				if( mGR.move==0 && mGR.mPage <4){
					mGR.move = 0.1f;
					mGR.y1=0;
					mGR.y2=-2;
					mGR.mPage++;
				}
				break;
			case 101:
				if( mGR.move==0 && mGR.mPage >0){
					mGR.move = -.1f;
					mGR.y1=0;
					mGR.y2=2;
					mGR.mPage--;
					}
				break;
				}
			if(mGR.mSel !=0)
				M.sound2(GameRenderer.mContext,R.drawable.click);
			mGR.mSel=0;
		}
		return true;
		
	}
	/****************************Level End*******************************/
	/****************************Menu Start******************************/
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, .00f);
		DrawTexture(gl, mGR.mTex_Playde, 0,-.73f);
		
		DrawTexture(gl, mGR.mTex_Ggl, .83f,-.9f);
		DrawTexture(gl, mGR.mTex_Leader, .83f,-.7f);
		
		DrawTexture(gl, mGR.mTex_Cntbg	,-.83f,0.00f+mGR.my);
		DrawTexture(gl, mGR.mTex_About	,-.83f,0.23f+mGR.my);
		DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,-.83f,0.03f+mGR.my);
		DrawTexture(gl, mGR.mTex_FBI,-.83f,-.18f+mGR.my);
		if(mGR.my>-1)
			DrawTexture(gl, mGR.mTex_cDown, -.83f,-.9f);
		else
			DrawTexture(gl, mGR.mTex_cUp, -.83f,-.9f);
		
		switch (mGR.mSel) {
		case 1:
			DrawTextureS(gl, mGR.mTex_uparrowS, -.83f,-.9f,1.6f,1);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Plays, 0,-.73f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_CSel, .83f,-.9f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,0.23f+mGR.my);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,0.03f+mGR.my);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,-.18f+mGR.my);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_CSel, .83f,-.7f);
			break;
		
		}
		
		if(mGR.mMenu!=0)
		{
			mGR.my+=mGR.mMenu;
			if(mGR.mMenu>0 && mGR.my>-.55){
				mGR.mMenu=0;
				mGR.my = -.55f;
			}
			if(mGR.mMenu<0 && mGR.my<-1.5){
				mGR.mMenu=0;
				mGR.my = -1.5f;
			}
		}
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(CirCir(-.83f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0)
				mGR.mSel =1;
		}
		if(CircRectsOverlap(0,-.73f, mGR.mTex_Playde.width()*.4f, mGR.mTex_Playde.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =2;
		}
		if(CircRectsOverlap( .83f,-.9f, mGR.mTex_Ggl.width()*.4f, mGR.mTex_Ggl.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =3;
		}
		if(CircRectsOverlap( .83f,-.7f, mGR.mTex_Ggl.width()*.4f, mGR.mTex_Ggl.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =7;
		}


		if(CirCir(-.83f,0.23f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =4;
		}
		if(CirCir(-.83f,0.03f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =5;
		}
		if(CirCir(-.83f,-.18f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =6;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(mGR.my>-1)
					mGR.mMenu = -.05f;
				else
					mGR.mMenu = 0.05f;
				break;
			case 2:
				mGR.clud_x = 1.1f;
				mGR.mount_x = 1.1f;
				mGR.ground_x = 1.1f;
				mGR.road_x = 1.1f;
				M.GameScreen = M.GAMELEVEL;
				M.loopStop();
				break;
			case 3:
				Google();
				break;
			case 4:
				M.GameScreen = M.GAMEA;
				M.loopStop();
				break;
			case 5:
				M.setValue =!M.setValue;
				if(!M.setValue)
					M.loopStop();
				else
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
				break;
			case 6:
				facebook();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			
			}
			if(mGR.mSel !=0)
				M.sound2(GameRenderer.mContext,R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	/****************************Menu End********************************/
	/****************************Game Play Start*************************/
	void GameCondition()
	{
		M.loopStop();
		if(mGR.mLevel <= mGR.mSS)
		{
			System.out.println(mGR.mUnLevel+"         !!              "+mGR.mLevel);
			M.GameScreen = M.GAMEWIN;
			if(mGR.mLevel == 50)
				M.GameScreen = M.GAMECONG;
			if(mGR.mUnLevel <= mGR.mLevel)
				mGR.mUnLevel = mGR.mLevel+1;
		}
		else
		{
			M.GameScreen = M.GAMEOVER;
			M.sound1(GameRenderer.mContext, R.drawable.gameover);
		}
		mGR.mScore += mGR.mSS*123+M.mRand.nextInt(100);
		double time = System.currentTimeMillis() - mGR.mTimeTaken;
		time = (1.0/time)*mGR.mLevel*10000;
		mGR.mScore += (int)time;
		if(mGR.mScore > mGR.mHScore)
			mGR.mHScore = mGR.mScore;
	}
	void setTruck()
	{
		
		for(int i=0;i<mGR.mTruck.length;i++)
		{
			if(mGR.mTruck[i].x>1.5f)
			{
				float k =0;
				for(int j=0;j<mGR.mTruck.length;j++){
					if(mGR.mTruck[j].x< k)
						k =mGR.mTruck[j].x;
				}
				mGR.mTruck[i].set(k - 1.5f,0.01f+(mGR.mSS/15f)*.01f);
				if(mGR.mSS <= M.INC*4)
					mGR.mTruck[i].type =0;
			}
		}
	}
	void kill(int i)
	{
		M.sound3(GameRenderer.mContext, R.drawable.puppet_blast);
		mGR.mSoldier[i].isDead = true;
	}
	void gameLogic()
	{
		int i=0;
		setTruck();
		for(i=0;i<mGR.mSoldier.length;i++)
		{
			if(!mGR.mSoldier[i].isDead && mGR.mSoldier[i].x>-1.1f&&mGR.mSoldier[i].x<1.1f && !mGR.mSoldier[i].isRailing){
				/****************************Truck Start*********************************/
				for(int j=0;j<mGR.mTruck.length;j++){
					if(mGR.mSoldier[i].vx==0 && CircRectsOverlap(mGR.mTruck[j].x-.22f, mGR.mTruck[j].y+.06f, mGR.mTex_Truck[3].width()*.25f,mGR.mTex_Truck[3].Height()*.2f, mGR.mSoldier[i].x, mGR.mSoldier[i].y, .1f)){
						mGR.mSoldier[i].vx=mGR.mTruck[j].vx;
						mGR.mSoldier[i].y=mGR.mTruck[j].y+.06f;
						if(mGR.mTruck[j].type==1){
							setAni(mGR.mSoldier[i].x,mGR.mSoldier[i].y);
							kill(i);
						}
						else{
							mGR.mSS++;
							M.sound13(GameRenderer.mContext,R.drawable.safe_alnding);
						}
					}
				}
				/****************************Missile Start*********************************/
				if(mGR.mSS>=M.INC*2 && CirCir(mGR.mMissile.x, mGR.mMissile.y, .1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .1)){
					setAni(mGR.mMissile.x, mGR.mMissile.y);
					mGR.mSoldier[i].set(-100, -100);
					kill(i);
					mGR.mMissile.x= mGR.mMissile.y = -100;
				}
				/****************************mTex_Chakri Start*********************************/
				if(mGR.mSS>=M.INC*5){
					if(CirCir(mGR.mTank.x+mGR.mTank.cx1,mGR.mTank.y+mGR.mTank.cy1, .1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .1)){
						setAni(mGR.mTank.x+mGR.mTank.cx1,mGR.mTank.y+mGR.mTank.cy1);
						mGR.mSoldier[i].set(-100, -100);
						kill(i);
						mGR.mTank.isB1 = 1;
					}
					if(CirCir(mGR.mTank.x+mGR.mTank.cx2,mGR.mTank.y+mGR.mTank.cy2, .1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .1)){
						setAni(mGR.mTank.x+mGR.mTank.cx2,mGR.mTank.y+mGR.mTank.cy2);
						mGR.mSoldier[i].set(-100, -100);
						kill(i);
						mGR.mTank.isB2 = 1;
					}
					if(CirCir(mGR.mTank.x+mGR.mTank.cx3,mGR.mTank.y+mGR.mTank.cy3, .1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .1)){
						setAni(mGR.mTank.x+mGR.mTank.cx3,mGR.mTank.y+mGR.mTank.cy3);
						mGR.mSoldier[i].set(-100, -100);
						kill(i);
						mGR.mTank.isB3 = 1;
					}
				}
				/****************************Tower Rays Start*********************************/
				for(int j=0;Counter%200 < 40 && mGR.mSS>=M.INC*3 && j<10;j++){
					if(CirCir(-.59f+.12f*j,.06f+.072f*j, .05f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .05)||
							CirCir(-.77f+.08f*j,.27f+.048f*j, .05f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .05)){
						setAni(mGR.mSoldier[i].x,mGR.mSoldier[i].y);
						mGR.mSoldier[i].set(-100, -100);
						kill(i);
					}
				}
				/****************************Ralling Start*********************************/
				if(mGR.mSS>=M.INC*1 && CircRectsOverlap(-.6f,-.57f, .15f,.1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .05)){
					mGR.mSoldier[i].isRailing = true;
					kill(i);
				}
				if(mGR.mSS>=M.INC*6 &&CircRectsOverlap(0.6f,-.57f, .15f,.1f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .05)){
					mGR.mSoldier[i].isRailing = true;
					kill(i);
				}
				/****************************UFO Start*********************************/
				if(CircRectsOverlap(mGR.mUfo.x,mGR.mUfo.y, .06f,.5f,  mGR.mSoldier[i].x,mGR.mSoldier[i].y, .05)){
					setAni(mGR.mSoldier[i].x,mGR.mSoldier[i].y);
					mGR.mSoldier[i].set(-100, -100);
					kill(i);
				}			
			}
		}
		mGR.mHeli.update();
		if(mGR.mSS>=M.INC*2 && Counter%50==0)
		{
			mGR.mMissile.set(.68f,-.32f);
			mGR.mMissile.vx =-.05f;
			mGR.mMissile.vy =0.03f;
			M.sound16(GameRenderer.mContext, R.drawable.rocket);
		}
		if(mGR.mSS>=M.INC*7 && Counter%100==0){
			mGR.mUfo.set(-1.3f,0.0f);
			mGR.mUfo.vx =.1f;
			mGR.mUfo.vy = 0;
		}
		mGR.mUfo.ufo();
		if(mGR.mSS>=M.INC*8){
		if(mGR.mTank.x > mGR.mHeli.x)
			mGR.mTank.x-=.001f;
		else
			mGR.mTank.x+=.001f;
		}
		if(mGR.mLSoldier<=0 )
		{
			c2++;
//			System.out.println(" c2 = "+c2);
			if(c2 > 30){
				M.GameScreen = M.GAMECOMP;
				c2+=1;
			}
			if(c2 > 100){
				GameCondition();
			}
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		int i=0;
		DrawTexture(gl, mGR.mTex_Cloud		, mGR.clud_x	, .36f);
		DrawTexture(gl, mGR.mTex_Mountain	, mGR.mount_x	, .17f);
		DrawTexture(gl, mGR.mTex_Ground		, mGR.ground_x	, -.35f);
		
		DrawTexture(gl, mGR.mTex_Road, mGR.road_x, -.70f);
		if(mGR.mSS>=M.INC*2){
			if(mGR.mMissile.x>-1.1f){
				DrawRotate(gl, mGR.mTex_Missile, mGR.mMissile.x, mGR.mMissile.y,-45);
				mGR.mMissile.x+=mGR.mMissile.vx;mGR.mMissile.y+=mGR.mMissile.vy;
			}
			DrawTexture(gl, mGR.mTex_Launcher, .8f,-.35f);
		}
		if(mGR.mSS>=M.INC*5)
		{
			DrawTexture(gl, mGR.mTex_Tank[Counter%2], mGR.mTank.x,mGR.mTank.y);
			DrawTexture(gl, mGR.mTex_Tank[2], mGR.mTank.x,mGR.mTank.y+.27f);
			DrawRotate(gl, mGR.mTex_Chakri[mGR.mTank.isB1], mGR.mTank.x+mGR.mTank.cx1,mGR.mTank.y+mGR.mTank.cy1,Counter*5);
			DrawRotate(gl, mGR.mTex_Chakri[mGR.mTank.isB2], mGR.mTank.x+mGR.mTank.cx2,mGR.mTank.y+mGR.mTank.cy2,Counter*6);
			DrawRotate(gl, mGR.mTex_Chakri[mGR.mTank.isB3], mGR.mTank.x+mGR.mTank.cx3,mGR.mTank.y+mGR.mTank.cy3,Counter*4);
		}
		if(mGR.mSS>=M.INC*3){
			if(Counter%200 < 40 ){
				DrawRotate(gl, mGR.mTex_Rays[Counter%2],   -.8f+1.4f,-.14f+0.91f,45);
				DrawRotate(gl, mGR.mTex_Rays[Counter%2],   -.8f+1.2f,-.14f+1.10f,45);
				if(M.GameScreen == M.GAMEPLAY)
					M.sound14(GameRenderer.mContext,R.drawable.tower_ray);
			}
			DrawTexture(gl, mGR.mTex_Tower, -.8f,-.14f);
		}
		DrawTexture(gl, mGR.mTex_BStone, mGR.ston_x, -.45f);
		DrawTextureS(gl, mGR.mTex_Tree, .8f, -.44f,.7f,.7f);
		if(mGR.mSS>=M.INC*1)
			DrawTexture(gl, mGR.mTex_Ralling, -.6f,-.57f);
		if(mGR.mSS>=M.INC*6)
			DrawTexture(gl, mGR.mTex_Ralling, 0.6f,-.57f);
		if(mGR.mSS>=M.INC*7){
			DrawTexture(gl, mGR.mTex_ufo_rays[Counter%2], mGR.mUfo.x,mGR.mUfo.y);
			DrawTexture(gl, mGR.mTex_Newufo, mGR.mUfo.x,mGR.mUfo.y+.62f);
			if(mGR.mUfo.x>-1&&mGR.mUfo.x<1&&mGR.mUfo.y>-1&&mGR.mUfo.y<1 && M.GameScreen == M.GAMEPLAY)
				M.sound15(GameRenderer.mContext,R.drawable.ufo);
		}
		
		for(i=0;i<mGR.mTruck.length;i++){
			if(mGR.mTruck[i].x > -1.5){
				DrawTexture(gl, mGR.mTex_Truck[mGR.mTruck[i].type+3],mGR.mTruck[i].x-.22f, mGR.mTruck[i].y+.06f);
				DrawRGB(gl, mGR.mTex_Truck[0], mGR.mTruck[i].x, mGR.mTruck[i].y,mGR.mTruck[i].r, mGR.mTruck[i].g,mGR.mTruck[i].b);
				DrawTexture(gl, mGR.mTex_Truck[1],mGR.mTruck[i].x, mGR.mTruck[i].y);
				if(Counter%2==0)
					DrawTexture(gl, mGR.mTex_Truck[2],mGR.mTruck[i].x, mGR.mTruck[i].y);
			}
			mGR.mTruck[i].update();
		}
		for(i=0;i<mGR.mSoldier.length;i++)
		{
			if(mGR.mSoldier[i].x>-1.1f && mGR.mSoldier[i].x<1.1&&mGR.mSoldier[i].y>-1f&&mGR.mSoldier[i].y<1)
			{
				if(mGR.mSoldier[i].vx!=0)
					DrawTexture(gl, mGR.mTex_PupetHalf, mGR.mSoldier[i].x,mGR.mSoldier[i].y);
				else if(mGR.mSoldier[i].isRailing)
					DrawTexture(gl, mGR.mTex_HPupet, mGR.mSoldier[i].x,mGR.mSoldier[i].y);
				else if(mGR.mSoldier[i].isDead)
					DrawTexture(gl, mGR.mTex_PDead[i%mGR.mTex_PDead.length], mGR.mSoldier[i].x,mGR.mSoldier[i].y);
				else if(mGR.mSoldier[i].y>.5f)
					DrawTexture(gl, mGR.mTex_PJump[0], mGR.mSoldier[i].x,mGR.mSoldier[i].y);
				else
					DrawTexture(gl, mGR.mTex_PJump[1], mGR.mSoldier[i].x,mGR.mSoldier[i].y);
				mGR.mSoldier[i].update();
			}
		}
		
		DrawTexture(gl, mGR.mTex_Helicopter0[Counter%2], mGR.mHeli.x, mGR.mHeli.y);
		
		DrawAni(gl);
		
		
		if(M.GameScreen == M.GAMECOMP)
		{
			if(mGR.mLevel <= mGR.mSS)
			{
				if(mGR.mLevel == 50)
					DrawTransScal(gl, mGR.mTex_Cong		, 0.00f,-.26f,c2/100f,c2/100f);
				else
					DrawTransScal(gl, mGR.mTex_Lvl		, 0.00f,-.26f,c2/100f,c2/100f);
			}
			else
			{
				DrawTransScal(gl, mGR.mTex_Gameover,0.00f,-.26f,c2/100f,c2/100f);
			}
		}
		if(M.GameScreen == M.GAMEPLAY||M.GameScreen == M.GAMECOMP){
			DrawTexture(gl, mGR.mTex_Save	, 0.00f,-.93f);
			drawNumber(gl,  mGR.mSS, mGR.mSS>99?-.55f:-.45f, -.93f, 0);
			drawNumber(gl,  mGR.mLevel		, -.20f, -.93f, 0);
			drawNumber(gl,  mGR.mLSoldier	, 0.67f, -.93f, 0);
			
			DrawTexture(gl, mGR.mTex_Level	, -.80f, 0.75f);
			drawNumber(gl,  mGR.mLevel		, -.82f, 0.66f, 0);
			gameLogic();
		}
		if(Counter<50 && Counter%2==0){
			DrawTextureRS(gl, mGR.mTex_Arrow, -.8f, 0.50f,180,2);
			DrawTextureRS(gl, mGR.mTex_Arrow, 0.8f, 0.50f,000,2);
			DrawTextureRS(gl, mGR.mTex_Jumpky, 0.0f, -.80f,000,1);
		}
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mHeli.dir =0;
		if(M.ScreenHieght*.75 > event.getY()){
			//if(CirCir(-.8f, 0.00f, mGR.mTex_Arrow.width()*.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			if(M.ScreenWidth/2 > event.getX())
			{
				mGR.mHeli.dir = 2;
			}//if(CirCir(0.8f, 0.00f, mGR.mTex_Arrow.width()*.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			else
			{
				mGR.mHeli.dir = 1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			mGR.mHeli.dir =0;
			if(M.ScreenHieght*.75 < event.getY())
			{
				setSoilder();
			}
			if(50> event.getY())
			{
				mGR.gameReset();
			}
		}
		return true;
	}
	void setSoilder()
	{
		for(int i =0;i<mGR.mSoldier.length && mGR.mLSoldier>0;i++){
			if(mGR.mSoldier[i].x>2||mGR.mSoldier[i].x<-2||mGR.mSoldier[i].y>2||mGR.mSoldier[i].y<-2)
			{
				mGR.mSoldier[i].set(mGR.mHeli.x-.03f, mGR.mHeli.y);
				mGR.mLSoldier--;
				M.sound9(GameRenderer.mContext,R.drawable.puppet_fall);
//				System.out.println("  "+mGR.mLSoldier);
				break;
			}
		}
	}
	void setAni(float x,float y)
	{
		int k=0;
		for(int i =0;i<mGR.mAni.length&&k<10;i++){
			if(mGR.mAni[i].x>1||mGR.mAni[i].x<-1||mGR.mAni[i].y>1||mGR.mAni[i].y<-1){
				mGR.mAni[i].set(x, y);
				k++;
			}
		}
	}
	void DrawAni(GL10 gl)
	{
		for(int i =0;i<mGR.mAni.length;i++){
			if(mGR.mAni[i].x<1.2 && mGR.mAni[i].x>-1.2 && mGR.mAni[i].y<1.2 && mGR.mAni[i].y>-1.2){
				DrawTexture(gl, mGR.mTex_PetPart[mGR.mAni[i].obj], mGR.mAni[i].x, mGR.mAni[i].y);
				mGR.mAni[i].update();
			}
		}
	}
	/****************************Game Play End***************************/
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	void DrawRotate(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, x, y, angle);
	}
	void DrawTextureRS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float scal)
	{
		Tex.drawRS(gl, x, y, angle,scal);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy)
	{
		Tex.drawScal(gl, x, y,sx,sy);
	}
	void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b)
	{
		Tex.drawrgb(gl, x, y,r,g,b);
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
	void drawNumber(GL10 gl,int no,float x, float y,int al)
	{
		float dx = mGR.mTex_Font[0].width()*.7f;
		String strs = ""+no;
		if(al==1)
			x-=strs.length()*dx/4;
		for(int i =0;i<strs.length();i++){
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
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
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
}
