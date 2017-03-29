import java.util.ArrayList;

public class Player {

	private char name;
	private int numLegalMove;
	private ArrayList<qizi> qiziList;
	private boolean type; //V player is 1, H player is 0.
	
	
	public Player(char name, boolean type) {
		this.name = name;
		this.numLegalMove = 0;
		this.type = type;
		this.qiziList = new ArrayList<qizi>();
	}
	
	public void calculateNumLegalMove() {
		int i = 0;
		
		//H player
		if(!type){
			for(qizi q: getQiziList()) {
				
				int qx = q.getX();
				int qy = q.getY();
				if((qx+1)<=5 &&(BoardGame.board.boardMap[qy][qx+1] == '+')) {
					this.numLegalMove += 1;
					
				}
				if((qy+1)<=5 &&(BoardGame.board.boardMap[qy+1][qx] == '+')) {
					this.numLegalMove += 1;
					
				}
				if((qy-1)>= 0 &&(BoardGame.board.boardMap[qy-1][qx] == '+')) {
					this.numLegalMove += 1;
					
				}
				
			}
		}else {// V player
			for(qizi q: getQiziList()) {
				
				int qx = q.getX();
				int qy = q.getY();
				
				
				if((qx+1)<=5 &&(BoardGame.board.boardMap[qy][qx+1] == '+')) {
					this.numLegalMove += 1;
					
					
				}
				if((qx-1)>= 0 &&(BoardGame.board.boardMap[qy][qx-1] == '+')) {
					this.numLegalMove += 1;
					
				}
				if((qy-1)>=0 &&(BoardGame.board.boardMap[qy-1][qx] == '+')) {
					this.numLegalMove += 1;
					
				}
			}
				
		}
	
	}
	
	public int getNumLegalMove() {
		return this.numLegalMove;
	}

	public ArrayList<qizi> getQiziList() {
		return qiziList;
	}

	public void setQiziList(ArrayList<qizi> qiziList) {
		this.qiziList = qiziList;
	}
	
	
	

	
}
