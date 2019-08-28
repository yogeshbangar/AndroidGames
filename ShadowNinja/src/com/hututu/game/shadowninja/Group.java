package com.hututu.game.shadowninja;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	final float BGH = 1.4f;
	GameRenderer mGR = null;
	int Counter =0;
	float sx,sy;
	int mSel = 0;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(mSel+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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
//		M.GameScreen =M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 50) {
				M.GameScreen = M.GAMEMENU;
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUS:
			Draw_Gameover(gl);
			break;
		}
		Counter++;
//		 setting();
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUS:
			Handle_Gameover(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, .52f);
		DrawTransScal(gl, mGR.mTex_Rate, 0, -.10f,mSel == 1?1.1f:1,mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Play, -.52f, -.38f,mSel == 2?1.1f:1,mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_leader,.52f, -.38f,mSel == 3?1.1f:1,mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1], 0, -.6f,mSel == 4?1.1f:1,mSel == 4?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		mSel = 0;
		if (CircRectsOverlap(0, -.10f, mGR.mTex_Rate.width() * .36,
				mGR.mTex_Rate.Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 1;// Rate
		}
		if(CircRectsOverlap(-.52f, -.38f, mGR.mTex_Rate.width()*.36, mGR.mTex_Rate.Height()*.45, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 2;//Play
		}
		if(CircRectsOverlap(0.52f, -.38f, mGR.mTex_Rate.width()*.36, mGR.mTex_Rate.Height()*.45, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 3;//LeaderbOARD
		}
		if(CircRectsOverlap(0, -.6f, mGR.mTex_Sound[0].width()*.45, mGR.mTex_Sound[0].Height()*.45, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			mSel = 4;//sOUND
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mSel >0){
			switch (mSel) {
			case 1:
				RateUs();
				break;
			case 2:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.sound6(R.drawable.start);
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBorad
				break;
			case 4:
				M.setValue =!M.setValue;
				break;
			}
			mSel =0;
		}
		return true;
	}
	
	
	void Draw_Gameover(GL10 gl){
		if(M.GameScreen == M.GAMEPAUS)
			DrawTexture(gl, mGR.mTex_Pause, 0, .65f);
		else
			DrawTexture(gl, mGR.mTex_Gameover, 0, .65f);
		
		mGR.mTex_SBox.drawRGBS(gl, 0, .22f,.9f,.9f,.9f, 1,1	);
//		DrawTexture(gl, mGR.mTex_SBox, 0, .22f);
		
		DrawTexture(gl, mGR.mTex_Score, -.28f, .34f);
		drawNumber(gl, mGR.mScore, -.38f, .27f);
		DrawTexture(gl, mGR.mTex_Best , -.32f, .20f);
		drawNumber(gl, mGR.mHScore, -.38f, 0.13f);
		
		DrawTransScal(gl, mGR.mTex_Play, 0, -.10f,mSel == 1?1.1f:1,mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achive, -.52f, -.38f,mSel == 2?1.1f:1,mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_leader,.52f, -.38f,mSel == 3?1.1f:1,mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Ads, 0.6f, -.1f,mSel == 4?1.1f:1,mSel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Home, 0, -.6f,mSel == 5?1.1f:1,mSel == 5?0.5f:1);
	
	}
	boolean Handle_Gameover(MotionEvent event){
		mSel = 0;
		if (CircRectsOverlap(0, -.10f, mGR.mTex_Rate.width() * .36,
				mGR.mTex_Rate.Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 1;// Play
		}
		if (CircRectsOverlap(-.52f, -.38f, mGR.mTex_Rate.width() * .36,
				mGR.mTex_Rate.Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 2;// Achive
		}
		if (CircRectsOverlap(0.52f, -.38f, mGR.mTex_Rate.width() * .36,
				mGR.mTex_Rate.Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 3;// LeaderbOARD
		}
		if (CircRectsOverlap(0.6f, -.1f, mGR.mTex_Sound[0].width() * .45,
				mGR.mTex_Sound[0].Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 4;// ADS
		}
		if (CircRectsOverlap(0, -.6f, mGR.mTex_Rate.width() * .36,
				mGR.mTex_Rate.Height() * .45, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .01f)) {
			mSel = 5;// Home
		}
		
		if(MotionEvent.ACTION_UP == event.getAction() && mSel >0){
			switch (mSel) {
			case 1:
				if(M.GameScreen == M.GAMEOVER){
					mGR.gameReset();
					M.sound6(R.drawable.start);
				}
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 4:
				mGR.mInApp.onBuyGold10000(null);//Ads Free
				break;
			case 5:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mSel =0;
		}
		return true;
	}
	
	void gameLogic() {
		if(mGR.gameOver>0){
			if(mGR.gameOver ==10)
				M.sound1(R.drawable.gameover);
			mGR.gameOver ++;
			if(mGR.gameOver>50){
				GameRenderer.mStart.Achivment();
				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.ShowInterstitial();
			}
		}
		if(mGR.mPlyer.y<-.5f)
		{
			GameRenderer.mStart.Achivment();
			M.GameScreen = M.GAMEOVER;
			GameRenderer.mStart.ShowInterstitial();
		}
		mGR.mPlyer.update(Counter);
		for (int i = 0; i < mGR.mWay.length; i++) {
//			if(CircRectsOverlap(mGR.mWay[i].x, mGR.mWay[i].y, mGR.mWay[i].sx*mGR.mTex_BG.width()*.5, mGR.mTex_BG.Height()*BGH*.5, mGR.mPlyer.x, mGR.mPlyer.y, .1)){
			if(Rect2RectIntersection(mGR.mWay[i].x, mGR.mWay[i].y, mGR.mWay[i].sx*mGR.mTex_BG.width()*1.0f, mGR.mTex_BG.Height()*BGH*1.0f, mGR.mPlyer.x, mGR.mPlyer.y, .05f, .2f)){
				if(mGR.mPlyer.y<(mGR.mWay[i].y+mGR.mTex_BG.Height()*BGH*0.5f))
				{
					mGR.mPlyer.x = mGR.mWay[i].x-mGR.mWay[i].sx*mGR.mTex_BG.width()*0.5f;
//					M.GameScreen = M.GAMEOVER;
				}else if(mGR.mPlyer.vy < 0)
					mGR.mPlyer.reset(.065f+mGR.mWay[i].y +mGR.mTex_BG.Height()*BGH*.5f);
			}
			if (mGR.mWay[i].x < -4) {
				int old = (i == 0?mGR.mWay.length:i)-1;
				mGR.mWay[i].setSx();
				mGR.mWay[i].set(M.mRand.nextInt(30)*.01f+mGR.dis+mGR.mWay[old].x + (mGR.mWay[old].sx + mGR.mWay[i].sx)* (mGR.mTex_BG.width() / 2f));
			}
		}
		for (int i = 0; i < mGR.mWay.length && Counter % 40 == 0; i++) {
			if(mGR.mWay[i].sx > 1.2f &&
					CircRectsOverlap(mGR.mWay[i].x, mGR.mWay[i].y, mGR.mWay[i].sx*mGR.mTex_BG.width()*.35, mGR.mTex_BG.Height()*BGH*.5, 1.1, mGR.mWay[i].y, .01)){
//				if(mGR.mWay[i].x > -1.4)
				for (int j = 0; j < mGR.mOpp.length && mGR.mWay[i].sx > .8; j++) {
					if(mGR.mOpp[j].x < -1.4){
						mGR.mOpp[j].set(1.3f, .075f+mGR.mWay[i].y +mGR.mTex_BG.Height()*BGH*.5f,M.mRand.nextBoolean()?0:2);
						break;
					}
				}
			}
		}
		for(int i =0;i<mGR.mWay.length;i++){
			mGR.mWay[i].update();
			
		}
		for(int i =0;i<mGR.mOpp.length;i++){
			mGR.mOpp[i].update(Counter);
		}
		
		for (int i = 0; i < mGR.mOpp.length; i++) {
			if(mGR.mOpp[i].act !=5 && 
					CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mTex_Ads.width()*.3, mGR.mTex_Ads.Height()*.3, mGR.mPlyer.x, mGR.mPlyer.y, mGR.mTex_Ads.width()*.3)){
				if(mGR.mOpp[i].act == 2){
					if (mGR.mPlyer.act == 0) {
						M.spd = 0;
						mGR.mPlyer.Die(4);
						mGR.mOpp[i].act = 5;
						mGR.mOpp[i].count = 0;
						mGR.mOpp[i].hit = 0;
					} else {
						mGR.mHide.set(mGR.mOpp[i].x, mGR.mOpp[i].y, 1);
						M.spd = 0;
						mGR.mOpp[i].x =-100;
					}
					if(mGR.gameOver==0)
						mGR.gameOver = 1;
					//Loose
				}
				else if(mGR.mPlyer.act <= 1 || mGR.mOpp[i].count > mGR.mPlyer.count){
					M.spd = 0;
					if(mGR.mPlyer.act<4)
					{
						if(mGR.mOpp[i].act ==1)
							mGR.mPlyer.Die(5);
						else
							mGR.mPlyer.Die(4);
					}if(mGR.gameOver==0)
						mGR.gameOver = 1;//Loose
				}else{
					mGR.mScore++;
					mGR.mTotal++;
					if(mGR.mScore>mGR.mHScore){
						mGR.mHScore=mGR.mScore;
					}
					mGR.mHide.set(mGR.mOpp[i].x, mGR.mOpp[i].y, 0);
					mGR.mOpp[i].x =-100;
					M.sound4(R.drawable.kill);
					//Win
				}
			}
		}
		if(mGR.mPlyer.Jump == 0 && M.spd != 0){
			M.sound5(R.drawable.ranning);
		}else{
			M.sound5Pause();
		}
	}

	void Draw_Gameplay(GL10 gl) {
		for (int i = 0; i < mGR.mWay.length; i++) {
			mGR.mTex_BG.drawRGBS(gl, mGR.mWay[i].x, mGR.mWay[i].y,
					mGR.mWay[i].r, mGR.mWay[i].g, mGR.mWay[i].b,mGR.mWay[i].sx, BGH);
		}
		
		switch (mGR.mPlyer.act) {
		case 1://Jump
			DrawTextureR(gl, mGR.mTex_PAct[1][0], mGR.mPlyer.x, mGR.mPlyer.y,-Counter*30);
			break;
		case 2:case 3:
			if (M.spd == 0) {
				DrawTexture(gl, mGR.mTex_PSit, mGR.mPlyer.x,mGR.mPlyer.y - .028f);
			} else {
				DrawTexture(gl, mGR.mTex_PAct[0][mGR.mPlyer.count % 5], mGR.mPlyer.x, mGR.mPlyer.y - .028f);
				DrawTexture(gl, mGR.mTex_PAct[2][mGR.mPlyer.count % 3], mGR.mPlyer.x + .063f + (mGR.mPlyer.count % 3) * .002f,
						mGR.mPlyer.y + .039f + (mGR.mPlyer.count % 3) * .002f);
			}
			break;
		case 4:case 5:
			DrawTexture(gl,mGR.mTex_Die[mGR.mPlyer.act % 2][0],mGR.mPlyer.x, mGR.mPlyer.y-.02f);
		break;
		default://Run
			DrawTexture(gl, mGR.mTex_PAct[0][mGR.mPlyer.count%5], mGR.mPlyer.x, mGR.mPlyer.y-.028f);
			DrawTexture(gl, mGR.mTex_PAct[0][5], mGR.mPlyer.x+.042f+(mGR.mPlyer.count%3)*.004f, mGR.mPlyer.y+.059f+(mGR.mPlyer.count%3)*.004f);
			break;
			
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			
			switch (mGR.mOpp[i].act) {
			case 0://Hit front
				DrawTexture(gl,mGR.mTex_Opp[mGR.mOpp[i].act][mGR.mOpp[i].count%mGR.mTex_Opp[mGR.mOpp[i].act].length], mGR.mOpp[i].x, mGR.mOpp[i].y);
				break;
			case 1://Hit Back
				if(mGR.mOpp[i].vy>0)
					DrawTextureR(gl,mGR.mTex_Opp[1][0], mGR.mOpp[i].x, mGR.mOpp[i].y,Counter*30);
				else{
					if(mGR.mPlyer.act>3)
						DrawTexture(gl, mGR.mTex_OpSit, mGR.mOpp[i].x, mGR.mOpp[i].y- .028f);
					else
						DrawTexture(gl,mGR.mTex_Opp[1][1+(mGR.mOpp[i].count%4)], mGR.mOpp[i].x, mGR.mOpp[i].y);
				}
				break;
			case 2://Run Blue
				DrawTexture(gl,mGR.mTex_Opp[mGR.mOpp[i].act][mGR.mOpp[i].count%mGR.mTex_Opp[mGR.mOpp[i].act].length], mGR.mOpp[i].x, mGR.mOpp[i].y);
				break;
			default://Die
				DrawTexture(gl,mGR.mTex_Die[mGR.mOpp[i].act % 2][0],
						mGR.mOpp[i].x, mGR.mOpp[i].y-.02f);
				break;
			}
		}
		if(mGR.mHide.hide>0){
			if(mGR.mHide.img ==0){
				DrawTransScal(gl,mGR.mTex_Opp[0][0], mGR.mHide.x, mGR.mHide.y,1,mGR.mHide.hide);
			}
			if(mGR.mHide.img ==1){
				DrawTransScal(gl,mGR.mTex_Opp[2][0], mGR.mHide.x, mGR.mHide.y,1,mGR.mHide.hide);
			}
			mGR.mHide.update();
		}
		if(mGR.gameStart)
			gameLogic();
		else
			DrawTexture(gl, mGR.mTex_Help, 0.0f,.40f);
	}

	boolean Handle_Gameplay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction() && (mGR.gameOver == 0)) {
			if (!mGR.gameStart) {
				mGR.gameStart = true;
			} else {
				if (event.getX() < M.ScreenWidth / 2) {
					if (mGR.mPlyer.Jump < 2) {
						mGR.mPlyer.vy = .07f;
						mGR.mPlyer.act = 1;
						mGR.mPlyer.Jump++;
						if (mGR.mPlyer.Jump == 1)
							M.sound2(R.drawable.jump);
					}
				} else {
					mGR.mPlyer.act = M.mRand.nextInt(2) + 2;
					mGR.mPlyer.count = 0;
					if (M.mRand.nextBoolean())
						M.sound7(R.drawable.sword_swing0);
					else
						M.sound8(R.drawable.sword_swing1);
				}
			}
		}
		return true;
	}
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, x, y,angle);
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

	void drawNumber(GL10 gl, int no, float x, float y) {
		float dx = mGR.mTex_Font[0].width()*.5f;
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
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
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				"Roking new Game '"
						+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK+getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,
					"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
