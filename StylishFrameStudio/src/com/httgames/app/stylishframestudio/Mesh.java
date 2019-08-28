package com.httgames.app.stylishframestudio;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
public class Mesh 
{
	private boolean mShouldLoadTexture = false;
	private int mTextureId = -1;
	private int mNumOfIndices = -1;
	private Bitmap mBitmap;
	private FloatBuffer mVerticesBuffer = null;
	private FloatBuffer mTextureBuffer;
	private ShortBuffer mIndicesBuffer = null;
	
	public void drawDark(GL10 gl,float x,float y) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mShouldLoadTexture) {
			loadGLTexture(gl);
			mShouldLoadTexture = false;
		}
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
			 gl.glColor4f(0,0,0,.75f);
		}
		gl.glTranslatef(x, y, -2.41f);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	public void draw(GL10 gl) 
	{}
	public void drawAll(GL10 gl,float _x,float _y,float ang,float sx,float sy,float rgb[]){
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mShouldLoadTexture) {
			loadGLTexture(gl);
			mShouldLoadTexture = false;
		}
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
			gl.glColor4f((rgb[0] + .4f)* 1.25f, (rgb[1] + .4f)* 1.25f, (rgb[2] + .4f)* 1.25f, 1);
		}
		gl.glTranslatef(_x,_y,-2.41f);
		gl.glRotatef(ang, 0, 0, 1);
		gl.glScalef(sx, sy, 0);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	
	public void drawPos(GL10 gl,float x, float y) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mShouldLoadTexture) {
			loadGLTexture(gl);
			mShouldLoadTexture = false;
		}
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
			 gl.glColor4f(1,1,1,1);
		}
		gl.glTranslatef(x, y, -2.41f);
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	public void drawTransprentScal(GL10 gl,float x, float y, float z,float t)
    {
        gl.glPushMatrix();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
        if (mShouldLoadTexture) {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
        }
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
            gl.glColor4f(t,t,t,t);
        }
        gl.glTranslatef(x, y, -2.41f);
        gl.glScalef(z, z, 0);
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
        gl.glPopMatrix();

    }

	protected void setVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}
	protected void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mNumOfIndices = indices.length;
	}
	protected void setTextureCoordinates(float[] textureCoords) { // New
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(textureCoords);
		mTextureBuffer.position(0);
	}
	public Bitmap getBitmap() { 
		return this.mBitmap;
	}
	public void loadBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
		mShouldLoadTexture = true;
	}
	private void loadGLTexture(GL10 gl) { 
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_BLEND);
        if(!IsPowerOfTwo(mBitmap.getWidth()) || !IsPowerOfTwo(mBitmap.getHeight()))
        	mBitmap = PTImg(mBitmap);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
    }
	Bitmap PTImg(Bitmap bitmapOrg) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newWidth = PT(width);
		int newHeight = PT(height);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}
	int PT(int x) {
		--x;
		for (int i = 1; i < 32; i <<= 1) {
			x = x | x >> i;
		}
		return x + 1;
	}
	float width()
	{
		return Math.abs(mVerticesBuffer.get(0)*2);
	}
	float Height()
	{
		return Math.abs(mVerticesBuffer.get(1)*2);
	}
	boolean IsPowerOfTwo(int x)
	{
	    return (x & (x - 1)) == 0;
	}
}
