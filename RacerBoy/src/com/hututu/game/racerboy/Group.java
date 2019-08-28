package com.hututu.game.racerboy;
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
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(mGR.Levelno+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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
//		M.GameScreen = M.GAMELVL;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>100){
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMESET:
			Draw_Setting(gl);
			break;
		case M.GAMEPLAY:
			Draw_Play(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Draw_GameOver(gl);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Draw_About(gl);
			break;
		case M.GAMELVL:
			Draw_Level(gl);
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
		case M.GAMESET:
			Handle_Setting(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Handle_GameOver(event);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Handle_About(event);
			break;
		case M.GAMELVL:
			Handle_Level(event);
			break;
		}
		Handle(event);
		return true;
	}
	

	/************************Level Start*****************************/
	void Draw_Level(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		DrawTexture(gl, mGR.mTex_level , 0, .73f);
		for(int i=0;i<13;i++){
			DrawTransScal(gl, mGR.mTex_Squr, -.75f+.25f*(i%7), 0.3f-(i/7)*.6f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
			if(i<9)
				drawNumber(gl, ""+(i+1), -.75f+.25f*(i%7), 0.3f-(i/7)*.6f, 0);
			else
				drawNumber(gl, ""+(i+1), -.78f+.25f*(i%7), 0.3f-(i/7)*.6f, 0);
		}
		
		DrawTransScal(gl, mGR.mTex_Squr, -.84f, 0.69f,mGR.mSel == 20?1.1f:1,mGR.mSel == 20?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back, -.84f, 0.69f,mGR.mSel == 20?1.1f:1,mGR.mSel == 20?0.5f:1);
		
	}
	boolean Handle_Level(MotionEvent event){
		mGR.mSel = 0;
		for(int i =0;i<13;i++){
			if(CircRectsOverlap(-.75f+.25f*(i%7), 0.3f-(i/7)*.6f, mGR.mTex_Squr.width()*.4, mGR.mTex_Squr.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
				mGR.mSel = i+1;
			}}
		if(CircRectsOverlap(-.84f, 0.69f, mGR.mTex_Back.width()*.4, mGR.mTex_Back.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 20;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel >0){
			switch (mGR.mSel) {
			default:
				mGR.gameReset(mGR.mSel,true);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.main_gameplay);
				break;
			case 20:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Level End*****************************/
	/************************GameOver Start*****************************/
	void Draw_GameOver(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		if(M.GameScreen == M.GAMEWIN)
			DrawTexture(gl, mGR.mTex_levelwin , 0, .73f);
		else
			DrawTexture(gl, mGR.mTex_gameover , 0, .73f);
		
		DrawTexture(gl, mGR.mTex_score 	, -.4f, .50f);
		DrawTexture(gl, mGR.mTex_Bar 	, -.4f, .33f);
		drawNumber(gl,(int)mGR.mScore+"", -.4f, .33f, 1);
		
		DrawTexture(gl, mGR.mTex_HS		, 0.4f, .50f);
		DrawTexture(gl, mGR.mTex_Bar 	, 0.4f, .33f);
		drawNumber(gl,(int)mGR.Bestdistance+"", 0.4f, .33f, 1);
		
		DrawTransScal(gl, mGR.mTex_Button, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		if (mGR.FromLevel) {
			if (M.GameScreen == M.GAMEWIN && mGR.Levelno < 13) {
				DrawTransScal(gl, mGR.mTex_NLevel, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
			} else {
				DrawTransScal(gl, mGR.mTex_Jump, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
			}
		} else {
			DrawTransScal(gl, mGR.mTex_Jump, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_Button,  .39f, 0,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu,  .39f, 0,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		
		for(int i=0;i<5;i++){
			DrawTransScal(gl, mGR.mTex_Squr, -.56f+.28f*i, -.42f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Icon[i], -.56f+.28f*i, -.42f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
		}
		
	}
	boolean Handle_GameOver(MotionEvent event){
		mGR.mSel = 0;
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.56f+.28f*i, -.42f, mGR.mTex_Squr.width()*.4, mGR.mTex_Squr.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
				mGR.mSel = i+1;
			}}
		if(CircRectsOverlap(-.39f, 0, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(0.39f, 0, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 7;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 3:
				facebook();
				break;
			case 4:
				MoreGame();
				break;
			case 5:
				RateUs();
				break;
			case 6:
				if (mGR.FromLevel) {
					if (M.GameScreen == M.GAMEWIN && mGR.Levelno < 13) {
						mGR.gameReset(mGR.Levelno+1, mGR.FromLevel);
						M.GameScreen = M.GAMEPLAY;
						M.play(R.drawable.main_gameplay);
					} else {
						mGR.gameReset(mGR.Levelno, mGR.FromLevel);
						M.GameScreen = M.GAMEPLAY;
						M.play(R.drawable.main_gameplay);
					}
				} else {
					mGR.gameReset(1, mGR.FromLevel);
					M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.main_gameplay);
				}
				break;
			case 7:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************GameOver End*****************************/
	
	
	/************************Pause Start*****************************/
	void Draw_Pause(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		DrawTexture(gl, mGR.mTex_paused , 0, .73f);
		
		DrawTexture(gl, mGR.mTex_score , 0, .55f);
		DrawTexture(gl, mGR.mTex_Bar , 0, .38f);
		drawNumber(gl, (int)mGR.mScore+"", 0, .38f, 1);
		DrawTransScal(gl, mGR.mTex_Button, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Conti, -.39f, 0,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Button,  .39f, 0,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu,  .39f, 0,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		
		for(int i=0;i<5;i++){
			DrawTransScal(gl, mGR.mTex_Squr, -.56f+.28f*i, -.42f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Icon[i], -.56f+.28f*i, -.42f,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
		}
		
	}
	boolean Handle_Pause(MotionEvent event){
		mGR.mSel = 0;
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.56f+.28f*i, -.42f, mGR.mTex_Squr.width()*.4, mGR.mTex_Squr.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
				mGR.mSel = i+1;
			}}
		if(CircRectsOverlap(-.39f, 0, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(0.39f, 0, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 7;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 3:
				facebook();
				break;
			case 4:
				MoreGame();
				break;
			case 5:
				RateUs();
				break;
			case 6:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.main_gameplay);
				break;
			case 7:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Pause End*****************************/
	/************************About Start*****************************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		if(M.GameScreen == M.GAMEABOUT){
			DrawTexture(gl, mGR.mTex_About , 0, .73f);
			DrawTexture(gl, mGR.mTex_AboutTxt , 0, 0);
		}else{
			DrawTexture(gl, mGR.mTex_Help , 0, .73f);
			DrawTexture(gl, mGR.mTex_HelpSrc , 0, 0);
		}
		DrawTransScal(gl, mGR.mTex_Squr, -.84f, 0.69f,mGR.mSel == 10?1.1f:1,mGR.mSel == 10?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back, -.84f, 0.69f,mGR.mSel == 10?1.1f:1,mGR.mSel == 10?0.5f:1);
	}
	boolean Handle_About(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.84f, 0.69f, mGR.mTex_Back.width()*.4, mGR.mTex_Back.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 10;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 10:
				M.GameScreen = M.GAMESET;
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************About End*****************************/
	
	/************************Setting Start*****************************/
	void Draw_Setting(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		DrawTexture(gl, mGR.mTex_OptionB , 0, .73f);
//		for(int i =0;i<4;i++){
//			DrawTransScal(gl, mGR.mTex_Button, 0, .45f-.35f*i,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
//		}
//		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1], 0.02f, .45f-.35f*0,mGR.mSel == 0+1?1.1f:1,mGR.mSel == 0+1?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?0:1], 0, .45f-.35f*1,mGR.mSel == 1+1?1.1f:1,mGR.mSel == 1+1?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_HelpB, -.01f, .45f-.35f*2,mGR.mSel == 2+1?1.1f:1,mGR.mSel == 2+1?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_AboutB,0.015f, .45f-.35f*3,mGR.mSel == 3+1?1.1f:1,mGR.mSel == 3+1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Button, -.39f, 0.26f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1], -.39f, 0.26f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Button,  .39f, 0.26f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?0:1],  .39f, 0.26f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Button, -.39f, -.26f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_HelpB, -.39f, -.26f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Button,  .39f, -.26f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_AboutB,  .39f, -.26f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		
		
		DrawTransScal(gl, mGR.mTex_Squr, -.84f, 0.69f,mGR.mSel == 10?1.1f:1,mGR.mSel == 10?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back, -.84f, 0.69f,mGR.mSel == 10?1.1f:1,mGR.mSel == 10?0.5f:1);
	}
	boolean Handle_Setting(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.39f, 0.26f, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.39f, 0.26f, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.39f, -.26f, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.39f, -.26f, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.84f, 0.69f, mGR.mTex_Back.width()*.4, mGR.mTex_Back.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = 10;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				M.setMusic = !M.setMusic;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 10:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(R.drawable.button_click);
			
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Setting End*****************************/
	
	/************************Menu Start*****************************/
	void Draw_Menu(GL10 gl){
//		mGR.addFree = true;
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		for(int i =0;i<2;i++){
			DrawTransScal(gl, mGR.mTex_Button, -.70f,.1f-.31f*i,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_SBtn[i],-.70f,.1f-.31f*i,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Button, -.70f,.1f-.31f*2,mGR.mSel == 2+1?1.1f:1,mGR.mSel == 2+1?0.5f:1);
		if(!mGR.addFree){
			DrawTransScal(gl, mGR.mTex_buy2,-.70f,.1f-.31f*2,mGR.mSel == 2+1?1.1f:1,mGR.mSel == 2+1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_buy3,-.36f,.1f-.31f*2,mGR.mSel == 2+1?1.1f:1,mGR.mSel == 2+1?0.5f:1);
		}
		else
			DrawTransScal(gl, mGR.mTex_SBtn[2],-.70f,.1f-.31f*2,mGR.mSel == 2+1?1.1f:1,mGR.mSel == 2+1?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		for(int i =0;i<3;i++)
		if(CircRectsOverlap( -.70f,.1f-.31f*i, mGR.mTex_Button.width()*.4, mGR.mTex_Button.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03f)){
			mGR.mSel = i+1;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.stop();
				mGR.gameReset(1,false);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.main_gameplay);
				break;
			case 2:
				M.stop();
				M.GameScreen = M.GAMESET;
				break;
			case 3:
				M.stop();
//				M.GameScreen = M.GAMELVL;
				if(!mGR.addFree)
					mGR.mInApp.onBuyGold10000(null);
				else
					M.GameScreen = M.GAMELVL;
				//Ads Free
				break;
			}
			if(mGR.mSel != 0){
				M.sound1(R.drawable.button_click);
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Menu End*****************************/
	final int back[]={141,142,143,144,145,146,147,148,157,158,159,160,161,162,163,164,176,177,180,181};
	final int Frant[]={93,94,95,96,133,134,135,136,137,138,139,140,149,150,151,152,153,154,155,156,178,179,182,183,166,168,169,170,171,172,173,174,175};
	/************************Game Play Start*****************************/
	byte isIn(final int val){
		if ((val >= 57 && val <= 80)){
			return 1;//back 
		}
		for(int m =0;m<back.length;m++){
			if(back[m] ==val)
				return 1;//Back
		}
		for(int m =0;m<Frant.length;m++){
			if(Frant[m] ==val)
				return 2;//Front
		}
		return 0;
	}
	void gameLogic(){
		for (int i = 0; i < mGR.bgfx.length; i++) {
			mGR.bgbx[i] += mGR.mGuy.spd*.2;
			mGR.bgfx[i] += mGR.mGuy.spd*.4;
		}
		for (int i = 0; i < mGR.bgfx.length; i++) {
			if (mGR.bgbx[i] < -1.6)
				mGR.bgbx[i] = mGR.bgbx[(i==0?mGR.bgbx.length:i)-1]+mGR.mTex_BGForg[0][0].width();
			if (mGR.bgfx[i] < -1.6)
				mGR.bgfx[i] = mGR.bgfx[(i==0?mGR.bgfx.length:i)-1]+mGR.mTex_BGForg[0][0].width();
		}
		mGR.mScore-=mGR.mGuy.spd*10;
		mGR.TDistance-=mGR.mGuy.spd*10;
		if(mGR.Bestdistance<mGR.mScore)
			mGR.Bestdistance=mGR.mScore;
		mGR.mGuy.update();
		mGR.mOppt.update();
		boolean coli = false;
		for (int i = 0; i < mGR.mTile.length; i++) {
			mGR.mTile[i].x += mGR.mGuy.spd;
			if (mGR.mTile[i].x - .2 < mGR.mGuy.x&& mGR.mTile[i].x + .2 > mGR.mGuy.x){
				if (mGR.mTile[i].x - .1 < mGR.mGuy.x&& mGR.mTile[i].x + .1 > mGR.mGuy.x)
					mGR.mTile[i].down = mGR.mGuy.down;
				for (int j = 0; j < mGR.mTile[i].arry.length; j++) {
					if (mGR.mTile[i].arry[j] != 0) {
						if (isIn(mGR.mTile[i].arry[j]) == 0)
						{
							if (CircRectsOverlap(mGR.mTile[i].x, .93f - j
									* mGR.mTex_Tile[i].Height(),
									mGR.mTex_Tile[i].width() * .5,
									mGR.mTex_Tile[i].Height() * .1, mGR.mGuy.x,
									mGR.mGuy.y,
									mGR.mTex_Guy[0][0].Height() * .4f)) {
								mGR.mGuy.x += mGR.mGuy.spd;
//								System.out.println(""+mGR.mTile[i].arry[j]);
							}

							if (CircRectsOverlap(mGR.mTile[i].x, .93f - j
									* mGR.mTex_Tile[i].Height(),
									mGR.mTex_Tile[i].width() * .4,
									mGR.mTex_Tile[i].Height() * .4, mGR.mGuy.x,
									mGR.mGuy.y,
									mGR.mTex_Guy[0][0].Height() * .4f)) {
								if (mGR.mGuy.down == 0) {
									if (mGR.mGuy.y > .93f - j* mGR.mTex_Tile[i].Height()) {
										mGR.mGuy.y = .93f- j* mGR.mTex_Tile[i].Height()+ (mGR.mTex_Guy[0][0].Height() - mGR.mTex_Tile[i].Height() / 2);
										coli = true;
									}
								} else {
									if (mGR.mGuy.y < .93f - j* mGR.mTex_Tile[i].Height()) {
										mGR.mGuy.y = .93f- j* mGR.mTex_Tile[i].Height()
											- (mGR.mTex_Guy[0][0].Height() - mGR.mTex_Tile[i].Height() / 2);
										coli = true;
									}
								}
							}
						}
					}
				}
			}
			/********************opponent Start*********************************/
			if (mGR.mTile[i].x - .2 < mGR.mOppt.x&& mGR.mTile[i].x + .2 > mGR.mOppt.x){
				mGR.mOppt.down = mGR.mTile[i].down;
				for (int j = 0; j < mGR.mTile[i].arry.length; j++) {
					if (mGR.mTile[i].arry[j] != 0) {
						if (isIn(mGR.mTile[i].arry[j]) == 0) {
							if (CircRectsOverlap(mGR.mTile[i].x, .93f - j
									* mGR.mTex_Tile[i].Height(),
									mGR.mTex_Tile[i].width() * .4,
									mGR.mTex_Tile[i].Height() * .1,
									mGR.mOppt.x, mGR.mOppt.y,
									mGR.mTex_Guy[0][0].Height() * .4f)) {
								// mGR.mOppt.x+=mGR.mOppt.spd;
							}
							if (CircRectsOverlap(mGR.mTile[i].x, .93f - j
									* mGR.mTex_Tile[i].Height(),
									mGR.mTex_Tile[i].width() * .4,
									mGR.mTex_Tile[i].Height() * .4,
									mGR.mOppt.x, mGR.mOppt.y,
									mGR.mTex_Guy[0][0].Height() * .4f)) {
								if (mGR.mOppt.down == 0) {
									if (mGR.mOppt.y > .93f - j
											* mGR.mTex_Tile[i].Height()) {
										mGR.mOppt.y = .93f
												- j
												* mGR.mTex_Tile[i].Height()
												+ (mGR.mTex_Guy[0][0].Height() - mGR.mTex_Tile[i]
														.Height() / 2);
									}
								} else {
									if (mGR.mOppt.y < .93f - j
											* mGR.mTex_Tile[i].Height()) {
										mGR.mOppt.y = .93f
												- j
												* mGR.mTex_Tile[i].Height()
												- (mGR.mTex_Guy[0][0].Height() - mGR.mTex_Tile[i]
														.Height() / 2);
									}
								}
							}
						}
					}
				}
			}
			/********************opponent End*********************************/
		}
		mGR.colli++;
		if(coli){
			if(mGR.colli>3){
				mGR.mAnim.set(mGR.mGuy.x-0.04f, mGR.mGuy.y+(mGR.mGuy.down == 0?-.13f:.13f), 0);
				M.sound7(R.drawable.land);
			}
			mGR.colli = 0;
			
		}
		for (int i = 0; i < mGR.mTile.length; i++) {
			if(mGR.mTile[i].x < -1.05f){
				mGR.mTile[i].x = mGR.mTile[(i == 0 ? mGR.mTile.length : i) - 1].x+ mGR.mTex_Tile[i].width();
				for (int j = 0; j < mGR.mL01.l1.length; j++) {
					if (mGR.mGuy.colNo >= mGR.mL01.l1[0].length) {
						
						mGR.mTile[i].arry[j] = (short)(9+((i%2)+j)%2);
					} else {
						switch (mGR.Levelno) {
						case 13:
							mGR.mTile[i].arry[j] = mGR.mL13.l1[j][mGR.mGuy.colNo];
							break;
						case 12:
							mGR.mTile[i].arry[j] = mGR.mL12.l1[j][mGR.mGuy.colNo];
							break;
						case 11:
							mGR.mTile[i].arry[j] = mGR.mL11.l1[j][mGR.mGuy.colNo];
							break;
						case 10:
							mGR.mTile[i].arry[j] = mGR.mL10.l1[j][mGR.mGuy.colNo];
							break;
						case 9:
							mGR.mTile[i].arry[j] = mGR.mL09.l1[j][mGR.mGuy.colNo];
							break;
						case 8:
							mGR.mTile[i].arry[j] = mGR.mL08.l1[j][mGR.mGuy.colNo];
							break;
						case 7:
							mGR.mTile[i].arry[j] = mGR.mL07.l1[j][mGR.mGuy.colNo];
							break;
						case 6:
							mGR.mTile[i].arry[j] = mGR.mL06.l1[j][mGR.mGuy.colNo];
							break;
						case 5:
							mGR.mTile[i].arry[j] = mGR.mL05.l1[j][mGR.mGuy.colNo];
							break;
						case 4:
							mGR.mTile[i].arry[j] = mGR.mL04.l1[j][mGR.mGuy.colNo];
							break;
						case 3:
							mGR.mTile[i].arry[j] = mGR.mL03.l1[j][mGR.mGuy.colNo];
							break;
						case 2:
							mGR.mTile[i].arry[j] = mGR.mL02.l1[j][mGR.mGuy.colNo];
							break;
						case 1:
						default:
							mGR.mTile[i].arry[j] = mGR.mL01.l1[j][mGR.mGuy.colNo];
							break;
						}
					}
				}
				mGR.mGuy.colNo++;
				if (mGR.mGuy.colNo >= mGR.mL01.l1[0].length) {
					if (mGR.FromLevel) {
//						M.GameScreen = M.GAMEWIN;
					} else {
						mGR.mGuy.colNo %= mGR.mL01.l1[0].length;
						mGR.Levelno++;
						if (mGR.Levelno == 13)
							mGR.Levelno = 5;
					}
				}
			}
		}
		
		
		
		if (mGR.mGuy.x < -.95f || mGR.mGuy.y < -1.2f || mGR.mGuy.y > 1.2f) {
			if (mGR.mGuy.x < -.95f && mGR.OverCunt <20) {
				if(mGR.OverCunt ==0)
					M.sound9(R.drawable.cha_hit);
				mGR.OverCunt++;
				mGR.mGuy.spd =0;
			} else {
				M.stop();
				GameRenderer.mStart.Achivement();
				if (mGR.mGuy.colNo+20 > mGR.mL01.l1[0].length)
					M.GameScreen = M.GAMEWIN;
				else
					M.GameScreen = M.GAMEOVER;
//				M.sound2(R.drawable.game_over);
				GameRenderer.mStart.ShowInterstitial();
			}
		}
	}
	
	
	
	void Draw_Play(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG[mGR.mBG], 0,0);
		switch (mGR.mBG) {
		case 0:
			for(int i=0;i<mGR.bgfx.length;i++)
				DrawTexture(gl, mGR.mTex_BGForg[mGR.mBG][1], mGR.bgbx[i],-.50f);
			for(int i=0;i<mGR.bgbx.length;i++)
				DrawTexture(gl, mGR.mTex_BGForg[mGR.mBG][0], mGR.bgfx[i],-.50f);
			break;
		case 1:
			for(int i=0;i<mGR.bgfx.length;i++)
				mGR.mTex_BGForg[1][1].drawColor(gl, mGR.bgbx[i],-.44f,0.40f,.78f,.90f);
			for(int i=0;i<mGR.bgbx.length;i++)
				mGR.mTex_BGForg[1][0].drawColor(gl, mGR.bgfx[i],-.50f,0.00f,.50f,.67f);
			break;
		case 2:
			for(int i=0;i<mGR.bgfx.length;i++)
				mGR.mTex_BGForg[1][1].drawColor(gl, mGR.bgbx[i],-.44f,0.68f,.55f,.38f);
			for(int i=0;i<mGR.bgbx.length;i++)
				mGR.mTex_BGForg[1][0].drawColor(gl, mGR.bgfx[i],-.50f,0.38f,.20f,.03f);
			break;
		}
		for(int i =0;i<mGR.mTile.length;i++){
			for(int j =0;j<mGR.mTile[i].arry.length;j++){
				if(mGR.mTile[i].arry[j] >0){
					if (isIn(mGR.mTile[i].arry[j]) != 2)
						DrawTexture(gl, mGR.mTex_Tile[mGR.mTile[i].arry[j]-1], mGR.mTile[i].x,.93f - j * mGR.mTex_Tile[i].Height());
				}
			}
		}
		if(mGR.OverCunt==0 /*&& mGR.goAnim > 80*/)
		{
			DrawTexture(gl, mGR.mTex_Rival[mGR.mOppt.down%2][Counter%mGR.mTex_Rival[0].length], mGR.mOppt.x, mGR.mOppt.y);
			if(mGR.goAnim < 60){ mGR.goAnim++;
				DrawTexture(gl, mGR.mTex_RSGuy[mGR.mGuy.down%2][Counter%mGR.mTex_RSGuy[0].length], mGR.mGuy.x, mGR.mGuy.y);
			}else
				DrawTexture(gl, mGR.mTex_Guy[mGR.mGuy.down%2][Counter%mGR.mTex_Guy[0].length], mGR.mGuy.x, mGR.mGuy.y);
			
		}else{
			DrawTexture(gl, mGR.mTex_Rival[mGR.mOppt.down%2][0], mGR.mOppt.x, mGR.mOppt.y);
			DrawTransScal(gl, mGR.mTex_GO[mGR.mGuy.down%2][(mGR.OverCunt/5)%4], mGR.mGuy.x, mGR.mGuy.y,1,1);
		}
		for(int i =0;i<mGR.mTile.length;i++){
			for(int j =0;j<mGR.mTile[i].arry.length;j++){
				if(mGR.mTile[i].arry[j] >0){
					if (isIn(mGR.mTile[i].arry[j]) == 2)
						DrawTexture(gl, mGR.mTex_Tile[mGR.mTile[i].arry[j]-1], mGR.mTile[i].x,.93f - j * mGR.mTex_Tile[i].Height());
				}
			}
		}
		if(mGR.mAnim.cont<mGR.mTex_Anim.length){
			DrawTexture(gl, mGR.mTex_Anim[mGR.mAnim.cont], mGR.mAnim.x,mGR.mAnim.y);
			mGR.mAnim.cont++;
		}
		
//		if(mGR.goAnim < 80){mGR.goAnim++;
//			DrawTextureS(gl, mGR.mTex_GO[(mGR.goAnim/20)%4], 0, 0,(mGR.goAnim%20)*.04f);
//		}
		gameLogic();
	}
	
	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			if(!mGR.go)
				mGR.go = true;
			if(mGR.colli < 5)
				mGR.mGuy.change();
		}
		return false;
	}
	
	/************************Game play End*****************************/
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
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
	//$  : 
		//=  ;
		//.  <
	void drawNumber(GL10 gl, String strs, float x, float y,int aling) {
			float dx = mGR.mTex_Font[0].width()*.6f;
			if(aling ==1)
				x-=(dx*strs.length()*.5f+dx*.5);
			for (int i = 0; i < strs.length(); i++) {
				int k = ((int) strs.charAt(i)) - 48;
				if (k >= 0 && k < mGR.mTex_Font.length)
					mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
			}
		}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook()
	{
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
