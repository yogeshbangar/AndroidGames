

package com.httgames.amazingfreerunner;

import java.util.Random;


import android.media.MediaPlayer;
import android.util.Log;

public class C 
{
	public static final int ACHIV[] = {
		  R.string.achievement_classic_runner,
		  R.string.achievement_sharp_runner,
		  R.string.achievement_exalted_runner,
		  R.string.achievement_master_runner,
		  R.string.achievement_king_runner
	};
	public static float spd = -.03f;
	public static final String DIR = "/photo_grid";
	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=htt+games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	public static final int GAMELOGO = 0 ,GAMEPAUSE = 1,GAMEMENU = 3,GAMEHIGH=4,GAMEPLAY = 5;
	public static final int GAMEHELP   = 7,GAMEOVER = 8,GAMEA = 9,GAMELEVEL = 10,GAMEWIN = 11;
	public static final int GAMECONG = 12,GAMEINFO = 13;
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/4726576184";	//AdHouse
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=854;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/4726576184";	//AndroidMarket
	public static void play( int resource) 
	{
		try{
			stop();
			if (setValue) 
			{
				if(mp==null)
					mp = MediaPlayer.create(HTTRenderer.mContext, resource);
				
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){}
	}
	public static void playStop() 
	{
//		try{
//			if (mp != null)
//			{
//				Log.d("=================","=====================loopStop========================================");
//				mp.pause();
//			}	
//		}catch(Exception e){}
	}
	public static void loopStop() 
	{
		try{
//			if (mp != null)
//			{
//				Log.d("=================","=====================loopStop========================================");
//				mp.pause();
//			}	
			sound3Pause();
		}catch(Exception e){}
	}
	public static void stop() {
		try{
			Log.d("---------------------------------------------","sound stop");
//			if (mp != null)
//			{
//				mp.stop();
//				mp.release();
//				mp = null;
//			}
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
			if (mpEffect14 != null)
			{
				mpEffect14.stop();
				mpEffect14.release();
				mpEffect14 = null;
			}
			if (mpEffect15 != null)
			{
				mpEffect15.stop();
				mpEffect15.release();
				mpEffect15 = null;
			}
			
			
			
			
			if (mpBG1 != null)
			{
				mpBG1.stop();
				mpBG1.release();
				mpBG1 = null;
			}
			if (mpBG2 != null)
			{
				mpBG2.stop();
				mpBG2.release();
				mpBG2 = null;
			}
			if (mpBG3 != null)
			{
				mpBG3.stop();
				mpBG3.release();
				mpBG3 = null;
			}
			if (mpBG4 != null)
			{
				mpBG4.stop();
				mpBG4.release();
				mpBG4 = null;
			}
			if (mpBG5 != null)
			{
				mpBG5.stop();
				mpBG5.release();
				mpBG5 = null;
			}
			
			
		}catch(Exception e){}
	}
	public static void sound1( int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(HTTRenderer.mContext, resource);
			}
			if(!mpEffect.isPlaying())
				mpEffect.start();
//			else
//				sound2(GameRenderer.mContext,R.drawable.g2);
		}catch(Exception e){}	
	}
	public static void sound2( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(HTTRenderer.mContext, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
//			else
//				sound3Play(GameRenderer.mContext,R.drawable.g3);
		}catch(Exception e){}
	}
	public static void sound3( int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(HTTRenderer.mContext, resource);
				if(!mpEffect3.isPlaying())
				{
					//if(resource!=2131099648)
						//mpEffect3.setLooping(true);
					mpEffect3.start();
				}
//				else
//					sound4(GameRenderer.mContext,R.drawable.g4);
			}
		}catch(Exception e){}
	}
	public static void sound3Pause() 
	{
		if(!setValue)
			 return;
		try{
			if (mpEffect3 != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mpEffect3.pause();
			}	
		}catch(Exception e){}
	}
	public static void sound4( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(HTTRenderer.mContext, resource);
			}
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
			//else
				//sound5(GameRenderer.mContext,R.drawable.b5);
		}catch(Exception e){}
	}
	public static void sound5( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
		{
			mpEffect5 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect5.isPlaying() && setValue)
			mpEffect5.start();
	
		}catch(Exception e){}
	}
	public static void sound5Pause() {
		if (!setValue)
			return;
		try {
			if (mpEffect5 != null) {
				if (mpEffect5.isPlaying())
					mpEffect5.pause();
			}
		} catch (Exception e) {
		}
	}
	public static void sound6( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
		{
				mpEffect6 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect6.isPlaying() && setValue)
			mpEffect6.start();
		//else
			///sound7(GameRenderer.mContext,R.drawable.b7);
		}catch(Exception e){}
	}
	public static void sound7( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
		{
				mpEffect7 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect7.isPlaying() && setValue)
			mpEffect7.start();
	
		}catch(Exception e){}
	}
	public static void sound8( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
		{
				mpEffect8 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect8.isPlaying() && setValue)
			mpEffect8.start();
		}catch(Exception e){}
	}
	public static void sound9( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect9==null)
		{
				mpEffect9 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect9.isPlaying() && setValue)
			mpEffect9.start();
		}catch(Exception e){}
	}
	public static void sound10( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect10==null)
		{
				mpEffect10 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect10.isPlaying() && setValue)
			mpEffect10.start();
		}catch(Exception e){}
	}
	public static void sound11( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect11==null)
		{
				mpEffect11 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect11.isPlaying() && setValue)
			mpEffect11.start();
		}catch(Exception e){}
	}
	public static void sound12( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect12==null)
		{
				mpEffect12 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect12.isPlaying() && setValue)
			mpEffect12.start();
		}catch(Exception e){}
	}
	public static void sound13( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect13==null)
		{
				mpEffect13 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect13.isPlaying() && setValue)
			mpEffect13.start();
		}catch(Exception e){}
	}
	public static void sound14( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect14==null)
		{
				mpEffect14 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect14.isPlaying() && setValue)
			mpEffect14.start();
		}catch(Exception e){}
	}
	
	public static void sound15( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
		{
				mpEffect15 = MediaPlayer.create(HTTRenderer.mContext, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		}catch(Exception e){}
	}
	
	
	
	
	
	
	
}
