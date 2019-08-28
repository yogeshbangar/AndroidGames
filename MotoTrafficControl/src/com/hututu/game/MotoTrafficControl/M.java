

package com.hututu.game.MotoTrafficControl;

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
	public static final int GAMELOGO 		   = 0;
	public static final int GAMEADD 		   = 1;
	public static final int GAMESPLASH  	   = 2;
	public static final int GAMEMENU 	 	   = 3;
	public static final int GAMEWORLD 	 	   = 4;
	public static final int GAMEPLAY 		   = 5;
	public static final int GAMEABTUS 		   = 6;
	public static final int GAMESHOP 		   = 7;
	public static final int GAMECHALLENGE      = 8;
	public static final int GAMEPAUSE 		   = 9;
	public static final int KEYSHOP 		   = 10;
	public static final int COINSHOP 		   = 11;
	public static final int WORLDSHOP 		   = 12;
	public static final int GAMEKEYUSE 		   = 13;
	public static final int GAMEOVER 		   = 14;
	public static final int GAMELOADING		   = 15;
	public static final int GAMEUSEPOWER	   = 16;
	public static final int GAMEFREECOIN	   = 17;
	
	
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	public  static boolean setValue	= true,SetBG =true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect41=null,mpEffect42=null,mpEffect5=null,mpEffect51=null
			  	  ,mpEffect52=null,mpEffect6=null,mpEffect7=null,mpEffect8=null,mpEffect9=null;
							  
	private static MediaPlayer mpBG = null,mpBG1 = null;
	
