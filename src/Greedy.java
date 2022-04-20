
public class Greedy implements Player
{
	int id;
	int cols;
	
    @Override
    public String name() {
        return "Greedy";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id;
    	this.cols = cols;
    }

    @Override
    public void calcMove( 		
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        
        int currentScore = calcScore(board, this.id);
        int opponentScore = calcScore(board, 3-this.id);
        int bestCol = 0;
        for(int k = 0; k <= 6; k++) {
        if(board.isValidMove(bestCol) == false) {
        	bestCol++;
        }
        }
        // Make a maximum value move.
        for(int i = 0; i <=6; i++) {
        	if(board.isValidMove(i) == true) {
        		board.move(i, id);
        		if(calcScore(board, this.id) > currentScore){
        			bestCol = i;
        			currentScore = calcScore(board, this.id);
        		}
        		board.unmove(i, id);
        		//board.move(i, 3-this.id);
        		//if(calcScore(board, 3-this.id) != opponentScore){
        		//	bestCol = i;
        		//}
        		//board.unmove(i,3-id);
        	}
        }
        
        arb.setMove(bestCol);
    }
	
 // Return the number of connect-4s that player #id has.
 	public int calcScore(Connect4Board board, int id)
 	{
 		final int rows = board.numRows();
 		final int cols = board.numCols();
 		int score = 0;
 		// Look for horizontal connect-4s.
 		for (int r = 0; r < rows; r++) {
 			for (int c = 0; c <= cols - 4; c++) {
 				if (board.get(r, c + 0) != id) continue;
 				if (board.get(r, c + 1) != id) continue;
 				if (board.get(r, c + 2) != id) continue;
 				if (board.get(r, c + 3) != id) continue;
 				score++;
 			}
 		}
 		// Look for vertical connect-4s.
 		for (int c = 0; c < cols; c++) {
 			for (int r = 0; r <= rows - 4; r++) {
 				if (board.get(r + 0, c) != id) continue;
 				if (board.get(r + 1, c) != id) continue;
 				if (board.get(r + 2, c) != id) continue;
 				if (board.get(r + 3, c) != id) continue;
 				score++;
 			}
 		}
 		// Look for diagonal connect-4s.
 		for (int c = 0; c <= cols - 4; c++) {
 			for (int r = 0; r <= rows - 4; r++) {
 				if (board.get(r + 0, c + 0) != id) continue;
 				if (board.get(r + 1, c + 1) != id) continue;
 				if (board.get(r + 2, c + 2) != id) continue;
 				if (board.get(r + 3, c + 3) != id) continue;
 				score++;
 			}
 		}
 		for (int c = 0; c <= cols - 4; c++) {
 			for (int r = rows - 1; r >= 4 - 1; r--) {
 				if (board.get(r - 0, c + 0) != id) continue;
 				if (board.get(r - 1, c + 1) != id) continue;
 				if (board.get(r - 2, c + 2) != id) continue;
 				if (board.get(r - 3, c + 3) != id) continue;
 				score++;
 			}
 		}
 		return score;
 	}
	
}
