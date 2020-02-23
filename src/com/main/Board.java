package com.main;

import java.util.Random;

public class Board {
	private int[][] board = null;
	private int N = 0;
	
	//we generate our board with N-queens
	public Board(int n) {
		N = n;
		
		Random r = new Random();
		int current_row = 0;
		
		board = new int[N][N]; // our board will be 8x8
		for(int col = 0; col<N; col++) { // col : also known as the x-axis
			current_row = r.nextInt(N); // pick psuedo-random spot for the queen to be placed
    		for(int row = 0; row<N; row++) { // row : also known as the y-axis
    			// we make all spaces on the board 0 to indicate nothing is there and 1 later on to say there is a queen
    			if(current_row == row) board[col][row] = 1;
    			else board[col][row] = 0;        			
    		}
    	}
	}
	
	// grabs queen row in given col
	private int getQueenRow(int x) {
		for(int y = 0; y<N; y++) {
			if(board[x][y] == 1) return y;
		}
		return -1; // will never return -1 because the board will always have a queen on each column
	}
	
	// to determine the score we check the number of collisions there are amongst the queens on the board
	public int getScore() {
		int score = 0;
		
		// we loop through each queen, and we will only regard the queens in front of it
		// because if there is a collision with a queen prior we will already have noted it
		for(int current_queen = 0; current_queen<N; current_queen++) {
			for(int next_queen = current_queen+1; next_queen<N; next_queen++) {
				// we check if the current queen is on the same row as the next queen to determine a collision
				if(getQueenRow(current_queen) == getQueenRow(next_queen)) score++;
				
				
				// if it is not then we check if the current queen connects with the next queen diagonally
				// if the absolute value of both queen's row subtracted are the same as the absolute value of their col subtracted
				// then they have a collision
				else if(Math.abs(current_queen - next_queen) == Math.abs(getQueenRow(current_queen) - getQueenRow(next_queen))) score++;
			}
		}
		
		return score;
	}
	
	public String toString() {
		String data = "";
		data += "-----------------\n";
		for(int row = 0; row<N; row++) {
			for(int col = 0; col<N; col++) {
				data += "|"+board[col][row];
			}
			data += "|\n-----------------\n";
		}
		data += "\n";
		return data;
	}
}
