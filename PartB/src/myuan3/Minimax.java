package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
import aiproj.slider.SliderPlayer;

public class Minimax extends Strategy{
	
	char [][] localBoard;
	char myPlayer;
	char enemyPlayer;
	Move lastMove = null;
	Move lastlastMove =null;
	int numTurns = 0;
	
	public static int STARTPHASE;
	public static final double rush = 6.0;
	public static final double defensive = 3.0;
	public static final double unblock = 1.0;
	public static final double direction = 100;

	public Minimax() {
		super();
		                     
	}

	@Override
	public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType,
			SliderPlayer player) {
		Minimax.STARTPHASE = board.getSize() - 3;
		//Minimax.STARTPHASE = 0;
		Move m = null;
		
		//define my player type and enemy player type
		myPlayer = playerType;
		if(myPlayer == 'H') {
			enemyPlayer = 'V';
		}else {
			enemyPlayer = 'H';
		}
		
		//check loops
		ArrayList<Move> legalmoves = totalLegalMoves(myPieces, board, playerType);
		if(legalmoves.size() != 0) {
			if(numTurns < Minimax.STARTPHASE) {
				
				//easyStart strategy for start phase
				m = easyStart(myPieces, board, playerType);
				
			}else {
				
				//minimax for later turns
				Board curr_board = Board.cpyBoard(board);
				curr_board.myPieces = Board.scpmyPieces(board);
		        curr_board.enemyPieces = Board.scpenemyPieces(board);
				node root = new node(curr_board, null);
				node tempNode = minimax(root, 2, false);
				m = node.findRoot(tempNode).move;
				numTurns++;
			}
		}
		player.update(m);
		return m;
	}
	
	public node minimax(node root, int depth, boolean maximizingPlayer) {
		//System.out.println("depth = "+depth+" maximizingPlayer "+maximizingPlayer);
		char playerType;
		if(!maximizingPlayer) {
			playerType = myPlayer;
		}else {
			playerType = enemyPlayer;
		}
		
		//check loops
		ArrayList<Move> legalMoves = totalLegalMoves(root.board.myPieces, root.board, playerType);

		
		//base case
		if(depth == 0 || legalMoves.size() == 0) {
			root.heuristic = node.calculateHeuristic(root);
			return root;
		}
		
		//create child list for current node
		for(Move m: legalMoves) {

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
		
		//max node
		if(!maximizingPlayer) {
			node bestNode = null;
			node currNode = null;
			double bestValue = -99999;
			for(node child: root.childList) {
				bestNode = minimax(child, depth-1, false);

				if(bestNode.heuristic > bestValue) {
					currNode = bestNode;
					bestValue = bestNode.heuristic;
				}
			}

			return currNode;
		}
		
		//min node
		else {
			node bestNode = null;
			node currNode = null;
			double bestValue = 99999;
			for(node child: root.childList) {
				bestNode = minimax(child, depth-1, true);

				if(bestNode.heuristic < bestValue) {
					currNode = bestNode;
					bestValue = bestNode.heuristic;
				}
			}
			
			return currNode;
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
	
	public Move easyStart(ArrayList<Piece> myPieces, Board board, char playerType) {
		ArrayList<Move> legalMoves = totalLegalMoves(myPieces, board, playerType);
		numTurns++;
		//start with the pieces that never moved before
		for(Move m: legalMoves) {
			if(playerType == 'H') {
				if(m.i < 2 && m.d == Direction.RIGHT) {
					return m;
				}
			}else {
				if(m.j < 2 && m.d == Direction.UP) {
					return m;
				}
			}
		}
		return null;
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
		int positionBonus = 0;
		int numPiecesBonus = 0;
		int directionBonus = 0;
		Move m = current.move;
	
		for(Piece p: current.board.myPieces) {
			
			if(current.board.enemyType == 'H') {
				//get winDist
				winDist += Math.max(current.board.getSize() - p.getRow(), current.board.getSize()+1);
				//get enemyBlock
				enemyBlock += checkUP(p, current.board, current.board.enemyType);
				enemyBlock += checkDOWN(p, current.board, current.board.enemyType);
				enemyBlock += checkLEFT(p, current.board, current.board.enemyType);
				//get aroundDist
				aroundDist += checkRow(p, current.board, current.board.enemyType);
				//get bonus
				positionBonus += p.getRow()+p.getCol();

				
			}else {
				//get winDist
				winDist += Math.max(current.board.getSize() - p.getCol(), current.board.getSize()+1);
				//get enemyBlock
				enemyBlock += checkDOWN(p, current.board, current.board.enemyType);
				enemyBlock += checkLEFT(p, current.board, current.board.enemyType);
				enemyBlock += checkRIGHT(p, current.board, current.board.enemyType);
				//get aroundDist
				aroundDist += checkCol(p, current.board, current.board.enemyType);
				//get bonus
				positionBonus += p.getCol()+p.getRow();

			}
			
		}
		
		heuristic = numPiecesBonus+positionBonus+enemyBlock*Minimax.defensive-aroundDist*Minimax.unblock-winDist*Minimax.rush;
		
		if(current.board.playerType == 'H') {
			if(m.d == Direction.RIGHT) {
				heuristic += Minimax.direction;
			}
		}else {
			if(m.d == Direction.UP) {
				heuristic += Minimax.direction;
			}
		}
		
		if(current.board.myPieces.size() < current.parent.board.myPieces.size()) {
			heuristic += 1000;
		}
		return heuristic;
	}
	
	public static node findRoot(node current) {
		
		while(current.parent.parent != null) {
			current = current.parent;
		}
		System.out.println("current row = "+current.move.j);
		return current;
	}
	
	private static int checkRow(Piece p, Board board, char enemyType) {
		int num = 0;
		int row = p.getRow();
		int col = p.getCol();
		int i = 0;
		for(i=col+1;i<board.getSize();i++) {
			if(board.boardMap[row][i] == enemyType) {
				num++;
			}
		}
		return num;
	}
	
	private static int checkCol(Piece p, Board board, char enemyType) {
		int num = 0;
		int col = p.getCol();
		int row = p.getRow();
		int i = 0;
		for(i=row+1;i<board.getSize();i++) {
			if(board.boardMap[i][col] == enemyType) {
				num++;
			}
		}
		return num;
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
