package myuan3;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class LimitedDepth extends Strategy{
	
	public static final int DEPTH = 3;

	public LimitedDepth(String name) {
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
		int current_least_dist = board.getSize()*(board.getSize()-1);
		int depth = 0;
		
		for(Move m: legalMoves) {
			try {
				ArrayList<Piece> currPieces = new ArrayList<Piece>();
				currPieces.addAll(myPieces);
				//System.out.println("move is: "+m.j+", "+m.i);
				Piece afterMove = findPiece(m.j, m.i, currPieces);
				int[] coordinates = doMove(m, board.getSize());
				afterMove.setRow(coordinates[0]);
				afterMove.setCol(coordinates[1]);
				int mDist = sumEuclideanDistance(currPieces, board.getSize(), playerType);
				if(mDist == current_least_dist) {
					bestMoves.add(m);
				}else if(mDist < current_least_dist) {
					current_least_dist = mDist;
					bestMoves.removeAll(bestMoves);
					bestMoves.add(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Random rand = new Random();
		Move m = null;
		if(bestMoves.size() > 0) {
			int i = rand.nextInt(bestMoves.size());
			m = bestMoves.get(i);
		}
		player.update(m);
		System.out.println("current least dist = "+current_least_dist);
		return m;
	}
	
	
	private int euclideanDistance(int row, int col, int size, char playerType) {
		if(playerType == 'H') {
			return size - col;
		}else {
			return size - row;
		}
	}
	
	private int sumEuclideanDistance(ArrayList<Piece> myPieces, int size, char playerType) {
		int sum = 0;
		for(Piece p: myPieces) {
			sum += euclideanDistance(p.getRow(), p.getCol(), size, playerType);
		}
		return sum;
	}

}
