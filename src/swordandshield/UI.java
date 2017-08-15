package swordandshield;

public interface UI {

	void displayBoard(Board b);
	void displayPlayablePieces(Player p);
	void println(String s);
	String getCreatePhase();
	String getMovePhase();
}
