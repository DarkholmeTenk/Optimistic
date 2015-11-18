package kalah;

import kalah.engine.*;
import kalah.engine.message.engine.EngineMessageFactory;

import java.net.Socket;

/**
 * Used by a game engine to set up our agent for a tournament.
 */
public class AgentBootstrap {

  public static void main(String[] args) {

    System.out.println("Hello World!");

    Socket socket = new Socket();

    Speaker speaker = new Speaker(socket);
    Agent agent = new Agent(speaker);
    Listener listener = new Listener(socket, agent);

  }

}
