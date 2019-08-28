
package com.htt.games.motospeedking;

public class Road {
	
	
	public static final byte ROD[][][]={
	
		  {{18, 2, 0, 0, 5,18,18},
		  { 3, 4, 0, 0, 6, 7,18},
		  {19, 0, 0, 0, 0,22, 1},
		  { 2, 0,20,10, 0,22,18},
		  { 2, 0,11,13, 0,22, 1},
		  {19, 0, 0, 0, 0, 5,18},
		  {19, 0, 0, 0, 0,22, 1},
		  {14,21, 0, 0,20,17, 1},
		  { 3, 4, 0, 0, 6, 7,18},
		  {19, 0, 0, 0, 0, 5, 1},
		  { 2, 0, 8,10, 0, 6, 7},
		  { 2, 0, 5, 2, 0, 0,22},
		  {19, 0, 5,14,15, 0,22},
		  { 2, 0,11,12,13, 0, 5},
		  { 2, 0, 0, 0, 0, 0, 5},
		  {19, 0, 0, 0, 0,16,17},
		  {14,15, 0, 0, 0, 5,18},
		  {18, 2, 0, 0, 0, 5, 1},
		  { 1,14,21, 0,20,17, 1},
		  {18, 3, 4, 0, 6, 7,18},
		  {18, 2, 0, 0, 0, 5, 1},
		  { 3, 4, 0, 0, 0, 6, 7},
		  { 2, 0, 0, 0, 0, 0, 5},
		  {19, 0, 0, 0, 0, 0, 5},
		  { 2, 0, 0, 0, 0, 0, 5},
		  {19, 0, 0, 0, 0, 0, 5}},

		  {{ 2, 0, 0, 0,22, 1,18},
		  { 2, 0, 0, 0, 6, 7, 1},
		  {19, 0, 0, 0, 0, 6, 7},
		  {14, 9,10, 0, 0, 0, 5},
		  { 1, 3, 4, 0, 0,16,17},
		  {18, 2, 0, 0, 0, 5, 1},
		  { 1,19, 0, 0,20,17,18},
		  {18, 2, 0, 0,22, 1, 1},
		  { 1,19, 0, 0, 5,18, 1},
		  {18, 2, 0, 0,22, 1,18},
		  { 1,14,21, 0,22,18, 1},
		  {18, 3, 4, 0, 6, 7, 1},
		  { 3, 4, 0, 0, 0, 6, 7},
		  {19, 0, 0, 0, 0, 0,22},
		  { 2, 0, 8, 9,10, 0,22},
		  {19, 0,23,12,13, 0, 5},
		  {19, 0, 0, 0, 0, 0,22},
		  {14,21, 0, 0, 0,16,17},
		  {18,14,21, 0,20,17,18},
		  {18, 3, 4, 0, 6, 7, 1},
		  { 1, 2, 0, 0, 0,22,18},
		  { 3, 4, 0, 0, 0, 6, 7},
		  { 2, 0, 0, 0, 0, 0, 5},
		  { 2, 0, 0, 0, 0, 0, 5},
		  {14,15, 0, 0,16, 9,17},
		  { 1,19, 0, 0, 5,18,18}},

		  {{18, 2, 0, 0, 0, 5,18},
		  { 1,19, 0, 0, 0,22, 1},
		  {18, 2, 0, 0, 0, 5,18},
		  { 1,14,21, 0,20,17, 1},
		  {18,18, 2, 0, 5, 1,18},
		  { 1, 3, 4, 0, 6, 7, 1},
		  { 3, 4, 0, 0, 0, 6, 7},
		  { 2, 0, 0, 0, 0, 0,22},
		  {19, 0, 0,16,10, 0, 5},
		  { 2, 0,20,17, 2, 0,22},
		  {19, 0, 5, 3, 4, 0, 5},
		  { 2, 0,11,13, 0, 0,22},
		  {19, 0, 0, 0, 0,16,17},
		  {14,21, 0, 0,20,17,18},
		  { 3, 4, 0, 0, 6, 7, 1},
		  {19, 0, 0, 0, 0,22,18},
		  { 2, 0, 0, 0, 0,22, 1},
		  {19, 0, 8,21, 0, 5,18},
		  { 2, 0, 5, 2, 0,22, 1},
		  {19, 0, 5, 2, 0, 5,18},
		  { 2, 0,11,13, 0,22, 1},
		  {19, 0, 0, 0, 0,22, 1},
		  {14,21, 0, 0,16,17,18},
		  {18, 2, 0,20,17,18, 1},
		  { 3, 4, 0, 6, 7, 1,18},
		  { 2, 0, 0, 0, 5,18, 1}},

		  {{18,18, 3, 4, 0, 5,18},
		  {18, 3, 4, 0, 0, 5, 1},
		  { 3, 4, 0, 0,16,17,18},
		  { 2, 0, 0,16,17,18, 1},
		  {14,21, 0, 5,18, 1,18},
		  {18, 2, 0, 6, 7,18, 1},
		  { 1,19, 0, 0, 6, 7,18},
		  { 1,14,21, 0, 0, 5, 1},
		  {18, 3, 4, 0, 0, 5,18},
		  { 1,19, 0, 0,20,17, 1},
		  {18,14,21, 0, 5,18, 1},
		  {18, 3, 4, 0,22, 1,18},
		  { 3, 4, 0, 0, 5,18, 1},
		  {19, 0, 0, 0, 6, 7,18},
		  { 2, 0, 0, 0, 0, 6, 7},
		  {19, 0,20,15, 0, 0,22},
		  { 2, 0, 5,14,15, 0, 5},
		  {19, 0, 5,18, 2, 0,22},
		  { 2, 0,23,12,13, 0, 5},
		  {19, 0, 0, 0, 0, 0,22},
		  {14,15, 0, 0, 0,20,17},
		  {18, 2, 0, 0, 0, 5,18},
		  { 1,14,21, 0,20,17,18},
		  {18,18, 2, 0, 5,18,18},
		  { 1, 3, 4, 0, 6, 7, 1},
		  {18, 2, 0, 0, 0, 5,18}},

		  {{ 2, 0, 0, 0, 0, 0, 5},
		  {14,21, 0, 0, 0,20,17},
		  {18,14,21, 0, 0, 6, 7},
		  { 1,18,14,21, 0, 0, 5},
		  {18, 1, 3, 4, 0, 0,22},
		  { 1, 3, 4, 0, 0,16,17},
		  {18, 2, 0, 0, 0,22, 1},
		  { 1,14,15, 0,16,17,18},
		  {18, 1,19, 0, 5,18, 1},
		  { 1, 3, 4, 0, 6, 7, 1},
		  {18, 2, 0, 0, 0, 5,18},
		  { 1,14,21, 0,20,17, 1},
		  { 1,18,19, 0,22, 1,18},
		  {18, 3, 4, 0, 6, 7, 1},
		  { 3, 4, 0, 0, 0, 6, 7},
		  {19, 0, 0, 0, 0, 0,22},
		  { 2, 0,20, 9,15, 0, 5},
		  {19, 0, 5, 3, 4, 0,22},
		  { 2, 0, 5,19, 0, 0, 5},
		  {19, 0, 5, 2, 0, 0,22},
		  { 2, 0,23,13, 0,16,17},
		  {19, 0, 0, 0, 0, 5,18},
		  {14,21, 0, 0,20,17, 1},
		  {18,14,21, 0, 6, 7,18},
		  { 1,18, 2, 0, 0,22, 1},
		  {18, 1,14,21, 0, 5,18}},
	};
}
