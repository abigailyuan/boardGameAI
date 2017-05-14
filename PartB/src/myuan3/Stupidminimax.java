package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
import aiproj.slider.SliderPlayer;

public class Stupidminimax extends Strategy{
	
	char [][] localBoard;

	public Stupidminimax(String name) {
		super(name);
		                     
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType,
			SliderPlayer player) {
		Move m = null;
		//create a path record
		ArrayList<Move> path = new ArrayList<Move>();
		//copy the board to a new board
		Board curr_board = Board.cpyBoard(board);
		curr_board.myPieces.addAll(myPieces);
		curr_board.enemyPieces.addAll(enemyPieces);
		node root = new node(curr_board, null);
		
		player.update(m);
		return m;
	}
	
	public node minimax(node root, int depth, boolean maximizingPlayer) {
		ArrayList<Move> legalMoves = totalLegalMoves(root.board.myPieces, root.board, maximizingPlayer?root.board.playerType:root.board.enemyType);
		if(depth == 0 || legalMoves.size() == 0) {
			root.heuristic = 0;//TODO add heuristic method
			return root;
		}
		//create child list for current node
		for(Move m: legalMoves) {
			 Board temp = Board.cpyBoard(root.board);
			 temp.myPieces.addAll(root.board.myPieces);
			 temp.enemyPieces.addAll(root.board.enemyPieces);
			 
			 Piece p = findPiece(m.j, m.i, temp.myPieces, temp.enemyPieces);
			 if(p != null) {
				 //TODO move p and update board
				 updateBoard(temp, m, p);
			 }
			 node newNode = new node(temp, root);
		}
		if(maximizingPlayer) {
			node bestNode = null;
			int bestValue = -99999;
			
			
		}
	}
	
	public void updateBoard(Board board, Move m, Piece p) {
		int row = m.j;
		int col = m.i;
		
		
		board.boardMap[row][col] = '+';
		if(m.d == Direction.UP) {
			if((row-1) < board.getSize()) {
				p.setRow(row-1);
				board.boardMap[row-1][col] = p.getType();
			}else {
				board.myPieces.remove(p);
			}
		}else if(m.d == Direction.DOWN) {
			p.setRow(row+1);
			board.boardMap[row+1][col] = p.getType();
		}else if(m.d == Direction.LEFT) {
			p.setCol(col-1);
			board.boardMap[row][col-1] = p.getType();
		}else if(m.d == Direction.RIGHT) {
			if((col+1) < board.getSize()) {
				p.setCol(col+1);
				board.boardMap[row][col+1] = p.getType();
			}else {
				board.myPieces.remove(p);
			}
		}
	}
	
	public Piece findPiece(int row, int col, ArrayList<Piece>myPieces, ArrayList<Piece>enemyPieces){
		
		Piece pReturn = null;
		
		//find the piece in my pieces
		for(Piece p: myPieces) {
			if(p.getCol() == col && p.getRow() == row) {
				pReturn = p;
			}
		}
		
		//find the piece in enemy's pieces
		for(Piece p: enemyPieces) {
			if(p.getCol() == col && p.getRow() == row) {
				pReturn = p;
			}
		}
		if(pReturn == null) {
			return pReturn;
		}else {
			return pReturn;
		}
	}
}



class node{
	public Board board;
	public ArrayList<node> childList;
	public node parent;
	public Move move;
	public int heuristic;
	
	public node(Board board, node parent) {
		this.board = board;
		this.parent = parent;
		this.move = null;
		this.childList = new ArrayList<node>();
	}
	
}
