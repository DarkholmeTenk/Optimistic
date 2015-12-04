package kalah.engine;

import kalah.engine.message.agent.AgentMessage;

import java.io.*;

/**
 * Sends messages to stdout.
 */
public class Speaker
{
  private final DataOutputStream output;

  public Speaker(PrintStream stream)
  {
    this.output = new DataOutputStream(stream);
  }

  /**
   * Makes speaker say something on its output
   *
   * @param message The AgentMessage to send to the game engine
   */
  public void say(AgentMessage message) throws IOException
  {
    output.writeBytes(message.toString());
  }
}
