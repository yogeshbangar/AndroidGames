package com.hututu.game.kingpenguin;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter = 0;
	static float sx,sy;
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
		Counter++;
		if(mGR.mTex_Logo==null)
			return;
//		M.GameScreen =M.GAMELVLCOMP;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>50){
				if(!GameRenderer.mStart.addFree)
					M.GameScreen = M.GAMEADV;
				else{
					M.GameScreen = M.GAMEMAIN;
					M.play(GameRenderer.mContext, R.drawable.splesh);
				}
				Counter =0;
			}
			break;
		case M.GAMEADV:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Ling,0.0f,-.9f);
				mGR.mTex_LingBar.drawSS(gl,-.63f,-.9f	, Counter*.63f, 1.3f);//10
			}
			if((Counter/mGR.mLOne.FrogAnim.length)%3==0)
				Group.DrawTexture(gl, mGR.mLsix.mTex_PSplash[mGR.mLOne.FrogAnim[Counter%mGR.mLOne.FrogAnim.length]],0,0);
			else
				Group.DrawTexture(gl, mGR.mLsix.mTex_PSplash[0],0,0);
			break;
//		case M.GAMELOAD:
//			if(Counter>210)
//			{
//				M.GameScreen = mGR.mLSelect.mGameSel + 1;
//				mGR.mLSelect.resetLevel(M.GameScreen);
//				if (!M.HELPSCR[mGR.mLSelect.mGameSel]) {
//					M.GameScreen = M.GAMEHELP;
//				}
//			}
//			else{
//				DrawTexture(gl, mGR.mTex_Ling,0.0f,-.9f);
//				mGR.mTex_LingBar.drawSS(gl,-.63f,-.9f	, Counter*.3f, 1.3f);//10
//			}
//			if((Counter/mGR.mLOne.FrogAnim.length)%3==0)
//				Group.DrawTexture(gl, mGR.mLsix.mTex_PSplash[mGR.mLOne.FrogAnim[Counter%mGR.mLOne.FrogAnim.length]],0,0);
//			else
//				Group.DrawTexture(gl, mGR.mLsix.mTex_PSplash[0],0,0);
//			break;
		case M.GAMEONE:
			mGR.mLOne.Draw_GamePlay(gl);
			break;
		case M.GAMETWO:
			mGR.mLTwo.Draw_GamePlay(gl);
			break;
		case M.GAMETREE:
			mGR.mLTree.Draw_GamePlay(gl);
			break;
		case M.GAMEFOUR:
			mGR.mLFour.Draw_GamePlay(gl);
			break;
		case M.GAMEFIVE:
			mGR.mLFive.Draw_GamePlay(gl);
			break;
		case M.GAMESIX:
			mGR.mLsix.Draw_GamePlay(gl);
			break;
		case M.GAMESEVEN:
			mGR.mLSeven.Draw_GamePlay(gl);
			break;
		case M.GAMEEIGHT:
			mGR.mLEight.Draw_GamePlay(gl);
			break;
		case M.GAMENINE:
			mGR.mLNine.Draw_GamePlay(gl);
			break;
		case M.GAMETEN:
			mGR.mLTen.Draw_GamePlay(gl);
			break;
		case M.GAMEELEVEN:
			mGR.mLEleven.Draw_GamePlay(gl);
			break;
		case M.GAMETWELVE:
			mGR.mLTwelve.Draw_GamePlay(gl);
			break;
		case M.GAMETHIRTEEN:
			mGR.mLThirteen.Draw_GamePlay(gl);
			break;
		case M.GAMESELECT:
			mGR.mLSelect.Draw_LevelSel(gl);
			break;
		case M.GAMEMAIN:
			mGR.mLSelect.Draw_Main(gl);
			break;
		case M.GAMEBUY:
			mGR.mLSelect.Draw_BUY(gl);
			break;
		case M.GAMEACHIV:
			mGR.mLSelect.Draw_ACHIV(gl);
			break;
		case M.GAMESETTING:
			mGR.mLSelect.Draw_SETTING(gl);
			break;
		case M.GAMEABOUT:
			mGR.mLSelect.Draw_About(gl);
			break;
		case M.GAMELVLCOMP:
			mGR.mLSelect.Draw_LevelComplete(gl);
			break;
		case M.GAMELVLPUSE:
			mGR.mLSelect.Draw_LevelPause(gl);
			break;
		case M.GAMEHELP:
			mGR.mLSelect.Draw_Help(gl);
			break;
		case M.GAMEFREE:
