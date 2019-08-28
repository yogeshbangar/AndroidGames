

package com.hututu.game.helicoptercontrol;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
public class M 
{
	
	
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	public static final int GAMELOGO = 0 ,GAMEADD = 1,GAMESPLASH=2,GAMEMENU = 3,GAMEHIGH=4,GAMEPLAY = 5,GAMEGOINGPLAY = 6;
	public static final int GAMEHELP   = 7,GAMEOVER = 8,GAMEGOINGOVER = 9,GAMELEVEL = 10,GAMEWIN = 11;
	public static final int GAMECONG = 12,GAMEINFO = 13,GAMEPAUSE =14;
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null;;
	private static MediaPlayer mpBG1 = null;
	
	public static float BGSPEED =-.03f;
	public static final int LAST =50;
	
	public static final String MARKET 	        = "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 	        = "market://details?id=";
//	public static final String AMAZONMARKET 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.stuntracingcar&showAll=1";
//	public static final String AMAZONLINK 	    = "http://www.amazon.com/gp/mas/dl/android?p=";
	public static final String MY_AD_UNIT_ID    = "ca-app-pub-3395412980708319/5703781784";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/7180514987";	//AdHouse
	
	public static void loadSound(Context context)
	{
		try{
			
			if(mpBG1==null)
				mpBG1 = MediaPlayer.create(context, R.drawable.background);//
			
			if(mpEffect==null)
				mpEffect = MediaPlayer.create(context, R.drawable.coin);//
			
			if(mpEffect2==null)
				mpEffect2 = MediaPlayer.create(context, R.drawable.coin);
			
			if(mpEffect3==null)
				mpEffect3 = MediaPlayer.create(context, R.drawable.coin);//
			
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, R.drawable.explosion);//
			
		}catch(Exception e){}
	}
	public static void BGplay(Context context, int resource) 
	{
		try{
			stop();
			if (setValue) 
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
	public static void stop() {
		try{
			
			if (mpBG1 != null)
			{
				mpBG1.setLooping(false);
				mpBG1.stop();
				mpBG1.release();
				mpBG1 = null;
			}
			
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
			{
				mpEffect = MediaPlayer.create(context, resource);
			}
			if(!mpEffect.isPlaying() && setValue)
				mpEffect.start();
			else
				sound2(context, resource);
				
			
		}catch(Exception e){}	
	}
	public static void sound2(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
			else
				sound3(context, resource);
			
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
				if(!mpEffect3.isPlaying() && setValue)
					mpEffect3.start();
				else
					sound1(context,resource);
			}
		}catch(Exception e){}
	}
	public static void sound4(Context context, int resource) {
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
	public static void sound5(Context context, int resource) {
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
	public static void sound6(Context context, int resource) {
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
}
