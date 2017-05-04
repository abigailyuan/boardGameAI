package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;

public class DFS extends Strategy{

	public DFS(String name) {
		super(name);
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType) {
		ArrayList<Move> legalMoves = totalLegalMoves(myPieces, board, playerType);
		int numLegalMoves = legalMoves.size();
		
		return legalMoves.get(0);
	}

}
