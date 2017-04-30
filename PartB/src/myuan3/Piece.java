package myuan3;

public class Piece {

	private int col;
	private int row;
	
	public Piece(int i, int row) {
		this.setCol(i);
		this.setRow(row);
	}

	private void setRow(int row) {
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
}
