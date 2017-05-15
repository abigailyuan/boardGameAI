package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
import aiproj.slider.SliderPlayer;

public class Stupidminimax extends Strategy{
	
	char [][] localBoard;
	char myPlayer;
	char enemyPlayer;

	public Stupidminimax(String name) {
		super(name);
		                     
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType,
			SliderPlayer player) {
		
		myPlayer = playerType;
		if(myPlayer == 'H') {
			enemyPlayer = 'V';
		}else {
			enemyPlayer = 'H';
		}
		Move m = null;
		//create a path record
		ArrayList<Move> path = new ArrayList<Move>();
		//copy the board to a new board
		Board curr_board = Board.cpyBoard(board);
		curr_board.myPieces = Board.scpmyPieces(board);
		curr_board.enemyPieces = Board.scpenemyPieces(board);
		node root = new node(curr_board, null);
		node tempNode = minimax(root, 4, true);
		System.out.println("heuristic = "+tempNode.heuristic);
		m = node.findRoot(tempNode).move;
		//ArrayList<Move> legalmoves = totalLegalMoves(myPieces, board, playerType);
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
//		for(Move m1: legalmoves) {
//			System.out.println("move row = "+m1.j+" col = "+m1.i+" direction = "+m1.d);
//		}
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		player.update(m);
		return m;
	}
	
	public node minimax(node root, int depth, boolean maximizingPlayer) {
		char playerType;
		if(maximizingPlayer) {
			playerType = myPlayer;
		}else {
			playerType = enemyPlayer;
		}
		ArrayList<Move> legalMoves = totalLegalMoves(root.board.myPieces, root.board, playerType);
		
		
		if(depth == 0 || legalMoves.size() == 0) {
			root.heuristic = node.calculateHeuristic(root);
			//System.out.println("returned node move row = "+root.move.j);
			return root;
		}
//		if(depth == 4) {
//			System.out.println("depth = "+depth);
//			for(Piece p: root.board.myPieces) {
//				System.out.println("my piece row = "+p.getRow()+" col = "+p.getCol());
//			}
//			System.out.println("player type = "+root.board.playerType);
//			for(Move m2:legalMoves) {
//				System.out.println("move row = "+m2.j+" col = "+m2.i+" direction = "+m2.d);
//			}
//			System.out.println("-----------------------------------------------");
//		}
		
		
		//create child list for current node
		for(Move m: legalMoves) {

			 Board temp = Board.cpyBoard(root.board);
			 temp.myPieces = Board.scpenemyPieces(root.board);
			 temp.enemyPieces = Board.scpmyPieces(root.board);
			 temp.enemyType = root.board.playerType;
			 temp.playerType = root.board.enemyType;
			 
			 Piece p = findPiece(m.j, m.i, temp.myPieces, temp.enemyPieces);
			 
			 //updateBoard(temp, m, p);
			 if(p != null) {
				 updateBoard(temp, m, p);
			 }
			 node newNode = new node(temp, root);
			 newNode.move = m;
			 root.childList.add(newNode);
		}

		if(maximizingPlayer) {
			node bestNode = null;
			double bestValue = -99999;
			for(node child: root.childList) {
				bestNode = minimax(child, depth-1, false);
				bestValue = Math.max(bestValue, bestNode.heuristic);
			}

			return bestNode;
		}
		else {
			node bestNode = null;
			double bestValue = 99999;
			for(node child: root.childList) {
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
				winDist += current.board.getSize() - p.getRow();
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
		
		while(current.parent.parent != null) {
			current = current.parent;
		}
		System.out.println("current row = "+current.move.j);
		return current;
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
		
		if(p.getCol() == 0) {
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
