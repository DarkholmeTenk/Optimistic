package kalah.agent;

import kalah.engine.Listener;
import kalah.engine.message.engine.*;
import kalah.game.board.*;
import kalah.agent.exceptions.*;

public class ExternalAgent extends AbstractAgent
{
  private final Listener listener;

  public ExternalAgent(Listener listener)
  {
    this.listener = listener;
  }

  @Override
  public Action takeNextAction()
  {
    EngineMessage message = listener.next();

    if(message instanceof MoveMessage)
      return new Action(((MoveMessage) message).getHouse());
    else if (message instanceof SwapMessage)
      return new SwapAction();
    else
      throw new CannotActException("Expected a change message.");
  }

  @Override
  public void opponentAction(BoardState state, Action action) {}

  @Override
  public Action getNextMove(BoardState board) { return null; }

}
