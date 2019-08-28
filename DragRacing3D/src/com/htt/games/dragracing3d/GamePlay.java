package com.htt.games.dragracing3d;


import javax.microedition.khronos.opengles.GL10;

import rajawali.materials.Material;
import rajawali.materials.textures.Texture;
import rajawali.parser.Loader3DSMax;

import android.view.MotionEvent;
public class GamePlay {
	HTTRenderer mGR;
	public GamePlay(HTTRenderer GR){
		mGR =GR;
		mGR.mPlayer = new Bike();
		mGR.mOpponent = new Bike();
		mGR.gamePlayed = new boolean[7][10];
		mGR.gameUnlock = new boolean[7][10];
		for (int i = 0; i < mGR.gameUnlock.length; i++)
			mGR.gameUnlock[i][0] = true;
		mGR.mBikeUnlock[0]	= true;
		mGR.mSpec = new Spec[17];
		mGR.mSpec[0] = new Spec("TRIPAL SEVEN", 0);
		mGR.mSpec[1] = new Spec("KT 1000R", 1);
		mGR.mSpec[2] = new Spec("GRAPTOR B1", 2);
		mGR.mSpec[3] = new Spec("BOXER GT", 3);
		mGR.mSpec[4] = new Spec("ATM 110", 4);
		mGR.mSpec[5] = new Spec("SRV 6", 5);
		mGR.mSpec[6] = new Spec("MONSTER KING", 6);
		mGR.mSpec[7] = new Spec("FZ 12000", 7);
		mGR.mSpec[8] = new Spec("STREETFIGHTER", 8);
		mGR.mSpec[9] = new Spec("CRB 777Z", 9);
		mGR.mSpec[10] = new Spec("MT 2K", 10);
		mGR.mSpec[11] = new Spec("GT 650", 11);
		mGR.mSpec[12] = new Spec("DUKES 290", 12);
		mGR.mSpec[13] = new Spec("MK2 1100S", 13);
		mGR.mSpec[14] = new Spec("TUNON V4", 14);
		mGR.mSpec[15] = new Spec("DETONAL 300R", 15);
		mGR.mSpec[16] = new Spec("YAMA Z SPORTS", 16);
	}
	void gameReset() {
		int opp = (int) ((mGR.city * 10 + mGR.mLevel) / 4.5f);
		float val = ((mGR.city * 10 + mGR.mLevel) - opp*4.5f); 
		mGR.mPlayer.set	 (-5.00f, -3.3f, 
				mGR.mSpec[mGR.mBike].getExhost(), mGR.mSpec[mGR.mBike].getNitro(), mGR.mSpec[mGR.mBike].getWeight(),mGR.mSpec[mGR.mBike].getGearBox(),mGR.mBike);
		mGR.mOpponent.set(-5.00f, 3.30f, 
				mGR.mSpec[opp].get4OppExhost(val), mGR.mSpec[opp].get4OppNitro(val), mGR.mSpec[opp].get4OppWeight(val),mGR.mSpec[opp].get4OppGearBox(val),opp);
		
		mGR.mOpponent.Gear = 1;
		mGR.mOpponent.rpm = 50;
		for (int i = 0; i < mGR.m3D_Road.length; i++) {
			mGR.m3D_Road[i].setPosition(-30+i * M.RMUL, 0, 0);
			mGR.m3D_Road[i].setScale(.01*M.ROAD);
			mGR.m3D_Fancing[i].setScale(.01*M.ROAD);
			mGR.m3D_Fancing[i].setPosition(-30+i * M.RMUL, 0, 0);
		}
		mGR.m3D_Block.setScale(M.RMUL*.01);
		mGR.mSpeed = -0.1f;
		mGR.mTowerX = -.41f;
		mGR.start = 0;
		mGR.gamedistace = (mGR.city*10+50)*10;
		mGR.mFLine = mGR.gamedistace;
		mGR.mIsGameWin = 0;
		for(int i =0;i<mGR.m2D_ShiftTxt.length;i++){
			mGR.m2D_ShiftTxt[i].setPosition(0, 80, 6);
		}
		mGR.Gamestart = System.currentTimeMillis();
		Counter = 0;
		mGR.waitCounter =0;
		mGR.citiUnlock = false;
		for(int i =0;i<mGR.m3D_Bike.length;i++){
			mGR.m3D_Bike[i].setRotation(0,0,90);
			mGR.m3D_Bike[i].setScale(.05f);
		}
		mGR.setVisible(false);
	}
	int Counter;
	int mSel;
	int Counter2;
	/*************************Game Play Start****************************/
	void gameWinOver() {
		M.stop();
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
			mGR.setVisible(false);
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
		unclockAchive();
		mGR.showCount++;
		/*if(mGR.showCount%3==0)
			GameRenderer.mStart.show();
		*/
		mGR.setVisible(false);
	}
	
