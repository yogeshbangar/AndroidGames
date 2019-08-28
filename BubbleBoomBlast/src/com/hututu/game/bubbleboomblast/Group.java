//ScoreBoard: 81,62,8
//GamePlay Score: 64,4,132
//GamePlay Level: 4,94,98

package com.hututu.game.bubbleboomblast;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen = M.GAMEPLAY;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_logo, 0, 0);
			if(Counter>60)
			{
				M.GameScreen = M.GAMEADD;
				Counter =0;
				if(mGR.addFree)
					M.GameScreen = M.GAMEMENU;
				
//				{try {handler.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTransScal(gl,mGR.mTex_Cross, .80f,0.85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			else 
			{
				mGR.mTex_Ling.drawSS(gl   ,-.140f,.87f	, .7f, 1.0f);//10
				mGR.mTex_LingBar.drawSS(gl,-.118f,.811f, Counter*.198f, 1.3f);//10
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMESET:
			Draw_Setting(gl);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Draw_HelpAbout(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEPUSE:
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMECONG:
			Draw_POWC(gl);
			break;
		}
		Counter++;
//		setting();
	}
	
	public boolean TouchEvent(MotionEvent event) 
	{
		
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEADD:
			mGR.mSel = 0;
			if(CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 1;
			}
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
//				GameRenderer.mStart.show();
				M.GameScreen = M.GAMEMENU;
				mGR.mSel = 0;
				M.sound10(GameRenderer.mContext,R.drawable.click);
//				M.sound1(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMESET:
			Handle_Setting(event);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Handle_HelpAbout(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEPUSE:
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMECONG:
			Handle_POWC(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	
/**********************************Start GamePlay************************************/
//	final float cx = -.75f;
//	final float cy = -.70f;
	final float cx = -.60f;
	final float cy = -.40f;
	float dis=1;
	int Inc = 10;
	
	
	void setB11(int i,int j) {
		mGR.mArr[i][j].val = 0;
		mGR.mScore += Inc;
		Inc += 5 + M.mRand.nextInt(5);
		for(int m=0;m<mGR.mB11.length;m++) {
			if (mGR.mB11[m].x < -1 || mGR.mB11[m].x > 1 || mGR.mB11[m].y < -1 || mGR.mB11[m].y > 1){
				mGR.mB11[m].set(bx+bdx*j,by-bdy*i, 0);
				break;
			}
		}
	}
	
	void setBalst(int i,int j) {
		mGR.mArr[i][j].val = 0;
		mGR.mScore += Inc;
		Inc += 5 + M.mRand.nextInt(5);
		for(int m=0;m<mGR.mBlast.length;m++) {
			if (mGR.mBlast[m].blast >= mGR.mTex_Blast.length) {
				mGR.mBlast[m].set(bx+bdx*j,by-bdy*i, 0);
				break;
			}
		}
		if(mGR.BestScore[mGR.mLevel%mGR.BestScore.length]<mGR.mScore)
		{
			mGR.BestScore[mGR.mLevel%mGR.BestScore.length]=mGR.mScore;
		}
	}
	void setArroW(int i,int j) {
		int k =0;
		for(int m=0;m<mGR.mArrow.length && k<4;m++) {
			if (mGR.mArrow[m].x < -1 || mGR.mArrow[m].x > 1 || mGR.mArrow[m].y < -1 || mGR.mArrow[m].y > 1) {
				mGR.mArrow[m].set(bx+bdx*j,by-bdy*i,(byte) k);k++;
			}
		}
	}

	void CallBoumb(int i,int j,int k)
	{
		M.sound1(GameRenderer.mContext, R.drawable.bomb_blast);
		setBalst(i, j);
		for(int p=0;p<mGR.mBomb.length;p++)
		{
			if(mGR.mBomb[p].dir<0 || mGR.mBomb[p].dir >= mGR.mTex_Bomb.length)
			{
				mGR.mBomb[p].set(bx+bdx*j,by-bdy*i,(byte)(mGR.mTex_Bomb.length-1));
				break;
			}
		}
		for(int m=i-2;m<i+3;m++)
		{
			for(int n=j-2;n<j+3;n++)
			{
				if(m>=0 && m<mGR.mArr.length && n>=0 && n<mGR.mArr[0].length)
				{
					if(mGR.mArr[m][n].val != 0)
					{
						//setBalst(m, n);
						CommonAction(m, n, k);
					}
					
				}
			}
		}
	}
	
	int checkDirection(float x, float y,int bc)
	{
		float lastposx = mGR.mBall[bc].x-mGR.mBall[bc].vx;
		float lastposy = mGR.mBall[bc].y-mGR.mBall[bc].vy+.01f;
		float[] dire	= new float [4];
		int k =0;
		dire[0] /*Top*/ = (float)Math.sqrt(((lastposx - x)*(lastposx - x))+((lastposy-y+mGR.mTex_Ball[0].Height()*.5f)*(lastposy-y+mGR.mTex_Ball[0].Height()*.5f)));
		dire[1] /*Bot*/ = (float)Math.sqrt(((lastposx - x)*(lastposx - x))+((lastposy-y-mGR.mTex_Ball[0].Height()*.5f)*(lastposy-y-mGR.mTex_Ball[0].Height()*.5f)));
		dire[2] /*Left*/= (float)Math.sqrt(((lastposx - x - mGR.mTex_Ball[0].width()*.5f)*(lastposx - x - mGR.mTex_Ball[0].width()*.5f))+((lastposy-y)*(lastposy-y)));
		dire[3] /*Righ*/= (float)Math.sqrt(((lastposx - x + mGR.mTex_Ball[0].width()*.5f)*(lastposx - x + mGR.mTex_Ball[0].width()*.5f))+((lastposy-y)*(lastposy-y)));
		
		for(int i=1;i<dire.length;i++)
		{
			if(dire[k]>dire[i])
			{
				k =i;
			}
		}
		
		/*
		mGR.mBall[bc].x = lastposx;
		mGR.mBall[bc].y = lastposy;
		if (mGR.mLevel == 14) {
			if (k == 0 || k == 1)
				mGR.mBall[bc].vy = -mGR.mBall[bc].vy;
			if (k == 2 || k == 3)
				mGR.mBall[bc].vx = -mGR.mBall[bc].vx;
		} else {
			mGR.mBall[bc].vx = -mGR.mBall[bc].vx;

		}
		*/
		mGR.mBall[bc].x = lastposx;
		mGR.mBall[bc].y = lastposy;
		mGR.mBall[bc].SolidCount++;
		
		if (mGR.mLevel == 22 || mGR.mLevel == 39) 
		{
			mGR.mBall[bc].vx = -mGR.mBall[bc].vx;
		}
		else
		{
			if (k == 0 || k == 1){
				mGR.mBall[bc].vy = -mGR.mBall[bc].vy/2;
			}
			if (k == 2 || k == 3)
				mGR.mBall[bc].vx = -mGR.mBall[bc].vx;
			
		}
			
		if(mGR.mBall[bc].SolidCount>5)
			mGR.mBall[bc].set(-100, -100, 0, 0);
		return k;
	}
	
	void CommonAction(int i,int j,int k)
	{

		switch (mGR.mArr[i][j].val) {
		case 1:
			mGR.BlastCount++;
			mGR.mPopedBall++;
			setBalst(i, j);
			M.sound2(GameRenderer.mContext, R.drawable.bubbleblast0);
			break;
		case 2:
			mGR.mArr[i][j].val = 0;
			setArroW(i, j);
			break;
		case 3:case 12:
			if (mGR.mBall[k].gayab == 0)
				mGR.mBall[k].gayab = 10;
			if(mGR.mArr[i][j].val==12)
				M.sound13(GameRenderer.mContext,R.drawable.icebubblesolid);
			else
				M.sound15(GameRenderer.mContext,R.drawable.metal_brick);
			break;
		case 4:
			mGR.mul = 3;
			M.mulCount = 3;
			setBalst(i, j);
			break;
		case 5:
			M.sound16(GameRenderer.mContext,R.drawable.soft_brick);
			setBalst(i, j);
			if (mGR.mBall[k].gayab == 0)
				mGR.mBall[k].gayab = 10;
			break;
		case 6:
			M.sound17(GameRenderer.mContext,R.drawable.sponge);
			checkDirection(bx + bdx * j, by - bdy * i, k);
			mGR.mArr[i][j].col = 53;
			break;
		case 7:
			M.sound2(GameRenderer.mContext, R.drawable.bubbleblast0);
			mGR.mArr[i][j].val = 0;
			for(int m=i-1;m<i+2;m++)
			{
				for(int n=j-1;n<j+2;n++)
				{
					if(m>=0 && m<mGR.mArr.length && n>=0 && n<mGR.mArr[0].length)
					{
						if(mGR.mArr[m][n].val == 1)
						{
							mGR.mArr[m][n].val = 12;
						}
					}
				}
			}
			break;
		case 8:
			mGR.mrBomb++;
			M.sound11(GameRenderer.mContext,R.drawable.extra_life);
			setBalst(i, j);
			break;
		case 9:
			M.mulCount = 9;
			setBalst(i, j);
			break;
		case 10:
			CallBoumb(i, j,k);
			break;
		case 11:
			setB11(i, j);
			break;
		
		}
	
	}
	
	void gamelogic() {
		
		for(int k=0;k<mGR.mBall.length&&k<mGR.mul;k++)
			mGR.mBall[k].update();
		boolean isWin = true;
		for(int i=0;i<mGR.mArr.length;i++) {
			for(int j=0;j<mGR.mArr[i].length;j++)
			{
				if(mGR.mArr[i][j].val!=0) {
					for (int k = 0; k < mGR.mBall.length && k < mGR.mul; k++) {
						if (CirCir(bx + bdx * j, by - bdy * i, mGR.mTex_Ball[0].width() * .5f, mGR.mBall[k].x, mGR.mBall[k].y, mGR.mTex_Ball[0].width() * .5f)) 
						{
							CommonAction(i, j, k);
						}
					}
					
				}
				if(mGR.mArr[i][j].val!=0)
				{
					for(int m=0;m<mGR.mArrow.length;m++) {
						if (mGR.mArrow[m].x >= -1 && mGR.mArrow[m].x <= 1 && mGR.mArrow[m].y >= -1 && mGR.mArrow[m].y <= 1) {
							if(CirCir(bx+bdx*j,by-bdy*i,mGR.mTex_Ball[0].width()*.3f,mGR.mArrow[m].x,mGR.mArrow[m].y,mGR.mTex_Arrow.width()*.3f))
							{
								if(mGR.mArr[i][j].val != 3)
								CommonAction(i, j, 0);
							}
						}
					}
					for(int m=0;m<mGR.mB11.length;m++) {
						if (mGR.mB11[m].x >= -1 && mGR.mB11[m].x <= 1 && mGR.mB11[m].y >= -1 && mGR.mB11[m].y <= 1){
							if(CirCir(bx+bdx*j,by-bdy*i,mGR.mTex_Ball[0].width()*.3f,mGR.mB11[m].x,mGR.mB11[m].y,mGR.mTex_Arrow.width()*.3f)){
								if(mGR.mArr[i][j].val == 3 || mGR.mArr[i][j].val == 12|| mGR.mArr[i][j].val == 7)
								{
									mGR.mB11[m].set(-100, -100, 0);
									if(mGR.mArr[i][j].val == 7)
										CommonAction(i, j, 0);
								}
								else if(mGR.mArr[i][j].val == 5 || mGR.mArr[i][j].val == 6)
								{
									mGR.mB11[m].set(-100, -100, 0);
									setBalst(i, j);
									if(mGR.mArr[i][j].val == 6){
										M.sound17(GameRenderer.mContext,R.drawable.sponge);
											
									}else{
										M.sound16(GameRenderer.mContext,R.drawable.soft_brick);
									}
								}
								else{
									CommonAction(i, j, 0);
								}
							}
						}
					}
					
				}
				if(mGR.mArr[i][j].val==1)
					isWin =  false;
			}
		}
		if(isWin){
			gameWinOver();
		}
		if(mGR.mScore>mGR.mHScore){
			mGR.mHScore = mGR.mScore;
		}
		for(int m=0;m<mGR.mArrow.length;m++) {
			if (mGR.mArrow[m].x >= -1 && mGR.mArrow[m].x <= 1 && mGR.mArrow[m].y >= -1 && mGR.mArrow[m].y <= 1) {
				mGR.mArrow[m].update();
			}
		}
		if(mGR.NumberOfBall<1)
		{
			gameWinOver();
		}
		
	}
	int achiment[] = {
			R.string.achievement_blast_50_bubbles,
			R.string.achievement_blast_150_bubbles,
			R.string.achievement_achieve_1000,
			R.string.achievement_achieve_10k,
			R.string.achievement_achieve_50k,
			R.string.achievement_achieve_100k
	};
	void CheckAchiv()
	{
		int score = 0;
		for(int i=0;i<mGR.BestScore.length;i++)
		{
			score+=mGR.BestScore[i];
		}
		if(mGR.BlastCount>=50 && !mGR.Achi[0])
		{
			mGR.Achi[0] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[0]);
			
		}
		if(mGR.BlastCount>=150 && !mGR.Achi[1])
		{
			mGR.Achi[1] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[1]);
		}
		if(score>=1000 && !mGR.Achi[2])
		{
			mGR.Achi[2] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[2]);
		}
		if(score>=10000 && !mGR.Achi[3])
		{
			mGR.Achi[3] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[3]);
		}
		if(score>=10000 && !mGR.Achi[4])
		{
			mGR.Achi[4] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[4]);
		}
		if(score>=10000 && !mGR.Achi[5])
		{
			mGR.Achi[5] = true;
			GameRenderer.mStart.UnlockAchievement(achiment[5]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_score, score);
		
	}
	void gameWinOver()//1,0
	{
//		mGR.gameCounter++;
//		System.out.println("  +++++++++++++++++++++++++= " +mGR.gameCounter);
		if(mGR.mBall[0].y>-1)
			return;
		boolean isIn = false;
		for(int k=0;k<mGR.mBall.length&&k<mGR.mul;k++)
		{
			if(mGR.mBall[k].x>-1f && mGR.mBall[k].x<1&&mGR.mBall[k].y>-1&& mGR.mBall[k].y<1 )
			{
				isIn = true;
			}
		}
		if(!isIn || mGR.NumberOfBall<1)
		{
			if(mGR.mTargetBall<=mGR.mPopedBall)
			{
				if(mGR.mLevel == 53)
					M.GameScreen = M.GAMECONG;
				else
					M.GameScreen = M.GAMEWIN;
				scal=10;scount=0;
				if(mGR.mULevel < mGR.mLevel+2)
				{
					mGR.mULevel = mGR.mLevel+2;
					GameRenderer.mStart.Submitscore(R.string.leaderboard_unlock_levels, mGR.mULevel);
				}
				M.sound14(GameRenderer.mContext,R.drawable.level_complete);
			}
			else
			{
				M.GameScreen = M.GAMEOVER;
				M.sound12(GameRenderer.mContext,R.drawable.gameover);
			}
		}
//		System.out.println("  ~~~~~~~~~~~~~~~ " +mGR.gameCounter);
		CheckAchiv();
		if(mGR.adsCount%2==0)
			GameRenderer.mStart.show();
	}
	final float bx = -.16f,bdx=.07f,by=0.91f,bdy=.13f;
	void Draw_GamePlay(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_bg[mGR.mLevel%mGR.mTex_bg.length], 0, 0);
		
		
	
		DrawTransScal(gl, mGR.mTex_Retry,-.92f+.20f*0,.82f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu	,-.92f+.20f*1,.82f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Puse	,-.92f+.20f*2,.82f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		DrawTexture(gl, mGR.mTex_Poped	,-.75f,.57f);
		drawNumberScal(gl, mGR.mPopedBall, -.73f,.555f,.8f,.30f,.05f,.63f);//164,4,132//0.003921569
		DrawTexture(gl, mGR.mTex_Ter	,-.75f,.36f);
		drawNumberScal(gl, mGR.mTargetBall, -.73f,.345f,.8f,.30f,.05f,.63f);//164,4,132//0.003921569
		
		DrawTexture(gl, mGR.mTex_SocrBar,-.75f,-.69f);
		drawNumberScal(gl, mGR.mScore   ,-.75f,-.705f,.64f,.30f,.05f,.63f);//164,4,132//0.003921569
		
		DrawTexture(gl, mGR.mTex_BarBomb,-.81f,-.90f);
		drawNumberScal(gl,mGR.NumberOfBall ,-.80f,-.91f,.64f,.01f,.21f,.21f);//1,56,56//0.003921569
		
		
		mGR.mTex_Ling.drawSS(gl   ,-.058f-.61f,-.88f	, .7f, 1.0f);//10
		mGR.mTex_LingBar.drawSS(gl,-.645f,-.938f, dis*5.6f, 1.3f);//10
		
		
		
		DrawTextureS(gl, mGR.mTex_Cannon[1], cx+0, cy-.02f,1.00f);
		mGR.mTex_Cannon[0].drawRotet2(gl, mGR.mBall[0].ang, cx, cy, 1, .3f);
		DrawTextureS(gl, mGR.mTex_Cannon[2], cx-.05f, cy-.07f,1.00f);
		if(mGR.mTex_Fire.length>mGR.fireCount)
			mGR.mTex_Fire[mGR.fireCount%mGR.mTex_Fire.length].drawRotet2(gl, mGR.mBall[0].ang, cx, cy, 1, 1.1f);
		if(mGR.mTex_Cef.length>mGR.fireCount)
			mGR.mTex_Cef[mGR.fireCount%mGR.mTex_Cef.length].drawRotet2(gl, mGR.mBall[0].ang, cx, cy, 1, 0.5f);
		mGR.fireCount++;
		
		for (int k = 0; k < mGR.mBall.length && k < mGR.mul; k++) {
			if (mGR.mBall[k].gayab > 0)
				DrawTransScal(gl, mGR.mTex_FireBall, mGR.mBall[k].x, mGR.mBall[k].y, 1, mGR.mBall[k].gayab * .1f);
			else if (M.mulCount == 10)
				mGR.mTex_Spiral.drawRotet2(gl, -Counter*10, mGR.mBall[k].x, mGR.mBall[k].y, 1, 0);
			else
				DrawTexture(gl, mGR.mTex_FireBall, mGR.mBall[k].x, mGR.mBall[k].y);
		}
		
		
		
		for(int i=0;i<mGR.mBlast.length;i++)
		{
			if (mGR.mBlast[i].blast < mGR.mTex_Blast.length) {
				DrawTexture(gl, mGR.mTex_Blast[mGR.mBlast[i].blast],mGR.mBlast[i].x, mGR.mBlast[i].y);
				mGR.mBlast[i].blast++;
			}
		}
		for(int m=0;m<mGR.mArrow.length;m++) {
			if (mGR.mArrow[m].x >= -1 && mGR.mArrow[m].x <= 1 && mGR.mArrow[m].y >= -1 && mGR.mArrow[m].y <= 1) {
				mGR.mTex_Arrow.drawRotet2(gl, mGR.mArrow[m].dir*90, mGR.mArrow[m].x,mGR.mArrow[m].y, 1, 0);
			}
		}
		
		
		for(int i=0;i<mGR.mArr.length;i++) {
			for(int j=0;j<mGR.mArr[i].length;j++) {
				if(mGR.mArr[i][j].val>0){
					if(mGR.mArr[i][j].val == 1){
						DrawTexture(gl, mGR.mTex_Ball[0], bx+bdx*j,by-bdy*i-.015f);
						DrawTexture(gl, mGR.mTex_tBall[mGR.mArr[i][j].col], bx+bdx*j,by-bdy*i);
					}
					else if(mGR.mArr[i][j].val == 6 && mGR.mArr[i][j].col > 49 && mGR.mArr[i][j].col < 55){
						DrawTexture(gl, mGR.mTex_BStone, bx+bdx*j,by-bdy*i);
						mGR.mArr[i][j].col--;
					}
					else
						DrawTexture(gl, mGR.mTex_Ball[mGR.mArr[i][j].val-1], bx+bdx*j,by-bdy*i);
				}
				
			}
		}
		
		for(int m=0;m<mGR.mB11.length;m++) {
			if (mGR.mB11[m].x >= -1 && mGR.mB11[m].x <= 1 && mGR.mB11[m].y >= -1 && mGR.mB11[m].y <= 1){
				DrawTexture(gl, mGR.mTex_Ball[10], mGR.mB11[m].x,mGR.mB11[m].y);
				mGR.mB11[m].uodate();
			}
			}
			
		for(int m=0;m<mGR.mBomb.length;m++) {
			if (mGR.mBomb[m].dir> -1 && mGR.mBomb[m].dir <mGR.mTex_Bomb.length) {
				DrawTexture(gl, mGR.mTex_Bomb[mGR.mBomb[m].dir], mGR.mBomb[m].x,mGR.mBomb[m].y);
				if(Counter%3==0)
					mGR.mBomb[m].dir--;
			}
		}
		if(mGR.mLevel<3 && !mGR.ShowHelp)
			DrawTexture(gl, mGR.mTex_Finger, -.66f-(Counter%60)*.005f, -.55f-(Counter%60)*.005f);
		
		if(mGR.mTargetBall<=mGR.mPopedBall)
		{
			DrawTransScal(gl, mGR.mTex_NexLvl,0.78f,mGR.svx-.9f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
			DrawTransScal(gl, mGR.mTxt_Target,.40f,mGR.svx-.94f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
			if(mGR.svx<0)
				mGR.svx+=.01f;
		}
		
		
		gamelogic();
	}
	
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<3;i++)
		{
			if(CircRectsOverlap(-.92f+.20f*i,.82f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(0.78f,-.9f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f))
		{
			mGR.mSel = 4;
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(mGR.NumberOfBall <1)
				return false;
			boolean isIn = false;
			for(int k=0;k<mGR.mBall.length&&k<mGR.mul;k++)
			{
				if(mGR.mBall[k].x>-1f && mGR.mBall[k].x<1&&mGR.mBall[k].y>-1&& mGR.mBall[k].y<1 )
				{
					isIn = true;
				}
			}
			if(!isIn)
			{
				mGR.isReady = true ;
				if((M.mulCount == 2 && mGR.mul == 3)||M.mulCount == 10){
				M.mulCount = 0 ;
				mGR.mul = 1;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(!mGR.isReady || mGR.NumberOfBall <1)
				return false;
			float x = world2screenX(cx)	- event.getX();
			float y = worldscreenY(cy) - event.getY();
			float n= (float)(GetAngle(-y,-x));
//			System.out.println("!!!!!!!!!!  "+n);
			if (n < 0 || n > 4.5f) 
			{
				for (int k = 0; k < mGR.mBall.length && k < mGR.mul; k++) {
					mGR.mBall[k].set((cx - (float) Math.sin(n) * .231f),(cy + (float) Math.cos(n) * .385f), 0, 0);
					mGR.mBall[k].ang = (float) Math.toDegrees(n);
					dis = 5 * (float) Math.sqrt(((cx - screen2worldX(event.getX())) * (cx - screen2worldX(event.getX())))
									+ ((cy - screen2worldY(event.getY())) * (cy - screen2worldY(event.getY()))));
				}
				
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mGR.mSel!=0)
			{
				switch (mGR.mSel) {
				case 1:
					mGR.gameReset();
					break;
				case 2:
					mGR.mPage = (mGR.mLevel/6)%9;
					M.GameScreen = M.GAMELEVEL;
					break;
				case 3:
					M.GameScreen = M.GAMEPUSE;
					break;
				case 4:
					gameWinOver();
					break;
					
				}
				if (mGR.mSel != 0)
					M.sound10(GameRenderer.mContext,R.drawable.click);
				mGR.mSel = 0;
			}else
			{
				float s =.7f;
				if(!mGR.isReady || mGR.NumberOfBall <1)
					return false;
				
				float x1 = world2screenX(cx)- event.getX();
				float y1 = worldscreenY(cy) - event.getY();
				float ng = (float) (GetAngle(-y1, -x1));
				if (ng < 0 || ng > 4.5f){
					mGR.isReady = false;
					mGR.ShowHelp= true;
					mGR.NumberOfBall--;
					dis = 5 * (float) Math .sqrt(((cx - screen2worldX(event.getX())) * (cx - screen2worldX(event .getX())))
							+ ((cy - screen2worldY(event.getY())) * (cy - screen2worldY(event.getY()))));
					
	//				if(M.mulCount ==9)
	//					dis = 2;
					mGR.mBall[0].set((cx - (float) Math.sin(ng) * .231f), (cy + (float) Math.cos(ng) * .385f),
							-(float) Math.sin(ng) * .06f * dis * s,	(float) Math.cos(ng) * .1f * dis*s);
					
					if(mGR.mul == 3)
					{
						mGR.mBall[1].set((cx - (float) Math.sin(ng) * .231f), (cy + (float) Math.cos(ng) * .385f),
								-(float) Math.sin(ng+.3f) * .06f * dis*s,	(float) Math.cos(ng+.3f) * .1f * dis*s);
						mGR.mBall[2].set((cx - (float) Math.sin(ng) * .231f), (cy + (float) Math.cos(ng) * .385f),
								-(float) Math.sin(ng-.3f) * .06f * dis*s,	(float) Math.cos(ng-.3f) * .1f * dis*s);
					}
					if (Inc == 10 && mGR.Leave > 1 && mGR.mul == 1)
						mGR.Leave--;
					Inc = 10;
					mGR.fireCount =0;
					if(M.mulCount ==9) {
						mGR.mul = 1;
						M.mulCount = 10;
					}
					if(mGR.mul == 3){
						M.mulCount =2;
					}
					M.sound8(GameRenderer.mContext,R.drawable.cannon_bomb_shoot);
				}
//				System.out.println(dis);
			}
			break;
		}
		return true;
	}
	/**********************************End GamePlay************************************/
	
	
	/********************************Start POWC**************************************/
	float scal=10,scount=0;
	void Draw_POWC(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_bg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		
		if (M.GameScreen == M.GAMEPUSE) {
			DrawTexture(gl, mGR.mTex_Paused, 0, .35f);
			DrawTexture(gl, mGR.mTex_Score	, 0.00f, .02f);
			drawNumberScal(gl, mGR.mScore	, -.02f, .01f,1,1);//81,62,8//0.003921569
			DrawTransScal(gl, mGR.mTex_PlayIcn,-.25f+.25f*1, -.37f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		}
		if(M.GameScreen == M.GAMEOVER){
			DrawTexture(gl, mGR.mTex_Gameover,0.00f, .35f);
			DrawTexture(gl, mGR.mTex_Score	, 0.00f, .02f);
			drawNumberScal(gl, mGR.mScore	, -.02f, .01f,1,1);//81,62,8//0.003921569
			DrawTransScal(gl, mGR.mTex_Leaderboard,-.25f+.25f*1, -.37f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		}
		if (M.GameScreen == M.GAMEWIN) {
			DrawTexture(gl, mGR.mTex_Levelclear, 0, .50f);
			for (int i = 0; i < 3; i++){
				DrawTexture(gl, mGR.mTex_Star[0], -.22f + .22f * i, .25f);
			}
			for (int i = 0; i < 3 && i< mGR.Leave ; i++){
				if(scount>i)
					DrawTexture(gl, mGR.mTex_Star[1], -.22f + .22f * i, .25f);
				if(scount==i)
					DrawTextureS(gl, mGR.mTex_Star[1], -.22f + .22f * i, .25f, scal);
			}
			if (scount < 4) {
				scal *= .7f;
				if (scal < 1) {
					scal = 10;
					scount++;
					if(scount<mGR.Leave){
						System.out.println(scount);
						M.sound18(GameRenderer.mContext,R.drawable.star);
					}
				}
			}
			DrawTexture(gl, mGR.mTex_Score , 0.00f,-.06f);
			drawNumberScal(gl, mGR.mScore  , -.02f,-.06f,1,1);//81,62,8//0.003921569
			DrawTransScal(gl, mGR.mTex_Next, -.25f+.25f*1, -.37f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		}
		if(M.GameScreen == M.GAMECONG){
			DrawTexture(gl, mGR.mTex_Cong, 0, .35f);
			for (int i = 0; i < 3; i++)
				DrawTexture(gl, mGR.mTex_Star[0], -.22f + .22f * i, .02f);
			for (int i = 0; i < 3 && i< mGR.Leave ; i++){
				if(scount>i)
					DrawTexture(gl, mGR.mTex_Star[1], -.22f + .22f * i, .02f);
				if(scount==i)
					DrawTextureS(gl, mGR.mTex_Star[1], -.22f + .22f * i, .02f, scal);
			}
			if (scount < 4) {
				scal *= .7f;
				if (scal < 1) {
					scal = 10;
					scount++;
				}
			}
		}
		
		DrawTransScal(gl, mGR.mTex_Menu, -.25f+.25f*0, -.37f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Retry,-.25f+.25f*2, -.37f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
	}
	boolean Handle_POWC(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<3;i++){
			if(CircRectsOverlap(-.25f+.25f*i, -.37f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				mGR.mPage = (mGR.mLevel/6)%9;
				M.GameScreen = M.GAMELEVEL;
			break;
			case 2:
				if (M.GameScreen == M.GAMEPUSE) {
					//continue
					M.GameScreen = M.GAMEPLAY;
//					mGR.gameReset();
				}
				if(M.GameScreen == M.GAMEOVER){
					//Leader-board
					GameRenderer.mStart.onShowLeaderboardsRequested();
				}
				if (M.GameScreen == M.GAMEWIN) {
					//Next
					if(mGR.mLevel<Level.Level.length-1)
						mGR.mLevel +=1;
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
				}
				break;
			case 3:
				//Retry
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				break;
			}
			if (mGR.mSel != 0)
				M.sound10(GameRenderer.mContext,R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/**********************************End POWC************************************/
	/********************************Start Level**************************************/
	float mScal =1;
	float mLScal =1;
	float mLSvs =.02f;
	void Draw_Level(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_bg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		
		DrawTransScal(gl, mGR.mTex_Board2, 0, .75f,mGR.mSel==996?1.1f:1,mGR.mSel==996?0.5f:1);
		
		for(int i=0;i<6;i++)
		{
			if(mGR.mLevel == i+mGR.mPage*6){
				DrawTransScal(gl, mGR.mTex_LIcn, -.31f+.31f*(i%3), .30f-.45f*(i/3),mGR.mSel==((i+1)+mGR.mPage*6)?1.1f:
					mLScal,mGR.mSel==((i+1)+mGR.mPage*6)?0.5f:1);
			}
			else
				DrawTransScal(gl, mGR.mTex_LIcn, -.31f+.31f*(i%3), .30f-.45f*(i/3),mGR.mSel==((i+1)+mGR.mPage*6)?1.1f:mScal,mGR.mSel==((i+1)+mGR.mPage*6)?0.5f:1);
			if(i+mGR.mPage*6 < mGR.mULevel){
				if((i+1)+mGR.mPage*6<10)
					drawNumberScal(gl, (i+1)+mGR.mPage*6 , -.305f+.31f*(i%3), .30f-.45f*(i/3),.7f*mScal,1);
				else
					drawNumberScal(gl, (i+1)+mGR.mPage*6 , -.32f+.31f*(i%3), .30f-.45f*(i/3),.7f*mScal,1);
			}
			else{
				DrawTransScal(gl, mGR.mTex_Lok, -.31f+.31f*(i%3), .30f-.45f*(i/3),mGR.mSel==((i+1)+mGR.mPage*6)?1.1f:mScal,mGR.mSel==((i+1)+mGR.mPage*6)?0.5f:1);				
				
			}
		}
		if (mGR.mPage > 0)
			DrawTextureR(gl, mGR.mTex_MArrow, -.7f, 0,180,mGR.mSel==998?1.6f:1.4f);
		if (mGR.mPage < 8)
			DrawTextureR(gl, mGR.mTex_MArrow, 0.7f, 0,  0,mGR.mSel==997?1.6f:1.4f);

		for(int i=0;i<9;i++)
		{
			DrawTexture(gl, mGR.mTex_Dot[1]		,-.27f+.068f*i,-.52f);
			if(mGR.mPage == i)
				DrawTexture(gl, mGR.mTex_Dot[0]	,-.27f+.068f*i,-.52f);
		}
		DrawTransScal(gl, mGR.mTex_Back	, .89f,-.8f,mGR.mSel==999?1.1f:1,mGR.mSel==999?0.5f:1);
		if(mScal<1)
			mScal*=1.5f;
		if(mLScal>1.14)
		{
			mLSvs =-.02f;
		}
		if(mLScal<.86)
		{
			mLSvs =0.02f;
		}
		mLScal+=mLSvs;
//		System.out.println(mGR.mPage);
	}
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<6;i++){
			if(CircRectsOverlap(-.31f+.31f*(i%3), .30f-.45f*(i/3), mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = (i+1)+mGR.mPage*6;
			}	
		}
		if(CircRectsOverlap(.89f,-.8f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 999;
		}
		if(CircRectsOverlap(-.7f, 0, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 998;
		}
		if(CircRectsOverlap(0.7f, 0, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 997;
		}
		if(CircRectsOverlap(0.0f, .75f, mGR.mTex_Board2.width()*.4f, mGR.mTex_Board2.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 996;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			
			case 999:
				M.GameScreen = M.GAMEMENU;
			break;
			case 998:
				if (mGR.mPage>0) {
					mGR.mPage--;
					mScal=.1f;
				}
				break;
			case 997:
				if (mGR.mPage<8) {
					mGR.mPage++;
					mScal=.1f;
				}
				break;
				default:
				if (-1+mGR.mSel < mGR.mULevel) {
					mGR.mLevel = (mGR.mSel - 1);
					M.GameScreen = M.GAMEPLAY;
					mGR.gameReset();
				}
					break;
				case 996:
					mGR.mMainActivity.onBuyGold50000(null);
					break;
			case 0:	break;
			}
			if (mGR.mSel != 0)
				M.sound10(GameRenderer.mContext,R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/**********************************End Level************************************/
	
	/********************************Start HelpAbout**************************************/
	void Draw_HelpAbout(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_bg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		if(M.GameScreen == M.GAMEABOUT)
			DrawTexture(gl, mGR.mTex_AboutTex, 0, 0);
		else
			DrawTexture(gl, mGR.mTex_helpscr, 0, 0);
		DrawTransScal(gl, mGR.mTex_Back	, .89f,-.8f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
	}
	boolean Handle_HelpAbout(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CircRectsOverlap(.89f,-.8f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESET;
			break;
			}
			if (mGR.mSel != 0)
				M.sound10(GameRenderer.mContext,R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/**********************************End HelpAbout************************************/
	
	
	/********************************Start Menu**************************************/
	void Draw_Setting(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_bg[0], 0, 0);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTransScal(gl, mGR.mTex_About, 0, .53f-.34f*0,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Help	, 0, .53f-.34f*1,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0], 0, .53f-.34f*2,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_fb	, -.27f+.27f*0,-.49f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Twit	, -.27f+.27f*1,-.49f,mGR.mSel==5?1.1f:1,mGR.mSel==5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_gp	, -.27f+.27f*2,-.49f,mGR.mSel==6?1.1f:1,mGR.mSel==6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back	, .89f,-.8f,mGR.mSel==7?1.1f:1,mGR.mSel==7?0.5f:1);
	}
	boolean Handle_Setting(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0, .53f-.34f*0, mGR.mTex_Setting.width()*.5f, mGR.mTex_Setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0, .53f-.34f*1, mGR.mTex_Play.width()*.4, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0, .53f-.34f*2, mGR.mTex_Lboard.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.27f+.27f*0,-.49f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.27f+.27f*1,-.49f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(-.27f+.27f*2,-.49f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(.89f,-.8f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 7;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 2:
				M.GameScreen = M.GAMEHELP;
				break;
			case 3:
				M.setValue=!M.setValue;
				break;
			case 4:
				facebook();
				break;
			case 5:
				twitter();
				break;
			case 6:
				google();
				break;
			case 7:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if (mGR.mSel != 0)
				M.sound10(GameRenderer.mContext,R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/**********************************End Setting************************************/
	
	
	/********************************Start Menu**************************************/
	float as = 1;
	float avs = .01f;
	
	
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		
		DrawTextureS(gl, mGR.mTex_SplashT, -.37f, .39f,as);
		
		if(as<.95f) avs  = .002f;
		if(as>1.05f) avs =-.002f;
		as+=avs;
		
		DrawTransScal(gl, mGR.mTex_Setting	, .89f, .8f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Play	 	, .8f, .00f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Lboard	, .8f,-.36f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiev	, .8f,-.72f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.89f, .8f, mGR.mTex_Setting.width()*.5f, mGR.mTex_Setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(.8f, .00f, mGR.mTex_Play.width()*.4, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(.8f,-.36f, mGR.mTex_Lboard.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(.8f,-.72f, mGR.mTex_Achiev.width()*.4f, mGR.mTex_Setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 4;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESET;
				break;
			case 2:
				mGR.mPage = (mGR.mLevel/6)%9;
				M.GameScreen =M.GAMELEVEL;
				if(!GameRenderer.mStart.isSignedIn())
					GameRenderer.mStart.Join();
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			}
			if (mGR.mSel != 0)
				M.sound10(GameRenderer.mContext,R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	/**********************************End Menu************************************/
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float scl)
	{
		Tex.drawRotet(gl, x, y, angle,scl);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	float world2screenX(float a) {
		float c = ((a / 2) + 0.5f) * M.ScreenWidth;
		return c;
	}

	float worldscreenY(float a) {
		float c = (.5f-(a / 2)) * (M.ScreenHieght);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width() * .6f;
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,float scal,float r,float g,float b)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawSRGB(gl,x+i*dx,y,scal,r,g,b);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,float scal,float t)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawSRGB(gl,x+i*dx,y,scal,t,t,t);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	void twitter()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	
	void google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
