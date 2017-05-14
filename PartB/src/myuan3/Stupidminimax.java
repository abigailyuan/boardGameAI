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
		curr_board.myPieces = Board.scpmyPieces(board);
		curr_board.enemyPieces = Board.scpenemyPieces(board);
		node root = new node(curr_board, null);
		m = node.findRoot(minimax(root, 3, true)).move;
		player.update(m);
		return m;
	}
	
	public node minimax(node root, int depth, boolean maximizingPlayer) {
		System.out.println("depth = "+depth);
		ArrayList<Move> legalMoves = totalLegalMoves(root.board.myPieces, root.board, maximizingPlayer?root.board.playerType:root.board.enemyType);
		if(depth == 0 || legalMoves.size() == 0) {
			root.heuristic = node.calculateHeuristic(root);
			return root;
		}
		//create child list for current node
		for(Move m: legalMoves) {
			System.out.println("row = "+m.j);
			System.out.println("col = "+m.i);
			System.out.println("direction = "+m.d);
			 Board temp = Board.cpyBoard(root.board);
			 temp.myPieces = Board.scpenemyPieces(root.board);
			 temp.enemyPieces = Board.scpmyPieces(root.board);
			 temp.enemyType = root.board.playerType;
			 temp.playerType = root.board.enemyType;
			 
			 Piece p = findPiece(m.j, m.i, temp.myPieces, temp.enemyPieces);
			 if(p != null) {
				 updateBoard(temp, m, p);
			 }
			 node newNode = new node(temp, root);
			 newNode.move = m;
			 root.childList.add(newNode);
		}
		int i = root.board.getSize();
		for(i=root.board.getSize()-1;i>=0;i--) {
			root.board.printRow(i);
		}
		//System.out.println("current player is"+root.board.playerType);
		System.out.println("-------------------");
		if(maximizingPlayer) {
			node bestNode = null;
			double bestValue = -99999;
			for(node child: root.childList) {
				System.out.println("current type "+root.board.playerType);
				System.out.println("child type "+child.board.playerType);
				bestNode = minimax(child, depth-1, false);
				bestValue = Math.max(bestValue, bestNode.heuristic);
			}
			return bestNode;
		}
		else {
			node bestNode = null;
			double bestValue = 99999;
			for(node child: root.childList) {
				System.out.println("current type "+root.board.playerType);
				System.out.println("child type "+child.board.playerType);
				bestNode = minimax(child, depth-1, true);
				bestValue = Math.min(bestValue, bestNode.heuristic);
			}
			return bestNode;
		}
	}
	
	public void updateBoard(Board board, Move m, Piece p) {
		int row = m.j;
		int col = m.i;
		
		
		board.boardMap[row][col] = '+';
		if(m.d == Direction.UP) {
			if((row+1) < board.getSize()) {
				p.setRow(row+1);
				board.boardMap[row+1][col] = p.getType();
			}else {
				board.myPieces.remove(p);
			}
		}else if(m.d == Direction.DOWN) {
			p.setRow(row-1);
			board.boardMap[row-1][col] = p.getType();
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
	public double heuristic;
	
	public node(Board board, node parent) {
		this.board = board;
		this.parent = parent;
		this.move = null;
		this.childList = new ArrayList<node>();
	}
	
	public static double calculateHeuristic(node current) {
		double heuristic = 0;
		int winDist = 0;
		int aroundDist = 0;
		int enemyBlock = 0;
	
		for(Piece p: current.board.myPieces) {
			
			if(current.board.enemyType == 'H') {
				//get winDist
				winDist += p.getRow();
				//get enemyBlock
				enemyBlock += checkUP(p, current.board, current.board.enemyType);
				enemyBlock += checkDOWN(p, current.board, current.board.enemyType);
				enemyBlock += checkLEFT(p, current.board, current.board.enemyType);
				
			}else {
				//get winDist
				winDist += current.board.getSize() - p.getCol();
				//get enemyBlock
				enemyBlock += checkDOWN(p, current.board, current.board.enemyType);
				enemyBlock += checkLEFT(p, current.board, current.board.enemyType);
				enemyBlock += checkRIGHT(p, current.board, current.board.enemyType);
			}
			
		}
		
		heuristic = winDist * 1.0 + enemyBlock * 1.0;
		
		
		return heuristic;
	}
	
	public static node findRoot(node current) {
		if(current.parent.parent == null) {
			return current.parent;
		}else {
			findRoot(current.parent);
		}
		return null;
	}
	
	private static int checkUP(Piece p, Board board, char enemyType) {
		
		if((p.getRow()+1) == board.getSize()) {
			return 0;
		}else {
			if(board.boardMap[p.getRow()+1][p.getCol()] == enemyType) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	private static int checkDOWN(Piece p, Board board, char enemyType) {
		
		if(p.getRow() == 0) {
			return 0;
		}else {
			if(board.boardMap[p.getRow()-1][p.getCol()] == enemyType) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	private static int checkLEFT(Piece p, Board board, char enemyType) {
		
		if((p.getCol()-1) == 0) {
			return 0;
		}else {
			if(board.boardMap[p.getRow()][p.getCol()-1] == enemyType) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	private static int checkRIGHT(Piece p, Board board, char enemyType) {
		
		if((p.getCol()+1) == board.getSize()) {
			return 0;
		}else {
			if(board.boardMap[p.getRow()][p.getCol()+1] == enemyType) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	
}
