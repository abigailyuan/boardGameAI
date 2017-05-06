package myuan3;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class FastWin extends Strategy{

	

	public FastWin(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType,
			SliderPlayer player) {
		
		//total legal moves
		ArrayList<Move> legalMoves = totalLegalMoves(myPieces, board, playerType);
		//a list for possible best moves
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		int current_least_dist = board.getSize();
		for(Move m: legalMoves) {
			int mDist = euclideanDistance(m.j, m.i, board.getSize(), playerType);
			if(mDist == current_least_dist) {
				bestMoves.add(m);
			}else if(mDist < current_least_dist) {
				current_least_dist = mDist;
				bestMoves.removeAll(bestMoves);
				bestMoves.add(m);
			}
		}
		Random rand = new Random();
		Move m = null;
		if(bestMoves.size() > 0) {
			int i = rand.nextInt(bestMoves.size());
			m = bestMoves.get(i);
		}
		player.update(m);
		//System.out.println("current least dist = "+current_least_dist);
		return m;
	}
	
	private int euclideanDistance(int row, int col, int size, char playerType) {
		if(playerType == 'H') {
			return size - col;
		}else {
			return size - row;
		}
	}

}
