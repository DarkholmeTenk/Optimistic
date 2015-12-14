package kalah.program;

import java.util.List;

import kalah.agent.factories.AbstractAgentFactory;
import kalah.agent.factories.AbstractAgentFactoryFactory;
import kalah.engine.ExternalGameDriver;
import kalah.engine.GameDriver;
import kalah.engine.InternalGameDriver;
import kalah.engine.Listener;
import kalah.engine.Speaker;
import kalah.game.board.BoardState;
import kalah.game.board.Player;

public class Optimism
{

	public static AbstractAgentFactory[] getAgents(List<String> remainingArgs)
	{
		AbstractAgentFactory[] agents = new AbstractAgentFactory[2];
		AbstractAgentFactory af1 = null;
		AbstractAgentFactory af2 = null;
		for(String s : remainingArgs)
		{
			AbstractAgentFactory factory = AbstractAgentFactoryFactory.get(s);
			if(factory == null)
				throw new RuntimeException("Agent not recognised " + s);
			if(af1 == null)
				af1 = factory;
			else if(af2 == null)
				af2 = factory;
			else
				throw new RuntimeException("3 agents detected");
		}
		if(af1 == null)
			throw new RuntimeException("No agent detected");
		agents[0] = af1;
		agents[1] = af2;
		return agents;
	}

	public static void main(String... args)
	{
		GameDriver gd = null;
		List<String> remainingArgs = Configuration.readArgs(args);
		AbstractAgentFactory[] afs = getAgents(remainingArgs);
		try
		{
			if(afs[1] == null)
				gd = new ExternalGameDriver(afs[0], new Listener(System.in), new Speaker(System.out));
			else
				gd = new InternalGameDriver(afs[0], afs[1], Player.PLAYER1, new BoardState(Configuration.boardSize, Configuration.boardC));
			gd.complete();
			if(gd instanceof InternalGameDriver)
			{
				BoardState bs = gd.getGameState();
				System.out.println("Player 1: " + bs.getScore(Player.PLAYER1));
				System.out.println("Player 2: " + bs.getScore(Player.PLAYER2));
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
