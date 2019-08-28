package com.hututu.game.kingpenguin;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class LevelTwelve {
	
	boolean fall;
	
	int counter=0,C1,C2;
	int cndySpeed =30;
	Vecter	mStep = new Vecter();
	Vecter Woter[];
	Twelve mCandy[];
	Twelve mWatch;
	
	SimplePlane mTex_BG,mTex_Step,/*mTex_Sky,*/mTex_Penguin[],mTex_PSplash[];
	GameRenderer mGR;
	public LevelTwelve(GameRenderer _GameRenderer)
	{
		mGR =_GameRenderer;
		mTex_BG			= GameRenderer.add("l12/l12.png");
		mTex_Step		= GameRenderer.add("l12/2.png");
		mTex_Penguin	= new SimplePlane[6];
		Bitmap b		= GameRenderer.FlipHorizontal(GameRenderer.LoadImgfromAsset("l6/eating.png"));
		int k=5;
		for (int i = 2; i < 8 && k>=0; i++) {
			mTex_Penguin[k] = GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
			k--;
		}
		b.recycle();
		mTex_PSplash	= new SimplePlane[6];
		b		= GameRenderer.FlipHorizontal(GameRenderer.LoadImgfromAsset("l6/splash.png"));
		k=5;
		for (int i = 2; i < 8 && k>=0; i++) {
			mTex_PSplash[k] = GameRenderer.addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
			k--;
		}
		
		mWatch = new Twelve();
		mCandy = new Twelve[7];
		for (int i = 0; i < mCandy.length; i++)
			mCandy[i] = new Twelve();
		Woter	= new Vecter[3];
		for (int i = 0; i < Woter.length; i++)
			Woter[i] = new Vecter();
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
		cndySpeed =30;
		C1 = C2 = 100;
		fall = false;
		counter = 0;
		mStep.set(0, -.45f, 0);
		for (int i = 0; i < mCandy.length; i++) {
			mCandy[i].x = 100;
		}

		for (int i = 0; i < Woter.length; i++) {
			Woter[i].set(0, 0, 100);
		}
		mWatch.y = -100;
	}
	void calulation()
	{
		mGR.mScore++;
		mGR.Total[mGR.mLSelect.mGameSel]++;
		mGR.mLSelect.Achievement(fall, 0);
		
	}
	void SetWoterAnim(float _x,float _y)
	{
		M.sound2(GameRenderer.mContext, R.drawable.l_2_penguin_in_water);
		for (int i = 0; i < Woter.length; i++) {
			if (Woter[i].no >= mGR.mLTwo.mTex_JumpAnim.length) {
				Woter[i].set(_x, _y, 0);
				break;
			}
		}
	}
	void GameLogic()
	{
		if(counter%100==0){
			mWatch.woterset();
		}
		if(mWatch.y > -2f)
			mWatch.y+=mWatch.vy;
		if(mStep.x >-.45f && mStep.no ==1)
			mStep.x-=.05f;
		if(mStep.x < .45f && mStep.no ==2)
			mStep.x+=.05f;
		for(int i=0;i<mCandy.length;i++){
			if(mCandy[i].x <= 1.1){
				mCandy[i].update();
				if(mCandy[i].y<-.3f &&!mCandy[i].woter)
				{
					if(Group.CircRectsOverlap(mStep.x,mStep.y, mTex_Step.width()*.4f, mTex_Step.Height()*.4f, mCandy[i].x, mCandy[i].y, mGR.mLsix.mTex_Fruit[0].Height()*.4f)){
						mCandy[i].reset();
						mStep.vy =-.02f;
						mStep.y =-.455f;
						calulation();
						M.sound1(GameRenderer.mContext, R.drawable.jump_12);
					}
					else{
						SetWoterAnim(mCandy[i].x, mCandy[i].y-.1f);
						mCandy[i].woter = true;
						mCandy[i].vy =-.02f;
						mCandy[i].vx =.002f;
						fall = true;
					}
				}
			}
			if(!mCandy[i].woter && mCandy[i].x > 0.7 && mCandy[i].x < 0.9)
			{
				mCandy[i].x=1.2f;
				M.sound9(GameRenderer.mContext, R.drawable.l_6_eating);
				C1 =0;
			}
		}
		for(int i=0;i<mCandy.length&&counter%cndySpeed==0;i++){
			if(mCandy[i].x > 1.1 || mCandy[i].y < -1.1)
			{
				mCandy[i].set();
				mCandy[i].candy =(byte)((mGR.mScore/50)%5);
				if(mGR.mScore%10==0 && cndySpeed>20)
					cndySpeed--;
				break;
			}
		}
		
		if(mWatch.y<-.3f &&!mWatch.woter)
		{
			if(Group.CircRectsOverlap(mStep.x,mStep.y, mTex_Step.width()*.4f, mTex_Step.Height()*.4f, mWatch.x, mWatch.y, mGR.mLsix.mTex_Fruit[0].Height()*.4f)){
				mWatch.y = -110;
				mGR.mLSelect.IncTime(4000);
				mGR.mLSelect.Animtin(mStep.x,mStep.y);
			}
			else{
				
				SetWoterAnim(mWatch.x, mWatch.y);
				mWatch.vy =-.02f;
				mWatch.woter = true;
			}
		}
		if(mStep.y<-.45f){
			mStep.y+=mStep.vy;
			mStep.vy+=.01f;
			if(mStep.y>-.45f)
				mStep.y=-.45f;
		}
		counter++;
	}
	void Draw_GamePlay(GL10 gl)
	{
		
//		mTex_Sky.drawScal(gl, 0, .5f, 7.3f, 1.0f);
		Group.DrawTexture(gl, mTex_BG, 0f, 0f);
//		Group.DrawTexture(gl, mTex_BG[1], -.7f, -.25f);
//		Group.DrawTexture(gl, mTex_BG[2], 0.7f, -.25f);
		Group.DrawTexture(gl, mTex_Step,mStep.x,mStep.y);
		
		if(C1<3)
			Group.DrawTexture(gl, mTex_Penguin[C1],0.84f,0.4f);
		else if(C1<30)
			Group.DrawTexture(gl, mTex_Penguin[(C1%3)+3],0.84f,0.4f);
		else if((C1/mGR.mLOne.FrogAnim.length)%2==0)
			Group.DrawTexture(gl, mTex_PSplash[mGR.mLOne.FrogAnim[C1%mGR.mLOne.FrogAnim.length]],0.84f,0.4f);
		else
			Group.DrawTexture(gl, mTex_PSplash[0],0.84f,0.4f);
		C2++;
		if(C2%3==0)
			C1++;
		
		for(int i=0;i<mCandy.length;i++){
			if(mCandy[i].x <= 1.1){
				if(mCandy[i].y>=-.3f)
					Group.DrawTexture(gl, mGR.mLsix.mTex_Fruit[mCandy[i].candy],mCandy[i].x,mCandy[i].y);
				else
					Group.DrawTransScal(gl, mGR.mLsix.mTex_Fruit[mCandy[i].candy],mCandy[i].x,mCandy[i].y,1,.5f);
			}
		}
		if(mWatch.y > -2f)
			Group.DrawTransScal(gl, mGR.mLSelect.mTex_BWatch,mWatch.x,mWatch.y,1,mWatch.y>=-.3f?1:.5f);
		
		
		for(int i=0;i<Woter.length;i++){
			if(Woter[i].no < mGR.mLTwo.mTex_JumpAnim.length){
				Group.DrawTexture(gl, mGR.mLTwo.mTex_JumpAnim[Woter[i].no], Woter[i].x,Woter[i].y);
				if(C2%2==0)
					Woter[i].no++;
			}
		}
		Group.DrawTransScal(gl, mGR.mLFour.mTex_Left , -.8f, -.8f,mStep.no == 1?1.1f:1,mStep.no == 1?.5f:1);
		Group.DrawTransScal(gl, mGR.mLFour.mTex_Right, 0.8f, -.8f,mStep.no == 2?1.1f:1,mStep.no == 2?.5f:1);
		mGR.mLSelect.GTime(gl);
		if(mGR.mGameTime > System.currentTimeMillis())
				GameLogic();
		
//		Group.DrawTexture(gl, mGR.mLsix.mTex_Fruit[0],Group.sx,Group.sy);
		mGR.mLSelect.Draw_Animtin(gl);
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		mStep.no = 0;
		if(mGR.timesUp < 5 && mGR.ClockCount < 5){
			if(Group.CircRectsOverlap(-.8f, -.8f, mGR.mLFour.mTex_Left.width(), mGR.mLFour.mTex_Left.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
			{
				mStep.no = 1;
			}
			if(Group.CircRectsOverlap(0.8f, -.8f, mGR.mLFour.mTex_Left.width(), mGR.mLFour.mTex_Left.Height(), Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .01))
			{
				mStep.no = 2;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			mStep.no = 0;
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}
			/*else if(mGR.mGameTime<System.currentTimeMillis())
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
			
		}
		return true;
	}

}
