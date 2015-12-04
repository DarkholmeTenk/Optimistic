package kalah.engine;

import kalah.agent.AbstractAgent;
import kalah.agent.factories.*;
import kalah.game.board.*;

public abstract class GameDriver
{
  protected final AbstractAgent playerOne;
  protected final AbstractAgent playerTwo;
  protected BoardState board;

  protected GameDriver(
      AbstractAgentFactory factoryOne,
      AbstractAgentFactory factoryTwo,
      Player factoryOneIs,
      BoardState board)
  {
    if (factoryOneIs == Player.PLAYER1)
    {
      this.playerOne = factoryOne.getAgent(Player.PLAYER1);
      this.playerTwo = factoryTwo.getAgent(Player.PLAYER2);
    }
    else
    {
      this.playerTwo = factoryTwo.getAgent(Player.PLAYER1);
      this.playerOne = factoryOne.getAgent(Player.PLAYER2);
    }

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
