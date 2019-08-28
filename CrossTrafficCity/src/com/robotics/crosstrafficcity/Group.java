package com.robotics.crosstrafficcity;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx, sy, sz = 0.0f;
	int counter = 0;
	int slowCounter = 0;
	int sel =0;
	public void setting() {float ud = .01f;switch (_keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;case 5:sz -= ud;break;case 6:sz += ud;break;}System.out.println(counter+"~~~~~~~  " + sx + "f,  " + sy + "f,  "+ sz + "f");}
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
		counter++;
//		setting();
		if(counter == 3){
			mGR.ForSwitchCamera();
		}
		if(mGR.m2d_LBoard == null){
			if (counter > 1) {
				mGR.init();
			}
			return;
		}
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.oLogo.setPosition(.88,-2.52,3);
			mGR.oLogo.setRotation(130, 0, 194);
			mGR.oLogo.setVisible(true);
			if(counter > 60){
				M.GameScreen = M.GAMEMENU;
				mGR.m2d_Crosy.setX(-1.5f);
			}
			break;
		default:
			Draw_GamePlay();
			if (counter % 10 == 8) {
				if (M.GameScreen != M.GAMEPLAY) {
					mGR.updateTimeBitmap();
					mGR.TimeUpdate();
					mGR.updateFreeBitmap();
					mGR.FreeUpdate();
					mGR.updateCoinBitmap();
					mGR.CoinUpdate();
					mGR.updateExtraBitmap();
					mGR.ExtraUpdate();
				}

				mGR.updateScoreBitmap();
				mGR.ScoreUpdate();
			}
			if(counter%3 ==0)
				slowCounter++;
			break;
		}
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEOVER:
			Handle_Over(event);
			break;
		case M.GAMENEWCHAR:
			Handle_NewChar(event);
			break;
		case M.GAMEGIFT:
			Handle_Gift(event);
			break;
		default:
			Handle_Gameplay(event);
			break;
		}
		
