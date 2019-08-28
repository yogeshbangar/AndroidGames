package com.ultimate.frog.fun;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
public class Group extends Mesh 
{
	int _keyCode =0;
	GameRenderer mGR = null;
	int sel =0;
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (_keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEPAUSE;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60) {
				M.GameScreen = M.GAMESPLASH;
			}
			break;
		case M.GAMESPLASH:
			Draw_Start(gl);
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEWIN:
		case M.GAMEOVER:
			Draw_PauseOver(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMESURVIVAL:
			Draw_survival(gl);
			break;
		}
//		setting();
		Counter++;
		if(Counter%3==0)
			C2++;
	}
	public boolean TouchEvent(MotionEvent event) {

		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMESPLASH:
			Handle_Start(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEWIN:
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Handle_PauseOver(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMESURVIVAL:
			Handle_survival(event);
			break;
		}
		Handle(event);
		return true;
	}

	void gamelogic() {
		if(mGR.mPlayer.y > -.3){
			mGR.mPlayer.y+=M.SPD;
			mGR.mBG+=M.SPD;
			for (int i = 0; i < mGR.mLeaf.length; i++) {
				mGR.mLeaf[i].y+=M.SPD;
			}
			for (int i = 0; i < mGR.mLeaf.length; i++) {
				if(mGR.mLeaf[i].y<-1.4)
					mGR.mLeaf[i].Set(mGR.mLeaf[(i==0?mGR.mLeaf.length:i)-1].y+M.HGT);
			}
		}
		if(mGR.mLevel == 1 && mGR.mPlayer.End ==0){
			if(mGR.mTime+M.TIME < System.currentTimeMillis()){
				mGR.mPlayer.End = 19;
				M.sound5(R.drawable.timeup);
			}
		}
		if(mGR.mPlayer.End>0){
			mGR.mPlayer.End++;
			if(mGR.mPlayer.End>20){
				M.Stopplay();
				M.sound2(R.drawable.game_over);
				switch (mGR.mLevel) {
				case 1:
					if(mGR.mTime+M.TIME < System.currentTimeMillis() && !mGR.mPlayer.gone2){
						M.GameScreen = M.GAMEWIN;
						GameRenderer.mStart.Achivment();
						if(mGR.mScore > mGR.m1Best)
							mGR.m1Best = mGR.mScore;
					}else{
						M.GameScreen = M.GAMEOVER;
					}
					break;
				case 2:
					if(mGR.mScore > mGR.m1Best)
						mGR.m1Best = mGR.mScore;
					M.GameScreen = M.GAMEOVER;
					break;
				default:
					if(mGR.mLevel == 0 && mGR.mRow <=0){
						M.GameScreen = M.GAMEWIN;
						GameRenderer.mStart.Achivment();
						mGR.mTime = (int)(System.currentTimeMillis()-mGR.mTime);
						if(mGR.m0Best == 0 || mGR.m0Best > mGR.mTime)
							mGR.m0Best = mGR.mTime;
					}else{
						M.GameScreen = M.GAMEOVER;
					}
					break;
				}
				
				GameRenderer.mStart.ShowInterstitial();
				
			}
		}
		mGR.mPlayer.update();
		System.out.println(mGR.mRow+"   "+mGR.mBG);
	}

	void Draw_Gameplay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			for (int j = 0; j < mGR.mLeaf[i].no.length; j++) {
				if(mGR.mLeaf[i].no[j]<3){
					if(mGR.mLeaf[i].iAnim > 0 && mGR.mLeaf[i].no[j]==2 && mGR.mLeaf[i].jAnim == j){
						DrawTextureS(gl, mGR.mTex_Leaf[3],-.75f + j * .5f, mGR.mLeaf[i].y,1.1f);
						mGR.mLeaf[i].iAnim--;
					}else if(mGR.mLeaf[i].iAnim > 0 && mGR.mLeaf[i].no[j]==1 && mGR.mLeaf[i].jAnim == j){
						if(mGR.mLeaf[i].iAnim > 6)
							DrawTexture(gl, mGR.mTex_LeafJump[0],-.75f + j * .5f, mGR.mLeaf[i].y);
						else if(mGR.mLeaf[i].iAnim > 2 ){
							DrawTexture(gl, mGR.mTex_LeafJump[1],-.75f + j * .5f, mGR.mLeaf[i].y);
							mGR.mLeaf[i].iAnim--;
						}
						if(mGR.mLeaf[i].iAnim > 2 ){
							mGR.mLeaf[i].iAnim--;
						}
					}else{
						DrawTexture(gl, mGR.mTex_Leaf[mGR.mLeaf[i].no[j]],-.75f + j * .5f, mGR.mLeaf[i].y);
					}
				}
			}
		}
		
		if(mGR.mPlayer.rot == 45){
			if(mGR.mPlayer.z<.05)
				DrawTexture(gl, mGR.mTex_Woter, mGR.mPlayer.x, mGR.mPlayer.y);
			mGR.mTex_Ninja[1].drawRotet(gl, mGR.mPlayer.x, mGR.mPlayer.y,mGR.mPlayer.z,45);
			if(mGR.mPlayer.z<.1){
				mGR.mPlayer.z+=.03f;
				mGR.mPlayer.y+=.005f;
			}
		}
		if(!mGR.mPlayer.gone2){
			DrawTexture(gl, mGR.mTex_Ninja[mGR.mPlayer.img>0?1:0], mGR.mPlayer.x, mGR.mPlayer.y);
		}
		if(mGR.mLevel == 0 && mGR.mBG <2){
			DrawTexture(gl, mGR.mTex_Top, 0, mGR.mBG);
		}
		
		if (mGR.mPlayer.iStart) {
			String str = "";
			if (mGR.mLevel == 0) {
				int tii = (int) (System.currentTimeMillis() - mGR.mTime);
				str = tii / 1000 + ":";
				if (tii % 1000 < 10)
					str += "00" + tii % 1000;
				else if (tii % 1000 < 100)
					str += "0" + tii % 1000;
				else
					str += "" + tii % 1000;
				
			}
			if (mGR.mLevel == 1) {
				int tii = (int) ((mGR.mTime + 20000) - System.currentTimeMillis());
				str = tii / 1000 + ":";
				if (tii % 1000 < 10)
					str += "00" + tii % 1000;
				else if (tii % 1000 < 100)
					str += "0" + tii % 1000;
				else
					str += "" + tii % 1000;
			}
			drawNumber(gl, str, .55f, .70f, 1);
			gamelogic();
		}else{
			int j =0;
			for (int i = 0; i < mGR.mLeaf[1].no.length; i++) {
				if(mGR.mLeaf[1].no[i] == 0){
					j = i;
					break;
				}
			}
			if((-.75f + j * .5f) < 0){
				if(C2 % 2 == 0)
					DrawTexture(gl, mGR.mTex_Arrow[0], (-.75f + j * .5f)+.3f,mGR.mPlayer.y+M.HGT);
			}else{
				if(C2 % 2 == 0)
					DrawTexture(gl, mGR.mTex_Arrow[1], (-.75f + j * .5f)-.3f,mGR.mPlayer.y+M.HGT);
			}
				
		}
	}

	boolean Handle_Gameplay(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlayer.End == 0){
			int i = (mGR.mPlayer.Pos+1)%mGR.mLeaf.length;
			for (int j = 0; j < mGR.mLeaf[0].no.length; j++) {
				if(CircRectsOverlap(-.75f + j * .5f, mGR.mLeaf[i].y, mGR.mTex_Leaf[0].width()*.5f, M.HGT*.8f, screen2worldX(event.getX()),screen2worldY(event.getY()), .03f)){
					mGR.mPlayer.set(-.75f + j * .5f, mGR.mLeaf[i].y,i);
					M.sound3(R.drawable.jump);
					if(!mGR.mPlayer.iStart){
						mGR.mPlayer.iStart = true;
						mGR.mTime = System.currentTimeMillis();
					}
					if(mGR.mLeaf[i].no[j] !=0){
						mGR.mPlayer.End = 1;
						if( mGR.mLeaf[i].no[j] == 2){
							mGR.mLeaf[i].iAnim = 5;
							mGR.mPlayer.gone2 = true;
							mGR.mLeaf[i].jAnim = j;
							M.sound1(R.drawable.eat);
						}
						if( mGR.mLeaf[i].no[j] == 1){
							mGR.mLeaf[i].iAnim = 10;
							mGR.mPlayer.gone2 = true;
							mGR.mLeaf[i].jAnim = j;
							M.sound7(R.drawable.wrong_jump);
						}
						if( mGR.mLeaf[i].no[j] == 3){
							mGR.mPlayer.rot = 45;
							mGR.mPlayer.z = -.15f;
							mGR.mPlayer.gone2 = true;
							M.sound6(R.drawable.water);
						}
					}else{
						if(mGR.mLevel == 0){
							mGR.mRow -=1;
							if(mGR.mRow<=0){
								mGR.mPlayer.End = 1;
							}
						}
						mGR.mScore++;
					}
					
				}
//				DrawTexture(gl, mGR.mTex_Leaf[mGR.mLeaf[i].no[j]],.75f + j * .5f, mGR.mLeaf[i].y);
			}
		}
		return true;
	}
	void survival() {
		System.out.println(mGR.mRow+"   "+M.SPD);
		if(M.SPD>-.05)
			M.SPD -= .0001f;
		mGR.mPlayer.y += M.SPD;
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			mGR.mLeaf[i].y += M.SPD;
		}
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			if (mGR.mLeaf[i].y < -1.4)
				mGR.mLeaf[i]
						.Set(mGR.mLeaf[(i == 0 ? mGR.mLeaf.length : i) - 1].y
								+ M.HGT);
		}
		if (mGR.mPlayer.End > 0 || mGR.mPlayer.y < -.95) {
			mGR.mPlayer.End++;
			if (mGR.mPlayer.End > 30) {
				M.Stopplay();
				M.sound2(R.drawable.game_over);
				if (mGR.mScore > mGR.m2Best)
					mGR.m2Best = mGR.mScore;
				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.Achivment();
				GameRenderer.mStart.ShowInterstitial();
			}
		}
		mGR.mPlayer.update();
	}

	void Draw_survival(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for (int i = 0; i < mGR.mLeaf.length; i++) {
			for (int j = 0; j < mGR.mLeaf[i].no.length; j++) {
				if(mGR.mLeaf[i].no[j]<3){
					if(mGR.mLeaf[i].iAnim > 0 && mGR.mLeaf[i].no[j]==2 && mGR.mLeaf[i].jAnim == j){
						DrawTextureS(gl, mGR.mTex_Leaf[3],-.75f + j * .5f, mGR.mLeaf[i].y,1.1f);
						mGR.mLeaf[i].iAnim--;
					}else if(mGR.mLeaf[i].iAnim > 0 && mGR.mLeaf[i].no[j]==1 && mGR.mLeaf[i].jAnim == j){
						if(mGR.mLeaf[i].iAnim > 6)
							DrawTexture(gl, mGR.mTex_LeafJump[0],-.75f + j * .5f, mGR.mLeaf[i].y);
						else if(mGR.mLeaf[i].iAnim > 2 ){
							DrawTexture(gl, mGR.mTex_LeafJump[1],-.75f + j * .5f, mGR.mLeaf[i].y);
							mGR.mLeaf[i].iAnim--;
						}
						if(mGR.mLeaf[i].iAnim > 2 ){
							mGR.mLeaf[i].iAnim--;
						}
					}else{
						DrawTexture(gl, mGR.mTex_Leaf[mGR.mLeaf[i].no[j]],-.75f + j * .5f, mGR.mLeaf[i].y);
					}
				}
			}
		}
		if(!mGR.mPlayer.gone2){
			if(mGR.mPlayer.rot == 45){
				if(mGR.mPlayer.z<.05)
					DrawTexture(gl, mGR.mTex_Woter, mGR.mPlayer.x, mGR.mPlayer.y);
				mGR.mTex_Ninja[1].drawRotet(gl, mGR.mPlayer.x, mGR.mPlayer.y,mGR.mPlayer.z,45);
				if(mGR.mPlayer.z<.1){
					mGR.mPlayer.z+=.03f;
					mGR.mPlayer.y+=.005f;
				}
			}else{
				DrawTexture(gl, mGR.mTex_Ninja[mGR.mPlayer.img>0?1:0], mGR.mPlayer.x, mGR.mPlayer.y);
			}
		}
		if(mGR.mLevel == 0 && mGR.mBG <2){
			DrawTexture(gl, mGR.mTex_Top, 0, mGR.mBG);
		}

		if (mGR.mPlayer.iStart) {

			int tii = (int) (System.currentTimeMillis() - mGR.mTime);
			String str = tii / 1000 + ":";
			if (tii % 1000 < 10)
				str += "00" + tii % 1000;
			else if (tii % 1000 < 100)
				str += "0" + tii % 1000;
			else
				str += "" + tii % 1000;
			drawNumber(gl, str, .55f, .92f, 1);

			survival();
		}else{
			int j =0;
			for (int i = 0; i < mGR.mLeaf[1].no.length; i++) {
				if(mGR.mLeaf[1].no[i] == 0){
					j = i;
					break;
				}
			}
			if((-.75f + j * .5f) < 0){
				if(C2 % 2 == 0)
					DrawTexture(gl, mGR.mTex_Arrow[0], (-.75f + j * .5f)+.3f,mGR.mPlayer.y+M.HGT);
			}else{
				if(C2 % 2 == 0)
					DrawTexture(gl, mGR.mTex_Arrow[1], (-.75f + j * .5f)-.3f,mGR.mPlayer.y+M.HGT);
			}
				
		}
	}

	boolean Handle_survival(MotionEvent event){
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlayer.End == 0){
			int i = (mGR.mPlayer.Pos+1)%mGR.mLeaf.length;
			for (int j = 0; j < mGR.mLeaf[0].no.length; j++) {
				if(CircRectsOverlap(-.75f + j * .5f, mGR.mLeaf[i].y, mGR.mTex_Leaf[0].width()*.5f, M.HGT*.8f, screen2worldX(event.getX()),screen2worldY(event.getY()), .03f)){
					mGR.mPlayer.set(-.75f + j * .5f, mGR.mLeaf[i].y,i);
					M.sound3(R.drawable.jump);
					if(!mGR.mPlayer.iStart){
						mGR.mPlayer.iStart = true;
						mGR.mTime = System.currentTimeMillis();
					}
					if(mGR.mLeaf[i].no[j] !=0){
						mGR.mPlayer.End = 1;
						if( mGR.mLeaf[i].no[j] == 2){
							mGR.mLeaf[i].iAnim = 5;
							mGR.mPlayer.gone2 = true;
							mGR.mLeaf[i].jAnim = j;
							M.sound1(R.drawable.eat);
						}
						if( mGR.mLeaf[i].no[j] == 1){
							mGR.mLeaf[i].iAnim = 10;
							mGR.mPlayer.gone2 = true;
							mGR.mLeaf[i].jAnim = j;
							M.sound7(R.drawable.wrong_jump);
						}
						if( mGR.mLeaf[i].no[j] == 3){
							mGR.mPlayer.rot = 45;
							mGR.mPlayer.z = -.15f;
							M.sound6(R.drawable.water);
						}
					}else{
						mGR.mScore++;
					}
					
				}
			}
		}
		return true;
	}
	

	
	float asz = 1;
	float avz = .01f;
	int C2 = 0;
	void Draw_Start(GL10 gl) {
		
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTextureS(gl, mGR.mTex_SText, 0, .42f,asz);
		DrawTransScal(gl, mGR.mTex_Play, 0, -.02f, sel == 1 ? 1.1f : 1, sel == 1 ? .5f : 1);
		if(C2%10 <5)
			DrawTexture(gl, mGR.mTex_Frog[C2%5], .6f, -.73f);
		else
			DrawTexture(gl, mGR.mTex_Frog[0], .6f, -.73f);
		if (asz > 1.04)
			avz = -.01f;
		if (asz < 0.96)
			avz = 0.01f;
		asz += avz;
		
		DrawTransScal(gl, mGR.mTex_Leader, -.80f, -.9f, sel == 2 ? 1.1f : 1, sel == 2 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_Achiv, -.45f, -.9f, sel == 3 ? 1.1f : 1, sel == 3 ? .5f : 1);
	}
	boolean Handle_Start(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, -.02, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(-.80f, -.91, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(-.45, -.91, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			}
			M.sound4(R.drawable.right_jump );
			sel =0;
		}
		return true;
	}
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTransScal(gl, mGR.mTex_Classic	, 0, .5f,sel==1?1.1f:1,sel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Time		, 0, .2f,sel==2?1.1f:1,sel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Survival	, 0,-.1f,sel==3?1.1f:1,sel==3?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0],0.5f, -.5f,sel==4?1.1f:1,sel==4?.5f:4);
		DrawTransScal(gl, mGR.mTex_Rate, 0, -.5f,sel==5?1.1f:1,sel==5?.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader, -.5f,-.5f,sel==6?1.1f:1,sel==6?.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, 0.5, mGR.mTex_Classic.width()*.5, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(0, 0.2, mGR.mTex_Classic.width()*.5, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(0, -.1, mGR.mTex_Classic.width()*.5, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if(CircRectsOverlap(0.5, -.5, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 4;
		}
		if(CircRectsOverlap(0.0, -.5, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 5;
		}
		if(CircRectsOverlap(-.5, -.5, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 6;
		}
		
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				mGR.gameReset(0);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				mGR.gameReset(1);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 3:
				mGR.gameReset(2);
				M.GameScreen = M.GAMESURVIVAL;
				M.play(R.drawable.bg);
				break;
			case 4:
				M.setValue = !M.setValue;
				break;
			case 5:
				RateUs();
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
				
			}
			sel =0;M.sound4(R.drawable.right_jump );
		}
		return true;
	}
	
	void Draw_PauseOver(GL10 gl){
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_Gover,0, 0.5f);
		else if(M.GameScreen == M.GAMEWIN)
			DrawTexture(gl, mGR.mTex_Win,0, 0.5f);
		else
			DrawTexture(gl, mGR.mTex_GPaused,0, 0.5f);
		
		
		if (M.GameScreen == M.GAMEPAUSE) {
			DrawTransScal(gl, mGR.mTex_Play, 0.0f,0.1f,sel==4?1.1f:1,sel==4?.5f:1);
		} else {
			switch (mGR.mLevel) {
			case 1:
				DrawTexture(gl, mGR.mTex_Jump, 0, .31f);
				if (M.GameScreen == M.GAMEWIN) {
					drawNumber(gl, mGR.mScore + "", .06f, 0.19f, 1);
				}
				if (mGR.m1Best > 0) {
					drawNumber(gl, mGR.m1Best + "", .06f, -.11f, 1);
				}
				break;
			case 2:
				DrawTexture(gl, mGR.mTex_Jump, 0, .31f);
				if (M.GameScreen == M.GAMEWIN) {
					drawNumber(gl, mGR.mScore + "", .06f, 0.19f, 1);
				}
				if (mGR.m2Best > 0) {
					drawNumber(gl, mGR.m2Best + "", .06f, -.11f, 1);
				}
				break;
			default:
				DrawTexture(gl, mGR.mTex_TrvTime, 0, .31f);
				String str = mGR.mTime / 1000 + ":";
				if (M.GameScreen == M.GAMEWIN) {
					if (mGR.mTime % 1000 < 10)
						str += "00" + mGR.mTime % 1000;
					else if (mGR.mTime % 1000 < 100)
						str += "0" + mGR.mTime % 1000;
					else
						str += "" + mGR.mTime % 1000;

					drawNumber(gl, str, .06f, .19f, 1);
				} else {
					drawNumber(gl, ":", .06f, .19f, 1);
				}
				if (mGR.mTime > 0) {
					str = mGR.m0Best / 1000 + ":";
					if (mGR.m0Best % 1000 < 10)
						str += "00" + mGR.m0Best % 1000;
					else if (mGR.m0Best % 1000 < 100)
						str += "0" + mGR.m0Best % 1000;
					else
						str += "" + mGR.m0Best % 1000;

					drawNumber(gl, str, .06f, -.11f, 1);
				} else {
					drawNumber(gl, ":", .06f, -.11f, 1);
				}
				break;
			}
			DrawTexture(gl, mGR.mTex_Best, 0, 0);
			
		}
		
		
		DrawTransScal(gl, mGR.mTex_Retry, 0.0f,-.30f,sel==1?1.1f:1,sel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu ,-.40f,-.57f,sel==2?1.1f:1,sel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Score, .40f,-.57f,sel==3?1.1f:1,sel==3?.5f:1);
	}
	boolean Handle_PauseOver(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0.0f,-.30f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(-.40f,-.57f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(0.40f,-.57f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if(CircRectsOverlap(0.0f,0.10f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 4;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				mGR.gameReset(mGR.mLevel);
				if(mGR.mLevel == 2)
					M.GameScreen = M.GAMESURVIVAL;
				else
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
				if(M.GameScreen == M.GAMEPAUSE){
					mGR.mTime = System.currentTimeMillis()-mGR.mTime;
					if(mGR.mLevel == 2)
						M.GameScreen = M.GAMESURVIVAL;
					else
						M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.bg);
				}
				break;
			}
			M.sound4(R.drawable.right_jump );sel =0;
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
	void drawNumber(GL10 gl,String strs,float x, float y,int aling)
	{
		float dx = mGR.mTex_Font[0].width()*.6f;
		if(aling == 1)
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

}
