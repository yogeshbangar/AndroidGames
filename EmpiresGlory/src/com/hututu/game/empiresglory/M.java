

package com.hututu.game.empiresglory;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	
	static final byte time = 5;
	public static final int ACHIV[] = {
		R.string.achievement_power_achiever,
		  R.string.achievement_striker,
		  R.string.achievement_empirer,
		  R.string.achievement_bow_player,
		  R.string.achievement_freezer,
		  R.string.achievement_earner,
		  R.string.achievement_sparkler,
		  R.string.achievement_senior_earner,
		  R.string.achievement_crystaler,
		  R.string.achievement_senior_crystaler,
		  R.string.achievement_master_crystaler,
		  R.string.achievement_master__earner,
		  R.string.achievement_gold_wall,
		  R.string.achievement_player,
		  R.string.achievement_platinum_wall,
		  R.string.achievement_senior_player,
		  R.string.achievement_king_of_tower,
		  R.string.achievement_bow_senior_player,
		  R.string.achievement_master__player,
		  R.string.achievement_defender,
		  R.string.achievement_senior_defender,
		  R.string.achievement_mater_defender,
		  R.string.achievement_force_master,
		  R.string.achievement_brio_master,
		  R.string.achievement_fire_master,
		  R.string.achievement_bow_master_player
	};
	
	
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.game.archeryking";
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	public static final int GAMELOGO = 0;
	public static final int GAMEMENU = 1;
	public static final int GAMEPLAY = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEABOUT = 4;
	public static final int GAMEWIN = 5;
	public static final int GAMECONG = 6;
	public static final int GAMEUPGR = 7;
	public static final int GAMESETI = 8;
	public static final int GAMEBUY = 9;
	public static final int GAMEPAUSE = 10;
	public static final int GAMESPLASH = 11;
	
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/4726576184";	//AdHouse
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854, mMaxY = 480;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	public  static boolean setMusic	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null,mpBG6 = null,mpBG7 = null,mpBG8 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/4726576184";	//AndroidMarket
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
		try{
			stop(6);
			if (setMusic) 
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
	public static void stop(int Number) {
		System.out.println("Stop    " +Number);
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
			
			if (mpBG7 != null)
			{
				mpBG7.stop();
				mpBG7.release();
				mpBG7 = null;
			}
			
			if (mpBG8 != null)
			{
				mpBG8.stop();
				mpBG8.release();
				mpBG8 = null;
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
					//if(resource!=2131099648)
						//mpEffect3.setLooping(true);
					mpEffect3.start();
				}
				else
					sound4(context,resource);
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
				sound5(context,resource);
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
		System.out.println("sound8");
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
	
	
	
	
	
	
	
	
	
	
	public static void sound16(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG1==null)
		{
				mpBG1 = MediaPlayer.create(context, resource);
		}
		if(!mpBG1.isPlaying() && setValue)
			mpBG1.start();
		}catch(Exception e){}
	}
	public static void sound17(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG2==null)
		{
				mpBG2 = MediaPlayer.create(context, resource);
		}
		if(!mpBG2.isPlaying() && setValue)
			mpBG2.start();
		}catch(Exception e){}
	}
	public static void sound18(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG3==null)
		{
				mpBG3 = MediaPlayer.create(context, resource);
		}
		if(!mpBG3.isPlaying() && setValue)
			mpBG3.start();
		}catch(Exception e){}
	}
	public static void sound19(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG4==null)
		{
				mpBG4 = MediaPlayer.create(context, resource);
		}
		if(!mpBG4.isPlaying() && setValue)
			mpBG4.start();
		}catch(Exception e){}
	}
	public static void sound20(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG5==null)
		{
				mpBG5 = MediaPlayer.create(context, resource);
		}
		if(!mpBG5.isPlaying() && setValue)
			mpBG5.start();
		else
			sound21(context,resource);
		}catch(Exception e){}
	}
	public static void sound21(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG6==null)
		{
				mpBG6 = MediaPlayer.create(context, resource);
		}
		if(!mpBG6.isPlaying() && setValue)
			mpBG6.start();
		else
			sound22(context,resource);
		}catch(Exception e){}
	}
	public static void sound22(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG7==null)
		{
				mpBG7 = MediaPlayer.create(context, resource);
		}
		if(!mpBG7.isPlaying() && setValue)
			mpBG7.start();
		else
			sound23(context,resource);
		}catch(Exception e){}
	}
	public static void sound23(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG8==null)
		{
				mpBG8 = MediaPlayer.create(context, resource);
		}
		if(!mpBG8.isPlaying() && setValue)
			mpBG8.start();
		}catch(Exception e){}
	}
	
	
	static void Wallhit(Context context){
		sound20(context, R.drawable.wall_hit);
	}
	
	
	static double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	
	static void playGame(Context context){
//		if(M.mRand.nextBoolean())
//			play(context, R.drawable.game_bg);
//		else
			play(context, R.drawable.boss);
	}
	
}
