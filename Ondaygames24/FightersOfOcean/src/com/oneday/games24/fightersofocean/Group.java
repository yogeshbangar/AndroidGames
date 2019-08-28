package com.oneday.games24.fightersofocean;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	float sx,sy;
	int Counter =0;
	int LowCount =0;

	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
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
		if(Counter%2==0)
			LowCount++;
//		M.GameScreen = M.GAMEPLAY;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>50)
			{
				mGR.resumeCounter = 20;
				M.GameScreen = M.GAMEMENU;
//				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				//DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
				mGR.mFont.draw(gl, "Skip",.8f,.9f, 0, 0);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMEPUSE:
			DrawWinOver(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABUT:
			Draw_HelpAbout(gl);
			break;
		case M.GAMELOAD:
			Draw_Load(gl);
			break;
		case M.GAMEPLAY:
		default:
			Draw_GamePlay(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
			/*AdHouse*/
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMEPUSE:
			Handle_WinOver(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABUT:
			Handle_HelpAbout(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		}
//		Handle(event);
		return true;
	}
	/**********************************START LOAD	***********************************************************/
	void Draw_Load(GL10 gl)
	{
		mGR.Load+=.01f;
		if(Counter%2==0)
			DrawTexture(gl, mGR.mTex_Boat[2], mGR.Load,-.85f);
		else{
			DrawTexture(gl, mGR.mTex_BoatX[0], mGR.Load,-.85f);
			DrawTexture(gl, mGR.mTex_BFire[1], mGR.Load+.17f,-.85f-.10f);
		}
		mGR.mFont.draw(gl, "Wave "+mGR.mLevel+" Loading", 0,-.9f, 0, 1);
		if(mGR.Load>.9f)
		{
			M.play(GameRenderer.mContext, R.drawable.background);
			M.GameScreen = M.GAMEPLAY;
		}
	}
	/**********************************END LOAD	***********************************************************/
	/**********************************START HELP ABOUT *******************************************************/
	void Draw_HelpAbout(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_GameBG[0] , 0, 0.519f,3.2000f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[1] , 0, 0.519f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[2] , 0, -.479f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[3] , 0, -.170f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[4] , 0, -.069f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_Black		, 0, 0.000f,1.0000f,1);
		
		if(M.GameScreen == M.GAMEHELP)
		{
			DrawTexture(gl, mGR.mTex_HelpScr		, 0, -.5f);
			mGR.mFont.draw(gl, "Help"						, 0, +.60f, 0, 1);
			mGR.mFont.draw(gl, "Tap to Shoot"				, 0, +.42f, 0, 1);
			mGR.mFont.draw(gl, "upgrade gun,boat and power"	, 0, 0.31f, 0, 1);
			mGR.mFont.draw(gl, "for improve strength"		, 0, 0.20f, 0, 1);
		}
		else
			DrawTexture(gl, mGR.mTex_About		,0.00f,0.0f);
		
		DrawTexture(gl, mGR.mTex_PIcon		, .85f,-.8f);
		DrawTexture(gl, mGR.mTex_Back		, .85f,-.8f);
		
		
	}
	boolean Handle_HelpAbout(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.83f,-.85f,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 4;//Back
		}
		
	
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************************END HELP ABOUT********************************************************/
	
	/**************************************START MENU**********************************************************/
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash		,0.0f,0.0f);
		DrawTransScal(gl, mGR.mTex_Help	 	,-.83f,-.6f,1,mGR.mSel==4?.5f:1);
		DrawTransScal(gl, mGR.mTex_Continue	,0.00f,(mGR.resumeCounter > 60)?-.46f:-.85f,1,mGR.mSel==7?.5f:1);
		DrawTransScal(gl, mGR.mTex_Exit    	,0.83f,-.6f,1,mGR.mSel==5?.5f:1);
		DrawTexture(gl, mGR.mTex_PIcon		,0.94f,0.8f);
		DrawTexture(gl, mGR.mTex_Info_icon 	,0.94f,0.8f);
		
		DrawTexture(gl, mGR.mTex_PIcon		,0.94f,0.5f);
		DrawTexture(gl, mGR.mTex_Sound 		,0.94f,0.5f);
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_Target	,0.94f,0.5f);
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.83f,-.6f,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 4;//Help
		}
		
		if(CircRectsOverlap(0.83f,-.6f,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 5;//Exit
		}
		if(CircRectsOverlap(0.92f,0.8f,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 6;//Info
		}
		
		if(CircRectsOverlap(0.92f,0.5f,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 8;//Sound
		}
		
		float y = (mGR.resumeCounter > 60)?-.46f:-.85f;
		if(CircRectsOverlap(0.0f, y,mGR.mTex_Help.width()*.4f,mGR.mTex_Help.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 7;//Continue
		}
		
	
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			
			case 4:
				M.GameScreen = M.GAMEHELP;
				break;
			case 5:
				GameRenderer.mStart.get();
				break;
			case 6:
				M.GameScreen = M.GAMEABUT;
				break;
			case 7:
				M.GameScreen = M.GAMESHOP;
				break;
			case 8:
				M.setValue = !M.setValue;
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
			mGR.mSel = 0;
		}
		return true;
	}
	/****************************************END MENU**********************************************************/
	/**************************************Start Shop**********************************************************/
	void DrawGun(GL10 gl)
	{
		for(int i=0;i<mGR.mGun.length;i++)
		{
			DrawTexture(gl, mGR.mTex_Objectbox	,mGR.mGun[i].x,.3f);
			if(mGR.mGun[i].mIsPurchased)
				mGR.mFont.draw(gl, "Purchased",mGR.mGun[i].x, 0.45f, 3, 1);
			else
				mGR.mFont.draw(gl, "Coin : "+mGR.mGun[i].mCost,mGR.mGun[i].x, 0.45f, 2, 1);
			DrawTexture(gl, mGR.mTex_Gun[i]	,mGR.mGun[i].x,.22f);
		}
		if(!mGR.mGun[mGR.GunPoint].mIsPurchased){
			DrawTexture	(gl, mGR.mTex_Button2[0],.21f,-.48f);
			mGR.mFont.draw(gl, "Buy"			,.21f,-.48f, 0, 1);
			mGR.mFont.draw(gl, mGR.mGun[mGR.GunPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, "Damage : "+mGR.mGun[mGR.GunPoint].mDamage	,-.3f, -.19f-.13f, 0, 0);
			mGR.mFont.draw(gl, "Clip : "+mGR.mGun[mGR.GunPoint].mClipSize		,-.3f, -.19f-.26f, 0, 0);
		}
		else
		{
			mGR.mFont.draw(gl, mGR.mGun[mGR.GunPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, "Purchased"	,-.3f, -.19f-.13f, 0, 0);
		}
		if(mGR.mMove == 1)
		{
			for(int i=0;i<mGR.mGun.length;i++)
			{
				mGR.mGun[i].x +=.01f*5;
			}
			if(mGR.mGun[mGR.GunPoint].x>0)
				mGR.mMove = 0;
		}
		if(mGR.mMove == 2)
		{
			for(int i=0;i<mGR.mGun.length;i++)
			{
				mGR.mGun[i].x -=.01f*5;
			}
			if(mGR.mGun[mGR.GunPoint].x<0)
				mGR.mMove = 0;
		}
		
		
	}
	void DrawPower(GL10 gl)
	{
		for(int i=0;i<mGR.mPower.length;i++)
		{
			DrawTexture(gl, mGR.mTex_Objectbox	,mGR.mPower[i].x,.3f);
			if(mGR.mPower[i].mIsPurchased)
				mGR.mFont.draw(gl, "Purchased",mGR.mPower[i].x, 0.45f, 3, 1);
			else
				mGR.mFont.draw(gl, "Coin : "+mGR.mPower[i].mCost,mGR.mPower[i].x, 0.45f, 5, 1);
			DrawTexture(gl, mGR.mTex_Power[i]	,mGR.mPower[i].x,.22f);
		}
		if(!mGR.mPower[mGR.PowerPoint].mIsPurchased){
			DrawTexture	(gl, mGR.mTex_Button2[0],.21f,-.48f);
			mGR.mFont.draw(gl, "Buy"			,.21f,-.48f, 0, 1);
			
			mGR.mFont.draw(gl, mGR.mPower[mGR.PowerPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, "Coin : "+mGR.mPower[mGR.PowerPoint].mCost	,-.3f, -.19f-.13f, 0, 0);
		}
		else{
			mGR.mFont.draw(gl, mGR.mPower[mGR.PowerPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, "Purchased"	,-.3f, -.19f-.13f, 0, 0);
		}
		
		if(mGR.mMove == 1)
		{
			for(int i=0;i<mGR.mPower.length;i++)
			{
				mGR.mPower[i].x +=.01f*5;
			}
			if(mGR.mPower[mGR.PowerPoint].x>0)
				mGR.mMove = 0;
		}
		if(mGR.mMove == 2)
		{
			for(int i=0;i<mGR.mPower.length;i++)
			{
				mGR.mPower[i].x -=.01f*5;
			}
			if(mGR.mPower[mGR.PowerPoint].x<0)
				mGR.mMove = 0;
		}
		
		
	}
	void DrawBoat(GL10 gl)
	{
		for(int i=0;i<mGR.mUBoat.length;i++)
		{
			DrawTexture(gl, mGR.mTex_Objectbox	,mGR.mUBoat[i].x,.3f);
			if(mGR.mUBoat[i].mIsPurchased)
				mGR.mFont.draw(gl, "Purchased",mGR.mUBoat[i].x, 0.45f, 3, 1);
			else
				mGR.mFont.draw(gl, (i!=3?"XP : ":"Coin :")+mGR.mUBoat[i].mCost,mGR.mUBoat[i].x, 0.45f, 0, 1);
			DrawTexture(gl, mGR.mTex_UBoat[i]	,mGR.mUBoat[i].x,.22f);
		}
		if(!mGR.mUBoat[mGR.BoatPoint].mIsPurchased){
			DrawTexture	(gl, mGR.mTex_Button2[0],.21f,-.48f);
			mGR.mFont.draw(gl, "Buy"			,.21f,-.48f, 0, 1);
			
			mGR.mFont.draw(gl, mGR.mUBoat[mGR.BoatPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, (mGR.BoatPoint!=3?"XP : ":"Coin :")+mGR.mUBoat[mGR.BoatPoint].mCost	,-.3f, -.19f-.13f, 0, 0);
		}
		else{
			mGR.mFont.draw(gl, mGR.mUBoat[mGR.BoatPoint].Name		,-.3f, -.19f-.00f, 0, 0);
			mGR.mFont.draw(gl, "Purchased"	,-.3f, -.19f-.13f, 0, 0);
		}
			
		if(mGR.mMove == 1)
		{
			for(int i=0;i<mGR.mUBoat.length;i++)
			{
				mGR.mUBoat[i].x +=.01f*5;
			}
			if(mGR.mUBoat[mGR.BoatPoint].x>0)
				mGR.mMove = 0;
		}
		if(mGR.mMove == 2)
		{
			for(int i=0;i<mGR.mUBoat.length;i++)
			{
				mGR.mUBoat[i].x -=.01f*5;
			}
			if(mGR.mUBoat[mGR.BoatPoint].x<0)
				mGR.mMove = 0;
		}
		
		
	}
	void Draw_Shop(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_GameBG[0] , 0, 0.519f,3.2000f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[1] , 0, 0.519f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[2] , 0, -.479f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[3] , 0, -.170f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[4] , 0, -.069f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_Black		, 0, 0.000f,1.0000f,1);
		
		DrawTexture	(gl, mGR.mTex_Pattern[0],0.641f, 0.0f);
		DrawTexture	(gl, mGR.mTex_Pattern[2],-.641f, 0.0f);
		DrawTexture	(gl, mGR.mTex_Pattern[1],0.641f, -.5f);
		DrawTexture	(gl, mGR.mTex_Pattern[3],-.641f, -.5f);
		
	
		switch (mGR.SelPoint) {
		case 0:
			DrawTexture(gl, mGR.mTex_Option[0]	,-.56f, .82f);
			DrawTexture(gl, mGR.mTex_Option[1]	,-.00f, .80f);
			DrawTexture(gl, mGR.mTex_Option[0]	,0.56f, .82f);
			DrawGun(gl);
			DrawTexture	(gl, mGR.mTex_Button1[0],0.8f, -.55f);
			mGR.mFont.draw(gl, M.SHARE			,0.8f, -.55f, 2, 1);
			break;
		case 1:
			DrawTexture(gl, mGR.mTex_Option[0]	,-.56f, .82f);
			DrawTexture(gl, mGR.mTex_Option[0]	,-.00f, .82f);
			DrawTexture(gl, mGR.mTex_Option[1]	,0.56f, .80f);
			DrawPower(gl);
			DrawTexture	(gl, mGR.mTex_Button1[0],0.8f, -.55f);
			mGR.mFont.draw(gl, M.SHARE			,0.8f, -.55f, 5, 1);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Option[1]	,-.56f, .80f);
			DrawTexture(gl, mGR.mTex_Option[0]	,-.00f, .82f);
			DrawTexture(gl, mGR.mTex_Option[0]	,0.56f, .82f);
			DrawBoat(gl);
			DrawTexture	(gl, mGR.mTex_Button1[0],0.8f, -.55f);
			mGR.mFont.draw(gl, M.SHARE			,0.8f, -.55f, 0, 1);
			break;
		}
		mGR.mFont.draw(gl, M.BOAT	,-.56f, 0.82f, 4, 1);
		mGR.mFont.draw(gl, M.GUN	,0.00f, 0.82f, 4, 1);
		mGR.mFont.draw(gl, M.POWER	,0.56f, 0.82f, 4, 1);
		
		
		DrawFlip	(gl, mGR.mTex_AIcon,-.90f,0.35f);
		DrawTexture	(gl, mGR.mTex_AIcon,+.90f,0.35f);
		
		
		DrawTexture(gl, mGR.mTex_HIcon	,-.92f,-.3f);
		mGR.mFont.draw(gl, M.PlayerLife+"/"+mGR.mPlayer.MaxLife	,-.87f,-.3f, 1, 0);
		DrawTextureS(gl, mGR.mTex_Player	,-.72f,-.55f,1.2f,1.2f);
		
		mGR.mFont.draw(gl,"Coin:"+mGR.mPlayer.mCoin	,.50f, -0.29f, 2, 0);
		mGR.mFont.draw(gl,"XP     :"+mGR.mPlayer.mXP,.50f, -0.40f, 5, 0);
		
		DrawTexture	(gl, mGR.mTex_Button0[0],-.75f, -.86f);
		mGR.mFont.draw(gl, M.FREEGAME		,-.75f, -.86f, 0, 1);
		DrawTexture	(gl, mGR.mTex_Button0[0],0.75f, -.86f);
		mGR.mFont.draw(gl, M.CONTINUE		,0.75f, -.86f, 0, 1);

		switch (mGR.mSel) {
		case 4:
			DrawTexture	(gl, mGR.mTex_Button1[1],0.8f, -.55f);
			break;
		case 5:
			DrawTexture	(gl, mGR.mTex_Button0[1],-.75f, -.86f);
			break;
		case 6:
			DrawTexture	(gl, mGR.mTex_Button0[1],0.75f, -.86f);
			break;
		}
		
	}
	boolean Handle_Shop(MotionEvent event) 
	{
		mGR.mSel = 0;
		if(mGR.mMove ==0 && CircRectsOverlap(.21f,-.48f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 3;//Buy
		}
		if(mGR.mMove ==0 && CircRectsOverlap(0.8f, -.57f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 4;//share
		}
		
		if(mGR.mMove ==0 && CircRectsOverlap(-.75f, -.86f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 5;//Free Games
		}
		
		if(mGR.mMove ==0 && CircRectsOverlap(0.75f, -.86f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 6;//Continue
		}
		
		if(mGR.mMove ==0 && CircRectsOverlap(-.56f, .8f,mGR.mTex_Option[0].width()*.3f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 7;//SelPoint = 2
		}
		if(mGR.mMove ==0 && CircRectsOverlap(+.00f, .8f,mGR.mTex_Option[0].width()*.3f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 8;//SelPoint = 0
		}
		if(mGR.mMove ==0 && CircRectsOverlap(+.56f, .8f,mGR.mTex_Option[0].width()*.3f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 9;//SelPoint = 1
		}
		
		if(mGR.mMove ==0 && CircRectsOverlap(0.90f,0.35f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 10;//Right Arrow
		}
		if(mGR.mMove ==0 && CircRectsOverlap(-.90f,0.35f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
				mGR.mSel = 11;//Left Arrow
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 3:
				if(mGR.SelPoint==0 && !mGR.mGun[mGR.GunPoint].mIsPurchased)
				{
					if(mGR.mGun[mGR.GunPoint].mCost <= mGR.mPlayer.mCoin){
						mGR.mPlayer.mCoin -= mGR.mGun[mGR.GunPoint].mCost;
						mGR.mGun[mGR.GunPoint].mIsPurchased= true;
						if(mGR.mGun[mGR.GunPoint].mClipSize > mGR.Bullet)
							mGR.Bullet = mGR.mGun[mGR.GunPoint].mClipSize;
						if(mGR.mGun[mGR.GunPoint].mDamage > mGR.mPlayer.mDamageBy)
						{
							mGR.mPlayer.mDamageBy = mGR.mGun[mGR.GunPoint].mDamage;
							mGR.mPlayer.mGunType = mGR.GunPoint+1;
						}
						
					}else
						GameRenderer.mStart.Massage(M.XPCON);
					
				}
				else if(mGR.SelPoint == 1 && !mGR.mPower[mGR.PowerPoint].mIsPurchased)
				{
					if(mGR.mPower[mGR.PowerPoint].mCost <= mGR.mPlayer.mCoin){
						mGR.mPlayer.mCoin -= mGR.mPower[mGR.PowerPoint].mCost;
						if(mGR.mPlayer.Power<mGR.PowerPoint)
							mGR.mPlayer.Power=mGR.PowerPoint;
						mGR.mPower[mGR.PowerPoint].mIsPurchased= true;
					}
					else
						GameRenderer.mStart.Massage(M.XPCON);
				}
				else if(mGR.SelPoint==2 && !mGR.mUBoat[mGR.BoatPoint].mIsPurchased)
				{
					if(mGR.BoatPoint == 3)
					{
						if(mGR.mUBoat[mGR.BoatPoint].mCost <= mGR.mPlayer.mCoin){
							mGR.mPlayer.mCoin -= mGR.mUBoat[mGR.BoatPoint].mCost;
							mGR.mUBoat[mGR.BoatPoint].mCost=(int)(mGR.mUBoat[mGR.BoatPoint].mCost*1.1);
	//						mGR.mUBoat[mGR.BoatPoint].mDamage=(int)(mGR.mUBoat[mGR.BoatPoint].mDamage*1.1);
							M.PlayerLife+=mGR.mUBoat[mGR.BoatPoint].mDamage;
							if(M.PlayerLife > mGR.mPlayer.MaxLife)
								M.PlayerLife = mGR.mPlayer.MaxLife;
						}
						else
							GameRenderer.mStart.Massage(M.XPCON);
					}
					else if(mGR.mUBoat[mGR.BoatPoint].mCost <= mGR.mPlayer.mXP)
					{
						mGR.mPlayer.mXP -= mGR.mUBoat[mGR.BoatPoint].mCost;
						if(mGR.BoatPoint == 0)
						{
							mGR.mUBoat[mGR.BoatPoint].mCost+=40;
							mGR.mPlayer.Bomb++;
						}
						if(mGR.BoatPoint == 1)
						{
							mGR.mUBoat[mGR.BoatPoint].mCost+=40;
							mGR.mPlayer.Net++;
						}
						if(mGR.BoatPoint == 2)
						{
							mGR.mUBoat[mGR.BoatPoint].mCost+=20;
							mGR.mPlayer.MaxLife+=mGR.mUBoat[mGR.BoatPoint].mDamage;
						}
						if(mGR.BoatPoint == 4 && mGR.BoatPoint== 5)
						{
							mGR.mUBoat[mGR.BoatPoint].mIsPurchased= true;
							if(mGR.mPlayer.BoatMan < mGR.BoatPoint)
								mGR.mPlayer.BoatMan = mGR.BoatPoint;
						}
					}
					else 
						GameRenderer.mStart.Massage(M.XPMSG);
				}
				else
					mGR.mSel = 0;
				//Buy
				break;
			case 4:
				share2friend();//Share
				break;
			case 5:
				MoreGame();
				//Free Games
				break;
			case 6:
				M.GameScreen = M.GAMELOAD;
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.stop();
				//Continue
				break;
			case 7:
				mGR.SelPoint = 2;
				break;
			case 8:
				mGR.SelPoint = 0;
				break;
			case 9:
				mGR.SelPoint = 1;
				break;
			case 10:
				moveAction(1,mGR.SelPoint);
				break;
			case 11:
				moveAction(2,mGR.SelPoint);
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
			mGR.mSel = 0;
		}
		return true;
	}
	void moveAction(int select,int type)
	{
		int i =0;
		if(type ==0){
			if(select == 1)
			{
				mGR.mMove = 1;
				mGR.GunPoint = mGR.mGun.length;
				for(i=0;i<mGR.mGun.length;i++)
				{
					if(mGR.mGun[i].x >.5f && mGR.mGun[i].x <.7f)
						break;
				}
				for(int j=0;j<mGR.mGun.length;j++)
				{
					if(i<0)
						i=mGR.mGun.length-1;
					if(j<2)
						mGR.mGun[i].set(0.6f-(j*.6f));
					else
					{
						if(mGR.GunPoint==mGR.mGun.length)
							mGR.GunPoint = i;
						mGR.mGun[i].set(0.6f-(j*.6f));
					}
					i--;
				}
				mGR.GunPoint %= mGR.mGun.length;
			}
			if(select == 2)
			{
			
				mGR.mMove = 2;
				mGR.GunPoint = mGR.mGun.length;
				for(i=0;i<mGR.mGun.length;i++)
				{
					if(mGR.mGun[i].x <-.5f && mGR.mGun[i].x >-.7f)
						break;
				}
				for(int j=0;j<mGR.mGun.length;j++)
				{
					if(i==mGR.mGun.length)
						i=0;
					if(j<2)
						mGR.mGun[i].set(-.6f+(j*.6f));
					else
					{
						if(mGR.GunPoint==mGR.mGun.length)
							mGR.GunPoint = i;
						mGR.mGun[i].set(-.6f+(j*.6f));
					}
					i++;
				}
				mGR.GunPoint %= mGR.mGun.length;
			}
		}
		if(type==1)
		{

			if(select == 1)
			{
				mGR.mMove = 1;
				mGR.PowerPoint = mGR.mPower.length;
				for(i=0;i<mGR.mPower.length;i++)
				{
					if(mGR.mPower[i].x >.5f && mGR.mPower[i].x <.7f)
						break;
				}
				for(int j=0;j<mGR.mPower.length;j++)
				{
					if(i<0)
						i=mGR.mPower.length-1;
					if(j<2)
						mGR.mPower[i].set(0.6f-(j*.6f));
					else
					{
						if(mGR.PowerPoint==mGR.mPower.length)
							mGR.PowerPoint = i;
						mGR.mPower[i].set(0.6f-(j*.6f));
					}
					i--;
				}
				mGR.PowerPoint %= mGR.mPower.length;
			}
			if(select == 2)
			{
			
				mGR.mMove = 2;
				mGR.PowerPoint = mGR.mPower.length;
				for(i=0;i<mGR.mPower.length;i++)
				{
					if(mGR.mPower[i].x <-.5f && mGR.mPower[i].x >-.7f)
						break;
				}
				for(int j=0;j<mGR.mPower.length;j++)
				{
					if(i==mGR.mPower.length)
						i=0;
					if(j<2)
						mGR.mPower[i].set(-.6f+(j*.6f));
					else
					{
						if(mGR.PowerPoint==mGR.mPower.length)
							mGR.PowerPoint = i;
						mGR.mPower[i].set(-.6f+(j*.6f));
					}
					i++;
				}
				mGR.PowerPoint %= mGR.mPower.length;
			}
		
		}
		if(type == 2)
		{
			if(select == 1)
			{
				mGR.mMove = 1;
				mGR.BoatPoint = mGR.mUBoat.length;
				for(i=0;i<mGR.mUBoat.length;i++)
				{
					if(mGR.mUBoat[i].x >.5f && mGR.mUBoat[i].x <.7f)
						break;
				}
				for(int j=0;j<mGR.mUBoat.length;j++)
				{
					if(i<0)
						i=mGR.mUBoat.length-1;
					if(j<2)
						mGR.mUBoat[i].set(0.6f-(j*.6f));
					else
					{
						if(mGR.BoatPoint==mGR.mUBoat.length)
							mGR.BoatPoint = i;
						mGR.mUBoat[i].set(0.6f-(j*.6f));
					}
					i--;
				}
				mGR.BoatPoint %= mGR.mUBoat.length;
			}
			if(select == 2)
			{
			
				mGR.mMove = 2;
				mGR.BoatPoint = mGR.mUBoat.length;
				for(i=0;i<mGR.mUBoat.length;i++)
				{
					if(mGR.mUBoat[i].x <-.5f && mGR.mUBoat[i].x >-.7f)
						break;
				}
				for(int j=0;j<mGR.mUBoat.length;j++)
				{
					if(i==mGR.mUBoat.length)
						i=0;
					if(j<2)
						mGR.mUBoat[i].set(-.6f+(j*.6f));
					else
					{
						if(mGR.BoatPoint==mGR.mUBoat.length)
							mGR.BoatPoint = i;
						mGR.mUBoat[i].set(-.6f+(j*.6f));
					}
					i++;
				}
				mGR.BoatPoint %= mGR.mUBoat.length;
			}
		
		
		}
	}
	/**************************************End Shop*************************************************************/
	/**************************************Start Win Over*******************************************************/
	void WinOver()
	{
		M.sound3Pause();
		M.stop();
		mGR.mAdd++;
		mGR.resumeCounter = 0;
		if(M.PlayerLife<=0)
		{
//			if(mGR.adsCount%3==0)
			GameRenderer.mStart.show();
//			mGR.adsCount++;
			M.GameScreen = M.GAMEOVER;
			M.PlayerLife=mGR.lastLife;
		}
		if(mGR.mPlayer.mNoKill>=mGR.mPlayer.mKillTarget)
		{
//			if(mGR.adsCount%3==0)
				GameRenderer.mStart.show();//Smart();
//			mGR.adsCount++;
			M.GameScreen = M.GAMEWIN;
			mGR.mPlayer.mXP+=(mGR.mLevel*4);
			mGR.mPlayer.mLCoin+=(mGR.mLevel*4);
		}
		mGR.mPlayer.Accurecy = (mGR.mPlayer.Accurecy*100)/(mGR.mPlayer.FirBullate+1);
		mGR.mPlayer.mLCoin+=(mGR.mPlayer.Accurecy*3);
		mGR.mPlayer.mCoin+=mGR.mPlayer.mLCoin;
	}
	void DrawWinOver(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_GameBG[0] , 0, 0.519f,3.2000f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[1] , 0, 0.519f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[2] , 0, -.479f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[3] , 0, -.170f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[4] , 0, -.069f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_Black		, 0, 0.000f,1.0000f,1);

		
		DrawTexture	(gl, mGR.mTex_Pattern[0],0.641f, 0.0f);
		DrawTexture	(gl, mGR.mTex_Pattern[2],-.641f, 0.0f);
		DrawTexture(gl, mGR.mTex_Option[1]	,-.00f, .80f);
		DrawTexture(gl, mGR.mTex_Scorebox		, .00f, .26f);
//		DrawTexture(gl, mGR.mTex_Objectbox		,-.67f, .26f);
//		DrawTexture(gl, mGR.mTex_Objectbox		, .67f, .26f);
		
		DrawTexture	(gl, mGR.mTex_Add[mGR.mAdd%3],-.67f, .26f);
		DrawTexture	(gl, mGR.mTex_Add[(mGR.mAdd+1)%3],0.67f, .26f);
		
		
		
		if(M.GAMEOVER == M.GameScreen)
		{
			mGR.mFont.draw(gl, "Wave Failed",0.00f, 0.82f, 4, 1);
			
			mGR.mFont.draw(gl, "Kill:"+mGR.mPlayer.mNoKill				,0, .55f-.11f, 0, 1);//-.26f
			mGR.mFont.draw(gl, "XP:0"									,0, .55f-.22f, 3, 1);
			mGR.mFont.draw(gl, "Money : "+mGR.mPlayer.mLCoin			,0, .55f-.33f, 2, 1);
			mGR.mFont.draw(gl, "Accuracy : "+mGR.mPlayer.Accurecy		,0, .55f-.44f, 5, 1);
			
		}
		else if(M.GAMEWIN == M.GameScreen)
		{
			mGR.mFont.draw(gl, "Wave Cleared"							,0.00f, 0.82f, 4, 1);
			
			mGR.mFont.draw(gl, "Kill:"+mGR.mPlayer.mNoKill				,0, .55f-.11f, 0, 1);//-.26f
			mGR.mFont.draw(gl, "XP:"+(mGR.mLevel*4)						,0, .55f-.22f, 3, 1);
			mGR.mFont.draw(gl, "Money : "+mGR.mPlayer.mLCoin				,0, .55f-.33f, 2, 1);
			mGR.mFont.draw(gl, "Accuracy : "+mGR.mPlayer.Accurecy		,0, .55f-.44f, 5, 1);
		}
		else if(M.GAMEPUSE == M.GameScreen)
		{
			mGR.mFont.draw(gl, "Wave Pause"							,0.00f, 0.82f, 4, 1);
		}
//		mGR.mFont.draw(gl, M.GETFREE,-.67f,.42f, 5, 1);
//		mGR.mFont.draw(gl, M.GETFREE, .67f,.42f, 5, 1);
		
		float y = (mGR.resumeCounter > 60)?-.46f:-.85f;
		
		if(mGR.resumeCounter > 60){
			DrawTexture	(gl, mGR.mTex_Button0[0],0.0f, y);
			mGR.mFont.draw(gl, M.CONTINUE		,0.0f, y, 0, 1);
			if(mGR.mSel == 6)
				DrawTexture	(gl, mGR.mTex_Button0[1],0.0f, y);
		}
		else{
			DrawTexture	(gl, mGR.mTex_Button0[0],0.0f, y);
			mGR.mFont.draw(gl, M.CONTINUE		,0.0f, y, 0, 1);
			if(mGR.mSel == 6)
				DrawTexture	(gl, mGR.mTex_Button0[1],0.0f, y);
		}
			
	}
	boolean Handle_WinOver(MotionEvent event) 
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.67f, .26f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 4;//Get Free Coin
		}
		if(CircRectsOverlap(0.67f, .26f,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 5;//Get Free Coin
		}
		float y = (mGR.resumeCounter > 60)?-.46f:-.85f;
		if(CircRectsOverlap(0.0f, y,mGR.mTex_Option[0].width()*.4f,mGR.mTex_Option[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 6;//Continue
		}
		
	
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			
			case 4:
				Download(M.LOADGAME[(mGR.mAdd%3)]);
				//Get Free Coin
				break;
			case 5:
				Download(M.LOADGAME[((mGR.mAdd+1)%3)]);
				//Get Free Coin
				break;
			case 6:
				if(M.GameScreen == M.GAMEPUSE){
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext, R.drawable.background);
				}
				else {
					if(M.GameScreen == M.GAMEWIN)
						mGR.mLevel++;
					M.GameScreen = M.GAMESHOP;
				}
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.gearsift);
			mGR.mSel = 0;
		}
		return true;
	}
	/**************************************End Win Over*********************************************************/
	/**************************************Start Game Play******************************************************/
	void bgMove()
	{
		float x = .01f;
		float in = -2f;
		mGR.mBGMove1[0]-=x*.2f;
		mGR.mBGMove1[1]-=x*.2f;
		if(mGR.mBGMove1[0] < in)
			mGR.mBGMove1[0] = mGR.mBGMove1[1] +  2;
		if(mGR.mBGMove1[1] < in)
			mGR.mBGMove1[1] = mGR.mBGMove1[0] +  2;

		mGR.mBGMove2[0]-=x*2f;
		mGR.mBGMove2[1]-=x*2f;
		if(mGR.mBGMove2[0] < in)
			mGR.mBGMove2[0] = mGR.mBGMove2[1] +  2;
		if(mGR.mBGMove2[1] < in)
			mGR.mBGMove2[1] = mGR.mBGMove2[0] +  2;

		mGR.mBGMove3[0]-=x*3f;
		mGR.mBGMove3[1]-=x*3f;
		if(mGR.mBGMove3[0] < in)
			mGR.mBGMove3[0] = mGR.mBGMove3[1] +  2;
		if(mGR.mBGMove3[1] < in)
			mGR.mBGMove3[1] = mGR.mBGMove3[0] +  2;

		mGR.mBGMove4[0]-=x*4f;
		mGR.mBGMove4[1]-=x*4f;
		if(mGR.mBGMove4[0] < in)
			mGR.mBGMove4[0] = mGR.mBGMove4[1] +  2;
		if(mGR.mBGMove4[1] < in)
			mGR.mBGMove4[1] = mGR.mBGMove4[0] +  2;

	}
	void setOpp(int _no)
	{
		float low = -1.5f;
		for(int i = 0;i<mGR.mOpp.length;i++)
		{
			if(low>mGR.mOpp[i].x)
			{
				low = mGR.mOpp[i].x;
			}
		}
		mGR.setOpp(_no,low-.16f);
		mGR.BoatSet();
	}
	void gameplay()
	{
//		int i;
		bgMove();
		for(int i=0;i<mGR.mOpp.length;i++)
		{
			if(mGR.mOpp[i].mLife>0)
			{
				mGR.mOpp[i].update(Counter%5==0?true:false);
			}
			
		}
		if(Counter%10==0 && M.mOppSet < mGR.mPlayer.mKillTarget)
		{
			for(int i = 0;i<mGR.mOpp.length;i++)
			{
				if(mGR.mOpp[i].mLife < -12)
				{
					setOpp(i);
				}
			}
		}
		if(Counter%5==0 && mGR.mPlayer.tuch && mGR.mReload >45)
		{
			if(mGR.mPlayer.mBullet>0)
				mGR.mPlayer.mBullet--;
			if(mGR.mPlayer.mBullet<=0)
				mGR.mReload = 0;
			mGR.mPlayer.FirBullate++;
			boolean target = false;
			for(int i=0;i<mGR.mOpp.length && mGR.mPlayer.mBullet>0;i++)
			{
				if(CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mTex_Boat[mGR.mOpp[i].mNo].width()*.4f, mGR.mTex_Boat[mGR.mOpp[i].mNo].Height()*.4f, mGR.mPlayer.tx, mGR.mPlayer.ty, .02)
						&&  mGR.mOpp[i].mLife>0)
				{
					target=true;
					mGR.mOpp[i].mLife-=mGR.mPlayer.mDamageBy;
					mGR.mPlayer.mLCoin+=1;
					if(mGR.mOpp[i].mLife<=0)
					{
						mGR.mPlayer.mNoKill++;
					}
				}
			}
			if(target)
				mGR.mPlayer.Accurecy++;
		}
		
		if(Counter%10==0 && mGR.mPlayer.Power > -1)
		{
			float x = -1;
			int k = 0;
			for(int i=0;i<mGR.mOpp.length;i++)
			{
				if(x<mGR.mOpp[i].x &&  mGR.mOpp[i].mLife>0)
				{
					x = mGR.mOpp[i].x;
					k = i;
				}
			}
			if(x>-(mGR.mPlayer.Power+1)/100f)
			{
				mGR.mOpp[k].mLife-=mGR.mPower[mGR.mPlayer.Power].mDamage;
				mGR.mPlayer.mLCoin+=1;
				if(mGR.mOpp[k].mLife<=0)
					mGR.mPlayer.mNoKill++;
			}
		}
		if(mGR.NetBlast>-1.1f){
			mGR.NetBlast-=.01f;
			for(int i=0;i<mGR.mOpp.length;i++)
			{
				if(mGR.mOpp[i].x >mGR.NetBlast-.1f && mGR.mOpp[i].x <mGR.NetBlast+.1f && mGR.mOpp[i].mLife>0)
				{
					mGR.mOpp[i].mLife = 0;
					mGR.mPlayer.mNoKill++;
				}
			}
		}
		if(mGR.mPlayer.tuch && mGR.mPlayer.mBullet > 0 && mGR.mReload >45)
			M.playGun(GameRenderer.mContext,mGR.mPlayer.mGunType,mGR.mPlayer.tuch,Counter);
		else
			M.playGun(GameRenderer.mContext,mGR.mPlayer.mGunType,false,Counter);

		if(M.PlayerLife<=0 || mGR.mPlayer.mNoKill>=mGR.mPlayer.mKillTarget)
		{
			mGR.mWait++;
			if(mGR.mWait>60 || M.PlayerLife<=0)
				WinOver();
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		int i =0;
		gameplay();
		DrawTextureS(gl, mGR.mTex_GameBG[0], 0, 0.519f,3.2f,1);

		DrawTextureS(gl, mGR.mTex_GameBG[1], mGR.mBGMove1[0], 0.519f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBG[1], mGR.mBGMove1[1], 0.519f,1.5625f,1);

		DrawTextureS(gl, mGR.mTex_GameBG[2] , mGR.mBGMove4[0], -.479f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBGF[0], mGR.mBGMove4[1], -.479f,1.5625f,1);

		DrawTextureS(gl, mGR.mTex_GameBG[3] , mGR.mBGMove3[0], -.170f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBGF[1], mGR.mBGMove3[1], -.170f,1.5625f,1);

		DrawTextureS(gl, mGR.mTex_GameBG[4] , mGR.mBGMove2[0], -.069f,1.5625f,1);
		DrawTextureS(gl, mGR.mTex_GameBGF[2], mGR.mBGMove2[1], -.069f,1.5625f,1);

		
		if(mGR.bombBlast>-1 && mGR.bombBlast < mGR.mTex_BBlast.length)
		{
			DrawTexture(gl, mGR.mTex_BBlast[mGR.bombBlast], -.20f,-.4f+mGR.mTex_BBlast[mGR.bombBlast].Height()/2);
			if(Counter%2==0)
				mGR.bombBlast++;
		}
		
		
		boolean power = false;
		for(i=0;i<mGR.mOpp.length;i++)
		{
			if(mGR.mOpp[i].mLife>0){
				if(mGR.mOpp[i].x>-.2)
					power =true;
				if((mGR.mOpp[i].mNo == 2 || mGR.mOpp[i].mNo == 5 || mGR.mOpp[i].mNo == 11)&& Counter%2==0){
					int m = 0;
					if(mGR.mOpp[i].mNo == 5)
						m = 1;
					if(mGR.mOpp[i].mNo == 11)
						m = 2;
					DrawTexture(gl, mGR.mTex_BoatX[m], mGR.mOpp[i].x,mGR.mOpp[i].y);
				}
				else
				{
					DrawTexture(gl, mGR.mTex_Boat[mGR.mOpp[i].mNo], mGR.mOpp[i].x,mGR.mOpp[i].y);
				}
				if(mGR.mOpp[i].mNo == 14)
					DrawTexture(gl, mGR.mTex_BoatX[3], mGR.mOpp[i].x-.05f,mGR.mOpp[i].y-.1f);
				if(Counter%2==0 && mGR.mOpp[i].x > -.1f)
				{
					switch (mGR.mOpp[i].mNo) {
					case 0:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.07f,mGR.mOpp[i].y+.05f);
						break;
					case 1:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.04f,mGR.mOpp[i].y+.09f);
						break;
					case 2:
						DrawTexture(gl, mGR.mTex_BFire[1], mGR.mOpp[i].x+.17f,mGR.mOpp[i].y-.10f);
						break;
					case 3:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x-.03f,mGR.mOpp[i].y+.10f);
						break;
					case 4:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.14f,mGR.mOpp[i].y+.10f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.07f,mGR.mOpp[i].y+.10f);
						break;
					case 5:
						DrawTexture(gl, mGR.mTex_BFire[1], mGR.mOpp[i].x+.21f,mGR.mOpp[i].y-.09f);
						break;
					case 6:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.04f,mGR.mOpp[i].y+.11f);
						break;
					case 7:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.00f,mGR.mOpp[i].y+.11f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.10f,mGR.mOpp[i].y+.10f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x-.06f,mGR.mOpp[i].y+.10f);
						break;
					case 8:
						DrawTexture(gl, mGR.mTex_BFire[1], mGR.mOpp[i].x+.12f,mGR.mOpp[i].y-.14f);
						break;
					case 9:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.08f,mGR.mOpp[i].y+.05f);
						break;
					case 10:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x-.01f,mGR.mOpp[i].y+.09f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.06f,mGR.mOpp[i].y+.09f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.15f,mGR.mOpp[i].y+.09f);
						break;
					case 11:
						DrawTexture(gl, mGR.mTex_BFire[1], mGR.mOpp[i].x+.22f,mGR.mOpp[i].y-.12f);
						break;
					case 12:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.17f,mGR.mOpp[i].y+.11f);
						break;
					case 13:
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x+.29f,mGR.mOpp[i].y+.125f);
						DrawTexture(gl, mGR.mTex_BFire[0], mGR.mOpp[i].x-.17f,mGR.mOpp[i].y+.09f);
						break;
					case 14:
						DrawTexture(gl, mGR.mTex_BFire[1], mGR.mOpp[i].x+.24f,mGR.mOpp[i].y-.05f);
						break;
						
						}
				}
				float yy=.6f;
				DrawTexture(gl, mGR.mTex_mLife, mGR.mOpp[i].x,mGR.mOpp[i].y+mGR.mTex_Boat[mGR.mOpp[i].mNo].Height()*yy);
				float life =(mGR.mOpp[i].mLife)*(30f/(6f+(mGR.mOpp[i].mNo*M.LS)));
				mGR.mTex_Fill.drawSS(gl,-.08f+mGR.mOpp[i].x,mGR.mOpp[i].y+mGR.mTex_Boat[mGR.mOpp[i].mNo].Height()*yy, life, 1);
				
			}
			if(mGR.mOpp[i].mLife<1 && mGR.mOpp[i].mLife>-13)
			{
				mGR.mOpp[i].mLife --;
				if(mGR.mOpp[i].mNo%3 == 2)
				{
					DrawTexture(gl, mGR.mTex_CBlast[Math.abs(mGR.mOpp[i].mLife%mGR.mTex_CBlast.length)], mGR.mOpp[i].x,mGR.mOpp[i].y);
				}
				else{
					mGR.mTex_Boat[mGR.mOpp[i].mNo].drawColor(gl, mGR.mOpp[i].x,mGR.mOpp[i].y, .4f, .4f, .4f);
					DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x,mGR.mOpp[i].y);
					DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x-.05f,mGR.mOpp[i].y+.02f);
					DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x+.05f,mGR.mOpp[i].y+.02f);
				}
				if(mGR.mOpp[i].mNo == 4||mGR.mOpp[i].mNo == 3||mGR.mOpp[i].mNo == 7||mGR.mOpp[i].mNo == 9||mGR.mOpp[i].mNo == 12||mGR.mOpp[i].mNo == 13)
				{
					DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x-.10f,mGR.mOpp[i].y+.03f);
					DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x+.10f,mGR.mOpp[i].y+.03f);
					if(mGR.mOpp[i].mNo == 12||mGR.mOpp[i].mNo == 13)
					{
						DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x-.15f,mGR.mOpp[i].y+.04f);
						DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.mOpp[i].x+.15f,mGR.mOpp[i].y+.04f);
					}
				}
			}
		}

		if(mGR.NetBlast>-1.1)
		{
			for(i =0;i<10;i++)
				DrawTexture(gl, mGR.mTex_Fire[Counter%mGR.mTex_Fire.length], mGR.NetBlast,-(i*.08f));
		}
		
		
		DrawTexture(gl, mGR.mTex_BWall[1], -.64f,-.87f);
		DrawTexture(gl, mGR.mTex_BWall[0],  .64f,-.87f);
		
		DrawTextureS(gl, mGR.mTex_PIcon		, 0.8f,-.8f,1.6f,1.6f);
		DrawTextureS(gl, mGR.mTex_RIcon		, 0.8f,-.8f,1.6f,1.6f);
		
		DrawTextureS(gl, mGR.mTex_PIcon	 		, 0.80f,.60f,1.6f,1.6f);
		DrawTextureS(gl, mGR.mTex_UBoat[0]		, 0.80f,.60f,0.8f,0.8f);
		mGR.mFont.draw(gl,mGR.mPlayer.Bomb+""	, 0.82f,.56f,   2,   0);
		
		
		
		DrawTextureS(gl, mGR.mTex_PIcon	 		, 0.80f,.20f,1.6f,1.6f);
		DrawTextureS(gl, mGR.mTex_UBoat[1]		, 0.80f,.20f,0.6f,0.6f);
		mGR.mFont.draw(gl,mGR.mPlayer.Net+""	, 0.82f,.16f,   2,  0 );
		
		
		DrawTexture(gl, mGR.mTex_Player, mGR.mPlayer.x,mGR.mPlayer.y);
		if(mGR.mPlayer.Power>=0){
			if(LowCount%2==0 && power)
				DrawTexture(gl, mGR.mTex_mPFire, mGR.mPlayer.x-.05f+sx,mGR.mPlayer.y+.17f+sy);
			DrawTexture(gl, mGR.mTex_mPowrBoat[mGR.mPlayer.Power%mGR.mTex_mPowrBoat.length], mGR.mPlayer.x,mGR.mPlayer.y+.15f);
		}
		if(mGR.mPlayer.mBullet <=0 || mGR.mReload <46)
		{
			if(mGR.mReload>1){
				mGR.mTex_Reload1.drawSS(gl,-.23f,0, mGR.mReload, 1);
				DrawTexture(gl, mGR.mTex_Reload0,0,0);
			}else if(Counter%2==0){
				DrawTexture(gl, mGR.mTex_Reload0,0,0);
			}
			if(mGR.mReload>.3)
				mGR.mReload+=1.5f;
		}

		DrawTexture(gl, mGR.mTex_Target	, mGR.mPlayer.tx,mGR.mPlayer.ty);

		DrawTextureS(gl, mGR.mTex_UBoat[2]	,-.95f,.9f,.5f,.5f);
		mGR.mFont.draw(gl, M.PlayerLife+""					, -.9f, 0.9f,1,0);
		
		DrawTextureS(gl, mGR.mTex_mPowrBoat[1]	,-.15f,.91f,1f,1f);
		mGR.mFont.draw(gl, mGR.mPlayer.mBullet+"/"+mGR.Bullet,-.1f, 0.9f,1,0);
		
		mGR.mFont.draw(gl, "Wave:"+mGR.mLevel	, 0.7f, 0.9f,1,0);
