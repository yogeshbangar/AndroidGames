package com.htt.app.photoframeschool;


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
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
public class Group extends Mesh {
	GameRenderer mGR = null;
	int Sel =0;
	int forSave = 0;
	int Counter =0;
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	@Override
	public void draw(GL10 gl) 
	{
		switch (M.AppScreen) {
		case M.APPLOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 120)
				M.AppScreen = M.APPMENU;
			Counter++;
			break;
		case M.APPMENU:
			Draw_Menu(gl);
			break;
		case M.APPGRID:
			Draw_Grid(gl);
			break;
		case M.APPMAIN:
			Draw_GalleryMain(gl);
			break;
		case M.APPSUB:
			Draw_GallerySub(gl);
			break;
		case M.APPPLAY:
			Draw_Frame(gl);
			break;
		case M.ADDTEXT:
			Draw_AddText(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		
		switch (M.AppScreen) {
		case M.APPMENU:
			Handle_Menu(event);
			break;
		case M.APPGRID:
			Handle_Grid(event);
			break;
		case M.APPMAIN:
			Handle_GalleryMain(event);
			break;
		case M.APPSUB:
			Handle_GallerySub(event);
			break;
		case M.APPPLAY:
			Handle_Frame(event);
			break;
		case M.ADDTEXT:
			Handle_AddText(event);
			break;
		}
//		Handle(event);
		newHandle(event);
		return true;
	}
	
	 /*************************Gallery Sub Start**************************************/
		void Draw_Grid(GL10 gl){
			DrawTexture(gl, mGR.mTex_BG, 0, 0);
			DrawTexture(gl, mGR.mTex_Bar, 0, .893f);
			DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
			for (int i = 0; i < 12 && i < mGR.mTex_Grid[mGR.mFramPage].length; i++) {
				DrawTransScal(gl, mGR.mTex_Grid[mGR.mFramPage][i],move-.36f+.36f*(i%3), .60f-.35f*(i/3),
						Sel==(i+1)?1.8f:1.6f,Sel==(i+1)?0.5f:1);
			}
			
			if (move != 0 || movevx !=0) {
				int nPage = 0;
				move += movevx;
				if (move > 0 ){
					nPage = mGR.mFramPage-1;
				}
				if (move < 0 ){
					nPage = mGR.mFramPage+1;
				}
				if (nPage >= 0 && nPage < mGR.mTex_Grid.length) {
					for (int i = 0; i < 12 && i < mGR.mTex_Grid[nPage].length; i++) {
						DrawTransScal(gl, mGR.mTex_Grid[nPage][i],move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3),
								Sel==(i+1)?1.8f:1.6f,Sel==(i+1)?0.5f:1);
					}
				}
				if (movevx > 0 && move > 1) {
					movevx = move = 0;
					mGR.mFramPage--;
				}
				if (movevx < 0 && move < -1) {
					movevx = move = 0;
					mGR.mFramPage++;
				}
			}
			if (mGR.mFramPage > 0)
				DrawTransScal(gl, mGR.mTex_Arrow[0],-.3f, -.72f,Sel==(101)?1.1f:1,Sel==(101)?0.5f:1);
			
			if (mGR.mFramPage < mGR.mTex_Grid.length-1)
				DrawTransScal(gl, mGR.mTex_Arrow[1],0.3f, -.72f,Sel==(102)?1.1f:1,Sel==(102)?0.5f:1);
		}
		boolean Handle_Grid(MotionEvent event){
			Sel =0;
			for (int i = 0; i < 12 && i < mGR.mTex_Grid[mGR.mFramPage].length; i++) {
				if(CircRectsOverlap(move-.36f+.36f*(i%3), .60f-.35f*(i/3), mGR.mTex_SubICN.width()*.3f, mGR.mTex_SubICN.Height()*.2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
					Sel = i+1;
				}
			}
			if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =100;
			}
			if(CircRectsOverlap(-.3f,-.72f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =101;
			}
			if(CircRectsOverlap(0.3f,-.72f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =102;
			}
			if (MotionEvent.ACTION_DOWN == event.getAction()){
				moveR = screen2worldX(event.getX());
			}
			if (MotionEvent.ACTION_MOVE == event.getAction()){
				move =  screen2worldX(event.getX())-moveR;
				if(Math.abs(move) > .1)
					isMove = true;
				else
					isMove = false;
			}
			if (MotionEvent.ACTION_UP == event.getAction()) {
				if(isMove){
					isMove = false;
					if (mGR.mFramPage > 0 && move > 0)
						movevx = .1f;
					else if (mGR.mFramPage < mGR.mTex_Grid.length-1 && move < 0)
						movevx = -.1f;
					else
						moveR = move = movevx = 0;
				}else if (Sel > 0) {
					moveR = move = movevx = 0;
					switch (Sel) {
					case 100:
						M.AppScreen = M.APPMENU;
						break;
					case 101:
						if (mGR.mFramPage > 0)
							movevx = .1f;
						break;
					case 102:
						if (mGR.mFramPage < mGR.mTex_Grid.length-1)
							movevx = -.1f;
						break;
					default:
						forSave =0;
						if(Sel <= mGR.mTex_Grid[mGR.mFramPage].length )
							mGR.mFramImg = Sel -1;
						mGR.gameReset();
						M.AppScreen = M.APPPLAY;
						break;
					}
				}
				Sel = 0;
			}
			return true;
		}
		/*************************GallerySub End****************************************/
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
//		newset(gl);
		DrawTransScal(gl, mGR.mTex_Start	,-.30f, -.35f,Sel == 1 ?1.1f:1,Sel == 1 ?0.5f:1);
		for(int i =0;i<mGR.mTex_SplBtn.length;i++)
			DrawTransScal(gl, mGR.mTex_SplBtn[i] ,-.46f + .23f*i, -.687f,Sel == 2+i ?1.1f:1,Sel == 2+i ?0.5f:1);
		
	}
	boolean Handle_Menu(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(-.30f, -.35f, mGR.mTex_Effect.width()*.4f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =1;
		}
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.46f + .23f*i, -.687f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =2+i;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				M.AppScreen = M.APPGRID;
				break;
			case 2:
				GameRenderer.mStart.openFolder();
//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//				photoPickerIntent.setType("image/*");
//				GameRenderer.mStart.startActivityForResult(photoPickerIntent, 2503);// Gallery
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				share2friend();
				break;
			case 5:
				facebook();
				break;
			case 6:
				RateUs();
				break;
			}
			Sel = 0;
		}
		return true;
	}
	
	float rgb[] = {.35f,.35f,.35f};
	void Draw_Frame(GL10 gl){
		
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bar, 0, .893f);
		
		DrawTransScal(gl, mGR.mTex_Mode[mGR.mMode==0?0:1], -.45f, .89f,Sel == 15 ?1.1f:1,Sel == 15 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Mode[mGR.mMode==0?3:2], -.20f, .89f,Sel == 16 ?1.1f:1,Sel == 16 ?0.5f:1);
		
		if(mGR.mMode == 1 && mGR.mEdit.size()>0)
			DrawTransScal(gl, mGR.mTex_Undo, 0.12f, .89f,forSave == 19 ?1.1f:1,forSave == 19 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ShareBtn, 0.30f, .89f,forSave == 17 ?1.1f:1,forSave == 17 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_SaveBtn , 0.48f, .89f,forSave == 18 ?1.1f:1,forSave == 18 ?0.5f:1);
