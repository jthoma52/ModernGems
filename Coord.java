// Represents coordinates in a Matrix-like fashion: r is the row
// index, 0 is at the top, c is the column index, 0 is at the left. 
public class Coord {
  public final int row;
  public final int col;

  // Constructor, 
  public Coord(int ir, int ic){
    this.row = ir;
    this.col = ic;
  }

  // Are coordinates equal
  public boolean equals(Coord c){
    return this.row==c.row && this.col==c.col;
  }
  public String toString(){
    return String.format("(%d,%d)",row,col);
  }

}
