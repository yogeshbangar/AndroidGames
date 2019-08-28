

package com.hututu.game3d.crossyroad;

import java.util.Random;


import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	public static final float MIN = -2;
	public static final float MIN2 = -1.9f;
	public static final int LIGHT = 3;
	public static final int CAR = 20;
	public static final int WOOD = 30;
	public static final int TREE = 40;
	public static final int DROP = 10;
	public static final int CENT = 10;
	public static final int SLEEP = 5;
	public static final int TEAR = 10;
	public static final int FIRE = 20;
	public static final int PLAYER = 33;
	public static final int ACHIV[] = {
		
		R.string.achievement_baller,//",//1
		R.string.achievement_bhalu,//2
		R.string.achievement_boltman,//3
		R.string.achievement_boy,//4
		R.string.achievement_chicken,//5
		R.string.achievement_cow,//6
		R.string.achievement_crocodile,//7
		R.string.achievement_crying_boy,//8
		R.string.achievement_dog,//9
		R.string.achievement_dragon,//10
		R.string.achievement_elephant,//11
		R.string.achievement_frenkenstein,//12
		R.string.achievement_horse,//13
		R.string.achievement_girrafe,//14
		R.string.achievement_lion,//15
		R.string.achievement_mallard,//16
		R.string.achievement_man,//17
		R.string.achievement_meerkat,//18
		R.string.achievement_wizard,//19
		R.string.achievement_mad_bull,//20
		R.string.achievement_panda,//21
		R.string.achievement_pig,//22
		R.string.achievement_pigeon,//23
		R.string.achievement_pltypus,//24
		R.string.achievement_poppy,//25
		R.string.achievement_pug,//26
		R.string.achievement_scruffy,//27
		R.string.achievement_sleeping_man,//28
		R.string.achievement_speciman,//29
		R.string.achievement_ghost,//30
		R.string.achievement_turtle,//31
		R.string.achievement_vampire,//32
		R.string.achievement_zombie,//33
	};
	public static final String BUYID[] = 
	{
		"one",//Baller ()
		"two",//Bhalu ()
		"three",// Boltman ()
		"four",// Boy ()
		"five",// Chicken ()
		"six",// Cow ()
		"seven",// Crocodile ()
		"eight",// Crying Boy ()
		"nine",// Dog ()
		"ten",// Dragon ()
		"eleven",// Elephant ()
		"twelve",// Frenkenstein ()
		"thirteen",// Horse ()
		"fourteen",// Girrafe ()
		"fifteen",// Lion ()
		"sixteen",// Mallard ()
		"seventeen",// Man ()
		"eighteen",// Meerkat ()
		"nineteen",// Wizard ()
		"twenty",// Mad bull ()
		"twentyone",// Panda ()
		"twentytwo",// Pig ()
		"twentythree",// Pigeon ()
		"twentyfour",// Pltypus ()
		"twentyfive",// Poppy ()
		"twentysix",// Pug ()
		"twentyseven",// Scruffy ()
		"twentyeight",// Sleeping Man ()
		"twentynine",// Speciman ()
		"thirty",// Ghost ()
		"thirtyone",// Turtle ()
		"thirtytwo",// Vampire ()
		"thirtythree",// Zombie ()

	};
	public static final String NAME[] = 
	{
		"Baller",//1
		"Bhalu",//2
		"Boltman",//3
		"Boy",//4
		"Chicken",//5
		"Cow",//6
		"Crocodile",//7
		"Crying Boy",//8
		"Dog",//9
		"Dragon",//10
		"Elephant",//11
		"Frenkenstein",//12
		"Horse",//13
		"Girrafe",//14
		"Lion",//15
		"Mallard",//16
		"Man",//17
		"Meerkat",//18
		"Wizard",//19
		"Mad bull",//20
		"Panda",//21
		"Pig",//22
		"Pigeon",//23
		"Pltypus",//24
		"Poppy",//25
		"Pug",//26
		"Scruffy",//27
		"Sleeping Man",//28
		"Speciman",//29
		"Ghost",//30
		"Turtle",//31
		"Vampire",//32
		"Zombie",//33
	};
	//Unlock achievement.
	public static final int ANIMAL[] = {
		  R.drawable.baller,
		  R.drawable.bear,
		  R.drawable.boltman,
		  R.drawable.boy,
		  R.drawable.chicken,
		  R.drawable.cow,
		  R.drawable.crocodile,
		  R.drawable.crying,
		  R.drawable.dog,
		  R.drawable.dragon,
		  R.drawable.elephant,
		  R.drawable.frenkenstein,
		  R.drawable.horse,
		  R.drawable.girrafe,
		  R.drawable.lion,
		  R.drawable.mallard,
		  R.drawable.man,
		  R.drawable.meerkat,
		  R.drawable.wizard,
		  R.drawable.bull,
		  R.drawable.panda,
		  R.drawable.pig,
		  R.drawable.pigeon,
		  R.drawable.pltypus,
		  R.drawable.poppybird,
		  R.drawable.dog,
		  R.drawable.dog,
		  R.drawable.sleeping_man,
		  R.drawable.speciman,
		  R.drawable.ghost,
		  R.drawable.dog,
		  R.drawable.vampire0,
		  R.drawable.zombie,
	};
	
	public static final String MARKET 	= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
	public static Random mRand = new Random();
	public static final String score = "score";
	public static byte GameScreen;
	
	public static final byte GAMELOGO = 0;
	public static final byte GAMEMENU = 1;
	public static final byte GAMEPLAY = 2;
	public static final byte GAMEOVER = 3;
	public static final byte GAMENEWCHAR = 4;
	public static final byte GAMEPAUSE = 5;
	public static final byte GAMESHOP = 6;
	public static final byte GAMEGIFT = 7;
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
		System.out.println("sound12");
//		resource = R.drawable.coincollect;
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
//		resource = R.drawable.tab;
		System.out.println("sound14");
		if(!setValue)
			 return;
		try{
			if(mpEffect14==null)
		{
				mpEffect14 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpEffect14.isPlaying() && setValue)
			mpEffect14.start();
		else{
			sound15(resource);
		}
		}catch(Exception e){}
	}
	
	public static void sound15( int resource) {
		System.out.println("sound15");
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
		{
				mpEffect15 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		else{
			sound17(resource);
		}
		}catch(Exception e){}
	}
	
	
	public static void sound16( int resource) {
		if(!setValue)
			 return;
		try{
			if(mpBG1==null)
		{
				mpBG1 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpBG1.isPlaying() && setValue)
			mpBG1.start();
		}catch(Exception e){}
	}
	public static void sound17( int resource) {
		System.out.println("sound17");
		if(!setValue)
			 return;
		try{
			if(mpBG2==null)
		{
				mpBG2 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpBG2.isPlaying() && setValue)
			mpBG2.start();
		else{
			sound18(resource);
		}
		}catch(Exception e){}
	}
	public static void sound18( int resource) {
		System.out.println("sound18");
		if(!setValue)
			 return;
		try{
			if(mpBG3==null)
		{
				mpBG3 = MediaPlayer.create(Start.mContext, resource);
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
				mpBG4 = MediaPlayer.create(Start.mContext, resource);
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
				mpBG5 = MediaPlayer.create(Start.mContext, resource);
		}
		if(!mpBG5.isPlaying() && setValue)
			mpBG5.start();
		}catch(Exception e){}
	}
	public static void animalStop(){
		if (mpEffect != null)
		{
			mpEffect.stop();
			mpEffect.release();
			mpEffect = null;
		}
	}
	
	
}
