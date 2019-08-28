package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class LevelSix {
	
	boolean move = false;
	
	int mSel = 0;
	int counter = 0;
	int counter2 = 0;
	
	Fruit	mFruit[];
	
	SimplePlane mTex_BG,mTex_Fruit[],mTex_button[],mTex_Penguin[],mTex_PSplash[];
	GameRenderer mGR;
	public LevelSix(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		mTex_BG			= GameRenderer.add("l6/l6.png");
		
		mTex_button		= new SimplePlane[5];
		for(int i=0;i<mTex_button.length;i++)
			mTex_button[i]	= GameRenderer.add("l6/b"+i+".png");
		mTex_Fruit		= new SimplePlane[5];
		for(int i=0;i<mTex_Fruit.length;i++)
			mTex_Fruit[i]	= GameRenderer.add("l6/"+i+".png");
		
		mTex_Penguin	= new SimplePlane[6];
		Bitmap b		= GameRenderer.LoadImgfromAsset("l6/eating.png");
		for (int i = 0; i < mTex_Penguin.length; i++) {
			mTex_Penguin[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
		}
		b.recycle();
		mTex_PSplash	= new SimplePlane[6];
		b		= GameRenderer.LoadImgfromAsset("l6/splash.png");
		for (int i = 0; i < mTex_PSplash.length; i++) {
			mTex_PSplash[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
		}
		
		
		mFruit			= new Fruit[7];
		for(int i=0;i<mFruit.length;i++)
		{
			mFruit[i]	= new Fruit();
		}
		set();
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		set();
	}
	void set()
	{
		move = false;
		for(int i=0;i<mFruit.length;i++)
		{
			mFruit[i].set(-.19f+i*.31f,.45f);
		}
		counter = 100;
	}
	
	void gameLogic()
	{
		if(move){
			for(int i=0;i<mFruit.length;i++)
			{
				mFruit[i].update();
			}
			for(int i=0;i<mFruit.length;i++)
			{
				if(mFruit[i].vy==0 && mFruit[i].x < -.19f)
				{
					move = false;
				}
			}
			if(!move)
			{
				for(int i=0;i<mFruit.length;i++)
				{
					if(mFruit[i].vy!=0)
					{
						mFruit[i].set(mFruit[i==0?mFruit.length-1:i-1].x+.31f,.50f);
						if(mFruit[i==0?mFruit.length-1:i-1].n==mFruit[i].n)
						{
							mFruit[i].n = (mFruit[i].n+1)%5;
						}
					}
				}
			}
		}
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
//		Group.DrawTexture(gl, konveer, .39f, .20f);
		for(int i=0;i<mTex_button.length;i++)
			Group.DrawTransScal(gl, mTex_button[i],-.54f+i*.33f,-.8f,(mSel-1)==i?1.1f:1,(mSel-1)==i?0.5f:1);
		
		if(counter<3)
			Group.DrawTexture(gl, mTex_Penguin[counter],-0.74f,-0.09f);
		else if(counter<30)
			Group.DrawTexture(gl, mTex_Penguin[(counter%3)+3],-0.74f,-0.09f);
		else if((counter/mGR.mLOne.FrogAnim.length)%2==0)
			Group.DrawTexture(gl, mTex_PSplash[mGR.mLOne.FrogAnim[counter%mGR.mLOne.FrogAnim.length]],-0.74f,-0.09f);
		else
			Group.DrawTexture(gl, mTex_PSplash[0],-0.74f,-0.09f);
		
		
		for(int i=0;i<mFruit.length;i++)
		{
			Group.DrawTexture(gl, mTex_Fruit[mFruit[i].n],mFruit[i].x,mFruit[i].y);
		}
		
		mGR.mLSelect.GTime(gl);
		gameLogic();
		counter2++;
		if(counter2%3==0)
			counter++;
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		mSel = 0;
		for(int i=0;i<mTex_button.length;i++){
			if(Group.CircRectsOverlap(-.54f+i*.33f,-.8f, mTex_button[i].width()*.4f,mTex_button[i].Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01f)){
				mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				if(!move && mSel>0){
					int j =0;
					for(int i=1;i<mFruit.length;i++)
					{
						if(mFruit[j].x > mFruit[i].x)
						{
							j=i;
						}
					}
					if(mSel-1 == mFruit[j].n){
						mFruit[j].vy = -.12f;
						mFruit[j].vx = -.12f;
						move = true;
						counter =0;
						mGR.mScore++;
						mGR.Total[mGR.mLSelect.mGameSel]++;
						mGR.mLSelect.Achievement(false,0);
						M.sound1(GameRenderer.mContext, R.drawable.l_6_fruits_on_floor);
						M.sound9(GameRenderer.mContext, R.drawable.l_6_eating);
						if(mGR.mScore%5==0)
						{
							mGR.mLSelect.IncTime(2000);
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
			mSel = 0;
		}
		return true; 
	}

}
