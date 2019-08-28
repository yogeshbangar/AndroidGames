package com.hututu.game.cricket.premier.league;

public class TeamInfo {
	int win =0;
	int draw = 0;
	int loose =0;

	int pld = 0;
	int point = 0;

	void result(int res) {
		pld++;
		point += res;
		if (res == 2) {
			win++;
		}
		if (res == 1) {
			draw++;
		}
		if (res == 0) {
			loose++;
		}
	}
	void reset(){
		win = 0;
		draw = 0;
		loose = 0;
		pld = 0;
		point =0;
	}
}
