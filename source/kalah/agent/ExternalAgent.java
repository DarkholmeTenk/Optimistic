package kalah.agent;

import kalah.engine.Listener;
import kalah.engine.message.engine.*;
import kalah.game.board.*;

import java.io.IOException;

public class ExternalAgent extends AbstractAgent
{
  private final Listener listener;

  public ExternalAgent(Player player, Listener listener)
  {
    super(player);
    this.listener = listener;
  }

  @Override
  public void opponentAction(BoardState state, Action action) {}

  @Override
  public Action getNextMove(BoardState board)
  {
    try
    {
      EngineMessage message = listener.next();

      if(message instanceof MoveMessage)
        return new Action(this.agentPlayer, ((MoveMessage) message).getHouse());
      else if (message instanceof SwapMessage)
        return new SwapAction();
      else
        return null;
    }
    catch (IOException e)
    {
      return null;
    }
  }

}
