package com.oneday.games24.extrememotoracer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
//import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


//"25:47:52:55:03:6B:ED:27:CD:5A:6F:8B:09:48:92:C5:4B:73:01:83"
//"953298825440"

public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int counter,scounter = 0;
	float delx=0,dely=0;
	int totalC = 7;
	float moveY = -.6f,vy=-.1f;
	float sx,sy;
	public void setting1()
	{
		float ud=.01f;
		switch (GameRenderer.mStart._keyCode) {
		case 1:sy-=ud;break;
		case 2:sy+=ud;break;
		case 3:sx-=ud;break;
		case 4:sx+=ud;break;
		}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);
	}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	//@Override
	public void draw(GL10 gl) 
	{
//		setting1();
		counter++;
		switch (M.GameScreen) {
		case M.GameLogo:
			drawTexRS(gl, mGR.mTex_Logo, .667f, .6f,0,0,0);
			if(counter>60)
			{
				M.GameScreen = M.GameADD;
			}
//			drawPlayerCrash(gl);
			break;
		case M.GameADD:
			drawTex(gl, mGR.mTex_TopGame, 0, 0);
			drawTex(gl, mGR.mTex_Continue, 0, -.8f);
			break;
		case M.GameSplash:
//			drawTex(gl, mGR.mTex_AllBG, 0, 0);
			
			drawTexRS(gl, mGR.mTex_AllBG, .667f, .6f,0,0,0);
			drawTex(gl, mGR.mTex_facebook, -.8f, -.87f);
			if(counter > 30)
				drawTex(gl, mGR.mTex_Continue, 0, -.8f);
			break;
		case M.GameMenu:
//		case M.GameSetting:
			DrawMenu(gl);
			break;
		case M.GamePause:
		case M.GameOver:
		case M.GamePlay:
		case M.GameChallenge:
		case M.GameSing:
			DrawGamePlay(gl);
			break;
		case M.GameHelp:
		case M.GameAbout:
			DrawHelp(gl);
			break;
		case M.GameBulk:
			DrawTop(gl);
			break;
		case M.GameCarSelection:
			DrawCarSelection(gl);
			break;
		}
//		M.GameScreen = M.GamePause;
//		DrawGamePlay(gl);
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{ 
//		setGameOverAnimation();
		switch (M.GameScreen) {
		case M.GameADD:
			HandleADD(event);
			break;
		case M.GameSplash:
			HandleSplash(event);
			break;
		case M.GameMenu:
//		case M.GameSetting:
			HandleMenu(event);
			break;
		case M.GamePlay:
			HandleGame(event);
			break;
		case M.GameOver:
			HandleGameOver(event);
			break;
		case M.GamePause:
			HandlePause(event);
			break;
		case M.GameCarSelection:
			HandleCarSelection(event);
			break;
		case M.GameBulk:
			HandleTop(event);
			break;
		case M.GameSing:
//			HandleSing(event);
		case M.GameChallenge:
			if(event.getAction()==MotionEvent.ACTION_UP)
			{
				if(M.setBG)
				{
					if(mGR.mCarSel == 0)
						M.BGplay(GameRenderer.mContext,R.drawable.gameplay_bike);
					else
						M.BGplay(GameRenderer.mContext,R.drawable.gameplay_car);
				}
				else
					M.loopSoundStop();
				mGR.gameStart();
			}
			break;
		} 
//		Handle(event);
		return true;
	}
	void gameLogic()
	{
		mGR.acc();
		mGR.mPlayer.update();
		if(mGR.mPlayer.ChallengeCom == 0)
		{
//			Log.d((mGR.mPlayer.Distance/(-60*1000)) +" ~~~~~~ "+ Track.Challenges[mGR.Challenge%Track.Challenges.length][0]+"   "+
//					mGR.mPlayer.Crosscar +" ~~~~~~ "+  Track.Challenges[mGR.Challenge%Track.Challenges.length][1]+"   "+ 
//					mGR.mPlayer.CollectCoin +" ~~~~~~ "+  Track.Challenges[mGR.Challenge%Track.Challenges.length][2]+"   ", 
//					(((System.currentTimeMillis()-mGR.GameTime)/(1000*60))+" ~~~~~~ "+ Track.Challenges[mGR.Challenge%Track.Challenges.length][3]
//							+"  "+ Track.Challenges[mGR.Challenge%Track.Challenges.length][3]));
				
			if((mGR.mPlayer.Distance/(-60*1000)) >= Track.Challenges[mGR.Challenge%Track.Challenges.length][0]&&
				mGR.mPlayer.Crosscar >= Track.Challenges[mGR.Challenge%Track.Challenges.length][1]&&
				mGR.mPlayer.CollectCoin >= Track.Challenges[mGR.Challenge%Track.Challenges.length][2]&&
				(((System.currentTimeMillis()-mGR.GameTime)/(1000*60))>=Track.Challenges[mGR.Challenge%Track.Challenges.length][3]
						||Track.Challenges[mGR.Challenge%Track.Challenges.length][3] == 0))
			{
				M.sound5(GameRenderer.mContext,R.drawable.levelwin);
				Player.mTotalCoin +=Track.Challenges[mGR.Challenge%Track.Challenges.length][4];
				mGR.mPlayer.ChallengeCom = 1;
				mGR.Challenge++;
			}
		}
		coinSet();
		GameCollisions();
		int nagetive =-1;
		for(int i=0;i<mGR.mOpponent.length;i++)
		{
			mGR.mOpponent[i].update();
			if(mGR.mOpponent[i].y<-1.2)
				nagetive = i;
		}
		
		mGR.mVCount++;
		if(nagetive>-1 && mGR.mVCount%25==0)
		{
			mGR.mPlayer.Crosscar++;
			Player.mTotalCrossCar++;
			int i=0,k=0;
			int id[]=new int[Track.Track1[0].length];
			for(;i<mGR.mRow.length;i++)
			{
				if(mGR.mRow[i].y<1.5f && mGR.mRow[i].y>1.00)
				{
					for(int j = 0;j<Track.Track1[0].length;j++)
					{
						if(mGR.mRow[i].bg == 3)
						{
							if(Track.Track2[mGR.mRow[i].Id][j] == 3 ||Track.Track2[mGR.mRow[i].Id][j] == 7 ||Track.Track2[mGR.mRow[i].Id][j] == 18 || Track.Track2[mGR.mRow[i].Id][j] == 21
									|| Track.Track2[mGR.mRow[i].Id][j] == 22|| Track.Track2[mGR.mRow[i].Id][j] == 33|| Track.Track2[mGR.mRow[i].Id][j] == 38)
							{
								id[k] = j;
								k++;
							}
						}
						else
						{
							if(Track.Track1[mGR.mRow[i].Id][j] == 3 ||Track.Track1[mGR.mRow[i].Id][j] == 4 ||Track.Track1[mGR.mRow[i].Id][j] == 10 || Track.Track1[mGR.mRow[i].Id][j] == 11)
							{
								id[k] = j;
								k++;
							}
						}
					}
					k = M.mRand.nextInt(k);
//					id[k] = 4;
					mGR.mOpponent[nagetive].set((-1f+mGR.mTex_BG[0][0].width()/2)+id[k]*mGR.mTex_BG[0][0].width(),mGR.mRow[i].y, 0,M.mRand.nextBoolean()?-.02f:-.015f,M.mRand.nextInt(mGR.mTex_Traffic.length));
					break;
				}
			}
		}
		for(int i=0;i<mGR.mRow.length;i++)
		{
			if(mGR.mRow[i].y<-1-mGR.mTex_BG[0][0].Height()/2)
			{
				int no = mGR.mRoadBlock[mGR.mPath][RoadBlock.sNext].mTrack[mGR.mRoadBlock[mGR.mPath][RoadBlock.sNext].counter%mGR.mRoadBlock[mGR.mPath][RoadBlock.sNext].mTrack.length];
				if(i == 0)
					mGR.mRow[i].set(mGR.mRow[mGR.mRow.length-1].y+ mGR.mTex_BG[0][0].Height(), no, 0, RoadBlock.BG);
				else
					mGR.mRow[i].set(mGR.mRow[i-1].y+ mGR.mTex_BG[0][0].Height(), no, 0, RoadBlock.BG);
				setObj(i);
				
				mGR.mRoadBlock[mGR.mPath][RoadBlock.sNext%mGR.mRoadBlock[mGR.mPath].length].update();
			}
		}
		for(int i=0;i<mGR.mRow.length;i++)
			mGR.mRow[i].update();
		
		if(mGR.mPlayer.mTuchScr && mGR.mPlayer.Bost > 1)
			M.CallBost(GameRenderer.mContext, mGR.mCarSel);
		else
			M.CallBostP(GameRenderer.mContext, mGR.mCarSel);
	}
	
	void DrawGamePlay(GL10 gl)
	{
		for(int i=0;i<mGR.mRow.length;i++)
		{
			for(int j = 0;j<Track.Track1[0].length&&mGR.mRow[i].y<1.1;j++)
			{
				if(mGR.mRow[i].bg == 3)
					drawTex(gl, mGR.mTex_BG[mGR.mRow[i].bg][Track.Track2[mGR.mRow[i].Id][j]-1],(-1f+mGR.mTex_BG[0][0].width()/2)+j*mGR.mTex_BG[0][0].width(), mGR.mRow[i].y);
				else
					drawTex(gl, mGR.mTex_BG[mGR.mRow[i].bg][Track.Track1[mGR.mRow[i].Id][j]-1],(-1f+mGR.mTex_BG[0][0].width()/2)+j*mGR.mTex_BG[0][0].width(), mGR.mRow[i].y);
			}
		}
//		System.out.println("  "+mGR.mPlayer.Distroy+"   "+ mGR.mCarSel+"  ~~~~~~~~~~~~~~~~~  "+M.GameScreen);
		if(M.GameScreen == M.GameOver && mGR.mCarSel == 0){
			drawPlayerCrash(gl);
		}
		else{
			if(mGR.mPlayer.Distroy == 0)
			{
				drawTex(gl, mGR.mTex_Car[mGR.mCarSel], mGR.mPlayer.x,mGR.mPlayer.y);
			}
			else
			{
				if(mGR.mPlayer.Distroy>mGR.mTex_Blast.length)
				{
					drawTex(gl, mGR.mTex_Car[mGR.mCarSel], mGR.mPlayer.x,mGR.mPlayer.y);
					drawTex(gl, mGR.mTex_Blast[mGR.mPlayer.Distroy%mGR.mTex_Blast.length], mGR.mPlayer.x,mGR.mPlayer.y);
				}
				else if(mGR.mPlayer.Distroy%2==0)
					drawTex(gl, mGR.mTex_Car[mGR.mCarSel], mGR.mPlayer.x,mGR.mPlayer.y);
				mGR.mPlayer.Distroy--;
			}
		}
		for(int i=0;i<mGR.mOpponent.length;i++)
			drawTex(gl, mGR.mTex_Traffic[mGR.mOpponent[i].no], mGR.mOpponent[i].x,mGR.mOpponent[i].y);
		for(int i=0;i<mGR.mRow.length;i++)
		{
			if(mGR.mRow[i].obj!=0)
			{
				DrawObj(gl, i);
			}
		}
		for(int i=0;i<mGR.mCoins.length;i++)
		{
			drawTex(gl, mGR.mTex_Coin[counter%mGR.mTex_Coin.length], mGR.mCoins[i].x,mGR.mCoins[i].y);
		}
		if(M.GameScreen == M.GamePlay)
		{
			gameLogic();
			drawTex(gl, mGR.mTex_Pause,-.85f,.75f);
		}
		else
		{
			//if(counter%5==0)
				mGR.StartConter++;
			drawNumber(gl, ""+mGR.StartConter, 0, 0, 1, 0);
		}
		if(M.GameScreen == M.GamePause)
		{
			drawTex(gl, mGR.mTex_pauseBack, 0,mGR.pause);
			if(mGR.pause<-.5f)
				mGR.pause+=.1f;
			else
				drawAnimation(gl);
			drawTex(gl, mGR.mTex_Tag,-.5f,mGR.pause+.33f);
			drawTex(gl, mGR.mTex_pausetex,-.5f,mGR.pause+.33f);
		
			//for(int i=0;i<counter%mGR.mTex_Selection.length;i++)
			{
			switch (mGR.mSel) {
			case 1://Music
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], -.75f,mGR.pause+.17f);
				break;
			case 2://Sound
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], -.75f,mGR.pause-.10f);
				break;
			case 3://Sensor
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length],  -.31f,mGR.pause+.03f);
				break;
			case 4://Continue
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], .27f,mGR.pause+.07f);
				break;
			case 5://Replay
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length],0.75f ,mGR.pause+.21f);
				break;
			case 6://Menu
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length],0.75f,mGR.pause-.1f);
				break;
				}
			
			}
			
			drawTex(gl, mGR.mTex_Music[M.setBG?0:1],-.75f,mGR.pause+.17f);
			drawTex(gl, mGR.mTex_sound[M.setValue?0:1],-.75f,mGR.pause-.10f);
			drawNumber(gl, "Sensor", -.41f,mGR.pause+.05f, 2, 0);
			drawNumber(gl, M.setsensor?"On":"Off", -.28f,mGR.pause+.01f, 2, 1);
			
			drawTex(gl, mGR.mTex_Continue, .27f,mGR.pause+.07f);
			drawTexRS(gl, mGR.mTex_Light,1,1,counter*10, .25f,mGR.pause+.07f);
			drawTex(gl, mGR.mTex_Replay,0.75f ,mGR.pause+.21f);
			drawTex(gl, mGR.mTex_MenuIcn,0.75f,mGR.pause-.1f);
		}
		if(M.GameScreen == M.GameOver || M.GameScreen == M.GameSing)
		{
			drawTex(gl, mGR.mTex_pauseBack, 0,mGR.pause);
			if(mGR.pause<-.5f)
				mGR.pause+=.1f;
			else
				drawAnimation(gl);
			drawTex(gl, mGR.mTex_Tag,-.5f,mGR.pause+.33f);
			drawTex(gl, mGR.mTex_GameOverTex,-.5f,mGR.pause+.33f);
//			drawNumber(gl, "Challenge Complete",0.10f,mGR.pause+.35f, 2, 0);
			long ctime =  mGR.pauseTime-mGR.GameTime;
			String time = ctime/(1000*60)+":"+(ctime/1000)%60+":"+(ctime/10)%99;
			drawNumber(gl, "PLAYED TIME",-.55f,mGR.pause+.17f, 2, 1);
			drawNumber(gl, time			,-.55f,mGR.pause+.11f, 2, 1);
			
			drawNumber(gl, "Collect Coins"	 ,-.50f,mGR.pause-.05f, 2, 1);
			drawNumber(gl, ""+mGR.mPlayer.CollectCoin,-.50f,mGR.pause-.12f, 2, 1);
			
			drawNumber(gl, "Total Coins"		,0.15f,mGR.pause+.17f, 2, 1);
			drawNumber(gl, ""+Player.mTotalCoin,0.15f,mGR.pause+.11f, 2, 1);
			
//			drawTexRS(gl, mGR.mTex_Submit,mGR.mSel == 4?.7f:.5f,mGR.mSel == 4?.7f:.5f,0,-.6f,mGR.pause+.03f);
//			drawTexRS(gl, mGR.mTex_leaderboard,mGR.mSel == 5?.7f:.5f,mGR.mSel == 5?.7f:.5f,0,0.7f,mGR.pause+.15f);
			
			//for(int i=0;i<counter%mGR.mTex_Selection.length;i++)
			{
			switch (mGR.mSel) {
			case 1://Rate Us
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], .70f,mGR.pause+.20f);
				break;
			case 2://Replay
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], .70f,mGR.pause-.12f);
				break;
			case 3://Menu
				drawTex(gl, mGR.mTex_Selection[counter%mGR.mTex_Selection.length], .10f,mGR.pause-.10f);
				break;
				}
			}
