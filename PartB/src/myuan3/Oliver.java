package myuan3;

import aiproj.slider.SliderPlayer;

import java.util.Scanner;

import aiproj.slider.Move;

public class Oliver implements SliderPlayer{
	
	Board myBoard;
	char playerType;
	
	public Oliver() {
		
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
	}

	@Override
	public void update(Move move) {
		
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

}
