package kalah.engine;

public abstract class GameDriver
{
  protected final AbstractAgent playerOne;
  protected final AbstractAgent playerTwo;
  private final Game game;

  protected GameDriver(
      AbstractAgent playerOne, AbstractAgent playerTwo, Game game)
  {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.game = game;

    playerOne.informOfState(game.getBoardState());
    playerTwo.informOfState(game.getBoardState());
  }

  private AbstractAgent getAgent(Player player)
  {
    if (player == Player.Player1)
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
    Player active = game.getActivePlayer();
    Player inactive = active.getOpponent();
    Acion action = getAgent(active).decideNextAction();

    if (action != null)
    {
      game.apply(action);
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

  public Game getGame() { return game; }
}
