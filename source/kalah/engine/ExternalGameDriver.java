package kalah.engine;

import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.engine.message.engine.*;
import kalah.agent.ExternalAgent;
import kalah.agent.factories.*;
import kalah.game.board.*;
import kalah.engine.exceptions.*;

import java.io.IOException;

public class ExternalGameDriver extends GameDriver
{
  private final Speaker speaker;
  private final Listener listener;

  public ExternalGameDriver(
      AbstractAgentFactory agentFactory, Listener listener, Speaker speaker)
    throws IOException
  {
    super(
        agentFactory,
        new ExternalAgentFactory(listener, speaker),
        getInternalPlayer(listener),
        new BoardState(7, 7));

    this.speaker = speaker;
    this.listener = listener;
  }

  private static Player getInternalPlayer(Listener listener) throws IOException
  {
    EngineMessage message = listener.next();

    if(message instanceof StartMessage)
      if(((StartMessage) message).getPosition() == Position.South)
        return Player.PLAYER1;
      else
        return Player.PLAYER2;
    else if (message instanceof GameOverMessage)
      throw new UnexpectedMessageTypeException(message, StartMessage.class);
    else
      throw new UnexpectedMessageTypeException(message, StartMessage.class);
  }

}
