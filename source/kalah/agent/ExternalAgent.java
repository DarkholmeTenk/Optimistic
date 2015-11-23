package kalah.agent;

public class ExternalAgent extends AbstractAgent
{
  private final Listener Listener;

  public ExternalAgent(Listener listener)
  {
    this.listener = listener;
  }

  /**
   * Gets this agent's next move given the last move that was made.
   *
   * @param lastMove The last move made in the game the agent is playing.
   * @return This agent's move.
   */
  public Action decideNextAction()
  {
    Message message = listener.next();

    if(message instanceof MoveMessage)
      return new MoveAction((MoveMessage message).getHouse());
    else if (message instanceof SwapMessage)
      return new SwapAction();
    else
      throw new IllegalMessageException("Expected a change message.");
  }

  /**
   * Called when an opponent makes a move so that the agent can update its
   * state.
   */
  public void informOfAction(Action action)
  {

  }

  /**
   * Called to inform the agent of the initial board state (useful for starting
   * a game part way through).
   */
  public void informOfState(BoardState board)
  {
    
  }
}
