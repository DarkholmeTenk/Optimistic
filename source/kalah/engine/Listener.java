package kalah.engine;

import kalah.Agent;
import kalah.engine.message.engine.EngineMessageFactory;

import java.io.*;
import java.net.Socket;

/**
 * Listens to a given socket and passes messages back to its owner.
 */
public class Listener {

  private final Agent owner;
  private final Socket socket;

  public Listener(Socket socket, Agent owner) {
    this.owner = owner;
    this.socket = socket;
  }

  /**
   * Makes the listener listen to its socket forever and send messages back to
   * its owner.
   */
  public void listen() throws IOException {
    BufferedReader input =
      new BufferedReader(new InputStreamReader(socket.getInputStream()));

    while (true) {
      owner.handle(EngineMessageFactory.getMessage(input.readLine()));
    }
  }

}