//		newset(gl);
		for(int i =0;i<mGR.mFrames.size();i++){
			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor((int)(mGR.mFrames.get(i).rectX),(int)(mGR.mFrames.get(i).rectY),(int)mGR.mFrames.get(i).rectDx,(int)mGR.mFrames.get(i).rectDy);
			if(mGR.mFrames.get(i).mTexture!=null){
				mGR.mFrames.get(i).mTexture.drawAll(gl, mGR.mFrames.get(i).x, mGR.mFrames.get(i).y, 
						mGR.mFrames.get(i).ang + mGR.mFrames.get(i).langle,
						mGR.mFrames.get(i).lsx * mGR.mFrames.get(i).sx,
						mGR.mFrames.get(i).lsy * mGR.mFrames.get(i).sy,
						mGR.mFrames.get(i).rgb);
			}else{
				DrawTexture(gl, mGR.mTex_Plus, mGR.mFrames.get(i).x, mGR.mFrames.get(i).y);
			}
//			DrawTransScal(gl, mGR.mTex_SplBtn[i%mGR.mTex_SplBtn.length], mGR.mFrames.get(i).x, mGR.mFrames.get(i).y,5,1);
			gl.glDisable(GL10.GL_SCISSOR_TEST);	
		}
		for(int i =0;i<mGR.mTex_Frame.size();i++){
			mGR.mTex_Frame.get(i).mTex.drawAll(gl, mGR.mTex_Frame.get(i).x, mGR.mTex_Frame.get(i).y, 
					0,2,2,mGR.mTex_Frame.get(i).rgb);
			
		}
		for(int i =0;i<mGR.mEdit.size();i++){
			if(mGR.mEdit.get(i).mTexture!=null){
				mGR.mEdit.get(i).mTexture.drawAll(gl, mGR.mEdit.get(i).x, mGR.mEdit.get(i).y, 
						mGR.mEdit.get(i).angle + mGR.mEdit.get(i).langle,
						mGR.mEdit.get(i).lsx * mGR.mEdit.get(i).sx,
						mGR.mEdit.get(i).lsy * mGR.mEdit.get(i).sy,
						mGR.mEdit.get(i).rgb);
			}
		}
