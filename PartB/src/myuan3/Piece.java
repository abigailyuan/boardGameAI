package myuan3;

public class Piece {

	private int x;
	private int y;
	
	public Piece(int i, int row) {
		this.setX(i);
		this.setY(row);
	}

	private void setY(int row) {
		this.y = row;
		
	}

	public int getX() {
		return this.x;
	}

	public void setX(int i) {
		this.x = i;
	}

	public int getY() {
		
		return this.y;
	}
}
