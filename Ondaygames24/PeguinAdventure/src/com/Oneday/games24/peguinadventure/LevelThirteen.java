package com.Oneday.games24.peguinadventure;

import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;

public class LevelThirteen {
	boolean fall = false;
	
	Thirteen mThirteen;
	ThirteenObj mObj;
	
	SimplePlane mTex_BG,mTex_Panguin,mTex_Shadow;
	GameRenderer mGR;
	public LevelThirteen (GameRenderer _GameRenderer){
		mGR = _GameRenderer;
		mTex_BG			= GameRenderer.add("l13/l13.png");
		mTex_Panguin	= GameRenderer.add("l13/pengu.png");
		mTex_Shadow		= GameRenderer.add("l13/bubble_sadow.png");
		mThirteen = new Thirteen();
		mObj = new ThirteenObj();
	}
	void GameContinue() {
		mGR.mGameTime += System.currentTimeMillis();
	}

	void GameResart() {
		M.stop();
		mGR.mGameTime = 20000;
		mGR.isPlay = false;
		mGR.timesUp = 0;
		mGR.ClockCount = 0;
		mGR.mScore = 0;
		
	}
	void calculation()
	{
		mGR.mScore++;
		mGR.Total[mGR.mLSelect.mGameSel]++;
		mGR.mLSelect.Achievement(fall, 0);
	}
	void gameSet()
	{
		mThirteen.set(0,-.8f, 0, 0);
		mObj.set(.6f, .6f, M.mRand.nextInt(5));
	}
	void GameLogic()
	{
		mThirteen.update();
		if(Group.CirCir(mThirteen.x, mThirteen.y, mTex_Panguin.width()*.9f, mObj.x,mObj.y,mGR.mLsix.mTex_Fruit[0].Height()*.5f))
		{
			M.sound1(GameRenderer.mContext, R.drawable.jump_12);
			calculation();
			if(mObj.no==5)
				mGR.mLSelect.IncTime(5000);
			mObj.set((mThirteen.x>0?-.8f:.8f)*M.mRand.nextFloat(), (mThirteen.y>0?-.8f:.5f)*M.mRand.nextFloat(), M.mRand.nextInt(5));
			if(mGR.mScore%6==0)
				mObj.no =5;
			mGR.mLSelect.Animtin(mObj.x,mObj.y);
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		Group.DrawTexture(gl, mTex_BG, 0, 0);
		Group.DrawTexture(gl, mTex_Panguin, mThirteen.x, mThirteen.y);
		Group.DrawTexture(gl, mTex_Shadow, mThirteen.x, -.84f);
		if(mObj.no==5)
			Group.DrawTexture(gl, mGR.mLSelect.mTex_BWatch, mObj.x, mObj.y);
		else
			Group.DrawTexture(gl, mGR.mLsix.mTex_Fruit[mObj.no], mObj.x, mObj.y);
		mGR.mLSelect.Draw_Animtin(gl);
		GameLogic();
		mGR.mLSelect.GTime(gl);
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mLSelect.ClockScreen(event);
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			if(!mGR.isPlay)
			{
				mGR.mGameTime += System.currentTimeMillis();
				mGR.isPlay = true;
			}else if(mGR.mGameTime<System.currentTimeMillis())
			{
				if(mGR.timesUp < 5 && mGR.ClockCount < 5){
					/*mGR.mLSelect.ClockScreen(event);*/
				}
			}
			if(mGR.timesUp < 5 && mGR.ClockCount < 5){
				if(Group.CirCir(mThirteen.x, mThirteen.y, mTex_Panguin.width(), Group.screen2worldX(event.getX()),Group.screen2worldY(event.getY()), .02))
				{
					float xx =  M.world2screenX(Group.screen2worldX(event.getX()));
					float yy =  M.world2screenY(Group.screen2worldY(event.getY()));
					float pxx = M.world2screenX(mThirteen.x);
					float pyy = M.world2screenY(mThirteen.y);
					float cx =  xx - pxx;
					float cy =  yy - pyy;
					double d =  M.GetAngle(-cx, -cy);//vy, -vx
					cx = Group.screen2worldX(event.getX()) - mThirteen.x;
					cy = Group.screen2worldY(event.getY()) - mThirteen.y;
					float dis = (float)Math.sqrt(cx*cx+cy*cy);
					mThirteen.vx = (float)Math.cos(d)*dis*.5f;
					mThirteen.vy =-(float)Math.sin(d)*dis*.5f;
					M.sound2(GameRenderer.mContext, R.drawable.l_2_second_jump);
				}
			}
		}
		
		return true;
	}
	
}
