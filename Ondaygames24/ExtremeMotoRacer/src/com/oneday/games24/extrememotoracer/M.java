package com.oneday.games24.extrememotoracer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	
	public static final String LEADERBOARD_ID = "CgkI4OGmqN8bEAIQAg";
	public static final String score = "score";
	public static int GameScreen;
	public static final int GameLogo = 0,GamePlay = 1,GameOver =2,GameCarSelection=3,GameSplash=4;
	public static final int GameADD=5,GameHelp=6,GameBulk = 7,GameMenu = 8,GamePause = 9,GameAbout =10;//GameSetting
	public static final int GameChallenge=11,GameSing = 12;
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 320,mMaxY=480;
	public static Random mRand = new Random();
	public  static boolean setValue	= true;
	public  static boolean setBG	= true;
	public  static boolean setsensor= true;
	public  static boolean SingIn = false;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null,mpEffect9=null,mpSplash= null,mpGameplay = null;
	private static MediaPlayer mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null;
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Onedaygame24";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.oneday.games24.extrememotoracer";
	public static final String FACEBOOK 	= "https://www.facebook.com/HututuGames";
	
	public static int LIFE = 5;
	public final static float SLOWSPEED		= .01f;
	public final static float NORMALSPEED	= .02f;
	public final static float FASTSPEED		= .03f;
	public static int BOOST	= 34;
	
