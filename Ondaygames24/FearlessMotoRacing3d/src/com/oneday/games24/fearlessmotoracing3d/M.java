

package com.oneday.games24.fearlessmotoracing3d;

import java.util.Random;


import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	public static final int ACHIV[] = {
		  R.string.achievement_classic_racer,
		  R.string.achievement_sharp_racer,
		  R.string.achievement_exalted_racer,
		  R.string.achievement_master_racer,
		  R.string.achievement_royal_racer
	};
	public static final int BIKEPRICE[] = {0,100000,500000};
	public static final short Challenges[][] =
		{
			//Meter,Car,Coins,Min,Points
			{   0,  0,100, 0,200},
			{   0,  0,  0, 1,200},
			{   0, 10,  0, 0,200},
			{1000,  0,  0, 0,200},
			{   0, 10, 10, 0,200},
			{   0,  0, 10, 1,200},
			{1000,  0,  0, 2,200},
			{   0, 10, 10, 2,200},
			{   0,  0,  0, 5,300},
			{   0,  0, 20, 0,300},
			{   0, 20,  0, 0,300},
			{2000,  0,  0, 0,300},
			{   0, 20, 20, 0,300},
			{   0,  0, 20, 2,300},
			{2000,  0,  0, 5,300},
			{   0, 20, 20, 5,300},
			{   0,  0,  0, 7,400},
			{   0,  0, 30, 0,400},
			{   0, 30,  0, 0,400},
			{3000,  0,  0, 0,400},
			{   0, 30, 30, 0,400},
			{   0,  0, 30, 3,400},
			{3000,  0,  0, 7,400},
			{   0, 30, 30, 7,400},
			{   0,  0,  0, 9,500},
			{   0,  0, 40, 0,500},
			{   0, 40,  0, 0,500},
			{4000,  0,  0, 0,500},
			{   0, 40, 40, 0,500},
			{   0,  0, 40, 4,500},
			{4000,  0,  0, 9,500},
			{   0, 40, 40, 9,500},
			{   0,  0,  0,11,600},
			{   0,  0, 50, 0,600},
			{   0, 50,  0, 0,600},
			{5000,  0,  0, 0,600},
			{   0, 50, 50, 0,600},
			{   0,  0, 50, 5,600},
			{5000,  0,  0,11,600},
			{   0, 50, 50,11,600},
			{   0,  0,  0,13,700},
			{   0,  0, 60, 0,700},
			{   0, 60,  0, 0,700},
			{6000,  0,  0, 0,700},
			{   0, 60, 60, 0,700},
			{   0,  0, 60, 6,700},
			{6000,  0,  0,13,700},
			{   0, 60, 60,13,700},
			{   0, 80, 80,15,800},
			{   0,100,100,20,1000}
		};
	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=Onedaygame24";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
	public static Random mRand = new Random();
	public static final String score = "score";
	public static byte GameScreen;
	
	public static final byte GAMELOGO = 0;
	public static final byte GAMEMENU = 1;
	public static final byte GAMEPLAY = 2;
	public static final byte GAMEOVER = 3;
	public static final byte GAMEABOUT = 4;
	public static final byte GAMEPAUSE = 5;
	public static final byte GAMESHOP = 6;
	public static final byte GAMEHELP = 7;
	public static final byte GAMESPLASH = 8;
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	public  static boolean setMusic	= true;
	public  static boolean setSenser	= true;
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
					mp = MediaPlayer.create(Start.mContext, resource);
				
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){}
	}
	public static void stop() {
		try{
			Log.d("---------------------------------------------","sound stop");
			if (mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
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
				mpEffect = MediaPlayer.create(Start.mContext, resource);
			}
			if(!mpEffect.isPlaying())
				mpEffect.start();
//			else
//				sound2(CallActivity.mContext,R.drawable.g2);
		}catch(Exception e){}	
	}
	public static void sound2( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(Start.mContext, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
//			else
//				sound3Play(CallActivity.mContext,R.drawable.g3);
		}catch(Exception e){}
	}
	public static void sound3( int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(Start.mContext, resource);
				if(!mpEffect3.isPlaying())
				{
					//if(resource!=2131099648)
						//mpEffect3.setLooping(true);
					mpEffect3.start();
				}
				else
					sound4(R.drawable.coin_k);
			}
		}catch(Exception e){}
	}
	public static void sound4( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(Start.mContext, resource);
			}
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
			else
				sound5(R.drawable.coin_k);
		}catch(Exception e){}
	}
	public static void sound5( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
		{
			mpEffect5 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpEffect5.isPlaying() && setValue)
			mpEffect5.start();
	
		}catch(Exception e){}
	}
	public static void sound6( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
		{
				mpEffect6 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpEffect6.isPlaying() && setValue)
			mpEffect6.start();
		//else
			///sound7(CallActivity.mContext,R.drawable.b7);
		}catch(Exception e){}
	}
	public static void sound7( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
		{
				mpEffect7 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect8 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect9 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect10 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect11 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect12 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect13 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect14 = MediaPlayer.create(Start.mContext, resource);
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
				mpEffect15 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		}catch(Exception e){}
	}
	
	public static void Bikepause(){
		if (mpEffect != null) {
			if (mpEffect.isPlaying())
				mpEffect.pause();
		}
		if (mpEffect2 != null) {
			if (mpEffect2.isPlaying())
				mpEffect2.pause();
		}
	}
	
	public static void Bikeplay(int bike, boolean isBoost) {
		if (isBoost) {
			if (mpEffect != null) {
				if (mpEffect.isPlaying())
					mpEffect.pause();
			}
			switch (bike) {
			case 0:
				M.sound2(R.drawable.bike0_boost);
				break;
			case 1:
				M.sound2(R.drawable.bike1_boost);
				break;
			case 2:
				M.sound2(R.drawable.bike2_boost);
				break;
			}
		} else {
			if (mpEffect2 != null) {
				if (mpEffect2.isPlaying())
					mpEffect2.pause();
			}
			switch (bike) {
			case 0:
				M.sound1(R.drawable.bike0_normal);
				break;
			case 1:
				M.sound1(R.drawable.bike1_normal);
				break;
			case 2:
				M.sound1(R.drawable.bike2_normal);
				break;
			}
		}
	}
	
	
	
}