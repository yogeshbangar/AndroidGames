package com.flying.doodle.monkey;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

//v1kd-t5yz/2

public class Group extends Mesh 
{
	int _keyCode =0;
	GameRenderer mGR = null;
	int sel =0;
	int Counter =0;
	/*float sx,sy;
	public void setting(){float ud=.01f;switch (_keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	*/
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.mTex_Splash.drawBlack(gl, 0, 0);
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_GameMenu(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Draw_GameOverPause(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
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
			Handle_GameMenu(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Handle_GameOverPause(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	float tsz = 1,tvz =.01f;
	void Draw_GameMenu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTextureS(gl, mGR.mTex_Text, 0, .5f,tsz);
		if(tsz > 1.1)
			tvz = -.005f;
		if(tsz < 0.9)
			tvz = 0.005f;
		DrawTransScal(gl, mGR.mTex_Play, 0, 0,sel == 1?1.1f:1,sel == 1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Achiv	, -.6f, -.4f,sel == 2?1.1f:1,sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader	, -.2f, -.4f,sel == 3?1.1f:1,sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Rate		, 0.2f, -.4f,sel == 4?1.1f:1,sel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1], 0.6f, -.4f,sel == 5?1.1f:1,sel == 5?0.5f:1);
	}
	boolean Handle_GameMenu(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, 0, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(-.6f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(-.2f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;
		}
		if(CircRectsOverlap(0.2f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(CircRectsOverlap(0.6f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 5;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();// Leader Board
				break;
			case 4:
				RateUs();
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			}
			M.sound1(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	
	void Draw_GameOverPause(GL10 gl){
//		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		Draw_Gameplay(gl);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_Gameover, 0, 0.5f);
		else
			DrawTexture(gl, mGR.mTex_Gamepaused, 0, 0.5f);
		DrawTransScal(gl, mGR.mTex_Play, 0, 0,sel == 1?1.1f:1,sel == 1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Achiv	, -.6f, -.4f,sel == 2?1.1f:1,sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader	, -.2f, -.4f,sel == 3?1.1f:1,sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Rate		, 0.2f, -.4f,sel == 4?1.1f:1,sel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1], 0.6f, -.4f,sel == 5?1.1f:1,sel == 5?0.5f:1);
		
	}
	boolean Handle_GameOverPause(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, 0, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(-.6f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(-.2f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;
		}
		if(CircRectsOverlap(0.2f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(CircRectsOverlap(0.6f, -.4f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 5;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel > 0) {
			switch (sel) {
			case 1:
				mGR.mLScore = mGR.mScore;
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();// Leader Board
				break;
			case 4:
				RateUs();
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			}
			M.sound1(R.drawable.button_click);
			sel = 0;
		}
		return true;
	}
	void Gameover(){
		M.GameScreen = M.GAMEOVER;
		if(mGR.mHScore < mGR.mScore)
			mGR.mHScore = mGR.mScore;
		mGR.mTotal+=mGR.mScore;
		M.sound7(R.drawable.gameover);
		GameRenderer.mStart.Achivment();
		GameRenderer.mStart.ShowInterstitial();
	}
	void gameLogic() {
		if(M.SPD > -.03)
			M.SPD -= 0.0005f;
		mGR.mPlayer.update();
		mGR.mScore-=M.SPD;
		for(int i = 0;i<mGR.mHardle.length;i++){
			mGR.mHardle[i].update();
			if(mGR.mHardle[i].y < -1.3){
				mGR.mHardle[i].set(mGR.mHardle[(i == 0 ? mGR.mHardle.length : i) - 1].y + mGR.mPlayer.mDist);
				if(mGR.mPlayer.mDist>1.5){
					mGR.mPlayer.mDist -=.1f;
				}
			}
			if(CircRectsOverlap(mGR.mHardle[i].x, mGR.mHardle[i].y, mGR.mTex_Hardle.width()*.5,mGR.mHardle[i].z*(mGR.mTex_Hardle.Height()*.3), mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Char[0].Height()*.5)){
				Gameover();
			}
		}
		for (int i = 0; i < mGR.mPar.length && mGR.mPlayer.iStart /*&& Counter % 2 == 0*/; i++) {
			if (mGR.mPar[i].sx <= 0) {
				mGR.mPar[i].set(mGR.mPlayer.x, mGR.mPlayer.y);
				break;
			}
		}
		for(int i =0;i<mGR.mTrans.length;i++){
			mGR.mTrans[i].y += M.SPD*.5f;
			if(mGR.mTrans[i].y < -1.2)
				mGR.mTrans[i].set(mGR.mTrans[(i == 0 ? mGR.mTrans.length:i)-1].y+.5f);
		}
		for(int i =0;i<mGR.mKante.length;i++){
			mGR.mKante[i] += M.SPD*.2f;
		}
	}

	void Draw_Gameplay(GL10 gl) {
		String str =  "";
		int ss =0;
		for(int i =0;i<mGR.mTrans.length;i++){
			DrawTransScal(gl, mGR.mTex_Hardle, mGR.mTrans[i].x, mGR.mTrans[i].y, 1, .2f);
		}
		
		for (int i = 0; i < mGR.mPar.length && mGR.mPlayer.iStart; i++) {
			if (mGR.mPar[i].sx > 0) {
				DrawTransScal(gl, mGR.mTex_Anim, mGR.mPar[i].x, mGR.mPar[i].y,1,mGR.mPar[i].sx);
				mGR.mPar[i].update();
			}
		}
		
		mGR.mTex_Char[Counter % 8 < 4 ? 0 : 1].drawRotet(gl, mGR.mPlayer.x,mGR.mPlayer.y, mGR.mPlayer.rot);
		
		
		for (int i = 0; i < mGR.mHardle.length && mGR.mPlayer.iStart; i++) {
			DrawTextureS(gl, mGR.mTex_Hardle, mGR.mHardle[i].x, mGR.mHardle[i].y, mGR.mHardle[i].z);
		}
		
		DrawTexture(gl, mGR.mTex_Tree, 0, -.9f);
		for(int i =0;i<mGR.mKante.length;i++){
			DrawTextureR(gl, mGR.mTex_Kante,-.95f, mGR.mKante[i],180);
			DrawTexture (gl, mGR.mTex_Kante, .95f, mGR.mKante[i]);
			if(mGR.mKante[i] < -1.5)
				mGR.mKante[i] = mGR.mKante[(i == 0 ? mGR.mKante.length:i)-1]+mGR.mTex_Kante.Height();
		}
		DrawTexture(gl, mGR.mTex_Score,-.56f, .90f);
		DrawTexture(gl, mGR.mTex_Best ,-.77f, .90f);
		
		str =  (int)(mGR.mHScore)+":";
		ss = (int)((mGR.mHScore - (int)(mGR.mHScore))*100f);
		if(ss>9)
			str +=ss+";<";
		else
			str +="0"+ss+";<";
		drawNumber(gl, str, -.61f, .90f);
		
		str =  (int)(mGR.mLScore)+":";
		ss = (int)((mGR.mLScore - (int)(mGR.mLScore))*100f);
		if(ss>9)
			str +=ss+";<";
		else
			str +="0"+ss+";<";
		DrawTexture(gl, mGR.mTex_Score,0.56f, .90f);
		DrawTexture(gl, mGR.mTex_Last ,0.35f, .90f);
		drawNumber(gl, str, .51f, .90f);
		
		str =  (int)(mGR.mScore)+":";
		ss = (int)((mGR.mScore - (int)(mGR.mScore))*100f);
		if(ss>9)
			str +=ss+" ;<";
		else
			str +="0"+ss+" ;<";
		if(M.GameScreen == M.GAMEPLAY){
			drawNumber(gl, str, mGR.mPlayer.x+.1f,mGR.mPlayer.y);
			if(mGR.mPlayer.iStart)
				gameLogic();
			else{
				DrawTexture (gl, mGR.mTex_Tap, 0,-.7f);
			}
		}else{
			DrawTexture (gl, mGR.mTex_Score2, 0,0.35f);
			drawNumber(gl, str, -str.length()*mGR.mTex_Font[0].width()*.35f,.35f);
		}
	}

	boolean Handle_Gameplay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
				mGR.mPlayer.vy = .03f;
				if (mGR.mPlayer.vx > 0)
					mGR.mPlayer.vx = -.04f;
				else
					mGR.mPlayer.vx = 0.04f;
				
				if(!mGR.mPlayer.iStart)
					mGR.mPlayer.iStart = true;
				M.sound2(R.drawable.n1);
			
		}
		return true;
	}

	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
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
	void drawNumber(GL10 gl,String strs,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width()*.7f;
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
