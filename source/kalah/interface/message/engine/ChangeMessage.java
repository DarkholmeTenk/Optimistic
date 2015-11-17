package kalah.interface.message.engine;

import kalah.game.board.BoardState;

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
public class ChangeMessage implements EngineMessage {

  private final Player activePlayer;
  private final BoardState state;

  private ChangeMessage(BoardState state, Player activePlayer) {
    this.state = state;
    this.activePlayer = activePlayer;
  }

  public Player getActivePlayer() { return activePlayer; }
  public BoardState getState() { return state; }

}
