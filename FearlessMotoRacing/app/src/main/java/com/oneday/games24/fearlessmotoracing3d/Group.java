package com.oneday.games24.fearlessmotoracing3d;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.9f;
	int counter = 0;
	int sel =0;
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
	void Draw(){
//		M.GameScreen = M.GAMEOVER;
		if(mGR.oCoin==null){
			if(counter%10 == 2){
				mGR.init();
			}
			counter++;
			return;
		}
		if(counter%10==9)
			mGR.setVisible(false);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			 logo();
			break;
		case M.GAMESPLASH:
			mGR.oSplash.setVisible(true);
			mGR.oSplash.setScale(.745);
			mGR.oSplash.setPosition(0, 0, 0);
			mGR.oSplash.setRotation(180, 0, 180);
			break;
		case M.GAMEMENU:
			Menu();
			break;
		case M.GAMEPLAY:
			Draw_GamePlay();
			break;
		case M.GAMEOVER:
			case M.GAMEPAUSE:
			GameOver();
			break;
		case M.GAMEABOUT:
			About();
			break;
		case M.GAMESHOP:
			Shop();
			break;
		case M.GAMEHELP:
			Help();
			break;
		}
		counter++;
//		setting();
	}
	public boolean onTouch(View v, MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Manu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEOVER:case M.GAMEPAUSE:
			Handle_GameOver(event);
			break;
		case M.GAMEABOUT:
			break; 
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMESPLASH:
		case M.GAMEHELP:
			Handle_Help(event);
			break;
		}
