package kalah.engine.message.engine;

import kalah.engine.message.engine.exceptions.*;
import kalah.game.board.*;
import kalah.engine.message.engine.Turn;

/**
 * Creates EngineMessage objects from a string recieved by the game engine
 * via a Listener.
 */
public class EngineMessageFactory {

  public static EngineMessage getMessage(String input) {
    int indexOfFirstSemicolon = input.indexOf(';');

    String messageName;
    String messageBody;

    if (indexOfFirstSemicolon == -1) {
      messageName = input;
      messageBody = "";
    } else {
      messageName = input.substring(0, indexOfFirstSemicolon);
      messageBody = input.substring(indexOfFirstSemicolon + 1);
    }

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
    String changeTurn =
        messageBody.substring(messageBody.length() - 3, messageBody.length());
    Turn turn = changeTurn.equals("END") ? Turn.END :
        changeTurn.equals("YOU") ? Turn.YOU : Turn.OPP;

    if (changeType.equals("SWAP")) {
      return new SwapMessage(turn);
    } else {
      try {
        return new MoveMessage(Integer.parseInt(changeType), turn);
      } catch (NumberFormatException e) {
        throw new InvalidChangeTypeException(changeType);
      }
    }
  }

  private static StartMessage createStartMessage(String messageBody) {
    String position = messageBody;

    switch (position) {
      case "North": return new StartMessage(Position.North);
      case "South": return new StartMessage(Position.South);
      default: throw new InvalidPositionException(position);
    }
  }

}
