package com.game2d.timberboy;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh {
	float ly=1.1f,lvy=.0f;
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
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, ly);
			ly+=lvy;
			lvy-=.001f;
			if(ly<0){
				lvy=0f;
			}
			
			if(Counter>60)
				M.GameScreen =M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEOVER:
			Draw_Over(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			DrawTexture(gl, mGR.mTex_FillBar, 0.0f, 0.9f);
			mGR.mTex_Red.drawSS(gl, -.25f, 0.9f, mGR.mPlayer.fill, 1);
			drawNumber(gl, mGR.mScore, .4f, .9f);
			
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
		case M.GAMEOVER:
			Handle_Over(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		}
		
		Handle(event);
		return true;
	}
	void setCut(int m){
		for(int i =0;i<mGR.mCutAnim.length;i++){
			if(!mGR.mCutAnim[i].isIn()){
				mGR.mCutAnim[i].set(0, mGR.mBranch[m].y,mGR.mBranch[m].branch, mGR.mPlayer.isRight);
				break;
			}
		}
	}
	void gamlogic(){
		mGR.mPlayer.cut =true;
		for (int i = 0; i < mGR.mBranch.length; i++) {
			if (mGR.mBranch[i].y < -.59) {
				mGR.mPlayer.cut = false;
			}
		}

		for (int i = 0; i < mGR.mBranch.length && mGR.mPlayer.cut; i++) {
			mGR.mBranch[i].y -= .1f;
		}
		if(M.GameScreen == M.GAMEPLAY)
		{
			for (int  i = 0; i < mGR.mBranch.length && mGR.mPlayer.OverCont==-1; i++) {
				if (mGR.mBranch[i].y < -.5) {
					if (mGR.mBranch[i].branch < 2 && !mGR.mPlayer.isRight) {
						mGR.mPlayer.OverCont=0;
						M.sound9(R.drawable.miss);
					}
					if (mGR.mBranch[i].branch >= 2 && mGR.mBranch[i].branch < 4
							&& mGR.mPlayer.isRight) {
						mGR.mPlayer.OverCont=0;
						M.sound9(R.drawable.miss);
					}
				}
			}
			if(mGR.mPlayer.fill>0 && mGR.mPlayer.OverCont ==-1 && !(mGR.mPlayer.tap)){
				mGR.mPlayer.fill -=.015f;
				if(mGR.mPlayer.fill<=0){
					mGR.mPlayer.OverCont =0;
					M.sound9(R.drawable.miss);
				}
			}
		}
	}
	void Draw_GamePlay(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG[mGR.mPlayer.BG], 0, 0);
		for(int i =0;i<mGR.mBranch.length;i++){
			if(mGR.mBranch[i].branch < 2){
				if(mGR.mBranch[i].branch == 0)
					mGR.mTex_Branch[mGR.mPlayer.BG][0].drawRot(gl, -.5f, mGR.mBranch[i].y,0);
				else
					mGR.mTex_Branch[3][0].drawRot(gl, -.5f, mGR.mBranch[i].y,0);
			}
			if(mGR.mBranch[i].branch >= 2 && mGR.mBranch[i].branch < 4){
				if(mGR.mBranch[i].branch == 2)
					mGR.mTex_Branch[mGR.mPlayer.BG][1].drawRot(gl, +.5f, mGR.mBranch[i].y,0);
				else
					mGR.mTex_Branch[3][1].drawRot(gl, +.5f, mGR.mBranch[i].y,0);
			}
			DrawTexture(gl, mGR.mTex_Block, 0, mGR.mBranch[i].y);
		}
		float bvx,bvy;
		for(int i =0;i<mGR.mCutAnim.length;i++){
			if(mGR.mCutAnim[i].update()){
				if(mGR.mCutAnim[i].branch >=0 && mGR.mCutAnim[i].branch < 4){
					if(mGR.mCutAnim[i].vx > 0){
						bvx = (float)(Math.cos(Math.toRadians(mGR.mCutAnim[i].rotat))*(0.5));
						bvy = (float)(Math.sin(Math.toRadians(mGR.mCutAnim[i].rotat))*(0.3));
						if(mGR.mCutAnim[i].branch == 2)
							mGR.mTex_Branch[mGR.mPlayer.BG][1].drawRot(gl, mGR.mCutAnim[i].x+bvx, mGR.mCutAnim[i].y+bvy,mGR.mCutAnim[i].rotat);
						else
							mGR.mTex_Branch[3][1].drawRot(gl, mGR.mCutAnim[i].x+bvx, mGR.mCutAnim[i].y+bvy,mGR.mCutAnim[i].rotat);
//						mGR.mTex_Branch[mGR.mCutAnim[i].branch].drawRot(gl, mGR.mCutAnim[i].x+bvx, mGR.mCutAnim[i].y+bvy,mGR.mCutAnim[i].rotat);
					}
					else {
						bvx = (float)(Math.cos(Math.toRadians(mGR.mCutAnim[i].rotat))*(-.5));
						bvy = (float)(Math.sin(Math.toRadians(mGR.mCutAnim[i].rotat))*(-.3));
						if(mGR.mCutAnim[i].branch == 0)
							mGR.mTex_Branch[mGR.mPlayer.BG][0].drawRot(gl,mGR.mCutAnim[i].x+bvx, mGR.mCutAnim[i].y+bvy,mGR.mCutAnim[i].rotat);
						else
							mGR.mTex_Branch[3][0].drawRot(gl,mGR.mCutAnim[i].x+bvx, mGR.mCutAnim[i].y+bvy,mGR.mCutAnim[i].rotat);
					}
				}
				mGR.mTex_CutBlock.drawRot(gl, mGR.mCutAnim[i].x, mGR.mCutAnim[i].y,mGR.mCutAnim[i].rotat);
			}
		}
		if (mGR.mPlayer.tap && Counter % 8 < 4) {
			DrawTexture(gl, mGR.mTex_Tab[0], -.7f, -.5f);
			DrawTexture(gl, mGR.mTex_Tab[1], 0.7f, -.5f);
		}
		if(mGR.mPlayer.OverCont>=0 && mGR.mPlayer.OverCont<mGR.mTex_smoke.length){
//			DrawTexture(gl, mGR.mTex_smoke[mGR.mPlayer.OverCont], mGR.mPlayer.isRight?.3f:-3f, -.65f);
			DrawTransScal(gl,  mGR.mTex_smoke[mGR.mPlayer.OverCont%8], mGR.mPlayer.isRight?.3f:-.3f, -.65f,2,1);
			if(Counter%2==0){
				mGR.mPlayer.OverCont++;
				if(mGR.mPlayer.OverCont == mGR.mTex_smoke.length){
					M.stop();
					M.GameScreen = M.GAMEOVER;
					M.sound8(R.drawable.gameover);
					GameRenderer.mStart.Achivment();
					if(mGR.AdcCount%5==0){
//						GameRenderer.mStart.ShowInterstitial();
						if(mGR.AdcCount%10==5)
							GameRenderer.mStart.ShowSmart();
						else
							GameRenderer.mStart.ShowInterstitial();
					}
					mGR.AdcCount++;
				}
			}
		}else if(mGR.mPlayer.OverCont ==-1){
			if (mGR.mPlayer.isRight) {
				if (mGR.mPlayer.hit > 0) {
					DrawTexture(gl, mGR.mTex_Player[1], 0.3f, -.65f);
					mGR.mPlayer.hit--;
				} else {
					DrawTexture(gl, mGR.mTex_Player[0], 0.3f, -.65f);
				}
			} else {
				if (mGR.mPlayer.hit > 0) {
					DrawTexture(gl, mGR.mTex_Player[3], -.3f, -.65f);
					mGR.mPlayer.hit--;
				} else {
					DrawTexture(gl, mGR.mTex_Player[2], -.3f, -.65f);
				}
			}
		}else{
			DrawTransScal(gl,  mGR.mTex_Rip, mGR.mPlayer.isRight?.35f:-.35f, -.65f,1.5f,1);
		}
