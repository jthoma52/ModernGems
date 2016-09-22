// Gems which exist in board.  Each Gem has a kind(int), a flag
// status(boolean) that marks it for scoring/deletion, and an isEmpty
// property(boolean). Empty spaces on boards are represented by a gem
// created with Gem.makeEmpty().
public class Gem {

  // Kind of the gem, use sameKind to compare if two gems have the
  // same kind.
  private final int kind;

  // Return true if the this gem has the same kind as that.
  public boolean sameKind(Gem that){
    return this.kind == that.kind;
  }

  // Tracks whether the gem has been flagged for removal.
  private boolean flag = false; 

  // Methods to mark gems for deletion from a board. setFlag() marks
  // the gem for removal, clearFlag() changes its state back, and
  // flagged() returns true/false on whether the gem is marked for
  // removal.
  public void setFlag(){
    this.flag = true;
  }
  public void clearFlag(){
    this.flag = false;
  }
  public boolean flagged(){
    return this.flag;
  }

  // Is this an empty space gem? No for standard gems
  private boolean empty;

  // Is the gem empty space?
  public boolean isEmpty(){
    return this.empty;
  }
  // Is this a gem rather than space?
  public boolean isFilled(){
    return !this.empty;
  }

  // Create a gem of a specific kind
  public Gem(int kind){
    this.kind = kind;
    this.empty = false;
  }

  // Create an empty space gem
  public static Gem makeEmpty(){
    Gem g = new Gem(-1);
    g.empty = true;
    return g;
  }
  
  public int getKind()
  {
	  return this.kind;
  }

  // Return an exact clone of the gem
  public Gem clone(){
    Gem g = new Gem(this.kind);
    g.flag = this.flag;
    g.empty = this.empty;
    return g;
  }

  // Return a string depicting the kind of the string
  public String kindString(){
    if(isEmpty()){ 
      return ""; 
    }
    return String.format("%d",this.kind);
  }

}
