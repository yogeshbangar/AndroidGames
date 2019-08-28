package com.hututu.game.archeryking;

import javax.microedition.khronos.opengles.GL10;

public class BGround {
	
	SimplePlane mTex_BG[];
	GameRenderer mGR = null;
	
	final float speed=.003f;
	float BG1,BG2,L0BG1,L0BG2,L1BG1,L1BG2;
	
	Tree mTree[];
	Tree mClud[];
	
	float Ang,rAng;
	int Walk;
	int sMove,svx=1;
	
	public BGround(GameRenderer GR)
	{
		mGR			= GR;
		mTex_BG		= new SimplePlane[14];
		mTex_BG[0]	= mGR.add("bg/tree2.png");
		mTex_BG[1]	= mGR.add("bg/tree0.png");
		mTex_BG[2]	= mGR.add("bg/tree1.png");
		mTex_BG[3]	= mGR.add("bg/tree3.png");
		mTex_BG[4]	= mGR.add("bg/tree4.png");
		mTex_BG[5]	= mGR.add("bg/cloud.png");
		mTex_BG[6]	= mGR.add("bg/bg0.jpg");
		mTex_BG[7]	= mGR.add("bg/bg1.jpg");
		mTex_BG[8]	= mGR.add("bg/game-background.jpg");
		mTex_BG[9]	= mGR.add("bg/layer0.png");
		mTex_BG[10]	= mGR.add("bg/layer1.png");
		mTex_BG[11]	= mGR.addRotate("bg/sun0.png");
		mTex_BG[12]	= mGR.addRotate("bg/sun1.png");
		mTex_BG[13]	= mGR.addRotate("bg/sun2.png");
		
		mTree		= new Tree[10];
		for(int i=0; i<mTree.length;i++)
		{
			mTree[i]=new Tree(-1f + i*.5f,M.mRand.nextInt(5));
		}
		mClud		= new Tree[3];
		for(int i=0; i<mClud.length;i++)
		{
			mClud[i]=new Tree(-1f + i*1f,M.mRand.nextInt(50)+50);
			mClud[i].y=M.mRand.nextFloat()%.4f+.4f;
		}
		BG1			= -1;
		BG2			= BG1 +mTex_BG[6].width()*2;
		L0BG1		= 0;
		L0BG2		= mTex_BG[9].width();
		L1BG1		= 0;
		L1BG2		= mTex_BG[10].width();
		
	}
	void update()
	{
		
		for(int i=0; i<mClud.length;i++)
		{
			mClud[i].x -=speed*1.2f; 
			if(mClud[i].x<-1.5f)
			{
				if(i==0)
					mClud[i].setxyI(mClud[mClud.length-1].x+1f,M.mRand.nextFloat()%.4f+.4f);
				else
					mClud[i].setxyI(mClud[i-1].x+1f,M.mRand.nextFloat()%.4f+.4f);
			}
		}
		Ang	+=.2f;
		rAng-=.2f;	
		
		if(M.GameScreen != M.GAMEPLAY)
		{
			BG1-=speed;
			BG2-=speed;
			if(BG1<-3.0f)
				BG1			= BG2 +mTex_BG[6].width()*2;
			if(BG2<-3.0f)
				BG2			= BG1 +mTex_BG[6].width()*2;
			
			for(int i=0; i<mTree.length;i++)
			{
				mTree[i].x -=speed*1.5f; 
				if(mTree[i].x<-1.5f)
				{
					if(i==0)
						mTree[i].x = mTree[mTree.length-1].x+.5f;
					else
						mTree[i].x = mTree[i-1].x+.5f;
				}
			}
			L0BG1-=speed*2.5f;
			L0BG2-=speed*2.5f;
			if(L0BG1<-2.5f)
				L0BG1			= L0BG2 +mTex_BG[9].width();
			if(L0BG2<-2.5f)
				L0BG2			= L0BG1 +mTex_BG[9].width();
			
			L1BG1-=speed*3.5f;
			L1BG2-=speed*3.5f;
			if(L1BG1<-2.5f)
				L1BG1			= L1BG2 +mTex_BG[9].width();
			if(L1BG2<-2.5f)
				L1BG2			= L1BG1 +mTex_BG[9].width();
			
		}
		
		
	}
	void DrawBG(GL10 gl)
	{
		if(BG1>-1.7 && BG1 < 1.7)
			mGR.root.DrawTexture(gl, mTex_BG[6], BG1, 0);
		if((BG1+mTex_BG[6].width())>-1.7 && (BG1+mTex_BG[6].width()) < 1.7)
			mGR.root.DrawTexture(gl, mTex_BG[7], BG1+mTex_BG[6].width(), 0);
		
		if(BG2>-1.7 && BG2 < 1.7)
			mGR.root.DrawTexture(gl, mTex_BG[6], BG2, 0);
		if((BG2+mTex_BG[6].width())>-1.7 && (BG2+mTex_BG[6].width()) < 1.7)
			mGR.root.DrawTexture(gl, mTex_BG[7], BG2+mTex_BG[6].width(), 0);
		
		mGR.root.DrawTextureR(gl, mTex_BG[11],-.9f, 0.8f,Ang,1);
		mGR.root.DrawTextureR(gl, mTex_BG[12],-.9f, 0.8f,rAng,1);
		mGR.root.DrawTextureR(gl, mTex_BG[13],-.9f, 0.8f,0,1);
		
		
		for(int i=0; i<mClud.length;i++)
		{
			mGR.root.DrawTransScal(gl, mTex_BG[5], mClud[i].x,mClud[i].y, mClud[i].ID/100f,mClud[i].ID/100f);
		}
		for(int i=0; i<mTree.length;i++)
		{
			mGR.root.DrawTexture(gl, mTex_BG[mTree[i].ID], mTree[i].x,mTree[i].y);
			if(mTree[i].ID == 0)
			{
				mGR.root.DrawTexture(gl, mTex_BG[mTree[i].ID], mTree[i].x-.2f,mTree[i].y+.04f);
				mGR.root.DrawTexture(gl, mTex_BG[mTree[i].ID], mTree[i].x-.1f,mTree[i].y-.05f);
				mGR.root.DrawTexture(gl, mTex_BG[mTree[i].ID], mTree[i].x+.1f,mTree[i].y-.05f);
				mGR.root.DrawTexture(gl, mTex_BG[mTree[i].ID], mTree[i].x+.2f,mTree[i].y+.04f);
			}
		}
		
		
		
		
		mGR.root.DrawTexture(gl, mTex_BG[9], L0BG1,-.6f);
		mGR.root.DrawTexture(gl, mTex_BG[9], L0BG2,-.6f);
		
		mGR.root.DrawTexture(gl, mTex_BG[10], L1BG1,-.75f);
		mGR.root.DrawTexture(gl, mTex_BG[10], L1BG2,-.75f);
		
		update();
	}
	void RunDraw_Char(GL10 gl)
	{
		if(mGR.resumeCounter%2==0)
			Walk++;
		float x=.5f,y=-.5f;
		mGR.root.DrawTexture(gl, mGR.mTex_Character[0]	, x+.14f+(sMove*.0015f),y+.07f);
		mGR.root.DrawTexture(gl, mGR.mTex_Walk[Walk%mGR.mTex_Walk.length], x+.05f,y-.10f);
		mGR.root.DrawTexture(gl, mGR.mTex_Character[5]	, x+(sMove*.001f),y-.00f);
		mGR.root.DrawTextureR(gl, mGR.mTex_Character[1]	, x+.01f,y+.27f+(sMove*.001f),sMove/2,1);
		mGR.mTex_Character[2].drawRotet3(gl, 0, 0, sMove*3, x, y+.1f, 1);
		sMove+=svx;
		if(sMove>10)
			svx =-1;
		if(sMove<-10)
			svx =1;
	}
}
