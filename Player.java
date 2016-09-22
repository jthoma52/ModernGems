// Interface to which all player classes must adhere
public interface Player{

  // Perform a single move in the given game. May throw a
  // QuitGameException to indicate that no move is desired.
  public void executeMove(Game game);

  // Return the name of class name of the Player
  public String toString();

  // Return the class name of the Player
  public String saveString();
}
