package com.hututu.games.popthefootball;

import javax.microedition.khronos.opengles.GL10;


import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

public class LevelSelection {
	
	boolean isDown = false;
	boolean popUp = false;
	
	int mSel;
	int mPage =0;
	int mGameSel;
	int anim = 0;
	int counter =0;
	int ScoreCounter;
	
	float sd;
	float sx;
	float svx;
	float scal = 3;
	float move;
	float fillupdate;
	
	Animation secAnim[];
	Animation Anim[];
	
	SimplePlane mTex_BG, mTex_Holder, /*mTex_HolderS,*/ mTex_AchievB,mTex_HututuB,mTex_SettingB,mTex_ShopB;
	SimplePlane mTex_Bar, mTex_ShopS,/*mTex_BarS,*/ mTex_BStar, mTex_BWatch, mTex_Pluse;//, mTex_Rankbar, mTex_RankbarBG;
	SimplePlane mTex_StingBar,mTex_Reset;// /*mTex_ShopSadow,*/ mTex_freegold ,  ;//, /*mTex_ButtonS,*/ mTex_AdsFree;
	SimplePlane mTex_AchieveBox, /*mTex_IconShadow,*/ mTex_Dot1, mTex_LeaderB,/*mTex_RankbarFil,*/mTex_Panguin;
	SimplePlane mTex_NoAds, mTex_AboutI, mTex_ResetI, mTex_SoundI, mTex_Shop,/*mTex_StingBS,*/ mTex_MusicI;
	SimplePlane mTex_PopUp, /*mTex_PopUpS,*/ mTex_NoThnx, mTex_Header,mTex_StarShow,mTex_StarRed,mTex_Back;
	SimplePlane mTex_MenuI,mTex_Restart,mTex_PlayI,mTex_SettingI, mTex_Dot0, mTex_SY,mTex_Lock,mTex_QI;
	SimplePlane[] mTex_SecEff,mTex_LIcon,mTex_LevelHelp,mTex_Partical, /*mTex_Buy, /*mTex_Free,*/ mTex_AIcon;
	SimplePlane[] mTex_Rank,mTex_RankEye;
	
	
	GameRenderer mGR;
	public LevelSelection(GameRenderer _GameRenderer)
	{
		try{
		mGR=_GameRenderer;
		mTex_BG			= GameRenderer.add("ls/bg.jpg");
		mTex_Holder		= GameRenderer.add("ls/level_box.png");
		
		mTex_StarShow	= GameRenderer.add("ls/shop_button-54.png");
		mTex_AchievB	= GameRenderer.add("ls/achiev_button.png");
		mTex_HututuB	= GameRenderer.add("ls/hututu_button.png");
		mTex_SettingB	= GameRenderer.add("ls/setting_button.png");
		mTex_ShopB		= GameRenderer.add("ls/shop_button.png");
		mTex_Bar		= GameRenderer.add("ls/bar0.png");
//		mTex_BarS		= GameRenderer.add("ls/bar0_sadow.png");
//		mTex_freegold	= GameRenderer.add("ls/freegold.png");
		mTex_BStar		= GameRenderer.add("ls/big_star.png");
		mTex_BWatch		= GameRenderer.add("ls/shop_button-56.png");
		mTex_Pluse		= GameRenderer.add("ls/plus.png");
		
//		mTex_Rankbar	= GameRenderer.add("ls/rankbar.png");
//		mTex_RankbarBG	= GameRenderer.add("ls/rankbar_bg.png");
//		mTex_RankbarFil	= GameRenderer.add("ls/rankbar_fill.png");
		mTex_Panguin	= GameRenderer.add("ls/splash_font.png");
		mTex_ShopS		= GameRenderer.add("ls/shop_win-selectin.png");
		mTex_Shop		= GameRenderer.add("ls/shop_win.png");
//		mTex_ShopSadow	= GameRenderer.add("ls/shop_win_sadow.png");
		
		mTex_Back		= GameRenderer.add("ls/back_button.png");
		mTex_Reset		= GameRenderer.add("ls/reset_button.png");
//		mTex_ButtonS	= GameRenderer.add("ls/icon_shadow0.png");
//		mTex_AdsFree	= GameRenderer.add("ls/adsfree.png");
		mTex_AchieveBox	= GameRenderer.add("ls/achieve_box.png");
//		mTex_IconShadow	= GameRenderer.add("ls/icon_shadow1.png");
		mTex_Dot0		= GameRenderer.add("ls/dot0.png");
		mTex_Dot1		= GameRenderer.add("ls/dot1.png");
		mTex_LeaderB	= GameRenderer.add("ls/score_button.png");
		
		mTex_NoAds		= GameRenderer.add("ls/no_ads.png");
		mTex_AboutI		= GameRenderer.add("ls/play_icon-62.png");
		mTex_ResetI		= GameRenderer.add("ls/reset_icon.png");
		mTex_SoundI		= GameRenderer.add("ls/reset_icon-64.png");
		mTex_StingBar	= GameRenderer.add("ls/setting_bar.png");
//		mTex_StingBS	= GameRenderer.add("ls/setting_bar_sadow.png");
		mTex_MusicI		= GameRenderer.add("ls/sound_icon.png");
		
		mTex_PopUp		= GameRenderer.add("ls/po_pup.png");
//		mTex_PopUpS		= GameRenderer.add("ls/po_pup_sadow.png");
		mTex_NoThnx		= GameRenderer.add("ls/no_thanks.png");
		mTex_Header		= GameRenderer.add("ls/header.png");
		
		mTex_StarRed	= GameRenderer.add("ls/red_star.png");
		mTex_SY			= GameRenderer.add("ls/yellow_star.png");
		
		mTex_MenuI		= GameRenderer.add("ls/pause_icon.png");
		mTex_Restart	= GameRenderer.add("ls/play_icon-25.png");
		mTex_PlayI		= GameRenderer.add("ls/play_icon.png");
		mTex_SettingI	= GameRenderer.add("ls/setting_icon.png");
		mTex_Lock		= GameRenderer.add("ls/lock.png");
		mTex_QI			= GameRenderer.add("ls/shop_button-58.png");
		
		mTex_SecEff	= new SimplePlane[4];
		for(int i=0;i<mTex_SecEff.length;i++)
			mTex_SecEff[i]	= GameRenderer.add("ls/effect_bubble"+i+".png");
		
		
		mTex_Rank	= new SimplePlane[16];
		for(int i=0;i<mTex_Rank.length;i++)
			mTex_Rank[i]	= GameRenderer.add("football/"+(i)+".png");
		
		mTex_RankEye	= new SimplePlane[4];
		mTex_RankEye[0]	= GameRenderer.add("football/b0.png");
		for (int i = 1; i < mTex_RankEye.length; i++) {
			mTex_RankEye[i] = GameRenderer.addRotate("football/b" + (i) + ".png");
		}
		mTex_AIcon = new SimplePlane[40];
		for (int i = 0; i < mTex_AIcon.length; i++) {
			mTex_AIcon[i] = GameRenderer.add("64X64/" + (i + 1) + ".png");
		}
		
		
		mTex_Partical	= new SimplePlane[4];
		for(int i=0;i<mTex_Partical.length;i++)
			mTex_Partical[i]	= GameRenderer.add("ls/partical"+i+".png");
		
		
		mTex_LIcon	= new SimplePlane[11];
		for(int i=0;i<mTex_LIcon.length;i++)
			mTex_LIcon[i]	= GameRenderer.add("level_icon/l"+(i+1)+".png");
		
		
		mTex_LevelHelp	= new SimplePlane[11];
		for(int i=0;i<mTex_LevelHelp.length;i++)
			mTex_LevelHelp[i]	= GameRenderer.add("help/l"+(i+1)+".png");
		
		secAnim			= new Animation[50];
		for(int i=0;i<secAnim.length;i++)
			secAnim[i]	= new Animation();
		
		Anim			= new Animation[50];
		for(int i=0;i<Anim.length;i++)
			Anim[i]	= new Animation();
		
		}catch(Exception e){}
	}
	void Gamelogic()
	{
		sx+=svx;
		if(sx>0 && mGameSel <=0 && !isDown)
		{
			sx=svx =0;
		}
		if(sx<0 && mGameSel >=12 && !isDown)
		{
			sx=svx =0;
		}
		if(Math.abs(sx)>.95 && !isDown)
		{
			if(sx>0 && mGameSel >0)
				mGameSel-=1;
			if(sx<0 && mGameSel <10)
				mGameSel+=1;
			
			sx=svx =0;
			
		}
		
	}

