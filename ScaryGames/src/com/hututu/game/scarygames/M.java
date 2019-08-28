

package com.hututu.game.scarygames;

import java.util.Random;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	
	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 	= "market://details?id=";
//
//	public static final String MARKET 	= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	public static final String LINK 	= "samsungapps://ProductDetail/";
//	public static final String PUBLICLINK 	= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	
//	public static final String MARKET 	= "http://slideme.org/user/hututugames";
//	public static final String LINK 	= "market://details?id=";
//	public static final String PUBLICLINK 	= "http://slideme.org/user/hututugames";

//	public static final String  LINK = "http://www.amazon.com/gp/mas/dl/android?p=";
//	public static final String  MARKET = "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	public static final String PUBLICLINK 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
	
	
	public static final String Scary_Game_Setting = "Scary Game Setting";
	public static final String Share = "Share";
	public static final String Choose_Game = "Select Game";
	public static final String PLAY[] = {"Iron Balance","Zigsaw Puzzle","Illusion","Find 5 Mistakes"};
	public static final String Choose_Scary = "Choose Scary";
	public static final String ScaryName[] = {"Man 1","Man 2","Man 3","Man 4","Man 5","Man 6","Man 7","Man 8","Man 9","Man 10","Man 11","Man 12"};
	public static final String Time = "Time";
	public static final String TimeDetail[] = {"10 Second","15 Second","30 Second","45 Second","1 Minute","2 Minute","3 Minute","After Game Complete"};
	public static final int Timesec[]={10,15,30,45,60,120,180,600};
	
	public static final String Sound = "Sound";
	public static final String Scrreming[] = {"Screaming 1","Screaming 2","Screaming 3","Screaming 4","Screaming 5","Screaming 6"};
	
	public static final float Diff  	=-.40f;
	public static final float gapWidth 	= .50f;
	public static final float Dc		=-.5f;
	public static float SJump			= .10f;
	public static final float decMy		=0.05f;
	public static float xInc			= .02f;
	public static final float xIncSlow  = .02f;
	public static final float xIncNormal= .04f;
	public static final float xIncFast  = .06f;
	
	
	public static Random mRand = new Random();
	public static int GameScreen;
	public static final int GAMELOGO = 0 ,GAMESETTING = 1,GAMESKAL = 2,GAMEMENU = 3,GAMESOUND = 4,GAMEPLAY = 5,GAMESEL = 13,GAMEADD=50;
	public static final int GAMETIME = 6 ,GAMEHELP   = 7,GAMEOVER = 8,GAMEABOUT = 9,GAMEDIS = 10,GAMEWIN = 11,GAMEPAUSE = 12;
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=800;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/6482510985";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/7959244189";	//AdHouse
	public static final int ROW=12;
	public static final int COLUMN=10;
	public static int BG =0;

	
	public static void play(Context context, int resource) 
	{
		try{
			stop();
			if (setValue) 
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
//				sound2(context,R.drawable.b2);
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
			//else
				//sound3Play(context,R.drawable.b3);
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
					//if(resource!=2131099648)
						//mpEffect3.setLooping(true);
					mpEffect3.start();
				}
//				else
//					sound4(context,R.drawable.b4);
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
			//else
				//sound5(context,R.drawable.b5);
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
//		else
//			sound6(context,R.drawable.b6);
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
		//else
			///sound7(context,R.drawable.b7);
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
//		else
//			sound8(context,R.drawable.b8);
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
		}catch(Exception e){}
	}
	
	
	public static void playsound(Context context, int no)
	{
		switch (no) {
		case 0:
			sound1(context, R.drawable.scream1);
			break;
		case 1:
			sound2(context, R.drawable.scream2);
			break;
		case 2:
			sound3(context, R.drawable.scream3);
			break;
		case 3:
			sound4(context, R.drawable.scream4);
			break;
		case 4:
			sound5(context, R.drawable.scream5);
			break;
		case 5:
			sound6(context, R.drawable.scream6);
			break;
		
		}
	}
	
	
	
	
}
