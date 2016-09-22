import java.util.*;
import java.io.*;

// Main interactive game class to play Modern Gems.
public class ModernGems {
  // All input comes from user form this Scanner by default
  public static Scanner stdin = null;

  // Supported RemovalPolicy classes 
  public static RemovalPolicy [] policies = {
	  
    new SingleRemovalPolicy(),
    new AdjacentRemovalPolicy(),
    new WholeRowColRemovalPolicy(),

  };
  public static String [] policyStrings;

  // Supported Player classes
  public static Player [] players = {
	
    new InteractivePlayer(stdin),
    new GreedyPlayer(),

  };
  public static String [] playerStrings;

  // Ensure input has been set up properly
  public static void checkInput() throws RuntimeException{
    if(stdin == null){
      throw new RuntimeException("stdin is not set");
    }
  }

  // Initialization of some static fields
  static{
    policyStrings = new String[policies.length];
    for(int i=0; i<policies.length; i++){
      policyStrings[i] = policies[i].toString();
    }
    playerStrings = new String[players.length];
    for(int i=0; i<players.length; i++){
      playerStrings[i] = players[i].toString();
    }
  };

  // Select a RemovalPolicy
  public static RemovalPolicy stringToPolicy(String option){
    RemovalPolicy policy = null;
    for(int i=0; i<policies.length; i++){
      if(option.equals(policyStrings[i])){
        policy = policies[i];
      }
    }
    return policy;
  }
  // Select interactive or AI player
  public static Player stringToPlayer(String option){
    Player player = null;
    for(int i=0; i<players.length; i++){
      if(option.equals(playerStrings[i])){
        player = players[i];
      }
    }
    // Interactive player is a special case; create a new one with the
    // current stdin
    if(player != null && player instanceof InteractivePlayer){
      player = new InteractivePlayer(stdin);
    }
    return player;
  }

  public static String boardPairs[][] = {
    {"tiny",
     "1  3  2\n"+
     "1  1  1\n"+
     "1  2  2\n"+
     ""
    },
    {"cross",
     "1  1  2  1  1\n"+
     "1  1  2  1  1\n"+
     "2  2  2  2  2\n"+
     "1  1  2  1  1\n"+
     "1  1  2  1  1\n"+
     ""
    },
    {"almostCross",
     "1  1  2  1  1\n"+
     "1  1  2  1  1\n"+
     "2  2  1  2  2\n"+
     "1  1  2  1  1\n"+
     "1  1  2  1  1\n"+
     ""
    },
    {"curve",
     "1  1  2\n"+
     "2  1  1\n"+
     "2  2  1\n"+
     ""
    },
    {"normal",
     "4  1  4  8  2\n"+
     "9 10  6  5  9\n"+
     "6  5  1  8  4\n"+
     "2  5  5 10 10\n"+
     "5  9  4  9  5\n"+
     ""
    },
    {"skinny",
     "3  3  2\n"+
     "3  2  3\n"+
     "1  2  1\n"+
     "2  2  2\n"+
     "2  1  3\n"+
     "1  1  2\n"+
     "2  1  1\n"+
     ""
    },
    {"large",
     "4  3  5  3  5  1  3  3  1  1\n"+
     "2  4  1  4  1  4  4  5  4  2\n"+
     "5  3  5  2  3  3  3  1  3  4\n"+
     "1  2  2  2  2  4  2  4  5  1\n"+
     "1  3  1  2  3  3  3  4  1  1\n"+
     "2  2  2  1  5  1  5  5  3  5\n"+
     "2  4  1  5  2  2  5  4  5  2\n"+
     ""
    },
  };
  
  // Pick the board
  public static String boardString(String option){
    for(String [] pair : boardPairs){
      if(option.equals(pair[0])){
        return pair[1];
      }
    }
    throw new RuntimeException("No board named "+option+" exists");
  }

  // Read from the user and start playing
  public static void main(String args[]) throws Exception{
    ModernGems.stdin = new Scanner(System.in);
    topLoop();
  }

  // Top loop for interactively playing
  public static void topLoop() throws Exception{
    ModernGems.checkInput();
    String loadString = "load", newString = "new", quitString = "quit";
    while(true){
      try{
	System.out.printf("----------------------\n");
	System.out.printf("Welcome to Modern Gems\n");
	System.out.printf("----------------------\n");
	System.out.printf("Select from:\n");
	System.out.printf("%8s: Start a new game\n",newString);
	System.out.printf("%8s: Load an existing game\n", loadString);
	System.out.printf("%8s: Quit\n",quitString);
	System.out.printf(">>> ");
	String option = ModernGems.stdin.next();
	if(option.equals(newString)){
	  interactiveNewGame();
	}
	else if(option.equals(loadString)){
	  interactiveLoadGame();
	}
	else if(option.equals(quitString)){
	  break;
	}
	else{
	  System.out.printf("'%s' is not a valid option\n",option);
	}
	System.out.println();
      }
      catch(NoSuchElementException e){ // End of input
	break;
      }
    }
    System.out.println("Thanks for playing!");
  }

  // Interactively create a new game
  public static void interactiveNewGame(){
    ModernGems.checkInput();
    System.out.printf("Select the board from the following:\n  ");
    for(String [] pair : ModernGems.boardPairs){
      System.out.printf("%s ",pair[0]);
    }
    System.out.println();
    System.out.println("Board choice:");
    System.out.printf(">>> ");
    String option = ModernGems.stdin.next();
    Board board = Board.fromSaveString(ModernGems.boardString(option));

    System.out.printf("Select the removal policy from the following:\n  ");
    for(String s : ModernGems.policyStrings){
      System.out.printf("%s ",s);
    }
    System.out.println();
    System.out.println("Policy choice:");
    System.out.printf(">>> ");
    RemovalPolicy policy = ModernGems.stringToPolicy(ModernGems.stdin.next());
    
    System.out.printf("Select the player from the following:\n  ");
    for(String s : ModernGems.playerStrings){
      System.out.printf("%s ",s);
    }
    System.out.println();
    System.out.println("Player choice:");
    System.out.printf(">>> ");
    Player player = ModernGems.stringToPlayer(ModernGems.stdin.next());
    
    System.out.println("\nLet's play!!\n");
    
    Game game = new Game(board, policy, player);
    boolean verbose = true;
    game.playAll(verbose);
  }

  // Interactively load an existing game; by convention, saved games
  // should have the .mg extension
  public static void interactiveLoadGame() throws Exception{
    ModernGems.checkInput();
    System.out.printf("Likely game files (*.mg) in the working directory:\n ");
    File [] files = (new File(".")).listFiles();
    for(File f : files){
      if(f.getName().endsWith(".mg")){ 
	System.out.printf("%s ",f.getName());
      }
    }
    System.out.println();
    System.out.printf("Name a file to load:\n");
    System.out.printf(">>> ");
    String filename = ModernGems.stdin.next();
    Game game = Game.fromFile(filename);
    System.out.printf("Game load completed from file '%s'. Let's play!\n",filename);
    game.playAll(true);
  }
}
