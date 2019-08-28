package com.oneday.games24.zombiesnak;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int _keyCode =0;
	int Counter =0;
	int sel =0;
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMESHOP;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.mTex_CBG.drawRGB(gl, 0, 0, 0, 0, 0);
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 60){
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
		case M.GAMEPAUSE:
			Draw_OverPause(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		}
//		setting();
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{

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
		case M.GAMEPAUSE:
			Handle_OverPause(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	void Draw_Shop(GL10 gl){
		for(int i =0;i<mGR.mTex_Ball.length;i++){
			DrawTextureR(gl, mGR.mTex_Ball[i], - .66f+ (i%4)*.44f, .75f- (i/4)*.3f,0);
			if(!mGR.mBall.isRandom && i == mGR.mBall.img)
				DrawTextureR(gl, mGR.mTex_Sel, - .66f+ (i%4)*.44f, .75f- (i/4)*.3f,0);
		}
		DrawTransScal(gl, mGR.mTex_Rand, .66f, -.15f,1,mGR.mBall.isRandom ?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Play , 0.0f,-.56f,sel == 50 ?1.1f:1,sel == 50 ?0.5f:1);
		
	}
	boolean Handle_Shop(MotionEvent event){
		sel = 0;
		for(int i =0;i<mGR.mTex_Ball.length+1;i++){
			if(CircRectsOverlap(- .66f+ (i%4)*.44f, .75f- (i/4)*.3f, mGR.mTex_Rate.width()*.4f, mGR.mTex_Rate.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				sel = i+1;
			}
		}
		if(CircRectsOverlap(0, -.56f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 50;
		}
	
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 50:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 16:
				mGR.mBall.isRandom = !mGR.mBall.isRandom;
				break;
			default:
				mGR.mBall.img = sel - 1;
				mGR.mBall.isRandom = false;
				break;
			}
			M.sound1(R.drawable.click);
			sel = 0;
		}
		return true;
	}
	
	
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Text, 0, 0.5f);
		
		DrawTransScal(gl, mGR.mTex_Play , 0.00f, 0.0f,sel == 1 ?1.1f:1,sel == 1 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Player,0.65f, 0.0f,sel == 5 ?1.1f:1,sel == 5 ?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Rate , -.60f, -.4f,sel == 2 ?1.1f:1,sel == 2 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound, 0.00f, -.4f,sel == 3 ?1.1f:1,sel == 3 ?0.5f:1);
		if(!M.setValue)
			DrawTransScal(gl, mGR.mTex_SOff, 0.0f, -.4f,sel == 3 ?1.1f:1,sel == 3 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leder, 0.6f, -.4f,sel == 4 ?1.1f:1,sel == 4 ?0.5f:1);
		
	}
	boolean Handle_Menu(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, 0, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(-.6f, -.4f, mGR.mTex_Rate.width()*.4f, mGR.mTex_Rate.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(0.0f, -.4f, mGR.mTex_Sound.width()*.4f, mGR.mTex_Sound.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;
		}
		if(CircRectsOverlap(0.6f, -.4f, mGR.mTex_Leder.width()*.4f, mGR.mTex_Leder.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(CircRectsOverlap(0.65f, 0.0f, mGR.mTex_Sound.width()*.4f, mGR.mTex_Sound.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel > 0){
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				MoreGame();
				break;
			case 3:
				M.setValue = !M.setValue;
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 5:
				M.GameScreen = M.GAMESHOP;
				break;
			}
			M.sound1(R.drawable.click);
			sel = 0;
		}
		return true;
	}
	void Draw_OverPause(GL10 gl){
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_GOver, 0, 0.7f);
		else
			DrawTexture(gl, mGR.mTex_Pause, 0, 0.7f);
		
		DrawTexture(gl, mGR.mTex_SBox, 0, .3f);
		drawNumber(gl, mGR.mScore, .04f, .37f);
		drawNumber(gl, mGR.mHScore, .04f, .08f);
		DrawTransScal(gl, mGR.mTex_Play , 0.0f,-.17f,sel == 1 ?1.1f:1,sel == 1 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiv, -.6f,-.50f,sel == 2 ?1.1f:1,sel == 2 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Home , 0.0f,-.50f,sel == 3 ?1.1f:1,sel == 3 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leder, 0.6f,-.50f,sel == 4 ?1.1f:1,sel == 4 ?0.5f:1);
		
		
	}
	boolean Handle_OverPause(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, -.17f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(-.6f, -.5f, mGR.mTex_Rate.width()*.4f, mGR.mTex_Rate.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(0.0f, -.5f, mGR.mTex_Home.width()*.4f, mGR.mTex_Sound.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;
		}
		if(CircRectsOverlap(0.6f, -.5f, mGR.mTex_Leder.width()*.4f, mGR.mTex_Leder.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel > 0){
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			}
			M.sound1(R.drawable.click);
			sel = 0;
		}
		return true;
	}
	void gamelogic(){
		for(int i =0;i<mGR.mLine.length;i++){
			mGR.mLine[i].x +=M.SPD;
			
			if(CircRectsOverlap(mGR.mLine[i].x,mGR.mLine[i].y + (mGR.mTex_Arrow.Height()*.5f +.1f),
					mGR.mTex_Arrow.width()*.5f, mGR.mTex_Arrow.Height()*.47f, mGR.mBall.x, mGR.mBall.y, mGR.mTex_Ball[0].width()*.5f)){
				mGR.mBall.Over =1;
				M.sound2(R.drawable.crash);
			}
			if(CircRectsOverlap(mGR.mLine[i].x,mGR.mLine[i].y - (mGR.mTex_Arrow.Height()*.5f +.1f), 
					mGR.mTex_Arrow.width()*.5f, mGR.mTex_Arrow.Height()*.47f, mGR.mBall.x, mGR.mBall.y, mGR.mTex_Ball[0].width()*.5f)){
				mGR.mBall.Over =1;
				M.sound2(R.drawable.crash);
			}
			if(!mGR.mLine[i].sound && mGR.mLine[i].x < mGR.mBall.x-.05f){
				mGR.mLine[i].sound = true;
				M.sound3(R.drawable.cross);
				mGR.mScore++;
				mGR.mTotal++;
				if (mGR.mScore > mGR.mHScore)
					mGR.mHScore = mGR.mScore;
			}
			if (mGR.mLine[i].x < -1.02f) {
				mGR.mLine[i].set(mGR.mLine[(i == 0 ? mGR.mLine.length : i) - 1].x + 2);
				
			}
		}
		for (int i = 0; i < mGR.mFollower.length && Counter % 3 == 0; i++) {
			if(mGR.mFollower[i].x < -1.2){
				mGR.mFollower[i].set(mGR.mBall.x, mGR.mBall.y);
				break;
			}
		}
		for(int i =0;i<mGR.mFollower.length;i++){
			if(mGR.mFollower[i].x >= -1.2){
				mGR.mFollower[i].Update();
			}
		}
		mGR.mBall.update();
	}
	void Draw_Gameplay(GL10 gl){
//		DrawTexture(gl, mGR.mTex_GBG,0, 0);
		drawNumber(gl, mGR.mScore, .04f, .8f);
		for(int i =0;i<mGR.mFollower.length;i++){
			if(mGR.mFollower[i].x >= -1.2){
				DrawTextureR(gl, mGR.mTex_Ball[mGR.mBall.img], mGR.mFollower[i].x, mGR.mFollower[i].y,Counter*(mGR.mBall.img%2==0?10:-10));
			}
		}
		if(!mGR.mBall.isStart)
			DrawTexture(gl, mGR.mTex_Help, -0.08f,0.05f);
		else
			DrawTextureR(gl, mGR.mTex_Ball[mGR.mBall.img], mGR.mBall.x, mGR.mBall.y,Counter*(mGR.mBall.img%2==0?10:-10));
		
		for(int i =0;i<mGR.mLine.length;i++){
			mGR.mTex_Arrow.drawRGB(gl,mGR.mLine[i].x, mGR.mLine[i].y + (mGR.mTex_Arrow.Height()*.5f +.1f),mGR.mLine[i].r,mGR.mLine[i].g,mGR.mLine[i].b);
			mGR.mTex_Arrow.drawRGB(gl,mGR.mLine[i].x, mGR.mLine[i].y - (mGR.mTex_Arrow.Height()*.5f +.1f),mGR.mLine[i].r,mGR.mLine[i].g,mGR.mLine[i].b);
		}
		if(mGR.mBall.isStart){
			if(mGR.mBall.Over == 0)
				gamelogic();
			else{
				mGR.mBall.Over++;
				if(mGR.mBall.Over > 20){
					M.GameScreen = M.GAMEOVER;
					GameRenderer.mStart.Achivment();
					GameRenderer.mStart.ShowInterstitial();
				}
			}
		}
	}
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			if(!mGR.mBall.isStart)
				mGR.mBall.isStart = true;
			mGR.mBall.isTuch =true;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			mGR.mBall.isTuch =false;
		}
		return true;
	}
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
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
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width()*.6f;
		String strs = ""+no;
		x -= strs.length() * dx * .5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
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
