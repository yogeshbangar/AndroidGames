package com.oneday.games24.motocitytraffic;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0,Counter2=0;
	final int Check=100;
	float sx=0,sy=0,px=0,py=0;
	float trans=1,inc=0;
	float mScale =1;
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


	
	public void draw(GL10 gl) 
	{
	  Counter++;
	  switch (M.GameScreen)
	   {
		  case Check:
//               DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);		
//			   float ani;	
//			   if(Counter%2==1)
//			  	 Counter2++;
//			    ani= .5f-/*Math.abs*/((float)Math.cos(Math.toRadians(Counter2*40%360))*.5f);
//				DrawTransScal(gl,mGR.mTex_SlowEffect,0,0,1,ani);
			break;
		  case M.GAMELOGO:
			 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80)
			{
//				if(!GameRenderer.mStart.addFree)
//			       M.GameScreen = M.GAMEADD;
//				else
				{
				   M.SplashPlay(GameRenderer.mContext,R.raw.splash_theme);
				   M.GameScreen = M.GAMESPLASH;
				   setSplash();
				   Counter =0;
				   mScale =.1f;
				}
				Counter =0;
			}
			break;
		 case M.GAMEADD:
		 case M.GAMELOADING:
			 if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else
			{
				DrawTexture(gl, mGR.mTex_LoadBar,0.0f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_LoadBar.width()/100f)-.32f,-.9f);
			}
			break;
		 case M.GAMESPLASH:
			 DrawSplash(gl);
			 break;
		 case M.GAMEFREECOIN:
			 DrawFreeCoin(gl);
			 break;
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEABTUS:
			DrawAbtUs(gl);
			break;
		case M.GAMEWORLD:
			DrawWorld(gl); 
			break;
		case M.GAMESHOP:
			DrawShop(gl);
			break;
		case M.COINSHOP:
		case M.KEYSHOP:
		case M.WORLDSHOP:
			DrawAllShop(gl);
			break;
		case M.GAMECHALLENGE:
			DrawChallenge(gl);
			break;
		case M.GAMEPAUSE:
			DrawPause(gl);
			break;
		case M.GAMEOVER:
			DrawOver(gl);
			break; 
		case M.GAMEUSEPOWER:
			DrawUsePower(gl);
			break;
		case M.GAMEPLAY:
		case M.GAMEKEYUSE:
			DrawGameplay(gl);
			break;
	   }
	  if(mGR.isJoin)
		  DrawJoin(gl);
	   
//		setting();
	}
	
