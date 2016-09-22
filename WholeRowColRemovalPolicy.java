/*
 * The last of the removal policy classes. If any gem in the row
 * or column of the current gem matches the current gem, remove
 * said gems. Removed gems must be in a row. For example: the
 * removal of row: 1 1 1 2 assuming the first gem is the current
 * gem would result in: 2. However, in the situation: 1 2 1 2, assuming
 * the first gem is the current gem would result in: 2 1 2.
 */


public class WholeRowColRemovalPolicy implements RemovalPolicy{

	// class fully complete
	
	private int flagged;
	
	public String description()
	{
		return "Adjacent gems in whole row/column will be removed";
	}
	
	public void flagConnectedGems(int row, int col, Board b)
	{
		
		if(!b.validGemAt(row, col))
		{
			String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
		    throw new RuntimeException(msg);
		}
		
		b.clearFlags();
		Gem myGem = b.gemAt(row, col);
		myGem.setFlag();
		flagged =1;
		
		// start looping to the right... shouldnt have to use try catch blocks
		for(int myCol = col+1; myCol<b.getCols(); myCol++)
		{
			if(myGem.getKind() == b.gemAt(row, myCol).getKind()) //if the consecutive one next to it is the same...
			{
				b.gemAt(row,myCol).setFlag();	// flag it
				flagged++;
			}
			else	
				break;	// otherwise, break out of the loop.
		}
		
		// same thing through the left
		for(int myCol = col-1; myCol >=0; myCol--)
		{
			if(myGem.getKind() == b.gemAt(row, myCol).getKind())
			{
				b.gemAt(row, myCol).setFlag();
				flagged++;
			}
			else
				break;
		}
		
		//loop down the row...
		for(int myRow = row+1; myRow <b.getRows(); myRow++)
		{
			if(myGem.getKind() == b.gemAt(myRow,col).getKind())
			{
				b.gemAt(myRow,col).setFlag();
				flagged++;
			}
			else
				break;
			
		}
		
		//and loop up
		for(int myRow = row-1; myRow>=0;myRow--)
		{
			if(myGem.getKind() == b.gemAt(myRow, col).getKind())
			{
				b.gemAt(myRow, col).setFlag();
				flagged++;
			}
			else
				break;
		}
		
		
	}
	
	public int scoreMove(int row, int col, Board b)
	{
		if(!b.validGemAt(row,col))	// if it isn't a valid gem...
		{
		      String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
		      throw new RuntimeException(msg);
	    }
		this.flagConnectedGems(row, col, b);	//call function above
		return flagged*flagged;	// return flagged squared
	}
	
	
	public String toString()
	{
		return "WholeRowColRemovalPolicy";
	}
	public String saveString()
	{
		return "WholeRowColRemovalPolicy";
	}
}
