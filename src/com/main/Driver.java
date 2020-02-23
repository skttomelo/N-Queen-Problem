package com.main;

import java.util.ArrayList;

public class Driver {
	
	static ArrayList<Board> gen_boards(int n, int size){
		ArrayList<Board> boards = new ArrayList<Board>(); // will hold all of our boards we will be looking at
		
		for(int i = 0; i<size; i++) {
    		boards.add(new Board(n));
        }
		return boards;
	}
	
    public static void main(String[] args) {
    	ArrayList<Board> boards = gen_boards(8, 50); // we create the boards we will be evaluating
    	
    	int highest_score_board = 0;
    	for(Board board : boards) { // loop through all boards and show them in console
    		
    		// for shits and giggles we will find the highest scoring board in the randomly generated boards
    		if(board.getScore() > highest_score_board) highest_score_board = board.getScore();
    		
    		System.out.println("The score of this board is: "+board.getScore());
    		System.out.println(board.toString());
    	}
    	
    	System.out.println("the highest score of all the boards was "+highest_score_board);
    }
}