public boolean TouchEvent(MotionEvent event) 
{
	System.out.println("GameScreen "+M.GameScreen);
	   if(mGR.isJoin)
		   return HandleJoin(event);
	  switch (M.GameScreen)
	  {
			case M.GAMELOGO:
				break;
			case Check:
				
				break;
			case M.GAMEADD:
			case M.GAMELOADING:
				if((MotionEvent.ACTION_UP == event.getAction()) && (Counter>100)
				   && CirCir(0.9f,.9f,.11f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				   {
					 if(M.GameScreen == M.GAMEADD)
					 {
					    M.SplashPlay(GameRenderer.mContext,R.raw.splash_theme);
					    M.GameScreen = M.GAMESPLASH;
					    setSplash();
					    Counter =0;
					    mScale =.1f;
					 }
					 else
					 {
						M.GameScreen = M.GAMEPLAY;
						M.BGPlay(GameRenderer.mContext,R.raw.bg);
					 }
				   }
				break;
			case M.GAMESPLASH:
				  if(event.getAction() == MotionEvent.ACTION_UP)
				  {
					  if(mGR.mFreeCoin>0)
					  {
					    M.FreeCoinSound(GameRenderer.mContext,R.raw.freecoin);
					    M.GameScreen   = M.GAMEFREECOIN;
					    mGR.mTotalCoin +=mGR.mFreeCoin;
					    mGR.mFreeCoin=0;
					    mScale =.1f;
					  }
					  else
					  {
						  M.GameScreen = M.GAMEWORLD;
					  }
					  M.StopSound();
				  }
				break;
			case M.GAMEFREECOIN:
				  if(event.getAction() == MotionEvent.ACTION_UP && mScale >=1)
				  {
					  M.GameScreen = M.GAMEWORLD;
				  }
				break;
			case M.GAMEMENU:
				HandleMenu(event);
				break;
			case M.GAMEABTUS:
				HandleAbtUs(event);
				break;
			case M.GAMEWORLD:
				HandleWorld(event);
				break;
			case M.GAMESHOP:
				HandleShop(event);
				break;
			case M.COINSHOP:
			case M.KEYSHOP:
			case M.WORLDSHOP:
				HandleAllShop(event);
				break;
			case M.GAMECHALLENGE:
				HandleChallenge(event);
				break;
			case M.GAMEPAUSE:
				HandlePause(event);
				break;
			case M.GAMEOVER:
				HandleOver(event);
				break;
			case M.GAMEKEYUSE:
				HandleKeyUse(event);
				break;
			case M.GAMEPLAY:
				HandleGame(event);
				break;
			case M.GAMEUSEPOWER:
				HandleUsePower(event);
				break;
   	  }
	  
	  
	  if(event.getAction()==MotionEvent.ACTION_DOWN)
	  {
		  if(mGR.mSel!=0)
			 M.ClickSound(GameRenderer.mContext,R.raw.click);
		  mGR.tmpCoin=mGR.mTotalCoin;
	  }
	  if(event.getAction()==MotionEvent.ACTION_UP)
	  {
		  int diff = mGR.tmpCoin-mGR.mTotalCoin;
		  if(diff>0)
		  mGR.mKharchCoin+=diff;
		  CoinAchieve();
//		  System.out.println("innnnnnnnnnnn   "+mGR.mKharchCoin+" tmp=============     "+mGR.tmpCoin);
		  
	  }
	  WorldAchieve();
	  Handle(event);
	return true;
}

void setSplash()
{
	float x4[]   = {-.784f,.785f  ,-.774f ,.765f};
	float y4[]   = {1.197f,-1.192f,-1.197f,1.182f};
	float ang4[] = {47    ,227    ,313    ,133f};
	int pos=0;
	
	pos = M.mRand.nextBoolean()?0:2;
	mGR.mSplashCar[0].set(x4[pos],y4[pos],0,0,ang4[pos]);
	ResetBoost(0,-100,-100);
	pos++;
	pos%=x4.length;
	mGR.mSplashCar[1].set(x4[pos],y4[pos],0,0,ang4[pos]);
	ResetBoost(1,-100,-100);

}
void DrawSplash(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
       mScale *=1.6f;
	   if(mScale >=1.2f) {
		   mScale = 1f;
	   }
	   for(int i=0;i<mGR.mSplashCar.length;i++)
	   {
		   DrawBoost(i,gl);
		   DrawTextureR(gl,mGR.mTex_Car[mGR.mSplashCar[i].no],mGR.mSplashCar[i].x,mGR.mSplashCar[i].y,mGR.mSplashCar[i].ang,0,0);
		   
	   }
	   for(int i=0;i<mGR.mSplashCar.length;i++)
	   {
		   mGR.mSplashCar[i].update();
		   float dx,dy;
		   if(mGR.mSplashCar[i].no<19)
			  {
					 //For Car
				  dx = mGR.mTex_Car[mGR.mSplashCar[i].no].width() *.24f;
				  dy = mGR.mTex_Car[mGR.mSplashCar[i].no].Height()*.699f;  
			  }
			 else if(mGR.mSplashCar[i].no==21)
			  {
					 // For Fire Brigade
			      dx = mGR.mTex_Car[mGR.mSplashCar[i].no].width()*.21f;
			      dy = mGR.mTex_Car[mGR.mSplashCar[i].no].Height()*1.209f;
			  }
			 else
			  {
				 // For Tanker
				  dx = mGR.mTex_Car[mGR.mSplashCar[i].no].width()*.23f;
				  dy = mGR.mTex_Car[mGR.mSplashCar[i].no].Height()*1.389f; 
			  }
		   float rad = (float)Math.toRadians(mGR.mSplashCar[i].ang);
	       px = (float)(dx*Math.cos(rad));
		   py = (float)(dy*Math.sin(rad));
		   if(Counter%2==0)
		    setBoost(i,mGR.mSplashCar[i].x-px,mGR.mSplashCar[i].y+py,mGR.mSplashCar[i].vx,mGR.mSplashCar[i].vy,mGR.mSplashCar[i].ang);
		   if(mGR.mSplashCar[i].x>1.3f || mGR.mSplashCar[i].x<-1.3f || mGR.mSplashCar[i].y<-1.3f || mGR.mSplashCar[i].y>1.3f)
		   {
			   setSplash();
		   }
	   }
	   DrawTextureSS(gl,mGR.mTex_SplashFont,0-mGR.mTex_SplashFont.width()*.4f,.389f, mScale,1f,mGR.mTex_SplashFont.width()*.4f,0);
	   DrawTexture(gl,mGR.mTex_Tap2Play,0,-.3f);
}
void DrawFreeCoin(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	DrawTextureS(gl,mGR.mTex_TransBg,0,0 , mScale);
	DrawTextureS(gl,mGR.mTex_SmallPup,0,0, mScale);
	mGR.mFont.Draw_StringS(gl,"Congratulation",0,.43f,1, mScale);
	mGR.mFont.Draw_StringS(gl,"You have got 500 Coins",0,.1f,1, mScale);
	DrawTextureS(gl,mGR.mTex_BigBox,-.16f+.089f,-.24f, mScale);
	mGR.mFont.Draw_StringS(gl,"500",-.13f+.089f,-.24f,1, mScale);
	DrawTextureS(gl,mGR.mTex_CoinIcn,.069f+.089f,-.24f, mScale);
	
	DrawTextureS(gl,mGR.mTex_Tap2Play,0,-.519f, mScale);
	mScale *=1.6f;
   if(mScale >=1.2f)
	  mScale =1f;
   
}
String Menu[] ={"About","Sound","Music","Reset","Share"};
void DrawMenu(GL10 gl)
{
	
	float x=.089f,y=.5f,dy=.31f;
	DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	DrawTexture(gl,mGR.mTex_TransBg,0,0);
	for(int i=0;i<5;i++)
	{
		DrawTransScal(gl,mGR.mTex_MenuBox    ,x,y-i*dy     ,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		DrawTransScal(gl,mGR.mTex_MenuIcn[i] ,x-.26f,y-i*dy,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		
		mGR.mFont.Draw_String(gl,Menu[i]   ,x ,y-i*dy,1);
	}
	if(!M.setValue)
	  DrawTransScal(gl,mGR.mTex_soundOff ,x-.26f,y-1*dy,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	if(!M.SetBG)
	  DrawTransScal(gl,mGR.mTex_MusicOff ,x-.26f,y-2*dy,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
	
//	DrawTransScal(gl,mGR.mTex_GoogleBtn ,-.809f,-.779f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
//	DrawTransScal(gl,mGR.mTex_FbBtn     ,-.629f,-.779f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
	DrawTransScal(gl,mGR.mTex_BackBtn   ,.729f,-.779f ,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);


		
}
boolean HandleMenu(MotionEvent event)
{
	mGR.mSel=0;
	float x=.089f,y=.5f,dy=.31f;
	for(int i=0;i<5;i++)
	{
	  if(CircRectsOverlap(x,y-i*dy,mGR.mTex_MenuBox.width()*.8f,mGR.mTex_MenuBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	  {
		 mGR.mSel =i+1; //Menu
	  }
	}
	if(CircRectsOverlap(-.809f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =6;
	if(CircRectsOverlap(-.629f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =7;
	if(CircRectsOverlap(.729f ,-.779f,mGR.mTex_BackBtn.width()*.4f,mGR.mTex_BackBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =8;

	if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 
		 switch(mGR.mSel)
		 {
		   case 1:
			   M.GameScreen = M.GAMEABTUS;
			  break;
		   case 2:
			   M.setValue = !M.setValue; 
			  break;
		   case 3:
			   M.SetBG = !M.SetBG;
			  break;
		   case 4:
			   // Reset Game
			   GameRenderer.mStart.resetGame();
			  break;
		   case 5:
			   share2friend();
			   break;
		   case 6:
//			   Google();
			   break;
		   case 7:
//			   FaceBook();
			   break;
		   case 8:
			   mGR.resetWorld();
			   M.GameScreen = M.GAMEWORLD;
			   break;
		}
		 mGR.mSel=0;
	 }
	return true;
}
void DrawWorld(GL10 gl)
{
	 gl.glDisable(GL10.GL_SCISSOR_TEST);
 	 DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
 	 DrawTexture(gl,mGR.mTex_TransBg,0,0);
 	 DrawTexture(gl,mGR.mTex_SmallPup,0,0);
 	 
 	 DrawTexture(gl,mGR.mTex_BigBox,-.709f,.769f);
 	 DrawTransScal(gl,mGR.mTex_ShopIcn,-.479f,.769f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
 	 mGR.mFont.Draw_String(gl,"Shop",-.759f,.759f,0);
 	 
 	 DrawTexture(gl,mGR.mTex_SmallBox,-.069f,.769f);
 	 DrawTransScal(gl,mGR.mTex_KeyIcn  ,.079f ,.769f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
 	 DrawTransScal(gl,mGR.mTex_Add   ,-.139f,.909f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
 	 mGR.mFont.Draw_String(gl,mGR.mNoKey+"",-.09f,.759f,0);
 	 
 	 DrawTexture(gl,mGR.mTex_BigBox   ,.559f,.769f);
 	 DrawTransScal(gl,mGR.mTex_CoinIcn  ,.789f,.769f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
 	 DrawTransScal(gl,mGR.mTex_Add    ,.409f,.909f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
 	 mGR.mFont.Draw_String(gl, mGR.mTotalCoin+"",.47f,.759f,0);
 	
 	 DrawTransScal(gl,mGR.mTex_CtrlBtn,-.799f ,-.799f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
 	 DrawTransScal(gl,mGR.mTex_ChallengeBtn,.799f  ,-.799f,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
 	 //DrawTransScal(gl,mGR.mTex_Join ,0 ,-.799f,mGR.mSel==9?1.1f:1,mGR.mSel==9?.5f:1);

 	 DrawTextureRST(gl ,mGR.mTex_Arrow ,-.669f,.17f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1,180);
 	 DrawTextureRST(gl ,mGR.mTex_Arrow ,.669f ,.17f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1,0);
	 gl.glScissor(XPos(-.599f),0,(int)M.ScreenWidth-2*XPos(-.599f),(int)M.ScreenHieght);
	 gl.glEnable(GL10.GL_SCISSOR_TEST);
	for(int i=0;i<mGR.mWorld.length;i++)
	{
	   if(mGR.mWorld[i].x>-1 && mGR.mWorld[i].x<1)
	   {
	     if(mGR.mWorld[i].isUnLock)	
	    	 DrawTransScal(gl,mGR.mTex_WorldUnLock[i],mGR.mWorld[i].x,.17f,mGR.mWorld[i].z,mGR.mSel==i+10?.5f:1f);
	     else
	    	 DrawTransScal(gl,mGR.mTex_WorldLock[i],mGR.mWorld[i].x,.17f,mGR.mWorld[i].z,mGR.mSel==i+10?.5f:1f);
	     
	     mGR.mFont.Draw_String(gl,(i+1)+"",mGR.mWorld[i].x+.028f,.17f,1);
	     if(mGR.mWorld[i].x>-.005f && mGR.mWorld[i].x<.005f)
		    {
			   if(World.Count>5)
			   {
				   mGR.mWorldSel=i;
				   World.VX=0;
				   mGR.mMove=0;
			   }
		    }
	    }
	 }
	 if(mGR.mMove==0 && !mGR.mWorld[mGR.mWorldSel].isUnLock)
	  {
	     DrawTexture(gl,mGR.mTex_BigBox   ,-.26f ,-.30f);
	     mGR.mFont.Draw_String(gl, mGR.mWorld[mGR.mWorldSel].mWorldCoin+"",-.33f ,-.30f,0);
	     DrawTexture(gl,mGR.mTex_CoinIcn  ,-.04f ,-.30f);
	  }
	  DrawTransScal(gl,mGR.mTex_PlayBtn,.188f ,-.30f,mGR.mSel==6?1.1f:1,mGR.mSel==6?.5f:1);
	  if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	  {
		  DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
		  mGR.mAchieveAni.updateAchieve();
  	  }
     updateWorld();
}
float findSmall()
{
	float small = -1.2f; 
	for(int i=0;i<mGR.mWorld.length;i++)
	{
		if(mGR.mWorld[i].x<small)
		{
			small=mGR.mWorld[i].x;
		}
	}
	return small;
}
float findBig()
{
	float big = 1.2f;
	for(int i=0;i<mGR.mWorld.length;i++)
	{
		if(mGR.mWorld[i].x>big)
		{
			big=mGR.mWorld[i].x;
		}
	}
	return big;
}
boolean HandleWorld(MotionEvent event)
{
	 mGR.mSel=0;
	 if(CircRectsOverlap(-.669f,.17f,mGR.mTex_Arrow.width()*.4f,mGR.mTex_Arrow.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		// Left
		   mGR.mSel=1;
	 }
	 if(CircRectsOverlap(.669f ,.17f,mGR.mTex_Arrow.width()*.4f,mGR.mTex_Arrow.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
  	    //Right
		  mGR.mSel=2;
	 }
	 if(CircRectsOverlap(-.479f,.769f,mGR.mTex_ShopIcn.width()*.4f,mGR.mTex_ShopIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=3; //Shop
	 }
	 if(CircRectsOverlap(0,.769f,mGR.mTex_KeyIcn.width()*.8f,mGR.mTex_KeyIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=4; //Key
	 }
	 if(CircRectsOverlap(.65f,.769f,mGR.mTex_BigBox.width()*.7f,mGR.mTex_BigBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=5; //TotalCoin
	 }
	 if(CircRectsOverlap(.188f ,-.30f,mGR.mTex_PlayBtn.width()*.4f,mGR.mTex_PlayBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=6; //Play
	 }
	 if(CircRectsOverlap(-.799f,-.799f,mGR.mTex_CtrlBtn.width()*.4f,mGR.mTex_CtrlBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=7; //Control
	 }
	 if(CircRectsOverlap(.799f,-.799f,mGR.mTex_ChallengeBtn.width()*.4f,mGR.mTex_ChallengeBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=8; //Challenge
	 }
	 if(CircRectsOverlap(0,-.799f,mGR.mTex_Join.width()*.4f,mGR.mTex_Join.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=9; //LeaderBoard
	 }
	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 
		 switch(mGR.mSel)
		 {
		  case 1:
			  mGR.mMove=1;
			  World.Count=0;
			  break;
		  case 2:
			  mGR.mMove=2;
			  World.Count=0;
			  break;
		   case 3:
			   M.GameScreen = M.GAMESHOP;
			  break;
		   case 4:
			   M.GameScreen = M.KEYSHOP;
			  break;
		   case 5:
			   M.GameScreen = M.COINSHOP;
			  break;
		   case 6:
			   //Play
			   if(mGR.mWorld[mGR.mWorldSel].isUnLock)
			   {
				   mGR.mWorldNo = mGR.mWorldSel;
				   mGR.GameReset();
				   M.GameScreen = M.GAMEPLAY;
				   M.BGPlay(GameRenderer.mContext,R.raw.bg);
//				   if(GameRenderer.mStart.addFree)
//				   {
//				     M.GameScreen = M.GAMEPLAY;
//				     M.BGPlay(GameRenderer.mContext,R.raw.bg);
//				   }
//				   else
//				     M.GameScreen = M.GAMELOADING;
			   }
			   else
			   {
				   if(mGR.mTotalCoin>=mGR.mWorld[mGR.mWorldSel].mWorldCoin)
				   {
					   mGR.mTotalCoin -=mGR.mWorld[mGR.mWorldSel].mWorldCoin;
					   mGR.mWorld[mGR.mWorldSel].isUnLock=true;
					   mGR.GameReset();
					   if(GameRenderer.mStart.addFree)
					   {
					     M.GameScreen = M.GAMEPLAY;
					     M.BGPlay(GameRenderer.mContext,R.raw.bg);
					   }
					   else
					     M.GameScreen = M.GAMELOADING;
				   }
				   else
				   {
					   GameRenderer.mStart.NoCoin();
				   }
			   }
			   
			  break;
		   case 7:
			   M.GameScreen = M.GAMEMENU;
			   break;
		   case 8:
               M.GameScreen = M.GAMECHALLENGE;
			   break;
		   case 9:
			    mGR.isJoin=false;
			   break;
		}
		 World.VX = 0;
		 mGR.mSel=0;
		 if(mGR.isJoin)
		   mGR.mJoinAnim=10;
	 }
	return true;
}
int UnlockVal[]  = new int[5];
int UpGrdVal []  = new int[5];
boolean isUnLock[] = new boolean[5];
void GetunlockVal()
{
	UnlockVal[0] = mGR.mSlowCar.mUnlockVal;
	UnlockVal[1] = mGR.mFastCar.mUnlockVal;
	UnlockVal[2] = mGR.mBridge.mUnlockVal;
	UnlockVal[3] = mGR.mTunnel.mUnlockVal;
	UnlockVal[4] = mGR.mStopCar.mUnlockVal;
}
void GetupgrdVal()
{
	UpGrdVal[0] = mGR.mSlowCar.mUpgradeVal;
	UpGrdVal[1] = mGR.mFastCar.mUpgradeVal;
	UpGrdVal[2] = mGR.mBridge.mUpgradeVal;
	UpGrdVal[3] = mGR.mTunnel.mUpgradeVal;
	UpGrdVal[4] = mGR.mStopCar.mUpgradeVal;
}
void CheckunLock()
{
	isUnLock[0] = mGR.mSlowCar.isUnlock;
	isUnLock[1] = mGR.mFastCar.isUnlock;
	isUnLock[2] = mGR.mBridge .isUnlock;
	isUnLock[3] = mGR.mTunnel .isUnlock;
	isUnLock[4] = mGR.mStopCar.isUnlock;
}
void DrawShop(GL10 gl)
{
	 float x=-.75f,dx =.379f;
	 DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	 DrawTexture(gl,mGR.mTex_TransBg,0,0);
	 DrawTexture(gl,mGR.mTex_BigPup,0,0);
	 
 	 GetunlockVal();
 	 GetupgrdVal();
 	 CheckunLock();
 	 for(int i=0;i<mGR.mTex_GamePower.length;i++)
 	 {
 		DrawTexture(gl,mGR.mTex_Preview          ,x+i*dx,.18f);
// 		if(i==2 || i==3 )
// 		  DrawTextureSS(gl,mGR.mTex_GamePower[i],x+i*dx,.23f,1f,.6f,0,0);
// 		else
		DrawTexture(gl,mGR.mTex_GamePower[i]  ,x+i*dx,.23f);
 		if(!isUnLock[i])
 		 {
 		   DrawTexture(gl,mGR.mTex_PreviewBox,(x-.1f)+i*dx,-.009f);
 		   mGR.mFont.Draw_String(gl, UnlockVal[i]+"",(x-.08f)+i*dx,-.009f,1);
  		   DrawTexture(gl,mGR.mTex_SmallCoin ,(x+.02f)+i*dx,-.009f);
 		   DrawTexture(gl,mGR.mTex_Lock      ,(x+.1f)+i*dx,.02f);
 		   DrawTransScal(gl,mGR.mTex_UnlockTxt ,x+i*dx,-.169f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
  		 }
 		else
 		{
 		  float ani = 1-Math.abs((float)Math.cos(Math.toRadians(Counter*5))*.3f);	
 		  DrawTransScal(gl,mGR.mTex_Buy ,x+i*dx,-.164f,mGR.mSel==i+1?1.2f:ani,mGR.mSel==i+1?.5f:ani);
 		  DrawTexture(gl,mGR.mTex_Power     ,x+i*dx,-.29f);
 		  DrawUpgradeBar(gl,i,x+i*dx);
 		  DrawTexture(gl,mGR.mTex_ShopBox   ,(x-.049f)+i*dx,-.419f);
 		  mGR.mFont.Draw_String(gl, UpGrdVal[i]+"",(x-.032f)+i*dx,-.4195f,1);
 		  DrawTexture(gl,mGR.mTex_SmallCoin  ,(x+.04f)+i*dx,-.419f);
 		  
 		}
 		
 	 }
 	 
 	 DrawTexture(gl,mGR.mTex_SmallBox,-.069f,.769f);
 	 DrawTransScal(gl,mGR.mTex_KeyIcn  ,.079f ,.769f,mGR.mSel==10?1.2f:1,mGR.mSel==10?.5f:1);
	 DrawTransScal(gl,mGR.mTex_Add   ,-.139f,.909f,mGR.mSel==10?1.2f:1,mGR.mSel==10?.5f:1);
	 mGR.mFont.Draw_String(gl,mGR.mNoKey+"",-.09f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_BigBox   ,.559f,.769f);
	 DrawTransScal(gl,mGR.mTex_CoinIcn  ,.789f,.769f,mGR.mSel==11?1.2f:1,mGR.mSel==11?.5f:1);
	 DrawTransScal(gl,mGR.mTex_Add    ,.409f,.909f,mGR.mSel==11?1.2f:1,mGR.mSel==11?.5f:1);
	 mGR.mFont.Draw_String(gl, mGR.mTotalCoin+"",.47f,.759f,0);
 	 
	 DrawTransScal(gl,mGR.mTex_CtrlBtn,-.809f,-.809f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
//	 DrawTransScal(gl,mGR.mTex_AchieveBtn,-.599f,-.809f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
//	 DrawTransScal(gl,mGR.mTex_LeaderBtn ,-.389f,-.809f,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
	 DrawTransScal(gl,mGR.mTex_PlayBtn   ,.809f ,-.819f,mGR.mSel==9?1.2f:1,mGR.mSel==9?.5f:1);
	if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	{
	  DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
	  mGR.mAchieveAni.updateAchieve();
	}
   PowerUnlockAchieve();
	DrawTransScal(gl,mGR.mTexFreeCoin   ,-.009f ,-.759f, mGR.mSel==12?1.2f: (float) (1.5f + 0.2*(Math.sin(Counter*.1f))),mGR.mSel==12?.5f:1);
 	 
}
void DrawUpgradeBar(GL10 gl,int i,float x)
{
	switch(i)
	{
	  case 0:
		  for(int j=0;j<mGR.mSlowCar.noBuy && j<9;j++)
		    DrawTexture(gl,mGR.mTex_UpgradeFill,x-.129f+j*.031f,-.29f);
		 break;
	  case 1:
		  for(int j=0;j<mGR.mFastCar.noBuy && j<9;j++)
			  DrawTexture(gl,mGR.mTex_UpgradeFill,x-.129f+j*.031f,-.29f);
		  break;
	  case 2:
		  for(int j=0;j<mGR.mBridge.noBuy && j<9;j++)
			  DrawTexture(gl,mGR.mTex_UpgradeFill,x-.129f+j*.031f,-.29f);
		  break;
	  case 3:
		  for(int j=0;j<mGR.mTunnel.noBuy && j<9;j++)
			  DrawTexture(gl,mGR.mTex_UpgradeFill,x-.129f+j*.031f,-.29f);
		  break;
	  case 4:
		  for(int j=0;j<mGR.mStopCar.noBuy && j<9;j++)
			  DrawTexture(gl,mGR.mTex_UpgradeFill,x-.129f+j*.031f,-.29f);
		  break;
	  
	}

}
boolean HandleShop(MotionEvent event)
{
	mGR.mSel=0;
 	 HandleUnLock(event);
 	 HandleUpGrde(event);
	if(CircRectsOverlap(-.809f,-.809f, mGR.mTex_CtrlBtn.width()*.4f, mGR.mTex_CtrlBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=6;	//Control
	}
	if(CircRectsOverlap(-.599f,-.809f, mGR.mTex_AchieveBtn.width()*.4f, mGR.mTex_AchieveBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=7;	//Achieve
	}
	if(CircRectsOverlap(-.389f,-.809f, mGR.mTex_LeaderBtn.width()*.4f, mGR.mTex_LeaderBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=8;	//LeaderBoard
	}
	if(CircRectsOverlap(.809f,-.809f, mGR.mTex_PlayBtn.width()*.4f, mGR.mTex_PlayBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=9; //PlayBtn
	}
	if(CircRectsOverlap(.0f,.769f, mGR.mTex_KeyIcn.width()*.8f, mGR.mTex_KeyIcn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=10; //KeyShop
	}
	if(CircRectsOverlap(.65f,.769f, mGR.mTex_BigBox.width()*.7f, mGR.mTex_BigBox.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=11; //CoinShop
	}
	if(CircRectsOverlap(-.009f ,-.759f, mGR.mTex_BigBox.width()*.7f, mGR.mTex_BigBox.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=12; //CoinShop
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
			case 6:
				M.GameScreen = M.GAMEMENU;
				break;
			case 7:
				  mGR.isJoin =false;
				break;
			case 8:
				 mGR.isJoin =false;
				break;
			case 9:
				 //Play
				 if(mGR.mWorld[mGR.mWorldSel].isUnLock)
				   {
					   mGR.mWorldNo = mGR.mWorldSel;
					   mGR.GameReset();
					   M.GameScreen = M.GAMEPLAY;
					   M.BGPlay(GameRenderer.mContext,R.raw.bg);
//					   if(GameRenderer.mStart.addFree)
//					   {
//					     M.GameScreen = M.GAMEPLAY;
//					     M.BGPlay(GameRenderer.mContext,R.raw.bg);
//					   }
//					   else
//					     M.GameScreen = M.GAMELOADING;
				   }
				   else
				   {
					   if(mGR.mTotalCoin>=mGR.mWorld[mGR.mWorldSel].mWorldCoin)
					   {
						   mGR.mTotalCoin -=mGR.mWorld[mGR.mWorldSel].mWorldCoin;
						   mGR.mWorld[mGR.mWorldSel].isUnLock=true;
						   mGR.GameReset();
						   M.GameScreen = M.GAMEPLAY;
						   M.BGPlay(GameRenderer.mContext,R.raw.bg);
//						   if(GameRenderer.mStart.addFree)
//						   {
//						     M.GameScreen = M.GAMEPLAY;
//						     M.BGPlay(GameRenderer.mContext,R.raw.bg);
//						   }
//						   else
//						     M.GameScreen = M.GAMELOADING;
					   }
					   else
					   {
						   GameRenderer.mStart.NoCoin();
					   }
				   }
				break;
			case 10:
				//KeyShop
				M.GameScreen = M.KEYSHOP;
				break;
			case 11:
				//CoinShop
				M.GameScreen = M.COINSHOP;
				break;
			case 12:
				mGR.isReword = false;
				GameRenderer.mStart.mGameAd.ShowRewardVideo();
				break;
		}
		if(mGR.isJoin)
		  mGR.mJoinAnim=10;
		mGR.mSel=0;
	}
	return true;
}
boolean HandleUnLock(MotionEvent event)
{
	mGR.mSel=0;
	float x=-.75f,dx =.379f;
	for(int i=0;i<5;i++)
	{
	   if(!isUnLock[i] && CircRectsOverlap(x+i*dx,-.169f,mGR.mTex_UnlockTxt.width()*.4f,mGR.mTex_UnlockTxt.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	   {
		  mGR.mSel=i+1;
	   }
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  if(mGR.mTotalCoin>=mGR.mSlowCar.mUnlockVal)
			  {
			     mGR.mSlowCar.isUnlock = true;
			     mGR.mTotalCoin -= mGR.mSlowCar.mUnlockVal;
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 2:
			  if(mGR.mTotalCoin>=mGR.mFastCar.mUnlockVal)
			  {
			    mGR.mFastCar.isUnlock = true;
			    mGR.mTotalCoin -= mGR.mFastCar.mUnlockVal;
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 3:
			  if(mGR.mTotalCoin>=mGR.mBridge.mUnlockVal)
			  {
			     mGR.mBridge.isUnlock  = true;
			     mGR.mTotalCoin -= mGR.mBridge.mUnlockVal;
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 4:
			  if(mGR.mTotalCoin>=mGR.mTunnel.mUnlockVal)
			  {
			     mGR.mTunnel.isUnlock  = true;
			     mGR.mTotalCoin -=mGR.mTunnel.mUnlockVal;
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 5:
			  if(mGR.mTotalCoin>=mGR.mStopCar.mUnlockVal)
			  {
			    mGR.mStopCar.isUnlock = true;
			    mGR.mTotalCoin -=mGR.mStopCar.mUnlockVal;
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		}
		mGR.mSel=0;
	}
	return true;
}
boolean HandleUpGrde(MotionEvent event)
{
	float x=-.75f,dx =.379f;
	for(int i=0;i<5;i++)
	{
	   if(isUnLock[i] && CircRectsOverlap(x+i*dx,-.164f,mGR.mTex_UnlockTxt.width()*.4f,mGR.mTex_UnlockTxt.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	   {
		  mGR.mSel=i+1;
	   }
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  if(mGR.mTotalCoin>=mGR.mSlowCar.mUpgradeVal)
			  {
				  mGR.mTotalCoin -=mGR.mSlowCar.mUpgradeVal;
				  mGR.mSlowCar.noBuy++;
				  if(mGR.mSlowCar.noBuy>9)
				  {
					PowerUnlockAchieve();
					mGR.mSlowCar.noBuy =9;
				  }
  			      mGR.mSlowCar.Set(mGR.mSlowCar.noBuy);
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 2:
			   if(mGR.mTotalCoin>=mGR.mFastCar.mUpgradeVal)
			   {
				  mGR.mTotalCoin  -=mGR.mFastCar.mUpgradeVal;
				  mGR.mFastCar.noBuy++;
				  if(mGR.mFastCar.noBuy>9)
				  {
					PowerUnlockAchieve();
					mGR.mFastCar.noBuy =9;
					
				  }
				  
  				   mGR.mFastCar.Set(mGR.mFastCar.noBuy);
			   }
			   else
			   {
				   GameRenderer.mStart.NoCoin();
			   }
			  break;
		  case 3:
			    if(mGR.mTotalCoin>=mGR.mBridge.mUpgradeVal)
			    {
			    	mGR.mTotalCoin -=mGR.mBridge.mUpgradeVal;
			    	mGR.mBridge.noBuy++;
			    	if(mGR.mBridge.noBuy>9)
			    	{
			    	   PowerUnlockAchieve();
			    	   mGR.mBridge.noBuy=9;
			    	}
			    	mGR.mBridge.Set(mGR.mBridge.noBuy);
			    }
			    else
			    {
			    	GameRenderer.mStart.NoCoin();
			    }
			  break;
		  case 4:
			  if(mGR.mTotalCoin>=mGR.mTunnel.mUpgradeVal)
			  {
				  mGR.mTotalCoin -=mGR.mTunnel.mUpgradeVal;
				  mGR.mTunnel.noBuy++;
				  if(mGR.mTunnel.noBuy>9)
				  {
					 PowerUnlockAchieve();
					 mGR.mTunnel.noBuy =9;
				  }
				  mGR.mTunnel.Set(mGR.mTunnel.noBuy);
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		  case 5:
			  if(mGR.mTotalCoin>=mGR.mStopCar.mUpgradeVal)
			  {
				  mGR.mTotalCoin -= mGR.mStopCar.mUpgradeVal;
				  mGR.mStopCar.noBuy++;
				  if(mGR.mStopCar.noBuy>9)
				  {
				    PowerUnlockAchieve();
					mGR.mStopCar.noBuy =9;
				  }
				  mGR.mStopCar.Set(mGR.mStopCar.noBuy);
			  }
			  else
			  {
				  GameRenderer.mStart.NoCoin();
			  }
			  break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawAllShop(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	 DrawTexture(gl,mGR.mTex_TransBg,0,0);
	 DrawTexture(gl,mGR.mTex_SmallPup,0,-.299f);
	
	 DrawTexture(gl,mGR.mTex_SmallBox,-.069f,.769f);
	 DrawTexture(gl,mGR.mTex_KeyIcn  ,.079f ,.769f);
	 DrawTexture(gl,mGR.mTex_Add     ,-.139f,.909f);
	 mGR.mFont.Draw_String(gl,mGR.mNoKey+"",-.09f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_BigBox   ,.559f,.769f);
	 DrawTexture(gl,mGR.mTex_CoinIcn  ,.789f,.769f);
	 DrawTexture(gl,mGR.mTex_Add      ,.409f,.909f);
	 mGR.mFont.Draw_String(gl, mGR.mTotalCoin+"",.47f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_BigBox    ,-.709f,.769f);
	 DrawTransScal(gl,mGR.mTex_ShopIcn ,-.479f,.769f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	 mGR.mFont.Draw_String(gl,"Shop"   ,-.759f,.759f,0);
	 
	 DrawTransScal(gl,mGR.mTex_PauseIcn[2]  ,-.809f,-.809f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	 DrawTransScal(gl,mGR.mTex_PlayBtn      ,.809f ,-.819f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
	 
	 DrawTexture(gl, mGR.mTex_KeyDe[M.GameScreen ==M.KEYSHOP?1:0]    ,-.459f,.439f);
	 DrawTexture(gl, mGR.mTex_CoinDe[M.GameScreen ==M.COINSHOP?1:0]  ,0,.439f);
	 DrawTexture(gl, mGR.mTex_WorldDe[M.GameScreen ==M.WORLDSHOP?1:0],.459f,.439f);
	 
	 switch (M.GameScreen)
	 {
		case M.KEYSHOP:
			DrawKeyShop(gl);
			break;
		case M.COINSHOP:
			DrawCoinShop(gl);
			break;
		case M.WORLDSHOP:
			DrawWorldShop(gl);
			break;
	}
	if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	 {
	   DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
	   mGR.mAchieveAni.updateAchieve();
  	 }
}
int    coinVal[]   = {5000     ,  20000  , 50000   ,100000};
String coinprize[] = {"$: 0.99","$: 1.99","$: 3.99","$: 5.99"};
void DrawCoinShop(GL10 gl)
{
	float y  = .15f;
	float dy =  .3f; 
	for(int i=0;i<4;i++)
	{
//		DrawTexture(gl, mGR.mTex_BigBox,-.329f,y-i*dy);
//		mGR.mFont.Draw_String(gl, coinVal[i]+"",-.315f,y-i*dy,1);
//		DrawTexture(gl, mGR.mTex_CoinIcn,-.109f,y-i*dy);
//		DrawTexture(gl, mGR.mTex_DollarBox,.119f,y-i*dy);
//		mGR.mFont.Draw_String(gl, coinprize[i]+"",.139f,y-i*dy,1);
//		DrawTransScal(gl, mGR.mTex_BuyIcn,.349f,y-i*dy,mGR.mSel ==i+100?1.2f:1,mGR.mSel ==i+100?.5f:1);

		DrawTextureS(gl, mGR.mTex_BigBox,0,-.1f,2);
		mGR.mFont.Draw_String(gl,"Get 500"     ,-.12f,.00f,0);
		mGR.mFont.Draw_String(gl,"Free Coins"     ,-.15f,-.2f,0);
	}
}
boolean HandleCoinShop(MotionEvent event)
{
	mGR.mSel=0;
	float y  = .15f;
	float dy =  .3f; 

	if(CircRectsOverlap(0,0,mGR.mTex_BuyIcn.width(),mGR.mTex_BuyIcn.Height(),screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel =201;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch (mGR.mSel)
		{
		  case 100:
			  //5000 Coin
//			  mGR.mMainActivity.onBuyCoin5000(null);
			  break;
		  case 101:
			  //20000 Coin
//			  mGR.mMainActivity.onBuyCoin20000(null);
			  break;
		  case 102:
			  //50000 Coin
//			  mGR.mMainActivity.onBuyCoin50000(null);
			  break;
		  case 103:
			  //100000 Coin
//			  mGR.mMainActivity.onBuyCoin100000(null);
			  break;
			case 201:
				mGR.isReword = false;
				GameRenderer.mStart.mGameAd.ShowRewardVideo();
				break;
		}
		mGR.mSel =0;
	}
	return true;
}
int    KeyVal[]   = {10       ,  25     , 60      ,100};
String Keyprize[] = {"$: 0.99","$: 1.99","$: 3.99","$: 5.99"};
void DrawKeyShop(GL10 gl)
{
	float y  = .15f;
	float dy =  .3f; 
//	for(int i=0;i<4;i++)
//	{
//		DrawTexture(gl, mGR.mTex_SmallBox,-.26f,y-i*dy);
//		mGR.mFont.Draw_String(gl, KeyVal[i]+"",-.23f,y-i*dy,1);
//		DrawTexture(gl, mGR.mTex_CoinIcn,-.109f,y-i*dy);
//		DrawTexture(gl, mGR.mTex_DollarBox,.119f,y-i*dy);
//		mGR.mFont.Draw_String(gl, Keyprize[i]+"",.139f,y-i*dy,1);
//		DrawTransScal(gl, mGR.mTex_BuyIcn,.349f,y-i*dy,mGR.mSel ==i+100?1.2f:1,mGR.mSel ==i+100?.5f:1);
//	}
	DrawTextureS(gl, mGR.mTex_BigBox,0,-.12f,2);
	mGR.mFont.Draw_String(gl,"Get 5"     ,-.06f,.00f,0);
	mGR.mFont.Draw_String(gl,"Free keys"     ,-.15f,-.2f,0);
}
boolean HandleKeyShop(MotionEvent event)
{
	mGR.mSel=0;
	float y  = .15f;
	float dy =  .3f; 

	if(CircRectsOverlap(0f,-.12,mGR.mTex_BuyIcn.width(),mGR.mTex_BuyIcn.Height(),screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel =201;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch (mGR.mSel)
		{
		  case 100:
			  //10 Key
//			  mGR.mMainActivity.onBuy10Key(null);
			  break;
		  case 101:
			  //25 Key
//			  mGR.mMainActivity.onBuy25Key(null);
			  break;
		  case 102:
			  //60 Key
//			  mGR.mMainActivity.onBuy60Key(null);
			  break;
		  case 103:
			  //100 Key
//			  mGR.mMainActivity.onBuy100Key(null);
			  break;
			case 201:
				mGR.isReword = true;
				GameRenderer.mStart.mGameAd.ShowRewardVideo();
				break;
		}
		mGR.mSel =0;
	}
	return true;
}
void DrawWorldShop(GL10 gl)
{
		
	DrawTextureRST(gl ,mGR.mTex_Arrow ,-.669f,-.009f,mGR.mSel==100?1.2f:1,mGR.mSel==100?.5f:1,180);
	DrawTextureRST(gl ,mGR.mTex_Arrow ,.669f ,-.009f,mGR.mSel==200?1.2f:1,mGR.mSel==200?.5f:1,0);
	gl.glScissor(XPos(-.599f),0,(int)M.ScreenWidth-2*XPos(-.599f),(int)M.ScreenHieght);
	gl.glEnable(GL10.GL_SCISSOR_TEST);
	
	for(int i=0;i<mGR.mWorld.length;i++)
	{
	   if(mGR.mWorld[i].x>-1 && mGR.mWorld[i].x<1)
	   {
		   mGR.mWorld[i].z = 1f-(float)Math.abs(mGR.mWorld[i].x*.85f-0);
	     if(mGR.mWorld[i].isUnLock)	
	    	 DrawTransScal(gl,mGR.mTex_WorldUnLock[i],mGR.mWorld[i].x,-.009f,mGR.mWorld[i].z,mGR.mSel==i+10?.5f:1f);
	     else
	    	 DrawTransScal(gl,mGR.mTex_WorldLock[i],mGR.mWorld[i].x,-.009f,mGR.mWorld[i].z,mGR.mSel==i+10?.5f:1f);
	     
	     mGR.mFont.Draw_String(gl,(i+1)+"",mGR.mWorld[i].x+.028f,.17f,1);
	   }
	   if(mGR.mWorld[i].x>-.005f && mGR.mWorld[i].x<.005f)
	    {
		   if(World.Count>5)
		   {
			   mGR.mWorldSel=i;
			   World.VX=0;
			   mGR.mMove=0;
		   }
	    }
	}
	if(mGR.mMove==0 && !mGR.mWorld[mGR.mWorldSel].isUnLock)
	{
		DrawTexture(gl,mGR.mTex_BigBox   ,-.26f ,-.449f);
		mGR.mFont.Draw_String(gl, mGR.mWorld[mGR.mWorldSel].mWorldCoin+"",-.3f ,-.449f,0);
		DrawTexture(gl,mGR.mTex_CoinIcn  ,-.04f ,-.449f);
		DrawTransScal(gl,mGR.mTex_MenuBox,.188f ,-.449f,mGR.mSel==4?1.1f:1,mGR.mSel==4?.5f:1);
		mGR.mFont.Draw_String(gl, "Unlock", .2f ,-.449f,1);
		if(mGR.mWorld[mGR.mWorldSel].mDollar>0)
		{
		  DrawTexture(gl,mGR.mTex_DollarBox ,-.129f,-.739f);
		  mGR.mFont.Draw_String(gl, mGR.mWorld[mGR.mWorldSel].mDollar+"",-.18f ,-.739f,0);
		  DrawTransScal(gl,mGR.mTex_BuyIcn    ,.099f ,-.739f,mGR.mSel==5?1.1f:1,mGR.mSel==5?.5f:1);
		}
	}
	updateWorld();

}
void updateWorld()
{
	if(mGR.mMove == 1)
	{
		World.VX =-.05f;
	}
	if(mGR.mMove == 2)
	{
		World.VX =.05f;
	}
	for(int i=0;i<mGR.mWorld.length;i++)
	{
		if(mGR.mWorld[i].x>2f && World.VX>0)
	    {
		  float big = findSmall();
		  mGR.mWorld[i].x = big-.4f;
	    }
	    if(mGR.mWorld[i].x<-2.4f && World.VX<0)
	    {
		  float small = findBig();
		  mGR.mWorld[i].x = small+.4f;
	    }
	}
	for(int i=0;i<mGR.mWorld.length;i++)
	{
		if(mGR.mMove!=0)
		{
	       mGR.mWorld[i].update(World.VX);
		}
	}
}

boolean HandleWorldShop(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(-.669f,-.009f,mGR.mTex_Arrow.width()*.4f,mGR.mTex_Arrow.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		// Left
	    mGR.mSel = 100;
	 }
	 if(CircRectsOverlap(.669f ,-.009f,mGR.mTex_Arrow.width()*.4f,mGR.mTex_Arrow.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
	    //Right
	    mGR.mSel = 200;//Right
	 }
	 if(CircRectsOverlap(.188f ,-.449f,mGR.mTex_MenuBox.width()*.4f,mGR.mTex_MenuBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
 	    //Unlock
		 mGR.mSel = 4;
	 }
	 if(CircRectsOverlap(.099f ,-.739f,mGR.mTex_BuyIcn.width()*.4f,mGR.mTex_BuyIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
 	    //Buy World
		 mGR.mSel = 5;
	 }
	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 
		 switch (mGR.mSel)
		 {
		   case 100:
			   World.Count=0;
			   mGR.mMove=1;
			   break;
		   case 200:
			   World.Count=0;
			   mGR.mMove=2;
			   break;
			case 4:
				if(!mGR.mWorld[mGR.mWorldSel].isUnLock)
				{
				   //Unlock
					if(mGR.mTotalCoin>=mGR.mWorld[mGR.mWorldSel].mWorldCoin)
					{
						mGR.mTotalCoin -=mGR.mWorld[mGR.mWorldSel].mWorldCoin;
						mGR.mWorld[mGR.mWorldSel].isUnLock=true;
					}
				   else
				    {
					    GameRenderer.mStart.NoCoin();
				    }
					
				}
				break;
			case 5:
				if(!mGR.mWorld[mGR.mWorldSel].isUnLock && mGR.mWorld[mGR.mWorldSel].mDollar>0)
				{
				   //Buyyy
					switch(mGR.mWorld[mGR.mWorldSel].mWorldCoin)
					{
					  case 5000:
//						  mGR.mMainActivity.onBuyWorld5000(null);
						 break;
					  case 7500:
//						  mGR.mMainActivity.onBuyWorld7500(null);
						 break;
					  case 10000:
//						  mGR.mMainActivity.onBuyWorld10000(null);
						 break;
					}
				}
				break;
			
		}
		 mGR.mSel=0;
	 }
	return true;
}
boolean HandleAllShop(MotionEvent event)
{
	mGR.mSel=0;
	switch(M.GameScreen)
	{
	    case M.KEYSHOP:
	    	HandleKeyShop(event);
	    	break;
	    case M.COINSHOP:
	    	HandleCoinShop(event);
	    	break;
    	case M.WORLDSHOP:
    		HandleWorldShop(event);
    		break;
	}
	if(CircRectsOverlap(-.479f,.769f,mGR.mTex_ShopIcn.width()*.4f,mGR.mTex_ShopIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=1; //Shop
	 }
	if(CircRectsOverlap(-.809f,-.809f, mGR.mTex_CtrlBtn.width()*.4f, mGR.mTex_CtrlBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=2;	//Control
	 }
	if(CircRectsOverlap(.809f,-.809f, mGR.mTex_PlayBtn.width()*.4f, mGR.mTex_PlayBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=3; //PlayBtn
	 }
	  for(int i=0;i<3;i++)
		if(CircRectsOverlap(-.459f+i*.459f,.439f,mGR.mTex_KeyDe[0].width()*.4f, mGR.mTex_KeyDe[0].Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel =i+10;
		}
		
   if(event.getAction() == MotionEvent.ACTION_UP)
   { 
	  switch(mGR.mSel)
		{
	      case 1:
	    	  M.GameScreen = M.GAMESHOP;
	    	 break;
	      case 2:
	    	  mGR.resetWorld();
	    	  M.GameScreen = M.GAMEWORLD;
	    	 break;
	      case 3:
	    	  //Play
			   if(mGR.mWorld[mGR.mWorldSel].isUnLock)
			   {
				   mGR.mWorldNo = mGR.mWorldSel;
				   mGR.GameReset();
				   if(GameRenderer.mStart.addFree)
				   {
				     M.GameScreen = M.GAMEPLAY;
				     M.BGPlay(GameRenderer.mContext,R.raw.bg);
				   }
				   else
				     M.GameScreen = M.GAMELOADING;
			   }
			   else
			   {
				 GameRenderer.mStart.unLockWorld();  
			   }
	         break;
	      case 10:
	    	  M.GameScreen = M.KEYSHOP;
	    	  break;
	      case 11:
	    	  M.GameScreen = M.COINSHOP;
	    	  break;
    	  case 12:
    		  mGR.resetWorld();
    		  M.GameScreen = M.WORLDSHOP;
    		  break;
		}  
	   mGR.mSel=0;
   }
	
	return true;
}
void DrawAbtUs(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	DrawTexture(gl,mGR.mTex_TransBg,0,0);
	DrawTexture(gl,mGR.mTex_AbtTxt,0,0);
	DrawTransScal(gl,mGR.mTex_GoogleBtn ,-.809f,-.779f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	DrawTransScal(gl,mGR.mTex_FbBtn     ,-.629f,-.779f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	DrawTransScal(gl,mGR.mTex_BackBtn   ,.729f,-.779f ,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
}
boolean HandleAbtUs(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(-.809f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =1;
	if(CircRectsOverlap(-.629f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =2;
	if(CircRectsOverlap(.729f ,-.779f,mGR.mTex_BackBtn.width()*.4f,mGR.mTex_BackBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =3;
	if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch(mGR.mSel)
		 {
		   case 1:
//			   Google();
			   break;
		   case 2:
//			   FaceBook();
			   break;
		   case 3:
			   M.GameScreen = M.GAMEMENU;
			   break;
		}
		 mGR.mSel=0;
	 }
	return true;
}

String Pause[] ={"Continue","Retry","Menu","Shop","Setting"};
void DrawPause(GL10 gl)
{
	
	float x=.099f,y=.5f,dy=.31f;
	DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	DrawTexture(gl,mGR.mTex_TransBg,0,0);
	for(int i=0;i<5;i++)
	{
		DrawTransScal(gl,mGR.mTex_MenuBox     ,x,y-i*dy     ,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?.5f:1);
		DrawTransScal(gl,mGR.mTex_PauseIcn[i] ,x-.28f,y-i*dy,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?.5f:1);
		
		mGR.mFont.Draw_String(gl,Pause[i]     ,x-.13f ,y-i*dy,0);
	}
	
//	DrawTransScal(gl,mGR.mTex_GoogleBtn ,-.809f,-.779f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
//	DrawTransScal(gl,mGR.mTex_FbBtn     ,-.629f,-.779f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
	DrawTransScal(gl,mGR.mTex_LeaderBtn ,.809f,-.779f ,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
	
	 
		
}
boolean HandlePause(MotionEvent event)
{
	mGR.mSel=0;
	float x=.099f,y=.5f,dy=.31f;
	for(int i=0;i<5;i++)
	{
	  if(CircRectsOverlap(x,y-i*dy,mGR.mTex_MenuBox.width()*.8f,mGR.mTex_MenuBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	  {
		 mGR.mSel =i+1; //Menu
	  }
	}
	if(CircRectsOverlap(-.809f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =6;
	if(CircRectsOverlap(-.629f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =7;
	if(CircRectsOverlap(.809f ,-.779f,mGR.mTex_LeaderBtn.width()*.4f,mGR.mTex_LeaderBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =8;

	if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 
		 switch(mGR.mSel)
		 {
		   case 1:
			   M.GameScreen = M.GAMEPLAY;
			    if(M.SetBG)
			     M.BGPlay(GameRenderer.mContext,R.raw.bg);
			   else
			     M.BgStop();
			  break;
		   case 2:
			    mGR.GameReset();
			   M.GameScreen = M.GAMEPLAY;
			   M.BGPlay(GameRenderer.mContext,R.raw.bg);
//			   if(GameRenderer.mStart.addFree)
//			   {
//			     M.GameScreen = M.GAMEPLAY;
//			     M.BGPlay(GameRenderer.mContext,R.raw.bg);
//			   }
//			   else
//		          M.GameScreen = M.GAMELOADING;
			  break;
		   case 3:
			   mGR.resetWorld();
			   M.GameScreen = M.GAMEWORLD;
			  break;
		   case 4:
			   M.GameScreen = M.GAMESHOP;
			  break;
		   case 5:
			   M.GameScreen = M.GAMEMENU;
			   break;
		   case 6:
			   Google();
			   break;
		   case 7:
			   FaceBook();
			   break;
		   case 8:
			      mGR.isJoin =false;

			   break;
		}
		 if(mGR.isJoin)
		   mGR.mJoinAnim=10;
		 mGR.mSel=0;
	 }
	return true;
}
void DrawOver(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	DrawTexture(gl,mGR.mTex_TransBg,0,0);	
	DrawTexture(gl,mGR.mTex_SmallPup,0,-.03f);
//	DrawTextureSS(gl,mGR.mTex_SmallPup,0,-.03f,1,.8f,0,mGR.mTex_SmallPup.Height()*.3f);
	 
	 DrawTexture(gl,mGR.mTex_BigBox,-.709f,.769f);
	 DrawTransScal(gl,mGR.mTex_ShopIcn,-.479f,.769f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	 mGR.mFont.Draw_String(gl,"Shop",-.759f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_SmallBox,-.069f,.769f);
	 DrawTransScal(gl,mGR.mTex_KeyIcn  ,.079f ,.769f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	 DrawTransScal(gl,mGR.mTex_Add   ,-.139f,.909f  ,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	 mGR.mFont.Draw_String(gl,mGR.mNoKey+"",-.09f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_BigBox   ,.559f,.769f);
	 DrawTransScal(gl,mGR.mTex_CoinIcn  ,.789f,.769f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
	 DrawTransScal(gl,mGR.mTex_Add    ,.409f,.909f  ,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
	 mGR.mFont.Draw_String(gl, mGR.mTotalCoin+"",.47f,.759f,0);
	 
	 DrawTexture(gl,mGR.mTex_StageFont ,-.28f,.399f);
	 DrawTexture(gl,mGR.mTex_StageBox  , .2f,.399f);
	 
	 for(int i=0;i<mGR.mStageFill && i<33;i++)
 	  DrawTexture(gl,mGR.mTex_StageBar,-.089f+i*.018f,.319f);
	 
	  if(mGR.mStageFill<mGR.mStageCounter)
		 mGR.mStageFill++;
	  else
		  mGR.mStageFill=mGR.mStageCounter;
	  
	 mGR.mFont.Draw_String(gl, mGR.mBestStage+"",-.05f,.429f,0);
//	 mGR.mFont.Draw_String(gl, mGR.mScore+"/"+(mGR.mBestStage+1)*100,.18f,.429f,0);
	 mGR.mFont.Draw_String(gl, mGR.mNoCrossCar+"/"+(mGR.mBestStage+1)*100,.18f,.429f,0);
	 
	 
	 DrawTexture(gl,mGR.mTex_ScoreFont ,-.28f,.02f);
	 DrawTexture(gl,mGR.mTex_ScoreBox  , .2f ,.02f);
	 
	 if(mGR.mCntScore<mGR.mBestScore)
	 {
		if(mGR.mBestScore>100)
	      mGR.mCntScore+=mGR.mBestScore/10;
		else
 		  mGR.mCntScore+=2;
	 }
	 else
	   mGR.mCntScore =mGR.mBestScore;
	 
	 mGR.mFont.Draw_String(gl, mGR.mCntScore+"",-.06f,0,0);
	 DrawTexture(gl,mGR.mTex_BigBox    , -.079f,-.349f);
	 DrawTexture(gl,mGR.mTex_CoinIcn   ,.149f,-.349f);
	 
	 mGR.mFont.Draw_String(gl,mGR.mLevelCoin+"",-.17f,-.359f,0);
	 
//	 DrawTransScal(gl,mGR.mTex_AchieveBtn,-.809f,-.789f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
//	 DrawTransScal(gl,mGR.mTex_LeaderBtn ,-.579f,-.789f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
	 
	 DrawTransScal(gl,mGR.mTex_PlayBtn      ,0,-.789f    ,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
	 DrawTransScal(gl,mGR.mTex_ChallengeBtn,.579f,-.789f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
	 DrawTransScal(gl,mGR.mTex_PauseIcn[2]  ,.809f,-.789f,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
	if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	{
	  DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
	  mGR.mAchieveAni.updateAchieve();
	}
	 
	 
}
boolean HandleOver(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(-.479f,.769f,mGR.mTex_ShopIcn.width()*.4f,mGR.mTex_ShopIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=1; //Shop
	 }
	 if(CircRectsOverlap(0,.769f,mGR.mTex_KeyIcn.width()*.8f,mGR.mTex_KeyIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=2; //Key
	 }
	 if(CircRectsOverlap(.65f,.769f,mGR.mTex_BigBox.width()*.7f,mGR.mTex_BigBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=3; //TotalCoin
	 }
	 if(CircRectsOverlap(-.809f,-.789f,mGR.mTex_AchieveBtn.width()*.4f,mGR.mTex_AchieveBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=4; //Achieve
	 }
	 if(CircRectsOverlap(-.579f,-.789f,mGR.mTex_LeaderBtn .width()*.4f,mGR.mTex_LeaderBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=5; //Leader
	 }
	 if(CircRectsOverlap(0,-.789f,mGR.mTex_PlayBtn.width()*.4f,mGR.mTex_PlayBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=6; //Playy
	 }
	 if(CircRectsOverlap(.579f,-.789f,mGR.mTex_AchieveBtn.width()*.4f,mGR.mTex_AchieveBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=7; //Challenge
	 }
	 if(CircRectsOverlap(.809f,-.789f,mGR.mTex_LeaderBtn .width()*.4f,mGR.mTex_LeaderBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=8; //World
	 }
	 
	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch (mGR.mSel)
		 {
		   case 1:
			    M.GameScreen = M.GAMESHOP;
			   break;
		    case 2:
		    	M.GameScreen = M.KEYSHOP;
			   break;
		   case 3:
			     M.GameScreen = M.COINSHOP;
			   break;
		   case 4:
			   mGR.isJoin=false;
			   break;
		   case 5:
			    mGR.isJoin=false;
			   break;
		   case 6:
			   //Play
			   mGR.GameReset();
			   if(GameRenderer.mStart.addFree)
			   {
			      M.GameScreen = M.GAMEPLAY;
			      M.BGPlay(GameRenderer.mContext,R.raw.bg);
			   }
			   else
			      M.GameScreen = M.GAMELOADING;
			   break;
		   case 7:
			   M.GameScreen = M.GAMECHALLENGE;
			   break;
		   case 8:
			   mGR.resetWorld();
			   M.GameScreen = M.GAMEWORLD;
			   break;
		}
		 mGR.mSel=0;
		 if(mGR.isJoin)
			 mGR.mJoinAnim=10;
	 }
	return true;
}

void ResetBoost(int id,float x,float y)
{
	for(int j=0;j<mGR.mAni[id].length;j++)
	{
  	   mGR.mAni[id][j].SetBoost(x,y,0,0,0);
	}
}

void setBoost(int id,float x,float y,float vx,float vy,float ang)
{
	int cr =0;
	for(int j=0;j<mGR.mAni[id].length && cr<1 /*&& Counter%2==0*/;j++)
	{
		if(mGR.mAni[id][j].x == mGR.mAni[id][j].y)
		{
		    cr++;
			mGR.mAni[id][j].SetBoost(x,y,vx,vy,ang);
		}
	}
}

float[][] CalCarPoints(float x,float y,float ang,int no)
{
      float dx,dy;
	  float rx,ry;
	  float Points[][] = new float[2][2];
	  float rad = (float)Math.toRadians(ang);
	  if(no>16)
	  {
	     dx = mGR.mTex_Car[no].width() *.119f;
	     dy = mGR.mTex_Car[no].Height()*.28f;
	  }
	  else
	  {
		  dx = mGR.mTex_Car[no].width() *.129f;
	      dy = mGR.mTex_Car[no].Height()*.399f;
	  }
       rx = (float)(dx*Math.cos(rad));
	   ry = (float)(dy*Math.sin(rad));
	   for(int i=1;i<=Points[0].length;i++)
	   {
		   Points[0][i-1]  = x-(i<2?i:i-1)*(i<2?rx:-rx);
		   Points[1][i-1]  = y+(i<2?i:i-1)*(i<2?ry:-ry);
	   }
	   return Points;
}

float[][] CalBusPoints(float x,float y,float ang,int no)
{
	  float rx,ry; 
	  float dx,dy;
	  float Points[][] = new float[2][6];
	  if(no ==21)
	  {
	    dx = mGR.mTex_Car[no].width()*.04f+.02f;
	    dy = mGR.mTex_Car[no].Height()*.359f;
	  }
	  else
	  {
		  dx = mGR.mTex_Car[no].width()*.069f+.01f;
	      dy = mGR.mTex_Car[no].Height()*.359f+.03f;
	  }
	  float rad = (float)Math.toRadians(ang);
      rx  = (float)(dx*Math.cos(rad));
	  ry  = (float)(dy*Math.sin(rad));
	  for(int i=1;i<=Points[0].length;i++)
	  {
		  Points[0][i-1]  = x-(i<4?i:i-3)*(i<4?rx:-rx);
		  Points[1][i-1]  = y+(i<4?i:i-3)*(i<4?ry:-ry);
	  }
	  return Points;
}
void StopCar(int go)
{
	for(int i=0;i<mGR.mCar.length;i++)
	{
		if(!mGR.mCar[i].isStop && mGR.mCar[i].x>-.7f && mGR.mCar[i].x<.7f && mGR.mCar[i].y<.7f && mGR.mCar[i].y>-.7f)
		{
			if(go==0)
			{
			 mGR.mCar[i].Speed=0;
			 mGR.mCar[i].isStop=true;
			 mGR.mCar[i].mTap=0;
			}
			else{
				
				mGR.mCar[i].Speed=1;
			    mGR.mCar[i].isStop=false;
			    mGR.mCar[i].mTap=0;
			}
		}
	}
}

void Gamelogic()
{
	
	if(!mGR.isCrash && mGR.mCount > mGR.mGenTime/* && !mGR.mStopCar.isActive*/)
    {
      if(mGR.mGenTime > mGR.mDelay)
      {
    	  if(mGR.mGenTime%3==0)
          {
         	if(mGR.mDelay>40)
         	  mGR.mDelay--;
          }
         mGR.mGenTime -= mGR.mCarGenSpeed;
         mGR.mCarGenSpeed+=2;
      }
      else
      {
         if(mGR.mGenTime>40)
	       mGR.mGenTime=40;
//         System.out.println("mGR.mDelay============   "+mGR.mDelay+"Speddddd======"+mGR.mCarGenSpeed+" Time============== "+mGR.mGenTime);
      }
      mGR.CreateCar();
      mGR.mCount = 0;
    }
	mGR.mCount++;
//	Counter2++;
//	if(Counter2>80)
//    {
//   	  mGR.mCarGenSpeed+=3;
//   	  System.out.println("Speddddd======"+mGR.mCarGenSpeed+"Counter===================  "+Counter2);
//   	  Counter2=0;
//    }
	for(int i=0;i<mGR.mCar.length;i++)
	{
		mGR.mCar[i].update();
		if(mGR.mCar[i].x !=100f && mGR.mCar[i].y!=100f)	
		{
			if(mGR.mCar[i].mTap ==1)
		    {
				float dx,dy;
				if(mGR.mCar[i].no<19)
				  {
					 // For Car
					  dx = mGR.mTex_Car[mGR.mCar[i].no].width() *.24f;
					  dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*.699f;  
				  }
				 else if(mGR.mCar[i].no==21)
				  {
					 // For Fire Brigade
				    dx = mGR.mTex_Car[mGR.mCar[i].no].width()*.21f;
				    dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*1.209f;
				  }
				 else
				  {
					 // For Tanker
				    dx = mGR.mTex_Car[mGR.mCar[i].no].width()*.23f;
				    dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*1.389f; 
				  }
				  float rad = (float)Math.toRadians(mGR.mCar[i].ang);
			      px = (float)(dx*Math.cos(rad));
				  py = (float)(dy*Math.sin(rad));
				  if(Counter%2==0)
		    	   setBoost(i,mGR.mCar[i].x-px,mGR.mCar[i].y+py,mGR.mCar[i].vx,mGR.mCar[i].vy,mGR.mCar[i].ang);
		    }
		}
	    if(mGR.mCar[i].x !=100f && mGR.mCar[i].y!=100f)
	    {
    	    if(mGR.mCar[i].x>1.4f || mGR.mCar[i].x<-1.4f || mGR.mCar[i].y>1.4f || mGR.mCar[i].y<-1.4f)
			{
		    	 M.CoinSound(GameRenderer.mContext,R.raw.coin);
		    	 mGR.mCar[i].resetCar(100,100);
		    	 ResetBoost(i,-100,-100);
		    	 if(mGR.mCar[i].no<19)
		    	 {
		    	   mGR.mTotalCoin++;
		    	   mGR.mLevelCoin++;
		    	 }
		    	 else
		    	 {
		    		mGR.mTotalCoin+=2;
		    	    mGR.mLevelCoin+=2;
		    	    mGR.mCoinAni.setAchieve(0,.99f,50);
		    	 }
		    	 
		    	 mGR.mNoCrossCar++;
//		    	 System.out.println("In Resettttttttttttt"+mGR.mCar[i].vx);
		    }
	    }
	   
	}
	if(mGR.mSlowCar.isActive && mGR.mSlowCar.mActivetime>0)
	{
		if(Counter%10==0)
		  mGR.mSlowCar.mActivetime--;
		for(int i=0;i<mGR.mCar.length;i++)
		{
		  if(mGR.mCar[i].x>-1 && mGR.mCar[i].x<1 && mGR.mCar[i].y>-1 && mGR.mCar[i].y<1)
		    mGR.mCar[i].Speed =.65f;
		}
		if(mGR.mSlowCar.mActivetime<=0)
		{
			mGR.mSlowCar.mActivetime=0;
			mGR.mSlowCar.isActive =false;
			for(int i=0;i<mGR.mCar.length;i++)
			{
			  if(mGR.mCar[i].x>-1 && mGR.mCar[i].x<1 && mGR.mCar[i].y>-1 && mGR.mCar[i].y<1)
			    mGR.mCar[i].Speed =1;
			}
		}
	}
	if(mGR.mFastCar.isActive && mGR.mFastCar.mActivetime>0)
	{
		if(Counter%10==0)
		  mGR.mFastCar.mActivetime--;
		for(int i=0;i<mGR.mCar.length;i++)
		{
		  if(mGR.mCar[i].x>-1 && mGR.mCar[i].x<1 && mGR.mCar[i].y>-1 && mGR.mCar[i].y<1)
		    mGR.mCar[i].Speed =1.3f;
		}
		if(mGR.mFastCar.mActivetime<=0)
		{
		   mGR.mFastCar.mActivetime=0;
		   mGR.mFastCar.isActive =false;	
		   for(int i=0;i<mGR.mCar.length;i++)
			{
			  if(mGR.mCar[i].x>-1 && mGR.mCar[i].x<1 && mGR.mCar[i].y>-1 && mGR.mCar[i].y<1)
			    mGR.mCar[i].Speed =1;
			}
		}
	}
	if(mGR.mBridge.mActivetime>0 && mGR.mBridge.x>-1 && mGR.mBridge.x<1)
	{
	    checkBridgeCollision();
	   if(Counter%10==0)
		  mGR.mBridge.mActivetime--;
	   if(mGR.mBridge.mActivetime<=0)
	   {
		   mGR.mBridge.SetPos(10,10,0);
		   mGR.mBridge.mActivetime=0;
		   mGR.mBridge.isActive=false;
		   for(int i=0;i<mGR.mCar.length;i++)
		   {
			  if(mGR.mCar[i].isBridge)
				 mGR.mCar[i].isBridge =false;
		   }
	   }
	}
	if(mGR.mTunnel.mActivetime>0 && mGR.mTunnel.x>-1 && mGR.mTunnel.x<1)
	{
	   checkTunnelCollision();
	   if(Counter%10==0)
	    mGR.mTunnel.mActivetime--;
	   if(mGR.mTunnel.mActivetime<=0)
	   {
		   mGR.mTunnel.SetPos(10,10,0);
		   mGR.mTunnel.mActivetime=0;
		   mGR.mTunnel.isActive=false;
		   for(int i=0;i<mGR.mCar.length;i++)
		   {
			  if(mGR.mCar[i].isTunnel)
				mGR.mCar[i].isTunnel =false;
		   }
	   }
	} 
	if(mGR.mStopCar.isActive && mGR.mStopCar.mActivetime>0)
	{
		StopCar(0);
		if(Counter%10==0)
 		 mGR.mStopCar.mActivetime--;
		if(mGR.mStopCar.mActivetime<=0)
		{
			mGR.mStopCar.mActivetime=0;
			mGR.mStopCar.isActive =false;
			StopCar(1);
		}
		
	}
	for(int i=0;i<mGR.mCar.length;i++)
	{
		for(int j=0;j<mGR.mCar.length;j++)
		{
  		   boolean isAcc =false; 
		   if((!mGR.mCar[i].isBridge && !mGR.mCar[j].isBridge) && (!mGR.mCar[i].isTunnel && !mGR.mCar[j].isTunnel))
			 if((mGR.mCar[i].x!=100 && mGR.mCar[j].x!=100))
			 {
				float rectDx,rectDy;
				rectDx = mGR.mTex_Car[mGR.mCar[i].no].width()*.8f;
				rectDy = mGR.mTex_Car[mGR.mCar[i].no].width()*.7f;
			    if(i!=j && Rext2RectColli(mGR.mCar[i].x-rectDx*.3f,mGR.mCar[i].y+rectDy*.3f,rectDx,rectDy,mGR.mCar[j].x-rectDx*.3f,mGR.mCar[j].y+rectDy*.3f,rectDx,rectDy))
			    {
			    	
			       if(mGR.mCar[i].no <19 && mGR.mCar[j].no<19)
			       {
			    	  float w =.059f,h = .069f,w1 =.059f,h1 = .069f;
			    	  mGR.CarCordI = CalCarPoints(mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,mGR.mCar[i].no); 
			    	  mGR.CarCordJ = CalCarPoints(mGR.mCar[j].x,mGR.mCar[j].y,mGR.mCar[j].ang,mGR.mCar[j].no);
			    	  
			    	  if(mGR.mCar[i].no>16)
			    	  {
			    		  w = .03f;
			    		  h = .03f;
			    	  }
			    	  if(mGR.mCar[j].no>16)
			    	  {
			    		  w1 = .03f;
			    		  h1 = .03f;
			    	  }
			    	  for(int ii=0;ii<mGR.CarCordI[0].length;ii++) //For Car-Car
					   {
					     for(int jj=0;jj<mGR.CarCordJ[0].length;jj++)
						  {
						    if(Rext2RectColli(mGR.CarCordI[0][ii]-w*.9f,mGR.CarCordI[1][ii]+h*.9f,w*2f,h*2f,mGR.CarCordJ[0][jj]-w1*.9f,mGR.CarCordJ[1][jj]+h1*.9f,w1*2f,h1*2f))
							  {
//								  System.out.println("In Car------carr"+"w========== "+mGR.CarCordI[0][ii]+"h==========="+mGR.CarCordI[1][ii]+"w1============= "+mGR.CarCordJ[0][ii]+"h1============ "+mGR.CarCordI[1][ii]);
								  isAcc =true;
							  }
						  }
					  }
			       }
			       else if(mGR.mCar[i].no >18 && mGR.mCar[j].no>18)
			       {
			    	   float w=0,h=0,w1=0,h1=0;
			    	   mGR.BusCordI = CalBusPoints(mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,mGR.mCar[i].no);
			    	   mGR.BusCordJ = CalBusPoints(mGR.mCar[j].x,mGR.mCar[j].y,mGR.mCar[j].ang,mGR.mCar[j].no);
		    		  if(mGR.mCar[i].no<21)
		    		  {
			    	      w=.02f;h=.109f;//For School Bus
		    		  }
		    		  else if(mGR.mCar[i].no == 21)
		    		  {
		    			  w=.03f;h=.079f;//For Fire Brigade
		    		  }
			    	  else if(mGR.mCar[i].no > 21)
			    	  {
			    	     w=.05f ;h=.05f;//For Tanker
			    	  }
		    		  if(mGR.mCar[j].no<21)
		    		  {
			    	      w1=.02f;h1=.109f;//For School Bus
		    		  }
		    		  else if(mGR.mCar[j].no == 21)
		    		  {
		    			  w1=.03f;h1=.079f;//For Fire Brigade
		    		  }
		    		  else if(mGR.mCar[j].no > 21)
			    	  {
			    	      w1=.05f ;h1=.05f;//For Tanker
			    	  }
			    	  for(int ii=0;ii<mGR.BusCordI[0].length;ii++) //For Bus- Bus
					   {
					     for(int jj=0;jj<mGR.BusCordJ[0].length;jj++)
						  {
						    if(Rext2RectColli(mGR.BusCordI[0][ii]-w*.9f,mGR.BusCordI[1][ii]+h*.9f,2f*w,2f*h,mGR.BusCordJ[0][jj]-w1*.9f,mGR.BusCordJ[1][jj]+h1*.9f,2f*w1,2f*h1))
							  {
//						    	System.out.println("In Truck------Truck"+"w========== "+mGR.BusCordI[0][ii]+"h==========="+mGR.BusCordI[1][ii]+"w1============= "+mGR.BusCordJ[0][ii]+"h1============ "+mGR.BusCordJ[1][ii]);
						    	 isAcc =true;
							  }
						  }
					  }
			       }
			       else
			       {
			    	   float w=.059f,h=.069f,w1=0,h1=0;
			    	    
			    	   if(mGR.mCar[i].no<19 && mGR.mCar[j].no>18)
			    	   {
			    		   mGR.CarCordI = CalCarPoints(mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,mGR.mCar[i].no);
			    		   mGR.BusCordI = CalBusPoints(mGR.mCar[j].x,mGR.mCar[j].y,mGR.mCar[j].ang,mGR.mCar[j].no);
			    		   if(mGR.mCar[i].no>16)
					    	 {
			    			    w = .03f;
			    			    h = .03f;
					    	 }
			    		    if(mGR.mCar[j].no<21)
				    		 {
					    	    w1=.02f;h1=.109f;//For School Bus
				    		 }
			    		  	else if(mGR.mCar[j].no == 21)
				    		 {
				    		    w1=.03f;h1=.079f;//For Fire Brigade
				    		 }
				    		 else if(mGR.mCar[j].no > 21)
					    	 {
					    	    w1=.05f ;h1=.05f;//For Tanker
					    	 }
//				    	   System.out.println("In iiiiiiiiiiiiiiii");
			    	   }
			    	   else
			    	   {
			    		   mGR.CarCordI = CalCarPoints(mGR.mCar[j].x,mGR.mCar[j].y,mGR.mCar[j].ang,mGR.mCar[j].no);
			    		   mGR.BusCordI = CalBusPoints(mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,mGR.mCar[i].no);
			    		   if(mGR.mCar[j].no>16)
					    	{
			    			    w = .03f;
			    			    h = .03f;
					    	}
			    		    if(mGR.mCar[i].no<21)
				    		 {
					    	    w1=.02f;h1=.109f;//For School Bus
				    		 }
				    	    else if(mGR.mCar[i].no == 21)
				    		 {
				    		    w1=.03f;h1=.079f;//For Fire Brigade
				    		 }
				    		 else if(mGR.mCar[i].no > 21)
					    	 {
					    	    w1=.05f ;h1=.05f;//For Tanker
					    	 }
//			    		   System.out.println("In jjjjjjjjjjjj");
			    	   }
			    	   for(int ii=0;ii<mGR.CarCordI[0].length;ii++) //For Bussss -Car
					   {
					     for(int jj=0;jj<mGR.BusCordI[0].length;jj++)
						  {
						    if(Rext2RectColli(mGR.CarCordI[0][ii]-w*.9f,mGR.CarCordI[1][ii]+h*.9f,2f*w,2f*h,mGR.BusCordI[0][jj]-w1*.9f,mGR.BusCordI[1][jj]+h1*.9f,2f*w1,2f*h))
							  {
//						    	System.out.println("In Busss------Carrrrrrrrrrr"+"w========= "+w+"h=============="+h+"w1 ==========="+w1+"h1=========== "+h1);
//						    	System.out.println("In Car------carr"+"w========== "+mGR.CarCordI[0][ii]+"h==========="+mGR.CarCordI[1][ii]+"w1============= "+mGR.BusCordI[0][ii]+"h1============ "+mGR.BusCordI[1][ii]);
						    	isAcc =true;
							  }
						  }
					  }
			       }
			    }
			 }
			 if(isAcc)
			 {
				    if(mGR.mOverCnt==0)
				       M.StopSound();
				    if(!mGR.mCar[i].isCollide)
				    {
			    	  mGR.mCar[i].isCollide =true;
			    	  mGR.mCarBreak[i].mAni=0;
			    	  mGR.mCarBreak[i].set(mGR.mCar[i].x,mGR.mCar[i].y,0,0,mGR.mCar[i].ang);
				      mGR.mCar[i].resetCar(mGR.mCar[i].x,mGR.mCar[i].y);
				      mGR.mCarBreak[i].ang += M.randomRange(15f,25f);
				      
//				      System.out.println("X============="+mGR.mCar[i].x+"Y============== "+mGR.mCar[i].y+"i=========== "+i);
				    }
				    if(!mGR.mCar[j].isCollide)
				    {
				      mGR.mCar[j].isCollide =true;
				      mGR.mCarBreak[j].mAni=0;
				      mGR.mCarBreak[j].set(mGR.mCar[j].x,mGR.mCar[j].y,0,0,mGR.mCar[j].ang);
				      mGR.mCar[j].resetCar(mGR.mCar[j].x,mGR.mCar[j].y);
//				      System.out.println("JX============="+mGR.mCar[j].x+"JY============== "+mGR.mCar[j].y+"i=========== "+j);
				      mGR.mCarBreak[j].ang -= M.randomRange(15f,25f);  
				      
			 		}
					if(!mGR.isCrash)
				     mGR.mOverCnt=0;
				     mGR.isCrash =true;
			 }
		}
	}
	if(mGR.isCrash)
	{
	  mGR.mOverCnt++;
	  if(mGR.mOverCnt>30)
	  {
		    M.BgStop();
		    M.GameScreen = M.GAMEKEYUSE;
		    StageAchieve();
		    ScoreAchieve();
		    mGR.resetPower();
		    mGR.mAdCount++;
		    inc=1;
		 if(!GameRenderer.mStart.addFree && mGR.mAdCount%3==0)
		 {
		     GameRenderer.mStart.ShowInterstitial();
		 }
		  
	  }
	  if(mGR.mOverCnt==1)
	  {
		  M.Crash1Sound(GameRenderer.mContext,R.raw.carcrash0);
		  M.Crash2Sound(GameRenderer.mContext,R.raw.carcrash1);
//		  mGR.noStage       =  (mGR.mStageRem+mGR.mScore)/100;
//		  mGR.mScore        += mGR.mStageRem;
//		  mGR.mStageRem     =  mGR.mScore%100;
		  mGR.noStage       = mGR.mNoCrossCar/100; 
		  mGR.mCntScore  	= 0;
		  mGR.mStageFill 	= 0;
		  mGR.mStageCounter = 0;
		  if(mGR.noStage>mGR.mBestStage)
		  {
			 mGR.mBestStage    = mGR.noStage;
			 mGR.mStageCounter = 33;
		  }
		  else
		  {
			  
			  float diff;
			  if(mGR.mScore>0 /*&& mGR.noStage>0*/)
			  {
				 float stage = ((mGR.mBestStage+1)*100);
				 float scr   = mGR.mNoCrossCar;
			     float div   = stage/scr;
			     diff        = (33f/div);
			     diff        %=33;
			  }
			  else
			  {
				  diff=0;
			  }
			  mGR.mStageCounter = (int)diff;
			  
		  }
	  }
	}
	else
	{
		if(Counter%33==0)
		{
		   mGR.mGameSec++;
		   if(mGR.mGameSec>60)
		      mGR.mGameSec=1;
		   
	       mGR.mGameMin  +=(mGR.mGameSec/60);
		   mGR.mChallengeSec++;
		   mGR.mTotalSec++;
		   if(mGR.mTotalSec>60)
		      mGR.mTotalSec=1;
		   mGR.mTotalMin +=mGR.mTotalSec/60;
		   
		   int Scr     =  mGR.mLevelCoin*mGR.mGameSec;
		   mGR.mScore  = ((Scr)*((mGR.mWorldNo +1)*mGR.mGameMin))/2;
		}
//	    System.out.println("mGR.mTotalMin==========  "+mGR.mTotalMin+"  "+"TotalSec====== "+mGR.mTotalSec+"   "+"Min========= "+mGR.mGameMin+"  "+"sec===  "+mGR.mGameSec+"      "+mGR.mScore);
	    if(mGR.mScore>mGR.mBestScore)
	    {
	      mGR.mBestScore = mGR.mScore;
	      
	    }
	}
	CarAchieve();
	CheckChallenge();
}
int sec[]      = {30,60,120,60*5,60*10,60*30,60*45};
int totalMin[] = {10,30,60,90,60*2,60*5,10*60};
int coinCha[]  = {100,500,1000,5000,10000,25000,50000,75000,100000,500000};
void CheckChallenge()
{
	 for(int i=0;i<sec.length;i++)
	 {
		 if(mGR.mChallengeSec >=sec[i])
		 {
			 if(!mGR.isChallenge[i])
			 {
			    mGR.isChallenge[i]=true;
			    mGR.mTotalCoin+=M.ChallengeCoin[i];
			    mGR.mChallenge.setAchieve(0,.99f,70);
//			    System.out.println("In Second challenge");
			 }
		 }
	 }
	 
	 for(int i=0;i<totalMin.length;i++)
	 {
		 int k=7+i;
		 if(mGR.mTotalMin>=totalMin[i])
		 {
			 if(!mGR.isChallenge[k])
			 {
			    mGR.isChallenge[k]=true;
			    mGR.mTotalCoin+=M.ChallengeCoin[k];
			    mGR.mChallenge.setAchieve(0,.99f,70);
//			    System.out.println("In Minute  challenge");
			 }
		 }
	 }
	 for(int i=0;i<coinCha.length;i++)
	 {
		 int k=14+i;
		 if(mGR.mTotalCoin>=coinCha[i])
		 {
			 if(!mGR.isChallenge[k])
			 {
			    mGR.isChallenge[k]=true;
			    mGR.mTotalCoin+=M.ChallengeCoin[k];
			    mGR.mChallenge.setAchieve(0,.99f,70);
//			    System.out.println("In Coinnnnn  challenge");
			 }
		 }
	 }
}
void checkBridgeCollision()
{
	float rad,rectDx1,rectDy1;
	 
	for(int i=0;i<mGR.mCar.length;i++)
	{
	  if(mGR.mCar[i].x>-1f && mGR.mCar[i].x<1)
	  {
		if(mGR.mCar[i].ang == mGR.mBridge.ang)
		{
			mGR.mCar[i].isBridge = false;
			if(mGR.mBridge.ang == 0f || mGR.mBridge.ang == 180f)
			{
			  rad     = mGR.mTex_Bridge[mGR.mBridge.no].Height()*.3f;
			  rectDx1 = mGR.mTex_Car[mGR.mCar[i].no].width() *.5f;	
			  rectDy1 = mGR.mTex_Car[mGR.mCar[i].no].Height()*.5f;
			}
			else
			{
				rad     = mGR.mTex_Bridge[mGR.mBridge.no].width()*.3f;
			    rectDy1 = mGR.mTex_Car[mGR.mCar[i].no].width() *.5f;	
			    rectDx1 = mGR.mTex_Car[mGR.mCar[i].no].Height()*.5f;
			}
			if(CircRectsOverlap(mGR.mCar[i].x,mGR.mCar[i].y,rectDx1,rectDy1,mGR.mBridge.x,mGR.mBridge.y,rad))
			{
				mGR.mCar[i].isBridge = true;
			}
		}
	  }
	}
}
void checkTunnelCollision()
{
	float rad,rectDx1,rectDy1;
	for(int i=0;i<mGR.mCar.length;i++)
	{
		trans =1;
		if(mGR.mCar[i].x>-1f && mGR.mCar[i].x<1)
		{
			mGR.mCar[i].isTunnel = false;
			if(mGR.mCar[i].ang == mGR.mTunnel.ang)
			{
				
				if(mGR.mTunnel.ang ==0f || mGR.mTunnel.ang ==180f)
				{
				  rad = mGR.mTex_Tunnel.Height()*.3f;
				  
				  rectDx1 = mGR.mTex_Car[mGR.mCar[i].no].width() *.5f;	
				  rectDy1 = mGR.mTex_Car[mGR.mCar[i].no].Height()*.5f;
				}
				else
				{
					rad = mGR.mTex_Tunnel.width()*.3f;
				    rectDy1 = mGR.mTex_Car[mGR.mCar[i].no].width() *.5f;	
				    rectDx1 = mGR.mTex_Car[mGR.mCar[i].no].Height()*.5f;
				}
				if(CircRectsOverlap(mGR.mCar[i].x,mGR.mCar[i].y,rectDx1,rectDy1,mGR.mTunnel.x,mGR.mTunnel.y,rad))
				{
					mGR.mCar[i].isTunnel = true;
					trans =.5f;
				}
		    }
		}
	}
}
void DrawBoost(int id,GL10 gl)
{
   for(int j=0;j<mGR.mAni[id].length;j++)
   	{
		if(mGR.mAni[id][j].x>-1.2&&mGR.mAni[id][j].x<1.2&&mGR.mAni[id][j].y>-1.2&&mGR.mAni[id][j].y<1.2)
		{
			DrawTextureRST(gl,mGR.mTex_Fire,mGR.mAni[id][j].x, mGR.mAni[id][j].y,mGR.mAni[id][j].z,mGR.mAni[id][j].t,0);
			mGR.mAni[id][j].UpdateBoost();
		}
	}
}
void DrawGameplay(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_WorldBG[mGR.mWorldNo %mGR.mTex_WorldBG.length],0,0);
	for(int i=0;i<mGR.mCar.length;i++)
	{
	  if((mGR.mBridge.x>1 && mGR.mBridge.y>1) &&(mGR.mTunnel.x>1 && mGR.mTunnel.y>1))
	  {
        if(!mGR.mCar[i].isCollide && mGR.mCar[i].x>-1.5f && mGR.mCar[i].x<1.5f && mGR.mCar[i].y>-1.5f && mGR.mCar[i].y<1.5f)
        {
    	   DrawBoost(i,gl);
    	  if(!mGR.mCar[i].isBridge || mGR.mCar[i].isTunnel)
	        DrawTextureRST(gl,mGR.mTex_Car[mGR.mCar[i].no],mGR.mCar[i].x,mGR.mCar[i].y,1,mGR.mCar[i].isTunnel ?.5f:1f,mGR.mCar[i].ang);
        }
	  }
	}
	if(mGR.mTunnel.mActivetime>0 && mGR.mTunnel.x>-1f && mGR.mTunnel.x<1f)
	{
		for(int i=0;i<mGR.mCar.length;i++)
		{
			DrawBoost(i,gl);
		    if(mGR.mCar[i].isTunnel && !mGR.mCar[i].isCollide && mGR.mCar[i].x>-1.5f && mGR.mCar[i].x<1.5f && mGR.mCar[i].y>-1.5f && mGR.mCar[i].y<1.5f)
	           DrawTextureRST(gl,mGR.mTex_Car[mGR.mCar[i].no],mGR.mCar[i].x,mGR.mCar[i].y,1,mGR.mCar[i].isTunnel ?.5f:1f,mGR.mCar[i].ang);
		}	
	    DrawTextureRST(gl,mGR.mTex_Tunnel,mGR.mTunnel.x,mGR.mTunnel.y,1,trans,mGR.mTunnel.ang);
	    for(int i=0;i<mGR.mCar.length;i++)
		{
		   if(!mGR.mCar[i].isTunnel && !mGR.mCar[i].isCollide && mGR.mCar[i].x>-1.5f && mGR.mCar[i].x<1.5f && mGR.mCar[i].y>-1.5f && mGR.mCar[i].y<1.5f)
			   DrawTextureR(gl,mGR.mTex_Car[mGR.mCar[i].no],mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,0,0);
		}
	}
	if(mGR.mBridge.mActivetime>0 && mGR.mBridge.x>-1f && mGR.mBridge.x<1f)
	{
		for(int i=0;i<mGR.mCar.length;i++)
		{
			DrawBoost(i,gl);
		    if(!mGR.mCar[i].isBridge && !mGR.mCar[i].isCollide && mGR.mCar[i].x>-1.5f && mGR.mCar[i].x<1.5f && mGR.mCar[i].y>-1.5f && mGR.mCar[i].y<1.5f)
		    	DrawTextureR(gl,mGR.mTex_Car[mGR.mCar[i].no],mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,0,0);
		}
	   DrawTextureR(gl,mGR.mTex_Bridge[mGR.mBridge.no],mGR.mBridge.x,mGR.mBridge.y,mGR.mBridge.ang,0,0);
	   for(int i=0;i<mGR.mCar.length;i++)
		{
		   if(mGR.mCar[i].isBridge && !mGR.mCar[i].isCollide && mGR.mCar[i].x>-1.5f && mGR.mCar[i].x<1.5f && mGR.mCar[i].y>-1.5f && mGR.mCar[i].y<1.5f)
	           DrawTextureR(gl,mGR.mTex_Car[mGR.mCar[i].no],mGR.mCar[i].x,mGR.mCar[i].y,mGR.mCar[i].ang,0,0);
		}
	}
	if(mGR.mSlowCar.isActive)
	{
		if(Counter%2==1)
	  	  Counter2++;
       float ani= .5f-/*Math.abs*/((float)Math.cos(Math.toRadians(Counter2*40%360))*.5f);
	   DrawTransScal(gl,mGR.mTex_SlowEffect,0,0,1,ani);
	}
	if(mGR.mFastCar.isActive)
	{
		if(Counter%2==1)
	  	  Counter2++;
	     float ani= .5f-/*Math.abs*/((float)Math.cos(Math.toRadians(Counter2*40%360))*.5f);
		 DrawTransScal(gl,mGR.mTex_FastEffect,0,0,1,ani);
	}
	
	if(mGR.mStopCar.isActive)
		DrawTexture(gl,mGR.mTex_GamePower[4],0,0);
	
	for(int i=0;i<mGR.mCarBreak.length;i++)
	{
	   if(mGR.mCarBreak[i].x>-1.2f && mGR.mCarBreak[i].x<1.2f && mGR.mCarBreak[i].y>-1.2f && mGR.mCarBreak[i].y<1.2f)
	   {
		  DrawTextureS(gl,mGR.mTex_Smoke[mGR.mCarBreak[i].mAni%mGR.mTex_Smoke.length],mGR.mCarBreak[i].x,mGR.mCarBreak[i].y+.3f,2.2f);
	      DrawTextureR(gl,mGR.mTex_CarBreak[mGR.mCar[i].no],mGR.mCarBreak[i].x,mGR.mCarBreak[i].y,mGR.mCarBreak[i].ang,0,0);
	      if(Counter%3==0)
	        mGR.mCarBreak[i].mAni++;
	      mGR.mCarBreak[i].mAni %= mGR.mTex_Smoke.length;
	   }
	}
	if(mGR.mTapEffect.x>-1.2f && mGR.mTapEffect.x<1.2f)
	{
	   DrawTransScal(gl,mGR.mTex_TapEffect,mGR.mTapEffect.x,mGR.mTapEffect.y,mGR.mTapEffect.z,mGR.mTapEffect.z);
	   mGR.mTapEffect.UpdateTap();
	}
	if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	{
	  DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
	  mGR.mAchieveAni.updateAchieve();
	}
	if(mGR.mChallenge.x>-1.2f && mGR.mChallenge.x<1.2f)
	{
	  DrawTextureS(gl,mGR.mTex_ChallengeComp,mGR.mChallenge.x,mGR.mChallenge.y+.3f,mGR.mChallenge.z);
	  mGR.mChallenge.updateAchieve();
	}
	if(mGR.mScoreAni.x>-1.2f && mGR.mScoreAni.x<1.2f)
	{
	  mGR.mFont.Draw_StringS(gl,"100+",mGR.mScoreAni.x,mGR.mScoreAni.y+.2f,1,mGR.mScoreAni.z);
	  mGR.mScoreAni.updateAchieve();
	}
	if(mGR.mCoinAni.x>-1.2f && mGR.mCoinAni.x<1.2f)
	{
	  mGR.mFont.Draw_StringS(gl,"2+",mGR.mCoinAni.x,mGR.mCoinAni.y+.1f,1,mGR.mCoinAni.z);
	  mGR.mCoinAni.updateAchieve();
	}
	
	DrawTexture(gl,mGR.mTex_CoinIcn,.909f,.849f);
	DrawTexture(gl,mGR.mTex_BigBox,.679f,.849f);
	mGR.mFont.Draw_String(gl,mGR.mLevelCoin+"",.6f,.849f,0);
	if(M.GameScreen == M.GAMEPLAY)
	{
       Gamelogic();
//       DrawTransScal(gl,mGR.mTex_GamePower[0],.699f ,-.859f,mGR.mSel==1|| mGR.mSlowCar.isActive?1.2f:1f,mGR.mSel==1||!mGR.mSlowCar.isActive?.5f:1f);
//       DrawTransScal(gl,mGR.mTex_GamePower[1],.879f ,-.859f,mGR.mSel==2|| mGR.mFastCar.isActive?1.2f:1f,mGR.mSel==2||!mGR.mFastCar.isActive?.5f:1f);
       DrawTransScal(gl,mGR.mTex_PauseBtn    ,-.919f ,-.869f,mGR.mSel==3?1.1f:.9f,mGR.mSel==3?.5f:1f);
	}
	
	
//	if(Math.abs(mGR.mPopVX)>0)
//	{
//	  mGR.mPopX+=mGR.mPopVX;
//	  if(mGR.mPopVX>0)
//	  {
//		  if(mGR.mPopX>-.859f)
//		  {
//		    mGR.mPopX   = -.859f;
//		    mGR.mPopVX  = 0;
//		    mGR.mPopAng =180;
//		  }
//	  }
//	  if(mGR.mPopVX<0)
//	  {
//		  if(mGR.mPopX<-1.02f)
//		  {
//		    mGR.mPopX   = -1.02f;
//		    mGR.mPopVX  = 0;
//		    mGR.mPopAng = 0;
//		  }
//	  }
//	}
	DrawTexture(gl,mGR.mTex_GamePopup,-1.02f,0);
	DrawTransScal(gl, mGR.mTex_GameArrow,-1.02f+.069f,0,mGR.mSel>=10?1.2f:1f,mGR.mSel>=10?.5f:1);
//	if(M.GameScreen == M.GAMEUSEPOWER)
//	{
//	     DrawTransScal(gl,mGR.mTex_GamePower[2],mGR.mPopX-.079f,.369f ,mGR.mSel==1?.8f:.7f,mGR.mSel==1?.5f:1);
//	     DrawTransScal(gl,mGR.mTex_GamePower[3],mGR.mPopX-.079f,0     ,mGR.mSel==2?.8f:.7f,mGR.mSel==2 ?.5f:1);
//		 DrawTransScal(gl,mGR.mTex_GamePower[4],mGR.mPopX-.079f,-.369f,mGR.mSel==3?.8f:.7f,mGR.mSel==3?.5f:1);
//		DrawUsePower(gl);
//	}

	if(M.GameScreen==M.GAMEKEYUSE)
	{
		DrawTexture(gl,mGR.mTex_TransBg,0,0);
		DrawTexture(gl,mGR.mTex_KeyIcn,-.03f,.1185f);
		DrawTexture(gl,mGR.mTex_SmallBox,-.18f,.1185f);
		mGR.mFont.Draw_String(gl,mGR.mNoKey+"",-.19f,.119f,0); 
		DrawTransScal(gl,mGR.mTex_UseBtn,-.1f,-.145f,mGR.mSel==1?1.1f:.9f,mGR.mSel==1?.5f:1f);
		DrawTexture(gl,mGR.mTex_KeyAnimation,.2f,0);
//		DrawTransScal(gl,mGR.mTex_BackBtn   ,.729f,-.779f ,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
		int h2 =(int)(mGR.mTex_FillCir.Height()*M.ScreenHieght*.3f);
		gl.glEnable(GL10.GL_SCISSOR_TEST);
		if(mGR.mKeyAniCnt>h2)
		{
		   mGR.mKeyAniCnt=0;	
		   M.GameScreen = M.GAMEOVER;
		}
		gl.glScissor(XPos(-.109f),YPos(.16f),XPos(-.29f),mGR.mKeyAniCnt);
		DrawTexture(gl,mGR.mTex_FillCir,.2f,-.01f);
		mGR.mKeyAniCnt+=inc;
		
	}

}
boolean HandleGame(MotionEvent event)
{
	mGR.mSel=0;
	if(mGR.mOverCnt!=0)
		return true;
//	if(CircRectsOverlap(.699f,-.859f,mGR.mTex_GamePower[0].width()*.5f,mGR.mTex_GamePower[0].Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	{
//		mGR.mSel=1; //Slow Car
//	}
//	if(CircRectsOverlap(.879f,-.859f,mGR.mTex_GamePower[0].width()*.5f,mGR.mTex_GamePower[0].Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	{
//		mGR.mSel=2; //Fast Car
//	}
	if(CircRectsOverlap(-.919f ,-.869f,mGR.mTex_GamePower[0].width()*.5f,mGR.mTex_GamePower[0].Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=3; //Pause
	}
	if(CircRectsOverlap(-1.02f+.069f,0,mGR.mTex_GameArrow.width()*.4f, mGR.mTex_GameArrow.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=10;//Arrow
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  if(mGR.mSlowCar.mActivetime>0 && !mGR.mSlowCar.isActive && !mGR.mFastCar.isActive)
			  {
				 mGR.mSlowCar.isActive = true;
			  }
			 break;
		  case 2:
			  if(mGR.mFastCar.mActivetime>0 && !mGR.mFastCar.isActive && !mGR.mSlowCar.isActive)
			  {
				 mGR.mFastCar.isActive = true;
			  }
			break;
		  case 3:
			  M.GameScreen = M.GAMEPAUSE;
			  M.StopSound();
			  break;
		  
		  case 10:
			   M.GameScreen =M.GAMEUSEPOWER;
			   M.StopSound();
//			   mGR.mPopVX=.04f;
			  break;
		
		}
		mGR.mSel=0;
	}
	if(mGR.mSel!=0)
		return true;
	
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		float rdx,rdy;
		for(int i=0;i<mGR.mCar.length;i++)
		{
			if(mGR.mCar[i].ang ==0|| mGR.mCar[i].ang ==180)
			{
				rdx = mGR.mTex_Car[mGR.mCar[i].no].width() *.4f;	
			    rdy = mGR.mTex_Car[mGR.mCar[i].no].Height()*.4f;	
			}
			else
			{
				rdy = mGR.mTex_Car[mGR.mCar[i].no].width() *.4f;	
			    rdx = mGR.mTex_Car[mGR.mCar[i].no].Height()*.4f;
			}
			if(CircRectsOverlap(mGR.mCar[i].x,mGR.mCar[i].y,rdx,rdy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			{
				mGR.mCar[i].mTap++;
				switch(mGR.mCar[i].mTap)
			 	{
				  case 1:
					  if(mGR.mCar[i].no!=15)
						{
						  if(M.mRand.nextBoolean())
				   	        M.CarPaas1Sound(GameRenderer.mContext,R.raw.car_pass0);
						  else
						    M.CarPaas2Sound(GameRenderer.mContext,R.raw.car_pass1);
					 	}
					   else
						    M.PoliceSound(GameRenderer.mContext,R.raw.police_car);
					  
					  mGR.mCar[i].Speed=2f;	
					  mGR.mTapEffect.SetTap(mGR.mCar[i].x,mGR.mCar[i].y);
					  if(mGR.mCar[i].no>18)
					  {
						mGR.mScore+=100;  
					    mGR.mScoreAni.setAchieve(0,.99f,50);
					    ResetBoost(i,-100,-100);
					  }
					break;
			 	  case 2:
			 		  mGR.mCar[i].Speed=0;	
			 		  mGR.mTapEffect.SetTap(mGR.mCar[i].x,mGR.mCar[i].y);
					break; 
				  case 3:
					  mGR.mCar[i].Speed=1f;
					  mGR.mCar[i].mTap =0;
					  mGR.mTapEffect.SetTap(mGR.mCar[i].x,mGR.mCar[i].y);
					break;
				}
				break;
		     }
			
         }
	}
	return true; 
	
}
boolean HandleKeyUse(MotionEvent event)
{
	 mGR.mSel=0;
	 if(CircRectsOverlap(-.1f,-.145f,mGR.mTex_UseBtn.width()*.4f, mGR.mTex_UseBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		 mGR.mSel=1;
	 }
//	 if(CircRectsOverlap(.729f,-.779f,mGR.mTex_BackBtn.width()*.5f,mGR.mTex_BackBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	 {
//		mGR.mSel=8; //Back
//	 }
	 if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch (mGR.mSel)
		 {
		   case 1:
			 if(mGR.mNoKey>0)
				StartGame();
			 else
			 {
				 GameRenderer.mStart.NoKey();
			 }
			 break;
//		   case 8:
//			   M.GameScreen = M.GAMEOVER;
//			   break;
		 }
		 mGR.mSel=0;
	 }
	return true;
}

void StartGame()
{
	for(int i=0;i<mGR.mCar.length;i++)
	{
	   mGR.mCar[i].resetCar(100,100);
	   mGR.mCarBreak[i].resetCar(100,100);
	   ResetBoost(i,100,100);
	}
	mGR.mCarIndex=0;
	mGR.mNoKey--;
	mGR.mOverCnt=0;
	mGR.isCrash=false;
   if(M.SetBG)
	  M.BGPlay(GameRenderer.mContext,R.raw.bg);
   M.GameScreen = M.GAMEPLAY;
}
void DrawUsePower(GL10 gl)
{
   DrawShop2(gl);	
}
int     activetime[] = new int[5];
boolean isactive[]   = new boolean[5];
void getActivetime()
{
	activetime[0] = mGR.mSlowCar.mActivetime;
	activetime[1] = mGR.mFastCar.mActivetime;
	activetime[2] = mGR.mBridge.mActivetime;
	activetime[3] = mGR.mTunnel.mActivetime;
	activetime[4] = mGR.mStopCar.mActivetime;
}
void getisActive()
{
	isactive[0] = mGR.mSlowCar.isActive;
	isactive[1] = mGR.mFastCar.isActive;
	isactive[2] = mGR.mBridge .isActive;
	isactive[3] = mGR.mTunnel .isActive;
	isactive[4] = mGR.mStopCar.isActive;
}
void DrawShop2(GL10 gl)
{
	 float x=-.75f,dx =.379f;
	 DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	 DrawTexture(gl,mGR.mTex_TransBg,0,0);
	 DrawTexture(gl,mGR.mTex_BigPup,0,0);
 	 GetunlockVal();
 	 GetupgrdVal();
 	 CheckunLock();
 	 getisActive();
 	 getActivetime();
 	 for(int i=0;i<mGR.mTex_GamePower.length;i++)
 	 {
 		DrawTexture(gl,mGR.mTex_Preview          ,x+i*dx,.18f);
		DrawTexture(gl,mGR.mTex_GamePower[i]  ,x+i*dx,.28f);
 		if(!isUnLock[i])
 		 {
 		   DrawTexture(gl,mGR.mTex_PreviewBox,(x-.1f)+i*dx,-.009f);
 		   mGR.mFont.Draw_String(gl, UnlockVal[i]+"",(x-.08f)+i*dx,-.009f,1);
 		   DrawTexture(gl,mGR.mTex_SmallCoin ,(x+.02f)+i*dx,-.009f);
 		   DrawTexture(gl,mGR.mTex_Lock      ,(x+.1f)+i*dx,.02f);
 		   DrawTransScal(gl,mGR.mTex_UnlockTxt ,x+i*dx,-.169f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
  		 }
 		else
 		{
 		  float ani = 1-Math.abs((float)Math.cos(Math.toRadians(Counter*5))*.3f);
 		  if(!isactive[i] && activetime[i]>0)
 		  { 
 			 mGR.mFont.Draw_StringS(gl,"Touch",(x+.04f)+i*dx,.109f  ,1,mGR.mSel==i+100?1.1f:.89f);
 		     mGR.mFont.Draw_StringS(gl,"For Use",(x+.049f)+i*dx,.01f,1,mGR.mSel==i+100?1.1f:.89f); 
 		  }
 		  DrawTransScal(gl,mGR.mTex_Buy ,x+i*dx,-.164f,mGR.mSel==i+1?1.2f:ani,mGR.mSel==i+1?.5f:ani);
 		  DrawTexture(gl,mGR.mTex_Power     ,x+i*dx,-.29f);
 		  DrawUpgradeBar(gl,i,x+i*dx);
 		  DrawTexture(gl,mGR.mTex_ShopBox   ,(x-.049f)+i*dx,-.419f);
 		  mGR.mFont.Draw_String(gl, UpGrdVal[i]+"",(x-.032f)+i*dx,-.4195f,1);
 		  DrawTexture(gl,mGR.mTex_SmallCoin  ,(x+.04f)+i*dx,-.419f);
 		  
 		}
 		
 	 }
 	 
	 DrawTexture(gl,mGR.mTex_BigBox   ,.559f,.769f);
	 DrawTransScal(gl,mGR.mTex_CoinIcn  ,.789f,.769f,mGR.mSel==11?1.2f:1,mGR.mSel==11?.5f:1);
	 mGR.mFont.Draw_String(gl, mGR.mTotalCoin+"",.47f,.759f,0);
 	 
//	 DrawTransScal(gl,mGR.mTex_LeaderBtn ,-.809f,-.809f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
//	 DrawTransScal(gl,mGR.mTex_AchieveBtn,-.599f,-.809f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
	 DrawTransScal(gl,mGR.mTex_PlayBtn   ,0 ,-.819f,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
	 
	 
	if(mGR.mAchieveAni.x>-1.2f && mGR.mAchieveAni.x<1.2f)
	{
	  DrawTextureS(gl,mGR.mTex_AchieveUnlock,mGR.mAchieveAni.x,mGR.mAchieveAni.y,mGR.mAchieveAni.z);
	  mGR.mAchieveAni.updateAchieve();
	}
   PowerUnlockAchieve();
 	 
}
boolean HandleShop2(MotionEvent event)
{
	 mGR.mSel=0;
 	 HandleUnLock(event);
 	 HandleUpGrde(event);
	if(CircRectsOverlap(-.809f,-.809f, mGR.mTex_CtrlBtn.width()*.4f, mGR.mTex_CtrlBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=6;	//LeaderBoard
	}
	if(CircRectsOverlap(-.599f,-.809f, mGR.mTex_AchieveBtn.width()*.4f, mGR.mTex_AchieveBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=7;	//Achieve
	}
	if(CircRectsOverlap(0,-.809f, mGR.mTex_PlayBtn.width()*.4f, mGR.mTex_PlayBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=8; //PlayBtn
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
			case 6:
				 mGR.isJoin =false;
				break;
			case 7:
				  mGR.isJoin =false;
				break;
			case 8:
				 //Play
					 M.GameScreen = M.GAMEPLAY;
					if(M.SetBG)
					   M.BGPlay(GameRenderer.mContext,R.raw.bg);
					else
					  M.StopSound();
				break;
		}
		if(mGR.isJoin)
		  mGR.mJoinAnim=10;
		mGR.mSel=0;
	}
	return true;
}
boolean HandleUsePower(MotionEvent event)
{
	mGR.mSel=0;
	float x=-.75f,dx =.379f;
    HandleShop2(event);
	for(int i=0;i<5;i++)
	{
		if(CircRectsOverlap(x+i*dx,.05f,mGR.mTex_PreviewBox.width()*.4, mGR.mTex_PreviewBox.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=i+100;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 100:
			  //Slow Car
			  
			  if(mGR.mFastCar.isActive)
				  GameRenderer.mStart.UseFarOrSlowPower();
			  if(mGR.mSlowCar.mActivetime>0 && !mGR.mSlowCar.isActive && !mGR.mFastCar.isActive)
			  {
				 mGR.mSlowCar.isActive = true;
			  }
			 break;
		  case 101:
			  //Fast Car
			  if(mGR.mSlowCar.isActive)
				  GameRenderer.mStart.UseFarOrSlowPower();
			  if(mGR.mFastCar.mActivetime>0 && !mGR.mFastCar.isActive && !mGR.mSlowCar.isActive)
			  {
				 mGR.mFastCar.isActive = true;
			  }
			break; 
		  case 102:
			 //Bridge Power
			  if(mGR.mWorldNo <5)
			  {
				  GameRenderer.mStart.After5world();
			  }
			  else
		      {
			    if(mGR.mTunnel.x>-1f && mGR.mTunnel.x<1f)
				  {
			    	GameRenderer.mStart.UseOnePower();
				  }
				 else if(mGR.mBridge.mActivetime>0 && !mGR.mBridge.isActive)
				  {
					  mGR.setBridge();
					  mGR.mBridge.isActive =true;
//					  System.out.println("In Bridgeeeee");
				  }
			  }
			  break;
		  case 103:
			  //Tunnel Power
			  if(mGR.mWorldNo <5)
			  {
				  GameRenderer.mStart.After5world();
			  }
			  else
			  {
			    if(mGR.mBridge.x>-1 && mGR.mBridge.x<1f)
			    {
				   GameRenderer.mStart.UseOnePower();
				   
			    }
			    else if(mGR.mTunnel.mActivetime>0 && !mGR.mTunnel.isActive)
			    {
				  mGR.setTunnel();
				  mGR.mTunnel.isActive =true;
//				  System.out.println("In Tuneelll");
			    }
			  }
			  break;
		  case 104:
			  //Stop Power
			  if(mGR.mWorldNo <5)
			  {
				  GameRenderer.mStart.After5world();
			  }
			  else
			  {
			    if(mGR.mStopCar.mActivetime>0 && !mGR.mStopCar.isActive)
			    {
				  mGR.mStopCar.isActive=true; 
			    }
			  }
			  break;
		  
		}
		mGR.mSel=0; 
	}
	return true;
}

int carCross[] ={10,25,50,100,250,500,1000,2500,5000,10000,20000,35000,50000,75000,100000};
void CarAchieve()
{
	//0-14 Achievement
	for(int i=0;i<carCross.length;i++)
	{
		if(carCross[i]== mGR.mNoCrossCar)
		{
			if(!mGR.isAchieve[i])
			{
				mGR.isAchieve[i]=true;
				mGR.mAchieveAni.setAchieve(0,.99f,70);
			}
		}
	}
}
void WorldAchieve()
{
	//15-2Z Achievement
	for(int i=1;i<mGR.mWorld.length;i++)
	{
		if(mGR.mWorld[i].isUnLock)
		{
			int k=14+i;
			if(!mGR.isAchieve[k])
			{
				mGR.isAchieve[k]=true;
				mGR.mAchieveAni.setAchieve(0,.99f,70);
			}
		}
	}
}
void PowerUnlockAchieve()
{
    for(int i=0;i<isUnLock.length;i++)
    {
    	int k=26+i;
    	if(isUnLock[i])
    	{
    		if(!mGR.isAchieve[k])
			{
				mGR.isAchieve[k]=true;
				mGR.mAchieveAni.setAchieve(0,.99f,70);
			}
    	}
    }
	 int k=31;
	 if(mGR.mSlowCar.noBuy>9)
	 {
		if(!mGR.isAchieve[k])
		{
			mGR.isAchieve[k]=true;
			mGR.mAchieveAni.setAchieve(0,.99f,70);
		}
	 }
	  k=32;
	 if(mGR.mFastCar.noBuy>9)
	 {
		if(!mGR.isAchieve[k])
		{
			mGR.isAchieve[k]=true;
			mGR.mAchieveAni.setAchieve(0,.99f,70);
		}
	 }
	  k=33;
	 if(mGR.mBridge.noBuy>9)
	 {
		if(!mGR.isAchieve[k])
		{
			mGR.isAchieve[k]=true;
			mGR.mAchieveAni.setAchieve(0,.99f,70);
		}
	 }
	 k=34;
	 if(mGR.mTunnel.noBuy>9)
	 {
		if(!mGR.isAchieve[k])
		{
			mGR.isAchieve[k]=true;
			mGR.mAchieveAni.setAchieve(0,.99f,70);
		}
	 }
	 k=35;
	 if(mGR.mStopCar.noBuy>9)
	 {
		if(!mGR.isAchieve[k])
		{
			mGR.isAchieve[k]=true;
			mGR.mAchieveAni.setAchieve(0,.99f,70);
		}
	 }
}
int stage[] = {5,10,25,50,100,250,500};
void StageAchieve()
{
	for(int i=0;i<stage.length;i++)
	{
	   int k=36+i;
	   if(mGR.noStage>=stage[i])
	   {
		   if(!mGR.isAchieve[k])
			{
				mGR.isAchieve[k]=true;
				mGR.mAchieveAni.setAchieve(0,.99f,70);
//				System.out.println("In Stage============"+mGR.noStage);
			}
	   }
	}
}
int coin[] = {50000,100000};
void CoinAchieve()
{
	for(int i=0;i<coin.length;i++)
	{
		int k=43+i;
		if(mGR.mKharchCoin>=coin[i])
		   {
			   if(!mGR.isAchieve[k])
				{
					mGR.isAchieve[k]=true;
					mGR.mAchieveAni.setAchieve(0,.99f,70);
//					System.out.println("In Kharch coinn============"+mGR.mKharchCoin);
				}
		   }
	}
}
int Score[] = {10000,25000,50000,100000,500000};
void ScoreAchieve()
{
	for(int i=0;i<Score.length;i++)
	{
	   int k=45+i;
	   if(mGR.mBestScore>=Score[i])
	   {
		   if(!mGR.isAchieve[k])
			{
				mGR.isAchieve[k]=true;
				mGR.mAchieveAni.setAchieve(0,.99f,70);
//				System.out.println("In Best Scoree============"+mGR.mBestScore);
			}
	   }
	}
}
float ChallengY=.659f;
void DrawChallenge(GL10 gl)
{
	float dy=.29f;
	  DrawTexture(gl,mGR.mTex_WorldBG[4],0,0);
	  DrawTexture(gl,mGR.mTex_TransBg,0,0);
	  
	  DrawTextureRST(gl,mGR.mTex_Arrow,0, .93f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1 ,270); //Up
	  DrawTextureRST(gl,mGR.mTex_Arrow,0,-.77f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1 ,90);//Down
	  gl.glEnable(GL10.GL_SCISSOR_TEST);
	  gl.glScissor(XPos(-1),YPos(.639f),(int)M.ScreenWidth,YPos(-.489f));
	  for(int i=0;i<M.Challenge.length;i++)
	  {
	     DrawTexture(gl ,mGR.mTex_ChallengeBox,-.079f ,ChallengY-i*dy);
	     mGR.mFont.Draw_String(gl,M.Challenge[i],-.05f,ChallengY-i*dy,1);
	     
	     DrawTexture(gl ,mGR.mTex_ChallengeRW[0],-.679f-.079f,ChallengY-i*dy);
	     if(mGR.isChallenge[i])
    	   DrawTexture(gl ,mGR.mTex_ChallengeRW[1],-.679f-.079f,ChallengY-i*dy);
	     DrawTexture(gl ,mGR.mTex_SmallBox     ,.679f-.079f ,ChallengY-i*dy);
	     mGR.mFont.Draw_String(gl,M.ChallengeCoin[i]+"",.71f-.079f,ChallengY-i*dy,1);
	     DrawTexture(gl ,mGR.mTex_CoinIcn      ,.829f-.079f ,ChallengY-i*dy);
	  } 
	  
	  gl.glDisable(GL10.GL_SCISSOR_TEST);
//	  DrawTransScal(gl,mGR.mTex_GoogleBtn ,-.809f,-.8f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
//	  DrawTransScal(gl,mGR.mTex_FbBtn     ,-.629f,-.8f,mGR.mSel==7?1.2f:1,mGR.mSel==7?.5f:1);
	  DrawTransScal(gl,mGR.mTex_BackBtn   ,.729f ,-.8f ,mGR.mSel==8?1.2f:1,mGR.mSel==8?.5f:1);
	  
	  
	  ChallengY+=cvy;
	  if(cvy>0)
	  {
	    cvy -=cvy*.1f;
	    if(cvy<-0.001)
    	  cvy=0;
	  }
	  if(cvy<0)
	  {
	    cvy -=cvy*.1f;
	    if(cvy>0.001)
    	   cvy=0;
	  }
	  if(ChallengY>=.659f+(M.Challenge.length-4)*dy)
	  {
//		 ChallengY =.659f+(M.Challenge.length-4)*dy;
		 cvy =-.03f;
	  }
	  if(ChallengY<.659f)
	  {
//		 ChallengY=.659f;
		 cvy =.03f;
	  }

}
float cvy;
float cy;
boolean HandleChallenge(MotionEvent event)
{
	 mGR.mSel =0;
	
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		cy = screen2worldY(event.getY());
	}
//	if(CircRectsOverlap(0,.89f,mGR.mTex_Arrow.width()*.5f,mGR.mTex_Arrow.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	{
//		mGR.mSel  = 1;
//		mGR.mMove = 1;
//	}
//	if(CircRectsOverlap(0,-0.72f,mGR.mTex_Arrow.width()*.5f,mGR.mTex_Arrow.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	{
//		mGR.mSel  = 2;
//		mGR.mMove = 2;
//	}
		
	if(CircRectsOverlap(-.809f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =6;
	if(CircRectsOverlap(-.629f,-.779f,mGR.mTex_GoogleBtn.width()*.4f,mGR.mTex_GoogleBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =7;
	if(CircRectsOverlap(.729f ,-.779f,mGR.mTex_BackBtn.width()*.4f,mGR.mTex_BackBtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =8;

	
	float ddy = Math.abs(screen2worldY(event.getY())-cy);
	if(ddy<.05f && mGR.mSel==0)
     return true; 
			
	if(event.getAction() == MotionEvent.ACTION_MOVE)
	{
		if(cy<screen2worldY(event.getY()))
		{
			cvy = Math.abs(cy-screen2worldY(event.getY()))*.3f;
			cvy +=.06f;
			cy  = screen2worldY(event.getY()); 
//			System.out.println("In Positiveee"+cvy);
		}
		if(cy>screen2worldY(event.getY()))
		{
			cvy  =-Math.abs(cy-screen2worldY(event.getY()))*.3f;
			cvy -=.06f;
			cy  = screen2worldY(event.getY());
//			System.out.println("In negativeee"+cvy);
		}
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	 {
		 switch(mGR.mSel)
		 {
		   case 6:
			   Google();
			   break;
		   case 7:
			   FaceBook();
			   break;
		   case 8:
			   mGR.resetWorld();
			   M.GameScreen = M.GAMEWORLD;
			   cvy=0;
			   break;
		 }
		 
		 mGR.mMove=mGR.mSel=0;
	 }
	
	return true;
}


void DrawJoin(GL10 gl)
{
	DrawTextureS(gl, mGR.mTex_TransBg,0,0+mGR.mJoinAnim,.6f);
  	DrawTexture(gl, mGR.mTex_JoinText,0-mGR.mJoinAnim,.16f);
  	DrawTransScal(gl, mGR.mTex_JoinBtn,0,-.419f-mGR.mJoinAnim,mGR.mSel==100?1.2f:1,mGR.mSel==100?.45f:1);
  	DrawTransScal(gl, mGR.mTex_Exit,.55f+mGR.mJoinAnim,.54f ,mGR.mSel==200?1.2f:1,mGR.mSel==200?.45f:1);
  	
  	if(mGR.mJoinAnim>=0)
  	  mGR.mJoinAnim*=.3f; 
}
boolean HandleJoin(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(0+mGR.mJoinAnim   ,-.419f,mGR.mTex_JoinBtn.width()*.5f,mGR.mTex_JoinBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=100;
	if(CircRectsOverlap(.55f+mGR.mJoinAnim,.54f,mGR.mTex_JoinBtn.width()*.5f,mGR.mTex_JoinBtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=200;
	
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 100:
			   mGR.isJoin = false;
			 break;
		 case 200:
			  mGR.isJoin =false;
			 break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
{
	Tex.drawPos(gl, x, y);
}
void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY)
{
	Tex.drawRotet(gl, x, y,angle,rotX,rotY);
}
void DrawTexturRPS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY,float scal)
{
	Tex.drawRotetSP(gl, x, y,angle,rotX,rotY,scal);
}
void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
{
	Tex.drawScal(gl, x, y,scal,scal);
}
void DrawTextureSS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy,float w,float h)
{
	Tex.drawSS(gl, x, y,sx,sy,w,h);
}
void DrawTextureRST(GL10 gl,SimplePlane Tex,float x,float y,float scal,float tran,float angle)
{
	Tex.drawRotetScal(gl, x, y,scal,angle,tran);
}
void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b,float t)
{
	Tex.drawRGB(gl, x, y, r, g, b,t);
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
boolean Rext2RectColli(float x1,float y1,float dx1,float dy1,float x2,float y2,float dx2,float dy2)
{
 if(x1 <=x2+dx2 && x1+dx1>=x2 && y1<=y2+dy2 && dy1+y1>=y2)
    return true;
 
  return false;
}
//boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
//{
//	ax -= adx/2;
//	ay += ady/2;
//	bx -= bdx/2;
//	by += bdy/2;
//	if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
//	{
//		return true;
//	}
//	return false;
//}
boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
{
	float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
	if (bVectMag<(r1+r2))
       return true;
    return false ;

}
float i_x,i_y;
boolean get_line_intersection(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y)
{
	    float s1_x, s1_y, s2_x, s2_y;
	    s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
	    s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

	    float s, t;
	    s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
	    t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
	    {
	    	i_x = p0_x + (t * s1_x);
	    	i_y = p0_y + (t * s1_y);
	        return true;
	    }

	    return false; // No collision
}
boolean circleLineIntersect(float x1, float y1, float x2, float y2, float cx, float cy, float cr){
      float dx = x2 - x1;
      float dy = y2 - y1;
      float a = dx * dx + dy * dy;
      float b = 2 * (dx * (x1 - cx) + dy * (y1 - cy));
      float c = cx * cx + cy * cy;
      c += x1 * x1 + y1 * y1;
      c -= 2 * (cx * x1 + cy * y1);
      c -= cr * cr;
      float bb4ac = b * b - 4 * a * c;

      if(bb4ac<0){
          return false;// No collision
      }else{
          return true;//Collision
      }
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

   int YPos(float y) {
        return (int)(((1-(y))*M.ScreenHieght)/2);
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
	void drawNumber(GL10 gl,int no,float x, float y,int Align)
	{
		float dx = mGR.mTex_Font[0].width()*.6f;
		String str = no+"";
		float len  = str.length();
		if(Align ==1) //Center
		  x -= (dx*(len))*.5f;
		if(Align ==2)//Right
	  	  x -= (dx*(len))*1f;
		String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y);
		}
	}
	void DrawNumberScal(GL10 gl,int no,float x, float y,float Scal,float tr,int Align)
	{
		float dx = (mGR.mTex_Font[0].width()*.6f)*Scal;
		String str = no+"";
		float len  = str.length();
		if(Align ==1) //Center
		  x -= (dx*(len))*.5f;
		if(Align ==2)//Right
	  	  x -= (dx*(len))*1f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				DrawTransScal(gl,mGR.mTex_Font[k],x+i*dx,y,Scal,tr);
		}
	}
	void FaceBook()
	{
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
//	    GameRenderer.mContext.startActivity(mIntent);
	}
	void Google()
	{
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
//	    GameRenderer.mContext.startActivity(mIntent);
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
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
//	void CarLineCollision()
//	{
//		float rx,ry,rx1,ry1;
//		float lx,ly;
//		for(int i=0;i<mGR.mCar.length;i++)
//		{
//			if(mGR.mCar[i].x>-1f && mGR.mCar[i].x<1)
//			{
//				float dx,dy;
//				if(mGR.mCar[i].no<19)
//				  {
//					 // For Car
//					  dx = mGR.mTex_Car[mGR.mCar[i].no].width() *.24f;
//					  dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*.699f;  
//				  }
//				 else if(mGR.mCar[i].no==21)
//				  {
//					 // For Fire Brigade
//				    dx = mGR.mTex_Car[mGR.mCar[i].no].width()*.21f;
//				    dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*1.209f;
//				  }
//				 else
//				  {
//					 // For Tanker
//				    dx = mGR.mTex_Car[mGR.mCar[i].no].width()*.23f;
//				    dy = mGR.mTex_Car[mGR.mCar[i].no].Height()*1.389f; 
//				  }
//				  float rad = (float)Math.toRadians(mGR.mCar[i].ang);
//			      lx = (float)(dx*Math.cos(rad));
//				  ly = (float)(dy*Math.sin(rad));
//				  
//				  rx =  mGR.mCar[i].x-lx;
//				  ry =  mGR.mCar[i].y+ly;
//				  
//				  rx1 =  mGR.mCar[i].x+lx;
//				  ry1 =  mGR.mCar[i].y-ly;
//				  
//				  if(mGR.mCar[i].ang==mGR.mStopCar.ang)
//				  {
////					float rvx = mGR.mStopCar.x+Math.abs(mGR.mCar[i].vx*50f);  
////					float rvy = mGR.mStopCar.y+Math.abs(mGR.mCar[i].vy*40f);
//					  float rvx = Math.abs(mGR.mStopCar.x);  
//					  float rvy = Math.abs(mGR.mStopCar.y);
//					if(rvx<=0)
//						rvx=.01f;
//					if(rvy<=0)
//						rvy=.01f;
//					System.out.println("rvx=============   "+rvx+"rvy============= "+rvy);
//				    if(get_line_intersection(rx,ry,rx1,ry1,-rvx,-rvy,rvx,rvy))
//				    {
//					   mGR.mCar[i].Speed=0;
//
//				    }
//				  }
//			}
//		}
//	}
}
