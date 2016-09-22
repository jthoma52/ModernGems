// In the Single Removal Policy, only the gem indicated by
// flagConnected() is flagged for removal. No other gems are flagged
// so players can only score single points at a time.
public class SingleRemovalPolicy implements RemovalPolicy{
  public String description(){
    return "Single gems are removed";
  }	

  // Mark all gems connected to the gem at row/col on the given board
  public void flagConnectedGems(int row, int col, Board b) {
    if(!b.validGemAt(row,col)){
      String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
      throw new RuntimeException(msg);
    }
    b.clearFlags();
    Gem center = b.gemAt(row,col);
    center.setFlag();
  }

  // Determine the score for removing the gem at row/col; do not
  // actually perform the removal on the board, at most flag the gems
  // for removal.  The numeric score is the square of the number of
  // gems removed.
  public int scoreMove(int row, int col, Board b) {
    if(!b.validGemAt(row,col)){
      String msg = String.format("Position (%d,%d) invalid on board:\n%s",row,col,b.toString());
      throw new RuntimeException(msg);
    }
    this.flagConnectedGems(row,col,b);
    return 1;
  }    
    
  public String toString(){ return "SingleRemovalPolicy"; }
  public String saveString(){ return "SingleRemovalPolicy"; }
}
    
