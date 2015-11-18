package kalah.engine;

import kalah.Controller;
import kalah.engine.message.engine.EngineMessageFactory;

import java.io.*;
import java.net.Socket;

/**
 * Listens to a given socket and passes messages back to its owner.
 */
public class Listener {

  private final Controller controller;
  private final Socket socket;

  public Listener(Socket socket, Controller controller) {
    this.controller = controller;
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
      controller.handle(EngineMessageFactory.getMessage(input.readLine()));
    }
  }

}
