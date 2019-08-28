package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

public class LevelEight {
	
	
	byte candy[][]	= new byte[3][4];
	int ext = 0;
	EightObj	mEightObj = new EightObj();
	Vecter		mWrong = new Vecter();
	SimplePlane mTex_BG;
	GameRenderer mGR;
	public LevelEight(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		mTex_BG			= GameRenderer.add("l8/level8.jpg");
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		set(1,2);
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		
	}
	void set(int m,int n)
	{
		if(ext%5 == 0)
			mGR.mLSelect.IncTime(1000);
		
		mEightObj.set(-.56f+n*.39f, .41f-m*.59f, m, n);
		int count = 0;
		for (int i = 0; i < candy.length; i++) {
			for (int j = 0; j < candy[i].length; j++) {
				if (count < 3) {
					candy[i][j] = (byte) (M.mRand.nextInt(3)==0 ? 1 : 0);
					if (i == m && j == n)
						candy[i][j] = 0;
					if (candy[i][j] == 1){
						count++;
						System.out.print("@@@@"+count);
					}
				} else
					candy[i][j] = 0;
			}
		}
		if (count < 3) 
		{
			for (int i = 0; i < candy.length &&count < 3; i++) 
			{
				for (int j = 0; j < candy[i].length&&count < 3; j++) 
				{
					if (candy[i][j] == 0 && i != m && j != n){
						candy[i][j] = 1;
						count++;
						System.out.print("###"+count);
					}
				}
			}
		}
		for (int i = 0; i < candy.length ; i++) {
			for (int j = 0; j < candy[i].length; j++) {
				{
					if (candy[i][j] == 1)
						System.out.print("~~~~~~"+count);
				} 
			}
		}
		mWrong.off = true;
	}
	void gameLOgic(){
		if(!mWrong.off){
			mWrong.no--;
			if(mWrong.no<1)
				mWrong.off =true;
		}
		{
			boolean reset = true;
			for(int i =0;i<candy.length;i++){
				for(int j =0;j<candy[i].length;j++){
					if(candy[i][j]==1){
						reset = false;
						break;
					}
				}
			}
			if(reset)
			{
				set(mEightObj.i,mEightObj.j);
			}
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG	, 0, 0);
		for(int i =0;i<candy.length;i++){
			for(int j =0;j<candy[i].length;j++){
				if(candy[i][j] == 1)
					Group.DrawTexture(gl, mGR.mLsix.mTex_Fruit[(i+j)%mGR.mLsix.mTex_Fruit.length] , -.56f+j*.39f, .46f-i*.59f);
			}
		}
		if(mEightObj.counter < 3){
			mEightObj.counter ++;
			Group.DrawTransScal(gl, mGR.mLOne.mTex_Frog[0][0], mEightObj.x, mEightObj.y, mEightObj.counter * .3f, 1);
		}
		else{
			mGR.mLOne.Draw_AniChar(gl, mEightObj.x, mEightObj.y, (mGR.mLOne.ani/mGR.mLOne.FrogAnim.length)%3==0);
		}
		if(!mWrong.off)
			Group.DrawTexture(gl, mGR.mLSelect.mTex_NoAds , mWrong.x, mWrong.y);
		mGR.mLSelect.GTime(gl);
		gameLOgic();
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
				PerformAction(event);
			}
			else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				PerformAction(event);
			}
		}
		return true;
	}
	void PerformAction(MotionEvent event)
	{
		for(int i =0;i<candy.length;i++){
			for(int j =0;j<candy[i].length;j++){
				if(Group.CirCir(-.56f+j*.39f, .46f-i*.59f, .2f, Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()), .01f))
				{
//						mEightObj.set(-.46f+j*.31f, .74f-i*.54f);
					if(!mEightObj.setAnim(i, j)){
						mWrong.set(-.56f+j*.39f, .46f-i*.59f, 20);
						mWrong.off =false;
						M.sound2(GameRenderer.mContext, R.drawable.l_5_wrong_click);
					}
					else
					{
						M.sound1(GameRenderer.mContext, R.drawable.l_8_jump);
						candy[mEightObj.i][mEightObj.j] =0;
						mGR.mScore++;
						mGR.Total[mGR.mLSelect.mGameSel]++;
						mGR.mLSelect.Achievement(false,0);
					}
				}
			}
		}
		/*if(Group.CircRectsOverlap(0.72f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mGR.mGameTime -= System.currentTimeMillis();
			M.GameScreen = M.GAMEHELP;
		}
		if(Group.CircRectsOverlap(0.9f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mGR.mGameTime -= System.currentTimeMillis();
			M.GameScreen = M.GAMELVLPUSE;
		}*/
	}
}
