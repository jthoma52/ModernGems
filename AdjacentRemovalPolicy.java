/*
 * This class is the first of the removal policies. If any gem 
 * in any immediate NSEW direction is the same gem (number) as
 * the current gem, remove it as well.
 */


public class AdjacentRemovalPolicy implements RemovalPolicy{
	
	//class is complete!
	
	private int flagged;
	
	
	public String description()
	{
		return "Adjacent gems of the same kind will be removed";
	}
	
	public void flagConnectedGems(int row, int col, Board b)
	{
		if(!b.validGemAt(row,col))
		{
			String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
		    throw new RuntimeException(msg);
		}
		b.clearFlags();
		
		Gem center = b.gemAt(row, col);
		center.setFlag();
		flagged = 1;
		
		//checks each plus wing of the center. Catches exceptions when they occur.
		try
		{
			//check bottom
			if(b.gemAt(row+1,col).getKind() == center.getKind())	// if the theyre both of he same kind..
			{
				b.gemAt(row+1,col).setFlag();  //flag it
				flagged++;
			}
		}
		catch(IndexOutOfBoundsException e){}	// catch any out of bounds indexes and just do nothing with them.
		
		//same as above, just different area. all are the same from here...
		try
		{
			//top check
			if(b.gemAt(row-1, col).getKind() == center.getKind())
			{
				b.gemAt(row-1,col).setFlag();
				flagged++;
			}
		}
		catch(IndexOutOfBoundsException e){}
		
		try
		{
			//right check
			if(b.gemAt(row,col+1).getKind() == center.getKind())
			{
				b.gemAt(row,col+1).setFlag();
				flagged++;
			}
		}
		catch(IndexOutOfBoundsException e){}
		
		
		try
		{
		//left check
			if(b.gemAt(row, col-1).getKind() == center.getKind())
			{
				b.gemAt(row, col-1).setFlag();
				flagged++;
			}
		}
		catch(IndexOutOfBoundsException e){}
		
		
	}
	
	
	public int scoreMove(int row, int col, Board b)
	{
		if(!b.validGemAt(row, col))	// if it isnt a valid gem
		{
			String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
		      throw new RuntimeException(msg);	// raise this runtime exception
		}
		this.flagConnectedGems(row,col,b);
		
		return flagged*flagged;	// return flagged squared.
		
	}
	
	//works 100%
	public String toString()
	{
		return "AdjacentRemovalPolicy";
	}
	
	//works100%
	public String saveString()
	{
		return "AdjacentRemovalPolicy";
	}

}
