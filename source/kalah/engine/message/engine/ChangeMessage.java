package kalah.engine.message.engine;

import kalah.engine.message.engine.Turn;

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
  private final Turn turn;

  protected ChangeMessage(Turn turn) {
    this.turn = turn;
  }

  public Turn getTurn() {
    return turn;
  }
}
