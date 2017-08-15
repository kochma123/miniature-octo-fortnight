package swordandshield;
import java.util.Map;

public class GameState {

	private Game g;

	public GameState(Game g) {
		this.g = g;
	}
	public Player getP1() {
		return g.p1;
	}
	public Player getP2() {
		return g.p1;
	}
	public Player getActivePlayer(){
		return g.activePlayer;
	}
	public Board getBoard(){
		return g.board;
	}
	public Map<String, Piece> getMoveablePieces(){
		return g.moveablePieces;
	}
	public boolean getCreate(){
		return g.create;
	}
}