//		if(M.GameScreen == M.GAMEPLAY)
			gamlogic();
	}
	
	
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlayer.OverCont ==-1){
			
			if(event.getX() < M.ScreenWidth/2){
				mGR.mPlayer.isRight = false;
			}else{
				mGR.mPlayer.isRight = true;
			}
			for (int i = 0; i < mGR.mBranch.length && mGR.mPlayer.OverCont==-1; i++) {
				if (mGR.mBranch[i].y < -.5) {
					if (mGR.mBranch[i].branch < 2 && !mGR.mPlayer.isRight) {
						mGR.mPlayer.OverCont=0;
						M.sound9(R.drawable.miss);
					}
					if (mGR.mBranch[i].branch >= 2 && mGR.mBranch[i].branch < 4
							&& mGR.mPlayer.isRight) {
						mGR.mPlayer.OverCont=0;
						M.sound9(R.drawable.miss);
					}
				}
				if(mGR.mPlayer.OverCont >-1)
					return false;
			}
			
			
			
			int k =0;
			for(int i =1;i<mGR.mBranch.length;i++){
				if(mGR.mBranch[i].y < mGR.mBranch[k].y){
					k =i;
				}
			}
			setCut(k);
			mGR.mBranch[k].set(mGR.mBranch[(k == 0? mGR.mBranch.length:k)-1].y+mGR.mTex_Block.Height(),k%2==0?M.mRand.nextInt(5):4);
			if(mGR.mPlayer.fill <3.5)
				mGR.mPlayer.fill +=.08f;
			mGR.mPlayer.hit =4;
			mGR.mPlayer.cut = true;
			if(mGR.mPlayer.tap)
				mGR.mPlayer.tap = false;
			
			mGR.mScore++;
			mGR.mTotal++;
			if(mGR.mHScore < mGR.mScore)
				mGR.mHScore =mGR.mScore;
			M.sound1(R.drawable.axe);
		}
		return true;
	}
	
	
	void Draw_Menu(GL10 gl){
		Draw_GamePlay(gl);
		DrawBlack(gl, mGR.mTex_BG[mGR.mPlayer.BG], 0, 0);
		
		DrawTransScal(gl, mGR.mTimberBoy, 0, 0.6f, 1,1);
		DrawTransScal(gl, mGR.mTex_play, .0f, .15f, Sel==1?1.1f:1, Sel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_lether	,-.6f, -.15f, Sel==2?1.1f:1, Sel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_fb		,-.2f, -.15f, Sel==3?1.1f:1, Sel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?1:0], .2f, -.15f, Sel==4?1.1f:1, Sel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_sound[M.setValue?1:0], .6f, -.15f, Sel==5?1.1f:1, Sel==5?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_rate	,-.0f, -.4f, Sel==6?1.1f:1, Sel==6?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_about,0.4f, -.4f, Sel==7?1.1f:1, Sel==7?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		Sel=0;
		if(CirCir(.0f, .15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=1;
		}
		if(CirCir(-.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=2;
		}
		if(CirCir(-.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=3;
		}
		if(CirCir(0.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=4;
		}
		if(CirCir(0.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=5;
		}
		if(CirCir(-.0f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=6;
		}
		if(CirCir(0.4f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=7;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel >0){
			switch (Sel) {
			case 1:
				mGR.mPlayer.tap = true;
				mGR.gameReset(true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.theme);
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 3:
				facebook();
				break;
			case 4:
				M.setMusic =!M.setMusic;
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			case 6:
				MoreGame();//RateUs();
				break;
			case 7:
//				M.GameScreen = M.GAMEABOUT;
				break;
			}
			M.sound7(R.drawable.click);
			Sel=0;
		}
		return true;
	}
	
	
	void Draw_Over(GL10 gl){
		Draw_GamePlay(gl);
//		DrawTexture(gl, mGR.mTex_Rip, mGR.mPlayer.isRight?.3f:-3f, -.65f);
		DrawBlack(gl, mGR.mTex_BG[mGR.mPlayer.BG], 0, 0);
		
		
		DrawTexture(gl,mGR.mTex_Over, 0, .70f);
		DrawTexture(gl, mGR.mTex_ScoreBg, 0, .47f);
		drawNumber(gl, mGR.mScore 	, 0,0.54f);
		drawNumber(gl, mGR.mHScore	, 0,0.41f);
		
		
		
		
		DrawTransScal(gl, mGR.mTex_Retry, .0f, .15f, Sel==1?1.1f:1, Sel==1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_lether	,-.6f, -.15f, Sel==2?1.1f:1, Sel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_rate		,-.2f, -.15f, Sel==3?1.1f:1, Sel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Home, .2f, -.15f, Sel==4?1.1f:1, Sel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ads, .6f, -.15f, Sel==5?1.1f:1, Sel==5?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_gp	,-.4f, -.4f, Sel==6?1.1f:1, Sel==6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Share,0.4f, -.4f, Sel==7?1.1f:1, Sel==7?0.5f:1);
	}
	boolean Handle_Over(MotionEvent event){
		Sel=0;
		if(CirCir(.0f, .15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=1;
		}
		if(CirCir(-.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=2;
		}
		if(CirCir(-.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=3;
		}
		if(CirCir(0.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=4;
		}
		if(CirCir(0.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=5;
		}
		if(CirCir(-.4f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=6;
		}
		if(CirCir(0.4f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=7;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				mGR.gameReset(true);
				mGR.mPlayer.tap = true;
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.theme);
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				mGR.gameReset(true);
				M.GameScreen =M.GAMEMENU;
				break;
			case 5:
				mGR.mInApp.onBuyGold10000(null);//Ads Free
				break;
			case 6:
				GameRenderer.mStart.onShowAchievementsRequested();
//				google();
				break;
			case 7:
				share2friend();
				break;
			}
			M.sound7(R.drawable.click);
			Sel=0;
		}
		return true;
	}
	
	

	
	void Draw_Pause(GL10 gl){
		Draw_GamePlay(gl);
		DrawBlack(gl, mGR.mTex_BG[mGR.mPlayer.BG], 0, 0);
		
		
		DrawTransScal(gl, mGR.mTex_play, .0f, .15f, Sel==1?1.1f:1, Sel==1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_lether	,-.6f, -.15f, Sel==2?1.1f:1, Sel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Home		,-.2f, -.15f, Sel==3?1.1f:1, Sel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?1:0], .2f, -.15f, Sel==4?1.1f:1, Sel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_sound[M.setValue?1:0], .6f, -.15f, Sel==5?1.1f:1, Sel==5?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_fb	,-.4f, -.4f, Sel==6?1.1f:1, Sel==6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Share,0.4f, -.4f, Sel==7?1.1f:1, Sel==7?0.5f:1);
	}
	boolean Handle_Pause(MotionEvent event){
		Sel=0;
		if(CirCir(.0f, .15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=1;
		}
		if(CirCir(-.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=2;
		}
		if(CirCir(-.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=3;
		}
		if(CirCir(0.2f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=4;
		}
		if(CirCir(0.6f, -.15f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=5;
		}
		if(CirCir(-.4f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=6;
		}
		if(CirCir(0.4f, -.4f, .1, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel=7;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel >0){
			switch (Sel) {
			case 1:
				mGR.mPlayer.tap = true;
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.theme);
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//LeaderBoard
				break;
			case 3:
				mGR.gameReset(true);
				M.GameScreen =M.GAMEMENU;
				break;
			case 4:
				M.setMusic =!M.setMusic;
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			case 6:
				facebook();
				break;
			case 7:
				share2friend();
				break;
			}
			M.sound7(R.drawable.click);
			Sel=0;
		}
		return true;
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawBlack(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawBlack(gl, x, y);
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
