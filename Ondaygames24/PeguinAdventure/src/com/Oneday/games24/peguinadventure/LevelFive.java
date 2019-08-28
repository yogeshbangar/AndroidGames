package com.Oneday.games24.peguinadventure;

import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;

public class LevelFive {
	boolean isWrong = false;
	
	int mSel;
	int counter;
	int game;
	
	Vecter	mVecter[];
	SimplePlane mTex_FBack[],/*mTEx_Mark[],*/mTEx_BG;
	GameRenderer mGR;
	public LevelFive(GameRenderer _GameRenderer)
	{
		counter = 51;
		mTex_FBack			= new SimplePlane[1];
		mTex_FBack[0]		= GameRenderer.addRotate("l5/bubble.png");
//		mTEx_Mark			= new SimplePlane[1];
//		mTEx_Mark[0]		= GameRenderer.add("l5/right.png");
//		mTEx_Mark[0]		= GameRenderer.add("l5/wrong.png");
		mTEx_BG				= GameRenderer.add("l5/bg.png");
		
		
		mVecter			= new Vecter[14];
		mVecter[0]		= new Vecter(-.3f,0.7f,0);
		mVecter[1]		= new Vecter(0.8f,-.7f,0);
		mVecter[2]		= new Vecter(-.5f,-.7f,0);
		mVecter[3]		= new Vecter(0.5f,0.5f,0);
		mVecter[4]		= new Vecter(-.8f,0.0f,0);
		mVecter[5]		= new Vecter(0.8f,0.0f,0);
		mVecter[6]		= new Vecter(0.0f,0.7f,0);
		mVecter[7]		= new Vecter(0.0f,-.7f,0);
		mVecter[8]		= new Vecter(-.6f,0.4f,0);
		mVecter[9]		= new Vecter(0.4f,-.6f,0);
		mVecter[10]		= new Vecter(0.4f,-.1f,0);
		mVecter[11]		= new Vecter(-.4f,-.0f,0);
		mVecter[12]		= new Vecter(0.3f,+.8f,0);
		mVecter[13]		= new Vecter(-.8f,-.8f,0);

		mGR = _GameRenderer;
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		set(3,true);
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
	}
	void set(int no,boolean time)
	{
		if(time)
			mGR.mLSelect.IncTime(2000);
		isWrong = false;
		game =no;
		if(game>10)
			game =10;
		int m = 0;
		for(int i=0;i<mVecter.length;i++)
		{
			if(m < game && M.mRand.nextBoolean()){
				mVecter[i].no = (byte)(M.mRand.nextInt(10)+m*10);
				mVecter[i].off = true;
				m++;
			}
			else{
				mVecter[i].no = (byte)(0);
				mVecter[i].off = false;
			}
		}
		if(m<3)
		{
			for(int i=0;i<mVecter.length;i++)
			{
				if(i < 3){
					mVecter[i].no = (byte)(M.mRand.nextInt(10)+i*10);
					mVecter[i].off = true;
				}
				else
					mVecter[i].off = false;
			}
		}
		for(int k=0;k<mVecter.length;k++){
			for(int i=0;i<mVecter.length;i++)
			{
				int j = M.mRand.nextInt(14);
				if(mVecter[i].off && mVecter[j].off){
					byte temp = mVecter[i].no;
					mVecter[i].no = mVecter[j].no;
					mVecter[j].no = temp;
					mVecter[i].off = true;
				}
			}
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		Group.DrawTexture(gl, mTEx_BG, 0, 0);
		mGR.mLFour.Draw_Five(gl, 0, 0);
		for(int i=0;i<mVecter.length;i++)
		{
			if(mVecter[i].off){
				Group.DrawTextureR(gl, mTex_FBack[0], mVecter[i].x, mVecter[i].y, 0,1);
				mGR.NumberRotate(gl, mVecter[i].no, mVecter[i].x, mVecter[i].y, counter);
			}
			if(mVecter[i].no > 102){
//				Group.DrawTexture(gl, mTEx_Mark[0], mVecter[i].x, mVecter[i].y);
				mVecter[i].no--;
			}
			if(mVecter[i].no == 101)
				Group.DrawTexture(gl, mGR.mLSelect.mTex_NoAds, mVecter[i].x, mVecter[i].y);
		}
		counter++;
		if(counter==10){
			if(isWrong)
				set(game,false);
			else
				set(game+M.mRand.nextInt(2),true);
		}
		mGR.mLSelect.Draw_Animtin(gl);
		mGR.mLSelect.GTime(gl);
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		if(MotionEvent.ACTION_UP == event.getAction()){
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				for(int i=0;i<mVecter.length&&counter >5;i++)
				{
					if(mVecter[i].off && Group.CircRectsOverlap(mVecter[i].x, mVecter[i].y, mTex_FBack[0].width()*.4f, mTex_FBack[0].Height()*.4f,
							Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01f)){
						for(int j=0;j<mVecter.length;j++){
							if(mVecter[j].off && i!=j && mVecter[j].no < mVecter[i].no)
							{
								mVecter[i].no = 101;
								counter = 0;
								isWrong = true;
								break;
							}
						}
						if(!isWrong){
							mGR.mScore++;
							mGR.mLSelect.Animtin(mVecter[i].x, mVecter[i].y);
							M.sound1(GameRenderer.mContext, R.drawable.l_5_right_click);
						}
						else
							M.sound2(GameRenderer.mContext, R.drawable.l_5_wrong_click);
						mVecter[i].off = false;
						if(mVecter[i].no != 101){
							mVecter[i].no = 102;
						}
						break;
					}
				}
				boolean reset = true;
				for(int j=0;j<mVecter.length;j++){
					if(mVecter[j].off)
					{
						reset  = false;
						break;
					}
				}
				if(reset)
					counter = 0;
	//			set(game);
				mGR.Total[mGR.mLSelect.mGameSel]++;
				mGR.mLSelect.Achievement(false,0);
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
			mSel = 0;
		}
		return true;
	}
	
	
	
}
