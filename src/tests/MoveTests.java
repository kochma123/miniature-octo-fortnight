package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swordandshield.Game;

public class MoveTests {

	Game g;

	@Before()
	public void setup() {
		g = new Game();
		g.readCommandCreatePhase("create a 0");
	}

	@Test
	public void moveLeft() {
		g.readCommandSecondPhase("move a left");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(2, 1).getLetter());
	}

	@Test
	public void moveRight() {
		g.readCommandSecondPhase("move a right");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(2, 3).getLetter());
	}

	@Test
	public void moveUp() {
		g.readCommandSecondPhase("move a up");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(1, 2).getLetter());
	}

	@Test
	public void moveDown() {
		g.readCommandSecondPhase("move a down");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(3, 2).getLetter());
	}

	// TODO write push move test
	@Test
	public void testPush(){
		g.readCommandSecondPhase("move a down");
		g.readCommandCreatePhase("create b 0");
		g.readCommandSecondPhase("move b down");
	}
	
	
	@Test
	public void moveTwice() {
		g.readCommandSecondPhase("move a down");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(3, 2).getLetter());
		g.readCommandSecondPhase("move a down");
		assertEquals("a", g.board.getPiece(3, 2).getLetter());
		assertEquals(null,g.board.getPiece(4, 2));
	}

	@Test
	public void moveOffBoard() {
		g.readCommandSecondPhase("move a left");
		assertEquals(null, g.board.getPiece(2, 2));
		assertEquals("a", g.board.getPiece(2, 1).getLetter());
		g.nextTurn();
		g.nextTurn();
		g.readCommandSecondPhase("move a left");
		assertEquals(null, g.board.getPiece(2, 1));
		assertEquals("a", g.board.getPiece(2, 0).getLetter());
		g.nextTurn();
		g.nextTurn();
		g.readCommandSecondPhase("move a left");
		assertEquals(null, g.board.getPiece(2, 0));
	}

}
