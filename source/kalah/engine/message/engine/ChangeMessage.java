package kalah.engine.message.engine;

import kalah.game.board.*;

/**
 * Represents a change message from the game engine.
 *
 * Syntax:
 *   "CHANGE" ";" <MOVESWAP> ";" <STATE> ";" <TURN> <NL>
 *     <MOVESWAP> ::= <MOVE> | "SWAP"
 *     <STATE> ::= (<NAT> ",")*n <NAT> "," (<NAT> ",")*n <NAT>
 *     <NAT> ::= "0" | "1" | "2" | ... | k
 *     <TURN> ::= "YOU" | "OPP" | "END"
 */
public abstract class ChangeMessage implements EngineMessage {

  private final Player activePlayer;
  private final BoardState state;

  protected ChangeMessage(BoardState state, Player activePlayer) {
    this.state = state;
    this.activePlayer = activePlayer;
  }

  public Player getActivePlayer() { return activePlayer; }
  public BoardState getState() { return state; }

}
