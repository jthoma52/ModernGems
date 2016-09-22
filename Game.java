// Represents state of a game of Modern Gems

import java.util.*;
import java.io.*;

public class Game{
	
  private Board board;
  private RemovalPolicy policy;
  private Player player;
  private int score;
  private int moveNum;

  // New public construct to create a brand new game. Makes a clone of
  // the given board so the Game operation does not affect the
  // original board.
  public Game(Board board, RemovalPolicy policy, Player player){
    this.board = board.clone();
    this.policy = policy;
    this.player = player;
    this.score = 0;
    this.moveNum = 0;
  }

  // Return the game's current board 
  public Board getBoard(){
    return this.board;
  }
  // Return how many moves have been made in the game
  public int getMoveNum(){
    return this.moveNum;
  }
  // Return the removal policy with which the game was initialized
  public RemovalPolicy getPolicy(){
    return this.policy;
  }
  // Return the player with which the game was initialized
  public Player getPlayer(){
    return this.player;
  }
  // Return the game's current score
  public int getScore(){
    return this.score;
  }
  // Make an adjustment to the score of the board by adding on the
  // change parameter. Usually not needed as removeGemAdjustScore()
  // does this automatically.
  public void adjustScore(int change){
    this.score += change;
  }

  // Remove the gem indicated and any connected gems according to the
  // removal policy. Update the score of the game and the move number.
  public void removeGemAdjustScore(int row, int col){
    int moveScore = this.policy.scoreMove(row, col, this.board);
    this.score += moveScore;
    this.policy.flagConnectedGems(row,col,this.board);
    this.board.doRemovals();
    this.moveNum++;
  }

  // Perform a single move by calling the players executeMove() method
  // once.
  public void play1(){
    this.board.clearFlags();
    this.player.executeMove(this);
  }

  // Play a game until there are no remaining Gems on the board by
  // repeatedly calling the player's executeMove() method. If the
  // parameter verbose is true, then print game state information
  // after each turn.
  public void playAll(boolean verbose){
    System.out.println(this.policy.description());
    while(this.board.hasValidGem()){
      if(verbose){
        System.out.printf("\n-- Move %3d --\nCurrent score: %d\n",
                          this.getMoveNum(),this.getScore());
        System.out.println(this.board.toString());
      }
      try{
        this.play1();
      }
      catch(QuitGameException e){
        if(verbose){ System.out.printf("Quitting\n"); }
        return;
      }
    }
    if(verbose){
      System.out.printf("Final Score: %d\n",this.getScore());
    }
    return;
  }

  // Save the state of the game to the named file. Requires that
  // Board.saveString() has been implemented.
  public void save(String filename) throws Exception{
    PrintWriter out = new PrintWriter(new File(filename));
    out.print(this.saveString());//no newline
    out.close();
  }

  // Create a stringy version of the game for saving. Requires that
  // Board.saveString() has been implemented.
  private String saveString(){
    String save = 
      String.format("%d %d %s %s\n%s",
		    this.score, this.moveNum,
		    this.player.saveString(),
                    this.policy.saveString(),
		    this.board.saveString());
    return save;
  }

  
  // Allows creation of an empty Game for loading in fromFile().
  private Game() {
  }

  // Load a game from the named file
  public static Game fromFile(String filename) throws Exception{
    // Load a game from the file filename
    Game game = new Game();
    Scanner in = null;
    try{
      in = new Scanner(new File(filename));
      game.score = in.nextInt();
      game.moveNum = in.nextInt();
      game.player = ModernGems.stringToPlayer(in.next());
      game.policy = ModernGems.stringToPolicy(in.next());
      in.nextLine();

      // Read in the entire board string and create it by passing in 
      String boardString = "";
      while(in.hasNextLine()){
        boardString += in.nextLine() + "\n";
      }
      System.out.printf("Creating board from string:\n%s\n",boardString);
      game.board = Board.fromSaveString(boardString);
    }
    finally{
      if(in != null){
	in.close();
      }
    }
    return game;
  }
}