//		Handle(event);
		return true;
	}
	void logo(){
		mGR.oLogo.setVisible(true);
		if(counter> 100){
			M.GameScreen = M.GAMESPLASH;
			mGR.setVisible(false);
			mGR.mStart.load();
			
		}
		if(mGR.getCurrentCamera().getY()!=0)
			mGR.getCurrentCamera().setY(0);
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	void Help(){
		mGR.oMenu_bg.setVisible(true);
//		mGR.oHelp_Txt.setVisible(true);
		
		mGR.oBack.setRotation(180, 0, 180);
		mGR.oBack.setPosition(sx, sy, -1.939);
		mGR.oBack.setScale(sel == 1?1.1:1);
		
		if(mGR.getCurrentCamera().getY()!=0)
			mGR.getCurrentCamera().setY(0);
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	boolean Handle_Help(MotionEvent event){
		sel =0;
//		System.out.println(screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		if(CircRectsOverlap( 0.4 , -0.05f,.12,.12f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =1;//Back
		if(MotionEvent.ACTION_UP==event.getAction() && sel>0){
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				mGR.setVisible(false);
				break;
			}
			M.sound5(R.raw.click);
			sel =0;
		}
		return true;
	}
	void Shop(){
		
		for(int i =0;i<mGR.mPlayer.obj.length;i++){
			mGR.mPlayer.obj[i].setVisible(mGR.mPlayer.bike == i);
		}
		mGR.o3d_Top.setVisible(true);
		mGR.mPlayer.oShadow.setVisible(true);
		mGR.oPower.setVisible(true);
		mGR.oBoard.setVisible(true);
		mGR.oShop_BG.setVisible(true);
		mGR.oBack.setVisible(true);
		mGR.oPurchase.setVisible(true);
		mGR.oCanT.setVisible(true);
		mGR.oTime.setVisible(true);
		mGR.oCoin.setVisible(true);
		mGR.oAdsFree.setVisible(true);
		
		for (int i = 0; i < mGR.oPowerBar.length; i++)
			mGR.oPowerBar[i].setVisible(true);
		
		mGR.oShop_BG.setRotX(114);
		mGR.oShop_BG.setScale(1.38);
		mGR.oShop_BG.setY(4.52);
		float redin =(float)Math.toRadians(counter%360);
		double sin = -Math.sin(redin);
		double cos = -Math.cos(redin);
		float k0y = -3.74f;
		for (int i = 0; i < mGR.mPlayer.obj.length; i++) {
			mGR.mPlayer.obj[i].setPosition(sin * .24, cos * .24 +k0y, 1.88);
			mGR.mPlayer.obj[i].setRotation(0, 0, counter % 360);
			mGR.mPlayer.obj[i].setScale(.0066);
		}
		if(mGR.mPlayer.bike == 0)
			mGR.mPlayer.oShadow.setPosition(0,k0y,1.64);
		else //if(mGR.mPlayer.bike == 1)
			mGR.mPlayer.oShadow.setPosition(0,k0y,1.62);
//		else
//			mGR.mPlayer.oShadow.setPosition(0,k0y,sz);
		
		mGR.mPlayer.oShadow.setScale(.5,.5,1);
		
		mGR.mPlayer.oShadow.setRotation(180, 0, -(counter % 360));
		
		mGR.o3d_Top.setPosition(sin * .42, cos * .42 +k0y, 2);
		mGR.o3d_Top.setRotation(0, 0, counter % 360);

		mGR.oBoard.setRotation(114, 0, 180);
		mGR.oBoard.setScale(.84f);
		mGR.oBoard.setPosition(0, -0.32, -1.939);
		
		mGR.oCanT.setVisible(false);
		mGR.oTime.setVisible(true);
		
		mGR.oCoin.setRotation(114, 0, 180);
		mGR.oCoin.setScale(6.01);
		mGR.oCoin.setPosition(3.4,28.14, -1.939);
		
		
		for (int i = 0; i < mGR.oArrow.length; i++) {
			mGR.oArrow[i].setVisible(true);
			mGR.oArrow[i].setScale(i==sel-4?1.6:1.8);
		}
		
		if(counter%10==0){
			mGR.updateCoinBitmap();
			mGR.updateTimeBitmap();
		}
		if(mGR.mShouldUpdateCoin)
			mGR.CoinUpdate();
		if(mGR.mShouldUpdateTime)
			mGR.TimeUpdate();
		
		mGR.oBack.setRotation(114, 0, 180);
		mGR.oBack.setPosition(-13.5, 65.7, -1.939);
		mGR.oBack.setScale(sel == 1?11:9.51);
		mGR.oAdsFree.setScale(sel == 2?3.3:3);
		if(mGR.bike[mGR.mPlayer.bike]){
			mGR.OBuy.setScale(sel == 3?1.1:1.03);
			mGR.OBuy.setVisible(true);
			mGR.oPlayTxt.setVisible(false);

			mGR.oTime.setRotation(114, 0, 180);
			mGR.oTime.setScale(1.2);
			mGR.oTime.setPosition(-.07,.28, -1.939);
			
		}
		else{
			mGR.oPlayTxt.setScale(sel == 3?1.1:1.03);
			mGR.OBuy.setVisible(false);
			mGR.oPlayTxt.setVisible(true);
			
			mGR.oTime.setRotation(114, 0, 180);
			mGR.oTime.setScale(.76);
			mGR.oTime.setPosition(-.7,.35, -1.939);
		}
		
		if(mGR.mPlayer.bike == 0){
			mGR.oPowerBar[0].setPosition(-.22+2.7*.1258, -.95f, -1.939);//Scal By One .90f
			mGR.oPowerBar[0].setScale(2.7,.75,.75);
			
			mGR.oPowerBar[1].setPosition(-.22+1.88*.1258, -.72f, -1.939);//Scal By One .94f
			mGR.oPowerBar[1].setScale(1.88,.80,.80);
			
			mGR.oPowerBar[2].setPosition(-.22+2.88*.1258f, -.47, -1.939);//Scal By One .96f
			mGR.oPowerBar[2].setScale(2.88,.85,.85);
			
			mGR.oPowerBar[3].setPosition(-.23f+4*.1258f, -.18, -1.939);//Scal By One 1
			mGR.oPowerBar[3].setScale(4,.90,.90);
		}
		if(mGR.mPlayer.bike == 1){
			mGR.oPowerBar[0].setPosition(-.22+3.6*.1258, -.95f, -1.939);//Scal By One .90f
			mGR.oPowerBar[0].setScale(3.6,.75,.75);
			
			mGR.oPowerBar[1].setPosition(-.22+4.7*.1258, -.72f, -1.939);//Scal By One .94f
			mGR.oPowerBar[1].setScale(4.6,.80,.80);
			
			mGR.oPowerBar[2].setPosition(-.22+3.84*.1258f, -.47, -1.939);//Scal By One .96f
			mGR.oPowerBar[2].setScale(3.84,.85,.85);
			
			mGR.oPowerBar[3].setPosition(-.23f+3*.1258f, -.18, -1.939);//Scal By One 1
			mGR.oPowerBar[3].setScale(3,.90,.90);
		}
		if(mGR.mPlayer.bike == 2){
			mGR.oPowerBar[0].setPosition(-.22+4.5*.1258, -.95f, -1.939);//Scal By One .90f
			mGR.oPowerBar[0].setScale(4.5,.75,.75);
			
			mGR.oPowerBar[1].setPosition(-.22+3.76*.1258, -.72f, -1.939);//Scal By One .94f
			mGR.oPowerBar[1].setScale(3.74,.80,.80);
			
			mGR.oPowerBar[2].setPosition(-.22+4.80*.1258f, -.47, -1.939);//Scal By One .96f
			mGR.oPowerBar[2].setScale(4.80,.85,.85);
			
			mGR.oPowerBar[3].setPosition(-.23f+4*.1258f, -.18, -1.939);//Scal By One 1
			mGR.oPowerBar[3].setScale(4,.90,.90);
		}
		if(mGR.getCurrentCamera().getY()!=0)
			mGR.getCurrentCamera().setY(0);
//		if(mGR.getCurrentCamera().getRotX()!=0)
//			mGR.getCurrentCamera().setRotX(0);//-66
		if(mGR.getCurrentCamera().getRotX()!=-66)
			mGR.getCurrentCamera().setRotX(-66);//-66
	}
	
	boolean Handle_Shop(MotionEvent event){
		sel =0;
		if(CircRectsOverlap(-.47,.9,.12,.08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =1;//Back
		
		if(CircRectsOverlap(-.05,.58,.14,.1, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =2;//Ads Free
		
		if(CircRectsOverlap(0.40,-.56,.12,0.08, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =3;//Play-buy
		
		if(CircRectsOverlap(-.45,.15,.12,.08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =4;//Left
		
		if(CircRectsOverlap(0.45,.15,.12,.08, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =5;//Right
		
		if(MotionEvent.ACTION_UP==event.getAction() && sel>0){
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				mGR.setVisible(false);
				break;
			case 2:

				break;
			case 3:
				if(mGR.bike[mGR.mPlayer.bike]){
					if(HTTRenderer.mCoin >= M.BIKEPRICE[mGR.mPlayer.bike]){
						HTTRenderer.mCoin -= M.BIKEPRICE[mGR.mPlayer.bike];
						mGR.bike[mGR.mPlayer.bike] = false;
					}else{
						mGR.mStart.Buy();
					}
				}else{
					mGR.setVisible(false);
					mGR.gameReset();
					M.GameScreen = M.GAMEPLAY;
				}
				break;
			case 4:
				mGR.mPlayer.bike--;
				if(mGR.mPlayer.bike<0)
					mGR.mPlayer.bike = 2;
				break;
			case 5:
				mGR.mPlayer.bike++;
				if(mGR.mPlayer.bike>2)
					mGR.mPlayer.bike = 0;
				break;
			}
			M.sound5(R.raw.click);
			sel =0;
		}
		return true;
	}
	
	void About(){
		/*mGR.oMenu_bg.setVisible(true);
		mGR.test.setVisible(true);
		mGR.o3d_Tree[0].setVisible(true);
		mGR.test.setRotation(counter, counter, counter);*/
////		mGR.oMenu_bg.set

		double sin = -Math.sin(0);
		double cos = -Math.cos(0);
		float k0y = 0.74f;
		for (int i = 0; i < 1; i++) {
			mGR.mPlayer.obj[i].setPosition(0, 0, 1.88);
			mGR.mPlayer.obj[i].setRotation(0, 0, counter % 360);
			mGR.mPlayer.obj[i].setScale(.0066);
			mGR.mPlayer.obj[i].setVisible(true);
		}
	}
	float svx = 0.01f;
	int rvz = 1;
	void Menu(){
		
		mGR.oMenu_bg.setVisible(true);
//		mGR.oMusic[0].setVisible(M.setMusic);
//		mGR.oMusic[1].setVisible(!M.setMusic);
//		mGR.oSencer[0].setVisible(M.setSenser);
//		mGR.oSencer[1].setVisible(!M.setSenser);
		mGR.oSound[0].setVisible(M.setValue);
		mGR.oSound[1].setVisible(!M.setValue);
		mGR.ofb.setVisible(true);
		mGR.oGoogle.setVisible(true);
		mGR.oHTT.setVisible(true);
		
		mGR.oAchieve.setVisible(true);
		mGR.oHelp.setVisible(true);
		mGR.oGlow.setVisible(true);
		mGR.oStart.setVisible(true);
		mGR.oScore.setVisible(true);
		mGR.oTime.setVisible(true);
		mGR.oCoin.setVisible(true);
		mGR.oSArrow.setVisible(true);
				
		mGR.oSound[0].setScale(sel == 1 ? 1.05f :.95f);
		mGR.oSound[1].setScale(sel == 1 ? 1.05f :.95f);
		
//		mGR.oMusic[0].setScale(sel == 3 ? 1.05f :.95f);
//		mGR.oMusic[1].setScale(sel == 3 ? 1.05f :.95f);

//		mGR.oSencer[0].setScale(sel == 3 ? 1.05f :.95f);
//		mGR.oSencer[1].setScale(sel == 3 ? 1.05f :.95f);

		mGR.ofb.setScale(sel == 4 ? 1.05f :.95f);
		mGR.oGoogle.setScale(sel == 5 ? 1.05f :.95f);
		mGR.oHTT.setScale(sel == 6 ? 1.05f :.95f);
		
		mGR.oAchieve.setScale(sel == 8 ?1.1:1);
		mGR.oHelp.setScale(sel == 7 ?1.1:1);
		
		
		mGR.oGlow.setScale(mGR.oGlow.getScaleX()+svx);
		if(mGR.oGlow.getScaleX() > 1.05)
			svx = -.01f;
		if(mGR.oGlow.getScaleX() < 0.90)
			svx = 0.01f;
		
		
		mGR.oTime.setPosition(1.3, -1.78,-1.939);
		mGR.oTime.setScale(1.46,1.00,1.46);
		mGR.oTime.setRotation(180, 0, 180);
		
		mGR.oCoin.setPosition(1.5, -2.05,-1.939);
		mGR.oCoin.setScale(1.46,1.00,1.46);
		mGR.oCoin.setRotation(180, 0, 180);
		
		mGR.oSArrow.setRotation(180, 0, mGR.oSArrow.getRotZ()+rvz);
		mGR.oSArrow.setPosition(.032f, .17, 1.39);
		mGR.oSArrow.setScale(.61);
		
		if(mGR.oSArrow.getRotZ() > 195)
			rvz = -1;
		if(mGR.oSArrow.getRotZ() < 165)
			rvz = 1;
		
		
		if(mGR.mShouldUpdateCoin)
			mGR.CoinUpdate();
		if(mGR.mShouldUpdateTime)
			mGR.TimeUpdate();
		
		if(mGR.getCurrentCamera().getY()!=0)
			mGR.getCurrentCamera().setY(0);
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	
	boolean Handle_Manu(MotionEvent event){
		sel =0;
		for(int i=0;i<3;i++){
			if(CirCir(-.35f+i*.35f, 0.63f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				sel =i+1;
		}
		for(int i=0;i<3;i++){
			if(CirCir(-.23f+i*.23f, -.84f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				sel =i+4;
		}
		
		if(CirCir(-0.3, -.14f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =7;
		if(CirCir(0.3, -.14f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =8;
		if(CirCir(0.03, -.03f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			sel =9;
		if(MotionEvent.ACTION_UP==event.getAction() && sel>0){
			switch (sel) {
			case 1:
				M.setValue=!M.setValue;
				break;
			case 2:
//				M.setMusic=!M.setMusic;
				break;
			case 3:
				M.setMusic=!M.setMusic;//M.setSenser=!M.setSenser;
				break;
			case 4:
				//facebook();
				M.GameScreen = M.GAMEABOUT;
				break;
			case 5:
				google();
				break;
			case 6:
				MoreGame();
				break;
			case 7:
//				mGR.mStart.onShowLeaderboardsRequested();
//				M.GameScreen = M.GAMEHELP;
//				mGR.setVisible(false);
				break;
			case 8:
//				mGR.mStart.onShowAchievementsRequested();//Achivement
				break;
			case 9:
				M.GameScreen = M.GAMESHOP;
				mGR.setVisible(false);
				break;
			}
			M.sound5(R.raw.click);
			sel =0;
		}
		return true;
	}
	
	
	void GameOver() {
		for(int i =0;i<mGR.mOpp.length;i++){
			mGR.mOpp[i].oShadow.setVisible(true);
		}
		for (int i = 0; i < mGR.o3d_Road.length; i++) {
			mGR.o3d_Road[i].setVisible(true);
		}
		for (int i = 0; i < mGR.oSide.length; i++) {
			for (int j = 0; j < mGR.oSide[i].length; j++){
				if(mGR.mPlayer.Road-2 <= i*2+j && mGR.mPlayer.Road > i*2+j)
					mGR.oSide[i][j].setVisible(true);
				else
					mGR.oSide[i][j].setVisible(false);
			}
		}
		if((mGR.mPlayer.Road==1)){
			mGR.oSide[2][1].setVisible(true);
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			mGR.mOpp[i].obj.setVisible(true);
		}
		for (int i = 0; i < mGR.o3d_Tree.length; i++) {
			for (int j = 0; j < mGR.o3d_Tree[i].length; j++){
				mGR.o3d_Tree[i][j].setVisible(false);
//				if(i == 1 || i == 3){
//					mGR.o3d_Tree[i][j].setScale(.01f * mGR.width,.02f * mGR.width,.01f * mGR.width);
//				}
			}
		}
		for(int i =0;i<mGR.TreVal.length;i++){
			if(mGR.TreVal[i]<mGR.o3d_Tree.length)
				mGR.o3d_Tree[mGR.TreVal[i]][i].setVisible(true);
		}
		mGR.mPlayer.obj[mGR.mPlayer.bike].setVisible(true);
		mGR.oBoard.setVisible(true);

		mGR.oBoard.setPosition(0, -1.77, 2.50);
		mGR.oBoard.setScale(.41);
		mGR.oBoard.setRotation(124, 0, 180);

		if (M.GameScreen == M.GAMEOVER) {
			for (int i = 0; i < 3; i++) {
				mGR.oIcnBtn[i].setPosition((.27f + .26 * i), -2.04, 2.51f);
				mGR.oIcnBtn[i].setVisible(i != 2);
				mGR.oIcnBtn[i].setRotZ(counter * 5);
				if (sel == i + 2)
					mGR.oIcnBtn[i].setScale(.35f);
				else
					mGR.oIcnBtn[i].setScale(.27);
			}
			mGR.oRetry.setPosition(.27f, -2.04, 2.51f);
			mGR.oRetry.setScale(.27);
			mGR.oRetry.setRotation(124, 0, 180);
			mGR.oRetry.setVisible(true);

			mGR.oMenu.setPosition(.53f, -2.04, 2.51f);
			mGR.oMenu.setScale(.27);
			mGR.oMenu.setVisible(true);

			mGR.oGameOver.setPosition(-0.44, -1.42, 2.51);
			mGR.oGameOver.setScale(.43);
			mGR.oGameOver.setVisible(true);

			mGR.oPlayTime.setPosition(-0.46, -1.71, 2.51);
			mGR.oPlayTime.setScale(.39);
			mGR.oPlayTime.setVisible(true);

			
			mGR.oTime.setPosition(.70, -1.75, 2.51);
			mGR.oTime.setRotation(124, 0, 180);
			mGR.oTime.setScale(.85f);
			mGR.oTime.setVisible(true);

			
			mGR.oTotalCoin.setPosition(-0.44, -1.9, 2.51);
			mGR.oTotalCoin.setScale(.38);
			mGR.oTotalCoin.setVisible(true);

			mGR.oCoin.setPosition(.24, -1.9243, 2.51);
			mGR.oCoin.setScale(.38);
			mGR.oCoin.setRotation(124, 0, 180);
			mGR.oCoin.setVisible(true);

			if (counter % 100 == 0 && mGR.oTime.isVisible()) {
				mGR.updateTimeBitmap();
				mGR.updateCoinBitmap();
			}
			if (mGR.mShouldUpdateTime && mGR.oTime.isVisible()
					&& (counter > 10)) {
				mGR.TimeUpdate();
			}
			if (mGR.mShouldUpdateCoin && mGR.oTime.isVisible()
					&& (counter > 10)) {
				mGR.CoinUpdate();
			}
		} else {
			for (int i = 0; i < 3; i++) {

				mGR.oIcnBtn[i].setPosition((-.4f + .4 * i), -2.04, 2.51f);
				mGR.oIcnBtn[i].setVisible(true);
				mGR.oIcnBtn[i].setRotZ(counter * 5);
				if (sel == i + 1)
					mGR.oIcnBtn[i].setScale(.35f);
				else
					mGR.oIcnBtn[i].setScale(.30);
			}
			mGR.oPaused.setPosition(-0.44, -1.42, 2.51);
			mGR.oPaused.setScale(.43);
			mGR.oPaused.setRotation(124, 0, 180);
			mGR.oPaused.setVisible(true);

			mGR.oPlay.setPosition(-.4f, -2.04, 2.51f);
			mGR.oPlay.setScale(.3);
			mGR.oPlay.setVisible(true);

			mGR.oRetry.setPosition(-.0f, -2.04, 2.51f);
			mGR.oRetry.setScale(.3);
			mGR.oRetry.setVisible(true);

			mGR.oMenu.setPosition(0.4f, -2.04, 2.51f);
			mGR.oMenu.setScale(.3);
			mGR.oMenu.setVisible(true);
		}

		if (mGR.getCurrentCamera().getY() != 2)
			mGR.getCurrentCamera().setY(2);
		if (mGR.getCurrentCamera().getRotX() != -56)
			mGR.getCurrentCamera().setRotX(-56);// -66
	}
	
	boolean Handle_GameOver(MotionEvent event){
		sel =0;
		if(M.GameScreen == M.GAMEOVER){
			for(int i=0;i<2;i++){
				if(CirCir(.20f+i*.3f, -.87f, .05f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
					sel =i+2;
			}
		}else{
			for(int i=0;i<3;i++){
				if(CirCir(-.3f+i*.3f, -.87f, .08f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
					sel =i+1;
			}
		}
		if(MotionEvent.ACTION_UP==event.getAction() && sel>0){
			switch (sel) {
			case 1:
				mGR.setVisible(false);
//				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				
				break;
			case 2:
				mGR.setVisible(false);
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3:
				mGR.setVisible(false);
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound5(R.raw.click);
			sel =0;
		}
		return true;
	}
	int nm = 0;
	void setNew(){
		boolean move = false;
		int noObj =1;
		if(mGR.mPlayer.rowOpp >=0 &&  mGR.mPlayer.rowOpp <20){
			move = false;
			noObj =1;
		}
		if(mGR.mPlayer.rowOpp >=20 &&  mGR.mPlayer.rowOpp <50){
			if(mGR.mPlayer.rowOpp%10==5)
				move = true;
			noObj =1;
		}
		if(mGR.mPlayer.rowOpp >=50 &&  mGR.mPlayer.rowOpp <90){
			if(mGR.mPlayer.rowOpp%10==4 || mGR.mPlayer.rowOpp%10==8 )
				move = true;
			noObj =1;
		}
		if(mGR.mPlayer.rowOpp >=90 &&  mGR.mPlayer.rowOpp <140){
			if(mGR.mPlayer.rowOpp%3==0)
				noObj =2;
			else
				move = true;
		}
		if(mGR.mPlayer.rowOpp >=140 &&  mGR.mPlayer.rowOpp <220){
			if(mGR.mPlayer.rowOpp%2==0)
				noObj =2;
			else
				move = true;
		}
		if(mGR.mPlayer.rowOpp >=200){
			if(M.mRand.nextBoolean())
				noObj =2;
			move = M.mRand.nextBoolean();
		}
		int next =-1;
		mGR.mPlayer.Crosscar+=noObj;
		mGR.mScore+=mGR.mPlayer.tapBot*2+noObj*(2+M.mRand.nextInt(10));
		
		int m = nm;
		nm++;
		nm %= mGR.mOpp.length;
		for (int i = 0,k=0; i < mGR.mOpp.length && k < noObj; i++) {
			int n = m % mGR.mOpp.length;
			
			if (mGR.mOpp[n].obj.getY() < -mGR.width) {
				mGR.mOpp[n].set(mGR.mOpp[n].spd,22, 0);
				if(next !=-1){
					if (M.mRand.nextInt(2) == 0) {
						mGR.mOpp[n].obj.setX(0);
						mGR.mOpp[next].obj.setX(M.mRand.nextBoolean() ? .7: -.7);
						if (move) {
							if (mGR.mOpp[next].obj.getX() > 0)
								mGR.mOpp[n].indi = 2;
							else
								mGR.mOpp[n].indi = 1;
						}
					}else{
						mGR.mOpp[n].obj.setX(-.7f);
						mGR.mOpp[next].obj.setX(0.7);
						if (move) {
							if (M.mRand.nextBoolean())
								mGR.mOpp[n].indi = 1;
							else
								mGR.mOpp[next].indi = 2;
						}
					}
				}
				if(noObj == 1 && move){
					if (mGR.mOpp[n].obj.getX() > .1)
						mGR.mOpp[n].indi = 2;
					else if (mGR.mOpp[n].obj.getX() < -.1)
						mGR.mOpp[n].indi = 1;
					else
						mGR.mOpp[n].indi = M.mRand.nextInt(2)+1;
				}
				next = i;
				k++;
			}
			m++;
		}
	}
	int overCounter =0;
	void Gameover_k(){
		M.Bikepause(); 
		M.stop();
		mGR.mPlayer.gameCrash();
		HTTRenderer.mCoin += mGR.mPlayer.CollectCoin;
		M.sound6(R.raw.blast_k);
	}
	void setCoin(int no){
		double dis =18;
		for(int i =0;i<mGR.o3d_Coin.length;i++){
			if(dis < mGR.o3d_Coin[i].getY()){
				dis =mGR.o3d_Coin[i].getY();
			}
		}
		dis+=2;
		mGR.o3d_Coin[no].setPosition(-.7+M.mRand.nextInt(3) * .7,dis,0);
	}
	void Draw_GamePlay(){
		if(mGR.getCurrentCamera().getY()!=2)
			mGR.getCurrentCamera().setY(2);
		if(mGR.getCurrentCamera().getRotX()!=-56)
			mGR.getCurrentCamera().setRotX(-56);//-56
		for (int i = 0; i < mGR.o3d_Road.length; i++) {
			mGR.o3d_Road[i].setVisible(true);
		}
		if(mGR.chalage){
			if(counter%10==0)
				mGR.updateChallangeBitmap();
			mGR.ChallangeUpdate();
			for (int i = 0; i < mGR.o3d_Coin.length; i++) {
				mGR.o3d_Coin[i].setVisible(true);
				mGR.o3d_Coin[i].setRotZ(counter*(i+1));
			}
			mGR.oBoard.setVisible(true);
			mGR.oBoard.setPosition(0, .22, 1.50);
			mGR.oBoard.setScale(.6,.9,1);
			mGR.oBoard.setRotation(180, 0, 180);
			
			mGR.oChallenge.setVisible(true);
			mGR.oChallenge.setPosition(0, 0, 1.50);
			mGR.oChallenge.setScale(1);
			mGR.oChallenge.setRotation(180, 0, 180);
//			mGR.Challenge =7;//(int)(sx*10);
			
			

			for (int i = 0; i < mGR.oSide.length; i++) {
				for (int j = 0; j < mGR.oSide[i].length; j++){
					if(mGR.mPlayer.Road-2 <= i*2+j && mGR.mPlayer.Road > i*2+j)
						mGR.oSide[i][j].setVisible(true);
					else
						mGR.oSide[i][j].setVisible(false);
				}
			}
			for(int i =0;i<mGR.TreVal.length;i++){
				if(mGR.TreVal[i]<mGR.o3d_Tree.length)
					mGR.o3d_Tree[mGR.TreVal[i]][i].setVisible(true);
			}
			return;
		}
		if(mGR.ChallengeCom == 0)
		{
			if(mGR.mPlayer.Distance >= M.Challenges[mGR.Challenge%M.Challenges.length][0]&&
				mGR.mPlayer.Crosscar >= M.Challenges[mGR.Challenge%M.Challenges.length][1]&&
				mGR.mPlayer.CollectCoin >= M.Challenges[mGR.Challenge%M.Challenges.length][2]&&
				(((System.currentTimeMillis()-mGR.mTime)/60000)>=M.Challenges[mGR.Challenge%M.Challenges.length][3]
						||M.Challenges[mGR.Challenge%M.Challenges.length][3] == 0))
			{
//				M.sound5(GameRenderer.mContext,R.raw.levelwin);
//				Player.mTotalCoin +=Track.Challenges[mGR.Challenge%Track.Challenges.length][4];
				mGR.ChallengeCom = 1;
				mGR.Challenge++;
				mGR.mPlayer.CollectCoin+=M.Challenges[mGR.Challenge%M.Challenges.length][4];
			}
		}
		
		if(mGR.ChallengeCom > 0 && mGR.ChallengeCom < 200) {
			mGR.ChallengeCom++;
			if (counter % 10 == 0)
				mGR.updateChallangeBitmap();
			mGR.ChallangeUpdate();
			mGR.oChallenge.setVisible(true);
			mGR.oChallenge.setPosition(0, 0, 1.50);
			mGR.oChallenge.setScale(1);
			mGR.oChallenge.setRotation(180, 0, 180);
			if( mGR.ChallengeCom > 199){
				mGR.updateChallangeBitmap();
				mGR.ChallangeUpdate();
			}
		}else{
			if (counter % 10 == 0)
				mGR.updateChallangeBitmap();
			mGR.ChallangeUpdate();
			mGR.oChallenge.setVisible(true);
			mGR.oChallenge.setPosition(.235, -1.823, 1.50);
			mGR.oChallenge.setScale(1);
			mGR.oChallenge.setRotation(124, 0, 180);
		}
		mGR.oMeter.setVisible(true);
		mGR.oSArrow.setVisible(true);
		mGR.oTime.setVisible(true);
		mGR.oCoin.setVisible(true);
		for(int i =0;i<mGR.mOpp.length;i++){
			mGR.mOpp[i].oShadow.setVisible(true);
			switch(i%5){
			case 1:
				mGR.mOpp[i].oShadow.setPosition(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(), .58);
				mGR.mOpp[i].oShadow.setY(mGR.mOpp[i].obj.getY()+(.36-mGR.mOpp[i].obj.getY()*.02));
				mGR.mOpp[i].oShadow.setScale(.84,1.44,1);
				break;
			case 2:
				mGR.mOpp[i].oShadow.setPosition(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(), .20);
				mGR.mOpp[i].oShadow.setY(mGR.mOpp[i].obj.getY()+(.46-mGR.mOpp[i].obj.getY()*.02));
				mGR.mOpp[i].oShadow.setScale(1.37,2.56,1);
				break;
			case 3:
				mGR.mOpp[i].oShadow.setPosition(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(), .65);
				mGR.mOpp[i].oShadow.setY(mGR.mOpp[i].obj.getY()+(.36-mGR.mOpp[i].obj.getY()*.02));
				mGR.mOpp[i].oShadow.setScale(0.71,0.99,1);
				break;
			case 4:
				mGR.mOpp[i].oShadow.setPosition(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(), .40);
				mGR.mOpp[i].oShadow.setY(mGR.mOpp[i].obj.getY()+(.46-mGR.mOpp[i].obj.getY()*.02));
				mGR.mOpp[i].oShadow.setScale(1.17,2.00,1);
				break;
			default:
				mGR.mOpp[i].oShadow.setPosition(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(), .58);
				mGR.mOpp[i].oShadow.setY(mGR.mOpp[i].obj.getY()+(.36-mGR.mOpp[i].obj.getY()*.02));
				mGR.mOpp[i].oShadow.setScale(.82,1.11,1);
				break;
			}
			if(mGR.mOpp[i].oShadow.getY()<-1)
				mGR.mOpp[i].oShadow.setPosition(-100, 100, -100);
		}
		{
			for (int i = 0; i < mGR.o3d_Coin.length; i++) {
				mGR.o3d_Coin[i].setVisible(true);
				mGR.o3d_Coin[i].setY(mGR.o3d_Coin[i].getY()- mGR.mPlayer.Spd);
				mGR.o3d_Coin[i].setRotZ(counter*(i+1));
				if(mGR.o3d_Coin[i].getY()<-mGR.width)
					setCoin(i);
				if (Rect2RectIntersection(mGR.o3d_Coin[i].getX(), mGR.o3d_Coin[i].getY(),.2f, .2f, mGR.mPlayer.obj[mGR.mPlayer.bike].getX(), mGR.mPlayer.obj[mGR.mPlayer.bike].getY(), .4f,1)) {
					M.sound3(R.raw.coin_k);
					setCoin(i);
					mGR.mPlayer.CollectCoin++;
				}
			}
		}
		for (int i = 0; i < mGR.o3d_Road.length; i++) {
			mGR.o3d_Road[i].setY(mGR.o3d_Road[i].getY() - mGR.mPlayer.Spd);
		}
		
		for(int i=0;i<mGR.o3d_Road.length;i++){
			if(mGR.o3d_Road[i].getY() < -mGR.width){
				mGR.o3d_Road[i].setY(mGR.o3d_Road[i==0?mGR.o3d_Road.length-1:i-1].getY()+mGR.width-.02);
			}
		}
		for (int i = 0; i < mGR.oSide.length; i++) {
			for (int j = 0; j < mGR.oSide[i].length; j++){
				if(mGR.mPlayer.Road-2 <= i*2+j && mGR.mPlayer.Road > i*2+j)
					mGR.oSide[i][j].setVisible(true);
				else
					mGR.oSide[i][j].setVisible(false);
				mGR.oSide[i][j].setY(mGR.oSide[0][j].getY() - mGR.mPlayer.Spd*.67);
			}
		}
		if((mGR.mPlayer.Road==1)){
			mGR.oSide[2][1].setVisible(true);
		}
//		if(mGR.mPlayer.Road>6){
//			mGR.oSide[2][0].setVisible(true);
//			mGR.oSide[2][1].setVisible(true);
//		}
		boolean next = false;
		for (int i = 0; i < mGR.oSide[0].length; i++) {
			if (mGR.oSide[0][i].getY() < -10)
				next =true;
			if (mGR.oSide[0][i].getY() < -11){
				mGR.oSide[0][i].setY(mGR.oSide[0][i == 0 ? 1 : 0].getY() + 22.0);
				if(mGR.mPlayer.Road%2==1)
					mGR.mPlayer.Road++;
				if(i ==0 && mGR.mPlayer.isRoad){
					mGR.mPlayer.isRoad= false;
					mGR.mPlayer.Road++;
					if(mGR.mPlayer.Road>8)
						mGR.mPlayer.Road =1;
				}
			}
		}
		for (int i = 0; i < mGR.o3d_Tree.length; i++) {
			for (int j = 0; j < mGR.o3d_Tree[i].length; j++) {
				mGR.o3d_Tree[i][j].setY(mGR.o3d_Tree[0][j].getY()- mGR.mPlayer.Spd);
				if (mGR.o3d_Tree[i][j].getY() < -mGR.width) {
					mGR.o3d_Tree[i][j].setY(mGR.o3d_Tree[0][j == 0 ? mGR.o3d_Tree[j].length - 1: j - 1].getY()+ mGR.width*2);
					mGR.TreVal[j] = (byte)(((mGR.mPlayer.Road-1) /2)%4);
					if(mGR.mPlayer.isRoad && next){
						mGR.TreVal[j] = 100;
					}
				}
				mGR.o3d_Tree[i][j].setVisible(false);
			}
		}
		for(int i =0;i<mGR.TreVal.length;i++){
			if(mGR.TreVal[i]<mGR.o3d_Tree.length)
				mGR.o3d_Tree[mGR.TreVal[i]][i].setVisible(true);
		}
		for(int i=0;i<mGR.mOpp.length;i++){
			if(mGR.mOpp[i].obj.getY()>22 || mGR.mOpp[i].obj.getY()<-mGR.width)
				mGR.mOpp[i].obj.setVisible(false);
			else{
				mGR.mOpp[i].obj.setVisible(true);
			}
			mGR.mOpp[i].update((mGR.mPlayer.Spd-mGR.mOpp[i].spd));
			if (mGR.mOpp[i].indi == 0) {
				mGR.mOpp[i].oIndicator.setVisible(false);
			} else {
				if (counter%4 < 2) 
					mGR.mOpp[i].oIndicator.setVisible(false);
				else
					mGR.mOpp[i].oIndicator.setVisible(true);
					
				mGR.mOpp[i].oIndicator.setRotation(90, 0, 180);
				mGR.mOpp[i].oIndicator.setScale(.22);
				switch (i % 4) {
				case 0:
					mGR.mOpp[i].oIndicator.setPosition(mGR.mOpp[i].obj.getX()
							+ (mGR.mOpp[i].indi == 1 ? .15 : -.15),
							mGR.mOpp[i].obj.getY() - .02,
							mGR.mOpp[i].obj.getZ() - .24);
					break;
				case 1:
					mGR.mOpp[i].oIndicator.setPosition(mGR.mOpp[i].obj.getX()
							+ (mGR.mOpp[i].indi == 1 ? .15 : -.15),
							mGR.mOpp[i].obj.getY() - .15,
							mGR.mOpp[i].obj.getZ() - .24);
					break;
				case 2:
					mGR.mOpp[i].oIndicator.setPosition(mGR.mOpp[i].obj.getX()
							+ (mGR.mOpp[i].indi == 1 ? .15 : -.16),
							mGR.mOpp[i].obj.getY() - .57,
							mGR.mOpp[i].obj.getZ() - .52);
					mGR.mOpp[i].oIndicator.setScale(.32);
					break;
				case 3:
					mGR.mOpp[i].oIndicator.setPosition(mGR.mOpp[i].obj.getX()
							+ (mGR.mOpp[i].indi == 1 ? .13 : -.13),
							mGR.mOpp[i].obj.getY() - .0,
							mGR.mOpp[i].obj.getZ() - .24);
					break;
				}
			
		}
			
		}
		if (mGR.mPlayer.newOpp > 20) {
			mGR.mPlayer.newOpp =0; 
			mGR.mPlayer.rowOpp++;
			setNew();
		}
		mGR.mPlayer.update();
		mGR.mPlayer.obj[mGR.mPlayer.bike].setVisible(true);
		for(int i=0;i<mGR.mOpp.length && mGR.mPlayer.Crash <=0;i++){
			if (Rect2RectIntersection(mGR.mOpp[i].obj.getX(), mGR.mOpp[i].obj.getY(),.4f, 1, mGR.mPlayer.obj[mGR.mPlayer.bike].getX(), mGR.mPlayer.obj[mGR.mPlayer.bike].getY(), .4f,1)) {
				Gameover_k();
				break;
			}
		}
		if(mGR.mPlayer.Crash>250){
			mGR.mHScore += mGR.mScore;
			mGR.mTime = System.currentTimeMillis() - mGR.mTime;
			M.GameScreen = M.GAMEOVER;
			mGR.setVisible(false);
			M.stop();
			if (overCounter % 3 == 0)
				HTTRenderer.mStart.ShowInterstitial();
			overCounter++;
		}
		mGR.oCircle.setVisible(true);
		mGR.oCircle.setScale(.35f);
		mGR.oCircle.setPosition( 0.3f,  -2.12, 2.5);
		mGR.oCircle.setRotation(125, 0, mGR.mPlayer.counter);
		
		mGR.oMeter.setScale(.35f);
		mGR.oMeter.setPosition(0, -2.01, 2.5);
		mGR.oMeter.setRotation(125, 0, 180);
		
		mGR.oSArrow.setRotation(124, 0, counter%3+150-mGR.mPlayer.gear*2);
		mGR.oSArrow.setPosition(-.0153,-2.19, 2.51);
		mGR.oSArrow.setScale(.36);
		
		mGR.oTime.setRotation(124, 0, 180);
		mGR.oTime.setPosition(0.12f, -2.14, 2.51);
		mGR.oTime.setScale(.48);
		
		mGR.oCoin.setRotation(124, 0, 180);
		mGR.oCoin.setScale(.5);
		mGR.oCoin.setPosition(.732, -2.14, 2.51);

		if (mGR.mPlayer.gear > 2) {
			mGR.o3d_Boost.setVisible(counter%2==0);
			mGR.o3d_Boost.setPosition(mGR.mPlayer.obj[mGR.mPlayer.bike].getPosition());
			mGR.o3d_Boost.setRotation(mGR.mPlayer.obj[mGR.mPlayer.bike].getRotation());
		} else {
			mGR.o3d_Boost.setVisible(false);
		}
		if(counter%10==0){
			mGR.updateTimeBitmap();
			if(counter%20==0)
				mGR.updateCoinBitmap();
		}
		
		if (counter > 10) {
			if (mGR.mShouldUpdateTime) {
				mGR.TimeUpdate();
			}
			if (mGR.mShouldUpdateCoin) {
				mGR.CoinUpdate();
			}
		}
		
		if(mGR.oSArrow.getRotZ() > 270)
			rvz = -1;
		if(mGR.oSArrow.getRotZ() < 90)
			rvz = 1;
		if(mGR.mPlayer.Crash <=0)
			M.Bikeplay(mGR.mPlayer.bike, mGR.mPlayer.gear>2);
	}

	boolean Handle_Gameplay(MotionEvent event) {
		if (event.getX() < M.ScreenWidth / 2)
			mGR.mPlayer.vx = -.01f;
		else
			mGR.mPlayer.vx = 0.01f;
		mGR.mPlayer.isTap = true;
		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.mPlayer.isTap = false;
			if(mGR.chalage){
				mGR.chalage = false;
				mGR.updateChallangeBitmap();
			}
		}
		return true;
	}
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
