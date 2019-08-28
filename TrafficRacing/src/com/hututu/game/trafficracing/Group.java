package com.hututu.game.trafficracing;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
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
	public Group(GameRenderer _GameRenderer){
		mGR = _GameRenderer;
	}
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen = M.GAMEMAP;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 60){
				M.GameScreen = M.GAMEADD;
				Counter =0;
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Ling,0.0f,-.9f);
				mGR.mTex_LingBar.drawSS(gl,-.63f,-.9f	, Counter*.63f, 1.3f);//10
//				System.out.println("   ~~~~~~~~~~ "+Counter+"  ~~~~  "+(Counter*.63f));
//				DrawTexture(gl, mGR.mTex_LingBar,.2f+Counter*(mGR.mTex_Bar.width()/100f)-.32f,-.9f);
			}
			break;
//		case M.GAMELOAD:
//			DrawTexture(gl, mGR.mTex_PCar[mGR.mPlayer.carNo][0],-1+Counter*(.01f),-.8f);
//			DrawTexture(gl, mGR.mTex_Gun,-1+Counter*(.01f),-.8f);
//			DrawTexture(gl, mGR.mTex_Loading,0,-.9f);
//			if(Counter>200){
//				M.GameScreen = M.GAMEPLAY;
//				sound();
//			}
//			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEMAP:
			Draw_Map(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Draw_Over(gl);
			break;
		case M.GAMECONG:
		case M.GAMEPAUSE:
			Draw_Cong(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		default:
			Draw_GamePLay(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
//			if(MotionEvent.ACTION_UP == event.getAction())
//				SetAnim();
			break;
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				sound();
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEMAP:
			Handle_Map(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Handle_Over(event);
			break;
		case M.GAMECONG:
		case M.GAMEPAUSE:
			Handle_Cong(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		default:
			Handle_GamePlay(event);
			break;
		}
		Handle(event);
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ About Start  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBG	, 0.00f,0.00f);
//		DrawTexture(gl, mGR.mTex_Box	, 0.00f,-.07f);
		DrawTexture(gl, mGR.mTex_AScr	, 0.00f,-.07f);
		DrawTexture(gl, mGR.mTex_Button	, -.73f,-.92f);
		DrawTransScal(gl, mGR.mTex_Back	, -.73f,-.92f,(mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?0.5f:1);
		
	}
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.73f,-.9f, mGR.mTex_Cont.width()*0.5f,mGR.mTex_Cont.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ About End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Shop Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Shop(GL10 gl)
	{
		float yy = .19f;
		DrawTexture(gl, mGR.mTex_AllBG	, 0.00f,0.00f);
//		DrawTexture(gl, mGR.mTex_ADS	,0.00f,0.55f+.1f+yy);
		DrawTransScal(gl, mGR.mTex_ADS	,0.00f,0.55f+.1f+yy,mGR.mSel == 31?1.05f:1,mGR.mSel == 31?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_Coins	,0.00f,0.35f+yy);
		drawNumber(gl, mGR.coin, -.1f, .35f+yy);
		if(!mGR.mCarUp){
//			DrawTexture(gl, mGR.mTex_CarT	,0.00f,0.55f+yy+.1f);
			for(int i = 0;i<4;i++){
				DrawTexture(gl, mGR.mTex_SCarBG[mGR.mPlayer.carNo==i?0:1],-.36f,0.03f-i*.23f+yy);
				DrawTexture(gl, mGR.mTex_Pointer	,-.16f,0.03f-i*.23f+yy);
				DrawTextureS(gl, mGR.mTex_PCar[i][0],-.45f,0.03f-i*.23f+yy,.7f);
			}
			if(mGR.mPlayer.buy[mGR.mPlayer.carNo] != 0){
				DrawTexture(gl, mGR.mTex_CarBTN	,0.17f,0.03f+yy);
				DrawTransScal(gl, mGR.mTex_Buy	,0.52f,0.04f+yy,mGR.mSel == 5?1.5f:1,mGR.mSel == 5?0.5f:1);
				drawNumber(gl, mGR.mPlayer.buy[mGR.mPlayer.carNo], 0.05f, .03f+yy);
			}
			else
			{
				DrawTexture(gl, mGR.mTex_CarBTN	,0.27f,0.10f+yy);
				DrawTexture(gl, mGR.mTex_Upgrade,0.27f,0.10f+yy);
				if(mGR.mSel == 4)
					DrawTexture(gl, mGR.mTex_CarBS	,0.27f,0.10f+yy);
			}
			
			for(int i = 0;i<4;i++){
				DrawTexture(gl, mGR.mTex_UText[i]	,.13f,-.15f-i*.15f+yy);
				for(int j = 0;j<6;j++){
					if(mGR.mPlayer.power[mGR.mPlayer.carNo][i]>j)
						DrawTexture(gl, mGR.mTex_Fill[1],.30f+j*.05f,-.15f-i*.15f+yy);
					else
						DrawTexture(gl, mGR.mTex_Fill[0],.30f+j*.05f,-.15f-i*.15f+yy);
				}
			}
		}
		else
		{
//			DrawTexture(gl, mGR.mTex_Upgrade	,0.00f,0.55f+.1f+yy);
			for(int i = 0;i<3;i++){
				DrawTexture(gl, mGR.mTex_UIcn[i]	,-.43f,-i*.28f+yy);
				for(int j = 0;j<6;j++){
					if(mGR.mPlayer.power[mGR.mPlayer.carNo][i]>j)
						DrawTexture(gl, mGR.mTex_Fill[1],-.27f+j*.07f,-i*.28f+yy);
					else
						DrawTexture(gl, mGR.mTex_Fill[0],-.27f+j*.07f,-i*.28f+yy);
				}
				if(mGR.mPlayer.power[mGR.mPlayer.carNo][i]<6){
					DrawTransScal(gl, mGR.mTex_Buy	,0.48f,-i*.28f+yy,mGR.mSel == (6+i)?1.1f:1,mGR.mSel == (6+i)?0.5f:1);
					DrawTexture(gl, mGR.mTex_Vlue	,0.29f,-i*.28f+yy);
					int c = (int)(mGR.mPlayer.power[mGR.mPlayer.carNo][i]*((mGR.mPlayer.carNo+1)*53f));//calculation
					drawNumber(gl, c, 0.29f,-i*.28f+yy);
				}
			}
		}
		DrawTexture(gl, mGR.mTex_Button	,-.73f,-.92f);
		DrawTexture(gl, mGR.mTex_Button	,0.73f,-.92f);
		DrawTransScal(gl, mGR.mTex_Back	, -.73f,-.92f,(mGR.mSel == 1||mGR.mSel == 3)?1.1f:1,(mGR.mSel == 1||mGR.mSel == 3)?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Cont	, 0.74f,-.92f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
	}
	boolean Handle_Shop(MotionEvent event)
	{
		mGR.mSel = 0;
		float yy = .19f;
		if(CircRectsOverlap(0.00f,0.55f+.1f+yy, mGR.mTex_ADS.width()*0.3f,mGR.mTex_ADS.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 31;
		}
		
		if(CircRectsOverlap(-.73f,-.92f, mGR.mTex_Cont.width()*0.5f,mGR.mTex_Cont.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mCarUp)
				mGR.mSel = 3;
			else
				mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.74f,-.92f, mGR.mTex_Cont.width()*0.5f,mGR.mTex_Cont.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(mGR.mPlayer.buy[mGR.mPlayer.carNo] != 0){
			if(!mGR.mCarUp && CircRectsOverlap(0.52f,0.04f+yy, mGR.mTex_Buy.width()*0.5f,mGR.mTex_Buy.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 5;
			}
		}
		else
		{
			if(!mGR.mCarUp && CircRectsOverlap(0.27f,0.10f+yy, mGR.mTex_CarBTN.width()*0.5f,mGR.mTex_CarBTN.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 4;
			}
		}
		for(int i = 0;i<3 && mGR.mCarUp;i++){
			if(CircRectsOverlap(0.48f,-i*.28f+yy, mGR.mTex_Buy.width()*0.5f,mGR.mTex_Buy.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 6+i;
			}
		}
		for(int i = 0;i<4 && !mGR.mCarUp;i++){
			if(CircRectsOverlap(-.36f,0.03f-i*.23f+yy, mGR.mTex_Buy.width()*0.5f,mGR.mTex_Buy.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 10+i;
			}
		}
		int c1;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				break;
			case 3:
				mGR.mCarUp = false;
				break;
			case 4:
				mGR.mCarUp = true;
				break;
			case 5:
				if(mGR.coin >= mGR.mPlayer.buy[mGR.mPlayer.carNo]){
					mGR.coin -= mGR.mPlayer.buy[mGR.mPlayer.carNo];
					mGR.mPlayer.buy[mGR.mPlayer.carNo] = 0;
				}
				else
					GameRenderer.mStart.Dont();
				break;
			case 6:
				c1 = (int)(mGR.mPlayer.power[mGR.mPlayer.carNo][0]*((mGR.mPlayer.carNo+1)*53f));//calculation
				if(mGR.mPlayer.power[mGR.mPlayer.carNo][0]<6){
					if(mGR.coin >= c1){
						mGR.coin -= c1;
						mGR.mPlayer.power[mGR.mPlayer.carNo][0]++;
					}
					else
						GameRenderer.mStart.Dont();
				}
				
					
				break;
			case 7:
				c1 = (int)(mGR.mPlayer.power[mGR.mPlayer.carNo][1]*((mGR.mPlayer.carNo+1)*53f));//calculation
				if(mGR.mPlayer.power[mGR.mPlayer.carNo][1]<6){
					if(mGR.coin >= c1){
						mGR.coin -= c1;
						mGR.mPlayer.power[mGR.mPlayer.carNo][1]++;
					}
					else
						GameRenderer.mStart.Dont();
				}
				
				break;
			case 8:
				c1 = (int)(mGR.mPlayer.power[mGR.mPlayer.carNo][2]*((mGR.mPlayer.carNo+1)*53f));//calculation
				if(mGR.mPlayer.power[mGR.mPlayer.carNo][2]<6 ){
					if(mGR.coin >= c1){
					mGR.coin -= c1;
					mGR.mPlayer.power[mGR.mPlayer.carNo][2]++;}
					else
						GameRenderer.mStart.Dont();
				}
				
				break;
			case 10:case 11:case 12:case 13:
				mGR.mPlayer.carNo = mGR.mSel - 10;
				break;
			case 31:
				mGR.mMainActivity.onBuyGold50000(null);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Shop End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Map Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Map(GL10 gl)
	{
		DrawTransScal(gl, mGR.mTex_Map	, 0,0,1f,1);
//		DrawTransScal(gl, mGR.mTex_Mark, sx,sy,1f,1);
		DrawTransScal(gl, mGR.mTex_Cont	, 0.8f,-.9f,mGR.mSel == 1?1.5f:1,mGR.mSel == 1?0.5f:1);
		if(M.uLevel>0 && (mGR.mHeli.g || mGR.mHeli.x>=0.045f))
		{
			 if((M.uLevel==1 && Counter %2==0)||M.uLevel!=1)
				 DrawTransScal(gl, mGR.mTex_Mark, 0.07f, .28f,1.1f,1);
		}
		if(M.uLevel>1)
		{
			if((M.uLevel==2 && Counter %2==0)||M.uLevel!=2)
				 DrawTransScal(gl, mGR.mTex_Mark, -.535f, .65f,1.1f,1);
			
		}
		if(M.uLevel>2){
			if((M.uLevel==3 && Counter %2==0)||M.uLevel!=3)
				 DrawTransScal(gl, mGR.mTex_Mark, -.365f,-.389f,1.1f,1);
		}
		if(M.uLevel>3){
			if((M.uLevel==4 && Counter %2==0)||M.uLevel!=4)
				 DrawTransScal(gl, mGR.mTex_Mark, 0.56f,-.50f,1.1f,1);
		}
		if(!mGR.mHeli.g){
		if(mGR.mHeli.x<0.045f){
			DrawTexture(gl, mGR.mTex_Heli[Counter%2]	, mGR.mHeli.x,mGR.mHeli.y);
			DrawTransScal(gl, mGR.mTex_Heli[Counter%2]	, mGR.mHeli.x,mGR.mHeli.y-mGR.mBullet[0].y,1f,.4f);
			mGR.mHeli.x+=.01f;
			mGR.mHeli.y+=.0049f;
			mGR.mBullet[0].co=0;
			if(mGR.mBullet[0].y>0)
				mGR.mBullet[0].y -=.0017f;
		}else if(mGR.mBullet[0].co>=0 && mGR.mBullet[0].co < mGR.mTex_Blast.length) {
			DrawTexture(gl, mGR.mTex_Heli[Counter%2]	, mGR.mHeli.x,mGR.mHeli.y);
			DrawTransScal(gl, mGR.mTex_Heli[Counter%2]	, mGR.mHeli.x,mGR.mHeli.y-mGR.mBullet[0].y,1f,.4f);
//			mGR.mBullet[0].co++;
		}
		else
			mGR.mHeli.g = true;
		}
	}
	boolean Handle_Map(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.8f,-.9f, mGR.mTex_Cont.width()*0.5f,mGR.mTex_Cont.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESHOP;
				mGR.mCarUp = false;
				mGR.mHeli.g = true;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Map End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Over Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Over(GL10 gl)
	{
//		for(int i =0;i<mGR.mBG.length;i++)
//		{
//			if(mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f){
//				DrawTexture(gl, mGR.mTex_BG[mGR.mBG[i].no], mGR.mBG[i].x, mGR.mBG[i].y);
//			}
//		}
		DrawTexture(gl, mGR.mTex_AllBG	, 0.00f,0.00f);
//		DrawTexture(gl, mGR.mTex_Box	, 0.00f,-.07f);
		if(M.GameScreen == M.GAMEWIN){
			DrawTexture(gl, mGR.mTex_NLevel	,0.00f,0.50f);
			DrawTexture(gl, mGR.mTex_Next	,0.35f,-.65f);
		}
		else{
			DrawTexture(gl, mGR.mTex_GOver	,0.00f,0.50f);
			DrawTexture(gl, mGR.mTex_Retry	,0.35f,-.65f);
		}
		DrawTexture(gl, mGR.mTex_NScore	,-.35f,0.10f);
		DrawTexture(gl, mGR.mTex_SBar	,0.20f,0.10f);
		drawNumber(gl, mGR.mScore, 0.05f,0.10f);
		DrawTexture(gl, mGR.mTex_BScore	,-.35f,-.12f);
		DrawTexture(gl, mGR.mTex_SBar	,0.20f,-.12f);
		drawNumber(gl, mGR.mHScore, 0.05f,-.12f);
		DrawTransScal(gl, mGR.mTex_View	,-.3f,-.33f,mGR.mSel == 1?1.5f:1,mGR.mSel == 1?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_CarBTN	,0.3f,-.33f);
		DrawTexture(gl, mGR.mTex_Submit	, .3f,-.33f);
		DrawTexture(gl, mGR.mTex_Shop	,-.35f,-.65f);
		DrawTexture(gl, mGR.mTex_Menu	,-00,-.65f);
		
		switch (mGR.mSel) {
		case 2:
			DrawTexture(gl, mGR.mTex_CarBS	,0.3f,-.33f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_CirSel	,-.35f,-.65f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_CirSel	,-.00f,-.65f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_CirSel	,0.35f,-.65f);
			break;
		}
		
	}
	boolean Handle_Over(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CircRectsOverlap(-.3f,-.33f, mGR.mTex_View.width()*0.4f,mGR.mTex_View.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//View
		}
		if(CircRectsOverlap(.3f,-.33f, mGR.mTex_Submit.width()*0.4f,mGR.mTex_Submit.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//Submit
		}
		if(CircRectsOverlap(-.35f,-.65f, mGR.mTex_Shop.width()*0.5f,mGR.mTex_Shop.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Shop
		}
		if(CircRectsOverlap(-.0f,-.65f, mGR.mTex_Shop.width()*0.5f,mGR.mTex_Shop.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Menu
		}
		if(CircRectsOverlap(0.35f,-.65f, mGR.mTex_Shop.width()*0.5f,mGR.mTex_Shop.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Retry
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//View;
				break;
			case 2:
				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
				//Submit
				break;
			case 3:
				M.GameScreen = M.GAMESHOP;
				mGR.mCarUp = false;
				
				break;
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			case 5:
				if(M.GameScreen == M.GAMEWIN){
					M.GameScreen = M.GAMEMAP;
				}
				else
				{
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
				}
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Over End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Cong Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_Cong(GL10 gl)
	{
//		for(int i =0;i<mGR.mBG.length;i++)
//		{
//			if(mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f){
//				DrawTexture(gl, mGR.mTex_BG[mGR.mBG[i].no], mGR.mBG[i].x, mGR.mBG[i].y);
//			}
//		}
		DrawTexture(gl, mGR.mTex_AllBG	, 0.00f,0.00f);
//		DrawTexture(gl, mGR.mTex_Box	, 0.00f,-.07f);
		DrawTexture(gl, mGR.mTex_CarBTN	,-00,-.65f);
		if(M.GAMEPAUSE == M.GameScreen){
			DrawTexture(gl, mGR.mTex_GPuase	,0.00f,0.50f);
			DrawTexture(gl, mGR.mTex_Cont	,-00,-.65f);
			DrawTexture(gl, mGR.mTex_Shop	,-.35f,-.65f);
		}else{
			DrawTexture(gl, mGR.mTex_Cong	,0.00f,0.50f);
			DrawTexture(gl, mGR.mTex_PAgain	,-00,-.65f);
			DrawTexture(gl, mGR.mTex_Rate	,-.35f,-.65f);
		}
		
		DrawTexture(gl, mGR.mTex_NScore	,-.35f,0.10f);
		DrawTexture(gl, mGR.mTex_SBar	,0.20f,0.10f);
		drawNumber(gl, mGR.mScore, 0.05f,0.10f);
		DrawTexture(gl, mGR.mTex_BScore	,-.35f,-.12f);
		DrawTexture(gl, mGR.mTex_SBar	,0.20f,-.12f);
		drawNumber(gl, mGR.mHScore, 0.05f,-.12f);
		DrawTransScal(gl, mGR.mTex_View	,-.3f,-.33f,mGR.mSel == 1?1.5f:1,mGR.mSel == 1?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_CarBTN	,0.3f,-.33f);
		DrawTexture(gl, mGR.mTex_Submit	, .3f,-.33f);
		
		
		DrawTexture(gl, mGR.mTex_More	,0.35f,-.65f);
		switch (mGR.mSel) {
		case 2:
			DrawTexture(gl, mGR.mTex_CarBS	,0.3f,-.33f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_CirSel	,-.35f,-.65f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_CarBS	,0.0f,-.65f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_CirSel	,0.35f,-.65f);
			break;
		}
		
	}
	boolean Handle_Cong(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CircRectsOverlap(-.3f,-.33f, mGR.mTex_View.width()*0.4f,mGR.mTex_View.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//View
		}
		if(CircRectsOverlap(.3f,-.33f, mGR.mTex_Submit.width()*0.4f,mGR.mTex_Submit.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//Submit
		}
		if(CircRectsOverlap(-.4f,-.65f, mGR.mTex_Shop.width()*0.5f,mGR.mTex_Shop.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Shop
		}
		if(CircRectsOverlap(-.0f,-.65f, mGR.mTex_CarBTN.width()*0.4f,mGR.mTex_CarBTN.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Play  Again
		}
		if(CircRectsOverlap(0.4f,-.65f, mGR.mTex_Shop.width()*0.5f,mGR.mTex_Shop.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Retry
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//View;
				break;
			case 2:
				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
				//Submit
				break;
			
			case 3:
				if(M.GAMEPAUSE==M.GameScreen)
					M.GameScreen = M.GAMESHOP;
				else
					RateUs();
				break;
			case 4:
				if(M.GAMEPAUSE==M.GameScreen){
					M.GameScreen = M.GAMEPLAY;
					sound();
				}
				else
					GameRenderer.mStart.gReset();
				break;
			case 5:
				MoreGame();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Cong End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Menu Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	float mo =0;
	float mov =.001f;
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		for(int i =0;i<mGR.mTruk.length;i++){
			mGR.mTruk[i].update();
		}
		DrawTextureS(gl, mGR.mTex_Truk[mGR.mTruk[0].no][0], mGR.mTruk[0].x, mGR.mTruk[0].y,mGR.mTruk[0].z);
		DrawTextureS(gl, mGR.mTex_Truk[mGR.mTruk[1].no][1],-mGR.mTruk[1].x, mGR.mTruk[1].y,mGR.mTruk[1].z);
		
		DrawTexture(gl, mGR.mTex_name, 0.0f, .71f);
		DrawTexture(gl, mGR.mTex_gore, 0.6f,-.73f+mo);
		DrawTransScal(gl, mGR.mTex_FB	, 0.89f,0.40f,mGR.mSel == 1?1.5f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_GP	, 0.89f,0.07f,mGR.mSel == 2?1.5f:1,mGR.mSel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Play	, -.68f,-.13f,mGR.mSel == 3?1.2f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_About, -.68f,-.43f,mGR.mSel == 4?1.2f:1,mGR.mSel == 4?0.5f:1);
		DrawTransScal(gl,mGR.mTex_HScore, -.68f,-.73f,mGR.mSel == 5?1.2f:1,mGR.mSel == 5?0.5f:1);
		mo+=mov;
		if(mo>.1)mov=-.001f;
		if(mo< 0)mov=0.001f;
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(0.89f,0.40f, mGR.mTex_FB.Height()*0.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1; 
		}
		if(CirCir(0.89f,0.07f, mGR.mTex_GP.Height()*0.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.68f,-.13f, mGR.mTex_Play.width()*0.5f,mGR.mTex_Play.Height()*0.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.68f,-.43f, mGR.mTex_HScore.width()*0.5f,mGR.mTex_HScore.Height()*0.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.68f,-.73f, mGR.mTex_About.width()*0.5f,mGR.mTex_About.Height()*0.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(0.6f,-.73f+mo, mGR.mTex_gore.width()*0.3f, mGR.mTex_gore.Height()*0.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6; 
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				FaceBook();
				break;
			case 2:
				Google();
				break;
			case 3:
				if(!mGR.mHeli.g){
					mGR.mHeli.set(-1.2f, -.2f);
					mGR.mBullet[0].set(-100f, 0.2f);
				}
				M.GameScreen = M.GAMEMAP;
				break;
			case 4:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			
			case 6:
				MoreGame();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Menu End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Game Play Start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void setCoinAnim(float x, float y)
	{
		for(int i=0;i<mGR.mACoin.length;i++)
		{
			if(mGR.mACoin[i].x<-.8f||mGR.mACoin[i].x>.8||mGR.mACoin[i].y<-.7||mGR.mACoin[i].y>.7)
			{
				mGR.mACoin[i].set(x,y);
				break;
			}
		}
		M.sound5(GameRenderer.mContext, R.drawable.coin);
	}
	void Collide(float x, float y)
	{
		M.SPEED = 0;
		mGR.mBullet[0].setAni(x, y);
		mGR.mPlayer.blast();
//		M.sound2(GameRenderer.mContext, R.drawable.car_crash);
	}
	void checkCollition(int no,float x)
	{
		switch (no) {
		case 3:case 6:
			no=1;
			break;
		case 7:
			no=2;
			break;
		case 8:
			no=3;
			break;
		default:
			no =0;
			break;
		}
		for(int i=0;i<BGPATH.x[0].length;i++)
		{
			if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y,mGR.mTex_PCar[1][0].width()*.4f,mGR.mTex_PCar[1][0].Height()*.4f,
					BGPATH.getX(BGPATH.x[no][i])+x, BGPATH.getY(BGPATH.y[no][i]), .03f)){
				Collide(mGR.mPlayer.x, mGR.mPlayer.y);
				break;
			}
		}
	}
	void SetAnim()
	{
		int k=0;
		for(int i =0;i<mGR.mAni.length&&k<1;i++)
		{
			if(mGR.mAni[i].x < -.9 || mGR.mAni[i].x >.9|| mGR.mAni[i].y<-.9||mGR.mAni[i].y>.9f || mGR.mAni[i].z<1.1)
			{
				mGR.mAni[i].set(mGR.mPlayer.x+.06f, mGR.mPlayer.y);
				k++;
			}
		}
	}
	void DrawAnimation(GL10 gl)
	{
		for(int i =0;i<mGR.mAni.length;i++)
		{
			if(mGR.mAni[i].x > -1 && mGR.mAni[i].x <1 && mGR.mAni[i].y>-1 && mGR.mAni[i].y < 1 && mGR.mAni[i].z<3)
			{
				DrawTransScal(gl, mGR.mTex_Smoke, mGR.mAni[i].x, mGR.mAni[i].y, mGR.mAni[i].z,mGR.mAni[i].t);
				mGR.mAni[i].update();
			}
		}
	}
	void opponentdie(int i)
	{
		M.sound2(GameRenderer.mContext, R.drawable.car_crash);
		for(int j =0;j<mGR.mCOpp.length;j++)
		{
			if(mGR.mCOpp[j].x<-1.5f)
			{
				mGR.mCOpp[j].cSet(mGR.mOpp[i]);
				break;
			}
		}
		
	}
	void gameLogic()
	{
		int co = -1;
		for(int i =0;i<mGR.mBG.length;i++)
		{
			mGR.mBG[i].x+=M.SPEED;
			if(co==-1 && CircRectsOverlap(mGR.mBG[i].x, mGR.mBG[i].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, 1.2f, 0, .05f))
			{
				co =i;
			}
		}
		if(co == -1)
			co =0; 
		for(int i =0;i<mGR.mBG.length;i++)
		{
			if(mGR.mBG[i].x<-1.5f){
				mGR.mBG[i].x = mGR.mBG[i==0?mGR.mBG.length-1:i-1].x + mGR.mTex_BG[0].width();
				mGR.mBG[i].no = mGR.RArry; 
				mGR.RCount--;
				if(mGR.RCount<=0)
				{
					mGR.RArry++;
					mGR.RArry %= BackGround.Road.length;
					mGR.RCount = BackGround.Road[mGR.RArry];
				}
			}
			if((mGR.mBG[i].no == 2) && mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f && (mGR.mPlayer.y > .2f || mGR.mPlayer.y < -.62f) ){
				if(CircRectsOverlap(mGR.mBG[i].x, mGR.mBG[i].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					Collide(mGR.mPlayer.x, mGR.mPlayer.y);
			}
			if((mGR.mBG[i].no == 1||mGR.mBG[i].no == 3 || mGR.mBG[i].no == 5||mGR.mBG[i].no == 6||
					mGR.mBG[i].no == 7||mGR.mBG[i].no == 8) && mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f){
				if(CircRectsOverlap(mGR.mBG[i].x, mGR.mBG[i].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					checkCollition(mGR.mBG[i].no,mGR.mBG[i].x);
			}
			if(mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f && (mGR.mPlayer.y > .62f || mGR.mPlayer.y < -.62f) ){
				if(CircRectsOverlap(mGR.mBG[i].x, mGR.mBG[i].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					Collide(mGR.mPlayer.x, mGR.mPlayer.y);
			}
		}
		for(int i =0;i<mGR.mOpp.length;i++)
		{
			for(int j =i+1;j<mGR.mOpp.length;j++)
			{
				if(mGR.mOpp[i].x>-1.5f && mGR.mOpp[j].x > -1.5f)
				{
					if(CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mTex_OppCar[mGR.mOpp[i].no][0].width()*.4f, mGR.mTex_OppCar[mGR.mOpp[i].no][0].Height()*.4f, mGR.mOpp[j].x, mGR.mOpp[j].y, mGR.mTex_PCar[0][0].Height()*.4f))
					{
						mGR.mBullet[0].setAni(mGR.mOpp[i].x, mGR.mOpp[i].y);
						mGR.mOpp[i].x = -3f;
					}
				}
			}
			for(int j =0;j<mGR.mBG.length;j++){
				if((mGR.mBG[j].no == 1||mGR.mBG[j].no == 2||mGR.mBG[j].no == 3 || mGR.mBG[j].no == 5||mGR.mBG[j].no == 6) && mGR.mBG[j].x > -1.5f && mGR.mBG[j].x < 1.5f && mGR.mOpp[i].y > .2f ){
					if(CircRectsOverlap(mGR.mBG[j].x, mGR.mBG[j].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, mGR.mOpp[i].x, mGR.mOpp[i].y, .1f))
						mGR.mOpp[i].x+=M.SPEED*.5f;
				}
				if((mGR.mBG[j].no == 7||mGR.mBG[j].no == 8) && mGR.mBG[j].x > -1.5f && mGR.mBG[j].x < 1.5f && mGR.mOpp[i].y < -.2f ){
					if(CircRectsOverlap(mGR.mBG[j].x, mGR.mBG[j].y, mGR.mTex_BG[0].width()*.5f, mGR.mTex_BG[0].Height()*.5f, mGR.mOpp[i].x, mGR.mOpp[i].y, .1f))
						mGR.mOpp[i].x+=M.SPEED*.5f;
				}
			}
			for(int j =0;j<mGR.mBullet.length;j++)
			{
				if(mGR.mBullet[j].x>-1.2 && mGR.mBullet[j].x<1.2f && mGR.mBullet[j].co == -1){
					if(CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mTex_OppCar[mGR.mOpp[i].no][0].width()*.4f,mGR.mTex_OppCar[mGR.mOpp[i].no][0].Height()*.4f
							,mGR.mBullet[j].x,mGR.mBullet[j].y,mGR.mTex_Bullet.width()*.3f))
					{
						opponentdie(i);
						updateScore(5);
						mGR.mBullet[j].set(mGR.mOpp[i].x, mGR.mOpp[i].y);
						mGR.mBullet[j].co = 0;
						mGR.mOpp[i].x = -3f;
					}
				}
			}
			if(CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mTex_OppCar[mGR.mOpp[i].no][0].width()*.4f, mGR.mTex_OppCar[mGR.mOpp[i].no][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_PCar[0][0].Height()*.4f))
			{
				Collide(mGR.mOpp[i].x, mGR.mOpp[i].y);
				opponentdie(i);
				mGR.mOpp[i].x = -3f;
			}
			mGR.mOpp[i].x+=M.SPEED*.5f;
		}
		for(int i =0;i<mGR.mCoin.length;i++)
		{
			mGR.mCoin[i].x+=M.SPEED*.5f;
			if(mGR.mCoin[i].x<-1.5f){
				mGR.mCoin[i].set(mGR.mCoin[i==0?mGR.mCoin.length-1:i-1].x + .2f,mGR.cy);
				mGR.CoinSet++;
				if(mGR.CoinSet%10==0)
					mGR.cy =-.5f + M.mRand.nextFloat(); 
			}
			if(mGR.mCoin[i].x>-1.0f && mGR.mCoin[i].x< 0f && !mGR.mCoin[i].g){
				if(CircRectsOverlap(mGR.mPlayer.x,mGR.mPlayer.y,mGR.mTex_PCar[0][0].width()*.3f,mGR.mTex_PCar[0][0].Height()*.3f
						,mGR.mCoin[i].x, mGR.mCoin[i].y, mGR.mTex_Coin[0].width()*.9f)){
					mGR.mCoin[i].g = true;
					mGR.coin++;
					setCoinAnim(mGR.mCoin[i].x,mGR.mCoin[i].y);
					updateScore(1);
				}
			}
		}
		for(int i =0;i<mGR.mOpp.length&& Counter%mGR.oPPset==0&& M.SPEED !=0;i++)
		{
			if(mGR.mOpp[i].x<-1.5f){
//				System.out.println("[cnt = "+mGR.oPPCnt+"]  [set = "+mGR.oPPset+"]");
				mGR.oPPCnt++;
				if(mGR.oPPset>35 && mGR.oPPCnt%5==0)
					mGR.oPPset--;
				if(mGR.mBG[co].no == 1||mGR.mBG[co].no == 2||mGR.mBG[co].no == 3 ||
						mGR.mBG[co].no == 5||mGR.mBG[co].no == 6){
					mGR.mOpp[i].set(1.2f, (M.mRand.nextFloat()%.8f)-.6f);
				}
				else if(mGR.mBG[co].no == 7||mGR.mBG[co].no == 8){
					mGR.mOpp[i].set(1.2f, (M.mRand.nextFloat()%.4f)-.1f);
				}
				else
					mGR.mOpp[i].set(1.2f, M.mRand.nextFloat()-.5f);
				break;
			}
		}
		for(int i =0;i<mGR.mBullet.length;i++)
		{
			if(mGR.mBullet[i].x>-1.2 && mGR.mBullet[i].x<1.2f && mGR.mBullet[i].co == -1)
			{
				mGR.mBullet[i].x+=.05f;
			}
		}
		if(mGR.mPlayer.damege<=0 && mGR.mPlayer.start < 2)
		{
			M.GameScreen = M.GAMEOVER;
			callAds();
			M.sound10(GameRenderer.mContext, R.drawable.game_over);
			if(mGR.mPlayer.disBar<mGR.mPlayer.Dis)
				mGR.mPlayer.disBar = mGR.mPlayer.Dis;
			sound();
		}
		if(mGR.mPlayer.start > 0 && mGR.mPlayer.fule>.05f)
		{
			if(mGR.mPlayer.start == 1){
				M.SPEED = M.CSPEED;
			}
			mGR.mPlayer.start--;
		}
		if(mGR.mPlayer.fule<.05f && M.SPEED<0)
		{
			M.SPEED+=.001f;
			if(M.SPEED>0)
				M.SPEED =0;
		}
		mGR.mPlayer.update();
		
		if(mGR.mPlayer.fule<=0){
			M.GameScreen = M.GAMEOVER;
			callAds();
			M.sound10(GameRenderer.mContext, R.drawable.game_over);
			if(mGR.mPlayer.disBar<mGR.mPlayer.Dis)
				mGR.mPlayer.disBar = mGR.mPlayer.Dis;
			sound();
		}
		if(mGR.mPlayer.Dis>1.8f)
		{
			M.SPEED+=.001f;
			if(M.SPEED>0){
				M.GameScreen = M.GAMEWIN;
				callAds();
				if(M.uLevel<4)
					M.uLevel++;
				else
					M.GameScreen = M.GAMECONG;
				mGR.mPlayer.disBar =0;
			}
			sound();
		}
		if(Counter%200 ==0)
		{
			switch (M.mRand.nextInt(3)) {
			case 0:
				M.sound11(GameRenderer.mContext, R.drawable.horn);
				break;
			case 1:
				M.sound12(GameRenderer.mContext, R.drawable.horn1);
				break;
			default:
				M.sound13(GameRenderer.mContext, R.drawable.horn2);
				break;
			}
		}
	}
	void callAds()
	{
		if (mGR.ads % 1 == 0) {
			GameRenderer.mStart.show();
		}
		mGR.ads++;

	}
	void sound()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.play(GameRenderer.mContext, R.drawable.game_play);
		else
			M.play(GameRenderer.mContext, R.drawable.theme);
	}
	void updateScore(int iu){
		mGR.mScore+=156*iu;
		if(mGR.mScore>mGR.mHScore)
			mGR.mHScore=mGR.mScore;
	}
	
	
	void Draw_GamePLay(GL10 gl)
	{
		
		for(int i =0;i<mGR.mBG.length;i++)
		{
			if(mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f){
				DrawTexture(gl, mGR.mTex_BG[mGR.mBG[i].no], mGR.mBG[i].x, mGR.mBG[i].y);
			}
		}
		
		
		if(((mGR.mPlayer.start > 0 && Counter%2==0)||mGR.mPlayer.start <= 0) && mGR.mPlayer.damege>0){
			DrawTexture(gl, mGR.mTex_PCar[mGR.mPlayer.carNo][1], mGR.mPlayer.x, mGR.mPlayer.y);
			DrawTexture(gl, mGR.mTex_PCar[mGR.mPlayer.carNo][0], mGR.mPlayer.x, mGR.mPlayer.y);
			DrawTexture(gl, mGR.mTex_Gun, mGR.mPlayer.x, mGR.mPlayer.y);
		}
		
		for(int i =0;i<mGR.mBullet.length;i++){
			if(mGR.mBullet[i].x>-1.1 && mGR.mBullet[i].x<1.1f && mGR.mBullet[i].co==-1){
				DrawTexture(gl, mGR.mTex_Bullet, mGR.mBullet[i].x, mGR.mBullet[i].y);
			}else if(mGR.mBullet[i].co>-1 && mGR.mBullet[i].co<mGR.mTex_Blast.length){
				DrawTexture(gl, mGR.mTex_Blast[mGR.mBullet[i].co%mGR.mTex_Blast.length], mGR.mBullet[i].x, mGR.mBullet[i].y);
				mGR.mBullet[i].co++;
			}
		}
		for(int i =0;i<mGR.mOpp.length;i++)
		{
			if(mGR.mOpp[i].x > -1.5f && mGR.mOpp[i].x < 1.5f){
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mOpp[i].no][2], mGR.mOpp[i].x, mGR.mOpp[i].y, 1, 1, 1,1);
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mOpp[i].no][1], mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mOpp[i].r, mGR.mOpp[i].g, mGR.mOpp[i].b,1);
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mOpp[i].no][0], mGR.mOpp[i].x, mGR.mOpp[i].y, 1, 1, 1,1);
			}
		}
		for(int i =0;i<mGR.mCOpp.length;i++)
		{
			if(mGR.mCOpp[i].x>-1.0f){
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mCOpp[i].no][2], mGR.mCOpp[i].x, mGR.mCOpp[i].y, 1, 1, 1,1);
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mCOpp[i].no][1], mGR.mCOpp[i].x, mGR.mCOpp[i].y, mGR.mCOpp[i].r, mGR.mCOpp[i].g, mGR.mCOpp[i].b,mGR.mCOpp[i].t);
				DrawRGB(gl, mGR.mTex_OppCar[mGR.mCOpp[i].no][0], mGR.mCOpp[i].x, mGR.mCOpp[i].y, 1, 1, 1,mGR.mCOpp[i].t);
				mGR.mCOpp[i].update();
			}
		}
		
		for(int i =0;i<mGR.mCoin.length;i++)
		{
			if(mGR.mCoin[i].x>-1.0f && mGR.mCoin[i].x <1.1f &&!mGR.mCoin[i].g){
				DrawTextureS(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length],mGR.mCoin[i].x, mGR.mCoin[i].y,2);
			}
		}
		
		for(int i =0;i<mGR.mBG.length;i++)
		{
			if(mGR.mBG[i].x > -1.5f && mGR.mBG[i].x < 1.5f){
				if(mGR.mBG[i].no == 11)
					DrawTexture(gl, mGR.mTex_Tree, mGR.mBG[i].x+.14f, -.7f);
			}
		}
		DrawAnimation(gl);
		DrawTexturR(gl, mGR.mTex_Arrow, -.80f, -.3f,180,mGR.mPlayer.d == 1?1.9f:1.3f,mGR.mPlayer.d == 1?0.5f:1);
		DrawTexturR(gl, mGR.mTex_Arrow, -.80f, 0.3f,000,mGR.mPlayer.d == 2?1.9f:1.3f,mGR.mPlayer.d == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Joy  , 0.85f, -.3f,mGR.mSel == 1?1.8f:1.5f,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Boost, 0.50f, -.8f,mGR.mSel == 2?1.8f:1.5f,mGR.mSel == 2?0.5f:1);
		
		
		
		DrawTexture(gl, mGR.mTex_SBar, .74f, .86f);
		DrawTexture(gl, mGR.mTex_STxt, .62f, .86f);
		drawNumber(gl, mGR.mScore, .74f, .86f);
		
		DrawTexture(gl, mGR.mTex_CBar, .00f, .86f);
		DrawTexture(gl, mGR.mTex_UIcn[2], -.10f, .86f);
		drawNumber(gl, mGR.mPlayer.Gun, .00f, .86f);
		
		DrawTexture(gl, mGR.mTex_CBar, .79f, .68f);
		DrawTexture(gl, mGR.mTex_CTxt, .70f,.68f);
		drawNumber(gl, mGR.coin, .80f, .68f);
		
		if((mGR.mPlayer.fule <.3 && Counter %3==0)|| mGR.mPlayer.fule >.2 )
			mGR.mTex_Feel.drawSS(gl,-.84f,0.87f	, mGR.mPlayer.fule*1.6f, .9f);//10
		mGR.mTex_Feel.drawSS(gl,-.84f,0.66f	, mGR.mPlayer.boost*1.6f, .9f);
//		
		DrawTexture(gl, mGR.mTex_FuelBoost 	, -.7f, 0.75f);
		if((mGR.mPlayer.fule <.3 && Counter %2==0)){
			DrawTexture(gl, mGR.mTex_fuelglow 	,-.9f, .81f);
			if(mGR.mPlayer.fualSound%50==0)
				M.sound14(GameRenderer.mContext, R.drawable.low_fule);
			mGR.mPlayer.fualSound++;
		}
		
		
		
		DrawTransScal(gl, mGR.mTex_Bar	  	, 0, -.9f, 3.0f, 1);
		mGR.mTex_LingBar.drawScal(gl,-.9f+mGR.mPlayer.disBar,-.9f, 1.5f,2f);
		DrawTransScal(gl, mGR.mTex_Pointer	,-.9f+mGR.mPlayer.Dis,-.9f, 1, 1);
		
		for(int i =0;i<mGR.mACoin.length;i++)
		{
			if(mGR.mACoin[i].x > -0.85f && mGR.mACoin[i].x < 0.85f && mGR.mACoin[i].y > -0.75f && mGR.mACoin[i].y < .75f){
				DrawTextureS(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length],mGR.mACoin[i].x, mGR.mACoin[i].y,2);
				mGR.mACoin[i].update();
			}
		}
		
		gameLogic();
	}
	public boolean Handle_GamePlay(MotionEvent ev) 
	{
		mGR.mPlayer.d = 0;
		mGR.mSel = 0;
		int count =  MotionEventCompat.getPointerCount(ev);
		for(int i=0;i<count;i++)
		{
			final float x = MotionEventCompat.getX(ev, i); 
			final float y = MotionEventCompat.getY(ev, i); 
			mGR.mPlayer.d = 0;
			mGR.mSel = 0;
			if(x < M.ScreenWidth*.4f && y > M.ScreenHieght*.5f)
			{
				mGR.mPlayer.d = 1;
			}
			if(x < M.ScreenWidth*.4f && y < M.ScreenHieght*.5f)
			{
				mGR.mPlayer.d = 2;
			}
			if(CirCir(0.85f, -.3f, mGR.mTex_Arrow.Height()*0.9f, screen2worldX(x), screen2worldY(y), .05f))
			{
				mGR.mSel = 1;
			}
			if(CirCir( 0.50f, -.8f, mGR.mTex_Arrow.Height()*0.9f, screen2worldX(x), screen2worldY(y), .05f))
			{
				mGR.mSel = 2;
				mGR.mPlayer.isBoost = true; 
			}
		}
		if(mGR.mPlayer.fule<.05f || mGR.mPlayer.Dis>1.8f || mGR.mPlayer.damege<=0)
			mGR.mPlayer.isBoost = false;
		if(MotionEvent.ACTION_UP== ev.getAction())
		{
			mGR.mPlayer.d = 0;
			for(int i =0;i<mGR.mBullet.length && mGR.mSel==1 && mGR.mPlayer.Gun>0;i++)
			{
				if(mGR.mBullet[i].x<-1.1 || mGR.mBullet[i].x>1.1f || mGR.mBullet[i].co >= mGR.mTex_Blast.length)
				{
					mGR.mBullet[i].set(mGR.mPlayer.x+.06f, mGR.mPlayer.y);
					SetAnim();
					mGR.mPlayer.Gun--;
					break;
				}
			}
			if(mGR.mPlayer.isBoost){
				mGR.mPlayer.isBoost = false;
				M.SPEED = M.CSPEED;
			}
			mGR.mSel = 0;
		} 

		return true;
	}
	
	
	
	/*
	public boolean Handle_GamePLay(MotionEvent event) 
	{
		mGR.mPlayer.d = 0;
		mGR.mSel = 0;
		if(event.getX() < M.ScreenWidth*.4f && event.getY() > M.ScreenHieght*.5f)
		{
			mGR.mPlayer.d = 1;
		}
		if(event.getX() < M.ScreenWidth*.4f && event.getY() < M.ScreenHieght*.5f)
		{
			mGR.mPlayer.d = 2;
		}
		if(CirCir(0.85f, -.3f, mGR.mTex_Arrow.Height()*0.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CirCir( 0.50f, -.8f, mGR.mTex_Arrow.Height()*0.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
			mGR.mPlayer.isBoost = true; 
		}
		if(mGR.mPlayer.fule<.05f || mGR.mPlayer.Dis>1.8f)
			mGR.mPlayer.isBoost = false;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			mGR.mPlayer.d = 0;
			for(int i =0;i<mGR.mBullet.length && mGR.mSel==1 && mGR.mPlayer.Gun>0;i++)
			{
				if(mGR.mBullet[i].x<-1.1 || mGR.mBullet[i].x>1.1f || mGR.mBullet[i].co >= mGR.mTex_Blast.length)
				{
					mGR.mBullet[i].set(mGR.mPlayer.x+.06f, mGR.mPlayer.y);
					SetAnim();
					mGR.mPlayer.Gun--;
					break;
				}
			}
			if(mGR.mPlayer.isBoost){
				mGR.mPlayer.isBoost = false;
				M.SPEED = M.CSPEED;
			}
			mGR.mSel = 0;
		}
		return true;
	}*/
	/**~~~~~~~~~~~~~~~~~~~~~~~~~~~ Game Play End   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTexturR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float scal,float Tranc)
	{
		Tex.drawRotet(gl, x, y,angle,Tranc,scal);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b,float t)
	{
		Tex.drawRGB(gl, x, y, r, g, b,t);
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
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void FaceBook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
	    GameRenderer.mContext.startActivity(mIntent);
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
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
