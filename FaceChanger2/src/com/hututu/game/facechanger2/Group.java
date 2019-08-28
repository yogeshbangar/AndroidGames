package com.hututu.game.facechanger2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.1f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.AppScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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
	float ssx=1,svx=.003f;
	public void draw(GL10 gl) {
//		 M.AppScreen = M.APPEDIT;
		switch (M.AppScreen) {
		case M.APPHTT:
			DrawTextureS(gl, mGR.mTex_htt, 0, 0,ssx);
			ssx+=svx;
			if(ssx>1.1)
				svx=-.003f;
			if(ssx<0.9)
				svx=0.003f;
			if (Counter > 160) {
				M.AppScreen = M.APPMENU;
				if (!mGR.addFree) {
					GameRenderer.mStart.show();
				}
			}
			break;
		case M.APPMENU:
			Draw_Menu(gl);
			break;
		case M.APPOPTION:
		case M.APPABOUT:
			Draw_Option(gl);
			break;
		case M.APPEDIT:
			Draw_Edit(gl);
			if(mGR.mEdit.size() == 0)
				M.AppScreen = M.APPMENU;
			break;
		case M.APPCLIPS:
			Draw_Clip(gl);
			if(mGR.mEdit.size() == 0)
				M.AppScreen = M.APPMENU;
			break;
		case M.APPMAIN:
			Draw_GalleryMain(gl);
			if(mGR.mEdit.size() == 0)
				M.AppScreen = M.APPMENU;
			break;
		case M.APPSUB:
			Draw_GallerySub(gl);
			if(mGR.mEdit.size() == 0)
				M.AppScreen = M.APPMENU;
			break;
		}
		Counter++;
//		 setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.AppScreen) {
		case M.APPHTT:
			
			break;
		case M.APPMENU:
			Handle_Menu(event);
			break;
		case M.APPOPTION:
		case M.APPABOUT:
			Handle_Option(event);
			break;
		case M.APPEDIT:
			Handle_Edit(event);
			break;
		case M.APPCLIPS:
			Handle_Clip(event);
		break;
		case M.APPMAIN:
			Handle_GalleryMain(event);
			break;
		case M.APPSUB:
			Handle_GallerySub(event);
			break;
		}
