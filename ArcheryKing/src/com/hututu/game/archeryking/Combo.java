package com.hututu.game.archeryking;

public class Combo {
	boolean Achiev[][],BSet =false;
	boolean NewAchiv =false;
	int Color[]	= new int[4];
	int Counter=1000;
	int ColorAch=0;
	float Blast = 0;
	Animation mAni[];
	
	public Combo()
	{
		BSet =false;
		mAni	= new Animation[100];
		for(int i=0;i<mAni.length;i++)
		{
			mAni[i]	= new Animation();
		}
		Achiev		= new boolean[4][4];
		for(int i=0;i<Achiev.length;i++)
		{
			Achiev[i]	= new boolean[4];
		}
		for(int i=0;i<Color.length;i++)
		{
			Color[i]= 0;
		}
		ColorAch=0;
		Counter=1000;
	}
	void set()
	{
		Blast = 0;
		BSet =false;
		for(int i=0;i<mAni.length;i++)
		{
			mAni[i].set(-100, -100);
		}
	}
	void SetAnimation(float x,float y)
	{
		for(int i=0;i<mAni.length;i++)
		{
			mAni[i].set(x, y);
		}
	}
	void update()
	{
		Counter++;
		if(Blast>=1)
			Blast-=.5f;
		if(Blast<1 && !BSet)
		{
			BSet =true;
			SetAnimation(0,0);
			Blast =.99f;
		}
		animation();
	}
	void animation()
	{
		for(int i=0;i<mAni.length;i++)
		{
			if(mAni[i].x>-1 && mAni[i].y >-1 && mAni[i].x<1 &&mAni[i].y<1 && mAni[i].z>0&& mAni[i].t>0)
				mAni[i].update();
		}
	}
	void colorUpdate(int type)
	{
		for(int i=0;i<Color.length;i++)
		{
			if(type == i)
				Color[i]++;
			else
				Color[i]=0;
		}
		if(type<Color.length){
			switch (Color[type]) {
			case 3:
				Reset(3);
				if(!Achiev[type][3])
					NewAchiv = true;
				Achiev[type][3] = true;
				break;
			case 5:
				Reset(2);
				if(!Achiev[type][2])
					NewAchiv = true;
				Achiev[type][2] = true;
				break;
			case 7:
				Reset(1);
				if(!Achiev[type][1])
					NewAchiv = true;
				Achiev[type][1] = true;
				break;
			case 10:
				Reset(0);
				if(!Achiev[type][0])
					NewAchiv = true;
				Achiev[type][0] = true;
				break;
			}
		}
	}
	void Reset(int col)
	{
		NewAchiv = false;
		ColorAch = col;
		Blast=5;
		BSet = false;
		Counter=0;
		GameRenderer.mArrowNO += 10-col*2;
		M.sound7(GameRenderer.mContext, R.drawable.newachievement);
	}
}
