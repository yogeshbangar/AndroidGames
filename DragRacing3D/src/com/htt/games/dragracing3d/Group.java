package com.htt.games.dragracing3d;

import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = .20f;
	int Counter = 0;
	int mSel =0;
	public void setting() {float ud = .01f;switch (_keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;case 5:sz -= ud;break;case 6:sz += ud;break;}System.out.println("~~~~~~~  " + sx + "f,  " + sy + "f,  "+ sz + "f");}
	public boolean Handle(MotionEvent event){
		int val = 170;
		if(event.getX() <val && event.getY() <val){_keyCode = 1;}
		if(event.getX() >M.ScreenWidth - val && event.getY() <val){_keyCode = 2;}
		if(event.getX() < val && event.getY() >M.ScreenHieght-val){_keyCode = 3;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >M.ScreenHieght-val){_keyCode = 4;}
		if(event.getX() < val && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 5;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 6;}
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	void Draw(){}
	public boolean onTouch(View v, MotionEvent event) {
		Handle(event);
		return true;
	}
	/*************************Menu Start****************************/
	final int wieght = 30;
	final int Exhaust = 35;
	final int Nirto = 40;
	final int GBox = 50;
	final int Mul = 5;
	int popup = 0;
	
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
//				DrawTransScal(gl, mGR.mTex_Mock, mGR.mSmock[i].x,mGR.mSmock[i].y,mGR.mSmock[i].z,mGR.mSmock[i].t);
				mGR.mSmock[i].update();
			}
		}
	}
	void Draw_Bike(GL10 gl, float x, float y, int no, float move, float scal, boolean falme, boolean Smock) {
		mGR.m3D_Bike[no].setVisible(true);
		mGR.m3D_Bike[no].setPosition(x, y, 2.36);
		mGR.m3D_Bike[no].setRotation(0, 60, 90);
		mGR.m3D_Bike[no].setScale(.05);
	}
	int getCost(int bike)
	{
		return (int)
				 ((6 * (wieght	* bike * Mul))
				+ (6 * (Exhaust	* bike * Mul))
				+ (6 * (Nirto	* bike * Mul))
				+ (6 * (GBox	* bike * Mul)));
	}
	float menux[] = {-.9f,-.4f,.4f,.9f};
	void Draw_Menu(GL10 gl){
		mGR.m2D_ComnBG.setVisible(true);
		mGR.m2D_ComnBG.setPosition(0, 0, 0);
		if(M.GameScreen == M.GAMESHOP){
			mGR.oUpdt.setPosition(0.0,3.4, 0);
		}else{
			mGR.oUpdt.setPosition(8.0,3.4, 0);
		}
		mGR.oUpdt.setScale(4.94);
		mGR.oUpdt.setVisible(true);
		if(Counter%10==8){
			mGR.updateUpdtBitmap();
		}
		float mx = 11;
		if (M.GameScreen == M.GAMESHOP) {
			if (mGR.mBikevx == 0) {
				mGR.mBikex = mGR.mBike * .7f;
				for (int i = 0; i < 17; i++) {
					Draw_Bike(gl, mx*(mGR.mBikex - i * .7f), .33f, i, 0, 1, false,false);
				}
			} else {
				for (int i = 0; i < 17; i++)
					Draw_Bike(gl, (mGR.mBikex - i * .7f)*mx, .33f, i, 0, 1, false,false);
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
			if (mGR.mBike > 0){
				mGR.m2D_Arrow[0].setVisible(true);
				mGR.m2D_Arrow[0].setScale(mSel == 1?4.5:3.5);
			}
			if (mGR.mBike < 16){
				mGR.m2D_Arrow[1].setVisible(true);
				mGR.m2D_Arrow[1].setScale(mSel == 2?4.5:3.5);
			}
			
			if(mGR.mBikeUnlock[mGR.mBike])
				mGR.m2D_Buy.setVisible(false);
			else
				mGR.m2D_Buy.setVisible(true);
			mGR.m2D_Back.setVisible(true);
			mGR.m2D_Back.setPosition(10.75, -1.77+-2.48*2, 0);
		}else{
			
			mGR.m3D_Bike[mGR.mBike].setVisible(true);
			mGR.m3D_Bike[mGR.mBike].setPosition(0, 2.13, 2.36);
			mGR.m3D_Bike[mGR.mBike].setRotation(35, 0, Counter%360);
			mGR.m3D_Bike[mGR.mBike].setScale(.05);
//			Draw_Bike(gl, 0 * .7f, .33f, mGR.mBike, 0, 1, false,false);
		}
//		DrawTexture(gl, mGR.mTex_BykFG, 0.03f,.32f);
		for(int i=0;i<mGR.m2D_PowerFill.length;i++){
			mGR.m2D_PowerBar[i].setVisible(true);
			mGR.m2D_PowerBar[i].setPosition(2.42 ,-1.54-1.28*i,0);
			mGR.m2D_PowerFill[i].setVisible(true);
			mGR.m2D_PowerFill[i].setPosition(2.42,-1.52-1.28*i,0);
		}
		mGR.oSPD.setPosition(-4.16f,-5.46, 0);
		mGR.oSPD.setScale(4.5);
		mGR.oSPD.setVisible(true);
		if(Counter%10==8){
			mGR.updateSPDBitmap();
		}
		float p0 = (1+ mGR.mSpec[mGR.mBike].eng()*5000)*6;
		mGR.m2D_PowerFill[0].setX(-1.85+.125*p0);//34
		mGR.m2D_PowerFill[0].setScaleX(p0);
		
		float p1 = (1+ mGR.mSpec[mGR.mBike].getWeight()*10000)*6;
		mGR.m2D_PowerFill[1].setX(-1.85+.125*p1);//34
		mGR.m2D_PowerFill[1].setScaleX(p1);
		
		float p2 = (mGR.mSpec[mGR.mBike].getNitro()*.075f)*4;
		mGR.m2D_PowerFill[2].setX(-1.85+.125*p2);//34
		mGR.m2D_PowerFill[2].setScaleX(p2);
		
		mGR.oCoin.setPosition(-10.4,-5.4, 0);
		mGR.oCoin.setScale(5.0);//Doller
		mGR.oCoin.setVisible(true);
		if(Counter%10==8){
			mGR.updateCoinBitmap();
		}
		if (M.GameScreen == M.GAMEMENU) {
			mGR.m2D_RaceB.setPosition (10.75, -1.77-2.48*0, 0);
			mGR.m2D_ShopB.setPosition (10.75, -1.77-2.48*1, 0);
			mGR.m2D_Garage.setPosition(10.75, -1.77-2.48*2, 0);
			
			mGR.m2D_RaceB.setVisible(true);
			mGR.m2D_ShopB.setVisible(true);
			mGR.m2D_Garage.setVisible(true);
			
			mGR.m2D_RaceB.setScale(mSel == 3 ? M.SL : M.SCL);
			mGR.m2D_ShopB.setScale(mSel == 4 ? M.SL : M.SCL);
			mGR.m2D_Garage.setScale(mSel ==5 ? M.SL : M.SCL);
			
		} else if (M.GameScreen == M.GAMEQRACE) {
			mGR.m2D_QRaceB.setVisible(true);
			mGR.m2D_CareerB.setVisible(true);
			mGR.m2D_Back.setVisible(true);
			mGR.m2D_Back.setPosition(10.75, -1.77 - 2.48 * 2, 0);

			mGR.m2D_QRaceB.setScale(mSel == 3 ? M.SL : M.SCL);
			mGR.m2D_CareerB.setScale(mSel == 4 ? M.SL : M.SCL);
			mGR.m2D_Back.setScale(mSel == 5 ? M.SL : M.SCL);

		}else if (M.GameScreen == M.GAMEPUASE) {
			mGR.m2D_Conti.setPosition(10.75, -1.77-2.48*0, 0);
			mGR.m2D_Conti.setVisible(true);
			mGR.m2D_ShopB.setVisible(true);
			mGR.m2D_Garage.setVisible(true);
			
			mGR.m2D_Conti.setScale(mSel == 3?M.SL:M.SCL);
			mGR.m2D_ShopB.setScale(mSel == 4?M.SL:M.SCL);
			mGR.m2D_ShopB.setPosition(10.75, -1.77+-2.48*1, 0);
			mGR.m2D_Garage.setScale(mSel == 5?M.SL:M.SCL);
			mGR.m2D_Garage.setPosition(10.75, -1.77-2.48*2, 0);
		} 
		
		for(int i=0;i<5;i++){
			if(i == 4){
				mGR.m2D_ICN[4].setVisible(!M.setValue);
				mGR.m2D_ICN[5].setVisible(M.setValue);
				mGR.m2D_ICN[4].setScale(mSel == 10?M.SL:M.SCL);
				mGR.m2D_ICN[5].setScale(mSel == 10?M.SL:M.SCL);
			}
			else{
				mGR.m2D_ICN[i].setVisible(true);
				mGR.m2D_ICN[i].setScale(mSel == i+6?M.SL:M.SCL);
			}
		}
//		mGR.m2D_Buy2.setVisible(true);
		if (popup == 2 /*&& M.GameScreen == M.GAMESHOP*/) {
			Purchase(gl);
		}
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	float radius = .1f;
	boolean Handle_Menu(MotionEvent event){
		System.out.println(screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		mSel = 0;
		if(M.GameScreen == M.GAMESHOP && 
				CircRectsOverlap(-1.3f,.45f, radius*3,radius*3, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 1;
		}
		if(M.GameScreen == M.GAMESHOP && 
				CircRectsOverlap(1.25f,.45f, radius*3,radius*3, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 2;
		}
		if(CircRectsOverlap(1.25f,-.22f, radius*2,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 3;
		}
		if(CircRectsOverlap(1.25f,-.54f, radius*2,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 4;
		}
		if((M.GameScreen == M.GAMEMENU||M.GameScreen == M.GAMEPUASE) && 
				CircRectsOverlap(1.25f,-.83f, radius*2,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 5;
		}
		if((M.GameScreen != M.GAMEMENU&&M.GameScreen != M.GAMEPUASE) && 
				CircRectsOverlap(1.25f,-.83f, radius*2,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 11;
		}
		
		for(int i=0;i<5;i++){
			if(CircRectsOverlap(-.70f+.36f*i,-.88f, radius,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 6+i;
			}
		}
		if(CircRectsOverlap(-.78f, -.24f, radius,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
//			if(!GameRenderer.mStart.isSignedIn())
//				mSel = 12;
		}
		if(CircRectsOverlap(-.76f,-.76f, radius,radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 13;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.fromLevel = false;
			switch (mSel) {
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
//					M.play(GameRenderer.mContext, R.drawable.theme);
				} else if (M.GameScreen == M.GAMEMENU) {
					M.GameScreen = M.GAMEQRACE;
				} else if (M.GameScreen == M.GAMEQRACE) {
					mGR.mGamePlay.gameReset();
					M.GameScreen = M.GAMEPLAY;
//					load();
					mGR.mIsQuckRace = true;
				} else if (M.GameScreen == M.GAMESHOP) {
					if (!mGR.mBikeUnlock[mGR.mBike]) {
						if (getCost(mGR.mBike) <= mGR.mDoller) {
							mGR.mBikeUnlock[mGR.mBike] = true;
							mGR.mDoller -= getCost(mGR.mBike);
							mGR.mGamePlay.unclockAchive();
						} else {
							popup = 2;
						}
					}
				}
				mGR.setVisible(false);
				break;
			case 4:
				if (M.GameScreen == M.GAMEMENU||M.GameScreen == M.GAMEPUASE) {
					M.GameScreen = M.GAMESHOP;
				} else if (M.GameScreen == M.GAMEQRACE){
					M.GameScreen = M.GAMECARER;
					mGR.mIsQuckRace = false;
				}
				mGR.setVisible(false);
				break;
			case 5:
				M.GameScreen = M.GAMEUPGRD;
				mGR.setVisible(false);
				break;
			case 6:
//				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			case 7:
//				GameRenderer.mStart.onShowLeaderboardsRequested();
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
				mGR.setVisible(false);
				break;
			case 12:
//				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 13:
				popup = 2;
				break;
			}
//			if(mSel!=0)
//				M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*************************Menu End******************************/
	
	void Purchase(GL10 gl) {
		/*mGR.m2D_wlBG.setVisible(true);
		DrawTexture(gl, mGR.mTex_PopBuy, 0, 0);
		
		DrawTransScal(gl, mGR.mTex_Adsfree , -.32f,0.10f, mSel == 22 ? 1.1f : 1, mSel == 22 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$0.99", -.30f,0.04f, 1, 0);
		mGR.mFont.Draw_String(gl, "20000", -.66f, 0.03f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,-.32f, -.41f, mSel == 23 ? 1.1f : 1, mSel == 23 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$1.99", -.30f, -.47f, 1, 0);
		mGR.mFont.Draw_String(gl, "50000", -.66f, -.49f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,0.58f, 0.10f, mSel == 24 ? 1.1f : 1, mSel == 24 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$2.99", 0.60f, 0.04f, 1, 0);
		mGR.mFont.Draw_String(gl, "80000", 0.26f, 0.03f, 0, 0);

		DrawTransScal(gl, mGR.mTex_Adsfree,0.58f, -.41f, mSel == 25 ? 1.1f : 1, mSel == 25 ? 0.5f : 1);
		mGR.mFont.Draw_String(gl, "$3.99", 0.60f, -.47f, 1, 0);
		mGR.mFont.Draw_String(gl,"150000", 0.26f, -.49f, 0, 0);*/

	}
	boolean Handle_Purchase(MotionEvent event)
	{
		/*mSel = 0;
		if(CircRectsOverlap( 0.76, 0.56f, mGR.mTex_UpBox.width()*.4, mGR.mTex_UpBox.Height()*.3, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 21;
		}
		if(CircRectsOverlap( -.32f,0.10f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 22;
		}
		if(CircRectsOverlap( -.32f, -.41f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 23;
		}
		if(CircRectsOverlap( 0.58f, 0.10f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 24;
		}
		if(CircRectsOverlap(  0.58f, -.41f, mGR.mTex_Adsfree.width()*.5, mGR.mTex_Adsfree.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 25;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mSel) {
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
			if(mSel!=0)
				M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel = 0;
		}*/
		return true;
	}
	/*************************Level Start******************************/
	void Draw_Level(GL10 gl) {
		for(int i = 0;i<mGR.m2D_Lock[0].length;i++){
			mGR.m2D_Lock[0][i].setVisible(false);
			mGR.m2D_Lock[1][i].setVisible(false);
			
			mGR.m2D_Lock[0][i].setScale(mSel == i+1?7:M.SCL);
			mGR.m2D_Lock[1][i].setScale(mSel == i+1?7:M.SCL);
		}
		
		mGR.m2D_LevelBack.setVisible(true);
		mGR.m2D_LevelBack.setScale(4.25);
		mGR.m2D_LevelBack.setPosition(0, -.02, 0);
		mGR.m2D_LevelBoxS.setVisible(true);
		
		
		for (int i = 0; i < 4; i++) {
			if(mGR.mLevel == i)
				mGR.m2D_LevelBoxS.setPosition(-11.1+3.58*i, .55f, 0);
			if(mGR.gameUnlock[mGR.city][i]){
//				if(mGR.gamePlayed[mGR.city][i])
				{
					mGR.m2D_Lock[1][i].setVisible(true);
					mGR.m2D_Lock[1][i].setPosition(-11.1+3.58*i, .55f, 0);
				}
			}else{
				mGR.m2D_Lock[0][i].setVisible(true);
				mGR.m2D_Lock[0][i].setPosition(-11.1+3.58*i, .55f, 0);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (mGR.mLevel == i + 4)
				mGR.m2D_LevelBoxS.setPosition(-.37+3.43*i, 4.34, 0);
			if (mGR.gameUnlock[mGR.city][i + 4]) {
//				if (mGR.gamePlayed[mGR.city][i + 4]) 
				{
					mGR.m2D_Lock[1][i + 4].setVisible(true);
					mGR.m2D_Lock[1][i + 4].setPosition(-.37+3.43*i, 4.34,0);
				}
			} else {
				mGR.m2D_Lock[0][i + 4].setVisible(true);
				mGR.m2D_Lock[0][i + 4].setPosition(-.37+3.43*i, 4.34, 0);
			}

		}
		for (int i = 0; i < 3; i++) {
			if(mGR.mLevel == i+7)
				mGR.m2D_LevelBoxS.setPosition(-.37+3.43*i,-3.45, 0);
			if(mGR.gameUnlock[mGR.city][i+7]){
//				if(mGR.gamePlayed[mGR.city][i+7])
				{
					mGR.m2D_Lock[1][i + 7].setVisible(true);
					mGR.m2D_Lock[1][i + 7].setPosition(-.37+3.43*i,-3.45,0);
				}
			}else{
				mGR.m2D_Lock[0][i + 7].setVisible(true);
				mGR.m2D_Lock[0][i + 7].setPosition(-.37+3.43*i,-3.45, 0);
			}
		}
		
		
		mGR.oDoler.setPosition(10.05,-.45, 0);
		mGR.oDoler.setScale(3.93);
		mGR.oDoler.setRotation(180, 0, 180);
		mGR.oDoler.setVisible(true);
		
		mGR.oSPD.setPosition(-8.7,-5.9, 0);
		mGR.oSPD.setScale(3.93);
		mGR.oSPD.setRotation(180, 0, 180);
		mGR.oSPD.setVisible(true);
		
		mGR.oCoin.setPosition(1,-9.00, 0);
		mGR.oCoin.setScale(3.93);
		mGR.oCoin.setRotation(180, 0, 180);
		mGR.oCoin.setVisible(true);
		
		if(Counter%10==8){
			mGR.updateDolreBitmap();
			mGR.updateSPDBitmap();
			mGR.updateCoinBitmap();
		}
		mGR.m2D_RaceB.setVisible(true);
		mGR.m2D_RaceB.setPosition(10.32, -7.2, 0);
		mGR.m2D_RaceB.setScale(mSel == 20?M.SL:M.SCL);
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}

	boolean Handle_Level(MotionEvent event) {
		mSel = 0;
		for (int i = 0; i < 4; i++) {
			if(CircRectsOverlap(-1.3f+.42f*i, 0.04, radius, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 1+i;
			}
			
		}
		for (int i = 0; i < 3; i++) {
			if(CircRectsOverlap(-.05f+.4f*i, .47f, radius, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 5+i;
			}
		}
		for (int i = 0; i < 3; i++) {
			if(CircRectsOverlap(-.05f+.4f*i, -.47f, radius, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 8+i;
			}
		}
		
		if(CircRectsOverlap(1.25f, -.89f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
		{
			mSel = 20;
		}
		System.out.println(mSel+"  "+screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(mSel>0 && mSel<11)
				if(mGR.gameUnlock[mGR.city][mSel-1]){
					mGR.mLevel = mSel-1;
			}
			if(mSel==20){
				mGR.mGamePlay.gameReset();
				M.GameScreen = M.GAMEPLAY;
			}
//			if(mSel!=0)
//				M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*************************Level End******************************/
	/*************************Career Start****************************/
	final float city[][]={
			{10.21f,-1.70f},
			{07.89f, 0.16f},
			{09.31f, 2.28f},
			{06.44f, 3.89f},
			{-0.42f, 2.72f},
			{-7.37f, 1.52f},
			{-4.16f,-2.85f},
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
	float mapx = 15.7f;
	float dpx = .1f;
	float dsx = 4f;
	float dsvx = 0.05f;
	void Draw_Career(GL10 gl) {
		mGR.m2D_Map.setVisible(true);
		mGR.m2D_MapDot.setPosition(city[mGR.city][0], city[mGR.city][1], 0);
		mGR.m2D_MapDot.setVisible(true);
		mGR.m2D_MapDot.setScale(dsx);
		for (int i = 0; i < 7; i++) {
			mGR.m2D_MapDot2[i].setVisible(true);
			if (i == mGR.city){
				mGR.m2D_MapDot2[i].setVisible(false);
				mGR.m2D_Lock[0][i].setVisible(false);
				mGR.m2D_Lock[1][i].setVisible(false);
			}else if (i < mGR.city){
				mGR.m2D_Lock[1][i].setVisible(true);
			}else{
				mGR.m2D_Lock[0][i].setVisible(true);
			}
			mGR.m2D_MapDot2[i].setPosition(city[i][0], city[i][1], 0);
			mGR.m2D_Lock[0][i].setPosition(city[i][0]-.19, city[i][1]+.14, 0);
			mGR.m2D_Lock[1][i].setPosition(city[i][0]-.19, city[i][1]+.14, 0);
			mGR.m2D_Lock[1][i].setScale(4.17);
			mGR.m2D_Lock[0][i].setScale(2.44f);
		}
		mGR.m2D_Mapbox.setVisible(true);
		mGR.m2D_Mapbox.setPosition(mapx, -4.55, 0);
		mGR.m2D_City[mGR.city].setVisible(true);
		mGR.m2D_City[mGR.city].setPosition(mapx, -4.55, 0);
		mGR.m2D_GoB.setVisible(true);
		mGR.m2D_GoB.setPosition(mapx, -7.45, 0);
		mGR.m2D_GoB.setScale(mSel == 1?M.SL:M.SCL);
		
		mGR.oCoin.setPosition(mapx,-2, 0);
		mGR.oCoin.setScale(5);
		mGR.oCoin.setRotation(180, 0, 180);
		mGR.oCoin.setVisible(true);
		
		if(Counter%10==8){
			mGR.updateCoinBitmap();
		}
		if(mapx>-9.35f){
			mapx -=dpx;
			dpx+=.02f;
		}
		else
			mapx =-9.35f;
		dsx+=dsvx;
		if (dsx > 4.5f)
			dsvx = -.03f;
		if (dsx < 3.5f)
			dsvx = 0.03f;
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}

	boolean Handle_Career(MotionEvent event) {
		mSel = 0;
		if (CircRectsOverlap(-1.1f, -.89f, radius * 3, radius,screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)) {
			mSel = 1;
		}
		System.out.println(mSel+"  "+screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		if (MotionEvent.ACTION_UP == event.getAction() && mSel > 0) {
			switch (mSel) {
			case 1:
				mGR.setVisible(false);
				M.GameScreen = M.GAMELEVEL;
				break;
			}
			// M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*************************Career End******************************/
	/*************************UPgrade Start******************************/
	void Draw_UPgrade(GL10 gl) {
		mGR.m2D_ComnBG.setVisible(true);
		mGR.m2D_Sing.setVisible(true);
		switch (mGR.upgrade) {
		case 0:
			mGR.m2D_Sing.setPosition(-8.65, 5.85, 0);
			mGR.m2D_Sing.setScale(3.36, 5.11, 3.40);
			break;
		case 1:
			mGR.m2D_Sing.setPosition(-2.94, 5.85, 0);
			mGR.m2D_Sing.setScale(3.82, 5.11, 3.40);
			break;
		case 2:
			mGR.m2D_Sing.setPosition(2.78, 5.85, 0);
			mGR.m2D_Sing.setScale(3.82, 5.11, 3.40);
			break;
		case 3:
			mGR.m2D_Sing.setPosition(8.51, 5.85, 0);
			mGR.m2D_Sing.setScale(3.62, 5.11, 3.40);
			break;
		}
		
		
		mGR.m2D_Gbar.setVisible(true);
		
		
		
		mGR.oUpdt.setPosition(0,5.51, 0);
		mGR.oUpdt.setScale(5);
		mGR.oUpdt.setRotation(180, 0, 180);
		mGR.oUpdt.setVisible(true);
		
		mGR.oDoler.setPosition(0,1.24, 0);
		mGR.oDoler.setScale(8.38);
		mGR.oDoler.setRotation(180, 0, 180);
		mGR.oDoler.setVisible(true);
		
		mGR.oCoin.setPosition(-10.4,-5.4, 0);
		mGR.oCoin.setScale(4.5);
		mGR.oCoin.setRotation(180, 0, 180);
		mGR.oCoin.setVisible(true);
		
		if(Counter%10==8){
			mGR.updateUpdtBitmap();
			mGR.updateDolreBitmap();
			mGR.updateCoinBitmap();
		}
		
		for (int i = 0; i < 4; i++) {
			mGR.m2D_UpBox[i].setVisible(true);
			mGR.m2D_UpBox[i].setPosition(-7.0+4.5*i, 2.84, 0);
			mGR.m2D_UpBox[i].setScale(mSel == 1+i?5:M.SCL);
			mGR.m2D_Lock[0][i].setPosition(-7.0+4.5*i, 2.84, 0);
		}
		
		switch (mGR.upgrade) {
		case 0:
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlWeight>=i){
					mGR.m2D_Engin[1][i].setVisible(true);
					mGR.m2D_Engin[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else if(mGR.mSpec[mGR.mBike].lvlWeight +1 >=i){
					mGR.m2D_Engin[1][i].setVisible(false);
					mGR.m2D_Engin[0][i].setVisible(true);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else{
					mGR.m2D_Lock[0][i].setVisible(true);
					mGR.m2D_Engin[1][i].setVisible(false);
					mGR.m2D_Engin[0][i].setVisible(false);
				}
			}
			break;
		case 1:
			for (int i = 0; i < 4; i++) {
				if (mGR.mSpec[mGR.mBike].lvlExhaust >= i) {
					mGR.m2D_Exhaus[1][i].setVisible(true);
					mGR.m2D_Exhaus[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(false);
				} else if (mGR.mSpec[mGR.mBike].lvlExhaust + 1 >= i) {
					mGR.m2D_Exhaus[1][i].setVisible(false);
					mGR.m2D_Exhaus[0][i].setVisible(true);
					mGR.m2D_Lock[0][i].setVisible(false);
				} else {
					mGR.m2D_Exhaus[1][i].setVisible(false);
					mGR.m2D_Exhaus[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(true);
				}
			}
			break;
			case 2:
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlNitro>=i){
					mGR.m2D_Nitro[1][i].setVisible(true);
					mGR.m2D_Nitro[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else if(mGR.mSpec[mGR.mBike].lvlNitro+1 >=i){
					mGR.m2D_Nitro[1][i].setVisible(false);
					mGR.m2D_Nitro[0][i].setVisible(true);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else{
					mGR.m2D_Nitro[1][i].setVisible(false);
					mGR.m2D_Nitro[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(true);
				}
			}
			break;
			case 3:
			
			for (int i = 0; i < 4; i++) {
				if(mGR.mSpec[mGR.mBike].lvlGearBox>=i){
					mGR.m2D_Gear[1][i].setVisible(true);
					mGR.m2D_Gear[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else if(mGR.mSpec[mGR.mBike].lvlGearBox+1 >=i){
					mGR.m2D_Gear[1][i].setVisible(false);
					mGR.m2D_Gear[0][i].setVisible(true);
					mGR.m2D_Lock[0][i].setVisible(false);
				}
				else{
					mGR.m2D_Gear[1][i].setVisible(false);
					mGR.m2D_Gear[0][i].setVisible(false);
					mGR.m2D_Lock[0][i].setVisible(true);
				}
			}
			break;
		}
		mGR.m3D_Bike[mGR.mBike].setVisible(true);
		mGR.m3D_Bike[mGR.mBike].setPosition(1.8,-3.1, 2.36);
		mGR.m3D_Bike[mGR.mBike].setRotation(35, 0, Counter%360);
		mGR.m3D_Bike[mGR.mBike].setScale(.04);
		if(mGR.fromLevel){
			mGR.m2D_Back.setVisible(false);
			mGR.m2D_Conti.setPosition(10.75, -6.95, 0);
			mGR.m2D_Conti.setVisible(true);
			mGR.m2D_Conti.setScale(mSel == 20?M.SL:M.SCL);
//		DrawTransScal(gl, mGR.fromLevel?mGR.mTex_Conti:mGR.mTex_BackB, .83f,-.89f, mSel == 20 ? 1.1f : 1, mSel == 20 ? 0.5f : 1);
		}else{
			mGR.m2D_Back.setVisible(true);
			mGR.m2D_Back.setPosition(10.75, -6.95, 0);
			mGR.m2D_Conti.setVisible(false);
			mGR.m2D_Back.setScale(mSel == 20?M.SL:M.SCL);
		}
		mGR.m2D_ShopB.setVisible(true);
		mGR.m2D_ShopB.setPosition(-5, -6.95, 0);
		mGR.m2D_ShopB.setScale(mSel == 23?M.SL:M.SCL);
		/*if (popup == 1) {
			mGR.mTex_WinLstBG.drawColor(gl, 0,0,0,0,0,.9f);
			DrawTexture(gl, mGR.mTex_Popup, 0, 0);
			mGR.mFont.Draw_String(gl, "installed", 0, 0, 0, 1);
			mGR.mFont.Draw_String(gl, "ok", 0, -.25f, 2, 1);
		}
		if (popup == 2)
			Purchase(gl);*/
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	boolean Handle_Upgarde(MotionEvent event) {
		mSel = 0;
		if (popup == 2){
			
		}else if (popup == 1){
			if(CircRectsOverlap( 0, -.25f, radius*3, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 10;
			}
		}else{
			for (int i = 0; i < 4; i++) {
				if(CircRectsOverlap( -.70f + .466f * i, .35f, radius*2, radius*2, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
				{
					mSel = 1+i;
				}
			}
			for (int i = 0; i < 4; i++) {
				if(CircRectsOverlap( -.9f + .69 * i, .65f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
				{
					mSel = 5+i;
				}
			}
			if(CircRectsOverlap(1.25f,-.80f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 20;
			}
			if(CircRectsOverlap( -.76f,-.76f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 21;
			}
			if(CircRectsOverlap(-.78f, -.24f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
//				if(!GameRenderer.mStart.isSignedIn())
					mSel = 22;
			}
			if(CircRectsOverlap(-.62f,-.80f, radius*2, radius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 23;
			}
		}
		System.out.println(mSel+"  "+screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		if (MotionEvent.ACTION_UP == event.getAction() && mSel>0) {
			switch (mSel) {
			case 1:
				HTTRenderer.mStart.Install();//popup = 1;
				break;
			case 2:case 3:case 4:
				switch (mGR.upgrade) {
				case 0:
					if(mGR.mSpec[mGR.mBike].lvlWeight>(mSel-2)){
						HTTRenderer.mStart.Install();//popup = 1;
					}else{
						if(mGR.mDoller>=(mSel-1)*(wieght*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlWeight+1 == mSel - 1){
								mGR.mSpec[mGR.mBike].lvlWeight = mSel - 1;
								mGR.mDoller-=(mSel-1)*(wieght*(mGR.mBike+1)*Mul);
							}
						}else{
							HTTRenderer.mStart.DoNot();//popup = 2;
						}
					}
					break;
				case 1:
					if(mGR.mSpec[mGR.mBike].lvlExhaust>(mSel-2)){
						HTTRenderer.mStart.Install();//popup = 1;
					}else{
						if(mGR.mDoller>=(mSel-1)*(Exhaust*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlExhaust+1 == mSel - 1){
								mGR.mSpec[mGR.mBike].lvlExhaust = mSel - 1;
								mGR.mDoller-=(mSel-1)*(Exhaust*(mGR.mBike+1)*Mul);
							}
							//mGR.mSpec[mGR.mBike].lvlExhaust = mSel - 1;
						}else{
							HTTRenderer.mStart.DoNot();//popup = 2;
						}
					}
					break;
				case 2:
					if(mGR.mSpec[mGR.mBike].lvlNitro>(mSel-2)){
						HTTRenderer.mStart.Install();//	popup = 1;
					}else{
						if(mGR.mDoller>=(mSel-1)*(Nirto*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlNitro+1 == mSel - 1){
								mGR.mSpec[mGR.mBike].lvlNitro = mSel - 1;
								mGR.mDoller-=(mSel-1)*(Nirto*(mGR.mBike+1)*Mul);
							}
//							mGR.mSpec[mGR.mBike].lvlNitro = mSel - 1;
						}else{
							HTTRenderer.mStart.DoNot();//popup = 2;
						}
					}
					break;
				case 3:
					if(mGR.mSpec[mGR.mBike].lvlGearBox>(mSel-2)){
						HTTRenderer.mStart.Install();//popup = 1;
					}else{
						if(mGR.mDoller>=(mSel-1)*(GBox*(mGR.mBike+1)*Mul)){
							if(mGR.mSpec[mGR.mBike].lvlGearBox+1 == mSel - 1){
								mGR.mSpec[mGR.mBike].lvlGearBox = mSel - 1;
								mGR.mDoller-=(mSel-1)*(GBox*(mGR.mBike+1)*Mul);
							}
//							mGR.mSpec[mGR.mBike].lvlGearBox = mSel - 1;
						}else{
							HTTRenderer.mStart.DoNot();//popup = 2;
						}
					}
					break;
				}
				break;
			
			case 5:case 6:case 7:case 8:
				mGR.upgrade = mSel -5;
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
//				popup =2;
				break;
			case 22:
//				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 23:
				M.GameScreen = M.GAMESHOP;
				break;
			}
			mGR.setVisible(false);
//			M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*************************UPgrade End******************************/
	
	
	
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }

	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}
	boolean Rect2RectIntersection(double ax,double ay,double adx,double ady,double bx,double by,double bdx,double bdy){
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
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		Start.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		Start.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		Start.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		Start.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		Start.mContext.startActivity(mIntent);
	}
}