//		Handle(event);
		return true;
	}
	boolean check[] = new boolean[7];
	
	void setWater(float x,float y){
		for (int i = 0; i < mGR.mWater.length; i++) {
			mGR.mWater[i].vz = (M.mRand.nextInt(30)+10)*.001f;
			float vx = (M.mRand.nextInt(70))*(M.mRand.nextBoolean()?.002f:-.002f);
			float vy = (M.mRand.nextInt(70))*(M.mRand.nextBoolean()?.002f:-.002f);
			mGR.m3d_Drop[i].setPosition(x + vx, y + vy, -.1);
		}
	}
	
	void setTree(int no){
		int NO = M.mRand.nextInt(2)+1;
		byte ran[] = { 0, 1, 2, 3, 4, 5, 6 };
		boolean che[] = {false,false,false,false,false,false,false};
		for (int i = 0,k =0; i < mGR.mTrees.length && k < 2; i++) {
			if(mGR.mTrees[i].no <0){
				mGR.m3d_Tree[i].setX(.8*(k==0?1:-1));
				mGR.mTrees[i].set(no);
				k++;
			}
		}
		for (int i = 0,k =0; i < mGR.mTrees.length && k < NO; i++) {
			if(mGR.mTrees[i].no <0){
				int nn = M.mRand.nextInt(ran.length - k);
				if (!(ran[nn] == 0 ? true : check[ran[nn] - 1]) || !(ran[nn] == ran.length-1 ? true : check[ran[nn] + 1])) {
					mGR.m3d_Tree[i].setX(ran[nn]*.2-.6);
					mGR.mTrees[i].set(no);
					che[ran[nn]] = true;
				}
				for (int p = nn; p < ran.length-1; p++) {
					ran[p] = ran[p+1];
				}
				k++;
			}
		}
		for(int i =0;i<che.length;i++){
			check[i] = che[i];
		}
	}
	
	void setCar(int no){
		int NO = M.mRand.nextInt(2)+1;
		float vx = ((M.mRand.nextInt(40))*.0005f+.006f)*(M.mRand.nextBoolean()?-1f:1f);
		float xm = (M.mRand.nextInt(400)*.01f)-2f;
		int k =0;
		for (int i = 0; i < mGR.mCar.length && k < NO; i++) {
			if(mGR.mCar[i].no <0){
				xm = xm + 1;
				if(xm > 2){
					xm = -4f + xm;
				}
				mGR.m3d_Car[i].setX(xm);
				mGR.mCar[i].set(no, vx);
				
				k++;
			}
		}
	}
	boolean wood = false;
	int setWood(int no){
		int NO = M.mRand.nextInt(3)+3;
		float vx = ((M.mRand.nextInt(40))*.0002f+.002f)*(wood?-1f:1f);
		wood = !wood;
		int k =0;
		for (int i = 0; i < mGR.mWoods.length && k < NO; i++) {
			if(mGR.mWoods[i].no <0){
				mGR.mWoods[i].set(no, vx);
				mGR.m3d_Wood[i].setX(-2+k);
				k++;
			}
		}
		if(k==0)
			return -1;
		return 0;
	}

	void setCoin(float _y) {
		if (M.mRand.nextBoolean()) {
			for (int i = 0; i < mGR.m2d_Cent.length; i++) {
				if (mGR.m2d_Cent[i].getY() < M.MIN - .1) {
					mGR.m2d_Cent[i].setPosition(-.8
							+ (M.mRand.nextInt(9) * .2f), _y, 0.1);
					break;
				}
			}
		}
	}
	void getNew(final int i,final float _y){
		int newobj =0;
		for (int j = 0; j < mGR.m3d_BGObj.length; j++) {
			newobj = mGR.getObj(mGR.type);
			if (newobj >= 0) {
				mGR.mBGVactor[i].set(mGR.type, newobj, _y);
				mGR.m3d_BGObj[mGR.type][newobj].setY(mGR.mBGVactor[i].y);
				switch (mGR.type) {
				case 0://Road
					setCoin(_y);
					setCar(i);
					break;
				case 1://Gross
					setTree(i);
					break;
				case 2://Water
					if(setWood(i) == -1){
						mGR.m3d_BGObj[mGR.type][newobj].setY(-40);
						mGR.type = 1;
						getNew(i, _y);
					}
					break;
				case 3://Train
					break;
				}
				
				break;
			} else {
				mGR.type++;
				mGR.type %= mGR.m3d_BGObj.length;
				mGR.noTime++;
			}
		}
		mGR.noTime--;
		if(mGR.noTime<0)
		{
			if (mGR.type == 1 || M.mRand.nextInt(5)==0) {
				mGR.type = M.mRand.nextInt(6);
				if(mGR.type > 3)
					mGR.type = 0;
				switch (mGR.type) {
				case 0://Road
					mGR.noTime = M.mRand.nextInt(mGR.m3d_BGObj.length) + 2;
					break;
				case 2://Water
					mGR.noTime = M.mRand.nextInt(3) + 1;
					break;
				case 3://Train
					mGR.noTime = M.mRand.nextInt(2) + 1;
					break;
				}
				
			} else {
				mGR.type = 1;
				mGR.noTime = M.mRand.nextInt(2) + 1;
			}
		}
	}
	int freeTrain(int no){
		for (int i = 0; i < mGR.mTrain.length; i++){
			if(mGR.mTrain[i].no == no){
				return -1;
			}
		}
		return no;
	}
	void setTrain(){
		int getNo[] = new int[mGR.mBGVactor.length];
		int newOne=0;
		for (int i = 0; i < mGR.mBGVactor.length; i++) {
			if (mGR.mBGVactor[i].type == 3) {
				getNo[newOne++] = i;
			}
		}
		if(newOne>0){
			newOne = M.mRand.nextInt(newOne);
			for (int i = 0; i < mGR.mTrain.length; i++) {
				if (mGR.mTrain[i].no == -1) {
					int kk = getNo[newOne];
					if(freeTrain(kk)!=-1 && M.GameScreen == M.GAMEPLAY)
						M.sound2(R.drawable.bell);
						mGR.mTrain[i].set(mGR.mBGVactor[kk].y,kk);
					break;
				}
			}
		}
	}
	int shopimg ;
	void gameLogic(){
		if(counter%100==0 && M.GameScreen == M.GAMEPLAY){
			M.sound4(R.drawable.bg);
		}
		if(counter%200==0){
			setTrain();
		}
		boolean wood = false;
		for (int i = 0; i < mGR.mWoods.length; i++) {
			if (mGR.mWoods[i].no > -1) {
				if (CircRectsOverlap(mGR.m3d_Wood[i].getX(), mGR.m3d_Wood[i].getY(), .3f, .02f, mGR.mPlayer.x, mGR.mPlayer.y, .02)) {
					mGR.mPlayer.x += mGR.mWoods[i].vx;//(float)mGR.m3d_Wood[i].getX();
					if(mGR.mPlayer.waterSound){
						mGR.mPlayer.waterSound =false;
						M.sound16(R.drawable.wooden);
					}
					if((mGR.mPlayer.x > .85||mGR.mPlayer.x <-.85)){
						mGR.mPlayer.x += mGR.mWoods[i].vx*3;
						mGR.m3d_Wood[i].setX(mGR.m3d_Wood[i].getX()+mGR.mWoods[i].vx*3);
						mGR.mPlayer.Crash =1;
					}
					wood = true;
					break;
				}
			}
		}
		
		if ((!mGR.mPlayer.water&&(mGR.mPlayer.y > mGR.mBGVactor[mGR.mPlayer.no].y-.07)&& !wood && mGR.mBGVactor[mGR.mPlayer.no].type == 2)) {
			setWater(mGR.mPlayer.x,mGR.mPlayer.y);
			mGR.mPlayer.water= true;
			M.sound7(R.drawable.dumping);
		}
		for (int i = 0; i < mGR.mTrain.length; i++) {
			if ((mGR.mTrain[i].vx > 0 && mGR.mTrain[i].x < 2) || (mGR.mTrain[i].vx < 0 && mGR.mTrain[i].x > -2)) {
				if (mGR.mTrain[i].x > -1 && mGR.mTrain[i].x < 1 && M.GameScreen == M.GAMEPLAY){
					M.sound3(R.drawable.train);
				}
				if(mGR.mPlayer.y>-.7)
					mGR.mTrain[i].y+=mGR.Spd;
				mGR.mTrain[i].x += mGR.mTrain[i].vx;
				if(mGR.mTrain[i].vx > 0 && mGR.mTrain[i].x > -M.MIN2){
					mGR.mTrain[i].no = -1;
				}
				if(mGR.mTrain[i].vx < 0 && mGR.mTrain[i].x > M.MIN2){
					mGR.mTrain[i].no = -1;
				}
			}
		}
		for(int i =0;i<mGR.mBGVactor.length;i++){
			if(mGR.mPlayer.y>-.7){
				mGR.mBGVactor[i].y +=mGR.Spd;
			}
		}
		if(mGR.mPlayer.y>-.7){
			mGR.mPlayer.y +=mGR.Spd;
		}
		for(int i =0;i<mGR.mBGVactor.length;i++){
			if (mGR.mBGVactor[i].y < M.MIN) {
				getNew(i,mGR.mBGVactor[(i == 0 ? mGR.mBGVactor.length : i) - 1].y + .268f);
			}
		}
	}
	void Draw_GamePlay(){
		mGR.setVisible(false);
		for(int i =0;i<mGR.mBGVactor.length;i++)
		{
			mGR.m3d_BGObj[mGR.mBGVactor[i].type][mGR.mBGVactor[i].no].setVisible(true);
			mGR.m3d_BGObj[mGR.mBGVactor[i].type][mGR.mBGVactor[i].no].setY(mGR.mBGVactor[i].y);
			if(mGR.mBGVactor[i].type == 3){
				mGR.m3d_Signel[mGR.mBGVactor[i].no].setVisible(true);
				mGR.m3d_Signel[mGR.mBGVactor[i].no].setY(mGR.mBGVactor[i].y-.1);
			}
		}
		
		for (int i = 0; i < mGR.mTrain.length; i++) {
			if ((mGR.mTrain[i].vx > 0 && mGR.mTrain[i].x < 2) || (mGR.mTrain[i].vx < 0 && mGR.mTrain[i].x > -2)) {
				mGR.m3d_Train[i].setVisible(true);
				mGR.m3d_Train[i].setPosition(mGR.mTrain[i].x, mGR.mTrain[i].y, 0.0);
				mGR.m2d_Tlight[i].setVisible(true);
				mGR.m2d_Tlight[i].setPosition(0, mGR.mTrain[i].y, 0.01);
				mGR.m2d_TRed[i].setVisible(true);
				mGR.m2d_TRed[i].setPosition((counter%10>4?.038f:-.038f), mGR.mTrain[i].y-.155, .3);
				mGR.m2d_TRed[i].setRotation(90, 0, 0);
				if (M.GameScreen == M.GAMEPLAY && CircRectsOverlap(mGR.m3d_Train[i].getX(), mGR.m3d_Train[i].getY(), .3f, .02f, mGR.mPlayer.x, mGR.mPlayer.y, .02)){
						mGR.mPlayer.Crash = 1;
						M.sound7(R.drawable.crash);
				}
				
			}else{
				mGR.m2d_TRed[i].setVisible(false);
				mGR.m3d_Train[i].setVisible(false);
				mGR.m2d_Tlight[i].setVisible(false);
			}
		}
		
		for(int i = 0;i<mGR.mCar.length;i++){
			if(mGR.mCar[i].no>-1){
				mGR.m3d_Car[i].setVisible(true);
				mGR.m3d_Car[i].setY(mGR.mBGVactor[mGR.mCar[i].no].y);
				mGR.m3d_Car[i].setX(mGR.m3d_Car[i].getX()+mGR.mCar[i].vx);
				if(mGR.mCar[i].vx > 0)
					mGR.m3d_Car[i].setRotZ(0);
				else
					mGR.m3d_Car[i].setRotZ(180);
				if(mGR.m3d_Car[i].getX() > 2 && mGR.mCar[i].vx > 0){
					mGR.m3d_Car[i].setX(-2);
				}if(mGR.m3d_Car[i].getX() < -2 && mGR.mCar[i].vx < 0){
						mGR.m3d_Car[i].setX(2);
				}
//				if(mGR.m3d_Car[i].getX() < -1 || mGR.m3d_Car[i].getX() > 1)
//					mGR.m3d_Car[i].setX(mGR.m3d_Car[i].getX()+mGR.mCar[i].vx*2);
				float dx =.3f;
				if(i%15==0||i%15==1){
					dx = .2f;
				}
				if(i%15>=2 && i%15<=10){
					dx = .25f;
				}
				if(i%15>=11 && i%15<=14){
					dx = .4f;
				}
				if (M.GameScreen == M.GAMEPLAY && CircRectsOverlap(mGR.m3d_Car[i].getX(), mGR.m3d_Car[i].getY(), dx, .02f, mGR.mPlayer.x, mGR.mPlayer.y, .02)){
					mGR.mPlayer.Crash = 1;
					M.sound7(R.drawable.crash);
				}
				
				if(mGR.mBGVactor[mGR.mCar[i].no].y < M.MIN2)
					mGR.mCar[i].no = -1;
			}else{
				mGR.m3d_Car[i].setVisible(false);
			}
		}
		
		for(int i = 0;i<mGR.mWoods.length;i++){
			if(mGR.mWoods[i].no>-1){
				mGR.m3d_Wood[i].setVisible(true);
				mGR.m3d_Wood[i].setY(mGR.mBGVactor[mGR.mWoods[i].no].y);
				mGR.m3d_Wood[i].setX(mGR.m3d_Wood[i].getX()+mGR.mWoods[i].vx);
				if(mGR.m3d_Wood[i].getX() > 2 && mGR.mWoods[i].vx > 0){
					mGR.m3d_Wood[i].setX(-2);
				}if(mGR.m3d_Wood[i].getX() < -2 && mGR.mWoods[i].vx < 0){
						mGR.m3d_Wood[i].setX(2);
				}
//				if(mGR.m3d_Wood[i].getX() < -1 || mGR.m3d_Wood[i].getX() > 1)
//					mGR.m3d_Wood[i].setX(mGR.m3d_Wood[i].getX()+mGR.mWoods[i].vx*2);
				
				if(mGR.mBGVactor[mGR.mWoods[i].no].y < M.MIN2)
					mGR.mWoods[i].no = -1;
			}else{
				mGR.m3d_Wood[i].setVisible(false);
			}
		}
		for (int i = 0; i < mGR.mTrees.length; i++) {
			if (mGR.mTrees[i].no > -1) {
				mGR.m3d_Tree[i].setVisible(true);
				mGR.m3d_Tree[i].setY(mGR.mBGVactor[mGR.mTrees[i].no].y);
				if (mGR.mBGVactor[mGR.mTrees[i].no].y < M.MIN2)
					mGR.mTrees[i].no = -1;
			} else {
				mGR.m3d_Tree[i].setVisible(false);
			}
		}
//		mGR.orthoCam.setRotation(-89, 0, 0);
		if (mGR.mPlayer.Crash == 2){
			mGR.m3d_Player[mGR.mPlayer.img].setPosition(mGR.m3d_Egle.getX(),mGR.m3d_Egle.getY(),mGR.mPlayer.z);
		}else  if(!mGR.mPlayer.water){
			mGR.m3d_Player[mGR.mPlayer.img].setPosition(mGR.mPlayer.x,mGR.mPlayer.y,mGR.mPlayer.z);
			float hight = .01f;
			if(mGR.mBGVactor[mGR.mPlayer.no].type == 1 || mGR.mBGVactor[mGR.mPlayer.no].type == 2)
				hight = .04f;
			
			if(mGR.mPlayer.z>hight){
				mGR.mPlayer.z+=mGR.mPlayer.vz;
				mGR.mPlayer.vz-=.02f;
			}else{
				mGR.mPlayer.z =hight;
			}
		}else{
			mGR.m3d_Player[mGR.mPlayer.img].setPosition(mGR.mPlayer.x,mGR.mPlayer.y,mGR.m3d_Player[mGR.mPlayer.img].getZ() - .02);
		}
		if(mGR.mPlayer.img == 31){
			if(counter%400 >200 && mGR.mPlayer.Crash == 0 && !mGR.mPlayer.water){
				mGR.m3d_Bat[slowCounter%3].setVisible(true);
				mGR.m3d_Bat[slowCounter%3].setPosition(mGR.mPlayer.x,mGR.mPlayer.y,mGR.mPlayer.z+.06);
			}else{
				mGR.m3d_Player[mGR.mPlayer.img].setVisible(true);
			}
		}else{
			mGR.m3d_Player[mGR.mPlayer.img].setVisible(true);
		}
		
		if (mGR.mPlayer.Crash == 1) {
			if (mGR.m3d_Player[mGR.mPlayer.img].getScaleZ() > .1)
				mGR.m3d_Player[mGR.mPlayer.img].setScale(.3, .3, mGR.m3d_Player[mGR.mPlayer.img].getScaleZ() - .03);
		} else {
			if (mGR.mPlayer.isPress) {
				if (mGR.m3d_Player[mGR.mPlayer.img].getScaleX() < .4)
					mGR.m3d_Player[mGR.mPlayer.img].setScale(
							mGR.m3d_Player[mGR.mPlayer.img].getScaleX() + .02,.3,
							mGR.m3d_Player[mGR.mPlayer.img].getScaleZ() - .02);
			} else {
				mGR.m3d_Player[mGR.mPlayer.img].setScale(.3);
			}
		}
		if(mGR.mPlayer.y < mGR.mBGVactor[mGR.mPlayer.no].y-.07){
			mGR.mPlayer.y+=.07;
			if(mGR.mPlayer.y > mGR.mBGVactor[mGR.mPlayer.no].y-.07){
				mGR.mPlayer.y = mGR.mBGVactor[mGR.mPlayer.no].y;
			}
		}
		
		if(mGR.mPlayer.vx > 0.01 || mGR.mPlayer.vx < -.01){
			if(mGR.mPlayer.vx > 0.01){
				mGR.mPlayer.vx -= .04f;
				mGR.mPlayer.x -= .04f;
			}else{
				mGR.mPlayer.vx += .04f;
				mGR.mPlayer.x += .04f;
				if(mGR.mPlayer.vx > 0.00){
					mGR.mPlayer.x -= mGR.mPlayer.x%.2f;
				}
			}
			
		}//abcdef
		
		
		for(int i = 0;i<mGR.m3d_Drop.length;i++){
			if(mGR.m3d_Drop[i].getZ() > -.5){
				mGR.m3d_Drop[i].setVisible(true);
				mGR.m3d_Drop[i].setZ(mGR.m3d_Drop[i].getZ()+mGR.mWater[i].vz);
				mGR.mWater[i].vz -=.001f;
				if(mGR.mPlayer.y>-.7){
					mGR.m3d_Drop[i].setY(mGR.m3d_Drop[i].getY()+mGR.Spd);
				}
			}else{
				mGR.m3d_Drop[i].setVisible(false);
			}
		}
//		M.GameScreen = M.GAMEGIFT;
		if (M.GameScreen == M.GAMEGIFT) {
			if (mGR.giftMove >= 50) {
				if (mGR.giftMove == 50) {
					for (int i = 0; i < mGR.m2d_Cent.length; i++) {
						mGR.m2d_Cent[i].setPosition(0.13,-0.3, .4);
						mGR.m2d_Cent[i].setScale(1);
						mGR.mCent[i].reset();
					}
				}
				mGR.giftMove++;
				for (int i = 0; i < mGR.m2d_Cent.length; i++) {
					mGR.m2d_Cent[i].setVisible(true);
					mGR.m2d_Cent[i].setPosition(mGR.m2d_Cent[i].getX()
							+ mGR.mCent[i].vx, mGR.m2d_Cent[i].getY()
							+ mGR.mCent[i].vy, mGR.m2d_Cent[i].getZ());
					mGR.mCent[i].update();
				}
				mGR.oFree.setPosition(1.55, -2.87, 3.01);
				mGR.oFree.setVisible(true);
				
				if (mGR.giftMove >= 150){

					M.GameScreen = M.GAMEMENU;
					mGR.m2d_Crosy.setX(-1.5f);
					mGR.gameReset(false);
				
				}
				
			} else {
				mGR.m2d_Tran.setVisible(true);
				if (mGR.giftMove == 0) {
					mGR.m3d_Gift.setPosition(1.13, -3.3, 4);
				}
				if (mGR.giftMove > 0 && mGR.giftMove < 50) {
					mGR.m3d_Gift.setPosition(1.13 + M.mRand.nextInt(5) * .01,
							M.mRand.nextInt(5) * .01 - 3.3, 4);
					mGR.giftMove++;
				}

				mGR.m3d_Gift.setScale(.075);
				mGR.m3d_Gift.setRotZ(28);
				mGR.m3d_Gift.setRotation(0, 0, counter * 3);
				mGR.m3d_Gift.setVisible(true);

//				mGR.m2d_Val.setVisible(true);
//				mGR.m2d_Val.setPosition(1.36, -3.82, 3);
//				mGR.m2d_Val.setScale(sel == 1 ? .55 : .5);
			}
		}
		
		
		if(M.GameScreen == M.GAMENEWCHAR){
			mGR.m2d_Tran.setVisible(true);
			
			mGR.m3d_Mashin.setPosition(1.13, -3.3, 2.99);
			mGR.m3d_Mashin.setScale(.075);
			mGR.m3d_Mashin.setRotZ(28);
			mGR.m3d_Mashin.setRotation(20,9,31);
			mGR.m3d_Mashin.setVisible(true);
			
			mGR.m3d_Trance.setPosition(1.13, -3.3, 2.99);
			mGR.m3d_Trance.setScale(.075);
			mGR.m3d_Trance.setRotZ(28);
			mGR.m3d_Trance.setRotation(20,9,31);
			mGR.m3d_Trance.setVisible(true);

			mGR.m3d_GiftBox[0].setPosition(1.13-.32+M.mRand.nextInt(5)*.01, -3.3+.40+M.mRand.nextInt(5)*.01, 3.8);
			mGR.m3d_GiftBox[1].setPosition(1.13+.00+M.mRand.nextInt(5)*.01, -3.3+.45+M.mRand.nextInt(5)*.01, 3.8);
			mGR.m3d_GiftBox[2].setPosition(1.13-.09+M.mRand.nextInt(5)*.01, -3.3+.26+M.mRand.nextInt(5)*.01, 3.81);
			
			for(int i = 0 ;i<mGR.m3d_GiftBox.length;i++){
				if(i!=3)
					mGR.m3d_GiftBox[i].setScale(.075);
				mGR.m3d_GiftBox[i].setRotZ(28);
				mGR.m3d_GiftBox[i].setRotation(20,9,31);
				mGR.m3d_GiftBox[i].setVisible(true);
			}
			if(mGR.giftMove == 0){
				mGR.m3d_GiftBox[3].setPosition(1.13, -3.33, 3.33);
				mGR.m3d_GiftBox[3].setScale(.05);
			}
			if(mGR.giftMove == 1){
				if(mGR.m3d_GiftBox[3].getX() < 1.55)
					mGR.m3d_GiftBox[3].setX(mGR.m3d_GiftBox[3].getX()+.02);
				else
					mGR.giftMove = 2;
			}
			if (mGR.giftMove == 2) {
				if (mGR.m3d_GiftBox[3].getZ() < 4.0) {
					if(mGR.m3d_GiftBox[3].getY() >-3.54)
						mGR.m3d_GiftBox[3].setY(mGR.m3d_GiftBox[3].getY() - .02);
					if (mGR.m3d_GiftBox[3].getX() > 1.21)
						mGR.m3d_GiftBox[3].setX(mGR.m3d_GiftBox[3].getX() - .02);
					mGR.m3d_GiftBox[3].setZ(mGR.m3d_GiftBox[3].getZ() + .05);
					mGR.m3d_GiftBox[3].setScale(mGR.m3d_GiftBox[3].getScaleX() + .003);
				}else
					mGR.giftMove = 3;
			}
			if(mGR.giftMove > 2 && mGR.giftMove < 50){
//				if(mGR.giftMove == 3)
//					M.sound11(R.drawable.new_object);
				mGR.giftMove++;
				mGR.m3d_GiftBox[3].setRotZ(counter*10);
				mGR.m3d_GiftBox[3].setY(-3.54);
			}
			if(mGR.giftMove == 50){
			{
					for(int i = 0 ;i<mGR.m3d_GiftBox.length;i++){
						mGR.m3d_GiftBox[i].setVisible(false);
					}
					mGR.m3d_Trance.setVisible(false);
					mGR.m3d_Mashin.setVisible(false);
					mGR.m3d_Player[mGR.mPlayer.buyChar].setVisible(true);
					mGR.m3d_Player[mGR.mPlayer.buyChar].setPosition(1.0, -2.97, 3.33);
					mGR.m3d_Player[mGR.mPlayer.buyChar].setScale(.5);
					mGR.m3d_Player[mGR.mPlayer.buyChar].setRotation(24, 0, counter*4);
				}
			}
			if(mGR.giftMove < 5){
				mGR.m2d_100.setVisible(true);
				mGR.m2d_100.setPosition(1.36, -3.82, 3);
				mGR.m2d_100.setScale(sel == 1?.55:.5);
			}
		}
		
		if(M.GameScreen == M.GAMESHOP){
			
			mGR.m2d_Tran.setVisible(true);
			
			if((mov+mGR.move)>0.25){
				mov =0;
				mGR.move =0.25f;
			}
			if((mov+mGR.move)<-5.14){
				mov =0;
				mGR.move =-5.14f;
			}
			double mx;
			double my;
			shopimg = (int) (-(mov + mGR.move) * 6.3);

			float dist = .18f;
			mx = Math.cos(.3);
			my = Math.sin(.3);
			mGR.m3d_Cube.setPosition(.53+mx*dist*(-1)+(mov+mGR.move), -2.4+my*dist*(-1)+(mov+mGR.move)*.32, 3.1);
			mGR.m3d_Cube.setVisible(true);
			if(shopimg == -1){
				mGR.m3d_Cube.setRotZ(counter*3);
				mGR.m3d_Cube.setScale(.06f);
			}else{
				mGR.m3d_Cube.setScale(.03f);
				mGR.m3d_Cube.setRotZ(0);
			}
			
			for(int i =0;i<mGR.m3d_Player.length;i++){
				
				if(shopimg == i){
//					System.out.println((shopimg+1)+"  ~~~~~~~~~~~~~~~~~~~~~~~"+(mov+mGR.move)+"~~~~~~~~~~~~~~~~~~~~~  "+shopimg);
					mGR.m3d_Player[i].setPosition(.53+mx*dist*i+(mov+mGR.move), -2.4+my*dist*i+(mov+mGR.move)*.32, 3.1);
					mGR.m3d_Player[i].setVisible(true);
					mGR.m3d_Player[i].setScale(.4);
					mGR.m3d_Player[i].setRotZ(counter*3);
				}else{
					mGR.m3d_Player[i].setPosition(.53+mx*dist*i+(mov+mGR.move), -2.4+my*dist*i+(mov+mGR.move)*.32, 3);
					mGR.m3d_Player[i].setVisible(true);
					mGR.m3d_Player[i].setScale(.15);
					mGR.m3d_Player[i].setRotZ(0);
				}
			}
			
			if(shopimg ==-1){
				mGR.m2d_Play.setPosition(1.06, -3.07, 3);
				mGR.m2d_Play.setVisible(true);
				mGR.m2d_Play.setScale(sel == 4?.55:.5);
			}
			
			if(shopimg >= 0 && shopimg < mGR.m3d_Player.length){
				if(mGR.isbuyPlayer[shopimg]){
					mGR.m2d_Play.setPosition(1.06, -3.07, 3);
					mGR.m2d_Play.setVisible(true);
					mGR.m2d_Play.setScale(sel == 4?.55:.5);
				}else{
					mGR.oFree.setPosition(1.75, -2.87, 3.01);
					mGR.oFree.setVisible(true);
					mGR.m2d_Val.setPosition(1.06, -3.07, 3);
					mGR.m2d_Val.setVisible(true);
					mGR.m2d_Val.setScale(sel == 4?.55:.5);
				}
			}
			
			mGR.m2d_Back.setPosition(0, -1.59, 3);
			mGR.m2d_Back.setVisible(true);
			mGR.m2d_Back.setScale(sel == 5?.55:.5);
			
			mGR.oCoin.setPosition(1.13, -1.55, 3.01);
			mGR.oCoin.setVisible(true);
			
			//gameplay
			mGR.m2d_share.setPosition(0.85f, -4.05, 3);
			mGR.m2d_share.setVisible(true);
			mGR.m2d_share.setScale(sel == 1?.55:.5);
			
			mGR.m2d_Google.setPosition(1.59, -3.79, 3);
			mGR.m2d_Google.setVisible(true);
			mGR.m2d_Google.setScale(sel == 2?.55:.5);
			
			mGR.m2d_facebook.setPosition(1.88, -3.68, 3);
			mGR.m2d_facebook.setVisible(true);
			mGR.m2d_facebook.setScale(sel == 3?.55:.5);
			
			
		}
		
		
		mGR.oScore.setPosition(mGR.orthoCam.getX()*1.03+.72, mGR.orthoCam.getX()*.36-1.22, 3);
		mGR.oScore.setRotation(180, 0, 200);
		mGR.oScore.setVisible(true);

		
		
		if(M.GameScreen == M.GAMEMENU){
		//gameplay
			mGR.m3d_Player[mGR.mPlayer.img].setRotZ(180);
			mGR.m2d_LBoard.setPosition(1.59, -3.79, 3);
			mGR.m2d_LBoard.setVisible(true);
			
			mGR.m2d_Achiv.setPosition(1.88, -3.68, 3);
			mGR.m2d_Achiv.setVisible(true);
			
			mGR.m2d_char.setPosition(0.85f, -4.05, 3);
			mGR.m2d_char.setVisible(true);
			
			if(mGR.m2d_Crosy.getX()<0.86){
				mGR.m2d_Crosy.setPosition(mGR.m2d_Crosy.getX()+.2, -2.49f, 3);
			}else
				mGR.m2d_Crosy.setPosition(0.86f,  -2.49f,  3.0f);
			
//			mGR.m2d_Crosy.setPosition(0.86f,  -2.49f,  3.0f);
			
			mGR.m2d_Crosy.setVisible(true);
			mGR.m2d_Hand[slowCounter%4 < 2?0:1].setVisible(true);
			
		}
		if(M.GameScreen ==M.GAMEPAUSE){
			mGR.m2d_Crosy.setVisible(true);
			mGR.m2d_Hand[slowCounter%4 < 2?0:1].setVisible(true);
		}
		
		if(M.GameScreen == M.GAMEOVER){
			//gameover
			mGR.m2d_DEPati.setPosition(0,-1.25,2.9);
			mGR.m2d_DEPati.setVisible(true);
			
			mGR.m2d_Sound[0].setVisible(M.setValue);
			mGR.m2d_Sound[0].setPosition(0.85f, -4.05, 3);
			mGR.m2d_Sound[0].setScale(sel == 1?.55:.5);
			
			mGR.m2d_Sound[1].setPosition(0.85f, -4.05, 3);
			mGR.m2d_Sound[1].setVisible(!M.setValue);
			mGR.m2d_Sound[1].setScale(sel == 1?.55:.5);
			
			mGR.m2d_Play.setPosition(1.13, -3.95, 3);
			mGR.m2d_Play.setVisible(true);
			mGR.m2d_Play.setScale(sel == 2?.55:.5);
			
			mGR.m2d_Achiv.setPosition(1.59, -3.79, 3);
			mGR.m2d_Achiv.setVisible(true);
			mGR.m2d_Achiv.setScale(sel == 3?.55:.5);
			
			mGR.m2d_LBoard.setPosition(1.88, -3.68, 3);
			mGR.m2d_LBoard.setVisible(true);
			mGR.m2d_LBoard.setScale(sel == 4?.55:.5);
			
			if (HTTRenderer.mStart.isVideoAvail) 
			{
				mGR.m2d_EPati.setPosition(1.2, -3.38, 3);
				mGR.m2d_EPati.setVisible(true);
				mGR.m2d_Ebutn.setPosition(1.6, -3.23, 3);
				mGR.m2d_Ebutn.setVisible(true);
				mGR.m2d_Ebutn.setScale(sel == 9 ? 1.5 : 1.4);
				
				mGR.oExtra.setPosition(1.28, -3.51, 3);
				mGR.oExtra.setVisible(true);
				mGR.oExtra.setScale(.64);
				
			}
			switch (mGR.giftNo) {
			case 0:
				if(mGR.mPlayer.buyChar > 0){
					if(mGR.m2d_RPati.getY()>-2.28){
						mGR.m2d_RPati.setPosition(.73, mGR.m2d_RPati.getY()-.2, 3);
					}else
						mGR.m2d_RPati.setPosition(.73, -2.28, 3);
					mGR.m2d_RPati.setScale(1.21);
					mGR.m2d_RPati.setRotZ(200);
					mGR.m2d_RPati.setVisible(true);
					
					mGR.oTime.setVisible(true);
					if(mGR.cent > 99){
						mGR.oTime.setPosition(1.07, mGR.m2d_RPati.getY()+0.09, 3);
						mGR.m2d_Win.setPosition(1.17,mGR.m2d_RPati.getY()+0.16, 3);
						mGR.m2d_Win.setVisible(true);
						mGR.m2d_Win.setScale(sel == 6?.58:.5);
					}else{
						mGR.oTime.setPosition(1.2, mGR.m2d_RPati.getY()+.13, 3);
						mGR.m2d_Cent[0].setPosition(.31,-2.4, 3);
						mGR.m2d_Cent[0].setRotZ(200);
						mGR.m2d_Cent[0].setVisible(true);
					}
				}
				
				if(mGR.m2d_Bpati.getY()>-2.6){
					mGR.m2d_Bpati.setPosition(.92, mGR.m2d_Bpati.getY()-0.2, 3);
				}else
					mGR.m2d_Bpati.setPosition(.92, -2.6, 3);
				mGR.m2d_Bpati.setScale(1.21);
				mGR.m2d_Bpati.setVisible(true);
				
				mGR.oFree.setPosition(1.15, mGR.m2d_Bpati.getY()+0.04, 3);
				mGR.oFree.setVisible(true);

				if((System.currentTimeMillis() - mGR.time)> 21600000){
					mGR.m2d_Gift.setPosition(1.3, mGR.m2d_Bpati.getY()+0.13, 3);
					mGR.m2d_Gift.setScale(sel == 7?.58:.5);
					mGR.m2d_Gift.setVisible(true);
				}
				
				
				break;
			case 1:
//				mGR.m2d_Bpati.setPosition(.92, -2.6, 3);
				if(mGR.m2d_Bpati.getY()>-2.6){
					mGR.m2d_Bpati.setPosition(.92, mGR.m2d_Bpati.getY()-0.2, 3);
				}else
					mGR.m2d_Bpati.setPosition(.92, -2.6, 3);
				mGR.m2d_Bpati.setScale(1.21);
				mGR.m2d_Bpati.setVisible(true);
				
				mGR.oFree.setPosition(1.15, mGR.m2d_Bpati.getY()+0.04, 3.01);
				mGR.oFree.setVisible(true);
				mGR.m2d_Val.setPosition(1.3, mGR.m2d_Bpati.getY()+0.13, 3);
				mGR.m2d_Val.setVisible(true);
				mGR.m2d_Val.setScale(sel == 8?.58:.5);
				
				if(mGR.m2d_SPati.getY()>-3)
					mGR.m2d_SPati.setPosition(.99,mGR.m2d_SPati.getY()-.2, 3);
				else
					mGR.m2d_SPati.setPosition(.99,-3, 3);
				mGR.m2d_SPati .setScale(1.21);
				mGR.m2d_SPati.setVisible(true);
				
				mGR.oCoin.setPosition(1.24,mGR.m2d_SPati.getY()-0.04, 3.01);
				mGR.oCoin.setVisible(true);
				
				
				break;
			case 2:
				if(mGR.mPlayer.buyChar == -1){
					mGR.giftNo = 100;
					return;
				}
				if(mGR.m2d_RPati.getY()>-2.28){
					mGR.m2d_RPati.setPosition(.73, mGR.m2d_RPati.getY()-.2, 3);
				}else
					mGR.m2d_RPati.setPosition(.73, -2.28, 3);
				
//				mGR.m2d_RPati.setPosition(.73, -2.28, 3);
				mGR.m2d_RPati.setScale(1.21,2.5,1.21);
				mGR.m2d_RPati.setRotZ(9.7);
				mGR.m2d_RPati.setVisible(true);
				
				mGR.m3d_Player[mGR.mPlayer.buyChar].setPosition(1.23, mGR.m2d_RPati.getY()+0.08, 3);
				mGR.m3d_Player[mGR.mPlayer.buyChar].setVisible(true);
				mGR.m3d_Player[mGR.mPlayer.buyChar].setRotZ(counter);
				mGR.m3d_Player[mGR.mPlayer.buyChar].setScale(.4);
				
				
				mGR.oTime.setPosition(1.13, mGR.m2d_RPati.getY()+0.25, 3);
				mGR.oTime.setVisible(true);
				mGR.m2d_Val.setPosition(0.7, mGR.m2d_RPati.getY()+-0.14, 3);
				mGR.m2d_Val.setScale(sel == 5?.58:.5);
				mGR.m2d_Val.setVisible(true);
				
//				mGR.m2d_SPati.setPosition(.99,-3, 3);
				if(mGR.m2d_SPati.getY()>-3)
					mGR.m2d_SPati.setPosition(.99,mGR.m2d_SPati.getY()-.2, 3);
				else
					mGR.m2d_SPati.setPosition(.99,-3, 3);
				mGR.m2d_SPati .setScale(1.21);
				mGR.m2d_SPati.setVisible(true);
				
				mGR.oCoin.setPosition(1.37, mGR.m2d_SPati.getY(), 3);
				mGR.oCoin.setVisible(true);
				
				break;
			}
			
			
	//		mGR.oFree.setPosition(1.15, -2.56, 3);
	//		mGR.m2d_Gift.setPosition(1.3, -2.47, 3);
			
//			mGR.oFree.setPosition(1.15, -2.56, 3);
//			mGR.m2d_Val.setPosition(1.3, -2.47, 3);
			
			
			
//		mGR.oTime.setRotation(sx*100, sy*100, sz*100);
		
		}
		if(M.GameScreen == M.GAMEPLAY){
			if(counter%200<100 && mGR.mPlayer.img == 9 && mGR.m3d_Player[mGR.mPlayer.img].getRotZ() == 0)
			{
				for (int i = 0; i < mGR.m2d_Fire.length ; i++) {
					if(mGR.m2d_Fire[i].getScaleZ()<=0){
						mGR.m2d_Fire[i].setPosition(mGR.mPlayer.x, mGR.mPlayer.y+0.08, mGR.mPlayer.z+.13);
						mGR.mFire[i].set((M.mRand.nextInt(30)-14)*.001f);
						mGR.m2d_Fire[i].setScale(1);
						break;
					}
				}
				
				for (int i = 0; i < mGR.m2d_Fire.length; i++) {
					if(mGR.m2d_Fire[i].getScaleZ()>0){
						mGR.m2d_Fire[i].setPosition(mGR.m2d_Fire[i].getX()+mGR.mFire[i].vx, mGR.m2d_Fire[i].getY()+.04, mGR.m2d_Fire[i].getZ());
						mGR.m2d_Fire[i].setScale(mGR.m2d_Fire[i].getScaleZ()-.05);
						mGR.m2d_Fire[i].setVisible(true);
					}
				}
			}
			if (counter % 200 < 100 && mGR.mPlayer.img == 7
					&& mGR.m3d_Player[mGR.mPlayer.img].getRotZ() == 0) {
				for (int i = 0, k = 0; i < mGR.m2d_Tears.length && k < 2
						&& counter % 5 == 0; i++) {
					if (mGR.m2d_Tears[i].getScaleZ() <= 0
							|| mGR.m2d_Tears[i].getZ() < .1) {
						mGR.m2d_Tears[i].setPosition(mGR.mPlayer.x,
								mGR.mPlayer.y, mGR.mPlayer.z + .28);
						mGR.mTears[i].set(k == 0 ? -.01f : .01f);
						mGR.m2d_Tears[i].setScale(1);
						k++;
					}
				}
				for (int i = 0; i < mGR.m2d_Tears.length; i++) {
					if (mGR.m2d_Tears[i].getScaleZ() > 0) {
						mGR.m2d_Tears[i].setPosition(mGR.m2d_Tears[i].getX()
								+ mGR.mTears[i].vx, mGR.m2d_Tears[i].getY(),
								mGR.m2d_Tears[i].getZ() + mGR.mTears[i].vz);
						mGR.mTears[i].vz -= .006f;
						mGR.m2d_Tears[i]
								.setScale(mGR.m2d_Tears[i].getScaleZ() - .04);
						mGR.m2d_Tears[i].setVisible(true);
					}
				}
			}
			if(counter%200<100 && mGR.mPlayer.img == 27)
			{
				for (int i = 0; i < mGR.m2d_Sleeping.length && counter%20==0; i++) {
					if(mGR.m2d_Sleeping[i].getScaleZ()<=0){
						mGR.m2d_Sleeping[i].setPosition(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.z+.3);
						mGR.m2d_Sleeping[i].setScale(1);
						break;
					}
				}
				
				for (int i = 0; i < mGR.m2d_Sleeping.length; i++) {
					if(mGR.m2d_Sleeping[i].getScaleZ()>0){
						mGR.m2d_Sleeping[i].setPosition(mGR.m2d_Sleeping[i].getX(), mGR.m2d_Sleeping[i].getY(), mGR.m2d_Sleeping[i].getZ()+.01);
						mGR.m2d_Sleeping[i].setScale(mGR.m2d_Sleeping[i].getScaleZ()-.01);
						mGR.m2d_Sleeping[i].setVisible(true);
					}
				}
			}
			
			
			
			
			for (int i = 0; i < mGR.m2d_Cent.length; i++) {
				if (mGR.m2d_Cent[i].getY() >= M.MIN) {
					if ((mGR.mPlayer.y > -.7))
						mGR.m2d_Cent[i].setY(mGR.m2d_Cent[i].getY() + mGR.Spd);
					mGR.m2d_Cent[i].setVisible(true);
					if (CircRectsOverlap(mGR.m2d_Cent[i].getX(),
							mGR.m2d_Cent[i].getY(), .05f, .1f, mGR.mPlayer.x,
							mGR.mPlayer.y, .1)) {
						{
							M.sound6(R.drawable.coincollect);
							mGR.m2d_Cent[i].setY(-100);
							mGR.cent++;
							if(mGR.isdpublecent)
								mGR.cent += M.mRand.nextInt(3);
						}
					}
				}
			}
			
			
			if(mGR.mPlayer.water || mGR.mPlayer.Crash > 0 ){
				mGR.mPlayer.died++;
				if(mGR.mPlayer.died > 20){
					M.GameScreen = M.GAMEOVER;
//					M.sound13(R.drawable.offers);
					HTTRenderer.mStart.Achivment();
//					if(HTTRenderer.mStart.isVideoAvail)
//						HTTRenderer.mStart.ShowVideoHandler();
//			    	else
			    		HTTRenderer.mStart.ShowInterstitial();
				}
			}
			if(mGR.orthoCam.getX() < mGR.mPlayer.x*.6f-.02){
				mGR.orthoCam.setX(mGR.orthoCam.getX()+.005f);
			}
			if(mGR.orthoCam.getX() > mGR.mPlayer.x*.6f+.02){
				mGR.orthoCam.setX(mGR.orthoCam.getX()-.005f);
			}
			if(mGR.mPlayer.isEgle){
				mGR.m3d_Egle.setVisible(true);
				mGR.m3d_Egle.setPosition(mGR.m3d_Egle.getX(), mGR.m3d_Egle.getY()+mGR.mEgle.vy, mGR.m3d_Egle.getZ()+mGR.mEgle.vz);
				mGR.m3d_Egle.setScale(mGR.m3d_Egle.getScaleZ()+mGR.mEgle.vx);
				if(mGR.m3d_Egle.getZ()<0.2){
					mGR.mEgle.vz = 0;
					mGR.mEgle.vy = -.1f;
					mGR.mPlayer.Crash = 2;
				}
				if(mGR.m3d_Egle.getScaleZ()<.1){
					mGR.mEgle.vx =0;
				}
//				if(mGR.m3d_Egle.getY()<-4){
//					mGR.mPlayer.Crash = 2;
//				}
			}
			if(mGR.mPlayer.time+5000 < System.currentTimeMillis()){
				if(!mGR.mPlayer.isEgle){
					mGR.m3d_Egle.setPosition(mGR.mPlayer.x,-0.5, 7);
					mGR.m3d_Egle.setScale(.3f);
					mGR.mEgle.reset();
					mGR.mPlayer.isEgle = true;
					M.sound8(R.drawable.eagle);
				}
			}
			if (counter % 200 == 0 && mGR.mPlayer.img != 30)
				M.sound1(M.ANIMAL[mGR.mPlayer.img%M.ANIMAL.length]);
		}else{
			if(mGR.orthoCam.getX() < -.01){
				mGR.orthoCam.setX(mGR.orthoCam.getX()+.005f);
				if(mGR.orthoCam.getX() < -.01){
					mGR.orthoCam.setX(0);
				}
			}
			if(mGR.orthoCam.getX() > 0.01){
				mGR.orthoCam.setX(mGR.orthoCam.getX()-.005f);
				if(mGR.orthoCam.getX() > 0.01){
					mGR.orthoCam.setX(0);
				}
			}
		}
		
		gameLogic();
	}
	float DirX;
	float DirY;
	
	public boolean Handle_Gameplay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			DirX = screen2worldX(event.getX());
			DirY = screen2worldY(event.getY());
			mGR.mPlayer.isPress = true;
			if(M.GameScreen == M.GAMEPAUSE){
				mGR.mPlayer.time = System.currentTimeMillis();
				M.GameScreen =M.GAMEPLAY;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && !mGR.mPlayer.isEgle  && !mGR.mPlayer.water && mGR.mPlayer.Crash == 0) {
			mGR.mPlayer.isPress = false;
			if(DirY>screen2worldY(event.getY())+.2){
				int val = mGR.mPlayer.no - 1;
				if(val < 0)
					val = mGR.mBGVactor.length-1;
				boolean down = false;
				for (int i = 0; i < mGR.mTrees.length && !down; i++){
					if (mGR.mTrees[i].no == val) {
						if (CircRectsOverlap(mGR.m3d_Tree[i].getX(), mGR.mPlayer.y, .1f, .02f, mGR.mPlayer.x, mGR.mPlayer.y, .02)) {
							down = true;
						}
					} 
				}
				if(!down){
					if(mGR.mScore > 0)
						mGR.mScore--;
					int ld = mGR.mPlayer.no; 
					mGR.mPlayer.no =val;
					mGR.mPlayer.y=mGR.mBGVactor[mGR.mPlayer.no].y;
					if(mGR.mBGVactor[ld].type == 2 && mGR.mBGVactor[mGR.mPlayer.no].type != 2){
						if(Math.abs(mGR.mPlayer.x%.2f) > .1)
							mGR.mPlayer.x+=.2f*(mGR.mPlayer.x>0?1:-1);
						mGR.mPlayer.x -= mGR.mPlayer.x%.2f;
					}
					mGR.m3d_Player[mGR.mPlayer.img].setRotZ(180);
					M.sound14(R.drawable.tab);
				}
				
			}
			else if(DirX>screen2worldX(event.getX())+.1){
				boolean Right = false;
				for (int i = 0; i < mGR.mTrees.length && !Right; i++){
					if (mGR.mTrees[i].no == mGR.mPlayer.no) {
						if (CircRectsOverlap(mGR.m3d_Tree[i].getX(), mGR.mPlayer.y, .05f, .02f, mGR.mPlayer.x-.2, mGR.mPlayer.y, .02)) {
							Right = true;
						}
					} 
				}
				if(!Right)
					if(mGR.mPlayer.x>-.8){
//						mGR.mPlayer.x-=.2f;//Right
						mGR.mPlayer.vx += .2f;
						mGR.mPlayer.z = .05f;
						mGR.mPlayer.vz=.04f;
						mGR.m3d_Player[mGR.mPlayer.img].setRotZ(-90);
						mGR.mPlayer.time = System.currentTimeMillis();
						M.sound14(R.drawable.tab);
					}
			}
			else if(DirX<screen2worldX(event.getX())-.1){
				boolean Left = false;
				for (int i = 0; i < mGR.mTrees.length && !Left; i++){
					if (mGR.mTrees[i].no == mGR.mPlayer.no) {
						if (CircRectsOverlap(mGR.m3d_Tree[i].getX(), mGR.mPlayer.y, .05f, .02f, mGR.mPlayer.x+.2, mGR.mPlayer.y, .02)) {
							Left = true;
						}
					} 
				}
				if(!Left)
					if(mGR.mPlayer.x<.8){
//						mGR.mPlayer.x+=.2f;//Right
						mGR.mPlayer.vx -= .2f;
						mGR.mPlayer.z = .05f;
						mGR.mPlayer.vz=.04f;
						mGR.m3d_Player[mGR.mPlayer.img].setRotZ(90);
						mGR.mPlayer.time = System.currentTimeMillis();
						M.sound14(R.drawable.tab);
					}
//				mGR.mPlayer.x+=.2f;//Left
			}else{
				int val = mGR.mPlayer.no + 1;
				val %= mGR.mBGVactor.length;
				boolean up = false;
				for (int i = 0; i < mGR.mTrees.length && !up; i++){
					if (mGR.mTrees[i].no == val) {
						if (CircRectsOverlap(mGR.m3d_Tree[i].getX(), mGR.mPlayer.y, .1f, .02f, mGR.mPlayer.x, mGR.mPlayer.y, .02)) {
							up = true;
						}
					} 
				}
				if(!up){
					mGR.mScore++;
					if(mGR.mHScore< mGR.mScore)
						mGR.mHScore=mGR.mScore;
					int ld = mGR.mPlayer.no; 
					mGR.mPlayer.no++;
					mGR.mPlayer.no%=mGR.mBGVactor.length;
					mGR.mPlayer.waterSound = true;
//					if(mGR.mPlayer.no==1||mGR.mPlayer.no==2)
//						 mGR.mPlayer.z = .04f;
//					else
//						mGR.mPlayer.z = .02f;
					mGR.mPlayer.z = .05f;
					mGR.mPlayer.vz=.04f;
					
//					mGR.mPlayer.y = mGR.mBGVactor[mGR.mPlayer.no].y;
					if(mGR.mBGVactor[ld].type == 2 && mGR.mBGVactor[mGR.mPlayer.no].type != 2){
						if(Math.abs(mGR.mPlayer.x%.2f) > .1)
							mGR.mPlayer.x+=.2f*(mGR.mPlayer.x>0?1:-1);
						mGR.mPlayer.x -= mGR.mPlayer.x%.2f;
					}
					mGR.m3d_Player[mGR.mPlayer.img].setRotZ(0);
					mGR.mPlayer.time = System.currentTimeMillis();
					M.sound14(R.drawable.tab);
				}
			}
		}
		return true;
	}
	public boolean Handle_Gift(MotionEvent event) {
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+"   "+screen2worldY(event.getY()));
//		if(CirCir(0.05f,-0.80, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;	//100
//		}
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (sel) {
			case 1://100
				if(mGR.giftMove == 0){
					M.sound9(R.drawable.giftblast);
					mGR.time = System.currentTimeMillis();
					mGR.isbuyPlayer[mGR.mPlayer.buyChar] = true;
					mGR.giftMove = 1;
					mGR.FreeCoin =80 + M.mRand.nextInt(100);
					mGR.cent +=mGR.FreeCoin;
				}
				break;
			}
			if(mGR.giftMove>100){
				M.GameScreen = M.GAMEMENU;
				mGR.m2d_Crosy.setX(-1.5f);
				mGR.gameReset(false);
			}
			if(sel !=0)
				M.sound5(R.drawable.button_click);
			sel = 0;
		
		}
		return true;
	}
	public boolean Handle_NewChar(MotionEvent event) {
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+"   "+screen2worldY(event.getY()));
		if(CirCir(0.05f,-0.80, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;	//100
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (sel) {
			case 1://100
				if(mGR.giftMove == 0){
					if(mGR.mPlayer.buyChar >= 0 && mGR.mPlayer.buyChar< mGR.isbuyPlayer.length)
						mGR.isbuyPlayer[mGR.mPlayer.buyChar] = true;
					mGR.cent -=100;
					mGR.giftMove = 1;
					M.sound10(R.drawable.jackpot);
				}
				break;
			}
			if(mGR.giftMove > 48){
				mGR.gameReset(false);
				M.GameScreen = M.GAMEMENU;
				mGR.m2d_Crosy.setX(-1.5f);
			}
			if(sel !=0)
				M.sound5(R.drawable.button_click);
			sel = 0;
		
		}
		return true;
	}
	float mov =0;
	public boolean Handle_Shop(MotionEvent event) {
		sel = 0;
//		System.out.println(screen2worldX(event.getX())+"   "+screen2worldY(event.getY()));
		if(CirCir(-.43f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;//Share
		}
		if(CirCir(.22f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;//Google+
		}
		if(CirCir(0.50,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;//Facebook
		}
		if(CirCir(0.02f,-0.3, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 4;//Play
		}
		if(CirCir(-.43f,0.77, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 5;//Back
		}
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			DirX = screen2worldX(event.getX());
		}
		if(MotionEvent.ACTION_MOVE == event.getAction()){
			int img = (int) (-(mov + mGR.move) * 6.3);
			mov = DirX - screen2worldX(event.getX());
			int img1 = (int) (-(mov + mGR.move) * 6.3);
			if(img1!=img)
				M.sound12(R.drawable.coincollect);
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.move += DirX - screen2worldX(event.getX());
			mov =0;
			switch (sel) {
			case 1://Share
				shareImage();
				break;
			case 2: //Google+
				google();
				break;
			case 3: //Facebook
				facebook();
				break;
			case 4: // Img Sel
				int img = (int) (-(mov + mGR.move) * 6.3);
				if(img == -1){
					mGR.mPlayer.random =true;
					M.GameScreen = M.GAMEMENU;
					mGR.m2d_Crosy.setX(-1.5f);
					M.animalStop();
				}
				if(img >= 0 && img < mGR.m3d_Player.length){
					if(mGR.isbuyPlayer[img]){
						mGR.mPlayer.random =false;
						mGR.mPlayer.img = img;
						M.GameScreen = M.GAMEMENU;
						mGR.m2d_Crosy.setX(-1.5f);
						M.animalStop();
					}else{
						mGR.mPlayer.buyChar = img;
						mGR.mInApp.onBuyCharecter(null);
					}
				}
				break;
			case 5:
				M.GameScreen = M.GAMEMENU;
				mGR.m2d_Crosy.setX(-1.5f);
				break;
			}
			if(sel !=0)
				M.sound5(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	public boolean Handle_Menu(MotionEvent event) {
		sel = 0;
		if(CirCir(-.43f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;	//Character
		}
		if(CirCir(.22f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;//Leader board
		}
		if(CirCir(0.50,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3;//Achievement
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (sel) {
			case 1:
				mGR.mPlayer.x = 0;
				M.GameScreen = M.GAMESHOP;
				break;
			case 2: // Leader Board
				HTTRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 3: // Achievement
				HTTRenderer.mStart.onShowAchievementsRequested();
				break;
			default: // GameStart
			{
				mGR.mScore++;
				if(mGR.mHScore< mGR.mScore)
					mGR.mHScore=mGR.mScore;
				int ld = mGR.mPlayer.no; 
				mGR.mPlayer.no++;
				mGR.mPlayer.no%=mGR.mBGVactor.length;
				mGR.mPlayer.z = .05f;
				mGR.mPlayer.vz=.04f;
				if(mGR.mBGVactor[ld].type == 2 && mGR.mBGVactor[mGR.mPlayer.no].type != 2){
					if(Math.abs(mGR.mPlayer.x%.2f) > .1)
						mGR.mPlayer.x+=.2f*(mGR.mPlayer.x>0?1:-1);
					mGR.mPlayer.x -= mGR.mPlayer.x%.2f;
				}
				mGR.m3d_Player[mGR.mPlayer.img].setRotZ(0);
				mGR.mPlayer.time = System.currentTimeMillis();
				M.GameScreen = M.GAMEPLAY;
				mGR.mPlayer.time = System.currentTimeMillis();
			}
//				int ld = mGR.mPlayer.no; 
//				mGR.mPlayer.no++;
//				mGR.mPlayer.no%=mGR.mBGVactor.length;
//				mGR.mPlayer.y=mGR.mBGVactor[mGR.mPlayer.no].y;
//				if(mGR.mBGVactor[ld].type == 2 && mGR.mBGVactor[mGR.mPlayer.no].type != 2){
//					if(Math.abs(mGR.mPlayer.x%.2f) > .1)
//						mGR.mPlayer.x+=.2f*(mGR.mPlayer.x>0?1:-1);
//					mGR.mPlayer.x -= mGR.mPlayer.x%.2f;
//				}
//				mGR.mPlayer.time = System.currentTimeMillis();
//				M.GameScreen = M.GAMEPLAY;
				break;
			}
			if(sel !=0)
				M.sound5(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	public boolean Handle_Over(MotionEvent event) {
		
		sel = 0;
		System.out.println(screen2worldX(event.getX())+"   "+screen2worldY(event.getY()));
		if(CirCir(-.43f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 1;	//Sound
		}
		if(CirCir(-.18f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 2;	//Play
		}
		if(CirCir(.21f,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 3; //Achievement
		}
		if(CirCir(0.49,-0.83, .05, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 4;// Leader Board
		}
		if(mGR.giftNo == 2 &&
				CirCir(-.06f,0.10, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 5;	//Buy Character
		}
		
		if(mGR.giftNo == 0 &&
				CirCir(0.35f,0.21, .06, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 6;	//Win Price
		}
		if(mGR.giftNo == 0 &&
				CirCir(0.35f,-.02, .06, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 7;	//Free Gift
		}
		
		if(mGR.giftNo == 1 &&
				CirCir(0.35f,-.02, .06, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 8;	//Paiggie bank
		}
		if(HTTRenderer.mStart.isVideoAvail &&
				CirCir(0.40f,-.5, .06, screen2worldX(event.getX()), screen2worldY(event.getY()), .05)){
			sel = 9;	//video
		}
//		System.out.println(screen2worldX(event.getX())+"   "+screen2worldY(event.getY())+"    "+mGR.giftNo+"  "+sel);
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (sel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				mGR.gameReset(false);
				M.GameScreen = M.GAMEMENU;
				mGR.m2d_Crosy.setX(-1.5f);
				break;
			case 3:
				//Achievement
				HTTRenderer.mStart.onShowAchievementsRequested();
				break;
			case 4:
				//Leader Board
				HTTRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 5:
				if(mGR.mPlayer.buyChar>=0)
					mGR.mInApp.onBuyCharecter(null);
				//Buy Character
				//mGR.mPlayer.buyChar
				break;
			case 6:
				if (mGR.cent > 99 && (mGR.mPlayer.buyChar > 0))
					M.GameScreen = M.GAMENEWCHAR;
				break;
			case 7:
				long mmtme =System.currentTimeMillis() - mGR.time;
				if(mmtme > 21600000){
					M.GameScreen = M.GAMEGIFT;
				}
				break;
			case 8:
				if(!mGR.isPeggie){
					mGR.mInApp.onBuyPiggy(null);//Buy Peggie
				}
				break;
			case 9:
				if(HTTRenderer.mStart.isVideoAvail){
//					HTTRenderer.mStart.ShowVideoHandler();
				}
				break;
			}
			if(sel !=0)
				M.sound5(R.drawable.button_click);
			sel = 0;	
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

	void shareImage() {
		Uri uri = Uri.parse("android.resource://com.hututu.game3d.crossyroad/drawable/share");
		Intent shareIntent = new Intent();
		shareIntent.setType("image/png");
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+ " " + mGR.mScore+ " "
								+ " and Best Score is "+mGR.mHScore+ " "
								+ "in  "+ HTTRenderer.mContext.getResources().getString(R.string.app_name)
								+ "  Can you beat me..."
								+ "Let the battle commence!!! Download it now and let’s ROCK!!!!  "
								+ M.SHARELINK);
		try {
			HTTRenderer.mContext.startActivity(Intent.createChooser(
					shareIntent, "Share from"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(HTTRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
 

}
