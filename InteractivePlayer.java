import java.util.Scanner;

public class InteractivePlayer implements Player{
  Scanner input;		// Use this field to get input from the user
  // Pass the scanner to be used in on construction
  public InteractivePlayer(Scanner input){
    this.input = input;
  }

  // Provides implementation of the one method in the Player
  // interface.  Now throws SaveGameException and QuitGameException
  // when those actions are desired.
  public void executeMove(Game game) {
    Board board = game.getBoard();
    System.out.printf("Choices:\n");
    System.out.printf("%-20s: %s\n","move row col","remove gem at row/col position");
    System.out.printf("%-20s: %s\n","save filename.mg","save game and continue playing");
    System.out.printf("%-20s: %s\n","quit","quit current game");
    System.out.printf(">>> ");
    String choice = input.next();
    if(choice.equals("move")){
      int row = input.nextInt();
      int col = input.nextInt();
      if(board.validGemAt(row,col)){
        game.removeGemAdjustScore(row,col);
      }
      else{
        System.out.printf("Invalid gem position (%d,%d)\n",row,col);
      }
    }
    else if(choice.equals("save")){
      String filename = input.next();
      try{
        game.save(filename);
      }
      catch(Exception e){
        System.err.printf("Could not save to file %s : %s\n",filename,e.getMessage());
      }
    }
    else if(choice.equals("quit")){
      throw new QuitGameException();
    }
    else{
      System.out.printf("Invalid option '%s'\n\n",choice);
    }
  }

  public String toString(){ return "InteractivePlayer"; }
  public String saveString(){ return "InteractivePlayer"; }

}
