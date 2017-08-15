package swordandshield;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An object to represent a player. Contains a map that contains the pieces this
 * player can play.
 * <p>
 * The map is a String -> Piece map that uses the letter of the piece contains
 * as the key to the piece Such that
 * K.toUppercase()->V.getLetter().toUpperCase() for all K->V
 * 
 * @param map
 *            A map that contains the playable pieces for the player
 */
public class Player implements Cloneable , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8377282162323802261L;
	private Map<String, Piece> unPlayedPieces;
	private int createLocation;// board is a square so only need one value
	private int face;
	private Map<String, Piece> playedPieces;
	private Board board;
	private String name;

	public Player(Map<String, Piece> map,String name, Board board, int face, int createLocation) {
		this.unPlayedPieces = map;
		this.name = name;
		this.createLocation = createLocation;
		this.face = face;
		this.board = board;
		this.playedPieces = new HashMap<String, Piece>();
		board.addPlayer(this);
	}
	
	public String getName(){
		return name;
	}

	public int getFace() {
		return face;
	}

	public int createLocation() {
		return createLocation;
	}

	public Map<String, Piece> getUnPlayedPieces() {
		return unPlayedPieces;
	}

	public Piece playPiece(String p, String rotation) {
		Piece piece = unPlayedPieces.get(p.toUpperCase());
		board.playPiece(piece, this);
		playedPieces.put(p.toUpperCase(), piece);
		piece.setMoved(true);
		
		switch (rotation) {
		case ("3"):
			piece.rotate();
		case ("2"):
			piece.rotate();
		case ("1"):
			piece.rotate();
		}
		piece.setMoved(false);
		return piece;
	}

	public Map<String, Piece> getPlayedPieces() {
		return playedPieces;
	}
	
	@Override
	public String toString(){
		return "Player"+name;
	}
	
	@Override
	protected Player clone(){
		return null;
	}
}