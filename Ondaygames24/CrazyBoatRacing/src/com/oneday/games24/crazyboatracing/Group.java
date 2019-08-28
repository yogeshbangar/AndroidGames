//AC38939418,
//AA88209869
//Your Online Account Opening form has been submitted successfully.
//Please note the TCRN : AC38939418 and TARN : AA88209869 for future reference.

//AA86937658

//46092598
// PPK Number is U000161325

package com.oneday.games24.crazyboatracing;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	int Counter2 = 0;
	float sx=.1f,sy=1;
	float ssx=.0f,ssy=0;
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen =M.GAMELEVEL;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl,mGR.mTex_Logo,0,0);
			if(Counter > 60){
				if(!mGR.addFree){
					M.GameScreen = M.GAMEADD;
					Counter = 0;
				}
				else{
					Menu();
				}
			}
			break;	
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Cross, .9f,0.9f);
			else 
			{
				mGR.mTex_Ling.drawSS(gl,-.75f,0.9f	, .7f, 1.0f,0);//10
				mGR.mTex_LingBar.drawSS(gl,-.74f,0.9f	, Counter*.44f, 1.3f,0);//10
			}
			break;
		case M.GAMESTART:
		case M.GAMEGO:
			Draw_GameStart(gl); 
			break;
		case M.GAMEMENU:
		case M.GAMEJOIN:
			Draw_Menu(gl);
			break;
		case M.GAMELEVEL:
		case M.GAMEJOIN2:
			Draw_Level(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Draw_About(gl);
			break;
		case M.GAMELOAD:
			mGR.mTex_Ling.drawSS(gl,-.75f,0.9f	, .7f, 1.0f,0);//10
			mGR.mTex_LingBar.drawSS(gl,-.74f,0.9f	, Counter*.44f, 1.3f,0);//10
			if(Counter>100)
				M.GameScreen = M.GAMESTART;
			break;
		default:
			Draw_GamePlay(gl);
			break;
		}
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				Menu();
				M.sound2(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEOVER:
			Handle_Gameover(event);
			break;
		case M.GAMEWIN:
			Handle_GameWin(event);
			break;
		case M.GAMESTART:
		case M.GAMEGO:
			Handle_GameStart(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Handle_About(event);
			break;
		case M.GAMEJOIN:
		case M.GAMEJOIN2:
			Handle_Join(event);
		break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		default:
			Handle_GamePlay(event);
			break;
		}
		return true;
	}
	void setLoad()
	{
		Counter = 0;
		M.GameScreen = M.GAMELOAD;
	}
	/*************************Pause Start********************************/
	void Roket(GL10 gl,float x, float y)
	{
//		DrawTextureS(gl, mGR.mTex_Missile, x,y, 1);
		DrawTextRS(gl, mGR.mTex_Missile, x,y, (float)Math.toDegrees(mGR.mFire.ang)-90,1,1);
//		DrawTextRS(gl, mGR.mTex_Missile, x-.3f	,y, (float)Math.toDegrees(mGR.mFire.ang)+90,1,1);
		
//		if((mGR.mFire.count%30)<12){
//			for(int i=0;i<mGR.mRoket.length;i++){
//				if(mGR.mRoket[i].x >-1 && mGR.mRoket[i].x < 1&&mGR.mRoket[i].y >-1&&mGR.mRoket[i].y<1 && mGR.mRoket[i].img<mGR.mTex_Blast.length){
//					DrawTransScal(gl, mGR.mTex_Blast[mGR.mRoket[i].img], mGR.mRoket[i].x, mGR.mRoket[i].y,1,mGR.mRoket[i].z);
//					mGR.mRoket[i].update();
//				}
//			}
//			for(int i=0;i<mGR.mRoket.length;i++){
//				if(mGR.mRoket[i].x <=-1 || mGR.mRoket[i].x >= 1||mGR.mRoket[i].y <=-1||mGR.mRoket[i].y>=1||mGR.mRoket[i].img >=mGR.mTex_Blast.length){				
//					mGR.mRoket[i].set(x, y+(mGR.mFire.count%10)*.15f);
//					break;
//				}
//			}
//			DrawTextureS(gl, mGR.mTex_Roket[0], x,y+(mGR.mFire.count)*.14f, 1+(mGR.mFire.count%10)*.005f);
//		}else{
//			DrawTextureS(gl, mGR.mTex_Roket[1], x,y+.1f, 1+(30-(mGR.mFire.count%30))*.025f);
//			for(int i=0;i<mGR.mRoket.length;i++){
//				if(mGR.mRoket[i].x >-1 && mGR.mRoket[i].x < 1&&mGR.mRoket[i].y >-1&&mGR.mRoket[i].y<1 && mGR.mRoket[i].img<mGR.mTex_Blast.length){
//					DrawTransScal(gl, mGR.mTex_Blast[mGR.mRoket[i].img], mGR.mRoket[i].x, mGR.mRoket[i].y,1,mGR.mRoket[i].z);
//					mGR.mRoket[i].update2();
//				}
//			}
//			for(int i=0;i<mGR.mRoket.length;i++){
//				if(mGR.mRoket[i].x <=-1 || mGR.mRoket[i].x >= 1||mGR.mRoket[i].y <=-1||mGR.mRoket[i].y>=1||mGR.mRoket[i].img >=mGR.mTex_Blast.length){				
//					mGR.mRoket[i].set(x, y+(30-(mGR.mFire.count%30))*.005f+.20f);
//					break;
//				}
//			}
//		}
	}
	void Draw_Pause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_LvlBox, 0, 0);
		DrawTexture(gl, mGR.mTex_Pause , 0, 0.33f);
		DrawTextureS(gl, mGR.mTex_mainBtn , -.4f, -.05f,mGR.mSel==101?1.1f:1);
		DrawTextureS(gl, mGR.mTex_restart , 0.4f, -.05f,mGR.mSel==102?1.1f:1);
		DrawTextureS(gl, mGR.mTex_SplashIcn[0] 	, -.4f, -.30f,mGR.mSel==103?1.1f:1);
		DrawTextureS(gl, mGR.mTex_fb			, 0.0f, -.30f,mGR.mSel==104?1.1f:1);
		DrawTextureS(gl, mGR.mTex_SplashIcn[2] 	, 0.4f, -.30f,mGR.mSel==105?1.1f:1);
		
		if(!M.setBG)
			mGR.mTex_LingBar.drawFilp(gl, -.4f, -.30f);
		if(!M.setValue)
			mGR.mTex_LingBar.drawFilp(gl, 0.4f, -.30f);
		
		
		DrawTextureS(gl, mGR.mTex_Cross, .77f,0.36f,mGR.mSel==106?1.1f:1);
	}
	boolean Handle_Pause(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.4f, -.05f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 101;//Menu
		}
		if(CircRectsOverlap(0.4f, -.05f, mGR.mTex_restart.width()*.4f, mGR.mTex_restart.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 102;//Restart
		}
		if(CircRectsOverlap(-.4f, -.30f, mGR.mTex_fb.width()*.4f, mGR.mTex_fb.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 103;//sound
		}
		if(CircRectsOverlap(0.0f, -.30f, mGR.mTex_fb.width()*.4f, mGR.mTex_fb.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 104;//FB
		}
		if(CircRectsOverlap(0.4f, -.30f, mGR.mTex_fb.width()*.4f, mGR.mTex_fb.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 105;//Music
		}
		if(CircRectsOverlap(.77f,0.36f, mGR.mTex_fb.width()*.4f, mGR.mTex_fb.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 106;//Cross
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 101:
				Menu();
				break;
			case 102:
				if(!mGR.EndLess){
//					M.GameScreen = M.GAMESTART;
					setLoad();
					M.sound12(GameRenderer.mContext, R.drawable.power_up);
					mGR.EndLess = false;
					mGR.gx = -2f;
					sx = 10;
					sy = 0;
				}
				else{
					mGR.roadNo = M.mRand.nextInt(2);
					mGR.roadSide = M.mRand.nextInt(mGR.mTex_SideRoad.length);
					mGR.gameReset();
					mGR.EndLess = true;
					M.GameScreen = M.GAMEGO;
					M.sound13(GameRenderer.mContext, R.drawable.popup1);
					mGR.gx = 2f;
					sx=sy=0;
				}
				break;
			case 103:
				M.setBG=!M.setBG;
				break;
			case 104:
				facebook();
				break;
			case 105:
				M.setValue = !M.setValue;
				break;
			case 106:
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.game_theme);
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Pause End~~********************************/
	/*************************About Start********************************/
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
//		DrawTexture(gl, mGR.mTex_bgclor, 0,-.45f);
//		DrawTexture(gl, mGR.mTex_RoadPatrn, mGR.mRpatern.x, mGR.mRpatern.y);
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Cloud, mGR.mRMtion.x,0.8f);
		DrawTexture(gl, mGR.mTex_Cloud, mGR.mRMtion.y,0.75f);
//		DrawTexture(gl, mGR.mTex_Cartop, .07f, .41f+(Counter%2)*.005f);
		
//		if(Counter%2==0)
//			DrawTexture(gl, mGR.mTex_Wheel, -0.53f,0.23f);
		mGR.mRpatern.y +=.1f;mGR.mRpatern.x -=.25f;
		if(mGR.mRpatern.x<-.3f)
			mGR.mRpatern.set(1.8f, -.6f, 0);
		
		mGR.mRMtion.y -=.006f;mGR.mRMtion.x -=.006f;
		if(mGR.mRMtion.x<-1.9f)
			mGR.mRMtion.x = mGR.mRMtion.y+mGR.mTex_Cloud.width();
		if(mGR.mRMtion.y<-1.9f)
			mGR.mRMtion.y = mGR.mRMtion.x+mGR.mTex_Cloud.width();
			
		DrawTexture(gl, mGR.mTex_LvlBox, 0, -.43f);
		DrawTexture(gl, mGR.mTex_About , 0, -.43f);
		DrawTextureS(gl, mGR.mTex_Cross, 0.74f,-.15f,mGR.mSel==1?1.5f:1);
		
		if(M.GameScreen == M.GAMEHELP)
		{
			DrawTexture(gl, mGR.mTex_Help, 0, 0);
			DrawTexture(gl, mGR.mTex_Back, -.9f, 0.9f);
		}
	}
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(M.GameScreen == M.GAMEABOUT && CircRectsOverlap(0.74f,-.15f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;
		}
		if(M.GameScreen == M.GAMEHELP && CircRectsOverlap(0, 0, 2,2, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				Menu();
//				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.game_theme);
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************About End********************************/
	/*************************Menu Start********************************/
	
	boolean Handle_Join(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.76f,0.15f, mGR.mTex_Back.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 501;
		}
		if(CircRectsOverlap(0,-.52f, mGR.mTex_Join[0].width()*.4f, mGR.mTex_Join[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 502;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 501:
//				M.GameScreen = M.GAMEMENU;
				if(M.GameScreen == M.GAMEJOIN)
					Menu();
				else
					M.GameScreen = M.GAMELEVEL;
				break;
			case 502:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				if(M.GameScreen == M.GAMEJOIN)
					M.GameScreen = M.GAMEMENU;
				else
					M.GameScreen = M.GAMELEVEL;
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	void Menu()
	{
		mGR.mCoin[0].set(-2, -.17f);
		for(int i=0;i<mGR.mTex_SplashBtn.length;i++)
			mGR.mCoin[i+1].set(2,-.29f-i*.14f);
		
		for(int i=0;i<mGR.mTex_SplashIcn.length;i++)
			mGR.mCoin[i+5].set(10,-.29f-i*.14f);
		M.GameScreen = M.GAMEMENU;
	}
	
	
	void Draw_Menu(GL10 gl)
	{
//		DrawTexture(gl, mGR.mTex_bgclor, 0,-.45f);
//		DrawTexture(gl, mGR.mTex_RoadPatrn, mGR.mRpatern.x, mGR.mRpatern.y);
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Cloud, mGR.mRMtion.x,0.8f);
		DrawTexture(gl, mGR.mTex_Cloud, mGR.mRMtion.y,0.75f);
//		DrawTexture(gl, mGR.mTex_Cartop, .07f, .41f+(Counter%2)*.005f);
		
//		if(Counter%2==0)
//			DrawTexture(gl, mGR.mTex_Wheel, -0.53f,0.23f);
		mGR.mRpatern.y +=.1f;mGR.mRpatern.x -=.25f;
		if(mGR.mRpatern.x<-.3f)
			mGR.mRpatern.set(1.8f, -.6f, 0);
		
		mGR.mRMtion.y -=.006f;mGR.mRMtion.x -=.006f;
		if(mGR.mRMtion.x<-1.9f)
			mGR.mRMtion.x = mGR.mRMtion.y+mGR.mTex_Cloud.width();
		if(mGR.mRMtion.y<-1.9f)
			mGR.mRMtion.y = mGR.mRMtion.x+mGR.mTex_Cloud.width();
		
		
		DrawTexture(gl, mGR.mTex_Highway,mGR.mCoin[0].x-.2f,0.57f);
		if(mGR.mCoin[0].x<.23f){
			mGR.mCoin[0].x+=.2f;
		}
		else
			mGR.mCoin[0].x=.23f;
		
		for(int i=0;i<mGR.mTex_SplashBtn.length;i++){
			DrawTextureS(gl, mGR.mTex_SplashBtn[i], mGR.mCoin[i+1].x,-.29f-i*.14f,(mGR.mSel == i+1)?1.2f:1);
			if(mGR.mCoin[i+1].x>-.55f){
				mGR.mCoin[i+1].x-=.3f;
				break;
			}
			if(mGR.mCoin[i+1].x<-.55f){
				mGR.mCoin[i+1].x = -.55f;
				M.sound15(GameRenderer.mContext, R.drawable.swing2);
			}
		}
		
		for(int i=0;i<mGR.mTex_SplashIcn.length && mGR.mCoin[4].x <-.50f;i++){
			DrawTextureS(gl, mGR.mTex_SplashIcn[i],-.8f+i*.40f,-.92f,(mGR.mSel == i+5)?1.2f:mGR.mCoin[i+5].x);
			if(mGR.mCoin[i+5].x>1){
				mGR.mCoin[i+5].x*=.7f;
				break;
			}
			if(mGR.mCoin[i+5].x<1){
				mGR.mCoin[i+5].x = 1;
				M.sound19(GameRenderer.mContext, R.drawable.swing0);
				if(i == 4)
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
			}
		}
		if(mGR.mCoin[5].x<=1&&!M.setBG)
			mGR.mTex_LingBar.drawFilp(gl, -.8f+0*.40f,-.92f);
		if(mGR.mCoin[7].x<=1&&!M.setValue)
			mGR.mTex_LingBar.drawFilp(gl, -.8f+2*.40f,-.92f);
		
		if(M.GameScreen ==M.GAMEJOIN)
		{
			DrawTextureS(gl, mGR.mTex_LvlBox, 0, -.20f,sx);
			DrawTextureS(gl, mGR.mTex_Join[1] , 0, -.25f,sx);
			DrawTextureS(gl, mGR.mTex_Join[0] , 0, -.53f,mGR.mSel==502?1.1f:sx);
			DrawTextureS(gl, mGR.mTex_Cross, .76f,0.15f,mGR.mSel==501?1.1f:sx);
			if(sx<1){
				sx*=1.5f;
				if(sx > 1)
					sx = 1;
			}
			
		}
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<mGR.mTex_SplashBtn.length;i++){
			if(CircRectsOverlap(-.55f,-.29f-i*.14f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = i+1;
			}
		}
		for(int i=0;i<mGR.mTex_SplashIcn.length;i++){
			if(CircRectsOverlap(-.8f+i*.40f,-.92f, mGR.mTex_SplashIcn[0].width()*.4f, mGR.mTex_SplashIcn[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = i+5;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMELEVEL;
				mGR.roadNo = M.mRand.nextInt(2);
				mGR.roadSide = M.mRand.nextInt(mGR.mTex_SideRoad.length);
				if(!GameRenderer.mStart.isSignedIn()){
					M.GameScreen = M.GAMEJOIN2;
					join = .1f;
				}
				break;
			case 2:
				mGR.roadNo = M.mRand.nextInt(2);
				mGR.roadSide = M.mRand.nextInt(mGR.mTex_SideRoad.length);
				mGR.gameReset();
				mGR.EndLess = true;
				M.GameScreen = M.GAMEGO;
				M.sound13(GameRenderer.mContext, R.drawable.popup1);
				mGR.gx = 2f;
				sx=sy=0;
				break;
			case 3:
				M.GameScreen = M.GAMESHOP;
				M.sound13(GameRenderer.mContext, R.drawable.popup1);
				sx = .1f;
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();
//				Achivment
				break;
			case 5:
				M.setBG=!M.setBG;
				if(M.setBG)
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
				else
					M.stop(GameRenderer.mContext);
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 7:
				M.setValue = !M.setValue;
				break;
			case 8:
				MoreGame();
				break;
			case 9:
				M.GameScreen = M.GAMEABOUT;
				M.sound12(GameRenderer.mContext, R.drawable.power_up);
				break;
			}
			if(mGR.mSel!=0){
				if(mGR.mSel!=5 && mGR.mSel !=7)
					M.stop(GameRenderer.mContext);
				M.sound2(GameRenderer.mContext, R.drawable.click);
			}
			mGR.mSel = 0;
		}
		
		return true;
	}
	/*************************Menu End~~********************************/
	
	/*************************Shop Start********************************/
	final int car_price[]={0,2000,4000,7000,10000};
	final int car_power[]={2,3,4,5,7};
	float zoom = 1;
	void Draw_Shop(GL10 gl)
	{
//		DrawTexture(gl, mGR.mTex_Lvl, 0, 0);
		DrawTexture(gl, mGR.mTex_CarS, 0, 0);
		DrawTexture(gl, mGR.mTex_ShpT, 0, .82f);
		if(sx<.01f)sx=.01f;
		if(sx<1){sx*=1.5f;if(sx > 1)sx = 1;}
		DrawTexture(gl, mGR.mTex_ShopCar[mGR.car_No][0], 0,.54f);
		DrawTextureS(gl, mGR.mTex_ShopCar[mGR.car_No][1], 0,.27f,sx);
		DrawTexture(gl, mGR.mTex_RedCir, .42f,.42f);
		

		DrawTextureS(gl, mGR.mTex_Button	,-.6f,-.67f,mGR.mSel==4?1.1f:1);
		DrawTexture(gl, mGR.mTex_Back	,-.6f,-.67f);
		
		mGR.mTex_Button.drawSS(gl		,.6f, -.67f,mGR.mSel==3?1.1f:1,mGR.mSel==3?1.1f:1,180);
		
		
	
		if(mGR.car_No == mGR.car_Sel)
			DrawTexture(gl, mGR.mTex_RightMrk, .42f,.42f);
		
		if(mGR.car_buy[mGR.car_No]){
			if(mGR.car_No == mGR.car_Sel)
				DrawTexture(gl, mGR.mTex_RaceBtn,.6f, -.67f);
			else
				DrawTexture(gl, mGR.mTex_SelectB,.6f, -.67f);
		}
		else if(car_price[mGR.car_No]<=mGR.mTotalCoin)
			DrawTexture(gl, mGR.mTex_SelectB,.6f, -.67f);
		else
			DrawTexture(gl, mGR.mTex_Buy,.6f, -.67f);
		
		
		if(!mGR.car_buy[mGR.car_No])
		{
			DrawTexture(gl, mGR.mTex_BVlue	,-.34f,-.01f);
			DrawTexture(gl, mGR.mTex_Price	,+.34f,-.01f);
			drawNumber(gl, car_price[mGR.car_No]	, .12f, -.01f);
			if(sx >= 1)
				DrawTexture(gl, mGR.mTex_CLock,.17f, .35f);
		}
		
		
//		if(zoom <0.95f || sy==0){ sy = .003f;zoom = 0.95f;}
//		if(zoom >1.05f)			{ sy =-.003f;zoom = 1.05f;}
//		zoom+=sy;
		
		DrawTexture(gl, mGR.mTex_CashHnd,-.34f,-.16f);
		DrawTexture(gl, mGR.mTex_Price	,0.34f,-.16f);
		drawNumber(gl, mGR.mTotalCoin	,.12f, -.16f);
		
		
		DrawTexture(gl, mGR.mTex_Speed	,0.00f,-.40f);
		DrawTexture(gl, mGR.mTex_Cntorl ,-.04f,-.50f);
		for(int i=0;i<car_power[mGR.car_No]*3;i++)
		{
			DrawTexture(gl, mGR.mTex_SpdFill[0],-.13f+.03f*i, -.395f);
			DrawTexture(gl, mGR.mTex_SpdFill[1],-.13f+.03f*i, -.495f);
		}
		if(mGR.car_No >0 )
			DrawTextRS(gl, mGR.mTex_Arrow,-.8f, 0.28f,180,mGR.mSel == 1?1.2f:1,1);
		if(mGR.car_No <4 )
			DrawTextRS(gl, mGR.mTex_Arrow,0.8f, 0.28f,0,mGR.mSel == 2?1.2f:1,1);
		
//		DrawTransScal(gl, mGR.mTex_Back			,-.75f,-.45f,mGR.mSel == 4?1.2f:1,mGR.mSel == 4?.5f:1);
//		DrawTransScal(gl, mGR.mTex_SplashIcn[3]	, .75f,-.45f,mGR.mSel == 5?1.2f:1,mGR.mSel == 5?.5f:1);
		
		
		
	}
	boolean Handle_Shop(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.8f, 0.28f, mGR.mTex_Arrow.width()*.4f, mGR.mTex_Arrow.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.8f, 0.28f, mGR.mTex_Arrow.width()*.4f, mGR.mTex_Arrow.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.6f, -.67f, mGR.mTex_Buy.width()*.9f, mGR.mTex_Buy.Height()*.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.6f, -.67f, mGR.mTex_Buy.width()*.9f, mGR.mTex_Buy.Height()*.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				if(mGR.car_No >0 ){
					mGR.car_No--;
					sx = .1f;
				}
				break;
			case 2:
				if(mGR.car_No <4 ){
					mGR.car_No++;
					sx = .1f;
				}
				break;
			case 3:
				if(mGR.car_buy[mGR.car_No])
				{
					if(mGR.car_No == mGR.car_Sel)
						M.GameScreen = M.GAMELEVEL;
					else
						mGR.car_Sel=mGR.car_No;
				}
				else if(car_price[mGR.car_No]<=mGR.mTotalCoin)
				{
					mGR.car_buy[mGR.car_No] = true;
					mGR.car_Sel=mGR.car_No;
					mGR.mTotalCoin-=car_price[mGR.car_No];
				}
				else
				{
					GameRenderer.mStart.buy();
				}
				break;
			case 4:
				Menu();
				break;
			case 5:
				MoreGame();
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		
		return true;
	}
	/*************************Shop End~~********************************/
	/*************************Level Screen Start********************************/
	final float map[][][]={
			{{0.54f,0.00f,-.55f,0.01f,0.53f,-.48f,0.42f,-.25f,0.55f,-.50f},
			 {0.45f,0.46f,0.29f,0.17f,0.04f,-.06f,-.26f,-.40f,-.64f,-.68f}
			},{{0.56f,-.56f,-.56f,0.02f,0.49f,-.09f,-.57f,0.59f,0.38f,-.55f},
			   {0.46f,0.44f,0.01f,0.28f,0.00f,-.21f,-.42f,-.37f,-.65f,-.70f}
			},{{0.41f,-.56f,0.03f,0.63f,0.62f,0.39f,0.02f,-.54f,-.51f,-.54f},
			   {0.46f,0.46f,0.20f,0.22f,-.25f,-.61f,-.13f,0.01f,-.33f,-.72f}
			}
	};
	float mapx,mapvx,mapy;
	void Draw_Level(GL10 gl)
	{
		mapy =-.14f;
		DrawTexture(gl, mGR.mTex_CarS, 0, 0);
		
		DrawTexture(gl, mGR.mTex_MapT, 0, .82f);
		DrawTexture(gl, mGR.mTex_LvlScr[mGR.levelScr], mapx,mapy);
		if(mapvx < 0){
			DrawTexture(gl, mGR.mTex_LvlScr[mGR.levelScr+1], mapx+2,mapy);
			drawNumberScal(gl, mGR.levelScr+2, mapx+2, mapy, 2, 1);
		}
		else if(mapvx > 0){
			DrawTexture(gl, mGR.mTex_LvlScr[mGR.levelScr-1], mapx-2,mapy);
			drawNumberScal(gl, mGR.levelScr+0, mapx-2, mapy, 2, 1);
		}
		if(mGR.levelScr>0)
			DrawTextRS(gl, mGR.mTex_Arrow, -.9f,0,180,1,1);
		if(mGR.levelScr<2)
			DrawTextRS(gl, mGR.mTex_Arrow, +.9f,0,  0,1,1);
		if(mapvx!=0){
			mapx+=mapvx;
			if(mapvx < 0 && mapx <-2)
			{
				mapx = 0;
				mapvx = 0;
				mGR.levelScr++;
			}
			if(mapvx > 0 && mapx > 2)
			{
				mapx = 0;
				mapvx = 0;
				mGR.levelScr--;
			}
		}
		if(sx <0.8f){ sy = .01f;sx = 0.8f;}
		if(sx >1.3f){ sy =-.01f;sx = 1.3f;}
		sx+=sy;
		
////		DrawTextureS(gl, mGR.mTex_MapDot, ssx, ssy,1);
//		mGR.levelScr = 1;
		for(int j =0;j<map[0][0].length;j++){
			if(mGR.mULevel>(mGR.levelScr*10+j)){
				if(mGR.mULevel-1==(mGR.levelScr*10+j)){
					DrawTextureS(gl, mGR.mTex_MapDot, mapx+map[mGR.levelScr][0][j], map[mGR.levelScr][1][j],mGR.mSel-1==j?1.5f:sx);
					drawNumberScal(gl, (mGR.levelScr*10)+(j+1),mapx+ map[mGR.levelScr][0][j]-(((mGR.levelScr*10)+j)<9?0:.02f), map[mGR.levelScr][1][j], sx-0.5f, 1);
				}
				else{
					DrawTextureS(gl, mGR.mTex_MapDot, mapx+map[mGR.levelScr][0][j], map[mGR.levelScr][1][j],mGR.mSel-1==j?1.5f:1);
					drawNumberScal(gl, (mGR.levelScr*10)+(j+1),mapx+ map[mGR.levelScr][0][j]-(((mGR.levelScr*10)+j)<9?0:.02f), map[mGR.levelScr][1][j], 0.5f, 1);
				}
				
				for(int k=0;k<3&&mGR.mLStar[j]>0;k++)
				{
					DrawTextureS(gl, mGR.mTex_bStar, mapx+map[mGR.levelScr][0][j]+(-.08f+k*.08f), map[mGR.levelScr][1][j]-.06f,.35f);
					if(mGR.mLStar[j]>k)
						DrawTextureS(gl, mGR.mTex_star, mapx+map[mGR.levelScr][0][j]+(-.08f+k*.08f), map[mGR.levelScr][1][j]-.06f,.3f);
				}
			}else{
				DrawTextureS(gl, mGR.mTex_Lock, mapx+map[mGR.levelScr][0][j], map[mGR.levelScr][1][j],mGR.mSel-1==j?1.5f:1);
			}
			
		}

		DrawTextureS(gl, mGR.mTex_Button,.64f ,-.86f,mGR.mSel==102?1.2f:1);
		DrawTextureS(gl, mGR.mTex_Back	,.64f ,-.86f,mGR.mSel==102?1.2f:1);
		if(M.GameScreen ==M.GAMEJOIN2)
		{
			DrawTextureS(gl, mGR.mTex_LvlBox, 0, -.20f,join);
			DrawTextureS(gl, mGR.mTex_Join[1] , 0, -.25f,join);
			DrawTextureS(gl, mGR.mTex_Join[0] , 0, -.53f,mGR.mSel==502?1.1f:join);
			DrawTextureS(gl, mGR.mTex_Cross, .76f,0.15f,mGR.mSel==501?1.1f:join);
			if(join<1){
				join*=1.5f;
				if(join > 1)
					join = 1;
			}
			
		}
	}
	float join = .1f;
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.9f,0, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 100;
		}
		if(CircRectsOverlap(0.9f,0, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 101;
		}
		if(CircRectsOverlap(.64f ,-.86f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 102;
		}
		
		for(int j =0;j<map[0][0].length;j++){
			if(mGR.mULevel>(mGR.levelScr*10+j))
			{
				if(CirCir(mapx+map[mGR.levelScr][0][j], map[mGR.levelScr][1][j], .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
					mGR.mSel = j+1;
				}
				
			}
		}
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:
			case 6:case 7:case 8:case 9:case 10:
				mGR.mLevel = (mGR.levelScr*10) + mGR.mSel-1;
//				M.GameScreen = M.GAMESTART;
				setLoad();
				M.sound12(GameRenderer.mContext, R.drawable.power_up);
				mGR.EndLess = false;
				mGR.gx = -2f;
				sx = 10;
				sy = 0;
				break;
			case 100:
				if(mapvx == 0 && mGR.levelScr>0){
					mapvx=0.2f;
				}
				break;
			case 101:
				if(mapvx == 0 && mGR.levelScr<2)
				{
					mapvx=-.2f;
				}
				break;
			case 102:
				Menu();
				break;
			
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		
		return true;
	}
	/*************************Level Screen End~~********************************/
	
	/*************************Game Start Screen********************************/
	void Draw_GameStart(GL10 gl)
	{
		if(rz>1)
			rzv = -.1f;
		if(rz<0.1)
			rzv = 0.1f;
		rz += rzv;
		if(Counter %4 == 0)
			Counter2++;
		for(int i=0;i<mGR.mRaodY.length;i++){
			mGR.mTex_Road[mGR.roadNo].drawSS(gl, -.0f, mGR.mRaodY[i],1,1,180);
			DrawTransScal(gl, mGR.mTex_Road[mGR.roadNo], -.0f, mGR.mRaodY[i],1,rz);
		}
		for(int i=0;i<mGR.mSRaodY.length;i++){
			
			DrawTexture(gl, mGR.mTex_SideRoad[mGR.roadSide][i%2], -.85f, mGR.mSRaodY[i]);
			mGR.mTex_SideRoad[mGR.roadSide][i%2].drawSS(gl	  , 0.85f, mGR.mSRaodY[i],1,1,180);
		}
		if(M.LEVEL[mGR.mLevel][1]==0){
			DrawTransScal(gl, mGR.mTex_Bar, -.7f,.94f,1,.5f);
			DrawTransScal(gl, mGR.mTex_Target[1],-.9f,.94f,.6f,.5f);
			drawNumberScal(gl, mGR.mNoCoin, -.78f, .94f,.6f,.5f);
		}
		else if(Counter2 % 2 == 0)
		{
			DrawTexture(gl, mGR.mTex_Bar, -.7f,.94f);
			DrawTextureS(gl, mGR.mTex_Target[1],-.9f,.94f,.6f);
			drawNumberScal(gl, mGR.mNoCoin, -.78f, .94f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][4]==0){
				DrawTransScal(gl, mGR.mTex_Bar, -.2f,.94f,1,.5f);
				DrawTransScal(gl, mGR.mTex_Target[4],-.4f,.94f,.6f,.5f);
				drawNumberScal(gl, mGR.mNoDimond, -.28f, .94f,.6f,.5f);
		}
		else if(Counter2 % 2 == 0)
		{
			DrawTexture(gl, mGR.mTex_Bar, -.2f,.94f);
			DrawTextureS(gl, mGR.mTex_Target[4],-.4f,.94f,.6f);
			drawNumberScal(gl, mGR.mNoDimond, -.28f, .94f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][0]>0 && (Counter2 % 2 == 0)){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[0],-.9f,.84f,.6f);
			drawNumberScal(gl, (int)mGR.mMeter, -.78f, .84f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][2]>0&& (Counter2 % 2 == 0)){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[2],-.9f,.84f,.6f);
			drawNumberScal(gl, mGR.mBus, -.78f, .84f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][3]>0 && (Counter2 % 2 == 0)){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[3],-.9f,.84f,.6f);
			drawNumberScal(gl, mGR.mKill, -.78f, .84f,.6f,1);
		}
		DrawTexture(gl, mGR.mTex_LvlBox	,mGR.gx+.00f, .0f);
		if(M.GameScreen == M.GAMESTART){
			DrawTexture(gl, mGR.mTex_LvlTxt	,mGR.gx+.00f, .32f);
			drawNumber(gl, mGR.mLevel+1		,mGR.gx+.38f, .32f);
			DrawTexture(gl, mGR.mTex_Go		,mGR.gx+.00f,-.2f);
			if (mGR.gx >= 0) {
				int type = 0;
				if (M.LEVEL[mGR.mLevel][0] > 0) {type = 0;}
				if (M.LEVEL[mGR.mLevel][1] > 0) {type = 1;}
				if (M.LEVEL[mGR.mLevel][2] > 0) {type = 2;}
				if (M.LEVEL[mGR.mLevel][3] > 0) {type = 3;}
				if (M.LEVEL[mGR.mLevel][4] > 0) {type = 4;}
				mGR.gx = 0;
				if(sy == 0)
					DrawTextureS(gl, mGR.mTex_Target[type], -.3f, 0, sx);
				else if(sy > 0)
					DrawTextureS(gl, mGR.mTex_Target[type], -.3f, 0, 1);
				if(sy == 1)
					DrawTextureS(gl, mGR.mTex_X, -.1f, 0, sx-.3f);
				else if(sy > 1)
					DrawTextureS(gl, mGR.mTex_X, -.1f, 0, 1-.3f);
				if(sy == 2)
					drawNumberScal(gl, M.LEVEL[mGR.mLevel][type], 0, 0, sx, 1);
				else if(sy > 2)
					drawNumberScal(gl, M.LEVEL[mGR.mLevel][type], 0, 0, 1, 1);
				if (sy < 3) {
					sx *= .8f;
					if (sx <= 1) {
						M.sound19(GameRenderer.mContext, R.drawable.swing1);
						sy++;
						sx = 10;
					}
				}
			}else{
				mGR.gx+=.1f;
				if(mGR.gx>=0){
					M.sound19(GameRenderer.mContext, R.drawable.swing1);
					
				}
			}
		}
		else
		{
			DrawTexture(gl, mGR.mTex_PowerTxt	,mGR.gx+.00f, .32f);
			DrawTexture(gl, mGR.mTex_Cross		,mGR.gx+.78f, .36f);
			DrawTexture(gl, mGR.mTex_Goggles	,mGR.gx-.50f,-.15f);
			DrawTexture(gl, mGR.mTex_Magnet		,mGR.gx+.00f,-.15f);
			DrawTexture(gl, mGR.mTex_ARadar		,mGR.gx+.50f,-.13f);
			for(int i =0;i<3;i++){
				if(mGR.mPower[i])
					DrawTexture(gl, mGR.mTex_Free	,mGR.gx-.51f+(i*.508f),-.33f);
				else
					drawNumberScal(gl, 300			,mGR.gx-.58f+(i*.508f),-.33f,.7f,1);
				if(mGR.mPlayer.mPower[i])
					DrawTexture(gl, mGR.mTex_RightMrk,mGR.gx-.58f+(i*.508f),-.08f);
			}
			if(mGR.gx<=0){
				mGR.gx =0;
				if(Counter%50 < 25)
					drawNumberScal(gl, mGR.mGoCount+1,mGR.gx, .6f,.7f+(Counter%50)*.07f,1);
				else
					drawNumberScal(gl, mGR.mGoCount+1,mGR.gx, .6f,.7f+(50-Counter%50)*.07f,1);
				if(Counter%50==0){
					if(mGR.mGoCount>0){
						mGR.mGoCount--;
						M.sound8(GameRenderer.mContext, R.drawable.count_down_sound);
					}
					else{
						if(mGR.mLevel == 0){
							M.GameScreen = M.GAMEHELP;
						}
						else{
							M.GameScreen = M.GAMEPLAY;
							M.play(GameRenderer.mContext, R.drawable.game_theme);
						}
					}
				}
			}else
			{
				mGR.gx-=.1f;
			}
//			DrawTransScal(gl, mGR.mTex_Road[0],0,0,1,mGR.mPlayer.mPower[0]?sx:1);
		}
	}
	boolean Handle_GameStart(MotionEvent event)
	{
		mGR.mSel = 0;
		if(M.GameScreen == M.GAMESTART &&
				CircRectsOverlap(mGR.gx+.00f,-.2f, mGR.mTex_Go.width()*.4f, mGR.mTex_Go.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;
		}
		if(M.GameScreen == M.GAMEGO ){
			if(CircRectsOverlap(mGR.gx+.78f, .36f, mGR.mTex_Cross.width(), mGR.mTex_Cross.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = 2;
			}
			if(CircRectsOverlap(mGR.gx-.50f,-.15f, mGR.mTex_Go.width()*.6f, mGR.mTex_Go.Height()*.6f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = 3;
			}
			if(CircRectsOverlap(mGR.gx-.00f,-.15f, mGR.mTex_Go.width()*.6f, mGR.mTex_Go.Height()*.6f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = 4;
			}
			if(CircRectsOverlap(mGR.gx+.50f,-.15f, mGR.mTex_Go.width()*.6f, mGR.mTex_Go.Height()*.6f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = 5;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEGO;
				M.sound13(GameRenderer.mContext, R.drawable.popup1);
				mGR.gx = 2f;
				sx=sy=0;
				mGR.gameReset();
				break;
			case 2:
				if(mGR.mLevel == 0){
					M.GameScreen = M.GAMEHELP;
				}
				else{
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext, R.drawable.game_theme);
				}
				break;
			case 3:case 4:case 5:
				if(!mGR.mPlayer.mPower[mGR.mSel-3]){
					if(mGR.mPower[mGR.mSel-3])
					{
						mGR.mPlayer.mPower[mGR.mSel-3] = true;
					}
					else if(mGR.mTotalCoin >=300)
					{
						mGR.mTotalCoin-=300;
						mGR.mPlayer.mPower[mGR.mSel-3] = true;
					}
					else
					{
						GameRenderer.mStart.buy();
					}
				}
				//Googles
				//Magnet
				//Police
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game Start Screen End~~********************************/
	/*************************Game PLay Start********************************/
	
	void BlastAnimation(GL10 gl)
	{
		for (int i = 0; i < mGR.mBAnim.length; i++) {
			if (mGR.mBAnim[i].x > -1 && mGR.mBAnim[i].x < 1 && mGR.mBAnim[i].y > -1 && mGR.mBAnim[i].y < 1
					&& mGR.mBAnim[i].img < mGR.mTex_Blast.length) {
				DrawTexture(gl, mGR.mTex_Blast[mGR.mBAnim[i].img], mGR.mBAnim[i].x,mGR.mBAnim[i].y);
				mGR.mBAnim[i].update();
			}
		}
		if(mGR.mCoinAnim.x>.1f && mGR.mCoinAnim.y<1f)
		{
			DrawTransScal(gl,mGR.mTex_Coin[0],-.2f,-.6f+mGR.mCoinAnim.y,1.5f,mGR.mCoinAnim.x);
			DrawTransScal(gl,mGR.mTex_Pls 	,-.0f ,-.6f+mGR.mCoinAnim.y,1.0f,mGR.mCoinAnim.x);
			drawNumberScal(gl, mGR.mCoinAnim.counter,0.1f,-.6f+mGR.mCoinAnim.y,1.0f,mGR.mCoinAnim.x);
			
			DrawTransScal(gl,mGR.mTex_Diamond2	,-.2f,-.7f+mGR.mCoinAnim.y,1.5f,mGR.mCoinAnim.x);
			DrawTransScal(gl,mGR.mTex_Pls 		,-.0f,-.7f+mGR.mCoinAnim.y,1.5f,mGR.mCoinAnim.x);
			drawNumberScal(gl,20-mGR.mCoinAnim.counter	,0.1f,-.7f+mGR.mCoinAnim.y,1.0f,mGR.mCoinAnim.x);
			mGR.mCoinAnim.y+=.02f;
			mGR.mCoinAnim.x-=.04f;
		}
		if(balsc > 0 && Counter%5==0)
			setBlastAnim(mmx, mmy, 0);
	}
	int balsc =0;
	float mmx,mmy;
	void setBlastAnim(float x,float y,int count)
	{
		mmx = x;mmy=y;
		for(int i=0,cot = 0;i<mGR.mBAnim.length&&cot<12;i++){
			if(mGR.mBAnim[i].x <=-1 || mGR.mBAnim[i].x >= 1||mGR.mBAnim[i].y <=-1||mGR.mBAnim[i].y>=1||mGR.mBAnim[i].img >=mGR.mTex_Blast.length){				
				mGR.mBAnim[i].set(x, y);
				cot++;
			}
		}
		if(count ==-1)
		{
			M.sound1(GameRenderer.mContext, R.drawable.blast);
			balsc = 3;
		}
		balsc--;
	}
	final int val[] = {10000,25000,5000,12000,100,500,100,500,1000,3000};
	final int ID[] = {  
	  R.string.achievement_drive_10000_meter,
	  R.string.achievement_drive_25000_meter,
	  R.string.achievement_collect_5000_coin,
	  R.string.achievement_collect_12000_coin,
	  R.string.achievement_collide_100_coin_boat,
	  R.string.achievement_collide_500_coin_boat,
	  R.string.achievement_blast_100_opponent_car,
	  R.string.achievement_blast_500_opponent_car,
	  R.string.achievement_collect_1000_diamond,
	  R.string.achievement_collect_3000_diamond,
	};
	
	
	void Achivment()
	{
		
		if (!mGR.addFree)
		{
			GameRenderer.mStart.show();
		}
		Counter =0;
		mGR.Total[0]+= (int)mGR.mMeter;
		mGR.Total[1]+=mGR.mNoCoin;
		mGR.Total[2]+= mGR.mBus;
		mGR.Total[3]+= mGR.mKill;
		mGR.Total[4]+= mGR.mNoDimond;
		
		for(int i=0;i<10;i++){
			if(!mGR.Achiv[i] && mGR.Total[i/2]>=val[i])
			{
				mGR.Achiv[i] = true;
				GameRenderer.mStart.UnlockAchievement(ID[i]);
			}
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score);
	}
	
	void gameover()
	{
		M.stop(GameRenderer.mContext);
		mGR.mPlayer.blast =0;
		M.GameScreen = M.GAMEOVER;
		mGR.gx = 2f;
		mGR.mTempCoin = mGR.mTotalCoin;
		mGR.mTotalCoin+=mGR.mNoCoin;
		M.sound9(GameRenderer.mContext, R.drawable.gameover);
		Achivment();
	}
	void gameWin()
	{
		M.stop(GameRenderer.mContext);
		M.GameScreen = M.GAMEWIN;
		mGR.gx = 2;
		if(mGR.mULevel-1 <= mGR.mLevel)
		{
			mGR.mULevel = mGR.mLevel+2;
		}
		mGR.mTempCoin = mGR.mTotalCoin;
		mGR.mTotalCoin+=mGR.mNoCoin;
		sx =10;
		sy =0;
		M.sound10(GameRenderer.mContext, R.drawable.level_complte);
		Achivment();
		if(mGR.mLStar[mGR.mLevel]<mGR.mPlayer.collide)
			mGR.mLStar[mGR.mLevel] =(byte)mGR.mPlayer.collide;
	}
	void gameLogic()
	{
		mGR.mMeter -=M.Speed*100;
		if(M.Speed > -.03f)
			M.Speed-=.00001f;
		for(int i=0;i<mGR.mRaodY.length;i++){
			mGR.mRaodY[i]+=M.Speed;
		}
		for(int i=0;i<mGR.mSRaodY.length;i++){
			mGR.mSRaodY[i]+=M.Speed;
		}
		for(int i=0;i<mGR.mRaodY.length;i++){
			if(mGR.mRaodY[i]<-1.5f)
			{
				mGR.mRaodY[i] = mGR.mRaodY[i==0?(mGR.mRaodY.length-1):(i-1)]+mGR.mTex_Road[mGR.roadNo].Height();
			}
		}
		for(int i=0;i<mGR.mSRaodY.length;i++){
			if(mGR.mSRaodY[i]<-1.6f)
			{
				mGR.mSRaodY[i] = mGR.mSRaodY[i==0?(mGR.mSRaodY.length-1):(i-1)]+mGR.mTex_SideRoad[0][0].Height();
			}
		}
		for(int i=0;i<mGR.mOpp.length;i++){
			if(mGR.mOpp[i].y > -2)
			{
				for(int j=0;j<mGR.mOpp.length;j++){
					if(i!=j && mGR.mOpp[i].x == mGR.mOpp[j].x)
					{
						if(CircRectsOverlap(mGR.mOpp[i].x,mGR.mOpp[i].y ,mGR.mTex_OppCar[0].width()*.5f, mGR.mTex_OppCar[0].Height()*.5f,
								mGR.mOpp[j].x,mGR.mOpp[j].y ,mGR.mTex_OppCar[0].width()*.5f))
						{
							if(mGR.mOpp[i].spd < mGR.mOpp[j].spd )
								mGR.mOpp[i].spd = mGR.mOpp[j].spd;
							else
								mGR.mOpp[j].spd = mGR.mOpp[i].spd;
							if(mGR.mOpp[j].y>mGR.mOpp[i].y)
								mGR.mOpp[j].y +=.01f;
							else
								mGR.mOpp[i].y +=.01f;
						}
					}
					
				}
				
				if(mGR.mOpp[i].no == 10){
					if(CirCir(mGR.mOpp[i].x,mGR.mOpp[i].y ,mGR.mTex_police.width()*.5f, 
							mGR.mPlayer.x,mGR.mPlayer.y ,mGR.mTex_PlyCar[0][0].width()*.8f) && M.GameScreen != M.GAMEOVER){
						if(mGR.mPlayer.life >0){
//							mGR.mBlast.set(mGR.mOpp[i].x,mGR.mOpp[i].y,0);
							setBlastAnim(mGR.mOpp[i].x,mGR.mOpp[i].y,-1);
							mGR.mCoinAnim.set(-1, 1, -1);
							mGR.mPlayer.life--;
							mGR.mOpp[i].x=mGR.mOpp[i].y=-2f;
							if(mGR.mPlayer.collide>1)
								mGR.mPlayer.collide--;
						}
						else
							gameover();
					}
				}
				
				if(Rect2RectIntersection(mGR.mOpp[i].x,mGR.mOpp[i].y ,mGR.mTex_OppCar[mGR.mOpp[i].no].width()*.5f, mGR.mTex_OppCar[mGR.mOpp[i].no].Height()*.5f, 
						mGR.mPlayer.x,mGR.mPlayer.y ,mGR.mTex_PlyCar[0][0].width()*.5f, mGR.mTex_PlyCar[0][0].Height()*.5f) && M.GameScreen != M.GAMEOVER)
				{
					if(mGR.mOpp[i].no == 11){
						setAnimation(mGR.mOpp[i].x,mGR.mOpp[i].y);
//						mGR.mBlast.set(mGR.mOpp[i].x,mGR.mOpp[i].y,0);
//						setBlastAnim(mGR.mOpp[i].x,mGR.mOpp[i].y);
						mGR.mCoinAnim.set(1, 0, 20 - M.mRand.nextInt(6));
						mGR.mOpp[i].x=mGR.mOpp[i].y=-2f;
						mGR.mBus++;
						mGR.mNoCoin+=mGR.mCoinAnim.counter;
						mGR.mNoDimond+=20-mGR.mCoinAnim.counter;
						M.sound7(GameRenderer.mContext, R.drawable.collect_sound);
					}
					else{
						if(mGR.mPlayer.life >0){
//							mGR.mBlast.set(mGR.mOpp[i].x,mGR.mOpp[i].y,0);
							setBlastAnim(mGR.mOpp[i].x,mGR.mOpp[i].y,-1);
							mGR.mCoinAnim.set(-1, 1, -1);
							mGR.mPlayer.life--;
							mGR.mOpp[i].x=mGR.mOpp[i].y=-2f;
							if(mGR.mPlayer.collide>1)
								mGR.mPlayer.collide--;
						}
						else
							gameover();
					}
				}
				mGR.mOpp[i].y+=(M.Speed+mGR.mOpp[i].spd);
			}
		}
		
//		int newCar = (M.Speed > -0.015f) ? 30 :(30+(int)(M.Speed*200));
		
		int newCar = (32+(int)(M.Speed*200));
		if(newCar < 15)
			newCar =15;
		for(int i=0;i<mGR.mOpp.length && Counter%newCar==0;i++){
			if(mGR.mOpp[i].y < -1.9)
			{
				int path =M.mRand.nextInt(9)+3;
				mGR.mOpp[i].set(-0.93f+0.13f*(path), 1.5f, 0.001f+M.mRand.nextInt(40)/10000f,M.mRand.nextInt(mGR.mTex_OppCar.length));
				mGR.mCarCount++;
				if(mGR.mCarCount%60 == 0)
					mGR.mOpp[i].no = 9;
//				if(mGR.mCarCount%30 == 10)
//					mGR.mOpp[i].no = 10;
				if(mGR.mCarCount%60 == 30)
					mGR.mOpp[i].no = 11;
				if(mGR.mPlayer.mPower[2] && mGR.mOpp[i].no == 10)
					mGR.mOpp[i].no = 0;
				
				if(path <3)
					mGR.mOpp[i].spd = M.Speed *2;
				break;
			}
		}
		if(mGR.mPlayer.dir != 0 || mGR.mPlayer.vx !=0){
			mGR.mPlayer.x+=mGR.mPlayer.vx;
			if(mGR.mPlayer.x < -0.67f && mGR.mPlayer.vx < 0)
			{
				mGR.mPlayer.x = -0.67f;
				mGR.mPlayer.vx =0;
				mGR.mPlayer.len =0;
			}
			if(mGR.mPlayer.x > -0.93f+0.13f*12 && mGR.mPlayer.vx > 0)
			{
				mGR.mPlayer.x = -0.93f+0.13f*12;
				mGR.mPlayer.vx =0;
				mGR.mPlayer.len =13;
			}
			if(mGR.mPlayer.dir == 0 )
			{
				if(mGR.mPlayer.x < -0.93f+0.13f*mGR.mPlayer.len && mGR.mPlayer.vx < 0)
				{
					mGR.mPlayer.x = -0.93f+0.13f*mGR.mPlayer.len;
					mGR.mPlayer.vx =0;
				}
				if(mGR.mPlayer.x > -0.93f+0.13f*mGR.mPlayer.len && mGR.mPlayer.vx > 0)
				{
					mGR.mPlayer.x = -0.93f+0.13f*mGR.mPlayer.len;
					mGR.mPlayer.vx = 0;
				}
			}
		}
		for(int i =0;i<mGR.mCoin.length;i++){
			mGR.mCoin[i].y+=M.Speed;
			if(mGR.mPlayer.y>-.9 && mGR.mPlayer.y <0){
				if(Rect2RectIntersection(mGR.mCoin[i].x,mGR.mCoin[i].y ,mGR.mTex_Coin[0].width()*.8f, mGR.mTex_Coin[0].Height(), 
						mGR.mPlayer.x,mGR.mPlayer.y ,mGR.mTex_PlyCar[0][0].width()*.8f, mGR.mTex_PlyCar[0][0].Height()) && M.GameScreen != M.GAMEOVER)
				{
					mGR.mCoin[i].y = -2f;
					mGR.mNoCoin++;
					M.sound3(GameRenderer.mContext, R.drawable.coin);
				}
			}
			if(mGR.mCoin[i].y<.7 && mGR.mMagnet.counter>0 && mGR.mCoin[i].y>=mGR.mPlayer.y)
			{
				if(mGR.mCoin[i].x > mGR.mPlayer.x && mGR.mCoin[i].counter <4)
				{
					mGR.mCoin[i].x-=.15f;
				}
				if(mGR.mCoin[i].x < mGR.mPlayer.x && mGR.mCoin[i].counter <4)
				{
					mGR.mCoin[i].x+=.15f;
				}
				if(mGR.mCoin[i].counter >3)
					mGR.mCoin[i].x = mGR.mPlayer.x;
				mGR.mCoin[i].y-=.1f;
				mGR.mCoin[i].counter++;
			}
		}
		if(mGR.mCoin[mGR.mCoin.length-1].y<-1.2f)
		{
			int rand = M.mRand.nextInt(9)+2;
			for(int i =0;i<mGR.mCoin.length;i++){
				mGR.mCoin[i].set(-0.93f+0.13f*(rand+(i%2)), 1+i*.05f);
			}
		}
		if(Rect2RectIntersection(mGR.mDimond.x,mGR.mDimond.y ,mGR.mTex_Coin[0].width()*.8f, mGR.mTex_Coin[0].Height(), 
				mGR.mPlayer.x,mGR.mPlayer.y ,mGR.mTex_PlyCar[0][0].width()*.8f, mGR.mTex_PlyCar[0][0].Height()) && M.GameScreen != M.GAMEOVER)
		{
			mGR.mDimond.y = -2f;
			mGR.mNoDimond++;
			M.sound9(GameRenderer.mContext, R.drawable.item_collect);
		}
		if(Rect2RectIntersection(mGR.mMagnet.x,mGR.mMagnet.y ,mGR.mTex_Coin[0].width()*.8f, mGR.mTex_Coin[0].Height(), 
				mGR.mPlayer.x,mGR.mPlayer.y ,mGR.mTex_PlyCar[0][0].width()*.8f, mGR.mTex_PlyCar[0][0].Height()) && M.GameScreen != M.GAMEOVER)
		{
			M.sound11(GameRenderer.mContext, R.drawable.impact);
			mGR.mMagnet.y = -2f;
			mGR.mMagnet.counter=100;
			if(mGR.mPlayer.mPower[1])
				mGR.mMagnet.counter+=100;
		}
		if(mGR.mMagnet.counter>0){
			mGR.mMagnet.counter--;
			if(mGR.mDimond.y<.7 && mGR.mMagnet.counter>0)
			{
				if(mGR.mDimond.x > mGR.mPlayer.x && mGR.mDimond.counter <4)
				{
					mGR.mDimond.x-=.15f;
				}
				if(mGR.mDimond.x < mGR.mPlayer.x && mGR.mDimond.counter <4)
				{
					mGR.mDimond.x+=.15f;
				}
				if(mGR.mDimond.counter >3)
					mGR.mDimond.x = mGR.mPlayer.x;
				mGR.mDimond.y-=.1f;
				mGR.mDimond.counter++;
			}
		}
		if(mGR.mDimond.y<-1.7f)
			mGR.mDimond.set(-0.93f+0.13f*( M.mRand.nextInt(13)), 2);
		if(mGR.mMagnet.y<-1.7f){
			mGR.mMagnet.x=-0.93f+0.13f*( M.mRand.nextInt(13));
			mGR.mMagnet.y=3;
		}
		mGR.mDimond.y+=M.Speed;
		mGR.mMagnet.y+=M.Speed;
		if(M.LEVEL[mGR.mLevel][0] <=mGR.mMeter && M.LEVEL[mGR.mLevel][1] <=mGR.mNoCoin && M.LEVEL[mGR.mLevel][2] <=mGR.mBus
			&& M.LEVEL[mGR.mLevel][3] <=mGR.mKill && M.LEVEL[mGR.mLevel][4] <=mGR.mNoDimond && mGR.finish == 2)
		{
			mGR.finish = 1.1f; 
		}
		if(mGR.finish < 1.2f){
			mGR.finish+=M.Speed;
			if(mGR.finish<mGR.mPlayer.y)
			{
				
				if(!mGR.EndLess)
					gameWin();
			}
		}
		if(mGR.mFire.count>0 && mGR.mFire.count<30)
		{
			
			if(mGR.mFire.count==1)
			{
				mGR.mFire.i =- 1;
				float y = -.9f;
				for(int i=0;i<mGR.mOpp.length;i++){
					if(mGR.mOpp[i].y > -.9 && mGR.mOpp[i].y <.9 && mGR.mOpp[i].x > -.9 && mGR.mOpp[i].x <.9 && mGR.mOpp[i].no == 9)
					{
						if(mGR.mOpp[i].y > y){
							y = mGR.mOpp[i].y;
							mGR.mFire.i = i;
							mGR.mFire.x = mGR.mPlayer.x;
							mGR.mFire.y = -.7f;
						}
					}
				}
			}
//			if(mGR.mFire.count>10)
			{
				mGR.mFire.vz = -.3f;
				if( mGR.mFire.i >=0){
					if(CirCir(mGR.mFire.x, mGR.mFire.y, mGR.mTex_Magnet2.Height(), mGR.mOpp[mGR.mFire.i].x, mGR.mOpp[mGR.mFire.i].y, mGR.mTex_Magnet2.Height()))
					{
						mGR.mFire.x = mGR.mOpp[mGR.mFire.i].x;
						mGR.mFire.y = mGR.mOpp[mGR.mFire.i].y;
						setBlastAnim(mGR.mOpp[mGR.mFire.i].x,mGR.mOpp[mGR.mFire.i].y,-1);
						mGR.mOpp[mGR.mFire.i].x=mGR.mOpp[mGR.mFire.i].y=-2f;
						mGR.mKill++;
						mGR.mFire.count =30;
					}
					else{
						mGR.mFire.update();
						float x = world2screenX(mGR.mFire.x) - world2screenX(mGR.mOpp[mGR.mFire.i].x);
						float y = world2screenY(mGR.mFire.y) - world2screenY(mGR.mOpp[mGR.mFire.i].y);
						mGR.mFire.ang  = (float)GetAngle(-x, y);
						mGR.mFire.vx =(float)Math.cos(mGR.mFire.ang)*.10f;
						mGR.mFire.vy =(float)Math.sin(mGR.mFire.ang)*.06f;
					}
				}
				else
				{
					mGR.mFire.vx =0;
					mGR.mFire.vy =.08f;
					mGR.mFire.update();
				}
			}
			mGR.mFire.count++;
//			if(mGR.mFire.count==10)
//			{
//				mGR.mFire.i =- 1;
//				float y = -.9f;
//				for(int i=0;i<mGR.mOpp.length;i++){
//					if(mGR.mOpp[i].y > -.9 && mGR.mOpp[i].y <.9 && mGR.mOpp[i].x > -.9 && mGR.mOpp[i].x <.9 && mGR.mOpp[i].no == 9)
//					{
//						if(mGR.mOpp[i].y > y){
//							y = mGR.mOpp[i].y;
//							mGR.mFire.i = i;
//							mGR.mFire.x = 0;
//							mGR.mFire.y = 0.7f;
//						}
//					}
//				}
//			}
//			if(mGR.mFire.count>10)
//			{
//				mGR.mFire.vz = -.3f;
//				if( mGR.mFire.i >=0){
//					if(CirCir(mGR.mFire.x, mGR.mFire.y, mGR.mTex_Magnet2.Height(), mGR.mOpp[mGR.mFire.i].x, mGR.mOpp[mGR.mFire.i].y, mGR.mTex_Magnet2.Height()))
//					{
//						mGR.mFire.x = mGR.mOpp[mGR.mFire.i].x;
//						mGR.mFire.y = mGR.mOpp[mGR.mFire.i].y;
//					}
//					else{
//						mGR.mFire.update();
//						float x = world2screenX(mGR.mFire.x) - world2screenX(mGR.mOpp[mGR.mFire.i].x);
//						float y = world2screenY(mGR.mFire.y) - world2screenY(mGR.mOpp[mGR.mFire.i].y);
//						double th = GetAngle(-x, y);
//						mGR.mFire.vx =(float)Math.cos(th)*.10f;
//						mGR.mFire.vy =(float)Math.sin(th)*.06f;
//					}
//				}
//				else
//					mGR.mFire.count = 29;
//			}
//			if(mGR.mFire.z > .9f)
//				mGR.mFire.z+=mGR.mFire.vz;
//			mGR.mFire.count++;
//			if(mGR.mFire.i >=0 && mGR.mFire.count == 30){
//				setBlastAnim(mGR.mOpp[mGR.mFire.i].x,mGR.mOpp[mGR.mFire.i].y,-1);
//				mGR.mOpp[mGR.mFire.i].x=mGR.mOpp[mGR.mFire.i].y=-2f;
//				mGR.mKill++;
//			}
		}
		if(mGR.mSky.y<-2)
			mGR.mSky.set(0, 4, M.mRand.nextInt(3));
		
		mGR.mSky.y +=M.Speed;
	}
	float cr,cvr,anim;
	float rz,rzv=.1f;
	void Draw_GamePlay(GL10 gl)
	{
		if(rz>1)
			rzv = -.1f;
		if(rz<0.1)
			rzv = 0.1f;
		rz += rzv;
		for(int i=0;i<mGR.mRaodY.length;i++){
			mGR.mTex_Road[mGR.roadNo].drawSS(gl, -.0f, mGR.mRaodY[i],1,1,180);
			DrawTransScal(gl, mGR.mTex_Road[mGR.roadNo], -.0f, mGR.mRaodY[i],1,rz);
		}
		for(int i=0;i<mGR.mSRaodY.length;i++){
			DrawTexture(gl, mGR.mTex_SideRoad[mGR.roadSide][i%2], -.85f, mGR.mSRaodY[i]);
			mGR.mTex_SideRoad[mGR.roadSide][i%2].drawSS(gl, 0.85f, mGR.mSRaodY[i],1,1,180);
		}
		if(mGR.finish < 1.2f && !mGR.EndLess){
			DrawTexture(gl, mGR.mTex_Finish, -.1f,mGR.finish);
		}
		for(int i=0;i<mGR.mOpp.length;i++){
			if(mGR.mOpp[i].y > -1.5 && mGR.mOpp[i].y < 1.5 )
			{
				if(mGR.mOpp[i].x>-0.90f+0.13f*(2)){
					if(mGR.mOpp[i].no<7){
						DrawTexture(gl, mGR.mTex_BWA[Counter%mGR.mTex_BWA.length],-.07f+mGR.mOpp[i].x,mGR.mOpp[i].y-.38f);
						DrawTexture(gl, mGR.mTex_BWA[Counter%mGR.mTex_BWA.length], .07f+mGR.mOpp[i].x,mGR.mOpp[i].y-.38f);
					}
					else
					{
						DrawTexture(gl, mGR.mTex_SWA[Counter%mGR.mTex_SWA.length],-.04f+mGR.mOpp[i].x,mGR.mOpp[i].y-.18f);
						DrawTexture(gl, mGR.mTex_SWA[Counter%mGR.mTex_SWA.length], .04f+mGR.mOpp[i].x,mGR.mOpp[i].y-.18f);
					}
				}
				else{
					if(mGR.mOpp[i].no<7){
						mGR.mTex_BWA[Counter%mGR.mTex_BWA.length].drawSS(gl,-.07f+mGR.mOpp[i].x,mGR.mOpp[i].y+.38f, 1, 1,180);
						mGR.mTex_BWA[Counter%mGR.mTex_BWA.length].drawSS(gl, .07f+mGR.mOpp[i].x,mGR.mOpp[i].y+.38f, 1, 1,180);
					}
					else
					{
						mGR.mTex_SWA[Counter%mGR.mTex_SWA.length].drawSS(gl,-.04f+mGR.mOpp[i].x,mGR.mOpp[i].y+.18f, 1, 1,180);
						mGR.mTex_SWA[Counter%mGR.mTex_SWA.length].drawSS(gl, .04f+mGR.mOpp[i].x,mGR.mOpp[i].y+.18f, 1, 1,180);
					}
				}
			}
		}
		if(mGR.mPlayer.blast <= -1){
			DrawTexture(gl, mGR.mTex_SWA[Counter%mGR.mTex_SWA.length],-.04f+mGR.mPlayer.x,mGR.mPlayer.y-.18f);
			DrawTexture(gl, mGR.mTex_SWA[Counter%mGR.mTex_SWA.length], .04f+mGR.mPlayer.x,mGR.mPlayer.y-.18f);
		}
		for(int i=0;i<mGR.mOpp.length;i++){
			if(mGR.mOpp[i].y > -1.5 && mGR.mOpp[i].y < 1.5 )
			{
				if(mGR.mOpp[i].no == 9)
					DrawTextRS(gl, mGR.mTex_killCar, mGR.mOpp[i].x,mGR.mOpp[i].y, Counter*10, 1,1);
				if(mGR.mOpp[i].no == 10)
					DrawTextRS(gl, mGR.mTex_police, mGR.mOpp[i].x,mGR.mOpp[i].y, Counter*10, 1,1);
				if(mGR.mOpp[i].x>-0.90f+0.13f*(2)){
					
					DrawTexture(gl, mGR.mTex_OppCar[mGR.mOpp[i].no], mGR.mOpp[i].x,mGR.mOpp[i].y);
				}
				else{
					mGR.mTex_OppCar[mGR.mOpp[i].no].drawSS(gl,mGR.mOpp[i].x,mGR.mOpp[i].y	, 1, 1,180);//10
				}
			}
		}
		for(int i =0;i<mGR.mCoin.length;i++)
			if(mGR.mPlayer.y>-1.1 && mGR.mPlayer.y <1.1){
				DrawTextureS(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length], mGR.mCoin[i].x,mGR.mCoin[i].y,1.5f);
			}
		if(mGR.mDimond.y > -1.1 && mGR.mDimond.y < 1.1)
			DrawTexture(gl, mGR.mTex_Diamond2, mGR.mDimond.x,mGR.mDimond.y);
		if(mGR.mMagnet.y > -1.1 && mGR.mMagnet.y < 1.1)
			DrawTexture(gl, mGR.mTex_Magnet2, mGR.mMagnet.x,mGR.mMagnet.y);
		if(mGR.mPlayer.blast <= -1){
			int p =0;
			if(mGR.mPlayer.vx > 0) p = 1;
			if(mGR.mPlayer.vx < 0) p = 2;
			DrawTexture(gl, mGR.mTex_PlyCar[mGR.car_Sel][p], mGR.mPlayer.x,mGR.mPlayer.y);
			if(mGR.mPlayer.vx !=0){
				anim = 10;
				cr+=cvr;
				if(mGR.mPlayer.dir == 1){cvr=-2;if(cr<-10){cr=-10;}}
				if(mGR.mPlayer.dir == 2){cvr=+2;if(cr>10){cr=10;}}
			}
			else if(anim>0){
				cr+=cvr;
				if(cr<-10){cvr= 5;}
				if(cr>10){cvr=-5;}anim --;
				if(cr>-3 && cr< 3){cvr = 0;}
			}
			else{cr =0;}
			
		}
		else if(mGR.mPlayer.blast<mGR.mTex_Blast.length){
			DrawTexture(gl, mGR.mTex_Blast[mGR.mPlayer.blast], mGR.mPlayer.x,mGR.mPlayer.y);
			mGR.mPlayer.blast++;
		}
		
		if(mGR.mFire.count>0 && mGR.mFire.count<30)
		{
//			DrawTextureS(gl,mGR.mTex_Fireball,mGR.mFire.x,mGR.mFire.y,mGR.mFire.z);
			Roket(gl, mGR.mFire.x,mGR.mFire.y);
			
		}
		BlastAnimation(gl);
		if(M.LEVEL[mGR.mLevel][1]==0){
			DrawTransScal(gl, mGR.mTex_Bar, -.7f,.94f,1,.5f);
			DrawTransScal(gl, mGR.mTex_Target[1],-.9f,.94f,.6f,.5f);
			drawNumberScal(gl, mGR.mNoCoin, -.78f, .94f,.6f,.5f);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_Bar, -.7f,.94f);
			DrawTextureS(gl, mGR.mTex_Target[1],-.9f,.94f,.6f);
			drawNumberScal(gl, mGR.mNoCoin, -.78f, .94f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][4]==0){
			DrawTransScal(gl, mGR.mTex_Bar, -.2f,.94f,1,.5f);
			DrawTransScal(gl, mGR.mTex_Target[4],-.4f,.94f,.6f,.5f);
			drawNumberScal(gl, mGR.mNoDimond, -.28f, .94f,.6f,.5f);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_Bar, -.2f,.94f);
			DrawTextureS(gl, mGR.mTex_Target[4],-.4f,.94f,.6f);
			drawNumberScal(gl, mGR.mNoDimond, -.28f, .94f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][0]>0){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[0],-.9f,.84f,.6f);
			drawNumberScal(gl, (int)mGR.mMeter, -.78f, .84f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][2]>0){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[2],-.9f,.84f,.6f);
			drawNumberScal(gl, mGR.mBus, -.78f, .84f,.6f,1);
		}
		if(M.LEVEL[mGR.mLevel][3]>0){
			DrawTexture(gl, mGR.mTex_Bar, -.67f,.84f);
			DrawTextureS(gl, mGR.mTex_Target[3],-.9f,.84f,.6f);
			drawNumberScal(gl, mGR.mKill, -.78f, .84f,.6f,1);
		}
		for(int i=0;i<mGR.mPlayer.life;i++)
			DrawTextureS(gl, mGR.mTex_Life, 0.93f, +.97f-(i*.07f),1.5f);
		if(M.GameScreen == M.GAMEPLAY)
			Draw_Anim(gl);
		if(mGR.mSky.y<2)
		{
//			mGR.mSky.type = 1;
			float trc =.7f;
			if(mGR.mSky.type==0){
				DrawTransScal(gl, mGR.mTex_Bridge[mGR.mSky.mCar[1].counter%3], 0, mGR.mSky.y,1,mGR.mPlayer.mPower[0]?trc:1);
			}
			else if(mGR.mSky.type==1)
			{
				mGR.mTex_Plan.drawSedo(gl,mGR.mSky.y+.1f,mGR.mSky.mCar[0].y-.5f, .3f);
				DrawTransScal(gl, mGR.mTex_Plan, mGR.mSky.y,mGR.mSky.mCar[0].y,1,mGR.mPlayer.mPower[0]?trc:1);
				if(Counter%2==0)
					DrawTransScal(gl, mGR.mTex_PlanWing, mGR.mSky.y-.78f,.015f+mGR.mSky.mCar[0].y,1,mGR.mPlayer.mPower[0]?trc:1);
			}
			else if(mGR.mSky.type==2)
			{
				trc =.4f;
				mGR.mTex_Cloud.drawSedo(gl,-mGR.mSky.y-.1f,mGR.mSky.mCar[0].y-.5f, .3f);
				DrawTransScal(gl, mGR.mTex_Cloud,-mGR.mSky.y,mGR.mSky.mCar[1].y,1,mGR.mPlayer.mPower[0]?trc:1);
			}
			if(mGR.mSky.y<1.2f && M.GameScreen == M.GAMEPLAY){
				mGR.mSky.mCar[0].x-=.005f;
				mGR.mSky.mCar[0].y+=.0005f;
				mGR.mSky.mCar[1].x+=.005f;
				mGR.mSky.mCar[1].y-=.0005f;
			}
		}
		DrawTexture(gl, mGR.mTex_Missile_Btn, 0.8f, -.4f);
		DrawTexture(gl, mGR.mTex_HelpIcn, 0.75f, 0.9f);
		
		if(M.GameScreen == M.GAMEOVER)
			Draw_GameOver(gl);
		if(M.GameScreen == M.GAMEWIN)
			Draw_GameWin(gl);
		if(M.GameScreen == M.GAMEPAUSE)
			Draw_Pause(gl);
		if(M.GameScreen == M.GAMEPLAY){
			gameLogic();
			
		}
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir( 0.8f, -.4f, mGR.mTex_Magnet2.width()*0.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.8f, 0.9f, mGR.mTex_Back.width()*.8f, mGR.mTex_Back.Height()*.8f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mSel!=1)
		{
			if(event.getX()>M.ScreenWidth/2){
				if(mGR.mPlayer.len<12){
					mGR.mPlayer.dir = 1;
					mGR.mPlayer.vx = .025f;
				}
			}
			else{
				if(mGR.mPlayer.len>2){
					mGR.mPlayer.dir = 2;
					mGR.mPlayer.vx =-.025f;
				}
			}
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(mGR.mSel ==1){
				mGR.mFire.set(mGR.mPlayer.x, mGR.mPlayer.y);
				M.sound14(GameRenderer.mContext, R.drawable.missile);
				for(int i=0;i<mGR.mRoket.length;i++)
				{
					mGR.mRoket[i].set(-100, 0);
				}
			}
			if(mGR.mSel ==2){
				M.GameScreen = M.GAMEHELP;
				M.stop(GameRenderer.mContext);
			}
			mGR.mPlayer.dir = 0;
			for(int i =0 ;i<14;i++){
				if(mGR.mPlayer.x < -0.93f+0.13f*i)
				{
					if(mGR.mPlayer.vx < 0)
						mGR.mPlayer.len = i-1;
					else if(mGR.mPlayer.vx > 0)
						mGR.mPlayer.len = i;
					break;
				}
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game Play End~~********************************/
	
	
	/*************************Game Over Start********************************/
	void Draw_GameOver(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_box		, mGR.gx,0.06f);
		DrawTexture(gl, mGR.mTex_LFailed	, mGR.gx,.62f);
		DrawTexture(gl, mGR.mTex_Cash		, mGR.gx,.38f);
		drawNumber(gl, mGR.mTotalCoin		, mGR.gx-.08f,.29f);
		DrawTexture(gl, mGR.mTex_cresh		, mGR.gx,.11f);
		
		DrawTexture(gl, mGR.mTex_Highscore	, mGR.gx,-.14f);
		drawNumber(gl, mGR.Total[0]			, mGR.gx-.08f,-.24f);
		
		DrawTextureS(gl, mGR.mTex_Playagain	, mGR.gx,-.37f,mGR.mSel==1?1.1f:1);
		DrawTextureS(gl, mGR.mTex_Store	, mGR.gx,-.58f,mGR.mSel==2?1.15f:1);
		DrawTextureS(gl, mGR.mTex_fb, mGR.gx-.67f,-.65f,mGR.mSel==3?1.2f:1);
		DrawTextureS(gl, mGR.mTex_SplashIcn[3], mGR.gx+.67f,-.65f,mGR.mSel==4?1.2f:1);
		
		if(mGR.gx>0)
			mGR.gx-=.1f;
		
	}
	boolean Handle_Gameover(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(mGR.gx,-.37f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//PlayAgain
		}
		if(CircRectsOverlap(mGR.gx,-.58f, mGR.mTex_restart.width()*.4f, mGR.mTex_restart.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//Shop
		}
		if(CircRectsOverlap(mGR.gx-.67f,-.65f, mGR.mTex_SplashIcn[3].width()*.4f, mGR.mTex_SplashIcn[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;//Facebook
		}
		if(CircRectsOverlap(mGR.gx+.67f,-.65f, mGR.mTex_morecoin.width()*.4f, mGR.mTex_morecoin.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;//More
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				mGR.roadNo = M.mRand.nextInt(2);
//				
				if(!mGR.EndLess){
//					M.GameScreen = M.GAMESTART;
					setLoad();
					M.sound12(GameRenderer.mContext, R.drawable.power_up);
					mGR.gx = -2f;
					sx = 10;
					sy = 0;
				}
				else{
					mGR.gameReset();
					M.GameScreen = M.GAMEGO;
					M.sound13(GameRenderer.mContext, R.drawable.popup1);
					mGR.gx = 2f;
					sx=sy=0;
				}
				break;
			case 2:
				M.GameScreen = M.GAMESHOP;
				break;
			
			case 3:
				facebook();
				break;
			case 4:
				MoreGame();//mGR.mMainActivity.onBuyGold50000(null);
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game over End~~********************************/
	/*************************GameWin Start********************************/
	void Draw_GameWin(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_box		, mGR.gx+.00f,0.00f);
		if(mGR.mLevel < 29){
			DrawTexture(gl, mGR.mTex_LCompleted	, mGR.gx+.00f,0.58f);
			DrawTransScal(gl, mGR.mTex_nextBtn	, mGR.gx+.00f,-.45f,mGR.mSel == 1 ?1.1f:1,mGR.mSel == 1 ?0.5f:1);
			drawNumber (gl, mGR.mLevel+1		, mGR.gx+.30f, .63f);
		}
		else{
			DrawTexture(gl, mGR.mTex_Cong	, mGR.gx+.00f,0.58f);
			DrawTransScal(gl, mGR.mTex_mainBtn	, mGR.gx+.00f,-.45f,mGR.mSel == 1 ?1.1f:1,mGR.mSel == 1 ?0.5f:1);
		}
		
		DrawTexture(gl, mGR.mTex_bStar		, mGR.gx-.35f,0.10f);
		DrawTexture(gl, mGR.mTex_bStar		, mGR.gx+.00f,0.15f);
		DrawTexture(gl, mGR.mTex_bStar		, mGR.gx+.35f,0.10f);
		
		
		DrawTransScal(gl, mGR.mTex_SplashIcn[3]	, mGR.gx-.66f,-.60f,mGR.mSel == 2 ?1.1f:1,mGR.mSel == 2 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_fb			, mGR.gx+.66f,-.60f,mGR.mSel == 3 ?1.1f:1,mGR.mSel == 3 ?0.5f:1);
		
		if(mGR.gx<=0){
			
			for(int i =0;i<mGR.mAnim.length;i++)
			{
				if(mGR.mAnim[i].x>-1&&mGR.mAnim[i].x<1&&mGR.mAnim[i].y>-1&&mGR.mAnim[i].y<1){
					DrawTransScal(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length],mGR.mAnim[i].x, mGR.mAnim[i].y, 1.5f, 1);
					mGR.mAnim[i].x+=.04f;
					mGR.mAnim[i].y+=.08f;
					if(mGR.mAnim[i].y>.9)
						mGR.mAnim[i].y = -2f;
					if(Counter%5==0)
						M.sound3(GameRenderer.mContext, R.drawable.coin);
				}
			}
			for(int i =0;i<mGR.mAnim.length&& Counter%2==0&& mGR.mTempCoin<mGR.mTotalCoin;i++)
			{
				if(mGR.mAnim[i].x<-.9||mGR.mAnim[i].x>.9||mGR.mAnim[i].y<-.9||mGR.mAnim[i].y>.9)
				{
					mGR.mAnim[i].set(-.25f+(M.mRand.nextInt()%100)*.001f,-.23f);
					break;
				}
			}
			if(mGR.mTempCoin<mGR.mTotalCoin)
				mGR.mTempCoin+=5;
			if(mGR.mTempCoin>mGR.mTotalCoin)
				mGR.mTempCoin=mGR.mTotalCoin;
			
			for(int i=0;i<mGR.mPlayer.collide && i <= sy;i++)
			{
				if(sy==i)
					DrawTextureS(gl, mGR.mTex_star, mGR.gx-.35f+(i*.35f),i==1?.15f:.10f,sx);
				else
					DrawTextureS(gl, mGR.mTex_star, mGR.gx-.35f+(i*.35f),i==1?.15f:.10f,1);
				sx*=.8;
				if(sx<=1)
				{
					sx = 10;
					sy+=1;
				}
			}
			DrawTexture(gl, mGR.mTex_coin2bar	,.60f,.92f);
			drawNumber (gl, mGR.mTempCoin		,.53f,.92f);
			
		}
		DrawTexture(gl, mGR.mTex_coin2bar	, mGR.gx+.00f,-.16f);
		drawNumber (gl, mGR.mNoCoin			, mGR.gx-.06f,-.16f);
		
		if(mGR.gx>0)
			mGR.gx-=.1f;
		
	}
	boolean Handle_GameWin(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(mGR.gx+.00f,-.45f, mGR.mTex_mainBtn.width()*.4f, mGR.mTex_mainBtn.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(mGR.gx-.66f,-.60f, mGR.mTex_restart.width()*.4f, mGR.mTex_restart.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(mGR.gx+.66f,-.60f, mGR.mTex_SplashIcn[3].width()*.4f, mGR.mTex_SplashIcn[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				if(mGR.mLevel < 29){
					mGR.mLevel++;
					mGR.gameReset();
//					M.GameScreen = M.GAMESTART;
					setLoad();
					M.sound12(GameRenderer.mContext, R.drawable.power_up);
					mGR.EndLess = false;
					mGR.gx = -2f;
					sx=10;
					sy = 0;
				}else{
					Menu();
				}
				
				break;
			case 2:
				MoreGame();
				break;
			case 3:
				facebook();
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************GameWin End~~********************************/
	
	
	
	
	
	
	
	
	
	void Draw_Anim(GL10 gl)
	{
		for(int i =0;i<mGR.mAnim.length;i++)
		{
			if(mGR.mAnim[i].x>-1&&mGR.mAnim[i].x<1&&mGR.mAnim[i].y>-1&&mGR.mAnim[i].y<1){
				DrawTransScal(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length],mGR.mAnim[i].x, mGR.mAnim[i].y, 1.5f, mGR.mAnim[i].tran);
				mGR.mAnim[i].update();
			}
		}
	}
	void setAnimation(float x,float y)
	{
		int count =0;
		for(int i =0;i<mGR.mAnim.length&& count<30;i++)
		{
			if(mGR.mAnim[i].x<-1||mGR.mAnim[i].x>1||mGR.mAnim[i].y<-1||mGR.mAnim[i].y>1){
				mGR.mAnim[i].set(x, y);
				count++;
			}
		}
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextRS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float scal,float tarn)
	{
		Tex.drawRotet(gl, x, y,angle,scal,tarn);
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
	
	float world2screenX(float c)
	{
		return ((c/2)+.5f)*M.mMaxX;
	}
	float world2screenY(float c)
	{
		return (.5f-(c/2))*M.mMaxY;
	}
	
	
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width()*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,float scal,float tran)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawTransprentScal(gl,x+i*dx,y,scal,tran);
		}
	}
	void drawNumberRed(GL10 gl,int no,float x, float y,float scal)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawRed(gl,x+i*dx,y,scal);
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
		mIntent.setData(Uri.parse(M.FACEBOOK));
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
