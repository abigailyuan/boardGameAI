package myuan3;

import java.util.ArrayList;

import aiproj.slider.Move.Direction;

public class Board {

	private int size;
	char[][] boardMap;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> enemyPieces;
	private char playerType;
	private char enemyType;
	
	public Board(int size, char playerType) {
		
		this.setSize(size);
		boardMap = new char[size][size];
		this.playerType = playerType;
		if(this.playerType == 'H') {
			this.enemyType = 'V';
		}else {
			this.enemyType = 'H';
		}
		this.setEnemyPieces(new ArrayList<Piece>());
		this.setMyPieces(new ArrayList<Piece>());
	}
	
	public void readRow(int row, String rowLine ) {
		char[] line =  rowLine.toCharArray();
		int i = 0;
		for(char c: line) {
			boardMap[row][i] = c;
			if(boardMap[row][i] == this.playerType) {
				Piece q = new Piece(i, row);
				this.getMyPieces().add(q);
				i++;
			}else if(boardMap[row][i] == this.enemyType) {
				Piece q = new Piece(i, row);
				this.getEnemyPieces().add(q);
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
		//TODO implement
		return Direction.UP;
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
	
}
