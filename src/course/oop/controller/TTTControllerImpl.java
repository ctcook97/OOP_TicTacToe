package course.oop.controller;

import course.oop.board.ThreeByThreeBoard;
import course.oop.player.HumanPlayer;

public class TTTControllerImpl implements TTTControllerInterface {
	
	HumanPlayer[] players = new HumanPlayer[2];
	ThreeByThreeBoard gameBoard;

	@Override
	public void startNewGame(int numPlayers, int timeoutInSecs) {
		gameBoard = new ThreeByThreeBoard();
		
	}

	@Override
	public void createPlayer(String username, String marker, int playerNum) {
		if (marker.length() > 3) {
			System.out.println("Marker must be 3 or less characters"); //for now
			return;
		}
		
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
		gameBoard.setSelection(row, col, currentPlayer);
		if(gameBoard.getSquare(row,col) > 0)
			return true;
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
		boardString += "\n";
		
		return boardString;
	}
	
	public String getSquare(int row, int col) {
		if(gameBoard.getSquare(row,col) > 0)
			return String.format("%3s", players[gameBoard.getSquare(row,col) - 1].getMarker());
		else
			return "   ";
	}

}