//		for(int i =0;i<mGR.mFrames.size();i++){
//			DrawTransScal(gl, mGR.mTex_Test, mGR.mFrames.get(i).x, mGR.mFrames.get(i).y
//					,mGR.mImgSel == i ?8.1f:1,mGR.mImgSel == i ?0.5f:1);
//		}
		
		if(mGR.action == 4)
		{
			if(mGR.mMode == 0){
				for(int i=0;i<3;i++){
					rgb[0] = rgb[1] =rgb[2]= -.4f;
					rgb[i] = .4f;
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
					rgb[i] = mGR.mFrames.get(mGR.mImgSel).rgb[i];
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
					DrawTexture(gl, mGR.mTex_RGBSel, mGR.mFrames.get(mGR.mImgSel).rgb[i], -.35f-.08f*i);
	
				}
			}else if(mGR.mEdit.size()>0){
				for(int i=0;i<3;i++){
					rgb[0] = rgb[1] =rgb[2]= -.4f;
					rgb[i] = .4f;
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
					rgb[i] = mGR.mEdit.get(mGR.mEdit.size()-1).rgb[i];
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
					DrawTexture(gl, mGR.mTex_RGBSel, mGR.mEdit.get(mGR.mEdit.size()-1).rgb[i], -.35f-.08f*i);
				}
			}
		}
		if (mGR.action == 5 && mGR.mTex_Frame.size() > 0)
		{
			for(int i=0;i<3;i++){
				rgb[0] = rgb[1] =rgb[2]= -.4f;
				rgb[i] = .4f;
				mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
				rgb[i] = mGR.mTex_Frame.get(0).rgb[i];
				mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
				DrawTexture(gl, mGR.mTex_RGBSel, mGR.mTex_Frame.get(0).rgb[i], -.35f-.08f*i);

			}
		}
		
		DrawTexture(gl, mGR.mTex_Bar, 0, -.69f);
		/*if(mGR.mMode == 0){
			DrawTransScal(gl, mGR.mTex_BgBtn	, -.44f+.23f*0, -.69f,Sel == 1 ?1.1f:1,Sel == 1 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_FramBtn	, -.44f+.23f*1, -.69f,Sel == 2 ?1.1f:1,Sel == 2 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_ClrBtn	, -.44f+.23f*2, -.69f,Sel == 3 ?1.1f:1,Sel == 3 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_TextBtn	, -.44f+.23f*3, -.69f,Sel == 4 ?1.1f:1,Sel == 4 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_GalrBtn	, -.44f+.23f*4, -.69f,Sel == 5 ?1.1f:1,Sel == 5 ?0.5f:1);
		}
		if(mGR.mMode == 1)*/{
			DrawTransScal(gl, mGR.mTex_Effect	, -.48f+.162f*0, -.69f,mGR.action == 4?1.1f:1,mGR.action == 4 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_ClrBtn	, -.48f+.162f*1, -.69f,mGR.action == 5?1.1f:1,mGR.action == 5 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_ZoomBtn	, -.48f+.162f*2, -.69f,mGR.action == 0 ?1.1f:1,mGR.action == 0 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_RotBtn	, -.48f+.162f*3, -.69f,mGR.action == 3 ?1.1f:1,mGR.action == 3 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_flipH	, -.48f+.162f*4, -.69f,Sel == 10?1.1f:1,Sel == 10?0.5f:1);
			DrawTransScal(gl, mGR.mTex_FlipV	, -.48f+.162f*5, -.69f,Sel == 11?1.1f:1,Sel == 11?0.5f:1);
			DrawTransScal(gl, mGR.mTex_More		, -.48f+.162f*6, -.69f,mGR.action == 6?1.1f:1,mGR.action == 6?0.5f:1);
			switch (mGR.action) {
			case 0:
				for(int i =0;i<6;i++){
					DrawTransScal(gl, mGR.mTex_Zoom[i]	, -.47f+.188f*i, -.46f,Sel == 20+i ?1.1f:1,Sel == 20+i ?0.5f:1);
				}
				switch (Sel) {
				case 20:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsx += .01f;
						mGR.mFrames.get(mGR.mImgSel).lsy += .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsx += .01f;
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsy += .01f;
					}
					break;
				case 21:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsx -= .01f;
						mGR.mFrames.get(mGR.mImgSel).lsy -= .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsx -= .01f;
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsy -= .01f;
					}
					break;
				case 22:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsx += .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsx += .01f;
					}
					break;
				case 23:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsx -= .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsx -= .01f;
					}
					break;
				case 24:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsy += .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsy += .01f;
					}
					break;
				case 25:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).lsy -= .01f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).lsy -= .01f;
					}
					break;
				}
				break;
			case 3:
				for(int i =0;i<4;i++){
					DrawTransScal(gl, mGR.mTex_Rotate[i], -.47f+.188f*i, -.46f,Sel == 30+i ?1.1f:1,Sel == 30+i ?0.5f:1);
				}
				switch (Sel) {
				case 30:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).ang += 1f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).angle += 1f;
					}
					break;
				case 31:
					if (mGR.mMode == 0) {
						mGR.mFrames.get(mGR.mImgSel).ang -= 1f;
					} else if (mGR.mEdit.size() > 0) {
						mGR.mEdit.get(mGR.mEdit.size() - 1).angle -= 1f;
					}
					break;
				}
				break;
			case 6:
				DrawTransScal(gl, mGR.mTex_Clipart	, 0.47f-.188f*0, -.46f,Sel == 60+0 ?1.1f:1,Sel == 60+0 ?0.5f:1);
				DrawTransScal(gl, mGR.mTex_TextBtn	, 0.47f-.188f*1, -.46f,Sel == 60+1 ?1.1f:1,Sel == 60+1 ?0.5f:1);
				break;
			}
		}
		
		
		if(mGR.showPopup)
		{
			mGR.mTex_BG.drawDark(gl, 0, 0);
//			newset(gl);
			DrawTexture(gl, mGR.mTex_Popup	, 0, 0);
			DrawTransScal(gl, mGR.mTex_Cross	, .506f, 0.196f,Sel == 70 ?1.1f:1,Sel == 70 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Camera	,-.270f, -.080f,Sel == 71 ?1.1f:1,Sel == 71 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_File		, .270f, -.080f,Sel == 72 ?1.1f:1,Sel == 72 ?0.5f:1);
		}
		if(screenshot){
			SaveScreen(gl);
		}
	}
	public void openFolder()
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
		intent.setDataAndType(uri, "image/*");
		GameRenderer.mStart.startActivityForResult(intent, 1);// Gallery
