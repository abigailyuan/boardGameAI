package myuan3;

import aiproj.slider.SliderPlayer;
import myuan3.Board.Corner;

import java.util.ArrayList;
import java.util.Scanner;


import aiproj.slider.Move;
import aiproj.slider.Move.Direction;

public class Abby implements SliderPlayer{
	
	private Board myBoard;
	private char playerType;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> enemyPieces;
	private Strategy strategy;
	
	public Abby() {
		this.strategy = new DFS("DFS");
	}

	@Override
	public void init(int dimension, String board, char player) {
		
		//initialise a board with dimention
		myBoard = new Board(dimension, player);
		int row = 0;
		
		//read the board
		Scanner scanner = new Scanner(board);
		while(scanner.hasNextLine()) {
			String s = scanner.nextLine();
			this.myBoard.readRow(row, s);
			this.myBoard.printRow(row);
			row++;
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
			int j = this.myBoard.convertRow(move.j);
			//delete old piece
			this.myBoard.boardMap[j][move.i] = '+';
			
			//find the piece in list
			Piece p;
			try {
				p = findPiece(move.i, j);
				
				//add new piece
				Direction d = move.d;
				//check if the piece is at edge or corner
				Direction edge = this.myBoard.checkEdge(j, move.i);
				Corner corner = this.myBoard.checkCorner(j, move.i);
				
				switch(d) {
				//TODO handle cases
					case UP:
						if(edge == Direction.UP || corner == Corner.UL || corner == Corner.UR) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[j+1][move.i] = p.getType();
							p.setCol(move.i);
							p.setRow(j+1);
						}
					case DOWN:
						if(edge == Direction.DOWN || corner == Corner.DL || corner == Corner.DR) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[j-1][move.i] = p.getType();
							p.setCol(move.i);
							p.setRow(j+1);
						}
					case LEFT:
						if(edge == Direction.LEFT || corner == Corner.UL || corner == Corner.DL) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[j][move.i-1] = p.getType();
							p.setCol(move.i-1);
							p.setRow(j);
						}
					case RIGHT:
						if(edge == Direction.RIGHT || corner == Corner.UR || corner == Corner.DR) {
							//remove if out of edge
							if(p.getType() == this.playerType) {
								this.myPieces.remove(p);
							}
						}else {
							//move the piece on board and change the coordinates
							this.myBoard.boardMap[j][move.i+1] = p.getType();
							p.setCol(move.i+1);
							p.setRow(j);
						}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Move move() {
		Move m = this.strategy.makeMove(myBoard, myPieces, enemyPieces, this.playerType);
		return m;
	}
	
	private Piece findPiece(int row, int col) throws Exception {
		
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
