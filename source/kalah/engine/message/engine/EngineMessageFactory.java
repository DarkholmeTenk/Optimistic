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

    if      (messageName.equals("START")) return createStartMessage(messageBody);
    else if (messageName.equals("CHANGE")) return createChangeMessage(messageBody);
    else if (messageName.equals("END")) return new GameOverMessage();
    else throw new InvalidMessageNameException(messageName);
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

    if      (position.equals("North")) return new StartMessage(Position.North);
    else if (position.equals("South")) return new StartMessage(Position.South);
    else throw new InvalidPositionException(position);
  }

}
