package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import swordandshield.Piece;

public class RotateTests {
	Piece p;

	@Before
	public void setup() {
		p = new Piece("a", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING);
	}

	@Test
	public void rotateOnce() {
		Piece expected = new Piece("a", Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING);
		p.rotate();
		assertEquals(expected, p);
	}
	
	@Test 
	public void rotateTwice(){
		Piece expected = new Piece("a", Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SWORD, Piece.Side.NOTHING);
		p.rotate();
		p.rotate();
		assertEquals(expected, p);
	}
	
	@Test
	public void rotateThrice(){
		Piece expected = new Piece("a", Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.SWORD);
		p.rotate();
		p.rotate();
		p.rotate();
		assertEquals(expected, p);
	}
	
	@Test
	public void fullRotation(){
		Piece expected = new Piece("a", Piece.Side.SWORD, Piece.Side.NOTHING, Piece.Side.NOTHING, Piece.Side.NOTHING);
		p.rotate();
		p.rotate();
		p.rotate();
		p.rotate();
		assertEquals(expected, p);
	}

}
