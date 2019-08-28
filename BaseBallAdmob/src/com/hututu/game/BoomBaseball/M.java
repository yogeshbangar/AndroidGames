

package com.hututu.game.BoomBaseball;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
public class M 
{
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.game.archeryking";
//	public static final String MARKET 		= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	public static final String LINK 		= "samsungapps://ProductDetail/";
//	public static final String SHARELINK 	= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	
//	public static final String MARKET 		= "http://slideme.org/user/hututugames";
//	public static final String LINK 		= "market://details?id=";
//	public static final String SHARELINK 	= "http://slideme.org/user/hututugames";

//	public static final String  LINK 		= "http://www.amazon.com/gp/mas/dl/android?p=";
//	public static final String  MARKET 		= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	public static final String SHARELINK 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	
	
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	public static final int GAMELOGO = 0,GAMEADD  = 1,GAMESPLASH  = 2,GAMEMENU  = 3 ,GAMESETTING  = 4,GAMEPLAY = 5,GAMEWIN = 6;
	public static final int GAMEHELP = 7,GAMEOVER = 8,GAMEABOUT   = 9,GAMEPAUSE =10,GAMETARGET=11,GAMEACHIV=12;
	public static final int GAMELOAD =14;
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/7540441785";	//AdHouse
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	public  static boolean setValue	= true,SetBG =true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null
							  ,mpEffect9=null,mpEffect10=null,mpEffect11=null,mpEffect12=null,mpEffect13=null,mpEffect131=null,mpEffect132=null,mpEffect133= null,mpEffect14=null,mpEffect15=null
							  ,mpEffect16=null,mpEffect17=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/6063708585";	//AndroidMarket
	
	public static final float Wx[]     		   = {-.919f,-.839f,-.70f ,-.594f,-.489f,-.569f,-.499f,-.364f,-.269f,-.269f,-.364f,-.264f,-.12f,.029f,.029f,-.12f,-.12f ,.199f ,.159f ,.234f , .145f,.179f ,.434f,.369f,.484f,.484f ,.369f ,.369f ,.484f ,.659f,.714f,.659f,.659f ,.709f ,.709f,.689f ,-.042f,.689f};
	public static final float Wy[]     		   = {-.079f,-.079f,-.01f ,.074f ,.149f ,-.194f ,-.194f ,.100f,.100f,-.044f,-.044f ,-.304f,.19f,.19f ,-.04f,-.04f,-.274f,.0749f,-.095f,-.095f,-.279f,-.279f,.359f,.189f,.189f,-.009f,-.009f,-.209f,-.209f,.214f,.214f,.12f ,-.005f,-.005f,.12f ,-.189f,.379f ,.349f};
	
	public static final float DoorX[]  		   = {-.699f,-.369f, .01f,.25f ,.909f};  
	public static final float DoorY[]  		   = {-.22f ,-.319f,-.28f,-.28f,-.184f};
	
	public static final float CarX[]   		   = {.469f,.609f,-.549f};
	public static final float CarY[]   		   = {-.429f,-.369f,-.389f};
	public static final float CarRad[][] 	   = {{.025f,.1f,.07f},
												  {.05f,.11f,.075f}};
	
	public static final float Building[][]     = {{-.9f,-.609f,-.319f,-.04f , .2f ,.429f,.679f , .89f},
											     {-.1f ,-.08f ,-.049f, .15f ,-.05f,.15f ,.17f  ,-.029f}};
	public static final float BuildingArea[][] = {{.1f,.17f,.12f,.14f,.1f ,.14f,.1f,.1f},
		   									     { .1f,.16f,.3f ,.42f,.25f,.42f,.3f,.07f}};
	public static final float Chimney[][]	   = {{-.98f,-.889f,-.559f,-.125f,.039f,.529f,.819f,.919f},
											      {.059f, .09f , .28f ,.599f ,.599f,.629f,.19f ,.17f}};
	
	public static final float TreeX[]   	   = {-.929f,-.859f,-.26f,-.439f};
	public static final float TreeY[]   	   = {.29f  ,.21f  ,.429f,-.08f};
	
//	public static final int Score[]          = {-100,500,1000,6500}; 
	public static final int Restart =100;
	public static final float VY =-.0185f;
	
