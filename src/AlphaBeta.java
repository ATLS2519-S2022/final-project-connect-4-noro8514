
public class AlphaBeta implements Player{
	int id;
	int cols;
	/**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
    public String name() {
    	return "AyBee";
    };

  
    /**
     * Initialize the player. The game calls this method once,
     * before any calls to calcMove().
     * 
     * @param id integer identifier for the player (can get opponent's id via 3-id);
     * @param msecPerMove time allowed for each move
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id;
    	this.cols = cols;
    };

    
    /**
     * Called by driver program to calculate the next move.
     *  
     * @param board current connect 4 board
     * @param oppMoveCol column of opponent's most recent move; -1 if this is the first move 
     * 		  of the game; note that the board may not be empty on the first move of the game!
     * @param arb handles communication between game and player
     * @throws TimeUpException If the game determines the player has run out of time
     */
    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException{
    	if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
    	};
    	int move = 0;
    	int maxDepth = 1;
    	int startValue;
    	int currentValue;
    	int alpha = -1000;
    	int beta = 1000;
    	
    	
    	while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {

    		startValue = -1000;

    		
    		for(int i = 0; i <=6; i++) {
            	if(board.isValidMove(i) == true) {
            		board.move(i, id);
            		currentValue = minimax(board, maxDepth-1, false, arb, alpha, beta);
            		board.unmove(i, id);
            		if(currentValue > startValue){
            			move = i;
            			startValue = currentValue;
            		}

            	}
    		}
    		System.out.println(maxDepth);
    		arb.setMove(move);
    		maxDepth++;
    	}
    }
    
    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb, int alpha, int beta) {
    	int value;
    	int temp;
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return score(board);
    	}
    	
    	if (isMaximizing) {
    		value = -1000;
    		for(int i = 0; i <=6; i++) {
    			if (board.isValidMove(i) == true)
    			{
    			board.move(i, id);
    			temp = minimax(board, depth-1, false, arb, alpha, beta);
    			if(temp > value) {
    				value = temp;
    			}
    			board.unmove(i, id);
    			if(value > alpha) {
    				alpha = value;
    			}
    			if(alpha >= beta) {
    				break;
    			}
    		}
    		}
    		return value;
    	}
    	else {
    		value = 1000;
    		for(int i = 0; i <=6; i++) {
    			if (board.isValidMove(i) == true)
    			{
    			board.move(i, 3 - this.id);
    			temp = minimax(board, depth-1, true, arb, alpha, beta);
    			if(temp < value) {
    				value = temp;
    			}
    			board.unmove(i, 3-this.id);
    			if(value < beta) {
    				beta = value;
    			}
    			if(alpha >= beta) {
    				break;
    			}
    		}
    		}
    		return value;
    	}
    	
    }
    
    public int score(Connect4Board board) {
    	return calcScore(board, id) - calcScore(board, 3-id);
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
