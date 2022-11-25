

package com.oneday.games24.clashofbattles;

import java.util.Random;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	public static final int ACHIV[] = {
		  R.string.achievement_classic_gunner,
		  R.string.achievement_sharp_gunner,
		  R.string.achievement_exalted_gunner,
		  R.string.achievement_master_runner,
		  R.string.achievement_king_runner
	};
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
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
	public static final int GAMELOGO = 0;
	public static final int GAMEMENU = 1;
	public static final int GAMEPLAY = 2;
	public static final int GAMESHOP = 3;
	public static final int GAMEABUT = 4;
	public static final int GAMEOPT 	= 5;
	public static final int GAMEOVER 	= 6;
	public static final int GAMECONG 	= 7;
	public static final int GAMEBUY 	= 8;
	public static final int GAMEPAUS 	= 9;
	
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/4726576184";	//AdHouse
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	public  static boolean setMusic	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null,mpBG6 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/4726576184";	//AndroidMarket
	public static void pause(){
		if(mp!=null){
			mp.pause();
		}
	}
	public static void play( int resource) 
	{
		try{
			stop();
			if (setMusic) 
			{
				if(mp==null)
					mp = MediaPlayer.create(GameRenderer.mContext, resource);
				
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){}
	}
	@SuppressLint("LongLogTag")
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
			if (mpBG6 != null)
			{
				mpBG6.stop();
				mpBG6.release();
				mpBG6 = null;
			}
			
		}catch(Exception e){}
	}
	public static void sound1( int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if(!mpEffect.isPlaying())
				mpEffect.start();
		}catch(Exception e){}	
	}
	public static void sound2( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
			else
				sound3(resource);
		}catch(Exception e){}
	}
	public static void sound3( int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(GameRenderer.mContext, resource);
				if(!mpEffect3.isPlaying())
				{
					mpEffect3.start();
				}
				else
					sound4(resource);
			}
		}catch(Exception e){}
	}
	public static void sound4( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
			
		}catch(Exception e){}
	}
	public static void sound5( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
		{
			mpEffect5 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpEffect5.isPlaying() && setValue)
			mpEffect5.start();
		else
			sound6(resource);
		}catch(Exception e){}
	}
	public static void sound6( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
		{
				mpEffect6 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpEffect6.isPlaying() && setValue)
			mpEffect6.start();
		else
			sound7(resource);
		}catch(Exception e){}
	}
	public static void sound7( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
		{
				mpEffect7 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpEffect7.isPlaying() && setValue)
			mpEffect7.start();
		else
			sound8(resource);
		}catch(Exception e){}
	}
	public static void sound8( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
		{
				mpEffect8 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect9 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect10 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect11 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect12 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect13 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect14 = MediaPlayer.create(GameRenderer.mContext, resource);
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
				mpEffect15 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		}catch(Exception e){}
	}
	
	
	
	
	public static void sound16( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG1==null)
		{
				mpBG1 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG1.isPlaying() && setValue)
			mpBG1.start();
		}catch(Exception e){}
	}
	public static void sound17( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG2==null)
		{
				mpBG2 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG2.isPlaying() && setValue)
			mpBG2.start();
		}catch(Exception e){}
	}
	public static void sound18( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG3==null)
		{
				mpBG3 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG3.isPlaying() && setValue)
			mpBG3.start();
		}catch(Exception e){}
	}
	public static void sound19( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG4==null)
		{
				mpBG4 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG4.isPlaying() && setValue)
			mpBG4.start();
		}catch(Exception e){}
	}
	public static void sound20( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG5==null)
		{
				mpBG5 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG5.isPlaying() && setValue)
			mpBG5.start();
		}catch(Exception e){}
	}
	public static void sound21( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG6==null)
		{
				mpBG6 = MediaPlayer.create(GameRenderer.mContext, resource);
		}
		if(!mpBG6.isPlaying() && setValue)
			mpBG6.start();
		}catch(Exception e){}
	}
	
	static void BlastAir(){
		sound2(R.raw.blast_air);
	}
	static void BlastDusman(){
		sound5(R.raw.blast0);
	}
	
	static void Bullate(byte no) {
		if (no == 0) {
			sound14(R.raw.shoot01);
		}
		if (no == 1) {
			sound15(R.raw.shoot02);
		}
		if (no == 2) {
			sound16(R.raw.shoot03);
		}
	}
	
	
	
}
