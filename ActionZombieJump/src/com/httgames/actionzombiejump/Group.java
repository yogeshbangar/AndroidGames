package com.httgames.actionzombiejump;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {
	GameRenderer mGR = null;
	int Counter = 0;
	float sx, sy;
	int sel =0;
	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}
	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEPAUSE;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Draw_Over(gl);
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
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Handle_Over(event);
			break;
		}
		Handle(event);
		return true;
	}

	
	void gameLogic(){
		
//		for(int i=0;i<mGR.mBack.length;i++){
//			mGR.mBack[i]+=M.SPD*.3f;
//		}
//		for(int i=0;i<mGR.mBack.length;i++){
//			if(mGR.mBack[i]<-1.8)
//				mGR.mBack[i] = mGR.mBack[(i==0?mGR.mBack.length:i)-1]+mGR.mTex_BG.width();;
//		}
		mGR.mScore-=M.SPD*4;
		mGR.mTotal-=M.SPD*4;
		if(mGR.mHScore<mGR.mScore)
			mGR.mHScore=(int)mGR.mScore;
		mGR.mZombie.Update();
		for(int i=0;i<mGR.mBase.length;i++){
			mGR.mBase[i]+=M.SPD;
		}
		for(int i=0;i<mGR.mBase.length;i++){
			if(mGR.mBase[i]<-1.3)
			mGR.mBase[i] = mGR.mBase[(i==0?mGR.mBase.length:i)-1]+mGR.mTex_Base.width();;
		}
//		System.out.println(mGR.mHardle[0].lvl+"   "+mGR.mHardle[1].lvl+"   "+mGR.mHardle[2].lvl);
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = Level.Lvl[mGR.mHardle[i].lvl].length - 1,m=0; j >= 0; j--,m++) {
				for (int k = 0; k < Level.Lvl[mGR.mHardle[i].lvl][j].length; k++) {
					if (Level.Lvl[mGR.mHardle[i].lvl][j][k] > 0&& Level.Lvl[mGR.mHardle[i].lvl][j][k] <= mGR.mTex_Tile.length) {
						if(Rect2RectIntersection((mGR.mHardle[i].x + k * mGR.mTex_Tile[0].width()), (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y), mGR.mTex_Tile[0].width(), mGR.mTex_Tile[0].Height(),
								mGR.mZombie.x, mGR.mZombie.y, mGR.mTex_Zombie[0].width()*.5f, mGR.mTex_Zombie[0].Height())){
							
							switch (Level.Lvl[mGR.mHardle[i].lvl][j][k]) {
							case 1:
								if(mGR.mZombie.y < (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)-mGR.mTex_Tile[0].width()*.9f){
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) -( mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.6f);
									mGR.mZombie.vy = 0;
								}else if(mGR.mZombie.y > (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)+mGR.mTex_Tile[0].width()*1){
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) + mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.5f;
									mGR.mZombie.vy = 0;
									mGR.mZombie.jump =0;
								}
								
								
								break;
							case 2:
								if(Rect2RectIntersection((mGR.mHardle[i].x + k * mGR.mTex_Tile[0].width()), (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y), mGR.mTex_Tile[0].width()*.5f, mGR.mTex_Tile[0].Height(),
										mGR.mZombie.x, mGR.mZombie.y, mGR.mTex_Zombie[0].width()*.5f, mGR.mTex_Zombie[0].Height())){
									if(mGR.mZombie.y < (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)+mGR.mTex_Tile[0].width()*.4)
									{
										mGR.mZombie.gOver =1;
									}
								}
								break;
							case 3:case 4:case 5:case 6:
								if(mGR.mZombie.y < (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)-mGR.mTex_Tile[0].width()*.9f){
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) -( mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.6f);
									mGR.mZombie.vy = 0;
								}else if(mGR.mZombie.y > (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)+mGR.mTex_Tile[0].width()*1){
									mGR.mZombie.gOver =1;
								}/*else{
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) + mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.5f;
									mGR.mZombie.vy = 0;
									mGR.mZombie.jump =0;
								}*/
								break;
							case 7:case 8:
								if(mGR.mZombie.y < (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)-mGR.mTex_Tile[0].width()*.9f){
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) -( mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.6f);
									mGR.mZombie.vy = 0;
								}else if(mGR.mZombie.y > (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y)+mGR.mTex_Tile[0].width()*1){
									mGR.mZombie.y = (m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y) + mGR.mTex_Tile[0].Height()*.5f + mGR.mTex_Zombie[0].Height()*.5f;
									mGR.mZombie.vy = 0;
									mGR.mZombie.jump =0;
								}else{
									mGR.mZombie.gOver =1;
								}
								break;
							}
							
							
						}
					}

				}
			}
		}
		for(int i=0;i<mGR.mHardle.length;i++){
			mGR.mHardle[i].x+=M.SPD;
			if(mGR.mHardle[i].x<-2.5){
				mGR.mHardle[i].set(mGR.mHardle[(i==0?mGR.mHardle.length:i)-1]);
			}
		}
		if(Counter%150==0)
			M.ranSound();
	}
	
	void Draw_Gameplay(GL10 gl) {

		for (int i = 0; i < mGR.mBack.length; i++) {
			DrawTexture(gl, mGR.mTex_BG, mGR.mBack[i], .25f);
		}
		for (int i = 0; i < mGR.mBase.length; i++) {
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i], -.74f);
		}
		
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = Level.Lvl[mGR.mHardle[i].lvl].length - 1,m=0; j >= 0; j--,m++) {
				for (int k = 0; k < Level.Lvl[mGR.mHardle[i].lvl][j].length; k++) {
					if (Level.Lvl[mGR.mHardle[i].lvl][j][k] > 0&& Level.Lvl[mGR.mHardle[i].lvl][j][k] <= mGR.mTex_Tile.length) {
						DrawTexture(gl,
								mGR.mTex_Tile[Level.Lvl[mGR.mHardle[i].lvl][j][k] - 1],
								mGR.mHardle[i].x + k * mGR.mTex_Tile[0].width(),
								m * mGR.mTex_Tile[0].Height()+mGR.mHardle[i].y);
					}

				}
			}
			if(mGR.mHardle[i].isCoin){
				DrawTexture(gl, mGR.mTex_Coin[Counter%mGR.mTex_Coin.length],-.2f+mGR.mHardle[i].x, mGR.mHardle[i].y);
				if(Rect2RectIntersection(-.2f+mGR.mHardle[i].x, mGR.mHardle[i].y, mGR.mTex_Tile[0].width(), mGR.mTex_Tile[0].Height(),
						mGR.mZombie.x, mGR.mZombie.y, mGR.mTex_Zombie[0].width()*.5f, mGR.mTex_Zombie[0].Height())){
					mGR.mHardle[i].isCoin =false;
					M.sound7(R.drawable.coin_k);
					mGR.mScore+=10;
					mGR.mTotal+=10;
				}
			}
		}
		DrawTexture(gl, mGR.mTex_Score, 0, .75f);
		drawNumber(gl, (int)mGR.mScore, 0, .65f, 1);
		if(mGR.mZombie.gOver == 0){
			if(mGR.isgameStart){
				DrawTexture(gl, mGR.mTex_Zombie[Counter%mGR.mTex_Zombie.length], mGR.mZombie.x, mGR.mZombie.y);
				gameLogic();
			}
			else
				DrawTexture(gl, mGR.mTex_Zombie[0], mGR.mZombie.x, mGR.mZombie.y);
				
		}
		else{
			DrawTexture(gl, mGR.mTex_Zombie[0], mGR.mZombie.x, mGR.mZombie.y);
			if(mGR.mZombie.gOver == 1){
				M.sound2(R.drawable.game_over);
			}
			if(mGR.mZombie.gOver > 20){
				M.stopplay();
				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.ShowInterstitial();
				GameRenderer.mStart.Achivment();
			}
			mGR.mZombie.gOver++;
		}
	}
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			if(!mGR.isgameStart){
				mGR.isgameStart = true;
			}
			if(mGR.mZombie.jump == 0){
				M.sound3lay(R.drawable.jump);
				mGR.mZombie.Jump();
			}
		}
		return true;
	}
	
	void Draw_Menu(GL10 gl){
		for (int i = 0; i < mGR.mBack.length; i++) {
			DrawTexture(gl, mGR.mTex_BG, mGR.mBack[i], .25f);
		}
		for (int i = 0; i < mGR.mBase.length; i++) {
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i], -.74f);
		}
		DrawTexture(gl, mGR.mTex_text, 0, .5f);
		DrawTransScal(gl, mGR.mTex_play, 0, 0, sel == 1 ? 1.1f : 1,sel == 1 ? .5f : 1);
		
		DrawTexture(gl, mGR.mTex_strip, .75f,.29f);
		DrawTransScal(gl, mGR.mTex_rate, 0.75f, .70f, sel == 2 ? 1.1f : 1,sel == 2 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_share,0.75f, .25f, sel == 3 ? 1.1f : 1,sel == 3 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_achi, 0.75f,-.20f, sel == 4 ? 1.1f : 1,sel == 4 ? .5f : 1);
	}
	boolean Handle_Menu(MotionEvent event){
		sel =0;
		if(CirCir( 0, 0, mGR.mTex_play.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =1;
		}
		if(CirCir( 0.75f, .70f, mGR.mTex_rate.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =2;
		}
		if(CirCir( 0.75f, .25f, mGR.mTex_share.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =3;
		}
		if(CirCir( 0.75f,-.20f, mGR.mTex_achi.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				RateUs();
				break;
			case 3:
				share2friend();
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			}
			M.sound1(R.drawable.click);
			sel =0;
		}
		return true;
	}
	
	
	
	
	void Draw_Over(GL10 gl){
		for (int i = 0; i < mGR.mBack.length; i++) {
			DrawTexture(gl, mGR.mTex_BG, mGR.mBack[i], .25f);
		}
		for (int i = 0; i < mGR.mBase.length; i++) {
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i], -.74f);
		}
		DrawTexture(gl, mGR.mTex_gbox, 0, .11f);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_GOver, 0, .60f);
		else
			DrawTexture(gl, mGR.mTex_Pause, 0, .60f);
		drawNumber(gl, (int)mGR.mScore, 0, .18f, 1);
		drawNumber(gl, mGR.mHScore, 0,-.20f, 1);
		
		
		DrawTransScal(gl, mGR.mTex_play, -.75f, 0, sel == 1 ? 1.1f : 1,sel == 1 ? .5f : 1);
		
		DrawTexture(gl, mGR.mTex_strip, .75f,.29f);
		DrawTransScal(gl, mGR.mTex_home, 0.75f, .70f, sel == 2 ? 1.1f : 1,sel == 2 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_lether,0.75f, .25f, sel == 3 ? 1.1f : 1,sel == 3 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_achi, 0.75f,-.20f, sel == 4 ? 1.1f : 1,sel == 4 ? .5f : 1);
	}
	boolean Handle_Over(MotionEvent event){
		sel =0;
		if(CirCir( -.75f, 0, mGR.mTex_play.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =1;
		}
		if(CirCir( 0.75f, .70f, mGR.mTex_home.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =2;
		}
		if(CirCir( 0.75f, .25f, mGR.mTex_lether.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =3;
		}
		if(CirCir( 0.75f,-.20f, mGR.mTex_ads.width()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			sel =4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && sel >0){
			switch (sel) {
			case 1:
				if(M.GameScreen == M.GAMEOVER)
					mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			}
			M.sound1(R.drawable.click);
			sel =0;
		}
		return true;
	}
	void DrawTexture(GL10 gl, APlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, APlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, APlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}

	void DrawFlip(GL10 gl, APlane Tex, float x, float y) {
		Tex.drawFilp(gl, x, y);
	}

	void DrawTransScal(GL10 gl, APlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}

	float screen2worldX(float a) {
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}

	boolean Rect2RectIntersection(float ax, float ay, float adx, float ady,
			float bx, float by, float bdx, float bdy) {
		ax -= adx / 2;
		ay += ady / 2;
		bx -= bdx / 2;
		by += bdy / 2;
		if (ax + adx > bx && ay - ady < by && bx + bdx > ax && by - bdy < ay) {
			return true;
		}
		return false;
	}

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2,
			double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2))
				+ ((cy1 - cy2) * (cy1 - cy2)));
		if (bVectMag < (r1 + r2))
			return true;
		return false;

	}

	double GetAngle(double d, double e) {
		if (d == 0)
			return e >= 0 ? Math.PI / 2 : -Math.PI / 2;
		else if (d > 0)
			return Math.atan(e / d);
		else
			return Math.atan(e / d) + Math.PI;
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
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+ GameRenderer.mContext.getResources().getString(R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+ M.SHARELINK + getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	int _keyCode = 0;
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	public void setting(){float ud=.01f;switch (_keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
}
