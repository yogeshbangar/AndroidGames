package com.hututu.app.scaryapp;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
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
//		M.GameScreen = M.GAMESKAL;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter >60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMETIME:
			Draw_Time(gl);
			break;
		case M.GAMESOUND:
			Draw_Sound(gl);
			break;
			
		case M.GAMEHELP:
			Draw_Help(gl);
			break;
		case M.GAMESKAL:
			Draw_Skal(gl);
			break;
		case M.GAMEPLAY:
			Draw_Play(gl);
			break;
		}
		if(appExit && Counter%10==0)
			GameRenderer.mStart.finish();
		Counter ++;
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
		case M.GAMETIME:
			Handle_Time(event);
			break;
		case M.GAMESOUND:
			Handle_Sound(event);
			break;
		case M.GAMEHELP:
			Handle_Help(event);
			break;
		case M.GAMESKAL:
			Handle_Skal(event);
			break;
		case M.GAMEPLAY:
			Handle_Play(event);
			break;
		}
		Handle(event);
		return true;
	}
	/*************************Play Start**********************************/
	void Draw_Play(GL10 gl){
		DrawTextureS(gl, mGR.mTex_Skal[mGR.mImg], 0,0,mGR.scal, mGR.scal);
		if(mGR.scal<2){
			mGR.scal*=1.5f;
			if(mGR.scal>2)
				mGR.scal=2;
		}else
			DrawTransScal(gl, mGR.mTex_back, .62f, -.91f,mGR.mSel == 8 ?1.1f:1f,mGR.mSel == 8 ?0.5f:1);
	}
	boolean Handle_Play(MotionEvent event){
		mGR.mSel = 0;
		
		
		if(CircRectsOverlap(.65f, -.91f, mGR.mTex_go.width()*.4f, mGR.mTex_go.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0) {
			switch (mGR.mSel) {
			case 8:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound6(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Play End************************************/
	/*************************Skal Start**********************************/
	void Draw_Skal(GL10 gl) {
		for (int i = 0; i < 12; i++) {
			DrawTextureS(gl, mGR.mTex_Skal[i], -.63f + ((i % 3) * .63f),
					.59f - ((i / 3) * .46f), .61f, .45f);
			if (i == mGR.mImg)
				DrawTextureS(gl, mGR.mTex_Icn[0], -.63f + ((i % 3) * .63f)
						- .24f, -.17f + .59f - ((i / 3) * .46f), 1, 1);
		}
	}
	boolean Handle_Skal(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<12;i++){
			if(CircRectsOverlap(-.63f+((i%3)*.63f),.59f-((i/3)*.46f), mGR.mTex_Icn[0].width()*.5f, mGR.mTex_Icn[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+1;
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			mGR.mImg = mGR.mSel - 1;
			M.GameScreen = M.GAMEMENU;
			M.sound6(GameRenderer.mContext, R.drawable.button);
		}
		return true;
	}
	/*************************Skal End**********************************/
	
	
	/*************************Help Start**********************************/
	void Draw_Help(GL10 gl){
		DrawTexture(gl, mGR.mTex_bg, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_InfoT, 0, 0);
		
		DrawTransScal(gl, mGR.mTex_back, .62f, -.91f,mGR.mSel == 8 ?1.1f:1f,mGR.mSel == 8 ?0.5f:1);
	}
	boolean Handle_Help(MotionEvent event){
		mGR.mSel = 0;
		
		
		if(CircRectsOverlap(.65f, -.91f, mGR.mTex_go.width()*.4f, mGR.mTex_go.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0) {
			switch (mGR.mSel) {
			case 8:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound6(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Help End************************************/
	/*************************Sound Start**********************************/
	void Draw_Sound(GL10 gl){
		DrawTexture(gl, mGR.mTex_bg, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		for(int i=0;i<5;i++){
			if(i == mGR.mSound){
				DrawTransScal(gl, mGR.mTex_Select	, 0,0.40f-.20f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
				DrawTransScal(gl, mGR.mTex_SIcn[0]	, -.31f,0.40f-.20f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			}else{
				DrawTransScal(gl, mGR.mTex_SIcn[1]	, -.30f,0.40f-.20f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			}
			DrawTransScal(gl, mGR.mTex_Scremming,  .12f,0.40f-.20f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_back, .62f, -.91f,mGR.mSel == 8 ?1.1f:1f,mGR.mSel == 8 ?0.5f:1);
	}
	boolean Handle_Sound(MotionEvent event){
		mGR.mSel = 0;
		for(int i=0;i<5;i++){
			if(CircRectsOverlap(0,0.40f-.20f*i, mGR.mTex_Select.width()*.5f, mGR.mTex_Select.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+1;
			}
		}
		
		if(CircRectsOverlap(.65f, -.91f, mGR.mTex_go.width()*.4f, mGR.mTex_go.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0) {
			switch (mGR.mSel) {
			default:
				mGR.mSound = mGR.mSel -1;
				M.soundPlay(GameRenderer.mContext, mGR.mSound);
				break;
			case 8:
				M.GameScreen = M.GAMEMENU;
				M.sound6(GameRenderer.mContext, R.drawable.button);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Sound End************************************/
	/*************************Time Start**********************************/
	void Draw_Time(GL10 gl){
		DrawTexture(gl, mGR.mTex_bg, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		for(int i=0;i<7;i++){
			if(i == M.mTime)
				DrawTransScal(gl, mGR.mTex_Select	, 0,0.51f-.17f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Time[i]	, -.26f,0.50f-.17f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			if(i<4)
				DrawTransScal(gl, mGR.mTex_Second,  .12f,0.51f-.17f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			else
				DrawTransScal(gl, mGR.mTex_Minute,  .12f,0.51f-.17f*i,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_back, .62f, -.91f,mGR.mSel == 8 ?1.1f:1f,mGR.mSel == 8 ?0.5f:1);
	}
	boolean Handle_Time(MotionEvent event){
		mGR.mSel = 0;
		for(int i=0;i<7;i++){
			if(CircRectsOverlap(0,0.51f-.17f*i, mGR.mTex_Select.width()*.5f, mGR.mTex_Select.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+1;
			}
		}
		
		if(CircRectsOverlap(.65f, -.91f, mGR.mTex_go.width()*.4f, mGR.mTex_go.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 8;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0) {
			switch (mGR.mSel) {
			default:
				M.mTime = mGR.mSel -1;
				break;
			case 8:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound6(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Time End************************************/
	/*************************Menu Start**********************************/
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_bg, 0, 0);
		DrawTexture(gl, mGR.mTex_Name, 0, .62f);
		for(int i=0;i<3;i++){
			if(i == 0)
				DrawTextureS(gl, mGR.mTex_Skal[mGR.mImg], -.68f+.68f*i, -.44f, .3f,.20f);
			else
				DrawTransScal(gl, mGR.mTex_Icn[i]	, -.68f+.68f*i, -.44f,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Text[i], -.68f+.68f*i,-.58f,mGR.mSel == i+1 ?1.1f:1f,mGR.mSel == i+1 ?0.5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_InfoI, -.79f, -.87f,mGR.mSel == 4 ?1.1f:1f,mGR.mSel == 4 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_go, .71f, -.91f,mGR.mSel == 5 ?1.1f:1f,mGR.mSel == 5 ?0.5f:1);
	}
	boolean appExit = false;
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		for(int i=0;i<3;i++){
			if(CircRectsOverlap(-.68f+.68f*i, -.44f, mGR.mTex_Icn[i].width()*.4f, mGR.mTex_Icn[i].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(-.79f, -.87f, mGR.mTex_InfoI.width()*.4f, mGR.mTex_InfoI.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(.71f, -.91f, mGR.mTex_go.width()*.4f, mGR.mTex_go.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0){
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESKAL;
				break;
			case 2:
				M.GameScreen = M.GAMESOUND;
				break;
			case 3:
				M.GameScreen = M.GAMETIME;
				break;
			case 4:
				M.GameScreen = M.GAMEHELP;
				break;
			case 5:
				startService(null);
				appExit = true;
				//Start
				break;
			}
			M.sound6(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Menu End************************************/
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy)
	{
		Tex.drawScal(gl, x, y,sx,sy);
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

	public void startService(View view) {
		stopService(view);
		Intent intent = new Intent(GameRenderer.mStart.getBaseContext(), MyService.class);
//        intent.putExtra("yogesh", 10);
		GameRenderer.mStart.startService(intent);
	}

	// Method to stop the service
	public void stopService(View view) {
		GameRenderer.mStart.stopService(new Intent(GameRenderer.mStart
				.getBaseContext(), MyService.class));
	}
}
