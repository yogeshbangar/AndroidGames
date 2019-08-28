package com.htt.games.motospeedking;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends AMesh 
{
	
	GameRenderer mGR = null;
	int Sel =0;
	int Counter =0;
	float sx,sy;
	int _keyCode = 0;
	public void setting(){float ud=.01f;switch (_keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen = M.BUT;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.mTex_splash2.drawDark(gl, 0, 0,1);
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>90)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_GameMenu(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Draw_GameOver(gl);
			break;
		case M.GAMESETTING:
			Draw_GameSet(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.BUT:
			Draw_But(gl);
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
			Handle_GameMenu(event);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMESETTING:
			Handle_GameSet(event);
			break;
		case M.GAMEPLAY:
			Handle_Game(event);
			break;
		case M.BUT:
			Handle_But(event);
			break;
		}
		
//		Handle(event);
		return true;
	}
	
	
	
	void Draw_But(GL10 gl) {
		DrawTexture(gl, mGR.mTex_splash2, 0, 0);

		for (int i = 0; i < 30; i++)
			DrawTransScal(gl, mGR.mTex_seti, -.82f + (i % 4) * .5f,
					.89f - (i / 4) * .35f, Sel == i + 1 ? 1.1f : 1,
					Sel == i + 1 ? 0.5f : 1);

	}

	boolean Handle_But(MotionEvent event) {
		Sel = 0;
		for (int i = 0; i < 30; i++) {
			if (CircRectsOverlap(-.82f + (i % 4) * .5f,
					.89f - (i / 4) * .35f,
					mGR.mTex_seti.width() * .5f, mGR.mTex_seti.Height() * .5f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),
					.02f)) {
				Sel = i + 1;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			Sel = 0;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	void Draw_GameMenu(GL10 gl){
		DrawTexture(gl, mGR.mTex_splash2, 0, 0);
		
//		DrawTransScal(gl, mGR.mTex_ads, -.82f, .89f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_seti, 0.82f, .89f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_more[0], -.80f, -.72f,Sel == 3?1.1f:1,Sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_more[1], -.40f, -.72f,Sel == 4?1.1f:1,Sel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_more[2], -.00f, -.72f,Sel == 5?1.1f:1,Sel == 5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_play, .65f, -.68f,Sel == 6?1.1f:1,Sel == 6?0.5f:1);
	}
	boolean Handle_GameMenu(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.82f, .89f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =1;
		}
		if(CircRectsOverlap(0.82f, .89f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =2;
		}
		if(CircRectsOverlap(-.80f, -.72f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =3;
		}
		if(CircRectsOverlap(-.40f, -.72f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =4;
		}
		if(CircRectsOverlap(-.00f, -.72f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =5;
		}
		if(CircRectsOverlap(.65f, -.68f, mGR.mTex_play.width()*.5f, mGR.mTex_play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =6;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 1:
				// ADS
				break;
			case 2:
				M.GameScreen = M.GAMESETTING;
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				facebook();
				break;
			case 5:
				share2friend();
				break;
			case 6:
				mGR.gameReset(true);
				M.play(R.drawable.bg);
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			M.sound1(R.drawable.click);
			Sel = 0;
		}
		return true;
	}
	
	void Draw_GameSet(GL10 gl){
		Draw_BG(gl);
		mGR.mTex_splash2.drawDark(gl, 0, 0, .55f);
		
		DrawTexture(gl, mGR.mTex_settingT, 0, .43f);
		
		DrawTexture(gl, mGR.mTex_sound , -.22f, 0.10f);
		DrawTransScal(gl, mGR.mTex_soundB, 0.22f, 0.10f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		if(!M.setValue)
			DrawTransScal(gl, mGR.mTex_soundO, 0.22f, 0.10f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_music , -.22f, -.15f);
		DrawTransScal(gl, mGR.mTex_musicB, 0.22f, -.15f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		if(!M.setMusic)
			DrawTransScal(gl, mGR.mTex_soundO, 0.22f, -.15f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_play, 0,-.45f,Sel == 6?1.1f:1,Sel == 6?0.5f:1);
				
	}
	boolean Handle_GameSet(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(0.22f, 0.10f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =1;
		}
		if(CircRectsOverlap(0.22f, -.15f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =2;
		}
		if(CircRectsOverlap(.0f, -.45f, mGR.mTex_play.width()*.5f, mGR.mTex_play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =6;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 1:
				M.setValue = !M.setValue;// ADS
				break;
			case 2:
				M.setMusic = !M.setMusic;// ADS
				break;
			case 6:
				mGR.gameReset(true);
				M.play(R.drawable.bg);
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			M.sound1(R.drawable.click);
			Sel = 0;
		}
		return true;
	}
	
	
	void Draw_GameOver(GL10 gl){
		Draw_BG(gl);
		mGR.mTex_splash2.drawDark(gl, 0, 0,.55f);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_GOver, 0, .43f);
		else
			DrawTexture(gl, mGR.mTex_Pause, 0, .43f);
		
		DrawTexture(gl, mGR.mTex_score, 0, 0.20f);
		drawNumber(gl, (int)mGR.mScore, 0, .1f, 1, 0);
		
		if(mGR.NewBest == 1){
			DrawTexture(gl, mGR.mTex_NewBest, 0,-.05f);
			drawNumber(gl, mGR.mHScore, 0,-.2f, 1, 1);
		}
		else{
			DrawTexture(gl, mGR.mTex_BScore, 0,0.00f);
			drawNumber(gl, mGR.mHScore, 0,-.1f, 1, 0);
		}
		
//		DrawTransScal(gl, mGR.mTex_ads, -.82f, .89f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_seti, 0.82f, .89f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		
		for(int i =0;i<5;i++)
			DrawTransScal(gl, mGR.mTex_more[i], -.76f+.37f*i, -.38f,Sel == i+3?1.1f:1,Sel == i+3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_play, 0,-.65f,Sel == 8?1.1f:1,Sel == 8?0.5f:1);
	}
	boolean Handle_GameOver(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.82f, .89f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =1;
		}
		if(CircRectsOverlap(0.82f, .89f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =2;
		}
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.76f+.37f*i, -.38f, mGR.mTex_seti.width()*.5f, mGR.mTex_seti.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				Sel =3+i;
			}
		}
		if(CircRectsOverlap(.0f, -.65f, mGR.mTex_play.width()*.5f, mGR.mTex_play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel =8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel > 0) {
			switch (Sel) {
			case 1:
				// ADS
				break;
			case 2:
				M.GameScreen = M.GAMESETTING;
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				facebook();
				break;
			case 5:
				share2friend();
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 7:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 8:
				if(M.GameScreen == M.GAMEOVER)
					mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			}
			M.sound1(R.drawable.click);
			Sel = 0;
		}
		return true;
	}
	
	
	
	
	
	void gamelogic() {
		for (int i = 0; i < mGR.mScan.length; i++) {
			mGR.mScan[i].y += M.SPD;
		}
		for (int i = 0; i < mGR.mScan.length; i++) {
//			if (mGR.mScan[i].y < -0) 
			{
				for (int j = 0; j < Road.ROD[mGR.mScan[i].no].length; j++) {
					if (mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() < 0
							&& mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() > -.7f) {
						for (int k = 0; k < Road.ROD[mGR.mScan[i].no][j].length; k++) {

							if (Road.ROD[mGR.mScan[i].no][j][k] > 0
									/*&& Road.ROD[mGR.mScan[i].no][j][k] <= mGR.mTex_Tile.length*/) {
								if (Rect2RectIntersection(-.8f + k * mGR.mTex_Tile[0].width(),
										mGR.mScan[i].y - j* mGR.mTex_Tile[0].Height(),
										mGR.mTex_Tile[0].width(),mGR.mTex_Tile[0].Height()*.8f,
										mGR.mDriver.x, mGR.mDriver.y,
										mGR.mTex_Driver.width()*.7f, mGR.mTex_Driver.Height()*.4f)) {
									M.SPD = 0;
									mGR.mDriver.blast = 0;
									M.sound2(R.drawable.game_over);
								}

							}
						}
					}
				}
			}
			if (mGR.mScan[i].y < -1.1) {
				mGR.mScan[i].set(mGR.mScan[i == 0 ? 1 : 0].y
						+ (26 * mGR.mTex_Tile[i].Height()));
			}
		}
		for (int i = 0; i < mGR.BGY.length; i++) {
			mGR.BGY[i] += M.SPD;
		}
		for (int i = 0; i < mGR.BGY.length; i++) {
			if (mGR.BGY[i] < -1.3f)
				mGR.BGY[i] = mGR.BGY[(i == 0 ? mGR.BGY.length : i) - 1]
						+ mGR.mTex_Road.Height();
		}
		if(M.SPD !=0)
			mGR.mDriver.update();
		
		mGR.mScore-=M.SPD*10;
		mGR.mTotal-=M.SPD*10;
		if(mGR.mScore > mGR.mHScore){
			mGR.mHScore = (int)mGR.mScore;
			if(mGR.NewBest == 0)
				mGR.NewBest = 1;
		}
		
	}
	void Draw_Gameplay(GL10 gl){
		Draw_BG(gl);
		drawNumber(gl, (int)mGR.mScore, 0, .85f, 1, 1);
		if(mGR.mDriver.blast == -1)
			gamelogic();
		if(mGR.mDriver.blast == -2 && Counter % 8 <4){
			DrawTexture(gl,mGR.mTex_tabL,-.5f,-.7f);
			DrawTexture(gl,mGR.mTex_tabR,0.5f,-.7f);
		}
		if(mGR.mDriver.blast >= mGR.mTex_Blast.length){
			GameRenderer.mStart.ShowInterstitial();
			M.GameScreen = M.GAMEOVER;
			GameRenderer.mStart.Achivment();
			M.BGSTOP();
		}
	}

	boolean Handle_Game(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (mGR.mDriver.blast == -2) {
				mGR.mDriver.blast = -1;
			} else {
				if (event.getX() < M.ScreenWidth / 2) {
					mGR.mDriver.vx += mGR.mTex_Tile[0].width();
				} else {
					mGR.mDriver.vx -= mGR.mTex_Tile[0].width();
				}
				M.sound3(R.drawable.jump);
			}
		}
		return true;
	}
	
	
	void Draw_BG(GL10 gl){
		for(int i =0; i<mGR.BGY.length;i++){
			DrawTexture(gl,mGR.mTex_Road,0,mGR.BGY[i]);
		}
		for (int i = 0; i < mGR.mScan.length; i++) {
			for (int j = 0; j < Road.ROD[mGR.mScan[i].no].length; j++) {
				for (int k = 0; k < Road.ROD[mGR.mScan[i].no][j].length; k++) {
					if (mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() < 1.1&& mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() > -1.1f) {
						if (Road.ROD[mGR.mScan[i].no][j][k] > 0) {
							int no = Road.ROD[mGR.mScan[i].no][j][k] - 1;
							switch (no) {
							case 17:
								no = 0;
								break;
							case 18:
								no = 1;
								break;
							case 19:
								no = 15;
								break;
							case 20:
								no = 14;
								break;
							case 21:
								no = 4;
								break;
							case 22:
								no = 10;
								break;
							}
							DrawTexture(gl,mGR.mTex_Tile[no],-.8f + k * mGR.mTex_Tile[0].width(),
									mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height());
						}
					}
				}
			}
		}
		if(mGR.mDriver.blast < 0)
			DrawTexture(gl, mGR.mTex_Driver,mGR.mDriver.x, mGR.mDriver.y);
		else if(mGR.mDriver.blast < mGR.mTex_Blast.length){
			DrawTexture(gl, mGR.mTex_Blast[mGR.mDriver.blast],mGR.mDriver.x, mGR.mDriver.y);
			mGR.mDriver.blast++;
		}
		for(int i =0;i<mGR.mMove.length;i++){
			mGR.mMove[i].update();
		}
		int p =0;
		for (int i = 0; i < mGR.mScan.length; i++) {
			for (int j = 0; j < Road.ROD[mGR.mScan[i].no].length; j++) {
				for (int k = 0; k < Road.ROD[mGR.mScan[i].no][j].length; k++) {
					if (mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() < 1.1&& mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height() > -1.3f) {
						if (Road.ROD[mGR.mScan[i].no][j][k] > 16) {
							switch (Road.ROD[mGR.mScan[i].no][j][k]) {
							case 17:
								p =0;
								break;
							case 18:
								p =1;
								break;
							case 19:
								p =2;
								break;
							case 20:
								p =3;
								break;
							case 21:
								p =4;
								break;
							case 22:
								p =5;
								break;
							}
							DrawTexture(gl,mGR.mTex_TreeS,-.8f + k * mGR.mTex_Tile[0].width(),
									mGR.mScan[i].y - j * mGR.mTex_Tile[0].Height()+.02f);
							mGR.mTex_Tree.drawRotet2(gl,-.8f + k * mGR.mTex_Tile[0].width(),mGR.mScan[i].y - j* mGR.mTex_Tile[0].Height()
									,mGR.mMove[p].move);
						}
					}
				}
			}
		}
	}
	void DrawTexture(GL10 gl,ZPlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTransScal(GL10 gl,ZPlane Tex,float x,float y, float z,float t)
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
		float c = ((a / M.ScreenHieght*(mGR.BackKey?0.92f:1))- 0.5f)*(-2);
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
	void drawNumber(GL10 gl, int no, float x, float y, int aling,int type) {
		float dx = mGR.mTex_Font[type][0].width()*.8f;
		if(type == 0)
			dx *=.64f; 
		String strs = "" + no;
		if(aling == 1)
			x-=dx*strs.length()*.45f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font[type].length)
				mGR.mTex_Font[type][k].drawPos(gl, x + i * dx, y);
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
