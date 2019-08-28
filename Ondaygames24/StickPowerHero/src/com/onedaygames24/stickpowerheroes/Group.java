package com.onedaygames24.stickpowerheroes;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0,Counter2=0,AdCnt;
	final int Check=100;
	float sx=0,sy=0;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}
	     System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);
	}
	
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.7f,0.0f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			GameRenderer.mStart._keyCode = 3;
		}
		if(CircRectsOverlap(0.7f,0.0f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
		    GameRenderer.mStart._keyCode = 4;
		}
		if(CircRectsOverlap(.0f,-.7f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0f,0.7f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)
		{
			GameRenderer.mStart._keyCode = 0;
		}
		return true;
	}

	public Group(GameRenderer _GameRenderer){
		mGR = _GameRenderer;
	}

    float ax,ay;
    
	public void draw(GL10 gl) 
	{
	  Counter++;
	  switch (M.GameScreen)
	   {
			  case Check:
//				  for(int i=0;i<mGR.BG.length;i++)
//				    DrawTexture(gl,mGR.mTex_GameBg[0],mGR.BG[i],0);
//				  
//				  for(int i=0;i<mGR.BG.length;i++)
//					  mGR.BG[i]-=.02f;
//				  for(int i=0;i<mGR.BG.length;i++)
//				  {
//					  if(mGR.BG[i]<-1-mGR.mTex_GameBg[0].width())
//					    mGR.BG[i]=mGR.BG[i==0?1:0]+mGR.mTex_GameBg[0].width(); 
//				  }
				break;
			  case M.GAMELOGO:
				 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
				 if(Counter>80)
				 {
				   Counter =0;
				   M.GameScreen = M.GAMEMENU;
				 }
				break;
			  case M.GAMEMENU:
				  DrawMenu(gl);
				  break;
			  case M.GAMEHELP:
				  DrawHelp(gl);
				  break;
			  case M.GAMEHERO:
				  DrawHero(gl);
				  break;
//			  case M.GAMESHOP:
//				  DrawShop(gl);
//				  break;
			  case M.GAMEOVER:
			  case M.GAMEPAUSE:
				  DrawGameOver(gl);
				  break;
			  case M.GAMEPLAY:
				  DrawGamePlay(gl);
				 break;	
	   }
//		setting();
	}
	
public boolean TouchEvent(MotionEvent event) 
{
	  switch (M.GameScreen)
	  {
			case Check:
				break;
			case M.GAMEMENU:
				HandleMenu(event);
				break;
			case M.GAMEHERO:
				HandlHero(event);
				break;
			case M.GAMEHELP:
				HandleHelp(event);
				break;
//			case M.GAMESHOP:
//				HandlShop(event);
//				break;
			case M.GAMEOVER:
			case M.GAMEPAUSE:
				HandleOver(event);
				break;
			case M.GAMEPLAY:
				HandleGame(event);
				break;
   	  }
	  if(event.getAction() == MotionEvent.ACTION_DOWN && mGR.mSel!=0)
	  {
		  M.clickSound(GameRenderer.mContext,R.drawable.click);
	  }
	  Handle(event);
	return true;
}
 
void DrawMenu(GL10 gl)
{
    SimplePlane plan[]= {/*mGR.mTex_NoAds,*/mGR.mTex_Help,mGR.mTex_Sound[0],mGR.mTex_HeroBtn/*,mGR.mTex_DiamondBtn*/};
	float dy=-.22f;
	DrawTexture(gl,mGR.mTex_GameBg[0],1.059f,0);
	DrawTexture(gl,mGR.mTex_SplashTxt,-.119f,.519f);
	DrawTransScal(gl,mGR.mTex_Play,0,0,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	for(int i=0;i<plan.length;i++)
	  DrawTransScal(gl,i==1?(M.setValue?plan[i]:mGR.mTex_Sound[1]):plan[i],-.749f,-.409f+i*dy,mGR.mSel==i+2?1.2f:1,mGR.mSel==i+2?.5f:1);
//	for(int i=0;i<1;i++)
//		DrawTransScal(gl,plan[i],.749f,-.409f+(i+1)*dy,mGR.mSel==i+5?1.2f:1,mGR.mSel==i+5?.5f:1);
	
}
boolean HandleMenu(MotionEvent event)
{
	float dy=-.22f;
	mGR.mSel=0;
	if(CircRectsOverlap(0,0,mGR.mTex_Play.width()*.5f,mGR.mTex_Play.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=1;
	for(int i=0;i<3;i++)
 	  if(CircRectsOverlap(-.749f,-.409f+i*dy,mGR.mTex_Help.width()*.5f,mGR.mTex_Help.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=i+2;
//	for(int i=0;i<2;i++)
// 	  if(CircRectsOverlap(.749f,-.409f+(i+1)*dy,mGR.mTex_Help.width()*.5f,mGR.mTex_Help.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//		mGR.mSel=i+5;

	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch(mGR.mSel)
		 {
		  case 1:
			  // Play
			  M.GameScreen = M.GAMEPLAY;
			  mGR.GameReset();
			  break;
//		  case 2://Noads
//			  mGR.mMainActivity.onBuyAdfree(null);
//			  break;
		  case 2://Help
			  M.GameScreen = M.GAMEHELP;
			  mGR.GameReset();
			  break;
		  case 3:
			   M.setValue =!M.setValue;
			  break;
		  case 4:
			   //Hero
			  M.GameScreen = M.GAMEHERO;
			  break;
//		  case 6:
//			  //Shop
//			  M.GameScreen = M.GAMESHOP;
//			  break;
			  
		 }
		 mGR.mSel=0;
	 }
	return true; 
}
void DrawHelp(GL10 gl)
{
	for(int i=0;i<mGR.BG.length;i++)
	  DrawTexture(gl,mGR.mTex_GameBg[0],mGR.BG[i],0+mGR.mHilaVal);
	
	for(int i=0;i<mGR.mBlock.length;i++)
	{
	   DrawTextureSS(gl,mGR.mTex_Block,mGR.mBlock[i].x,mGR.mBlock[i].y+mGR.mHilaVal,mGR.mBlock[i].s,1,0,0);
	   DrawTexture(gl,mGR.mTex_RedDot,mGR.mBlock[i].x,-.425f+mGR.mHilaVal);
	}
	for(int i=0;i<mGR.mDanda.length;i++)
	{
	  DrawTexturRPS(gl,mGR.mTex_Danda,mGR.mDanda[i].x,mGR.mDanda[i].y+mGR.mHilaVal,mGR.mDanda[i].ang,mGR.mTex_Danda.width()*.5f,0,mGR.mDanda[i].s,1);
	  if(mGR.mDanda[i].sv>0)
	   DrawTexture(gl,mGR.mTex_Tap ,-.02f,-.659f);
	}
	for(int i=0;i<mGR.mDiamond.length && mGR.isDiaEnable ;i++)
	{
		if(mGR.mDiamond[i].x>-1.2f && mGR.mDiamond[i].x<1.2f)
		{
			DrawTextureS(gl,mGR.mTex_Diamond,mGR.mDiamond[i].x,mGR.mDiamond[i].y,.7f);
			float x   = mGR.mPlayer.x-mGR.mDiamond[i].x;
			float y   = (mGR.mPlayer.y-.06f)-mGR.mDiamond[i].y;
			float dis = (float)Math.sqrt(x*x+y*y);
			if(dis<.15f && !mGR.mPlayer.isUlta)
			{
			   M.SwingSound(GameRenderer.mContext,R.drawable.swing);	
			   mGR.mPlayer.isUlta=true;
			   DrawTexture(gl,mGR.mTex_Tap ,-.02f,-.659f);
			}
		}
		if(mGR.mPlayer.isUlta && Counter%20==10)
		{
		     M.SwingSound(GameRenderer.mContext,R.drawable.swing);
		     mGR.mPlayer.isUlta=false;
		     DrawTexture(gl,mGR.mTex_Tap ,-.02f,-.659f);
		}
		if(mGR.mDiamond[i].bx>-1.2f && mGR.mDiamond[i].bx<1.2f)
		{
			if(mGR.mDiamond[i].t>0)
			{
			  DrawTransScal(gl,mGR.mTex_Blast,mGR.mDiamond[i].bx,mGR.mDiamond[i].by,mGR.mDiamond[i].s,mGR.mDiamond[i].t);
			  mGR.mDiamond[i].update2();
			}
		}
	}
	if(Counter%2==0)
		Counter2++;
	float dy=.06f;
	if(mGR.mPlayer.mHeroNo==3)
	   dy=.09f;
	else if(mGR.mPlayer.mHeroNo==5)
	   dy=.12f;
	switch(mGR.mPlayer.mAction)
	{
	   case 0://Stand
		   if(mGR.mPlayer.isUlta)
			  DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][Counter2%2],mGR.mPlayer.x,mGR.mPlayer.y-dy,180,0,0);
		   else
		      DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][Counter2%2],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	   case 1://Kick
		   int pos[]={1,3};
		    DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos[(Counter2/15)%2]],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	   case 2://Run
		   int pos2[]={1,2};
		   if(mGR.mPlayer.isUlta)
		     DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos2[Counter2%2]],mGR.mPlayer.x,mGR.mPlayer.y-dy,180,0,0);
		   else
			   DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos2[Counter2%2]],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	}
	if(mGR.mPerfectAni.t>0)
	{
	  DrawTransScal(gl,mGR.mTex_Perfect,0,0,mGR.mPerfectAni.s,mGR.mPerfectAni.t);
	  mGR.mPerfectAni.update2();
	}
	
    DrawTexture(gl,mGR.mTex_Hand,0,-.729f);
    DrawTransScal(gl,mGR.mTex_LetsGo,0,.3f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	HelpLogic();
}
boolean HandleHelp(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(0,.3f,mGR.mTex_LetsGo.width()*.4f,mGR.mTex_LetsGo.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 mGR.mSel=1;
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		if(mGR.mSel==1)
			M.GameScreen = M.GAMEMENU;
		
		mGR.mSel=0;
	}
	return true;
	
}
float lx;
void HelpLogic()
{
	MoveBg();
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		mGR.mBlock[i].update();
		if(mGR.mBlock[i].x<-1.6f)
		{
		   M.NewPollSound(GameRenderer.mContext,R.drawable.newbrick);	
		   mGR.mBlock[i].Set(1.6f,1,.7f);
		   if(M.mRand.nextBoolean())
		     SetDia(mGR.mBlock[i].x-(3*mGR.mBlock[i].width),1,mGR.VX*.5f);
		}
	}
	for(int i=0;i<mGR.mDiamond.length;i++)
	{
		if(mGR.mDiamond[i].x<1.2f)
		   mGR.mDiamond[i].updateDia();
	}
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(!mGR.mBlock[i].isStop && mGR.mBlock[i].x<-.7f)
		{
			mGR.mBlock[i].vx=0;
			mGR.mBlock[i].x=-.7f;
			mGR.mBlock[i].isStop =true;
		}
		if(!mGR.mBlock[i].isDisCmp && mGR.mBlock[i].x<=mGR.mBlock[i].dis)
		{
			mGR.mBlock[i].vx= 0;
			mGR.mBlock[i].x = mGR.mBlock[i].dis;
			mGR.mBlock[i].isDisCmp =true;
			if(mGR.mBlock[i].dis>-.7f)
			 for(int k=0;k<mGR.mDiamond.length;k++)
			 {
				if(mGR.mDiamond[k].mStop==0)
				{
				  mGR.mDiamond[k].x = mGR.mDiamond[k].x-mGR.mDiamond[k].vx;	
				  mGR.mDiamond[k].vx = 0;
				  mGR.mDiamond[k].mStop++;
				}
			 }
		}
	}
	if(Counter%4==0)
	{
	   mGR.mHelpCnt++;
	   if(mGR.mHelpCnt==10)
	   {
		 int i = findClosest();
	     if(mGR.mPlayer.mAction==0 && mGR.mPlayer.vx==0 && mGR.mDanda[i].mAction==-1)
	     {
		   mGR.mDanda[i].Set(mGR.mPlayer.x+.07f,.05f);	
	  	   mGR.mDanda[i].mAction=0;
	  	   lx = M.randomRange(2.2f,2.4f);
	  	   Counter2=0; 
//	  	   System.out.println("11111111111111111111111 Starttt");
	     }
	   }
	}
	for(int i=0;i<mGR.mDanda.length;i++)
	{
		if(mGR.mDanda[i].s>lx && mGR.mDanda[i].sv!=0)
	  	{
	  	   mGR.mDanda[i].sv=0; 
		   mGR.mDanda[i].angInc=.02f;
		   mGR.mDanda[i].mAction=1;
		   mGR.mPlayer.mAction=1;//Kick
		   mGR.mDanda[i].s=lx;
//		   System.out.println("2222222222222222222222222  Starttt ");
	  	}
		if(mGR.mDanda[i].sv>0 || mGR.mDanda[i].s>0)
		{
		  mGR.mDanda[i].update();
		}
		if(mGR.mDanda[i].ang==0  &&  mGR.mDanda[i].mAction==1 && mGR.mPlayer.mAction==1)
		{
			M.LineFallSound(GameRenderer.mContext,R.drawable.line_fall);
	        mGR.mPlayer.mAction=2;
		    mGR.mPlayer.vx=.02f;
		    mGR.mDanda[i].angInc=0;
//		     System.out.println("Plyer Vx======111111111111111==========       "+mGR.mPlayer.vx);
		}
		if(mGR.mDanda[i].mAction==2)
		{
			float x = mGR.mDanda[i].x+mGR.mDanda[i].width;
			if(x<-.7f && x>-1 && mGR.mDanda[i].mStop==0)
			{
				mGR.mDanda[i].vx=0;
				mGR.mDanda[i].mStop++;
			}
	 	 if(x<-1.3f)
	       mGR.mDanda[i].reset();
		}
	}
	if((mGR.mPlayer.vx>0 && mGR.mPlayer.mAction==2))
	    mGR.mPlayer.update();
	else
	{
	   float x=((mGR.mBlock[mGR.mBlockSel].x+mGR.mBlock[mGR.mBlockSel].width)-.07f);
	   mGR.mPlayer.update2(x);
	}
	for(int i=0;i<mGR.mDiamond.length;i++)
	{
		if(mGR.mPlayer.vx>0 && CircRectsOverlap(mGR.mDiamond[i].x,mGR.mDiamond[i].y,.05f,.05f,mGR.mPlayer.x,mGR.mPlayer.y-.06f,.05f))
		{
			mGR.mDiamond[i].bx = mGR.mDiamond[i].x;
			mGR.mDiamond[i].by = mGR.mDiamond[i].y;
			mGR.mDiamond[i].set2(.06f,.06f);
			mGR.mDiamond[i].x  = mGR.mDiamond[i].y=100;
		}
	}
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(mGR.mPlayer.mCrash<1 && mGR.mBlockSel!=i)
		{
			if(mGR.mPlayer.vx>0 && CircRectsOverlap(mGR.mBlock[i].x,-.41f,.02f,.02f,GetPoint(),-.41f,.02f))
			{
			   if(mGR.mPerfectAni.t==0)
			   {
				 M.PerfectSound(GameRenderer.mContext,R.drawable.perfect);
				 mGR.mPerfectAni.set2(.03f,.03f);
			   }
			}
		   float x=((mGR.mBlock[i].x+mGR.mBlock[i].width)-.06f);
		   if(!mGR.mPlayer.isUlta && mGR.mPlayer.mAction==2 && CircRectsOverlap(x,-.389f,.06f,.05f,mGR.mPlayer.x,mGR.mPlayer.y,.06f))
 		    {
				mGR.mPlayer.reset();
				MoveBlock(mGR.VX*.5f);
				mGR.mBlockSel=i;
				mGR.mHelpCnt=0;
				Counter=0;
//				System.out.println("Plyer Vx=========22222222222222222222222222222=======       "+mGR.mPlayer.vx);
		    }
		}
	}
	
}
void DrawHero(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_GameBg[0],1.059f,0);
	DrawTexture(gl,mGR.mTex_CommonBox,0,0);
	DrawTexture(gl,mGR.mTex_HeroTxt,-.469f,.519f);
	DrawTexture(gl,mGR.mTex_Diamond,.729f,.529f);
	for(int i=0;i<mGR.mPlayer.isUnLock.length;i++)
	{
	   DrawTransScal(gl,mGR.mTex_Box[mGR.mPlayer.isUnLock[i]?0:1],i%2==0?-.409f:.419f ,.27f+(i/2)*(-.339f),mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	   if(!mGR.mPlayer.isUnLock[i])
		   drawNumber(gl,mGR.mPlayer.mVal[i],i%2==0?-.459f:.359f,.27f+(i/2)*(-.339f),1);
	   else
		   DrawTransScal(gl,mGR.mTex_Player[i][0],i%2==0?-.509f:.319f ,.27f+(i/2)*(-.339f),mGR.mSel==i+1?2.2f:2,mGR.mSel==i+1?.5f:1);
	   
	   DrawTransScal(gl,mGR.mTex_Diamond,i%2==0?-.259f:.559f,.27f+(i/2)*(-.339f),mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	}
	drawNumber(gl,mGR.mTotalDiamond,.678f,.519f,2);
}
boolean HandlHero(MotionEvent event)
{
	mGR.mSel=0;
	for(int i=0;i<mGR.mPlayer.isUnLock.length;i++)
	{
		if(CircRectsOverlap(i%2==0?-.409f:.419f,.27f+(i/2)*(-.339f),mGR.mTex_Box[0].width()*.4f,mGR.mTex_Box[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=i+1;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		if(mGR.mSel>0)
		{
			if(mGR.mTotalDiamond>=mGR.mPlayer.mVal[mGR.mSel-1])
			{
			  if(!mGR.mPlayer.isUnLock[mGR.mSel-1])
			  {
			    mGR.mTotalDiamond-=mGR.mPlayer.mVal[mGR.mSel-1];
			    mGR.mPlayer.isUnLock[mGR.mSel-1]=true;
			  }
			  mGR.mPlayer.mHeroNo=mGR.mSel-1;
			}
			else
			{
			  if(!mGR.mPlayer.isUnLock[mGR.mSel-1])
			    GameRenderer.mStart.NotEnough();	
			}
		}
		mGR.mSel=0;
	}
	return true;
}

//void DrawShop(GL10 gl)
//{
//	DrawTexture(gl,mGR.mTex_GameBg[0],1.059f,0);
//	DrawTexture(gl,mGR.mTex_CommonBox,0,0);
//	DrawTexture(gl,mGR.mTex_ShopTxt,-.469f,.519f);
//	DrawTexture(gl,mGR.mTex_Diamond,.729f,.529f);
//	for(int i=0;i<mGR.mTex_ShopBlock.length;i++)
//	{
//	   DrawTransScal(gl,mGR.mTex_ShopBlock[i],0,.309f+i*-.25f,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?.5f:1);
//	}
//	drawNumber(gl,mGR.mTotalDiamond,.678f,.519f,2);
//	DrawTransScal(gl,mGR.mTex_DiaEnable[mGR.isDiaEnable?0:1],0,-.619f,mGR.mSel==5?1.1f:1,mGR.mSel==5?.5f:1);
//}
//boolean HandlShop(MotionEvent event)
//{
//	mGR.mSel=0;
//	for(int i=0;i<mGR.mTex_ShopBlock.length;i++)
//	{
//		if(CircRectsOverlap(0,.309f+i*-.25f,mGR.mTex_ShopBlock[0].width()*.4f,mGR.mTex_ShopBlock[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//			mGR.mSel=i+1; 
//	}
//	if(CircRectsOverlap(0,-.619,mGR.mTex_ShopBlock[0].width()*.4f,mGR.mTex_ShopBlock[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//		mGR.mSel=5;
//	if(event.getAction() == MotionEvent.ACTION_UP)
//	{
//		switch(mGR.mSel)
//		{
//		 case 1:
//			 mGR.mMainActivity.onBuy400(null); 
//			 break;
//		 case 2:
//			 mGR.mMainActivity.onBuy1000(null);
//			 break;
//		 case 3:
//			 mGR.mMainActivity.onBuy3000(null);
//			 break;
//		 case 4:
//			 mGR.mMainActivity.onBuy5000(null);
//			 break;
//		 case 5:
//			 mGR.isDiaEnable =!mGR.isDiaEnable;
//			 break;
//		}
//		mGR.mSel=0;
//	}
//	return true;
//}
void DrawGameOver(GL10 gl)
{
	SimplePlane plan[] ={mGR.mTex_Home,mGR.mTex_Leader/*,mGR.mTex_Rate*/,M.GameScreen==M.GAMEOVER?mGR.mTex_Retry:mGR.mTex_PlayIcn};
	DrawTexture(gl,mGR.mTex_GameBg[0],1.059f,0);
	if(M.GameScreen == M.GAMEOVER)
	  DrawTexture(gl,mGR.mTex_GameOver,0,.469f);
	else
	  DrawTexture(gl,mGR.mtex_GamePause,0,.469f);
	DrawTexture(gl,mGR.mTex_ScoreBox,0,0);
	DrawTexture(gl,mGR.mTex_ScoreTxt,0,.129f);
	drawNumber(gl,mGR.mScore,0,.03f,1);
	DrawTexture(gl,mGR.mTex_Best,0,-.069f);
	drawNumber(gl,mGR.mBestScore,0,-.16f,1);
	for(int i=0;i<plan.length;i++)
	   DrawTransScal(gl,plan[i],-.369f+i*.369f,-.439f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	DrawTransScal(gl,mGR.mTex_Share,0,-.749f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
}
boolean HandleOver(MotionEvent event)
{
	mGR.mSel=0;
	for(int i=0;i<3;i++)
 	  if(CircRectsOverlap(-.369f+i*.369f,-.439f,mGR.mTex_Home.width()*.5f,mGR.mTex_Home.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 mGR.mSel=i+1;
	
	if(CircRectsOverlap(0,-.749f,mGR.mTex_Share.width()*.5f,mGR.mTex_Share.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=4;

	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch(mGR.mSel)
		 {
		  case 1:
			   M.GameScreen = M.GAMEMENU;
			  break;
		  case 2:
			  GameRenderer.mStart.onShowLeaderboardsRequested();
			  break;
		  /*case 2:
			  RateUs();
			  break;*/
		  case 3:
			  if(M.GameScreen== M.GAMEOVER)
				  mGR.GameReset();
			   M.GameScreen = M.GAMEPLAY;
			   
			  break;
		  case 4:
			   share2friend();
			  break;
		 }
		 mGR.mSel=0;
	 }
	return true; 
}
void SetDia(float x,int no,float moveVX)
{
	int k=0;
	for(int i=0;i<mGR.mDiamond.length && k<no;i++)
	{
	  if(mGR.mDiamond[i].x==mGR.mDiamond[i].y)
	  {
	     mGR.mDiamond[i].SetDia(x-(k*.12f),-.479f,moveVX);
	     k++;
	  }
	}
}
void GameLogic()
{
	MoveBg();
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		mGR.mBlock[i].update();
		if(mGR.mBlock[i].x<-1.3f)
		{
			M.NewPollSound(GameRenderer.mContext,R.drawable.newbrick);
			mGR.mBlock[i].Set(1.3f,(M.mRand.nextInt(10)+2)*.095f,M.mRand.nextBoolean()?((M.mRand.nextInt(10)+1)*.1f):-((M.mRand.nextInt(3))*.1f));
			if(mGR.isDiaEnable)
			{
				int no=0;
				if(mGR.mBlock[i].dis>.15f)
			       no=M.mRand.nextInt(2)+1;
				else
				{
					if(mGR.mBlock[i].dis>=0)
						no=1;
				}
				float dx;
				if(no>1)
				  dx = M.randomRange(.14f,.21f);
				else
				  dx =.16f;
			    SetDia(mGR.mBlock[i].x-(mGR.mBlock[i].width+dx),no,mGR.VX);
			}
		}
	}
	for(int i=0;i<mGR.mDiamond.length && mGR.isDiaEnable;i++)
	{
		if(mGR.mDiamond[i].x<1.2f)
		   mGR.mDiamond[i].updateDia();
	}
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(!mGR.mBlock[i].isStop && mGR.mBlock[i].x<-.7f)
		{
			mGR.mBlock[i].vx=0;
			mGR.mBlock[i].x=-.7f;
			mGR.mBlock[i].isStop =true;
		}
		if(!mGR.mBlock[i].isDisCmp && mGR.mBlock[i].x<=mGR.mBlock[i].dis)
		{
			mGR.mBlock[i].vx= 0;
			mGR.mBlock[i].x = mGR.mBlock[i].dis;
			mGR.mBlock[i].isDisCmp =true;
			if(mGR.mBlock[i].dis>-.7f)
			 for(int k=0;k<mGR.mDiamond.length;k++)
			 {
				if(mGR.mDiamond[k].mStop==0)
				{
				  mGR.mDiamond[k].x = mGR.mDiamond[k].x-mGR.mDiamond[k].vx;	
				  mGR.mDiamond[k].vx = 0;
				  mGR.mDiamond[k].mStop++;
				}
			 }
		}
	}
	for(int i=0;i<mGR.mDanda.length;i++)
	{
		if(mGR.mDanda[i].sv>0 || mGR.mDanda[i].s>0)
		{
		  mGR.mDanda[i].update();
		}
		if(mGR.mDanda[i].ang==0 &&  mGR.mDanda[i].mAction==1 && mGR.mPlayer.mAction==1)
		{
			M.LineFallSound(GameRenderer.mContext,R.drawable.line_fall);
		    mGR.mPlayer.mAction=2;
		    mGR.mPlayer.vx=.04f;
		    mGR.mDanda[i].angInc=0;
		}
		if(mGR.mDanda[i].mAction==2)
		{
			float x = mGR.mDanda[i].x+mGR.mDanda[i].width;
			if(x<-.7f && x>-1 && mGR.mDanda[i].mStop==0)
			{
				mGR.mDanda[i].vx=0;
				mGR.mDanda[i].mStop++;
			}
	 	 if(x<-1.3f)
	       mGR.mDanda[i].reset();
		}
	}
	if((mGR.mPlayer.vx>0 && mGR.mPlayer.mAction==2) || mGR.mPlayer.vy!=0)
	    mGR.mPlayer.update();
	else
	{
	   float x=((mGR.mBlock[mGR.mBlockSel].x+mGR.mBlock[mGR.mBlockSel].width)-.07f);
	   mGR.mPlayer.update2(x);
	}
	
 	CheckOver();
 	for(int i=0;i<mGR.mDiamond.length && mGR.isDiaEnable;i++)
	{
 		if(mGR.mPlayer.mCrash<1 && mGR.mPlayer.isUlta && mGR.isDiaEnable)
 		{
 			if(CircRectsOverlap(mGR.mDiamond[i].x,mGR.mDiamond[i].y,mGR.mTex_Diamond.width()*.35,mGR.mTex_Diamond.Height()*.35f,mGR.mPlayer.x,mGR.mPlayer.y-.06f,.05f))
 			{
 				M.CoinSound(GameRenderer.mContext,R.drawable.coincollect);
 				mGR.mDiamond[i].bx = mGR.mDiamond[i].x;
 				mGR.mDiamond[i].by = mGR.mDiamond[i].y;
 				mGR.mDiamond[i].set2(.06f,.06f);
 				mGR.mDiamond[i].x  = mGR.mDiamond[i].y=100;
 				mGR.mNoTaken++;
 			}
 		}
	}
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(mGR.mPlayer.mCrash<1 && mGR.mBlockSel!=i)
		{
			if(mGR.mPlayer.vx>0 && CircRectsOverlap(mGR.mBlock[i].x,-.41f,.02f,.02f,GetPoint(),-.41f,.02f))
			{
			   if(mGR.mPerfectAni.t==0)
				{
				  M.PerfectSound(GameRenderer.mContext,R.drawable.perfect); 
				  mGR.mPerfectAni.set2(.03f,.03f);
				  mGR.mScore++;
				}
			}
			if(mGR.mPlayer.isUlta && mGR.isDiaEnable)
		      if(mGR.mPlayer.mAction==2 && CircRectsOverlap(mGR.mBlock[i].x,mGR.mBlock[i].y,mGR.mBlock[i].width,mGR.mTex_Block.Height()*.5f,mGR.mPlayer.x,mGR.mPlayer.y-.06f,.05f))
			  {
		    	 mGR.mPlayer.mCrash=1;
		    	 SetGameOver();
		    	 mGR.mNoTaken=0;
			  }
			  float x=((mGR.mBlock[i].x+mGR.mBlock[i].width)-.06f);
			  if(!mGR.mPlayer.isUlta && mGR.mPlayer.mAction==2 && CircRectsOverlap(x,-.389f,.05f,.05f,mGR.mPlayer.x,mGR.mPlayer.y,.05f))
			   {
					mGR.mScore++;
					mGR.mNumberAni.set(.05f);
					mGR.mPlayer.reset();
					MoveBlock(mGR.VX);
					mGR.mBlockSel=i;
					mGR.mTotalDiamond+=mGR.mNoTaken;
					mGR.mNoTaken=0;
			   }
		}
	}
	if(mGR.mPlayer.mCrash>0)
	{
		if(mGR.mPlayer.x>GetPoint())
			SetGameOver();
		 if(mGR.mPlayer.y<-.7f)
		 {
			if(mGR.mPlayer.y>-.73f)
			{
			  if(M.mRand.nextBoolean())
			    M.Fall1Sound(GameRenderer.mContext,R.drawable.chafall1);
		      else
			    M.Fall2Sound(GameRenderer.mContext,R.drawable.chafall2);
			}
		   if(mGR.mHilanaCnt==100 && mGR.mPlayer.y<-1.1f)
		      mGR.mHilanaCnt=0;
		 }
	}
	if(mGR.mHilanaCnt!=100)
		hilana();
}
void hilana()
{
	 mGR.mWaitCnt++;
	 if(mGR.mHilanaCnt<20)
	 {
	   float rad 	  = (float)Math.toRadians(mGR.mHilanaCnt);
	   mGR.mHilaVal   = (float)(Math.sin(rad*2)*.02f);
	   mGR.mHilanaCnt+=5;
	 }
	 else
	 {
		 mGR.mHilanaCnt=100;
		 mGR.mHilaVal  =0;
		 M.BgStop();
	 }
	 if(mGR.mWaitCnt>30)
	 {	
//		 if(AdCnt%3==0)
		 GameRenderer.mStart.ShowHandle();
	 	 AdCnt++;
		 M.GameScreen = M.GAMEOVER;
		 Counter=0;
		 if(mGR.mScore>mGR.mBestScore)
		 {
		    mGR.mBestScore=mGR.mScore;
			GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score,mGR.mBestScore);
		 }
	 }
}
//float p,p1,p2;
void CheckOver()
{
	if(mGR.mPlayer.mCrash<1 && mGR.mPlayer.vx>0)
	{
		 float p  = GetPoint();
		for(int i=0;i<mGR.mBlock.length;i++)
		{
			if(mGR.mBlockSel!=i)
			{
				float p1 = mGR.mBlock[i].x+mGR.mBlock[i].width;
				float p2 = mGR.mBlock[i].x-mGR.mBlock[i].width;
			    if(p>p1)
				   mGR.mPlayer.mCrash=1;
			    if(p<p2)
				   mGR.mPlayer.mCrash=1;
			}
		}
	}
}
void SetGameOver()
{
	 if(mGR.mDanda[mGR.mBlockSel].mAction!=3)
	 {
		 mGR.mDanda[mGR.mBlockSel].mAction=3;
		 mGR.mDanda[mGR.mBlockSel].angInc=1;
		 mGR.mPlayer.vy =-.02f;
		 mGR.mPlayer.vx =0;
		 mGR.mPlayer.mAction=0;
	 }
}
float GetPoint()
{
	float c=0;
	int i=findClosest();
	if(mGR.mDanda[i].s>0)
	{
	  c = mGR.mDanda[i].x+mGR.mDanda[i].width;
	}
	return c;
}
void MoveBlock(float moveVx)
{
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		mGR.mBlock[i].vx=moveVx;
	}
	for(int i=0;i<mGR.mDanda.length;i++)
	{
		if(mGR.mDanda[i].s>0)
		{
		  mGR.mDanda[i].vx=moveVx;
		  mGR.mDanda[i].mAction=2;
		}
	}
	for(int i=0;i<mGR.mDiamond.length && mGR.isDiaEnable;i++)
	{
		if(mGR.mDiamond[i].x<1.2f)
		{
		   mGR.mDiamond[i].vx = moveVx;
		}
	}
	
}
void MoveBg()
{
	for(int i=0;i<mGR.BG.length;i++)
	  mGR.BG[i]-=mGR.mPlayer.vx*.25f;
    for(int i=0;i<mGR.BG.length;i++)
	{
	  if(mGR.BG[i]<-1-mGR.mTex_GameBg[0].width())
	    mGR.BG[i]=mGR.BG[i==0?1:0]+mGR.mTex_GameBg[0].width(); 
	}
}
void DrawGamePlay(GL10 gl)
{
	for(int i=0;i<mGR.BG.length;i++)
	DrawTexture(gl,mGR.mTex_GameBg[mGR.mBgNo],mGR.BG[i],0+mGR.mHilaVal);
	for(int i=0;i<mGR.mBlock.length;i++)
	{
	   DrawTextureSS(gl,mGR.mTex_Block,mGR.mBlock[i].x,mGR.mBlock[i].y+mGR.mHilaVal,mGR.mBlock[i].s,1,0,0);
	   DrawTexture(gl,mGR.mTex_RedDot,mGR.mBlock[i].x,-.425f+mGR.mHilaVal);
	}
	for(int i=0;i<mGR.mDanda.length;i++)
	  DrawTexturRPS(gl,mGR.mTex_Danda,mGR.mDanda[i].x,mGR.mDanda[i].y+mGR.mHilaVal,mGR.mDanda[i].ang,mGR.mTex_Danda.width()*.5f,0,mGR.mDanda[i].s,1);
	
	for(int i=0;i<mGR.mDiamond.length && mGR.isDiaEnable ;i++)
	{
		if(mGR.mDiamond[i].x>-1.2f && mGR.mDiamond[i].x<1.2f)
			DrawTextureS(gl,mGR.mTex_Diamond,mGR.mDiamond[i].x,mGR.mDiamond[i].y,.7f);
		if(mGR.mDiamond[i].bx>-1.2f && mGR.mDiamond[i].bx<1.2f)
		{
			if(mGR.mDiamond[i].t>0)
			{
			  DrawTransScal(gl,mGR.mTex_Blast,mGR.mDiamond[i].bx,mGR.mDiamond[i].by,mGR.mDiamond[i].s,mGR.mDiamond[i].t);
			  mGR.mDiamond[i].update2();
			}
		}
	}
	if(Counter%2==0)
		Counter2++;
		float dy=.06f;
		if(mGR.mPlayer.mHeroNo==3)
			dy=.09f;
		else if(mGR.mPlayer.mHeroNo==5)
		   dy=.12f;
	switch(mGR.mPlayer.mAction)
	{
	   case 0://Stand
		   if(mGR.mPlayer.isUlta)
			  DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][Counter2%2],mGR.mPlayer.x,mGR.mPlayer.y-dy,180,0,0);
		   else
		      DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][Counter2%2],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	   case 1://Kick
		   int pos[]={1,3};
		    DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos[(Counter2/5)%2]],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	   case 2://Run
		   int pos2[]={1,2};
		   if(mGR.mPlayer.isUlta)
		     DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos2[Counter2%2]],mGR.mPlayer.x,mGR.mPlayer.y-dy,180,0,0);
		   else
			   DrawTextureR(gl,mGR.mTex_Player[mGR.mPlayer.mHeroNo][pos2[Counter2%2]],mGR.mPlayer.x,mGR.mPlayer.y,0,0,0);
		   break;
	}
	DrawTextureS(gl,mGR.mTex_ScoreBox,0,.869f,.35f);
	drawNumberS(gl,mGR.mScore,.04f,.869f,mGR.mNumberAni.s,1);
	if(Math.abs(mGR.mNumberAni.sv)>0)
	  mGR.mNumberAni.update();
	if(mGR.mPerfectAni.t>0)
	{
	  DrawTransScal(gl,mGR.mTex_Perfect,0,0,mGR.mPerfectAni.s,mGR.mPerfectAni.t);
	  mGR.mPerfectAni.update2();
	}
	if(mGR.isDiaEnable)
	{
		DrawTexture(gl,mGR.mTex_Diamond,.899f,.879f);
		drawNumber(gl,mGR.mTotalDiamond,.819f,.869f,2);
	}
