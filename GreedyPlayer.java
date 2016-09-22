/*
 * The GreedyPlayer class goes through and chooses the move  
 * that would result in the maximum amount of points on
 * each move. This is an automated player.
 */



public class GreedyPlayer implements Player{

	
	public void executeMove(Game game)
	{
		Board myBoard = game.getBoard();
		RemovalPolicy myPolicy = game.getPolicy();
		
		int[] maxSpot = {myBoard.getRows()-1,myBoard.getCols()-1};	// start at the bottom right corner
		int maxScore = 0;
		
		//look at each spot and find the max score. loops from bottom right to top left
		for(int row = myBoard.getRows()-1; row >=0; row--)
		{
			for(int col = myBoard.getCols()-1; col>=0; col--)
			{
				//if it aint empty and its score is higher than the recorded highest.
				if(!myBoard.gemAt(row, col).isEmpty() && myPolicy.scoreMove(row, col, myBoard) >= maxScore)
				{
					maxSpot[0] = row;
					maxSpot[1] = col;
					maxScore = myPolicy.scoreMove(row, col, myBoard);   //set new max score spot.
				}
			}
		}
		game.removeGemAdjustScore(maxSpot[0], maxSpot[1]);   //remove the max score gem
		
	}
	
	
	public String toString()
	{
		return "GreedyPlayer";
	}
	
	public String saveString()
	{
		return toString();
	}
}
