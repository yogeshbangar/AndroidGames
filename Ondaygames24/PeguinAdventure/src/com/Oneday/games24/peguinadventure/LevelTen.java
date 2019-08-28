package com.Oneday.games24.peguinadventure;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class LevelTen {
	
	boolean fall;
	
	final byte anim1[] = {0,1,2,3,4,4,3,2,1,0};
	final byte anim2[] = {5,6,7,7,6,5};
	
	int Counter =0;
	int mSel;
	int acc;
	
	float rx[] = new float[3];
	float bx[] = new float[4];
	float cx[] = new float[3];
	float Speed = -.07f;
	float SpeedChange= 0;
	float fortDis;
	
	Vecter	mVecter[];
	TenP	mPlayer  =new TenP();
	
	SimplePlane	mTex_Raod,mTex_BG[],mTex_Up,mTex_Down,mTex_Cone,mTex_Player[],mTex_Speed[],mTex_SpeedUp,mTex_Sky;
	GameRenderer mGR;
	public LevelTen(GameRenderer _GameRenderer)
	{
		mGR =_GameRenderer;
		mTex_Raod		= GameRenderer.add("l10/road.png");
		mTex_BG			= new SimplePlane[4];
		mTex_BG[0]		= GameRenderer.add("l10/m1.png");
		mTex_BG[1]		= GameRenderer.add("l10/m2.png");
		mTex_BG[2]		= GameRenderer.add("l10/0.png");
		mTex_BG[3]		= GameRenderer.add("l10/1.png");
		mTex_Sky		= GameRenderer.add("l10/l10.png");
		mTex_Up			= GameRenderer.add("l10/up_arrow.png");
		mTex_Down		= GameRenderer.add("l10/dwon_arrow.png");
		mTex_Cone		= GameRenderer.add("l10/hurdal.png");
		
		mTex_Speed		= new SimplePlane[4];
		Bitmap b				= GameRenderer.LoadImgfromAsset("l10/speeder.png");
		for(int i=0;i<mTex_Speed.length;i++)
			mTex_Speed[i]=GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/mTex_Speed.length, 0, b.getWidth()/mTex_Speed.length,  b.getHeight(), null, true));
		
		b.recycle();
		mTex_Player		= new SimplePlane[8];
		b				= GameRenderer.LoadImgfromAsset("l10/swing.png");
		for(int i=0;i<mTex_Player.length;i++)
			mTex_Player[i]= GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/mTex_Player.length, 0, b.getWidth()/mTex_Player.length,  b.getHeight(), null, true));
		
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
			cx[i] = -1+i*mTex_BG[2].width();
		}
		for(int i=0;i<bx.length;i++){
			bx[i] = -1+i*mTex_BG[0].width();
		}
		Speed = -.07f;
		
		for(int i=0;i<mVecter.length;i++){
			mVecter[i].vy = M.mRand.nextInt(3);
			mVecter[i].set(i*1.3f, (.21f-mVecter[i].vy*.28f), 0);
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
			mVecter[no].set(mVecter[j].x+dd, (.21f-mVecter[no].vy*.28f), 0);
			if(no%3!=0 && no>10)
			{
				if(no==5||no==14){
					mVecter[no].set(mVecter[j].x+.1f, mVecter[j].y, 0);
					if(mVecter[j].vy == 0)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?1:2;
						mVecter[no].y  = (.21f-mVecter[no].vy*.28f);
					}
					if(mVecter[j].vy == 1)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?0:2;
						mVecter[no].y= (.21f-mVecter[no].vy*.28f);
					}
					if(mVecter[j].vy == 2)
					{
						mVecter[no].vy = M.mRand.nextBoolean()?1:0;
						mVecter[no].y= (.21f-mVecter[no].vy*.28f);
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
			mVecter[no].set(mVecter[j].x+1.3f, (.21f-mVecter[no].vy*.28f), 0);
			if(no%2==1 && no>10)
			{
				mVecter[no].set(mVecter[j].x+.5f, mVecter[j].y, 0);
				mVecter[no].vy = mVecter[j].vy;
			}
		}else{
			mVecter[no].set(mVecter[j].x+1.0f, (.21f-mVecter[no].vy*.28f), 0);
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
				mVecter[no].y  = (.21f-mVecter[no].vy*.28f);
			}
			if(mVecter[j].vy == 1)
			{
				mVecter[no].vy = M.mRand.nextBoolean()?0:2;
				mVecter[no].y= (.21f-mVecter[no].vy*.28f);
			}
			if(mVecter[j].vy == 2)
			{
				mVecter[no].vy = M.mRand.nextBoolean()?1:0;
				mVecter[no].y= (.21f-mVecter[no].vy*.28f);
			}
		}
	}
	void GameLogic(){
		mPlayer.update();
		for(int i=0;i<rx.length;i++)
		{
			rx[i]  += Speed;
			cx[i]  += Speed/2;
		}
		for(int i=0;i<rx.length;i++){
			if(rx[i]<-1.8)
				rx[i] = rx[i==0?rx.length-1:i-1] + mTex_Raod.width();
			if(cx[i]<-1.8)
				cx[i] = cx[i==0?cx.length-1:i-1] + mTex_Raod.width();
		}
		
		for(int i=0;i<bx.length;i++)
			bx[i]  += Speed/4;
		for(int i=0;i<bx.length;i++){
			if(bx[i]<-1.8)
				bx[i] = bx[i==0?bx.length-1:i-1] + mTex_Raod.width();
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
						mGR.mLSelect.IncTime(4000);
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
		mTex_Sky.drawScal(gl, 0,0.5f, 7.3f, 1.0f);
		for(int i=0;i<bx.length;i++){
			Group.DrawTexture(gl, mTex_BG[i%2]	, bx[i], i%2==0?0.67f:0.72f);
		}
		for(int i=0;i<rx.length;i++){
			Group.DrawTexture(gl, mTex_Raod 	, rx[i], -.40f);
			Group.DrawTexture(gl, mTex_BG[i%2+2], cx[i], 0.57f);
			
		}
		for(int i=0;i<mVecter.length;i++){
			if(mVecter[i].x>-1.2f && mVecter[i].x <1.2f && mVecter[i].no>=0){
				if(mVecter[i].no == 5){//time
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mVecter[i].x,mVecter[i].y);
				}
				else if(mVecter[i].no == 6){
					Group.DrawTexture(gl, mTex_Speed[Counter%4], mVecter[i].x,mVecter[i].y-.07f);
				}
				else{
					Group.DrawTexture(gl, mTex_Cone, mVecter[i].x,mVecter[i].y);
				}
				
			}
		}
		Group.DrawTransScal(gl, mTex_Up , -.8f, -.8f,mSel == 1?1.1f:1,mSel == 1?.5f:1);
		Group.DrawTransScal(gl, mTex_Down, 0.8f, -.8f,mSel == 2?1.1f:1,mSel == 2?.5f:1);
		
		
		if(mGR.isPlay && mGR.mGameTime>System.currentTimeMillis())
		{
			GameLogic();
			Group.DrawTexture(gl, mTex_Player[anim1[Counter%anim1.length]], mPlayer.x, mPlayer.y-.1f);
		}
		else{
			if((Counter/anim2.length)%6==0){
				Group.DrawTexture(gl, mTex_Player[anim2[Counter%anim2.length]], mPlayer.x, mPlayer.y-.1f);
			}
			else
				Group.DrawTexture(gl, mTex_Player[5], mPlayer.x, mPlayer.y-.1f);
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
