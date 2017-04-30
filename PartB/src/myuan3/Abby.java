package myuan3;

import aiproj.slider.SliderPlayer;

import java.util.ArrayList;
import java.util.Scanner;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;

public class Abby implements SliderPlayer{
	
	private Board myBoard;
	private char playerType;
	private ArrayList<Piece> myPieces;
	private ArrayList<Piece> enemyPieces;
	
	public Abby() {
		
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
		
		//initialise player type
		this.playerType = player;
		
		//initialise my pieces
		this.myPieces.addAll(this.myBoard.getMyPieces());
		this.enemyPieces.addAll(this.myBoard.getEnemyPieces());
	}

	@Override
	public void update(Move move) {
		if(move != null) {
			//delete old piece
			this.myBoard.boardMap[move.j-1][move.i-1] = '+';
			
			//add new piece
			Direction d = move.d;
			switch(d) {
			//TODO handle cases
				case UP:
				case DOWN:
				case LEFT:
				case RIGHT:
					
			}
		}
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Piece getPiece(int row, int col) {
		
		//find the piece in my pieces
		for(Piece p: this.myPieces) {
			if(p.getX() == row && p.getY() == col) {
				return p;
			}
		}
		
		//find the piece in enemy's pieces
		for(Piece p: this.enemyPieces) {
			if(p.getX() == row && p.getY() == col) {
				return p;
			}
		}
		return null;
	}

}
