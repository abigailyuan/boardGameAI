import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
public class BoardGame {
	
	public static Board board;
	public static Player H;
	public static Player V;
	private static int size;

	public static void main(String args[]) {
		
		BufferedReader br = null;
		
		//create two players
		H = new Player('H', false);
		V = new Player('V', true);
		
		try {

            // Refer to this http://www.mkyong.com/java/how-to-read-input-from-console-java/
            // for JDK 1.6, please use java.io.Console class to read system input.
            br = new BufferedReader(new InputStreamReader(System.in));

            //load the board.
            boolean flag = true;
            while (flag) {

             
                int size = Integer.parseInt(br.readLine());
                board = new Board(size);
                
                int i = 0;
                for(i = 0;i<size;i++) {
                	
                	board.readRow(i, br.readLine());
                }
                //System.out.println("board loaded.");
                flag = false;
                
                int j = 0;
                
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
		V.calculateNumLegalMove();
		int vLegalMove = V.getNumLegalMove();
		
		H.calculateNumLegalMove();
		int hLegalMove = H.getNumLegalMove();
		
		
		System.out.println(hLegalMove);
		System.out.println(vLegalMove);
		
		
		
	}
}
