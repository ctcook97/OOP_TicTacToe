package course.oop.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import course.oop.controller.TTTControllerImpl;

public class TTTDriver {
	
	static TTTControllerImpl ticTacToe;
	
	public static void main(String[] args) {
		
		System.out.println(
				"  _______ _        _______           _______         \n" + 
				" |__   __(_)      |__   __|         |__   __|        \n" + 
				"    | |   _  ___     | | __ _  ___     | | ___   ___ \n" + 
				"    | |  | |/ __|    | |/ _` |/ __|    | |/ _ \\ / _ \\\n" + 
				"    | |  | | (__     | | (_| | (__     | | (_) |  __/\n" + 
				"    |_|  |_|\\___|    |_|\\__,_|\\___|    |_|\\___/ \\___|\n" + 
				"                                                     \n" + 
				"        by Cameron Cook\n");
		
		ticTacToe = new TTTControllerImpl();
		
		Scanner s = new Scanner(System.in);
		System.out.println("1 or 2 players: ");
		int numPlayers = Integer.valueOf(s.nextLine());
		System.out.println("Timeout: ");
		int timeout = Integer.valueOf(s.nextLine());
		System.out.println("Player 1 username: ");
		String username = s.nextLine();
		System.out.println("Player 1 marker (please limit marker to 3 characters): ");
		String marker = s.nextLine();
		ticTacToe.createPlayer(username, marker, 1);
		if(numPlayers == 2) {
			System.out.println("Player 2 username: ");
			username = s.nextLine();
			System.out.println("Player 2 marker (please limit marker to 3 characters): ");
			marker = s.nextLine();
			ticTacToe.createPlayer(username, marker, 2);
			System.out.println("Enter 'start' to start the game: ");
			while(true) {
				String start = s.nextLine();
				if(start.contentEquals("start")) {
					System.out.println("Game starting. At any time enter 'quit' to quit the game");
					ticTacToe.startNewGame(2, timeout);
					System.out.println(ticTacToe.getGameDisplay());
					playTwoPlayerGame(timeout);
					break;
				}
				System.out.println("Unrecognized command. Please type 'start' to start the game");
			}
		}
		else {
			while(true) {
				System.out.println("Enter 'start' to start the game: ");
				String start = s.nextLine();
				if(start.contentEquals("start")) {
					System.out.println("Game starting. At any time enter 'quit' to quit the game");
					ticTacToe.startNewGame(1, timeout);
					System.out.println(ticTacToe.getGameDisplay());
					playOnePlayerGame(timeout);
					break;
				}
				System.out.println("Unrecognized command. Please type 'start' to start the game");
			}
		}
		
	}
	
	public static void playOnePlayerGame(int timeout) {
		while(true) {
			
			if(!(readMove(timeout, 1)) || ticTacToe.determineWinner() > 0)
				break;
			randomMove(2);
			if(ticTacToe.determineWinner() > 0)
				break;
		}
		printFinishedMessage();
	}
	
	public static void playTwoPlayerGame(int timeout) {
		int turn = 0;
		while (!(readMove(timeout, turn%2 + 1)) || ticTacToe.determineWinner() == 0) {
			readMove(timeout, turn%2 + 1);
			turn++;
		}
		printFinishedMessage();
	}
	
	public static void printFinishedMessage() {
		switch(ticTacToe.determineWinner()) {
		case 1: 
			System.out.println(ticTacToe.getPlayerName(1) + " wins!");
			break;
		case 2: 
			System.out.println(ticTacToe.getPlayerName(2) + " wins!");
			break;
		case 3:
			System.out.println("Tie game");
			break;
		}
		System.out.println("   ___   _   __  __ ___    _____   _____ ___ \n" + 
				"  / __| /_\\ |  \\/  | __|  / _ \\ \\ / / __| _ \\\n" + 
				" | (_ |/ _ \\| |\\/| | _|  | (_) \\ V /| _||   /\n" + 
				"  \\___/_/ \\_\\_|  |_|___|  \\___/ \\_/ |___|_|_\\\n" + 
				"                                             \n");
	}
	
	public static boolean readMove(int timeout, int currentPlayer) {
		if(timeout <= 0) {
			timeout = 100000; //Cheap fix for now, If you entered no timeout there's really just a timeout of 100,000 seconds
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		long start = System.currentTimeMillis();
		try {
			System.out.print(ticTacToe.getPlayerName(currentPlayer));
			System.out.println(", enter your desired row and column, seperated by a space. Must be integers between 0 and 2");
			while(true) {
				while ((System.currentTimeMillis() - start) < timeout * 1000 && !in.ready()) {
					
				}
				if (in.ready()) {
				    String s = in.readLine();
				    while(s.charAt(s.length()-1) == ' ') { //Remove trailing spaces
				    	s = s.substring(0, s.length()-1);
				    }
				    if(s.equals("quit")) {
				    	System.out.println("quitting game");
				    	return false;
				    }
				    int row = Integer.valueOf(s.substring(0,s.indexOf(" "))); //get row and col
				    s = s.substring(s.indexOf(" ") + 1);
				    int col = Integer.valueOf(s);
				    if(ticTacToe.setSelection(row, col, currentPlayer)) {
				    	System.out.println(ticTacToe.getGameDisplay());
				    	break;
				    }
				    else {
				    	System.out.println("Try again:");
				    }
				} else {
				    System.out.println("Turn forfeited as timeout was reached."); //So you just lose your turn if you do not go on time. This means someone can go twice in a row
				    break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void randomMove(int player) {
		//Keeps randomly picking squares until it finds an open one
		//Probably not hugely efficient but should not be an issue with 9 squares
		//HOWEVER this does cause a lot of "square already taken" messages to get printed because it might have to try a bunch before finding one
		int ran = (int) (Math.random()*9);
		while(! ticTacToe.setSelection(ran/3, ran%3, player)) {
			ran = (int) (Math.random()*9);
		}
		System.out.println(ticTacToe.getGameDisplay());
	}
	
}
