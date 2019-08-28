package com.hututu.game.TrainCrash;
public class GameLevel {
	
    GameRenderer mGR;
     public GameLevel(GameRenderer _mGR)
     {
	    mGR =_mGR; 
     }
     float getDis(float x1,float y1,float x2,float y2)
     {
     	float d=0;
     	 float X = x2-x1;
     	 float Y = y2-y1;
     	       d = (float)Math.sqrt(X*X+Y*Y); 
     	return d;
     }
     void Tile8to12logic(int ir,int jc)
     {
     	 for(int i=0;i<mGR.mTrainNo;i++)
     		{
     	       if(getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y-(1.6f*mGR.mTrain[i].vy),mGR.mTrain[i].x,mGR.mTrain[i].y)<.07f) //For Turn WithOut Signal
     			{
     	    	   	String s ="";
         	   		s = mGR.mTrain[i].vx+"";
         	   		if(s.contains("E"))
     	   			  mGR.mTrain[i].vx=0;
         	   		s = mGR.mTrain[i].vy+"";
     	   			if(s.contains("E"))
     	   				mGR.mTrain[i].vy=0;
     		    	switch(mGR.mTile[ir][jc].mTNo)
     		    	{
     		    	   case 8:
     		    	   case 139:
     		    		  if(!mGR.mTrain[i].isMove && mGR.mTrain[i].rCnt==0)
     		    		  {
     	    			      
     		    			 if(mGR.mTrain[i].vx<0)
     						   mGR.mTrain[i].setMove(true,90,3.85f);
     		    			 if(mGR.mTrain[i].vy<0)
     						    mGR.mTrain[i].setMove(true,-90,7f);
     		    		  }
     		    		  break;
     		    	   case 9:
     		    	   case 136:
     		    		    if(!mGR.mTrain[i].isMove && mGR.mTrain[i].rCnt==0)
     			    		  {
     			    			 if(mGR.mTrain[i].vx<0)
     							   mGR.mTrain[i].setMove(true,-90,3.85f);
     			    			 if(mGR.mTrain[i].vy>0)
     							    mGR.mTrain[i].setMove(true,90,7f);
     			    		  }
     		    		   break;
     		    	   case 10:
     		    	   case 137:
     		    		   if(!mGR.mTrain[i].isMove && mGR.mTrain[i].rCnt==0)
     		    		   {
     	    				 if(mGR.mTrain[i].vy>0)
     		    			    mGR.mTrain[i].setMove(true,-90,7f);
     	    				 if(mGR.mTrain[i].vx>0)
         					 	mGR.mTrain[i].setMove(true,90,3.85f);
     		    		   }
     		    		   break;
     		    	   case 11:
     		    	   case 138:
     		    		   if(!mGR.mTrain[i].isMove && mGR.mTrain[i].rCnt==0)
     		    		   {
     	    				 if(mGR.mTrain[i].vy<0)
     		    			    mGR.mTrain[i].setMove(true,90,7);
     	    				 if(mGR.mTrain[i].vx>0)
         					 	mGR.mTrain[i].setMove(true,-90,3.85f);
     		    		   }
     		    		   break;
     		    	}
     			}
     	      if(getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mBogi[i].vx),mGR.mTile[ir][jc].y-(1.6f*mGR.mBogi[i].vy),mGR.mBogi[i].x,mGR.mBogi[i].y)<.07f) //For Bogi
     			{
     	    		String s ="";
         	   		s = mGR.mBogi[i].vx+"";
         	   		if(s.contains("E"))
     	   			  mGR.mBogi[i].vx=0;
         	   		s = mGR.mBogi[i].vy+"";
     	   			if(s.contains("E"))
     	   			  mGR.mBogi[i].vy=0;
     		    	switch(mGR.mTile[ir][jc].mTNo)
     		    	{
     		    	   case 8:
     		    	   case 139:
     		    		    if(!mGR.mBogi[i].isMove && mGR.mBogi[i].rCnt==0)
     		    		    {
     		    		      if(mGR.mBogi[i].vx<0)
     						    mGR.mBogi[i].setMove(true,90,3.85f); 
     		    		      if(mGR.mBogi[i].vy<0)
     	    		    	   mGR.mBogi[i].setMove(true,-90,7f);
     		    		    }
     		    		   break;
     		    	   case 9:
     		    	   case 136:
     		    		   if(!mGR.mBogi[i].isMove && mGR.mBogi[i].rCnt==0)
     		    		    {
     		    		      if(mGR.mBogi[i].vx<0)
     						    mGR.mBogi[i].setMove(true,-90,3.85f); 
     		    		      if(mGR.mBogi[i].vy>0)
     	    		    	   mGR.mBogi[i].setMove(true,90,7f);
     		    		    }
     		    		   break; 
     		    	   case 10:
     		    	   case 137:
     		    		   if(!mGR.mBogi[i].isMove && mGR.mBogi[i].rCnt==0)
     		    		   {
     		    			   if(mGR.mBogi[i].vx>0) 
     						    mGR.mBogi[i].setMove(true,90,3.85f);
     		    			   if(mGR.mBogi[i].vy>0) 
     						    mGR.mBogi[i].setMove(true,-90,7f);
     		    		   }
     		    		   break;
     		    	   case 11:
     		    	   case 138:
     		    		   if(!mGR.mBogi[i].isMove && mGR.mBogi[i].rCnt==0)
     		    		   {
     		    			   if(mGR.mBogi[i].vx>0) 
     						    mGR.mBogi[i].setMove(true,-90,3.85f);
     		    			   if(mGR.mBogi[i].vy<0) 
     						    mGR.mBogi[i].setMove(true,90,7f);
     		    		   }
     		    		   break;
     		    	}
     			}
     		}
     }
     void FindTrainHome(int ir,int jc)
     {
     	for(int i=0;i<mGR.mTrainNo;i++)
     	{
     		if(getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y-(5f*mGR.mTrain[i].vy),mGR.mTrain[i].x,mGR.mTrain[i].y)<.07f) //Train End Point
     		{
     		 	switch(mGR.mTile[ir][jc].mTNo)
     			{
     			    case 210:case 211:case 212:case 213: //Brown Train
	     			    	if(mGR.mTrain[i].no==M.BROWNTRAIN)
	     			    	{
	     			    		if(mGR.mTrain[i].speed>=1)
	     			    		{
	     			    		    M.rightSound(GameRenderer.mContext,R.drawable.right_destination);	
	     			    			mGR.mFlag[M.BROWNTRAIN].SetFlag(mGR.mTile[ir][jc].x+(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y+(5f*mGR.mTrain[i].vy),255f,100f,0f);
	     			    			mGR.mWinCnt++;
	     			    			mGR.root.SetParticle(mGR.mTile[ir][jc].x,mGR.mTile[ir][jc].y);
	     			    		}
	     			    	}
	     			    	else
	     			    	{
	     			    		if(mGR.mTrain[i].speed>=1)
	     			    		{
	     			    			M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
	     			    			mGR.mLoosecnt++;
	     			    		}
	     			    	}
     			    	StopTrain(i);
     		    			
     			       break;
     			   case 214:case 215:case 216:case 217: //Yello Train
	     				   if(mGR.mTrain[i].no==M.YELLOWTRAIN)
	     			    	{
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.rightSound(GameRenderer.mContext,R.drawable.right_destination);
	     						 mGR.mFlag[M.YELLOWTRAIN].SetFlag(mGR.mTile[ir][jc].x+(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y+(5f*mGR.mTrain[i].vy),235f,222f,0f);
		   			    		 mGR.mWinCnt++;
		   			    		 mGR.root.SetParticle(mGR.mTile[ir][jc].x,mGR.mTile[ir][jc].y);
	     					  }
	     			    	}
	     				   else
	     				    {
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination); 
	     			    		 mGR.mLoosecnt++;
	     					  }
	     				    }
     				        StopTrain(i);	
 				       break;
     			   case 218:case 219:case 220:case 221: //Green Train
	     				   if(mGR.mTrain[i].no==M.GREENTRAIN)
	     			    	{
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.rightSound(GameRenderer.mContext,R.drawable.right_destination);
	     						 mGR.mFlag[M.GREENTRAIN].SetFlag(mGR.mTile[ir][jc].x+(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y+(5f*mGR.mTrain[i].vy),117f,211f,45f);
	   			    			 mGR.mWinCnt++;
	   			    			 mGR.root.SetParticle(mGR.mTile[ir][jc].x,mGR.mTile[ir][jc].y);
	     					  }
	     			    	}
	     				   else
	     				   {
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
     			    			 mGR.mLoosecnt++;
	     					  }
	     				   }
     				        StopTrain(i);
 				       break;
     			   case 222:case 223:case 224:case 225: //Blue Train
	     				   if(mGR.mTrain[i].no==M.BLUETRAIN)
	     			    	{
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.rightSound(GameRenderer.mContext,R.drawable.right_destination);
	     						 mGR.mFlag[M.BLUETRAIN].SetFlag(mGR.mTile[ir][jc].x+(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y+(5f*mGR.mTrain[i].vy),65f,201f,251f); 
	   			    			 mGR.mWinCnt++;
	   			    			 mGR.root.SetParticle(mGR.mTile[ir][jc].x,mGR.mTile[ir][jc].y);
	     					  }
	     			    	}
	     				   else
	     				   {
	     					  if(mGR.mTrain[i].speed>=1)
	     					  {
	     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
   			    				 mGR.mLoosecnt++;
	     					  }
	     				   }
	     				    StopTrain(i);	
     				   break;
 			   		
     			}
     		}
     	}
     }
     void FindStartHome(int ir,int jc)
     {
     	for(int i=0;i<mGR.mTrainNo;i++)
     	{
     		if(getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mTrain[i].vx),mGR.mTile[ir][jc].y-(1.6f*mGR.mTrain[i].vy),mGR.mTrain[i].x,mGR.mTrain[i].y)<.07f) //Train Start Point
     		{
     		 	switch(mGR.mTile[ir][jc].mTNo)
     			{
     			    case 194:case 195:case 196:case 197: //Brown Train
     			    	if(mGR.mTrain[i].no!=M.BROWNTRAIN)
     			    	{
     			    		if(mGR.mTrain[i].speed>=1)
     			    		{
     			    			M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
   			    				mGR.mLoosecnt++;
     			    		}
     			    		StopTrain(i);
     			    	}
     		    			
     			    	break;
     			   case 198:case 199:case 200:case 201: //Yello Train
     				   if(mGR.mTrain[i].no!=M.YELLOWTRAIN)
     			    	{
     					  if(mGR.mTrain[i].speed>=1)
     					  {
     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
 			    		 	 mGR.mLoosecnt++;
     					  }
     					    StopTrain(i);
     			    	}
     				    	
     				   break;
     			   case 202:case 203:case 204:case 205: //Green Train
     				   if(mGR.mTrain[i].no!=M.GREENTRAIN )
     			    	{
     					  if(mGR.mTrain[i].speed>=1)
     					  {
     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
		    				 mGR.mLoosecnt++;
     					  }
     					   StopTrain(i);
     			    	}
     				   break;
     			   case 206:case 207:case 208:case 209: //Blue Train
     				   if(mGR.mTrain[i].no!=M.BLUETRAIN )
     			    	{
     					  if(mGR.mTrain[i].speed>=1)
     					  {
     						 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
		    	  			 mGR.mLoosecnt++;
     					  }
     					   StopTrain(i);
     			    	}
     				   break;
     			   case 12:case 13:case 14:case 15:
     			   case 128:case 129:case 130:case 131:
		   			   	  if(mGR.mTrain[i].speed>=1)
		   			   	  {
		   			   		 M.wrongSound(GameRenderer.mContext,R.drawable.wrong_destination);
	    	  			     mGR.mLoosecnt++;
		   			   	  }
   				   		  StopTrain(i);
 				   	   break;
     				
     			}
     		}
     	}
     }
	void LevelLogic(int ir,int jc)
	{
			 for(int t=0;t<mGR.mTrainNo;t++)
			 {
			   for(int k=0;k<mGR.mArrowNo;k++)
			   {	
				   if(getDis(mGR.mArrow[k].x-(1.6f*mGR.mTrain[t].vx),mGR.mArrow[k].y-(1.6f*mGR.mTrain[t].vy),mGR.mTrain[t].x,mGR.mTrain[t].y)<.07f && getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mTrain[t].vx),mGR.mTile[ir][jc].y-(1.6f*mGR.mTrain[t].vy),mGR.mTrain[t].x,mGR.mTrain[t].y)<.07f)
				   {
					   switch(mGR.mTile[ir][jc].mTNo)
					   {
					      case 160:
					      case 230:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mTrain[t].vy>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,90,7f);
					    		  }
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,-90,3.85f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    	  }
					    	  break;
					      case 161:
					      case 228:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vy>0 || mGR.mTrain[t].vx<0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,-90,3.85f);
					    		  }
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			  TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 162:
					      case 232:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
					    			 {
					    			   if(mGR.mTrain[t].dir==0)
					    			   {
				    	        	     mGR.mTrain[t].setMove(true,90,3.85f);
					    			   }
					    			 }
					    		  }
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
					    			 {
					    				 if(mGR.mTrain[t].dir==0)
					    				 {
				    	        	       mGR.mTrain[t].setMove(true,-90f,7f);
					    				 }
					    			 }
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  
					    	  }
					    	  break;
					      case 168:
					      case 235:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }  
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mTrain[t].vy>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vy>0)
					    		  {
					    			  if(mGR.mTrain[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }  
					    	  }
					    	  break; 
					      case 181:
					      case 233:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vx<0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,90,7f);
					    		  }
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	  mGR.mTrain[t].setMove(true,-90,3.85f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vy<0 && mGR.mTrain[t].dir==0)
					    		  {
					    			  TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 182:
					      case 229:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vy<0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mTrain[t].vx>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mTrain[t].vy>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vx>0&& mGR.mTrain[t].dir==0)
					    		  {
				    				  TrainTukker(t);
					    		  }	
					    		  
					    	  }
					    	  break;
					      case 183:
					      case 231:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vy>0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mTrain[t].vy<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vx<0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 184:
					      case 234:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mTrain[t].vx>0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mTrain[t].vx<0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,-90,3.85f);
					    		  }
					    		  if(mGR.mTrain[t].vy>0)
					    		  {
					    			 if(!mGR.mTrain[t].isMove && mGR.mTrain[t].rCnt==0)
				    	        	   mGR.mTrain[t].setMove(true,90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mTrain[t].vy>0 && mGR.mTrain[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					   }
			       }
				   
				   if(getDis(mGR.mArrow[k].x-(1.6f*mGR.mBogi[t].vx),mGR.mArrow[k].y-(1.6f*mGR.mBogi[t].vy),mGR.mBogi[t].x,mGR.mBogi[t].y)<.07f && getDis(mGR.mTile[ir][jc].x-(1.6f*mGR.mBogi[t].vx),mGR.mTile[ir][jc].y-(1.6f*mGR.mBogi[t].vy),mGR.mBogi[t].x,mGR.mBogi[t].y)<.07f)
				   {
					   switch(mGR.mTile[ir][jc].mTNo)
					   {
					      case 160:
				    	  case 230: 
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		   
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mBogi[t].vy>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,90,7f);
					    		  }
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,-90,3.85f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    	  }
					    	  break;
					      case 161:
				    	  case 228: 
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vy>0 || mGR.mBogi[t].vx<0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,-90,3.85f);
					    		  }
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			  TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 162:
				    	  case 232: 
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
					    			 {
				    	        	   mGR.mBogi[t].setMove(true,90,3.85f);
					    			 }
					    		  }
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
					    			 {
				    	        	   mGR.mBogi[t].setMove(true,-90,7f);
					    			 }
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }
					    		  
					    	  }
					    	  break;
					      case 168:
					      case 235:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }  
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mBogi[t].vy>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vy>0)
					    		  {
					    			  if(mGR.mBogi[t].dir==0)
					    			  {
					    				  TrainTukker(t);
					    			  }
					    		  }  
					    	  }
					    	  break; 
					      case 181:
					      case 233:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vx<0 && mGR.mBogi[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,90,7f);
					    		  }
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	  mGR.mBogi[t].setMove(true,-90,3.85f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vy<0 && mGR.mBogi[t].dir==0)
					    		  {
					    			  TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 182:
					      case 229:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vy<0 && mGR.mBogi[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mBogi[t].vx>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mBogi[t].vy>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vx>0&& mGR.mBogi[t].dir==0)
					    		  {
				    				  TrainTukker(t);
					    		  }	
					    		  
					    	  }
					    	  break;
					      case 183:
					      case 231:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vy>0 && mGR.mBogi[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,90,3.85f);
					    		  }
					    		  if(mGR.mBogi[t].vy<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,-90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vx<0 && mGR.mBogi[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					      case 184:
					      case 234:
					    	  if(mGR.mArrow[k].Open==1)
					    	  {
					    		  if(mGR.mBogi[t].vx>0 && mGR.mBogi[t].dir==0)
					    		  {
			    				    TrainTukker(t);
					    		  }
					    		  if(mGR.mBogi[t].vx<0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,-90,3.85f);
					    		  }
					    		  if(mGR.mBogi[t].vy>0)
					    		  {
					    			 if(!mGR.mBogi[t].isMove && mGR.mBogi[t].rCnt==0)
				    	        	   mGR.mBogi[t].setMove(true,90,7f);
					    		  }
					    	  }
					    	  else
					    	  {
					    		  if(mGR.mBogi[t].vy>0 && mGR.mBogi[t].dir==0)
					    		  {
					    			  TrainTukker(t);
					    		  }
					    	  }
					    	  break;
					   }
				    }
		        }
	        }
		   Tile8to12logic(ir, jc);
		   FindTrainHome(ir, jc);
		   FindStartHome(ir, jc);

	}
  void StopTrain(int no)
  {
	  mGR.mTrain[no].SlowSpeed(.35f);
	  if(mGR.mBogi[no].x!=100 && mGR.mBogi[no].y!=100)
        mGR.mBogi[no].SlowSpeed(.35f);
  }
  void TrainTukker(int no)
  {
	  if(!mGR.mTrain[no].isCollide)
	  {
		 M.CrashSound(GameRenderer.mContext,R.drawable.crash); 
		 mGR.mTrain[no].isCollide =true;
	  }
	  mGR.mTrain[no].SlowSpeed(.35f);
	  if(mGR.mBogi[no].x!=100 && mGR.mBogi[no].y!=100)
	  {
		  if(!mGR.mBogi[no].isCollide)
			 mGR.mBogi[no].isCollide =true;
         mGR.mBogi[no].SlowSpeed(.35f);
	  }
	  if(!mGR.isHilana && mGR.mTrain[no].speed>.5f)
	  {
		  mGR.isHilana =true;
	      mGR.mHilanaCnt=0;
	      mGR.mTrain[no].ang-=10;
	     if(mGR.mBogi[no].x!=100 && mGR.mBogi[no].y!=100)
    	  mGR.mBogi[no].ang+=10f;
	  }
	  mGR.mLoosecnt++;
  }
 
}
