package kalah.game;

import kalah.game.board;

public class Game
{
  private Player north;
  private Player south;
  private BoardState board;

  /**
   * Creates a game in the initial state
   */
  public Game(int size, int counters, Player north, Player south)
  {
    this.north = north;
    this.south = south;
    this.board = new BoardState(size, counters);
  }

  /**
   * Creates a game starting at the given state.
   */
  public Game(BoardState board, Player north, Player south)
  {
    this.north = north;
    this.south = south;
    this.board = board;
  }

  /**
   * Returns the player whose turn it is.
   */
  public Player getActivePlayer()
  {

  }
}
