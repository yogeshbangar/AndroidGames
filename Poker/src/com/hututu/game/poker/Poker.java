package com.hututu.game.poker;

public class Poker {
	byte[] cardvals	= new byte[5];      //  Holds values of dealt cards
	byte[] correct	= new byte[25];      //  Holds values of dealt cards
	byte mState = M.NOTHING;
	GameRenderer mGR;
	public Poker(GameRenderer gr)
	{
		mGR = gr;
	}
	byte[] sort(byte[] x)
	{
		byte temp;
		for(int i=0;i<x.length;i++)
		{
			for(int j=i+1;j<x.length;j++)
			{
				if(x[i]<x[j])//>
				{
					temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
		
		for(int i=0;i<x.length;i++)
		{
//			System.out.println(i+"     ------------  "+x[i]+"  ---------------- ");
		}
		
		return x;
	}

//  [10]  [11]  [12]  
//  [10]  [11]  [13]  
//  [10]  [11]  [14]  
//  [10]  [12]  [13]  
//  [10]  [12]  [14]  
//  [10]  [13]  [14]  
//  [11]  [12]  [13]  
//  [11]  [12]  [14]  
//  [11]  [13]  [14]  
//  [12]  [13]  [14] 	
//	
	
	byte[] copy(int pn,int i)
	{
		
		byte _cardvals[] = new byte[5];
		if(i==0)
		{
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		if(i==1){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[13];
		}
		
		if(i==2){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		if(i==3){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
		}	
		if(i==4){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		if(i==5){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		
		if(i==6){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
		}
		
		if(i==7){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		
		if(i==8){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		
		if(i==9){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[12];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
		}
		
		
		
		
		
		
		
		
		
		if(i==10){
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		if(i==11){
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		
		if(i==12){
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[14];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		if(i==13){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		if(i==14){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		if(i==15){
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[14];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		
		//Change1
		if(i==16){
			_cardvals[1] = mGR.cardShuffle[pn*2+0];
			_cardvals[0] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		
		if(i==17){
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[12];
		}
		//Change1
		
		
		return _cardvals;
	}
	
	
	
	byte checkwin(byte[] _cardvals,byte pn) 
	{
		mGR.mCard[pn].mBig = -1;
		int suits[] = new int[4];         //  To check for a flush
		int matched[] = new int[13];      //  To check for pairs, threes, fours
		int pairs = 0, threes = 0, fours = 0;
		boolean flush = false;
//		boolean  straight = false;
		//    var info = document.forms[0].elements[0];
		int won = 0;
		for ( int i = 0; i < cardvals.length; i++ )
			cardvals[i] = _cardvals[i];
		//	    cardvals.sort(compare);           //  Sort the cards
		cardvals = sort(cardvals);
		for ( int i = 0; i < 4; i++ ) {
			suits[i] = 0;                 //  Initialise suits array to zero
		}

		for ( int i = 0; i < 13; i++ ) {
			matched[i] = 0;               //  Initialise matched array to zero  
		}

		for ( int i = 0; i < 5; i++ ) {
			matched[cardvals[i] % 13]++;            //  Update matched for cards
			suits[(int) Math.floor(cardvals[i]/13)]++;    //  Update suits for cards
		}


		//  Check for pairs, threes and fours

		for ( int i = 0; i < 13; i++ ) {
			if ( matched[i] == 2 ) {
				if(mGR.mCard[pn].mBig!=0)
					mGR.mCard[pn].mBig = (byte)i;
				pairs++;
			}
			else if ( matched[i] == 3 ) {
				if(mGR.mCard[pn].mBig!=0)
					mGR.mCard[pn].mBig = (byte)i;
				threes++;
			}
			else if ( matched[i] == 4 ) {
				if(mGR.mCard[pn].mBig!=0)
					mGR.mCard[pn].mBig = (byte)i;
				fours++;
			}
		}


		//  Check for a flush

		for ( int i = 0; i < 4; i++ ) {
			if ( suits[i] == 5 ) {
				flush = true;
			}
		}

		/*if ( cardvals[4] - cardvals[1] == -3  &&              //  Consistent with 
				cardvals[4] - cardvals[0] == -12 &&              //  A, T, J, Q, K...
				flush ) {        //  If we also have a flush, then its a royal flush
			mState = M.ROYALFLASH;	//info.value = "Royal flush!"; won = bet * 2500;
		}*/
		if ( cardvals[0] - cardvals[3] == 3  &&              //  Consistent with 
				cardvals[0] - cardvals[4] == 12 &&              //  A, T, J, Q, K...
				flush ) {        //  If we also have a flush, then its a royal flush
			mState = M.ROYALFLASH;	won++;//info.value = "Royal flush!"; won = bet * 2500;
		}else if ( cardvals[0] - cardvals[4] == 4 && flush ) {
			mState = M.STRAIGHTFLUSH;won++;	//info.value = "Straight flush!"; won = bet * 250;
		}
		//  Sort cards by face value (necessary to check for a straight)
		for ( int i = 0; i < 5; i++ )
			cardvals[i] = (byte) (cardvals[i] % 13);
		//	    cardvals.sort(compare);
		cardvals = sort(cardvals);

		if ( won == 0 ) {           // Don't check further if we've already won
			if ( fours > 0 ) {
				mState = M.FOUROFKIND;	//info.value = "Four of a kind!"; won = bet * 100;
				for(int m = 0;m<5;m++)
					if(_cardvals[m]%13!=mGR.mCard[pn].mBig)
						_cardvals[m] =-1;
			}
			else if ( threes == 1 && pairs == 1 ) {
				mState = M.FULLHOUSE;	//info.value = "Full house!"; won = bet * 50;
			}
			else if ( flush ) {
				mState = M.FLUSH;	//info.value = "A flush!"; won = bet * 20;
			}
			else if ( (cardvals[0] - cardvals[4] == 12 ||cardvals[0] - cardvals[4] == 4) &&
					cardvals[0] - cardvals[3] == 3 &&
					cardvals[0] - cardvals[2] == 2 &&
					cardvals[0] - cardvals[1] == 1) {
					mState = M.STRAIGHT;	//info.value = "A straight!"; won = bet * 15;
				
			}
			else if ( threes > 0 ) {
				mState = M.THREEOFKIND;	//info.value = "Three of a kind!"; won = bet * 4;
				for(int m = 0;m<5;m++)
					if(_cardvals[m]%13!=mGR.mCard[pn].mBig)
						_cardvals[m] =-1;
			}
			else if ( pairs == 2 ) {
				mState = M.TWOPAIR;	//info.value = "Two pair!"; won = bet * 3;
				for(int m = 0;m<5;m++)
				{
					int n = 0;
					for(;n<5;n++)
					{
						if(_cardvals[m]%13==_cardvals[n]%13 && m!=n)
							break;
					}
					if(n==5)
					{
						_cardvals[m] = -1;
						break;
					}
				}
				
			}
			/*else if ( matched[0]  == 2 ||
					matched[10] == 2 ||             
					matched[11] == 2 ||             
					matched[12] == 2 )*/
			else if ( pairs == 1 ) {
				mState = M.PAIR;	//info.value = "Jacks or better!"; won = bet * 2;
				for(int m = 0;m<5;m++)
					if(_cardvals[m]%13!=mGR.mCard[pn].mBig)
						_cardvals[m] =-1;
			}
			else {
				mState = M.NOTHING;	//info.value = "No win! Bad luck!";
			}
		}
		for(int m = 0;m<5;m++)
		{
			correct[pn*5+m]=_cardvals[m];
		}
//		System.out.println("  ~~~~~~~~~~~~~~~~~~~~~~~~~~~   "+mState+"  ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return mState;
		//  Update winnings if we've won

//		if ( won > 0 ) {
//			winnings += won;
//			//	        document.forms[0].elements[2].value = winnings;
//			info.value += " You win $" + won + "!";
//		}
//		System.out.println("  ~~~~~~~~~~~~~~~~~~~~~~~~~~~   "+info.value+"  ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	
	byte CheckState(byte pn)
	{
		//Change1
		byte State[] = new byte[20];
		byte StateBig[] = new byte[20];
		//Change1
		byte _cardvals[] = new byte[5];
		_cardvals[0] = mGR.cardShuffle[pn*2+0];
		_cardvals[1] = mGR.cardShuffle[pn*2+1];
		
		for(int i=0;i<State.length;i++)
		{
			State[i] = 0;
		}
		if(mGR.mDealer.mShowCard < 3)
		{
			return isPaire(pn);
		}
		if(mGR.mDealer.mShowCard == 3)
		{
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			return checkwin(_cardvals,pn);
		}
		if(mGR.mDealer.mShowCard == 4)
		{
			int i=0;
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[1] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			int temp = 0;
			for(i =0;i<State.length;i++)
			{
				if(State[i] > temp)
				{
					temp = State[i];
					mGR.mCard[pn].mBig =StateBig[i];
					_cardvals = copy(pn, i);
					checkwin(_cardvals,pn);
				}
			}
			State = sort(State);
			return State[0]; 
			
		}
		if(mGR.mDealer.mShowCard == 5)
		{
			int i=0;
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[13];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			//
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[13];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[12];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[2] = mGR.cardShuffle[12];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			//
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[14];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[14];
			_cardvals[2] = mGR.cardShuffle[10];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			_cardvals[0] = mGR.cardShuffle[pn*2+0];
			_cardvals[1] = mGR.cardShuffle[13];
			_cardvals[2] = mGR.cardShuffle[14];
			_cardvals[3] = mGR.cardShuffle[11];
			_cardvals[4] = mGR.cardShuffle[12];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			
			//Change1
			_cardvals[1] = mGR.cardShuffle[pn*2+0];
			_cardvals[0] = mGR.cardShuffle[10];
			_cardvals[2] = mGR.cardShuffle[12];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[10];
			_cardvals[2] = mGR.cardShuffle[12];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			//Change1
			
			
			//Change2
			_cardvals[1] = mGR.cardShuffle[pn*2+0];
			_cardvals[0] = mGR.cardShuffle[10];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			
			
			_cardvals[1] = mGR.cardShuffle[pn*2+1];
			_cardvals[0] = mGR.cardShuffle[10];
			_cardvals[2] = mGR.cardShuffle[11];
			_cardvals[3] = mGR.cardShuffle[13];
			_cardvals[4] = mGR.cardShuffle[14];
			State[i] = checkwin(_cardvals,pn);
			StateBig[i] = mGR.mCard[pn].mBig;
			i++;
			//Change2
			
			
			
			
			
			int temp = 0;
			for(i =0;i<State.length;i++)
			{
				if(State[i] > temp)
				{
					temp = State[i];
					mGR.mCard[pn].mBig =StateBig[i];
					_cardvals = copy(pn, i);
					checkwin(_cardvals,pn);
				}
			}
			State = sort(State);
			return State[0];
		}
		return M.NOTHING;
	}
	byte isPaire(int playerNo)
    {
		if((mGR.cardShuffle[playerNo*2]%13)==(mGR.cardShuffle[playerNo*2+1]%13))
		{
			mGR.mCard[playerNo].mState = M.PAIR;
		}
		else
		{
			mGR.mCard[playerNo].mState = M.NOTHING;
		}
        return mGR.mCard[playerNo].mState;
    }
	
	byte st[] = new byte[5];
	void CheckGameWin()
	{
		for(byte i =0;i<5;i++)
		{
			if(mGR.mCard[i].mState > M.FOLD)
				mGR.mCard[i].mState = CheckState(i);
			System.out.println(i+"  state   = "+mGR.mCard[i].mState);
		}
		
		for(int i =0;i<5;i++)
		{
			st[i]=mGR.mCard[i].mState;
		}
		st =sort(st);
		System.out.println("~~~~~  state   = "+st[0]);
		int counter = 0;
		int total = 0;
		for(int i =0;i<5;i++)
		{
			mGR.mCard[i].mTotalCoin-=mGR.mCard[i].mBat;
			total+=mGR.mCard[i].mBat;
			mGR.mCard[i].mBat =0;
			if(st[0] != mGR.mCard[i].mState)
			{
				if(mGR.mCard[i].mState > M.FOLD)
					mGR.mCard[i].mState = M.SHOWCARD;
			}
			else
			{
				counter++;
			}
		}
		byte temp = -1;
		//if((st[0]==M.PAIR || st[0]==M.THREEOFKIND||st[0]==M.TWOPAIR||st[0]==M.FOUROFKIND||st[0]==M.FULLHOUSE) && counter >1)
		if(counter >1)
		{
			counter =0;
			for(int i =0;i<5;i++)
			{
				if(st[0] == mGR.mCard[i].mState)
				{
					boolean first=false,second=false;
					for(byte m =0;m<5;m++)
					{
						if(correct[i*5+m] == mGR.cardShuffle[i*2+0])
						{
							first = true;
						}
						if(correct[i*5+m] == mGR.cardShuffle[i*2+1])
						{
							second = true;
						}
					}
					if((first && second)||(!first && !second))
					{
						if((mGR.cardShuffle[i*2+0]%13 >mGR.cardShuffle[i*2+1]%13 || mGR.cardShuffle[i*2+0]%13 == 0) && mGR.cardShuffle[i*2+1]%13 != 0)
							mGR.mCard[i].mBig = mGR.cardShuffle[i*2+0];
						else
							mGR.mCard[i].mBig = mGR.cardShuffle[i*2+1];
					}
					else if(first)
						mGR.mCard[i].mBig = mGR.cardShuffle[i*2+0];
					else
						mGR.mCard[i].mBig = mGR.cardShuffle[i*2+1];
					
					if((mGR.mCard[i].mBig%13 > temp%13 || mGR.mCard[i].mBig%13 == 0) && temp%13!=0)
					{
						temp = mGR.mCard[i].mBig;
					}
				}
			}
			for(int i =0;i<5;i++)
			{
				if(st[0] == mGR.mCard[i].mState)
				{
					if(mGR.mCard[i].mBig%13 == temp%13)
					{
						counter++;
					}
					else
					{
						if(mGR.mCard[i].mState > M.FOLD)
							mGR.mCard[i].mState = M.SHOWCARD;
					}
				}
			}
		}
		if((st[0]==M.NOTHING) && counter >1)
		{
			temp =-1;
			counter =0;
			for(int i =0;i<5;i++)
			{
				if(st[0] == mGR.mCard[i].mState)
				{
					if(mGR.cardShuffle[i*2]%13== 0 || (mGR.cardShuffle[i*2]%13 > mGR.cardShuffle[i*2+1]%13) && mGR.mCard[i].mBig%13!=0)
						mGR.mCard[i].mBig = mGR.cardShuffle[i*2];
					else
						mGR.mCard[i].mBig = mGR.cardShuffle[i*2+1];
				}
			}
			for(int i =0;i<5;i++)
			{
				if(st[0] == mGR.mCard[i].mState)
				{
					if((mGR.mCard[i].mBig%13 > temp%13 || mGR.mCard[i].mBig%13 == 0) && temp%13!=0)
					{
						temp = mGR.mCard[i].mBig;
					}
				}
			}
			for(int i =0;i<5;i++)
			{
				if(st[0] == mGR.mCard[i].mState)
				{
					if(mGR.mCard[i].mBig%13 == temp%13)
					{
						counter++;
					}
					else
					{
						if(mGR.mCard[i].mState > M.FOLD)
							mGR.mCard[i].mState = M.SHOWCARD;
					}
				}
			}
		}
		
		for(byte m =0;m<correct.length;m++)
			correct[m] = -1;
		for(byte i =0;i<5;i++)
		{
			if(st[0] == mGR.mCard[i].mState)
			{
				mGR.mCard[i].mTotalCoin += total/counter;
				mGR.mCard[i].mBat = total/counter;
				CheckState(i);
//				for(byte m =0;m<correct.length;m++)
//					System.out.println(i+"  ~~~------------~~~~~~ "+correct[m]);
			}
		}
		M.GameScreen = M.GAMEWIN;
	}
	
	
	
	int main()
	{
		int a = 0;
		int b = 0;
		int c = 0;
		int k[] = {10,11,12,13,14};
		int n = 5;
		//for (n = 1; n <= 50; n++)
		{
		    for (a = 0; a <= n - 3; ++a)
		    {
		        for (b = a+1; b <= n-2; b++)
		        {
		            for (c = b+1; c <= n-1; c++)
		            {
		                    System.out.println("~~~~~~~~~~~~~~  ["+k[a]+"]  ["+k[b]+"]  ["+k[c]+"]  ");  
	                
		            }
		        }
		    }
//		    System.out.println(n+"================================================= "+n);
		}
	    return 0;
	}
	
	
	
	
	

}
