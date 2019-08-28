

package com.hututu.game.ScarecrowvsBirds;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;

public class M 
{
	public static final String MARKET 	     = "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 	     = "market://details?id=";
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/2052311385";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/3529044583"; //AdHouse  
//	public static final String MARKET 		= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	public static final String LINK 		= "samsungapps://ProductDetail/";
	
//	public static final String MARKET 		= "http://slideme.org/user/hututugames";
//	public static final String LINK 		= "market://details?id=";

//	public static final String  LINK 		= "http://www.amazon.com/gp/mas/dl/android?p=";
//	public static final String  MARKET 		= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
	
	public static Random mRand = new Random();
	public static final String score = "score";
	public static       int GameScreen,GamePrevScreen;
	public static final int GAMELOGO  = 0,GAMEADD  = 1,GAMESPLASH = 2,GAMEMENU = 3,GAMEHIGHSCORE=4,GAMEPLAY = 5,GAMEPAUSE = 6;
	public static final int GAMEHELP  = 7,GAMEOVER = 8,GAMEABTUS = 9,GAMELEVEL = 10,GAMEWIN = 11;
	public static final int GAMECONG = 12;
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	private static MediaPlayer mpBg 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6 =null;
	
	public static final float  Ballonpos[] ={.2f,.4f,.6f,.8f};
	public static final int    ARROWPOWER =0,BLASTPOWER=1,SLOWPOWER =2,GANTAPOWER =3,THROWNPOWER =4;
	public static final float  Gravity = -.0035f;
	public static float        Speed =1f;
	public static float        SlowSpeed  =.5f;
	public static final float  Diff       =.6f;
	static final int 		   POWERTIME  = 100;
	static final int 		   ACTIVETIME = 60;
	
	public static void loadSound(Context context)
	{
		try{
			if(mpBg  == null)
				mpBg = MediaPlayer.create(context, R.drawable.background);//
			
			if(mpEffect==null)
				mpEffect = MediaPlayer.create(context, R.drawable.arrowshoot);//
			
			if(mpEffect2==null)
				mpEffect2 = MediaPlayer.create(context, R.drawable.bird);
			
			if(mpEffect3==null)
				mpEffect3 = MediaPlayer.create(context, R.drawable.crow);//
			
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, R.drawable.crow2);//
			
			if(mpEffect5==null)
				mpEffect5 = MediaPlayer.create(context, R.drawable.duck);//
			
			if(mpEffect6==null)
				mpEffect6 = MediaPlayer.create(context, R.drawable.powercollect);//
			
		}catch(Exception e){}
	}
	public static void BGplay(Context context, int resource) 
	{
		try{
			stop();
			if (setValue) 
			{
			  if(mpBg == null)
				 mpBg = MediaPlayer.create(context, resource);
				
				if(!mpBg.isPlaying())
				{
				  if(resource!=2131099648)
					mpBg.setLooping(true);
					mpBg.start();
				}
			}
		}catch(Exception e){}
	}
	public static void BGStop() 
	{
		if (mpBg != null)
		{
			mpBg.setLooping(false);
			mpBg.stop();
			mpBg.release();
			mpBg = null;
		}
	}
	public static void stop() {
		try{
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
			
			
		}catch(Exception e){}
	}
	public static void sound1(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
				mpEffect = MediaPlayer.create(context, resource);
			if(!mpEffect.isPlaying())
				mpEffect.start();
			
		}catch(Exception e){}	
	}
	public static void sound2(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			  mpEffect2 = MediaPlayer.create(context, resource);
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
			
		}catch(Exception e){}
	}
	public static void sound3(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(context, resource);
				if(!mpEffect3.isPlaying())
					mpEffect3.start();
			}
		}catch(Exception e){}
	}
	public static void sound4(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, resource);
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
		}catch(Exception e){}
	}
	public static void sound5(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
			   mpEffect5 = MediaPlayer.create(context, resource);
		    if(!mpEffect5.isPlaying() && setValue)
			   mpEffect5.start();
	
		}catch(Exception e){}
	}
	public static void sound6(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
			   mpEffect6 = MediaPlayer.create(context, resource);
		    if(!mpEffect6.isPlaying() && setValue)
			   mpEffect6.start();
	
		}catch(Exception e){}
	}
	
	
}
