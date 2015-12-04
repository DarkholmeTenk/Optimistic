package kalah;

import kalah.engine.*;
import kalah.agent.factories.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Used by a game engine to set up our agent for a tournament.
 */
public class AgentBootstrap {

  public static void main(String[] args) {

    AbstractAgentFactory factoryOne = new RandomAgentFactory();
    AbstractAgentFactory factoryTwo = new RandomAgentFactory();

    // TODO: Some logic to get the agent type from the args

    try
    {
      GameDriver driver = new ExternalGameDriver(
          new RandomAgentFactory(),
          new Listener(System.in),
          new Speaker(System.out));

      driver.complete();
    }
    catch (IOException e)
    {
      System.err.println("Something went wrong with the IO");
      System.err.println(e.getMessage());
    }

  }

}
