

package com.hututu.games.highwayracing;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	//meter-coin-bus-kill-dimond
	public static final int[][] LEVEL={
		{ 800, 0,0,0,0},
		{   0,20,0,0,0},
		{   0, 0,2,0,0},
		{1500, 0,0,0,0},
		{   0, 0,0,2,0},
		
		{0,  0,0,0,10},
		{0,  0,4,0, 0},
		{0,150,0,0, 0},
		{0,  0,0,5, 0},
		{0,  0,0,0,15},
		
		{   0,  0, 8,0,0},
		{6000,  0, 0,0,0},
		{   0,  0, 0,7,0},
		{   0,250, 0,0,0},
		{   0,  0,12,0,0},
		
		{ 9000,  0,0,0, 0},
		{    0,350,0,0, 0},
		{11000,  0,0,0, 0},
		{    0,  0,0,0,25},
		{    0,  0,0,9, 0},
		
		{12500,  0, 0, 0, 0},
		{    0,  0,18, 0, 0},
		{    0,460, 0, 0, 0},
		{    0,  0, 0,13, 0},
		{    0,  0, 0, 0,35},
		
		{    0,  0,21, 0,0},
		{15000,  0, 0, 0,0},
		{    0,  0, 0,16,0},
		{    0,600, 0, 0,0},
		{    0,  0,28, 0,0},
		
	};
	
	public static final String FACEBOOK	= "https://www.facebook.com/HututuGames";
	
//	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=Hututu+Games";
//	public static final String LINK 		= "market://details?id=";
//	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.game.archeryking";

