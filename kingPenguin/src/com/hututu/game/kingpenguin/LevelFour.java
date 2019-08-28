package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;




import android.graphics.Bitmap;
import android.view.MotionEvent;

public class LevelFour {
	final byte ani[] = {0,1,2,3,3,2,1,0};
	
	byte animCount;
	
	int Counter  =0;
	int Counter2  =0;
	int ballcount;
	int Watch;
	int mSel;
	
	float dir;
	float move;
	float mBGY;
	float distance =0;
	
	Vecter	mBall[];
	Vecter	Player;
	Vecter	PMove;
	
	ObjectF[]	mObject;
	
	SimplePlane mTex_Peng[],mTex_Face[],mTex_Copter[],mTex_Object[];
	SimplePlane mTex_Ball,mTex_Left,mTex_Right,mTex_BG[],mTex_Pengu[];
	GameRenderer mGR;
	public LevelFour(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		
		Bitmap b		= GameRenderer.LoadImgfromAsset("l4/fly0.png");
		mTex_Peng		= new SimplePlane[8];
		for(int i=0;i<mTex_Peng.length;i++)
			mTex_Peng[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Peng.length, 0,b.getWidth()/mTex_Peng.length, b.getHeight(), null, true));
		
		b.recycle();
		mTex_Face		= new SimplePlane[12];
		b		= GameRenderer.LoadImgfromAsset("l4/fly1.png");
		for(int i=0;i<mTex_Face.length;i++)
			mTex_Face[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Face.length, 0,b.getWidth()/mTex_Face.length, b.getHeight(), null, true));
		
		b.recycle();
		mTex_Copter		= new SimplePlane[2];
		b		= GameRenderer.LoadImgfromAsset("l4/copter.png");
		for(int i=0;i<mTex_Copter.length;i++)
			mTex_Copter[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Copter.length, 0,b.getWidth()/mTex_Copter.length, b.getHeight(), null, true));
		
		b.recycle();
		mTex_Pengu			= new SimplePlane[4];
		b		= GameRenderer.LoadImgfromAsset("l5/underwater.png");
		for(int i=0;i<mTex_Pengu.length;i++)
		{
			mTex_Pengu[i]=GameRenderer.addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/4, 0,b.getWidth()/4, b.getHeight(), null, true));
		}
		
		mTex_Ball		= GameRenderer.add("l4/bubble.png");
		mTex_Left		= GameRenderer.add("l4/move_left.png");
		mTex_Right		= GameRenderer.add("l4/move_right.png");

		mTex_BG			= new SimplePlane[7];
		mTex_BG[0]		= GameRenderer.add("l4/level4bg.png");
		mTex_BG[1]		= GameRenderer.add("l4/cloudsky.png");
		mTex_BG[2]		= GameRenderer.add("l4/daysky.png");
		mTex_BG[3]		= GameRenderer.add("l4/moonsky.png");
		mTex_BG[4]		= GameRenderer.add("l4/nightsky.png");
		mTex_BG[5]		= GameRenderer.add("l4/planetsky.png");
		mTex_BG[6]		= GameRenderer.add("l4/galaxysky.png");
		
		mTex_Object		= new SimplePlane[7];		//0,1,//4
		mTex_Object[0]	= GameRenderer.add("l4/day_plane_1.png");//2
		mTex_Object[1]	= GameRenderer.add("l4/moon.png"); //3
		mTex_Object[2]	= GameRenderer.add("l4/planet_0.png");//5
		mTex_Object[3]	= GameRenderer.add("l4/planet_1.png");//5
		mTex_Object[4]	= GameRenderer.add("l4/galaxy_0.png");//6
		mTex_Object[5]	= GameRenderer.add("l4/galaxy_1.png");//6
		mTex_Object[6]	= GameRenderer.add("l4/stars.png");//6
		
		mObject				= new ObjectF[8];
		for(int i=0;i<mObject.length;i++)
		{
			mObject[i]		= new ObjectF();
		}
		
		ballcount		= M.mRand.nextInt(7)+2;
		dir				= .2f;
		mBall			= new Vecter[20];
		mBall[0]		= new Vecter(0,0,0);
		for(int i =1;i<mBall.length;i++){
			mBall[i]	= new Vecter(mBall[i-1].x+dir,-1+i*.22f,0);
			ballcount--;
			if(ballcount<=0 || mBall[i].x > .7f||mBall[i].x <-.7f)
			{
				dir*=-1;
				ballcount		= M.mRand.nextInt(3)+2;
			}
		}
		Player			= new Vecter(0,-.4f,0);
		PMove			= new Vecter(0,0,0);
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
		mBGY = 0.0f;
		distance =0;
		move =-.01f;
		Counter  =0;
		Watch = 0;
		ballcount		= M.mRand.nextInt(7)+2;
		dir				= .2f;
		mBall[0].set(0,0,0);
		for(int i =1;i<mBall.length;i++){
			mBall[i].set(mBall[i-1].x+dir,i*.22f,0);
			ballcount--;
			if(ballcount<=0 || mBall[i].x > .7f||mBall[i].x <-.7f)
			{
				dir*=-1;
				ballcount		= M.mRand.nextInt(3)+2;
			}
		}
		Player.set(0,-.4f,0);
		PMove.set(0,0,0);
		
		mObject[0].set(M.mRand.nextFloat(), 2.12f*3		, .011f);//2
		mObject[1].set(M.mRand.nextFloat(), 2.12f*4		, .012f);//3
		mObject[2].set(M.mRand.nextFloat(), 2.12f*5		, .013f);//5
		mObject[3].set(M.mRand.nextFloat(), 2.12f*5.5f	, .014f);//5
		mObject[4].set(M.mRand.nextFloat(), 2.12f*6		, .015f);//6
		mObject[5].set(M.mRand.nextFloat(), 2.12f*6.5f	, .016f);//6
		mObject[6].set(0, 2.12f*7f	, .000f);//6
		mObject[7].set(0,16.84f		, .000f);//6
		
	}
	void gameLogic()
	{
		mBGY+=move*.1f;
		distance -=move;
		for(int i =0;i<mBall.length;i++){
			mBall[i].y+=move;
			if(mBall[i].off && 
					Group.CircRectsOverlap(Player.x,Player.y, mTex_Peng[0].width()*.5f, mTex_Peng[0].Height()*.6f, mBall[i].x,mBall[i].y, mTex_Ball.width()*.5))
			{
				mBall[i].off = false;
				if(mBall[i].no==1){
					mGR.mLSelect.IncTime(2000);
				}
				else
					M.sound2(GameRenderer.mContext, R.drawable.l_4_blast);
				mGR.mScore++;
				mGR.Total[mGR.mLSelect.mGameSel]++;
				mGR.mLSelect.Achievement(false,distance);
			}
		}
		for(int i =0;i<mBall.length;i++){
			if(mBall[i].y < -1.3f){
				mBall[i].set(mBall[i==0?mBall.length-1:i-1].x+dir,mBall[i==0?mBall.length-1:i-1].y+.22f,Watch%20==0?1:0);
				ballcount--;
				if(ballcount<=0 || mBall[i].x > .7f||mBall[i].x <-.7f)
				{
					dir*=-1;
					ballcount		= M.mRand.nextInt(7)+2;
				}
				Watch++;
			}
		}
		move =PMove.y;
		if(PMove.y <0)
			PMove.y+=.01f;
		else
			PMove.y =0;
		
		Player.x+=PMove.x;
		if(Player.x>.9f)
			Player.x = .9f;
		if(Player.x<-.9f)
			Player.x = -.9f;
		if(PMove.x>0)
		{
			PMove.x -=.03f;
			if(PMove.x <=0)
				PMove.x = 0;
		}
		if(PMove.x<0)
		{
			PMove.x +=.03f;
			if(PMove.x >=0)
				PMove.x = 0;
		}
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		Counter++;
		for(int i=0;i<mTex_BG.length;i++)
		{
			if((i*2.12f+mBGY)>-2.12f && (i*2.12+mBGY)<2.12f)
			{
				if(i==0)
					mTex_BG[0].drawScal(gl, 0, i*2.12f+mBGY, 1,1);
				else
					mTex_BG[i].drawScal(gl, 0, i*2.12f+mBGY,6.25f,1);
			}
		}
		
		for(int i=0;i<mObject.length;i++)
		{
			mObject[i].update(move*.1f);
			if(mObject[i].y>-2f && mObject[i].y < 2f){
				Group.DrawTexture(gl, mTex_Object[i==7?6:i], mObject[i].x, mObject[i].y);
			}
			if(i==6||i==7){
				if(mObject[i].y<-2f)
					mObject[i].y = 2f;
			}
		}
		for(int i =0;i<mBall.length;i++)
		{
			if(mBall[i].y>-1.2f && mBall[i].y < 1.2f && mBall[i].off)
			{
				if(mBall[i].no == 0)
					Group.DrawTexture(gl, mTex_Ball, mBall[i].x, mBall[i].y);
				else
					Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mBall[i].x, mBall[i].y);
			}
		}
		Group.DrawTexture(gl, mTex_Copter[Counter%2], Player.x, Player.y+.3f);
		if(PMove.y == 0)
			Group.DrawTexture(gl, mTex_Peng[ani[Counter%ani.length]], Player.x, Player.y);
		else
			Group.DrawTexture(gl, mTex_Peng[ani[Counter%ani.length]+4], Player.x, Player.y);
		
		if((Counter2/ani.length)%3==0)
			Group.DrawTexture(gl, mTex_Face[ani[Counter2%ani.length]+animCount*4], Player.x+.02f, Player.y+.07f);
		else
			Group.DrawTexture(gl, mTex_Face[0], Player.x+.02f, Player.y+.07f);
		
		if(Counter2%ani.length == 0){
			animCount=(byte)M.mRand.nextInt(3);
		}
		if(Counter%3==0)
			Counter2++;
		Group.DrawTexture(gl, mTex_Left, -.8f,-.8f);
		Group.DrawTexture(gl, mTex_Right,0.8f,-.8f);
		mGR.mLSelect.GTime(gl);
		gameLogic();
	}
	
	void Draw_Car(GL10 gl,float x,float y)
	{
		Group.DrawTexture(gl, mTex_Copter[Counter%2], x, y+.3f);
		Group.DrawTexture(gl, mTex_Peng[ani[Counter%ani.length]], x, y);
		if((Counter2/ani.length)%3==0)
			Group.DrawTexture(gl, mTex_Face[ani[Counter2%ani.length]+animCount*4], x+.02f, y+.07f);
		else
			Group.DrawTexture(gl, mTex_Face[0], x+.02f, y+.07f);
		if(Counter2%ani.length == 0){
			animCount=(byte)M.mRand.nextInt(3);
		}
		if(Counter%3==0)
			Counter2++;
		Counter++;
	}
	
	
	void Draw_Five(GL10 gl,float x,float y)
	{
		Group.DrawTexture(gl, mTex_Pengu[ani[Counter%ani.length]], x, y);
		if((Counter2/ani.length)%3==0)
			Group.DrawTexture(gl, mTex_Face[ani[Counter2%ani.length]+animCount*4], x+.025f, y+0.11f);
		else
			Group.DrawTexture(gl, mTex_Face[0], x+.025f, y+.11f);
		if(Counter2%ani.length == 0){
			animCount=(byte)M.mRand.nextInt(3);
		}
		if(Counter%3==0)
			Counter2++;
		Counter++;
	}
	
	
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		int mSel = 0;
		if(Group.CircRectsOverlap(-.80f,-.8f, mTex_Right.width()*.7f, mTex_Right.Height()*.7f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .05f))
		{
			mSel = 1;
		}
		if(Group.CircRectsOverlap(0.80f,-.8f, mTex_Right.width()*.7f, mTex_Right.Height()*.7f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .05f))
		{
			mSel = 2;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}
			if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				switch (mSel) {
				case 1:
					PMove.y = -.07f;
					PMove.x = -.08f;
					break;
				case 2:
					PMove.y = -.07f;
					PMove.x = 0.08f;
					break;
				}
				mSel = 0;
			}
		}
		if(MotionEvent.ACTION_UP ==event.getAction())
		{
			/*if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){mGR.mLSelect.ClockScreen(event);}
			}
			if(Group.CircRectsOverlap(0.72f	,0.86f, mGR.mTex_HelpB.width()*.4f, mGR.mTex_HelpB.Height()*.4f,
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
