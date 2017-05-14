package myuan3;

import java.util.ArrayList;
import java.util.LinkedList;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;



public class Minimax extends Strategy{
	public Minimax(String name) {
		super(name);
		
	}

	private LinkedList<Move> myMoves;
	private LinkedList<Piece> myMoves_p;
  private static final double VALUE_WIN  =  999999;
  private static final double VALUE_LOSS = -999999;
  private static final double VALUE_INTERRUPT = -9999990;



@Override
public Move makeMove(Board board, ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType, SliderPlayer player) {

  Move MyMove = null;
  Move[] step;
  step = new Move[1];
  step[0] = null;
  char enemyType = 'V';
  
  if(playerType == 'H') {
	  enemyType = 'V';
  }else {
	  enemyType = 'H';
  }

  int maxDepth = 9; // add here
      boolean allowInterrupt = false; // can not interrupt for depth = 2
      int Counter = 0;

  for(int depth=2; depth<maxDepth; depth += 2) {
    double v = alphaBeta(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, board, depth , true, step, true, myPieces, enemyPieces, playerType, enemyType, player, allowInterrupt);
    allowInterrupt = true;

    if(v==VALUE_INTERRUPT) {
              break;
          }
          MyMove = step[0];

    if(v==VALUE_WIN || v==VALUE_LOSS) {
      break;
    }

  }

      myMoves.addFirst(MyMove);


      if (MyMove==null) {
    //myMoves_p.addFirst(null);
          return null; // pass this round.
      } else {
    //myMoves_p.addFirst(((Abby)player).findPiece(MyMove.j,MyMove.i));
    //sboard.findPiece[MyMove.i][MyMove.j].movePiece(MyMove.d, board);

          return MyMove;
      }
}

private double alphaBeta(double a, double b, Board board, int depth, boolean roleFlag, Move[] step, boolean root,ArrayList<Piece> myPieces, ArrayList<Piece> enemyPieces, char playerType, char enemyType, SliderPlayer player, boolean allowInterrupt) {

		//Player currentPlayer = (roleFlag)?player:enemyPlayer;
		char currentPlayer = (roleFlag)?playerType:enemyType;
	ArrayList<Move> moves = null;
	if(depth > 0) {
		moves = totalLegalMoves(myPieces, board, playerType);
	}//init

	if(moves==null) {
			double eval = evaluate(roleFlag);
		return eval;
	}

	if(moves.size()==0) {
			if(enemyPieces.size()==0) {
					return VALUE_LOSS; // lose
					}
					if(myPieces.size()==0) {
					return VALUE_WIN; // win
					}
					// pass
					return alphaBeta(a, b, depth-1, !roleFlag, null, false);
			}

			int Counter;
			// ------- Interruption ------
			Counter++;
	if(Counter>=2000) { // about 1~2ms
			Counter = 0;
			if (allowInterrupt) {//false
					if(needInterrupt()) {
							return VALUE_INTERRUPT;
							}
					}
			}
			// ----------------------------

	double value = (roleFlag)?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY;

	for(Move m:moves) {

			//------------make the move--------
					Piece P = board.findPieces[m.i][m.j];
					boolean offEdgeFlag = P.movePiece(m.d, board);
					//---------------------------------


			double tmpValue = alphaBeta(a, b, depth-1, !roleFlag, null, false);

					// ------- break it ------
			if(tmpValue==VALUE_INTERRUPT) {
					return tmpValue;
				}
					// ----------------------------

			// only for first level
		if(root) { //record movements
			if(tmpValue>value) {
				step[0] = m; // choose this move;
			} else {
				if(tmpValue==value) { // there is a tile, go right first
					if(m.d == Move.Direction.RIGHT) {
							step[0] = m;
											}
				}
			}
		}


					// alpha beta
			if(roleFlag) {
					value = Math.max(value, tmpValue);
					a     = Math.max(value, a);


					} else {
					value = Math.min(value, tmpValue);
					b     = Math.min(value, b);
					}

					if(offEdgeFlag) {
							myPieces.add(P);
					} else {
					board.findPieces[P.getCol()][P.getRow()] = null;
					}
					board.findPieces[m.i][m.j] = P;
			P.setCol(m.i);
			P.setRow(m.j);

			if(b<=a) {
					break; // cut
					}

			}

			return value;
}

private boolean needInterrupt() {
		return false;
	}

public Piece findPiece(int row, int col, ArrayList<Piece>myPieces, ArrayList<Piece>enemyPieces) throws Exception {
	
	Piece pReturn = null;
	
	//find the piece in my pieces
	for(Piece p: myPieces) {
		if(p.getCol() == col && p.getRow() == row) {
			pReturn = p;
		}
	}
	
	//find the piece in enemy's pieces
	for(Piece p: enemyPieces) {
		if(p.getCol() == col && p.getRow() == row) {
			pReturn = p;
		}
	}
	if(pReturn == null) {
		throw new Exception("Piece not found.");
	}else {
		return pReturn;
	}
}
}
