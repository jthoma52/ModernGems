// Interface specifying methods required for any gem removal policy in
// Modern Gems. All methods must be implemented.
public interface RemovalPolicy{
  
  // Flag all gems that would be removed if location (r,c) were
  // selected by this policy.
  public void flagConnectedGems(int row, int col, Board b);
  
  // Provides the score for the suggested (r,c) move. The gems
  // involved in the move may be flagged in the process or may remain
  // unflagged.
  public int scoreMove(int row, int col, Board b);
  
  // a human-friendly description of this policy's rules.
  public String description();

  // return the name of the class
  public String toString();

  // return the name of the class
  public String saveString();
}
    
