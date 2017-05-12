package myuan3;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
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
		if(playerType == 'H') {
			while(m == null) {
				for(Move i: legalMoves) {
					if(i.d == Direction.RIGHT) {
						m = i;
						break;
					}
				}
				for(Move i: legalMoves) {
					if(i.d == Direction.UP) {
						m = i;
						break;
					}
				}
				for(Move i: legalMoves) {
					if(i.d == Direction.DOWN) {
						m = i;
						break;
					}
				}
			}
		}else {
			while(m == null) {
				for(Move i: legalMoves) {
					if(i.d == Direction.UP) {
						m = i;
						break;
					}
				}
				for(Move i: legalMoves) {
					if(i.d == Direction.RIGHT) {
						m = i;
						break;
					}
				}
				for(Move i: legalMoves) {
					if(i.d == Direction.LEFT) {
						m = i;
						break;
					}
				}
			}
		}
		
		
		
		player.update(m);
		
		return m;
	}
}
