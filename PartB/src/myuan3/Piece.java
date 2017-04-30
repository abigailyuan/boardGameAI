package myuan3;

public class Piece {

	private int col;
	private int row;
	private char Type;
	
	public Piece(int i, int row, char Type) {
		this.setCol(i);
		this.setRow(row);
		this.Type = Type;
	}

	public void setRow(int row) {
		this.row = row;
		
	}

	public int getCol() {
		return this.col;
	}

	public void setCol(int i) {
		this.col = i;
	}

	public int getRow() {
		
		return this.row;
	}
	
	public char getType() {
		return this.Type;
	}
}
