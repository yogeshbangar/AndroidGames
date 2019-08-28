package com.Oneday.games24.peguinadventure;

import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;

public class LevelThree {
	
	int in;
	int counter;
	int mSel;
	int ax,ay;
	
	float x,y;
	
	Color arr[][];
	
	SimplePlane mTex_Color[],mTex_bg;
	GameRenderer mGR;
	public LevelThree(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		mTex_bg 		= GameRenderer.add("l3/l3.png");
		mTex_Color		= new SimplePlane[5];
		for(int i=0;i<mTex_Color.length;i++)
		{
			mTex_Color[i] = GameRenderer.add("l3/"+i+".png");
		}
		arr = new Color[4][8];
		for(int i = 0;i<arr.length;i++)
		{
			arr[i]		= new Color[8];
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j]	= new Color(true,M.mRand.nextInt(2));
			}
		}
//		GameResart();
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		set(0);
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		
	}
	void set(int no)
	{
		mGR.mLSelect.IncTime(1000);
		counter =0;
		in =no;
		
		if(frog.Level1.length> in){
			ax = frog.Level1[in].length;
			ay = frog.Level1[in][0].length;
		}else{
			ax = M.mRand.nextInt(4)+1;
			ay = M.mRand.nextInt(8)+1;
		}
		if(ax == 1 && ay ==1)
		{
			if(M.mRand.nextBoolean())
				ax =2;
			else
				ay =2;
		}
		if(ay > 6 && ax >3){
			ay = 6;
			ax = 3;
		}
		
		x = (-ay*.25f*.5f)+.125f;
		y = (ax*.5f*.5f)-.25f;
		for(int i = 0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j].Set(true,M.mRand.nextInt(2));
				if(i>=ax || j >= ay)
					arr[i][j].ex = false;
			}
		}
		if(reset())
		{
			if(arr[0][0].no == 0)
				arr[0][0].no = 1;
			else
				arr[0][0].no = 0;
		}
		setAni();
	}
	void Draw_GamePlay(GL10 gl)
	{
		counter++;
		Group.DrawTexture(gl, mTex_bg, 0, 0);
		if(arr!=null){
			for(int i=0;i<arr.length;i++)
			{
				if(arr[i]!=null){
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j]!=null && arr[i][j].ex)
						{
							if(i==m && j==n){
								mGR.mLOne.Draw_AniCharColor(gl, x+j*.25f,y-i*.4f, arr[i][j].no+((in/5)%4), true,counter);
							}
							else{
								mGR.mLOne.Draw_AniCharColor(gl, x+j*.25f,y-i*.4f, arr[i][j].no+((in/5)%4), false,counter);
							}
							if(arr[i][j].color > 0){
								arr[i][j].color--;
								Group.DrawTransScal(gl, mTex_Color[arr[i][j].no+((in/5)%4)], x+j*.25f,y-i*.4f, 1.2f, arr[i][j].color/10f);
							}
						}
					}
				}
			}
		}
		if(mGR.mLOne.ani>=mGR.mLOne.FrogAnim.length)
			setAni();
		
		mGR.mLSelect.GTime(gl);
	}
	public boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
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
				for(int i=0;i<arr.length;i++)
				{
					for(int j=0;j<arr[i].length;j++)
					{
						if(arr[i][j].ex && Group.CirCir(x+j*.25f,y-i*.4f, .1f, Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()),.05f))
						{
							if(arr[i][j].no == 0)
								arr[i][j].no = 1;
							else
								arr[i][j].no = 0;
							arr[i][j].color = 10;
							if(arr[i][j].isOne)
								mGR.mScore++;
							else if(mGR.mScore>0)
								mGR.mScore--;
							arr[i][j].isOne = !arr[i][j].isOne;
							M.sound1(GameRenderer.mContext, R.drawable.l_3_blast);
						}
					}
				}
				mGR.Total[mGR.mLSelect.mGameSel]++;
				mGR.mLSelect.Achievement(false,0);
				if(reset())
				{
					set(in+1);
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
	boolean reset()
	{
		boolean reset = true;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[0][0].no != arr[i][j].no && arr[i][j].ex)
				{
					reset = false;
					break;
				}
			}
		}
		return reset;
	}
	float m,n;
	void setAni()
	{
		mGR.mLOne.ani =0;
		m = M.mRand.nextInt(ax)%arr.length;
		n = M.mRand.nextInt(ay)%arr[0].length;
	}
}