//		mGR.mFont.draw(gl, mGR.mPlayer.mNoKill+""			, -.9f, -.9f,1,0);
	}
	public boolean Handle_GamePlay(MotionEvent ev) 
	{
		//		mGR.mPlayer.tuch = mGR.mTarget.tuch = false;
		int count =  MotionEventCompat.getPointerCount(ev);
		for(int i=0;i<count;i++)
		{
			final float x = MotionEventCompat.getX(ev, i); 
			final float y = MotionEventCompat.getY(ev, i); 
			if(CirCir(0.8f,-.8f, .1f, screen2worldX(x), screen2worldY(y), .1f))
			{
				mGR.mPlayer.mBullet = mGR.Bullet;
				mGR.mReload = 1;
				M.sound10(GameRenderer.mContext, R.drawable.reload);
				//Reload
			}
			else
			{
				mGR.mPlayer.tuch = true;
				mGR.mPlayer.tx = screen2worldX(x);
				mGR.mPlayer.ty = screen2worldY(y)+.2f;
			}
		}
		if(MotionEvent.ACTION_UP== ev.getAction())
		{
			mGR.mPlayer.tuch = false;
			if(CirCir(0.80f,.60f, .1f, screen2worldX(ev.getX()), screen2worldY(ev.getY()), .1f) && mGR.mPlayer.Bomb > 0)
			{
				mGR.bombBlast = 0;
				M.sound9(GameRenderer.mContext, R.drawable.explosion);
				for(int i=0;i<mGR.mOpp.length;i++)
				{
					if(mGR.mOpp[i].x >-1 && mGR.mOpp[i].mLife>0)
					{
						mGR.mOpp[i].mLife = 0;
						mGR.mPlayer.mNoKill++;
					}
				}
				mGR.mPlayer.Bomb-=1;
			}
			if(CirCir(0.8f,.2f, .1f, screen2worldX(ev.getX()), screen2worldY(ev.getY()), .1f)&& mGR.mPlayer.Net > 0)
			{
				mGR.NetBlast = .2f;
				mGR.mPlayer.Net-=1;
			}
		} 

		return true;
	}
	/****************************************End Game Play******************************************************/
	

	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scalx,float scaly)
	{
		Tex.drawScal(gl, x, y,scalx,scaly);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawFlipScal(GL10 gl,SimplePlane Tex,float x,float y,float scalx,float scaly)
	{
		Tex.drawFilpScal(gl, x, y,scalx,scaly);
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
	float world2screenX(float a)
	{
		float c = ((a * M.ScreenWidth/2)+ (M.ScreenWidth/2));
		return c;
	}
	float world2screenY(float a)
	{
		float c = M.ScreenHieght/2 - (a*M.ScreenHieght/2);
		return c;
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
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Download(String str)
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+str));
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
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now,  let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
