package kalah.engine.message.engine;

import kalah.engine.message.engine.exceptions.*;
import kalah.game.board.*;

/**
 * Creates EngineMessage objects from a string recieved by the game engine
 * via a Listener.
 */
public class EngineMessageFactory {

  public static EngineMessage getMessage(String input) {
    int indexOfFirstSemicolon = input.indexOf(';');
    String messageName = input.substring(0, indexOfFirstSemicolon);
    String messageBody = input.substring(indexOfFirstSemicolon + 1);

    switch(messageName) {
      case "START": return createStartMessage(messageBody);
      case "CHANGE": return createChangeMessage(messageBody);
      case "END": return new GameOverMessage();
      default: throw new InvalidMessageNameException(messageName);
    }


  }

  private static ChangeMessage createChangeMessage(String messageBody) {
    int indexOfFirstSemicolon = messageBody.indexOf(';');
    String changeType = messageBody.substring(0, indexOfFirstSemicolon);
    String changeBody = messageBody.substring(indexOfFirstSemicolon + 1);

    if (changeType.equals("SWAP")) {
      return new SwapMessage();
    } else {
      try {
        return new MoveMessage(Integer.parseInt(changeType));
      } catch (NumberFormatException e) {
        throw new InvalidChangeTypeException(changeType);
      }
    }
  }

  private static StartMessage createStartMessage(String messageBody) {
    String position = messageBody.substring(0, messageBody.indexOf('\n'));

    switch (position) {
      case "North": return new StartMessage(Position.North);
      case "South": return new StartMessage(Position.South);
      default: throw new InvalidPositionException(position);
    }
  }

}
