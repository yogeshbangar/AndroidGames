package com.hututu.games.popthefootball;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Level10 {
	
	boolean fall;
	
	final byte anim1[] = {0,1,2,3,4,4,3,2,1,0};
	final byte anim2[] = {5,6,7,7,6,5};
	
	int Counter =0;
	int mSel;
	int acc;
	
	float rx[] = new float[2];
//	float bx[] = new float[4];
//	float cx[] = new float[3];
	float Speed = -.07f;
	float SpeedChange= 0;
	float fortDis;
	final float st = .28f;
	final float sdv =.28f;
	
	Vecter	mVecter[];
	TenP	mPlayer  =new TenP();
	
	SimplePlane	mTex_Raod,mTex_Up,mTex_Down,mTex_Cone,mTex_Speed[],mTex_SpeedUp;
	GameRenderer mGR;
	public Level10(GameRenderer _GameRenderer)
	{
		mGR =_GameRenderer;
		mTex_Raod		= GameRenderer.add("l10/bg10.png");
		mTex_Up			= GameRenderer.add("l10/up_arrow.png");
		mTex_Down		= GameRenderer.add("l10/dwon_arrow.png");
		mTex_Cone		= GameRenderer.add("l10/hurdal.png");
		
		mTex_Speed		= new SimplePlane[4];
		Bitmap b				= GameRenderer.LoadImgfromAsset("l10/speeder.png");
		for(int i=0;i<mTex_Speed.length;i++)
			mTex_Speed[i]=GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/mTex_Speed.length, 0, b.getWidth()/mTex_Speed.length,  b.getHeight(), null, true));
		
		mVecter			= new Vecter[20];
		for(int i=0;i<mVecter.length;i++)
			mVecter[i]	= new Vecter();
		
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
		fortDis = 0;
//		Itmno =0;
		fall =false ;
		mPlayer.set(-.7f,0.00f,1);
		for(int i=0;i<rx.length;i++){
			rx[i] = -1+i*mTex_Raod.width();
//			cx[i] = -1+i*mTex_BG[2].width();
		}
//		for(int i=0;i<bx.length;i++){
//			bx[i] = -1+i*mTex_BG[0].width();
//		}
		Speed = -.07f;
		
		for(int i=0;i<mVecter.length;i++){
			mVecter[i].vy = M.mRand.nextInt(3);
			mVecter[i].set(i*1.3f, (st-mVecter[i].vy*sdv), 0);
			if(i == 6)
				mVecter[i].no = 5;
			if(i==7){
				mVecter[i].no = 6;
				mVecter[i].y=mVecter[i==0?mVecter.length-1:i-1].y;
			}
		}
	}
	
	void setVector(int no)
	{
		Counter =0;
		int j = no == 0 ?mVecter.length-1:no-1;
		mVecter[no].vy = M.mRand.nextInt(3);
		if(mGR.mScore>60){
			float dd = (mGR.mScore-60)*.003f;
			dd = 1.5f-(dd>.6?0.6f:dd);
			mVecter[no].set(mVecter[j].x+dd, (st-mVecter[no].vy*sdv), 0);
			if(no%3!=0 && no>10)
			{
				if(no==5||no==14){
					mVecter[no].set(mVecter[j].x+.1f, mVecter[j].y, 0);
					if(mVecter[j].vy == 0)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?1:2;
						mVecter[no].y  = (st-mVecter[no].vy*sdv);
					}
					if(mVecter[j].vy == 1)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?0:2;
						mVecter[no].y= (st-mVecter[no].vy*sdv);
					}
					if(mVecter[j].vy == 2)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?1:0;
						mVecter[no].y= (st-mVecter[no].vy*sdv);
					}
					
				}
				else
				{
					mVecter[no].set(mVecter[j].x+.5f, mVecter[j].y, 0);
					mVecter[no].vy = mVecter[j].vy;
				}
			}
			
		}
		else if(mGR.mScore>20){
			mVecter[no].set(mVecter[j].x+1.3f, (st-mVecter[no].vy*sdv), 0);
			if(no%2==1 && no>10)
			{
				mVecter[no].set(mVecter[j].x+.5f, mVecter[j].y, 0);
				mVecter[no].vy = mVecter[j].vy;
			}
		}else{
			mVecter[no].set(mVecter[j].x+1.0f, (st-mVecter[no].vy*sdv), 0);
		}
		if(no == 0 || no == 8)
			mVecter[no].no = 5;
		if(no == 1 || no == 9){
			mVecter[no].no = 6;
			mVecter[no].y=mVecter[j].y;
			mVecter[no].vy = mVecter[j].vy;
		}
		if(no == 2 || no == 10)
		{
			if(mVecter[j].vy == 0)
			{
				mVecter[no].vy = M.mRand.nextBoolean()?1:2;
				mVecter[no].y  = (st-mVecter[no].vy*sdv);
			}
			if(mVecter[j].vy == 1)
			{
				mVecter[no].vy = M.mRand.nextBoolean()?0:2;
				mVecter[no].y= (st-mVecter[no].vy*sdv);
			}
			if(mVecter[j].vy == 2)
			{
				mVecter[no].vy = M.mRand.nextBoolean()?1:0;
				mVecter[no].y= (st-mVecter[no].vy*sdv);
			}
		}
	}
	void GameLogic(){
		mPlayer.update(st+.1f,sdv);
		for(int i=0;i<rx.length;i++)
		{
			rx[i]  += Speed;
		}
		for(int i=0;i<rx.length;i++){
			if(rx[i]<-2)
				rx[i] = rx[i==0?rx.length-1:i-1] + mTex_Raod.width();
		}
		
		
		for(int i=0;i<mVecter.length;i++){
			if(mVecter[i].no>-1 && Group.CirCir(mPlayer.x, mPlayer.y-.07f, .05f, mVecter[i].x,mVecter[i].y,.05f))
			{
				if(mVecter[i].no==6){
					Speed =-.12f;
					SpeedChange=+.001f;
					M.sound10(GameRenderer.mContext, R.drawable.l_10_speed);
				}
				else
				{
					if(mVecter[i].no==5){
						mGR.mLSelect.IncTime(4500);
						mGR.mLSelect.Animtin(mVecter[i].x,mVecter[i].y);
					}
					if(mVecter[i].no<5){
						SpeedChange =-.001f;
						Speed =-.02f;
						fall = true; 
						M.sound2(GameRenderer.mContext, R.drawable.l_10_hurdel_collison);
					}
					mVecter[i].no =-1;
				}
				
			}
			mVecter[i].x += Speed;
			if(mVecter[i].x < -1.2f)
				setVector(i);
		}
		if(SpeedChange!=0)
		{
			if(SpeedChange>0 && Speed<-.07f){
				Speed+=SpeedChange;
				if(Speed>-.07f){
					Speed = -.07f;
					SpeedChange =0;
				}
			}
			if(SpeedChange<0 && Speed>-.07f){
				Speed+=SpeedChange;
				if(Speed<-.07f){
					Speed = -.07f;
					SpeedChange =0;
				}
			}
		}
		fortDis-= Speed;
		if(fortDis>=1){
			mGR.Total[mGR.mLSelect.mGameSel]++;
			mGR.mScore++;
			fortDis=0;
		}
		mGR.mLSelect.Achievement(fall, 0);
		
	}
	void Draw_GamePlay(GL10 gl)
	{
		Counter++;
		for(int i=0;i<rx.length;i++){
			Group.DrawTexture(gl, mTex_Raod 	, rx[i], 0);
			
		}
		for(int i=0;i<mVecter.length;i++){
			if(mVecter[i].x>-1.2f && mVecter[i].x <1.2f && mVecter[i].no>=0){
				if(mVecter[i].no == 5){//time
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mVecter[i].x,mVecter[i].y-0.07f);
				}
				else if(mVecter[i].no == 6){
					Group.DrawTexture(gl, mTex_Speed[Counter%4], mVecter[i].x,mVecter[i].y-.14f);
				}
				
				
			}
		}