//	public static final String MARKET 		= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	public static final String LINK 		= "samsungapps://ProductDetail/";
//	public static final String SHARELINK 	= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	
//	public static final String MARKET 		= "http://www.lgworld.com/";
//	public static final String LINK 		= "http://www.lgworld.com/";
//	public static final String SHARELINK 	= "http://www.lgworld.com/";

	public static final String  LINK 		= "http://www.amazon.com/gp/mas/dl/android?p=";
	public static final String  MARKET 		= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
	public static final String SHARELINK 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
	
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	
	public static final int GAMELOGO = 0;
	public static final int GAMEADD	= 1;
	public static final int GAMEMENU	= 2;
	public static final int GAMEPLAY	= 3;
	public static final int GAMEOVER	= 4;
	public static final int GAMELEVEL	= 5;
	public static final int GAMEWIN 	= 6;
	public static final int GAMESTART= 7;
	public static final int GAMEGO 	= 8;
	public static final int GAMESHOP	= 9;
	public static final int GAMEABOUT	= 10;
	public static final int GAMEJOIN	= 11;
	public static final int GAMEPAUSE	= 12;
	public static final int GAMEHELP	= 13;
	
	
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/2610714584";	//AdHouse
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=854;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	public  static boolean setBG	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null,mpEffect16 = null;
	private static MediaPlayer mpEffect17 = null,mpEffect18 = null,mpEffect19 = null,mpEffect20 = null;
	private static MediaPlayer mpEffect27 = null,mpEffect28 = null,mpEffect29 = null,mpEffect30 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/1133981389";	//AndroidMarket
	public static float Speed = -.01f;
	public static final float SSpeed = -.01f;
	public static void play(Context context, int resource) 
	{
		try{
			stop(context);
			if (setBG) 
			{
				if(mp==null)
					mp = MediaPlayer.create(context, resource);
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){System.out.println(setBG+"~~~~~~~~M.play~~1~~~~~~~~~~~~~"+e.toString());}
	}
	public static void playStop() 
	{
		try{
			if (mp != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mp.pause();
			}	
		}catch(Exception e){}
	}
	public static void stop(Context context) {
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
			if (mpEffect16 != null)
			{
				mpEffect16.stop();
				mpEffect16.release();
				mpEffect16 = null;
			}
			if (mpEffect17 != null)
			{
				mpEffect17.stop();
				mpEffect17.release();
				mpEffect17 = null;
			}
			if (mpEffect18 != null)
			{
				mpEffect18.stop();
				mpEffect18.release();
				mpEffect18 = null;
			}
			if (mpEffect19 != null)
			{
				mpEffect19.stop();
				mpEffect19.release();
				mpEffect19 = null;
			}
			if (mpEffect20 != null)
			{
				mpEffect20.stop();
				mpEffect20.release();
				mpEffect20 = null;
			}
			
			if (mpEffect27 != null)
			{
				mpEffect27.stop();
				mpEffect27.release();
				mpEffect27 = null;
			}
			if (mpEffect28 != null)
			{
				mpEffect28.stop();
				mpEffect28.release();
				mpEffect28 = null;
			}
			if (mpEffect29 != null)
			{
				mpEffect29.stop();
				mpEffect29.release();
				mpEffect29 = null;
			}
			if (mpEffect30 != null)
			{
				mpEffect30.stop();
				mpEffect30.release();
				mpEffect30 = null;
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
			if(!mpEffect.isPlaying())
				mpEffect.start();
//			else
//				sound2(context,R.drawable.g2);
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
//			else
//				sound3Play(context,R.drawable.g3);
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
				{
					mpEffect3.start();
				}
				else
					sound4(context,R.drawable.coin);
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
			else
				sound5(context,R.drawable.coin);
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
		else
			sound6(context,R.drawable.coin);
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
		else
			sound17(context,resource);
		}catch(Exception e){}
	}
	public static void sound7(Context context, int resource) {
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
	public static void sound8(Context context, int resource) {
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
	public static void sound9(Context context, int resource) {
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
	public static void sound10(Context context, int resource) {
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
	public static void sound11(Context context, int resource) {
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
	public static void sound12(Context context, int resource) {
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
	public static void sound13(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect13==null)
		{
				mpEffect13 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect13.isPlaying() && setValue)
			mpEffect13.start();
		}catch(Exception e){}
	}
	public static void sound14(Context context, int resource) {
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
	
	public static void sound15(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
		{
				mpEffect15 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		else
			sound16(context,resource);
		}catch(Exception e){}
	}
	public static void sound16(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect16==null)
		{
				mpEffect16 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect16.isPlaying() && setValue)
			mpEffect16.start();
		}catch(Exception e){}
	}
	
	public static void sound17(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect17==null)
		{
				mpEffect17 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect17.isPlaying() && setValue)
			mpEffect17.start();
		else
			sound18(context,resource);
		}catch(Exception e){}
	}
	public static void sound18(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect18==null)
		{
				mpEffect18 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect18.isPlaying() && setValue)
			mpEffect18.start();
		}catch(Exception e){}
	}
	
	
	public static void sound19(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect19==null)
		{
				mpEffect19 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect19.isPlaying() && setValue)
			mpEffect19.start();
		else
			sound20(context,resource);
		}catch(Exception e){}
	}
	public static void sound20(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect20==null)
		{
				mpEffect20 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect20.isPlaying() && setValue)
			mpEffect20.start();
		else
			sound27(context,resource);
		}catch(Exception e){}
	}

	
	
	public static void sound27(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect27==null)
		{
				mpEffect27 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect27.isPlaying() && setValue)
			mpEffect27.start();
		else
			sound28(context,resource);
		}catch(Exception e){}
	}
	public static void sound28(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect28==null)
		{
				mpEffect28 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect28.isPlaying() && setValue)
			mpEffect28.start();
		else
			sound29(context,resource);
		}catch(Exception e){}
	}
	public static void sound29(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect29==null)
		{
				mpEffect29 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect29.isPlaying() && setValue)
			mpEffect29.start();
		else
			sound30(context,resource);
		}catch(Exception e){}
	}
	public static void sound30(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect30==null)
		{
				mpEffect30 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect30.isPlaying() && setValue)
			mpEffect30.start();
		}catch(Exception e){}
	}
	
	
	
}