//			drawTex(gl, mGR.mTex_Rate, .70f,mGR.pause+.20f);
			//drawTex(gl, mGR.mTex_Light, 0.70f ,mGR.pause-.12f);
			drawTexRS(gl, mGR.mTex_Light,.8f,.8f,counter*10, 0.70f ,mGR.pause-.12f);
			drawTex(gl, mGR.mTex_Replay,0.70f ,mGR.pause-.12f);
			drawTex(gl, mGR.mTex_MenuIcn,0.10f,mGR.pause-.10f);
			 if(M.GameScreen == M.GameSing)
			 {
				 drawTex(gl, mGR.mTex_pauseBack, 0,0);
				 drawTex(gl, mGR.mTex_CoinBack , 0,0);
				 drawNumber(gl, "Sing In",0.05f, 0,1, 1);
			 }
		}
		drawTex(gl, mGR.mTex_GameBar, 0,-.83f);
		drawTexRS(gl, mGR.mTex_Nip[0], 1,1, mGR.mPlayer.Speed, -.38f,-.92f);
		drawTex(gl, mGR.mTex_Nip[1],-.39f,-.92f);
		if(!M.setsensor)
		{
			if(mGR.mPlayer.forKeyPress != 1)
				drawTex(gl, mGR.mTex_gLeft,-.84f,-.92f);
			if(mGR.mPlayer.forKeyPress != 2)
				drawTex(gl, mGR.mTex_gRight,0.84f,-.92f);
		}
		drawTexRS(gl, mGR.mTex_Boost, mGR.mPlayer.Bost,1, 0, .1f+(mGR.mTex_Boost.width()*mGR.mPlayer.Bost/2),-.814f);
		if(counter%3!= 0 && mGR.mPlayer.Bost>=M.BOOST)
			drawTex(gl, mGR.mTex_Boostindi,-.017f,-.788f);
		long ctime; 
		
		if(M.GameScreen == M.GamePlay)
			ctime =  System.currentTimeMillis()-mGR.GameTime;
		else
		{
			ctime =  mGR.pauseTime-mGR.GameTime;
		}
		
		String time = ctime/(1000*60)+":"+(ctime/1000)%60+":"+(ctime/10)%99;
		drawTex(gl, mGR.mTex_Coin[counter%mGR.mTex_Coin.length],.02f,-.925f);
		String str = ""+Player.mTotalCoin;
		int length = totalC - str.length();
		str = "";
		for(int i=0;i<length;i++)
			str+="0";
		str+=""+Player.mTotalCoin;
		drawNumber(gl, str,mGR.mTex_Coin[0].width()+.04f,-.925f, 0, 0);
		if(M.GameScreen ==  M.GameChallenge)
		{
			float y =-.25f,diff=mGR.mTex_Font[1][0].Height();
			drawTexRS(gl, mGR.mTex_pauseBack,1, .7f, 0f, 0,y-.18f);
			drawNumber(gl, "Challenge",0,y+.02f, 1, 1);
			if(Track.Challenges[mGR.Challenge%Track.Challenges.length][0]!=0)
				drawNumber(gl, "Drive "+Track.Challenges[mGR.Challenge%Track.Challenges.length][0]+" meter  Safely",0,y-=diff, 2, 1);
			if(Track.Challenges[mGR.Challenge%Track.Challenges.length][1]!=0)
				drawNumber(gl, (y != .18f?"and ":"")+"Overtake "+Track.Challenges[mGR.Challenge%Track.Challenges.length][1]+" Cars",0,y-=diff, 2, 1);
			if(Track.Challenges[mGR.Challenge%Track.Challenges.length][2]!=0)
				drawNumber(gl, (y != .18f?"and ":"")+"Collect "+Track.Challenges[mGR.Challenge%Track.Challenges.length][2]+" Coins",0,y-=diff, 2, 1);
			if(Track.Challenges[mGR.Challenge%Track.Challenges.length][3]!=0)
				drawNumber(gl, (y != .18f?"In ":"Drive ")+Track.Challenges[mGR.Challenge%Track.Challenges.length][3]+" Min",0,y-=diff, 2, 1);
			drawNumber(gl, "Get "+Track.Challenges[mGR.Challenge%Track.Challenges.length][4]+" Coins",0,y-=diff, 2, 1);
			drawNumber(gl, "Tap to continue",.3f,y-.25f, 2, 0);
		}
		else
		{
			String Str = time+"";
			float dx = (Str.length()*mGR.mTex_Font[2][0].width()/43)/2; 
			drawTex(gl, mGR.mTex_TimeBar,.75f,.75f);
			drawNumber(gl, time, .545f-dx/2,.75f, 2, 0);
			drawTex(gl, mGR.mTex_TimeBar,.75f,.68f);
			drawNumber(gl, "dis:"+mGR.mPlayer.Distance/(-60*1000), .58f,.68f, 2, 0);
			for(int i=0;i<mGR.mPlayer.Strenth;i++)
			  drawTex(gl,mGR.mTex_Life,.54f+i* mGR.mTex_Life.width(),.58f);
		}
		if(mGR.mPlayer.ChallengeCom>0&&mGR.mPlayer.ChallengeCom<100)
		{
			mGR.mPlayer.ChallengeCom++;
			drawNumber(gl, "Congrates your challenge is Completed",0,0, 2, 1);
		}
	}
	boolean HandleGame(MotionEvent event)
	{
		mGR.mSel = 0;
		mGR.mPlayer.forKeyPress = 0;
		if(CirCir(-.85f,.75f, mGR.mTex_MenuIcn.width()/4,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Pause
		}
		if(CirCir(-.84f,-.92f, mGR.mTex_MenuIcn.width(),screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)
				&& !M.setsensor)
		{
			mGR.mSel = 2;//Left
			mGR.mPlayer.forKeyPress = 1;
		}
		if(CirCir(0.84f,-.92f, mGR.mTex_MenuIcn.width(),screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)
				&& !M.setsensor)
		{
			mGR.mSel = 3;//Right
			mGR.mPlayer.forKeyPress = 2;
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			//if(CirCir(0,-.89f, mGR.mTex_MenuIcn.width(),screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			if(mGR.mSel == 0)
			{
				mGR.mPlayer.mTuchScr = true;
			}	
				
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			mGR.mPlayer.mTuchScr = false;
			switch(mGR.mSel)
			{
			case 1://Pause
				 M.loopSoundStop();
				mGR.pause = -1.5f;
				M.GameScreen = M.GamePause;
				mGR.pauseTime =  System.currentTimeMillis();
				break;
			}
			mGR.mPlayer.forKeyPress = 0;
			mGR.mSel=0;
		}
		return true;
	}
	void drawTex(GL10 gl,SimplePlane mTex,float x,float y)
	{
		if(mTex!=null)
			mTex.drawTil(gl, x, y);
	}
	void drawTexRS(GL10 gl,SimplePlane mTex,float scalx,float scaly,float rotateZ,float x,float y)
	{
		mTex.drawScalRotet(gl, scalx, scaly, rotateZ, x, y);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;
    }
	
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		if( ax+adx > bx  && ay+ady > by && bx+bdx > ax && by+bdy> ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	
	void drawNumber(GL10 gl,String no,float x, float y,int fontType,int Align)
	{
		int k;
		float dx = mGR.mTex_Font[fontType][0].width();
		no = no.toUpperCase();
//		Log.d(no, no);
		if(fontType==0)
			dx *= 1.0f;
		else if(fontType==2)
			dx *= .5f;
		else 
			dx *= .7f;
		if(Align == 1)//Center
			x -= dx*(no.length()-1f)*.5f;
		if(Align == 2)//Right
			x -= dx*(no.length()-1f)*1.0f;
		
		for(int i =0;i<no.length();i++)
		{
			if(no.charAt(i) == 'I' || no.charAt(i) == ' ')
			{
				if(fontType==1)
					x-= .04f;
				if(fontType==2)
					x-= .02f; 
			}
			if(fontType==1)
				k = ((int)no.charAt(i))-65;
			else
				k = ((int)no.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font[fontType].length)
				mGR.mTex_Font[fontType][k].drawPos(gl,x+i*dx,y);
		}
	}
	void gameOver(int type)
	{
		if(mGR.mPlayer.Distroy>0)
			return;
		
		if(mGR.mPlayer.Strenth > 0)
		{
			M.sound2(GameRenderer.mContext,R.drawable.crash);
			mGR.mPlayer.Strenth--;
			if(type == 0)
				mGR.mPlayer.Distroy = mGR.mTex_Blast.length*2;
			else
			{
				int k=0;
				int id[]=new int[Track.Track1[0].length];
				mGR.mPlayer.Distroy = mGR.mTex_Blast.length;
				for(int j = 0;j<Track.Track1[0].length;j++)
				{
					if(mGR.mRow[type-1].bg == 3)
					{
						if(Track.Track2[mGR.mRow[type-1].Id][j] == 3 ||Track.Track2[mGR.mRow[type-1].Id][j] == 7 ||Track.Track2[mGR.mRow[type-1].Id][j] == 18 || Track.Track2[mGR.mRow[type-1].Id][j] == 21
								|| Track.Track2[mGR.mRow[type-1].Id][j] == 22|| Track.Track2[mGR.mRow[type-1].Id][j] == 33|| Track.Track2[mGR.mRow[type-1].Id][j] == 38)
						{
							id[k] = j;
							k++;
						}
					}
					else
					{
						if(Track.Track1[mGR.mRow[type-1].Id][j] == 3 ||Track.Track1[mGR.mRow[type-1].Id][j] == 4 ||Track.Track1[mGR.mRow[type-1].Id][j] == 10 || Track.Track1[mGR.mRow[type-1].Id][j] == 11)
						{
							id[k] = j;
							k++;
						}
					}
				}
				k = M.mRand.nextInt(k);
				mGR.mPlayer.x = (-1f+mGR.mTex_BG[0][0].width()/2)+id[k]*mGR.mTex_BG[0][0].width();
			}
		}
		else
		{
			M.loopSoundStop();
//			if(type ==0)
//			{
//				boolean ischange = mGR.mRand.nextBoolean();
//				if(ischange)
//				  M.sound2(GameRenderer.mContext,R.drawable.crash);
//				else
//			  	  M.sound3(GameRenderer.mContext,R.drawable.crash);
//			}
//			else
			 M.sound4(GameRenderer.mContext,R.drawable.gameover);
			Player.mTotalTime = System.currentTimeMillis()-mGR.GameTime;
			mGR.pause = -1.5f;
			M.GameScreen = M.GameOver;
			if(mGR.ads %3 == 0)
				GameRenderer.mStart.show();
//			if (GameRenderer.mStart.interstitialAdisReady()) {
//				try {
//					GameRenderer.mStart.show();
//				} catch (Exception e) {
//				}
//			}
			setGameOverAnimation();
			mGR.pauseTime = System.currentTimeMillis();
		}
	}
	void setGameOverAnimation()
	{
		payerro=0;
//		mGR.mPlayer.x=mGR.mPlayer.y = 0;
		mGR.overPlayer.set(mGR.mPlayer.x, mGR.mPlayer.y,(mGR.mRand.nextBoolean()?.01f:-.01f), (mGR.mRand.nextInt(20)+50)*.0005f, 1);
	}
	int payerro=0;
	void drawPlayerCrash(GL10 gl)
	{
//		System.out.println(payerro+"[x = "+mGR.overPlayer.x+"][y = "+ mGR.overPlayer.y+"][vx = "+mGR.overPlayer.vx+"][vy = "+mGR.overPlayer.vy+"]");
		if(payerro<20){
			payerro++;
			mGR.overPlayer.x+=mGR.overPlayer.vx;
			mGR.overPlayer.ax-=mGR.overPlayer.vx;
			mGR.overPlayer.y+= mGR.overPlayer.vy;
			if(counter % 10 == 0 && mGR.overPlayer.no<3)
				mGR.overPlayer.no ++;
		}
		mGR.mTex_crash[0].drawRotet(gl, payerro*10,mGR.overPlayer.ax,mGR.overPlayer.y);
		mGR.mTex_crash[mGR.overPlayer.no].drawRotet(gl, payerro*03,mGR.overPlayer.x,mGR.overPlayer.y);			
	}
	void GameCollisions()
	{
		/***************For Car Road Collision Start****************************/
		for(int i=0;i<mGR.mOpponent.length;i++)
		{
			if(Rect2RectIntersection(mGR.mOpponent[i].x, mGR.mOpponent[i].y, mGR.mTex_Car[1].width()*.6f, mGR.mTex_Car[1].Height()*.6f,
				  	mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[1].width()*.6f, mGR.mTex_Car[1].Height()*.6f))
			{
//				Log.d(" ~~~~~~~~~~~~~  ","~~~~~~~~~~~~~~~~~~~~");
				mGR.mOpponent[i].y = -2f;
				gameOver(0);
				return;
			}
		}
		
		
		
		for(int j=0;j<mGR.mRow.length;j++)
		{
			if(mGR.mRow[j].y< 1+mGR.mTex_BG[0][0].Height()&&
					CircRectsOverlap(0, mGR.mRow[j].y, 1,mGR.mTex_BG[0][0].Height()/2,mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[1].width()/2))
			{
				for(int k=0;k<Track.Track1[0].length;k++)
				{
					if(mGR.mRow[(j)%mGR.mRow.length].bg == 3)
					{
						if(Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 3 && Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 7 && Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 18 && Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 21
								&& Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 22 && Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 33 && Track.Track2[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 38)
						{
							if(Rect2RectIntersection(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[1].width()/2,mGR.mTex_Car[1].Height()/2,
									(-1f+mGR.mTex_BG[0][0].width()/2)+k*mGR.mTex_BG[0][0].width(), mGR.mRow[j].y,mGR.mTex_BG[0][0].width()/4,mGR.mTex_BG[0][0].Height()/2))
							{
//								Log.d(" ~!!!!!!!!!!!!!!!!!!","~~~~~~~~~~~~~~~~~~~~");
								gameOver(j+1);
								return;
							}
						}
					}
					else
					{
//						Log.d(mGR.mRow[j].bg+"     -------------------     ","            --------------------      "+j);
						if(Track.Track1[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 3 && Track.Track1[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 4 
								&& Track.Track1[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 10 && Track.Track1[mGR.mRow[(j)%mGR.mRow.length].Id][k] != 11)
						{
							if(Rect2RectIntersection(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[1].width()/2,mGR.mTex_Car[1].Height()/2,
									(-1f+mGR.mTex_BG[0][0].width()/2)+k*mGR.mTex_BG[0][0].width(), mGR.mRow[j].y,mGR.mTex_BG[0][0].width()/4,mGR.mTex_BG[0][0].Height()/2))
							{
								gameOver(j+1);
								return;
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		for(int i=0;i<mGR.mOpponent.length;i++)
		{
			for(int j=i+1;j<mGR.mOpponent.length;j++)
			{
				if(Rect2RectIntersection(mGR.mOpponent[i].x, mGR.mOpponent[i].y, mGR.mTex_Car[1].width()/2, mGR.mTex_Car[1].Height()*.8f,
									  	mGR.mOpponent[j].x, mGR.mOpponent[j].y, mGR.mTex_Car[1].width()/2, mGR.mTex_Car[1].Height()*.8f))
				{
					if(mGR.mOpponent[i].y>mGR.mOpponent[j].y)
						mGR.mOpponent[i].vy=mGR.mOpponent[j].vy*.9f;
					else
						mGR.mOpponent[j].vy=mGR.mOpponent[i].vy*.9f;
						
					return;
				}
			}
			for(int j=0;j<mGR.mRow.length;j++)
			{
				if(mGR.mRow[j].y< 1+mGR.mTex_BG[0][0].Height() && mGR.mOpponent[i].y >-1.1f)
				{
					if(CircRectsOverlap(0, mGR.mRow[j].y, 1,mGR.mTex_BG[0][0].Height()/2,mGR.mOpponent[i].x, mGR.mOpponent[i].y, mGR.mTex_Car[1].width()/2))
					{
						for(int k=0;k<Track.Track1[0].length;k++)
						{
							if(CircRectsOverlap((-1f+mGR.mTex_BG[0][0].width()/2)+k*mGR.mTex_BG[0][0].width(), mGR.mRow[j].y,mGR.mTex_BG[0][0].width()/4,mGR.mTex_BG[0][0].Height()/2,mGR.mOpponent[i].x, mGR.mOpponent[i].y, mGR.mTex_Car[1].width()/4))
							{
//								Log.d(mGR.mRow[(j+8)%mGR.mRow.length].bg+"     ++++++++++++++++++++     ","            ++++++++++++++++++++      ");
								if(mGR.mRow[(j+8)%mGR.mRow.length].bg == 3)
								{
									if(Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 3 && Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 7 && Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 18 && Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 21
											&& Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 22 && Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 33 && Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 38)
									{
										if(mGR.mOpponent[i].vx==0)
										{
											if(Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k]==32||
													Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k]==2||
													Track.Track2[mGR.mRow[(j+8)%mGR.mRow.length].Id][k]==13)
												mGR.mOpponent[i].vx =0.02f;
											else if((mGR.mOpponent[i].x > 0 && mGR.mOpponent[i].x < mGR.mTex_BG[0][0].width())||
													(mGR.mOpponent[i].x > -1 && mGR.mOpponent[i].x < -mGR.mTex_BG[0][0].width()))
												mGR.mOpponent[i].vx =0.02f;
											else
												mGR.mOpponent[i].vx =-.02f;
										}
									}
								}
								else
								{
//									Log.d(mGR.mRow[j].bg+"     -------------------     ","            --------------------      "+j);
									if(Track.Track1[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 3 && Track.Track1[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 4 && Track.Track1[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 10 && Track.Track1[mGR.mRow[(j+8)%mGR.mRow.length].Id][k] != 11)
									{
										if(mGR.mOpponent[i].vx==0)
										{
											if((mGR.mOpponent[i].x > 0 && mGR.mOpponent[i].x < mGR.mTex_BG[0][0].width())||
													(mGR.mOpponent[i].x > -1 && mGR.mOpponent[i].x < -mGR.mTex_BG[0][0].width()))
												mGR.mOpponent[i].vx =0.02f;
											else
												mGR.mOpponent[i].vx =-.02f;
										}
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
		/***************For Car Road Collision Start****************************/
	}
	void setObj(int i)
	{
		mGR.mRow[i].obj = 0;
		
		switch (mGR.mRow[i].Id) {
		case 1: //Bridge
			mGR.brigeCount++;
			if(mGR.brigeCount%5==0)
				mGR.mRow[i].obj = 1;
			else
				mGR.mRow[i].obj = 0;	
			break;
		case 2:case 15:case 18:case 24:case 27:
			mGR.brigeCount++;
			mGR.mRow[i].obj = 2;
			break;
		case 22:case 28:
			mGR.brigeCount++;
			if(mGR.brigeCount%2==0)
			{
				if(M.mRand.nextBoolean())
					mGR.mRow[i].obj = 3;
				else
					mGR.mRow[i].obj = 4;
			}
			break;
		case 16:
			mGR.brigeCount++;
			if(mGR.brigeCount%2==0)
			{
				if(M.mRand.nextBoolean())
					mGR.mRow[i].obj = 5;
				else
					mGR.mRow[i].obj = 6;
			}
			break;
		}
//		Log.d("            "+mGR.mRow[i].obj,mGR.mRow[i].Id+"    ");
	}
	void DrawObj(GL10 gl,int i)
	{
		switch (mGR.mRow[i].obj) {
		case 1: //Bridge
			drawTex(gl, mGR.mTex_Obj[0][1],0.1f, mGR.mRow[i].y);
			break;
		case 2://bothside tree
			if(mGR.mRow[i].bg < 2)
			{
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],-.8f, mGR.mRow[i].y);
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],0.8f, mGR.mRow[i].y);
			}
			else
			{
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],-.8f, mGR.mRow[i].y);
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][1],0.8f, mGR.mRow[i].y);
			}
			break;
		case 3://bothside tree
			if(mGR.mRow[i].bg < 2)
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],0.8f, mGR.mRow[i].y);
			else
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][1],0.8f, mGR.mRow[i].y);
			break;
		case 4://bothside tree
			drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],-.8f, mGR.mRow[i].y);
			break;
		case 5://bothside tree
			if(mGR.mRow[i].bg < 2)
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],0.6f, mGR.mRow[i].y);
			else
				drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][1],0.6f, mGR.mRow[i].y);
			break;
		case 6://bothside tree
			drawTex(gl, mGR.mTex_Obj[mGR.mRow[i].bg][0],-.6f, mGR.mRow[i].y);
			break;
		}
		
	}
	void coinSet()
	{
		int i=0;
		int coin = 0;
		for(i=0;i<mGR.mCoins.length;i++)
		{
			mGR.mCoins[i].Update();
			if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[0].width()/4, mGR.mTex_Car[0].Height()/4, mGR.mCoins[i].x, mGR.mCoins[i].y, mGR.mTex_Coin[0].width()/2))
			{
				coin =i;
				mGR.mCoins[i].y = -2f;
				mGR.mPlayer.CollectCoin++;
				Player.mTotalCoin++;
				M.sound6(GameRenderer.mContext,R.drawable.coin);
				//break;
			}
			if(mGR.mCoins[i].y<-1)
				coin =i;
		}
		
		if(mGR.mVCount%9==0)
		{
			int k=0;
			int id[]=new int[Track.Track1[0].length];
			for(i=0;i<mGR.mRow.length;i++)
			{
				if(mGR.mRow[i].y<1.5f && mGR.mRow[i].y>1.00)
				{
					for(int j = 0;j<Track.Track1[0].length;j++)
					{
						if(mGR.mRow[i].bg == 3)
						{
							if(Track.Track2[mGR.mRow[i].Id][j] == 3 ||Track.Track2[mGR.mRow[i].Id][j] == 7 ||Track.Track2[mGR.mRow[i].Id][j] == 18 || Track.Track2[mGR.mRow[i].Id][j] == 21
									|| Track.Track2[mGR.mRow[i].Id][j] == 22|| Track.Track2[mGR.mRow[i].Id][j] == 33|| Track.Track2[mGR.mRow[i].Id][j] == 38)
							{
								id[k] = j;
								k++;
							}
						}
						else
						{
							if(Track.Track1[mGR.mRow[i].Id][j] == 3 ||Track.Track1[mGR.mRow[i].Id][j] == 4 ||Track.Track1[mGR.mRow[i].Id][j] == 10 || Track.Track1[mGR.mRow[i].Id][j] == 11)
							{
								id[k] = j;
								k++;
							}
						}
					}
					k = M.mRand.nextInt(k);
					break;
				}
			}
//			for(i=0;i<mGR.mCoins.length;){
//				for(int j=0;j<k&&i<mGR.mCoins.length;j++){
//					System.out.println(id[j]+"  ");
//					mGR.mCoins[i].setCoin((-1f+mGR.mTex_BG[0][0].width()/2)+id[j]*mGR.mTex_BG[0][0].width(), 1.1f+i*.01f);
//					i++;
//				}
//			}
			mGR.mCoins[coin].setCoin((-1f+mGR.mTex_BG[0][0].width()/2)+id[k]*mGR.mTex_BG[0][0].width(), 1.1f);
		}
	}
	
	boolean HandleGameOver(MotionEvent event)
	{
		
		mGR.mSel = 0;
		if(CirCir(.70f,mGR.pause+.20f, mGR.mTex_MenuIcn.width()/2,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
//			mGR.mSel = 1;//Rate Us
		}
		if(CirCir(0.70f ,mGR.pause-.12f, mGR.mTex_MenuIcn.width()/2,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2; //Replay
		}
		if(CirCir(0.10f,mGR.pause-.10f, mGR.mTex_MenuIcn.width()/2,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Menu
		}
		
		if(CirCir(-.6f,mGR.pause+.03f, mGR.mTex_MenuIcn.width()/2,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4; //Submit
		}
		if(CirCir(0.7f,mGR.pause+.15f, mGR.mTex_MenuIcn.width()/2,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//HighScore
		}
		
		
		
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(mGR.mPlayer.mTuchScr)
			{
				mGR.mPlayer.mTuchScr = false;
				return true;
			}
			switch (mGR.mSel) {
			case 1://Rate Us
				Intent intent = null;
				intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);//mTex_Rate
//				M.GameScreen = M.GameSing;
				
				break;
			case 2://Replay
				counter =1;
				mGR.gameReset();
				M.GameScreen=M.GameChallenge;
//				GameRenderer.mStart.Submit();
				break;
			case 3://Menu
//				GameRenderer.mStart.Show();
				if(M.setBG)
				   M.Splashplay(GameRenderer.mContext,R.drawable.splash);
			 	else
			 	  M.loopSoundStop();
				M.GameScreen = M.GameMenu;
				break;
			case 4://Submit
//				long ctime =  mGR.pauseTime-mGR.GameTime;
//				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score, ctime);
				break;
			case 5://Highscore
//				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			
				}
			mGR.mSel = 0;
			
		}
		return true;
	}
//	boolean HandleSing(MotionEvent event)
//	{
//		mGR.mSel =0;
//		if(CircRectsOverlap(0, 0, .2f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//		{
//			mGR.mSel = 1;//music
//		}
//		if(event.getAction()==MotionEvent.ACTION_UP && mGR.mSel ==1)
//		{
//			GameRenderer.mStart.beginUserInitiatedSignIn();
//		}
//		return true;
//	}
	boolean HandlePause(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CirCir(-.75f,mGR.pause+.17f, mGR.mTex_MenuIcn.width()/4,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//music
		}
		if(CirCir(-.75f,mGR.pause-.10f, mGR.mTex_MenuIcn.width()/4,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2; //Sound
		}
		if(CirCir(-.31f,mGR.pause+.03f, mGR.mTex_MenuIcn.width()/4,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Sensor
		}
		if(CirCir(.27f,mGR.pause+.07f, mGR.mTex_MenuIcn.width()/3,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Continue
		}
		if(CirCir(0.75f ,mGR.pause+.21f, mGR.mTex_MenuIcn.width()/3,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5; //Replay
		}
		if(CirCir(0.75f,mGR.pause-.1f, mGR.mTex_MenuIcn.width()/3,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;//Menu
		}
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1://Music
				M.setBG = !M.setBG;
				break;
			case 2://Sound
				M.setValue = !M.setValue;
				break;
			case 3://Sensor
				M.setsensor = !M.setsensor;
				break;
			case 4://Continue
				if(M.setBG)
				{
					if(mGR.mCarSel == 0)
						M.BGplay(GameRenderer.mContext,R.drawable.gameplay_bike);
					else
						M.BGplay(GameRenderer.mContext,R.drawable.gameplay_car);
				}
				else
					M.loopSoundStop();
				M.GameScreen = M.GamePlay;
				mGR.mPlayer.forDistance = System.currentTimeMillis();
				mGR.GameTime = mGR.GameTime + (System.currentTimeMillis() - mGR.pauseTime);
				break;
			case 5://Replay
				counter =1;
				mGR.gameReset();
				M.GameScreen=M.GameChallenge;
				break;
			case 6://Menu
				if(M.setBG)
				   M.Splashplay(GameRenderer.mContext,R.drawable.splash);
				else
				   M.loopSoundStop();
				M.GameScreen = M.GameMenu;
				break;
				}
			if (mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
			
		}
		return true;
	}
	boolean HandleADD(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(0.0f,-.80f, mGR.mTex_Continue.width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Achieve
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(mGR.mSel==0)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.LINK+"com.oneday.games24.fightersofocean"));
				GameRenderer.mContext.startActivity(intent);//mTex_Rate
			}
			else
			if(mGR.mSel==1)
			{
				mGR.mTex_AllBG	= mGR.add("splash.jpg");
				mGR.mSel = 0;
				M.GameScreen = M.GameSplash;
				M.sound9(GameRenderer.mContext, R.drawable.bike);
				counter =0;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	boolean HandleSplash(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(-.8f,-.87f, mGR.mTex_facebook.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Achieve
		}
		if(CirCir(0.0f,-.8f, mGR.mTex_facebook.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//Exit
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				FaceBook();
				break;
			case 2:
				if(counter>30){
					if(M.setBG)
						M.Splashplay(GameRenderer.mContext,R.drawable.splash);
					else
						M.loopSoundStop();
					mGR.mTex_AllBG	= mGR.add("menu/back.png");
					mGR.mSel = 0;
					M.GameScreen = M.GameMenu;
				}
				break;		
			}
			mGR.mSel =0;
				
			
		}
		return true;
	}

	void DrawHelp(GL10 gl)
	{
//		drawTex(gl, mGR.mTex_BackScr,0,0);
		if(M.GameScreen == M.GameAbout)
			drawTex(gl, mGR.mTex_About,0,0);
		else
			drawTex(gl, mGR.mTex_HelpScr,0,0);
	}
	void DrawMenu(GL10 gl)
	{
		drawTex(gl, mGR.mTex_AllBG,0,0);
		if(mGR.mSel!=1)
			drawTex(gl, mGR.mTex_Menu[0],-.690f,-.630f);//Achieve
		if(mGR.mSel!=2)
			drawTex(gl, mGR.mTex_Menu[1],0.795f,0.355f);//Exit
		if(mGR.mSel!=3)
			drawTex(gl, mGR.mTex_Menu[2],0.260f,-.790f);//help
		if(mGR.mSel!=4)
			drawTex(gl, mGR.mTex_Menu[3],0.709f,-.610f);//About Us
		if(scounter%2==0 && mGR.mSel!=5)
			drawTex(gl, mGR.mTex_Menu[4],-.719f,0.324f);//Rate Us
		if(mGR.mSel!=6)
			drawTex(gl, mGR.mTex_Menu[5],-.260f,-.800f);//Setting
		//for(int i=0;i<=scounter%10;i++)
			drawTex(gl, mGR.mTex_Menu[6],0.000f,-.220f);//Start
		drawNumber(gl, ""+Player.mTotalCoin, 0, -.42f, 2, 1);
		if(counter%10==0)
			scounter++;
		
//		if(M.GameScreen == M.GameSetting)
		{
			drawTex(gl, mGR.mTex_Music[M.setBG?0:1],-.40f,.6f);
			drawTex(gl, mGR.mTex_sound[M.setValue?0:1]   ,0.00f,.6f);
			drawNumber(gl, "Sensor"					  ,0.40f,.6f, 2, 1);
			drawNumber(gl, M.setsensor?"On":"Off"	  ,0.40f,.55f, 2, 1);
		}
	}
	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(-.690f,-.630f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Achieve
		}
		if(CirCir(0.795f,0.355f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//Exit
		}
		if(CirCir(0.260f,-.790f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Help
		}
		if(CirCir(0.709f,-.610f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//About Us
		}
		if(CirCir(-.719f,0.324f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Rate Us
		}
		if(CirCir(-.260f,-.800f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;//Setting
		}
		if(CirCir(0.000f,-.200f, mGR.mTex_Menu[1].width(), screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;//Start
		}
//		if(M.GameScreen == M.GameSetting)
		{
			if(CirCir(-.40f,.6f, mGR.mTex_Menu[1].width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 8;//Music
			}
			if(CirCir(0.00f,.6f, mGR.mTex_Menu[1].width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 9;//sound
			}
			if(CirCir(0.40f,.6f, mGR.mTex_Menu[1].width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{

				mGR.mSel = 10;//Sensor
			}
		}
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			Intent intent = null;
			switch (mGR.mSel) {
			case 1://Achieve
				intent = new Intent(GameRenderer.mContext, AchieveList.class); //yogesh
//				intent = new Intent(GameRenderer.mContext, MainActivity.class); //yogesh
				GameRenderer.mContext.startActivity(intent);
				break;
			case 2://Exit
				try{handler.sendEmptyMessage(0);}catch(Exception e){}
				break;
			case 3://Help
				M.loopSoundStop();
				M.GameScreen = M.GameHelp;
				break;
			case 4://About Us
//				intent = new Intent(GameRenderer.mContext, AboutUs.class); //yogesh
//				GameRenderer.mContext.startActivity(intent);
				M.GameScreen = M.GameAbout;
				M.loopSoundStop();
				break;
			case 5://RateUs
				intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);//mTex_Rate
				break;
			case 6://Setting
				FaceBook();
//				M.GameScreen = M.GameSetting;
				break;
			case 7://Start
				M.GameScreen = M.GameCarSelection;
				break;
			case 8:
				M.setBG =!M.setBG;
				if(M.setBG)
				   M.Splashplay(GameRenderer.mContext,R.drawable.splash);
				else
				   M.loopSoundStop();
				break;
			case 9:
				M.setValue = !M.setValue;	
				break;
			case 10:
				M.setsensor =!M.setsensor;
				break;
			default:
				M.GameScreen = M.GameMenu;
				break;
				
			}
			if (mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		  
		}
		
		return true;
	}
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				GameRenderer.mStart.Exit();
				break;
			case 1:
				GameRenderer.mStart.Money();
				break;

			}
			
			}
		};
	void DrawCarSelection(GL10 gl)
	{
		drawTex(gl, mGR.mTex_BackScr,0,0);
		
		drawTex(gl, mGR.mTex_Back[0],0.75f,0.73f);
		if(mGR.mSel == 2)
			drawTex(gl, mGR.mTex_Back[1],0.75f,0.73f);
		
		drawTex(gl, mGR.mTex_CoinBack,0.0f, 0.73f);
		drawTex(gl, mGR.mTex_CoinTex, -.29f, 0.725f);
		
		String str = ""+Player.mTotalCoin;
		int length = totalC - str.length();
		str = "";
		for(int i=0;i<length;i++)
			str+="0";
		str+=""+Player.mTotalCoin;
		drawNumber(gl, str, -.08f, .73f, 0, 0);
		
		drawTex(gl, mGR.mTex_ShopBack, 0, -.13f);
		drawTex(gl, mGR.mTex_Features[0], 0, -.74f);
		if(Animation.counter!=0)
			drawTex(gl, mGR.mTex_Grear[counter%mGR.mTex_Grear.length], 0.00f, 0.34f);
		else
			drawTex(gl, mGR.mTex_Grear[0], 0.00f, 0.34f);
		
		drawTex(gl, mGR.mTex_Free[0],-.67f, 0.2f);
		if(mGR.mSel == 5)
		{
			drawTex(gl, mGR.mTex_Free[0],-.67f, 0.2f);
			drawTex(gl, mGR.mTex_Free[0],-.67f, 0.2f);
			drawTex(gl, mGR.mTex_Free[0],-.67f, 0.2f);
		}
		drawTex(gl, mGR.mTex_Free[2],-.67f, 0.2f);
		drawTex(gl, mGR.mTex_Free[0],0.67f, 0.2f);
		if(mGR.mSel == 6)
		{
			drawTex(gl, mGR.mTex_Free[0],0.67f, 0.2f);
			drawTex(gl, mGR.mTex_Free[0],0.67f, 0.2f);
			drawTex(gl, mGR.mTex_Free[0],0.67f, 0.2f);
		}
		drawTex(gl, mGR.mTex_Free[1],0.67f, 0.2f);
		
		drawTex(gl, mGR.mTex_PBack,-.6f, -.43f);
		if(mGR.mSel == 7)
		{
			drawTex(gl, mGR.mTex_PBack,-.6f, -.43f);
			drawTex(gl, mGR.mTex_PBack,-.6f, -.43f);
			drawTex(gl, mGR.mTex_PBack,-.6f, -.43f);
		}
		drawTex(gl, mGR.mTex_Features[2], 0.0f,-.72f);
		if(mGR.setVeh[mGR.mCarSel].Price == 0)
		{
			drawNumber(gl, "Purchase Price", -.91f,-.41f, 2, 0);
			drawNumber(gl, "Coin not require", -.925f, -.46f, 2, 0);
		}
		else
		{
			drawNumber(gl, "Purchase Price", -.92f,-.41f, 2, 0);
			drawNumber(gl, mGR.setVeh[mGR.mCarSel].Price+" Coins", -.87f, -.46f, 2, 0);
		}
		drawNumber(gl, mGR.setVeh[mGR.mCarSel].Name, 0.9f, -.55f, 1, 2);
		drawAnimation(gl);
		for(int i =0;i<mGR.setVeh[mGR.mCarSel].Acc;i++)
			drawTex(gl, mGR.mTex_Features[1], .16f+i*0.16f,-.64f);
		for(int i =0;i<mGR.setVeh[mGR.mCarSel].handling;i++)
			drawTex(gl, mGR.mTex_Features[1], .16f+i*0.16f,-.80f);
		for(int i =0;i<mGR.setVeh[mGR.mCarSel].strenth;i++)
			drawTex(gl, mGR.mTex_Features[1], .16f+i*0.16f,-.88f);
		
		String Str = mGR.setVeh[mGR.mCarSel].Speed+" MPH"+"";
		float dx = (Str.length()*mGR.mTex_Font[0][0].width())/10;
		drawNumber(gl, mGR.setVeh[mGR.mCarSel].Speed+" MPH",.18f-dx,-.725f, 2, 0);
		for(int i=0;i<mGR.setVeh.length;i++)
		{
			drawTexRS(gl, mGR.mTex_CarSelBack,mGR.setVeh[i].scal*2f,mGR.setVeh[i].scal*2f,0,mGR.setVeh[i].x,.49f);
			drawTexRS(gl, mGR.mTex_SelCar[mGR.setVeh[i].no],mGR.setVeh[i].scal*.8f,mGR.setVeh[i].scal*.8f,0,mGR.setVeh[i].x,.49f);
			if(mGR.setVeh[i].lock)
				drawTexRS(gl, mGR.mTex_Arrow[2],mGR.setVeh[i].scal*2f,mGR.setVeh[i].scal*2f,0,mGR.setVeh[i].x,.49f);
			mGR.setVeh[i].update();
		}
		drawTex(gl, mGR.mTex_SelCar[mGR.mCarSel],0,-.1f);
		for(int i=0;i<mGR.setVeh.length;i++)
		{
			if(mGR.setVeh[i].x > -.05f && mGR.setVeh[i].x < .05f)
			{
				if(Animation.counter>5)
				{
					Animation.counter = 0;
					mGR.mCarSel = i;
				}
			}
			if(Animation.Move && mGR.setVeh[i].x > 1.6f)
				mGR.setVeh[i].set(mGR.setVeh[(i+1)%mGR.setVeh.length].x - .8f);
			else if(!Animation.Move && mGR.setVeh[i].x < -1.6f)
				mGR.setVeh[i].set(mGR.setVeh[i==0?(mGR.setVeh.length-1):i-1].x + .8f);
		}
		
		drawTex(gl, mGR.mTex_Arrow[0], -.89f, 0.51f);
		if(mGR.mSel == 3)
			drawTex(gl, mGR.mTex_Arrow[0], -.89f, 0.51f);
		drawTex(gl, mGR.mTex_Arrow[1], 0.89f, 0.51f);
		if(mGR.mSel == 4)
			drawTex(gl, mGR.mTex_Arrow[1], 0.89f, 0.51f);
		
		
		drawTex(gl, mGR.mTex_Play[0],mGR.settingPlay,0.73f);
		if(mGR.mSel == 1)
			drawTex(gl, mGR.mTex_Play[1],mGR.settingPlay,0.73f);
		
		float add = 2;
		drawTex(gl, mGR.mTex_Buy[0],mGR.settingPlay + add, -.43f);
		if(mGR.mSel == 8)
		{
			drawTex(gl, mGR.mTex_Buy[0],mGR.settingPlay + add, -.43f);
			drawTex(gl, mGR.mTex_Buy[0],mGR.settingPlay + add, -.43f);
			drawTex(gl, mGR.mTex_Buy[0],mGR.settingPlay + add, -.43f);
		}
		drawTex(gl, mGR.mTex_Buy[1],mGR.settingPlay + add, -.43f);
		
		
		
		if(!mGR.setVeh[mGR.mCarSel].lock)
		{
			if(mGR.settingPlay<-.75f)
				mGR.settingPlay+=.04f;
			else
				mGR.settingPlay = -.75f;
			
		}
		else{
			if(mGR.settingPlay>-1.25f)
				mGR.settingPlay-=.04f;
		}
		
	}
	void drawAnimation(GL10 gl)
	{
		if(vy<0)
			drawTex(gl, mGR.mTex_Features[3],0,moveY);
		else
			drawTex(gl, mGR.mTex_Features[4],0,moveY);
		moveY+=vy;
		if(moveY<-1.5f)
			vy = .03f;
		if(M.GameScreen == M.GameCarSelection)
		{
			if(moveY>-.6f)
				vy =-.03f;
		}
		else if(moveY>-.3f)
			vy =-.03f;
			
			
	}
	boolean HandleCarSelection(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.75f,0.73f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1; // Play
		}
		if(CircRectsOverlap(+.75f,0.73f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;// Back
		}
		if(CircRectsOverlap(-.89f, 0.51f, mGR.mTex_Arrow[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;// Left Move
		}
		if(CircRectsOverlap(0.89f, 0.51f, mGR.mTex_Arrow[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;// Right Move
		}
		if(CircRectsOverlap(-.67f, 0.2f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;// Free Regular Coin
		}
		if(CircRectsOverlap(0.67f, 0.2f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;// Free Bulk Coin
		}
		if(CircRectsOverlap(-.6f, -.43f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
//			mGR.mSel = 7;// Free Regular Coin
		}
		if(CircRectsOverlap(0.71f, -.43f, mGR.mTex_Play[0].width()/2, mGR.mTex_Play[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 8;// Free Bulk Coin
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1://Play
				if(!mGR.setVeh[mGR.mCarSel].lock)
				{
					M.loopSoundStop();
					counter =1;
					mGR.gameReset();
					M.GameScreen=M.GameChallenge;
				}
				break;
			case 2://Back
				M.GameScreen = M.GameMenu;
				break;
			case 3://Right
				if(Animation.counter==0)
				{
					Animation.Move = false;
					Animation.counter = 1;
				}
				break;
			case 4://Left
				if(Animation.counter==0)
				{
					Animation.Move = true;
					Animation.counter = 1;
				}
				break;
			case 5:// Regular Coin
				Intent intent = null;
				intent = new Intent(GameRenderer.mContext, MList.class); //yogesh
				GameRenderer.mContext.startActivity(intent);
				break;
			case 6://bulk Coin
				M.loopSoundStop();
				M.GameScreen = M.GameBulk;
				break;
			case 7://Purchase Price
				break;
			case 8://Buy
				if(mGR.setVeh[mGR.mCarSel].lock)
				{
					if(Player.mTotalCoin>=mGR.setVeh[mGR.mCarSel].Price)
					{
						Player.mTotalCoin -= mGR.setVeh[mGR.mCarSel].Price;
						mGR.setVeh[mGR.mCarSel].lock =false;
					}
					else
					{
						try{handler.sendEmptyMessage(1);}catch(Exception e){}
					}
				}
				break;
			}
			if (mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	void setting()
	{
		
//		Log.d("~~ "+delx,dely+" ~~"+counter);
		switch (mGR.mPlayer.forKeyPress) {
		case 1:
			delx -=.01f;
			break;
		case 2:
			delx +=.01f;
			break;
		case 3:
			dely +=.01f;
			break;
		case 4:
			dely -=.01f;
			break;
		}
	}
	void DrawTop(GL10 gl)
	{
		drawTex(gl, mGR.mTex_BackScr	, 0		,0.0f);
		drawTex(gl, mGR.mTex_CoinBuy, 0		,0.0f);
//		drawTex(gl, mGR.mTex_Back, 0.85f	,0.7f);
		for(int i=0;i<4;i++)
		{
			if(mGR.mSel == i+1)
				drawTex(gl, mGR.mTex_Buy[0], .44f,.35f-(i*.337f));
		}
	}
	public boolean HandleTop(MotionEvent event)
	{
		mGR.mSel = 0;
//		Log.d("TAG", Player.mTotalCoin+"  Purchase successful~~~~~~~~~~~~~~~~~~~~~~~~~~~"+mGR.mMainActivity.total);
		for(int i=0;i<4;i++)
		{
			if(CircRectsOverlap(.44f,.35f-(i*.337f),.3f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()),.04f))
			{
				mGR.mSel = i+1;
				break;
			}
		}
		
		
//		if(CircRectsOverlap(0.0f,0.0f,1.0f, .5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.04f))
//		{
//			mGR.mSel = 1;
//		}
		
		if(CircRectsOverlap(0.85f ,0.7f,.2f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 6;//Menu
		}
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			try{
				/*switch(mGR.mSel)
				{
				case 1:
					mGR.mMainActivity.onBuyGold10000(null);
					break;
				case 2:
					mGR.mMainActivity.onBuyGold50000(null);
					break;
				case 3:
					mGR.mMainActivity.onBuyGold150000(null);
					break;
				case 4:
					mGR.mMainActivity.onBuyGold350000(null);
					break;
				case 6:
					M.GameScreen = M.GameCarSelection;
					break;
				}*/
				if (mGR.mSel != 0)
					M.sound1(GameRenderer.mContext, R.drawable.button);
				mGR.mSel = 0;
			}
			catch(Exception e)
			{
//				Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", e.toString());
			}
//			Log.d("TAG", Player.mTotalCoin+"  Purchase successful~~~~~~~~~~~~~~~~~~~~~~~~~~~"+mGR.mMainActivity.total);
		}
		return true;
	}

	
	void FaceBook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.FACEBOOK));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
