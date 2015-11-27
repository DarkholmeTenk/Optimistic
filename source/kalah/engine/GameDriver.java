package kalah.engine;

import kalah.agent.AbstractAgent;
import kalah.game.board.*;

public abstract class GameDriver
{
  protected final AbstractAgent playerOne;
  protected final AbstractAgent playerTwo;
  protected BoardState board;

  protected GameDriver(
      AbstractAgent playerOne, AbstractAgent playerTwo, BoardState board)
  {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.board = board;

    playerOne.informOfState(board);
    playerTwo.informOfState(board);
  }

  private AbstractAgent getAgent(Player player)
  {
    if (player == Player.PLAYER1)
      return playerOne;
    else
      return playerTwo;
  }

  /**
   * Steps through one turn of a game.
   *
   * @return False if the game cannot continue, true otherwise
   */
  public boolean step()
  {
    Player active = board.getCurrentPlayerTurn();
    Player inactive = active.getOpponent();
    Action action = getAgent(active).takeNextAction();

    if (action != null)
    {
      board.takeAction(action);
      getAgent(inactive).informOfAction(action);
      return true;
    }
    else
      return false;
  }

  /**
   * Runs through a complete game.
   */
  public void complete()
  {
    while(step());
  }

  public BoardState getGameState() { return board; }
}
