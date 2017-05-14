package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
import aiproj.slider.SliderPlayer;

public abstract class Strategy {
	
	private String name;

	public Strategy(String name) {
		this.name = name;
	}

	public abstract Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType, SliderPlayer player);
	
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
		//H player
			if(playerType == 'H'){
					int qx = p.getCol();
					int qy = p.getRow();
					//System.out.println("qx = "+qx);
					//System.out.println("qy = "+qy);
					if((qx+1)<=(board.getSize()-1) &&(board.boardMap[qy][qx+1] == '+')) {
						legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.RIGHT));
					}
					if((qy+1)<=(board.getSize()-1) &&(board.boardMap[qy+1][qx] == '+')) {
						legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.UP));	
					}
					if((qy-1)>= 0 &&(board.boardMap[qy-1][qx] == '+')) {
						legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.DOWN));	
					}
					if((qx+1) == board.getSize()) {
						legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.RIGHT));
					}
			}else {// V player		
				int qx = p.getCol();
				int qy = p.getRow();
				if((qx+1)<=(board.getSize()-1) &&(board.boardMap[qy][qx+1] == '+')) {
					legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.RIGHT));
				}
				if((qx-1)>= 0 &&(board.boardMap[qy][qx-1] == '+')) {
					legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.LEFT));
				}
				if((qy+1) < board.getSize() &&(board.boardMap[qy+1][qx] == '+')) {
					legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.UP));
				}
				if((qy+1) == board.getSize()) {
					legalMoves.add(new Move(p.getCol(), p.getRow(), Direction.UP));
				}
			}
		
		return legalMoves;
	}
	

	private boolean checkUp(Piece p, Board board) {
		
		char type = p.getType();
		if(type == 'H') {
			if(board.checkEdgeUP(p.getRow(), p.getCol())) {
				return false;
			}else if(board.boardMap[p.getRow()+1][p.getCol()] != '+') {
				return false;
			}
		}else if(type == 'V') {
			if(!board.checkEdgeUP(p.getRow(), p.getCol()) && board.boardMap[p.getRow()+1][p.getCol()] != '+') {
				return false;
			}else if(board.checkEdgeUP(p.getRow(), p.getCol())) {
				return true;
			}
		}
		return true;
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
	
	protected int[] doMove(Move m, int size) {
		Direction d = m.d;
		int row = m.j;
		int col = m.i;
		//System.out.println("move row: "+row+" col: "+col);
		switch(d) {
			case UP:
				row++;
				if(row == size) {
					return null;
				}
				break;
				
			case DOWN:
				row--;
				break;
			
			case LEFT:
				col--;
				break;
				
			case RIGHT:
				col++;
				if(col == size) {
					return null;
				}
				break;
		}
		int[] coordinates = new int[2];
		coordinates[0] = row;
		coordinates[1] = col;
		return coordinates;
	}
	
	protected int[] reverseMove(Move m, int size) {
		Direction d = m.d;
		int row = m.j;
		int col = m.i;
		//System.out.println("move row: "+row+" col: "+col);
		switch(d) {
			case UP:
				row--;
				if(row == size) {
					return null;
				}
				break;
				
			case DOWN:
				row++;
				break;
			
			case LEFT:
				col++;
				break;
				
			case RIGHT:
				col--;
				if(col == size) {
					return null;
				}
				break;
		}
		int[] coordinates = new int[2];
		coordinates[0] = row;
		coordinates[1] = col;
		return coordinates;
	}
	
	protected Piece findPiece(int row, int col, ArrayList<Piece> myPieces) throws Exception {
		
		Piece pReturn = null;
		
		//find the piece in my pieces
		for(Piece p: myPieces) {
			if(p.getCol() == col && p.getRow() == row) {
				pReturn = p;
			}
		}
		if(pReturn == null) {
			throw new Exception("Piece not found.");
		}else {
			return pReturn;
		}
	}
}
