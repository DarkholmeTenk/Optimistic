package kalah.interface.message.engine;

/**
 * Represents a move message from the game engine.
 *
 * Syntax:
 *   <MOVE> ::= "1" | "2" | ... | n
 */
public class MoveMessage extends ChangeMessage {

  private final int house;

  private StartMessage(int move) {
    this.house = move - 1;
  }

  public int getHouse() { return house; }

}
