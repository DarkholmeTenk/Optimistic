package kalah.engine;

import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.engine.message.engine.*;
import kalah.agent.AbstractAgent;
import kalah.agent.ExternalAgent;
import kalah.game.board.*;
import kalah.engine.exceptions.*;

public class ExternalGameDriver extends GameDriver
{
  private final Speaker speaker;
  private final Listener listener;

  public ExternalGameDriver(
      AbstractAgent agent, Listener listener, Speaker speaker)
  {
    super(agent, new ExternalAgent(listener), null);

    this.speaker = speaker;
    this.listener = listener;
    this.board = initGameState();
  }

  private BoardState initGameState()
  {
    EngineMessage message = listener.next();

    if(message instanceof StartMessage)
      if(((StartMessage) message).getPosition() == Position.North)
        return new BoardState(7, 7, playerOne, playerTwo);
      else
        return new BoardState(7, 7, playerTwo, playerOne);
    else if (message instanceof GameOverMessage)
      throw new UnexpectedMessageTypeException(message, StartMessage.class);
    else
      throw new UnexpectedMessageTypeException(message, StartMessage.class);
  }

}
