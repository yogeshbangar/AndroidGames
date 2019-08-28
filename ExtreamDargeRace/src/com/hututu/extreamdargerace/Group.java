
package com.hututu.extreamdargerace;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	int Counter =0;
	int Counter2 =0;
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
	
	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEMENU;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>60){
				M.GameScreen = M.GAMESPLASH;
				M.play(GameRenderer.mContext, R.drawable.theme);
			}
			break;
		case M.GAMEADD:
			
			if(Counter>98){
				DrawTexture(gl, mGR.mTex_Conti, 0.8f, -.88f);
//				M.GameScreen = M.GAMEMENU;
			}else{
				mGR.mTex_Ling.drawScal(gl, 0,-.87f, 1, 0.9f);
				mGR.mTex_LBar.drawSS(gl,-.59f,-.87f, (Counter%100)*.158f, .8f);//10
			}
			break;
		case M.GAMEMENU:
		case M.GAMEQRACE:
		case M.GAMESHOP:
		case M.GAMEPUASE:
			Draw_Menu(gl);
			break;
		case M.GAMECARER:
			Draw_Career(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMEPLAY:
			Draw_Play(gl);
			break;
		case M.GAMEUPGRD:
			Draw_UPgrade(gl);
			break;
		case M.GAMESPLASH:
			Draw_Splash(gl);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			Draw_GameOver(gl);
			break;
		case M.GAMELOAD:
			DrawTexture(gl, mGR.mTex_City[mGR.city][1], 0, 0);
			mGR.mTex_LBar.drawScal(gl, 0, -.89f, 27, 2); 
			mGR.mTex_Ling.drawScal(gl, 0,-.87f, 1, 0.9f);
			mGR.mTex_LBar.drawSS(gl,-.59f,-.87f, (Counter%100)*.158f, .8f);//10
			if(Counter>98){
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.theme);
			}
			break;
		}
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMEADD:
			if(MotionEvent.ACTION_UP == event.getAction() && Counter >100 &&
			CircRectsOverlap(0.8f, -.88f, mGR.mTex_Conti.width()*.4f, mGR.mTex_Conti.Height()*.4f, 
					screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
		case M.GAMEMENU:
		case M.GAMEQRACE:
		case M.GAMESHOP:
		case M.GAMEPUASE:
			if(popup == 2/* && M.GameScreen == M.GAMESHOP*/){
				Handle_Purchase(event);
			}else
				Handle_Menu(event);
			break;
		case M.GAMECARER:
			Handle_Career(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEPLAY:
			Handle_Play(event);
			break;
		case M.GAMEUPGRD:
			if(popup == 2){
				Handle_Purchase(event);
			}else
				Handle_Upgarde(event);
			break;
		case M.GAMESPLASH:
			if(MotionEvent.ACTION_UP==event.getAction() && (ey<.8)){
				M.stop(GameRenderer.mContext);
				M.GameScreen = M.GAMEADD;
				Counter =0;
				M.sound12(GameRenderer.mContext, R.drawable.click);
				if(mGR.addFree)
					M.GameScreen = M.GAMEMENU;
			}
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			Handle_WinOver(event);
			break;
		}
		Handle(event);
		return true;
	}
	void load()
	{
		M.GameScreen = M.GAMELOAD;
		if(mGR.addFree){
			M.GameScreen = M.GAMEPLAY;
			M.play(GameRenderer.mContext, R.drawable.theme);
		}
	}
	/*************************WinOver Start******************************/
	int nnn =0;
	void Draw_GameOver(GL10 gl){
		DrawTexture(gl, mGR.mTex_WinLstBG, 0, 0);
		Draw_Bike(gl, -.66f, -.15f, mGR.mOpponent.BikeNo, 0, .6f,false,false);
		Draw_Bike(gl, -.66f, 0.38f, mGR.mPlayer.BikeNo, 0, .6f,false,false);
		
		mGR.mFont.Draw_String(gl, mGR.mOpponent.time + " sec", -.65f, 0.11f, 0,0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.time + " sec", -.65f, -.41f, 0, 0);

		mGR.mFont.Draw_String(gl, mGR.mPlayer.MaxSpeed + " km/sec", -.060f, 0.32f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.m0to60 + " sec", -.06f, -.01f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.m0to100 + " sec", -.06f, -.34f, 0, 0);

		mGR.mFont.Draw_String(gl, mGR.mPlayer.parfect + "", 0.65f, 0.44f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.Good + "", 0.65f, 0.28f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.over + "", 0.65f, 0.12f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.bonus + "", 0.65f, -.05f, 0, 0);
		mGR.mFont.Draw_String(gl, mGR.mPlayer.price + "", 0.65f, -.20f, 0, 0);
		int total = mGR.mPlayer.parfect+ mGR.mPlayer.Good+ mGR.mPlayer.over + mGR.mPlayer.bonus+mGR.mPlayer.price;
		mGR.mFont.Draw_String(gl, total + "", 0.65f, -.35f, 0, 0);
		
		
		if (M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_LostTxt, 0, .67f);
		else
			DrawTexture(gl, mGR.mTex_WinTxt, 0, .67f);
		
		DrawTransScal(gl, mGR.mTex_RetryB, -.69f+.46f*0, -.68f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_GarageB2, -.69f+.46f*1, -.68f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
		if(M.GameScreen == M.GAMEOVER)
			DrawTransScal(gl, mGR.mTex_HintB , -.69f+.46f*2, -.68f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		else if(!mGR.mIsQuckRace && M.GameScreen == M.GAMEWIN){
			DrawTransScal(gl, mGR.mTex_NextN , -.69f+.46f*2, -.68f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
		}
		DrawTransScal(gl, mGR.mTex_BackB , -.69f+.46f*3, -.68f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		if (popup == 1) {
			mGR.mTex_WinLstBG.drawColor(gl, 0,0,0,0,0,.9f);
			DrawTexture(gl, mGR.mTex_Popup, 0, 0);
			mGR.mFont.Draw_String(gl, "Upgrade your bike", sx, 0, 0, 1);
			mGR.mFont.Draw_String(gl, "Now",-.18f, -.25f, 2, 1);
			mGR.mFont.Draw_String(gl, "later",.21f, -.25f, 2, 1);
		}
	}
	boolean Handle_WinOver(MotionEvent event){
		mGR.mSel =0;
		if (popup == 1) {
			if (CircRectsOverlap(-.18f, -.25f, mGR.mTex_RetryB.width() * .4, mGR.mTex_RetryB.Height() * .4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 100;
			}
			if (CircRectsOverlap(0.18f, -.25f, mGR.mTex_RetryB.width() * .4, mGR.mTex_RetryB.Height() * .4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 101;
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (CircRectsOverlap(-.69f + .46f * i, -.68f, mGR.mTex_RetryB.width() * .4, mGR.mTex_RetryB.Height() * .4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
					mGR.mSel = i + 1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			nnn++;
			nnn%=17;
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				load();
				break;
			case 2:
				M.GameScreen = M.GAMEUPGRD;
				break;
			case 3:
				if(M.GameScreen == M.GAMEWIN){
					M.GameScreen = M.GAMELEVEL;
					if(mGR.citiUnlock)
						M.GameScreen = M.GAMECARER;
				}else{
					popup = 1;
				}
				break;
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			case 100:
				M.GameScreen = M.GAMEUPGRD;
				popup = 0;
				break;
			case 101:
				popup = 0;
				break;
				}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	/*************************WinOver End********************************/
	/*************************UPgrade Start******************************/
	final int wieght = 30;
	final int Exhaust = 35;
	final int Nirto = 40;
	final int GBox = 50;
	final int Mul = 5;
	int popup = 0;
	
	float scal =.1f ;
	float svx =1;
	float ey =2.5f ;
	float rx =2.5f ;
	void Draw_Splash(GL10 gl) {
		if(scal>1)
			scal=1.0f;
		if(scal<1)
			DrawTexture(gl, mGR.mTex_SBack[3], 0,-.46f);
		else{
			DrawTexture(gl, mGR.mTex_SBack[Counter%3], 0,-.46f);
		}
		DrawTexture(gl, mGR.mTex_Splash[0], 0,-.42f);
		DrawTexture(gl, mGR.mTex_Splash[1], 0,-.71f);
		if(scal<1)
			DrawTexture(gl, mGR.mTex_Splash[2], 0,-.71f);
		else{
			DrawTextureS(gl, mGR.mTex_Splash[2], 0,svx,2);
			svx+=.1f;
			if(svx>-.5f)
				svx = -1.5f;
		}
		
		DrawTexture(gl, mGR.mTex_Splash[3], 0,-.71f);
		DrawTexture(gl, mGR.mTex_Splash[4], 0,0.31f);
		
		for(int i=0;i<mGR.mAnim.length;i++){
			DrawFullRotate(gl, mGR.mTex_Ray, mGR.mAnim[i].x, mGR.mAnim[i].y, mGR.mAnim[i].ang);
			mGR.mAnim[i].update();
		}
		if(scal<1){
			DrawTextureS(gl, mGR.mTex_SBike[0], 0,-(1-scal)*.38f,scal);
			DrawTextureS(gl, mGR.mTex_SBike[1], 0,-(1-scal)*.38f+(-.55f)*scal,scal);
			scal*=1.1f;
		}
		else{
			DrawTexture(gl, mGR.mTex_SBike[0], 0,(Counter%2)*.007f);
			DrawTexture(gl, mGR.mTex_SBike[1], 0,(-.55f)-(Counter%2)*.007f);
			if(Counter%2==0)
				DrawTexture(gl, mGR.mTex_SBike[2], 0,-.45f);
			else
				DrawTexture(gl, mGR.mTex_SBike[3], 0,-.77f);
		}
		
		DrawTexture(gl, mGR.mTex_Name[0],-rx,.50f);
		DrawTexture(gl, mGR.mTex_Name[1], rx,.50f);
		DrawTexture(gl, mGR.mTex_Name[2],0,ey);
		
		if(rx>.56){
			rx-=.1f;
			if(rx<.56)
				rx = .56f;
		}
		
		if(ey>.79){
			ey-=.1f;
			if(ey<.79)
				ey = .79f;
		}
		
	}
	boolean Handle_Splash(MotionEvent event){
		return true;
	}

	void Purchase(GL10 gl) {
		mGR.mTex_WinLstBG.drawColor(gl, 0, 0, 0, 0, 0, .9f);
		DrawTexture(gl, mGR.mTex_PopBuy, 0, 0);
		
		DrawTransScal(gl, mGR.mTex_Adsfree , -.32f,0.10f, mGR.mSel == 22 ? 1.1f : 1, mGR.mSel == 22 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$0.99", -.30f,0.04f, 1, 0);
		mGR.mFont.Draw_String(gl, "20000", -.66f, 0.03f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,-.32f, -.41f, mGR.mSel == 23 ? 1.1f : 1, mGR.mSel == 23 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$1.99", -.30f, -.47f, 1, 0);
		mGR.mFont.Draw_String(gl, "50000", -.66f, -.49f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,0.58f, 0.10f, mGR.mSel == 24 ? 1.1f : 1, mGR.mSel == 24 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$2.99", 0.60f, 0.04f, 1, 0);
		mGR.mFont.Draw_String(gl, "80000", 0.26f, 0.03f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,0.58f, -.41f, mGR.mSel == 25 ? 1.1f : 1, mGR.mSel == 25 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$3.99", 0.60f, -.47f, 1, 0);
		mGR.mFont.Draw_String(gl,"150000", 0.26f, -.49f, 0, 0);

	}
	boolean Handle_Purchase(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap( 0.76, 0.56f, mGR.mTex_UpBox.width()*.4, mGR.mTex_UpBox.Height()*.3, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 21;
		}
		if(CircRectsOverlap( -.32f,0.10f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 22;
		}
		if(CircRectsOverlap( -.32f, -.41f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 23;
		}
		if(CircRectsOverlap( 0.58f, 0.10f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 24;
		}
		if(CircRectsOverlap(  0.58f, -.41f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 25;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 21:
				popup = 0;
				break;
			case 22:
				mGR.mMainActivity.onBuyGold10000(null);//$1
				break;
			case 23:
				mGR.mMainActivity.onBuyGold30000(null);//$2
				break;
			case 24:
				mGR.mMainActivity.onBuyGold50000(null);//$3
				break;
			case 25:
				mGR.mMainActivity.onBuyGold100000(null);//$4
				break;
			}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	void Draw_UPgrade(GL10 gl) {
		DrawTexture(gl, mGR.mTex_ComanBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Strip, 0, .735f);
		mGR.mFont.Draw_String(gl, "Engine"	, -.82f + .47f * 0, .73f, mGR.upgrade == 0?2:0, 0);
		mGR.mFont.Draw_String(gl, "Exhaust"	, -.82f + .47f * 1, .73f, mGR.upgrade == 1?2:0, 0);
		mGR.mFont.Draw_String(gl, "Nirto"	, -.82f + .47f * 2, .73f, mGR.upgrade == 2?2:0, 0);
		mGR.mFont.Draw_String(gl, "GearBox", -.82f + .47f * 3, .73f, mGR.upgrade == 3?2:0, 0);
		
		for (int i = 0; i < 4; i++) {
			DrawTransScal(gl, mGR.mTex_UpBox, -.62f + .42f * i, .33f, mGR.mSel == i+1 ? 1.1f : 1, mGR.mSel == i+1 ? 0.5f : 1);
			if (i < 3)
				DrawTexture(gl, mGR.mTex_UpArrow, -.42f + .42f * i, .33f);
		}
		mGR.mFont.Draw_String(gl, "Garage", 0.8f, .90f, 1,1);
//		mGR.upgrade =0;
//		mGR.mBike = 1;
		switch (mGR.upgrade) {
		case 0:
//			DrawTexture(gl, mGR.mTex_Engin[mGR.mSpec[mGR.mBike].lvlWeight>0?1:0], -.62f + .42f * 1, .36f);
//			DrawTexture(gl, mGR.mTex_Engin[mGR.mSpec[mGR.mBike].lvlWeight>1?1:0], -.62f + .42f * 2, .36f);
//			DrawTexture(gl, mGR.mTex_Engin[mGR.mSpec[mGR.mBike].lvlWeight>2?1:0], -.62f + .42f * 3, .36f);
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlWeight>=i){
					mGR.mFont.Draw_String(gl, "install", -.60f + .42f * i, .18f, 1, 1);
					DrawTexture(gl, mGR.mTex_Engin[1], -.62f + .42f * i, .36f);
				}
				else if(mGR.mSpec[mGR.mBike].lvlWeight +1 >=i){
					mGR.mFont.Draw_String(gl, ""+(i*(wieght*(mGR.mBike+1)*Mul)), -.60f + .42f * i, .18f, 1, 1);
					DrawTexture(gl, mGR.mTex_Engin[0], -.62f + .42f * i, .36f);
				}
				else
					DrawTexture(gl, mGR.mTex_Lock[2], -.62f + .42f * i, .36f);
			}
			break;
		case 1:
			DrawTexture(gl, mGR.mTex_Exhaus[1], -.62f + .42f * 0, .33f);
			if(mGR.mSpec[mGR.mBike].lvlExhaust >=0){
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>0?1:0], -.62f + .42f * 1, .33f);
			}
			if(mGR.mSpec[mGR.mBike].lvlExhaust >=1){
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>1?1:0], -.62f + .42f * 2, .30f);
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>1?1:0], -.62f + .42f * 2, .42f);
			}
			if(mGR.mSpec[mGR.mBike].lvlExhaust >=2){
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>2?1:0], -.62f + .42f * 3, .30f);
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>2?1:0], -.62f + .42f * 3, .37f);
				DrawTexture(gl, mGR.mTex_Exhaus[mGR.mSpec[mGR.mBike].lvlExhaust>2?1:0], -.62f + .42f * 3, .44f);
			}
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlExhaust>=i)
					mGR.mFont.Draw_String(gl, "install", -.60f + .42f * i, .18f, 1, 1);
				else if(mGR.mSpec[mGR.mBike].lvlExhaust+1 >=i){
					mGR.mFont.Draw_String(gl, ""+(i*(Exhaust*(mGR.mBike+1)*Mul)), -.60f + .42f * i, .18f, 1, 1);
				}
				else
					DrawTexture(gl, mGR.mTex_Lock[2], -.62f + .42f * i, .36f);
			}
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Nitro[1], -.62f + .42f * 0, .33f);
			if(mGR.mSpec[mGR.mBike].lvlNitro>=0){
				DrawTexture(gl, mGR.mTex_Nitro[mGR.mSpec[mGR.mBike].lvlNitro>0?1:0], -.62f + .42f * 1, .33f);
			}
			if(mGR.mSpec[mGR.mBike].lvlNitro >=1){
				DrawTexture(gl, mGR.mTex_Nitro[mGR.mSpec[mGR.mBike].lvlNitro>1?1:0]	, -.62f + .42f * 2, .33f);
				DrawTextureS(gl, mGR.mTex_Nitro[mGR.mSpec[mGR.mBike].lvlNitro>1?1:0], -.58f + .42f * 2, .42f,.7f);
			}
			if(mGR.mSpec[mGR.mBike].lvlNitro >=2){
				DrawTexture(gl, mGR.mTex_Nitro[mGR.mSpec[mGR.mBike].lvlNitro>2?1:0], -.62f + .42f * 3, .33f);
				DrawTextureS(gl, mGR.mTex_Nitro[mGR.mSpec[mGR.mBike].lvlNitro>2?1:0], -.58f + .42f * 3, .42f,.7f);
			}
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlNitro>=i)
					mGR.mFont.Draw_String(gl, "install", -.60f + .42f * i, .18f, 1, 1);
				else if(mGR.mSpec[mGR.mBike].lvlNitro+1 >=i){
					mGR.mFont.Draw_String(gl, ""+(i*(Nirto*(mGR.mBike+1)*Mul)), -.60f + .42f * i, .18f, 1, 1);
				}
				else
					DrawTexture(gl, mGR.mTex_Lock[2], -.62f + .42f * i, .36f);
			}
			break;
		case 3:
			
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlGearBox>=i){
					mGR.mFont.Draw_String(gl, "install", -.60f + .42f * i, .18f, 1, 1);
					DrawTexture(gl, mGR.mTex_Gear[1], -.62f + .42f * i, .33f);
				}
				else if(mGR.mSpec[mGR.mBike].lvlGearBox+1 >=i){
					DrawTexture(gl, mGR.mTex_Gear[0], -.62f + .42f * i, .33f);
					mGR.mFont.Draw_String(gl, ""+(i*(GBox*(mGR.mBike+1)*Mul)), -.60f + .42f * i, .18f, 1, 1);
				}
				else
					DrawTexture(gl, mGR.mTex_Lock[2], -.62f + .42f * i, .36f);
//					mGR.mFont.Draw_String(gl, ""+(i*(GBox*(mGR.mBike+1)*Mul)), -.60f + .42f * i, .18f, 1, 1);
			}
			break;
		}
		DrawTexture(gl, mGR.mTex_Stand, .23f, -.82f);
		Draw_Bike(gl, .28f, -.48f, mGR.mBike, 0,1,false,false);
		DrawTransScal(gl, mGR.mTex_Sing, -.77f, -.23f, mGR.mSel == 22 ? 1.1f : 1, mGR.mSel == 22 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, mGR.mPName	, -.75f, -.25f, 0, 1);
		mGR.mFont.Draw_String(gl, mGR.mDoller+"", -.80f, -.47f, 0, 0);
		DrawTransScal(gl, mGR.fromLevel?mGR.mTex_Conti:mGR.mTex_BackB, .83f,-.89f, mGR.mSel == 20 ? 1.1f : 1, mGR.mSel == 20 ? 0.5f : 1);
		DrawTransScal(gl,mGR.mTex_Buy2, -.76f,-.76f, mGR.mSel == 13 ? 1.1f : 1, mGR.mSel == 13 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_ShopBO ,-.29f,-.89f, mGR.mSel == 23 ? 1.1f : 1, mGR.mSel == 23 ? 0.5f : 1);
		
		if (popup == 1) {
			mGR.mTex_WinLstBG.drawColor(gl, 0,0,0,0,0,.9f);
			DrawTexture(gl, mGR.mTex_Popup, 0, 0);
			mGR.mFont.Draw_String(gl, "installed", 0, 0, 0, 1);
			mGR.mFont.Draw_String(gl, "ok", 0, -.25f, 2, 1);
		}
		if (popup == 2)
			Purchase(gl);
		
	}
	boolean Handle_Upgarde(MotionEvent event) {
		mGR.mSel = 0;
		if (popup == 2){
			
		}else if (popup == 1){
			if(CircRectsOverlap( 0, -.25f, mGR.mTex_UpBox.width()*.7, mGR.mTex_Strip.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 10;
			}
		}else{
			for (int i = 0; i < 4; i++) {
				if(CircRectsOverlap( -.62f + .42f * i, .33f, mGR.mTex_UpBox.width()*.4, mGR.mTex_UpBox.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
				{
					mGR.mSel = 1+i;
				}
			}
			for (int i = 0; i < 4; i++) {
				if(CircRectsOverlap( -.82f + .47f * i, .73f, mGR.mTex_UpBox.width()*.7, mGR.mTex_Strip.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
				{
					mGR.mSel = 5+i;
				}
			}
			if(CircRectsOverlap( .83f,-.89f, mGR.mTex_BackB.width()*.5f, mGR.mTex_BackB.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 20;
			}
			if(CircRectsOverlap( -.76f,-.76f, mGR.mTex_UpBox.width()*.7, mGR.mTex_Strip.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 21;
			}
			if(CircRectsOverlap(-.78f, -.24f, mGR.mTex_Arrow[0].width()*.5f, mGR.mTex_Arrow[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				if(!GameRenderer.mStart.isSignedIn())
					mGR.mSel = 22;
			}
			if(CircRectsOverlap(-.29f,-.89f, mGR.mTex_ShopBO.width()*.5f, mGR.mTex_ShopBO.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 23;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				popup = 1;
				break;
			case 2:case 3:case 4:
				switch (mGR.upgrade) {
				case 0:
					if(mGR.mSpec[mGR.mBike].lvlWeight>(mGR.mSel-2)){
						popup = 1;
					}else{
						if(mGR.mDoller>=(mGR.mSel-1)*(wieght*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlWeight+1 == mGR.mSel - 1){
								mGR.mSpec[mGR.mBike].lvlWeight = mGR.mSel - 1;
								mGR.mDoller-=(mGR.mSel-1)*(wieght*(mGR.mBike+1)*Mul);
							}
						}else{
							popup = 2;
						}
					}
					break;
				case 1:
					if(mGR.mSpec[mGR.mBike].lvlExhaust>(mGR.mSel-2)){
						popup = 1;
					}else{
						if(mGR.mDoller>=(mGR.mSel-1)*(Exhaust*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlExhaust+1 == mGR.mSel - 1){
								mGR.mSpec[mGR.mBike].lvlExhaust = mGR.mSel - 1;
								mGR.mDoller-=(mGR.mSel-1)*(Exhaust*(mGR.mBike+1)*Mul);
							}
							//mGR.mSpec[mGR.mBike].lvlExhaust = mGR.mSel - 1;
						}else{
							popup = 2;
						}
					}
					break;
				case 2:
					if(mGR.mSpec[mGR.mBike].lvlNitro>(mGR.mSel-2)){
						popup = 1;
					}else{
						if(mGR.mDoller>=(mGR.mSel-1)*(Nirto*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlNitro+1 == mGR.mSel - 1){
								mGR.mSpec[mGR.mBike].lvlNitro = mGR.mSel - 1;
								mGR.mDoller-=(mGR.mSel-1)*(Nirto*(mGR.mBike+1)*Mul);
							}
//							mGR.mSpec[mGR.mBike].lvlNitro = mGR.mSel - 1;
						}else{
							popup = 2;
						}
					}
					break;
				case 3:
					if(mGR.mSpec[mGR.mBike].lvlGearBox>(mGR.mSel-2)){
						popup = 1;
					}else{
						if(mGR.mDoller>=(mGR.mSel-1)*(GBox*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlGearBox+1 == mGR.mSel - 1){
								mGR.mSpec[mGR.mBike].lvlGearBox = mGR.mSel - 1;
								mGR.mDoller-=(mGR.mSel-1)*(GBox*(mGR.mBike+1)*Mul);
							}
//							mGR.mSpec[mGR.mBike].lvlGearBox = mGR.mSel - 1;
						}else{
							popup = 2;
						}
					}
					break;
				}
				break;
			
			case 5:case 6:case 7:case 8:
				mGR.upgrade = mGR.mSel -5;
				break;
			case 10:
				popup = 0;
				break;
			case 20:
				if(mGR.fromLevel)
					M.GameScreen =M.GAMECARER;
				else
					M.GameScreen =M.GAMEMENU;
				break;
			case 21:
				popup =2;
				break;
			case 22:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 23:
				M.GameScreen = M.GAMESHOP;
				break;
			}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************UPgrade End******************************/
	/*************************Level Start******************************/
	void Draw_Level(GL10 gl) {
		DrawTexture(gl, mGR.mTex_City[mGR.city][1], 0, 0);
		DrawTexture(gl, mGR.mTex_LevelBack, 0, 0);
		for (int i = 0; i < 4; i++) {
			if(mGR.mLevel == i)
				DrawTransScal(gl, mGR.mTex_LevelBoxS,-.76f+.23f*i, 0, mGR.mSel == i+1 ? 1.1f : 1, mGR.mSel == i+1 ? 0.5f : 1);
			else
				DrawTransScal(gl, mGR.mTex_LevelBox,-.76f+.23f*i, 0, mGR.mSel == i+1 ? 1.1f : 1, mGR.mSel == i+1 ? 0.5f : 1);
			if(mGR.gameUnlock[mGR.city][i]){
				if(mGR.gamePlayed[mGR.city][i])
					mGR.mFont.Draw_Number(gl, (i+1), -.76f+.23f*i, 0, 1);
				else
					DrawTexture(gl, mGR.mTex_Lock[1], -.76f+.23f*i,0);
			}else
				DrawTexture(gl, mGR.mTex_Lock[0], -.76f+.23f*i,0);
		}
		for (int i = 0; i < 3; i++) {
			if(mGR.mLevel == i+4)
				DrawTransScal(gl, mGR.mTex_LevelBoxS,-.07f+.23f*i, .47f, mGR.mSel == i+5 ? 1.1f : 1, mGR.mSel == i+5 ? 0.5f : 1);
			else
				DrawTransScal(gl, mGR.mTex_LevelBox,-.07f+.23f*i, .47f, mGR.mSel == i+5 ? 1.1f : 1, mGR.mSel == i+5 ? 0.5f : 1);
			if(mGR.gameUnlock[mGR.city][i+4]){
				if(mGR.gamePlayed[mGR.city][i+4])
					mGR.mFont.Draw_Number(gl, (i+1), -.07f+.23f*i, .47f, 1);
				else
					DrawTexture(gl, mGR.mTex_Lock[1], -.07f+.23f*i,.47f);
			}else
				DrawTexture(gl, mGR.mTex_Lock[0], -.07f+.23f*i,.47f);
			
		}
		for (int i = 0; i < 3; i++) {
			if(mGR.mLevel == i+7)
				DrawTransScal(gl, mGR.mTex_LevelBoxS,-.07f+.23f*i,-.47f, mGR.mSel == i+8 ? 1.1f : 1, mGR.mSel == i+8 ? 0.5f : 1);
			else
				DrawTransScal(gl, mGR.mTex_LevelBox,-.07f+.23f*i,-.47f, mGR.mSel == i+8 ? 1.1f : 1, mGR.mSel == i+8 ? 0.5f : 1);
			if(mGR.gameUnlock[mGR.city][i+7]){
				if(mGR.gamePlayed[mGR.city][i+7])
					mGR.mFont.Draw_Number(gl, (i+1),-.07f+.23f*i,-.47f, 1);
				else
					DrawTexture(gl, mGR.mTex_Lock[1], -.07f+.23f*i,-.47f);
			}else
				DrawTexture(gl, mGR.mTex_Lock[0], -.07f+.23f*i,-.47f);
			
		}
		
		
		
		mGR.mFont.Draw_String(gl, "BONUS "+(int)(5 *(mGR.city+1)*15), .57f, .60f-.12f*0, 1, 0);
//		mGR.mFont.Draw_String(gl, "500 METER", .57f, .60f-.12f*1, 1, 0);
		mGR.mFont.Draw_String(gl, "PRICE "+(int)(5 *(mGR.city+1)*100), .57f,0.60f-.12f*2, 1, 0);

		mGR.mFont.Draw_String(gl, "BONUS "+(int)(7 *(mGR.city+1)*15), .57f,-.35f-.12f*0, 1, 0);
//		mGR.mFont.Draw_String(gl, "700 METER", .57f,-.35f-.12f*1, 1, 0);
		mGR.mFont.Draw_String(gl, "PRICE "+(int)(7 *(mGR.city+1)*100), .57f,-.35f-.12f*2, 1, 0);
		
		mGR.mFont.Draw_String(gl, "BONUS "+(int)(2 *(mGR.city+1)*15),-.7f,-.25f-.12f*0, 1, 0); 
//		mGR.mFont.Draw_String(gl, "900 METER",-.7f,-.25f-.12f*1, 1, 0);
		mGR.mFont.Draw_String(gl, "PRICE "+(int)(2 *(mGR.city+1)*100),-.7f,-.25f-.12f*2, 1, 0);
		
		mGR.mFont.Draw_Number(gl, mGR.mDoller,0,-.91f, 1);
		DrawTransScal(gl, mGR.mTex_RaceB,0.83f, -.89f, mGR.mSel == 20 ? 1.1f : 1, mGR.mSel == 20 ? 0.5f : 1);
	}

	boolean Handle_Level(MotionEvent event) {
		mGR.mSel = 0;
		
		for (int i = 0; i < 4; i++) {
			if(CircRectsOverlap(-.76f+.23f*i, 0, mGR.mTex_LevelBoxS.width()*.4, mGR.mTex_LevelBoxS.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 1+i;
			}
			
		}
		for (int i = 0; i < 3; i++) {
			if(CircRectsOverlap(-.07f+.23f*i, .47f, mGR.mTex_LevelBoxS.width()*.4, mGR.mTex_LevelBoxS.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 5+i;
			}
		}
		for (int i = 0; i < 3; i++) {
			if(CircRectsOverlap(-.07f+.23f*i, -.47f, mGR.mTex_LevelBoxS.width()*.4, mGR.mTex_LevelBoxS.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 8+i;
			}
		}
		
		if(CircRectsOverlap(0.83f, -.89f, mGR.mTex_RaceB.width()*.4, mGR.mTex_RaceB.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 20;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(mGR.mSel>0 && mGR.mSel<11)
				if(mGR.gameUnlock[mGR.city][mGR.mSel-1]){
					mGR.mLevel = mGR.mSel-1;
			}
			if(mGR.mSel==20){
				mGR.gameReset();
				load();
			}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Career End******************************/
	/*************************Career Start****************************/
	final byte city[][]={
			{71,-26},
			{53,-06},
			{63, 26},
			{44, 49},
			{-29,-46},
			{-10,33},
			{-50,19},
	};
	final String cityName[]={
			"Singapore",
			"Thailand",
			"Hong Kong",
			"Russia",
			"Rio de Jeneiro",
			"Spain",
			"New York"
	};
	float mapx = 2.7f;
	float dsx = 1f;
	float dsvx = 0.01f;
	void Draw_Career(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Map2, 0, 0);
		for (int i = 0; i < 7; i++) {
			if (i == mGR.city)
				DrawTextureS(gl, mGR.mTex_MapDot, city[i][0] * .01f, city[i][1] * .01f,dsx);
			else if (i < mGR.city)
				DrawTexture(gl, mGR.mTex_MapDot, city[i][0] * .01f, city[i][1] * .01f);
			else
				DrawTexture(gl, mGR.mTex_MapDot2, city[i][0] * .01f, city[i][1] * .01f);
		}
		DrawTexture(gl, mGR.mTex_MapBox,mapx, -.56f);
		DrawTexture(gl, mGR.mTex_City[mGR.city][0], mapx, -.56f);
		DrawTransScal(gl, mGR.mTex_GoB,mapx, -.91f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, cityName[mGR.city],mapx-.21f, -.22f, 0, 0);
		if(mapx>-.71f)
			mapx -=.2f;
		else
			mapx =-.71f;
		dsx+=dsvx;
		if (dsx > 1.1f)
			dsvx = -.01f;
		if (dsx < 0.8f)
			dsvx = 0.01f;
	}

	boolean Handle_Career(MotionEvent event) {
		mGR.mSel = 0;
		if(CircRectsOverlap(-.71f, -.91f, mGR.mTex_GoB.width(), mGR.mTex_GoB.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMELEVEL;
				break;
			}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Career End******************************/
	/*************************Menu Start****************************/
	final float bykTyr[][]={
			{.20f,-.12f,-.14f,-.12f},//all
			{.20f,-.14f,-.15f,-.13f},//10
			{.20f,-.13f,-.17f,-.12f},//11
			{.19f,-.13f,-.17f,-.12f},//12
			{.21f,-.13f,-.20f,-.13f},//13
			{.21f,-.13f,-.17f,-.13f},//14
			{.21f,-.08f,-.17f,-.08f},//15
			{.21f,-.08f,-.17f,-.08f},//16
	};
	final float flame[][]={
			{-.24f,-.03f},//<10
			{-.24f,0.13f},//10
			{-.24f,0.13f},//11
			{-.33f,0.00f},//12
			{-.30f,0.05f},//13
			{-.28f,0.13f},//14
			{-.28f,-.13f},//15
			{-.18f,-.13f},//16
	};
	void SetSmock(float x,float y){
		for(int i=0;i<mGR.mSmock.length;i++){
			if(mGR.mSmock[i].x<=-1.6){
				mGR.mSmock[i].setanimation(x,y,i);
				break;
			}
		}
	}
	void Draw_Smock(GL10 gl){
		for(int i=0;i<mGR.mSmock.length;i++){
			if(mGR.mSmock[i].x>-1.7){
				DrawTransScal(gl, mGR.mTex_Mock, mGR.mSmock[i].x,mGR.mSmock[i].y,mGR.mSmock[i].z,mGR.mSmock[i].t);
				mGR.mSmock[i].update();
			}
		}
	}
	void Draw_Bike(GL10 gl, float x, float y, int no, float move, float scal, boolean falme, boolean Smock) {
		int i = 0;
		i = (no < 9) ? 0 : no - 9;
		if (scal > .9) {
			
			if(Counter%5==0 && Smock)
				SetSmock(x+0+ bykTyr[i][2], y-.11f+ bykTyr[i][3]);
			DrawFullRotate(gl, mGR.mTex_Tyre[no][1], x + bykTyr[i][0], y + bykTyr[i][1], move);
			DrawFullRotate(gl, mGR.mTex_Tyre[no][0], x + bykTyr[i][2], y + bykTyr[i][3], move);
//			if(Smock)
			{
				Draw_Smock(gl);
				if(Smock)
					DrawTexture(gl, mGR.mTex_Smock[Counter%2], x + bykTyr[i][2], y + bykTyr[i][3]);
			}
			DrawTexture(gl, mGR.mTex_Bike[no], x, y);
			if (falme)
				DrawTexture(gl, mGR.mTex_Flame[Counter % 2], x + flame[i][0], y + flame[i][1]);
		} else {
			mGR.mTex_Tyre[no][1].drawfullRotet(gl, x + bykTyr[i][0] * scal, y + bykTyr[i][1] * scal, move, 0, scal);
			mGR.mTex_Tyre[no][0].drawfullRotet(gl, x + bykTyr[i][2] * scal, y + bykTyr[i][3] * scal, move, 0, scal);
			DrawTextureS(gl, mGR.mTex_Bike[no], x, y, scal);
		}
	}
	int getCost(int bike)
	{
//		(i*(wieght*(mGR.mBike+1)*Mul))
		
		return (int)
				 ((6 * (wieght	* bike * Mul))
				+ (6 * (Exhaust	* bike * Mul))
				+ (6 * (Nirto	* bike * Mul))
				+ (6 * (GBox	* bike * Mul)));
	}
	float menux[] = {-.9f,-.4f,.4f,.9f};
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_ComanBG, 0, 0);
		for (int i = 0; i < 4; i++) {
			DrawFullRotate(gl, mGR.mTex_anim[0], menux[i] - .23f, .5f, Counter * 2);
			DrawFullRotate(gl, mGR.mTex_anim[1], menux[i] - .00f, .2f, -Counter * 3);
			DrawFullRotate(gl, mGR.mTex_anim[2], menux[i] - .40f, .5f, Counter * 4);
			DrawFullRotate(gl, mGR.mTex_anim[2], menux[i] - .10f, .3f, -Counter * 5);
			if(menux[i]>1.5f)
				menux[i] = menux[i==3?0:i+1]-.7f;
			menux[i]+=.005f;
		}
		
		
			
		mGR.mFont.Draw_String(gl, "LEVEL "+(mGR.city+1), 0.0f, .90f, 1,1);
		mGR.mFont.Draw_String(gl, mGR.mSpec[mGR.mBike].Name, -.0f, 0.70f, 0,1);
		if (M.GameScreen == M.GAMESHOP) {
			if (mGR.mBikevx == 0) {
				mGR.mBikex = mGR.mBike * .7f;
				for (int i = 0; i < 17; i++) {
					Draw_Bike(gl, mGR.mBikex - i * .7f, .33f, i, 0, 1, false,false);
				}
			} else {
				for (int i = 0; i < 17; i++)
					Draw_Bike(gl, mGR.mBikex - i * .7f, .33f, i, 0, 1, false,false);
				if (mGR.mBikevx > 0) {
					if (mGR.mBikex + .02 < (mGR.mBike + 1) * .7f) {
						mGR.mBikex += mGR.mBikevx;
						mGR.mBikevx -= .005f;
					} else {
						mGR.mBikevx = 0;
						mGR.mBike++;
					}
				}
				if (mGR.mBikevx < 0) {
					if (mGR.mBikex - .02 > (mGR.mBike - 1) * .7f) {
						mGR.mBikex += mGR.mBikevx;
						mGR.mBikevx += .005f;
					} else {
						mGR.mBikevx = 0;
						mGR.mBike--;
					}
				}
			}
			if (mGR.mBike > 0)
				DrawTransScal(gl, mGR.mTex_Arrow[0], -.83f, .32f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			if (mGR.mBike < 16)
				DrawTransScal(gl, mGR.mTex_Arrow[1], 0.83f, .32f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);

			
			if(mGR.mBikeUnlock[mGR.mBike]){
				mGR.mFont.Draw_String(gl, "Install", 0,-.09f, 0, 1);
				DrawTransScal(gl, mGR.mTex_Buy, 0.80f, -.20f, 1, .3f);
			}
			else{
				mGR.mFont.Draw_String(gl, "$ "+getCost(mGR.mBike)+"", 0,-.09f, 0, 1);
				DrawTransScal(gl, mGR.mTex_Buy, 0.80f, -.20f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			}
				
				DrawTransScal(gl, mGR.mTex_BackB, .83f,-.89f, mGR.mSel == 11 ? 1.1f : 1, mGR.mSel == 11 ? 0.5f : 1);
				
				
			
		}else{
//			popup = 0;
//			mGR.mBikex = mGR.mBike * .7f;
			 
			{
				Draw_Bike(gl, 0 * .7f, .33f, mGR.mBike, 0, 1, false,false);
			}
		}
		DrawTexture(gl, mGR.mTex_BykFG, 0.03f,.32f);
		
		
		for(int i=0;i<3;i++){
			DrawTexture(gl, mGR.mTex_PowerBar, .06f,-.28f-.14f*i);
		}
		mGR.mTex_PowerFill.drawSS(gl,-.225f, -.282f-.14f*0,1+ mGR.mSpec[mGR.mBike].eng()*2800, 1);
		mGR.mTex_PowerFill.drawSS(gl,-.225f, -.282f-.14f*1,1+ mGR.mSpec[mGR.mBike].getWeight()*2800, 1);
		mGR.mTex_PowerFill.drawSS(gl,-.225f, -.282f-.14f*2, mGR.mSpec[mGR.mBike].getNitro()*.075f, 1);

		mGR.mFont.Draw_String(gl, "Power"	, -.40f, -.28f - .14f * 0, 0, 0);
//		mGR.mFont.Draw_String(gl, (int)(mGR.mSpec[mGR.mBike].Engine*8000)+"bhp"		, 0.35f, -.28f - .14f * 0, 0, 0);

		mGR.mFont.Draw_String(gl, "Weight"	, -.40f, -.28f - .14f * 1, 0, 0);
//		mGR.mFont.Draw_String(gl, (int)(mGR.mSpec[mGR.mBike].Weight*8000)+"lbs"		, 0.35f, -.28f - .14f * 1, 0, 0);

		mGR.mFont.Draw_String(gl, "Nitro"	, -.40f, -.28f - .14f * 2, 0, 0);
//		mGR.mFont.Draw_String(gl,  (int)(mGR.mSpec[mGR.mBike].Nitro)+"sec"		, 0.35f, -.28f - .14f * 2, 0, 0);
		DrawTransScal(gl, mGR.mTex_Sing, -.77f, -.23f, mGR.mSel == 12 ? 1.1f : 1, mGR.mSel == 12 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, mGR.mPName	, -.75f, -.25f, 0, 1);
		mGR.mFont.Draw_String(gl, mGR.mDoller+"", -.80f, -.47f, 0, 0);
		
		if (M.GameScreen == M.GAMEMENU) {
			DrawTransScal(gl, mGR.mTex_RaceB, 0.80f	, -.20f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_ShopB, 0.80f	, -.50f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
			DrawTransScal(gl,mGR.mTex_GarageB, 0.80f, -.80f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		} else if (M.GameScreen == M.GAMEQRACE){
			DrawTransScal(gl, mGR.mTex_QRaceB, 0.80f, -.20f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_CareerB, 0.80f, -.50f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_BackB, .83f,-.89f, mGR.mSel == 11 ? 1.1f : 1, mGR.mSel == 11 ? 0.5f : 1);
		}else if (M.GameScreen == M.GAMEPUASE) {
			DrawTransScal(gl, mGR.mTex_Conti, 0.80f	, -.20f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_ShopB, 0.80f	, -.50f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
			DrawTransScal(gl,mGR.mTex_GarageB, 0.80f, -.80f, mGR.mSel == 5 ? 1.1f : 1, mGR.mSel == 5 ? 0.5f : 1);
		} 
		
		for(int i=0;i<5;i++){
			if(i == 4)
				DrawTransScal(gl, mGR.mTex_ICN[M.setValue?5:4], -.45f+.22f*i,-.85f,mGR.mSel==(i+6)?1.1f:1,mGR.mSel==(i+6)?0.5f:1);
			else
				DrawTransScal(gl, mGR.mTex_ICN[i], -.45f+.22f*i,-.85f,mGR.mSel==(i+6)?1.1f:1,mGR.mSel==(i+6)?0.5f:1);
		}
		DrawTransScal(gl,mGR.mTex_Buy2, -.76f,-.76f, mGR.mSel == 13 ? 1.1f : 1, mGR.mSel == 13 ? 0.5f : 1);
		if (popup == 2 /*&& M.GameScreen == M.GAMESHOP*/) {
			Purchase(gl);
		}
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(M.GameScreen == M.GAMESHOP && 
				CircRectsOverlap(-.83f,.32f, mGR.mTex_Arrow[0].width(), mGR.mTex_Arrow[0].Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 1;
		}
		if(M.GameScreen == M.GAMESHOP && 
				CircRectsOverlap(0.83f,.32f, mGR.mTex_Arrow[0].width(), mGR.mTex_Arrow[0].Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.80f,-.20f, mGR.mTex_RaceB.width()*.4f, mGR.mTex_RaceB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.80f,-.50f, mGR.mTex_RaceB.width()*.4f, mGR.mTex_RaceB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 4;
		}
		if((M.GameScreen == M.GAMEMENU||M.GameScreen == M.GAMEPUASE) && 
				CircRectsOverlap(0.80f,-.80f, mGR.mTex_RaceB.width()*.4f, mGR.mTex_RaceB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 5;
		}
		if((M.GameScreen != M.GAMEMENU&&M.GameScreen != M.GAMEPUASE) && 
				CircRectsOverlap(.83f,-.89f, mGR.mTex_RaceB.width()*.4f, mGR.mTex_RaceB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 11;
		}
		
		for(int i=0;i<5;i++){
			if(CircRectsOverlap(-.45f+.22f*i,-.85f, mGR.mTex_RaceB.width()*.4f, mGR.mTex_RaceB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mGR.mSel = 6+i;
			}
		}
		if(CircRectsOverlap(-.78f, -.24f, mGR.mTex_Arrow[0].width()*.5f, mGR.mTex_Arrow[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			if(!GameRenderer.mStart.isSignedIn())
				mGR.mSel = 12;
		}
		if(CircRectsOverlap(-.76f,-.76f, mGR.mTex_Arrow[0].width()*.5f, mGR.mTex_Arrow[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mGR.mSel = 13;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.fromLevel = false;
			switch (mGR.mSel) {
			case 1:
				if (mGR.mBike > 0)
					mGR.mBikevx =-.085f;
				break;
			case 2:
				if (mGR.mBike < 16)
					mGR.mBikevx = .085f;
				break;
			case 3:
				
				if (M.GameScreen == M.GAMEPUASE) {
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext, R.drawable.theme);
				} else if (M.GameScreen == M.GAMEMENU) {
					M.GameScreen = M.GAMEQRACE;
				} else if (M.GameScreen == M.GAMEQRACE) {
					mGR.gameReset();
					load();
					mGR.mIsQuckRace = true;
				} else if (M.GameScreen == M.GAMESHOP) {
					if (!mGR.mBikeUnlock[mGR.mBike]) {
						if (getCost(mGR.mBike) <= mGR.mDoller) {
							mGR.mBikeUnlock[mGR.mBike] = true;
							mGR.mDoller -= getCost(mGR.mBike);
							unclockAchive();
						} else {
							popup = 2;
						}
					}
				}
				break;
			case 4:
				if (M.GameScreen == M.GAMEMENU||M.GameScreen == M.GAMEPUASE) {
					M.GameScreen = M.GAMESHOP;
				} else if (M.GameScreen == M.GAMEQRACE){
					M.GameScreen = M.GAMECARER;
					mGR.mIsQuckRace = false;
				}
				break;
			case 5:
				M.GameScreen = M.GAMEUPGRD;
				break;
			case 6:
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 8:
				facebook();
				break;
			case 9:
				MoreGame();
				break;
			case 10:
				M.setValue=!M.setValue;
				break;
			case 11:
				if (M.GameScreen == M.GAMESHOP) {
					if (!mGR.mBikeUnlock[mGR.mBike]) {
						for (int i = 16; i >= 0; i--)
							if (mGR.mBikeUnlock[i]) {
								mGR.mBike = i;
								break;
							}
					}
				}
				M.GameScreen = M.GAMEMENU;
				break;
			case 12:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 13:
				popup = 2;
				break;
			}
			if(mGR.mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Menu End******************************/
	/*************************Game Play Start****************************/
	
	void gameWinOver() {
		M.stop(GameRenderer.mContext);
		mGR.fromLevel = !mGR.mIsQuckRace;
		mGR.mPlayer.price = 0;
		mGR.mPlayer.bonus = 0;
		if (mGR.mIsGameWin == 1) {
			M.GameScreen = M.GAMEWIN;
			if (mGR.mLevel < 4) {
				mGR.mPlayer.bonus = (int) (2 * (mGR.city + 1) * 15);
				mGR.mPlayer.price = (int) (2 * (mGR.city + 1) * 100);
				
			}
			if (mGR.mLevel > 3 && mGR.mLevel < 7) {
				mGR.mPlayer.bonus = (int) (5 * (mGR.city + 1) * 15);
				mGR.mPlayer.price = (int) (5 * (mGR.city + 1) * 100);
			}
			if (mGR.mLevel > 6) {
				mGR.mPlayer.bonus = (int) (7 * (mGR.city + 1) * 15);
				mGR.mPlayer.price = (int) (7 * (mGR.city + 1) * 100);
			}
		}
		else{
			M.GameScreen = M.GAMEOVER;
			mGR.mPlayer.bonus = 0;
		}
		if(!mGR.mIsQuckRace && M.GameScreen == M.GAMEWIN)
		{
			mGR.gamePlayed[mGR.city][mGR.mLevel] = true;
			if(mGR.mLevel==0||mGR.mLevel==1 || mGR.mLevel== 4 || mGR.mLevel == 5 ||mGR.mLevel== 7 || mGR.mLevel == 8 ||
					mGR.mLevel == 2){
				mGR.mLevel++;
				mGR.gameUnlock[mGR.city][mGR.mLevel] = true;
			}
			else if (mGR.mLevel == 3){
				mGR.mLevel++;
				mGR.gameUnlock[mGR.city][3] = true;
				mGR.gameUnlock[mGR.city][4] = true;
				mGR.gameUnlock[mGR.city][7] = true;
			}else{
				boolean cityUnlock = false;
				for(int i=0;i<10;i++){
					if(!mGR.gamePlayed[mGR.city][i]){
						mGR.mLevel = i;
						cityUnlock = true;
						break;
					}
				}
				if(!cityUnlock){
					if(mGR.city<6){
						mGR.city++;
						mGR.citiUnlock = true;
						mGR.mLevel =0;
					}
					else
						M.GameScreen = M.GAMECONG;
				}
					
			}
		}
		mGR.mPlayer.parfect=200+M.mRand.nextInt(100);
		mGR.mPlayer.Good=100+M.mRand.nextInt(50);
		mGR.mPlayer.over=50+M.mRand.nextInt(50);
		
		int total = mGR.mPlayer.parfect
				+ mGR.mPlayer.Good
				+ mGR.mPlayer.over
				+ mGR.mPlayer.bonus
				+ mGR.mPlayer.price;
		
		mGR.mDoller += total;
		mGR.mScore += total;
		GameRenderer.mStart.Submitscore(R.string.leaderboard_score, mGR.mScore);
		unclockAchive();
		mGR.showCount++;
		if(mGR.showCount%3==0)
			GameRenderer.mStart.show();
		
	}
	
	void unclockAchive(){
		int unlockBike = 0;
		for(int i=0;i<mGR.mBikeUnlock.length;i++)
		{
			if(mGR.mBikeUnlock[i])
				unlockBike++;
		}
		if(!mGR.mAchiUnlock[0] && unlockBike>1){
			mGR.mAchiUnlock[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(!mGR.mAchiUnlock[1] && mGR.city>0){
			mGR.mAchiUnlock[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(!mGR.mAchiUnlock[2] && unlockBike>5){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if(!mGR.mAchiUnlock[3] && unlockBike>10){
			mGR.mAchiUnlock[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if(!mGR.mAchiUnlock[4] && unlockBike>=mGR.mBikeUnlock.length-1){
			mGR.mAchiUnlock[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
		if(!mGR.mAchiUnlock[5] && mGR.city>4){
			mGR.mAchiUnlock[5] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[5]);
		}
	}
	
	void Draw_Tower(GL10 gl){
		
		DrawTexture(gl, mGR.mTex_Tower, mGR.mTowerX, .29f);
		if (mGR.start > 0) {
			DrawTexture(gl, mGR.mTex_Light[0], mGR.mTowerX + .227f, .76f);
			DrawTexture(gl, mGR.mTex_Light[0], mGR.mTowerX + .227f, .67f);
		}
		switch (mGR.start) {
		case 2:
			DrawTexture(gl, mGR.mTex_Star, mGR.mTowerX + .227f, .6f - .07f * 0);
			DrawTexture(gl, mGR.mTex_Light[1], mGR.mTowerX + .235f, .6f - .07f * 0);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Star, mGR.mTowerX + .227f, .6f - .07f * 1);
			DrawTexture(gl, mGR.mTex_Light[1], mGR.mTowerX + .235f,
					.6f - .07f * 1);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Star, mGR.mTowerX + .227f, .6f - .07f * 2);
			DrawTexture(gl, mGR.mTex_Light[1], mGR.mTowerX + .235f,
					.6f - .07f * 2);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_Star, mGR.mTowerX + .227f, .6f - .07f * 3);
			DrawTexture(gl, mGR.mTex_Light[2], mGR.mTowerX + .235f,
					.6f - .07f * 3);
			break;
		}
		if (mGR.start < 5)
			DrawTexture(gl, mGR.mTex_Handle[mGR.start > 2 ? 0 : (Counter2 % 2)], .7f, -.3f);
		
		
	}
	void gamelogic() {
		mGR.mPlayer.update();
		mGR.mOpponent.update();
		
		if (mGR.mOpponent.rpm > 170) {
			if (mGR.mOpponent.Gear == 1 && mGR.start == 6) {
				mGR.mOpponent.rpm -= 60;
			}
			if (mGR.mOpponent.Gear == 2) {
				mGR.mOpponent.rpm -= 50;
			}
			if (mGR.mOpponent.Gear == 3) {
				mGR.mOpponent.netro = true;
				mGR.mOpponent.rpm -= 40;
			}
			if (mGR.mOpponent.Gear == 4) {
				mGR.mOpponent.rpm -= 30;
			}
			if (mGR.mOpponent.Gear == 5) {
				mGR.mOpponent.rpm -= 20;
			}
			if (mGR.mOpponent.Gear < 6)
				mGR.mOpponent.Gear++;
			if(mGR.mOpponent.rpm<20)
				mGR.mOpponent.rpm = 20;
		}
		
		mGR.mSpeed = -mGR.mPlayer.Engine;
		
		if ((mGR.mIsGameWin == 0)&&(mGR.mOpponent.x > -1 && mGR.mOpponent.x < 1)
				&& ((mGR.mPlayer.Engine > mGR.mOpponent.Engine && mGR.mPlayer.x < .4f) || 
						(mGR.mPlayer.Engine < mGR.mOpponent.Engine && mGR.mPlayer.x > -.4f))) {
			
			float opSpeed = (mGR.mPlayer.Engine - mGR.mOpponent.Engine);
//			float mul = 1;
//			if (mGR.mSpeed < -.1f) {
//				mul = -.1f / mGR.mSpeed;
//				opSpeed *= mul;
//				mGR.mSpeed = -.1f;
//			}
			mGR.mPlayer.pos(opSpeed);
			mGR.mOpponent.pos(-opSpeed/2);
			

		} else {
			float opSpeed = -(mGR.mPlayer.Engine - mGR.mOpponent.Engine);
//			float mul = 1;
//			if (mGR.mSpeed < -.1f) {
//				mul = -.1f / mGR.mSpeed;
//				opSpeed *= mul;
//				mGR.mSpeed = -.1f;
//			}
			mGR.mOpponent.pos(opSpeed);
		}
		mGR.mPlayer.Dis += mGR.mPlayer.Engine * (1.88f/mGR.gamedistace);
		mGR.mOpponent.Dis += mGR.mOpponent.Engine * (1.88f/mGR.gamedistace);
		mGR.mFLine += mGR.mSpeed;
		mGR.mTowerX += mGR.mSpeed;
		
		for (int i = 0; i < 2; i++) {
			if(mGR.mSpeed<-.1){
				mGR.mBGX0[i] += -.02f;
				mGR.mBGX1[i] += -.1f;
			}else{
				mGR.mBGX0[i] += mGR.mSpeed*.2f;
				mGR.mBGX1[i] += mGR.mSpeed;
			}
		}
		
		for (int i = 0; i < 2; i++) {
			if (mGR.mBGX0[i] < -2.4f)
				mGR.mBGX0[i] = mGR.mBGX0[i == 0 ? 1 : 0] + mGR.mTex_BG[0].width();
			if (mGR.mBGX1[i] < -2.4f)
				mGR.mBGX1[i] = mGR.mBGX1[i == 0 ? 1 : 0] + mGR.mTex_BG[1].width();
		}
		
		if (mGR.start == 5){
			mGR.mSel = 0;
			if(mGR.mPlayer.Gear < 1)
				mGR.mPlayer.Gear = 1;
			if (mGR.mPlayer.rpm > 50){
				mGR.mPlayer.rpm -= 5;
			}else
				mGR.start =6;
		}
		
		if(mGR.mPlayer.Dis>.94f){
			if(mGR.mPlayer.time ==0)
			{
				mGR.mPlayer.smock = 60;
				mGR.mPlayer.time = (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
			}
			if(mGR.mOpponent.time ==0)
				mGR.mOpponent.time = (int)(mGR.mPlayer.time+(mGR.mPlayer.Dis-mGR.mOpponent.Dis)*100);
			if(mGR.mIsGameWin == 0)
			{
				mGR.mIsGameWin = 1;
			}
			if (mGR.mPlayer.Engine > 0.0) {
				if (mGR.mOpponent.Dis > mGR.mPlayer.Dis && mGR.mPlayer.Engine < 0.05) {
//					mGR.mPlayer.Engine = .005f;
				} else 
					mGR.mPlayer.Engine -= .005f;
			} else {
				{
					mGR.mPlayer.reset();
					mGR.waitCounter++;
					if (mGR.waitCounter > 50)
						gameWinOver();
				}
			}
			if (mGR.mPlayer.rpm > 0){
				mGR.mPlayer.rpm-=5;
			}
		}
		if (mGR.mPlayer.Engine > 0.0 && mGR.start >5)
		{
			M.BikeSound(mGR.mPlayer.BikeNo);
		}else{
			M.BikeSoundStop();
		}
		if(mGR.mOpponent.Dis>.94f){
			if(mGR.mIsGameWin == 0)
			{
				mGR.mIsGameWin = 2;
				mGR.mOpponent.time = (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
			}
			if (mGR.mOpponent.Engine > 0) {
				mGR.mOpponent.Engine -= .004f;
			} else{
				mGR.mOpponent.reset();
			}
			if (mGR.mOpponent.rpm > 0){
				mGR.mOpponent.rpm-=5;
			}
		}
		if(mGR.start>4 && (mGR.mPlayer.Engine!=0)){
			wheel+=2;
			if(mGR.mPlayer.Engine>0.1)
				wheel+=2;
			if(mGR.mPlayer.Engine>0.2)
				wheel+=2;
			wheel%=360;
		}
		if (mGR.start > 0 && mGR.start < 6) {
			if(mGR.start == 5){
				mGR.mPlayer.smock = 50;
				mGR.mOpponent.smock = 40;
				M.startSoundStop();
			}
			else
				M.startSound(mGR.mPlayer.BikeNo);
		}
		
	}
	int wheel = 0;
	void Draw_Play(GL10 gl) {

		DrawTexture(gl, mGR.mTex_BG[0], mGR.mBGX0[0], .75f);
		DrawTexture(gl, mGR.mTex_BG[0], mGR.mBGX0[1], .75f);
		DrawTexture(gl, mGR.mTex_BG[1], mGR.mBGX1[0], .00f);
		DrawTexture(gl, mGR.mTex_BG[1], mGR.mBGX1[1], .00f);
		for (int i = 0; i < 3; i++){
			DrawTexture(gl, mGR.mTex_Ad[i], -1.0f + i * .8f + mGR.mBGX1[0], .63f);
		}
		for (int i = 4; i < 7; i++){
			DrawTexture(gl, mGR.mTex_Ad[i], -1.0f + (i-4) * .8f + mGR.mBGX1[1], .63f);
		}

		if (mGR.mTowerX > -2)
			DrawTexture(gl, mGR.mTex_SLine, mGR.mTowerX+.2f,-.16f);
		DrawTexture(gl, mGR.mTex_Bar, 0, 0.93f);
		DrawTextureS(gl, mGR.mTex_Pointer[0], mGR.mOpponent.Dis<.94?mGR.mOpponent.Dis:.94f, .96f, 3.0f);
		DrawTextureS(gl, mGR.mTex_Pointer[1], mGR.mPlayer.Dis<.94?mGR.mPlayer.Dis:.94f, .90f, 3.0f);
		
		DrawTexture(gl, mGR.mTex_FLine, mGR.mFLine,-.13f);// 
		
		DrawTexture(gl, mGR.mTex_Shadow, mGR.mOpponent.x, mGR.mOpponent.y-(mGR.mOpponent.BikeNo<15?.24f:.20f));//
		boolean smock =false;
		if((mGR.mOpponent.netro && mGR.mOpponent.Nitro>0)||mGR.mOpponent.smock>0){
			smock = true;
		}
		Draw_Bike(gl, mGR.mOpponent.x, mGR.mOpponent.y, mGR.mOpponent.BikeNo, -wheel * 11,1,(mGR.mOpponent.netro && mGR.mOpponent.Nitro>0)?true:false,smock);
		DrawTexture(gl, mGR.mTex_Driver[1], mGR.mOpponent.x+.03f, mGR.mOpponent.y+.11f);
		
		if (mGR.mTowerX > -2)
			Draw_Tower(gl);
		float px = mGR.mPlayer.x;
		if (mGR.mPlayer.x < -.5f)
			px = -.5f;
		if (mGR.mPlayer.x > .5f)
			px = 0.5f;
		smock =false;
		if((mGR.mPlayer.netro && mGR.mPlayer.Nitro>0)||mGR.mPlayer.smock>0){
			smock = true;
		}
		DrawTexture(gl, mGR.mTex_Shadow, mGR.mPlayer.x, mGR.mPlayer.y-(mGR.mPlayer.BikeNo<15?.24f:.20f));//
		Draw_Bike(gl, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.BikeNo, -wheel * 10,1,(mGR.mPlayer.netro && mGR.mPlayer.Nitro>0)?true:false,smock);
		DrawTexture(gl, mGR.mTex_Driver[2], px+.03f, mGR.mPlayer.y+.11f);
		
		DrawTexture(gl, mGR.mTex_Meter, 0, -.47f);
		mGR.mTex_Kata.drawfullRotet(gl, -.02f, -.98f, -mGR.mPlayer.rpm, -.4f,1);
		DrawTexture(gl, mGR.mTex_Light[1], -.02f, -.98f);

		if (mGR.start < 5) {
			if (mGR.mSel == 1) {
				if (mGR.mPlayer.rpm < 175){
					mGR.mPlayer.rpm += 4;
					if (mGR.mPlayer.rpm > 170)
						mGR.mPlayer.rpm -= 10;
				}
			} else if (mGR.mPlayer.rpm > 6)
				mGR.mPlayer.rpm -= 5;
		}

		
		if (mGR.mPlayer.Nitro > 0 && Counter % 2 == 0) {
			DrawTexture(gl, mGR.mTex_GearShift, .225f, -.525f);// Nitrogen
		}
		switch (mGR.mSel) {
		case 2:
			DrawTexture(gl, mGR.mTex_GearShift, .225f, -.525f);// Nitrogen
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_GearShift, -.720f, -.860f);// Gear Down
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_GearShift, .720f, -.860f);// Gear UP
			break;
		}

		int spd = (int)(mGR.mPlayer.Engine*1000);
		spd = spd < 412 ? spd : 413;
		if(spd>mGR.mPlayer.MaxSpeed)
			mGR.mPlayer.MaxSpeed = spd;
		
		if(mGR.mPlayer.m0to60==0 && spd>=60)
			mGR.mPlayer.m0to60= (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
		if(mGR.mPlayer.m0to100==0 && spd>=100)
			mGR.mPlayer.m0to100= (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
		mGR.mFont.Draw_Number(gl,spd , -.42f, -.85f, 1, 0);
		mGR.mFont.Draw_Number(gl, mGR.mPlayer.Gear, .36f, -.845f,0);

		if(mGR.mPlayer.rpm>135 && mGR.mPlayer.rpm<152){
			DrawTexture(gl, mGR.mTex_Shift[0], 0, 0);//Good
		}else if(mGR.mPlayer.rpm>151 && mGR.mPlayer.rpm<162){
			DrawTexture(gl, mGR.mTex_Shift[1], 0, 0);//Perfect
		}else if(mGR.mPlayer.rpm>161 ){
			DrawTexture(gl, mGR.mTex_Shift[2], 0, 0);//Over
		}
		if(mGR.mShift.y<1.1f){
			DrawTexture(gl, mGR.mTex_ShiftTxt[mGR.mShift.type%mGR.mTex_ShiftTxt.length], 0, mGR.mShift.y);//Over
			mGR.mShift.update();
		}
		if (mGR.start > 1 && mGR.start < 5) {
			if (Counter % 15 == 0){
				mGR.start++;
				M.sound14(GameRenderer.mContext, R.drawable.beep);
			}
		}
		if (mGR.start >= 5) {
			gamelogic();
		}else if (mGR.start > 1 && mGR.start < 6) {
			if(mGR.start == 5){
				M.startSoundStop();
			}
			else
				M.startSound(mGR.mPlayer.BikeNo);
			mGR.mPlayer.smock = 50;
			mGR.mOpponent.smock = 40;
		}
		
		if (mGR.start == 0 && Counter % 30 == 0)
			mGR.start = 1;
		if (Counter % 5 == 0)
			Counter2++;
		
		
	}
	
	public boolean Handle_Play(MotionEvent event) {
		mGR.mSel = 0;
		if(mGR.start <5 &&
				CircRectsOverlap(.7f, -.3f, .3f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mGR.mSel = 1;//Start Game
			if(mGR.start == 1){
				mGR.start = 2;
				Counter =1;
			}
		}
		if(mGR.start >=5 &&
				CircRectsOverlap(.225f,-.525f, mGR.mTex_GearShift.width(), mGR.mTex_GearShift.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mGR.mSel = 2;//nitrogen
		}
		if(mGR.start >=5 &&
				CircRectsOverlap(-.720f,-.860f, mGR.mTex_GearShift.width(), mGR.mTex_GearShift.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mGR.mSel = 3;///Gear Down
		}
		if(mGR.start >=5 &&
				CircRectsOverlap(0.720f,-.860f, mGR.mTex_GearShift.width(), mGR.mTex_GearShift.Height(), screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mGR.mSel = 4;//Gear Up
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				break;
			case 2:
				//Nitrogen
				mGR.mPlayer.netro = true;
				break;
			case 3:
				if (mGR.mPlayer.Gear == 2 && mGR.mPlayer.rpm < 170) {
					mGR.mPlayer.rpm += 10;
				}
				if (mGR.mPlayer.Gear == 3 && mGR.mPlayer.rpm < 170) {
					mGR.mPlayer.rpm += 10;
				}
				if (mGR.mPlayer.Gear == 4 && mGR.mPlayer.rpm < 170) {
					mGR.mPlayer.rpm += 10;
				}
				if (mGR.mPlayer.Gear == 5 && mGR.mPlayer.rpm < 170) {
					mGR.mPlayer.rpm += 10;
				}
				if (mGR.mPlayer.Gear > 2 && mGR.mPlayer.rpm < 170){
					mGR.mPlayer.Gear--;
					M.sound13(GameRenderer.mContext, R.drawable.gearsift);
				}
				break;
			case 4:
				
					
				if (mGR.mPlayer.Gear == 1 && mGR.start == 6) {
					mGR.mPlayer.gearChange = 15;
				}
				if (mGR.mPlayer.Gear == 2) {
					mGR.mPlayer.gearChange = 12;
				}
				if (mGR.mPlayer.Gear == 3) {
					mGR.mPlayer.gearChange = 10;
				}
				if (mGR.mPlayer.Gear == 4) {
					mGR.mPlayer.gearChange = 9;
				}
				if (mGR.mPlayer.Gear == 5) {
					mGR.mPlayer.gearChange = 8;
				}
				if (mGR.mPlayer.Gear < 6){
					if(mGR.mPlayer.rpm>135 && mGR.mPlayer.rpm<152){
						mGR.mPlayer.Good++;//Good
						mGR.mShift.set(-.5f,0);
					}else if(mGR.mPlayer.rpm>151 && mGR.mPlayer.rpm<162){
						mGR.mPlayer.parfect++;//Perfect
						mGR.mShift.set(-.5f,1);
					}else if(mGR.mPlayer.rpm>161 ){
						mGR.mPlayer.over++;//Over
						mGR.mShift.set(-.5f,2);
					}
					mGR.mPlayer.Gear++;
					M.sound13(GameRenderer.mContext, R.drawable.gearsift);
				}
				break;
			}
			
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game Play End******************************/
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}

	void DrawFullRotate(GL10 gl, SimplePlane Tex, float x, float y, float r) {
		Tex.drawfullRotet(gl, x, y,r,0,1);
	}

	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }

	float screen2worldX(float a) {
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
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

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2, double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2)) + ((cy1 - cy2) * (cy1 - cy2)));
		if (bVectMag < (r1 + r2))
			return true;
		return false;

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
	void facebook() {
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
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK+ getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
