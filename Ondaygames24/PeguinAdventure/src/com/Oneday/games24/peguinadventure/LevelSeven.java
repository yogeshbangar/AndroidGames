package com.Oneday.games24.peguinadventure;

import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;

public class LevelSeven {
	
	int fall =0;
	
	float BGMove1;
	float BGMove2;
	float vx,vy;
	float hx,hy;
	float speed = -.05f;
	
	SevenObj mObj[];
	SevenObj mFall;
	
	SimplePlane mTex_BG1,mTex_BG2,mTex_BG3,mTex_Box[],mTex_Hook;
	GameRenderer mGR;
	public LevelSeven(GameRenderer _GameRenderer)
	{
		mGR =_GameRenderer;
		mTex_BG1		= GameRenderer.add("l7/sky7.png");
		mTex_BG2		= GameRenderer.add("l7/l7.png");
		mTex_BG3		= GameRenderer.add("l7/x100.png");
		mTex_Box		= new SimplePlane[21];
		for(int i=0;i<mTex_Box.length;i++)
			mTex_Box[i]		= GameRenderer.addScal("l7/block"+i+".png",0.90625f);
		mTex_Hook		= GameRenderer.add("l7/hook.png");
		
		
		mObj			= new SevenObj[6];
		for(int i=0;i<mObj.length;i++)
			mObj[i]			= new SevenObj();
		
		mFall	= new SevenObj();
		GameResart();
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		set();
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		
	}
	void set()
	{
		mFall.y = -100;
		fall =0;
		BGMove1 = 0;
		BGMove2 = -.73f;
		vx	= .02f;
		vy	=-.2f;
		hx = -1.5f;
		hy = .8f;
		for(int i=0;i<mObj.length;i++){
			mObj[i].set(100,-100, 8,0);
		}
		mObj[0].set(-mTex_Box[0].width()*3.2f, -.8f, 8,0);
		mObj[1].set(hx-mTex_Box[0].width()*3.5f, 0.55f, 8,1);
	}
	void setnew(int i,boolean set)
	{
		speed = -.03f;
		if(i%2==0&&set)
			mGR.mLSelect.IncTime(2000);
		hx = (M.mRand.nextBoolean()?1:-1)*1.5f;
		hy = .8f;
		int n=0;
		for(int m =0;m<mObj[i].obj.length;m++)
		{
			if(mObj[i].obj[m]!=-1)
				n++;
		}
		if(set)
			mObj[i==mObj.length-1?0:i+1].set(hx-mTex_Box[0].width()*(n-1)*.5f, 0.55f, n,1);
		else
			mObj[i].set(hx-mTex_Box[0].width()*(n-1)*.5f, 0.55f, n,1);
		vx =  Math.abs(vx)+.005f;
		if(vx>.06f)
			vx = .06f;
		
		if(n>=8)
			fall ++;
		mGR.mScore+=n;
		mGR.Total[mGR.mLSelect.mGameSel]+=n;
		mGR.mLSelect.Achievement(false,fall);
	}
	void gameLogic()
	{
		int move = 0;
		
		for(int i=0;i<mObj.length;i++){
			if(move<mObj[i].move)
				move=mObj[i].move;
			if(mObj[i].move == 1){
				mObj[i].x+=vx;
			}
			if(mObj[i].move == 2){
				mObj[i].y+=vy;
				int j = (i==0)?mObj.length-1:i-1;
				for(int m =0;m<mObj[i].obj.length;m++){
					for(int n =0;n<mObj[j].obj.length&&mObj[i].obj[m]!=-1;n++){
						if(mObj[j].obj[n]!=-1){
							if(Group.Rect2RectIntersection(mObj[i].x+m*mTex_Box[0].width(), mObj[i].y, mTex_Box[0].width(), mTex_Box[0].Height(), 
									mObj[j].x+n*mTex_Box[0].width(), mObj[j].y, mTex_Box[0].width(), mTex_Box[0].Height()))
							{
								mObj[i].move = 0;
							}
						}
					}
				}
				if(mObj[i].move == 0){
					int fallCount = 0;
					boolean m0 = false;
					for(int m =0;m<mObj[i].obj.length;m++){
						boolean collide = false;
						for(int n =0;n<mObj[j].obj.length&&mObj[i].obj[m]!=-1;n++){
							if(mObj[j].obj[n]!=-1){
								if(Group.Rect2RectIntersection(mObj[i].x+m*mTex_Box[0].width(), mObj[i].y, mTex_Box[0].width(), mTex_Box[0].Height(), 
										mObj[j].x+n*mTex_Box[0].width(), mObj[j].y, mTex_Box[0].width(), mTex_Box[0].Height()))
								{
									collide = true;
									break;
								}
							}
						}
						if(!collide && mObj[i].obj[m]!=-1){
							if(m==0)
								m0 = true;
							fallCount++;
							mObj[i].obj[m]=-1;
							
						}
					}
					if(fallCount>0)
					{
						int cunt =0;
						for(int m =0;m<mObj[i].obj.length;m++)
						{
							if(mObj[i].obj[m]!=-1)
								cunt++;
						}
						mFall.set(m0?mObj[i].x:mObj[i].x+cunt*mTex_Box[0].width(), mObj[i].y, fallCount,1);
						
					}
					mObj[i].y = mObj[j].y + mTex_Box[0].Height();
					setnew(i, true);
				}
				if(mObj[i].y<-1.1f){
					setnew(i, false);
					M.sound1(GameRenderer.mContext, R.drawable.l_7_wrong);
				}
			}
		}
		if(move == 1){
			hy = .85f;
			hx+=vx;
			if(hx>1)
				vx = -Math.abs(vx);
			if(hx<-1)
				vx =  Math.abs(vx);
		}
		if(move == 2){
			hy-=vy;
		}
		boolean goDown = false;
		for(int i =0;i<mObj.length;i++)
		{
			if(mObj[i].move==0 && mObj[i].y>-.5){
				goDown = true;
				break;
			}
		}
		if(goDown){
			for(int i =0;i<mObj.length;i++)
			{
				if(mObj[i].move==0){
					mObj[i].y+=speed;
				}
			}
			BGMove1+=speed*.01f;
			BGMove2+=speed;
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		mTex_BG1.drawScal(gl, 0, 0, 2, 35);
		Group.DrawTexture(gl, mTex_BG2, 0, BGMove1);
		Group.DrawTexture(gl, mTex_BG3, 0, BGMove2);
		Group.DrawTexture(gl, mTex_Hook, hx, hy);
		for(int i=0;i<mObj.length;i++){
			for(int j=0;j<mObj[i].obj.length;j++)
			{
				if(mObj[i].y>-1.3f && mObj[i].y < 1.3f && mObj[i].obj[j]!=-1)
					Group.DrawTexture(gl, mTex_Box[(i*8+j)%mTex_Box.length], mObj[i].x+j*mTex_Box[0].width(), mObj[i].y);
			}
		}
		for(int j=0;j<mFall.obj.length;j++)
		{
			if(mFall.y>-1.3f && mFall.y < 1.3f && mFall.obj[j]!=-1){
				Group.DrawTexture(gl, mTex_Box[0], mFall.x+j*mTex_Box[0].width(), mFall.y);
				mFall.y +=vy;
			}
		}
		mGR.mLSelect.GTime(gl);
		gameLogic();
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(!mGR.isPlay)
			{
				if(performAction(event)){
					mGR.mGameTime += System.currentTimeMillis();
					mGR.isPlay = true;
				}
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				performAction(event);
				
			}

			
		}
		return true;
	}
	boolean performAction(MotionEvent event)
	{
		if(Group.CircRectsOverlap(0.72f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
//			mGR.mGameTime -= System.currentTimeMillis();
//			M.GameScreen = M.GAMEHELP;
			return false;
		}
		if(Group.CircRectsOverlap(0.9f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
//			mGR.mGameTime -= System.currentTimeMillis();
//			M.GameScreen = M.GAMELVLPUSE;
			return false;
		}
		for(int i=0;i<mObj.length;i++){
			if(mObj[i].move==1){
				mObj[i].move = 2;
				M.sound1(GameRenderer.mContext, R.drawable.l_7_building_fall);
			}
		}
		return true;
	}
}
