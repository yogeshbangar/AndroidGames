package com.hututu.game.bubblecandyunlimited;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
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
		switch (M.GameScreen) {	
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Hututu, 0, 0);
			if(Counter>71){
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash_other);
			}
			break;
		case M.GAMESPLASH:
			
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEOVER:
		case M.GAMECONG:
		case M.GAMEPAUSE:
			Draw_GameOver(gl);
			break;
		case M.GAMEOPTION:
			Draw_Option(gl);
			break;
			}
//		setting();
		Counter++;
	}
	public boolean TouchEvent(MotionEvent event) 
	{

		switch (M.GameScreen) {	
		case M.GAMELOGO:
			break;
		case M.GAMESPLASH:
			if(MotionEvent.ACTION_UP == event.getAction() &&
				CircRectsOverlap(.04f, -.38f, mGR.mTex_Play.width()*.5f, mGR.mTex_Play.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				M.GameScreen = M.GAMEMENU;
				M.sound10(R.drawable.click);
				M.play(R.drawable.splash_other);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEOVER:
		case M.GAMECONG:
		case M.GAMEPAUSE:
			Handle_GameOver(event);
			break;
		case M.GAMEOPTION:
			Handle_Option(event);
			break;
			}
		Handle(event);
		return true;
	}
	
	
	/*************************Game Start**********************************************/
	int setNewBall() {
		if(mGR.IsEndless){
			mGR.mPlayer.ResColor = (byte )M.mRand.nextInt(M.NOBAL);
			return mGR.mPlayer.ResColor;
		}
		int setNew = 0;
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				if (mGR.mCBubble[i][j].CBubble > emp) {
					setNew++;
				}
			}
		}
		if (setNew > 0) {
			setNew = M.mRand.nextInt(setNew);
			if(setNew==0)
				setNew=1;
			int new1 = 0;
			for (int i = 0; i < mGR.mCBubble.length && new1 < setNew; i++) {
				for (int j = 0; j < mGR.mCBubble[i].length && new1 < setNew; j++) {
					if (mGR.mCBubble[i][j].CBubble > emp) {
						mGR.mPlayer.ResColor = (byte)(mGR.mCBubble[i][j].CBubble % M.NOBAL);
						new1++;
					}
				}
			}
		}
		setNew = mGR.mPlayer.ResColor;
		return setNew;
	}
	
	Point currentPosition() {
		int posX = (int) Math.floor((mGR.by - mGR.mPlayer.y) / bdy);
		int posY = (int) Math.floor(((-bx + mGR.mPlayer.x) / bdx) + (bdx/2) * (posX % 2));
		if (posY > M.C-1)
			posY = M.C-1;
		if (posX > M.Row-1)
			posX = M.Row-1;
		if (posY < 0) 
			posY = 0;
		if (posX < 0) 
			posX = 0;
		return new Point(posX, posY);
	}
	ArrayList<CBubble> getNeighbors(Point p) {
		CBubble[][] grid = mGR.mCBubble;
		ArrayList<CBubble> list = new ArrayList<CBubble>();
		if ((p.x % 2) == 0) {
			if (p.y > 0) {
				list.add(grid[p.x][p.y - 1]);
			}
			if (p.y < M.C-1) {
				list.add(grid[p.x][p.y + 1]);
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
					list.add(grid[p.x - 1][p.y + 1]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
					list.add(grid[p.x + 1][p.y + 1]);
				}
			} else {
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
				}
			}
		} else {
			if (p.y < M.C-1) {
				list.add(grid[p.x][p.y + 1]);
			}
			if (p.y > 0) {
				list.add(grid[p.x][p.y - 1]);
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
					list.add(grid[p.x - 1][p.y - 1]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
					list.add(grid[p.x + 1][p.y - 1]);
				}
			} else {
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
				}

				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
				}
			}
		}
		return list;
	}

	void checkJump(ArrayList<CBubble> jump, CBubble compare) {
		if (compare.visited) {
			return;
		}
		mGR.mCBubble[compare.i][compare.j].visited = true;
		mGR.mCBubble[compare.i][compare.j].anim();
		if (mGR.mPlayer.fireColor == compare.CBubble) {
			checkJump(jump, this.getNeighbors(new Point(compare.i, compare.j)),compare);
		}
	}
	void checkJump(ArrayList<CBubble> jump, ArrayList<CBubble> neighbors,CBubble ball) {
		jump.add(ball);
		for (int i = 0; i < neighbors.size(); i++) {
			CBubble current = (CBubble) neighbors.get(i);//elementAt(i);
			if (current != null) {
				checkJump(jump, current);
			}
		}
	}

	int score(int color) {
		int scr = 321 + mGR.mPlayer.fireColor;
		if (color == mGR.mPlayer.fireColor)
			scr = 123 + mGR.mPlayer.fireColor;
		mGR.mScore += scr;
		if (mGR.IsEndless) {
			if (mGR.mHScore < mGR.mScore) {
				mGR.mHScore = mGR.mScore;
				mGR.playBest = true;
			}
		}
		return scr;
	}
	final byte emp = -1;
	
	
	void forFall() {
		int k = 0;
		int cnt2 = 0;
		for (int i = 0; i < M.C; i++) {
			if (mGR.mCBubble[0][i].CBubble > emp) {
				checkFall(mGR.mCBubble[0][i]);
			}
		}
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				if (mGR.mCBubble[i][j].CBubble > emp) {
					if (!mGR.mCBubble[i][j].checkFall) {
						mGR.mRCandy[k++].set((bx + (i % 2 == 0 ? 1 : 0)
								* (bdx / 2))
								+ j * bdx, mGR.by - i * bdy,
								mGR.mCBubble[i][j].CBubble, 10000 + (cnt2 += 1),
								mGR.mCBubble[i][j].candy,
								score(mGR.mCBubble[i][j].CBubble));
						mGR.mCBubble[i][j].reset();
					}
				}
			}
		}
		for (int j = 0; j < M.Row; j++)
			for (int i = 0; i < M.C; i++) {
				{
					mGR.mCBubble[j][i].checkFall = false;
				}
			}

	}
	
	void checkJump(int x, int y, int color) {
		int k =1;
		int cnt =10;
		int cnt2 =0;
		mGR.mCBubble[x][y].CBubble = (byte)color;
		ArrayList<CBubble> checkJump = new ArrayList<CBubble>();
		checkJump(checkJump, mGR.mCBubble[x][y]);
		if (checkJump.size() >= 3) {
			for (int i = 0; i < checkJump.size(); i++) {
				CBubble current = (CBubble) checkJump.get(i);
				mGR.mRCandy[k++].set((bx + (current.i % 2==0?1:0)*(bdx/2)) + current.j * bdx, mGR.by - current.i * bdy,current.CBubble,(color==current.CBubble)?cnt+=1:10000+(cnt2+=1),current.candy,score(current.CBubble));
				mGR.mCBubble[current.i][current.j].reset();
			}
			for (int i = 0; i < M.C; i++) {
				if (mGR.mCBubble[0][i].CBubble > emp) {
					checkFall(mGR.mCBubble[0][i]);
				}
			}
			for (int i = 0; i < mGR.mCBubble.length; i++) {
				for (int j = 0; j < mGR.mCBubble[i].length; j++) {
					if (mGR.mCBubble[i][j].CBubble > emp) {
						if (!mGR.mCBubble[i][j].checkFall) {
							mGR.mRCandy[k++].set((bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,mGR.mCBubble[i][j].CBubble,(color==mGR.mCBubble[i][j].CBubble)?cnt+=1:10000+(cnt2+=1),mGR.mCBubble[i][j].candy,score(mGR.mCBubble[i][j].CBubble));
							
							mGR.mCBubble[i][j].reset();
						}
					}
				}
			}
		} else if (mGR.IsEndless) {
			if (mGR.hitBall > 0)
				mGR.hitBall--;
			else {
				M.sound11(R.drawable.reset);
				mGR.newHit--;
				if(mGR.newHit <= 1)
					mGR.newHit = 5;
				mGR.hitBall = mGR.newHit;
				{
					for (int i = 0; i < mGR.mCBubble.length; i++) {
						for (int j = 0; j < mGR.mCBubble[i].length; j++) {
							if (mGR.mCBubble[i][j].CBubble > emp) {
								mGR.mCBubble[i][j].star=(byte)-M.mRand.nextInt(10);
							}
						}
					}
				}
				for (int j = M.Row - 1; j > 0; j--) {// 13
					for (int i = 0; i < M.C; i++) {// 9
						mGR.mCBubble[j][i].CBubble = mGR.mCBubble[j - 1][i].CBubble;
					}
				}
				{
					boolean zero = true;
					for (int i = 0; i < M.C; i++) {
						if (mGR.mCBubble[0][i].CBubble > emp)
							zero = false;
					}
					for (int i = 0; i < M.C; i++) {// 9
						mGR.mCBubble[0][i].set(M.mRand.nextInt(M.NOBAL), 0, i);
					}
					if(zero)
						for (int i = 0; i < M.C; i++) {// 9
							mGR.mCBubble[1][i].set(M.mRand.nextInt(M.NOBAL), 1, i);
						}
				}
				
				forFall();
			}
		}
		if (checkJump.size() < 3)
		{
			mGR.mJoin.set((bx + (x % 2 == 0 ? 1 : 0) * (bdx/2)) + y * bdx, mGR.by - x * bdy, 10, 0);
		}
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				mGR.mCBubble[i][j].visited = false;
				mGR.mCBubble[i][j].checkFall = false;
			}
		}
	}

	public void checkFall(CBubble check) {
		if (check.checkFall) {
			return;
		}
		mGR.mCBubble[check.i][check.j].checkFall = true;
		ArrayList<CBubble> v = getNeighbors(new Point(check.i, check.j));
		for (int i = 0; i < v.size(); i++) {
			CBubble current = (CBubble) v.get(i);

			if (current.CBubble > emp) {
				checkFall(current);
			}
		}
	}
	boolean checkCollision()
	{
		if (mGR.by + bdy < mGR.mPlayer.y) {
			int j = currentPosition().y;
			if (mGR.mCBubble[0][j].CBubble != -1 && (j > 0 && j < 8)) {
				float d1 = distance((bx + (bdx / 2)) + (j - 1) * bdx, mGR.by, mGR.mPlayer.x, mGR.mPlayer.x);
				float d2 = distance((bx + (bdx / 2)) + (j + 1) * bdx, mGR.by, mGR.mPlayer.x, mGR.mPlayer.x);
				if (mGR.mCBubble[0][j - 1].CBubble == -1
						&& mGR.mCBubble[0][j + 1].CBubble == -1) {
					if (d1 < d2) {
						j = j - 1;
					} else
						j = j + 1;
				} else if (mGR.mCBubble[0][j - 1].CBubble == -1) {
					j = j - 1;
				} else if (mGR.mCBubble[0][j + 1].CBubble == -1) {
					j = j + 1;
				}
			}
			
			
			mGR.mCBubble[0][j].CBubble = mGR.mPlayer.fireColor;
			checkJump(0, j, mGR.mPlayer.fireColor);
			mGR.mPlayer.set(0, false);
			mGR.mPlayer.fireColor = (byte)mGR.mPlayer.ResColor;
			if (delete() > emp)
				mGR.mPlayer.fireColor = (byte)delete();
			setNewBall();
		} else {
			for (int i = 0; i < mGR.mCBubble.length; i++) {
				for (int j = 0; j < mGR.mCBubble[i].length; j++) {
					if (mGR.mCBubble[i][j].CBubble > emp) {
						if (CirCir((bx + (i % 2 == 0 ? 1 : 0) * (bdx/2)) + j * bdx, mGR.by - i * bdy, redius, mGR.mPlayer.x, mGR.mPlayer.y, redius))
						{
							int p = 0; float dis = 10;
							Point point = currentPosition();
							ArrayList<CBubble> b = getNeighbors(point);
							for (int k = 0; k < b.size(); k++) {
								if(mGR.mCBubble[b.get(k).i][b.get(k).j].CBubble==-1) {
									p=k; break;
								}
							}
							for (int k = 0; k < b.size(); k++) {
								float d = distance(mGR.mPlayer.x, mGR.mPlayer.y, (bx + (b.get(k).i % 2 == 0 ? 1 : 0) * (bdx/2)) + b.get(k).j * bdx, mGR.by - b.get(k).i * bdy);
								if (dis > d && mGR.mCBubble[b.get(k).i][b.get(k).j].CBubble==-1) {
									dis = d;
									p = k;
								}
							}
							for (int k = 0; k < mGR.mRCandy.length; k++) {
								mGR.mRCandy[k].reset();
								}
							score(mGR.mPlayer.fireColor);
							ArrayList<CBubble> yogesh = getNeighbors(new Point(b.get(p).i,b.get(p).j));
							{
								int new1 =0;
								for (int k = 0; k < yogesh.size(); k++) {
									if(mGR.mCBubble[yogesh.get(k).i][yogesh.get(k).j].CBubble==emp /*|| mGR.mCBubble[yogesh.get(k).i][yogesh.get(k).j].Color==7*/)
										new1++;
								}
								if (yogesh.size() == new1)
									return false;
							}
							mGR.mCBubble[b.get(p).i][b.get(p).j].CBubble = mGR.mPlayer.fireColor;
							checkJump(b.get(p).i, b.get(p).j, b.get(p).CBubble);
							mGR.mPlayer.set(0, false);
							mGR.mPlayer.fireColor = mGR.mPlayer.ResColor;
							if (delete() > emp)
								mGR.mPlayer.fireColor = (byte)delete();
							setNewBall();
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	float distance(float cx1,float cy1,float cx2,float cy2)
	{
		return (float)Math.sqrt(((cx1 - cx2) * (cx1 - cx2)) + ((cy1 - cy2) * (cy1 - cy2)));
	}

	int delete() {
		int color = emp;
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				if (mGR.mCBubble[i][j].CBubble > emp) {
					if (mGR.mCBubble[i][j].CBubble == mGR.mPlayer.fireColor) {
						return emp;
					} else if(mGR.mCBubble[i][j].CBubble!=M.NOBAL){
						color = mGR.mCBubble[i][j].CBubble;
					}
				}
			}
		}
		return color;
	}

	void gameLogic() {
		if (mGR.mPlayer.move) {
			mGR.mPlayer.update();
			if(checkCollision())
				mGR.mPlayer.vx = mGR.mPlayer.vy =0;
			for (int i = 0,k=0; i < mGR.mAnim.length &&k<2; i++) {
				if(mGR.mAnim[i].anim<=0){
					mGR.mAnim[i].set(mGR.mPlayer.x-mGR.mPlayer.vx*k*.5f, mGR.mPlayer.y-mGR.mPlayer.vy*k*.5f,10,mGR.mPlayer.fireColor);
					k++;
				}
			}
		}
		boolean isGO = true;
		for (int i = mGR.mCBubble.length-1; i >=0 && isGO; i--) {
			for (int j = 0; j < mGR.mCBubble[i].length && isGO; j++) {
				if (mGR.mCBubble[i][j].CBubble > emp) {
					isGO = false;
					dis = ((mGR.by - i * bdy) - (M.BY - (M.Row - 1) * bdy));
				}
			}
		}

		for (int i = 0; i < mGR.mRCandy.length && isGO; i++) {
			if(mGR.mRCandy[i].count>0)
				isGO = false;
		}

		if (isGO) {
			M.mpstop();
			M.GameScreen = M.GAMECONG;
			if (mGR.win > 20) {
			} else
				mGR.win++;
		}
		
		
		if (dis < bdy * .4f) {
			if (mGR.win > 20) {
				M.mpstop();
				M.GameScreen = M.GAMEOVER;
				mGR.mTScore += mGR.mScore;
				GameRenderer.mStart.Achivement();
				GameRenderer.mStart.ShowInterstitial();
			} else
				mGR.win++;
		}
		
	}
	float dis = 10;
	final float redius = .06f;
	final float bdx=.21f ,bdy = .11f;
	final float bx=-.90f ;
	void Draw_Gameplay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
//		DrawTexture(gl, mGR.mTex_Reset,-.83f,-.74f);
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				if (mGR.mCBubble[i][j].CBubble > emp) {
					if (mGR.mCBubble[i][j].anim > 0) {
						DrawTextureS(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mCBubble[i][j].CBubble],
								(bx + (i % 2 == 0 ? 1 : 0) * (bdx / 2)) + j* bdx, mGR.by - i * bdy,
								mGR.mCBubble[i][j].anim * .02f + 1);
						mGR.mCBubble[i][j].anim--;
					} else
						DrawTexture(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mCBubble[i][j].CBubble],
								(bx + (i % 2 == 0 ? 1 : 0) * (bdx / 2)) + j* bdx, mGR.by - i * bdy);
					if (mGR.mCBubble[i][j].star < mGR.mTex_Star.length) {
						if (mGR.mCBubble[i][j].star > -1) {
							DrawTextureS(gl,mGR.mTex_Star[mGR.mCBubble[i][j].star],
									(bx + (i % 2 == 0 ? 1 : 0) * (bdx / 2)) + j* bdx, mGR.by - i * bdy,1.5f);
						}
						mGR.mCBubble[i][j].star++;
					}
				}
			}
		}
		if(mGR.mJoin.anim>0){
			if(mGR.mJoin.anim == 9)
				M.sound5(R.drawable.bubble_colusion);
			DrawTransScal(gl, mGR.mTex_Join, mGR.mJoin.x, mGR.mJoin.y,(10-mGR.mJoin.anim)*.02f+1,mGR.mJoin.anim*.1f);
			mGR.mJoin.anim--;
		}
		mGR.mTex_Canan.drawRotet2(gl, mGR.mPlayer.ang, ((float)(-6+Math.toRadians(mGR.mPlayer.ang))*.1f),-.95f, 1, .25f,1);
		if(Counter>10)
			DrawTexture(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mPlayer.fireColor], mGR.mPlayer.x, mGR.mPlayer.y);
		else
			DrawTransScal(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mPlayer.fireColor], mGR.mPlayer.x, mGR.mPlayer.y,(10-Counter)*.05f+1,Counter*.1f);
		DrawTexture(gl, mGR.mTex_CBar, -.46f, -.89f);
		for(int i =0;i<mGR.hitBall;i++){
			DrawTexture(gl, mGR.mTex_SBall, -.84f+.155f*i, -.95f+(i%2)*.122f);
		}
		DrawTexture(gl, mGR.mTex_CCircle, 0, -.905f);
		DrawTexture(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mPlayer.ResColor], 0, -.9f);
		for(int i=0;i<mGR.mRCandy.length;i++) {
			if(mGR.mRCandy[i].count>0) {
				if(mGR.mRCandy[i].color>=0){
					if(mGR.mRCandy[i].count > 2999){
							DrawTexture(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mRCandy[i].color%M.NOBAL], mGR.mRCandy[i].x, mGR.mRCandy[i].y);
					} else if (mGR.mRCandy[i].count > 0 && mGR.mRCandy[i].count < 11) {
						 mGR.mTex_Blast[0].drawSRGB(gl, mGR.mRCandy[i].x, mGR.mRCandy[i].y, mGR.mRCandy[i].count*.1f,M.COLOR[mGR.mRCandy[i].color][0]/255f,M.COLOR[mGR.mRCandy[i].color][1]/255f,M.COLOR[mGR.mRCandy[i].color][2]/255f ,1);
						mGR.mTex_Blast[1].drawSRGB(gl, mGR.mRCandy[i].x, mGR.mRCandy[i].y,.4f+ (10-mGR.mRCandy[i].count)*.1f, M.COLOR[mGR.mRCandy[i].color][0]/255f,M.COLOR[mGR.mRCandy[i].color][1]/255f,M.COLOR[mGR.mRCandy[i].color][2]/255f ,mGR.mRCandy[i].count*.1f);
						mGR.mTex_Blast[2].drawSRGB(gl, mGR.mRCandy[i].x, mGR.mRCandy[i].y,.4f+ (10-mGR.mRCandy[i].count)*.1f, M.COLOR[mGR.mRCandy[i].color][0]/255f,M.COLOR[mGR.mRCandy[i].color][1]/255f,M.COLOR[mGR.mRCandy[i].color][2]/255f ,mGR.mRCandy[i].count*.1f);
						if(mGR.mRCandy[i].count == 9)
							M.Blast();
					}
					else if(mGR.mRCandy[i].count>10){
						DrawTexture(gl, mGR.mTex_BCandy[mGR.mPlayer.ball][mGR.mRCandy[i].color], mGR.mRCandy[i].x, mGR.mRCandy[i].y);
					}
				}
				mGR.mRCandy[i].update();
			}
		}
		DrawTexture(gl, mGR.mTex_Line, 0, mGR.by+.05f);
		DrawTexture(gl, mGR.mTex_Line, 0, M.BY - (M.Row-1) * bdy);
		DrawTexture(gl, mGR.mTex_TBar, 0.0f, .91f);
		drawNumber(gl, mGR.mScore	 ,-.32f,.905f,1);
		drawNumber(gl, mGR.mHScore,.40f,.905f,1);
		
		DrawTransScal(gl,mGR.mTex_Paused, .80f, -.89f, mGR.mSel==2?1.1f:1.0f, mGR.mSel==2?0.5f:1);
		gameLogic();
	}
	

	boolean Handle_Gameplay(MotionEvent event) {
		mGR.mSel =0;
		if(CircRectsOverlap(.0f, -.89f, mGR.mTex_Paused.width()*.3f, mGR.mTex_Paused.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//Switch
		}
		if(CircRectsOverlap(.80f, -.89f, mGR.mTex_Paused.width()*.3f, mGR.mTex_Paused.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//Pause
		}
		
		if (mGR.mSel == 0)
		{
			float dx = M.ScreenWidth / 2;
			float dy = M.ScreenHieght * .9f;
			double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
			if (ang > 1.9f && ang < 4.3f && !mGR.mPlayer.move) {
				mGR.mPlayer.setAngle((float)ang);
			}
			
		}
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (mGR.mSel != 0) {
				switch (mGR.mSel) {
				case 1:
					byte temp = mGR.mPlayer.fireColor;
					mGR.mPlayer.fireColor = mGR.mPlayer.ResColor;
					mGR.mPlayer.ResColor = temp;
					Counter = 0;
						M.sound10(R.drawable.click);
					break;
				case 2:
					M.mpstop();
					M.GameScreen = M.GAMEPAUSE;
					M.sound10(R.drawable.click);
					break;
				}
				mGR.mSel = 0;
			} else {
				M.HIT();
				float dx = M.ScreenWidth / 2;
				float dy = M.ScreenHieght * .9f;
				double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
				if (ang > 1.9f && ang < 4.3f && !mGR.mPlayer.move) {
					mGR.mPlayer.set(ang, true);
					mGR.mPlayer.anim = 0;
				}
			}
		}
		return true;
	}
	/*************************Game End**********************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**************************Option Start**************************/
	void Draw_Option(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		for(int i =0;i<3;i++){
			DrawTransScal(gl, mGR.mTex_btn, 0, .27f-.27f*i,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?0.5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0], 0, .26f-.27f*0,mGR.mSel==0+1?1.1f:1,mGR.mSel==0+1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?1:0], 0, .26f-.27f*1,mGR.mSel==1+1?1.1f:1,mGR.mSel==1+1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_About, 0, .26f-.27f*2,mGR.mSel==2+1?1.1f:1,mGR.mSel==2+1?0.5f:1);
		
		
		DrawTransScal(gl, mGR.mTex_Back, .7f, .54f,mGR.mSel==10?1.1f:1,mGR.mSel==10?0.5f:1);
	}

	boolean Handle_Option(MotionEvent event) {
		mGR.mSel = 0;
		if(CircRectsOverlap(.7f, .54f, mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 10;
		}
		for(int i =0;i<3;i++)
		if(CircRectsOverlap(0, .27f-.27f*i, mGR.mTex_btn.width()*.5f, mGR.mTex_btn.Height()*.35f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = i+1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				M.setMusic = !M.setMusic;
				break;
			case 3:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 10:
				M.play(R.drawable.splash_other);
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel !=0)
				M.sound10(R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************** Option End **************************/
	/**************************Menu Start**************************/
	
	float sz=1,vsz = .01f;
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_btn, -.38f,-.08f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_btn, .41f,.24f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		
		
		DrawTransScal(gl, mGR.mTex_MenuTxt[0],-.38f,-.08f,mGR.mSel==1?1.1f:sz,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_MenuTxt[1], .41f,0.24f,mGR.mSel==2?1.1f:sz,mGR.mSel==2?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_MenuTxt[2], .04f,-.35f,mGR.mSel==3?1.1f:sz,mGR.mSel==3?0.5f:1);
		
		mGR.mTex_MenuTxt[2].drawRotet2(gl, Counter, 
				.04f,-.35f, mGR.mSel==3?1.1f:sz,0,mGR.mSel==3?0.5f:1);
		
		
		
		sz+=vsz;
		if(sz>1.05)
			vsz = -.003f;
		if(sz<0.95)
			vsz = 0.003f;
		boolean isStar = false;
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				if (mGR.mCBubble[i][j].star < 11) {
					if (mGR.mCBubble[i][j].star > -1) {
						mGR.mTex_Star[(i+j)%7].drawRotet2(gl, mGR.mCBubble[i][j].star*25, 
								(bx + (i % 2 == 0 ? 1 : 0) * (bdx / 2)) + j* bdx,
								(mGR.by+.1f) - i * bdy, 1, 0,(11-mGR.mCBubble[i][j].star)*.1f);
					}
					if(Counter %3==0)
					mGR.mCBubble[i][j].star++;
				}
				if(!isStar){
					int n = M.mRand.nextInt(mGR.mCBubble.length);
					int m = M.mRand.nextInt(mGR.mCBubble[i].length);
					if(mGR.mCBubble[n][m].star>10){
						mGR.mCBubble[n][m].star=0;
						isStar = true;
					}
				}
			}
		}
		
		
		
		/*DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		for(int i =0;i<3;i++){
			DrawTransScal(gl, mGR.mTex_btn, 0, .27f-.27f*i,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_MenuTxt[i], 0, .26f-.27f*i,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?0.5f:1);
		}*/
	}

	boolean Handle_Menu(MotionEvent event) {
		mGR.mSel = 0;
		/*for(int i =0;i<3;i++)
		if(CircRectsOverlap(0, .27f-.27f*i, mGR.mTex_btn.width()*.5f, mGR.mTex_btn.Height()*.35f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = i+1;
		}*/
		if(CircRectsOverlap(-.38f,-.08f, mGR.mTex_btn.width()*.5f, mGR.mTex_btn.Height()*.35f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(.41f,0.24f, mGR.mTex_btn.width()*.5f, mGR.mTex_btn.Height()*.35f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(.04f,-.35f,mGR.mTex_MenuTxt[2].width()*.5f, mGR.mTex_MenuTxt[2].Height()*.35f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.mpstop();
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset(0);
				M.play(R.drawable.main_gmeplay);
				break;
			case 2:
				M.mpstop();
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset(1);
				M.play(R.drawable.main_gmeplay);
				break;
			case 3:
				M.mpstop();
				M.GameScreen = M.GAMEOPTION;
				break;
			}
			if(mGR.mSel !=0)
				M.sound10(R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************** Menu End **************************/
	
	/**************************About Start**************************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_AbouTxt, 0, .14f);
		
		DrawTransScal(gl, mGR.mTex_fb	, -.40f,-.38f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back	, 0.02f,-.45f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_gp	, 0.42f,-.38f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
	}
	boolean Handle_About(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.40f,-.38f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.02f,-.45f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.42f,-.38f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				facebook();
				break;
			case 2:
				M.GameScreen = M.GAMEOPTION;
				break;
			case 3:
				google();
				break;
			}
			if(mGR.mSel !=0)
				M.sound10(R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/**************************About End**************************/
	
	
	/**************************GameOver Start**************************/
	void Draw_GameOver(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		
		if(M.GameScreen == M.GAMECONG)
			DrawTexture(gl, mGR.mTex_Congrat, 0, .45f);
		if(M.GameScreen == M.GAMEPAUSE)
			DrawTexture(gl, mGR.mTex_GPaused, 0, .45f);
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_GOver, 0, .45f);
		
		
		DrawTexture(gl, mGR.mTex_btn, 0, .20f);
		DrawTexture(gl, mGR.mTex_BScore, 0,.33f);
		drawNumber(gl, mGR.mHScore, 0, .20f,1);
		
		DrawTexture(gl, mGR.mTex_btn, 0, -.10f);
		DrawTexture(gl, mGR.mTex_Score, 0, 0.03f);
		drawNumber(gl, mGR.mScore, 0, -.10f,1);
		
		if(M.GameScreen == M.GAMEPAUSE)
			DrawTransScal(gl, mGR.mTex_Play , -.40f,-.38f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		else
			DrawTransScal(gl, mGR.mTex_Achiv , -.40f,-.38f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Retry , 0.02f,-.45f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader, 0.42f,-.38f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Home, .7f, .63f,mGR.mSel==10?1.1f:1,mGR.mSel==10?0.5f:1);
	}
	boolean Handle_GameOver(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.40f,-.38f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.02f,-.45f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.42f,-.38f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(.7f, .63f, mGR.mTex_Back.width()*.3f, mGR.mTex_Back.Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 10;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMEPAUSE){
					M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.main_gmeplay);
				}
				else{
					GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				}
				break;
			case 2:
				mGR.gameReset(mGR.mPlayer.ball);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.main_gmeplay);
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 10:
				M.play(R.drawable.splash_other);
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel !=0)
				M.sound10(R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/**************************GameOver End**************************/
	
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
//	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
//	{
//		Tex.drawFilp(gl, x, y);
//	}
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

	void drawNumber(GL10 gl, int no, float x, float y,int align) {
		float dx = mGR.mTex_Font[0].width() * .6f;
		String strs = "" + no;
		if(align == 1)
			x -=strs.length()*.5f*dx;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
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
}
