package com.hututu.games.popthefootball;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Level01 {
	byte arr[][];
	final byte FrogAnim[]={0,1,2,3,4,5,5,4,3,2,1,0};
	int counter = 0;
	int m,n;
	int ani;
	int in = 0;
	int anicount;
	int mSel;
	float mx,my;
	Animation 	mAni[];
	
	SimplePlane mTex_bg;
	SimplePlane[] mTex_Frog,mTex_Cap,mTex_Anim;
	GameRenderer mGR;
	public Level01(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
		mTex_Anim	= new SimplePlane[11];
		for(int i=0;i<mTex_Anim.length;i++)
			mTex_Anim[i]= GameRenderer.add("l1/blast"+i+".png");
		mTex_bg 		= GameRenderer.add("l1/l1.png");
		mTex_Cap		= new SimplePlane[2];
		mTex_Cap[0]		= GameRenderer.add("l1/cap0.png");
		mTex_Cap[1]		= GameRenderer.add("l1/cap1.png");
		Bitmap b		= GameRenderer.LoadImgfromAsset("l1/ball.png");
		mTex_Frog		= new SimplePlane[5];
		for(int i=0;i<mTex_Frog.length;i++)		{
			mTex_Frog[i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, (i)*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(), null, true));
		}
		mAni			= new Animation[5];
		for(int i =0;i<mAni.length;i++)
			mAni[i]		= new Animation();
		arr = new byte[4][8];//new Color[4][8];
		for(int i = 0;i<arr.length;i++)
		{
			arr[i]		= new byte[8];
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j]	= 0;
			}
		}
	}
	void GameContinue()
	{
		mGR.mGameTime += System.currentTimeMillis();
	}
	void GameResart()
	{
		M.stop();
		set(0);
		mGR.mLSelect.timeanim = 100;
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp =0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
	}
	void set(int no)
	{
		for(int i = 0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				arr[i][j] = 0;
			}
		}
		mGR.mLSelect.IncTime(1000);
		in = no;
		no%=frog.Level1.length;
//		arr = null;
//		arr = new byte[frog.Level1[no].length][frog.Level1[no][0].length];
		mx = (-frog.Level1[no][0].length*.25f*.5f)+.125f;
		my = (frog.Level1[no].length*.5f*.4f)-.25f;
		int getNO = M.mRand.nextInt(4)+1;
		for(int i = 0;i<frog.Level1[no].length;i++)
		{
			for(int j=0;j<frog.Level1[no][0].length;j++)
			{
				arr[i][j] = frog.Level1[no][i][j];
				if(getNO>0 && in>4 && M.mRand.nextBoolean()){
					arr[i][j] = 2;
					if(in>8)
						arr[i][j] = 3;
					getNO--;
				}
			}
		}
		counter = 0;
		ani = 0;
		setAni();
		for(int i =0;i<mAni.length;i++)
			mAni[i].i=100;
	}
	int Remain()
	{
		int co =0;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j] > 0)
					co++;
			}
		}
		return co;
	}
	
	void setAni()
	{
		ani =0;
		int co=Remain();
		anicount++;
		if(co>0)
			co = anicount%co;
		int ba =0;
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j] != 0)
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
	void SetAnim(float x,float y)
	{
		for(int i =0;i<mAni.length;i++)
		{
			if(mAni[i].i>=mTex_Anim.length)
			{
				mAni[i].set(x, y);
				break;
			}
		}
	}
	void DrawAnimation(GL10 gl)
	{
		for(int i =0;i<mAni.length;i++)
		{
			if(mAni[i].i<mTex_Anim.length)
			{
//				Group.DrawTransScal(gl, mTex_Smoke, mAni[i].x, mAni[i].y, mAni[i].z,mAni[i].t);
				Group.DrawTexture(gl, mTex_Anim[mAni[i].i], mAni[i].x, mAni[i].y);
				mAni[i].update();
			}
		}
	}
	/**~~~~~~~~~~~~~~~~~~~~~~Game Play One Start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	
	void Draw_GamePlay(GL10 gl)
	{
		counter++;
		Group.DrawTexture(gl, mTex_bg, 0, 0);
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j]>0){
					if(arr[i][j]>1 && arr[i][j]<4)
						Group.DrawTransScal(gl, mTex_Cap[arr[i][j]-2],mx+j*.25f,my-i*.5f,counter<5?counter*.2f:1,1);
					else if(i==m && j==n){
						Group.DrawTransScal(gl, mTex_Frog[0]/*[FrogAnim[ani%FrogAnim.length]]*/, mx+j*.25f,my-i*.5f,counter<5?counter*.2f:1,1);
					}
					else{
						Group.DrawTransScal(gl, mTex_Frog[0]/*[0]*/, mx+j*.25f,my-i*.5f,counter<5?counter*.2f:1,1);
					}
				}
			}
		}
		if(counter%2==0)
			ani++;
		if(ani>=FrogAnim.length)
			setAni();
		if(M.GameScreen != M.GAMELVLPUSE && M.GameScreen != M.GAMEHELP)
			mGR.mLSelect.GTime(gl);
		DrawAnimation(gl);
		
	}
	int Draw_AniChar(GL10 gl,float xx,float yy, boolean a)
	{
		counter++;
		if(a)
			Group.DrawTexture(gl, mTex_Frog[0]/*[FrogAnim[ani%FrogAnim.length]]*/, xx,yy);
		else
			Group.DrawTexture(gl, mTex_Frog[0]/*[0]*/, xx,yy);
		if(counter%2==0)
			ani++;
		return ani;
	}
	int Draw_AniChar(GL10 gl,float xx,float yy, boolean a,float scal)
	{
		counter++;
		if(a)
			Group.DrawTextureS(gl, mTex_Frog[0], xx,yy,scal);
		else
			Group.DrawTextureS(gl, mTex_Frog[0], xx,yy,scal);
		if(counter%2==0)
			ani++;
		return ani;
	}
	int Draw_AniCharColor(GL10 gl,float xx,float yy,int no, boolean a,int co)
	{
		if (no >= 0 && no < mTex_Frog.length) {
			if (a) {
				Group.DrawTransScal(gl, mTex_Frog[no]/*[FrogAnim[ani % FrogAnim.length]]*/, xx, yy, co < 5 ? co * .2f : 1, 1);
				if (counter % 2 == 0) {ani++;}counter++;
			} else
				Group.DrawTransScal(gl, mTex_Frog[no]/*[0]*/, xx, yy, co < 5 ? co * .2f : 1, 1);
		}
		
		return ani;
	}
	
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.ClockCount < 2)
		{
			if(!mGR.isPlay) 
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
				performAction(event);
			}
			else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){/*mGR.mLSelect.ClockScreen(event);*/}
			}
			else{
				performAction(event);
			}
			
		}
		return true;
	}
	void performAction(MotionEvent event)
	{

		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				if(arr[i][j]>0){
					if(Group.CirCir(mx+j*.25f,my-i*.5f, .1f, Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()),.04f))
					{
						arr[i][j]--;
						if(arr[i][j]==0){
							SetAnim(mx+j*.25f,my-i*.5f);
							M.sound1(GameRenderer.mContext, R.drawable.l_1_blast);
						}
						else
						{
							M.sound2(GameRenderer.mContext, R.drawable.l_1_ice_blast);
						}
						mGR.mScore++;
						mGR.Total[mGR.mLSelect.mGameSel]++;
						mGR.mLSelect.Achievement(false,0);
					}
				}
			}
		}
		if(Remain()==0)
		{
			set(in+1);
		}
	
	}
}
