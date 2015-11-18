package kalah.engine.message.engine;

import kalah.game.board.*;

/**
 * Represents a swap message from the game engine.
 *
 * Syntax:
 *   "SWAP"
 */
public class SwapMessage extends ChangeMessage {

  protected SwapMessage(BoardState state, Player player) {
    super(state, player);
  }

}
