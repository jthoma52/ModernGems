import java.util.Scanner;
public class Board {

	Gem[][] myBoard;   //Array of gems to keep track of "numbers"
	
	//Class is complete! passes all tests.
	
	public Board(int[][] g)
	{
		myBoard = new Gem[g.length][g[0].length];  // row, column creation of a board
		for(int row = 0; row<myBoard.length;row++)  //fill the board in with corresponding ints
		{
			for(int col = 0; col<myBoard[0].length;col++)
			{
				if(g[row][col] <0)
				{
					myBoard[row][col] = Gem.makeEmpty();		// if negative, make an empty gem in that position
				}
				else
				{
					myBoard[row][col] = new Gem(g[row][col]);	// new gem with the int as "kind" ...
				}
			}
		}	
	}
	// looks at our board, pulls out the kinds of gems, puts them into an array, returns a new board with that array.
	public Board clone()
	{
		int[][] myIntArr = new int[myBoard.length][myBoard[0].length];    // make an int array with proper (row,col) sizes
		for(int row = 0; row < myBoard.length; row++)
		{
			for(int col = 0; col <myBoard[0].length; col++)
			{
				myIntArr[row][col] = myBoard[row][col].getKind();	// set myArr(row,col) to corresponding boards kind
			}
		}
		Board myNewBoard = new Board(myIntArr);	//create new board from int array
		for(int row =0; row <myBoard.length;row++)
		{
			for(int col = 0; col<myBoard[0].length;col++)
			{
				if(myBoard[row][col].flagged())	// search through the old board for flagged gems. if one is flagged...
					myNewBoard.gemAt(row, col).setFlag(); //... flag it in the new board too.
			}
		}
		return myNewBoard;  //return newly built board
	}
	
	
	public int getRows() {return myBoard.length;}		// returns num rows
	public int getCols() {return myBoard[0].length;}	//returns num columns
	
	//checks for valid gems. TEST THIS LATER. NOT SURE IF ZERO INDEXED OR NOT.
	public boolean validGemAt(int row, int col)
	{
		if( row >= myBoard.length || col >= myBoard[0].length	//makes sure row and col are out of range and....
				|| row < 0 || col <0
				|| myBoard[row][col].isEmpty())	// ... checks if the spot is empty.
		{
			return false;	//if any of those, return false
		}
		return true;
	}
	
	//could just check the bottom left corner.....
	public boolean hasValidGem()
	{
		for(int row =0; row<myBoard.length;row++)
		{
			for(int col =0; col<myBoard[0].length;col++)
			{
				if(!myBoard[row][col].isEmpty())	// if there is atleast one unempty gem...
					return true;
			}
		}
		return false;
	}
	
	//finds a gem at ( row , col )
	public Gem gemAt(int row, int col)
	{
		return myBoard[row][col];	//return (row,col) gem.
	}
	
	//clears all flags
	public void clearFlags()
	{
		for(int row = 0; row < myBoard.length; row++)
		{
			for(int col = 0; col <myBoard[0].length; col++)
			{
				if(myBoard[row][col].flagged())	//if (row,col) gem is flagged
					myBoard[row][col].clearFlag(); //remove the flag.
			}
		}
		
	}
	
	
	
	//works
	public void doRemovals()
	{
		//makes any gem empty that was flagged.
		for(int row = 0; row<myBoard.length;row++)
		{
			
			for(int col =0; col < myBoard[0].length; col++)
			{
				if(myBoard[row][col].flagged())	// if gem at (row,col) is flagged...					
				{
					myBoard[row][col] = Gem.makeEmpty();	// make it empty
				}
			}
		}
		
		
		dropRows();   // see below
		allignLeft(); // see below
	}
	
