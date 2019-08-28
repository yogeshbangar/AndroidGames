package com.httgames.actionzombiejump;

public class Level {
	
	
	static final int[][][] Lvl=
	{
			  {{ 0, 8, 0, 0, 0, 7, 0, 0, 2, 0}},

			  {{ 0, 0, 0, 8, 1, 0, 0, 0, 0, 0},
			  { 0, 8, 1, 2, 7, 1, 0, 0, 0, 2}},

			  {{ 0, 0, 0, 8, 3, 0, 0, 0, 0, 0},
			  { 0, 0, 0, 0, 0, 0, 0, 0, 8, 0},
			  { 0, 7, 1, 0, 0, 0, 8, 1, 1, 1}},

			  {{ 0, 0, 0, 0, 8, 0, 0, 0, 0, 0},
			  { 0, 0, 8, 1, 0, 1, 0, 0, 0, 0},
			  { 8, 1, 1, 0, 3, 0, 1, 0, 0, 0}},

			  {{ 0, 0, 0, 0, 8, 1, 0, 0, 0, 0},
			  { 0, 0, 8, 1, 1, 1, 1, 0, 0, 0},
			  { 8, 1, 1, 1, 2, 2, 2, 0, 0, 0}},

			  {{ 0, 0, 0, 0, 0, 0, 8, 0, 0, 0},
			  { 0, 0, 0, 0, 8, 1, 1, 0, 0, 0},
			  { 0, 0, 8, 1, 2, 2, 2, 1, 0, 0}},

			  {{ 0, 0, 0, 0, 0, 8, 0, 0, 0, 0},
				  { 0, 0, 0, 8, 1, 1, 0, 0, 0, 0},
				  { 0, 8, 1, 1, 1, 2, 0, 0, 8, 2}},

			  {{ 0, 0, 0, 0, 0, 8, 3, 0, 0, 0},
			  { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
			  { 0, 0, 8, 1, 1, 0, 0, 0, 2, 0}},

			  
			  
			  {{ 0, 0, 0, 0, 8, 1, 0, 0, 0, 0},
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			  { 0, 0, 8, 1, 1, 0, 0, 0, 8, 0}},

			  {{ 0, 0, 2, 2, 2, 0, 0, 0, 0, 0},
			  { 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
			  { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0},
			  { 8, 0, 0, 0, 0, 0, 8, 0, 0, 8}},


	};
	
	
	
	/*
	static final int[][][] Lvl=
	{
			  {{ 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}},

			  {{ 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
			  { 0, 1, 1, 1, 1, 1, 0, 0, 3, 0}},

			  {{ 0, 0, 0, 8, 1, 0, 0, 0, 0, 0},
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			  { 0, 1, 0, 0, 0, 0, 1, 0, 1, 0}},

			  {{ 0, 0, 0, 0, 0, 0, 8, 0, 0, 0},
			  { 0, 0, 8, 0, 1, 1, 1, 0, 0, 0}},

			  {{ 0, 0, 0, 8, 1, 1, 1, 0, 0, 0},
			  { 0, 1, 2, 2, 2, 2, 2, 0, 0, 0}},
	};
	
	
	
	static final int[][][] Lvl1={
	  {{ 0, 0, 1, 0, 0, 1, 0, 0, 0, 0}},//1
	

	  {{ 2, 0, 0, 8, 1, 0, 8, 1, 0, 0}},//2
	

	  {{ 0, 0, 7, 1, 0, 0, 0, 0, 0, 0},//3
	  { 0, 1, 3, 3, 3, 9, 1, 2, 0, 0}},
	

	  {{ 0, 0, 0, 5, 5, 5, 0, 0, 0, 0},//4
	  { 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
	  { 0, 1, 0, 0, 0, 0, 0, 0, 1, 2}},
	

	  {{ 0, 0, 0, 0, 0, 4, 0, 0, 0, 0},//5
	  { 0, 8, 1, 0, 0, 1, 0, 0, 0, 0},
	  { 1, 0, 3, 3, 0, 0, 0, 3, 0, 0}},
	  
	  {{ 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},//6
	  { 0, 0, 8, 1, 1, 1, 0, 0, 0, 0},
	  { 2, 1, 0, 0, 6, 6, 1, 0, 0, 2}},
	  
	  {{ 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},//7
	  { 0, 0, 0, 8, 1, 1, 8, 1, 8, 1},
	  { 0, 8, 1, 1, 1, 1, 1, 1, 1, 1}},
	  
	  
	  {{ 0, 8, 1, 8, 1, 3, 0, 0, 0, 0},//8
		  { 1, 2, 3, 3, 3, 1, 0, 0, 7, 1}},


  {{ 0, 0, 8, 1, 2, 0, 1, 0, 0, 0},//9
  { 9, 1, 8, 1, 1, 1, 1, 0, 0, 0},
  { 1, 0, 0, 4, 4, 0, 0, 2, 0, 0}},

  {{ 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},//10
  { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
  { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
  { 8, 1, 0, 0, 0, 2, 0, 8, 1, 0}},
	  
	  
	  
	  {{ 0, 0, 0, 8, 1, 2, 0, 8, 1, 0},
	  { 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
	  { 0, 1, 1, 1, 2, 2, 0, 0, 0, 0}},
	  
	  {{ 0, 0, 0, 0, 8, 1, 0, 0, 0, 0},
	  { 0, 0, 8, 1, 8, 1, 8, 1, 0, 0},
	  { 8, 1, 8, 1, 8, 1, 8, 1, 1, 1}},
	  
	  {{ 0, 0, 8, 1, 1, 0, 0, 0, 0, 0},
	  { 0, 0, 8, 1, 0, 0, 0, 0, 0, 0},
	  { 0, 1, 0, 0, 0, 7, 1, 2, 0, 1}},
	  
	  {{ 0, 0, 8, 1, 1, 2, 0, 0, 0, 0},
	  { 8, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	  { 1, 1, 1, 1, 1, 1, 1, 0, 7, 1}},
	  
	  {{ 0, 5, 5, 0, 0, 0, 0, 0, 0, 0},
	  { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	  { 0, 0, 0, 0, 1, 3, 0, 0, 0, 1},
	  { 1, 2, 2, 1, 1, 1, 1, 0, 1, 1}},
	  
	  {{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
	  { 0, 2, 0, 0, 8, 1, 1, 0, 0, 0},
	  { 1, 1, 0, 1, 1, 1, 1, 0, 7, 1}},
	  
	  {{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
	  { 0, 0, 0, 0, 0, 8, 1, 1, 1, 1},
	  { 1, 0, 8, 1, 0, 0, 0, 0, 0, 0}},
	  
	  {{ 0, 0, 0, 0, 0, 0, 8, 1, 0, 0},
	  { 1, 0, 0, 0, 8, 1, 0, 1, 8, 1},
	  { 0, 0, 2, 1, 6, 1, 6, 1, 6, 1}},
	  
	  {{ 0, 0, 0, 8, 1, 8, 1, 8, 1, 0},
	  { 1, 2, 1, 0, 3, 3, 3, 3, 3, 0}},
	  
	  {{ 0, 0, 0, 0, 0, 8, 1, 0, 0, 0},
	  { 8, 1, 1, 2, 8, 1, 1, 1, 0, 0}},
	  
	  {{ 0, 7, 1, 7, 1, 7, 1, 7, 1, 0},
	  { 1, 0, 3, 4, 3, 4, 3, 4, 3, 0}},
	  
	  {{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
	  { 0, 1, 1, 0, 0, 0, 0, 0, 1, 1},
	  { 1, 1, 1, 1, 3, 1, 3, 1, 2, 0}},
	  
	  {{ 0, 0, 0, 0, 1, 8, 1, 8, 1, 0},
	  { 1, 0, 0, 1, 1, 0, 1, 0, 0, 0},
	  { 0, 8, 1, 1, 1, 0, 5, 0, 0, 0}},
	  
	  {{ 0, 0, 0, 0, 0, 2, 8, 1, 1, 0},
	  { 1, 0, 0, 0, 8, 1, 1, 1, 1, 0},
	  { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0}},
	  
	  {{ 1, 0, 0, 8, 1, 0, 0, 8, 1, 0},
	  { 1, 3, 1, 3, 1, 3, 1, 3, 1, 2}},
	  
	  {{ 0, 0, 0, 8, 1, 1, 3, 0, 0, 0},
	  { 0, 8, 1, 1, 1, 1, 1, 1, 0, 0},
	  { 0, 0, 0, 3, 2, 2, 3, 0, 0, 0}},
	  
	  {{ 0, 0, 8, 1, 0, 8, 1, 8, 1, 0},
	  { 8, 1, 5, 5, 5, 5, 5, 5, 5, 1}},
	  
	  {{ 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
	  { 0, 0, 0, 8, 1, 0, 2, 0, 0, 0},
	  { 0, 0, 0, 8, 1, 8, 1, 0, 0, 1},
	  { 0, 8, 1, 0, 0, 0, 0, 0, 1, 4}},
	  
	  {{ 0, 0, 0, 0, 0, 8, 1, 8, 1, 0},
	  { 1, 0, 0, 8, 1, 0, 1, 0, 1, 0},
	  { 4, 1, 1, 4, 1, 4, 1, 4, 1, 4}},
	  
	  {{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	  { 1, 8, 1, 0, 0, 0, 0, 8, 1, 0},
	  { 1, 4, 1, 4, 1, 1, 0, 2, 2, 2}},
	  
	  {{ 0, 0, 0, 0, 2, 0, 2, 0, 2, 0},
	  { 0, 7, 1, 1, 1, 1, 1, 1, 1, 1},
	  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 8}},
	  
	  {{ 0, 8, 1, 0, 0, 0, 0, 0, 0, 0},
	  { 1, 8, 1, 8, 1, 0, 0, 8, 1, 1},
	  { 1, 8, 1, 8, 1, 8, 1, 4, 4, 4}},
	  
	  {{ 0, 0, 0, 0, 3, 0, 0, 0, 0, 0},
	  { 0, 0, 0, 3, 1, 3, 0, 0, 0, 0},
	  { 1, 0, 0, 1, 1, 1, 0, 8, 1, 0},
	  { 1, 0, 0, 0, 0, 0, 0, 8, 1, 0}},
	  
	  {{ 0, 0, 0, 8, 1, 0, 0, 0, 0, 0},
	  { 8, 1, 1, 0, 1, 8, 1, 1, 0, 0},
	  { 1, 0, 0, 0, 1, 0, 0, 0, 8, 1}},
	  
	  {{ 0, 0, 0, 0, 0, 2, 1, 0, 0, 0},
	  { 1, 2, 0, 8, 1, 1, 1, 7, 1, 0},
	  { 1, 1, 1, 0, 0, 0, 0, 2, 0, 2}},
	  
	  {{ 0, 0, 0, 1, 0, 2, 0, 0, 0, 0},
	  { 0, 8, 1, 0, 1, 1, 0, 0, 8, 1},
	  { 1, 0, 0, 0, 0, 0, 8, 1, 0, 1}},
	  
	  {{ 0, 8, 1, 1, 0, 0, 0, 8, 1, 0},
	  { 1, 1, 6, 6, 6, 1, 1, 2, 1, 2}},
	  
	  {{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
	  { 0, 8, 1, 0, 2, 8, 1, 0, 0, 0},
	  { 1, 4, 1, 4, 1, 4, 1, 4, 1, 4}},
	  
	  {{ 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	  { 1, 1, 1, 0, 8, 1, 0, 0, 8, 1},
	  { 4, 4, 4, 1, 0, 1, 0, 1, 1, 1}},
	  
	  {{ 2, 0, 0, 0, 0, 0, 3, 3, 3, 0},
	  { 1, 0, 0, 1, 0, 8, 1, 1, 1, 0},
	  { 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}}
	};*/
}
