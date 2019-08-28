

package com.hututu.dont.tap.white.tile.piano.donttap;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M {
	
	public static final int ACHIV[]={
		  R.string.achievement_piano_master,
		  R.string.achievement_piano_super_player,
		  R.string.achievement_king_piano_master,
		  R.string.achievement_time_piano_master,
		  R.string.achievement_super_time_piano_master
	};
	
	public static final int Score[][] = {
			{ R.string.leaderboard_classic_25, R.string.leaderboard_classic_50, R.string.leaderboard_classic_100, },
			{ R.string.leaderboard_arcade_normal, R.string.leaderboard_arcade_fast, R.string.leaderboard_arcade_reverse },
			{ R.string.leaderboard_time_travel_15_sec, R.string.leaderboard_time_travel_30__sec, R.string.leaderboard_time_travel_60_sec },
			{ R.string.leaderboard_time_travel_15_sec, R.string.leaderboard_time_travel_30__sec, R.string.leaderboard_time_travel_60_sec },
			{ R.string.leaderboard_inside_bomb, R.string.leaderboard_inside_blinking, R.string.leaderboard_inside_double_row,
					R.string.leaderboard_inside_double_tap, R.string.leaderboard_inside_hide__seek, }
	};
	
	
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int GRAY = 3;//Touch
	public static final int RED = 4; //Wrong
	public static final int LBLACK = 5;//Out Screen
	public static final int BOMB = 6;
	public static final int BOMB_RED = 7; //Wrong
	public static final int DOUBL_ROW = 8; //ROW
	public static final int DOUBL_TAP = 9; //TAP
	public static final int DOUBL_HIDE = 10; //Hide
	public static final int CORECT = 20;
//	public static final int GAMEMORE = 8;
//	public static final int GAMEABOUT = 9;
	
	
	
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.game.archeryking";
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
	public static final int GAMEMENU = 2;
	public static final int GAMEPLAY = 3;
	public static final int GAMEWIN = 4;
	public static final int GAMEOVER = 5;
	public static final int GAMELEVEL = 6;
	public static final int GINSIDE = 7;
	public static final int GAMEMORE = 8;
	public static final int GAMEABOUT = 9;
	
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=800;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null,mpEffect16 = null;
	public static void play(Context context, int resource) 
	{
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
	public static void stop(Context context) {
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
			
			if (mpEffect16 != null)
			{
				mpEffect16.stop();
				mpEffect16.release();
				mpEffect16 = null;
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
	
	public static void Over(Context context, int resource) {
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
	//////////////////////////////////////////////////////////////
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
			if(mpBG2==null)
		{
				mpBG2 = MediaPlayer.create(context, resource);
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
				mpBG4 = MediaPlayer.create(context, resource);
		}
		if(!mpBG5.isPlaying() && setValue)
			mpBG5.start();
		}catch(Exception e){}
	}
	static int count = 0;
	
	public static void playAll(Context context){
		switch (count%19) {
		case 0:
			M.sound1(context, R.drawable.p17);
			break;
		case 1:
			M.sound2(context, R.drawable.p1);
			break;
		case 2:
			M.sound3(context, R.drawable.p2);
			break;
		case 3:
			M.sound4(context, R.drawable.p3);
			break;
		case 4:
			M.sound5(context, R.drawable.p4);
			break;
		case 5:
			M.sound6(context, R.drawable.p5);
			break;
		case 6:
			M.sound7(context, R.drawable.p6);
			break;
		case 7:
			M.sound8(context, R.drawable.p7);
			break;
		case 8:
			M.sound9(context, R.drawable.p8);
			break;
		case 9:
			M.sound10(context, R.drawable.p9);
			break;
		case 10:
			M.sound11(context, R.drawable.p10);
			break;
		case 11:
			M.sound12(context, R.drawable.p11);
			break;
		case 12:
			M.sound13(context, R.drawable.p12);
			break;
		case 13:
			M.sound14(context, R.drawable.p13);
			break;
		case 14:
			M.sound15(context, R.drawable.p14);
			break;
		default:
			M.sound16(context, R.drawable.p15);
			break;
		case 15:
			M.sound17(context, R.drawable.p18);
			break;
		case 16:
			M.sound19(context, R.drawable.p20);
			break;
		case 17:
			M.sound19(context, R.drawable.p23);
			break;
		}
		count++;
	}
	
	
	
	
}
