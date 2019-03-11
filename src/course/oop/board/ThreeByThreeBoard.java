package course.oop.board;

public class ThreeByThreeBoard {
	
	private int[][] board = new int[3][3];
	
	public boolean setSelection(int row, int col, int player) {
		
		if(row > 2 || col > 2 || row < 0 || col < 0) {
			System.out.println("Out of boundries");
			return false;
		}
		
		if(board[row][col] == 0) {
			board[row][col] = player;		
			return true;
		}
		else {
			System.out.println("Square already taken");
			return false;
		}
		
	}
	
	public int determineWinner() {
		//Check all the win lines
		if(board[1][1] != 0 && (
			(board[1][1] == board[0][0] && board[1][1] == board[2][2]) ||
			(board[1][1] == board[0][2] && board[1][1] == board[2][0]) ||
			(board[1][1] == board[0][1] && board[1][1] == board[2][1]) ||
			(board[1][1] == board[1][0] && board[1][1] == board[1][2]))){
			return board[1][1];
		}
		else if (board[0][0] != 0 &&(
			(board[0][0] == board[0][1] && board[0][0] == board[0][2]) ||
			(board[0][0] == board[1][0] && board[0][0] == board[2][0]))){
			return board[0][0];
		}
		else if (board [2][2] != 0 && (
			(board[2][2] == board[2][0] && board[2][2] == board[2][1]) ||
			(board[2][2] == board[0][2] && board[2][2] == board[1][2]))) {
			return board[2][2];
		}
		
		//See if every square has been marked, return still in progress if not
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				if (board[i][j] == 0)
					return 0;
			}
		}
		
		//Return tie
		return 3;
	}
	
	public int getSquare(int row, int col) {
		return board[row][col];
	}
	
}