//		Group.DrawTexture(gl, mGR.mLFour.mTex_Peng[Counter%mGR.mLFour.mTex_Peng.length], mPlayer.x, mPlayer.y-.1f);
		Group.DrawTextureS(gl, mGR.mLSelect.mTex_Rank[Counter%mGR.mLSelect.mTex_Rank.length], mPlayer.x, mPlayer.y-.1f,.4f);
		for(int i=0;i<mVecter.length;i++){
			if(mVecter[i].x>-1.2f && mVecter[i].x <1.2f && mVecter[i].no>=0){
				/*if(mVecter[i].no == 5){//time
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mVecter[i].x,mVecter[i].y-0.07f);
				}
				else if(mVecter[i].no == 6){
					Group.DrawTexture(gl, mTex_Speed[Counter%4], mVecter[i].x,mVecter[i].y-.14f);
				}*/
				if(mVecter[i].no != 5&&mVecter[i].no != 6){
					Group.DrawTexture(gl, mTex_Cone, mVecter[i].x,mVecter[i].y);
				}
				
			}
		}
		Group.DrawTransScal(gl, mTex_Up , -.8f, -.8f,mSel == 1?1.1f:1,mSel == 1?.5f:1);
		Group.DrawTransScal(gl, mTex_Down, 0.8f, -.8f,mSel == 2?1.1f:1,mSel == 2?.5f:1);
		
		
		
		
		if(mGR.isPlay && mGR.mGameTime>System.currentTimeMillis())
		{
			GameLogic();
		}
		mGR.mLSelect.Draw_Animtin(gl);
		mGR.mLSelect.GTime(gl);
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		mSel = 0;
		if(Group.CircRectsOverlap(-.8f, -.8f, mTex_Up.width(), mTex_Up.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
		{
			mSel = 1;
		}
		if(Group.CircRectsOverlap(0.8f, -.8f, mTex_Up.width(), mTex_Up.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
		{
			mSel = 2;
		}
		
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				switch (mSel) {
				case 1:
					if(mPlayer.no>0)
					{
						mPlayer.vy =-.094f;
						mPlayer.no--;
						M.sound1(GameRenderer.mContext, R.drawable.l_10_up_down_aerrow);
					}
					break;
				case 2:
					if(mPlayer.no<2)
					{
						mPlayer.vy = .094f;
						mPlayer.no++;
						M.sound1(GameRenderer.mContext, R.drawable.l_10_up_down_aerrow);
					}
					break;
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
		if(MotionEvent.ACTION_UP == event.getAction())
			mSel = 0;
		return true;
	}
}