	//fully works. uses a bubble sort (KIND OF) to shift things.
	public void dropRows()
	{
		for(int i =0; i <myBoard[0].length;i++) //goes through col times.
		{
			for(int col = 0; col < myBoard[0].length; col++)  //looks at each column...
			{
				for(int row = myBoard.length-1; row > 0; row--)
				{
					if(myBoard[row][col].isEmpty())
					{
						myBoard[row][col]= myBoard[row-1][col]; //move the num above it down
						myBoard[row-1][col] = Gem.makeEmpty(); //move the empty gem up
					}
				}
			}
		}
	}
	
	//works
	public void allignLeft()
	{
		for(int c = 0; c <myBoard[0].length;c++)   //runs through this col times.
		{
			for(int col =0; col <myBoard[0].length-1; col++)  // runs through col times again, incase more than one col is empty.
			{
				if(colIsEmpty(col))   //if the col is empty...
				{
					for(int i = 0; i <myBoard.length; i++) //moves through the rows and...
					{
						myBoard[i][col] = myBoard[i][col+1];  //moves each element to the left
						myBoard[i][col+1] = Gem.makeEmpty(); //makes the right column empty.
					}
				}
				
			}
		}
	}
	
	
	//determines if a column is empty
	public boolean colIsEmpty(int col)
	{
		for(int row = 0; row <myBoard.length;row++)
		{
			if(!myBoard[row][col].isEmpty())  //if any spot isnt empty, return false
				return false;
		}
		return true;  // else, return true
	}
	//works
	public String saveString()
	{
		String myString = "";
		for(int row =0; row <myBoard.length;row++)
		{
			for(int col = 0; col<myBoard[0].length;col++)
			{
				if(myBoard[row][col].isEmpty()){	// concatinate a period if (row,col) is empty
					myString+= ". ";
				}
				else{
					myString+=myBoard[row][col].getKind() +  " ";    // concatinate the int and a space if it aint empty
				}
			}
			myString +="\n";	// add a \n at the end of each line
		}
		return myString;  //return the built up string.
	}
	//works
	public static Board fromSaveString(String s)
	{
		int rows=0;
		int cols =0;
		
		//counts the numbers of rows and columns in the board. This is 100% correct.
		for(int i =0; i <s.length();i++)
		{
			if(s.charAt(i)== '\n')   //counts the nums of newlines. works
			{
				rows++;
			}
			if(s.charAt(i) != '\n' && s.charAt(i) !=' ')   // if its not a newline or a space, count it.
			{
				cols++;
			}
		}
		cols = cols/rows; //divide for numCols
		Scanner input = new Scanner(s);
		
		int[][] myArr = new int[rows][cols];
		//start a new arr
	
		for(int row = 0; row<rows; row++)
		{
			for(int col =0; col<cols; col++)
			{
				String looking = input.next();
				if(looking.equals("."))  // if its  a period, set the arr correspondance to -1 (aka make empty later)
				{
					myArr[row][col] = -1;
				}
				else
				{
					myArr[row][col]= Integer.parseInt(looking); // if its not a period, it must be an int. so parse it
				}
			}
		}
		input.close();
		return new Board(myArr);  // return a new board from myArr
	
	}
	
	

	
	
	
	
	
	
  //given method by teacher.
  public String toString(){
    StringBuilder sb = new StringBuilder();
    // Col header
    sb.append("   ");
    for(int j=0; j<this.getCols(); j++){
      sb.append(String.format("%3d",j));
    }
    sb.append("\n");
    sb.append("   ");
    for(int j=0; j<this.getCols(); j++){
      sb.append("---");
    }
    sb.append("\n");
    // Main body of board
    for(int i=0; i<this.getRows(); i++){
      sb.append(String.format("%2d|",i));
      for(int j=0; j<this.getCols(); j++){
        Gem g = this.gemAt(i,j);
        char flag = g.flagged() ? '*' : ' ';
        if(j==0){
          sb.append(String.format("%3s%c",g.kindString(),flag));
        }
        else{
          sb.append(String.format("%2s%c",g.kindString(),flag));
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
