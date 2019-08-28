package com.hututu.game.deathwell;
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
	public void draw(GL10 gl) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>100)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEOVER:
			Draw_GameOver(gl);
			break;
		case M.GAMEMENU:
		case M.GAMEPLAY:
		case M.GAMEPAUSE:
			Draw_Game(gl);
			break;
		}
		Counter++;
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
		case M.GAMEPAUSE:
			Handle_Menu(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEPLAY:
			Handle_Game(event);
			break;
		}
		
		Handle(event);
		return true;
	}
	
	
	/**************************Game Start*************************/
	void gameLogic(){
		if (GameRenderer.mStart.mGR.Cir == M.DIS && Counter % 3 == 0) {
			mGR.mOpp[2].mCount-=2;
		}
		mGR.mPlayer.Update();
		for (int i = 0; i < mGR.mOpp.length; i++) {
			mGR.mOpp[i].Update(i);
		}
		
		
		for (int i = 0; i < mGR.mOpp.length; i++) {
			if(CircRectsOverlap(mGR.mPixel.getX(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),
					mGR.mPixel.getY(mGR.mOpp[i].mCount, mGR.mOpp[i].mul), mGR.mTex_Car.width()*.2f, mGR.mTex_Car.Height()*.2f, 
					mGR.mPixel.getX(mGR.mPlayer.mCount, mGR.mPlayer.mul),
					mGR.mPixel.getY(mGR.mPlayer.mCount, mGR.mPlayer.mul), mGR.mTex_Car.width()*.2f)){
				//M.GameScreen = M.GAMEOVER;
				M.stop();
				mGR.OverCount =1;
				
				mGR.oppCrash= (byte)i;
				SetCrashGo(mGR.mPixel.getX(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),
						mGR.mPixel.getY(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),i);
			}
		}
		
	}
	float z = 1,vz = .01f;
	void Draw_Game(GL10 gl) {
		DrawTexture(gl, mGR.mTex_GameBG, 0, 0);
		
		if(mGR.OverCount == 0){
			DrawTextureR(gl, mGR.mTex_Car,mGR.mPixel.getX(mGR.mPlayer.mCount, mGR.mPlayer.mul),
					mGR.mPixel.getY(mGR.mPlayer.mCount, mGR.mPlayer.mul),mGR.mPlayer.ang);
			for(int i =0;i<mGR.mOpp.length;i++){
				DrawTextureR(gl, mGR.mTex_Opp[i],mGR.mPixel.getX(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),
						mGR.mPixel.getY(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),-mGR.mOpp[i].ang);
			}
		}else{
			DrawTexture(gl, mGR.mTex_Blast[0], mGR.mPixel.getX(mGR.mPlayer.mCount, mGR.mPlayer.mul),
					mGR.mPixel.getY(mGR.mPlayer.mCount, mGR.mPlayer.mul));
			for(int i =0;i<mGR.mOpp.length;i++){
				if (mGR.oppCrash == i) {
					DrawTexture(gl, mGR.mTex_Blast[mGR.oppCrash+1], mGR.mPixel.getX(mGR.mOpp[i].mCount,
							mGR.mOpp[i].mul),mGR.mPixel.getY(mGR.mOpp[i].mCount, mGR.mOpp[i].mul));
				} else {
					DrawTextureR(gl, mGR.mTex_Opp[i], mGR.mPixel.getX(mGR.mOpp[i].mCount,
							mGR.mOpp[i].mul),mGR.mPixel.getY(mGR.mOpp[i].mCount, mGR.mOpp[i].mul),
							-mGR.mOpp[i].ang);
				}
			}
		}
		
		drawNumber(gl, mGR.Cir, 0, 0,0);
		if (M.GameScreen == M.GAMEMENU || M.GameScreen == M.GAMEPAUSE) {
			DrawTransScal(gl, mGR.mTex_Icon, 0,0,mGR.mSel == 1?1.1f:z,mGR.mSel == 1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Play, 0,0,mGR.mSel == 1?1.1f:z,mGR.mSel == 1?0.5f:1);
			z+=vz;
			if(z > 1.1){
				vz = -.02f;
			}
			if(z < 0.95){
				vz = 0.02f;
			}
		} else if (mGR.OverCount == 0) {
			gameLogic();
		}
		if (mGR.OverCount > 0){
			if(mGR.OverCount == 3){
				M.sound1(R.drawable.carcrash);
			}
			CrashGo(gl);
			mGR.OverCount++;
			if(mGR.OverCount > 60){
				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.Achivment();
				if(mGR.count%2 ==0)
					GameRenderer.mStart.ShowInterstitial();
			}
		}
	}

	boolean Handle_Game(MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (MotionEvent.ACTION_UP == event.getAction()) {
				if (mGR.mPlayer.mPath == 0) {
					mGR.mPlayer.mPath = 1;
				} else {
					mGR.mPlayer.mPath = 0;
				}
				M.sound3(R.drawable.swing);
			}
		}
		return true;
	}
	/**************************Game End*************************/
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel =0;
		if(CircRectsOverlap(0,0, mGR.mTex_Icon.width()*.5f, mGR.mTex_Icon.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMEMENU)
					mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay);
				break;
			}
			if(mGR.mSel !=0)
				M.sound2(R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	/**************************gameOver Start*************************/
	void Draw_GameOver(GL10 gl){
//		DrawTexture(gl, mGR.mTex_GameBG, 0, 0);
//		mGR.mTex_GameBG.drawDark(gl, 0, 0,.5f);
		DrawTransScal(gl, mGR.mTex_GameBG,0,0,1,.5f);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTexture(gl, mGR.mTex_Over, 0, .26f);
		drawNumber(gl, mGR.Cir, -.00f, 0.07f,1);
		DrawTexture(gl, mGR.mTex_Score, 0, -.07f);
		drawNumber(gl, mGR.BestCir, -.0f, -.19f,0);
		
		for(int i =0;i<4;i++)
			DrawTransScal(gl, mGR.mTex_Icon, -.36f+.24f*i, -.51f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_fb		, -.36f+.24f*0, -.51f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_LBoard	, -.36f+.24f*1, -.51f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Retry	, -.36f+.24f*2, -.51f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0], -.36f+.24f*3, -.51f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
	}
	boolean Handle_GameOver(MotionEvent event){
		mGR.mSel =0;
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(-.36f+.24f*i, -.51f, mGR.mTex_Icon.width()*.5f, mGR.mTex_Icon.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				facebook();
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader - Board
				break;
			case 3:
				//Retry
				mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay);
				break;
			case 4:
				M.setValue = !M.setValue;
				break;
			}
			if(mGR.mSel !=0)
				M.sound2(R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	/**************************gameOver End*************************/
	
	void CrashGo(GL10 gl){
		for(int i =0;i<mGR.mCrash.length;i++){
			if(mGR.mCrash[i].update()){
				DrawTextureS(gl, mGR.mTex_Partical[mGR.mCrash[i].img], mGR.mCrash[i].x, mGR.mCrash[i].y, mGR.mCrash[i].z);
			}
		}
	}
	void SetCrashGo(float x,float y,int img){
		for(int i =0;i<mGR.mCrash.length;i++){
			mGR.mCrash[i].set(x, y, i < 20 ? (i % 2) : (img * 2 + (i % 2)));
		}
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
	void drawNumber(GL10 gl,int no,float x, float y,int type)
	{
		float dx = mGR.mTex_Font[type][0].width() * (type == 0 ? .6f : .8f);
		String strs = "" + no;
		x -=(strs.length()*dx*.5f - dx*.5f);
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font[type].length)
				mGR.mTex_Font[type][k].drawPos(gl, x + i * dx, y);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	    GameRenderer.mContext.startActivity(mIntent);
	}

	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
