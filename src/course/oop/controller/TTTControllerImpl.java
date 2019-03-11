package course.oop.controller;

import course.oop.board.ThreeByThreeBoard;
import course.oop.player.HumanPlayer;

import java.util.HashMap;

public class TTTControllerImpl implements TTTControllerInterface {
	
	public HashMap<Integer, HumanPlayer> players = new HashMap();
	public ThreeByThreeBoard gameBoard;

	@Override
	public void startNewGame(int numPlayers, int timeoutInSecs) {
		gameBoard = new ThreeByThreeBoard();
	}

	@Override
	public void createPlayer(String username, String marker, int playerNum) {
		if (players.get(playerNum) != null) {
			System.out.println("Player number already taken");
			return;
		}
		
		if (marker.length() > 3) {
			System.out.println("Marker must be 3 or less characters");
			return;
		}
		
		if (playerNum < 1) {
			System.out.println("Player number must be a positive integer");
			return;
		}
		
		//DOES NOT currently check if the userName or marker is already taken
		players.put(new Integer(playerNum), new HumanPlayer(username, marker));
	}

	@Override
	public boolean setSelection(int row, int col, int currentPlayer) {
		gameBoard.setSelection(row, col, currentPlayer);
		return false;
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
		
		return boardString;
	}
	
	public String getSquare(int row, int col) {
		if(gameBoard.getSquare(row,col) > 0)
			return String.format("%3s", players.get(gameBoard.getSquare(row,col)).getMarker());
		else
			return "   ";
	}

}
