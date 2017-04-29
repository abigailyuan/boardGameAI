package myuan3;
import java.util.ArrayList;

public class Player {

	
	private int numLegalMove;
	private ArrayList<Piece> qiziList;
	private boolean type; //V player is 1, H player is 0.
	
	
	public Player(boolean type) {
		
		this.numLegalMove = 0;
		this.type = type;
		this.qiziList = new ArrayList<Piece>();
	}
	
	public void calculateNumLegalMove() {
		
		//H player
		if(!type){
			for(Piece q: getQiziList()) {
				
				int qx = q.getX();
				int qy = q.getY();
				if((qx+1)<=(BoardGame.board.getSize()-1) &&(BoardGame.board.boardMap[qy][qx+1] == '+')) {
					this.numLegalMove += 1;
					
				}
				if((qy+1)<=(BoardGame.board.getSize()-1) &&(BoardGame.board.boardMap[qy+1][qx] == '+')) {
					this.numLegalMove += 1;
					
				}
				if((qy-1)>= 0 &&(BoardGame.board.boardMap[qy-1][qx] == '+')) {
					this.numLegalMove += 1;
					
				}
				
			}
		}else {// V player
			for(Piece q: getQiziList()) {
				
				int qx = q.getX();
				int qy = q.getY();
				
				
				if((qx+1)<=(BoardGame.board.getSize()-1) &&(BoardGame.board.boardMap[qy][qx+1] == '+')) {
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

	public ArrayList<Piece> getQiziList() {
		return qiziList;
	}

	public void setQiziList(ArrayList<Piece> qiziList) {
		this.qiziList = qiziList;
	}
	
	
	

	
}
