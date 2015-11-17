package kalah.engine.message.engine;

/**
 * Creates EngineMessage objects from a string recieved by the game engine
 * via a Listener.
 */
public class EngineMessageFactory {

  public static EngineMessage getMessage(String input) {
    return new GameOverMessage();
  }

}
