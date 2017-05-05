package myuan3;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class MiniMax extends Strategy{
	
	public MiniMax(String name) {
		super(name);
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType, SliderPlayer player) {
		ArrayList<Move> legalMoves = totalLegalMoves(myPieces, board, playerType);
		int numLegalMoves = legalMoves.size();
		
		Move m = null;
		Random rand = new Random();
		if(numLegalMoves > 0) {
			int i = rand.nextInt(numLegalMoves);
			m = legalMoves.get(i);
		}
		player.update(m);
		
		return m;
	}
}
