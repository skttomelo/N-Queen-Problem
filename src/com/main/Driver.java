package com.main;

import java.util.ArrayList;
import java.util.Random;

public class Driver {
	
	public ArrayList<Board> gen_boards(int n, int size){
		ArrayList<Board> boards = new ArrayList<Board>(); // will hold all of our boards we will be looking at
		
		for(int i = 0; i<size; i++) {
    		boards.add(new Board(makeRandBoard(n), n));
        }
		return boards;
	}
	
	private int[][] makeRandBoard(int N){
		Random r = new Random();
		int current_row = 0;
		
		int[][] board = new int[N][N]; // our board will be 8x8
		for(int col = 0; col<N; col++) { // col : also known as the x-axis
			current_row = r.nextInt(N); // pick psuedo-random spot for the queen to be placed
    		for(int row = 0; row<N; row++) { // row : also known as the y-axis
    			// we make all spaces on the board 0 to indicate nothing is there and 1 later on to say there is a queen
    			if(current_row == row) board[col][row] = 1;
    			else board[col][row] = 0;        			
    		}
    	}
		
		return board;
	}
	
	public void HillClimb(Board b) {
		
	}
	
    public static void main(String[] args) {
    	Driver d = new Driver();
    	ArrayList<Board> boards = d.gen_boards(8, 1); // we create the boards we will be evaluating
    	
    	int highest_score_board = 0;
    	for(Board board : boards) { // loop through all boards and show them in console
    		
    		// for shits and giggles we will find the highest scoring board in the randomly generated boards
    		if(board.getScore() > highest_score_board) highest_score_board = board.getScore();
    		
    		System.out.println("The score of this board is: "+board.getScore());
    		System.out.println(board.toString());
    		
    		for(Board successor : board.getNeighbors()) {
    			System.out.println("The score of this successor is: "+successor.getScore());
        		System.out.println(successor.toString());
    		}
    	}
    	
    	System.out.println("the highest score of all the boards was "+highest_score_board);
    }
}