	void unclockAchive(){
		/*	int unlockBike = 0;
		for(int i=0;i<mGR.mBikeUnlock.length;i++){
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
		GameRenderer.mStart.Submitscore(R.string.leaderboard_score, mGR.mScore);
		*/
	}
	
	void Draw_Tower(){
		
		/*DrawTexture(gl, mGR.mTex_Tower, mGR.mTowerX, .29f);
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
		*/
		if (mGR.start < 5)
			mGR.m2D_Handle.setVisible(true);
		else
			mGR.m2D_Handle.setVisible(false);
		
		
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
			mGR.mPlayer.pos(opSpeed);
			mGR.mOpponent.pos(-opSpeed/2);
			

		} else {
			float opSpeed = -(mGR.mPlayer.Engine - mGR.mOpponent.Engine);
			mGR.mOpponent.pos(opSpeed);
		}
		mGR.mPlayer.Dis += mGR.mPlayer.Engine * (1.88f/mGR.gamedistace);
		mGR.mOpponent.Dis += mGR.mOpponent.Engine * (1.88f/mGR.gamedistace);
		mGR.mFLine += mGR.mSpeed;
		mGR.mTowerX += mGR.mSpeed;
		
		/*for (int i = 0; i < 2; i++) {
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
		}*/
		for(int i =0;i<mGR.m3D_Road.length;i++){
			mGR.m3D_Road[i].setX(mGR.m3D_Road[i].getX()+mGR.mSpeed);
			mGR.m3D_Fancing[i].setX(mGR.m3D_Road[i].getX());
		}
		for(int i =0;i<mGR.m3D_Road.length;i++){
			if(mGR.m3D_Road[i].getX()<-30){
				mGR.m3D_Road[i].setX(mGR.m3D_Road[(i==0?mGR.m3D_Road.length:i)-1].getX()+M.RMUL);
			}
		}
		if (mGR.start == 5){
			mSel = 0;
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
				
				System.out.println(mGR.mPlayer.Engine+"~~~~~~~~~~~~~~~~~~~~~~~~~~AA");
				if (mGR.mOpponent.Dis > mGR.mPlayer.Dis && mGR.mPlayer.Engine < 0.25) {
					mGR.mPlayer.Engine = .35f;
				} else 
					mGR.mPlayer.Engine -= .05f;
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
		/*if (mGR.mPlayer.Engine > 0.0 && mGR.start >5)
		{
			M.BikeSound(mGR.mPlayer.BikeNo);
		}else{
			M.BikeSoundStop();
		}*/
		if(mGR.mOpponent.Dis>.94f){
			if(mGR.mIsGameWin == 0)
			{
				mGR.mIsGameWin = 2;
				mGR.mOpponent.time = (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
			}
			if (mGR.mOpponent.Engine > 0) {
				mGR.mOpponent.Engine -= .04f;
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
//				M.startSoundStop();
			}
			else{
//				M.startSound(mGR.mPlayer.BikeNo);
			}
		}
	}
	int wheel = 0;
	void Draw() {
		for(int i =0;i<mGR.m3D_Road.length;i++){
			mGR.m3D_Road[i].setVisible(true);
			mGR.m3D_Fancing[i].setVisible(true);
		}
		mGR.m3D_Block.setVisible(true);
		mGR.m3D_Block.setPosition(mGR.mTowerX, 0, 0);
		
		mGR.m3D_Light.setPosition(mGR.root.sx+mGR.root.sy, 0, 0);
		mGR.m3D_Light.setVisible(true);
		mGR.m3D_Light.setScale(.01*M.RMUL*mGR.root.sz);
		
		mGR.m2D_Bar.setVisible(true);
		
		mGR.m2D_Pointer[0].setVisible(true);
		mGR.m2D_Pointer[0].setX((mGR.mOpponent.Dis<.94?mGR.mOpponent.Dis:.94f)*11.45*1.062);
		
		mGR.m2D_Pointer[1].setVisible(true);
		mGR.m2D_Pointer[1].setX((mGR.mPlayer.Dis<.94?mGR.mPlayer.Dis:.94f)*11.45);
		
		mGR.m2D_FLine.setVisible(true);
		mGR.m2D_FLine.setPosition(mGR.mPlayer.Dis>0?mGR.mFLine:(mGR.mTowerX-1), 0, 0.01f);
		
		//DrawTexture(gl, mGR.mTex_Shadow, mGR.mOpponent.x, mGR.mOpponent.y-(mGR.mOpponent.BikeNo<15?.24f:.20f));*/
//		boolean smock =false;
//		if((mGR.mOpponent.netro && mGR.mOpponent.Nitro>0)||mGR.mOpponent.smock>0){
//			smock = true;
//		}
		/*Draw_Bike(gl, mGR.mOpponent.x, mGR.mOpponent.y, mGR.mOpponent.BikeNo, -wheel * 11,1,(mGR.mOpponent.netro && mGR.mOpponent.Nitro>0)?true:false,smock);
		DrawTexture(gl, mGR.mTex_Driver[1], mGR.mOpponent.x+.03f, mGR.mOpponent.y+.11f);*/
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setVisible(true);
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setPosition(mGR.mOpponent.x, mGR.mOpponent.y, 0);
//		System.out.println(mGR.mOpponent.y);
		if (mGR.mTowerX > -2)
			Draw_Tower();
		float px = mGR.mPlayer.x;
		if (mGR.mPlayer.x < -.5f)
			px = -.5f;
		if (mGR.mPlayer.x > .5f)
			px = 0.5f;
//		smock =false;
//		if((mGR.mPlayer.netro && mGR.mPlayer.Nitro>0)||mGR.mPlayer.smock>0){
//			smock = true;
//		}
		/*DrawTexture(gl, mGR.mTex_Shadow, mGR.mPlayer.x, mGR.mPlayer.y-(mGR.mPlayer.BikeNo<15?.24f:.20f));//
		Draw_Bike(gl, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.BikeNo, -wheel * 10,1,(mGR.mPlayer.netro && mGR.mPlayer.Nitro>0)?true:false,smock);
		DrawTexture(gl, mGR.mTex_Driver[2], px+.03f, mGR.mPlayer.y+.11f);*/
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setVisible(true);
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setPosition(mGR.mPlayer.x, mGR.mPlayer.y, 0);
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setRotation(0, 0, 90);
		
		mGR.m2D_Meter.setVisible(true);
		mGR.m2D_Kata.setVisible(true);
		mGR.m2D_Kata.setRotZ(180-mGR.mPlayer.rpm);
		mGR.m2D_Kata.setPosition(-.11f,-11.75f, 6);
/*		DrawTexture(gl, mGR.mTex_Light[1], -.02f, -.98f);
*/
		if (mGR.start < 5) {
			if (mSel == 1) {
				if (mGR.mPlayer.rpm < 175){
					mGR.mPlayer.rpm += 4;
					if (mGR.mPlayer.rpm > 170)
						mGR.mPlayer.rpm -= 10;
				}
			} else if (mGR.mPlayer.rpm > 6)
				mGR.mPlayer.rpm -= 5;
		}

		
		/*if (mGR.mPlayer.Nitro > 0 && Counter % 2 == 0) {
			DrawTexture(gl, mGR.mTex_GearShift, .225f, -.525f);// Nitrogen
		}
		switch (mSel) {
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
*/
		int spd = (int)(mGR.mPlayer.Engine*99);
		spd = spd < 412 ? spd : 413;
		if(spd>mGR.mPlayer.MaxSpeed)
			mGR.mPlayer.MaxSpeed = spd;
		
		if(mGR.mPlayer.m0to60==0 && spd>=60)
			mGR.mPlayer.m0to60= (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);
		if(mGR.mPlayer.m0to100==0 && spd>=100)
			mGR.mPlayer.m0to100= (int)((System.currentTimeMillis() - mGR.Gamestart)/1000);

		mGR.oCoin.setPosition(2.16, -11.37, 6);
		mGR.oCoin.setScale(2.3);
		mGR.oCoin.setVisible(true);
		mGR.oCoin.setRotation(130,0,180);

		mGR.oSPD.setPosition(-2.07,-12.25, 6);
		mGR.oSPD.setScale(1.59);
		mGR.oSPD.setVisible(true);
		mGR.oSPD.setRotation(130,0,180);
		if (Counter % 10 == 8) {
			mGR.updateCoinBitmap();
			mGR.updateSPDBitmap();
		}
		//139 - 156
		//157 - 166
		for(int i =0;i<mGR.m2D_Shift.length;i++)
			mGR.m2D_Shift[i].setVisible(false);
		for(int i =0;i<mGR.m2D_ShiftTxt.length;i++)
			mGR.m2D_ShiftTxt[i].setVisible(false);
		
		if(mGR.m2D_ShiftTxt[mGR.mShiftGear%mGR.m2D_ShiftTxt.length].getY()<11f){
			mGR.m2D_ShiftTxt[mGR.mShiftGear%mGR.m2D_ShiftTxt.length].setVisible(true);//Over
			mGR.m2D_ShiftTxt[mGR.mShiftGear%mGR.m2D_ShiftTxt.length].setY(mGR.m2D_ShiftTxt[mGR.mShiftGear%mGR.m2D_ShiftTxt.length].getY()+.4);
		}
		
		if(mGR.mPlayer.rpm>135 && mGR.mPlayer.rpm<152){
			mGR.m2D_Shift[0].setVisible(true);//Good
		}else if(mGR.mPlayer.rpm>151 && mGR.mPlayer.rpm<162){
			mGR.m2D_Shift[1].setVisible(true);//Perfect
		}else if(mGR.mPlayer.rpm>161 ){
			mGR.m2D_Shift[2].setVisible(true);//Over
		}
		
		
		if (mGR.start > 1 && mGR.start < 5) {
			if (Counter % 15 == 0){
				mGR.start++;
//				M.sound14(GameRenderer.mContext, R.drawable.beep);
			}
		}
		if (mGR.start >= 5) {
			gamelogic();
		}else if (mGR.start > 1 && mGR.start < 6) {
			/*if(mGR.start == 5)
				M.startSoundStop();
			else
				M.startSound(mGR.mPlayer.BikeNo);*/
			mGR.mPlayer.smock = 50;
			mGR.mOpponent.smock = 40;
		}
		
		if (mGR.start == 0 && Counter % 30 == 0)
			mGR.start = 1;
		if (Counter % 5 == 0)
			Counter2++;
		
		
		switch (mSel) {
		case 1:
			mGR.m2D_GearShift.setVisible(true);
			break;
		case 2:
			mGR.m2D_GearShift.setVisible(true);
			mGR.m2D_GearShift.setPosition(1.5f,-9.98,6.1f);
			break;
		case 3:
			mGR.m2D_GearShift.setVisible(true);
			mGR.m2D_GearShift.setPosition(-4.25f,-11.3,6.1f);
			break;
		case 4:
			mGR.m2D_GearShift.setVisible(true);
			mGR.m2D_GearShift.setPosition(4.25f,-11.3,6.1f);
			break;
		default:
			mGR.m2D_GearShift.setVisible(false);
			break;
		}
		
//		mGR.m2D_GearShift.setVisible(true);
//		mGR.m2D_GearShift.setPosition(mGR.root.sx,mGR.root.sy,6.1f);
		if(mGR.getCurrentCamera().getRotX()!=-50)
			mGR.getCurrentCamera().setRotX(-50);
		
//		if(mGR.getCurrentCamera().getRotX()!=mGR.root.sz*100)
//			mGR.getCurrentCamera().setRotX(mGR.root.sz*100);
	}
	float redius = .2f;
	public boolean Handle_Play(MotionEvent event) {
		System.out.println(screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		mSel = 0;
		if(mGR.start <5 &&
				CirCir(1.2, -.25f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 1;//Start Game
			if(mGR.start == 1){
				mGR.start = 2;
				Counter =1;
			}
		}
		if(mGR.start >=5 &&
				CirCir(.40f,-.48f,redius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 2;//nitrogen
		}
		if(mGR.start >=5 &&
				CirCir(-1.35f,-.85f,redius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 3;///Gear Down
		}
		if(mGR.start >=5 &&
				CirCir(1.35f,-.85f,redius, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 4;//Gear Up
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mSel) {
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
//					M.sound13(GameRenderer.mContext, R.drawable.gearsift);
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
						setGear(0);
					}else if(mGR.mPlayer.rpm>151 && mGR.mPlayer.rpm<162){
						mGR.mPlayer.parfect++;//Perfect
						setGear(1);
					}else if(mGR.mPlayer.rpm>161 ){
						mGR.mPlayer.over++;//Over
						setGear(2);
					}
					mGR.mPlayer.Gear++;
//					M.sound13(GameRenderer.mContext, R.drawable.gearsift);
				}
				break;
			}
			
			mSel = 0;
		}
		return true;
	}
	/*************************Game Play End******************************/
	void setGear(int no){
		mGR.mShiftGear=no;
		for(int i =0;i<mGR.m2D_ShiftTxt.length;i++){
			mGR.m2D_ShiftTxt[i].setPosition(0, -8, 6);
		}
	}
	
	/*************************WinOver Start******************************/
	int nnn =0;
	void Draw_GameOver(GL10 gl){
		mGR.m2D_wlBG.setVisible(true);
		mGR.m2D_wlBG.setScale(4.14);
		
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setVisible(true);
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setPosition(-9,1.56, 1);
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setRotation(0,60,90);
		mGR.m3D_Bike[mGR.mPlayer.BikeNo].setScale(.03);
		
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setVisible(true);
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setPosition(-9,-2.63, 1);
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setRotation(0, 60, 90);
		mGR.m3D_Opp[mGR.mOpponent.BikeNo].setScale(.03f);
		
		mGR.oCoin.setPosition(-8.68, -1.85, 0);
		mGR.oCoin.setScale(5.67);
		mGR.oCoin.setVisible(true);
		mGR.oCoin.setRotation(180,0,180);

		mGR.oSPD.setPosition(.1f,-.1f, 0);
		mGR.oSPD.setScale(4.2);
		mGR.oSPD.setVisible(true);
		mGR.oSPD.setRotation(180,0,180);
		
		mGR.oDoler.setPosition(10.7, 0, 0);
		mGR.oDoler.setScale(4.2);
		mGR.oDoler.setVisible(true);
		mGR.oDoler.setRotation(180,0,180);
		
		if (Counter % 10 == 8) {
			mGR.updateCoinBitmap();
			mGR.updateSPDBitmap();
			mGR.updateDolreBitmap();
		}
		
		if (M.GameScreen == M.GAMEOVER){
			mGR.m2D_Lost.setVisible(true);
			mGR.m2D_Lost.setPosition(0, 7.51, 0);
		}
		else{
			mGR.m2D_Win.setVisible(true);
			mGR.m2D_Win.setPosition(0, 7.51, 0);
		}
		
		mGR.m2D_Retry.setVisible(true);
		mGR.m2D_Retry.setPosition(-10+6.7*0, -6.65, 0);
		mGR.m2D_Retry.setScale(mSel == 1?5:M.SCL);
		
		mGR.m2D_Garage.setVisible(true);
		mGR.m2D_Garage.setPosition(-10+6.7*1, -6.65, 0);
		mGR.m2D_Garage.setScale(mSel == 2?M.SL:M.SCL);
		if(M.GameScreen == M.GAMEOVER){
			mGR.m2D_Hint.setVisible(true);
			mGR.m2D_Hint.setPosition(-10+6.7*2, -6.65, 0);
			mGR.m2D_Hint.setScale(mSel == 3?M.SL:M.SCL);
		}
		else if(!mGR.mIsQuckRace && M.GameScreen == M.GAMEWIN)
		{
			mGR.m2D_Next.setVisible(true);
			mGR.m2D_Next.setPosition(-10+6.7*2, -6.65, 0);
			mGR.m2D_Next.setScale(mSel == 3?M.SL:M.SCL);
		}
		mGR.m2D_Back.setVisible(true);
		mGR.m2D_Back.setPosition(-10+6.7*3, -6.65, 0);
		mGR.m2D_Back.setScale(mSel == 4?M.SL:M.SCL);
		if(mGR.getCurrentCamera().getRotX()!=0)
			mGR.getCurrentCamera().setRotX(0);
	}
	int popup = 0;
	boolean Handle_WinOver(MotionEvent event){
		mSel =0;
		System.out.println(screen2worldX(event.getX())+"    "+screen2worldY(event.getY()));
		if (popup == 1) {
			if (CircRectsOverlap(-.18f, -.25f, redius * 2, redius * 1.0, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 100;
			}
			if (CircRectsOverlap(0.18f, -.25f, redius * 2, redius * 1.0, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				mSel = 101;
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (CircRectsOverlap(-1.2f + .8f * i, -.78f, redius * 2, redius * 1.0, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
					mSel = i + 1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mSel>0){
			nnn++;
			nnn%=17;
			switch (mSel) {
			case 1:
				gameReset();
				M.GameScreen = M.GAMEPLAY;
//				load();
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
					M.GameScreen = M.GAMESHOP;
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
			mGR.setVisible(false);
//				M.sound12(GameRenderer.mContext, R.drawable.click);
			mSel =0;
		}
		return true;
	}
	/*************************WinOver End********************************/
	
	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
}
