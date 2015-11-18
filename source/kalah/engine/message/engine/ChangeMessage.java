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
public abstract class ChangeMessage implements EngineMessage {}
