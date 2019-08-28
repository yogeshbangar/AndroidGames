



package com.hututu.games.crazydragracing;
import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
//WIN,
public class M 
{
	
	
	public static final String WIN 			= "YOU WON";
	public static final String LOST 		= "YOU LOST";
	
	public static final String MAXSPEED		= "MAX SPEED\\";
	public static final String PERFECT 		= "PERFECT DRAG\\";
	public static final String MPH060 		= "0[60 MPH\\";
	public static final String MPH0100 		= "0[100 MPH\\";
	
	public static final String RACEBONUS	= "RACE BONUS\\";
	public static final String LAUNCEBONUS	= "LAUNCE BONUS\\";
	public static final String GOODBONUS	= "GOOD DRAG\\";
	public static final String RACEPRICE	= "RACE PRICE\\";
	public static final String TOTAL		= "TOTAL\\";
	public static final String HINT 		= "HINT";
	public static final String BONUS 		= "BONUS";
	
	
	public static final String SHARE 		= "SHARE";
	public static final String LEVCROSS 	= "Cross previous level first.";
	public static final String PHSCROSS 	= "Cross previous Phase first.";
	public static final String PHASE 		= "PHASE ";
	public static final String RATEUS 	= "RATE US";
	public static final String MORE 		= "MORE";
	public static final String LEVEL 		= "LEVEL";
	public static final String RACE 		= "RACE";
	public static final String BACK 		= "BACK";
	public static final String CALT 		= "COMPLETE ALL LEVEL TO";
	public static final String UNF 			= "UNLOCK NEXT PHASE";
	
	public static final String FULLY		= "FULLY";
	public static final String UPGRADED		= "UPGRADED";
	public static final String NEXT 		= "NEXT";
	public static final String RETRY 		= "RETRY";
	public static final String COIN 		= "COIN\\";
	public static final String UPRICE 	= "UPGRADE PRICE";
	public static final String UPGRADE	= "UPGRADE";
	public static final String BUYMORE	= "BUY COINS";
	public static final String TITLE		= "FEATURES";
	public static final String FEATURES[]	= {"ACCELERATION\\",
												"TOP SPEED\\",
												"HANDLING\\",
												"STRENGTH\\",
												"BOOST\\",
												"TYRE\\"};
	public static final String MENU[]	= {"RACE",
											"SHOP",
											"HELP",
											"ABOUT",
											"SOUND"};
	public static final String DONTCOIN	= "You Don't have sufficient coin.";
	public static final String PHASEM	= "You Cleared PHASE";
	
	public static final int CON = 9999;
	public static final int NO3=200;
	
	
	
	
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "market://details?id=com.hututu.games.crazydragracing";
	public static final String STUNT 		= "market://details?id=com.hututu.app.facechanger";
	public static final String COPTER 		= "market://details?id=com.hututu.game.crazyboatracing";
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
	public static final int GAMELOGO	= 0;
	public static final int GAMESHOP	= 1;
	public static final int GAMEMENU	= 2;
	public static final int GAMEHIGH	= 3;
	public static final int GAMEPLAY	= 4;
	public static final int GAMEADD	= 5;
	public static final int GAMEHELP	= 6;
	public static final int GAMEOVER	= 7;
	public static final int GAMEABUT	= 8;
	public static final int GAMELEVEL	= 9;
	public static final int GAMEWIN	= 10;
	public static final int GAMEJOIN	= 11;
	public static final int GAMEBUY	= 12;
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/2824367387";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/2482850980";	//AdHouse
	public static void loadSound(Context context)
	{
		try{
//			if(mp==null)
//				mp = MediaPlayer.create(context, R.drawable.bgsound);//
			
//			if(mpEffect==null)
//				mpEffect = MediaPlayer.create(context, R.drawable.g1);//
//			
//			if(mpEffect2==null)
//				mpEffect2 = MediaPlayer.create(context, R.drawable.g2);
//			
//			if(mpEffect3==null)
//				mpEffect3 = MediaPlayer.create(context, R.drawable.g3);//
//	
			
			
			
		}catch(Exception e){}
	}
	public static void play(Context context, int resource) 
	{
//		System.out.println("!~~~~~~~~~~~~~~~~~~~play~~~~~~~~~~~~~~~~~~~"+resource);
		try{
			stop(context);
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
//			System.out.println("00~~~~~~~~~~~~~~~~~~~play~~~~~~~~~~~~~~~~~~~"+resource);
		}catch(Exception e){}
	}
	public static void loopstop()
	{
//		System.out.println("!~~~~~~~~~~~~~~~~~~~Stop~~~~~~~~~~~~~~~~~~~");
		if (mp != null)
		{
			mp.setLooping(false);
			mp.stop();
			mp.release();
			mp = null;
		}
	}
	public static void stop(Context context) {
		try{
			loopstop();
			if (mp != null)
			{
				mp.setLooping(false);
				mp.stop();
				mp.release();
				mp = null;
			}
//			System.out.println("!~~~~~~~~~~~~~~~~~~~stop~~~~~~~~~~~~~~~~~~~");
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
//		System.out.println("!~~~~~~~~~~~~~~~~~~~sound1~~~~~~~~~~~~~~~~~~~"+resource);
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
//		System.out.println("!~~~~~~~~~~~~~~~~~~~sound2~~~~~~~~~~~~~~~~~~~"+resource);
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
	public static void sound3Play(Context context, int resource) {
//		System.out.println("!~~~~~~~~~~~~~~~~~~~sound3~~~~~~~~~~~~~~~~~~~"+resource);
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
//					sound4(context,R.drawable.g4);
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
//				System.out.println("======================================loopStop========================================");
				mpEffect3.pause();
			}	
		}catch(Exception e){}
	}
	public static void sound4(Context context, int resource) {
//		System.out.println("!~~~~~~~~~~~~~~~~~~~sound4~~~~~~~~~~~~~~~~~~~"+resource);
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
//		System.out.println("!~~~~~~~~~~~~~~~~~~~sound5~~~~~~~~~~~~~~~~~~~"+resource);
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
	
	
	
	
	
	
	
}
