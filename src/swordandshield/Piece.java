package swordandshield;
import java.io.Serializable;

public class Piece implements Cloneable, Serializable{

	private static final long serialVersionUID = -8069393288872634577L;
	
	private Side up;
	private Side down;
	private Side left;
	private Side right;
	private String letter;
	
	private boolean moved;
	private int x;
	private int y;

	

	public enum Side {
		SWORD, SHIELD, NOTHING
	}
	
	public Piece(String letter, Side up, Side right, Side down, Side left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.letter = letter;
	}
	
	public void rotate(){
		Side temp = up;
		up = left;
		left = down;
		down = right;
		right = temp;
	}
	
	public String getLetter(){
		return letter;
	}

	public String getSide(String s) {
		switch (s) {
		case("left"):
			return getString(left, "left");
		case ("right"):
			return getString(right, "right");
		case ("up"):
			return getString(up, "up");
		case ("down"):
			return getString(down, "down");
		default:
			throw new RuntimeException();
		}
	}

	private String getString(Side s, String direction){
		switch(s){
		case NOTHING:
			return " ";
		case SHIELD:
			return "#";
		case SWORD :
			return getSword(direction);
		default:
			throw new RuntimeException();
		}
	}

	private String getSword(String direction){
		switch(direction){
		case("down"):
		case("up"):
			return "|";
		case("left"):
		case("right"):
			return "-";
		default:
			throw new RuntimeException();
		}
	}

	public String toString(){
		return " "+ getSide("up") +" \n"
				+ getSide("left")+""+getLetter()+""+getSide("right")+"\n"
						+ " "+getSide("down")+" ";
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((down == null) ? 0 : down.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((letter == null) ? 0 : letter.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((up == null) ? 0 : up.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (down != other.down)
			return false;
		if (left != other.left)
			return false;
		if (letter == null) {
			if (other.letter != null)
				return false;
		} else if (!letter.equals(other.letter))
			return false;
		if (right != other.right)
			return false;
		if (up != other.up)
			return false;
		return true;
	}

	public void setMoved(boolean b) {
		moved = b;
	}
	public boolean hasMoved(){
		return moved;
	}
	
	@Override
	public Piece clone(){
		return null;
	}
}