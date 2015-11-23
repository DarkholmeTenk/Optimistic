package kalah;

import kalah.engine.*;
import kalah.engine.message.engine.EngineMessageFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * Used by a game engine to set up our agent for a tournament.
 */
public class AgentBootstrap {

  public static void main(String[] args) {

    System.out.println("Hello World!");

    Speaker speaker = new Speaker();
    Controller controller = new Controller(speaker);
    Listener listener = new Listener(controller);

    try {
      listener.listen();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

  }

}
