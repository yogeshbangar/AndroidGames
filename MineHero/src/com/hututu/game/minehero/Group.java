package com.hututu.game.minehero;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	int Counter =0;
	float sx,sy;
	byte change = 0;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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
	
	@Override
	public void draw(GL10 gl) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 100)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Draw_About(gl);
			break;
		case M.GAMEOVER:
			Draw_GameOver(gl);
			break;
		case M.GAMEBUY:
			Draw_Buy(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		}
		Counter++;
		if(change>0)
		{
			mGR.mTex_BG.drawRGBTS(gl, 0,0,0,0,0,change*.1f,1,2);
			change--;
		}
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {

		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Handle_About(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEBUY:
			Handle_Buy(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	int sel = 0;
	/***********************Pause Start*********************/
	void Draw_Pause(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, .5f);
		DrawTexture(gl, mGR.mTex_BG, 0,.5f-mGR.mTex_Splash.Height());
		DrawTexture(gl, mGR.mTex_Board,0,0);
		DrawTexture(gl, mGR.mTex_Pause, 0, .52f);
		for(int i=0;i<3;i++)
			DrawTransScal(gl,mGR.mTex_Button, 0,.32f-.20f*i,sel == i+1?1.1f:1,sel == i+1?.5f:1);
		DrawTransScal(gl,mGR.mTex_Cont, 0,.32f-.20f*0,sel == 1?1.1f:1,sel == 1?.5f:1);
		DrawTransScal(gl,mGR.mTex_Retry,0,.32f-.20f*1,sel == 2?1.1f:1,sel == 2?.5f:1);
		DrawTransScal(gl,mGR.mTex_Menu, 0,.32f-.20f*2,sel == 3?1.1f:1,sel == 3?.5f:1);
		
		for(int i =0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_Icon,-.62f+.41f*i,-.28f,sel == i+4?1.1f:1,sel == i+4?.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Achiv ,-.62f+.41f*0,-.28f,sel == 4?1.1f:1,sel == 4?.5f:1);
		DrawTransScal(gl, mGR.mTex_LBoard,-.62f+.41f*1,-.28f,sel == 5?1.1f:1,sel == 5?.5f:1);
		DrawTransScal(gl, mGR.mTex_fb	 ,-.62f+.41f*2,-.28f,sel == 6?1.1f:1,sel == 6?.5f:1);
		DrawTransScal(gl, mGR.mTex_hututu,-.62f+.41f*3,-.28f,sel == 7?1.1f:1,sel == 7?.5f:1);
		
	}

	boolean Handle_Pause(MotionEvent event) {
		sel = 0;
		for(int i=0;i<3;i++)
		if (CircRectsOverlap( 0,.32f-.20f*i,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = i+1;
		}
		for(int i=0;i<4;i++)
			if (CircRectsOverlap(-.62f+.41f*i,-.28f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i+4;
			}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay_bg);
				break;
			case 2:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay_bg);
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();//leader Board
				break;
			case 6:
				facebook();
				break;
			case 7:
				MoreGame();
				break;
			}
			change = 10;
			M.sound2(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	/*********************** Pause End **********************/
	/***********************Buy Start*********************/
	void Draw_Shop(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, .5f);
		DrawTexture(gl, mGR.mTex_BG, 0,.5f-mGR.mTex_Splash.Height());
		mGR.mTex_Board1.drawScal(gl, .035f,0, 1.2f, 1);
		DrawTexture(gl, mGR.mTex_STitle,0,.82f);
		for(int i=0;i<7;i++){
			mGR.mTex_BG.drawDarkS(gl			,0.011f, .640f-.21f*i, .89f, .11f, 1);
			DrawTransScal(gl, mGR.mTex_Upg		,0.690f, .640f-.21f*i,sel == i+1?1.1f:1,sel == i+1?.5f:1);
			DrawTexture(gl, mGR.mTex_Store[i]	,-.790f, .640f-.21f*i);
			if(mGR.mChar.Power[i]<10)
				drawNumber(gl,""+mGR.mChar.Power[i]*40,-.610f, .640f-.21f*i-.025f, 0.9f, 3, 0);
			DrawTexture(gl, mGR.mTex_Txt[i]		,-.380f, .640f-.21f*i+.021f);
			mGR.mTex_UBox.drawRGBTS(gl, .084f, .640f-.21f*i, .3f, .3f, .3f, 1, 17.5f, 1.3f);
			for(int j=0;j<10;j++){
				DrawTexture(gl, mGR.mTex_UBox,-.20f+.063f*j, .640f-.21f*i);
				if(j<mGR.mChar.Power[i]){
					mGR.mTex_UBox.drawRGBTS(gl, -.20f+.063f*j, .640f-.21f*i, 1, 0, 0, 1, 1, 1);
				}
			}
		}
		DrawTexture(gl, mGR.mTex_TCash,-.59f,-.81f);
		drawNumber(gl,mGR.mMoney+"", -.2f, -.81f, 1.1f, 2, 0);
		
		DrawTransScal(gl,mGR.mTex_Button, 0.68f,-.82f,sel == 8?1.1f:1,sel == 8?.5f:1);
		DrawTransScal(gl,mGR.mTex_Start  , 0.68f,-.82f,sel == 8?1.1f:1,sel == 8?.5f:1);
	}

	boolean Handle_Shop(MotionEvent event) {
		sel = 0;
		for(int i =0;i<7;i++){
			if (mGR.mChar.Power[i]<10 &&
					CircRectsOverlap(0.690f, .640f-.21f*i,mGR.mTex_Button.width() * 6.0f,mGR.mTex_Button.Height() * 0.6f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i+1;
			}
		}
		if (CircRectsOverlap(0.68f,-.82f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 8:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay_bg);
				break;
				default:
					if(mGR.mChar.Power[sel-1]<10 ){
						if(mGR.mChar.Power[sel-1]*40 <= mGR.mMoney){
							mGR.mChar.Power[sel-1]++;
							mGR.mMoney -=(mGR.mChar.Power[sel-1]*40);
						}else{
							M.GameScreen = M.GAMEBUY;
						}
					}
					break;
			}
			change = 10;
			M.sound2(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	/*********************** Shop End **********************/
	/***********************Buy Start*********************/
	void Draw_Buy(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, .5f);
		DrawTexture(gl, mGR.mTex_BG, 0,.5f-mGR.mTex_Splash.Height());
		DrawTexture(gl, mGR.mTex_Board1,0,0);
		DrawTexture(gl, mGR.mTex_BuyF,0,.82f);
		for(int i=0;i<4;i++){
			mGR.mTex_BG.drawDarkS(gl,-.015f, .5f-.26f*i, .72f, .11f, 1);
			DrawTransScal(gl, mGR.mTex_BuyBtn, .53f, .5f - .26f * i, sel == i + 1 ? 1.1f : 1, sel == i + 1 ? .5f : 1);
			drawNumber(gl, ":"+i+";99", 0, .5f-.26f*i, 1.1f, 1, 0);
			DrawTexture(gl, mGR.mTex_Coin[Counter%10],-.65f, .5f-.26f*i);
		}
		drawNumber(gl," 1000", -.55f, .5f-.26f*0, 1.1f, 0, 0);
		drawNumber(gl," 3000", -.55f, .5f-.26f*1, 1.1f, 0, 0);
		drawNumber(gl," 7000", -.55f, .5f-.26f*2, 1.1f, 0, 0);
		drawNumber(gl,"15000", -.55f, .5f-.26f*3, 1.1f, 0, 0);
		
		DrawTransScal(gl,mGR.mTex_Button, 0,-.62f,sel == 6?1.1f:1,sel == 6?.5f:1);
		DrawTransScal(gl,mGR.mTex_STitle, 0,-.62f,sel == 6?1.1f:1,sel == 6?.5f:1);
		DrawTexture(gl, mGR.mTex_TCash,-.48f,-.81f);
		drawNumber(gl,mGR.mMoney+"", -.1f, -.81f, 1.1f, 2, 0);
	}

	boolean Handle_Buy(MotionEvent event) {
		sel = 0;
		for(int i =0;i<4;i++){
			if (CircRectsOverlap(.53f, .5f-.26f*i,mGR.mTex_Button.width() * 0.8f,mGR.mTex_Button.Height() * 0.6f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i+1;
			}
		}
		if (CircRectsOverlap(0,-.62f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 6;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			M.sound2(R.drawable.click);
			switch (sel) {
			case 1:
				mGR.mInApp.onBuyGold10000(null);// buy
				break;
			case 2:
				mGR.mInApp.onBuyGold20000(null);// buy
				break;
			case 3:
				mGR.mInApp.onBuyGold30000(null);// buy
				break;
			case 4:
				mGR.mInApp.onBuyGold40000(null);// buy
				break;
			case 6:
				M.GameScreen = M.GAMESHOP;
				break;
			}
			change = 10;
			sel = 0;
		}
		return true;
	}

	/*********************** Buy End **********************/
	/***********************GameOver Start*********************/
	void gameOver(){
		GameRenderer.mStart.Achivment();
		if (mGR.mPoint > mGR.mBPoint)
			mGR.mBPoint = mGR.mPoint;
		M.GameScreen = M.GAMEOVER;
		mGR.mMoney+=(int)((mGR.mCoin+mGR.mCrystal*4)*(1+mGR.mChar.Power[6]*.1f));
		GameRenderer.mStart.ShowInterstitial();
		M.stop();
	}
	void Draw_GameOver(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, .5f);
		DrawTexture(gl, mGR.mTex_BG, 0,.5f-mGR.mTex_Splash.Height());
		DrawTexture(gl, mGR.mTex_Board1,0,0);
		DrawTexture(gl, mGR.mTex_Over,0,.82f);
		mGR.mTex_BG.drawDarkS(gl,-.015f, .67f, .72f, .07f, 1);
		DrawTexture(gl, mGR.mTex_Score,-.26f,.67f);
		drawNumber(gl, ""+mGR.mPoint, 0,.667f, 1, 0, 0);
		mGR.mTex_BG.drawDarkS(gl,-.015f, .31f, .72f, .46f, 1);
		
		DrawTexture(gl, mGR.mTex_Store[6]	,-.40f,.47f);
		DrawTexture(gl, mGR.mTex_Arow,-.12f,0.47f);
		drawNumber(gl, ""+mGR.mCoin,.07f,0.47f, 1, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Crystal,-.40f,.30f);
		DrawTexture(gl, mGR.mTex_Arow,-.12f,.30f);
		drawNumber(gl, ""+mGR.mCrystal, .07f,.30f, 1, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Total,-.22f,.08f);
		drawNumber(gl, ""+(int)((mGR.mCoin+mGR.mCrystal*4)*(1+mGR.mChar.Power[6]*.1f)), .1f,.08f, 1.2f, 0, 0);
		for(int i =0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_Icon,-.62f+.41f*i,-.18f,sel == i+1?1.1f:1,sel == i+1?.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Achiv,-.62f+.41f*0,-.18f,sel == 0+1?1.1f:1,sel == 0+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_LBoard,-.62f+.41f*1,-.18f,sel == 1+1?1.1f:1,sel == 1+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_fb,-.62f+.41f*2,-.18f,sel == 2+1?1.1f:1,sel == 2+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_hututu,-.62f+.41f*3,-.18f,sel == 3+1?1.1f:1,sel == 3+1?.5f:1);
		
		DrawTransScal(gl,mGR.mTex_Button, 0,-.42f,sel == 5?1.1f:1,sel == 5?.5f:1);
		DrawTransScal(gl,mGR.mTex_Retry , 0,-.42f,sel == 5?1.1f:1,sel == 5?.5f:1);
		
		DrawTransScal(gl,mGR.mTex_Button, 0,-.62f,sel == 6?1.1f:1,sel == 6?.5f:1);
		DrawTransScal(gl,mGR.mTex_Menu  , 0,-.62f,sel == 6?1.1f:1,sel == 6?.5f:1);
		DrawTexture(gl, mGR.mTex_TCash,-.48f,-.81f);
		drawNumber(gl,mGR.mMoney+"", -.1f, -.82f, 1.1f, 2, 0);
	}

	boolean Handle_GameOver(MotionEvent event) {
		sel = 0;
		for(int i =0;i<4;i++){
			if (CircRectsOverlap(-.62f+.41f*i,-.18f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
				sel = i+1;
			}
		}
		if (CircRectsOverlap(0,-.42f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 5;
		}
		if (CircRectsOverlap(0,-.62f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 6;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//leader Board
				break;
			case 3:
				facebook();
				break;
			case 4:
				MoreGame();
				break;
			case 5:
				M.GameScreen = M.GAMESHOP;
				break;
			case 6:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			change = 10;
			M.sound2(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	/*********************** GameOver End **********************/
	/***********************Menu Start*********************/
	float My[]={1.28f,0.00f,-1.28f};
	Animation menu = new Animation();
	void Draw_Menu(GL10 gl){
		for(int i =0;i<My.length;i++){
			DrawTexture(gl, mGR.mTex_Splash, 0, My[i]);
			My[i]+=.1f;
		}
		for(int i =0;i<My.length;i++){
			if(My[i]>1.8f){
				My[i] = My[i==0?2:i-1] - mGR.mTex_Splash.Height();
			}
		}
		menu.manu();
		DrawTexture(gl, mGR.mTex_SChar[Counter%2], menu.x+.05f, menu.y-.24f);
		DrawTexture(gl, mGR.mTex_SChar[2], 0, .64f);
		
		for(int i=0;i<4;i++)
			DrawTransScal(gl,mGR.mTex_Button, -.67f, -.36f - .19f * i,sel == i+1?1.1f:1,sel == i+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Start	, -.67f, -.36f - .19f * 0,sel == 0+1?1.1f:1,sel == 0+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_About	, -.67f, -.36f - .19f * 1,sel == 1+1?1.1f:1,sel == 1+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Help		, -.67f, -.36f - .19f * 2,sel == 2+1?1.1f:1,sel == 2+1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Buy		, -.67f, -.36f - .19f * 3,sel == 3+1?1.1f:1,sel == 3+1?.5f:1);
	}

	boolean Handle_Menu(MotionEvent event) {
		sel = 0;
		for (int i = 0; i < 4; i++) {
			if (CircRectsOverlap(-.67f, -.36f - .19f * i,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),
					.02f)) {
				sel = i + 1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay_bg);
				break;
			case 2:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				M.GameScreen = M.GAMEBUY;
				break;
			}
			change = 10;
			M.sound2(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	/*********************** Menu End **********************/
	
	
	/***********************About Start*********************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, .5f);
		DrawTexture(gl, mGR.mTex_BG, 0,.5f-mGR.mTex_Splash.Height());
		
		if(M.GameScreen == M.GAMEABOUT){
			DrawTexture(gl, mGR.mTex_Board,0,0);
			DrawTexture(gl, mGR.mTex_AFont, 0, .52f);
			DrawTexture(gl, mGR.mTex_AboutTxt, 0, 0);
		}else{
			DrawTexture(gl, mGR.mTex_Board1,0,0);
			DrawTexture(gl, mGR.mTex_HScr, -.07f, .06f);
		}
		DrawTransScal(gl,mGR.mTex_Button, .65f,-.91f,sel == 1?1.1f:1,sel == 1?.5f:1);
		DrawTransScal(gl,mGR.mTex_Back, .65f,-.91f,sel == 1?1.1f:1,sel == 1?.5f:1);
	}

	boolean Handle_About(MotionEvent event) {
		sel = 0;
		if (CircRectsOverlap(.65f,-.91f,mGR.mTex_Button.width() * .5f,mGR.mTex_Button.Height() * .4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.02f)) {
			sel = 1;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			change = 10;
			M.sound2(R.drawable.click);
			sel = 0;
		}
		return true;
	}

	/*********************** About End **********************/
	
	/***********************GamePlay Start*********************/
	void setCoinAnim(float _x,float _y){
		for(int i=0;i<mGR.mAniCoin.length;i++){
			if(!mGR.mAniCoin[i].isin()){
				mGR.mAniCoin[i].set(_x, _y, mGR.mChar.x, mGR.mChar.y);
				break;
			}
		}
	}
	void setCoin(float _x, float _y) {
		for (int i = 0; i < mGR.mTileRow.length; i++) {
			if (mGR.mTileRow[i].y < _y + .2f) {
				for (int j = 0; j < mGR.mTileRow[i].tile.length; j++) {
					if (mGR.mTileRow[i].tile[j].no != 0 && mGR.mTileRow[i].tile[j].obj == 0) {
						mGR.mTileRow[i].tile[j].obj = 1;
					}
				}
			}
		}
	}

	void setAnimation(float _x, float _y) {
		if(M.mRand.nextBoolean())
			M.sound10(R.drawable.wooden_breack0);
		else
			M.sound11(R.drawable.wooden_breack1);
		for (int i = 0, k = 0; i < mGR.mAnim.length && k < 6; i++) {
			if (!mGR.mAnim[i].isin()) {
				mGR.mAnim[i].set(_x, _y);
				k++;
			}
		}
	}
	void setOpp(float _x,float _y){
		for(int i=0;i<mGR.mMonster.length;i++){
			if(!mGR.mMonster[i].isin()){
				mGR.mMonster[i].set(_x, _y);
				break;
			}
		}
	}
	void setMonBlast(float _x,float _y){
		for(int i =0;i<mGR.mMonBlast.length;i++){
			if(mGR.mMonBlast[i].img>=mGR.mTex_MonBlast.length){
				mGR.mMonBlast[i].set(_x, _y, 0);
				break;
			}
		}
	}
	void gameLogic() {
		
		mGR.dist += mGR.spd;
		mGR.Score+= mGR.spd;
		for (int i = 0; i < mGR.mBG.length; i++) {
			mGR.mBG[i] += mGR.spd*.2f;
		}
		for (int i = 0; i < mGR.mTileRow.length; i++) {
			if (mGR.mTileRow[i].y < 1.6)
				mGR.mTileRow[i].update(mGR.spd);
		}
		for (int i = 0; i < mGR.mBG.length; i++) {
			if (mGR.mBG[i] > 1.7f)
				mGR.mBG[i] = mGR.mBG[i == 0 ? mGR.mBG.length - 1 : (i-1)] - mGR.mTex_BG.Height();
		}
		if (mGR.dist > .4f) {
			if ((mGR.Score % 20f) < 17.5) {
				mGR.dist = 0;
				if (mGR.imgreset) {
					M.sound9(R.drawable.stage_change);
					mGR.imgreset = false;
					mGR.Tileimg++;
					mGR.Tileimg %= mGR.mTex_Tile.length;
				}
				if (M.mRand.nextInt(5) == 1) {
					for (int i = 0; i < mGR.mSlider.length; i++) {
						if (!mGR.mSlider[i].isin()) {
							mGR.mSlider[i].set(0, -1.2f);
							break;
						}
					}
				} else {
					for (int i = 0; i < mGR.mTileRow.length; i++) {
						if (mGR.mTileRow[i].y > 1.5) {
							int box = M.mRand.nextInt(mGR.Action.length);
							if (box == 3 || box == 4) {
								byte arr[] = { 3, 0, 0, 0, 0, 0, 0, 0, 3 };
								mGR.mTileRow[i].set(-1.2f, arr);
								i++;
								while (i < mGR.mTileRow.length) {
									if (mGR.mTileRow[i].y > 1.5) {
										mGR.mTileRow[i].set(-1.2f
												- mGR.mTex_Tile[0].Height(),
												mGR.Action[box]);
										i = mGR.mTileRow.length;
										mGR.dist = -mGR.mTex_Tile[0].Height();
									}
									i++;

								}
							} else {
								mGR.mTileRow[i].set(-1.2f, mGR.Action[box]);
								if (mGR.mChar.CoinBox > 0) {
									mGR.mChar.CoinBox--;
									for (int j = 0; j < mGR.mTileRow[i].tile.length; j++) {
										if (mGR.mTileRow[i].tile[j].no != 0 && mGR.mTileRow[i].tile[j].obj == 0) {
											mGR.mTileRow[i].tile[j].obj = 1;
										}
									}
								}
							}
							break;
						}
					}
				}
			} else if (!mGR.imgreset) {
				mGR.imgreset = true;
			}
		}
		
		
		{
			boolean tuch = false;
			for (int i = 0; i < mGR.mSlider.length; i++) {
				if (mGR.mSlider[i].update(mGR.spd)) {
					if (Rect2RectIntersection(mGR.mSlider[i].x,mGR.mSlider[i].y,
							mGR.mTex_WoodBox.width() * .9f,mGR.mTex_Tile[0].Height() * .8f, 
							mGR.mChar.x, mGR.mChar.y, mGR.mTex_Tile[0].width() * .8f,mGR.mTex_Tile[0].Height() * .8f)) {
						tuch = true;
						mGR.mSlider[i].y = mGR.mChar.y - mGR.mTex_Tile[0].Height()*.78f;
					}
				}
			}
			for (int i = 0; i < mGR.mTileRow.length && !tuch; i++) {
				if (mGR.mTileRow[i].y < 1.2f) {
					for (int j = 0; j < mGR.mTileRow[i].tile.length && !tuch; j++) {
						if (mGR.mTileRow[i].tile[j].no != 0) {
							if (Rect2RectIntersection(-.888f + j * .222f,mGR.mTileRow[i].y,
									mGR.mTex_Tile[0].width() * .6f,mGR.mTex_Tile[0].Height() * .8f, 
									mGR.mChar.x, mGR.mChar.y, mGR.mTex_Tile[0].width() * .6f,mGR.mTex_Tile[0].Height() * .8f)) {
								if(mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height() * .4f<mGR.mChar.y){
									tuch = true;
									mGR.mTileRow[i].y = mGR.mChar.y - mGR.mTex_Tile[0].Height()*.78f;
									switch (mGR.mTileRow[i].tile[j].obj) {
									case 1:
										mGR.mCoin++;//Coin
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=5;
										M.sound3(R.drawable.coin);
										break;
									case 2:
										mGR.mCrystal++;//Crystal
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=6;
										M.sound3(R.drawable.coin);
										break;
									case 3:
										mGR.mChar.setMegnat();//Magnet
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=7;
										M.sound4(R.drawable.poswer0);
										mGR.mChamak.set(mGR.mChar.x, mGR.mChar.y, 10);
										break;
									case 4:
										mGR.mChar.setTimer();//Timer
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=8;
										M.sound4(R.drawable.poswer0);
										mGR.mChamak.set(mGR.mChar.x, mGR.mChar.y, 10);
										break;
									case 5:
										mGR.mChar.setCoinBox();
										setCoin(mGR.mChar.x, mGR.mChar.y);//Coin Box
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=9;
										M.sound5(R.drawable.poswer1);
										mGR.mChamak.set(mGR.mChar.x, mGR.mChar.y, 10);
										break;
									case 6:
										mGR.mChar.setDrill();//Drill
										mGR.mTileRow[i].tile[j].obj =0;
										mGR.mPoint+=10;
										M.sound5(R.drawable.poswer1);
										mGR.mChamak.set(mGR.mChar.x, mGR.mChar.y, 10);
										break;
									}
									if(mGR.mTileRow[i].tile[j].no == 2 && mGR.mTileRow[i].tile[j].reset == 0){
										mGR.mTileRow[i].tile[j].reset = 5;
										if (mGR.mChar.Drill > 0) {
											mGR.mTileRow[i].tile[j].reset = 8;
										}
									}
									if (mGR.mTileRow[i].tile[j].no == 1
											&& mGR.mTileRow[i].tile[j].reset == 0
											&& mGR.mChar.Drill > 0) {
										mGR.mTileRow[i].tile[j].reset = 7;
										mGR.mDrill.set(-.888f + j * .222f,mGR.mTileRow[i].y, 0);
									}
								}
							}
						}
						if (mGR.mChar.megnat > 0 && mGR.mTileRow[i].tile[j].obj == 1) {
							if (Rect2RectIntersection(-.888f + j * .222f, mGR.mTileRow[i].y,
									mGR.mTex_Tile[0].width() * .99f, mGR.mTex_Tile[0].Height() * .99f,
									mGR.mChar.x, mGR.mChar.y, 2, 1)) {
								mGR.mTileRow[i].tile[j].obj = 0;
								setCoinAnim(-.888f + j * .222f, mGR.mTileRow[i].y);
								mGR.mCoin++;
								mGR.mPoint+=4;
							}
						}
					}
				}
			}
			if(tuch){
				if(mGR.spd!=0){
					M.sound8(R.drawable.longjump);
				}
				mGR.spd = 0;
			}else{
				mGR.spd = 0.06f;
//				if(mGR.spd<.06f)
//					mGR.spd += 0.004f;
			}
		}
		for (int k = 0; k < mGR.mMonster.length; k++) {
			for (int i = 0; i < mGR.mTileRow.length && mGR.mMonster[k].check(); i++) {
				if (mGR.mTileRow[i].y < 1.2f) {
					for (int j = 0; j < mGR.mTileRow[i].tile.length && mGR.mMonster[k].check(); j++) {
						if (mGR.mTileRow[i].tile[j].no != 0) {
							if (Rect2RectIntersection(-.888f + j * .222f, mGR.mTileRow[i].y,
									mGR.mTex_Tile[0].width() * .6f, mGR.mTex_Tile[0].Height() * .8f,
									mGR.mMonster[k].x, mGR.mMonster[k].y,
									mGR.mTex_Tile[0].width() * .6f, mGR.mTex_Tile[0].Height() * .8f)) {
								if (mGR.mTileRow[i].y + mGR.mTex_Tile[0].Height() * .1f < mGR.mMonster[k].y) {
									mGR.mMonster[k].y = mGR.mTileRow[i].y + mGR.mTex_Tile[0].Height() * .78f;
									mGR.mMonster[k].tuch =true;
								}
							}
						}
					}
				}
			}
		}
		for (int k = 0; k < mGR.mMonster.length; k++){
			if(mGR.mMonster[k].update()){
				if (Rect2RectIntersection(0, mGR.mChackra.y-.1f,2, .05f,
						mGR.mMonster[k].x, mGR.mMonster[k].y,
						mGR.mTex_Tile[0].width() * .6f, mGR.mTex_Tile[0].Height() * .8f)) {
					setMonBlast(mGR.mMonster[k].x, mGR.mMonster[k].y);
					mGR.mMonster[k].y = 100;	
				}
				if (Rect2RectIntersection(mGR.mChar.x, mGR.mChar.y, mGR.mTex_Tile[0].width() * .6f,mGR.mTex_Tile[0].Height() * .8f,
						mGR.mMonster[k].x, mGR.mMonster[k].y,
						mGR.mTex_Tile[0].width() * .6f, mGR.mTex_Tile[0].Height() * .8f)) {
					setMonBlast(mGR.mMonster[k].x, mGR.mMonster[k].y);
					if(mGR.mMonster[k].y+.02f <mGR.mChar.y){
						mGR.mPoint+=10;
					}else{
						mGR.mChar.setslow();
					}
					M.sound1(R.drawable.blast0);
					mGR.mMonster[k].y = 100;	
				}
			}
		}
		if(mGR.mChackra.y < .2f){
			M.sound6(R.drawable.cutter);
		}
		if (mGR.mCharBlast.img == -1 &&
				Rect2RectIntersection(0, mGR.mChackra.y-.05f,2, .05f,
				mGR.mChar.x, mGR.mChar.y, mGR.mTex_Tile[0].width() * .6f, mGR.mTex_Tile[0].Height() * .8f)) {
			mGR.mCharBlast.set(mGR.mChar.x, mGR.mChar.y,0);
			mGR.mAnim[0].set(mGR.mChar.x, mGR.mChar.y);
			if(mGR.mChar.x>0)
				mGR.mAnim[0].vx = -.01f;
			else
				mGR.mAnim[0].vx = 0.01f;
			mGR.mAnim[0].vy = 0.08f;
			mGR.mChar.y = -10;
			M.sound7(R.drawable.distroy);
		}
		if(Counter %mGR.newMoster==0 && mGR.Score > 15){
			setOpp(-1,-.3f);
			if(mGR.newMoster>50)
				mGR.newMoster--;
		}
		
		if (mGR.mChar.update() && Counter % 5 == 0 && mGR.spd ==0) {
			for (int i = 0; i < mGR.mDust.length; i++) {
				if (mGR.mDust[i].vy <= .11f) {
					mGR.mDust[i].setDust(mGR.mChar.x
							+ (mGR.mChar.spd > 0 ? -.07f : 0.07f),
							mGR.mChar.y - .05f);
					break;
				}
			}
		}
		mGR.mChackra.update(mGR.spd,mGR.mChar.Timer);
		
	}
	int counter2;
	void Draw_Gameplay(GL10 gl) {
		for (int i = 0; i < mGR.mBG.length; i++){
			DrawTexture(gl, mGR.mTex_BG, 0, mGR.mBG[i]);
		}
		for (int i = 0; i < mGR.mSlider.length; i++) {
			if (mGR.mSlider[i].isin()) {
				DrawTexture(gl, mGR.mTex_Slider, mGR.mSlider[i].x,mGR.mSlider[i].y);
			}
		}
		for (int i = 0; i < mGR.mTileRow.length; i++) {
			if (mGR.mTileRow[i].y < 1.2f) {
				for (int j = 0; j < mGR.mTileRow[i].tile.length; j++){
					if (mGR.mTileRow[i].tile[j].no != 0){
						switch (mGR.mTileRow[i].tile[j].obj) {
						case 1:
							DrawTexture(gl, mGR.mTex_Coin[Counter%10], -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.6f);
							break;
						case 2:
							DrawTexture(gl, mGR.mTex_Crystal, -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.8f);
							break;
						case 3:
							DrawTexture(gl, mGR.mTex_Store[2], -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.8f);
							break;
						case 4:
							DrawTexture(gl, mGR.mTex_Store[3], -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.8f);
							break;
						case 5:
							DrawTexture(gl, mGR.mTex_Store[4], -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.8f);
							break;
						case 6:
							DrawTexture(gl, mGR.mTex_Store[5], -.888f + j * .222f, mGR.mTileRow[i].y+mGR.mTex_Tile[0].Height()*.8f);
							break;
							
						}
						
						if (mGR.mTileRow[i].tile[j].no == 1)
							DrawTexture(gl, mGR.mTex_Tile[mGR.Tileimg], -.888f + j * .222f, mGR.mTileRow[i].y);
						else if (mGR.mTileRow[i].tile[j].no == 3)
							DrawTexture(gl, mGR.mTex_WoodBox, -.888f + j * .222f, mGR.mTileRow[i].y);
						else
							DrawTexture(gl, mGR.mTex_Wood, -.888f + j * .222f, mGR.mTileRow[i].y+.05f);
					}
				}
			}
		}
		for(int i=0;i<mGR.mDust.length;i++){
			if(mGR.mDust[i].forDust()){
				DrawTransScal(gl, mGR.mTex_Dust, mGR.mDust[i].x, mGR.mDust[i].y, 1, mGR.mDust[i].vy);
			}
		}
		
		if (mGR.mChar.Drill > 0) {
			DrawTexture(gl, mGR.mTex_Car[10], mGR.mChar.x, mGR.mChar.y+(Counter%2)*.01f);
		} else {
			if (mGR.mChar.slow < 1) {
				DrawTexture(gl, mGR.mTex_Car[mGR.mChar.spd > 0 ? (2 + (Counter % 4)) : (mGR.mChar.spd < 0 ? 
						(6 + (Counter % 4)) : (Counter % 2))], mGR.mChar.x, mGR.mChar.y);
			} else {
				mGR.mTex_Car[mGR.mChar.spd > 0 ? (2 + (Counter % 4)) : (mGR.mChar.spd < 0 ? (6 + (Counter % 4))
					: (Counter % 2))].drawRGBTS(gl, mGR.mChar.x,mGR.mChar.y, 0, mGR.mChar.slow % 2, 0, 1, 1, 1);
			}
		}
		DrawTexture(gl, mGR.mTex_Rod, 0, mGR.mChackra.y);
		for(int i =0;i<8;i++)
			mGR.mTex_Chakra.drawRTS(gl, -.95f+.27f*i, mGR.mChackra.y - .1f, mGR.mChackra.z, -10*(Counter%36),0,0);
		
		if(Counter%6==0)
			counter2++;
		for (int k = 0; k < mGR.mMonster.length; k++){
			if(mGR.mMonster[k].isin()){
				if (mGR.mMonster[k].vx > 0) {
					DrawTexture(gl, mGR.mTex_Monster[0][counter2 % 3], mGR.mMonster[k].x, mGR.mMonster[k].y);
					mGR.mTex_Monster[0][3].drawRTS(gl, mGR.mMonster[k].x, mGR.mMonster[k].y, 1,-((Counter % 15) * 7) + 125, .42f, -.16f);
				} else {
					DrawTexture(gl, mGR.mTex_Monster[1][counter2 % 3], mGR.mMonster[k].x, mGR.mMonster[k].y);
					mGR.mTex_Monster[1][3].drawRTS(gl, mGR.mMonster[k].x, mGR.mMonster[k].y, 1,((Counter % 15) * 7) - 125, -.42f, -.16f);
				}
				
				
//				DrawTexture(gl, mGR.mTex_Monster[mGR.mMonster[k].vx>0?0:1][Counter%mGR.mTex_Monster.length], mGR.mMonster[k].x, mGR.mMonster[k].y);
			}
		}
		for(int i=0;i<mGR.mMonBlast.length;i++){
			if(mGR.mMonBlast[i].img<mGR.mTex_MonBlast.length){
				DrawTexture(gl, mGR.mTex_MonBlast[mGR.mMonBlast[i].img], mGR.mMonBlast[i].x, mGR.mMonBlast[i].y);
				mGR.mMonBlast[i].img++;
			}
		}
		for(int i=0;i<mGR.mAniCoin.length;i++){
			if(mGR.mAniCoin[i].update()){
				DrawTexture(gl, mGR.mTex_Coin[Counter%10], mGR.mAniCoin[i].x, mGR.mAniCoin[i].y);
				break;
			}
		}
		for(int i=0;i<mGR.mAnim.length;i++){
			if(mGR.mAnim[i].update()){
				if(mGR.mCharBlast.img>=0){
					DrawTexture(gl, mGR.mTex_Blood[i==0?0:3], mGR.mAnim[i].x, mGR.mAnim[i].y);
				}
				else
					mGR.mTex_Wood.drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y, 1, (i%2==0?10:-10)*(Counter%360),0,0);
			}
		}
		if(mGR.mCharBlast.img>=0){
			if(mGR.mCharBlast.img<mGR.mTex_Blood.length)
				DrawTexture(gl, mGR.mTex_Blood[mGR.mCharBlast.img], mGR.mCharBlast.x, mGR.mCharBlast.y);
			mGR.mCharBlast.img++;
			
			
			for(int i=0,k=0;i<mGR.mAnim.length&&k<3;i++){
				if(!mGR.mAnim[i].isin()){
					mGR.mAnim[i].set(mGR.mAnim[0].x, mGR.mAnim[0].y);
					k++;
				}
			}
			
			if(mGR.mCharBlast.img > 100){
				gameOver();
			}
		}
		if (mGR.mDrill.img >= 0 && mGR.mDrill.img < mGR.mTex_Drill.length) {
			DrawTexture(gl, mGR.mTex_Drill[mGR.mDrill.img], mGR.mDrill.x,mGR.mDrill.y);
			mGR.mDrill.img++;

		}
		if (mGR.mChamak.img >= 1 && mGR.mChamak.img < 11) {
			DrawTransScal(gl, mGR.mTex_Star, mGR.mChar.x,mGR.mChar.y,1,mGR.mChamak.img*.1f);
			mGR.mChamak.img--;

		}
		
		DrawTexture(gl, mGR.mTex_SBox[0],-.73f, 0.95f);
		drawNumber(gl, ""+mGR.mPoint, -.72f, 0.95f, 1, 0, 0);
		DrawTexture(gl, mGR.mTex_SBox[1],0.73f, 0.95f);
		drawNumber(gl, ""+mGR.mPoint, .67f, 0.95f, 1, 0, 0);
		gameLogic();
	}
	
	boolean Handle_GamePlay(MotionEvent event) {
		if(event.getX()>M.ScreenWidth/2){
			mGR.mChar.spd = (.025f+mGR.mChar.Power[0]*.001f);
		}else{
			mGR.mChar.spd =-(.025f+mGR.mChar.Power[0]*.001f);
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			mGR.mChar.spd =0;
		}
		return true;
	}

	/*********************** GamePlay End ***********************/	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
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
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
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
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl, String strs, float x, float y,float scal,float color,int aling) {
		float dx = mGR.mTex_Font[0].width()*.8f*scal;
		
		if(aling==1)
			x -= strs.length()*dx*.5f+dx*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i))-48;
			if (k >= 0 && k < mGR.mTex_Font.length){
				if(color == 4){
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,1,1,scal+.1f,scal+.1f);
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,0,0,1,scal-.05f,scal-.05f);
				}
				else if(color == 3)
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,.9f,.9f,.9f,1,scal,scal);
				else if(color == 2)
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,.455f,.271f,.099f,1,scal,scal);
				else if(color == 1)
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,.65f,0,1,scal,scal);
				else
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,1,1,scal,scal);
			}
		}
	}
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK+getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
