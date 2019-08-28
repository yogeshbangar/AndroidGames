package com.httgames.amazingfreerunner;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	final float BGH = 2;
	HTTRenderer mGR = null;
	int sel =0;
	int Counter =0;
	int SC =0;
	
	public Group(HTTRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEOVER;
		switch (C.GameScreen) {
		case C.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>100){
				C.GameScreen = C.GAMEMENU;
			}
			break;
		case C.GAMEMENU:
			Draw_Menu(gl);
			break;
		case C.GAMEOVER:
		case C.GAMEPAUSE:
			Draw_Over(gl);
			break;
		case C.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		}
//		setting();
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (C.GameScreen) {
		case C.GAMELOGO:
			break;
		case C.GAMEMENU:
			Handle_Menu(event);
			break;
		case C.GAMEOVER:
		case C.GAMEPAUSE:
			Handle_Over(event);
			break;
		case C.GAMEPLAY:
			Handle_Gamepley(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	
	void gamelogic(){
		mGR.mPlyer.update(Counter);
		for(int i =0;i<mGR.mBlock.length;i++){
			mGR.mBlock[i].x+=C.spd;
			if(Rect2RectIntersection(mGR.mBlock[i].x, mGR.mBlock[i].y-.78f, mGR.mBlock[i].sx*mGR.mTex_block.width()*1.0f, mGR.mTex_block.Height()*BGH*1.0f, mGR.mPlyer.x, mGR.mPlyer.y, .05f, .2f)){
				if (mGR.mPlyer.y < (mGR.mBlock[i].y - .78f + mGR.mTex_block.Height() * BGH * 0.5f)) {
					if (mGR.mPlyer.die == 0) {
						mGR.mPlyer.die = 1;
						C.sound1(R.drawable.gameover);
						mGR.mPlyer.x = mGR.mPlyer.x-.1f;
						C.spd = 0;
						continue;
					}
				}
				if (mGR.mPlyer.vy < 0)
					mGR.mPlyer.reset(mGR.mBlock[i].y - .75f+ mGR.mTex_block.Height() * BGH * .5f);
			}
			if(mGR.mBlock[i].isUp &&
					Rect2RectIntersection(mGR.mBlock[i].x, mGR.mBlock[i].y+.89f, mGR.mBlock[i].sx*mGR.mTex_block.width()*1.0f, mGR.mTex_block.Height()*BGH*1.0f, mGR.mPlyer.x, mGR.mPlyer.y, .05f, .2f)){
				if(mGR.mPlyer.die ==0 ){
					C.spd = 0;
					C.sound1(R.drawable.gameover);
					mGR.mPlyer.die = 1;
					if(mGR.mPlyer.vy > 0)
						mGR.mPlyer.vy =0;
				}
			}
			if(mGR.mBlock[i].x < -2){
				int old = (i == 0?mGR.mBlock.length:i)-1;
				mGR.mBlock[i].setSx();
				mGR.mBlock[i].set(C.mRand.nextInt(30) * .01f + mGR.dis+ mGR.mBlock[old].x
						+ (mGR.mBlock[old].sx + mGR.mBlock[i].sx)* (mGR.mTex_block.width() / 2f));
			}
		}
		if(mGR.mPlyer.die > 0)
		{
			mGR.mPlyer.die ++;
			if(mGR.mPlyer.die > 20){
				HTTRenderer.mStart.Achivment();
				C.GameScreen = C.GAMEOVER;
				HTTRenderer.mStart.ShowInterstitial();
			}
		}
		if(mGR.mPlyer.y <-.9f){
			C.GameScreen = C.GAMEOVER;
			HTTRenderer.mStart.ShowInterstitial();
		}
	}
	void Draw_GamePlay(GL10 gl){
		DrawTexture(gl, mGR.mTex_sky, 0, 0);
		for(int i =0;i<mGR.mBlock.length;i++){
			if(mGR.mBlock[i].isUp)
				DrawTexSR(gl, mGR.mTex_block, mGR.mBlock[i].x, mGR.mBlock[i].y+.75f,mGR.mBlock[i].sx,BGH,180);
			DrawTexSR(gl, mGR.mTex_block, mGR.mBlock[i].x, mGR.mBlock[i].y-.75f,mGR.mBlock[i].sx,BGH, 0);
		}
		
		if(Counter%3 ==0)
			SC++;
		if(mGR.mPlyer.isStart){
			if (mGR.mPlyer.die > 0)
				DrawTexture(gl, mGR.mTex_Boy[5], mGR.mPlyer.x, mGR.mPlyer.y);
			else if (mGR.mPlyer.Jump == 0)
				DrawTexture(gl, mGR.mTex_Boy[SC % 5], mGR.mPlyer.x,mGR.mPlyer.y);
			else {
				mGR.mTex_Boy[6].drawRotet(gl, mGR.mPlyer.x, mGR.mPlyer.y,-Counter * 20);
			}
			if(mGR.mPlyer.Jump == 0 && mGR.mPlyer.die == 0){
				C.sound5(R.drawable.ranning);
			}else{
				C.sound5Pause();
			}
			gamelogic();
		}
		else{
			DrawTexture(gl, mGR.mTex_help, .02f, .47f);
		}
	}
	boolean Handle_Gamepley(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlyer.die ==0){
			if(!mGR.mPlyer.isStart){
				mGR.mPlyer.isStart = true;
				C.sound4(R.drawable.start);
			}else if (mGR.mPlyer.Jump < 2) {
				mGR.mPlyer.vy = .07f;
				mGR.mPlyer.Jump++;
				if (mGR.mPlyer.Jump == 1){
					mGR.mScore++;
					mGR.mTotal++;
					if(mGR.mScore > mGR.mHScore)
						mGR.mHScore = mGR.mScore;
					C.sound2(R.drawable.jump);
				}
			}
		}
		return true;
	}
	void DrawTexture(GL10 gl,APlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTexSR(GL10 gl,APlane Tex,float x,float y,float sx,float sy,float angle)
	{
		Tex.drawRotet(gl, x, y, sx,sy,angle);
	}
	void DrawTextureS(GL10 gl,APlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,APlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,APlane Tex,float x,float y, float z,float t)
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
		float c = ((a / C.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / C.ScreenHieght)- 0.5f)*(-2);
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
	void drawNumber(GL10 gl, int no, float x, float y, int aling) {
		float dx = mGR.mTex_Font[0].width()*.5f;
		String strs = "" + no;
		if(aling == 1)
			x-=dx*strs.length()*.45f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(C.LINK + getClass().getPackage().getName()));
		HTTRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(C.MARKET));
		HTTRenderer.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		HTTRenderer.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		HTTRenderer.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		HTTRenderer.mContext.startActivity(mIntent);
	}
	float ty = 1.2f,tvy = 0;
	boolean ist = true;
	
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_comman, 0, 0);
		DrawTexture(gl, mGR.mTex_text, 0, ty);
		if(ist){
			ty +=tvy;
			tvy -=.007f;
			if(ty<.4){
				tvy = Math.abs(tvy)*.7f;
				if(tvy < .03){
					ist = false;
					ty = .4f;
				}
			}
		}
		DrawTransScal(gl, mGR.mTex_play ,0.0f, -.08f, sel == 1?1.1f:1, sel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_sound,0.0f, -.40f, sel == 2?1.1f:1, sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_rate ,-.6f, -.40f, sel == 3?1.1f:1, sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_leder,0.6f, -.40f, sel == 4?1.1f:1, sel == 4?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0.0f, -.08f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(0.0f, -.40f, mGR.mTex_sound.width()*.45f, mGR.mTex_sound.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(-.6f, -.40f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;	
		}
		if(CircRectsOverlap(0.6f, -.40f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel>0){
			switch (sel) {
			case 1:
				mGR.gameReset();
				C.GameScreen = C.GAMEPLAY;
				break;
			case 2:
				C.setValue =!C.setValue;
				break;
			case 3:
				RateUs();
				break;
			case 4:
				HTTRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			}
			C.sound6(R.drawable.click);
			sel = 0;
		}
		return true;
	}
	void Draw_Over(GL10 gl){
		Draw_More(gl);
		
		DrawTexture(gl, mGR.mTex_gameover, 0, .76f);
		DrawTexture(gl, mGR.mTex_score_box, 0, .36f);
		drawNumber(gl, mGR.mScore, 0, .39f, 1);
		drawNumber(gl, mGR.mHScore, 0, .18f, 1);
		DrawTransScal(gl, mGR.mTex_play	,0.0f, -.10f, sel == 1?1.1f:1, sel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_home	,0.0f, -.40f, sel == 2?1.1f:1, sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiv,-.6f, -.40f, sel == 3?1.1f:1, sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_leder,0.6f, -.40f, sel == 4?1.1f:1, sel == 4?0.5f:1);
	}
	boolean Handle_Over(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0.0f, -.10f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 1;
		}
		if(CircRectsOverlap(0.0f, -.40f, mGR.mTex_sound.width()*.45f, mGR.mTex_sound.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 2;
		}
		if(CircRectsOverlap(-.6f, -.40f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 3;	
		}
		if(CircRectsOverlap(0.6f, -.40f, mGR.mTex_play.width()*.45f, mGR.mTex_play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel>0){
			switch (sel) {
			case 1:
				if(C.GameScreen == C.GAMEOVER)
					mGR.gameReset();
				C.GameScreen = C.GAMEPLAY;
				break;
			case 2:
				C.GameScreen = C.GAMEMENU;
				break;
			case 3:
				HTTRenderer.mStart.onShowAchievementsRequested();//Achivment
				break;
			case 4:
				HTTRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			}
			C.sound6(R.drawable.click);
			sel = 0;
		}
		return true;
	}
	
	
	void Draw_More(GL10 gl){
		DrawTexture(gl, mGR.mTex_Achiv, 0, 0);
		DrawTexture(gl, mGR.mTex_block, 0, .76f);
		DrawTransScal(gl, mGR.mTex_help,-.6f, -.40f, sel == 3?1.1f:1, sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Logo,0.6f, -.40f, sel == 4?1.1f:1, sel == 4?0.5f:1);
		DrawTexture(gl, mGR.mTex_comman, 0, 0);
	}
	
	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,
				"Roking new Game '"+ HTTRenderer.mContext.getResources().getString(R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT,"Download it now and let’s ROCK!!!!  "+ C.SHARELINK+getClass().getPackage().getName());
		try {
			HTTRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(HTTRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
