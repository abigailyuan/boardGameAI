package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;

public abstract class Strategy {
	
	private String name;

	public Strategy(String name) {
		this.name = name;
	}

	public abstract Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces);
}
