package myuan3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class BoardGame {
	
	public static Board board;
	public static Player H;
	public static Player V;
	

	public static void main(String args[]) {
		
		Abby abby = new Abby();
		abby.init(4, "H + + +\nH + B +\nH B + +\n+ V V V\n", 'H');
		
		Oliver oliver = new Oliver();
		oliver.init(4, "H + + +\nH + B +\nH B + +\n+ V V V\n", 'V');
	
	}
}
