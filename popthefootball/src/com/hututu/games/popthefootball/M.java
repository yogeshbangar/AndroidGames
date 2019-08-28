

package com.hututu.games.popthefootball;

import java.util.Random;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	public  static boolean setValue	= true;
	public  static boolean sBGValue	= true;
	
	public static boolean[][] ACHIVE={
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
		{ false,false,false,false},
	};
	
	public static boolean[] HELPSCR={false,false,false,false,false,false,false,false,false,false,false,false,false};
	public static final byte[] UNLOCK={0,0,0,0,10,15,20,25,30,35,40,45,50};
	
	
	public static int GameScreen;
	public static final int GAMELOGO 		= 0;
	public static final int GAMEONE		= 1;
	public static final int GAMETWO		= 2;
	public static final int GAMETREE		= 3;
	public static final int GAMEFOUR		= 4;
	public static final int GAMEFIVE		= 5;
	public static final int GAMESIX		= 6;
//	public static final int GAMESEVEN		= 7;
//	public static final int GAMEEIGHT		= 8;
	public static final int GAMENINE		= 7;
	public static final int GAMETEN		= 8;
	public static final int GAMEELEVEN	= 9;
	public static final int GAMETWELVE	= 10;
	public static final int GAMETHIRTEEN	= 11;
	public static final int GAMESELECT	= 14;
	public static final int GAMEMAIN		= 15;
	public static final int GAMEBUY		= 16;
	public static final int GAMEACHIV		= 17;
	public static final int GAMESETTING	= 18;
	public static final int GAMEABOUT		= 19;
	public static final int GAMELVLCOMP	= 20;
	public static final int GAMELVLPUSE	= 21;
	public static final int GAMEHELP		= 22;
//	public static final int GAMEFREE		= 23;
	public static final int GAMEADV		= 24;
//	public static final int GAMELOAD		= 25;
	
	public static final int[][] SCORE={
		{ 20, 40, 70,100,150},//1
		{ 50,100,150,200,250},//2
		{ 50, 80,120,150,200},//3
		{100,150,220,300,400},//4
		{ 20, 30, 50, 75, 80},//5
		{ 20, 30, 40, 60, 80},//6
//		{ 50,100,200,300,400},//7
//		{ 20, 40, 60,100,150},//8
		{ 20, 40, 60,100,150},//9
		{ 50,100,150,200,250},//10
		{ 75,150,200,250,350},//11
		{ 50,100,150,200,250},//12
		{ 20, 30, 40, 60, 80},//13
	};
	
	public static final int[][] ACHIVE_VALUE={
		{ 100,1000,170,20}, //5Sec				1
		{1000,5000,300,200},//Without Falling	2
		{ 500,2000,250,20}, //5Sec				3
		{1000,5000,450,20},//less go moon		4
		{ 300,1000,100,8},  //5Sec				5
		{ 500,2000,100,5},  //5Sec				6
		//{1000,5000,500,10}, //build 8 room in each
		//{ 300,1500,170,9},  //5sec				8
		{1000,3000,170,100},//without falling	9
		{1000,5000,250,100},//no hitting obstacle
		{1000,5000,400,42}, //5Sec				11
		{1000,3000,300,50}, //no dropping in woter
		{1000,2000,100,30}, //no hitting Ground	13
	};
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	
//	public static final String Package[] = {
//		"com.hututu.game.speeddragracing",
//		"com.hututu.games.fightersofocean",
//		"com.hututu.game.helicoptercontrol",
//		"com.hututu.game.extrememotoracer",
//		"com.hututu.game.parkingchamp",
//		"com.hututu.game.penaltyshootout",
//		"com.hututu.game.santafreerunner",
//		"com.hututu.game.trafficracing",
//	};
	
	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=hututu+games+software+pvt+ltd";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.game.archeryking";
	public static final String FACEBOOK 	= "https://www.facebook.com/HututuGames";
	public static final String GOOGLE 	= "https://plus.google.com/+Hututugames";
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
	public static final String INTERSTITIAL_ID = "ca-app-pub-3395412980708319/2981731786";	//AdHouse
	private static MediaPlayer mp 	= null;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	
	public static Random mRand = new Random();
	
	public static void play(Context context, int resource) 
	{
		try{
			stop();
			if (sBGValue) 
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
		}catch(Exception e){}
	}
	public static void playStop() 
	{
		try{
			if (mp != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mp.pause();
				mp.stop();
				mp.release();
				mp = null;
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
			
		}catch(Exception e){}
	}

	public static void sound1(Context context, int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect == null) 
				mpEffect = MediaPlayer.create(context, resource);
			if (!mpEffect.isPlaying())
				mpEffect.start();
			else {
				if (mpEffect2 == null)
					mpEffect2 = MediaPlayer.create(context, resource);
				if (!mpEffect2.isPlaying())
					mpEffect2.start();
				else {
					if (mpEffect3 == null)
						mpEffect3 = MediaPlayer.create(context, resource);
					if (!mpEffect3.isPlaying())
						mpEffect3.start();
					else {
						if (mpEffect4 == null)
							mpEffect4 = MediaPlayer.create(context, resource);
						if (!mpEffect4.isPlaying() && setValue)
							mpEffect4.start();
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	public static void sound2(Context context, int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect5 == null)
				mpEffect5 = MediaPlayer.create(context, resource);
			if (!mpEffect5.isPlaying() && setValue)
				mpEffect5.start();
			else {
				if (mpEffect6 == null)
					mpEffect6 = MediaPlayer.create(context, resource);
				if (!mpEffect6.isPlaying() && setValue)
					mpEffect6.start();
				else {
					if (mpEffect7 == null)
						mpEffect7 = MediaPlayer.create(context, resource);
					if (!mpEffect7.isPlaying() && setValue)
						mpEffect7.start();
					else {
						if (mpEffect8 == null) 
							mpEffect8 = MediaPlayer.create(context, resource);
						if (!mpEffect8.isPlaying() && setValue)
							mpEffect8.start();
					}
				}
			}
		} catch (Exception e) {
		}
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
	
	public static void soundBotton(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
		{
				mpEffect15 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		}catch(Exception e){}
	}
	
	
	
	static float world2screenX(float a)
	{
		float c = ((a+1)*.5f)*M.ScreenWidth;
		return c;
	}
	static float world2screenY(float a)
	{
		float c = ((1-a)*.5f)*M.ScreenHieght;
		return c;
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
	
	
	
	
}
