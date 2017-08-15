package swordandshield;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Game implements Cloneable, Serializable {

	private static final long serialVersionUID = 852759352683547304L;
	Player p1;
	Player p2;
	Player activePlayer;
	Map<String, Piece> moveablePieces;
	public Board board;
	UI ui;
	boolean gameOver;
	Stack<GameState> stack;// for undo
	boolean pass;
	boolean create; // flag for undo
	boolean undoing;

	public Game() {
		ui = new TextUI();
		board = new Board(10, 10);
		p1 = new Player(player1List(), " zero", board, 1, 2);
		p2 = new Player(player2List(), " one ", board, 8, 7);
		stack = new Stack<GameState>();
		nextTurn();
	}

	public void displayBoard() {
		ui.displayBoard(board);
	}

	public void startGame() {
		while (!gameOver) {
			ui.println(activePlayer + "'s Turn");
			if (create) {
				do {
					ui.displayBoard(board);
					String input = ui.getCreatePhase();
					pass = readCommandCreatePhase(input);
					pushUndo();
				} while (!pass);
			}
			pass = false;

			if (!undoing)
				// ui.displayBoard(board);
				movePieces();

			nextTurn();
		}
	}

	private void movePieces() {
		do {
			ui.displayBoard(board);
			String input = ui.getMovePhase();
			readCommandSecondPhase(input);
			pushUndo();
		} while (!pass);

	}

	private void pushUndo() {
		stack.push(new GameState(this.clone()));
	}

	// The dirtiest nastiest way to deep clone
	public Game clone() {
		Game clone = null;
		try {
			FileOutputStream fout = new FileOutputStream("clone.dat");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(this);
			out.close();

			FileInputStream fin = new FileInputStream("clone.dat");
			ObjectInputStream in = new ObjectInputStream(fin);
			clone = (Game) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
		}
		return clone;
	}

	public void nextTurn() {
		if (activePlayer == p1)
			activePlayer = p2;
		else
			activePlayer = p1;
		moveablePieces = activePlayer.getPlayedPieces();
		for (Piece p : moveablePieces.values())
			p.setMoved(false);
		stack = new Stack<GameState>();
		pass = true;
		create = true;
	}

	public void readCommandSecondPhase(String line) {
		String[] values = line.split("\\s+");
		String command = values[0];
		String[] args = Arrays.copyOfRange(values, 1, values.length);
		switch (command) {
		case ("pass"):
			pass = true;
			return;
		case ("rotate"):
			parseRotate(args);
			return;
		case ("move"):
			parseMove(args);
			return;
		case ("undo"):
			parseUndo(args);
			return;
		default:
			ui.println("Command '" + command + "' not recognized");
			return;
		}
	}

	public boolean readCommandCreatePhase(String line) {
		String[] values = line.split("\\s+");
		String command = values[0];
		String[] args = Arrays.copyOfRange(values, 1, values.length);
		switch (command) {
		case ("pass"):
			pass = true;
			return true;
		case ("create"):
			return parseCreate(args);
		case ("undo"):
			return parseUndo(args);
		default:
			ui.println("Command '" + command + "' not recognized");
			return false;
		}
	}

	private boolean parseCreate(String[] args) {
		if (args.length < 2)
			return false;
		try {
			activePlayer.playPiece(args[0], args[1]);
		} catch (RuntimeException e) {
			ui.println("Create Square is Occupied");
			return false;
		}
		return true;
	}

	private boolean parseRotate(String[] args) {
		Piece piece = activePlayer.getPlayedPieces().get(args[0].toUpperCase());
		if (!piece.hasMoved()) {
			switch (args[1]) {
			case ("270"):
			case ("3"):
				piece.rotate();
			case ("180"):
			case ("2"):
				piece.rotate();
			case ("90"):
			case ("1"):
				piece.rotate();
			}
			piece.setMoved(true);
			return true;
		} else {
			return false;
		}
	}

	private boolean parseUndo(String[] args) {
		GameState gameState = stack.pop();
		undoing = true;
		p1 = gameState.getP1();
		p2 = gameState.getP2();
		activePlayer = gameState.getActivePlayer();
		board = gameState.getBoard();
		moveablePieces = gameState.getMoveablePieces();
		create = gameState.getCreate();
		return true;
	}

	private boolean parseMove(String[] args) {
		Piece piece = activePlayer.getPlayedPieces().get(args[0].toUpperCase());
		if (!piece.hasMoved()) {
			piece.setMoved(true);
			board.movePiece(piece, args[1]);
			return true;
		}
		ui.println("That piece is either not in play or has already moved this turn.");
		return false;
	}

	private Map<String, Piece> player1List() {
		Map<String, Piece> map = new HashMap<String, Piece>();
		map.put("A", new Piece("a", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("B", new Piece("b", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("C", new Piece("c", Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("D", new Piece("d", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("E", new Piece("e", Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("F", new Piece("f", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SWORD));
		map.put("G", new Piece("g", Piece.Side.SWORD, Piece.Side.SWORD, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("H", new Piece("h", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("I", new Piece("i", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("J", new Piece("j", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SHIELD));
		map.put("K", new Piece("k", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SWORD));
		map.put("L", new Piece("l", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("M", new Piece("m", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("N", new Piece("n", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("O", new Piece("o", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.SHIELD));
		map.put("P", new Piece("p", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SWORD));
		map.put("Q", new Piece("q", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("R", new Piece("r", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("S", new Piece("s", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("T", new Piece("t", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.NOTHING));
		map.put("U", new Piece("u", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SWORD));
		map.put("V", new Piece("v", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("W", new Piece("w", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("X", new Piece("x", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		return map;
	}

	private Map<String, Piece> player2List() {
		Map<String, Piece> map = new HashMap<String, Piece>();
		map.put("A", new Piece("A", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("B", new Piece("B", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("C", new Piece("C", Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("D", new Piece("D", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("E", new Piece("E", Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("F", new Piece("F", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SWORD));
		map.put("G", new Piece("G", Piece.Side.SWORD, Piece.Side.SWORD, Piece.Side.SWORD, Piece.Side.SWORD));
		map.put("H", new Piece("H", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("I", new Piece("I", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("J", new Piece("J", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SHIELD));
		map.put("K", new Piece("K", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SWORD));
		map.put("L", new Piece("L", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("M", new Piece("M", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("N", new Piece("N", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.NOTHING));
		map.put("O", new Piece("O", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.SHIELD));
		map.put("P", new Piece("P", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SWORD));
		map.put("Q", new Piece("Q", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("R", new Piece("R", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("S", new Piece("S", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.SHIELD));
		map.put("T", new Piece("T", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.NOTHING));
		map.put("U", new Piece("U", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SWORD));
		map.put("V", new Piece("V", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.NOTHING, Piece.Side.NOTHING));
		map.put("W", new Piece("W", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		map.put("X", new Piece("X", Piece.Side.NOTHING, Piece.Side.SHIELD, Piece.Side.SHIELD, Piece.Side.SHIELD));
		return map;
	}

	public static void main(String[] args) {
		new Game().startGame();
	}
}