//		GameRenderer.mStart.startActivityForResult(Intent.createChooser(intent, "Open folder"));
	}
	boolean Handle_Frame(MotionEvent event){
		Sel =0;
		if(mGR.showPopup && MotionEvent.ACTION_DOWN == event.getAction()){
			
			if(CircRectsOverlap(.506f, 0.196f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel = 70;
				mGR.showPopup = false;
			}
			if(CircRectsOverlap(-.270f, -.080f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel = 71;	
				mGR.showPopup = false;
				GameRenderer.mStart.takePhoto();// Camera
			}
			if(CircRectsOverlap(0.270f, -.080f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel = 72;
				mGR.showPopup = false;
				openFolder();
//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				photoPickerIntent.setType("image/*");
//				GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);// Gallery
			}
			if(MotionEvent.ACTION_UP == event.getAction() && Sel >0){
				switch (Sel) {
				case 70:
//					mGR.showPopup = false;
					break;
				case 71:
//					GameRenderer.mStart.takePhoto();// Camera
					break;
				case 72:
//					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//					photoPickerIntent.setType("image/*");
//					GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);// Gallery
					break;
				}
				Sel =0;
			}
			return true;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			Action = false;
			for(int i =0;i<mGR.mFrames.size();i++){
				if(CircRectsOverlap(
						(FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][0]/1000f)+(FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][2]/1000f),
					  -((FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][1]/1000f)-(FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][3]/1000f)),
					  (FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][2]/1000f), 
					  (FrameArr.Arr[mGR.mFramPage][mGR.mFramImg][i][3]/1000f), 
					  screen2worldX(event.getX()), 
					  screen2worldY(event.getY()), .01f)){
					mGR.mImgSel =i;
					if(mGR.mMode == 0&& mGR.mFrames.get(mGR.mImgSel).mTexture == null){
						mGR.showPopup = true;
						return true;
					}else{
						Action = true;
					}
					
					
					
				}
			}
			/*if(mGR.mMode == 0){
				for(int i =0;i<5;i++){
					if(CircRectsOverlap(-.44f+.23f*i,-.69f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
						Sel =1+i;
					}
				}
			}
			if(mGR.mMode == 1)*/
			{
				
				switch (mGR.action) {
				case 0:
					for(int i =0;i<6;i++){
						if(CircRectsOverlap(-.47f+.188f*i, -.46f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
							Sel =20+i;
//						DrawTransScal(gl, mGR.mTex_Effect	, -.47f+.188f*i, -.46f,mGR.action == 4?1.1f:1,mGR.action == 4 ?0.5f:1);
					}
					}
					break;
				case 3:
					for(int i =0;i<4;i++){
						if(CircRectsOverlap(-.47f+.188f*i, -.46f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
							Sel =30+i;
							if (Sel == 32) {

								if (mGR.mMode == 0) {
									mGR.mFrames.get(mGR.mImgSel).ang += 90f;
								} else if (mGR.mEdit.size() > 0) {
									mGR.mEdit.get(mGR.mEdit.size() - 1).angle += 90f;
								}
							}
							if (Sel == 33) {
								if (mGR.mMode == 0) {
									mGR.mFrames.get(mGR.mImgSel).ang -= 90f;
								} else if (mGR.mEdit.size() > 0) {
									mGR.mEdit.get(mGR.mEdit.size() - 1).angle -= 90f;
								}
							}
						}
					}
					break;
				case 4:
					rgbsel = -1;
					if(mGR.mMode == 0) {
						for (int i = 0; i < 3; i++){
							if (CircRectsOverlap(mGR.mFrames.get(mGR.mImgSel).rgb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									screen2worldX(event.getX()),screen2worldY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}else if(mGR.mEdit.size() > 0){
						for (int i = 0; i < 3; i++){
							int work = mGR.mEdit.size()-1;
							if (CircRectsOverlap(mGR.mEdit.get(work).rgb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									screen2worldX(event.getX()),screen2worldY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}
					break;
				case 5:
					rgbsel = -1;
					 {
						for (int i = 0; i < 3; i++){
							if (CircRectsOverlap(mGR.mTex_Frame.get(0).rgb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									screen2worldX(event.getX()),screen2worldY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}
					break;
				case 6:
					for(int i =0;i<2;i++){
						if(CircRectsOverlap(0.47f-.188f*i, -.46f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
							Sel =60+i;
							forSave = 60;
							mGR.action =0;
//							if(Sel == 60){
//								M.AppScreen = M.APPMAIN;
//							}
							if(Sel == 61){
								M.AppScreen = M.ADDTEXT;
//								mGR.mFrames.get(mGR.mImgSel).ang-=90f;
							}
						}
					}
					break;
				}
				for(int i =0;i<7;i++){
					if(CircRectsOverlap(-.48f+.162f*i,-.69f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
						Sel =6+i;
						switch (Sel) {
						case 6:
							mGR.action = 4;
							break;
						case 7:
							mGR.action = 5;
							break;
						case 8:
							mGR.action = 0;
							break;
						case 9:
							mGR.action = 3;
							break;
						case 10:
							if(mGR.mFrames.get(mGR.mImgSel).mTexture!=null && mGR.mMode == 0){
								mGR.mFrames.get(mGR.mImgSel).mTexture.loadBitmap(mGR.FlipHorizontal(mGR.mFrames.get(mGR.mImgSel).mTexture.getBitmap()));
							}else if(mGR.mEdit.size()>0 && mGR.mMode == 1){
								mGR.mEdit.get(mGR.mEdit.size()-1).mTexture.loadBitmap(mGR.FlipHorizontal(mGR.mEdit.get(mGR.mEdit.size()-1).mTexture.getBitmap()));
							}
							break;
						case 11:
							if(mGR.mFrames.get(mGR.mImgSel).mTexture!=null && mGR.mMode == 0){
								mGR.mFrames.get(mGR.mImgSel).mTexture.loadBitmap(mGR.FlipVerticle(mGR.mFrames.get(mGR.mImgSel).mTexture.getBitmap()));
							}else if(mGR.mEdit.size()>0 && mGR.mMode == 1){
								mGR.mEdit.get(mGR.mEdit.size()-1).mTexture.loadBitmap(mGR.FlipVerticle(mGR.mEdit.get(mGR.mEdit.size()-1).mTexture.getBitmap()));
							}
							break;
						case 12:
							mGR.action = 6;
//							M.AppScreen = M.APPMAIN;
							break;
						}
					}
				}
			}
			if(CircRectsOverlap(-.45f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =15;
				mGR.mMode = 0;
			}
			if(CircRectsOverlap(-.20f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =16;
				mGR.mMode = 1;
			}
			if(CircRectsOverlap(0.30f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				
				forSave = 17;
//				Sel =17;
				/*{
					if(!screenshot){
						mGR.isShare =true;
						mGR.PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
						screenshot = true;
					}
				}*/
				
			}
			if(CircRectsOverlap(0.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				forSave = 18;
//				Sel =18;
				/*{
					if(!screenshot){
						mGR.PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
						screenshot = true;
						System.out.println(mGR.PhotoPath+"    Share");
					}
					
				}*/
			}
			if(CircRectsOverlap(0.12f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				forSave = 19;
			}
		}
		if(mGR.mFrames.size()>0 && Action)
			if(mGR.mMode == 0){
				TouchMove(event);
			}else{
				TouchEdit(event);
			}
		
		
		
//		System.out.println(screen2worldX(event.getX())+"~~~~~~~~~~~~~~~~~~"+screen2worldY(event.getY())+"   "+Sel);
		if(MotionEvent.ACTION_UP ==event.getAction()){
//			if(CircRectsOverlap(0.30f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				if(forSave == 17)
				{
					if(!screenshot){
						mGR.isShare =true;
//						mGR.PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
						screenshot = true;
					}
				}
				
			}
//			if(CircRectsOverlap(0.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				if(forSave == 18)
				{
					if(!screenshot){
//						mGR.PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
						screenshot = true;
						System.out.println(mGR.mFrameWay+"    Share");
					}
					
				}
			}
//			if(CircRectsOverlap(0.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f))
			{
				if(forSave == 19 && mGR.mMode == 1 && mGR.mEdit.size()>0){
					mGR.mEdit.remove(mGR.mEdit.size()-1);
				}
			}
			if(forSave == 60)
			{
				M.AppScreen = M.APPMAIN;
			}
			forSave =0;
			Sel = 0;
			
		}
		
		return true;
	}
	
	boolean Action = false;
	//Action 4 == RBG
	float oldDist;
	float oldAng;
	float mode = 0;
	float mmode = 0;
	float scale = 1;
	float cx,cy;
	int rgbsel = -1;
    public boolean TouchMove( MotionEvent event) 
    {
    	
    	int work = mGR.mImgSel;//mGR.mFrames.size()-1;
//    	if(work<0)
//    		return false;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = screen2worldX(event.getX());
			cy = screen2worldY(event.getY());
			break;
		case MotionEvent.ACTION_UP: // first finger lifted
			 mode = 0;
			 mmode = 0;
			 mGR.mFrames.get(work).lsx = mGR.mFrames.get(work).lsx*mGR.mFrames.get(work).sx;
			 mGR.mFrames.get(work).lsy = mGR.mFrames.get(work).lsy*mGR.mFrames.get(work).sy;
			 mGR.mFrames.get(work).sx = mGR.mFrames.get(work).sy = 1;
			 mGR.mFrames.get(work).ang +=mGR.mFrames.get(work).langle;
			 mGR.mFrames.get(work).langle = 0;
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
				if (mGR.action == 0) //Scal
				{
					scale = (newDist / oldDist);
					mGR.mFrames.get(work).sy=mGR.mFrames.get(work).sx=scale;
				}
				if (mGR.action == 1) 
				{
					scale = (newDist / oldDist);
					mGR.mFrames.get(work).sx=scale;
				}
				if (mGR.action == 2) 
				{
					scale = (newDist / oldDist);
					mGR.mFrames.get(work).sy=scale;
				}
				if (mGR.action == 3){
					float  newAng = Angle(event);
					mGR.mFrames.get(work).langle = (float)Math.toDegrees(oldAng-newAng);
				}
				
			} else if (mmode == 1) {
				if (mGR.action == 4 && rgbsel>=0 ) {
					if(screen2worldX(event.getX()) > -.4f && screen2worldX(event.getX()) < 0.4f){
						mGR.mFrames.get(work).rgb[rgbsel] =screen2worldX(event.getX());
					}
				} else if (mGR.action == 5 && rgbsel>=0 ) {
					if(screen2worldX(event.getX()) > -.4f && screen2worldX(event.getX()) < 0.4f){
						mGR.mTex_Frame.get(0).rgb[rgbsel] =screen2worldX(event.getX());
					}
				} else if(mGR.mFrames.get(work).mTexture !=null){
					mGR.mFrames.get(work).x += -cx + screen2worldX(event.getX());//Uscreen2worldX(event.getX())- Uscreen2worldX(cx);
					mGR.mFrames.get(work).y += -cy + screen2worldY(event.getY());//Uscreen2worldY(event.getY())- Uscreen2worldY(cy);
					cx = screen2worldX(event.getX());
					cy = screen2worldY(event.getY());
				}
			}
			break;
		}
        return true; // indicate event was handled
    }
	
    
    
    
    
    
    public boolean TouchEdit( MotionEvent event) 
    {
    	
    	int work = mGR.mEdit.size()-1;
    	if(work<0)
    		return false;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = screen2worldX(event.getX());
			cy = screen2worldY(event.getY());
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
				if (mGR.action == 4 && rgbsel>=0 ) {
					if(screen2worldX(event.getX()) > -.4f && screen2worldX(event.getX()) < 0.4f){
						mGR.mEdit.get(work).rgb[rgbsel] =screen2worldX(event.getX());
					}
				} else if (mGR.action == 5 && rgbsel>=0 ) {
					if(screen2worldX(event.getX()) > -.4f && screen2worldX(event.getX()) < 0.4f){
						mGR.mTex_Frame.get(0).rgb[rgbsel] =screen2worldX(event.getX());
					}
				} else {
					mGR.mEdit.get(work).x += -cx + screen2worldX(event.getX());//Uscreen2worldX(event.getX())- Uscreen2worldX(cx);
					mGR.mEdit.get(work).y += -cy + screen2worldY(event.getY());//Uscreen2worldY(event.getY())- Uscreen2worldY(cy);
					cx = screen2worldX(event.getX());
					cy = screen2worldY(event.getY());
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
	
	
    /*************************Gallery Sub Start**************************************/
	float move,movevx =0;
	float moveR = 0;
	boolean isMove = false;
	void Draw_GallerySub(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bar, 0, .893f);
		DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
//		newset(gl);
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3), .60f-.35f*(i/3),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][mGR.page*12 +i],move-.36f+.36f*(i%3), .60f-.35f*(i/3),
					mGR.LibSel<2?.5f:1,Sel==(i+1)?0.5f:1);
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
						mGR.LibSel<2?.5f:1,Sel==(i+1)?0.5f:1);
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
			DrawTransScal(gl, mGR.mTex_Arrow[0],-.3f, -.72f,Sel==(101)?1.1f:1,Sel==(101)?0.5f:1);
		
		if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length)
			DrawTransScal(gl, mGR.mTex_Arrow[1],0.3f, -.72f,Sel==(102)?1.1f:1,Sel==(102)?0.5f:1);
	}
	boolean Handle_GallerySub(MotionEvent event){
		Sel =0;
		
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			if(CircRectsOverlap(-.36f+.36f*(i%3), .60f-.35f*(i/3), mGR.mTex_SubICN.width()*.3f, mGR.mTex_SubICN.Height()*.2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel = i+1;
			}
		}
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =100;
		}
		
		if(CircRectsOverlap(-.3f,-.72f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =101;
		}
		
		if(CircRectsOverlap(0.3f,-.72f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =102;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()){
			moveR = screen2worldX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()){
			move =  screen2worldX(event.getX())-moveR;
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
					M.AppScreen = M.APPMAIN;
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
						Edit tempe = new Edit(mGR.mTex_Library[mGR.LibSel][mGR.LibSubSel]);
						mGR.mEdit.add(tempe);
						M.AppScreen = M.APPPLAY;
//						mGR.clear();
					}
					break;
				}
			}
			Sel = 0;
		}
		return true;
	}
	/*************************GallerySub End****************************************/
	/*************************Gallery Main Start**************************************/
	void Draw_GalleryMain(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Bar, 0, .893f);
//		newset(gl);
		
		DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		
		for (int i = 0; i < 20; i++) {
			DrawTransScal(gl, mGR.mTex_MainICN[0]	,-.400f+.271f*(i%4), .610f-.271f*(i/4),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[i][0],-.400f+.271f*(i%4), .610f-.271f*(i/4),i<2?.4f:.7f,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_MainICN[1]	,-.305f+.271f*(i%4), .515f-.271f*(i/4),1,1);
		}
		
	}
	boolean Handle_GalleryMain(MotionEvent event){
		Sel =0;
		for(int i=0;i<20 ;i++){
			if(CircRectsOverlap(-.400f+.271f*(i%4), .610f-.271f*(i/4), mGR.mTex_MainICN[0].width()*.2f, 
					mGR.mTex_MainICN[0].Height()*.2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
				Sel =1+i;
			}
		}
		
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Effect.width()*.2f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =100;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				M.AppScreen = M.APPPLAY;
				break;
			default:
				mGR.LibSel = Sel-1;
				M.AppScreen = M.APPSUB;
				mGR.page = 0;
				break;
			
			}
			Sel = 0;
		}
		return true;
	}
	/*************************GalleryMain End****************************************/
	void Draw_AddText(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
//		newset(gl);
		DrawTexture(gl, mGR.mTex_Bar, 0, .56f);
		DrawTransScal(gl, mGR.mTex_Cross , -.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Ok , 0.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		if(!GameRenderer.mStart.isTxtVisible()){
			try {
				GameRenderer.mStart.Txthandler.sendEmptyMessage(View.VISIBLE);
			} catch (Exception e) {
			}
		}
		
	}
	boolean Handle_AddText(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.48f, .56f, mGR.mTex_Effect.width()*.3f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =100;
		}
		if(CircRectsOverlap(0.48f, .56f, mGR.mTex_Effect.width()*.3f, mGR.mTex_Effect.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .01f)){
			Sel =101;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				GameRenderer.mStart.hideKey();
				M.AppScreen = M.APPPLAY;
				break;
			default:
				mGR.mCanvas.drawColor(0, Mode.CLEAR);
				mGR.mCanvas.drawText(GameRenderer.mStart.getAddTxt(), 256,32, mGR.mPaint);
				Edit tempe = new Edit(mGR.addBitmap(mGR.mBitmap));
				mGR.mEdit.add(tempe);
				M.AppScreen = M.APPPLAY;
				break;
			}
			try {
				GameRenderer.mStart.Txthandler.sendEmptyMessage(View.GONE);
			} catch (Exception e) {
			}
			Sel = 0;
		}
		return true;
	}
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
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

	float screen2worldX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	int world2screenx(float a)
	{
		int c = (int)(.5f*((a*M.ScreenHieght)+M.ScreenWidth));
		return c;
	}
	
	int world2screenY(float a)
	{
		int c = (int)((.5f-(a / 2f))*(M.ScreenHieght));
		return c;
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
	float sw,sx,sy=.2f,sz=.2f;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.AppScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.5f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.5f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
	void newset(GL10 gl){
		float ud=.01f;
		for(int i = 0 ;i<8;i++)
			DrawTransScal(gl, mGR.mTex_BackBtn , -.45f +.3f*(i%4),((i/4)==0?1:-1)*.89f,GameRenderer.mStart._keyCode == 1+i ?1.1f:1,GameRenderer.mStart._keyCode == 1+i ?0.5f:1);
		
		
		for(int i = 0 ;i<8;i++)
			DrawTransScal(gl, mGR.mTex_BackBtn , -.45f +.3f*(i%4),((i/4)==0?1:-1)*.69f,GameRenderer.mStart._keyCode == 9+i ?1.1f:1,GameRenderer.mStart._keyCode == 9+i ?0.5f:1);
		
		switch (GameRenderer.mStart._keyCode) {
		case 1:
			sw += ud;
			break;
		case 2:
			sx += ud;
			break;
		case 3:
			sy += ud;
			break;
		case 4:
			sz += ud;
			break;
		case 5:
			sw -= ud;
			break;
		case 6:
			sx -= ud;
			break;
		case 7:
			sy -= ud;
			break;
		case 8:
			sz -= ud;
			break;
			
		case 9:
			sw += ud*.1f;
			break;
		case 10:
			sx += ud*.1f;
			break;
		case 11:
			sy += ud*.1f;
			break;
		case 12:
			sz += ud*.1f;
			break;
		case 13:
			sw -= ud*.1f;
			break;
		case 14:
			sx -= ud*.1f;
			break;
		case 15:
			sy -= ud*.1f;
			break;
		case 16:
			sz -= ud*.1f;
			break;
		}
		System.out.println(mGR.mFramImg+" > "+sw +" ~ " + sx+" ~ " + sy+" ~ "+sz+" >  {"+(int)(sw*1000) +"," + (int)(sx*1000)+"," + (int)(sy*1000)+","+(int)(sz*1000)+"},");
		
//		String str = "{"+-530,-260,530,240},//30 //4 
		
	}
	public boolean newHandle(MotionEvent event){
		for(int i = 0 ;i<8;i++)
			if (CircRectsOverlap(-.45f + .3f * (i % 4),((i / 4) == 0 ? 1 : -1) * .89f, .1f, .1f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),.1f))
				GameRenderer.mStart._keyCode = i+1;
		
		for(int i = 0 ;i<8;i++)
			if (CircRectsOverlap(-.45f + .3f * (i % 4),((i / 4) == 0 ? 1 : -1) * .65f, .1f, .1f,
					screen2worldX(event.getX()), screen2worldY(event.getY()),.1f))
				GameRenderer.mStart._keyCode = i+9;
		
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}

	
	void shareImage() {
		String extr = Environment.getExternalStorageDirectory().toString();
		File myPath = new File(extr, mGR.mFrameWay);
		if (!myPath.exists()) {
			GameRenderer.mStart.SaveMassage();
		}
		String str = Environment.getExternalStorageDirectory() + mGR.mFrameWay;
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(str)));
		shareIntent.setType("image/png");
		GameRenderer.mContext.startActivity(Intent.createChooser(shareIntent, "Where?"));
		mGR.isShare = false;
	}
	
	
	void SaveScreen(GL10 gl){
		if(!screenshot)
			return;
		for(int i =0;i<mGR.mFrames.size();i++){
			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor((int)(mGR.mFrames.get(i).rectX),(int)(mGR.mFrames.get(i).rectY),(int)mGR.mFrames.get(i).rectDx,(int)mGR.mFrames.get(i).rectDy);
			if(mGR.mFrames.get(i).mTexture!=null){
				mGR.mFrames.get(i).mTexture.drawAll(gl, mGR.mFrames.get(i).x, mGR.mFrames.get(i).y, 
						mGR.mFrames.get(i).ang + mGR.mFrames.get(i).langle,
						mGR.mFrames.get(i).lsx * mGR.mFrames.get(i).sx,
						mGR.mFrames.get(i).lsy * mGR.mFrames.get(i).sy,
						mGR.mFrames.get(i).rgb);
			}
			
			gl.glDisable(GL10.GL_SCISSOR_TEST);	
		}
		for(int i =0;i<mGR.mTex_Frame.size();i++){
			mGR.mTex_Frame.get(i).mTex.drawAll(gl, mGR.mTex_Frame.get(i).x, mGR.mTex_Frame.get(i).y, 
					0,2,2,mGR.mTex_Frame.get(i).rgb);
			
		}
		for(int i =0;i<mGR.mEdit.size();i++){
			if(mGR.mEdit.get(i).mTexture!=null){
				mGR.mEdit.get(i).mTexture.drawAll(gl, mGR.mEdit.get(i).x, mGR.mEdit.get(i).y, 
						mGR.mEdit.get(i).angle + mGR.mEdit.get(i).langle,
						mGR.mEdit.get(i).lsx * mGR.mEdit.get(i).sx,
						mGR.mEdit.get(i).lsy * mGR.mEdit.get(i).sy,
						mGR.mEdit.get(i).rgb);
			}
		}
		
		
        Bitmap bb = getNew(gl);
        Bitmap b = ImageCut(bb);
        System.out.println(mGR.mFrameWay+"    Share 5");
        String extr = Environment.getExternalStorageDirectory().toString();
        
        File folder = new File(Environment.getExternalStorageDirectory() + M.DIR);
        if(!folder.exists())
        {
            folder.mkdir();
        }  
        
        File myPath = new File(extr, mGR.mFrameWay);
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
        if(mGR.isMenu){
        	M.AppScreen =M.APPMENU;
        	mGR.isMenu = false;
        }
        	
        System.out.println(mGR.mFrameWay+"    Share 2");
//        GameRenderer.mStart.finish();
    }
    boolean screenshot = false;
    Bitmap getNew(GL10 gl)
    {
    	int width = (int)M.ScreenWidth;
    	int height = (int)(M.ScreenHieght);
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
    Bitmap ImageCut(Bitmap ybitmap)
    {
    	int x = -world2screenx(-.535f); //(int)(-M.ScreenWidth	 *.06);
    	int y = -world2screenY(0.750f);  //(int)(-M.ScreenHieght*.21);
    	int dx=(int)(world2screenx(0.535f)-world2screenx(-.535f));//(M.ScreenWidth  *.88);
    	int dy=(int)(M.ScreenHieght*.54f);//(M.ScreenHieght *.58);
    	
		System.out.println(x + "  ~~  " + y + "    " + dx + "    " + dy);
    	
//    	y = x = 0;
//    	dx = (int)M.ScreenWidth;
//    	dx = (int)M.ScreenWidth;
    	Bitmap image = Bitmap.createBitmap(dx,dy, Bitmap.Config.ARGB_8888);
//        image.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(image);
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(ybitmap, x, y, null);
        return image;
    }
	
	
}
