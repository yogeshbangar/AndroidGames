/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yogesh
 */
package com.onedaygames24.pokernpoker;
public class Dealer {
	int mBatChance 	= 0;
	int GameCounter	= 0;
	int mShowCard 	= 0;
	int mTableBat 	= 0;
	int mCurrentBat = 0;
	int mRaiseCount = 0;
	int mBigBat 	= 0;
	int mStart 		= 0;
	int mNewStart	= 0;
    void set(int BatChance,int TableBat,int CurrentBat)
    {
    	GameCounter	= 0;
    	mStart 		= mBatChance = BatChance;
    	mTableBat 	= TableBat;
    	mBigBat 	= mCurrentBat = CurrentBat;
    	mRaiseCount = 0;
    	mShowCard 	= 0;
    }

}
