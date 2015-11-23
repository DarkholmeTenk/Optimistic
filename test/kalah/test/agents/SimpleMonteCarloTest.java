package kalah.test.agents;

import static org.junit.Assert.*;
import kalah.agent.RandomAgent;
import kalah.agent.SimpleMonteCarloAgent;
import kalah.program.TwoAgentGame;

import org.junit.Test;

public class SimpleMonteCarloTest extends AgentTest
{
	private int runs = 20;

	@Test
	public void test()
	{
		TwoAgentGame game = new TwoAgentGame(SimpleMonteCarloAgent.playerOne, RandomAgent.playerTwo);
		double score = 0;
		for(int i = 0; i < runs; i++)
		{
			if(score < 0)
				fail("No way the monte carlo should have lost");
			score += game.play(start) / (double) runs;
		}
		System.out.println("Overall score: " + score);
	}

}
