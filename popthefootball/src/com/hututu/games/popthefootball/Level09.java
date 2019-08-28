package com.hututu.games.popthefootball;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;


public class Level09 {
	boolean fall =false;
	
	int mSel = 0;
	int stepCount,ano;
	int c1 = 100;
	
	final float Speed = .1f;
	float Move  = .5f;
	float fx,fy,fvy;
	float bgmove;
	
	NineObj mObj[];
	NineFrog mFrog = new NineFrog();
	
	SimplePlane mTex_BG[],mTex_Tile[];
	
	final byte arr[][] = {	{0,1,0,1},{0,1,1},{0,0,1,1},{0,0,1},{0,1,0,1},{1,1,1},{0,1,1,0},{0,2,0},
						{0,1,0,0},{1,1,1},{0,1,0,1},{0,1,1},{0,1,0,0},{1,0,0},{1,1,0,0},{2,0,0},
						{0,1,0,0},{1,0,0},{0,1,0,0},{1,1,1},{1,0,1,1},{1,1,1},{0,1,1,0},{0,2,0},
						{0,1,0,1},{1,1,1},{0,1,1,0},{0,1,0},{0,1,0,0},{0,1,1},{0,1,1,0},{0,2,0},
	};
	GameRenderer mGR;
	public Level09(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		try{
			mTex_BG			= new SimplePlane[7];
			for(int i=0;i<mTex_BG.length;i++)
				mTex_BG[i]=GameRenderer.add("l9/a"+i+".png");
			mTex_Tile		= new SimplePlane[4];
			mTex_Tile[0]	= GameRenderer.add("l9/0.png");
			mTex_Tile[1]	= GameRenderer.add("l9/1.png");
			mTex_Tile[2]	= GameRenderer.add("l9/2.png");
			mTex_Tile[3]	= GameRenderer.add("l9/3.png");
			mObj			= new NineObj[8];
			for(int i=0;i<mObj.length;i++)
				mObj[i]		= new NineObj(i%2==0?4:3);
			GameResart();
		}catch(Exception e){System.out.println("Level 9 ~~~~~~~~~~~~~~"+e.toString());}
	}
	void gameReset()
	{
		Move  = .7f;
		fx=fy=-100;fvy=0;
		mSel =stepCount=ano=0;
		fall =false;
		c1 = 6;
		mFrog.set(-.2f,-.45f);
		for(int i=0;i<mObj.length;i++){
			mObj[i].set(-.6f+i*.5f,arr[stepCount]);
			mObj[i].tile =1;
			stepCount++;
		}
		if(stepCount==8){
			ano =M.mRand.nextInt(3);
			stepCount =0;
		}
		bgmove = 0;
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		gameReset();
	}
	void GameLogic()
	{
		if(Move < .5f){
			bgmove-=Speed*.05f;
			Move +=Speed;
			mFrog.update();
			for(int i=0;i<mObj.length;i++)
				mObj[i].y-=Speed;
			for(int i=0;i<mObj.length;i++){
				if(mObj[i].y<-1.1f){
					mObj[i].set(mObj[(i==0?mObj.length:i)-1].y+.5f,arr[ano*8+stepCount]);
					if(mGR.mScore < 40)
						mObj[i].tile = 1;
					else if(mGR.mScore < 90)
						mObj[i].tile = 2;
					else
						mObj[i].tile = 3;
					stepCount++;
					if(stepCount>=8){
						ano =M.mRand.nextInt(3);
						stepCount =0;
					}
				}
			}
		}
		if(Move >= .5f && Move < .6f){
			Move = 1;
			int j = 0;
			for(int i=0;i<mObj.length;i++){
				if(mObj[i].y <-.59f)
				{
					j=i;
					mFrog.y =-.45f;
					break;
				}
			}
			for(int m=0;m<mObj[j].no.length;m++){
				if(Group.CircRectsOverlap(mObj[j].x+m*.4f,mObj[j].y, mTex_Tile[0].width()*.4f, mTex_Tile[0].Height()*.4f, mFrog.x, mFrog.y, .15f))
				{
					if(mObj[j].no[m]==0){
						M.sound2(GameRenderer.mContext, R.drawable.l_7_wrong);
						fall =true;
						fx  = mFrog.x;
						fy  = mFrog.y-.01f;
						fvy = -.01f;
						mObj[j].no[m] = -1;
						for(int n=0;n<mObj[j].no.length;n++){
							if(mObj[j].no[n]>0)
							{
								mFrog.x = mObj[j].x+n*.4f;
								if(mObj[j].no[n]==2){
									mObj[j].no[n]=1;
									mGR.mLSelect.IncTime(2000);
								}
								break;
							}
						}
					}
					else{
						if(mObj[j].no[m]==2){
							mObj[j].no[m]=1;
							mGR.mLSelect.IncTime(2000);
							mGR.mLSelect.Animtin(mFrog.x, mFrog.y);
						}
						mGR.mScore++;
						mGR.Total[mGR.mLSelect.mGameSel]++;
						mGR.mLSelect.Achievement(fall, 0);
					}
					break;
				}
			}
			for(int i=0;i<mObj.length;i++){
				mObj[j%mObj.length].y=(-0.6f+i*.5f);j++;
			}
		}
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		//Group.DrawTexture(gl, mTex_BG, 0, 0);
		for(int i=0;i<mTex_BG.length;i++){
			if (bgmove + i * mTex_BG[i].Height() > -2 && bgmove + i * mTex_BG[i].Height() < 2){
				if(i==0)
					Group.DrawTexture(gl, mTex_BG[i], 0, bgmove + i * mTex_BG[i].Height());
				else
					mTex_BG[i].drawScal(gl, 0, bgmove + i * mTex_BG[i].Height(),6.8f,1);
			}
		}
		Group.DrawTransScal(gl, mGR.mLFour.mTex_Left , -.8f, -.8f,mSel == 1?1.1f:1,mSel == 1?.5f:1);
		Group.DrawTransScal(gl, mGR.mLFour.mTex_Right, 0.8f, -.8f,mSel == 2?1.1f:1,mSel == 2?.5f:1);
		for(int i=0;i<mObj.length;i++){
			for(int j=0;j<mObj[i].no.length;j++){
				if(mObj[i].no[j]>0)
					Group.DrawTexture(gl, mTex_Tile[mObj[i].tile], mObj[i].x+j*.4f,mObj[i].y);
				if(mObj[i].no[j]==0)
					Group.DrawTexture(gl, mTex_Tile[0], mObj[i].x+j*.4f,mObj[i].y);
				if(mObj[i].no[j]==2)
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mObj[i].x+j*.4f,mObj[i].y+.1f);
			}
		}
		if(fy>-1.1f){
			Group.DrawTexture(gl, mTex_Tile[0], fx, fy-.2f);
			mGR.mLOne.Draw_AniChar(gl, fx, fy, (mGR.mLOne.ani/mGR.mLOne.FrogAnim.length)%3==0);
			fy+=fvy;fvy-=.01f;
		}else{
			if(c1<6){
				mGR.mLOne.Draw_AniChar(gl, mFrog.x, mFrog.y, (mGR.mLOne.ani/mGR.mLOne.FrogAnim.length)%3==0);
//				Group.DrawTexture(gl, mGR.mLTwo.mTex_Frog[c1], mFrog.x, mFrog.y);
				c1++;
			}
			else
				mGR.mLOne.Draw_AniChar(gl, mFrog.x, mFrog.y+.05f, (mGR.mLOne.ani/mGR.mLOne.FrogAnim.length)%3==0);
		}
		mGR.mLSelect.Draw_Animtin(gl);
		mGR.mLSelect.GTime(gl);
		GameLogic();
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		mSel = 0;
		if(mFrog.x > -.5f &&Group.CircRectsOverlap(-.8f, -.8f,mGR.mLFour.mTex_Right.width(), mGR.mLFour.mTex_Right.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
		{
			mSel = 1;
		}
		if(mFrog.x < 0.5f && Group.CircRectsOverlap(0.8f, -.8f, mGR.mLFour.mTex_Right.width(), mGR.mLFour.mTex_Right.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
		{
			mSel = 2;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(!mGR.isPlay)
			{
				if(performaction(event)){
					mGR.mGameTime += System.currentTimeMillis();
					mGR.isPlay = true;
				}
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else if(mGR.timesUp < 5 && mGR.ClockCount < 5 && (fy<-1.0f)){
				performaction(event);
			}
			mSel = 0;
		}
		return true;
	}
	boolean performaction(MotionEvent event)
	{

//		if(Group.CircRectsOverlap(0.72f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
//				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
//		{
//			mGR.mGameTime -= System.currentTimeMillis();
//			M.GameScreen = M.GAMEHELP;
//			return false;
//		}
//		if(Group.CircRectsOverlap(0.9f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
//				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
//		{
//			mGR.mGameTime -= System.currentTimeMillis();
//			M.GameScreen = M.GAMELVLPUSE;
//			return false;
//		}
		if(Move >= .5f && mSel!=0){
			Move =0;
			mFrog.vy = .1f;
			if(mSel == 1 )
				mFrog.vx =-.04f;
			if(mSel == 2 )
				mFrog.vx =0.04f;
			c1 =0;
			M.sound1(GameRenderer.mContext, R.drawable.l_9_jump);
		}
		return true;
	}
}
