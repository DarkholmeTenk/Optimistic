package kalah.engine;

import kalah.engine.message.engine.EngineMessageFactory;

import java.io.*;

/**
 * Listens to stdin and passes messages back to its owner.
 */
public class Listener
{
  private final BufferedReader input;

  public Listener()
  {
    this.input = new BufferedReader(new InputStreamReader(System.in));
  }

  public next() throws IOException
  {
    return EngineMessageFactory.getMessage(input.readLine());
  }
}
