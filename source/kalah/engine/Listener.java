package kalah.engine;

import kalah.engine.message.engine.*;

import java.io.*;

/**
 * Listens to stdin and passes messages back to its owner.
 */
public class Listener
{
  private final BufferedReader input;

  public Listener(InputStream stream)
  {
    this.input = new BufferedReader(new InputStreamReader(stream));
  }

  public EngineMessage next() throws IOException
  {
    return EngineMessageFactory.getMessage(input.readLine());
  }
}
