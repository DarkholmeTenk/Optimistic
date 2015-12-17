package kalah.engine.message.engine;

import kalah.engine.message.engine.Turn;

/**
 * Represents a swap message from the game engine.
 *
 * Syntax:
 *   "SWAP"
 */
public class SwapMessage extends ChangeMessage {
  protected SwapMessage(Turn turn) {
    super(turn);
  }
}