//		Handle(event);
		return true;
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.load();}};
	void callAds(){
		try {handler.sendEmptyMessage(0);} catch (Exception e) {}
	}
	
	/*************************Gallery Sub Start**************************************/
	float move,movevx =0;
	float moveR = 0;
	boolean isMove = false;
	void Draw_GallerySub(GL10 gl){
		Draw_BG(gl);
		mGR.mTex_Popup.drawDark(gl, 0, 37.6f, 1.2f, .37f,.45f);
		DrawTransScal(gl, mGR.mTex_Back, 18,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			DrawTransScal(gl, mGR.mTex_SubICN,-15+15*(i%3)+move, 26-14.1f*(i/3),mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][mGR.page*12 +i],move-15+15*(i%3), 26-14.1f*(i/3),
					mGR.LibSel<2?.5f:1,mGR.mSel==(i+1)?0.5f:1);
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
				DrawTransScal(gl, mGR.mTex_SubICN,-15+15*(i%3)+move+(move < 0 ? 50:-50),
						26-14.1f*(i/3),mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
				DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][nPage*12 +i],(move < 0 ? 50:-50)+move-15+15*(i%3), 26-14.1f*(i/3),
						mGR.LibSel<2?.5f:1,mGR.mSel==(i+1)?0.5f:1);
			}
			if (movevx > 0 && move > 50) {
				movevx = move = 0;
				mGR.page--;
			}
			if (movevx < 0 && move < -50) {
				movevx = move = 0;
				mGR.page++;
			}
		}
		if (mGR.page > 0)
			DrawTransScal(gl, mGR.mTex_Arrow[0],-12,-30,mGR.mSel==(101)?1.1f:1,mGR.mSel==(101)?0.5f:1);
		
		if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length)
			DrawTransScal(gl, mGR.mTex_Arrow[1],012,-30,mGR.mSel==(102)?1.1f:1,mGR.mSel==(102)?0.5f:1);
	}
	boolean Handle_GallerySub(MotionEvent event){
		mGR.mSel =0;
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			if(CircRectsOverlap(-15+15*(i%3), 26-14.1f*(i/3), mGR.mTex_SubICN.width()*.4f, mGR.mTex_SubICN.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(18,37, mGR.mTex_Back.width()*.5f, mGR.mTex_Back.Height()*.94f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 100;
		}
		if(CircRectsOverlap(-12,-30, mGR.mTex_Arrow[0].width()*.6f, mGR.mTex_Arrow[0].Height()*.5f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 101;
		}
		if(CircRectsOverlap( 12,-30, mGR.mTex_Arrow[0].width()*.6f, mGR.mTex_Arrow[0].Height()*.5f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 102;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()){
			moveR = Uscreen2worldX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()){
			move =  Uscreen2worldX(event.getX())-moveR;
			if(Math.abs(move) > 5)
				isMove = true;
			else
				isMove = false;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(isMove){
				isMove = false;
				if (mGR.page > 0 && move > 0)
					movevx = 5;
				else if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length && move < 0)
					movevx = -5;
				else
					moveR = move = movevx = 0;
			}else if (mGR.mSel > 0) {
				moveR = move = movevx = 0;
				switch (mGR.mSel) {
				case 100:
					M.AppScreen = M.APPMAIN;
					break;
				case 101:
					if (mGR.page > 0)
						movevx = 5f;
					break;
				case 102:
					if ((mGR.page + 1) * 12 < mGR.mTex_Library[mGR.LibSel].length)
						movevx = -5f;
				default:
					mGR.LibSubSel = mGR.page * 12 + mGR.mSel - 1;
					if(mGR.LibSubSel < mGR.mTex_Library[mGR.LibSel].length){
						Edit tempe = new Edit(mGR.mTex_Library[mGR.LibSel][mGR.LibSubSel]);
						mGR.mEdit.add(tempe);
						M.AppScreen = M.APPEDIT;
						mGR.clear();
					}
					break;
				}
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************GallerySub End****************************************/
	/*************************Gallery Main Start**************************************/
	void Draw_GalleryMain(GL10 gl){
		Draw_BG(gl);
		mGR.mTex_Popup.drawDark(gl, 0, 37.6f, 1.2f, .37f,.45f);
		DrawTransScal(gl, mGR.mTex_Back, 18,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		
		for (int i = 0; i < 20; i++) {
			DrawTransScal(gl, mGR.mTex_MainICN[0],-16.5f+11*(i%4), 26-12.1f*(i/4),mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[i][0],-16.5f+11*(i%4), 26-12*(i/4),i<2?.4f:.7f,mGR.mSel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_MainICN[1],-16.5f+11*(i%4)+4, -4+26-12.1f*(i/4),1,1);
		}
		
	}
	boolean Handle_GalleryMain(MotionEvent event){
		mGR.mSel =0;
		for(int i=0;i<20 ;i++){
			if(CircRectsOverlap(-16.5f+11*(i%4), 26-12.1f*(i/4), mGR.mTex_MainICN[0].width()*.4f, mGR.mTex_MainICN[0].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+1;
			}
		}
		
		if(CircRectsOverlap(18,37, mGR.mTex_Back.width()*.5f, mGR.mTex_Back.Height()*.94f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 100;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel >0) {
			switch (mGR.mSel) {
			case 100:
				M.AppScreen = M.APPCLIPS;
				break;
			default:
				mGR.LibSel = mGR.mSel-1;
				M.AppScreen = M.APPSUB;
				mGR.page = 0;
				break;
			
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************GalleryMain End****************************************/
	/*************************Edit Start**************************************/
	void Draw_Clip(GL10 gl){
		Draw_BG(gl);
		for (int i = 0; i < mGR.mEdit.size(); i++) {
			DrawTextRS(gl, mGR.mEdit.get(i).mTexture, mGR.mEdit.get(i).x,
					mGR.mEdit.get(i).y,
					mGR.mEdit.get(i).angle + mGR.mEdit.get(i).langle,
					mGR.mEdit.get(i).lsx * mGR.mEdit.get(i).sx,
					mGR.mEdit.get(i).lsy * mGR.mEdit.get(i).sy,
					mGR.mEdit.get(i).rgb);
		}
		mGR.mTex_Popup.drawDark(gl, 0, 37.6f, 1.2f, .37f,.45f);
		for(int i=0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_ClipIcn[i],-20+9.1f*i, 37,mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Back, 18,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		
		
		mGR.mTex_Popup.drawDark(gl, 0, -30, 1.2f, .31f,.2f);
		for (int i = 0; i < 3; i++) {
			if ((i == 1 && mGR.mEdit.size() <= 1) || (i == 2 && mGR.mRedo.size() < 1))
				DrawTransScal(gl, mGR.mTex_URL[i], -15 + 15 * i, -30, 1, .5f);
			else
				DrawTransScal(gl, mGR.mTex_URL[i], -15 + 15 * i, -30, mGR.mSel == (i + 5) ? 1.1f : 1, mGR.mSel == (i + 5) ? 0.5f : 1);
		}
		if(mGR.Popup == 1)
		{
			DrawTransScal(gl, mGR.mTex_Popup, 0, 0,1.1f,.9f);
			for (int i = 0; i < 3; i++) {
				DrawTransScal(gl, mGR.mTex_PopBtt[i], 1.5f, 8-8*i,mGR.mSel==(i+8)?1.1f:1,mGR.mSel==(i+8)?0.5f:1);
				DrawTransScal(gl, mGR.mTex_PopIcn[i],-7.5f, 8-8*i,mGR.mSel==(i+8)?1.1f:1,mGR.mSel==(i+8)?0.5f:1);
			}
		}
		if(screenshot)
			SaveScreen(gl);
	}
	boolean Handle_Clip(MotionEvent event){
		mGR.mSel =0;
		if(mGR.Popup == 1){
			for(int i=0;i<3;i++){
				if(CircRectsOverlap(1.5f, 8-8*i, mGR.mTex_PopBtt[i].width()*.5f, mGR.mTex_PopBtt[i].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
					mGR.mSel = i+8;
				}
			}
		}else{
			for(int i=0;i<4 ;i++){
				if(CircRectsOverlap(-20+9.1f*i, 37, mGR.mTex_Buy[0].width()*.4f, mGR.mTex_Buy[0].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
					mGR.mSel = i+1;
				}
			}
			for(int i=0;i<3;i++){
				if(CircRectsOverlap(-15+15*i,-30, mGR.mTex_URL[i].width()*.5f, mGR.mTex_URL[i].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
					mGR.mSel = i+5;
				}
			}
			
			if(CircRectsOverlap(18,37, mGR.mTex_Back.width()*.5f, mGR.mTex_Back.Height()*.94f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = 100;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:case 100:
				if(mGR.isChange)
					mGR.Popup = 1;
				else{
					M.AppScreen = M.APPMENU;
					GameRenderer.mStart.show();
				}
				break;
			case 2:
			case 8:
				if(mGR.isChange){
					screenshot = true;
					mGR.isShare = true;
					mGR.isChange = false;
				}
				else
					shareImage();
				mGR.Popup = 0;
				//Share
				break;
			case 3:
			case 9://Save
				mGR.Popup = 0;
				if(mGR.isChange){
					screenshot = true;
					mGR.isChange = false;
				}
				break;
			case 4:
				MoreGame();
				break;
			case 5:
				M.AppScreen = M.APPMAIN;
				break;
			case 6:
				if(mGR.mEdit.size()>1){
					mGR.mRedo.add(mGR.mEdit.get(mGR.mEdit.size()-1));
					mGR.mEdit.remove(mGR.mEdit.size()-1);
					mGR.isChange = true;
				}//Undo
				break;
			case 7:
				if(mGR.mRedo.size()>0){
					mGR.mEdit.add(mGR.mRedo.get(mGR.mRedo.size()-1));
					mGR.mRedo.remove(mGR.mRedo.size()-1);
					mGR.isChange = true;
				}//Redo
				break;
			case 10:
				M.AppScreen = M.APPMENU;
				mGR.Popup = 0;
				GameRenderer.mStart.show();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************OPTION End****************************************/
	/*************************Edit Start**************************************/
	void Draw_Edit(GL10 gl){
		Draw_BG(gl);
		for (int i = 0; i < mGR.mEdit.size(); i++) {
			DrawTextRS(gl, mGR.mEdit.get(i).mTexture, mGR.mEdit.get(i).x,
					mGR.mEdit.get(i).y,
					mGR.mEdit.get(i).angle + mGR.mEdit.get(i).langle,
					mGR.mEdit.get(i).lsx * mGR.mEdit.get(i).sx,
					mGR.mEdit.get(i).lsy * mGR.mEdit.get(i).sy,
					mGR.mEdit.get(i).rgb);
		}
		mGR.mTex_Popup.drawDark(gl, 0, 37.6f, 1.2f, .37f,.45f);
		DrawTransScal(gl, mGR.mTex_ContiBtt[0], -18,37,mGR.mSel==(101)?1.1f:1,mGR.mSel==(101)?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ContIcn[0], -8,37,mGR.mSel==(101)?1.1f:1,mGR.mSel==(101)?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ContiBtt[1],10,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ContIcn[1], 20,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_Back, 18,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		mGR.mTex_Popup.drawDark(gl, 0, -30, 1.2f, .31f,.2f);
		for (int i = 0; i < 5; i++) {
			DrawTexture(gl, mGR.mTex_EditIcn[i][i + 1 == mGR.mSel ? 1 : 0], -19 + 9.5f * i, -30);
		}
		DrawTexture(gl, mGR.mTex_EditIcn[mGR.action][1], -19 + 9.5f * mGR.action, -30);
		
		if(mGR.action == 4){
			for (int i = 0; i < 3 && mGR.mEdit.size() > 0; i++) {
				DrawTexture(gl, mGR.mTex_RGB[i],0,-5-7*i);
				DrawTexture(gl, mGR.mTex_RGB[3],mGR.mEdit.get(mGR.mEdit.size()-1).rgb[i],-5-7*i);
			}
		}
	}
	boolean Handle_Edit(MotionEvent event){
		mGR.mSel =0;
		
		
		for(int i=0;i<5;i++){
			if(CircRectsOverlap(-19 + 9.5f * i, -30, mGR.mTex_EditIcn[i][0].width()*.5f, mGR.mTex_EditIcn[i][0].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(18,37, mGR.mTex_ContiBtt[0].width()*.5f, mGR.mTex_Back.Height()*.94f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 100;
		}
		if(CircRectsOverlap(-12,37, mGR.mTex_ContiBtt[0].width()*.5f, mGR.mTex_ContiBtt[0].Height()*.5f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 101;//Continue
		}
		if(mGR.mEdit.size()>0 && mGR.mSel == 0)
			TouchMove(event);
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0) {
			switch (mGR.mSel) {
			case 100:
				if(mGR.mEdit.size()>1){
					mGR.mEdit.remove(mGR.mEdit.size()-1);
					M.AppScreen = M.APPCLIPS;
				}
				else{
					M.AppScreen = M.APPMENU;
					GameRenderer.mStart.show();
					}
				break;
			case 101:
				M.AppScreen = M.APPCLIPS;
				break;
			default:
				mGR.action = mGR.mSel -1;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	float oldDist;
	float oldAng;
	float mode = 0;
	float mmode = 0;
	float scale = 1;
	float cx,cy;
    public boolean TouchMove( MotionEvent event) 
    {
    	
    	int work = mGR.mEdit.size()-1;
    	if(work<0)
    		return false;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = event.getX();
			cy = event.getY();
			break;
		case MotionEvent.ACTION_UP: // first finger lifted
			 mode = 0;
			 mmode = 0;
			 mGR.mEdit.get(work).lsx = mGR.mEdit.get(work).lsx*mGR.mEdit.get(work).sx;
			 mGR.mEdit.get(work).lsy = mGR.mEdit.get(work).lsy*mGR.mEdit.get(work).sy;
			 mGR.mEdit.get(work).sx = mGR.mEdit.get(work).sy = 1;
			 mGR.mEdit.get(work).angle +=mGR.mEdit.get(work).langle;
			 mGR.mEdit.get(work).langle = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP: // second finger lifted
			 mode = 0;
			 mmode = 0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
			mode = 1;
			mmode = 0;
			oldDist = spacing(event);
			oldAng = Angle(event);
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode == 1)
			{
				float newDist = spacing(event);
				if (mGR.action == 0) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sy=mGR.mEdit.get(work).sx=scale;
				}
				if (mGR.action == 1) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sx=scale;
				}
				if (mGR.action == 2) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sy=scale;
				}
				if (mGR.action == 3){
					float  newAng = Angle(event);
					mGR.mEdit.get(work).langle = (float)Math.toDegrees(oldAng-newAng);
				}
				
			} else if (mmode == 1) {
				if (mGR.action == 4) {
					for (int i = 0; i < 3; i++) {
						if (CircRectsOverlap( mGR.mEdit.get(mGR.mEdit.size() - 1).rgb[i], -5- 6 * i,mGR.mTex_EditIcn[i][0].width() * 1.0f,mGR.mTex_RGB[0].Height() * 1.1f,Uscreen2worldX(event.getX()),Uscreen2worldY(event.getY()), 1)) {
							mGR.mEdit.get(work).rgb[i] = Uscreen2worldX(event.getX());
							if(mGR.mEdit.get(work).rgb[i] > 10)
								mGR.mEdit.get(work).rgb[i] = 10;
							if(mGR.mEdit.get(work).rgb[i] <-10)
								mGR.mEdit.get(work).rgb[i] = -10;
						}
					}
				} else {
					mGR.mEdit.get(work).x += Uscreen2worldX(event.getX())- Uscreen2worldX(cx);
					mGR.mEdit.get(work).y += Uscreen2worldY(event.getY())- Uscreen2worldY(cy);
					cx = event.getX();
					cy = event.getY();
				}
			}
			break;
		}
        return true; // indicate event was handled
    }
    float spacing(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = screen2worldX(event.getX(0)) - screen2worldX(event.getX(1));
        float y = screen2worldY(event.getY(0)) - screen2worldY(event.getY(1));
        return FloatMath.sqrt(x * x + y * y);
    }
    float Angle(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = screen2worldX(event.getX(0)) - screen2worldX(event.getX(1));
        float y = screen2worldY(event.getY(0)) - screen2worldY(event.getY(1));
        return (float)GetAngle(y, x);
    }
	/*************************OPTION End****************************************/
	/*************************OPTION Start**************************************/
	void Draw_Option(GL10 gl){
		Draw_BG(gl);
		mGR.mTex_Popup.drawDark(gl, 0, 37.6f, 1.2f, .37f,.45f);
		if(M.AppScreen == M.APPOPTION){
			DrawTexture(gl, mGR.mTex_Button[3], -17, 37);
			DrawTexture(gl, mGR.mTex_Popup, 0, 10);
			DrawTexture(gl, mGR.mTex_AdsFree, 0, 13);
			for(int i=0;i<2;i++){
				DrawTransScal(gl, mGR.mTex_Buy[i], -8+17*i, 4,mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
			}
		}
		else{
			DrawTexture(gl, mGR.mTex_Button[2], -17, 37);
			DrawTexture(gl, mGR.mTex_About, 0, 11);
		}
		
		DrawTransScal(gl, mGR.mTex_Back, 18,37,mGR.mSel==(100)?1.1f:1,mGR.mSel==(100)?0.5f:1);
		
		
		for(int i=0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_Button[i+4],- 7+21*(i%2), -16-11*(i/2),mGR.mSel==(i+4)?1.1f:1,mGR.mSel==(i+4)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Icon[i+4]  ,-16+21*(i%2), -15-11*(i/2),mGR.mSel==(i+4)?1.1f:1,mGR.mSel==(i+4)?0.5f:1);
		}
	}
	boolean Handle_Option(MotionEvent event){
		mGR.mSel =0;
		for(int i=0;i<2 && M.AppScreen == M.APPOPTION ;i++){
			if(CircRectsOverlap(-8+17*i, 4, mGR.mTex_Buy[0].width()*.4f, mGR.mTex_Buy[0].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+1;
			}
		}
		
		for(int i=0;i<4;i++){
			if(CircRectsOverlap(-10+21*(i%2), -16-11*(i/2), mGR.mTex_Button[i].width()*.5f, mGR.mTex_Button[i].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+4;
			}
		}
		if(CircRectsOverlap(18,37, mGR.mTex_Back.width()*.94f, mGR.mTex_Back.Height()*.94f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
			mGR.mSel = 100;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:case 100:
				M.AppScreen = M.APPMENU;
				GameRenderer.mStart.show();
				break;
			case 2:
				mGR.mInAPP.onBuyGold50000(null);//Buy
				break;
			case 4:
				facebook();
				break;
			case 5:
				share2friend();
				break;
			case 6:
				google();
				break;
			case 7:
				MoreGame();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************OPTION End****************************************/
	/*************************Menu Start**************************************/
	void Draw_Menu(GL10 gl){
		Draw_BG(gl);
		DrawTexture(gl, mGR.mTex_Splash, 0, 25);
//		DrawTexture(gl, mGR.mTex_Popup, 0, 1);
//		for(int i=0;i<3;i++){
//			DrawTransScal(gl, mGR.mTex_Priveiw, -13.5f+13*i, 1,mGR.mSel==(i+1)?1.1f:1,mGR.mSel==(i+1)?0.5f:1);
//		}
		for (int i = 0, j = mGR.mGallary.size() - 1; i < 3 && j >= 0; i++, j--) {
			DrawTransScal(gl, mGR.mGallary.get(j).mTexture, -15f+15f*i, 1.5f,mGR.mSel==(i+1)?0.3f:.31f,mGR.mSel==(i+1)?0.5f:1);
		}
		DrawTexture(gl,mGR.mTex_Priveiw,  0, 1);
		for(int i=0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_Button[i],- 7+21*(i%2), -16-11*(i/2),mGR.mSel==(i+4)?1.1f:1,mGR.mSel==(i+4)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Icon[i]  ,-16+21*(i%2), -15-11*(i/2),mGR.mSel==(i+4)?1.1f:1,mGR.mSel==(i+4)?0.5f:1);
		}
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel =0;
		for(int i=0;i<3;i++){
			if(CircRectsOverlap(-15f+15*i, 1, mGR.mTex_SubICN.width()*.4f, mGR.mTex_SubICN.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+1;
			}
		}
		
		for(int i=0;i<4;i++){
			if(CircRectsOverlap(-10+21*(i%2), -16-11*(i/2), mGR.mTex_Button[i].width()*.5f, mGR.mTex_Button[i].Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
				mGR.mSel = i+4;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
			case 2:
			case 3:
				if (mGR.mGallary.size() >= mGR.mSel) {
					mGR.mEdit.clear();
					Edit tempe = new Edit(mGR.mGallary.get(mGR.mGallary.size() - mGR.mSel).mTexture);
					tempe.lsy = tempe.lsx = 50 / mGR.mGallary.get(mGR.mGallary.size() - mGR.mSel).mTexture.width();
					mGR.mEdit.add(tempe);
					M.AppScreen = M.APPEDIT;
					
					mGR.path = M.DIR + "/" + System.currentTimeMillis()
							+ ".jpg";
					mGR.clear();
					callAds();
				}
				break;
			case 4:
				GameRenderer.mStart.takePhoto();// Camera
				break;
			case 5:
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);// Gallery
				break;
			case 6:
				M.AppScreen = M.APPABOUT;// About
				break;
			case 7:
				M.AppScreen = M.APPOPTION;// Option
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Menu End**************************************/
	void Draw_BG(GL10 gl){
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<8;j++){
				DrawTexture(gl, mGR.mTex_BG, i*mGR.mTex_BG.width()-24, j*mGR.mTex_BG.Height()-42);
			}
		}
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextRS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float sclx,float scly,float rgb[])
	{
		Tex.drawRotet(gl, x, y,angle,sclx,scly,rgb);
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
	float Uscreen2worldX(float a)
	{
		float c = ( a-(M.ScreenWidth/2))*(82/M.ScreenHieght);
		return c;
	}
	float Uscreen2worldY(float a)
	{
		//float c = ((a / M.ScreenHieght)- 0.5f)*(-41);
		float c = ((M.ScreenHieght/2)- a)*(82/M.ScreenHieght);
		
		return c;
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
	void drawNumber(GL10 gl,int no,float x, float y)
	{
//		float dx = mGR.mTex_Font[0].width();
//		 String strs = ""+no;
//		for(int i =0;i<strs.length();i++)
//		{
//			int k = ((int)strs.charAt(i))-48;
//			if(k>=0 && k<mGR.mTex_Font.length)
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
//		}
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
	void google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
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

	 void shareImage() {
		String extr = Environment.getExternalStorageDirectory().toString();
		File myPath = new File(extr, mGR.path);
		if (!myPath.exists()) {
			GameRenderer.mStart.SaveMassage();
		}
		String str = Environment.getExternalStorageDirectory() + mGR.path;
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(str)));
		shareIntent.setType("image/png");
		GameRenderer.mContext.startActivity(Intent.createChooser(shareIntent, "Where?"));
	}
	
	
	void SaveScreen(GL10 gl){
		if(!screenshot)
			return;
		Draw_BG(gl);
		for (int i = 0; i < mGR.mEdit.size(); i++) {
			DrawTextRS(gl, mGR.mEdit.get(i).mTexture, mGR.mEdit.get(i).x,
					mGR.mEdit.get(i).y,
					mGR.mEdit.get(i).angle + mGR.mEdit.get(i).langle,
					mGR.mEdit.get(i).lsx * mGR.mEdit.get(i).sx,
					mGR.mEdit.get(i).lsy * mGR.mEdit.get(i).sy,
					mGR.mEdit.get(i).rgb);
		}
        Bitmap bb = getNew(gl);
        Bitmap b = ImageCut(bb);
        String extr = Environment.getExternalStorageDirectory().toString();
        
        File folder = new File(Environment.getExternalStorageDirectory() + M.DIR);
        if(!folder.exists())
        {
            folder.mkdir();
        }  
        
        File myPath = new File(extr, mGR.path);
        if(myPath.exists())
        {
        	if(mGR.mGallary.size()>0)
        		mGR.mGallary.remove(mGR.mGallary.size()-1);
        	myPath.delete();
        }
        Gallary gallary = new Gallary(mGR.addBitmap(mGR.resizeImg(b, b.getWidth()/4, b.getHeight()/4)), extr+ mGR.path);
        mGR.mGallary.add(gallary);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(GameRenderer.mStart.getContentResolver(), b, "Screen", "screen");
            if(mGR.isShare)
            	shareImage();
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        GameRenderer.mStart.finish();
    }
    boolean screenshot = false;
    Bitmap getNew(GL10 gl)
    {
    	int width = (int)M.ScreenWidth;
    	int height = (int)M.ScreenHieght;
    	int screenshotSize = width * height;
    	ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
    	bb.order(ByteOrder.nativeOrder());
    	gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
    	int pixelsBuffer[] = new int[screenshotSize];
    	bb.asIntBuffer().get(pixelsBuffer);
    	bb = null;
    	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    	bitmap.setPixels(pixelsBuffer, screenshotSize-width, -width, 0, 0, width, height);
    	pixelsBuffer = null;
    	short sBuffer[] = new short[screenshotSize];
    	ShortBuffer sb = ShortBuffer.wrap(sBuffer);
    	bitmap.copyPixelsToBuffer(sb);
    	for (int i = 0; i < screenshotSize; ++i) {                  
    		short v = sBuffer[i];
    		sBuffer[i] = (short) (((v&0x1f) << 11) | (v&0x7e0) | ((v&0xf800) >> 11));
        }
        sb.rewind();
        bitmap.copyPixelsFromBuffer(sb);
        screenshot = false;
    	return bitmap;
    }
    Bitmap ImageCut(Bitmap bitmap)
    {
    	int x = 0;//(int)(-M.ScreenWidth	 *.06);
    	int y =0;//(int)(-M.ScreenHieght*.21);
    	int dx=(int)M.ScreenWidth;//(M.ScreenWidth  *.88);
    	int dy=(int)M.ScreenHieght;//(M.ScreenHieght *.58);
    	
    	
    	Bitmap image = Bitmap.createBitmap(dx,dy, Bitmap.Config.ARGB_8888);
//        image.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(image);
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, x, y, null);
        return image;
    }
}
