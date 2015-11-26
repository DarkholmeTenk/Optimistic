package kalah.engine;

import kalah.engine.message.agent.AgentMessage;

import java.io.*;

/**
 * Sends messages to stdout.
 */
public class Speaker
{
  private final DataOutputStream output;

  public Speaker()
  {
    this.output = new DataOutputStream(System.out);
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
