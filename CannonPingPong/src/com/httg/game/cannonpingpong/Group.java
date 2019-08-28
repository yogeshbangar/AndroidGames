package com.httg.game.cannonpingpong;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	int Sel = 0;
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
	float lx =1,ly=-0.02f;
	@Override
	public void draw(GL10 gl) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTextureS(gl, mGR.mTex_Logo, 0, 0,lx,lx);
			if(lx > 1.2){
				ly = -.01f;
			}
			if(lx < .9){
				ly = .01f;
			}
			lx+=ly;
			if(Counter>60){
				M.GameScreen =M.GAMEMENU;
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Draw_Over(gl);
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
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			Handle_Over(event);
			break;
		}

		Handle(event);
		return true;
	}
	
	
	
	void Draw_Menu(GL10 gl){
		DrawTextureS(gl, mGR.mTex_Sky, 0, .24f, 51f,1);
		for(int i =0;i<mGR.mClude.length;i++){
			DrawTexture(gl, mGR.mTex_Clud, mGR.mClude[i].x, mGR.mClude[i].y);
			mGR.mClude[i].x+=mGR.spd*.1f;
			if(mGR.mClude[i].x< -1.4f)
				mGR.mClude[i].x = mGR.mClude[(i==0?mGR.mClude.length:i)-1].x+1;
		}
		
		for(int i =0;i<mGR.mBG.length;i++){
			DrawTexture(gl, mGR.mTex_BG, mGR.mBG[i], -.35f);
		}
		for(int i =0;i<mGR.mTree.length;i++){
			DrawTexture(gl, mGR.mTex_Tree[i%2], mGR.mTree[i], i%2 == 0 ?-.53f:-.49f);
		}
		
		for(int i =0;i<mGR.mBase.length;i++){
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i],-.9f);
		}
		
		DrawTextureS(gl, mGR.mTex_Name, -.29f,.24f,lx,lx);
		if(lx > 1.05){
			ly = -.001f;
			lx = 1.05f;
		}
		if(lx < .95){
			ly = .001f;
			lx = 0.95f;
		}
		lx+=ly;
		DrawTexture(gl, mGR.mTex_Top, -.8f, -.47f);
		DrawTransScal(gl, mGR.mTex_Play , .64f,0.36f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Music, .44f,-.06f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound, .84f,-.06f,Sel == 3?1.1f:1,Sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiv, -.87f,.85f,Sel == 4?1.1f:1,Sel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Share, -.67f,.85f,Sel == 5?1.1f:1,Sel == 5?0.5f:1);
		
		if(!M.setMusic)
			DrawTransScal(gl, mGR.mTex_Cross, .44f,-.06f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		if(!M.setValue)
			DrawTransScal(gl, mGR.mTex_Cross, .84f,-.06f,Sel == 3?1.1f:1,Sel == 3?0.5f:1);
		
		
		for(int i =0;i<mGR.mBase.length;i++){
			mGR.mBase[i] += mGR.spd;
		}
		for(int i =0;i<mGR.mBase.length;i++){
			if(mGR.mBase[i]< -1.2f)
				mGR.mBase[i] = mGR.mBase[(i==0?mGR.mBase.length:i)-1]+mGR.mTex_Base.width();
		}
		
		for(int i =0;i<mGR.mTree.length;i++){
			mGR.mTree[i] += mGR.spd*.4f;
		}
		for(int i =0;i<mGR.mTree.length;i++){
			if(mGR.mTree[i]< -1.2f)
				mGR.mTree[i] = mGR.mTree[(i==0?mGR.mTree.length:i)-1]+(M.mRand.nextInt(5)*.1f)+.2f;
		}
		
		for(int i =0;i<mGR.mBG.length;i++){
			mGR.mBG[i] += mGR.spd*.2f;
		}
		for(int i =0;i<mGR.mBG.length;i++){
			if(mGR.mBG[i]< -2f)
				mGR.mBG[i] = mGR.mBG[(i==0?mGR.mBG.length:i)-1]+mGR.mTex_BG.width();
		}
		
	}
	boolean Handle_Menu(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(.64f,0.36f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 1;
		}
		if(CircRectsOverlap(.44f,-.06f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 2;
		}
		if(CircRectsOverlap(.84f,-.06f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 3;
		}
		if(CircRectsOverlap(-.87f,.85f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 4;
		}
		if(CircRectsOverlap(-.67f,.85f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel >0){
			switch (Sel) {
			case 1:
				mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				M.setMusic =!M.setMusic;
				break;
			case 3:
				M.setValue =!M.setValue; 
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 5:
				share2friend();
				break;
			}
			M.sound6(R.drawable.click);
			Sel = 0;
		}
		return true;
	}
	
	void Draw_Over(GL10 gl){
		DrawTextureS(gl, mGR.mTex_Sky, 0, .24f, 51f,1);
		for(int i =0;i<mGR.mClude.length;i++){
			DrawTexture(gl, mGR.mTex_Clud, mGR.mClude[i].x, mGR.mClude[i].y);
			mGR.mClude[i].x+=mGR.spd*.1f;
			if(mGR.mClude[i].x< -1.4f)
				mGR.mClude[i].x = mGR.mClude[(i==0?mGR.mClude.length:i)-1].x+1;
		}
		
		for(int i =0;i<mGR.mBG.length;i++){
			DrawTexture(gl, mGR.mTex_BG, mGR.mBG[i], -.35f);
		}
		for(int i =0;i<mGR.mTree.length;i++){
			DrawTexture(gl, mGR.mTex_Tree[i%2], mGR.mTree[i], i%2 == 0 ?-.53f:-.49f);
		}
		
		for(int i =0;i<mGR.mBase.length;i++){
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i],-.9f);
		}
		
		DrawTexture(gl, mGR.mTex_Popup, 0,.16f);
		
		drawNumber(gl, mGR.mScore, .07f, .27f);
		drawNumber(gl, mGR.mHScore,.07f, .05f);
		
		DrawTransScal(gl, mGR.mTex_Retry, -.30f,-.30f,Sel == 1?1.1f:1,Sel == 1?0.5f:1);
		if(M.GameScreen ==M.GAMEOVER)
			DrawTransScal(gl, mGR.mTex_More	, -.10f,-.30f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		else
			DrawTransScal(gl, mGR.mTex_Play	, -.10f,-.30f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leadr, 0.10f,-.30f,Sel == 3?1.1f:1,Sel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu	, 0.30f,-.30f,Sel == 4?1.1f:1,Sel == 4?0.5f:1);

		if(!M.setMusic)
			DrawTransScal(gl, mGR.mTex_Cross, .44f,-.06f,Sel == 2?1.1f:1,Sel == 2?0.5f:1);
		if(!M.setValue)
			DrawTransScal(gl, mGR.mTex_Cross, .84f,-.06f,Sel == 3?1.1f:1,Sel == 3?0.5f:1);
	}
	boolean Handle_Over(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(-.30f,-.30f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 1;
		}
		if(CircRectsOverlap(-.10f,-.30f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 2;
		}
		if(CircRectsOverlap(0.10f,-.30f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 3;
		}
		if(CircRectsOverlap(0.30f,-.30f, mGR.mTex_Play.width()*.45f, mGR.mTex_Play.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			Sel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				if(M.GameScreen ==M.GAMEOVER)
					MoreGame();
				else{
					mGR.gameReset(true);
					M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.bg);
				}
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board 
				break;
			case 4:
				mGR.gameReset(true);
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound6(R.drawable.click);
			Sel = 0;
		}
		return true;
	}
	
	
	void setNew(){
		for (int i = 0; i < mGR.mPad.length; i++){
			if(mGR.mPad[i].x < -1.2){
				mGR.mPad[i].set(1.1f);
				break;
			}
		}
	}
	void gameIover(){
		M.stop();
		M.sound2(R.drawable.gameover);
		GameRenderer.mStart.Achivment();
		M.GameScreen = M.GAMEOVER;
		if(mGR.adsCount %10==0)
			GameRenderer.mStart.ShowInterstitial();
		if(mGR.adsCount %10==5)
			GameRenderer.mStart.ShowSmart();
		mGR.adsCount++;
	}
	void engine(){
		
		for(int i =0;i<mGR.mBase.length;i++){
			mGR.mBase[i] += mGR.spd;
		}
		for(int i =0;i<mGR.mBase.length;i++){
			if(mGR.mBase[i]< -1.2f)
				mGR.mBase[i] = mGR.mBase[(i==0?mGR.mBase.length:i)-1]+mGR.mTex_Base.width();
		}
		
		for(int i =0;i<mGR.mTree.length;i++){
			mGR.mTree[i] += mGR.spd*.6f;
		}
		for(int i =0;i<mGR.mTree.length;i++){
			if(mGR.mTree[i]< -1.2f)
				mGR.mTree[i] = mGR.mTree[(i==0?mGR.mTree.length:i)-1]+(M.mRand.nextInt(5)*.1f)+.2f;
		}
		
		for(int i =0;i<mGR.mBG.length;i++){
			mGR.mBG[i] += mGR.spd*.3f;
		}
		for(int i =0;i<mGR.mBG.length;i++){
			if(mGR.mBG[i]< -2f)
				mGR.mBG[i] = mGR.mBG[(i==0?mGR.mBG.length:i)-1]+mGR.mTex_BG.width();
		}
		
		mGR.mBall.update();
		if (mGR.topx > -1.5f)
			mGR.topx += mGR.spd;
		for (int i = 0; i < mGR.mPad.length; i++) {
			if (mGR.mPad[i].isMove|| mGR.mPad[i].x >.9) {
				mGR.mPad[i].update();
				if (CircRectsOverlap(mGR.mPad[i].x, mGR.mPad[i].y + mGR.mTex_pad.Height() * .2f, mGR.mTex_pad.width() * .5,
						mGR.mTex_pad.Height() * .1f, mGR.mBall.x, mGR.mBall.y, mGR.mTex_Ball.width() * .5)) {
					M.sound1(R.drawable.crash);
					mGR.mBall.setnew();
					setNew();
					mGR.mPad[i].Colide = true;
					
					for(int m =0;m<mGR.mParticle.length;m++){
						mGR.mParticle[m].set(mGR.mPad[i].x, mGR.mPad[i].y);
					}
					mGR.mTotal++;
					mGR.mScore++;
					if (mGR.mHScore < mGR.mScore)
						mGR.mHScore = mGR.mScore;
				}
			}
		}
		if(mGR.mBall.y < -1.1)
		{
			gameIover();
		}
		for(int i =0;i<mGR.mAnim.length;i++){
			if(mGR.mAnim[i].sz<=0){
				mGR.mAnim[i].set(mGR.mBall.x, mGR.mBall.y);
				break;
			}
		}
	}
	void Draw_GamePlay(GL10 gl){
		DrawTextureS(gl, mGR.mTex_Sky, 0, .24f, 51f,1);
		for(int i =0;i<mGR.mClude.length;i++){
			DrawTexture(gl, mGR.mTex_Clud, mGR.mClude[i].x, mGR.mClude[i].y);
			mGR.mClude[i].x+=mGR.spd*.1f;
			if(mGR.mClude[i].x< -1.4f)
				mGR.mClude[i].x = mGR.mClude[(i==0?mGR.mClude.length:i)-1].x+1;
		}
		for(int i =0;i<mGR.mBG.length;i++){
			DrawTexture(gl, mGR.mTex_BG, mGR.mBG[i], -.35f);
		}
		for(int i =0;i<mGR.mTree.length;i++){
			DrawTexture(gl, mGR.mTex_Tree[i%2], mGR.mTree[i], i%2 == 0 ?-.53f:-.49f);
		}
		
		for(int i =0;i<mGR.mBase.length;i++){
			DrawTexture(gl, mGR.mTex_Base, mGR.mBase[i],-.9f);
		}
		
		DrawTexture(gl, mGR.mTex_Top, mGR.topx, -.47f);
		for(int i =0;i<mGR.mAnim.length;i++){
			if(mGR.mAnim[i].update()){
				DrawTextureS(gl, mGR.mTex_Smock,mGR.mAnim[i].x,mGR.mAnim[i].y,mGR.mAnim[i].sz,mGR.mAnim[i].sz);
			}
		}
		if (mGR.mBall.shoot > 0)
			DrawTextureR(gl, mGR.mTex_Ball, mGR.mBall.x, mGR.mBall.y,-Counter*3);
//		else
//		DrawTextureR(gl, mGR.mTex_Ball, sx,sy,-Counter*3);
		if (mGR.mBall.shoot >= 0 && mGR.mBall.shoot < mGR.mTex_Blast.length) {
			DrawTexture(gl, mGR.mTex_Blast[mGR.mBall.shoot], -.61f, -.2f);
			if(Counter%2==0)
				mGR.mBall.shoot++;
		}
		
		for(int i =0;i<mGR.mPad.length;i++){
			if(mGR.mPad[i].x > -1.1){
				if(mGR.mPad[i].Colide)
					DrawTexture(gl, mGR.mTex_Cart, mGR.mPad[i].x, mGR.mPad[i].y-.08f);
				else
					DrawTexture(gl, mGR.mTex_pad, mGR.mPad[i].x, mGR.mPad[i].y);
				if(mGR.mPad[i].isMove || mGR.mPad[i].x>.9){
					DrawTexture(gl, mGR.mTex_Wheel[Counter%4 < 2 ?1:0], mGR.mPad[i].x-.06f, mGR.mPad[i].y-.11f);
					DrawTexture(gl, mGR.mTex_Wheel[Counter%4 < 2 ?1:0], mGR.mPad[i].x+.04f, mGR.mPad[i].y-.11f);
				}else{
					if(mGR.mBall.shoot >=0){
						DrawTexture(gl, mGR.mTex_Wheel[Counter%8 < 4 ?1:0], mGR.mPad[i].x-.06f, mGR.mPad[i].y-.11f);
						DrawTexture(gl, mGR.mTex_Wheel[Counter%8 < 4 ?1:0], mGR.mPad[i].x+.04f, mGR.mPad[i].y-.11f);
					}else{
						DrawTexture(gl, mGR.mTex_Wheel[0], mGR.mPad[i].x-.06f, mGR.mPad[i].y-.11f);
						DrawTexture(gl, mGR.mTex_Wheel[1], mGR.mPad[i].x+.04f, mGR.mPad[i].y-.11f);
					}
				}
			}
		}
		for(int i =0;i<mGR.mParticle.length;i++){
			if(mGR.mParticle[i].update()){
				DrawTextureR(gl, mGR.mTex_Particle[i%mGR.mTex_Particle.length], mGR.mParticle[i].x, mGR.mParticle[i].y,mGR.mParticle[i].rot*Counter);
			}
		}
		drawNumber(gl, mGR.mScore, .8f, .88f);
		if (mGR.mBall.shoot > 0) {
			engine();
			if(mGR.mBall.hand && Counter%8 <4)
				DrawTexture(gl, mGR.mTex_Hand, 0.8f,0f);
		}
		else{
			if(Counter%8 <4)
				DrawTexture(gl, mGR.mTex_Hand, -.8f,0f);
			
		}
//		DrawTexture(gl, mGR.mTex_pad, 0, 0);
	}
	
	boolean Handle_GamePlay(MotionEvent event){
		if(MotionEvent.ACTION_UP == event.getAction()){
			/*if(event.getX() < 100 && event.getY() <100){
				mGR.gameReset();
			} else*/ {
				if (mGR.mBall.shoot == -1) {
					mGR.mBall.shoot = 0;
					M.sound5(R.drawable.shoot);
				} else {
					if(mGR.mBall.hand)
						mGR.mBall.hand = false;
					M.sound3(R.drawable.kart);
					for (int i = 0; i < mGR.mPad.length; i++)
						mGR.mPad[i].isMove = true;
				}
			}
		}
		return true;
	}
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRot(gl, x, y, angle);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scx,float scy)
	{
		Tex.drawScal(gl, x, y,scx,scy);
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
		float dx = mGR.mTex_Font[0].width()*.6f;
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
		Uri uri = Uri.parse("android.resource://"+getClass().getPackage().getName()+"/drawable/share");
		Intent shareIntent = new Intent();
		shareIntent.setType("image/png");
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+ " " + mGR.mScore+ " "
								+ " and Best Score is "+mGR.mHScore+ " "
								+ "in  "+ GameRenderer.mContext.getResources().getString(R.string.app_name)
								+ ". Can you beat me..."
								+ "Let the battle commence!!! Download it now and let’s ROCK!!!!  "
								+ "https://play.google.com/store/apps/details?id=" + getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(
					shareIntent, "Share from"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
