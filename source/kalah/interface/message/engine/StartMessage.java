package kalah.interface.message.engine;

/**
 * Represents a start message from the game engine.
 *
 * Syntax:
 *   "START" ";" <POSITION> <NL>
 *     <POSITION> ::= "North" | "South"
 */
public class StartMessage implements EngineMessage {

  private final Position position;

  private StartMessage(Position position) {
    this.position = position;
  }

  public Position getPosition() { return position; }

}
