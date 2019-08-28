package com.hututu.game.bubbleboomblast;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLUtils;
import android.util.Log;
public class Mesh 
{
	public FloatBuffer mVerticesBuffer = null;
	private ShortBuffer mIndicesBuffer = null;
	private FloatBuffer mTextureBuffer; // New variable.
	private int mTextureId = -1; // New variable.
	private Bitmap mBitmap; // New variable.
	private boolean mShouldLoadTexture = false; // New variable.
	private int mNumOfIndices = -1;
//	private final float[] mRGBA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	private FloatBuffer mColorBuffer = null;
	public void drawScal(GL10 gl,float x,float y,float sx, float sy) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		gl.glTranslatef(x, y, 0);
		gl.glScalef(sx, sy, 0);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	public void drawSS(GL10 gl,float x,float y,float sx, float sy) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		
		gl.glRotatef(0, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);
		gl.glRotatef(0, 0, 0, 1);
		gl.glTranslatef(x, y, 0);
		gl.glScalef(sx, sy, 0);
		gl.glTranslatef(width()/2, 0, 0);
		
		
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	public void drawRotet(GL10 gl,float _x,float _y,float rotateZ,float sx) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		gl.glTranslatef(_x,_y,0);
		gl.glRotatef(rotateZ, 0, 0, 1);
		gl.glScalef(sx, sx, 0);
		
		
		
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
	public void draw(GL10 gl) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		gl.glTranslatef(0, 0, 0);
		gl.glRotatef(0, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);
		gl.glRotatef(0, 0, 0, 1);
		//gl.glTranslatef(moveX, moveY, 0);
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
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		gl.glTranslatef(0, 0, 0);
		gl.glRotatef(0, 1, 0, 0);
		gl.glRotatef(0, 0, 1, 0);
		gl.glRotatef(0, 0, 0, 1);
		gl.glTranslatef(x, y, 0);
		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (mTextureId != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	public void drawSRGB(GL10 gl,float x, float y,float s, float r,float g, float b)
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
            gl.glColor4f(r,g,b,1);
        }
        gl.glTranslatef(x, y, 0);
        gl.glScalef(s,s,0);
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
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
        gl.glTranslatef(x, y, 0);
        gl.glScalef(z, z, 0);
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
        gl.glPopMatrix();

    }
	public void drawRotet2(GL10 gl,float rotate,float _x,float _y,float s,float tt) 
	{
		gl.glPushMatrix();
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		if (mColorBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		}
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
		gl.glTranslatef(_x,_y,0);
		gl.glScalef(M.ScreenHieght/M.ScreenWidth,s,0);
		gl.glRotatef(rotate, 0, 0, 1);
		gl.glTranslatef(0,Height()*tt,0);
		
		
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
	protected void setColor(float red, float green, float blue, float alpha) {
//		mRGBA[0] = red;
//		mRGBA[1] = green;
//		mRGBA[2] = blue;
//		mRGBA[3] = alpha;
	}
	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
	}
	public void loadBitmap(Bitmap bitmap) { // New function.
		this.mBitmap = bitmap;
		mShouldLoadTexture = true;
	}
	private void loadGLTexture(GL10 gl) { // New function
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
//        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
        if(!IsPowerOfTwo(mBitmap.getWidth()) || !IsPowerOfTwo(mBitmap.getHeight()))
        	mBitmap = PTImg(mBitmap);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
        mBitmap.recycle();
       
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
		Log.d("resizeImg========", "newWidth [" + newWidth + "] newHeight ["+ newHeight + "]");
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
