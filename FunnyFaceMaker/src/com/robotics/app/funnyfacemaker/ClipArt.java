package com.robotics.app.funnyfacemaker;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.View;


public class ClipArt {
	GameRenderer mGR;
	public ClipArt(GameRenderer gr){
		mGR =gr;
	}
	private float move,movevx =0;
    private float moveR = 0;
    private boolean isMove = false;
    int Sel;
    int Couter =0;
    
    public void draw(GL10 gl) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Couter>60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.APPMAIN:
			Draw_GalleryMain(gl);
			break;
		case M.GameSUB:
			Draw_GallerySub(gl);
			break;
		case M.GAMETEXT:
			Draw_AddText(gl);
			break;
		}
		Couter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		
		switch (M.GameScreen) {
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.APPMAIN:
			Handle_GalleryMain(event);
			break;
		case M.GameSUB:
			Handle_GallerySub(event);
			break;
		case M.GAMETEXT:
			Handle_AddText(event);
			break;
		}
		return true;
	}
    
	private void Draw_GallerySub(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_back , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3), .60f-.35f*(i/3),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][mGR.page*12 +i],move-.36f+.36f*(i%3), .60f-.35f*(i/3),
					mGR.LibSel<1?.5f:1,Sel==(i+1)?0.5f:1);
		}
		
		if (move != 0 || movevx !=0) {
			int nPage = 0;
			move += movevx;
			if (move > 0 ){
				nPage = mGR.page-1;
			}
			if (move < 0 ){
				nPage = mGR.page+1;
			}
			for (int i = 0; i < 12 && nPage*12 +i < mGR.mTex_Library[mGR.LibSel].length && nPage>=0; i++) {
				DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3)
						,Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
				DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][nPage*12 +i],move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3),
						mGR.LibSel<1?.5f:1,Sel==(i+1)?0.5f:1);
			}
			if (movevx > 0 && move > 1) {
				movevx = move = 0;
				mGR.page--;
			}
			if (movevx < 0 && move < -1) {
				movevx = move = 0;
				mGR.page++;
			}
		}
		
		
		if (mGR.page > 0)
			DrawTransScal(gl, mGR.mTex_Arw[0],-.3f, -.72f,Sel==(101)?1.1f:1,Sel==(101)?0.5f:1);
		
		if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length)
			DrawTransScal(gl, mGR.mTex_Arw[1],0.3f, -.72f,Sel==(102)?1.1f:1,Sel==(102)?0.5f:1);
	}
	private boolean Handle_GallerySub(MotionEvent event){
		Sel =0;
		
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			if(CircRectsOverlap(-.36f+.36f*(i%3), .60f-.35f*(i/3), mGR.mTex_SubICN.width()*.3f, mGR.mTex_SubICN.Height()*.2f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = i+1;
			}
		}
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		
		if(CircRectsOverlap(-.3f,-.72f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =101;
		}
		
		if(CircRectsOverlap(0.3f,-.72f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =102;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()){
			moveR = s2wX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()){
			move =  s2wX(event.getX())-moveR;
			if(Math.abs(move) > .1)
				isMove = true;
			else
				isMove = false;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(isMove){
				isMove = false;
				if (mGR.page > 0 && move > 0)
					movevx = .1f;
				else if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length && move < 0)
					movevx = -.1f;
				else
					moveR = move = movevx = 0;
			}else if (Sel > 0) {
				moveR = move = movevx = 0;
				switch (Sel) {
				case 100:
					M.GameScreen = M.APPMAIN;
					break;
				case 101:
					if (mGR.page > 0)
						movevx = .1f;
					break;
				case 102:
					if ((mGR.page + 1) * 12 < mGR.mTex_Library[mGR.LibSel].length)
						movevx = -.1f;
				default:
					mGR.mMode = 1;
					mGR.LibSubSel = mGR.page * 12 + Sel - 1;
					if(mGR.LibSubSel < mGR.mTex_Library[mGR.LibSel].length){
						Gellary tempe = new Gellary(mGR.mTex_Library[mGR.LibSel][mGR.LibSubSel]);
						mGR.mGellary.add(tempe);
						M.GameScreen = M.GAMEPLAY;
//						mGR.clear();
					}
					break;
				}
			}
			Sel = 0;
		}
		return true;
	}
	private void Draw_GalleryMain(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_back , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		
		for (int i = 0; i < 20; i++) {
			DrawTransScal(gl, mGR.mTex_Main[0]	,-.400f+.271f*(i%4), .610f-.271f*(i/4),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			if(i == 1)
				DrawTransScal(gl, mGR.mTex_Library[i][0],-.400f+.271f*(i%4), .610f-.271f*(i/4),i<2?.9f:1.0f,Sel==(i+1)?0.5f:1);
			else
				DrawTransScal(gl, mGR.mTex_Library[i][0],-.400f+.271f*(i%4), .610f-.271f*(i/4),i<2?.4f:.7f,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Main[1]	,-.305f+.271f*(i%4), .515f-.271f*(i/4),1,1);
		}
		
	}
	private boolean Handle_GalleryMain(MotionEvent event){
		Sel =0;
		for(int i=0;i<20 ;i++){
			if(CircRectsOverlap(-.400f+.271f*(i%4), .610f-.271f*(i/4), mGR.mTex_Main[0].width()*.2f, 
					mGR.mTex_Main[0].Height()*.2f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =1+i;
			}
		}
		
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				M.GameScreen = M.GAMEPLAY;
				break;
			default:
				mGR.LibSel = Sel-1;
				M.GameScreen = M.GameSUB;
				mGR.page = 0;
				break;
			
			}
			Sel = 0;
		}
		return true;
	}
	private void Draw_AddText(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_cross , -.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_right , 0.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		if(!GameRenderer.mStart.isTxtVisible()){
			try {
				GameRenderer.mStart.Txthandler.sendEmptyMessage(View.VISIBLE);
			} catch (Exception e) {
			}
		}
		
	}
	private boolean Handle_AddText(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.48f, .56f, mGR.mTex_effect.width()*.3f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		if(CircRectsOverlap(0.48f, .56f, mGR.mTex_effect.width()*.3f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =101;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				GameRenderer.mStart.hideKey();
				M.GameScreen = M.GAMEPLAY;
				break;
			default:
				mGR.mFontCnVs.drawColor(0, Mode.CLEAR);
				mGR.mFontCnVs.drawText(GameRenderer.mStart.getAddTxt(), 256,32, mGR.mPaint);
				Gellary tempe = new Gellary(GameRenderer.addBitmap(mGR.mBitmap));
				mGR.mGellary.add(tempe);
				mGR.mMode = 1;
				M.GameScreen = M.GAMEPLAY;
				break;
			}
			try {
				GameRenderer.mStart.Txthandler.sendEmptyMessage(View.GONE);
			} catch (Exception e) {
			}
			mGR.root.forSave =0;
			Sel = 0;
		}
		return true;
	}
	
	private void Draw_Menu(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_Start, 0.40f, -.42f, Sel == 1 ? 1.1f : 1,Sel == 1 ? 0.5f : 1);
		for (int i = 0; i < mGR.mTex_Spl.length; i++)
			DrawTransScal(gl, mGR.mTex_Spl[i], -.46f + .23f * i, -.9f,Sel == 2 + i ? 1.1f : 1, Sel == 2 + i ? 0.5f : 1);

	}
	private boolean Handle_Menu(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(0.40f, -.42f, mGR.mTex_effect.width()*.4f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =1;
		}
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.46f + .23f*i, -.9f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =2+i;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				GameRenderer.mStart.openFolder();
				break;
			case 3:
				mGR.root.MoreGame();
				break;
			case 4:
				mGR.root.share2friend();
				break;
			case 5:
				mGR.root.facebook();
				break;
			case 6:
				mGR.root.RateUs();
				break;
			}
			Sel = 0;
		}
		return true;
	}
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}


	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	
	private float s2wX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}
	private float s2wY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}
}
