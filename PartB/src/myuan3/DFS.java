package myuan3;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
import aiproj.slider.SliderPlayer;

public class DFS extends Strategy{

	public DFS() {
		super();
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType, SliderPlayer player) {
		ArrayList<Move> legalMoves = totalLegalMoves(myPieces, board, playerType);
		int numLegalMoves = legalMoves.size();
		Move m = null;
//		//debug
//		System.out.println("-----------------");
//		System.out.println("current board is:");
//		int row = board.getSize() -1;
//		while(row >=0) {
//			board.printRow(row);
//			row--;
//		}
//		System.out.println("legal moves are:");
//		for(Move m: legalMoves) {
//			System.out.println("move: ("+m.j+" ,"+m.i+") "+m.d);
//		}
//		//debug end
		Random rand = new Random();
		if(numLegalMoves > 0) {
			int i = rand.nextInt(numLegalMoves);
			m = legalMoves.get(i);
		}
		player.update(m);
		return m;
	}
	


}
