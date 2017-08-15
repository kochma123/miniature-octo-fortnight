package swordandshield;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5973436840858819411L;
	private Piece[][] board;
	private int width;
	private int height;
	private List<Player> players;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		board = new Piece[this.width][this.height];
		players = new ArrayList<Player>();
	}

	public boolean addPlayer(Player p) {
		return players.add(p);
	}

	public Piece getPiece(int x, int y) {
		return board[x][y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean movePiece(Piece p, String dir) {
		int newX = -1;
		int newY = -1;
		switch (dir) {
		case ("left"):
			newY = p.getY() - 1;
			newX = p.getX();
			break;
		case ("right"):
			newY = p.getY() + 1;
			newX = p.getX();
			break;
		case ("up"):
			newX = p.getX() - 1;
			newY = p.getY();
			break;
		case ("down"):
			newX = p.getX() + 1;
			newY = p.getY();
			break;
		}

		if ((newX < 0) || (newY < 0) || (newX > width - 1) || newY > height - 1 || (newX == 0 && newY == 1)
				|| (newX == 1 && newY == 0) || (newX == width - 2 && newY == height - 1)
				|| (newX == width - 1 && newY == height - 2)) {
			board[p.getX()][p.getY()] = null;
			return true;
		} else if (board[newX][newY] == null) {
			move(p, newX, newY);
			return true;
		} else {
			if (movePiece(board[newX][newY], dir)) {
				move(p, newX, newY);
				return true;
			}
		}
		return false;
	}

	private void move(Piece p, int x, int y) {
		board[p.getX()][p.getY()] = null;
		p.setY(y);
		p.setX(x);
		board[x][y] = p;
	}

	public void playPiece(Piece p, Player player) {
		int i = player.createLocation();
		if (board[i][i] != null)
			throw new RuntimeException();
		board[i][i] = p;
		p.setX(i);
		p.setY(i);
	}

	public String getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getFace() == x)
				return p.getName();
		}
		return null;
	}

	public boolean checkFace(int x, int y) {
		for (Player p : players) {
			if (p.getFace() == x && p.getFace() == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Board clone() {
		return null;
	}
}