//	public static final String MY_BANNER_ID        = "ca-app-pub-3395412980708319/9684400183";	//AndroidMarket
	public static final String MY_INTERSTITIAL_ID  = "ca-app-pub-3395412980708319/3525384585";	//AndroidMarket
	
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
	public static final int Achivemnet[]={
		R.string.achievement_cross10_car,
		R.string.achievement_cross25_car,
		R.string.achievement_cross50_car,
		R.string.achievement_cross100_car,
		R.string.achievement_cross250_car,
		R.string.achievement_cross500_car,
		R.string.achievement_cross1000_car,
		R.string.achievement_cross2500_car,
		R.string.achievement_cross5000_car,
		R.string.achievement_cross10000_car,
		R.string.achievement_cross20000_car,
		R.string.achievement_cross35000_car,
		R.string.achievement_cross50000_car,
		R.string.achievement_cross75000_car,
		R.string.achievement_cross100000_car,
		R.string.achievement_unlock_world_2,
		R.string.achievement_unlock_world_3,
		R.string.achievement_unlock_world_4,
		R.string.achievement_unlock_world_5,
		R.string.achievement_unlock_world_6,
		R.string.achievement_unlock_world_7,
		R.string.achievement_unlock_world_8,
		R.string.achievement_unlock_world_9,
		R.string.achievement_unlock_world_10,
		R.string.achievement_unlock_world_11,
		R.string.achievement_unlock_world_12,
		R.string.achievement_unlock_slow_car,
		R.string.achievement_fast_car,
		R.string.achievement_unlock_bridge,
		R.string.achievement_unlock_tunnel,
		R.string.achievement_unlock_stop_car,
		R.string.achievement_slow_car_full,
		R.string.achievement_fast_car_full_upgrade,
		R.string.achievement_bridge_full_upgrade,
		R.string.achievement_tunnnel_full_upgrade,
		R.string.achievement_stop_car_full,
		R.string.achievement_5_stage,
		R.string.achievement_10_stage,
		R.string.achievement_25_stage,
		R.string.achievement_50_stage,
		R.string.achievement_100_stage,
		R.string.achievement_250_stage,
		R.string.achievement_500_stage,
		R.string.achievement_50000_coin,
		R.string.achievement_100000_coins,
		R.string.achievement_10000_score,
		R.string.achievement_25000_score,
		R.string.achievement_50000_score,
		R.string.achievement_100000_score,
		R.string.achievement_500000_score,
		
	};
	
	public static final String Challenge[]={
			"Play for 30 Sec in one play",
			"Play for 60 Sec in one play",
			"Play for 120 Sec in one play",
			"Play for 5 Min in one play",
			"Play for 10 Min in one play",
			"Play for 30 Min in one play",
			"Play for 45 Min in one play",
			"Play for 10 Min for alltime",
			"Play for 30 Min for alltime",
			"Play for 60 Min for alltime",
			"Play for 90 Min for alltime",
			"Play for 2hr for alltime",
			"Play for 5hr for alltime",
			"Play for 10hr for alltime",
			"Collect 100 coins in alltime",	
			"Collect 500 coins in alltime",
			"Collect 1000 coins in alltime",
			"Collect 5000 coins in alltime",
			"Collect 10000 coins in alltime",
			"Collect 25000 coins in alltime",
			"Collect 50000 coins in alltime",
			"Collect 75000 coins in alltime",
			"Collect 100000 coins in alltime",
			"Collect 500000 coins in alltime",
			
		
	};
	public static final int ChallengeCoin[] ={5,10,20,40,80,100,120,5,10,20,40,80,100,120,5,10,20,40,80,100,120,150,175,200};
	public static void loadSound(Context context)
	{
		try{
			mpBG        =  MediaPlayer.create(context, R.drawable.bg);
			mpEffect    =  MediaPlayer.create(context, R.drawable.coin);
			mpEffect2   =  MediaPlayer.create(context, R.drawable.carcrash0);
			mpEffect3   =  MediaPlayer.create(context, R.drawable.carcrash1);
			mpEffect4   =  MediaPlayer.create(context, R.drawable.car_pass0);
			mpEffect41  =  MediaPlayer.create(context, R.drawable.car_pass0);
			mpEffect42  =  MediaPlayer.create(context, R.drawable.car_pass0);
			mpEffect5   =  MediaPlayer.create(context, R.drawable.car_pass1);
			mpEffect51  =  MediaPlayer.create(context, R.drawable.car_pass1);
			mpEffect52  =  MediaPlayer.create(context, R.drawable.car_pass1);
			mpEffect6   =  MediaPlayer.create(context, R.drawable.police_car);
			mpEffect7   =  MediaPlayer.create(context, R.drawable.click);
			mpBG1	    =  MediaPlayer.create(context, R.drawable.splash_theme);
			mpEffect8   =  MediaPlayer.create(context, R.drawable.freecoin);
			
		}catch(Exception e){}
	}
	public static void BGPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG==null)
				 mpBG= MediaPlayer.create(context, resource);
				
				if(!mpBG.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG.setLooping(true);
					mpBG.start();
				}
			}
		}catch(Exception e){}
	}
	public static void SplashPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG1==null)
				 mpBG1= MediaPlayer.create(context, resource);
				
				if(!mpBG1.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG1.setLooping(true);
					mpBG1.start();
				}
			}
		}catch(Exception e){}
	}
	
	public static void CoinSound(Context context, int resource) {
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
	public static void Crash1Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying()){
				mpEffect2.start();
			}
		}catch(Exception e){}	
	}
	public static void Crash2Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect3==null)
			{
				mpEffect3 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect3.isPlaying()){
				mpEffect3.start();
			}
		}catch(Exception e){}	
	}
	public static void CarPaas1Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect4.isPlaying()){
				mpEffect4.start();
			}
			else
			{
				CarPaas11Sound(context,resource);
			}
		}catch(Exception e){}	
	}
	public static void CarPaas11Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect41==null)
			{
				mpEffect41 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect41.isPlaying()){
				mpEffect41.start();
			}
			else
			{
				CarPaas12Sound(context,resource);
			}
		}catch(Exception e){}	
	}
	public static void CarPaas12Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect42==null)
			{
				mpEffect42 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect42.isPlaying()){
				mpEffect42.start();
			}
		}catch(Exception e){}	
	}
	public static void CarPaas2Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
			{
				mpEffect5 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect5.isPlaying()){
				mpEffect5.start();
			}
			else
			{
			   CarPaas21Sound(context, resource);
			}
		}catch(Exception e){}	
	}
	public static void CarPaas21Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect51==null)
			{
				mpEffect51 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect51.isPlaying()){
				mpEffect51.start();
			}
			else
				CarPaas22Sound(context, resource);
		}catch(Exception e){}	
	}
	public static void CarPaas22Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect52==null)
			{
				mpEffect52 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect52.isPlaying()){
				mpEffect52.start();
			}
		}catch(Exception e){}	
	}
	public static void PoliceSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
			{
				mpEffect6 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect6.isPlaying()){
				mpEffect6.start();
			}
		}catch(Exception e){}	
	}
	public static void ClickSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
			{
				mpEffect7 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect7.isPlaying()){
				mpEffect7.start();
			}
		}catch(Exception e){}	
	}
	public static void FreeCoinSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
			{
				mpEffect8 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect8.isPlaying()){
				mpEffect8.start();
			}
		}catch(Exception e){}	
	}
	public static void BgStop() 
	{
		try {
			if(mpBG != null)
			{
				mpBG.stop();
				mpBG.setLooping(false);
				mpBG.release();
				mpBG = null;
			}
			if(mpBG1 != null)
			{
				mpBG1.stop();
				mpBG1.setLooping(false);
				mpBG1.release();
				mpBG1 = null;
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
			if (mpEffect41 != null)
			{
				mpEffect41.stop();
				mpEffect41.release();
				mpEffect41 = null;
			}
			if (mpEffect42 != null)
			{
				mpEffect42.stop();
				mpEffect42.release();
				mpEffect42 = null;
			}
			if (mpEffect5 != null)
			{
				mpEffect5.stop();
				mpEffect5.release();
				mpEffect5 = null;
			}
			if (mpEffect51 != null)
			{
				mpEffect51.stop();
				mpEffect51.release();
				mpEffect51 = null;
			}
			if (mpEffect52 != null)
			{
				mpEffect52.stop();
				mpEffect52.release();
				mpEffect52 = null;
			}
			if (mpEffect6 != null)
			{
				mpEffect6.stop();
				mpEffect6.release();
				mpEffect6 = null;
			}
			
		}catch(Exception e){}
	}

}