//	public static final String MY_AD_UNIT_ID = "a14ee1fd03bb86e";	//slideme 
//	public static final String MY_AD_UNIT_ID = "a14ee1fd339624a";	//handster 
//	public static final String MY_AD_UNIT_ID = "a14ee1d6b86f1ac";	//getjar 
//	public static final String MY_AD_UNIT_ID = "a14ee1fcca4b3c5";	//appoke 
//	public static final String MY_AD_UNIT_ID = "a14ee1c3375ed09";	//andapponline
//	
//	public static final String MY_AD_UNIT_ID = "a14ee1fd6fb5507";	//opera
//	public static final String MY_AD_UNIT_ID = "a14ee1fc6d6d477";	//handango
	public static final String Package[] = {
		"com.oneday.games24.fightersofocean",
		"com.onedaygames24.pokernpoker",
		"com.onedaygames24.shoot2bottle",
		"com.oneday.games24.fightersofocean",
		"com.onedaygames24.pokernpoker",
		"com.onedaygames24.shoot2bottle",
		"com.oneday.games24.fightersofocean",
		"com.onedaygames24.pokernpoker",
		"com.onedaygames24.shoot2bottle",
		"com.oneday.games24.fightersofocean",
		"com.onedaygames24.pokernpoker",
		"com.onedaygames24.shoot2bottle",
		"com.oneday.games24.fightersofocean",
	};
	
	public static String Challenges[]={
		"Drive 2 min safely",
		"Drive 5 min safely",
		"Drive 7 min safely",
		"Drive 10 min safely",
		"Drive 15 min safely",
		"Drive 30 min safely",
		"Overtake 10 Cars",
		"Overtake 25 Cars",
		"Overtake 50 Cars",
		"Overtake 75 Cars",
		"Overtake 100 Cars",
		"Collect 10 Coins",
		"Collect 25 Coins",
		"Collect 50 Coins",
		"Collect 75 Coins",
		"Collect 100 Coins",
		"Collect 150 Coins",
		"Collect 200 Coins",
		"Collect 250 Coins",
		"Drive atleast 10 meter",
		"Drive atleast 25 meter",
		"Drive atleast 50 meter",
		"Drive atleast 75 meter",
		"Drive atleast 100 meter",
		"Drive atleast 150 meter",
		"Collect 10 Coins and Overtake 10 Cars",
		"Collect 25 Coins and Overtake 25 Cars",
		"Collect 50 Coins and Overtake 50 Cars",
		"Collect 75 Coins and Overtake 75 Cars",
		"Collect 100 Coins and Overtake 100 Cars",
		"Collect 150 Coins and Overtake 150 Cars",
		"Collect 10 Coins in 1 min",
		"Collect 25 Coins in 2 min",
		"Collect 50 Coins in 5 min",
		"Collect 75 Coins in 7 min",
		"Collect 100 Coins in 10 min",
		"Collect 150 Coins in 15 min",
		"Collect 200 Coins in 20 min",
		"Collect 250 Coins in 25 min",
		"Drive atleast 10 meter in 2 min",
		"Drive atleast 25 meter in 5 min",
		"Drive atleast 50 meter in 10 min",
		"Drive atleast 75 meter in 20 min",
		"Drive atleast 100 meter in 25 min",
		"Drive atleast 150 meter in 30 min",
		"Collect 10 Coins and Overtake 10 Cars in 2 min",
		"Collect 25 Coins and Overtake 25 Cars in 5 min",
		"Collect 50 Coins and Overtake 50 Cars in 10 min",
		"Collect 75 Coins and Overtake 75 Cars in 20 min",
		"Collect 100 Coins and Overtake 100 Cars in 30 min",
		
	};
	public static void loadSound(Context context)
	{
		try{
			if(mpSplash==null)
				mpSplash = MediaPlayer.create(context, R.drawable.splash);
//			if(mpGameplay==null)
//				mpGameplay = MediaPlayer.create(context, R.drawable.gameplay_bike);
//			
			if(mpEffect==null)
				mpEffect = MediaPlayer.create(context, R.drawable.button);//
			
			if(mpEffect2==null)
				mpEffect2 = MediaPlayer.create(context, R.drawable.crash);
			
//			if(mpEffect3==null)
//				mpEffect3 = MediaPlayer.create(context, R.drawable.crash);//
	
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, R.drawable.gameover);
			
			if(mpEffect5==null)
				mpEffect5 = MediaPlayer.create(context, R.drawable.levelwin);
			
			if(mpEffect6==null)
				mpEffect6 = MediaPlayer.create(context, R.drawable.coin);
			
			if(mpEffect7==null)
				mpEffect7 = MediaPlayer.create(context, R.drawable.coin2);
			
			if(mpEffect8==null)
				mpEffect8 = MediaPlayer.create(context, R.drawable.coin);
			
			if(mpEffect9==null)
				mpEffect9 = MediaPlayer.create(context, R.drawable.bike);
			
//			if(mpEffect10==null)
//				mpEffect10 = MediaPlayer.create(context, R.drawable.coin2);
//			if(mpEffect11==null)
//				mpEffect11 = MediaPlayer.create(context, R.drawable.coin2);
//			if(mpEffect12==null)
//				mpEffect12 = MediaPlayer.create(context, R.drawable.coin2);
//			if(mpEffect13==null)
//				mpEffect13 = MediaPlayer.create(context, R.drawable.coin2);
//			
			
			
			
		}catch(Exception e){}
	}
	public static void BGplay(Context context, int resource) 
	{
		try{
			stop();
			if (setBG) 
			{
				if(mpGameplay==null)
					mpGameplay= MediaPlayer.create(context, resource);
				
				if(!mpGameplay.isPlaying())
				{
					if(resource!=2131099648)
						mpGameplay.setLooping(true);
					mpGameplay.start();
				}
			}
		}catch(Exception e){}
	}
	public static void Splashplay(Context context, int resource) 
	{
		try{
			stop();
			if(setBG) 
			{
				if(mpSplash==null)
					mpSplash= MediaPlayer.create(context, resource);
				
				if(!mpSplash.isPlaying())
				{
					if(resource!=2131099648)
						mpSplash.setLooping(true);
					mpSplash.start();
				}
			}
		}catch(Exception e){}
	}
	public static void loopSoundStop() 
	{
		try{
			if (mpGameplay != null)
			{
				mpGameplay.pause();
				mpGameplay.setLooping(false);
				mpGameplay.release();
				mpGameplay = null;
				
			}
			if (mpSplash!= null)
			{
				mpSplash.pause();
				mpSplash.setLooping(false);
				mpSplash.release();
				mpSplash = null;
				
			}	
		}catch(Exception e){}
	}
	public static void stop() {
		try{
			if (mpGameplay != null)
			{
				mpGameplay.stop();
				mpGameplay.release();
				mpGameplay= null;
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
		}catch(Exception e){}	
	}
	public static void sound2(Context context, int resource){
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
		}catch(Exception e){}
	}
	public static void sound3(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect3==null)
			{
				mpEffect3 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect3.isPlaying() && setValue)
				mpEffect3.start();
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
		else
			sound7(context,resource);
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
		else
		    sound8(context, resource); 	
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
//		else
//			sound9(context,resource);
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
//		else
//			sound6(context,R.drawable.power);
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
//		else
//			sound6(context,R.drawable.power);
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
//		else
//			sound6(context,R.drawable.power);
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
//		else
//			sound6(context,R.drawable.power);
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
//		else
//			sound6(context,R.drawable.power);
		}catch(Exception e){}
	}
	
	public static void CallBost(Context context ,int no)
	{
		switch (no) {
		case 0:
			sound10(context, R.drawable.bike_boost);
			break;
		case 1:
			sound11(context, R.drawable.car_boost);
			break;
		case 2:
			sound12(context, R.drawable.car_boost);
			break;
		case 3:
		default:
			sound13(context, R.drawable.car_boost);
			break;
		}
	}
	public static void CallBostP(Context context ,int no)
	{
		switch (no) {
		case 0:
			if(mpEffect10!=null)
				if(mpEffect10.isPlaying())
					mpEffect10.pause();
			break;
		case 1:
			if(mpEffect11!=null)
				if(mpEffect11.isPlaying())
					mpEffect11.pause();
			break;
		case 2:
			if(mpEffect12!=null)
				if(mpEffect12.isPlaying())
					mpEffect12.pause();
			break;
		case 3:
		default:
			if(mpEffect13!=null)
				if(mpEffect13.isPlaying())
					mpEffect13.pause();
			break;
		}
	}
	
	
	public static void WriteSettings(Context context ,String Filename,String data)
	{
		FileOutputStream fOut = null;
		OutputStreamWriter osw = null;
		try{
			fOut = ((Activity)context).openFileOutput(Filename,0);      
			osw = new OutputStreamWriter(fOut);
			osw.write(data);
			osw.flush();
		}
		catch (Exception e) {      
			Log.d(e.toString()+"","EWEEEEEEEEEEEEEEEEEEEEEE");
		}
		finally {
			try {
				osw.close();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
   public static String ReadSettings(Context context ,String Filename)
   {
	   FileInputStream fIn = null;
	   byte[] inputBuffer = new byte[255];
	   String data = null;
	   try{
		   fIn = ((Activity)context).openFileInput(Filename);
		   if(fIn!=null)
		   {
			   	int i = fIn.available();
			   	fIn.read(inputBuffer);
			   	data = new String(inputBuffer,0,i);
		   }
	   }
	   catch (Exception e) {      
		   e.printStackTrace();
		   
	   }
	   finally {
		   try {
			   
			   	if(fIn!=null)
			   		fIn.close();
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
		   
	   }	
	   if(data==null)
	   {
		   return "0";
	   }
	   return data;
   }
}
