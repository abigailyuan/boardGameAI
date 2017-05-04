package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move.Direction;

public class Board {

	private int size;
	char[][] boardMap;
	public ArrayList<Piece> myPieces;
	public ArrayList<Piece> enemyPieces;
	private char playerType;
	private char enemyType;
	
	//Enumeration of all four corners of the board
	public enum Corner {UL, UR, DL, DR};
	
	public Board(int size, char playerType) {
		
		this.setSize(size);
		boardMap = new char[size][size];
		this.playerType = playerType;
		if(this.playerType == 'H') {
			this.enemyType = 'V';
		}else {
			this.enemyType = 'H';
		}
		this.myPieces = new ArrayList<Piece>();
		this.enemyPieces = new ArrayList<Piece>();
	}
	
	public void readRow(int row, String rowLine ) {
		
		char[] line =  rowLine.toCharArray();
		int i = 0;
		for(char c: line) {
			boardMap[row][i] = c;
			if(boardMap[row][i] == this.playerType) {
				Piece q = new Piece(i, row, this.playerType);
				this.myPieces.add(q);
				i++;
			}else if(boardMap[row][i] == this.enemyType) {
				Piece q = new Piece(i, row, this.enemyType);
				this.enemyPieces.add(q);
				i++;
			}else if(boardMap[row][i] == '+') {
				i++;
			}else if(boardMap[row][i] == 'B') {
				i++;
			}
		}
		
	}
	
	public boolean isEmpty(int row, int col) {
		
		return true;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void printRow(int row) {
		System.out.println(this.boardMap[row]);
	}
	
	public Direction checkEdge(int row, int col) {
		if(row == 0) {
			return Direction.DOWN; 
		}else if(row == this.size - 1) {
			return Direction.UP;
		}else if(col == 0) {
			return Direction.LEFT;
		}else if(col == this.size - 1) {
			return Direction.RIGHT;
		}else {
			return null;
		}
	}
	
	public boolean checkEdgeUP() {
		//TODO
		return true;
	}
	public boolean checkEdgeRIGHT() {
		//TODO
		return true;
	}
	public boolean checkEdgeLEFT() {
		//TODO
		return true;
	}
	public boolean checkEdgeDOWN() {
		//TODO
		return true;
	}
	
	public Corner checkCorner(int row, int col) {
		if(row == 0 && col == 0) {
			return Corner.DL;
		}else if(row == 0 && col == this.size - 1) {
			return Corner.DR;
		}else if(row == this.size - 1 && col == 0) {
			return Corner.UL;
		}else if(row == this.size - 1 && col == this.size - 1) {
			return Corner.UR;
		}else {
			return null;
		}
	}

	public ArrayList<Piece> getMyPieces() {
		return myPieces;
	}

	public void setMyPieces(ArrayList<Piece> myPieces) {
		this.myPieces = myPieces;
	}

	public ArrayList<Piece> getEnemyPieces() {
		return enemyPieces;
	}

	public void setEnemyPieces(ArrayList<Piece> enemyPieces) {
		this.enemyPieces = enemyPieces;
	}

	public int convertRow(int row) {
		return this.size-1 - row;
	}
	
}
