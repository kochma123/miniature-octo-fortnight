package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import swordandshield.Game;
import swordandshield.Piece;

public class CreateTests {

	Game g;

	String[] letters = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x" };

	@Test
	public void player1CreateAllLetters() {
		for (int i = 0; i < letters.length; i++) {
			g = new Game();
			g.readCommandCreatePhase("create " + letters[i] + " 0");
			assertEquals(letters[i], g.board.getPiece(2, 2).getLetter());
			assertEquals(null,g.board.getPiece(7, 7));
		}
	}
	
	@Test
	public void player1CreateRotation0() {
		g = new Game();
		g.readCommandCreatePhase("create a 0");
		Piece expected = new Piece("a", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(2, 2));
		assertEquals(null, g.board.getPiece(7, 7));
	}

	@Test
	public void player1CreateRotation90() {
		g = new Game();
		g.readCommandCreatePhase("create a 90");
		Piece expected = new Piece("a", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(2, 2));
		assertEquals(null, g.board.getPiece(7, 7));
	}

	@Test
	public void player1CreateRotation180() {
		g = new Game();
		g.readCommandCreatePhase("create a 180");
		Piece expected = new Piece("a", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(2, 2));
		assertEquals(null, g.board.getPiece(7, 7));
	}

	@Test
	public void player1CreateRotation270() {
		g = new Game();
		g.readCommandCreatePhase("create a 270");
		Piece expected = new Piece("a", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(2, 2));
		assertEquals(null,g.board.getPiece(7, 7));
	}


	@Test
	public void player2CreateAllLetters() {
		for (int i = 0; i < letters.length; i++) {
			g = new Game();
			g.nextTurn();
			g.readCommandCreatePhase("create " + letters[i] + " 0");
			assertEquals(letters[i].toUpperCase(), g.board.getPiece(7, 7).getLetter());
			assertEquals(null, g.board.getPiece(2, 2));
		}
	}

	@Test
	public void player2CreateRotation0() {
		g = new Game();
		g.nextTurn();
		g.readCommandCreatePhase("create a 0");
		Piece expected = new Piece("A", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(7, 7));
		assertEquals(null, g.board.getPiece(2, 2));
	}

	@Test
	public void player2CreateRotation90() {
		g = new Game();
		g.nextTurn();
		g.readCommandCreatePhase("create a 90");
		Piece expected = new Piece("A", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(7, 7));
		assertEquals(null, g.board.getPiece(2, 2));
	}

	@Test
	public void player2CreateRotation180() {
		g = new Game();
		g.nextTurn();
		g.readCommandCreatePhase("create a 180");
		Piece expected = new Piece("A", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(7, 7));
		assertEquals(null, g.board.getPiece(2, 2));
	}

	@Test
	public void player2CreateRotation270() {
		g = new Game();
		g.nextTurn();
		g.readCommandCreatePhase("create a 270");
		Piece expected = new Piece("A", Piece.Side.SWORD, Piece.Side.SHIELD, Piece.Side.SWORD, Piece.Side.SWORD);
		assertEquals(expected, g.board.getPiece(7, 7));
		assertEquals(null, g.board.getPiece(2, 2));
	}
	
	@Test
	public void player1CreateFull(){
		g = new Game();
		g.readCommandCreatePhase("create a 0");
		assertEquals("a", g.board.getPiece(2, 2).getLetter());
		assertEquals(null, g.board.getPiece(7, 7));
		g.readCommandCreatePhase("create b 0");
		assertEquals("a", g.board.getPiece(2, 2).getLetter());
		assertEquals(null, g.board.getPiece(7, 7));
	}
	

	@Test
	public void player2CreateFull(){
		g = new Game();
		g.nextTurn();
		g.readCommandCreatePhase("create a 0");
		assertEquals("A", g.board.getPiece(7, 7).getLetter());
		assertEquals(null, g.board.getPiece(2, 2));
		g.readCommandCreatePhase("create b 0");
		assertEquals("A", g.board.getPiece(7, 7).getLetter());
		assertEquals(null, g.board.getPiece(2, 2));
	}
}