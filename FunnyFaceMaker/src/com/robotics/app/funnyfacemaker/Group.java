package com.robotics.app.funnyfacemaker;

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
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {

	GameRenderer mGR = null;
	int last4;
	int Counter = 0;
	float sx, sy;
	int sel =0;
	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
		Draw_Game(gl);
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {
		if (M.GameScreen == M.GAMEPLAY) {
			Handle_Crop(event);
		} else {
			mGR.mClipArt.TouchEvent(event);
		}

//		Handle(event);
		return true;
	}
	int yimg = 0;
	int forSave = 0;
	private float rgb[] = {.35f,.35f,.35f};
	public void Draw_Game(GL10 gl){
		yimg = GameRenderer.mEditImg.size() - 1;
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		mGR.mTex_Box.drawScal(gl, 0, .12f, .94f, 1.15f);
		int _x =w2sX(-.55f);
		int _y =w2sY(.56f);
		int _dx =(int) (M.ScreenHieght * (.55f));
		int _dy =(int) (M.ScreenHieght * (.68f));
		
		gl.glEnable(GL10.GL_SCISSOR_TEST);
		gl.glScissor(_x,_y,_dx,_dy);
		
		for(int i =0;i<GameRenderer.mEditImg.size();i++){
			GameRenderer.mEditImg.get(i).tex.drawAll(gl, GameRenderer.mEditImg.get(i).x, GameRenderer.mEditImg.get(i).y, 
					GameRenderer.mEditImg.get(i).kon + GameRenderer.mEditImg.get(i).lkon,
					GameRenderer.mEditImg.get(i).lsx * GameRenderer.mEditImg.get(i).sx,
					GameRenderer.mEditImg.get(i).lsy * GameRenderer.mEditImg.get(i).sy,
					GameRenderer.mEditImg.get(i).rgb);
		
		}
		for(int i =0;i<mGR.mGellary.size();i++){
			if(mGR.mGellary.get(i).mTexture!=null){
				mGR.mGellary.get(i).mTexture.drawAll(gl, mGR.mGellary.get(i).movx, mGR.mGellary.get(i).movy, 
						mGR.mGellary.get(i).Eangle + mGR.mGellary.get(i).Elangle,
						mGR.mGellary.get(i).lex * mGR.mGellary.get(i).ex,
						mGR.mGellary.get(i).ley * mGR.mGellary.get(i).ey,
						mGR.mGellary.get(i).Ergb);
			}
		}
		gl.glDisable(GL10.GL_SCISSOR_TEST);
		
		
		
		
		
		
		if (GameRenderer.mEditImg.size() > 0) {
			
			DrawTransScal(gl, mGR.mTex_Mod[mGR.mMode==0?0:1], -.48f, .9f,sel == 15 ?1.1f:1,sel == 15 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Mod[mGR.mMode==0?3:2], -.23f, .9f,sel == 16 ?1.1f:1,sel == 16 ?0.5f:1);
			
			
			DrawTransScal(gl, mGR.mTex_undo , -.01f, .9f,forSave == 19 ?1.1f:1,forSave == 19 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_share, 0.16f, .9f,forSave == 17 ?1.1f:1,forSave == 17 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_save , 0.33f, .9f,forSave == 18 ?1.1f:1,forSave == 18 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_back , 0.50f, .9f,forSave == 20 ?1.1f:1,forSave == 20 ?0.5f:1);
			
			DrawTransScal(gl, mGR.mTex_effect, -.45f + .18f * 0, -.90f,last4 == 0 ? 1.1f : 1, last4 == 0 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_zoom, -.45f + .18f * 1, -.90f,last4 == 1 ? 1.1f : 1, last4 == 1 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_rotate, -.45f + .18f * 2, -.90f,last4 == 2 ? 1.1f : 1, last4 == 2 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_flipH, -.45f + .18f * 3, -.90f,last4 == 3 ? 1.1f : 1, last4 == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_flipV, -.45f + .18f * 4, -.90f,last4 == 4 ? 1.1f : 1, last4 == 4 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_more, -.45f + .18f * 5, -.90f,last4 == 5 ? 1.1f : 1, last4 == 5 ? 0.5f : 1);

			switch (last4) {
			case 0: {
				if(mGR.mMode == 0){
					for (int i = 0; i < 3; i++) {
						rgb[0] = rgb[1] = rgb[2] = -.4f;
						rgb[i] = .4f;
						mGR.mTex_RGBBar.drawAll(gl, 0, -.55f - .08f * i, 0, 11,1.2f, rgb);
						rgb[i] = GameRenderer.mEditImg.get(yimg).rgb[i];
						mGR.mTex_RGBBar.drawAll(gl, 0, -.55f - .08f * i, 0, 11,0.8f, rgb);
						DrawTexture(gl, mGR.mTex_RGBSel,GameRenderer.mEditImg.get(yimg).rgb[i], -.55f- .08f * i);
					}
				}else if(mGR.mGellary.size()>0){
					for(int i=0;i<3;i++){
						rgb[0] = rgb[1] =rgb[2]= -.4f;
						rgb[i] = .4f;
						mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
						rgb[i] = mGR.mGellary.get(mGR.mGellary.size()-1).Ergb[i];
						mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
						DrawTexture(gl, mGR.mTex_RGBSel, mGR.mGellary.get(mGR.mGellary.size()-1).Ergb[i], -.35f-.08f*i);
					}
				}
			}
				break;
			case 1:
				for (int i = 0; i < 6; i++) {
					DrawTransScal(gl, mGR.mTex_z[i], -.45f + .188f * i, -.65f,sel == 20 + i ? 1.1f : 1, sel == 20 + i ? 0.5f : 1);
				}
				switch (sel) {
				case 20:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsx += .01f;
						GameRenderer.mEditImg.get(yimg).lsy += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex += .01f;
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley += .01f;
					}
					break;
				case 21:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsx -= .01f;
						GameRenderer.mEditImg.get(yimg).lsy -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex -= .01f;
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley -= .01f;
					}
					break;
				case 22:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsx += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex += .01f;
					}
					break;
				case 23:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsx -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex -= .01f;
					}
					break;
				case 24:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsy += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley += .01f;
					}
					break;
				case 25:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).lsy -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley -= .01f;
					}
					break;
				}
				break;
			case 2:
				for (int i = 0; i < 4; i++) {
					DrawTransScal(gl, mGR.mTex_r[i], -.45f + .188f * i, -.65f,sel == 30 + i ? 1.1f : 1, sel == 30 + i ? 0.5f : 1);
				}
				switch (sel) {
				case 30:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).kon += 1f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle += 1f;
					}
					break;
				case 31:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).kon -= 1f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle -= 1f;
					}
					break;
				}
				break;
			case 5:
				DrawTransScal(gl, mGR.mTex_text, 0.45f - .188f * 1, -.65f,sel == 61 ? 1.1f : 1, sel == 61 ? 0.5f : 1);
				DrawTransScal(gl, mGR.mTex_more, 0.45f - .188f * 0, -.65f,sel == 60 ? 1.1f : 1, sel == 60 ? 0.5f : 1);
				break;
			}

		}
		
		/*if(GameRenderer.mEditImg.size()<2) */{
			if(GameRenderer.mEditImg.size()==0 || mGR.showPopup) {
				mGR.mTex_BG.drawDark(gl, 0, 0);
				DrawTexture(gl, mGR.mTex_selectPhoto	, 0, 0);
				DrawTransScal(gl, mGR.mTex_cross	, .506f, 0.196f,sel == 70 ?1.1f:1,sel == 70 ?0.5f:1);
				DrawTransScal(gl, mGR.mTex_camera	,-.270f, -.080f,sel == 71 ?1.1f:1,sel == 71 ?0.5f:1);
				DrawTransScal(gl, mGR.mTex_file		, .270f, -.080f,sel == 72 ?1.1f:1,sel == 72 ?0.5f:1);
			}else {
				DrawTransScal(gl, mGR.mTex_Play,-.48f,0.65f,sel == 1 ?1.1f:1,sel == 1 ?0.5f:1);
			}
		}
		
		
		if(isRemove && mGR.mMode == 0 && GameRenderer.mEditImg.size()>0){
			GameRenderer.mEditImg.remove(GameRenderer.mEditImg.size()-1);
			isRemove = false;
		}
		if(screenshot)
			SaveScreen(gl);
	}
	private boolean Action = false;
	boolean Handle_Crop(MotionEvent event) {
		sel =0;
		if(event.getAction() == MotionEvent.ACTION_DOWN /*&& GameRenderer.mEditImg.size() < 2*/){
			if(GameRenderer.mEditImg.size() == 0 ||  mGR.showPopup){
				if(CircRectsOverlap(.506f, 0.196f, mGR.mTex_Play.width()*.5f, mGR.mTex_Play.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
					sel =70;
					if(GameRenderer.mEditImg.size() == 0)
						M.GameScreen = M.GAMEMENU;
					else
						mGR.showPopup =false;
				}
				if(CircRectsOverlap(-.270f, -.080f, mGR.mTex_Play.width()*.5f, mGR.mTex_Play.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
					sel =71;
					mGR.showPopup =false;
					GameRenderer.mStart.takePhoto();
				}
				if(CircRectsOverlap(0.270f, -.080f, mGR.mTex_Play.width()*.5f, mGR.mTex_Play.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
					sel =72;
					mGR.showPopup =false;
					openFolder();
				}
			}
			if(GameRenderer.mEditImg.size() > 0){
				if(CircRectsOverlap(-.48f ,0.65f, mGR.mTex_Play.width()*.5f, mGR.mTex_Play.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
					mGR.showPopup =true;
					sel =1;
				}
			}
		}
		
		if(GameRenderer.mEditImg.size() > 0 && event.getAction() == MotionEvent.ACTION_DOWN){
			Action = false;
			for(int i =0;i<6;i++){
				if(CircRectsOverlap(-.45f + .18f * i, -.90f, mGR.mTex_zoom.width()*.25f, mGR.mTex_zoom.Height()*.25f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
					if(i == 3){
						if(mGR.mMode == 0)
							GameRenderer.mEditImg.get(yimg).tex.loadBitmap(mGR.FlipHorizontal(GameRenderer.mEditImg.get(yimg).tex.getBitmap()));
						else{
							int work = mGR.mGellary.size()-1;
							if(work>=0)
								mGR.mGellary.get(work).mTexture.loadBitmap(mGR.FlipHorizontal(mGR.mGellary.get(work).mTexture.getBitmap()));
						}
					}
					else if(i == 4){
						if(mGR.mMode == 0)
							GameRenderer.mEditImg.get(yimg).tex.loadBitmap(mGR.FlipVerticle(GameRenderer.mEditImg.get(yimg).tex.getBitmap()));
						else{
							int work = mGR.mGellary.size()-1;
							if(work>=0)
								mGR.mGellary.get(work).mTexture.loadBitmap(mGR.FlipVerticle(mGR.mGellary.get(work).mTexture.getBitmap()));
						}
					}
						
					else{
						last4 = i;
					}
					
				}
			}
			switch (last4) {
			case 0: {
				if(mGR.mMode == 0) {
						for (int i = 0; i < 3; i++){
						if (CircRectsOverlap(GameRenderer.mEditImg.get(yimg).rgb[i],-.55f - .08f * i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
								s2wX(event.getX()),s2wY(event.getY()), .01)) {
							rgbsel = i;
							Action = true;
						}
					}
				}else if(mGR.mGellary.size() > 0){
					for (int i = 0; i < 3; i++){
						int work = mGR.mGellary.size()-1;
						if (CircRectsOverlap(mGR.mGellary.get(work).Ergb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
								s2wX(event.getX()),s2wY(event.getY()), .01)) {
							rgbsel = i;
							Action = true;
						}
					}
				}
			}
				break;
			case 1:
				for (int i = 0; i < 6; i++) {
					if(CircRectsOverlap(-.45f + .188f * i, -.65f, mGR.mTex_z[0].width()*.5f, mGR.mTex_z[0].Height()*.4f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
						sel = 20+i;
					}
				}
				break;
			case 2:
				for (int i = 0; i < 4; i++) {
					if(CircRectsOverlap(-.45f + .188f * i, -.65f, mGR.mTex_z[0].width()*.5f, mGR.mTex_z[0].Height()*.4f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
						sel = 30+i;
					}
				}
				switch (sel) {
				case 32:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).kon += 90f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle += 90f;
					}
					
					break;
				case 33:
					if (mGR.mMode == 0) {
						GameRenderer.mEditImg.get(yimg).kon -= 90f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle -= 90f;
					}
				
					break;
				}
				break;
			case 5:
				for(int i =0;i<2;i++){
					if(CircRectsOverlap(0.45f - .188f * i, -.65f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
						sel =60+i;
						forSave = 60;
						last4 =1;
						if(sel == 61){
							M.GameScreen = M.GAMETEXT;
						}
					}
				}
				break;
			}
			if(CircRectsOverlap(0, 0, .5f, .5f, s2wX(event.getX()), s2wY(event.getY()), .02f)){
				Action =true;
			}
			if(CircRectsOverlap(-.49f, .89f, mGR.mTex_effect.width()*.2f, mGR.mTex_effect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				sel =15;
				mGR.mMode = 0;
			}
			if(CircRectsOverlap(-.20f, .89f, mGR.mTex_back.width()*.45f, mGR.mTex_back.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				sel =16;
				mGR.mMode = 1;
			}
			if(CircRectsOverlap(0.16f, .9f, mGR.mTex_back.width()*.45f, mGR.mTex_back.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 17;
			}
			if(CircRectsOverlap(0.33f, .9f, mGR.mTex_back.width()*.45f, mGR.mTex_back.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 18;
			}
			if(CircRectsOverlap(-.01f, .9f, mGR.mTex_back.width()*.45f, mGR.mTex_back.Height()*.5f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 19;
			}
			if(CircRectsOverlap(0.51f, .9f, mGR.mTex_back.width()*.45f, mGR.mTex_back.Height()*.6f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 20;
			}
			
			
			
		}
		
		if(GameRenderer.mEditImg.size()>0 && Action){
			if(mGR.mMode == 0){
				TouchMove(event);
			}else{
				TouchEdit(event);
			}
	}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (sel) {
			
			/*case 70:
				break;
			case 71:
				GameRenderer.mStart.op
				break;
			case 72:
				openFolder();
				break;
			
			case 1:
				mGR.showPopup = true;
				break;*/
			}
			if(forSave == 60)
			{
				M.GameScreen = M.APPMAIN;
			}
			if(!screenshot && forSave == 17){
				mGR.isShare =true;
				screenshot = true;
			}
			if(!screenshot && forSave == 18)
				screenshot = true;
			
			if(forSave == 19)
			{
				if(mGR.mMode == 1 && mGR.mGellary.size()>0){
					mGR.mGellary.remove(mGR.mGellary.size()-1);
				}else if(mGR.mMode == 0 && GameRenderer.mEditImg.size()>0){
					isRemove = true;
				}
			}
			if(forSave == 20)
				GameRenderer.mStart.dOyou();
			
			
			forSave =0;
			sel =0;	
			
		}
		return true;
	}
	boolean isRemove = false;
	private float oldDist;
	private float oldAng;
	private float mode = 0;
	private float mmode = 0;
	private float scale = 1;
	private float cx,cy;
	private int rgbsel = -1;
	private  boolean TouchMove( MotionEvent event) 
    {
    	
//    	int work = yimg;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = s2wX(event.getX());
			cy = s2wY(event.getY());
			break;
		case MotionEvent.ACTION_UP: // first finger lifted
			 mode = 0;
			 mmode = 0;
			 GameRenderer.mEditImg.get(yimg).lsx = GameRenderer.mEditImg.get(yimg).lsx*GameRenderer.mEditImg.get(yimg).sx;
			 GameRenderer.mEditImg.get(yimg).lsy = GameRenderer.mEditImg.get(yimg).lsy*GameRenderer.mEditImg.get(yimg).sy;
			 GameRenderer.mEditImg.get(yimg).sx = GameRenderer.mEditImg.get(yimg).sy = 1;
			 GameRenderer.mEditImg.get(yimg).kon +=GameRenderer.mEditImg.get(yimg).lkon;
			 GameRenderer.mEditImg.get(yimg).lkon = 0;
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
				if (last4 == 1) //Scal
				{
					scale = (newDist / oldDist);
					GameRenderer.mEditImg.get(yimg).sy=GameRenderer.mEditImg.get(yimg).sx=scale;
				}
				/*if (last4 == 1) 
				{
					scale = (newDist / oldDist);
					GameRenderer.mEditImg.get(yimg).sx=scale;
				}
				if (last4 == 2) 
				{
					scale = (newDist / oldDist);
					GameRenderer.mEditImg.get(yimg).sy=scale;
				}*/
				if (last4 == 2){
					float  newAng = Angle(event);
					GameRenderer.mEditImg.get(yimg).lkon = (float)Math.toDegrees(oldAng-newAng);
				}
				
			} else if (mmode == 1) {
				if (last4 == 0 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						GameRenderer.mEditImg.get(yimg).rgb[rgbsel] =s2wX(event.getX());
					}
				} /*else if (mGR.action == 5 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						mGR.mTex_Frame.get(0).rgb[rgbsel] =s2wX(event.getX());
					}
				} */else if(GameRenderer.mEditImg.get(yimg).tex !=null){
					GameRenderer.mEditImg.get(yimg).x += -cx + s2wX(event.getX());
					GameRenderer.mEditImg.get(yimg).y += -cy + s2wY(event.getY());
					cx = s2wX(event.getX());
					cy = s2wY(event.getY());
				}
			}
			break;
		}
        return true; 
    }
	 private boolean TouchEdit( MotionEvent event) 
	    {
	    	
	    	int work = mGR.mGellary.size()-1;
	    	if(work<0)
	    		return false;
	    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
	    	{
			case MotionEvent.ACTION_DOWN:
				mode = 0;
				mmode = 1;
				cx = s2wX(event.getX());
				cy = s2wY(event.getY());
				break;
			case MotionEvent.ACTION_UP: 
				 mode = 0;
				 mmode = 0;
				 mGR.mGellary.get(work).lex = mGR.mGellary.get(work).lex*mGR.mGellary.get(work).ex;
				 mGR.mGellary.get(work).ley = mGR.mGellary.get(work).ley*mGR.mGellary.get(work).ey;
				 mGR.mGellary.get(work).ex = mGR.mGellary.get(work).ey = 1;
				 mGR.mGellary.get(work).Eangle +=mGR.mGellary.get(work).Elangle;
				 mGR.mGellary.get(work).Elangle = 0;
				break;
			case MotionEvent.ACTION_POINTER_UP: 
				 mode = 0;
				 mmode = 0;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = 1;
				mmode = 0;
				oldDist = spacing(event);
				oldAng = Angle(event);
				break;
			case MotionEvent.ACTION_MOVE:
				if(mode == 1)
				{
					float newDist = spacing(event);
					if (last4 == 1) 
					{
						scale = (newDist / oldDist);
						mGR.mGellary.get(work).ey=mGR.mGellary.get(work).ex=scale;
					}
					/*if (mGR.action == 1) 
					{
						scale = (newDist / oldDist);
						mGR.mGellary.get(work).ex=scale;
					}
					if (mGR.action == 2) 
					{
						scale = (newDist / oldDist);
						mGR.mGellary.get(work).ey=scale;
					}*/
					if (last4 == 2){
						float  newAng = Angle(event);
						mGR.mGellary.get(work).Elangle = (float)Math.toDegrees(oldAng-newAng);
					}
					
				} else if (mmode == 1) {
					if (last4 == 0 && rgbsel>=0 ) {
						if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
							mGR.mGellary.get(work).Ergb[rgbsel] =s2wX(event.getX());
						}
					} /*else if (mGR.action == 5 && rgbsel>=0 ) {
						if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
							mGR.mTex_Frame.get(0).rgb[rgbsel] =s2wX(event.getX());
						}
					} */else {
						mGR.mGellary.get(work).movx += -cx + s2wX(event.getX());
						mGR.mGellary.get(work).movy += -cy + s2wY(event.getY());
						cx = s2wX(event.getX());
						cy = s2wY(event.getY());
					}
				}
				break;
			}
	        return true;
	    }
	 private float spacing(MotionEvent event)
		{
	    	int count =  MotionEventCompat.getPointerCount(event);
	    	if(count<2){
	    		return 0;
	    	}
	        float x = s2wX(event.getX(0)) - s2wX(event.getX(1));
	        float y = s2wY(event.getY(0)) - s2wY(event.getY(1));
	        return FloatMath.sqrt(x * x + y * y);
	    }
	    private float Angle(MotionEvent event)
		{
	    	int count =  MotionEventCompat.getPointerCount(event);
	    	if(count<2){
	    		return 0;
	    	}
	        float x = s2wX(event.getX(0)) - s2wX(event.getX(1));
	        float y = s2wY(event.getY(0)) - s2wY(event.getY(1));
	        return (float)GetAngle(y, x);
	    }
	public void openFolder() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
		intent.setDataAndType(uri, "image/*");
		GameRenderer.mStart.startActivityForResult(intent, 1);// Gallery
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

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

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
	
	public int w2sX(float a)
	{
		int c = (int)(.5f*((a*M.ScreenHieght)+M.ScreenWidth));
		return c;
	}
	
	public int w2sY(float a)
	{
		int c = (int)((.5f-(a / 2f))*(M.ScreenHieght));
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
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK + getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	private void shareImage() {
		String extr = Environment.getExternalStorageDirectory().toString();
		File myPath = new File(extr, mGR.PhotoPath);
		if (!myPath.exists()) {
			GameRenderer.mStart.SaveMassage();
		}
		String str = Environment.getExternalStorageDirectory() + mGR.PhotoPath;
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(str)));
		shareIntent.setType("image/png");
		GameRenderer.mContext.startActivity(Intent.createChooser(shareIntent, "Where?"));
		mGR.isShare = false;
	}
	
	private Bitmap ImageCut(Bitmap ybitmap)
    {
    	int x = -w2sX(-.55f);
    	int y = -w2sY(0.8f);
    	int dx=(int)(w2sX(0.55f)-w2sX(-.55f));
    	int dy=(int)(M.ScreenHieght*.68f);
    	Bitmap image = Bitmap.createBitmap(dx,dy, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawBitmap(ybitmap, x, y, null);
        return image;
    }
	
	private Bitmap getNew(GL10 gl)
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
    
    private void SaveScreen(GL10 gl){
		if(!screenshot)
			return;
		for(int i =0;i<GameRenderer.mEditImg.size();i++){
			if(GameRenderer.mEditImg.get(i).tex!=null){
				GameRenderer.mEditImg.get(i).tex.drawAll(gl, GameRenderer.mEditImg.get(i).x, GameRenderer.mEditImg.get(i).y, 
						GameRenderer.mEditImg.get(i).kon + GameRenderer.mEditImg.get(i).lkon,
						GameRenderer.mEditImg.get(i).lsx * GameRenderer.mEditImg.get(i).sx,
						GameRenderer.mEditImg.get(i).lsy * GameRenderer.mEditImg.get(i).sy,
						GameRenderer.mEditImg.get(i).rgb);
			}
			
		}
		for(int i =0;i<mGR.mGellary.size();i++){
			if(mGR.mGellary.get(i).mTexture!=null){
				mGR.mGellary.get(i).mTexture.drawAll(gl, mGR.mGellary.get(i).movx, mGR.mGellary.get(i).movy, 
						mGR.mGellary.get(i).Eangle + mGR.mGellary.get(i).Elangle,
						mGR.mGellary.get(i).lex * mGR.mGellary.get(i).ex,
						mGR.mGellary.get(i).ley * mGR.mGellary.get(i).ey,
						mGR.mGellary.get(i).Ergb);
			}
		}
		System.out.println(mGR.PhotoPath+"  ~~~~~~~~~~~~~~~~1~~~~~~~~~~");
		
        Bitmap bb = getNew(gl);
        System.out.println(mGR.PhotoPath+"  ~~~~~~~~~~~~~~~2~~~~~~~~~~~"+bb);
        System.out.println(mGR.PhotoPath+"  ~~~~~~~~~~~~~~3~~~~~~~~~~~~"+bb+"  "+bb.getWidth()+"  "+bb.getHeight());
        Bitmap b = ImageCut(bb);
        System.out.println(mGR.PhotoPath+"  ~~~~~~~~~~~~~~4~~~~~~~~~~~~"+b+"  "+b.getWidth()+"  "+b.getHeight());
        String extr = Environment.getExternalStorageDirectory().toString();
        
        File folder = new File(Environment.getExternalStorageDirectory() + M.DIR);
        if(!folder.exists())
        {
            folder.mkdir();
        }  
        
        File myPath = new File(extr, mGR.PhotoPath);
        
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
            System.out.println(myPath.exists()+"~~~~~~~~~~~~~123~~~~~~~~~~~~~~~"+e.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(myPath.exists()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+e.toString());
        }
        if(mGR.isMenu){
        	M.GameScreen =M.GAMEMENU;
        	mGR.isMenu = false;
        }
        	
    }
    boolean screenshot = false;
	public void setting() {
		float ud = .01f;
		switch (GameRenderer.mStart._keyCode) {
		case 1:
			sy -= ud;
			break;
		case 2:
			sy += ud;
			break;
		case 3:
			sx -= ud;
			break;
		case 4:
			sx += ud;
			break;
		}
		System.out.println(M.GameScreen + "~~~~~~~~~~~~~~~      " + sx
				+ "~~~~~~~~~~~~       " + sy);
	}

	public boolean Handle(MotionEvent event) {
		if (CircRectsOverlap(-.4f, 0.0f, .1f, .1f, s2wX(event.getX()),
				s2wY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 3;
		if (CircRectsOverlap(0.4f, 0.0f, .1f, .1f, s2wX(event.getX()),
				s2wY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 4;
		if (CircRectsOverlap(-.0f, -.8f, .1f, .1f, s2wX(event.getX()),
				s2wY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 1;
		if (CircRectsOverlap(0.0f, 0.8f, .1f, .1f, s2wX(event.getX()),
				s2wY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 2;
		if (event.getAction() == MotionEvent.ACTION_UP)
			GameRenderer.mStart._keyCode = 0;
		return true;
	}

}
