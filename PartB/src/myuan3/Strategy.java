package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;

public abstract class Strategy {
	
	private String name;

	public Strategy(String name) {
		this.name = name;
	}

	public abstract Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType);
	
	protected ArrayList<Move> totalLegalMoves(ArrayList<Piece> Pieces, Board board, char playerType){

		
		//create a list to store legal moves for the player
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		//add moves to the list
		for(Piece p: Pieces) {
			ArrayList<Move> pieceLegelMove = this.pieceLegalMoves(p, board, playerType);
			legalMoves.addAll(pieceLegelMove);
		}
		return legalMoves;
	}
	
	protected ArrayList<Move> pieceLegalMoves(Piece p, Board board, char playerType){
		
		//create a list to store legal moves for the player
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		if(playerType == 'H') {
			if(checkUp(p, board) && board.checkEdge(p.getRow(), p.getCol()) != Direction.UP) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.UP));
			}
			if(checkDown(p, board)) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.DOWN));
			}
			if(checkRight(p, board)) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.RIGHT));
			}
		}else if(playerType == 'V') {
			if(checkUp(p, board)) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.UP));
			}
			if(checkLeft(p, board)) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.LEFT));
			}
			if(checkRight(p, board) && board.checkEdge(p.getRow(), p.getCol()) != Direction.RIGHT) {
				legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.RIGHT));
			}
		}
		
		return legalMoves;
	}
	
	private boolean checkUp(Piece p, Board board) {

		if(p.getRow() == board.getSize()-1) {
			return true;
		}else if(board.boardMap[board.convertRow(p.getRow())-1][p.getCol()] == '+') {
			return true;
		}
		return false;
	}
	private boolean checkDown(Piece p, Board board) {

		if(p.getRow() == 0) {
			return true;
		}else if(board.boardMap[board.convertRow(p.getRow())+1][p.getCol()] == '+') {
			return true;
		}
		return false;
	}
	private boolean checkLeft(Piece p, Board board) {

		if(p.getCol() == 0) {
			return true;
		}else if(board.boardMap[board.convertRow(p.getRow())][p.getCol()-1] == '+') {
			return true;
		}
		return false;
	}
	private boolean checkRight(Piece p, Board board) {

		if(p.getCol() == board.getSize()-1) {
			return true;
		}else if(board.boardMap[board.convertRow(p.getRow())][p.getCol()+1] == '+') {
			return true;
		}
		return false;
	}
}