	void Draw_LevelSel(GL10 gl) {
//		 mGR.TStar =99;
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		Group.DrawTransScal(gl, mTex_Bar, -.7f, 0.84f, mSel == 1 ? 1.1f : 1, mSel == 1 ? 0.5f : 1);
		Group.DrawTransScal(gl, mTex_Bar, 0.7f, 0.84f, mSel == 2 ? 1.1f : 1, mSel == 2 ? 0.5f : 1);

		Group.DrawTexture(gl, mTex_BWatch, -.86f, 0.84f);
		Group.DrawTexture(gl, mTex_BStar, 0.86f, 0.84f);
		Group.DrawTexture(gl, mTex_Pluse, -.5f, 0.84f);
		Group.DrawTexture(gl, mTex_Pluse, 0.5f, 0.84f);
		mGR.Draw(gl, "x " + mGR.Clock, 0, -.73f, 0.84f, 0);
		mGR.Draw(gl, mGR.TStar + " / 55", 0, 0.60f, 0.84f, 0);
		
		for(int i = 0;i<11;i++){
			if((i-mGameSel+sx > -2) && (i-mGameSel+sx) < 2){
				Group.DrawTransScal(gl, mTex_Holder , i-mGameSel+sx, 0.1f,mSel==i+100?1.02f:1,mSel==i+100?0.5f:1);
				if(M.UNLOCK[i]<=mGR.TStar){
					Group.DrawTransScal(gl, mTex_LIcon[i] , i-mGameSel+sx, 0.1f,mSel==i+100?1.02f:1,mSel==i+100?0.5f:1);
				} else {
					Group.DrawTransScal(gl, mTex_Lock , i-mGameSel+sx		, .27f,mSel==i+100?1.02f:1,mSel==i+100?0.5f:1);
					Group.DrawTextureS(gl, mTex_BStar , i-mGameSel+sx-.15f	,-.05f,.5f);
					mGR.Draw(gl, M.UNLOCK[i]+" / 55",0, i-mGameSel+sx-.05f	,-.05f,0);
				}
				for(int j=0;j<5;j++)
					if (M.SCORE[i][j] <= mGR.BestScore[i]) {
						Group.DrawTexture(gl, mTex_SY, i-mGameSel+sx+(-.16f+.083f*j), -.245f);
					}
			}
		}
		
		for(int i=0;i<11;i++)
		{
			if(mGameSel==i)
				Group.DrawTexture(gl, mTex_Dot0, -.40f+i*.08f, -.60f);
			else
				Group.DrawTexture(gl, mTex_Dot1, -.40f+i*.08f, -.60f);
		}
		
		
//		Group.DrawTexture(gl, mTex_ButtonS		, -.9f, -.84f);
		Group.DrawTransScal(gl, mTex_Back	, -.9f, -.84f,mSel==4?1.1f:1,mSel==4?0.5f:1);
//		Group.DrawTexture(gl, mTex_ButtonS		, -.7f, -.84f);
		Group.DrawTransScal(gl, mTex_HututuB	, -.7f, -.84f,mSel==5?1.1f:1,mSel==5?0.5f:1);
//		Group.DrawTexture(gl, mTex_ButtonS		, 0.7f, -.84f);
		Group.DrawTransScal(gl, mTex_SettingB	, 0.7f, -.84f,mSel==6?1.1f:1,mSel==6?0.5f:1);
//		Group.DrawTexture(gl, mTex_ButtonS		, 0.9f, -.84f);
		Group.DrawTransScal(gl, mTex_ShopB		, 0.9f, -.84f,mSel==7?1.1f:1,mSel==7?0.5f:1);
		Gamelogic();
		if(popUp)
			DrawJoin(gl);
		
	}
	boolean Handle_LevelSel(MotionEvent event)
	{
		if(popUp)
		{
			return Handle_Join(event);
		}
		mSel =0;
		if(Group.CircRectsOverlap(-.7f,0.84f, mTex_Bar.width()*.4f, mTex_Bar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =1;
		}
		if(Group.CircRectsOverlap(0.7f,0.84f, mTex_Bar.width()*.4f, mTex_Bar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =2;
		}
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =4;
		}
		if(Group.CircRectsOverlap(-.7f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =5;
		}
		if(Group.CircRectsOverlap(0.7f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =6;
		}
		if(Group.CircRectsOverlap(0.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =7;
		}
		for(int i = 0;i<11;i++){
			if(((i-mGameSel+sx > -2) && (i-mGameSel+sx) < 2)
					&& Group.CircRectsOverlap(i-mGameSel+sx, 0, mTex_Holder.width()*.4f, mTex_Holder.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f)){
				mSel = mGameSel +100;
			}
		}
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			sd = Group.screen2worldX(event.getX());
			isDown = true;
		}
		sx = Group.screen2worldX(event.getX())-sd;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			isDown = false;
			if(Math.abs(sx)<.1){
				sx = 0;
				for(int i = 0;i<11;i++){
					if(Group.CircRectsOverlap(i-mGameSel+sx, 0, mTex_Holder.width()*.4f, mTex_Holder.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f)){
						if (M.UNLOCK[i] <= mGR.TStar) {
							LevelLoad();
						}
					}
				}
			}
			else
				if(sx > 0)
					svx =0.2f;
				else
					svx =-.2f;
			

			switch (mSel) {
			case 1:
			case 2:
			case 7:move = 0;
				M.GameScreen = M.GAMEBUY;
				break;
			case 3:
//				popUp = false;
//				if(!GameRenderer.mStart.isSignedIn())
//					popUp = true;
				M.GameScreen = M.GAMESELECT;
				break;
			case 4:
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
				break;
			case 5:
				Group.MoreGame();
				break;
			case 6:
				M.GameScreen = M.GAMESETTING;
				break;
				}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		
		}
		return true;
	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			GameRenderer.mStart.load();
		}
	};
	void LevelLoad()
	{
		if(!GameRenderer.mStart.addFree){
//			M.GameScreen = M.GAMELOAD;
			mGR.root.Counter = 0;
			
			
			M.GameScreen = mGR.mLSelect.mGameSel + 1;
			mGR.mLSelect.resetLevel(M.GameScreen);
			if (!M.HELPSCR[mGR.mLSelect.mGameSel]) {
				M.GameScreen = M.GAMEHELP;
			}
			if (!GameRenderer.mStart.interstitialAdisReady()) {
				try {
					adsHandler.sendEmptyMessage(0);
				} catch (Exception e) {
				}
			}
		}
		else
		{
			M.GameScreen = mGameSel + 1;
			resetLevel(M.GameScreen);
			if (!M.HELPSCR[mGameSel]) {
				M.GameScreen = M.GAMEHELP;
			}
		}
	}
	void Draw_Main(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		Group.DrawTexture (gl, mTex_RankEye[0],0,-.43f);
		Group.DrawTextureR(gl, mTex_RankEye[1],0,-.43f,counter*6,1);
		Group.DrawTextureR(gl, mTex_RankEye[2],0,-.43f,counter*2,1);
		Group.DrawTextureR(gl, mTex_RankEye[3],0,-.43f,-counter,1);
		
		Group.DrawTexture(gl, mTex_Rank[counter%mTex_Rank.length],0,-.43f);
		
		Group.DrawTexture(gl, mTex_Panguin, -.05f, .53f);
		
		Group.DrawTransScal(gl, mTex_Bar	,-.71f,0.84f,mSel==1?1.1f:1,mSel==1?0.5f:1);
		Group.DrawTransScal(gl, mTex_Bar	,0.71f,0.84f,mSel==2?1.1f:1,mSel==2?0.5f:1);
		
		Group.DrawTexture(gl, mTex_BWatch,-.86f,0.84f);
		Group.DrawTexture(gl, mTex_BStar, 0.86f,0.84f);
		Group.DrawTexture(gl, mTex_Pluse, -.5f,0.84f);
		Group.DrawTexture(gl, mTex_Pluse, 0.5f,0.84f);
		mGR.Draw(gl, "x "+mGR.Clock, 0, -.73f,0.84f,0);
		mGR.Draw(gl, mGR.TStar+" / 55" , 0, 0.60f,0.84f,0);
		Group.DrawTransScal(gl, mTex_AchievB	, -.9f, -.84f,mSel==4?1.1f:1,mSel==4?0.5f:1);
		Group.DrawTransScal(gl, mTex_HututuB	, -.7f, -.84f,mSel==5?1.1f:1,mSel==5?0.5f:1);
		Group.DrawTransScal(gl, mTex_SettingB	, 0.7f, -.84f,mSel==6?1.1f:1,mSel==6?0.5f:1);
		Group.DrawTransScal(gl, mTex_ShopB		, 0.9f, -.84f,mSel==7?1.1f:1,mSel==7?0.5f:1);
		counter2++;
		if(counter2%3==0)
			counter++;
	}
	int counter2 =0;
	boolean Handle_Main(MotionEvent event)
	{
		mSel =0;
		if(Group.CircRectsOverlap(-.7f,0.84f, mTex_Bar.width()*.4f, mTex_Bar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =1;
		}
		if(Group.CircRectsOverlap(0.7f,0.84f, mTex_Bar.width()*.4f, mTex_Bar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =2;
		}
		if(Group.CircRectsOverlap(0.0,-.10f, .3f, .5f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =3;
		}
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =4;
		}
		if(Group.CircRectsOverlap(-.7f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =5;
		}
		if(Group.CircRectsOverlap(0.7f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =6;
		}
		if(Group.CircRectsOverlap(0.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =7;
		}
		
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mSel) {
			case 1:
			case 2:
			case 7:move = 0;
				M.GameScreen = M.GAMEBUY;
				break;
			case 3:
				popUp = false;
				if(!GameRenderer.mStart.isSignedIn())
					popUp = true;
				M.GameScreen = M.GAMESELECT;
				break;
			case 4:
				cout=10;
				M.GameScreen = M.GAMEACHIV;
				break;
			case 5:
				Group.MoreGame();
				break;
			case 6:
				M.GameScreen = M.GAMESETTING;
				break;
				}
			if(mSel!=0){
				M.stop();
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			}
			mSel =0;
		}
		return true;
	}
	
	void Draw_About(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
//		Group.DrawTexture(gl, mTex_PopUpS, 0, 0);
		Group.DrawTexture(gl, mTex_PopUp , 0, 0);
		
		mGR.Draw(gl, "!! About Us !!" 	, 0, 0.05f, .60f,1);
		
		mGR.Draw(gl, "Developed & published by" 	, 0, 0.05f, .30f,1);
		mGR.Draw(gl, "hututu games" 				, 0, 0.05f,0.15f,1);
		mGR.Draw(gl, "software pvt. ltd" 				, 0, 0.05f,-.0f,1);
		mGR.Draw(gl, "Version 1.0.3" 		, 0, 0.05f,-.15f,1);
		mGR.Draw(gl, "info@hututugames.com" 		, 0, 0.05f,-.30f,1);
		
//		Group.DrawTexture(gl, mTex_ButtonS		, -.9f, -.84f);
		Group.DrawTransScal(gl, mTex_Back		, -.9f, -.84f,mSel==5?1.1f:1,mSel==5?0.5f:1);
		
	}
	boolean Handle_About(MotionEvent event)
	{
		mSel =0;
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mSel) {
			case 5:
				M.GameScreen = M.GAMESETTING;
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		}
		return true;
	}
	void Draw_SETTING(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		mGR.Draw(gl, "Setting" , 0, 0.05f, .8f,1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, 0.5f-0*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, 0.5f-0*(.33f),mSel==1?1.05f:1,mSel==1?0.5f:1);
		Group.DrawTexture(gl, mTex_AboutI  	 ,-.25f, 0.5f-0*(.33f));
		mGR.Draw(gl, "About" , 0, 0.10f, 0.5f-0*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, 0.5f-1*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, 0.5f-1*(.33f),mSel==2?1.05f:1,mSel==2?0.5f:1);
		Group.DrawTexture(gl, mTex_MusicI  	 ,-.25f, 0.5f-1*(.33f));
		if(!M.sBGValue)
			Group.DrawTextureS(gl, mTex_NoAds  	 ,-.25f, 0.5f-1*(.33f),.5f);
		mGR.Draw(gl, "Music : "+(M.sBGValue?"On":"Off") , 0, 0.10f, 0.5f-1*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, 0.5f-2*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, 0.5f-2*(.33f),mSel==3?1.05f:1,mSel==3?0.5f:1);
		Group.DrawTexture(gl, mTex_SoundI  	 ,-.25f, 0.5f-2*(.33f));
		if(!M.setValue)
			Group.DrawTextureS(gl, mTex_NoAds  	 ,-.25f, 0.5f-2*(.33f),.5f);
		mGR.Draw(gl, "Sound : "+(M.setValue?"On":"Off") , 0, 0.10f, 0.5f-2*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, 0.5f-3*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, 0.5f-3*(.33f),mSel==4?1.05f:1,mSel==4?0.5f:1);
		Group.DrawTexture(gl, mTex_ResetI  	 ,-.25f, 0.5f-3*(.33f));
		mGR.Draw(gl, "Reset Game" , 0		 ,0.10f, 0.5f-3*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_ButtonS		, -.9f, -.84f);
		Group.DrawTransScal(gl, mTex_Back		, -.9f, -.84f,mSel==5?1.1f:1,mSel==5?0.5f:1);
		
	}
	boolean Handle_SETTING(MotionEvent event)
	{
		mSel =0;
		for(int i=0;i<4;i++)
		{
			if(Group.CircRectsOverlap(0.00f, 0.5f-i*(.33f), mTex_StingBar.width()*.4f, mTex_StingBar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				mSel =i+1;
			}
		}
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =5;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mSel) {
			case 1:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 2:
				M.sBGValue=!M.sBGValue;
				break;
			case 3:
				M.setValue=!M.setValue;
				break;
			case 4:
				GameRenderer.mStart.resetGame();
				break;
			case 5:
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		}
		return true;
	}
	
	
	void Draw_ACHIV(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		mGR.Draw(gl, "Achievements: 40/40", 0, 0.05f, 0.8f, 1);
		
		if(((sx >0 && mPage==0)||(sx <0 && mPage==3))&&!isDown){
			float mm = sx >0 ? -2:2;
			sx+=mm*.1f;
			if(Math.abs(sx)<.2f){
				sx =0;
			}
		}
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<5;j++)
			{
//				Group.DrawTexture(gl, mTex_IconShadow, sx-.6f+j*.3f, .4f-i*.5f);
				if((mSel-100)/5 == i && (mSel-100)%5==j)
					Group.DrawTransScal(gl, mTex_AchieveBox, sx-.6f+j*.3f, .4f-i*.5f,1.05f,.5f);
				else
					Group.DrawTexture(gl, mTex_AchieveBox, sx-.6f+j*.3f, .4f-i*.5f);
				
				if(!M.ACHIVE[((i*5+j+mPage*10)/4)%11][(i*5+j+mPage*10)%4])
					Group.DrawTextureS(gl, mTex_QI,sx-.6f+j*.3f, .4f-i*.5f,1);
				else
					Group.DrawTexture(gl, mTex_AIcon[i*5+j+mPage*10], sx-.6f+j*.3f, .4f-i*.5f);
			}
		}
		if((sx >0 && mPage>0)||(sx <0 && mPage<3)){
			float mm = sx >0 ? -2:2;
			for(int i=0;i<2;i++)
			{
				for(int j=0;j<5;j++)
				{
//					Group.DrawTexture(gl, mTex_IconShadow, mm+sx-.6f+j*.3f, .4f-i*.5f);
					Group.DrawTexture(gl, mTex_AchieveBox, mm+sx-.6f+j*.3f, .4f-i*.5f);
					if(sx<0){
						if(M.ACHIVE[((i*5+j+(mPage+1)*10)/4)%11][(i*5+j+(mPage+1)*10)%4])
							Group.DrawTexture(gl, mTex_AIcon[i*5+j+(mPage+1)*10], mm+sx-.6f+j*.3f, .4f-i*.5f);
						else
							Group.DrawTextureS(gl, mTex_QI,mm+sx-.6f+j*.3f, .4f-i*.5f,1f);
					}
					else
					{
						if(M.ACHIVE[((i*5+j+(mPage-1)*10)/4)%11][(i*5+j+(mPage-1)*10)%4])
							Group.DrawTexture(gl, mTex_AIcon[i*5+j+(mPage-1)*10], mm+sx-.6f+j*.3f, .4f-i*.5f);
						else
							Group.DrawTextureS(gl, mTex_QI,mm+sx-.6f+j*.3f, .4f-i*.5f,1);
					}
				}
			}
			if(!isDown)
				sx-=mm*.1f;
			if(Math.abs(sx)>2){
				sx =0;
				mPage+=mm/2;
			}
		}
		for(int i=0;i<4;i++)
		{
			if(mPage==i)
				Group.DrawTexture(gl, mTex_Dot0, -.10f+i*.09f, -.55f);
			else
				Group.DrawTexture(gl, mTex_Dot1, -.10f+i*.09f, -.55f);
		}
//		Group.DrawTexture(gl, mTex_ButtonS		, -.9f, -.84f);
		Group.DrawTransScal(gl, mTex_Back		, -.9f, -.84f,mSel==1?1.1f:1,mSel==1?0.5f:1);
//		Group.DrawTexture(gl, mTex_ButtonS		, 0.7f, -.84f);
		Group.DrawTransScal(gl, mTex_LeaderB	, 0.7f, -.84f,mSel==2?1.1f:1,mSel==2?0.5f:1);
//		Group.DrawTexture(gl, mTex_ButtonS		, 0.9f, -.84f);
		Group.DrawTransScal(gl, mTex_AchievB	, 0.9f, -.84f,mSel==3?1.1f:1,mSel==3?0.5f:1);
		if(cout<10)
		{
//			mTex_PopUpS.drawScal(gl , 0.0f, .0f, 1.3f, 1.1f);
			mTex_PopUp.drawScal(gl	, 0.0f, .0f, 1.3f, 1.1f);
			String str =  GameRenderer.mStart.getString(frog.Achiv_DSC[(mPage*10+cout)%frog.Achiv_DSC.length]);
			String[] parts = str.split("-");
			mGR.Draw(gl, parts[0], 0, 0.0f, .35f, 1);
			mGR.Draw(gl, parts[1], 0, 0.0f,0.15f, 1);
			if(cout%4<2){
				int t = M.ACHIVE_VALUE[((mPage*10+cout)/4)%11][(mPage*10+cout)%4]-mGR.Total[mGameSel];
				if(t>0)
					mGR.Draw(gl, t+" left.", 0,0.0f,0.03f, 1);
			}
			Group.DrawTextureS(gl, mGR.mTex_Skip  	,0.65f,.35f,1.0f);
			Group.DrawTexture(gl, mTex_AchieveBox, 0.0f,-.25f);
			if(M.ACHIVE[((mPage*10+cout)/4)%11][(mPage*10+cout)%4])
				Group.DrawTexture(gl, mTex_AIcon[(mPage*10+cout)%mTex_AIcon.length],0.0f,-.25f);
			else
				Group.DrawTextureS(gl, mTex_QI,0.0f,-.25f,1);
		}
		if(popUp)
			DrawJoin(gl);
	}
	boolean Handle_ACHIV(MotionEvent event)
	{
		if(popUp)
			return Handle_Join(event);
		if(cout<10)
		{
			if(MotionEvent.ACTION_UP == event.getAction() &&
					Group.CircRectsOverlap(0.65f,.35f, mTex_NoAds.width()*.4f, mTex_NoAds.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				cout =10;
			}
			return true;
		}
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =1;
		}
		if(Group.CircRectsOverlap(0.7f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =2;
		}
		if(Group.CircRectsOverlap(0.9f, -.84f, mTex_AchievB.width()*.4f, mTex_AchievB.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =3;
		}
		
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<5;j++)
			{
				if(Group.CircRectsOverlap(sx-.6f+j*.3f, .4f-i*.5f, mTex_AchieveBox.width()*.4f, mTex_AchieveBox.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
				{
					mSel = 100 + i*5+j;
				}
			}
		}
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			sd = Group.screen2worldX(event.getX());
			isDown = true;
		}
		sx = Group.screen2worldX(event.getX())-sd;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(Math.abs(sx) < .10)
				sx =0;
			isDown = false;
			switch (mSel) {
			case 1:
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
				break;
			case 2:
				if(GameRenderer.mStart.isSignedIn()){
					GameRenderer.mStart.onShowLeaderboardsRequested();
				}
				else
					popUp = true;
				break;			
			case 3:
				if(GameRenderer.mStart.isSignedIn()){
					GameRenderer.mStart.onShowAchievementsRequested();
				}
				else
					popUp = true;
				break;
			case 100:case 101:case 102:case 103:case 104:case 105:case 106:case 107:case 108:case 109:
				if(sx == 0)
					cout = mSel -100;
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		}
		return true;
	}

	void Draw_BUY(GL10 gl) {
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		Group.DrawTexture(gl, mTex_Shop, 0, 0);
		
		for(int i=0;i<3;i++){
			if(i==mSel-1)
				Group.DrawTexture(gl, mTex_ShopS,-.425f+.424f*i, .057f);
		}
		Group.DrawTransScal(gl, mTex_Bar	,-.7f,0.84f,1,1);
		Group.DrawTransScal(gl, mTex_Bar	,0.7f,0.84f,1,1);
		Group.DrawTexture(gl, mTex_BWatch,-.86f,0.84f);
		Group.DrawTexture(gl, mTex_BStar, 0.86f,0.84f);
		mGR.Draw(gl, "x "+mGR.Clock, 0, -.73f,0.84f,0);
		mGR.Draw(gl, mGR.TStar+" / 55" , 0, 0.60f,0.84f,0);
		
		Group.DrawTransScal(gl, mTex_Back	, -.9f, -.84f,mSel==32?1.1f:1,mSel==32?0.5f:1);
		Group.DrawTransScal(gl, mTex_Reset	, 0.9f, -.84f,mSel==33?1.1f:1,mSel==33?0.5f:1);
	}

	boolean Handle_BUY(MotionEvent event) {
		mSel = 0;
		for(int i=0;i<3;i++){
			if(Group.CircRectsOverlap(-.425f+.424f*i, .057f, mTex_ShopS.width()*.4f, mTex_ShopS.Height()*.8f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
				mSel =i+1;
		}
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =32;
		}
		if(Group.CircRectsOverlap(0.9f, -.84f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =33;
		}
		
		if (MotionEvent.ACTION_UP == event.getAction()) {

			switch (mSel) {
			case 1:
				mGR.mMainActivity.onBuyHTT_REMOVE_ADS(null);
				break;
			case 2:
				mGR.mMainActivity.onBuyHTT_THREE_STARS(null);
				break;
			case 3:
				mGR.mMainActivity.onBuyHTT_TENCLOCKS(null);
				break;
			case 32:
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
				break;
			case 33:
				GameRenderer.mStart.resetGame();
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		}
		return true;
	}
	
	
	/***************************************************************/
	
	/***************************************************************/
	int cout=0;
	void Draw_AchiDcs(GL10 gl)
	{
		mTex_PopUp.drawScal(gl	, 0.2f, .35f, 1.3f, .8f);
		String str =  GameRenderer.mStart.getString(frog.Achiv_DSC[(mGameSel*4+cout)%frog.Achiv_DSC.length]);
		String[] parts = str.split("-");
		mGR.Draw(gl, parts[0], 0, 0.2f, .5f, 1);
		mGR.Draw(gl, parts[1], 0, 0.2f,0.3f, 1);
		if(cout%4<2){
			int t = M.ACHIVE_VALUE[mGameSel%11][cout%4]-mGR.Total[mGameSel];
			if(t>0)
				mGR.Draw(gl, t+" left", 0,0.2f,0.15f, 1);
		}
		Group.DrawTexture(gl, mGR.mLsix.mTex_Fruit[0],cout*.28f,-.03f);
		Group.DrawTextureS(gl, mGR.mTex_Skip  	,0.85f,.62f,1f);
	}
	boolean Handle_AchiDcs(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(Group.CirCir(0.88f,.60f, mTex_NoAds.width()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				cout =5;
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			}
		}
		return true;
	}
	int unlockLevels=0;
	void calcStars()
	{
		mGR.TStar = 0;
		for (int i = 0; i < M.SCORE.length; i++){
			for(int j=0;j<5;j++)
				if (M.SCORE[i][j] <= mGR.BestScore[i]){
					mGR.TStar++;
				}
		}
		mGR.TStar+=mGR.BuyStar;
		for (int i = 0; i < M.SCORE.length; i++){
			if(M.UNLOCK[i]>mGR.TStar){
				unlockLevels = i;
				break;
			}
			if(i == 9)
				unlockLevels = 10;
		}
//		System.out.println("["+(mGR.TStar)+" >= "+M.UNLOCK[unlockLevels]+"] [tstars = "+mGR.TStar+"][u = "+(unlockLevels)+"]");
	}
	void LevelComplete()
	{
		M.stop();
		popUp = false;
		isDown = false;
		cout=5;
		anim=0;
		scal =4;
		M.GameScreen = M.GAMELVLCOMP;
		ScoreCounter =0;
		fillupdate = mGR.mRankFill;
		if((mGR.mRankFill/16)<10)
			mGR.mRankFill=(mGR.mScore*.032f)/((mGR.mRankFill/16)+1)+mGR.mRankFill;
		GameRenderer.mStart.Submitscore(frog.Learboard[mGameSel], mGR.BestScore[mGameSel]);
		
		if (GameRenderer.mStart.interstitialAdisReady()) {
			try {
				GameRenderer.mStart.show();
			} catch (Exception e) {
			}
		}
		
		if((int)Math.abs(fillupdate/16)==(int)Math.abs(mGR.mRankFill/16)-1){
			isDown = true;
			mGR.Clock+=3;
//			GameRenderer.mStart.UnlockAchievement(frog.Achivemnet_RANK[(int)(mGR.mRankFill/16)]);
			M.sound11(GameRenderer.mContext, R.drawable.new_rank_unlock);
		}
		calcStars();
		
	}
	void Draw_LevelComplete(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		Group.DrawTexture(gl, mTex_Header, 0,0.88f);
		Group.DrawTexture(gl, mTex_Header, 0,-.80f);
		
		mGR.Draw(gl, "Level Complete !", 0, 0f, .84f, 1);
		
		
//		Group.DrawTexture(gl, mTex_BarS	,0.7f,0.84f);
		Group.DrawTexture(gl, mTex_Bar	,0.7f,0.84f);
		Group.DrawTexture(gl, mTex_BStar	,0.88f,0.84f);
		mGR.Draw(gl,  mGR.TStar+" / 55", 0, .65f, .84f, 1);
		
		if(ScoreCounter < mGR.mScore){
			ScoreCounter+=5;
			if(ScoreCounter > mGR.mScore)
				ScoreCounter = mGR.mScore;
		}
		
		mGR.Draw(gl, "Score : "+ScoreCounter, 0,-.60f, .40f, 1);
		
		for (int i = 0; i < 5; i++) {
			Group.DrawTexture(gl, mTex_StarShow,-.88f+i*.17f,.16f);
			Group.DrawTexture(gl, mTex_StarRed ,-.88f+i*.17f,.16f);
			if(M.SCORE[mGameSel][i]<=mGR.mScore)
			{
				if(anim>i)
					Group.DrawTexture(gl, mTex_SY,-.88f+i*.17f,.16f);
				if(anim==i){
					scal*=.85f;
					Group.DrawTransScal(gl, mTex_SY,-.88f+i*.17f,.16f,scal,1);
					if(scal<=1){
						M.sound1(GameRenderer.mContext, R.drawable.front_screen_star);
						anim++;
						if(anim == 5)
							popUp = true;
						else if(M.SCORE[mGameSel][anim]>=mGR.mScore)
						{
							popUp = true;
						}
						scal =4;
					}
				}
			}
			mGR.Draw_Num(gl, ""+M.SCORE[mGameSel][i], 1,-.91f+i*.17f,-.02f);
		}
		
		mGR.Draw(gl, "Best Score ", 0,0.60f, .50f, 1);
		mGR.Draw(gl, ""+mGR.BestScore[mGameSel], 0,0.60f, .35f, 1);
		
		mGR.Draw(gl, "Achievements", 0,.47f,0, 1);
		for (int i = 0; i < 4; i++) {
			Group.DrawTransScal(gl, mTex_AchieveBox,i*.28f,-.33f,mSel==i+4?1.1f:1,mSel==i+4?0.5f:1);
			if(M.ACHIVE[mGameSel][i]){
				Group.DrawTexture(gl, mTex_AIcon[mGameSel*4+i],i*.28f,-.33f);
			}else
				{
					Group.DrawTextureS(gl, mTex_QI,i*.28f,-.33f,1);
				}
				
		}
		if(fillupdate < mGR.mRankFill && popUp){
			fillupdate+=.05f;
			if(fillupdate >= mGR.mRankFill){
				fillupdate = mGR.mRankFill;
				Animtin(0,-.88f);
			}
		}
		Group.DrawTransScal(gl, mTex_StarShow	, -.9f, -.84f,mSel==1?1.1f:1,mSel==1?0.5f:1);
		Group.DrawTexture(gl, mTex_PlayI, -.92f, -.84f);
		Group.DrawTexture(gl, mTex_PlayI, -.89f, -.84f);
		Group.DrawTransScal(gl, mTex_StarShow	, 0.9f, -.84f,mSel==3?1.1f:1,mSel==3?0.5f:1);
		Group.DrawTexture(gl, mTex_Restart, 0.9f, -.84f);
		if(fillupdate >= mGR.mRankFill)
		{
			//Draw_Animtin(gl);
		}
		if(cout<4)
			Draw_AchiDcs(gl);
		if(isDown)
		{
			DrawRenkLevelUnlock(gl);
		}
		
	}
	
	boolean Handle_LevelComplete(MotionEvent event)
	{
		mSel =0;
		if(isDown)
		{
			if((MotionEvent.ACTION_UP == event.getAction())&&
					Group.CircRectsOverlap(.52f,.35f, mTex_NoAds.width()*.4f, mTex_NoAds.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				isDown = false;
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			}
			return true;
		}
		if(cout<4)
			Handle_AchiDcs(event);
		if(Group.CircRectsOverlap(-.9f, -.84f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =1;
		}
		if(Group.CircRectsOverlap(0.7f, -.84f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
//			mSel =2;
		}
		if(Group.CircRectsOverlap(0.9f, -.84f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =3;
		}
		for (int i = 0; i < 4; i++) {
			if(Group.CircRectsOverlap(i*.28f,-.33f, mTex_Back.width()*.4f, mTex_Back.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				mSel =i+4;
			}	
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mSel) {
			case 1:
				popUp = false;
//				if(!GameRenderer.mStart.isSignedIn())
//					popUp = true;
				M.GameScreen = M.GAMESELECT;
				break;
			case 2:
				anim = 0;
				scal = 4;
				break;
			case 3:
				LevelLoad();
				break;
			case 4:case 5:case 6:case 7:
				cout=mSel-4;
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel =0;
		}
		return true ;
	}
	void resetLevel(int level)
	{
		newLevel = false;
		ScoreCounter =1000;
		calcStars();
		switch(level){
		case M.GAMEONE:
			mGR.mLOne.GameResart();
			break;
		case M.GAMETWO:
			mGR.mLTwo.GameResart();
			break;
		case M.GAMETREE:
			mGR.mLTree.GameResart();
			break;
		case M.GAMEFOUR:
			mGR.mLFour.GameResart();
			break;
		case M.GAMEFIVE:
			mGR.mLFive.GameResart();
			break;
		case M.GAMESIX:
			mGR.mLsix.GameResart();
			break;
		case M.GAMENINE:
			mGR.mLNine.GameResart();
			break;
		case M.GAMETEN:
			mGR.mLTen.GameResart();
			break;
		case M.GAMEELEVEN:
			mGR.mLEleven.GameResart();
			break;
		case M.GAMETWELVE:
			mGR.mLTwelve.GameResart();
			break;
		case M.GAMETHIRTEEN:
			mGR.mLThirteen.GameResart();
			break;
		}
		if(M.HELPSCR[mGameSel])
		{
			BGSound();
		}
	}
	boolean newLevel = false;
	void ContinueLevel(int level)
	{
		switch(level){
		case M.GAMEONE:
			mGR.mLOne.GameContinue();
			break;
		case M.GAMETWO:
			mGR.mLTwo.GameContinue();
			break;
		case M.GAMETREE:
			mGR.mLTree.GameContinue();
			break;
		case M.GAMEFOUR:
			mGR.mLFour.GameContinue();
			break;
		case M.GAMEFIVE:
			mGR.mLFive.GameContinue();
			break;
		case M.GAMESIX:
			mGR.mLsix.GameContinue();
			break;
		case M.GAMENINE:
			mGR.mLNine.GameContinue();
			break;
		case M.GAMETEN:
			mGR.mLTen.GameContinue();
			break;
		case M.GAMEELEVEN:
			mGR.mLEleven.GameContinue();
			break;
		case M.GAMETWELVE:
			mGR.mLTwelve.GameContinue();
			break;
		case M.GAMETHIRTEEN:
			mGR.mLThirteen.GameContinue();
			break;
		}
		BGSound();
	}
	void Draw_LevelPause(GL10 gl)
	{
		float yy = .65f;
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, yy-0*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, yy-0*(.33f),mSel==1?1.05f:1,mSel==1?0.5f:1);
		Group.DrawTexture(gl, mTex_PlayI  	 ,-.25f, yy-0*(.33f));
		mGR.Draw(gl, "Continue" , 0		 ,0.10f, yy-0*(.33f),1);
		
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, yy-1*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, yy-1*(.33f),mSel==2?1.05f:1,mSel==2?0.5f:1);
		Group.DrawTexture(gl, mTex_Restart   ,-.27f, yy-1*(.33f));
		mGR.Draw(gl, "Restart Level" , 0	 , 0.10f,yy-1*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, yy-2*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, yy-2*(.33f),mSel==3?1.05f:1,mSel==3?0.5f:1);
		Group.DrawTexture(gl, mTex_PlayI  	 ,-.27f, yy-2*(.33f));
		Group.DrawTexture(gl, mTex_PlayI  	 ,-.235f, yy-2*(.33f));
		mGR.Draw(gl, "Choose game", 0		 , 0.10f,yy-2*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, yy-3*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, yy-3*(.33f),mSel==4?1.05f:1,mSel==4?0.5f:1);
		Group.DrawTexture(gl, mTex_MenuI  	 ,-.25f, yy-3*(.33f));
		mGR.Draw(gl, "Main Menu", 0		 , 0.10f,yy-3*(.33f),1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, yy-4*(.33f));
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, yy-4*(.33f),mSel==5?1.05f:1,mSel==5?0.5f:1);
		Group.DrawTexture(gl, mTex_SettingI	 ,-.25f, yy-4*(.33f));
		mGR.Draw(gl, "Setting" , 0		 ,0.10f, yy-4*(.33f),1);
		
		
	}
	boolean Handle_LevelPause(MotionEvent event){
		float yy = .65f;
		mSel = 0;
		for(int i=0;i<5;i++)
		{
			if(Group.CircRectsOverlap(0.00f, yy-i*(.33f), mTex_StingBar.width()*.4f, mTex_StingBar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
			{
				mSel =i+1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			mGR.mLSelect.calcStars();
			switch (mSel) {
			case 1:
				M.GameScreen = mGameSel+1;
				ContinueLevel(M.GameScreen);
				break;
			case 2:
				LevelLoad();
				break;
			case 3:
				popUp = false;
//				if(!GameRenderer.mStart.isSignedIn())
//					popUp = true;
				M.GameScreen = M.GAMESELECT;
				break;
			case 4:
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
				break;
			case 5:
				M.GameScreen = M.GAMESETTING;
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel = 0;
		}
		return true;
	}
	void Draw_Help(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		
//		Group.DrawTexture(gl, mTex_PopUpS, 0, 0);
		Group.DrawTexture(gl, mTex_PopUp , 0, 0);
		
		Group.DrawTexture(gl, mTex_LevelHelp[mGameSel%mTex_LevelHelp.length], 0, 0);
		
		mGR.Draw(gl, "!! Help !!" 	, 0, 0.04f, .60f,1);
		
//		Group.DrawTexture(gl, mTex_StingBS 	 ,0.00f, -.46f);
		Group.DrawTransScal(gl, mTex_StingBar,0.00f, -.6f,mSel==1?1.05f:1,mSel==1?0.5f:1);
		Group.DrawTexture(gl, mTex_PlayI	,-.25f, -.6f);
		mGR.Draw(gl, "Continue" , 0		 	,0.10f, -.6f,1);
	}
	boolean Handle_Help(MotionEvent event){
		mSel = 0;
		if(Group.CircRectsOverlap(0.00f, -.6f, mTex_StingBar.width()*.4f, mTex_StingBar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mSel) {
			case 1:
				M.GameScreen = mGameSel+1;
				if(M.HELPSCR[mGameSel])
					ContinueLevel(M.GameScreen);
				else
					M.HELPSCR[mGameSel] = true;
				BGSound();
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel = 0;
		}
		return true;
	}
	void BGSound()
	{
//		if(M.GameScreen == M.GAMEFOUR)
//			M.play(GameRenderer.mContext, R.drawable.l_4_coster);
//		else
			M.play(GameRenderer.mContext,R.drawable.bg);
	}
	void GTime(GL10 gl) {
		Group.DrawTransScal(gl, mTex_StarShow, 0.72f, 0.86f,mSel==1?1.05f:1,mSel==1?0.5f:1);
		Group.DrawTransScal(gl, mTex_QI, 0.72f, 0.86f,mSel==1?1.05f:1,mSel==1?0.5f:1);
		Group.DrawTransScal(gl, mGR.mTex_PauseB, 0.90f, 0.86f,mSel==2?1.05f:1,mSel==2?0.5f:1);
		Group.DrawTexture(gl, mTex_Bar, -.83f, 0.86f);
		long s =  (mGR.mGameTime - System.currentTimeMillis());
		if (!mGR.isPlay)
			s =  (mGR.mGameTime);
		if (s < 0)
			s = 0;
		if (s == 0 && mGR.timesUp == 0) {
			M.stop();
			mGR.timesUp = 60;
			Group.sx = Group.sy = .1f;
			M.sound10(GameRenderer.mContext, R.drawable.timeup);
		}
		String str = ((s / 1000) < 10 ? "0" : "") + (s / 1000) + ":"+ (((s % 1000) / 10) < 10 ? "0" : "") + ((s % 1000) / 10);
		mGR.Draw(gl, str, 0, -.79f, 0.86f, 1);
		for (int i = 0; i < 5; i++) {
			int nxv = (i > 0 ? M.SCORE[mGameSel][i - 1] : 0);
			if (M.SCORE[mGameSel][i] <= mGR.mScore) {
				Group.DrawTexture(gl, mGR.mTex_Star[6], -.95f + i* 0.06f, 0.67f);
			} else if (M.SCORE[mGameSel][i] > mGR.mScore && (nxv) < mGR.mScore) {
				int mn = (M.SCORE[mGameSel][i] - nxv) / 5;
				nxv = (mGR.mScore - nxv) / mn;
				Group.DrawTexture(gl, mGR.mTex_Star[nxv], -.95f+ i * 0.06f, 0.67f);
			} else
				Group.DrawTexture(gl, mGR.mTex_Star[0], -.95f + i* 0.06f, 0.67f);
		}
		if (mGR.timesUp > 1) {
			Group.DrawTextureS(gl, mGR.mTex_Trans, 0, 0,3.8f);
			mGR.timesUp--;
			mGR.DrawS(gl, "Times Up !", 0, 0, Group.sx);
			Group.sx += Group.sy;
			if (Group.sx > 2)
				Group.sy = -.1f;
			if (Group.sx < .2)
				Group.sy = 0.1f;
			if (Group.sx < 1.1 && Group.sy < 0) {
				Group.sx = 1;
				Group.sy = 0;
			}
			if (mGR.timesUp == 1
					&& mGR.ClockCount > 0) {
				LevelComplete();
			}

		} else if (mGR.ClockCount > 1) {
			Group.DrawTextureS(gl, mGR.mTex_Trans, 0, 0,3.8f);
			if(mGR.ClockCount % 40 == 0)
				M.sound9(GameRenderer.mContext, R.drawable.jump_12);
			mGR.ClockCount--;
			mGR.DrawS(gl, "" + (1+(mGR.ClockCount / 40)), 0, 0,Group.sx);
			Group.sx += Group.sy;
			if (Group.sx > 2)
				Group.sy = -.1f;
			if (Group.sx < .2)
				Group.sy = 0.1f;
			if (Group.sx < 1.1 && Group.sy < 0) {
				Group.sx = 1;
				Group.sy = 0;
			}
			if (mGR.ClockCount % 40 == 0)
				Group.sx = Group.sy = .1f;
			if (mGR.ClockCount == 2) {
				mGR.mGameTime = System.currentTimeMillis() + 5000;
				mGR.timesUp = 0;
			}
		} else if (s == 0) {
			Group.DrawTextureS(gl, mGR.mTex_Trans, 0, 0,3.8f);
//			Group.DrawTexture(gl, mTex_PopUpS, 0, 0);
			Group.DrawTexture(gl, mTex_PopUp, 0, 0);
			mGR.Draw(gl, "Bonus time (+5 Sec)", 0, -.0f, 0.70f, 1);

//			Group.DrawTexture(gl, mTex_BarS, -.0f,0.20f);
			Group.DrawTexture(gl, mTex_BWatch, -.15f,0.20f);
			mGR.Draw(gl, ":  " + mGR.Clock, 0, 0.05f,0.2f, 0);

//			Group.DrawTexture(gl, mTex_BarS, -.0f,-.15f);
			Group.DrawTransScal(gl, mTex_Bar, -.0f,-.15f, mSel == 3 ? 1.05f : 1, mSel == 3 ? 0.5f : 1);
			if(mGR.Clock>0)
				mGR.Draw(gl, "Use Clock !", 0, 0.04f, -.15f, 1);
			else
				mGR.Draw(gl, "Buy Clock !", 0, 0.04f, -.15f, 1);

//			Group.DrawTexture(gl, mTex_StingBS, 0,-.5f);
			Group.DrawTransScal(gl, mTex_NoThnx	, 0.00f,-.6f, mSel == 4 ? 1.05f : 1, mSel == 4 ? 0.5f : 1);
			mGR.Draw(gl, "No Thanks !", 0		, 0.04f,-.6f, 1);

		}
		if(timeanim<10)
		{
			for(int i=0;i<secAnim.length;i++){
				if(secAnim[i].z>.1f){
					Group.DrawTransScal(gl,mTex_SecEff[mGameSel%mTex_SecEff.length], secAnim[i].x,secAnim[i].y,secAnim[i].z,1);
					secAnim[i].updateEffect();
				}
			}
			mGR.Draw(gl, "+"+time+" Sec", 0, 0, 0.9f, 1);
			timeanim++;
		}
		if(unlockLevels<10)
		{
			int m=0;
			for(int j=0;j<5;j++){
				if (M.SCORE[mGameSel][j] <= mGR.BestScore[mGameSel]){
					m++;
				}
			}
//			System.out.println("["+(mGR.TStar+m)+" >= "+M.UNLOCK[unlockLevels]+"] ~~~~~~~~~~~~~~~~~~~[tstars = "+mGR.TStar+"] [m = "+m+"] [u = "+(unlockLevels)+"]");
			if(mGR.TStar+m >=M.UNLOCK[unlockLevels] && !newLevel){
				ScoreCounter =0;
				newLevel = true;
				for (int i = 0; i < M.SCORE.length; i++){
					if(M.UNLOCK[i]>=mGR.TStar){
						unlockLevels = i+1;
						break;
					}
					if(i == 9)
						unlockLevels = 10;
				}
//				System.out.println("["+(mGR.TStar+m)+" >= "+M.UNLOCK[unlockLevels]+"] [tstars = "+mGR.TStar+"] [m = "+m+"] [u = "+(unlockLevels)+"]");
			}
		}
		if(ScoreCounter<50)
		{
			mTex_Bar.drawScal(gl, 0, .8f,1.5f,1);
			mGR.Draw(gl, "New  Level Unlock!", 0, .04f, .8f, 1);
			ScoreCounter++;
		}
	}
	void ClockScreen(MotionEvent event)
	{
		mSel = 0;
		if(Group.CircRectsOverlap(0.72f	,0.86f, mTex_QI.width()*.4f, mTex_QI.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mSel = 1;//Help
		}
		if(Group.CircRectsOverlap(0.9f	,0.86f, mTex_QI.width()*.4f, mTex_QI.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mSel = 2;//Pause;
		}
		if(Group.CircRectsOverlap(-.0f,-.15f, mGR.mLSelect.mTex_Bar.width()*.4f, mGR.mLSelect.mTex_Bar.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mSel = 3;
		}
		if(Group.CircRectsOverlap(-.0f,-.6f, mGR.mLSelect.mTex_Bar.width()*.4f, mGR.mLSelect.mTex_Bar.Height()*.4f,
				Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()), .02f))
		{
			mSel =4;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mSel) {
			case 1:
				if(!(mGR.mGameTime<System.currentTimeMillis() && mGR.isPlay && mGR.timesUp < 5 && mGR.ClockCount < 5)){
					mGR.mGameTime -= System.currentTimeMillis();
					M.GameScreen = M.GAMEHELP;
					M.stop();
				}
				break;

			case 2:
				if(!(mGR.mGameTime<System.currentTimeMillis() && mGR.isPlay && mGR.timesUp < 5 && mGR.ClockCount < 5)){
					mGR.mGameTime -= System.currentTimeMillis();
					M.GameScreen = M.GAMELVLPUSE;
					M.stop();
				}
				break;
			case 3:
				if(mGR.mGameTime<System.currentTimeMillis() && mGR.isPlay && mGR.timesUp < 5 && mGR.ClockCount < 5)
				{
					if(mGR.Clock>0){
						mGR.ClockCount = 120;
						mGR.Clock --;
						Group.sx=Group.sy=.1f;
						BGSound();
					}else{move = 0;
						M.GameScreen = M.GAMEBUY;
					}
				}
				break;
			case 4:
				if(mGR.mGameTime<System.currentTimeMillis() && mGR.isPlay && mGR.timesUp < 5 && mGR.ClockCount < 5)
					mGR.mLSelect.LevelComplete();
				break;
			}
			if(mSel!=0)
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			mSel = 0;
		}
	}

	void IncTime(int t)
	{
		for(int i=0;i<secAnim.length;i++)
			secAnim[i].setEffect(0, 0.9f);
		mGR.mGameTime +=t;
		timeanim = 0;
		time = t/1000;
		M.sound14(GameRenderer.mContext, R.drawable.point_collect);
	}
	int timeanim = 51;
	int time = 1;
	
	void Animtin(float x,float y)
	{
		int j =0;
		for(int i=0;j<30 && i<Anim.length;i++){
			if(Anim[i].z<=0.1){
				Anim[i].setEffect(x, y);
				j++;
			}
		}
	}
	void Draw_Animtin(GL10 gl)
	{
		for(int i=0;i<Anim.length;i++){
			if(Anim[i].z>.1f){
				Group.DrawTransScal(gl,mTex_Partical[i%mTex_Partical.length], Anim[i].x,Anim[i].y,Anim[i].z,1);
				Anim[i].updateEffect();
			}
		}
	}
	
	void Achievement(boolean fall,float dis)
	{
		if(M.ACHIVE_VALUE[mGameSel][0]<=mGR.Total[mGameSel] && !M.ACHIVE[mGameSel][0])
		{
			M.ACHIVE[mGameSel][0] = true;
			GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+0]);
		}
		if(M.ACHIVE_VALUE[mGameSel][1]<=mGR.Total[mGameSel] && !M.ACHIVE[mGameSel][1])
		{
			M.ACHIVE[mGameSel][1] = true;
			GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+1]);
		}
		if(M.ACHIVE_VALUE[mGameSel][2]<=mGR.mScore && !M.ACHIVE[mGameSel][2])
		{
			M.ACHIVE[mGameSel][2] = true;
			GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+2]);
		}
		if(mGR.mScore>mGR.BestScore[mGameSel])
		{
			mGR.BestScore[mGameSel] = mGR.mScore;
		}
		if(mGameSel==0||mGameSel==2||mGameSel==3||mGameSel==4||mGameSel==5||/*mGameSel==7||*/mGameSel==8){
			
			if(mGR.isPlay){
				int s = (int)(mGR.mGameTime-System.currentTimeMillis());
				if(s > 15000 && M.ACHIVE_VALUE[mGameSel][3]<=mGR.mScore && !M.ACHIVE[mGameSel][3])
				{
					M.ACHIVE[mGameSel][3] = true;
					GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+3]);
				}
			}
		}
		if(mGameSel==1||mGameSel==6||mGameSel==7||mGameSel==9||mGameSel==10)
		{
			if(!fall && M.ACHIVE_VALUE[mGameSel][3]<=mGR.mScore && !M.ACHIVE[mGameSel][3])
			{
				M.ACHIVE[mGameSel][3] = true;
				GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+3]);
			}
		}
//		if(mGameSel==3)
//		{
//			if(dis > 100 && M.ACHIVE_VALUE[mGR.mLSelect.mGameSel][3]<=mGR.mScore && !M.ACHIVE[mGR.mLSelect.mGameSel][3])
//			{
//				M.ACHIVE[mGR.mLSelect.mGameSel][3] = true;
//				GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+3]);
//			}
//		}
//		if(mGameSel==6)
//		{
//			if( M.ACHIVE_VALUE[mGR.mLSelect.mGameSel][3]<=dis && !M.ACHIVE[mGR.mLSelect.mGameSel][3])
//			{
//				M.ACHIVE[mGR.mLSelect.mGameSel][3] = true;
//				GameRenderer.mStart.UnlockAchievement(frog.Achivemnet[mGameSel*4+3]);
//			}
//		}
	}

	void DrawRenkLevelUnlock(GL10 gl) {
		counter++;
		Group.DrawTextureS(gl, mGR.mTex_Trans, 0, 0, 3.8f);
		Group.DrawTexture(gl, mTex_PopUp, 0, 0);
		mGR.Draw(gl, "Congratulation", 0, 0, 0.20f, 1);
		Group.DrawTexture(gl, mTex_BWatch, -.18f, -.1f);
		mGR.Draw(gl, "x3  free", 0, -.04f, -.1f, 0);
		Group.DrawTextureS(gl, mGR.mTex_Skip, 0.49f, .33f, 1f);
	}
	
	void DrawJoin(GL10 gl)
	{
		counter++;
		Group.DrawTextureS(gl, mGR.mTex_Trans, 0, 0,3.8f);
		Group.DrawTextureS(gl, mTex_PopUp , 0, 0,1.5f);
		mGR.Draw(gl, "Challenge your friends and", 0, 0, 0.30f, 1);
		mGR.Draw(gl, "gain achievements!"		 , 0, 0, 0.15f, 1);
		Group.DrawTransScal(gl,mTex_StingBar,0.00f, -.2f,mSel==100?1.05f:1,mSel==100?0.5f:1);
		mGR.Draw(gl, "Join" 			, 0 ,0.05f, -.2f,1);
		Group.DrawTransScal(gl, mGR.mTex_Skip  	,0.75f,.50f,mSel==200?1.05f:1,mSel==200?0.5f:1);
	}
	boolean Handle_Join(MotionEvent event)
	{
		mSel =0;
		if(Group.CircRectsOverlap(0.00f, -.2f, mTex_StingBar.width()*.4f, mTex_StingBar.Height()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =100;
		}
		if(Group.CirCir(0.75f,.50f, mTex_NoAds.width()*.4f, Group.screen2worldX(event.getX()), Group.screen2worldY(event.getY()),.05f))
		{
			mSel =200;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mSel) {
			case 100:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				popUp = false;
				break;
			case 200:
				popUp = false;
				break;
			}
			mSel =0;
		}
		return true;
	}
	void check()
	{
		for (int i = 0; i < M.SCORE.length; i++){
			if(M.UNLOCK[i]>=mGR.TStar){
				unlockLevels = i;
//				break;
			}
			if(i == 9)
				unlockLevels = 10;
		}
	}
//	void Draw_New(GL10 gl)
//	{
//		if(Group.sx>.5){
//			mGR.TStar += 5;
//			Group.sx = 0;
//		}
//		Group.DrawTexture(gl, mTex_BG, 0, 0);
//		System.out.println("["+(mGR.TStar)+"] [u = "+(unlockLevels)+"]");
//		if(unlockLevels<10)
//		{
//			int m= (int)(Group.sx*10);
//			if(mGR.TStar+m >=M.UNLOCK[unlockLevels]){
//				ScoreCounter =0;
//				for (int i = 0; i < M.SCORE.length; i++){
//					if(M.UNLOCK[i]>=mGR.TStar){
//						unlockLevels = i+1;
//						break;
//					}
//					if(i == 9)
//						unlockLevels = 10;
//				}
//				System.out.println("["+(mGR.TStar+m)+" >= "+M.UNLOCK[unlockLevels]+"] [tstars = "+mGR.TStar+"] [m = "+m+"] [u = "+(unlockLevels)+"]");
//			}
//		}
//		if(ScoreCounter<100)
//		{
//			mTex_Bar.drawScal(gl, 0, .8f,1.5f,1);
//			mGR.Draw(gl, "New  Level Unlock!", 0, .04f, .8f, 1);
//			ScoreCounter++;
//		}
//	}
}
