package course.oop.main;

import java.util.Scanner;

import course.oop.controller.TTTControllerImpl;

public class TTTDriver {
	
	public static void main(String[] args) {
		
		TTTControllerImpl ticTacToe = new TTTControllerImpl();
		
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
			ticTacToe.startNewGame(2, timeout);
		}
		
		
	}
	
}
