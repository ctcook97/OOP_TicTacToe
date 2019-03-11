package course.oop.player;

public class HumanPlayer {
	
	String userName;
	String marker;
	private int wins; //These will be used in later iterations
	private int losses;
	
	public HumanPlayer(String Name, String Marker) {
		userName = Name;
		marker = Marker;
		wins = 0;
		losses = 0;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getMarker() {
		return marker;
	}
	
}
