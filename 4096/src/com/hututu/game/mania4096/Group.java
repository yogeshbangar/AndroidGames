package com.hututu.game.mania4096;
import javax.microedition.khronos.opengles.GL10;



import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
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
//		M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>50)
			{
				
				if(!mGR.addFree)
				{
					M.GameScreen = M.GAMEADD;
					Counter = 0;
				}else{
					M.GameScreen = M.GAMEMENU;
				}
				M.GameScreen = M.GAMEMENU;
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTransScal(gl,mGR.mTex_Cross, .80f,0.85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			else 
			{
				mGR.mTex_Ling.drawSS(gl,-.75f,.87f	, .7f, 1.0f);//10
				mGR.mTex_LingBar.drawSS(gl,-.74f,.87f	, Counter*.44f, 1.3f);//10
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		
		case M.GAMEWIN:
		case M.GAMEOVER:
			Draw_WinOver(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEJOIN:
			Draw_Join(gl);
			break;
		case M.GAMEHELP:
			Draw_Help(gl);
			break;
		case M.GAMECONTR:
		case M.GAMEABOUT:
			Draw_Control(gl);
			break;
		}
//		
		
//		setting();
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			
			break;
		case M.GAMEADD:
			mGR.mSel = 0;
			if(CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 1;
			}
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				mGR.mSel = 0;
				M.sound1(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEHELP:
			Handle_Help(event);
			break;
		case M.GAMECONTR:
		case M.GAMEABOUT:
			Handle_Control(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Handle_Over(event);
			break;
		case M.GAMEJOIN:
			Handle_Join(event);
			break;
		}
	
		
		Handle(event);
		return true;
	}
	
	
	void Draw_Join(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Small_popup, 0, 0);
		DrawTexture(gl, mGR.mTex_chalg, 0, +.08f);
		DrawTransScal(gl, mGR.mTex_join		,-.35f,-.18f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Later	,0.35f,-.18f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
	}
	boolean Handle_Join(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.35f,-.18f, mGR.mTex_join.width()*.4f, mGR.mTex_join.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.35f,-.18f, mGR.mTex_join.width()*.4f, mGR.mTex_join.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				M.GameScreen = M.GAMEPLAY;
				Counter = 0;
				break;
			case 2:
				M.GameScreen = M.GAMEPLAY;
				Counter = 0;
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	void Draw_Control(GL10 gl){
//		DrawTexture(gl, mGR.mTex_Bg, 0, 0);
		DrawTexture(gl, mGR.mTex_Big_popup,0, .34f);
		
		if(M.GameScreen == M.GAMEABOUT){
			DrawTexture(gl, mGR.mTex_aboutFont	,0, .71f);
			DrawTexture(gl, mGR.mTex_AboutTxt	,0, .26f);
		}
		/*else {
			DrawTexture(gl, mGR.mTex_GameEnd, 0, .71f);
			for (int i = 0; i < 4; i++) {
				DrawTexture(gl, mGR.mTex_Target0[i], .07f, .51f - i * .16f);
				DrawTransScal(gl, mGR.mTex_DSelect, -.43f, .51f - i * .16f,
						mGR.mSel - 2 == i ? 1.2f : 1, mGR.mSel - 2 == i ? 0.5f
								: 1);
				if (mGR.mLevel == i)
					DrawTexture(gl, mGR.mTex_Select, -.43f, .51f - i * .16f);
			}
		}
		*/
		
		DrawTransScal(gl, mGR.mTex_LeaderNo,0, -.28f,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTransScal(gl, mGR.mTex_LeaderBoard,0, -.47f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		
		for(int i =0;i<mGR.mTex_Icn.length;i++)
			DrawTransScal(gl, mGR.mTex_Icn[i],-.67f+i*.44f, -.68f,mGR.mSel-10 == i?1.1f:1,mGR.mSel-10 == i?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Back	, -.72f, .88f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
	}
	boolean Handle_Control(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.72f, .88f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
//		for(int i =0;i<4 && (M.GameScreen == M.GAMECONTR);i++){
//			if(CircRectsOverlap(0, .51f-i*.16f, mGR.mTex_Play.width()*.6f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//			{
//				mGR.mSel = i+2;
//			}
//		}
		if(CircRectsOverlap(0, -.28f, mGR.mTex_LeaderNo.width()*.4f, mGR.mTex_LeaderNo.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(CircRectsOverlap(0, -.47f, mGR.mTex_LeaderBoard.width()*.4f, mGR.mTex_LeaderBoard.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 8;
		}
		for(int i =0;i<mGR.mTex_Icn.length;i++){
			if(CircRectsOverlap(-.67f+i*.44f,-.68f, mGR.mTex_Icn[0].width()*.4f, mGR.mTex_Icn[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+10;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				myS = -2f;
				myB = 2f;
				break;
			case 2:case 3:case 4:case 5:
				mGR.mLevel = mGR.mSel -2;
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//LeaderBord
				break;
			case 8:
				GameRenderer.mStart.onShowAchievementsRequested();
				//Achievement
				break;
//			case 10:
//				M.GameScreen = M.GAMEABOUT;
//				break;
			case 10:
				M.GameScreen = M.GAMEHELP;
				break;
			case 11:
				MoreGame();
				break;
			case 12:
				facebook();
				break;
			case 13:
				google();
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	void Draw_Help(GL10 gl){
//		DrawTexture(gl, mGR.mTex_Bg, 0, 0);
//		DrawTexture(gl, mGR.mTex_SplashFont	, 0, 0.40f);
		DrawTexture(gl, mGR.mTex_Big_popup	,0, -.04f);
		DrawTexture(gl, mGR.mTex_HelpFont	,0, 0.32f);
		DrawTexture(gl, mGR.mTex_SwiptoMove	,0, -.10f);
		
		DrawTransScal(gl, mGR.mTex_Back	, -.68f, -.58f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
	}
	boolean Handle_Help(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.68f, -.54f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				myS = -2f;
				myB = 2f;
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	float myS=-2f,myB=2f;
	void Draw_Menu(GL10 gl){
//		DrawTexture(gl, mGR.mTex_Bg, 0, 0);
		DrawTexture(gl, mGR.mTex_SplashFont, 0, myS+.45f);
		
		DrawTransScal(gl, mGR.mTex_Play	, 0, myB-.26f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_HelpBtn, 0, myB-.50f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_ControlIcon, -.78f, myB-.7f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?0:1]	, 0.78f, myB-.7f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		
		if(myB>0)
			myB -=.2f;
		if(myS<0)
			myS +=.2f;
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0, myB-.26f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0, myB-.50f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.78f, myB-.7f, mGR.mTex_ControlIcon.width()*.4f, mGR.mTex_ControlIcon.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.78f, myB-.7f, mGR.mTex_ControlIcon.width()*.4f, mGR.mTex_ControlIcon.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				Counter = 0;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				break;
			case 2:
				M.GameScreen = M.GAMEHELP;
				break;
			case 3:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 4:
				M.setValue = !M.setValue;
				mGR.mSel = 0;
				M.sound1(GameRenderer.mContext, R.drawable.click);
				break;
				}
			if (mGR.mSel != 0) {
				myS = -2f;
				myB = 2f;
				mGR.mSel = 0;
				M.sound1(GameRenderer.mContext, R.drawable.click);
			}
		}
		return true;
	}
	
	
	
	void scoreUpdate(int no)
	{
		mGR.mScore+=10+no;
		if(mGR.mScore>mGR.mHScore)
			mGR.mHScore=mGR.mScore;
		New = true;
//		M.sound3(GameRenderer.mContext, R.drawable.cube1);
	}
	
	int GameCount =0;
//	int Gamemax = 4;
	void Gamelogic()
	{
		if(mGR.moveDirection == 1)
		{
			for (int i = 0; i < mGR.mNum.length; i++) {
				for (int j = 0; j < mGR.mNum[i].length-1; j++) {
					if(mGR.mNum[i][j].num == 0 && mGR.mNum[i][j+1].num != 0){
						mGR.mNum[i][j].num = mGR.mNum[i][j+1].num;
						mGR.mNum[i][j+1].num=0;
						New = true;
					}
				}
			}
			GameCount++;
			if(GameCount==(mGR.mNum.length-1)){
				for (int i = 0; i < mGR.mNum.length; i++) {
					for (int j = 0; j < mGR.mNum[i].length-1; j++) 
					{
						
						if(mGR.mNum[i][j].num == mGR.mNum[i][j+1].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i][j+1].isMarge 
								&& mGR.mNum[i][j].num !=0){
							mGR.mNum[i][j].set(mGR.mNum[i][j].num*2,true);
							scoreUpdate(mGR.mNum[i][j].num);
							mGR.mNum[i][j+1].set(0,true);
							GameCount = 0;
							
						}
					}
				}
			}
		}
		if(mGR.moveDirection == 2 && GameCount <(mGR.mNum.length-1))
		{
			for (int i = 0; i < mGR.mNum.length; i++) {
				for (int j = mGR.mNum[i].length-1; j > 0; j--) {
					if(mGR.mNum[i][j].num == 0 && mGR.mNum[i][j-1].num != 0){
							mGR.mNum[i][j].num = mGR.mNum[i][j-1].num;
							mGR.mNum[i][j-1].num=0;
							New = true;
					}
				}
			}
			GameCount++;
			if (GameCount == (mGR.mNum.length-1)) {
				for (int i = 0; i < mGR.mNum.length; i++) {
					for (int j = mGR.mNum[i].length - 1; j > 0; j--) {
						if (mGR.mNum[i][j].num == mGR.mNum[i][j - 1].num && !mGR.mNum[i][j].isMarge &&  !mGR.mNum[i][j - 1].isMarge 
								&& mGR.mNum[i][j].num !=0) {
							mGR.mNum[i][j].set(mGR.mNum[i][j].num*2,true);
							scoreUpdate(mGR.mNum[i][j].num);
							mGR.mNum[i][j - 1].set(0,true);
							GameCount = 0;
							
						}
					}
				}}
		}
		if(mGR.moveDirection == 3 && GameCount <(mGR.mNum.length-1))
		{
			for (int i = mGR.mNum.length-1;i>0; i--) {
				for (int j = 0; j < mGR.mNum[i].length; j++) {
					if(mGR.mNum[i][j].num == 0 && mGR.mNum[i-1][j].num!=0){
							mGR.mNum[i][j].num = mGR.mNum[i-1][j].num;
							mGR.mNum[i-1][j].num=0;
							New = true;
					}
				}
			}
			GameCount++;
			if(GameCount==(mGR.mNum.length-1)){
				for (int i = mGR.mNum.length-1;i>0; i--) {
					for (int j = 0; j < mGR.mNum[i].length; j++) {
						if(mGR.mNum[i][j].num == mGR.mNum[i-1][j].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i-1][j].isMarge 
								&& mGR.mNum[i][j].num !=0){
							mGR.mNum[i][j].set(mGR.mNum[i][j].num*2,true);
							scoreUpdate(mGR.mNum[i][j].num);
							mGR.mNum[i-1][j].set(0,true);
							GameCount = 0;
							
						}
					}
				}
			}
		}
		if(mGR.moveDirection == 4 && GameCount <(mGR.mNum.length-1))
		{
			for (int i = 0;i<mGR.mNum.length-1; i++) {
				for (int j = 0; j < mGR.mNum[i].length; j++) {
					if(mGR.mNum[i][j].num == 0&& mGR.mNum[i+1][j].num!=0){
							mGR.mNum[i][j].num = mGR.mNum[i+1][j].num;
							mGR.mNum[i+1][j].num=0;
							New = true;
					}
				}
			}
			GameCount++;
			if(GameCount==(mGR.mNum.length-1)){
				for (int i = 0;i<mGR.mNum.length-1; i++) {
					for (int j = 0; j < mGR.mNum[i].length; j++) {
						if(mGR.mNum[i][j].num == mGR.mNum[i+1][j].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i+1][j].isMarge
								&& mGR.mNum[i][j].num !=0){
							mGR.mNum[i][j].set(mGR.mNum[i][j].num*2,true);
							scoreUpdate(mGR.mNum[i][j].num);
							mGR.mNum[i+1][j].set(0,true);
							GameCount = 0;
						}
					}
				}
			}
		}
		if(GameCount == (mGR.mNum.length-1))
		{
			GameCount++;
			int mmm=0;
			for(int i =0;i<mGR.mNum.length;i++)
			{
				for(int j =0;j<mGR.mNum[i].length;j++)
				{
					if(mGR.max<mGR.mNum[i][j].num)
						mGR.max = mGR.mNum[i][j].num;
					mGR.mNum[i][j].isMarge = false;
					if(mGR.mNum[i][j].num==0)
						mmm++;
//					System.out.println("mNum["+i+"]["+j+"].num = "+mGR.mNum[i][j].num+";");
				}
			}
			if (mmm > 0) {
				mmm = M.mRand.nextInt(mmm);
				int k = 0;
				for (int i = 0; i < mGR.mNum.length; i++) {
					for (int j = 0; j < mGR.mNum[i].length; j++) {
						if (mGR.mNum[i][j].num == 0) {
							if (mmm == k) {
								gameMove++;
								if (New) {
									if (mGR.mHelp == 1) {
										si = sj = 0;
										mGR.mNum[si][sj].num = 4;
										scl = .1f;
									} else {
										mGR.mNum[i][j].num = gameMove % 2 == 0 ? 2 : 4;
										si = i;
										sj = j;
										scl = .1f;
									}
//									System.out.println(mGR.mHelp);
								}
//								M.sound2(GameRenderer.mContext, R.drawable.cube_apear);
								i = j = 5;
								break;
							}
							k++;
						}
					}
				}
			}
			if(mGR.max >= 4096)
			{
				M.GameScreen = M.GAMEWIN;
				GameRenderer.mStart.Submitscore();
				M.sound6(GameRenderer.mContext, R.drawable.level_complete);
				GameRenderer.mStart.show();
//				try{handler.sendEmptyMessage(0);}catch(Exception e){}
			}
		}
		if(scl <1)
			scl*=1.9f;
	}
	int si=0,sj=0;
	float scl=2;
	int gameMove;
	void Draw_GamePlay(GL10 gl)
	{
		updateAchivment();
		float goy =-.21f;
//		DrawTexture(gl, mGR.mTex_Bg, 0, 0);
		DrawTexture(gl, mGR.mTex_ScoreBox	,-.68f, .88f);
		DrawTexture(gl, mGR.mTex_BestScore	,0.68f, .88f);
		DrawTexture(gl, mGR.mTex_Target_box	,0.00f, .88f);
		
		DrawTexture(gl, mGR.mTex_Top_pattern, 0 , .72f+goy);
		DrawTexture(gl, mGR.mTex_GameBoard, 0,.05f+goy);
		
//		mGR.max = 4096;
		
		DrawTexture(gl,mGR.mTex_ScorePreview, -.82f+getPower(mGR.max)*2.24f*.066f, .77f+goy);
		drawNumberScal(gl, mGR.max			, -.90f+getPower(mGR.max)*2.24f*.066f, .77f+goy, .6f, 1);
		DrawTexture(gl, mGR.mTex_Mark		, -.82f+getPower(mGR.max)*2.24f*.066f, .72f+goy);
		mGR.mTex_Fill.drawSS(gl				, -.82f,.697f+goy, getPower(mGR.max)*2.24f, 1);//10
		
		if(M.GameScreen == M.GAMEPLAY){
			DrawTransScal(gl, mGR.mTex_MenuB		, -.6f,.67f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_RestartBtn	,  .6f,.67f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		}
		
		
		
		
		
		drawNumber(gl, mGR.mScore, -.85f, .83f);
		drawNumber(gl, mGR.mHScore, .49f, .83f);
//		DrawTexture(gl, mGR.mTex_Target[mGR.mLevel%4], 0, .83f);
		
		
		for(int i =0;i<mGR.mNum.length;i++)
		{
			for(int j =0;j<mGR.mNum[i].length;j++)
			{
//				DrawTexture(gl, mGR.mTex_Block[0],-.76f+.38f*j,.30f-.23f*i);
				if(mGR.mNum[i][j].num > 0){
					if(i==si && j == sj && scl <1)
						DrawTextureS(gl, mGR.mTex_Block[getPower(mGR.mNum[i][j].num)],-.76f+.38f*j,.30f-.23f*i,scl);
					else
						DrawTexture(gl, mGR.mTex_Block[getPower(mGR.mNum[i][j].num)],-.76f+.38f*j,.30f-.23f*i);
				}
			}
		}
		if(mGR.mHelp ==0){
			DrawTexture(gl, mGR.mTex_Hand, .42f-(Counter%60)*.01f, -.4f);
			DrawTexture(gl, mGR.mTex_Arrow, 0, -.23f);
		}
		if(mGR.mHelp ==1)
		{
			DrawTexture(gl, mGR.mTex_Hand, 0.3f,-.7f+(Counter%60)*.01f);
			DrawTexture(gl, mGR.mTex_Arrow1, 0, sy);
		}
		
			Gamelogic();
	}
	
	float SScal=5;
	int scot=0;
	float moveX,moveY;
	boolean New = false;
	boolean Handle_GamePlay(MotionEvent event)
	{
//		mGR.mSel = 0;
//		System.out.println(Counter+"      "+mGR.moveDirection);
		if(Counter<20)
			return true;
		
//		System.out.println(Counter+"    ~~~~	  "+mGR.moveDirection);
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			moveX = screen2worldX(event.getX());
			moveY = screen2worldY(event.getY());
			mGR.moveDirection =0;
			if(CircRectsOverlap(-.6f,.64f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 1;
				
			}
			if(CircRectsOverlap(0.6f,0.64f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 2;
			}
		}
//		mGR.mHelp = 100;
		if(MotionEvent.ACTION_MOVE == event.getAction() && mGR.moveDirection ==0 && mGR.mSel ==0)
		{
			float chX=(moveX - screen2worldX(event.getX())); float chY=(moveY - screen2worldY(event.getY()));
			if(Math.abs(chX) > Math.abs(chY)) {
				if(Math.abs(chX)>.1f){
					if(chX >.1f){
						if(mGR.mHelp == 0 || mGR.mHelp > 1){
							mGR.moveDirection = 1;//System.out.println("[Left]");
							mGR.mHelp++;
						}
					}
					else if(mGR.mHelp > 1){
						mGR.mHelp++;
						mGR.moveDirection = 2;//System.out.println("[Right]");
					}
				}
			} else {
				if(Math.abs(chY)>.1f){
					if((chY)>.1f && (mGR.mHelp > 1)){
						mGR.mHelp++;
						mGR.moveDirection = 3;//System.out.println("[Down]");
					}
					else if(mGR.mHelp > 0){ 
						mGR.moveDirection = 4;//System.out.println("[Up]");
						mGR.mHelp++;
					}
				}
			}
			
			if(mGR.moveDirection !=0)
			{
//				System.out.println("["+mGR.moveDirection+"]");
				New = false;
				if(!Check4GameOver())
				{
					GameRenderer.mStart.Submitscore();
					M.GameScreen = M.GAMEOVER;
					M.sound5(GameRenderer.mContext, R.drawable.gameover);
					M.sound7(GameRenderer.mContext, R.drawable.starcolect);
					GameRenderer.mStart.show();
//					try{handler.sendEmptyMessage(0);}catch(Exception e){}
					
				}
				else
					M.sound3(GameRenderer.mContext, R.drawable.cube0);
				GameCount=0;
//				mGR.Move[0]=mGR.Move[1]=mGR.Move[2]=mGR.Move[3]=0;
				//getDirection();
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(CircRectsOverlap(-.6f,.64f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					M.GameScreen = M.GAMEMENU;
					
				}
				
				
				break;
			case 2:
				if(CircRectsOverlap(0.6f,0.64f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.gameReset();
				}
				break;
			}
			if(mGR.mSel !=0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	void Draw_WinOver(GL10 gl)
	{
//		mGR.max = 1024;
		mGR.goCounter++;
		DrawTexture(gl, mGR.mTex_CongratsPopup, 0, .24f);
		if (M.GameScreen == M.GAMEWIN)
			DrawTexture(gl, mGR.mTex_Congratulation, 0, .61f);
		else
			DrawTexture(gl, mGR.mTex_Gameover, 0, .61f);
		DrawTexture(gl, mGR.mTex_uFont, 0, .39f);
		drawNumber(gl, mGR.max, -.1f, .25f);
		for (int i = 0; i < 3 && i <= scot; i++) {
			if (i == scot)
				DrawTextureS(gl, mGR.mTex_Star0, -.43f + .43f * i, -.03f, SScal);
			else
				DrawTexture(gl, mGR.mTex_Star0, -.43f + .43f * i, -.03f);
		}
		if (SScal <= 1) {
			if (mGR.max > 128 && scot == 0) {
				scot++;
				SScal = 5;
				M.sound7(GameRenderer.mContext, R.drawable.starcolect);
			} else if (mGR.max > 256 && scot == 1) {
				scot++;
				SScal = 5;
				M.sound7(GameRenderer.mContext, R.drawable.starcolect);
			}
		}
		if (SScal > 1)
			SScal *= .8f;
		else
			SScal = 1f;
		DrawTexture(gl, mGR.mTex_ScoreFont, -.16f, .12f);
		drawNumber(gl, mGR.mScore, 0.1f, .12f);

		if (mGR.goCounter > 40) {
			DrawTransScal(gl, mGR.mTex_MenuB, -.37f, -.31f, mGR.mSel == 1 ? 1.1f : 1, mGR.mSel == 1 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_RestartBtn, 0.37f, -.31f, mGR.mSel == 2 ? 1.1f : 1, mGR.mSel == 2 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_LeaderNo, 0.00f, -.53f, mGR.mSel == 3 ? 1.1f : 1, mGR.mSel == 3 ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_LeaderBoard, 0.00f, -.72f, mGR.mSel == 4 ? 1.1f : 1, mGR.mSel == 4 ? 0.5f : 1);
		} else {
			if (mGR.goCounter > 0) {
				if (mGR.goCounter < 10)
					DrawTransScal(gl, mGR.mTex_MenuB, -.37f, -.31f, (mGR.goCounter % 10) * .1f, (mGR.goCounter % 10) * .1f);
				else
					DrawTransScal(gl, mGR.mTex_MenuB, -.37f, -.31f, 1, 1);
			}
			if (mGR.goCounter > 9) {
				if (mGR.goCounter < 20)
					DrawTransScal(gl, mGR.mTex_RestartBtn, 0.37f, -.31f, (mGR.goCounter % 10) * .1f, (mGR.goCounter % 10) * .1f);
				else
					DrawTransScal(gl, mGR.mTex_RestartBtn, 0.37f, -.31f, 1, 1);
			}
			if (mGR.goCounter > 19) {
				if (mGR.goCounter < 30)
					DrawTransScal(gl, mGR.mTex_LeaderNo, 0.00f, -.53f, (mGR.goCounter % 10) * .1f, (mGR.goCounter % 10) * .1f);
				else
					DrawTransScal(gl, mGR.mTex_LeaderNo, 0.00f, -.53f, 1, 1);
			}
			if (mGR.goCounter > 29) {
				if (mGR.goCounter < 40)
					DrawTransScal(gl, mGR.mTex_LeaderBoard, 0.00f, -.72f, (mGR.goCounter % 10) * .1f, (mGR.goCounter % 10) * .1f);
				else
					DrawTransScal(gl, mGR.mTex_LeaderBoard, 0.00f, -.72f, 1, 1);
			}

		}

	}
	boolean Handle_Over(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.37f,-.31f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.37f,-.31f, mGR.mTex_MenuB.width()*.4f, mGR.mTex_MenuB.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.00f,-.53f, mGR.mTex_LeaderNo.width()*.4f, mGR.mTex_LeaderNo.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.00f,-.72f, mGR.mTex_LeaderBoard.width()*.4f, mGR.mTex_LeaderBoard.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
 {
			if (mGR.goCounter > 40) {
				switch (mGR.mSel) {
				case 1:
					M.GameScreen = M.GAMEMENU;
					break;
				case 2:
					mGR.gameReset();
					M.GameScreen = M.GAMEPLAY;
					Counter = 0;
					break;

				case 3:
					GameRenderer.mStart.onShowLeaderboardsRequested();
					// LeaderBord
					break;
				case 4:
					GameRenderer.mStart.onShowAchievementsRequested();
					// Achievement
					break;

				}

				if (mGR.mSel != 0)
					M.sound1(GameRenderer.mContext, R.drawable.click);
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	boolean Check4GameOver()
	{
//		if(mGR.moveDirection == 1)
		{
			for (int i = 0; i < mGR.mNum.length; i++) {
				for (int j = 0; j < 3; j++) 
				{
					if((mGR.mNum[i][j].num == mGR.mNum[i][j+1].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i][j+1].isMarge) 
							|| mGR.mNum[i][j].num ==0){
						return true;
					}
				}
			}
		}
//		if(mGR.moveDirection == 2 && GameCount <(mGR.mNum.length-1))
		{
			for (int i = 0; i < mGR.mNum.length; i++) {
				for (int j = mGR.mNum[i].length - 1; j > 0; j--) {
					if ((mGR.mNum[i][j].num == mGR.mNum[i][j - 1].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i][j - 1].isMarge)
							|| mGR.mNum[i][j].num == 0) {
						return true;
					}
				}
			}
		}
//		if(mGR.moveDirection == 3 && GameCount <(mGR.mNum.length-1))
 {
			for (int i = mGR.mNum.length - 1; i > 0; i--) {
				for (int j = 0; j < mGR.mNum[i].length; j++) {
					if ((mGR.mNum[i][j].num == mGR.mNum[i - 1][j].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i - 1][j].isMarge)
							|| mGR.mNum[i][j].num == 0) {
						return true;
					}
				}
			}
		}
//		if(mGR.moveDirection == 4 && GameCount <(mGR.mNum.length-1))
		{
			for (int i = 0; i < mGR.mNum.length - 1; i++) {
				for (int j = 0; j < mGR.mNum[i].length; j++) {
					if ((mGR.mNum[i][j].num == mGR.mNum[i + 1][j].num && !mGR.mNum[i][j].isMarge && !mGR.mNum[i + 1][j].isMarge)
							|| mGR.mNum[i][j].num == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	
	
	
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
		float dx = mGR.mTex_Font[0].width() * .7f;
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,float scal,float tran)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.7f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawTransprentScal(gl,x+i*dx,y,scal,tran);
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
	int getPower(int val)
	{
		int x = 1;
		for(int i=0;i<val;i++)
		{
			x*=2;
			if(val == x)
				return i;
			
		}
		return -1;
	}
	int Achivment[]={
			R.string.achievement_256,
			R.string.achievement_512,
			R.string.achievement_1024,
			R.string.achievement_2048,
			R.string.achievement_4096
	};
	void updateAchivment()
	{
		
		
		if(mGR.max == 256 && !mGR.ach[0])
		{
			mGR.ach[0] = true;
			GameRenderer.mStart.UnlockAchievement(mGR.root.Achivment[0]);
		}
		if(mGR.max == 512 && !mGR.ach[1])
		{
			mGR.ach[1] = true;
			GameRenderer.mStart.UnlockAchievement(mGR.root.Achivment[1]);
		}
		if(mGR.max == 1024 && !mGR.ach[2])
		{
			mGR.ach[2] = true;
			GameRenderer.mStart.UnlockAchievement(mGR.root.Achivment[2]);
		}
		if(mGR.max == 2048 && !mGR.ach[3])
		{
			mGR.ach[3] = true;
			GameRenderer.mStart.UnlockAchievement(mGR.root.Achivment[3]);
		}
		if(mGR.max == 4096 && !mGR.ach[4])
		{
			mGR.ach[4] = true;
			GameRenderer.mStart.UnlockAchievement(mGR.root.Achivment[4]);
		}
	}
}
