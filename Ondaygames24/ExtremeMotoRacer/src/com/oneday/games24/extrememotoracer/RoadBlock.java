package com.oneday.games24.extrememotoracer;

public class RoadBlock {
	
	static int sNext = 0;
	static int BG = 0;
	
	int mTimes;
	int mTrack[];
	int counter = 0;
	
	public RoadBlock(int Times,int[] Track)
	{
		counter = 0;
		mTimes = Times;
		mTrack = new int[Track.length];
		for(int i=0;i<mTrack.length;i++)
			mTrack[i] = Track[i];
	}
	void update()
	{
		counter++;
		if(counter>=mTrack.length*mTimes)
		{
			counter = 0;
			sNext++;
			sNext = sNext%GameRenderer.mGR.mRoadBlock[GameRenderer.mGR.mPath].length;
			if(sNext==0)
			{
				BG++;
				BG %=4;
				if(BG == 3)
					GameRenderer.mGR.mPath = 1;
				else
					GameRenderer.mGR.mPath = 0;
			}
		}
	}
	static void reset()
	{
		sNext = 0 ;
		BG = 0;
		
	}
}
