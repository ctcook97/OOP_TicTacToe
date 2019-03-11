package course.oop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import course.oop.board.ThreeByThreeBoard;
import course.oop.player.HumanPlayer;

public class TTTControllerImpl implements TTTControllerInterface {
	
	HumanPlayer[] players = new HumanPlayer[2];
	ThreeByThreeBoard gameBoard;

	@Override
	public void startNewGame(int numPlayers, int timeoutInSecs) {
		gameBoard = new ThreeByThreeBoard();
		
		System.out.println("Please enter '1' if you wish to manually play the game with 2 players or one player vs the computer (For example the TTTDriver.java I wrote");
		System.out.println("Please enter '2' if you have written a custom driver to do the moves of a 2 player game for testing purposes (For example the SampleAutoTest)");
		Scanner s = new Scanner(System.in);
		System.out.print("Selection: ");
		int gameType = s.nextInt();
		if(gameType == 1) {
			if (numPlayers == 1) {
				playTwoPlayerGame(timeoutInSecs);
			}
			else {
				playTwoPlayerGame(timeoutInSecs);
			}
		}
		s.close();
		
		if(numPlayers == 1) {
			createPlayer("Computer", "COM", 2); //FOR NOW, a computer player is just a human player object since they are not different (yet)
		}
	}

	@Override
	public void createPlayer(String username, String marker, int playerNum) {
		if (playerNum != 1 && playerNum != 2) {
			System.out.println("Player number must be 1 or 2");
			return;
		}
		
		if(playerNum == 1)
			players[0] = new HumanPlayer(username, marker);
		else
			players[1] = new HumanPlayer(username, marker);
	}

	@Override
	public boolean setSelection(int row, int col, int currentPlayer) {
		return gameBoard.setSelection(row, col, currentPlayer);
	}

	@Override
	public int determineWinner() {
		return gameBoard.determineWinner();
	}

	@Override
	public String getGameDisplay() {
		
		String boardString = "";
				
		boardString += "   |   |   \n";
		boardString += getSquare(0,0) + "|";
		boardString += getSquare(0,1) + "|";
		boardString += getSquare(0,2) + "\n   |   |   \n-----------\n   |   |   \n";
		boardString += getSquare(1,0) + "|";
		boardString += getSquare(1,1) + "|";
		boardString += getSquare(1,2) + "\n   |   |   \n-----------\n   |   |   \n";
		boardString += getSquare(2,0) + "|";
		boardString += getSquare(2,1) + "|";
		boardString += getSquare(2,2) + "\n   |   |   ";
		boardString += "\n";
		
		return boardString;
	}
	
	public String getSquare(int row, int col) {
		if(gameBoard.getSquare(row,col) > 0)
			return String.format("%3s", players[gameBoard.getSquare(row,col) - 1].getMarker());
		else
			return "   ";
	}
	
	public void randomMove(int player) {
		//Keeps randomly picking squares until it finds an open one
		//Probably not hugely efficient but should not be an issue with 9 squares
		int ran = (int) Math.random()*9;
		while(! setSelection(ran/3, ran%3, player)) {
			continue;
		}
	}
	
	public void playTwoPlayerGame(int timeout) {
		int turn = 0;
		while (determineWinner() == 0) {
			humanMove(timeout, turn%2 + 1);
			turn++;
		}
	}
	
	public void humanMove(int timeout, int currentPlayer) {
		if(timeout <= 0) {
			timeout = 100000; //Cheap fix for now, If you entered no timeout there's really just a timeout of 100,000 seconds
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		long start = System.currentTimeMillis();
		try {
			System.out.print(players[currentPlayer-1].getUserName());
			System.out.println(", enter your desired row and column, seperated by a space. Must be integers between 0 and 2. Do not include any additional spaces");
			while(true) {
				while ((System.currentTimeMillis() - start) < timeout * 1000 && !in.ready()) {
					
				}
				if (in.ready()) {
				    String s = in.readLine();
				    int row = Integer.valueOf(s.substring(0,s.indexOf(" ")));
				    s = s.substring(s.indexOf(" ") + 1);
				    int col = Integer.valueOf(s);
				    if(setSelection(row, col, currentPlayer)) {
				    	System.out.println(getGameDisplay());
				    	break;
				    }
				    else {
				    	System.out.println("Try again:");
				    }
				} else {
				    System.out.println("Turn forfeited as timeout was reached.");
				    break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
