package myuan3;

public class Board {

	private int size;
	char[][] boardMap;
	
	public Board(int size) {
		
		this.setSize(size);
		boardMap = new char[size][size];
	}
	
	public void readRow(int row, String rowLine ) {
		char[] line =  rowLine.toCharArray();
		int i = 0;
		for(char c: line) {
			boardMap[row][i] = c;
			if(boardMap[row][i] == 'H') {
				Piece q = new Piece(i, row);
				//BoardGame.H.getQiziList().add(q);
				i++;
			}else if(boardMap[row][i] == 'V') {
				Piece q = new Piece(i, row);
				//BoardGame.V.getQiziList().add(q);
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
	
}
