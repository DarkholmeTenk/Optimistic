package kalah.engine.message.engine;

import kalah.game.board.*;

/**
 * Represents a move message from the game engine.
 *
 * Syntax:
 *   <MOVE> ::= "1" | "2" | ... | n
 */
public class MoveMessage extends ChangeMessage {

  private final int house;

  protected MoveMessage(int move, BoardState state, Player player) {
    super(state, player);
    this.house = move - 1;
  }

  public int getHouse() { return house; }

}
