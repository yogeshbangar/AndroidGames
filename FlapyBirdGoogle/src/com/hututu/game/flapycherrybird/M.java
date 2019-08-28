

package com.hututu.game.flapycherrybird;

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
	public static float ScreenWidth,ScreenHieght;
	public static int GameScreen;
	public static final int GAMELOGO     = 0;
	public static final int GAMEADD      = 1;
	public static final int GAMESPLASH   = 2;
	public static final int GAMEMENU     = 3;
	public static final int GAMEPLAY     = 4;
	public static final int GAMEOPTION   = 5;
	public static final int GAMEABTUS    = 6;
	public static final int GAMEHELP     = 7;
	public static final int GAMEOVER     = 8;
	public static final int GAMEGOINGOVER= 9;
	public static final int GAMEGO2PLAY  = 10;
	public static final int GAMEPAUSE    = 11;
	public static final int GAMELOAD     =14;
	
	
	
	public static final float mMaxX = 800,mMaxY=480;
	
	public  static boolean setValue	= true,SetBG =true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null
							  ,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null;
	
	final static float speed=-.0125f;
	
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/4168172988";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/5644906187";	//AdHouse
	
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
			mpEffect  = MediaPlayer.create(context, R.drawable.button);
//			mpEffect2 = MediaPlayer.create(context, R.drawable.tap);
			mpEffect3 = MediaPlayer.create(context, R.drawable.collision);
			mpEffect4 = MediaPlayer.create(context, R.drawable.gameover);
			mpEffect5 = MediaPlayer.create(context, R.drawable.birdflap);
//			mpEffect6 = MediaPlayer.create(context, R.drawable.polhit);
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
	public static void TapSound(Context context, int resource) {
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
	public static void ColliSound(Context context, int resource) {
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
	public static void OverSound(Context context, int resource) {
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
		}catch(Exception e){}	
	}
	public static void flapSound(Context context, int resource) {
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
		}catch(Exception e){}	
	}
	public static void polhitSound(Context context, int resource) {
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
}
