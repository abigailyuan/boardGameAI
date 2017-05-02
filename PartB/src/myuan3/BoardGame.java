package myuan3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;
public class BoardGame {
	
	public static Board board;
	public static Player H;
	public static Player V;
	

	public static void main(String args[]) {
		
		Abby abby = new Abby();
		abby.init(4, "H + + +\nH + B +\nH B + +\n+ V V V\n", 'H');
		
		//debug
		System.out.println("---------------------");
		Move m = new Move(0, 3, Direction.UP);
		abby.update(m);
		int row = 3;
		while(row >= 0) {
			abby.myBoard.printRow(row);
			row --;
		}
		//debug end
		
		Oliver oliver = new Oliver();
		oliver.init(4, "H + + +\nH + B +\nH B + +\n+ V V V\n", 'V');
	
	}
}
