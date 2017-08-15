package swordandshield;
import java.io.Serializable;
import java.util.Scanner;

public class TextUI implements UI ,Serializable{
	
	private static final long serialVersionUID = 6419807883011833042L;
	Scanner scan;
	
	public TextUI(){
		scan = new Scanner(System.in);
	}

	@Override
	public void displayBoard(Board b) {
		printBreak();
		for (int i = 0; i < b.getHeight(); i++) {
			printRow(i, b);
			printBreak();
		}
	}

	@Override
	public void displayPlayablePieces(Player player) {
		for(Piece piece: player.getUnPlayedPieces().values())
			println(""+piece);
	}

	private void printRow(int x, Board b) {
		println(constructUpper(x, b));
		println(constructMiddle(x, b));
		println(constructLower(x, b));
	}

	private String constructUpper(int x, Board b) {
		String s = "|";
		for (int y = 0; y < b.getWidth(); y++) {
			if (checkNonPlayableSquare(x, y)) {
				s = s + "%%%%%|";
			} else {
				Piece p = b.getPiece(x, y);
				if (p == null)
					s = s + nullPiece();
				else
					s = s + "  " + p.getSide("up") + "  |";
			}
		}
		return s;
	}

	private String constructMiddle(int x, Board b) {
		String s = "|";
		for (int y = 0; y < b.getWidth(); y++) {
			if (checkNonPlayableSquare(x, y)) {
				s = s + "%%%%%|";
			} 
			else if (b.checkFace(x, y)){
				s = s+""+b.getPlayerAt(x, y)+"|";
			}
			else {
				Piece p = b.getPiece(x, y);
				if (p == null)
					s = s + nullPiece();
				else
					s = s + " " + b.getPiece(x, y).getSide("left") + "" + b.getPiece(x, y).getLetter() + ""
							+ b.getPiece(x, y).getSide("right") + " |";
			}
		}
		return s;
	}

	

	private String constructLower(int x, Board b) {
		String s = "|";
		for (int y = 0; y < b.getWidth(); y++) {
			if (checkNonPlayableSquare(x, y)) {
				s = s + "%%%%%|";
			} 
			else {
				Piece p = b.getPiece(x, y);
				if (p == null)
					s = s + nullPiece();
				else
					s = s + "  " + p.getSide("down") + "  |";
			}
		}
		return s;
	}

	private boolean checkNonPlayableSquare(int r, int i) {
		return (r == 0 && i == 0 || r == 0 && i == 1 || r == 1 && i == 0 || r == 8 && i == 9 || r == 9 && i == 8 || r == 9 && i == 9);
	}
	
	private String nullPiece() {
		return ("     |");
	}

	private void printBreak() {
		println("-------------------------------------------------------------");
	}

	@Override
	public void println(String s) {
		System.out.println(s);
	}



	@Override
	public String getCreatePhase() {
		System.out.println("Create a piece using 'create <letter> <0/90/180/270>'");
		return scan.nextLine();
	}



	@Override
	public String getMovePhase() {
		System.out.println("Move a piece using 'move <letter> <up/down/left/right>' or ");
		System.out.println("Rotate a piece using 'rotate <letter> <1/2/3/4>'");
		return scan.nextLine();
	}
}