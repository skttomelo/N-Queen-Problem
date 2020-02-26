package com.main;

import java.util.ArrayList;
import java.util.Random;

public class Driver {

	// Here are the links to github repos that I have used aside from reading the book
	// to further my understanding of the hill climbing (and rand restart) so that I
	// may implement the algorithms into  my code for the N-Queen Problem
	// The books 'code' based off the link provided in the homework: https://github.com/aimacode/aima-python/blob/master/search.py
	// search.py was the only thing I looked at to gather my most basic understanding of how I might implement this program
	// and the other link falls here: https://github.com/JKnighten/hill-climbing/blob/master/src/main/java/com/knighten/ai/hillclimb/nqueens/NQueensProblem.java
	// I looked at other things within this repo, but more or less between the book and I saw here I developed my understanding of 
	// the homework at hand
	
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
	
	public Board HillClimb(Board b) {
		boolean peak_plateau = false;
		
		Board initial = b;
		while(peak_plateau == false && initial.getScore() != 0) {
			// loop through all neighbors and find the best scoring one
			Board current_guess = initial;
			for(Board neighbor : initial.getNeighbors()) {
				if(neighbor.getScore() < initial.getScore()) current_guess = neighbor;
			}
			
			//check if solution is peak or plateau
			if(initial.isPeakOrPlateau(current_guess)) peak_plateau = true;
			else initial = current_guess;
		}
		
		return initial;
	}
	
	public Board HillClimbRandRestart(Board b) {
		Board initial = b;
		while(initial.getScore() != 0) {
			// loop through all neighbors and find the best scoring one
			Board current_guess = initial;
			for(Board neighbor : initial.getNeighbors()) {
				if(neighbor.getScore() < initial.getScore()) current_guess = neighbor;
			}
			
			//check if solution is peak or plateau
			if(initial.isPeakOrPlateau(current_guess)) initial = new Board(makeRandBoard(b.getN()), b.getN());
			else initial = current_guess;
		}
		
		return initial;
	}
	
    public static void main(String[] args) {
    	Driver d = new Driver();
    	int sample_size = 1;
    	int board_size = 0;
    	
    	// we get data from 5 diferent sample sizes: 10, 100, 1,000; 10,000; 100,000
    	for(int val = 0; val < 5; val++) {
    		sample_size *= 10;
    		
    		ArrayList<Board> boards = d.gen_boards(8, sample_size); // we create the boards we will be evaluating
        	
    		board_size = boards.size();
    		
        	double avg_score = 0;
        	double success_rate = 0;
        	double hc_avg_score = 0; // will hold all scores from hill climb and avg them
        	double hc_success_rate = 0; // hill climb success rate
        	double hcrr_avg_score = 0;
        	double hcrr_success_rate = 0;
        	
        	
        	//variables for tracking time in milli
        	long start = 0, end = start, hc_time = start, hcrr_time = start;
        	
        	// I didn't want to separate my algorithms into different for loops as well as this one, but I needed too
        	// because I wanted to see how long it took each algorithm to run entirely on all the generated boards
        	for(Board board : boards) {
        		avg_score += board.getScore();
        		if(board.getScore() == 0) success_rate++;
        	}
        	
        	avg_score = avg_score/board_size;
        	success_rate = (success_rate/board_size)*100;
        	
        	System.out.println("The Sample Size for this program is: "+sample_size);
        	System.out.println("The avg score of all boards before Hill Climb and Hill Climb Rand Restart: "+avg_score);
        	System.out.println("The success rate of all the boards before Hill Climb and Hill Climb Rand Restart: "+success_rate+"%\n");
        	
        	start = System.nanoTime();
        	for(Board board : boards) { // loop through all boards and show them in console
        		Board hill_climb = d.HillClimb(board);
        		
        		hc_avg_score += hill_climb.getScore();
        		if(hill_climb.getScore() == 0) hc_success_rate++;
        	}
        	end = System.nanoTime();
    		hc_time = end-start;
    		
        	hc_avg_score = hc_avg_score/board_size;
        	hc_success_rate = (hc_success_rate/board_size)*100;
    		
        	System.out.println("The avg score of all boards after Hill Climb: "+hc_avg_score);
        	System.out.println("The success rate of all the boards after Hill Climb: "+hc_success_rate+"%");
        	System.out.println("Hill Climb runtime Total Time (nanoseconds): "+hc_time+"\n");
        	
        	start = System.nanoTime();
        	for(Board board : boards) {
        		Board hill_climb_rand_restart = d.HillClimbRandRestart(board);
        		
        		hcrr_avg_score += hill_climb_rand_restart.getScore();
        		if(hill_climb_rand_restart.getScore() == 0) hcrr_success_rate++;
        	}
        	end = System.nanoTime();
    		hcrr_time = end-start;

        	hcrr_avg_score = hcrr_avg_score/board_size;
        	hcrr_success_rate = (hcrr_success_rate/board_size)*100;
        	
        	System.out.println("The avg score of all boards after Hill Climb Rand Restart: "+hcrr_avg_score);
        	System.out.println("The success rate of all the boards after Hill Climb Rand Restart: "+hcrr_success_rate+"%");
        	System.out.println("Hill Climb Rand Restart runtime Total Time (nanoseconds): "+hcrr_time);
        	System.out.println("\n---------------------------------------------------------------------------------\n");
    	}
    	
    }
}
