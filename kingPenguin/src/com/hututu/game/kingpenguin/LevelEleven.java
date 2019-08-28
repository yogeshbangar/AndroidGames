package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class LevelEleven {
	
	int in;
	int counter;
	
	float x,y;
	
	Eleven arr[][];
	SimplePlane mTex_bg;
	GameRenderer mGR;
	public LevelEleven(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		mTex_bg 		= GameRenderer.add("l11/l11.png");
		arr = new Eleven[5][8];
		for(int i = 0;i<arr.length;i++)
		{
			arr[i]		= new Eleven[8];
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j]	= new Eleven();
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
		counter=0;
		mGR.mLSelect.IncTime(1000);
		in =no%arrLevel.length;
		int m,n;
		m = arrLevel[in].length;
		n = arrLevel[in][0].length;
		
		x = (-n*.25f*.5f)+.125f;
		y = ( m*.40f*.5f)-.20f;
		for(int i = 0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(i>=m || j >= n)
					arr[i][j].no = 0;
				else
					arr[i][j].set(arrLevel[in][i][j]);
			}
		}
		for(int i =0;i<mGR.mLOne.mAni.length;i++)
			mGR.mLOne.mAni[i].i=100;
		setAni();
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_bg, 0, 0);
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j].no>0){
					if(i==m && j==n)
						mGR.mLOne.Draw_AniCharColor(gl, x+j*.25f,y-i*.4f, arr[i][j].no-1, true,counter);
					else
						mGR.mLOne.Draw_AniCharColor(gl, x+j*.25f,y-i*.4f, arr[i][j].no-1, false,counter);
				}
			}
		}
		if(arr[m][n].no==0)
			mGR.mLOne.ani = 100;
		if(mGR.mLOne.ani>=mGR.mLOne.FrogAnim.length)
			setAni();
		counter++;
		mGR.mLOne.DrawAnimation(gl);
		mGR.mLSelect.GTime(gl);
		
	}
	boolean Handle_GamePlay(MotionEvent ev)
	{
		mGR.mLSelect.ClockScreen(ev);
		if(counter<10)
			return true;
		if(!mGR.isPlay)
		{
			mGR.mGameTime += System.currentTimeMillis();
			mGR.isPlay = true;
		}else if(mGR.mGameTime<System.currentTimeMillis())
		{
			if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(ev);*/}
		}
		else if(mGR.timesUp < 5 && mGR.ClockCount < 5){
			PerformAction(ev);
			
		}
		return true;
	}
	
	int m=0,n=0,anicount=0;
	void setAni()
	{
		mGR.mLOne.ani=0;
		int co=0;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j].no != 0)
				{
					co++;
				}
			}
		}
		if(co==0)
			co++;
		anicount++;
		co = anicount%co;
		int ba =0;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j].no != 0)
				{
					if(ba == co)
					{
						m = i;
						n = j;
					}
					ba++;
				}
			}
		}
	}
	void PerformAction(MotionEvent ev)
	{

		int count =  MotionEventCompat.getPointerCount(ev);
		int co= 0;
		boolean brek = false;
		for(int i=0;i<count;i++)
		{
			final float xx = MotionEventCompat.getX(ev, i); 
			final float yy = MotionEventCompat.getY(ev, i); 
			for(int m=0;m<arr.length&&!brek;m++)
			{
				for(int n=0;n<arr[m].length&&!brek;n++)
				{
					if(arr[m][n].no>0){
						if(Group.CirCir(x+n*.25f,y-m*.4f, .12f, Group.screen2worldX(xx),Group.screen2worldY(yy), .03f))
						{
							arr[m][n].isTuch = true;
							if(co == 0)
								co = arr[m][n].no;
							else if(co == arr[m][n].no)
							{
								brek = true;
							}
								
						}
					}
				}
			}
		}
		boolean newSet = true;
		for(int i = 0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length ;j++)
			{
				if(brek && co == arr[i][j].no && arr[i][j].isTuch){
					arr[i][j].no = 0;
					mGR.mLOne.SetAnim(x+j*.25f,y-i*.4f);
					M.sound1(GameRenderer.mContext, R.drawable.l_1_blast);
				}
					
				arr[i][j].isTuch = false;
				if(arr[i][j].no>0)
					newSet =false;
			}
		}
		if(newSet){
			set(in+1);
			return;
		}
		if(brek)
		{
			mGR.mScore+=2;
			mGR.Total[mGR.mLSelect.mGameSel]+=2;
			mGR.mLSelect.Achievement(false, 0);
			
		}
		/*if(MotionEvent.ACTION_UP == ev.getAction()){
			if(Group.CircRectsOverlap(0.72f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
					Group.screen2worldX(ev.getX()), Group.screen2worldY(ev.getY()), .02f))
			{
				mGR.mGameTime -= System.currentTimeMillis();
				M.GameScreen = M.GAMEHELP;
			}
			if(Group.CircRectsOverlap(0.9f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
					Group.screen2worldX(ev.getX()), Group.screen2worldY(ev.getY()), .02f))
			{
				mGR.mGameTime -= System.currentTimeMillis();
				M.GameScreen = M.GAMELVLPUSE;
			}
		}*/
	
	}

	
	
	
	
	byte arrLevel[][][] = {
			{{1,1},},//00
			{{2},{2},},//01
			{{3,3,3,3},},//02
			{{0,4,0},{4,4,4},},//03
			{{5,5},{2,2},},//04
			{{1,0,0,0,0,1},{0,0,2,2,0,0},{1,0,0,0,0,1},},//05
			{{2,2,2,2},{5,5,5,5},},//06
			{{2,0,0,2},{4,0,0,4},{2,0,0,2},},//07
			{{3,2},{3,2},},//08
			{{0,0,2,0,0},{0,0,2,0,0},{4,4,0,4,4},{0,0,2,0,0},{0,0,2,0,0},},//09
			{{5,2},{2,5},},//10
			{{1,2,3},{2,3,1},},//11
			{{3,2,0,0,0,0,2,3},{2,3,0,0,0,0,3,2},},//12
			{{4,4,4},{2,2,2},{4,4,4},{2,2,2},},//13
			{{5,2,5},{0,0,0},{0,0,0},{5,2,5},},//14
			{{1,2,3,3,2,1},},//15
			{{4,0,0,0,4},{0,2,3,2,0},{0,0,3,0,0},},//16
			{{5,2,5,2},{0,3,3,0},},//17
			{{0,0,0,5,0,0,0},{0,0,0,5,0,0,0},{5,5,5,0,5,5,5},{0,0,0,5,0,0,0},{0,0,0,5,0,0,0},},//18
			{{1,2,2,3},{2,0,0,2},{3,2,2,1},},//19
			{{3,3,3,3},{3,3,2,3},{3,3,2,3},{3,3,3,3},},//20
			{{4,4,0,4,4},{0,0,2,0,0},{0,0,2,0,0},{5,5,0,5,5},},//21
			{{4,4,4},{0,4,0},{0,4,0},{0,4,0},},//22
			{{5,5,5,5},{2,2,2,2},{3,3,3,3},},//23
			{{1,2,1,2},{3,0,0,3},{2,1,2,1},},//24
			{{4,2,0,4,2},{0,0,3,0,0},{0,0,3,0,0},{2,4,0,2,4},},//25
			{{5,3,2,2,3,5},{2,0,3,3,0,2},{3,0,5,5,0,3},{5,3,2,2,3,5},},//26
			{{1,2,3,1},{2,3,1,2},{3,1,2,3},},//27
			{{1,2,3,2,1},{2,3,0,3,2},{3,1,0,1,3},{1,2,3,2,1},},//28
			{{0,0,1,0,3},{0,2,1,2,0},{0,2,1,2,0},{3,0,1,0,0},},//29
		};
	
}

