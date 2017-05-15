package myuan3;

import aiproj.slider.SliderPlayer;
import myuan3.Board.Corner;
import myuan3.Piece;

import java.util.ArrayList;
import java.util.Scanner;


import aiproj.slider.Move;
import aiproj.slider.Move.Direction;

public class Abby implements SliderPlayer{
	
	public static Board myBoard;
	private char playerType;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> enemyPieces;
	private Strategy strategy;
	
	public Abby() {
		//this.strategy = new Stupid("MiniMax");
		//this.strategy = new FastWin("FastWin");
		//this.strategy = new DFS("DFS");
		this.strategy = new Stupidminimax("Stupidminimax");
		this.myPieces = new ArrayList<Piece>();
		this.enemyPieces = new ArrayList<Piece>();
	}

	@Override
	public void init(int dimension, String board, char player) {
		
		//initialise a board with dimention
		myBoard = new Board(dimension, player);
		int row = dimension - 1;
		
		//read the board
		Scanner scanner = new Scanner(board);
		while(scanner.hasNextLine()) {
			String s = scanner.nextLine();
			this.myBoard.readRow(row, s);
			//this.myBoard.printRow(row);
			row--;
		}
		scanner.close();
		
		//Initialize player type
		this.playerType = player;
		
		//Initialize my pieces
		this.myPieces.addAll(this.myBoard.getMyPieces());
		this.enemyPieces.addAll(this.myBoard.getEnemyPieces());
		
	}

	@Override
	public void update(Move move){
		if(move != null) {
			//delete old piece
			this.myBoard.boardMap[move.j][move.i] = '+';
			
			//find the piece in list
			Piece p;
			try {
				p = findPiece(move.j, move.i);
				//System.out.println("this piece at"+p.getRow()+", "+p.getCol());
				
				//debug
				//System.out.println("("+p.getRow()+", "+p.getCol()+")"+"piece type: "+p.getType());
				//debug end
				
				//add new piece
				Direction d = move.d;
				//check if the piece is at edge or corner
				Direction edge = this.myBoard.checkEdge(move.j, move.i);
				Corner corner = this.myBoard.checkCorner(move.j, move.i);
				
				switch(d) {
					case UP:
						if(this.myBoard.checkEdgeUP(move.j, move.i)) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
								this.myBoard.myPieces.remove(p);
							}else {
								this.enemyPieces.remove(p);
								this.myBoard.enemyPieces.remove(p);
							}
							//debug
							//System.out.println("enter here");
							//debug end
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[move.j+1][move.i] = p.getType();
							p.setCol(move.i);
							p.setRow(move.j+1);
						}
						break;
					case DOWN:
						if(this.myBoard.checkEdgeDOWN(move.j, move.i)) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}else {
								this.enemyPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[move.j-1][move.i] = p.getType();
							p.setCol(move.i);
							p.setRow(move.j-1);
						}
						break;
					case LEFT:
						if(this.myBoard.checkEdgeLEFT(move.j, move.i)) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}else {
								this.enemyPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[move.j][move.i-1] = p.getType();
							p.setCol(move.i-1);
							p.setRow(move.j);
						}
						break;
					case RIGHT:
						if(this.myBoard.checkEdgeRIGHT(move.j, move.i)) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
								this.myBoard.myPieces.remove(p);
							}else {
								this.enemyPieces.remove(p);
								this.myBoard.enemyPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[move.j][move.i+1] = p.getType();
							p.setCol(move.i+1);
							p.setRow(move.j);
						}
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Move move() {
		Move m = this.strategy.makeMove(myBoard, myPieces, enemyPieces, this.playerType, this);
		//System.out.println("move is ("+m.j+", "+m.i+")");
		return m;
	}
	
	public Piece findPiece(int row, int col) throws Exception {
		
		Piece pReturn = null;
		
		//find the piece in my pieces
		for(Piece p: this.myPieces) {
			if(p.getCol() == col && p.getRow() == row) {
				pReturn = p;
			}
		}
		
		//find the piece in enemy's pieces
		for(Piece p: this.enemyPieces) {
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