//			mGR.mLSelect.Draw_AchiDcs(gl);
			mGR.mLSelect.Draw_FREE(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		if(mGR.mTex_Logo==null)
			return false;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEADV:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.play(GameRenderer.mContext, R.drawable.splesh);
				M.GameScreen = M.GAMEMAIN;
				M.soundBotton(GameRenderer.mContext, R.drawable.button_click);
			}
			break;
		case M.GAMEONE:
			mGR.mLOne.Handle_GamePlay(event);
			break;
		case M.GAMETWO:
			mGR.mLTwo.Handle_GamePlay(event);
			break;
		case M.GAMETREE:
			mGR.mLTree.Handle_GamePlay(event);
			break;
		case M.GAMEFOUR:
			mGR.mLFour.Handle_GamePlay(event);
			break;
		case M.GAMEFIVE:
			mGR.mLFive.Handle_GamePlay(event);
			break;
		case M.GAMESIX:
			mGR.mLsix.Handle_GamePlay(event);
			break;
		case M.GAMESEVEN:
			mGR.mLSeven.Handle_GamePlay(event);
			break;
		case M.GAMEEIGHT:
			mGR.mLEight.Handle_GamePlay(event);
			break;
		case M.GAMENINE:
			mGR.mLNine.Handle_GamePlay(event);
			break;
		case M.GAMETEN:
			mGR.mLTen.Handle_GamePlay(event);
			break;
		case M.GAMEELEVEN:
			mGR.mLEleven.Handle_GamePlay(event);
			break;
		case M.GAMETWELVE:
			mGR.mLTwelve.Handle_GamePlay(event);
			break;
		case M.GAMETHIRTEEN:
			mGR.mLThirteen.Handle_GamePlay(event);
			break;
		case M.GAMESELECT:
			mGR.mLSelect.Handle_LevelSel(event);
			break;
		case M.GAMEMAIN:
			mGR.mLSelect.Handle_Main(event);
			break;
		case M.GAMEBUY:
			mGR.mLSelect.Handle_BUY(event);
			break;
		case M.GAMEACHIV:
			mGR.mLSelect.Handle_ACHIV(event);
			break;
		case M.GAMESETTING:
			mGR.mLSelect.Handle_SETTING(event);
			break;
		case M.GAMEABOUT:
			mGR.mLSelect.Handle_About(event);
			break;
		case M.GAMELVLCOMP:
			mGR.mLSelect.Handle_LevelComplete(event);
			break;
		case M.GAMELVLPUSE:
			mGR.mLSelect.Handle_LevelPause(event);
			break;
		case M.GAMEHELP:
			mGR.mLSelect.Handle_Help(event);
			break;
		case M.GAMEFREE:
			mGR.mLSelect.Handle_FREE(event);
			break;
			}
		Handle(event);
		return true;
	}
	
	/**~~~~~~~~~~~~~~~~~~~~~~Game Play Start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~Game Play One End~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	/**~~~~~~~~~~~~~~~~~~~~~~Game Play Two Start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	void Draw_GamePlay(GL10 gl)
	{
		
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			
		}
		return true;
	}
	/**~~~~~~~~~~~~~~~~~~~~~~Game Play Two End~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/
	static void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	static void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float scal)
	{
		Tex.drawRotet(gl, x, y,angle,scal);
	}
	static void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	static void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	static boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;
    }
	static float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	static float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	static boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
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
	static boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	static double GetAngle(double d,double e)
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
		float dx = mGR.mTex_NFont[0][0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_NFont.length)
				mGR.mTex_NFont[0][k].drawPos(gl,x+i*dx,y);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	   GameRenderer.mContext.startActivity(mIntent);
	}
	static  void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	static void share2friend()
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
