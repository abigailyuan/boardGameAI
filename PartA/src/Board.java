
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
		for(i = 0;i<getSize();i++){
			boardMap[row][i] = line[i];
			if(boardMap[row][i] == 'H') {
				qizi q = new qizi(i, row);
				BoardGame.H.getQiziList().add(q);
			}else if(boardMap[row][i] == 'V') {
				qizi q = new qizi(i, row);
				BoardGame.V.getQiziList().add(q);
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
	
}