	public static final boolean isAchive[]={
	  false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	
	public static final int Achivemnet[]={
		R.string.achievement_5_car_hit,
		R.string.achievement_10_car_hit,
		R.string.achievement_50_car,
		
		R.string.achievement_door_shot,
		R.string.achievement_roof_hit,
		
		R.string.achievement_three_shot,
		R.string.achievement_five_shot,
		R.string.achievement_ten_shot,
		R.string.achievement_fifteen_shot,
		R.string.achievement_twenty_five_shot,
		R.string.achievement_hundred_shot,
		R.string.achievement_two_hundred_shot,
		R.string.achievement_five_hundred_shot,
		R.string.achievement_thousand_shot,
		
		R.string.achievement_three_strike,
		
		R.string.achievement_window_three,
		R.string.achievement_window_five,
		R.string.achievement_window_ten,
		R.string.achievement_windowfivehundred,
		R.string.achievement_windowsthousand,
		
		R.string.achievement_balloontwentyfive,
			
};
	public static final int Icon[]={
		R.drawable.ach0,
		R.drawable.ach1,
		R.drawable.ach2,
		R.drawable.ach3,
		R.drawable.ach4,
		R.drawable.ach5,
		R.drawable.ach6,
		R.drawable.ach7,
		R.drawable.ach8,
		R.drawable.ach9,
		R.drawable.ach10,
		R.drawable.ach11,
		R.drawable.ach12,
		R.drawable.ach13,
		R.drawable.ach14,
		R.drawable.ach15,
		R.drawable.ach16,
		R.drawable.ach17,
		R.drawable.ach18,
		R.drawable.ach19,
		R.drawable.ach20,
	};
	public static final int Msg[]={
		R.string.text1,
		R.string.text2,
		R.string.text3,
		R.string.text4,
		R.string.text5,
		R.string.text6,
		R.string.text7,
		R.string.text8,
		R.string.text9,
		R.string.text10,
		R.string.text11,
		R.string.text12,
		R.string.text13,
		R.string.text14,
		R.string.text15,
		R.string.text16,
		R.string.text17,
		R.string.text18,
		R.string.text19,
		R.string.text20,
		R.string.text21,
	};
	public static float randomRange(float min,float max)
	{
		float rand = M.mRand.nextFloat();
		max = max-min;
		max  = rand%max;
		return (max+min);
	}
	public static int randomRangeInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	public static double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	public static void loadSound(Context context)
	{
		try{
			mpBG1 		= MediaPlayer.create(context, R.drawable.theme);		// theme
			mpBG2 		= MediaPlayer.create(context, R.drawable.gamebg);		// GameBg
			mpEffect    = MediaPlayer.create(context, R.drawable.click);		// Click
			mpEffect2   = MediaPlayer.create(context, R.drawable.window);		// Window
			mpEffect3   = MediaPlayer.create(context, R.drawable.wall);			// wall
			mpEffect4   = MediaPlayer.create(context, R.drawable.strike);		// Strike
			mpEffect5   = MediaPlayer.create(context, R.drawable.femalevoice);	// Female1
			mpEffect6   = MediaPlayer.create(context, R.drawable.femalevoice1);	// Female2
			mpEffect7   = MediaPlayer.create(context, R.drawable.ballhit);		//ball Hit
			mpEffect8   = MediaPlayer.create(context, R.drawable.batswing);		// batswing
			mpEffect9   = MediaPlayer.create(context, R.drawable.clap);			// Clap
			mpEffect10  = MediaPlayer.create(context, R.drawable.ballswing);	// ballSwing
			mpEffect11  = MediaPlayer.create(context, R.drawable.bonusshot);	// BonusShot
			mpEffect12  = MediaPlayer.create(context, R.drawable.airbonus);		// airBonus
			mpEffect13  = MediaPlayer.create(context, R.drawable.counting);		// Counting
			mpEffect14  = MediaPlayer.create(context, R.drawable.bird);			// bird
			mpEffect15  = MediaPlayer.create(context, R.drawable.plain);		// Pane
			mpEffect16  = MediaPlayer.create(context, R.drawable.helli);		// Heli
			mpEffect17  = MediaPlayer.create(context, R.drawable.bonusball);	// BonusBall
		}catch(Exception e){}
	}
	public static void SplashPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG1==null)
				 mpBG1 = MediaPlayer.create(context, resource);
				
