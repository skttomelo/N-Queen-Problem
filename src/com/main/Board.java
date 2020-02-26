package com.main;

import java.util.ArrayList;
import java.util.Random;

public class Board {
	private int[][] board = null;
	private int N = 0;
	
	//we generate our board with N-queens
	public Board(int[][] board, int n) {
		N = n;
		this.board = board;
	}
	
	// we generate all possible queen moves based off the current board in relation to N=8,
	// there will be 8*7 successors or 56 possible successors to the current board to be exact
	public ArrayList<Board> getNeighbors(){
		ArrayList<Board> neighbors = new ArrayList<Board>();
		
		// x = col; y = row
		for(int x = 0; x<N; x++) {
			int curr_queen_row = getQueenRow(x);
			for(int y = 0; y<N; y++) {
				// if we are at the current queen's location we pass to the next loop through so as to not generate duplicate states
				if(y == curr_queen_row) continue;
				
				int[][] neighbor = new int[N][N];
				for(int i = 0; i<board.length; i++) {
					for(int v = 0; v<board.length;v++) {
						neighbor[i][v] = board[i][v];
					}
				}
				
				neighbor[x][y] = 1;
				neighbor[x][curr_queen_row] = 0;
				neighbors.add(new Board(neighbor, N));
			}
		}
		
		return neighbors;
	}
	
	
	// grabs queen row in given col
	private int getQueenRow(int x) {
		for(int y = 0; y<N; y++) {
			if(board[x][y] == 1) return y;
		}
		return -1; // will never return -1 because the board will always have a queen on each column
	}
	
	// returns N
	public int getN() {
		return N;
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
	
	// compare's current board to target solution to see if the target board has a higher score
	// the higher score indicates that we are at our peak/plateau
	public boolean isPeakOrPlateau(Board solution) {
		return solution.getScore() >= getScore();
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
