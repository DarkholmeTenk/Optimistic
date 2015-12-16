package kalah.engine;

import kalah.engine.message.agent.AgentMessage;
import kalah.program.Configuration;

import java.io.*;

/**
 * Sends messages to stdout.
 */
public class Speaker
{
	private final DataOutputStream output;

	public Speaker(PrintStream stream)
	{
		output = new DataOutputStream(stream);
	}

	/**
	 * Makes speaker say something on its output
	 *
	 * @param message The AgentMessage to send to the game engine
	 */
	public void say(AgentMessage message) throws IOException
	{
		Configuration.log("Making move: " + message);
		output.writeBytes(message.toString());
	}
}