				if(!mpBG1.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG1.setLooping(true);
					mpBG1.start();
				}
			}
		}catch(Exception e){}
	}
	public static void BGPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG2==null)
				 mpBG2= MediaPlayer.create(context, resource);
				
				if(!mpBG2.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG2.setLooping(true);
					mpBG2.start();
				}
			}
		}catch(Exception e){}
	}
	public static void BgStop() 
	{
		try {
			if(mpBG1 != null)
			{
				mpBG1.stop();
				mpBG1.setLooping(false);
				mpBG1.release();
				mpBG1 = null;
			}
			if(mpBG2 != null)
			{
				mpBG2.stop();
				mpBG2.setLooping(false);
				mpBG2.release();
				mpBG2 = null;
			}
		  }catch (Exception e){}
	}
	public static void StopSound() {
	   try{
		     BgStop();
			if (mpEffect != null)
			{
				mpEffect.stop();
				mpEffect.release();
				mpEffect = null;
			}
			if (mpEffect2 != null)
			{
				mpEffect2.stop();
				mpEffect2.release();
				mpEffect2 = null;
			}
			if (mpEffect3 != null)
			{
				mpEffect3.stop();
				mpEffect3.release();
				mpEffect3 = null;
			}
			if (mpEffect4 != null)
			{
				mpEffect4.stop();
				mpEffect4.release();
				mpEffect4 = null;
			}
			if (mpEffect5 != null)
			{
				mpEffect5.stop();
				mpEffect5.release();
				mpEffect5 = null;
			}
			if (mpEffect6 != null)
			{
				mpEffect6.stop();
				mpEffect6.release();
				mpEffect6 = null;
			}
			if (mpEffect7 != null)
			{
				mpEffect7.stop();
				mpEffect7.release();
				mpEffect7 = null;
			}
			if (mpEffect8 != null)
			{
				mpEffect8.stop();
				mpEffect8.release();
				mpEffect8 = null;
			}
			if (mpEffect9 != null)
			{
				mpEffect9.stop();
				mpEffect9.release();
				mpEffect9 = null;
			}
			if (mpEffect10 != null)
			{
				mpEffect10.stop();
				mpEffect10.release();
				mpEffect10 = null;
			}
			if (mpEffect11 != null)
			{
				mpEffect11.stop();
				mpEffect11.release();
				mpEffect11 = null;
			}
			if (mpEffect12 != null)
			{
				mpEffect12.stop();
				mpEffect12.release();
				mpEffect12 = null;
			}
			if (mpEffect13 != null)
			{
				mpEffect13.stop();
				mpEffect13.release();
				mpEffect13 = null;
			}
			if (mpEffect131!= null)
			{
				mpEffect131.stop();
				mpEffect131.release();
				mpEffect131 = null;
			}
			if (mpEffect132 != null)
			{
				mpEffect132.stop();
				mpEffect132.release();
				mpEffect132 = null;
			}
			if (mpEffect133 != null)
			{
				mpEffect133.stop();
				mpEffect133.release();
				mpEffect133 = null;
			}
			if (mpEffect14 != null)
			{
				mpEffect14.stop();
				mpEffect14.release();
				mpEffect14 = null;
			}
			if (mpEffect15 != null)
			{
				mpEffect15.stop();
				mpEffect15.setLooping(false);
				mpEffect15.release();
				mpEffect15 = null;
			}
			if (mpEffect16 != null)
			{
				mpEffect16.stop();
				mpEffect16.setLooping(false);
				mpEffect16.release();
				mpEffect16 = null;
			}
			if (mpEffect17 != null)
			{
				mpEffect17.stop();
				mpEffect17.release();
				mpEffect17 = null;
			}
			
		}catch(Exception e){}
	}
	public static void ClickSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(context, resource);
			}
			if(!mpEffect.isPlaying()){
				mpEffect.start();
			}
		}catch(Exception e){}	
	}
	public static void WindowSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
		}catch(Exception e){}
	}
	public static void WallSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(context, resource);
				if(!mpEffect3.isPlaying())
				{
					mpEffect3.start();
				}
			}
		}catch(Exception e){}
	}
	public static void StrikeSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
		}catch(Exception e){}
	}
	public static void FemaleSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
			{
				mpEffect5 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect5.isPlaying() && setValue)
				mpEffect5.start();
		}catch(Exception e){}
	}
	public static void FemaleSound2(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
			{
				mpEffect6 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect6.isPlaying() && setValue)
				mpEffect6.start();
		}catch(Exception e){}
	}
	public static void BallhitSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
			{
				mpEffect7 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect7.isPlaying() && setValue)
				mpEffect7.start();
		}catch(Exception e){}
	}
	public static void BatSwingSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
			{
				mpEffect8 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect8.isPlaying() && setValue)
				mpEffect8.start();
		}catch(Exception e){}
	}
	public static void ClapSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect9==null)
			{
				mpEffect9 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect9.isPlaying() && setValue)
				mpEffect9.start();
		}catch(Exception e){}
	}
	public static void BallSwingSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect10==null)
			{
				mpEffect10 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect10.isPlaying() && setValue)
				mpEffect10.start();
		}catch(Exception e){}
	}
	public static void BonusShotSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect11==null)
			{
				mpEffect11 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect11.isPlaying() && setValue)
				mpEffect11.start();
		}catch(Exception e){}
	}
	public static void AirBonusSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect12==null)
			{
				mpEffect12 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect12.isPlaying() && setValue)
				mpEffect12.start();
		}catch(Exception e){}
	}
	public static void CountingSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect13==null)
			{
				mpEffect13 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect13.isPlaying() && setValue)
				mpEffect13.start();
			else
				CountingSound1(context,R.drawable.counting);
		}catch(Exception e){}
	}
	public static void CountingSound1(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect131==null)
			{
				mpEffect131 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect131.isPlaying() && setValue)
				mpEffect131.start();
			else
			   CountingSound2(context,R.drawable.counting);
		}catch(Exception e){}
	}
	public static void CountingSound2(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect132==null)
			{
				mpEffect132 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect132.isPlaying() && setValue)
				mpEffect132.start();
			else
			  CountingSound3(context, R.drawable.counting);
		}catch(Exception e){}
	}
	public static void CountingSound3(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect133==null)
			{
				mpEffect133 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect133.isPlaying() && setValue)
				mpEffect133.start();
		}catch(Exception e){}
	}
	public static void BirdSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect14==null)
			{
				mpEffect14 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect14.isPlaying() && setValue)
				mpEffect14.start();
		}catch(Exception e){}
	}
	public static void PlaneSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
			{
				mpEffect15 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect15.isPlaying() && setValue)
			{
				mpEffect15.setLooping(true);
				mpEffect15.start();
			}
		}catch(Exception e){}
	}
	public static void HeliSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect16==null)
			{
				mpEffect16 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect16.isPlaying() && setValue)
			{
				mpEffect16.setLooping(true);
				mpEffect16.start();
			}
		}catch(Exception e){}
	}
	public static void PlaneHeliStop()
	{
		if (mpEffect15 != null)
		{
			mpEffect15.stop();
			mpEffect15.setLooping(false);
			mpEffect15.release();
			mpEffect15 = null;
		}
		if (mpEffect16 != null)
		{
			mpEffect16.stop();
			mpEffect16.setLooping(false);
			mpEffect16.release();
			mpEffect16 = null;
		}
	}
	public static void BonusBallSound(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect17==null)
			{
				mpEffect17 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect17.isPlaying() && setValue)
				mpEffect17.start();
		}catch(Exception e){}
	}
}
