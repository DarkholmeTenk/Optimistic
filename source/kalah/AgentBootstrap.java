package kalah;

import kalah.engine.*;
import kalah.agent.RandomAgent;

import java.io.IOException;
import java.net.Socket;

/**
 * Used by a game engine to set up our agent for a tournament.
 */
public class AgentBootstrap {

  public static void main(String[] args) {

    System.out.println("Hello World!");

    GameDriver driver = new ExternalGameDriver(
        new RandomAgent(),
        new Listener(System.in),
        new Speaker(System.out));

    driver.complete();

  }

}