//	DrawTexture(gl,mGR.mTex_RedDot,p,-.41f);
//	DrawTexture(gl,mGR.mTex_RedDot,p1,-.41f);
//	DrawTexture(gl,mGR.mTex_RedDot,p2,-.41f);
	GameLogic();
}
boolean HandleGame(MotionEvent event)
{
	
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		if(mGR.isDiaEnable)
		{
			if(mGR.mPlayer.vx>0)
			{
			  if(mGR.mPlayer.x<mGR.mBlock[findFar()].x-mGR.mBlock[findFar()].width)
			  {
				 M.SwingSound(GameRenderer.mContext,R.drawable.swing);
			     mGR.mPlayer.isUlta =!mGR.mPlayer.isUlta;
			  }
			}
		}
		for(int i=0;i<mGR.mBlock.length;i++)
		{
			if(mGR.mBlock[i].vx!=0)
				return true;
		}
	    int i=findClosest();
	    if(mGR.mPlayer.mAction==0 && mGR.mPlayer.vx==0 && mGR.mDanda[i].mAction==-1)
	    {
	      mGR.mDanda[i].Set(mGR.mPlayer.x+.07f,.1f);	
		  mGR.mDanda[i].mAction=0;
	    }
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		for(int i=0;i<mGR.mDanda.length;i++)
		 {
			if(mGR.mDanda[i].sv>0)
			{
			  mGR.mDanda[i].sv=0; 
			  mGR.mDanda[i].angInc=.02f;
			  mGR.mDanda[i].mAction=1;
			  mGR.mPlayer.mAction=1;//Kick
			  Counter2=0;
			}
		 }
	}
	return true;
}
int findClosest()
{
	int num=0;
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(mGR.mBlock[i].x>-1 && mGR.mBlock[i].x<-.69f)
			num=i;
	}
	return num;
}
int findFar()
{
	int num=0;
	for(int i=0;i<mGR.mBlock.length;i++)
	{
		if(mGR.mBlock[i].x>-.7f && mGR.mBlock[i].x<=1)
			num=i;
	}
	return num;
}
void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
{
	Tex.drawPos(gl, x, y);
}
void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY)
{
	Tex.drawRotet(gl, x, y,angle,rotX,rotY);
}
void DrawTexturRPS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY,float sx,float sy)
{
	Tex.drawRotetSP(gl, x, y,angle,rotX,rotY,sx,sy);
}
void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
{
	Tex.drawScal(gl, x, y,scal,scal);
}
void DrawTextureSS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy,float w,float h)
{
	Tex.drawSS(gl, x, y,sx,sy,w,h);
}
void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b,float t,float s)
{
	Tex.drawRGBS(gl, x, y, r, g, b,t,s);
}
void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
{
	Tex.drawTransprentScal(gl, x, y, z, t);
}
void DrawSmoke(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
{
	Tex.drawSmoke(gl, x, y, z, t);
}

boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
{
    if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
       return true;
    return false ;

}
boolean Rext2RectColli(float x1,float y1,float dx1,float dy1,float x2,float y2,float dx2,float dy2)
{
//	x1 -= dx1/2;
//	y1 -= dy1/2;
//	
//	x2 -= dx2/2;
//	y2 -= dy2/2;
	
 if(x1 <=x2+dx2 && x1+dx1>=x2 && y1<=y2+dy2 && dy1+y1>=y2)
    return true;
 
  return false;
}

boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
{
	float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
	if (bVectMag<(r1+r2))
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

int XPos(float x){
        return (int)(((1+x)*M.ScreenWidth)/2);
}

int YPos(float y){
    return (int)(((1-(y))*M.ScreenHieght)/2);
}
	
void drawNumber(GL10 gl,int no,float x, float y,int Align)
{
	float dx = mGR.mTex_Font[0].width()*.6f;
	String str = no+"";
	float len  = str.length();
	if(Align ==1) //Center
	  x-=(dx*len)*.5f;
	if(Align ==2)//Right
  	  x-=(dx*len)*1f;
	String strs = ""+no;
	for(int i =0;i<strs.length();i++)
	{
		int k = ((int)strs.charAt(i))-48;
		if(k>=0 && k<mGR.mTex_Font.length)
			DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y);
	}
}
void drawNumberS(GL10 gl,int no,float x, float y,float s,int Align)
{
	float dx = (mGR.mTex_Font[0].width()*.6f)*s;
	String str = no+"";
	float len  = str.length();
	if(Align ==1) //Center
	  x-=(dx*len)*.5f;
	if(Align ==2)//Right
  	  x-=(dx*len)*1f;
	String strs = ""+no;
	for(int i =0;i<strs.length();i++)
	{
		int k = ((int)strs.charAt(i))-48;
		if(k>=0 && k<mGR.mTex_Font.length)
			DrawTextureS(gl,mGR.mTex_Font[k],x+i*dx,y,s);
	}
}
void Twitter()
{
	Intent mIntent = new Intent(Intent.ACTION_VIEW);
	mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
    GameRenderer.mContext.startActivity(mIntent);
}
void FaceBook()
{
	Intent mIntent = new Intent(Intent.ACTION_VIEW);
	mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
    GameRenderer.mContext.startActivity(mIntent);
}
void Google()
{
	Intent mIntent = new Intent(Intent.ACTION_VIEW);
	mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
    GameRenderer.mContext.startActivity(mIntent);
}
void RateUs()
{
	Intent mIntent = new Intent(Intent.ACTION_VIEW);
	mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    GameRenderer.mContext.startActivity(mIntent);
}
//void MoreGame()
//{
//	Intent mIntent = new Intent(Intent.ACTION_VIEW);
//	mIntent.setData(Uri.parse(M.MARKET));
//	GameRenderer.mContext.startActivity(mIntent);
//}
void share2friend()
{
	shareImage();
 }
void shareImage(){
	  Uri uri = Uri.parse("android.resource://"+getClass().getPackage().getName()+"/drawable/share");
    Intent shareIntent = new Intent();
    shareIntent.setType("image/png");
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
    shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+" "+mGR.mScore+" "+"in  "+GameRenderer.mContext.getResources().getString(R.string.app_name)+"  Can you beat me..."+"Download Latest new game & enjoy!!!! "+"     "+(M.SHARELINK+getClass().getPackage().getName()));
    try{      
  	  GameRenderer.mContext.startActivity(Intent.createChooser(shareIntent,"Share from"));
     }catch (android.content.ActivityNotFoundException ex){
	    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	  }
}
  
}
