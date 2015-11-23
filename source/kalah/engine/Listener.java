package kalah.engine;

import kalah.Controller;
import kalah.engine.message.engine.EngineMessageFactory;

import java.io.*;

/**
 * Listens to stdin and passes messages back to its owner.
 */
public class Listener
{
  private final Controller controller;
  private final BufferedReader input;

  public Listener(Controller controller)
  {
    this.controller = controller;
    this.input = new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * Makes the listener listen forever and send messages to its owner.
   */
  public void listen() throws IOException
  {
    while (true)
    {
      controller.handle(EngineMessageFactory.getMessage(input.readLine()));
    }
  }
}
