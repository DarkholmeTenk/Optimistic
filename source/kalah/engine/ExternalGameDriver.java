package kalah.engine;

import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.engine.message.engine.*;

public class ExternalGameDriver extends GameDriver
{
  private final Speaker speaker;
  private final Listener listener;

  public ExternalGameDriver(Player agent, Listener listener)
  {
    super(agent, new ExternalAgent(listener), initGame());

    this.speaker = new Speaker();
    this.listener = listener;
  }

  private BoardState initGameState()
  {
    Message message = listener.next();

    if(message instanceof StartMessage)
      if((StartMessage message).getPosition() == Position.North)
        return new BoardState(7, 7, playerOne, playerTwo);
      else
        return new BoardState(7, 7, playerTwo, playerOne);
    else if (message instanceof GameOverMessage)
      throw new UnexpectedGameOverException();
    else
      throw new IllegalMessageException("Expected a start message.");
  }

}
